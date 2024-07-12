package io.netty.handler.codec.mqtt;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.util.CharsetUtil;
import java.util.ArrayList;
import java.util.List;

public final class MqttDecoder extends ReplayingDecoder<MqttDecoder.DecoderState> {
	private static final int DEFAULT_MAX_BYTES_IN_MESSAGE = 8092;
	private MqttFixedHeader mqttFixedHeader;
	private Object variableHeader;
	private int bytesRemainingInVariablePart;
	private final int maxBytesInMessage;

	public MqttDecoder() {
		this(8092);
	}

	public MqttDecoder(int maxBytesInMessage) {
		super(MqttDecoder.DecoderState.READ_FIXED_HEADER);
		this.maxBytesInMessage = maxBytesInMessage;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
		switch ((MqttDecoder.DecoderState)this.state()) {
			case READ_FIXED_HEADER:
				try {
					this.mqttFixedHeader = decodeFixedHeader(buffer);
					this.bytesRemainingInVariablePart = this.mqttFixedHeader.remainingLength();
					this.checkpoint(MqttDecoder.DecoderState.READ_VARIABLE_HEADER);
				} catch (Exception var8) {
					out.add(this.invalidMessage(var8));
					return;
				}
			case READ_VARIABLE_HEADER:
				try {
					if (this.bytesRemainingInVariablePart > this.maxBytesInMessage) {
						throw new DecoderException("too large message: " + this.bytesRemainingInVariablePart + " bytes");
					}

					MqttDecoder.Result<?> decodedVariableHeader = decodeVariableHeader(buffer, this.mqttFixedHeader);
					this.variableHeader = decodedVariableHeader.value;
					this.bytesRemainingInVariablePart = this.bytesRemainingInVariablePart - decodedVariableHeader.numberOfBytesConsumed;
					this.checkpoint(MqttDecoder.DecoderState.READ_PAYLOAD);
				} catch (Exception var7) {
					out.add(this.invalidMessage(var7));
					return;
				}
			case READ_PAYLOAD:
				try {
					MqttDecoder.Result<?> decodedPayload = decodePayload(buffer, this.mqttFixedHeader.messageType(), this.bytesRemainingInVariablePart, this.variableHeader);
					this.bytesRemainingInVariablePart = this.bytesRemainingInVariablePart - decodedPayload.numberOfBytesConsumed;
					if (this.bytesRemainingInVariablePart != 0) {
						throw new DecoderException("non-zero remaining payload bytes: " + this.bytesRemainingInVariablePart + " (" + this.mqttFixedHeader.messageType() + ')');
					}

					this.checkpoint(MqttDecoder.DecoderState.READ_FIXED_HEADER);
					MqttMessage message = MqttMessageFactory.newMessage(this.mqttFixedHeader, this.variableHeader, decodedPayload.value);
					this.mqttFixedHeader = null;
					this.variableHeader = null;
					out.add(message);
					break;
				} catch (Exception var6) {
					out.add(this.invalidMessage(var6));
					return;
				}
			case BAD_MESSAGE:
				buffer.skipBytes(this.actualReadableBytes());
				break;
			default:
				throw new Error();
		}
	}

	private MqttMessage invalidMessage(Throwable cause) {
		this.checkpoint(MqttDecoder.DecoderState.BAD_MESSAGE);
		return MqttMessageFactory.newInvalidMessage(cause);
	}

	private static MqttFixedHeader decodeFixedHeader(ByteBuf buffer) {
		short b1 = buffer.readUnsignedByte();
		MqttMessageType messageType = MqttMessageType.valueOf(b1 >> 4);
		boolean dupFlag = (b1 & 8) == 8;
		int qosLevel = (b1 & 6) >> 1;
		boolean retain = (b1 & 1) != 0;
		int remainingLength = 0;
		int multiplier = 1;
		int loops = 0;

		short digit;
		do {
			digit = buffer.readUnsignedByte();
			remainingLength += (digit & 127) * multiplier;
			multiplier *= 128;
		} while ((digit & 128) != 0 && ++loops < 4);

		if (loops == 4 && (digit & 128) != 0) {
			throw new DecoderException("remaining length exceeds 4 digits (" + messageType + ')');
		} else {
			MqttFixedHeader decodedFixedHeader = new MqttFixedHeader(messageType, dupFlag, MqttQoS.valueOf(qosLevel), retain, remainingLength);
			return MqttCodecUtil.validateFixedHeader(MqttCodecUtil.resetUnusedFields(decodedFixedHeader));
		}
	}

