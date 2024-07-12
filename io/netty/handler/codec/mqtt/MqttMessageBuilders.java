package io.netty.handler.codec.mqtt;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import java.util.ArrayList;
import java.util.List;

public final class MqttMessageBuilders {
	public static MqttMessageBuilders.ConnectBuilder connect() {
		return new MqttMessageBuilders.ConnectBuilder();
	}

	public static MqttMessageBuilders.ConnAckBuilder connAck() {
		return new MqttMessageBuilders.ConnAckBuilder();
	}

	public static MqttMessageBuilders.PublishBuilder publish() {
		return new MqttMessageBuilders.PublishBuilder();
	}

	public static MqttMessageBuilders.SubscribeBuilder subscribe() {
		return new MqttMessageBuilders.SubscribeBuilder();
	}

	public static MqttMessageBuilders.UnsubscribeBuilder unsubscribe() {
		return new MqttMessageBuilders.UnsubscribeBuilder();
	}

	private MqttMessageBuilders() {
	}

	public static final class ConnAckBuilder {
		private MqttConnectReturnCode returnCode;
		private boolean sessionPresent;

		ConnAckBuilder() {
		}

		public MqttMessageBuilders.ConnAckBuilder returnCode(MqttConnectReturnCode returnCode) {
			this.returnCode = returnCode;
			return this;
		}

		public MqttMessageBuilders.ConnAckBuilder sessionPresent(boolean sessionPresent) {
			this.sessionPresent = sessionPresent;
			return this;
		}

