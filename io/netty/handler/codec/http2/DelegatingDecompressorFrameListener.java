package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http2.Http2Connection.PropertyKey;
import io.netty.util.internal.ObjectUtil;

public class DelegatingDecompressorFrameListener extends Http2FrameListenerDecorator {
	private final Http2Connection connection;
	private final boolean strict;
	private boolean flowControllerInitialized;
	private final PropertyKey propertyKey;

	public DelegatingDecompressorFrameListener(Http2Connection connection, Http2FrameListener listener) {
		this(connection, listener, true);
	}

	public DelegatingDecompressorFrameListener(Http2Connection connection, Http2FrameListener listener, boolean strict) {
		super(listener);
		this.connection = connection;
		this.strict = strict;
		this.propertyKey = connection.newKey();
		connection.addListener(new Http2ConnectionAdapter() {
			@Override
			public void onStreamRemoved(Http2Stream stream) {
				DelegatingDecompressorFrameListener.Http2Decompressor decompressor = DelegatingDecompressorFrameListener.this.decompressor(stream);
				if (decompressor != null) {
					DelegatingDecompressorFrameListener.cleanup(decompressor);
				}
			}
		});
	}

	@Override
	public int onDataRead(ChannelHandlerContext ctx, int streamId, ByteBuf data, int padding, boolean endOfStream) throws Http2Exception {
		Http2Stream stream = this.connection.stream(streamId);
		DelegatingDecompressorFrameListener.Http2Decompressor decompressor = this.decompressor(stream);
		if (decompressor == null) {
			return this.listener.onDataRead(ctx, streamId, data, padding, endOfStream);
		} else {
			EmbeddedChannel channel = decompressor.decompressor();
			int compressedBytes = data.readableBytes() + padding;
			decompressor.incrementCompressedBytes(compressedBytes);

			try {
				channel.writeInbound(data.retain());
				ByteBuf buf = nextReadableBuf(channel);
				if (buf == null && endOfStream && channel.finish()) {
					buf = nextReadableBuf(channel);
				}

				if (buf == null) {
					if (endOfStream) {
						this.listener.onDataRead(ctx, streamId, Unpooled.EMPTY_BUFFER, padding, true);
					}

					decompressor.incrementDecompressedBytes(compressedBytes);
					return compressedBytes;
				} else {
					try {
						Http2LocalFlowController flowController = this.connection.local().flowController();
						decompressor.incrementDecompressedBytes(padding);

						while (true) {
							ByteBuf nextBuf = nextReadableBuf(channel);
							boolean decompressedEndOfStream = nextBuf == null && endOfStream;
							if (decompressedEndOfStream && channel.finish()) {
								nextBuf = nextReadableBuf(channel);
								decompressedEndOfStream = nextBuf == null;
							}

							decompressor.incrementDecompressedBytes(buf.readableBytes());
							flowController.consumeBytes(stream, this.listener.onDataRead(ctx, streamId, buf, padding, decompressedEndOfStream));
							if (nextBuf == null) {
								return 0;
							}

							padding = 0;
							buf.release();
							buf = nextBuf;
						}
					} finally {
						buf.release();
					}
				}
			} catch (Http2Exception var19) {
				throw var19;
			} catch (Throwable var20) {
				throw Http2Exception.streamError(
					stream.id(), Http2Error.INTERNAL_ERROR, var20, "Decompressor error detected while delegating data read on streamId %d", stream.id()
				);
			}
		}
	}

	@Override
	public void onHeadersRead(ChannelHandlerContext ctx, int streamId, Http2Headers headers, int padding, boolean endStream) throws Http2Exception {
		this.initDecompressor(ctx, streamId, headers, endStream);
		this.listener.onHeadersRead(ctx, streamId, headers, padding, endStream);
	}

	@Override
	public void onHeadersRead(
		ChannelHandlerContext ctx, int streamId, Http2Headers headers, int streamDependency, short weight, boolean exclusive, int padding, boolean endStream
	) throws Http2Exception {
		this.initDecompressor(ctx, streamId, headers, endStream);
		this.listener.onHeadersRead(ctx, streamId, headers, streamDependency, weight, exclusive, padding, endStream);
	}

