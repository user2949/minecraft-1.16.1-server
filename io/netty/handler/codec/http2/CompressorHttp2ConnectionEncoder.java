package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http2.Http2Connection.PropertyKey;
import io.netty.util.concurrent.Promise;
import io.netty.util.concurrent.PromiseCombiner;

public class CompressorHttp2ConnectionEncoder extends DecoratingHttp2ConnectionEncoder {
	public static final int DEFAULT_COMPRESSION_LEVEL = 6;
	public static final int DEFAULT_WINDOW_BITS = 15;
	public static final int DEFAULT_MEM_LEVEL = 8;
	private final int compressionLevel;
	private final int windowBits;
	private final int memLevel;
	private final PropertyKey propertyKey;

	public CompressorHttp2ConnectionEncoder(Http2ConnectionEncoder delegate) {
		this(delegate, 6, 15, 8);
	}

	public CompressorHttp2ConnectionEncoder(Http2ConnectionEncoder delegate, int compressionLevel, int windowBits, int memLevel) {
		super(delegate);
		if (compressionLevel < 0 || compressionLevel > 9) {
			throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
		} else if (windowBits < 9 || windowBits > 15) {
			throw new IllegalArgumentException("windowBits: " + windowBits + " (expected: 9-15)");
		} else if (memLevel >= 1 && memLevel <= 9) {
			this.compressionLevel = compressionLevel;
			this.windowBits = windowBits;
			this.memLevel = memLevel;
			this.propertyKey = this.connection().newKey();
			this.connection().addListener(new Http2ConnectionAdapter() {
				@Override
				public void onStreamRemoved(Http2Stream stream) {
					EmbeddedChannel compressor = stream.getProperty(CompressorHttp2ConnectionEncoder.this.propertyKey);
					if (compressor != null) {
						CompressorHttp2ConnectionEncoder.this.cleanup(stream, compressor);
					}
				}
			});
		} else {
			throw new IllegalArgumentException("memLevel: " + memLevel + " (expected: 1-9)");
		}
	}

	@Override
	public ChannelFuture writeData(ChannelHandlerContext ctx, int streamId, ByteBuf data, int padding, boolean endOfStream, ChannelPromise promise) {
		Http2Stream stream = this.connection().stream(streamId);
		EmbeddedChannel channel = stream == null ? null : stream.getProperty(this.propertyKey);
		if (channel == null) {
			return super.writeData(ctx, streamId, data, padding, endOfStream, promise);
		} else {
			ChannelFuture combiner;
			try {
				channel.writeOutbound(data);
				ByteBuf buf = nextReadableBuf(channel);
				if (buf != null) {
					PromiseCombiner combinerx = new PromiseCombiner();

					while (true) {
						ByteBuf nextBuf = nextReadableBuf(channel);
						boolean compressedEndOfStream = nextBuf == null && endOfStream;
						if (compressedEndOfStream && channel.finish()) {
							nextBuf = nextReadableBuf(channel);
							compressedEndOfStream = nextBuf == null;
						}

						ChannelPromise bufPromise = ctx.newPromise();
						combinerx.add((Promise)bufPromise);
						super.writeData(ctx, streamId, buf, padding, compressedEndOfStream, bufPromise);
						if (nextBuf == null) {
							combinerx.finish(promise);
							return promise;
						}

						padding = 0;
						buf = nextBuf;
					}
				}

				if (!endOfStream) {
					promise.setSuccess();
					return promise;
				}

				if (channel.finish()) {
					buf = nextReadableBuf(channel);
				}

				combiner = super.writeData(ctx, streamId, buf == null ? Unpooled.EMPTY_BUFFER : buf, padding, true, promise);
			} catch (Throwable var17) {
				promise.tryFailure(var17);
				return promise;
			} finally {
				if (endOfStream) {
					this.cleanup(stream, channel);
				}
			}

			return combiner;
		}
	}

	@Override
	public ChannelFuture writeHeaders(ChannelHandlerContext ctx, int streamId, Http2Headers headers, int padding, boolean endStream, ChannelPromise promise) {
		try {
			EmbeddedChannel compressor = this.newCompressor(ctx, headers, endStream);
			ChannelFuture future = super.writeHeaders(ctx, streamId, headers, padding, endStream, promise);
			this.bindCompressorToStream(compressor, streamId);
			return future;
		} catch (Throwable var9) {
			promise.tryFailure(var9);
			return promise;
		}
	}