	private static MqttDecoder.Result<?> decodeVariableHeader(ByteBuf buffer, MqttFixedHeader mqttFixedHeader) {
		switch (mqttFixedHeader.messageType()) {
			case CONNECT:
				return decodeConnectionVariableHeader(buffer);
			case CONNACK:
				return decodeConnAckVariableHeader(buffer);
			case SUBSCRIBE:
			case UNSUBSCRIBE:
			case SUBACK:
			case UNSUBACK:
			case PUBACK:
			case PUBREC:
			case PUBCOMP:
			case PUBREL:
				return decodeMessageIdVariableHeader(buffer);
			case PUBLISH:
				return decodePublishVariableHeader(buffer, mqttFixedHeader);
			case PINGREQ:
			case PINGRESP:
			case DISCONNECT:
				return new MqttDecoder.Result(null, 0);
			default:
				return new MqttDecoder.Result(null, 0);
		}
	}

	private static MqttDecoder.Result<MqttConnectVariableHeader> decodeConnectionVariableHeader(ByteBuf buffer) {
		MqttDecoder.Result<String> protoString = decodeString(buffer);
		int numberOfBytesConsumed = protoString.numberOfBytesConsumed;
		byte protocolLevel = buffer.readByte();
		numberOfBytesConsumed++;
		MqttVersion mqttVersion = MqttVersion.fromProtocolNameAndLevel(protoString.value, protocolLevel);
		int b1 = buffer.readUnsignedByte();
		numberOfBytesConsumed++;
		MqttDecoder.Result<Integer> keepAlive = decodeMsbLsb(buffer);
		numberOfBytesConsumed += keepAlive.numberOfBytesConsumed;
		boolean hasUserName = (b1 & 128) == 128;
		boolean hasPassword = (b1 & 64) == 64;
		boolean willRetain = (b1 & 32) == 32;
		int willQos = (b1 & 24) >> 3;
		boolean willFlag = (b1 & 4) == 4;
		boolean cleanSession = (b1 & 2) == 2;
		if (mqttVersion == MqttVersion.MQTT_3_1_1) {
			boolean zeroReservedFlag = (b1 & 1) == 0;
			if (!zeroReservedFlag) {
				throw new DecoderException("non-zero reserved flag");
			}
		}

		MqttConnectVariableHeader mqttConnectVariableHeader = new MqttConnectVariableHeader(
			mqttVersion.protocolName(), mqttVersion.protocolLevel(), hasUserName, hasPassword, willRetain, willQos, willFlag, cleanSession, keepAlive.value
		);
		return new MqttDecoder.Result<>(mqttConnectVariableHeader, numberOfBytesConsumed);
	}

	private static MqttDecoder.Result<MqttConnAckVariableHeader> decodeConnAckVariableHeader(ByteBuf buffer) {
		boolean sessionPresent = (buffer.readUnsignedByte() & 1) == 1;
		byte returnCode = buffer.readByte();
		int numberOfBytesConsumed = 2;
		MqttConnAckVariableHeader mqttConnAckVariableHeader = new MqttConnAckVariableHeader(MqttConnectReturnCode.valueOf(returnCode), sessionPresent);
		return new MqttDecoder.Result<>(mqttConnAckVariableHeader, 2);
	}

	private static MqttDecoder.Result<MqttMessageIdVariableHeader> decodeMessageIdVariableHeader(ByteBuf buffer) {
		MqttDecoder.Result<Integer> messageId = decodeMessageId(buffer);
		return new MqttDecoder.Result<>(MqttMessageIdVariableHeader.from(messageId.value), messageId.numberOfBytesConsumed);
	}

	private static MqttDecoder.Result<MqttPublishVariableHeader> decodePublishVariableHeader(ByteBuf buffer, MqttFixedHeader mqttFixedHeader) {
		MqttDecoder.Result<String> decodedTopic = decodeString(buffer);
		if (!MqttCodecUtil.isValidPublishTopicName(decodedTopic.value)) {
			throw new DecoderException("invalid publish topic name: " + decodedTopic.value + " (contains wildcards)");
		} else {
			int numberOfBytesConsumed = decodedTopic.numberOfBytesConsumed;
			int messageId = -1;
			if (mqttFixedHeader.qosLevel().value() > 0) {
				MqttDecoder.Result<Integer> decodedMessageId = decodeMessageId(buffer);
				messageId = decodedMessageId.value;
				numberOfBytesConsumed += decodedMessageId.numberOfBytesConsumed;
			}

			MqttPublishVariableHeader mqttPublishVariableHeader = new MqttPublishVariableHeader(decodedTopic.value, messageId);
			return new MqttDecoder.Result<>(mqttPublishVariableHeader, numberOfBytesConsumed);
		}
	}

