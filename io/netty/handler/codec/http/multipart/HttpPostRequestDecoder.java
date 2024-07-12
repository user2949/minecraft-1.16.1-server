package io.netty.handler.codec.http.multipart;

import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.internal.StringUtil;
import java.nio.charset.Charset;
import java.util.List;

public class HttpPostRequestDecoder implements InterfaceHttpPostRequestDecoder {
	static final int DEFAULT_DISCARD_THRESHOLD = 10485760;
	private final InterfaceHttpPostRequestDecoder decoder;

	public HttpPostRequestDecoder(HttpRequest request) {
		this(new DefaultHttpDataFactory(16384L), request, HttpConstants.DEFAULT_CHARSET);
	}

	public HttpPostRequestDecoder(HttpDataFactory factory, HttpRequest request) {
		this(factory, request, HttpConstants.DEFAULT_CHARSET);
	}

	public HttpPostRequestDecoder(HttpDataFactory factory, HttpRequest request, Charset charset) {
		if (factory == null) {
			throw new NullPointerException("factory");
		} else if (request == null) {
			throw new NullPointerException("request");
		} else if (charset == null) {
			throw new NullPointerException("charset");
		} else {
			if (isMultipart(request)) {
				this.decoder = new HttpPostMultipartRequestDecoder(factory, request, charset);
			} else {
				this.decoder = new HttpPostStandardRequestDecoder(factory, request, charset);
			}
		}
	}

	public static boolean isMultipart(HttpRequest request) {
		return request.headers().contains(HttpHeaderNames.CONTENT_TYPE)
			? getMultipartDataBoundary(request.headers().get(HttpHeaderNames.CONTENT_TYPE)) != null
			: false;
	}

	protected static String[] getMultipartDataBoundary(String contentType) {
		String[] headerContentType = splitHeaderContentType(contentType);
		String multiPartHeader = HttpHeaderValues.MULTIPART_FORM_DATA.toString();
		if (headerContentType[0].regionMatches(true, 0, multiPartHeader, 0, multiPartHeader.length())) {
			String boundaryHeader = HttpHeaderValues.BOUNDARY.toString();
			int mrank;
			int crank;
			if (headerContentType[1].regionMatches(true, 0, boundaryHeader, 0, boundaryHeader.length())) {
				mrank = 1;
				crank = 2;
			} else {
				if (!headerContentType[2].regionMatches(true, 0, boundaryHeader, 0, boundaryHeader.length())) {
					return null;
				}

				mrank = 2;
				crank = 1;
			}

			String boundary = StringUtil.substringAfter(headerContentType[mrank], '=');
			if (boundary == null) {
				throw new HttpPostRequestDecoder.ErrorDataDecoderException("Needs a boundary value");
			} else {
				if (boundary.charAt(0) == '"') {
					String bound = boundary.trim();
					int index = bound.length() - 1;
					if (bound.charAt(index) == '"') {
						boundary = bound.substring(1, index);
					}
				}

				String charsetHeader = HttpHeaderValues.CHARSET.toString();
				if (headerContentType[crank].regionMatches(true, 0, charsetHeader, 0, charsetHeader.length())) {
					String charset = StringUtil.substringAfter(headerContentType[crank], '=');
					if (charset != null) {
						return new String[]{"--" + boundary, charset};
					}
				}

				return new String[]{"--" + boundary};
			}
		} else {
			return null;
		}
	}

	@Override
	public boolean isMultipart() {
		return this.decoder.isMultipart();
	}

	@Override
	public void setDiscardThreshold(int discardThreshold) {
		this.decoder.setDiscardThreshold(discardThreshold);
	}

	@Override
	public int getDiscardThreshold() {
		return this.decoder.getDiscardThreshold();
	}

	@Override
	public List<InterfaceHttpData> getBodyHttpDatas() {
		return this.decoder.getBodyHttpDatas();
	}

	@Override
	public List<InterfaceHttpData> getBodyHttpDatas(String name) {
		return this.decoder.getBodyHttpDatas(name);
	}

	@Override
	public InterfaceHttpData getBodyHttpData(String name) {
		return this.decoder.getBodyHttpData(name);
	}

	@Override
	public InterfaceHttpPostRequestDecoder offer(HttpContent content) {
		return this.decoder.offer(content);
	}

	@Override
	public boolean hasNext() {
		return this.decoder.hasNext();
	}

	@Override
	public InterfaceHttpData next() {
		return this.decoder.next();
	}

	@Override
	public InterfaceHttpData currentPartialHttpData() {
		return this.decoder.currentPartialHttpData();
	}

	@Override
	public void destroy() {
		this.decoder.destroy();
	}

	@Override
	public void cleanFiles() {
		this.decoder.cleanFiles();
	}

	@Override
	public void removeHttpDataFromClean(InterfaceHttpData data) {
		this.decoder.removeHttpDataFromClean(data);
	}

	private static String[] splitHeaderContentType(String sb) {
		int aStart = HttpPostBodyUtil.findNonWhitespace(sb, 0);
		int aEnd = sb.indexOf(59);
		if (aEnd == -1) {
			return new String[]{sb, "", ""};
		} else {
			int bStart = HttpPostBodyUtil.findNonWhitespace(sb, aEnd + 1);
			if (sb.charAt(aEnd - 1) == ' ') {
				aEnd--;
			}

			int bEnd = sb.indexOf(59, bStart);
			if (bEnd == -1) {
				bEnd = HttpPostBodyUtil.findEndOfString(sb);
				return new String[]{sb.substring(aStart, aEnd), sb.substring(bStart, bEnd), ""};
			} else {
				int cStart = HttpPostBodyUtil.findNonWhitespace(sb, bEnd + 1);
				if (sb.charAt(bEnd - 1) == ' ') {
					bEnd--;
				}

				int cEnd = HttpPostBodyUtil.findEndOfString(sb);
				return new String[]{sb.substring(aStart, aEnd), sb.substring(bStart, bEnd), sb.substring(cStart, cEnd)};
			}
		}
	}

	public static class EndOfDataDecoderException extends DecoderException {
		private static final long serialVersionUID = 1336267941020800769L;
	}

	public static class ErrorDataDecoderException extends DecoderException {
		private static final long serialVersionUID = 5020247425493164465L;

		public ErrorDataDecoderException() {
		}

		public ErrorDataDecoderException(String msg) {
			super(msg);
		}

		public ErrorDataDecoderException(Throwable cause) {
			super(cause);
		}

		public ErrorDataDecoderException(String msg, Throwable cause) {
			super(msg, cause);
		}
	}

	protected static enum MultiPartStatus {
		NOTSTARTED,
		PREAMBLE,
		HEADERDELIMITER,
		DISPOSITION,
		FIELD,
		FILEUPLOAD,
		MIXEDPREAMBLE,
		MIXEDDELIMITER,
		MIXEDDISPOSITION,
		MIXEDFILEUPLOAD,
		MIXEDCLOSEDELIMITER,
		CLOSEDELIMITER,
		PREEPILOGUE,
		EPILOGUE;
	}

	public static class NotEnoughDataDecoderException extends DecoderException {
		private static final long serialVersionUID = -7846841864603865638L;

		public NotEnoughDataDecoderException() {
		}

		public NotEnoughDataDecoderException(String msg) {
			super(msg);
		}

		public NotEnoughDataDecoderException(Throwable cause) {
			super(cause);
		}

		public NotEnoughDataDecoderException(String msg, Throwable cause) {
			super(msg, cause);
		}
	}
}