	protected EmbeddedChannel newContentDecompressor(ChannelHandlerContext ctx, CharSequence contentEncoding) throws Http2Exception {
		if (HttpHeaderValues.GZIP.contentEqualsIgnoreCase(contentEncoding) || HttpHeaderValues.X_GZIP.contentEqualsIgnoreCase(contentEncoding)) {
			return new EmbeddedChannel(
				ctx.channel().id(), ctx.channel().metadata().hasDisconnect(), ctx.channel().config(), ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP)
			);
		} else if (!HttpHeaderValues.DEFLATE.contentEqualsIgnoreCase(contentEncoding) && !HttpHeaderValues.X_DEFLATE.contentEqualsIgnoreCase(contentEncoding)) {
			return null;
		} else {
			ZlibWrapper wrapper = this.strict ? ZlibWrapper.ZLIB : ZlibWrapper.ZLIB_OR_NONE;
			return new EmbeddedChannel(ctx.channel().id(), ctx.channel().metadata().hasDisconnect(), ctx.channel().config(), ZlibCodecFactory.newZlibDecoder(wrapper));
		}
	}

	protected CharSequence getTargetContentEncoding(CharSequence contentEncoding) throws Http2Exception {
		return HttpHeaderValues.IDENTITY;
	}

	private void initDecompressor(ChannelHandlerContext ctx, int streamId, Http2Headers headers, boolean endOfStream) throws Http2Exception {
		Http2Stream stream = this.connection.stream(streamId);
		if (stream != null) {
			DelegatingDecompressorFrameListener.Http2Decompressor decompressor = this.decompressor(stream);
			if (decompressor == null && !endOfStream) {
				CharSequence contentEncoding = headers.get(HttpHeaderNames.CONTENT_ENCODING);
				if (contentEncoding == null) {
					contentEncoding = HttpHeaderValues.IDENTITY;
				}

				EmbeddedChannel channel = this.newContentDecompressor(ctx, contentEncoding);
				if (channel != null) {
					decompressor = new DelegatingDecompressorFrameListener.Http2Decompressor(channel);
					stream.setProperty(this.propertyKey, decompressor);
					CharSequence targetContentEncoding = this.getTargetContentEncoding(contentEncoding);
					if (HttpHeaderValues.IDENTITY.contentEqualsIgnoreCase(targetContentEncoding)) {
						headers.remove(HttpHeaderNames.CONTENT_ENCODING);
					} else {
						headers.set(HttpHeaderNames.CONTENT_ENCODING, targetContentEncoding);
					}
				}
			}

			if (decompressor != null) {
				headers.remove(HttpHeaderNames.CONTENT_LENGTH);
				if (!this.flowControllerInitialized) {
					this.flowControllerInitialized = true;
					this.connection.local().flowController(new DelegatingDecompressorFrameListener.ConsumedBytesConverter(this.connection.local().flowController()));
				}
			}
		}
	}

	DelegatingDecompressorFrameListener.Http2Decompressor decompressor(Http2Stream stream) {
		return stream == null ? null : stream.getProperty(this.propertyKey);
	}

	private static void cleanup(DelegatingDecompressorFrameListener.Http2Decompressor decompressor) {
		decompressor.decompressor().finishAndReleaseAll();
	}

	private static ByteBuf nextReadableBuf(EmbeddedChannel decompressor) {
		while (true) {
			ByteBuf buf = decompressor.readInbound();
			if (buf == null) {
				return null;
			}

			if (buf.isReadable()) {
				return buf;
			}

			buf.release();
		}
	}

	private final class ConsumedBytesConverter implements Http2LocalFlowController {
		private final Http2LocalFlowController flowController;

		ConsumedBytesConverter(Http2LocalFlowController flowController) {
			this.flowController = ObjectUtil.checkNotNull(flowController, "flowController");
		}

		@Override
		public Http2LocalFlowController frameWriter(Http2FrameWriter frameWriter) {
			return this.flowController.frameWriter(frameWriter);
		}

		@Override
		public void channelHandlerContext(ChannelHandlerContext ctx) throws Http2Exception {
			this.flowController.channelHandlerContext(ctx);
		}

		@Override
		public void initialWindowSize(int newWindowSize) throws Http2Exception {
			this.flowController.initialWindowSize(newWindowSize);
		}

		@Override
		public int initialWindowSize() {
			return this.flowController.initialWindowSize();
		}

		@Override
		public int windowSize(Http2Stream stream) {
			return this.flowController.windowSize(stream);
		}

		@Override
		public void incrementWindowSize(Http2Stream stream, int delta) throws Http2Exception {
			this.flowController.incrementWindowSize(stream, delta);
		}

		@Override
		public void receiveFlowControlledFrame(Http2Stream stream, ByteBuf data, int padding, boolean endOfStream) throws Http2Exception {
			this.flowController.receiveFlowControlledFrame(stream, data, padding, endOfStream);
		}

		@Override
		public boolean consumeBytes(Http2Stream stream, int numBytes) throws Http2Exception {
			DelegatingDecompressorFrameListener.Http2Decompressor decompressor = DelegatingDecompressorFrameListener.this.decompressor(stream);
			if (decompressor != null) {
				numBytes = decompressor.consumeBytes(stream.id(), numBytes);
			}

			try {
				return this.flowController.consumeBytes(stream, numBytes);
			} catch (Http2Exception var5) {
				throw var5;
			} catch (Throwable var6) {
				throw Http2Exception.streamError(stream.id(), Http2Error.INTERNAL_ERROR, var6, "Error while returning bytes to flow control window");
			}
		}

		@Override
		public int unconsumedBytes(Http2Stream stream) {
			return this.flowController.unconsumedBytes(stream);
		}

		@Override
		public int initialWindowSize(Http2Stream stream) {
			return this.flowController.initialWindowSize(stream);
		}
	}

	private static final class Http2Decompressor {
		private final EmbeddedChannel decompressor;
		private int compressed;
		private int decompressed;

		Http2Decompressor(EmbeddedChannel decompressor) {
			this.decompressor = decompressor;
		}

		EmbeddedChannel decompressor() {
			return this.decompressor;
		}

		void incrementCompressedBytes(int delta) {
			assert delta >= 0;

			this.compressed += delta;
		}

		void incrementDecompressedBytes(int delta) {
			assert delta >= 0;

			this.decompressed += delta;
		}

		int consumeBytes(int streamId, int decompressedBytes) throws Http2Exception {
			if (decompressedBytes < 0) {
				throw new IllegalArgumentException("decompressedBytes must not be negative: " + decompressedBytes);
			} else if (this.decompressed - decompressedBytes < 0) {
				throw Http2Exception.streamError(
					streamId,
					Http2Error.INTERNAL_ERROR,
					"Attempting to return too many bytes for stream %d. decompressed: %d decompressedBytes: %d",
					streamId,
					this.decompressed,
					decompressedBytes
				);
			} else {
				double consumedRatio = (double)decompressedBytes / (double)this.decompressed;
				int consumedCompressed = Math.min(this.compressed, (int)Math.ceil((double)this.compressed * consumedRatio));
				if (this.compressed - consumedCompressed < 0) {
					throw Http2Exception.streamError(
						streamId,
						Http2Error.INTERNAL_ERROR,
						"overflow when converting decompressed bytes to compressed bytes for stream %d.decompressedBytes: %d decompressed: %d compressed: %d consumedCompressed: %d",
						streamId,
						decompressedBytes,
						this.decompressed,
						this.compressed,
						consumedCompressed
					);
				} else {
					this.decompressed -= decompressedBytes;
					this.compressed -= consumedCompressed;
					return consumedCompressed;
				}
			}
		}
	}
}