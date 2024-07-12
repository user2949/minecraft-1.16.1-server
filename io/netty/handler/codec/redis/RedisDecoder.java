package io.netty.handler.codec.redis;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;
import java.util.List;

public final class RedisDecoder extends ByteToMessageDecoder {
	private final RedisDecoder.ToPositiveLongProcessor toPositiveLongProcessor = new RedisDecoder.ToPositiveLongProcessor();
	private final boolean decodeInlineCommands;
	private final int maxInlineMessageLength;
	private final RedisMessagePool messagePool;
	private RedisDecoder.State state = RedisDecoder.State.DECODE_TYPE;
	private RedisMessageType type;
	private int remainingBulkLength;

	public RedisDecoder() {
		this(false);
	}

	public RedisDecoder(boolean decodeInlineCommands) {
		this(65536, FixedRedisMessagePool.INSTANCE, decodeInlineCommands);
	}

	public RedisDecoder(int maxInlineMessageLength, RedisMessagePool messagePool) {
		this(maxInlineMessageLength, messagePool, false);
	}

	public RedisDecoder(int maxInlineMessageLength, RedisMessagePool messagePool, boolean decodeInlineCommands) {
		if (maxInlineMessageLength > 0 && maxInlineMessageLength <= 536870912) {
			this.maxInlineMessageLength = maxInlineMessageLength;
			this.messagePool = messagePool;
			this.decodeInlineCommands = decodeInlineCommands;
		} else {
			throw new RedisCodecException("maxInlineMessageLength: " + maxInlineMessageLength + " (expected: <= " + 536870912 + ")");
		}
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		try {
			while (true) {
				switch (this.state) {
					case DECODE_TYPE:
						if (this.decodeType(in)) {
							break;
						}

						return;
					case DECODE_INLINE:
						if (this.decodeInline(in, out)) {
							break;
						}

						return;
					case DECODE_LENGTH:
						if (this.decodeLength(in, out)) {
							break;
						}

						return;
					case DECODE_BULK_STRING_EOL:
						if (this.decodeBulkStringEndOfLine(in, out)) {
							break;
						}

						return;
					case DECODE_BULK_STRING_CONTENT:
						if (this.decodeBulkStringContent(in, out)) {
							break;
						}

						return;
					default:
						throw new RedisCodecException("Unknown state: " + this.state);
				}
			}
		} catch (RedisCodecException var5) {
			this.resetDecoder();
			throw var5;
		} catch (Exception var6) {
			this.resetDecoder();
			throw new RedisCodecException(var6);
		}
	}

	private void resetDecoder() {
		this.state = RedisDecoder.State.DECODE_TYPE;
		this.remainingBulkLength = 0;
	}

	private boolean decodeType(ByteBuf in) throws Exception {
		if (!in.isReadable()) {
			return false;
		} else {
			this.type = RedisMessageType.readFrom(in, this.decodeInlineCommands);
			this.state = this.type.isInline() ? RedisDecoder.State.DECODE_INLINE : RedisDecoder.State.DECODE_LENGTH;
			return true;
		}
	}

	private boolean decodeInline(ByteBuf in, List<Object> out) throws Exception {
		ByteBuf lineBytes = readLine(in);
		if (lineBytes == null) {
			if (in.readableBytes() > this.maxInlineMessageLength) {
				throw new RedisCodecException("length: " + in.readableBytes() + " (expected: <= " + this.maxInlineMessageLength + ")");
			} else {
				return false;
			}
		} else {
			out.add(this.newInlineRedisMessage(this.type, lineBytes));
			this.resetDecoder();
			return true;
		}
	}

	private boolean decodeLength(ByteBuf in, List<Object> out) throws Exception {
		ByteBuf lineByteBuf = readLine(in);
		if (lineByteBuf == null) {
			return false;
		} else {
			long length = this.parseRedisNumber(lineByteBuf);
			if (length < -1L) {
				throw new RedisCodecException("length: " + length + " (expected: >= " + -1 + ")");
			} else {
				switch (this.type) {
					case ARRAY_HEADER:
						out.add(new ArrayHeaderRedisMessage(length));
						this.resetDecoder();
						return true;
					case BULK_STRING:
						if (length > 536870912L) {
							throw new RedisCodecException("length: " + length + " (expected: <= " + 536870912 + ")");
						}

						this.remainingBulkLength = (int)length;
						return this.decodeBulkString(in, out);
					default:
						throw new RedisCodecException("bad type: " + this.type);
				}
			}
		}
	}

	private boolean decodeBulkString(ByteBuf in, List<Object> out) throws Exception {
		switch (this.remainingBulkLength) {
			case -1:
				out.add(FullBulkStringRedisMessage.NULL_INSTANCE);
				this.resetDecoder();
				return true;
			case 0:
				this.state = RedisDecoder.State.DECODE_BULK_STRING_EOL;
				return this.decodeBulkStringEndOfLine(in, out);
			default:
				out.add(new BulkStringHeaderRedisMessage(this.remainingBulkLength));
				this.state = RedisDecoder.State.DECODE_BULK_STRING_CONTENT;
				return this.decodeBulkStringContent(in, out);
		}
	}

