package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http2.Http2FrameReader.Configuration;
import io.netty.util.internal.PlatformDependent;

public class DefaultHttp2FrameReader implements Http2FrameReader, Http2FrameSizePolicy, Configuration {
	private final Http2HeadersDecoder headersDecoder;
	private boolean readingHeaders = true;
	private boolean readError;
	private byte frameType;
	private int streamId;
	private Http2Flags flags;
	private int payloadLength;
	private DefaultHttp2FrameReader.HeadersContinuation headersContinuation;
	private int maxFrameSize;

	public DefaultHttp2FrameReader() {
		this(true);
	}

	public DefaultHttp2FrameReader(boolean validateHeaders) {
		this(new DefaultHttp2HeadersDecoder(validateHeaders));
	}

	public DefaultHttp2FrameReader(Http2HeadersDecoder headersDecoder) {
		this.headersDecoder = headersDecoder;
		this.maxFrameSize = 16384;
	}

	@Override
	public Http2HeadersDecoder.Configuration headersConfiguration() {
		return this.headersDecoder.configuration();
	}

	@Override
	public Configuration configuration() {
		return this;
	}

	@Override
	public Http2FrameSizePolicy frameSizePolicy() {
		return this;
	}

	@Override
	public void maxFrameSize(int max) throws Http2Exception {
		if (!Http2CodecUtil.isMaxFrameSizeValid(max)) {
			throw Http2Exception.streamError(this.streamId, Http2Error.FRAME_SIZE_ERROR, "Invalid MAX_FRAME_SIZE specified in sent settings: %d", max);
		} else {
			this.maxFrameSize = max;
		}
	}

	@Override
	public int maxFrameSize() {
		return this.maxFrameSize;
	}

	@Override
	public void close() {
		this.closeHeadersContinuation();
	}

	private void closeHeadersContinuation() {
		if (this.headersContinuation != null) {
			this.headersContinuation.close();
			this.headersContinuation = null;
		}
	}

	@Override
	public void readFrame(ChannelHandlerContext ctx, ByteBuf input, Http2FrameListener listener) throws Http2Exception {
		if (this.readError) {
			input.skipBytes(input.readableBytes());
		} else {
			try {
				do {
					if (this.readingHeaders) {
						this.processHeaderState(input);
						if (this.readingHeaders) {
							return;
						}
					}

					this.processPayloadState(ctx, input, listener);
					if (!this.readingHeaders) {
						return;
					}
				} while (input.isReadable());
			} catch (Http2Exception var5) {
				this.readError = !Http2Exception.isStreamError(var5);
				throw var5;
			} catch (RuntimeException var6) {
				this.readError = true;
				throw var6;
			} catch (Throwable var7) {
				this.readError = true;
				PlatformDependent.throwException(var7);
			}
		}
	}

	private void processHeaderState(ByteBuf in) throws Http2Exception {
		if (in.readableBytes() >= 9) {
			this.payloadLength = in.readUnsignedMedium();
			if (this.payloadLength > this.maxFrameSize) {
				throw Http2Exception.connectionError(Http2Error.FRAME_SIZE_ERROR, "Frame length: %d exceeds maximum: %d", this.payloadLength, this.maxFrameSize);
			} else {
				this.frameType = in.readByte();
				this.flags = new Http2Flags(in.readUnsignedByte());
				this.streamId = Http2CodecUtil.readUnsignedInt(in);
				this.readingHeaders = false;
				switch (this.frameType) {
					case 0:
						this.verifyDataFrame();
						break;
					case 1:
						this.verifyHeadersFrame();
						break;
					case 2:
						this.verifyPriorityFrame();
						break;
					case 3:
						this.verifyRstStreamFrame();
						break;
					case 4:
						this.verifySettingsFrame();
						break;
					case 5:
						this.verifyPushPromiseFrame();
						break;
					case 6:
						this.verifyPingFrame();
						break;
					case 7:
						this.verifyGoAwayFrame();
						break;
					case 8:
						this.verifyWindowUpdateFrame();
						break;
					case 9:
						this.verifyContinuationFrame();
						break;
					default:
						this.verifyUnknownFrame();
				}
			}
		}
	}