		public MqttConnAckMessage build() {
			MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.CONNACK, false, MqttQoS.AT_MOST_ONCE, false, 0);
			MqttConnAckVariableHeader mqttConnAckVariableHeader = new MqttConnAckVariableHeader(this.returnCode, this.sessionPresent);
			return new MqttConnAckMessage(mqttFixedHeader, mqttConnAckVariableHeader);
		}
	}

	public static final class ConnectBuilder {
		private MqttVersion version = MqttVersion.MQTT_3_1_1;
		private String clientId;
		private boolean cleanSession;
		private boolean hasUser;
		private boolean hasPassword;
		private int keepAliveSecs;
		private boolean willFlag;
		private boolean willRetain;
		private MqttQoS willQos = MqttQoS.AT_MOST_ONCE;
		private String willTopic;
		private byte[] willMessage;
		private String username;
		private byte[] password;

		ConnectBuilder() {
		}

		public MqttMessageBuilders.ConnectBuilder protocolVersion(MqttVersion version) {
			this.version = version;
			return this;
		}

		public MqttMessageBuilders.ConnectBuilder clientId(String clientId) {
			this.clientId = clientId;
			return this;
		}

		public MqttMessageBuilders.ConnectBuilder cleanSession(boolean cleanSession) {
			this.cleanSession = cleanSession;
			return this;
		}

		public MqttMessageBuilders.ConnectBuilder keepAlive(int keepAliveSecs) {
			this.keepAliveSecs = keepAliveSecs;
			return this;
		}

		public MqttMessageBuilders.ConnectBuilder willFlag(boolean willFlag) {
			this.willFlag = willFlag;
			return this;
		}

		public MqttMessageBuilders.ConnectBuilder willQoS(MqttQoS willQos) {
			this.willQos = willQos;
			return this;
		}

		public MqttMessageBuilders.ConnectBuilder willTopic(String willTopic) {
			this.willTopic = willTopic;
			return this;
		}

		@Deprecated
		public MqttMessageBuilders.ConnectBuilder willMessage(String willMessage) {
			this.willMessage(willMessage == null ? null : willMessage.getBytes(CharsetUtil.UTF_8));
			return this;
		}

		public MqttMessageBuilders.ConnectBuilder willMessage(byte[] willMessage) {
			this.willMessage = willMessage;
			return this;
		}

		public MqttMessageBuilders.ConnectBuilder willRetain(boolean willRetain) {
			this.willRetain = willRetain;
			return this;
		}

		public MqttMessageBuilders.ConnectBuilder hasUser(boolean value) {
			this.hasUser = value;
			return this;
		}

		public MqttMessageBuilders.ConnectBuilder hasPassword(boolean value) {
			this.hasPassword = value;
			return this;
		}

		public MqttMessageBuilders.ConnectBuilder username(String username) {
			this.hasUser = username != null;
			this.username = username;
			return this;
		}

		@Deprecated
		public MqttMessageBuilders.ConnectBuilder password(String password) {
			this.password(password == null ? null : password.getBytes(CharsetUtil.UTF_8));
			return this;
		}

		public MqttMessageBuilders.ConnectBuilder password(byte[] password) {
			this.hasPassword = password != null;
			this.password = password;
			return this;
		}

		public MqttConnectMessage build() {
			MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.CONNECT, false, MqttQoS.AT_MOST_ONCE, false, 0);
			MqttConnectVariableHeader mqttConnectVariableHeader = new MqttConnectVariableHeader(
				this.version.protocolName(),
				this.version.protocolLevel(),
				this.hasUser,
				this.hasPassword,
				this.willRetain,
				this.willQos.value(),
				this.willFlag,
				this.cleanSession,
				this.keepAliveSecs
			);
			MqttConnectPayload mqttConnectPayload = new MqttConnectPayload(this.clientId, this.willTopic, this.willMessage, this.username, this.password);
			return new MqttConnectMessage(mqttFixedHeader, mqttConnectVariableHeader, mqttConnectPayload);
		}
	}

	public static final class PublishBuilder {
		private String topic;
		private boolean retained;
		private MqttQoS qos;
		private ByteBuf payload;
		private int messageId;

		PublishBuilder() {
		}

		public MqttMessageBuilders.PublishBuilder topicName(String topic) {
			this.topic = topic;
			return this;
		}

		public MqttMessageBuilders.PublishBuilder retained(boolean retained) {
			this.retained = retained;
			return this;
		}

		public MqttMessageBuilders.PublishBuilder qos(MqttQoS qos) {
			this.qos = qos;
			return this;
		}

		public MqttMessageBuilders.PublishBuilder payload(ByteBuf payload) {
			this.payload = payload;
			return this;
		}

		public MqttMessageBuilders.PublishBuilder messageId(int messageId) {
			this.messageId = messageId;
			return this;
		}

		public MqttPublishMessage build() {
			MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.PUBLISH, false, this.qos, this.retained, 0);
			MqttPublishVariableHeader mqttVariableHeader = new MqttPublishVariableHeader(this.topic, this.messageId);
			return new MqttPublishMessage(mqttFixedHeader, mqttVariableHeader, Unpooled.buffer().writeBytes(this.payload));
		}
	}

	public static final class SubscribeBuilder {
		private List<MqttTopicSubscription> subscriptions;
		private int messageId;

		SubscribeBuilder() {
		}

		public MqttMessageBuilders.SubscribeBuilder addSubscription(MqttQoS qos, String topic) {
			if (this.subscriptions == null) {
				this.subscriptions = new ArrayList(5);
			}

			this.subscriptions.add(new MqttTopicSubscription(topic, qos));
			return this;
		}

		public MqttMessageBuilders.SubscribeBuilder messageId(int messageId) {
			this.messageId = messageId;
			return this;
		}

		public MqttSubscribeMessage build() {
			MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.SUBSCRIBE, false, MqttQoS.AT_LEAST_ONCE, false, 0);
			MqttMessageIdVariableHeader mqttVariableHeader = MqttMessageIdVariableHeader.from(this.messageId);
			MqttSubscribePayload mqttSubscribePayload = new MqttSubscribePayload(this.subscriptions);
			return new MqttSubscribeMessage(mqttFixedHeader, mqttVariableHeader, mqttSubscribePayload);
		}
	}

	public static final class UnsubscribeBuilder {
		private List<String> topicFilters;
		private int messageId;

		UnsubscribeBuilder() {
		}

		public MqttMessageBuilders.UnsubscribeBuilder addTopicFilter(String topic) {
			if (this.topicFilters == null) {
				this.topicFilters = new ArrayList(5);
			}

			this.topicFilters.add(topic);
			return this;
		}

		public MqttMessageBuilders.UnsubscribeBuilder messageId(int messageId) {
			this.messageId = messageId;
			return this;
		}

		public MqttUnsubscribeMessage build() {
			MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.UNSUBSCRIBE, false, MqttQoS.AT_LEAST_ONCE, false, 0);
			MqttMessageIdVariableHeader mqttVariableHeader = MqttMessageIdVariableHeader.from(this.messageId);
			MqttUnsubscribePayload mqttSubscribePayload = new MqttUnsubscribePayload(this.topicFilters);
			return new MqttUnsubscribeMessage(mqttFixedHeader, mqttVariableHeader, mqttSubscribePayload);
		}
	}
}