	private static MqttDecoder.Result<Integer> decodeMessageId(ByteBuf buffer) {
		MqttDecoder.Result<Integer> messageId = decodeMsbLsb(buffer);
		if (!MqttCodecUtil.isValidMessageId(messageId.value)) {
			throw new DecoderException("invalid messageId: " + messageId.value);
		} else {
			return messageId;
		}
	}

	private static MqttDecoder.Result<?> decodePayload(ByteBuf buffer, MqttMessageType messageType, int bytesRemainingInVariablePart, Object variableHeader) {
		switch (messageType) {
			case CONNECT:
				return decodeConnectionPayload(buffer, (MqttConnectVariableHeader)variableHeader);
			case CONNACK:
			case UNSUBACK:
			case PUBACK:
			case PUBREC:
			case PUBCOMP:
			case PUBREL:
			default:
				return new MqttDecoder.Result(null, 0);
			case SUBSCRIBE:
				return decodeSubscribePayload(buffer, bytesRemainingInVariablePart);
			case UNSUBSCRIBE:
				return decodeUnsubscribePayload(buffer, bytesRemainingInVariablePart);
			case SUBACK:
				return decodeSubackPayload(buffer, bytesRemainingInVariablePart);
			case PUBLISH:
				return decodePublishPayload(buffer, bytesRemainingInVariablePart);
		}
	}

	private static MqttDecoder.Result<MqttConnectPayload> decodeConnectionPayload(ByteBuf buffer, MqttConnectVariableHeader mqttConnectVariableHeader) {
		MqttDecoder.Result<String> decodedClientId = decodeString(buffer);
		String decodedClientIdValue = decodedClientId.value;
		MqttVersion mqttVersion = MqttVersion.fromProtocolNameAndLevel(mqttConnectVariableHeader.name(), (byte)mqttConnectVariableHeader.version());
		if (!MqttCodecUtil.isValidClientId(mqttVersion, decodedClientIdValue)) {
			throw new MqttIdentifierRejectedException("invalid clientIdentifier: " + decodedClientIdValue);
		} else {
			int numberOfBytesConsumed = decodedClientId.numberOfBytesConsumed;
			MqttDecoder.Result<String> decodedWillTopic = null;
			MqttDecoder.Result<byte[]> decodedWillMessage = null;
			if (mqttConnectVariableHeader.isWillFlag()) {
				decodedWillTopic = decodeString(buffer, 0, 32767);
				numberOfBytesConsumed += decodedWillTopic.numberOfBytesConsumed;
				decodedWillMessage = decodeByteArray(buffer);
				numberOfBytesConsumed += decodedWillMessage.numberOfBytesConsumed;
			}

			MqttDecoder.Result<String> decodedUserName = null;
			MqttDecoder.Result<byte[]> decodedPassword = null;
			if (mqttConnectVariableHeader.hasUserName()) {
				decodedUserName = decodeString(buffer);
				numberOfBytesConsumed += decodedUserName.numberOfBytesConsumed;
			}

			if (mqttConnectVariableHeader.hasPassword()) {
				decodedPassword = decodeByteArray(buffer);
				numberOfBytesConsumed += decodedPassword.numberOfBytesConsumed;
			}

			MqttConnectPayload mqttConnectPayload = new MqttConnectPayload(
				decodedClientId.value,
				decodedWillTopic != null ? decodedWillTopic.value : null,
				decodedWillMessage != null ? decodedWillMessage.value : null,
				decodedUserName != null ? decodedUserName.value : null,
				decodedPassword != null ? decodedPassword.value : null
			);
			return new MqttDecoder.Result<>(mqttConnectPayload, numberOfBytesConsumed);
		}
	}

	private static MqttDecoder.Result<MqttSubscribePayload> decodeSubscribePayload(ByteBuf buffer, int bytesRemainingInVariablePart) {
		List<MqttTopicSubscription> subscribeTopics = new ArrayList();
		int numberOfBytesConsumed = 0;

		while (numberOfBytesConsumed < bytesRemainingInVariablePart) {
			MqttDecoder.Result<String> decodedTopicName = decodeString(buffer);
			numberOfBytesConsumed += decodedTopicName.numberOfBytesConsumed;
			int qos = buffer.readUnsignedByte() & 3;
			numberOfBytesConsumed++;
			subscribeTopics.add(new MqttTopicSubscription(decodedTopicName.value, MqttQoS.valueOf(qos)));
		}

		return new MqttDecoder.Result<>(new MqttSubscribePayload(subscribeTopics), numberOfBytesConsumed);
	}

