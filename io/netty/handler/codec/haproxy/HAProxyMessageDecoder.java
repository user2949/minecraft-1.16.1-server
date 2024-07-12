package io.netty.handler.codec.haproxy;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ProtocolDetectionResult;
import io.netty.util.CharsetUtil;
import java.util.List;

public class HAProxyMessageDecoder extends ByteToMessageDecoder {
	private static final int V1_MAX_LENGTH = 108;
	private static final int V2_MAX_LENGTH = 65551;
	private static final int V2_MIN_LENGTH = 232;
	private static final int V2_MAX_TLV = 65319;
	private static final int DELIMITER_LENGTH = 2;
	private static final byte[] BINARY_PREFIX = new byte[]{13, 10, 13, 10, 0, 13, 10, 81, 85, 73, 84, 10};
	private static final byte[] TEXT_PREFIX = new byte[]{80, 82, 79, 88, 89};
	private static final int BINARY_PREFIX_LENGTH = BINARY_PREFIX.length;
	private static final ProtocolDetectionResult<HAProxyProtocolVersion> DETECTION_RESULT_V1 = ProtocolDetectionResult.detected(HAProxyProtocolVersion.V1);
	private static final ProtocolDetectionResult<HAProxyProtocolVersion> DETECTION_RESULT_V2 = ProtocolDetectionResult.detected(HAProxyProtocolVersion.V2);
	private boolean discarding;
	private int discardedBytes;
	private boolean finished;
	private int version = -1;
	private final int v2MaxHeaderSize;

	public HAProxyMessageDecoder() {
		this.v2MaxHeaderSize = 65551;
	}

	public HAProxyMessageDecoder(int maxTlvSize) {
		if (maxTlvSize < 1) {
			this.v2MaxHeaderSize = 232;
		} else if (maxTlvSize > 65319) {
			this.v2MaxHeaderSize = 65551;
		} else {
			int calcMax = maxTlvSize + 232;
			if (calcMax > 65551) {
				this.v2MaxHeaderSize = 65551;
			} else {
				this.v2MaxHeaderSize = calcMax;
			}
		}
	}

	private static int findVersion(ByteBuf buffer) {
		int n = buffer.readableBytes();
		if (n < 13) {
			return -1;
		} else {
			int idx = buffer.readerIndex();
			return match(BINARY_PREFIX, buffer, idx) ? buffer.getByte(idx + BINARY_PREFIX_LENGTH) : 1;
		}
	}

	private static int findEndOfHeader(ByteBuf buffer) {
		int n = buffer.readableBytes();
		if (n < 16) {
			return -1;
		} else {
			int offset = buffer.readerIndex() + 14;
			int totalHeaderBytes = 16 + buffer.getUnsignedShort(offset);
			return n >= totalHeaderBytes ? totalHeaderBytes : -1;
		}
	}