	private boolean decodeBulkStringEndOfLine(ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes() < 2) {
			return false;
		} else {
			readEndOfLine(in);
			out.add(FullBulkStringRedisMessage.EMPTY_INSTANCE);
			this.resetDecoder();
			return true;
		}
	}

	private boolean decodeBulkStringContent(ByteBuf in, List<Object> out) throws Exception {
		int readableBytes = in.readableBytes();
		if (readableBytes != 0 && (this.remainingBulkLength != 0 || readableBytes >= 2)) {
			if (readableBytes >= this.remainingBulkLength + 2) {
				ByteBuf content = in.readSlice(this.remainingBulkLength);
				readEndOfLine(in);
				out.add(new DefaultLastBulkStringRedisContent(content.retain()));
				this.resetDecoder();
				return true;
			} else {
				int toRead = Math.min(this.remainingBulkLength, readableBytes);
				this.remainingBulkLength -= toRead;
				out.add(new DefaultBulkStringRedisContent(in.readSlice(toRead).retain()));
				return true;
			}
		} else {
			return false;
		}
	}

	private static void readEndOfLine(ByteBuf in) {
		short delim = in.readShort();
		if (RedisConstants.EOL_SHORT != delim) {
			byte[] bytes = RedisCodecUtil.shortToBytes(delim);
			throw new RedisCodecException("delimiter: [" + bytes[0] + "," + bytes[1] + "] (expected: \\r\\n)");
		}
	}

	private RedisMessage newInlineRedisMessage(RedisMessageType messageType, ByteBuf content) {
		switch (messageType) {
			case INLINE_COMMAND:
				return new InlineCommandRedisMessage(content.toString(CharsetUtil.UTF_8));
			case SIMPLE_STRING: {
				SimpleStringRedisMessage cached = this.messagePool.getSimpleString(content);
				return cached != null ? cached : new SimpleStringRedisMessage(content.toString(CharsetUtil.UTF_8));
			}
			case ERROR: {
				ErrorRedisMessage cached = this.messagePool.getError(content);
				return cached != null ? cached : new ErrorRedisMessage(content.toString(CharsetUtil.UTF_8));
			}
			case INTEGER: {
				IntegerRedisMessage cached = this.messagePool.getInteger(content);
				return cached != null ? cached : new IntegerRedisMessage(this.parseRedisNumber(content));
			}
			default:
				throw new RedisCodecException("bad type: " + messageType);
		}
	}

	private static ByteBuf readLine(ByteBuf in) {
		if (!in.isReadable(2)) {
			return null;
		} else {
			int lfIndex = in.forEachByte(ByteProcessor.FIND_LF);
			if (lfIndex < 0) {
				return null;
			} else {
				ByteBuf data = in.readSlice(lfIndex - in.readerIndex() - 1);
				readEndOfLine(in);
				return data;
			}
		}
	}

	private long parseRedisNumber(ByteBuf byteBuf) {
		int readableBytes = byteBuf.readableBytes();
		boolean negative = readableBytes > 0 && byteBuf.getByte(byteBuf.readerIndex()) == 45;
		int extraOneByteForNegative = negative ? 1 : 0;
		if (readableBytes <= extraOneByteForNegative) {
			throw new RedisCodecException("no number to parse: " + byteBuf.toString(CharsetUtil.US_ASCII));
		} else if (readableBytes > 19 + extraOneByteForNegative) {
			throw new RedisCodecException("too many characters to be a valid RESP Integer: " + byteBuf.toString(CharsetUtil.US_ASCII));
		} else {
			return negative ? -this.parsePositiveNumber(byteBuf.skipBytes(extraOneByteForNegative)) : this.parsePositiveNumber(byteBuf);
		}
	}

	private long parsePositiveNumber(ByteBuf byteBuf) {
		this.toPositiveLongProcessor.reset();
		byteBuf.forEachByte(this.toPositiveLongProcessor);
		return this.toPositiveLongProcessor.content();
	}

	private static enum State {
		DECODE_TYPE,
		DECODE_INLINE,
		DECODE_LENGTH,
		DECODE_BULK_STRING_EOL,
		DECODE_BULK_STRING_CONTENT;
	}

	private static final class ToPositiveLongProcessor implements ByteProcessor {
		private long result;

		private ToPositiveLongProcessor() {
		}

		@Override
		public boolean process(byte value) throws Exception {
			if (value >= 48 && value <= 57) {
				this.result = this.result * 10L + (long)(value - 48);
				return true;
			} else {
				throw new RedisCodecException("bad byte in number: " + value);
			}
		}

		public long content() {
			return this.result;
		}

		public void reset() {
			this.result = 0L;
		}
	}
}