	private static MqttDecoder.Result<MqttSubAckPayload> decodeSubackPayload(ByteBuf buffer, int bytesRemainingInVariablePart) {
		List<Integer> grantedQos = new ArrayList();
		int numberOfBytesConsumed = 0;

		while (numberOfBytesConsumed < bytesRemainingInVariablePart) {
			int qos = buffer.readUnsignedByte();
			if (qos != MqttQoS.FAILURE.value()) {
				qos &= 3;
			}

			numberOfBytesConsumed++;
			grantedQos.add(qos);
		}

		return new MqttDecoder.Result<>(new MqttSubAckPayload(grantedQos), numberOfBytesConsumed);
	}

	private static MqttDecoder.Result<MqttUnsubscribePayload> decodeUnsubscribePayload(ByteBuf buffer, int bytesRemainingInVariablePart) {
		List<String> unsubscribeTopics = new ArrayList();
		int numberOfBytesConsumed = 0;

		while (numberOfBytesConsumed < bytesRemainingInVariablePart) {
			MqttDecoder.Result<String> decodedTopicName = decodeString(buffer);
			numberOfBytesConsumed += decodedTopicName.numberOfBytesConsumed;
			unsubscribeTopics.add(decodedTopicName.value);
		}

		return new MqttDecoder.Result<>(new MqttUnsubscribePayload(unsubscribeTopics), numberOfBytesConsumed);
	}

	private static MqttDecoder.Result<ByteBuf> decodePublishPayload(ByteBuf buffer, int bytesRemainingInVariablePart) {
		ByteBuf b = buffer.readRetainedSlice(bytesRemainingInVariablePart);
		return new MqttDecoder.Result<>(b, bytesRemainingInVariablePart);
	}

	private static MqttDecoder.Result<String> decodeString(ByteBuf buffer) {
		return decodeString(buffer, 0, Integer.MAX_VALUE);
	}

	private static MqttDecoder.Result<String> decodeString(ByteBuf buffer, int minBytes, int maxBytes) {
		MqttDecoder.Result<Integer> decodedSize = decodeMsbLsb(buffer);
		int size = decodedSize.value;
		int numberOfBytesConsumed = decodedSize.numberOfBytesConsumed;
		if (size >= minBytes && size <= maxBytes) {
			String s = buffer.toString(buffer.readerIndex(), size, CharsetUtil.UTF_8);
			buffer.skipBytes(size);
			numberOfBytesConsumed += size;
			return new MqttDecoder.Result<>(s, numberOfBytesConsumed);
		} else {
			buffer.skipBytes(size);
			numberOfBytesConsumed += size;
			return new MqttDecoder.Result<>(null, numberOfBytesConsumed);
		}
	}

	private static MqttDecoder.Result<byte[]> decodeByteArray(ByteBuf buffer) {
		MqttDecoder.Result<Integer> decodedSize = decodeMsbLsb(buffer);
		int size = decodedSize.value;
		byte[] bytes = new byte[size];
		buffer.readBytes(bytes);
		return new MqttDecoder.Result<>(bytes, decodedSize.numberOfBytesConsumed + size);
	}

	private static MqttDecoder.Result<Integer> decodeMsbLsb(ByteBuf buffer) {
		return decodeMsbLsb(buffer, 0, 65535);
	}

	private static MqttDecoder.Result<Integer> decodeMsbLsb(ByteBuf buffer, int min, int max) {
		short msbSize = buffer.readUnsignedByte();
		short lsbSize = buffer.readUnsignedByte();
		int numberOfBytesConsumed = 2;
		int result = msbSize << 8 | lsbSize;
		if (result < min || result > max) {
			result = -1;
		}

		return new MqttDecoder.Result<>(result, 2);
	}

	static enum DecoderState {
		READ_FIXED_HEADER,
		READ_VARIABLE_HEADER,
		READ_PAYLOAD,
		BAD_MESSAGE;
	}

	private static final class Result<T> {
		private final T value;
		private final int numberOfBytesConsumed;

		Result(T value, int numberOfBytesConsumed) {
			this.value = value;
			this.numberOfBytesConsumed = numberOfBytesConsumed;
		}
	}
}
