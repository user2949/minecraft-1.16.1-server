package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.HttpPostBodyUtil.SeekAheadOptimize;
import io.netty.handler.codec.http.multipart.HttpPostBodyUtil.TransferEncodingMechanism;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.EndOfDataDecoderException;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.ErrorDataDecoderException;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.MultiPartStatus;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.NotEnoughDataDecoderException;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class HttpPostMultipartRequestDecoder implements InterfaceHttpPostRequestDecoder {
	private final HttpDataFactory factory;
	private final HttpRequest request;
	private Charset charset;
	private boolean isLastChunk;
	private final List<InterfaceHttpData> bodyListHttpData = new ArrayList();
	private final Map<String, List<InterfaceHttpData>> bodyMapHttpData = new TreeMap(CaseIgnoringComparator.INSTANCE);
	private ByteBuf undecodedChunk;
	private int bodyListHttpDataRank;
	private String multipartDataBoundary;
	private String multipartMixedBoundary;
	private MultiPartStatus currentStatus = MultiPartStatus.NOTSTARTED;
	private Map<CharSequence, Attribute> currentFieldAttributes;
	private FileUpload currentFileUpload;
	private Attribute currentAttribute;
	private boolean destroyed;
	private int discardThreshold = 10485760;
	private static final String FILENAME_ENCODED = HttpHeaderValues.FILENAME.toString() + '*';

	public HttpPostMultipartRequestDecoder(HttpRequest request) {
		this(new DefaultHttpDataFactory(16384L), request, HttpConstants.DEFAULT_CHARSET);
	}

	public HttpPostMultipartRequestDecoder(HttpDataFactory factory, HttpRequest request) {
		this(factory, request, HttpConstants.DEFAULT_CHARSET);
	}

	public HttpPostMultipartRequestDecoder(HttpDataFactory factory, HttpRequest request, Charset charset) {
		this.request = ObjectUtil.checkNotNull(request, "request");
		this.charset = ObjectUtil.checkNotNull(charset, "charset");
		this.factory = ObjectUtil.checkNotNull(factory, "factory");
		this.setMultipart(this.request.headers().get(HttpHeaderNames.CONTENT_TYPE));
		if (request instanceof HttpContent) {
			this.offer((HttpContent)request);
		} else {
			this.undecodedChunk = Unpooled.buffer();
			this.parseBody();
		}
	}

	private void setMultipart(String contentType) {
		String[] dataBoundary = HttpPostRequestDecoder.getMultipartDataBoundary(contentType);
		if (dataBoundary != null) {
			this.multipartDataBoundary = dataBoundary[0];
			if (dataBoundary.length > 1 && dataBoundary[1] != null) {
				this.charset = Charset.forName(dataBoundary[1]);
			}
		} else {
			this.multipartDataBoundary = null;
		}

		this.currentStatus = MultiPartStatus.HEADERDELIMITER;
	}

	private void checkDestroyed() {
		if (this.destroyed) {
			throw new IllegalStateException(HttpPostMultipartRequestDecoder.class.getSimpleName() + " was destroyed already");
		}
	}

	@Override
	public boolean isMultipart() {
		this.checkDestroyed();
		return true;
	}

	@Override
	public void setDiscardThreshold(int discardThreshold) {
		this.discardThreshold = ObjectUtil.checkPositiveOrZero(discardThreshold, "discardThreshold");
	}

	@Override
	public int getDiscardThreshold() {
		return this.discardThreshold;
	}

	@Override
	public List<InterfaceHttpData> getBodyHttpDatas() {
		this.checkDestroyed();
		if (!this.isLastChunk) {
			throw new NotEnoughDataDecoderException();
		} else {
			return this.bodyListHttpData;
		}
	}

	@Override
	public List<InterfaceHttpData> getBodyHttpDatas(String name) {
		this.checkDestroyed();
		if (!this.isLastChunk) {
			throw new NotEnoughDataDecoderException();
		} else {
			return (List<InterfaceHttpData>)this.bodyMapHttpData.get(name);
		}
	}

	@Override
	public InterfaceHttpData getBodyHttpData(String name) {
		this.checkDestroyed();
		if (!this.isLastChunk) {
			throw new NotEnoughDataDecoderException();
		} else {
			List<InterfaceHttpData> list = (List<InterfaceHttpData>)this.bodyMapHttpData.get(name);
			return list != null ? (InterfaceHttpData)list.get(0) : null;
		}
	}

	public HttpPostMultipartRequestDecoder offer(HttpContent content) {
		this.checkDestroyed();
		ByteBuf buf = content.content();
		if (this.undecodedChunk == null) {
			this.undecodedChunk = buf.copy();
		} else {
			this.undecodedChunk.writeBytes(buf);
		}

		if (content instanceof LastHttpContent) {
			this.isLastChunk = true;
		}

		this.parseBody();
		if (this.undecodedChunk != null && this.undecodedChunk.writerIndex() > this.discardThreshold) {
			this.undecodedChunk.discardReadBytes();
		}

		return this;
	}

	@Override
	public boolean hasNext() {
		this.checkDestroyed();
		if (this.currentStatus == MultiPartStatus.EPILOGUE && this.bodyListHttpDataRank >= this.bodyListHttpData.size()) {
			throw new EndOfDataDecoderException();
		} else {
			return !this.bodyListHttpData.isEmpty() && this.bodyListHttpDataRank < this.bodyListHttpData.size();
		}
	}

	@Override
	public InterfaceHttpData next() {
		this.checkDestroyed();
		return this.hasNext() ? (InterfaceHttpData)this.bodyListHttpData.get(this.bodyListHttpDataRank++) : null;
	}

	@Override
	public InterfaceHttpData currentPartialHttpData() {
		return (InterfaceHttpData)(this.currentFileUpload != null ? this.currentFileUpload : this.currentAttribute);
	}

	private void parseBody() {
		if (this.currentStatus != MultiPartStatus.PREEPILOGUE && this.currentStatus != MultiPartStatus.EPILOGUE) {
			this.parseBodyMultipart();
		} else {
			if (this.isLastChunk) {
				this.currentStatus = MultiPartStatus.EPILOGUE;
			}
		}
	}

	protected void addHttpData(InterfaceHttpData data) {
		if (data != null) {
			List<InterfaceHttpData> datas = (List<InterfaceHttpData>)this.bodyMapHttpData.get(data.getName());
			if (datas == null) {
				datas = new ArrayList(1);
				this.bodyMapHttpData.put(data.getName(), datas);
			}

			datas.add(data);
			this.bodyListHttpData.add(data);
		}
	}

	private void parseBodyMultipart() {
		if (this.undecodedChunk != null && this.undecodedChunk.readableBytes() != 0) {
			for (InterfaceHttpData data = this.decodeMultipart(this.currentStatus); data != null; data = this.decodeMultipart(this.currentStatus)) {
				this.addHttpData(data);
				if (this.currentStatus == MultiPartStatus.PREEPILOGUE || this.currentStatus == MultiPartStatus.EPILOGUE) {
					break;
				}
			}
		}
	}

	private InterfaceHttpData decodeMultipart(MultiPartStatus state) {
		switch (state) {
			case NOTSTARTED:
				throw new ErrorDataDecoderException("Should not be called with the current getStatus");
			case PREAMBLE:
				throw new ErrorDataDecoderException("Should not be called with the current getStatus");
			case HEADERDELIMITER:
				return this.findMultipartDelimiter(this.multipartDataBoundary, MultiPartStatus.DISPOSITION, MultiPartStatus.PREEPILOGUE);
			case DISPOSITION:
				return this.findMultipartDisposition();
			case FIELD:
				Charset localCharset = null;
				Attribute charsetAttribute = (Attribute)this.currentFieldAttributes.get(HttpHeaderValues.CHARSET);
				if (charsetAttribute != null) {
					try {
						localCharset = Charset.forName(charsetAttribute.getValue());
					} catch (IOException var14) {
						throw new ErrorDataDecoderException(var14);
					} catch (UnsupportedCharsetException var15) {
						throw new ErrorDataDecoderException(var15);
					}
				}

				Attribute nameAttribute = (Attribute)this.currentFieldAttributes.get(HttpHeaderValues.NAME);
				if (this.currentAttribute == null) {
					Attribute lengthAttribute = (Attribute)this.currentFieldAttributes.get(HttpHeaderNames.CONTENT_LENGTH);

					long size;
					try {
						size = lengthAttribute != null ? Long.parseLong(lengthAttribute.getValue()) : 0L;
					} catch (IOException var12) {
						throw new ErrorDataDecoderException(var12);
					} catch (NumberFormatException var13) {
						size = 0L;
					}

					try {
						if (size > 0L) {
							this.currentAttribute = this.factory.createAttribute(this.request, cleanString(nameAttribute.getValue()), size);
						} else {
							this.currentAttribute = this.factory.createAttribute(this.request, cleanString(nameAttribute.getValue()));
						}
					} catch (NullPointerException var9) {
						throw new ErrorDataDecoderException(var9);
					} catch (IllegalArgumentException var10) {
						throw new ErrorDataDecoderException(var10);
					} catch (IOException var11) {
						throw new ErrorDataDecoderException(var11);
					}

					if (localCharset != null) {
						this.currentAttribute.setCharset(localCharset);
					}
				}

				if (!loadDataMultipart(this.undecodedChunk, this.multipartDataBoundary, this.currentAttribute)) {
					return null;
				}

				Attribute finalAttribute = this.currentAttribute;
				this.currentAttribute = null;
				this.currentFieldAttributes = null;
				this.currentStatus = MultiPartStatus.HEADERDELIMITER;
				return finalAttribute;
			case FILEUPLOAD:
				return this.getFileUpload(this.multipartDataBoundary);
			case MIXEDDELIMITER:
				return this.findMultipartDelimiter(this.multipartMixedBoundary, MultiPartStatus.MIXEDDISPOSITION, MultiPartStatus.HEADERDELIMITER);
			case MIXEDDISPOSITION:
				return this.findMultipartDisposition();
			case MIXEDFILEUPLOAD:
				return this.getFileUpload(this.multipartMixedBoundary);
			case PREEPILOGUE:
				return null;
			case EPILOGUE:
				return null;
			default:
				throw new ErrorDataDecoderException("Shouldn't reach here.");
		}
	}

	private static void skipControlCharacters(ByteBuf undecodedChunk) {
		if (!undecodedChunk.hasArray()) {
			try {
				skipControlCharactersStandard(undecodedChunk);
			} catch (IndexOutOfBoundsException var3) {
				throw new NotEnoughDataDecoderException(var3);
			}
		} else {
			SeekAheadOptimize sao = new SeekAheadOptimize(undecodedChunk);

			while (sao.pos < sao.limit) {
				char c = (char)(sao.bytes[sao.pos++] & 255);
				if (!Character.isISOControl(c) && !Character.isWhitespace(c)) {
					sao.setReadPosition(1);
					return;
				}
			}

			throw new NotEnoughDataDecoderException("Access out of bounds");
		}
	}

	private static void skipControlCharactersStandard(ByteBuf undecodedChunk) {
		char c;
		do {
			c = (char)undecodedChunk.readUnsignedByte();
		} while (Character.isISOControl(c) || Character.isWhitespace(c));

		undecodedChunk.readerIndex(undecodedChunk.readerIndex() - 1);
	}

	private InterfaceHttpData findMultipartDelimiter(String delimiter, MultiPartStatus dispositionStatus, MultiPartStatus closeDelimiterStatus) {
		int readerIndex = this.undecodedChunk.readerIndex();

		try {
			skipControlCharacters(this.undecodedChunk);
		} catch (NotEnoughDataDecoderException var8) {
			this.undecodedChunk.readerIndex(readerIndex);
			return null;
		}

		this.skipOneLine();

		String newline;
		try {
			newline = readDelimiter(this.undecodedChunk, delimiter);
		} catch (NotEnoughDataDecoderException var7) {
			this.undecodedChunk.readerIndex(readerIndex);
			return null;
		}

		if (newline.equals(delimiter)) {
			this.currentStatus = dispositionStatus;
			return this.decodeMultipart(dispositionStatus);
		} else if (newline.equals(delimiter + "--")) {
			this.currentStatus = closeDelimiterStatus;
			if (this.currentStatus == MultiPartStatus.HEADERDELIMITER) {
				this.currentFieldAttributes = null;
				return this.decodeMultipart(MultiPartStatus.HEADERDELIMITER);
			} else {
				return null;
			}
		} else {
			this.undecodedChunk.readerIndex(readerIndex);
			throw new ErrorDataDecoderException("No Multipart delimiter found");
		}
	}

	private InterfaceHttpData findMultipartDisposition() {
		int readerIndex = this.undecodedChunk.readerIndex();
		if (this.currentStatus == MultiPartStatus.DISPOSITION) {
			this.currentFieldAttributes = new TreeMap(CaseIgnoringComparator.INSTANCE);
		}

		while (!this.skipOneLine()) {
			String newline;
			try {
				skipControlCharacters(this.undecodedChunk);
				newline = readLine(this.undecodedChunk, this.charset);
			} catch (NotEnoughDataDecoderException var19) {
				this.undecodedChunk.readerIndex(readerIndex);
				return null;
			}

			String[] contents = splitMultipartHeader(newline);
			if (!HttpHeaderNames.CONTENT_DISPOSITION.contentEqualsIgnoreCase(contents[0])) {
				if (HttpHeaderNames.CONTENT_TRANSFER_ENCODING.contentEqualsIgnoreCase(contents[0])) {
					Attribute attribute;
					try {
						attribute = this.factory.createAttribute(this.request, HttpHeaderNames.CONTENT_TRANSFER_ENCODING.toString(), cleanString(contents[1]));
					} catch (NullPointerException var15) {
						throw new ErrorDataDecoderException(var15);
					} catch (IllegalArgumentException var16) {
						throw new ErrorDataDecoderException(var16);
					}

					this.currentFieldAttributes.put(HttpHeaderNames.CONTENT_TRANSFER_ENCODING, attribute);
				} else if (HttpHeaderNames.CONTENT_LENGTH.contentEqualsIgnoreCase(contents[0])) {
					Attribute attribute;
					try {
						attribute = this.factory.createAttribute(this.request, HttpHeaderNames.CONTENT_LENGTH.toString(), cleanString(contents[1]));
					} catch (NullPointerException var13) {
						throw new ErrorDataDecoderException(var13);
					} catch (IllegalArgumentException var14) {
						throw new ErrorDataDecoderException(var14);
					}

					this.currentFieldAttributes.put(HttpHeaderNames.CONTENT_LENGTH, attribute);
				} else {
					if (!HttpHeaderNames.CONTENT_TYPE.contentEqualsIgnoreCase(contents[0])) {
						throw new ErrorDataDecoderException("Unknown Params: " + newline);
					}

					if (HttpHeaderValues.MULTIPART_MIXED.contentEqualsIgnoreCase(contents[1])) {
						if (this.currentStatus == MultiPartStatus.DISPOSITION) {
							String values = StringUtil.substringAfter(contents[2], '=');
							this.multipartMixedBoundary = "--" + values;
							this.currentStatus = MultiPartStatus.MIXEDDELIMITER;
							return this.decodeMultipart(MultiPartStatus.MIXEDDELIMITER);
						}

						throw new ErrorDataDecoderException("Mixed Multipart found in a previous Mixed Multipart");
					}

					for (int i = 1; i < contents.length; i++) {
						String charsetHeader = HttpHeaderValues.CHARSET.toString();
						if (contents[i].regionMatches(true, 0, charsetHeader, 0, charsetHeader.length())) {
							String values = StringUtil.substringAfter(contents[i], '=');

							Attribute attribute;
							try {
								attribute = this.factory.createAttribute(this.request, charsetHeader, cleanString(values));
							} catch (NullPointerException var11) {
								throw new ErrorDataDecoderException(var11);
							} catch (IllegalArgumentException var12) {
								throw new ErrorDataDecoderException(var12);
							}

							this.currentFieldAttributes.put(HttpHeaderValues.CHARSET, attribute);
						} else {
							Attribute attribute;
							try {
								attribute = this.factory.createAttribute(this.request, cleanString(contents[0]), contents[i]);
							} catch (NullPointerException var9) {
								throw new ErrorDataDecoderException(var9);
							} catch (IllegalArgumentException var10) {
								throw new ErrorDataDecoderException(var10);
							}

							this.currentFieldAttributes.put(attribute.getName(), attribute);
						}
					}
				}
			} else {
				boolean checkSecondArg;
				if (this.currentStatus == MultiPartStatus.DISPOSITION) {
					checkSecondArg = HttpHeaderValues.FORM_DATA.contentEqualsIgnoreCase(contents[1]);
				} else {
					checkSecondArg = HttpHeaderValues.ATTACHMENT.contentEqualsIgnoreCase(contents[1]) || HttpHeaderValues.FILE.contentEqualsIgnoreCase(contents[1]);
				}

				if (checkSecondArg) {
					for (int ix = 2; ix < contents.length; ix++) {
						String[] values = contents[ix].split("=", 2);

						Attribute attribute;
						try {
							attribute = this.getContentDispositionAttribute(values);
						} catch (NullPointerException var17) {
							throw new ErrorDataDecoderException(var17);
						} catch (IllegalArgumentException var18) {
							throw new ErrorDataDecoderException(var18);
						}

						this.currentFieldAttributes.put(attribute.getName(), attribute);
					}
				}
			}
		}

		Attribute filenameAttribute = (Attribute)this.currentFieldAttributes.get(HttpHeaderValues.FILENAME);
		if (this.currentStatus == MultiPartStatus.DISPOSITION) {
			if (filenameAttribute != null) {
				this.currentStatus = MultiPartStatus.FILEUPLOAD;
				return this.decodeMultipart(MultiPartStatus.FILEUPLOAD);
			} else {
				this.currentStatus = MultiPartStatus.FIELD;
				return this.decodeMultipart(MultiPartStatus.FIELD);
			}
		} else if (filenameAttribute != null) {
			this.currentStatus = MultiPartStatus.MIXEDFILEUPLOAD;
			return this.decodeMultipart(MultiPartStatus.MIXEDFILEUPLOAD);
		} else {
			throw new ErrorDataDecoderException("Filename not found");
		}
	}

	private Attribute getContentDispositionAttribute(String... values) {
		String name = cleanString(values[0]);
		String value = values[1];
		if (HttpHeaderValues.FILENAME.contentEquals(name)) {
			int last = value.length() - 1;
			if (last > 0 && value.charAt(0) == '"' && value.charAt(last) == '"') {
				value = value.substring(1, last);
			}
		} else if (FILENAME_ENCODED.equals(name)) {
			try {
				name = HttpHeaderValues.FILENAME.toString();
				String[] split = value.split("'", 3);
				value = QueryStringDecoder.decodeComponent(split[2], Charset.forName(split[0]));
			} catch (ArrayIndexOutOfBoundsException var5) {
				throw new ErrorDataDecoderException(var5);
			} catch (UnsupportedCharsetException var6) {
				throw new ErrorDataDecoderException(var6);
			}
		} else {
			value = cleanString(value);
		}

		return this.factory.createAttribute(this.request, name, value);
	}

	protected InterfaceHttpData getFileUpload(String delimiter) {
		Attribute encoding = (Attribute)this.currentFieldAttributes.get(HttpHeaderNames.CONTENT_TRANSFER_ENCODING);
		Charset localCharset = this.charset;
		TransferEncodingMechanism mechanism = TransferEncodingMechanism.BIT7;
		if (encoding != null) {
			String code;
			try {
				code = encoding.getValue().toLowerCase();
			} catch (IOException var20) {
				throw new ErrorDataDecoderException(var20);
			}

			if (code.equals(TransferEncodingMechanism.BIT7.value())) {
				localCharset = CharsetUtil.US_ASCII;
			} else if (code.equals(TransferEncodingMechanism.BIT8.value())) {
				localCharset = CharsetUtil.ISO_8859_1;
				mechanism = TransferEncodingMechanism.BIT8;
			} else {
				if (!code.equals(TransferEncodingMechanism.BINARY.value())) {
					throw new ErrorDataDecoderException("TransferEncoding Unknown: " + code);
				}

				mechanism = TransferEncodingMechanism.BINARY;
			}
		}

		Attribute charsetAttribute = (Attribute)this.currentFieldAttributes.get(HttpHeaderValues.CHARSET);
		if (charsetAttribute != null) {
			try {
				localCharset = Charset.forName(charsetAttribute.getValue());
			} catch (IOException var18) {
				throw new ErrorDataDecoderException(var18);
			} catch (UnsupportedCharsetException var19) {
				throw new ErrorDataDecoderException(var19);
			}
		}

		if (this.currentFileUpload == null) {
			Attribute filenameAttribute = (Attribute)this.currentFieldAttributes.get(HttpHeaderValues.FILENAME);
			Attribute nameAttribute = (Attribute)this.currentFieldAttributes.get(HttpHeaderValues.NAME);
			Attribute contentTypeAttribute = (Attribute)this.currentFieldAttributes.get(HttpHeaderNames.CONTENT_TYPE);
			Attribute lengthAttribute = (Attribute)this.currentFieldAttributes.get(HttpHeaderNames.CONTENT_LENGTH);

			long size;
			try {
				size = lengthAttribute != null ? Long.parseLong(lengthAttribute.getValue()) : 0L;
			} catch (IOException var16) {
				throw new ErrorDataDecoderException(var16);
			} catch (NumberFormatException var17) {
				size = 0L;
			}

			try {
				String contentType;
				if (contentTypeAttribute != null) {
					contentType = contentTypeAttribute.getValue();
				} else {
					contentType = "application/octet-stream";
				}

				this.currentFileUpload = this.factory
					.createFileUpload(
						this.request, cleanString(nameAttribute.getValue()), cleanString(filenameAttribute.getValue()), contentType, mechanism.value(), localCharset, size
					);
			} catch (NullPointerException var13) {
				throw new ErrorDataDecoderException(var13);
			} catch (IllegalArgumentException var14) {
				throw new ErrorDataDecoderException(var14);
			} catch (IOException var15) {
				throw new ErrorDataDecoderException(var15);
			}
		}

		if (!loadDataMultipart(this.undecodedChunk, delimiter, this.currentFileUpload)) {
			return null;
		} else if (this.currentFileUpload.isCompleted()) {
			if (this.currentStatus == MultiPartStatus.FILEUPLOAD) {
				this.currentStatus = MultiPartStatus.HEADERDELIMITER;
				this.currentFieldAttributes = null;
			} else {
				this.currentStatus = MultiPartStatus.MIXEDDELIMITER;
				this.cleanMixedAttributes();
			}

			FileUpload fileUpload = this.currentFileUpload;
			this.currentFileUpload = null;
			return fileUpload;
		} else {
			return null;
		}
	}

	@Override
	public void destroy() {
		this.checkDestroyed();
		this.cleanFiles();
		this.destroyed = true;
		if (this.undecodedChunk != null && this.undecodedChunk.refCnt() > 0) {
			this.undecodedChunk.release();
			this.undecodedChunk = null;
		}

		for (int i = this.bodyListHttpDataRank; i < this.bodyListHttpData.size(); i++) {
			((InterfaceHttpData)this.bodyListHttpData.get(i)).release();
		}
	}

	@Override
	public void cleanFiles() {
		this.checkDestroyed();
		this.factory.cleanRequestHttpData(this.request);
	}

	@Override
	public void removeHttpDataFromClean(InterfaceHttpData data) {
		this.checkDestroyed();
		this.factory.removeHttpDataFromClean(this.request, data);
	}

	private void cleanMixedAttributes() {
		this.currentFieldAttributes.remove(HttpHeaderValues.CHARSET);
		this.currentFieldAttributes.remove(HttpHeaderNames.CONTENT_LENGTH);
		this.currentFieldAttributes.remove(HttpHeaderNames.CONTENT_TRANSFER_ENCODING);
		this.currentFieldAttributes.remove(HttpHeaderNames.CONTENT_TYPE);
		this.currentFieldAttributes.remove(HttpHeaderValues.FILENAME);
	}

	private static String readLineStandard(ByteBuf undecodedChunk, Charset charset) {
		int readerIndex = undecodedChunk.readerIndex();

		try {
			ByteBuf line = Unpooled.buffer(64);

			while (undecodedChunk.isReadable()) {
				byte nextByte = undecodedChunk.readByte();
				if (nextByte == 13) {
					nextByte = undecodedChunk.getByte(undecodedChunk.readerIndex());
					if (nextByte == 10) {
						undecodedChunk.readByte();
						return line.toString(charset);
					}

					line.writeByte(13);
				} else {
					if (nextByte == 10) {
						return line.toString(charset);
					}

					line.writeByte(nextByte);
				}
			}
		} catch (IndexOutOfBoundsException var5) {
			undecodedChunk.readerIndex(readerIndex);
			throw new NotEnoughDataDecoderException(var5);
		}

		undecodedChunk.readerIndex(readerIndex);
		throw new NotEnoughDataDecoderException();
	}

	private static String readLine(ByteBuf undecodedChunk, Charset charset) {
		if (!undecodedChunk.hasArray()) {
			return readLineStandard(undecodedChunk, charset);
		} else {
			SeekAheadOptimize sao = new SeekAheadOptimize(undecodedChunk);
			int readerIndex = undecodedChunk.readerIndex();

			try {
				ByteBuf line = Unpooled.buffer(64);

				while (sao.pos < sao.limit) {
					byte nextByte = sao.bytes[sao.pos++];
					if (nextByte == 13) {
						if (sao.pos < sao.limit) {
							nextByte = sao.bytes[sao.pos++];
							if (nextByte == 10) {
								sao.setReadPosition(0);
								return line.toString(charset);
							}

							sao.pos--;
							line.writeByte(13);
						} else {
							line.writeByte(nextByte);
						}
					} else {
						if (nextByte == 10) {
							sao.setReadPosition(0);
							return line.toString(charset);
						}

						line.writeByte(nextByte);
					}
				}
			} catch (IndexOutOfBoundsException var6) {
				undecodedChunk.readerIndex(readerIndex);
				throw new NotEnoughDataDecoderException(var6);
			}

			undecodedChunk.readerIndex(readerIndex);
			throw new NotEnoughDataDecoderException();
		}
	}

	private static String readDelimiterStandard(ByteBuf undecodedChunk, String delimiter) {
		int readerIndex = undecodedChunk.readerIndex();

		try {
			StringBuilder sb = new StringBuilder(64);
			int delimiterPos = 0;
			int len = delimiter.length();

			while (undecodedChunk.isReadable() && delimiterPos < len) {
				byte nextByte = undecodedChunk.readByte();
				if (nextByte != delimiter.charAt(delimiterPos)) {
					undecodedChunk.readerIndex(readerIndex);
					throw new NotEnoughDataDecoderException();
				}

				delimiterPos++;
				sb.append((char)nextByte);
			}

			if (undecodedChunk.isReadable()) {
				byte nextByte = undecodedChunk.readByte();
				if (nextByte == 13) {
					nextByte = undecodedChunk.readByte();
					if (nextByte == 10) {
						return sb.toString();
					}

					undecodedChunk.readerIndex(readerIndex);
					throw new NotEnoughDataDecoderException();
				}

				if (nextByte == 10) {
					return sb.toString();
				}

				if (nextByte == 45) {
					sb.append('-');
					nextByte = undecodedChunk.readByte();
					if (nextByte == 45) {
						sb.append('-');
						if (undecodedChunk.isReadable()) {
							nextByte = undecodedChunk.readByte();
							if (nextByte == 13) {
								nextByte = undecodedChunk.readByte();
								if (nextByte == 10) {
									return sb.toString();
								}

								undecodedChunk.readerIndex(readerIndex);
								throw new NotEnoughDataDecoderException();
							}

							if (nextByte == 10) {
								return sb.toString();
							}

							undecodedChunk.readerIndex(undecodedChunk.readerIndex() - 1);
							return sb.toString();
						}

						return sb.toString();
					}
				}
			}
		} catch (IndexOutOfBoundsException var7) {
			undecodedChunk.readerIndex(readerIndex);
			throw new NotEnoughDataDecoderException(var7);
		}

		undecodedChunk.readerIndex(readerIndex);
		throw new NotEnoughDataDecoderException();
	}

	private static String readDelimiter(ByteBuf undecodedChunk, String delimiter) {
		if (!undecodedChunk.hasArray()) {
			return readDelimiterStandard(undecodedChunk, delimiter);
		} else {
			SeekAheadOptimize sao = new SeekAheadOptimize(undecodedChunk);
			int readerIndex = undecodedChunk.readerIndex();
			int delimiterPos = 0;
			int len = delimiter.length();

			try {
				StringBuilder sb = new StringBuilder(64);

				while (sao.pos < sao.limit && delimiterPos < len) {
					byte nextByte = sao.bytes[sao.pos++];
					if (nextByte != delimiter.charAt(delimiterPos)) {
						undecodedChunk.readerIndex(readerIndex);
						throw new NotEnoughDataDecoderException();
					}

					delimiterPos++;
					sb.append((char)nextByte);
				}

				if (sao.pos < sao.limit) {
					byte nextByte = sao.bytes[sao.pos++];
					if (nextByte == 13) {
						if (sao.pos < sao.limit) {
							nextByte = sao.bytes[sao.pos++];
							if (nextByte == 10) {
								sao.setReadPosition(0);
								return sb.toString();
							}

							undecodedChunk.readerIndex(readerIndex);
							throw new NotEnoughDataDecoderException();
						}

						undecodedChunk.readerIndex(readerIndex);
						throw new NotEnoughDataDecoderException();
					}

					if (nextByte == 10) {
						sao.setReadPosition(0);
						return sb.toString();
					}

					if (nextByte == 45) {
						sb.append('-');
						if (sao.pos < sao.limit) {
							nextByte = sao.bytes[sao.pos++];
							if (nextByte == 45) {
								sb.append('-');
								if (sao.pos < sao.limit) {
									nextByte = sao.bytes[sao.pos++];
									if (nextByte == 13) {
										if (sao.pos < sao.limit) {
											nextByte = sao.bytes[sao.pos++];
											if (nextByte == 10) {
												sao.setReadPosition(0);
												return sb.toString();
											}

											undecodedChunk.readerIndex(readerIndex);
											throw new NotEnoughDataDecoderException();
										}

										undecodedChunk.readerIndex(readerIndex);
										throw new NotEnoughDataDecoderException();
									}

									if (nextByte == 10) {
										sao.setReadPosition(0);
										return sb.toString();
									}

									sao.setReadPosition(1);
									return sb.toString();
								}

								sao.setReadPosition(0);
								return sb.toString();
							}
						}
					}
				}
			} catch (IndexOutOfBoundsException var8) {
				undecodedChunk.readerIndex(readerIndex);
				throw new NotEnoughDataDecoderException(var8);
			}

			undecodedChunk.readerIndex(readerIndex);
			throw new NotEnoughDataDecoderException();
		}
	}

	private static boolean loadDataMultipartStandard(ByteBuf undecodedChunk, String delimiter, HttpData httpData) {
		int startReaderIndex = undecodedChunk.readerIndex();
		int delimeterLength = delimiter.length();
		int index = 0;
		int lastPosition = startReaderIndex;
		byte prevByte = 10;
		boolean delimiterFound = false;

		while (undecodedChunk.isReadable()) {
			byte nextByte = undecodedChunk.readByte();
			if (prevByte == 10 && nextByte == delimiter.codePointAt(index)) {
				if (delimeterLength == ++index) {
					delimiterFound = true;
					break;
				}
			} else {
				lastPosition = undecodedChunk.readerIndex();
				if (nextByte == 10) {
					index = 0;
					lastPosition -= prevByte == 13 ? 2 : 1;
				}

				prevByte = nextByte;
			}
		}

		if (prevByte == 13) {
			lastPosition--;
		}

		ByteBuf content = undecodedChunk.copy(startReaderIndex, lastPosition - startReaderIndex);

		try {
			httpData.addContent(content, delimiterFound);
		} catch (IOException var11) {
			throw new ErrorDataDecoderException(var11);
		}

		undecodedChunk.readerIndex(lastPosition);
		return delimiterFound;
	}

	private static boolean loadDataMultipart(ByteBuf undecodedChunk, String delimiter, HttpData httpData) {
		if (!undecodedChunk.hasArray()) {
			return loadDataMultipartStandard(undecodedChunk, delimiter, httpData);
		} else {
			SeekAheadOptimize sao = new SeekAheadOptimize(undecodedChunk);
			int startReaderIndex = undecodedChunk.readerIndex();
			int delimeterLength = delimiter.length();
			int index = 0;
			int lastRealPos = sao.pos;
			byte prevByte = 10;
			boolean delimiterFound = false;

			while (sao.pos < sao.limit) {
				byte nextByte = sao.bytes[sao.pos++];
				if (prevByte == 10 && nextByte == delimiter.codePointAt(index)) {
					if (delimeterLength == ++index) {
						delimiterFound = true;
						break;
					}
				} else {
					lastRealPos = sao.pos;
					if (nextByte == 10) {
						index = 0;
						lastRealPos -= prevByte == 13 ? 2 : 1;
					}

					prevByte = nextByte;
				}
			}

			if (prevByte == 13) {
				lastRealPos--;
			}

			int lastPosition = sao.getReadPosition(lastRealPos);
			ByteBuf content = undecodedChunk.copy(startReaderIndex, lastPosition - startReaderIndex);

			try {
				httpData.addContent(content, delimiterFound);
			} catch (IOException var13) {
				throw new ErrorDataDecoderException(var13);
			}

			undecodedChunk.readerIndex(lastPosition);
			return delimiterFound;
		}
	}

	private static String cleanString(String field) {
		int size = field.length();
		StringBuilder sb = new StringBuilder(size);

		for (int i = 0; i < size; i++) {
			char nextChar = field.charAt(i);
			switch (nextChar) {
				case '\t':
				case ',':
				case ':':
				case ';':
				case '=':
					sb.append(' ');
				case '"':
					break;
				default:
					sb.append(nextChar);
			}
		}

		return sb.toString().trim();
	}

	private boolean skipOneLine() {
		if (!this.undecodedChunk.isReadable()) {
			return false;
		} else {
			byte nextByte = this.undecodedChunk.readByte();
			if (nextByte == 13) {
				if (!this.undecodedChunk.isReadable()) {
					this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 1);
					return false;
				} else {
					nextByte = this.undecodedChunk.readByte();
					if (nextByte == 10) {
						return true;
					} else {
						this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 2);
						return false;
					}
				}
			} else if (nextByte == 10) {
				return true;
			} else {
				this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 1);
				return false;
			}
		}
	}

	private static String[] splitMultipartHeader(String sb) {
		ArrayList<String> headers = new ArrayList(1);
		int nameStart = HttpPostBodyUtil.findNonWhitespace(sb, 0);

		int nameEnd;
		for (nameEnd = nameStart; nameEnd < sb.length(); nameEnd++) {
			char ch = sb.charAt(nameEnd);
			if (ch == ':' || Character.isWhitespace(ch)) {
				break;
			}
		}

		int colonEnd;
		for (colonEnd = nameEnd; colonEnd < sb.length(); colonEnd++) {
			if (sb.charAt(colonEnd) == ':') {
				colonEnd++;
				break;
			}
		}

		int valueStart = HttpPostBodyUtil.findNonWhitespace(sb, colonEnd);
		int valueEnd = HttpPostBodyUtil.findEndOfString(sb);
		headers.add(sb.substring(nameStart, nameEnd));
		String svalue = valueStart >= valueEnd ? "" : sb.substring(valueStart, valueEnd);
		String[] values;
		if (svalue.indexOf(59) >= 0) {
			values = splitMultipartHeaderValues(svalue);
		} else {
			values = svalue.split(",");
		}

		for (String value : values) {
			headers.add(value.trim());
		}

		String[] array = new String[headers.size()];

		for (int i = 0; i < headers.size(); i++) {
			array[i] = (String)headers.get(i);
		}

		return array;
	}

	private static String[] splitMultipartHeaderValues(String svalue) {
		List<String> values = InternalThreadLocalMap.get().<String>arrayList(1);
		boolean inQuote = false;
		boolean escapeNext = false;
		int start = 0;

		for (int i = 0; i < svalue.length(); i++) {
			char c = svalue.charAt(i);
			if (inQuote) {
				if (escapeNext) {
					escapeNext = false;
				} else if (c == '\\') {
					escapeNext = true;
				} else if (c == '"') {
					inQuote = false;
				}
			} else if (c == '"') {
				inQuote = true;
			} else if (c == ';') {
				values.add(svalue.substring(start, i));
				start = i + 1;
			}
		}

		values.add(svalue.substring(start));
		return (String[])values.toArray(new String[values.size()]);
	}
}