	private static int findEndOfLine(ByteBuf buffer) {
		int n = buffer.writerIndex();

		for (int i = buffer.readerIndex(); i < n; i++) {
			byte b = buffer.getByte(i);
			if (b == 13 && i < n - 1 && buffer.getByte(i + 1) == 10) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public boolean isSingleDecode() {
		return true;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		super.channelRead(ctx, msg);
		if (this.finished) {
			ctx.pipeline().remove(this);
		}
	}

	@Override
	protected final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (this.version != -1 || (this.version = findVersion(in)) != -1) {
			ByteBuf decoded;
			if (this.version == 1) {
				decoded = this.decodeLine(ctx, in);
			} else {
				decoded = this.decodeStruct(ctx, in);
			}

			if (decoded != null) {
				this.finished = true;

				try {
					if (this.version == 1) {
						out.add(HAProxyMessage.decodeHeader(decoded.toString(CharsetUtil.US_ASCII)));
					} else {
						out.add(HAProxyMessage.decodeHeader(decoded));
					}
				} catch (HAProxyProtocolException var6) {
					this.fail(ctx, null, var6);
				}
			}
		}
	}

	private ByteBuf decodeStruct(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
		int eoh = findEndOfHeader(buffer);
		if (!this.discarding) {
			if (eoh >= 0) {
				int length = eoh - buffer.readerIndex();
				if (length > this.v2MaxHeaderSize) {
					buffer.readerIndex(eoh);
					this.failOverLimit(ctx, length);
					return null;
				} else {
					return buffer.readSlice(length);
				}
			} else {
				int length = buffer.readableBytes();
				if (length > this.v2MaxHeaderSize) {
					this.discardedBytes = length;
					buffer.skipBytes(length);
					this.discarding = true;
					this.failOverLimit(ctx, "over " + this.discardedBytes);
				}

				return null;
			}
		} else {
			if (eoh >= 0) {
				buffer.readerIndex(eoh);
				this.discardedBytes = 0;
				this.discarding = false;
			} else {
				this.discardedBytes = buffer.readableBytes();
				buffer.skipBytes(this.discardedBytes);
			}

			return null;
		}
	}

	private ByteBuf decodeLine(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
		int eol = findEndOfLine(buffer);
		if (!this.discarding) {
			if (eol >= 0) {
				int length = eol - buffer.readerIndex();
				if (length > 108) {
					buffer.readerIndex(eol + 2);
					this.failOverLimit(ctx, length);
					return null;
				} else {
					ByteBuf frame = buffer.readSlice(length);
					buffer.skipBytes(2);
					return frame;
				}
			} else {
				int length = buffer.readableBytes();
				if (length > 108) {
					this.discardedBytes = length;
					buffer.skipBytes(length);
					this.discarding = true;
					this.failOverLimit(ctx, "over " + this.discardedBytes);
				}

				return null;
			}
		} else {
			if (eol >= 0) {
				int delimLength = buffer.getByte(eol) == 13 ? 2 : 1;
				buffer.readerIndex(eol + delimLength);
				this.discardedBytes = 0;
				this.discarding = false;
			} else {
				this.discardedBytes = buffer.readableBytes();
				buffer.skipBytes(this.discardedBytes);
			}

			return null;
		}
	}

	private void failOverLimit(ChannelHandlerContext ctx, int length) {
		this.failOverLimit(ctx, String.valueOf(length));
	}

	private void failOverLimit(ChannelHandlerContext ctx, String length) {
		int maxLength = this.version == 1 ? 108 : this.v2MaxHeaderSize;
		this.fail(ctx, "header length (" + length + ") exceeds the allowed maximum (" + maxLength + ')', null);
	}

	private void fail(ChannelHandlerContext ctx, String errMsg, Exception e) {
		this.finished = true;
		ctx.close();
		HAProxyProtocolException ppex;
		if (errMsg != null && e != null) {
			ppex = new HAProxyProtocolException(errMsg, e);
		} else if (errMsg != null) {
			ppex = new HAProxyProtocolException(errMsg);
		} else if (e != null) {
			ppex = new HAProxyProtocolException(e);
		} else {
			ppex = new HAProxyProtocolException();
		}

		throw ppex;
	}

	public static ProtocolDetectionResult<HAProxyProtocolVersion> detectProtocol(ByteBuf buffer) {
		if (buffer.readableBytes() < 12) {
			return ProtocolDetectionResult.needsMoreData();
		} else {
			int idx = buffer.readerIndex();
			if (match(BINARY_PREFIX, buffer, idx)) {
				return DETECTION_RESULT_V2;
			} else {
				return match(TEXT_PREFIX, buffer, idx) ? DETECTION_RESULT_V1 : ProtocolDetectionResult.invalid();
			}
		}
	}

	private static boolean match(byte[] prefix, ByteBuf buffer, int idx) {
		for (int i = 0; i < prefix.length; i++) {
			byte b = buffer.getByte(idx + i);
			if (b != prefix[i]) {
				return false;
			}
		}

		return true;
	}
}
