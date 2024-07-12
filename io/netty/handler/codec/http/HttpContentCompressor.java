package io.netty.handler.codec.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.codec.http.HttpContentEncoder.Result;

public class HttpContentCompressor extends HttpContentEncoder {
	private final int compressionLevel;
	private final int windowBits;
	private final int memLevel;
	private final int contentSizeThreshold;
	private ChannelHandlerContext ctx;

	public HttpContentCompressor() {
		this(6);
	}

	public HttpContentCompressor(int compressionLevel) {
		this(compressionLevel, 15, 8, 0);
	}

	public HttpContentCompressor(int compressionLevel, int windowBits, int memLevel) {
		this(compressionLevel, windowBits, memLevel, 0);
	}

	public HttpContentCompressor(int compressionLevel, int windowBits, int memLevel, int contentSizeThreshold) {
		if (compressionLevel < 0 || compressionLevel > 9) {
			throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
		} else if (windowBits < 9 || windowBits > 15) {
			throw new IllegalArgumentException("windowBits: " + windowBits + " (expected: 9-15)");
		} else if (memLevel < 1 || memLevel > 9) {
			throw new IllegalArgumentException("memLevel: " + memLevel + " (expected: 1-9)");
		} else if (contentSizeThreshold < 0) {
			throw new IllegalArgumentException("contentSizeThreshold: " + contentSizeThreshold + " (expected: non negative number)");
		} else {
			this.compressionLevel = compressionLevel;
			this.windowBits = windowBits;
			this.memLevel = memLevel;
			this.contentSizeThreshold = contentSizeThreshold;
		}
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		this.ctx = ctx;
	}

	@Override
	protected Result beginEncode(HttpResponse headers, String acceptEncoding) throws Exception {
		if (this.contentSizeThreshold > 0 && headers instanceof HttpContent && ((HttpContent)headers).content().readableBytes() < this.contentSizeThreshold) {
			return null;
		} else {
			String contentEncoding = headers.headers().get(HttpHeaderNames.CONTENT_ENCODING);
			if (contentEncoding != null) {
				return null;
			} else {
				ZlibWrapper wrapper = this.determineWrapper(acceptEncoding);
				if (wrapper == null) {
					return null;
				} else {
					String targetContentEncoding;
					switch (wrapper) {
						case GZIP:
							targetContentEncoding = "gzip";
							break;
						case ZLIB:
							targetContentEncoding = "deflate";
							break;
						default:
							throw new Error();
					}

					return new Result(
						targetContentEncoding,
						new EmbeddedChannel(
							this.ctx.channel().id(),
							this.ctx.channel().metadata().hasDisconnect(),
							this.ctx.channel().config(),
							ZlibCodecFactory.newZlibEncoder(wrapper, this.compressionLevel, this.windowBits, this.memLevel)
						)
					);
				}
			}
		}
	}

	protected ZlibWrapper determineWrapper(String acceptEncoding) {
		float starQ = -1.0F;
		float gzipQ = -1.0F;
		float deflateQ = -1.0F;

		for (String encoding : acceptEncoding.split(",")) {
			float q = 1.0F;
			int equalsPos = encoding.indexOf(61);
			if (equalsPos != -1) {
				try {
					q = Float.parseFloat(encoding.substring(equalsPos + 1));
				} catch (NumberFormatException var12) {
					q = 0.0F;
				}
			}

			if (encoding.contains("*")) {
				starQ = q;
			} else if (encoding.contains("gzip") && q > gzipQ) {
				gzipQ = q;
			} else if (encoding.contains("deflate") && q > deflateQ) {
				deflateQ = q;
			}
		}

		if (!(gzipQ > 0.0F) && !(deflateQ > 0.0F)) {
			if (starQ > 0.0F) {
				if (gzipQ == -1.0F) {
					return ZlibWrapper.GZIP;
				}

				if (deflateQ == -1.0F) {
					return ZlibWrapper.ZLIB;
				}
			}

			return null;
		} else {
			return gzipQ >= deflateQ ? ZlibWrapper.GZIP : ZlibWrapper.ZLIB;
		}
	}
}