	private void processPayloadState(ChannelHandlerContext ctx, ByteBuf in, Http2FrameListener listener) throws Http2Exception {
		if (in.readableBytes() >= this.payloadLength) {
			ByteBuf payload = in.readSlice(this.payloadLength);
			this.readingHeaders = true;
			switch (this.frameType) {
				case 0:
					this.readDataFrame(ctx, payload, listener);
					break;
				case 1:
					this.readHeadersFrame(ctx, payload, listener);
					break;
				case 2:
					this.readPriorityFrame(ctx, payload, listener);
					break;
				case 3:
					this.readRstStreamFrame(ctx, payload, listener);
					break;
				case 4:
					this.readSettingsFrame(ctx, payload, listener);
					break;
				case 5:
					this.readPushPromiseFrame(ctx, payload, listener);
					break;
				case 6:
					this.readPingFrame(ctx, payload.readLong(), listener);
					break;
				case 7:
					readGoAwayFrame(ctx, payload, listener);
					break;
				case 8:
					this.readWindowUpdateFrame(ctx, payload, listener);
					break;
				case 9:
					this.readContinuationFrame(payload, listener);
					break;
				default:
					this.readUnknownFrame(ctx, payload, listener);
			}
		}
	}

	private void verifyDataFrame() throws Http2Exception {
		this.verifyAssociatedWithAStream();
		this.verifyNotProcessingHeaders();
		this.verifyPayloadLength(this.payloadLength);
		if (this.payloadLength < this.flags.getPaddingPresenceFieldLength()) {
			throw Http2Exception.streamError(this.streamId, Http2Error.FRAME_SIZE_ERROR, "Frame length %d too small.", this.payloadLength);
		}
	}

	private void verifyHeadersFrame() throws Http2Exception {
		this.verifyAssociatedWithAStream();
		this.verifyNotProcessingHeaders();
		this.verifyPayloadLength(this.payloadLength);
		int requiredLength = this.flags.getPaddingPresenceFieldLength() + this.flags.getNumPriorityBytes();
		if (this.payloadLength < requiredLength) {
			throw Http2Exception.streamError(this.streamId, Http2Error.FRAME_SIZE_ERROR, "Frame length too small." + this.payloadLength);
		}
	}

	private void verifyPriorityFrame() throws Http2Exception {
		this.verifyAssociatedWithAStream();
		this.verifyNotProcessingHeaders();
		if (this.payloadLength != 5) {
			throw Http2Exception.streamError(this.streamId, Http2Error.FRAME_SIZE_ERROR, "Invalid frame length %d.", this.payloadLength);
		}
	}

	private void verifyRstStreamFrame() throws Http2Exception {
		this.verifyAssociatedWithAStream();
		this.verifyNotProcessingHeaders();
		if (this.payloadLength != 4) {
			throw Http2Exception.connectionError(Http2Error.FRAME_SIZE_ERROR, "Invalid frame length %d.", this.payloadLength);
		}
	}