	@Override
	public ChannelFuture writeHeaders(
		ChannelHandlerContext ctx,
		int streamId,
		Http2Headers headers,
		int streamDependency,
		short weight,
		boolean exclusive,
		int padding,
		boolean endOfStream,
		ChannelPromise promise
	) {
		try {
			EmbeddedChannel compressor = this.newCompressor(ctx, headers, endOfStream);
			ChannelFuture future = super.writeHeaders(ctx, streamId, headers, streamDependency, weight, exclusive, padding, endOfStream, promise);
			this.bindCompressorToStream(compressor, streamId);
			return future;
		} catch (Throwable var12) {
			promise.tryFailure(var12);
			return promise;
		}
	}

	protected EmbeddedChannel newContentCompressor(ChannelHandlerContext ctx, CharSequence contentEncoding) throws Http2Exception {
		if (HttpHeaderValues.GZIP.contentEqualsIgnoreCase(contentEncoding) || HttpHeaderValues.X_GZIP.contentEqualsIgnoreCase(contentEncoding)) {
			return this.newCompressionChannel(ctx, ZlibWrapper.GZIP);
		} else {
			return !HttpHeaderValues.DEFLATE.contentEqualsIgnoreCase(contentEncoding) && !HttpHeaderValues.X_DEFLATE.contentEqualsIgnoreCase(contentEncoding)
				? null
				: this.newCompressionChannel(ctx, ZlibWrapper.ZLIB);
		}
	}

	protected CharSequence getTargetContentEncoding(CharSequence contentEncoding) throws Http2Exception {
		return contentEncoding;
	}

	private EmbeddedChannel newCompressionChannel(ChannelHandlerContext ctx, ZlibWrapper wrapper) {
		return new EmbeddedChannel(
			ctx.channel().id(),
			ctx.channel().metadata().hasDisconnect(),
			ctx.channel().config(),
			ZlibCodecFactory.newZlibEncoder(wrapper, this.compressionLevel, this.windowBits, this.memLevel)
		);
	}

	private EmbeddedChannel newCompressor(ChannelHandlerContext ctx, Http2Headers headers, boolean endOfStream) throws Http2Exception {
		if (endOfStream) {
			return null;
		} else {
			CharSequence encoding = headers.get(HttpHeaderNames.CONTENT_ENCODING);
			if (encoding == null) {
				encoding = HttpHeaderValues.IDENTITY;
			}

			EmbeddedChannel compressor = this.newContentCompressor(ctx, encoding);
			if (compressor != null) {
				CharSequence targetContentEncoding = this.getTargetContentEncoding(encoding);
				if (HttpHeaderValues.IDENTITY.contentEqualsIgnoreCase(targetContentEncoding)) {
					headers.remove(HttpHeaderNames.CONTENT_ENCODING);
				} else {
					headers.set(HttpHeaderNames.CONTENT_ENCODING, targetContentEncoding);
				}

				headers.remove(HttpHeaderNames.CONTENT_LENGTH);
			}

			return compressor;
		}
	}

	private void bindCompressorToStream(EmbeddedChannel compressor, int streamId) {
		if (compressor != null) {
			Http2Stream stream = this.connection().stream(streamId);
			if (stream != null) {
				stream.setProperty(this.propertyKey, compressor);
			}
		}
	}

	void cleanup(Http2Stream stream, EmbeddedChannel compressor) {
		if (compressor.finish()) {
			while (true) {
				ByteBuf buf = compressor.readOutbound();
				if (buf == null) {
					break;
				}

				buf.release();
			}
		}

		stream.removeProperty(this.propertyKey);
	}

	private static ByteBuf nextReadableBuf(EmbeddedChannel compressor) {
		while (true) {
			ByteBuf buf = compressor.readOutbound();
			if (buf == null) {
				return null;
			}

			if (buf.isReadable()) {
				return buf;
			}

			buf.release();
		}
	}
}