	private void verifySettingsFrame() throws Http2Exception {
		this.verifyNotProcessingHeaders();
		this.verifyPayloadLength(this.payloadLength);
		if (this.streamId != 0) {
			throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "A stream ID must be zero.");
		} else if (this.flags.ack() && this.payloadLength > 0) {
			throw Http2Exception.connectionError(Http2Error.FRAME_SIZE_ERROR, "Ack settings frame must have an empty payload.");
		} else if (this.payloadLength % 6 > 0) {
			throw Http2Exception.connectionError(Http2Error.FRAME_SIZE_ERROR, "Frame length %d invalid.", this.payloadLength);
		}
	}

	private void verifyPushPromiseFrame() throws Http2Exception {
		this.verifyNotProcessingHeaders();
		this.verifyPayloadLength(this.payloadLength);
		int minLength = this.flags.getPaddingPresenceFieldLength() + 4;
		if (this.payloadLength < minLength) {
			throw Http2Exception.streamError(this.streamId, Http2Error.FRAME_SIZE_ERROR, "Frame length %d too small.", this.payloadLength);
		}
	}

	private void verifyPingFrame() throws Http2Exception {
		this.verifyNotProcessingHeaders();
		if (this.streamId != 0) {
			throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "A stream ID must be zero.");
		} else if (this.payloadLength != 8) {
			throw Http2Exception.connectionError(Http2Error.FRAME_SIZE_ERROR, "Frame length %d incorrect size for ping.", this.payloadLength);
		}
	}

	private void verifyGoAwayFrame() throws Http2Exception {
		this.verifyNotProcessingHeaders();
		this.verifyPayloadLength(this.payloadLength);
		if (this.streamId != 0) {
			throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "A stream ID must be zero.");
		} else if (this.payloadLength < 8) {
			throw Http2Exception.connectionError(Http2Error.FRAME_SIZE_ERROR, "Frame length %d too small.", this.payloadLength);
		}
	}

	private void verifyWindowUpdateFrame() throws Http2Exception {
		this.verifyNotProcessingHeaders();
		verifyStreamOrConnectionId(this.streamId, "Stream ID");
		if (this.payloadLength != 4) {
			throw Http2Exception.connectionError(Http2Error.FRAME_SIZE_ERROR, "Invalid frame length %d.", this.payloadLength);
		}
	}

	private void verifyContinuationFrame() throws Http2Exception {
		this.verifyAssociatedWithAStream();
		this.verifyPayloadLength(this.payloadLength);
		if (this.headersContinuation == null) {
			throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Received %s frame but not currently processing headers.", this.frameType);
		} else if (this.streamId != this.headersContinuation.getStreamId()) {
			throw Http2Exception.connectionError(
				Http2Error.PROTOCOL_ERROR,
				"Continuation stream ID does not match pending headers. Expected %d, but received %d.",
				this.headersContinuation.getStreamId(),
				this.streamId
			);
		} else if (this.payloadLength < this.flags.getPaddingPresenceFieldLength()) {
			throw Http2Exception.streamError(this.streamId, Http2Error.FRAME_SIZE_ERROR, "Frame length %d too small for padding.", this.payloadLength);
		}
	}

	private void verifyUnknownFrame() throws Http2Exception {
		this.verifyNotProcessingHeaders();
	}

	private void readDataFrame(ChannelHandlerContext ctx, ByteBuf payload, Http2FrameListener listener) throws Http2Exception {
		int padding = this.readPadding(payload);
		this.verifyPadding(padding);
		int dataLength = lengthWithoutTrailingPadding(payload.readableBytes(), padding);
		ByteBuf data = payload.readSlice(dataLength);
		listener.onDataRead(ctx, this.streamId, data, padding, this.flags.endOfStream());
		payload.skipBytes(payload.readableBytes());
	}

	private void readHeadersFrame(ChannelHandlerContext ctx, ByteBuf payload, Http2FrameListener listener) throws Http2Exception {
		final int headersStreamId = this.streamId;
		final Http2Flags headersFlags = this.flags;
		final int padding = this.readPadding(payload);
		this.verifyPadding(padding);
		if (this.flags.priorityPresent()) {
			long word1 = payload.readUnsignedInt();
			final boolean exclusive = (word1 & 2147483648L) != 0L;
			final int streamDependency = (int)(word1 & 2147483647L);
			if (streamDependency == this.streamId) {
				throw Http2Exception.streamError(this.streamId, Http2Error.PROTOCOL_ERROR, "A stream cannot depend on itself.");
			} else {
				final short weight = (short)(payload.readUnsignedByte() + 1);
				ByteBuf fragment = payload.readSlice(lengthWithoutTrailingPadding(payload.readableBytes(), padding));
				this.headersContinuation = new DefaultHttp2FrameReader.HeadersContinuation() {
					@Override
					public int getStreamId() {
						return headersStreamId;
					}

					@Override
					public void processFragment(boolean endOfHeaders, ByteBuf fragment, Http2FrameListener listener) throws Http2Exception {
						DefaultHttp2FrameReader.HeadersBlockBuilder hdrBlockBuilder = this.headersBlockBuilder();
						hdrBlockBuilder.addFragment(fragment, ctx.alloc(), endOfHeaders);
						if (endOfHeaders) {
							listener.onHeadersRead(ctx, headersStreamId, hdrBlockBuilder.headers(), streamDependency, weight, exclusive, padding, headersFlags.endOfStream());
						}
					}
				};
				this.headersContinuation.processFragment(this.flags.endOfHeaders(), fragment, listener);
				this.resetHeadersContinuationIfEnd(this.flags.endOfHeaders());
			}
		} else {
			this.headersContinuation = new DefaultHttp2FrameReader.HeadersContinuation() {
				@Override
				public int getStreamId() {
					return headersStreamId;
				}

				@Override
				public void processFragment(boolean endOfHeaders, ByteBuf fragment, Http2FrameListener listener) throws Http2Exception {
					DefaultHttp2FrameReader.HeadersBlockBuilder hdrBlockBuilder = this.headersBlockBuilder();
					hdrBlockBuilder.addFragment(fragment, ctx.alloc(), endOfHeaders);
					if (endOfHeaders) {
						listener.onHeadersRead(ctx, headersStreamId, hdrBlockBuilder.headers(), padding, headersFlags.endOfStream());
					}
				}
			};
			ByteBuf fragment = payload.readSlice(lengthWithoutTrailingPadding(payload.readableBytes(), padding));
			this.headersContinuation.processFragment(this.flags.endOfHeaders(), fragment, listener);
			this.resetHeadersContinuationIfEnd(this.flags.endOfHeaders());
		}
	}

	private void resetHeadersContinuationIfEnd(boolean endOfHeaders) {
		if (endOfHeaders) {
			this.closeHeadersContinuation();
		}
	}

	private void readPriorityFrame(ChannelHandlerContext ctx, ByteBuf payload, Http2FrameListener listener) throws Http2Exception {
		long word1 = payload.readUnsignedInt();
		boolean exclusive = (word1 & 2147483648L) != 0L;
		int streamDependency = (int)(word1 & 2147483647L);
		if (streamDependency == this.streamId) {
			throw Http2Exception.streamError(this.streamId, Http2Error.PROTOCOL_ERROR, "A stream cannot depend on itself.");
		} else {
			short weight = (short)(payload.readUnsignedByte() + 1);
			listener.onPriorityRead(ctx, this.streamId, streamDependency, weight, exclusive);
		}
	}

	private void readRstStreamFrame(ChannelHandlerContext ctx, ByteBuf payload, Http2FrameListener listener) throws Http2Exception {
		long errorCode = payload.readUnsignedInt();
		listener.onRstStreamRead(ctx, this.streamId, errorCode);
	}

	private void readSettingsFrame(ChannelHandlerContext ctx, ByteBuf payload, Http2FrameListener listener) throws Http2Exception {
		if (this.flags.ack()) {
			listener.onSettingsAckRead(ctx);
		} else {
			int numSettings = this.payloadLength / 6;
			Http2Settings settings = new Http2Settings();

			for (int index = 0; index < numSettings; index++) {
				char id = (char)payload.readUnsignedShort();
				long value = payload.readUnsignedInt();

				try {
					settings.put(id, value);
				} catch (IllegalArgumentException var11) {
					switch (id) {
						case '\u0004':
							throw Http2Exception.connectionError(Http2Error.FLOW_CONTROL_ERROR, var11, var11.getMessage());
						case '\u0005':
							throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, var11, var11.getMessage());
						default:
							throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, var11, var11.getMessage());
					}
				}
			}

			listener.onSettingsRead(ctx, settings);
		}
	}

	private void readPushPromiseFrame(ChannelHandlerContext ctx, ByteBuf payload, Http2FrameListener listener) throws Http2Exception {
		final int pushPromiseStreamId = this.streamId;
		final int padding = this.readPadding(payload);
		this.verifyPadding(padding);
		final int promisedStreamId = Http2CodecUtil.readUnsignedInt(payload);
		this.headersContinuation = new DefaultHttp2FrameReader.HeadersContinuation() {
			@Override
			public int getStreamId() {
				return pushPromiseStreamId;
			}

			@Override
			public void processFragment(boolean endOfHeaders, ByteBuf fragment, Http2FrameListener listener) throws Http2Exception {
				this.headersBlockBuilder().addFragment(fragment, ctx.alloc(), endOfHeaders);
				if (endOfHeaders) {
					listener.onPushPromiseRead(ctx, pushPromiseStreamId, promisedStreamId, this.headersBlockBuilder().headers(), padding);
				}
			}
		};
		ByteBuf fragment = payload.readSlice(lengthWithoutTrailingPadding(payload.readableBytes(), padding));
		this.headersContinuation.processFragment(this.flags.endOfHeaders(), fragment, listener);
		this.resetHeadersContinuationIfEnd(this.flags.endOfHeaders());
	}

	private void readPingFrame(ChannelHandlerContext ctx, long data, Http2FrameListener listener) throws Http2Exception {
		if (this.flags.ack()) {
			listener.onPingAckRead(ctx, data);
		} else {
			listener.onPingRead(ctx, data);
		}
	}

	private static void readGoAwayFrame(ChannelHandlerContext ctx, ByteBuf payload, Http2FrameListener listener) throws Http2Exception {
		int lastStreamId = Http2CodecUtil.readUnsignedInt(payload);
		long errorCode = payload.readUnsignedInt();
		ByteBuf debugData = payload.readSlice(payload.readableBytes());
		listener.onGoAwayRead(ctx, lastStreamId, errorCode, debugData);
	}

	private void readWindowUpdateFrame(ChannelHandlerContext ctx, ByteBuf payload, Http2FrameListener listener) throws Http2Exception {
		int windowSizeIncrement = Http2CodecUtil.readUnsignedInt(payload);
		if (windowSizeIncrement == 0) {
			throw Http2Exception.streamError(this.streamId, Http2Error.PROTOCOL_ERROR, "Received WINDOW_UPDATE with delta 0 for stream: %d", this.streamId);
		} else {
			listener.onWindowUpdateRead(ctx, this.streamId, windowSizeIncrement);
		}
	}

	private void readContinuationFrame(ByteBuf payload, Http2FrameListener listener) throws Http2Exception {
		ByteBuf continuationFragment = payload.readSlice(payload.readableBytes());
		this.headersContinuation.processFragment(this.flags.endOfHeaders(), continuationFragment, listener);
		this.resetHeadersContinuationIfEnd(this.flags.endOfHeaders());
	}

	private void readUnknownFrame(ChannelHandlerContext ctx, ByteBuf payload, Http2FrameListener listener) throws Http2Exception {
		payload = payload.readSlice(payload.readableBytes());
		listener.onUnknownFrame(ctx, this.frameType, this.streamId, this.flags, payload);
	}

	private int readPadding(ByteBuf payload) {
		return !this.flags.paddingPresent() ? 0 : payload.readUnsignedByte() + 1;
	}

	private void verifyPadding(int padding) throws Http2Exception {
		int len = lengthWithoutTrailingPadding(this.payloadLength, padding);
		if (len < 0) {
			throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Frame payload too small for padding.");
		}
	}

	private static int lengthWithoutTrailingPadding(int readableBytes, int padding) {
		return padding == 0 ? readableBytes : readableBytes - (padding - 1);
	}

	private void verifyNotProcessingHeaders() throws Http2Exception {
		if (this.headersContinuation != null) {
			throw Http2Exception.connectionError(
				Http2Error.PROTOCOL_ERROR, "Received frame of type %s while processing headers on stream %d.", this.frameType, this.headersContinuation.getStreamId()
			);
		}
	}

	private void verifyPayloadLength(int payloadLength) throws Http2Exception {
		if (payloadLength > this.maxFrameSize) {
			throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Total payload length %d exceeds max frame length.", payloadLength);
		}
	}

	private void verifyAssociatedWithAStream() throws Http2Exception {
		if (this.streamId == 0) {
			throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Frame of type %s must be associated with a stream.", this.frameType);
		}
	}

	private static void verifyStreamOrConnectionId(int streamId, String argumentName) throws Http2Exception {
		if (streamId < 0) {
			throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "%s must be >= 0", argumentName);
		}
	}

	protected class HeadersBlockBuilder {
		private ByteBuf headerBlock;

		private void headerSizeExceeded() throws Http2Exception {
			this.close();
			Http2CodecUtil.headerListSizeExceeded(DefaultHttp2FrameReader.this.headersDecoder.configuration().maxHeaderListSizeGoAway());
		}

		final void addFragment(ByteBuf fragment, ByteBufAllocator alloc, boolean endOfHeaders) throws Http2Exception {
			if (this.headerBlock == null) {
				if ((long)fragment.readableBytes() > DefaultHttp2FrameReader.this.headersDecoder.configuration().maxHeaderListSizeGoAway()) {
					this.headerSizeExceeded();
				}

				if (endOfHeaders) {
					this.headerBlock = fragment.retain();
				} else {
					this.headerBlock = alloc.buffer(fragment.readableBytes());
					this.headerBlock.writeBytes(fragment);
				}
			} else {
				if (DefaultHttp2FrameReader.this.headersDecoder.configuration().maxHeaderListSizeGoAway() - (long)fragment.readableBytes()
					< (long)this.headerBlock.readableBytes()) {
					this.headerSizeExceeded();
				}

				if (this.headerBlock.isWritable(fragment.readableBytes())) {
					this.headerBlock.writeBytes(fragment);
				} else {
					ByteBuf buf = alloc.buffer(this.headerBlock.readableBytes() + fragment.readableBytes());
					buf.writeBytes(this.headerBlock);
					buf.writeBytes(fragment);
					this.headerBlock.release();
					this.headerBlock = buf;
				}
			}
		}

		Http2Headers headers() throws Http2Exception {
			Http2Headers var1;
			try {
				var1 = DefaultHttp2FrameReader.this.headersDecoder.decodeHeaders(DefaultHttp2FrameReader.this.streamId, this.headerBlock);
			} finally {
				this.close();
			}

			return var1;
		}

		void close() {
			if (this.headerBlock != null) {
				this.headerBlock.release();
				this.headerBlock = null;
			}

			DefaultHttp2FrameReader.this.headersContinuation = null;
		}
	}

	private abstract class HeadersContinuation {
		private final DefaultHttp2FrameReader.HeadersBlockBuilder builder = DefaultHttp2FrameReader.this.new HeadersBlockBuilder();

		private HeadersContinuation() {
		}

		abstract int getStreamId();

		abstract void processFragment(boolean boolean1, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception;

		final DefaultHttp2FrameReader.HeadersBlockBuilder headersBlockBuilder() {
			return this.builder;
		}

		final void close() {
			this.builder.close();
		}
	}
}
