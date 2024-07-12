package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http2.HpackUtil.IndexType;
import io.netty.handler.codec.http2.Http2Headers.PseudoHeaderName;
import io.netty.util.AsciiString;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.ThrowableUtil;

final class HpackDecoder {
	private static final Http2Exception DECODE_ULE_128_DECOMPRESSION_EXCEPTION = ThrowableUtil.unknownStackTrace(
		Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - decompression failure"), HpackDecoder.class, "decodeULE128(..)"
	);
	private static final Http2Exception DECODE_ULE_128_TO_LONG_DECOMPRESSION_EXCEPTION = ThrowableUtil.unknownStackTrace(
		Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - long overflow"), HpackDecoder.class, "decodeULE128(..)"
	);
	private static final Http2Exception DECODE_ULE_128_TO_INT_DECOMPRESSION_EXCEPTION = ThrowableUtil.unknownStackTrace(
		Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - int overflow"), HpackDecoder.class, "decodeULE128ToInt(..)"
	);
	private static final Http2Exception DECODE_ILLEGAL_INDEX_VALUE = ThrowableUtil.unknownStackTrace(
		Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - illegal index value"), HpackDecoder.class, "decode(..)"
	);
	private static final Http2Exception INDEX_HEADER_ILLEGAL_INDEX_VALUE = ThrowableUtil.unknownStackTrace(
		Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - illegal index value"), HpackDecoder.class, "indexHeader(..)"
	);
	private static final Http2Exception READ_NAME_ILLEGAL_INDEX_VALUE = ThrowableUtil.unknownStackTrace(
		Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - illegal index value"), HpackDecoder.class, "readName(..)"
	);
	private static final Http2Exception INVALID_MAX_DYNAMIC_TABLE_SIZE = ThrowableUtil.unknownStackTrace(
		Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - invalid max dynamic table size"), HpackDecoder.class, "setDynamicTableSize(..)"
	);
	private static final Http2Exception MAX_DYNAMIC_TABLE_SIZE_CHANGE_REQUIRED = ThrowableUtil.unknownStackTrace(
		Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - max dynamic table size change required"), HpackDecoder.class, "decode(..)"
	);
	private static final byte READ_HEADER_REPRESENTATION = 0;
	private static final byte READ_MAX_DYNAMIC_TABLE_SIZE = 1;
	private static final byte READ_INDEXED_HEADER = 2;
	private static final byte READ_INDEXED_HEADER_NAME = 3;
	private static final byte READ_LITERAL_HEADER_NAME_LENGTH_PREFIX = 4;
	private static final byte READ_LITERAL_HEADER_NAME_LENGTH = 5;
	private static final byte READ_LITERAL_HEADER_NAME = 6;
	private static final byte READ_LITERAL_HEADER_VALUE_LENGTH_PREFIX = 7;
	private static final byte READ_LITERAL_HEADER_VALUE_LENGTH = 8;
	private static final byte READ_LITERAL_HEADER_VALUE = 9;
	private final HpackDynamicTable hpackDynamicTable;
	private final HpackHuffmanDecoder hpackHuffmanDecoder;
	private long maxHeaderListSizeGoAway;
	private long maxHeaderListSize;
	private long maxDynamicTableSize;
	private long encoderMaxDynamicTableSize;
	private boolean maxDynamicTableSizeChangeRequired;

	HpackDecoder(long maxHeaderListSize, int initialHuffmanDecodeCapacity) {
		this(maxHeaderListSize, initialHuffmanDecodeCapacity, 4096);
	}

	HpackDecoder(long maxHeaderListSize, int initialHuffmanDecodeCapacity, int maxHeaderTableSize) {
		this.maxHeaderListSize = ObjectUtil.checkPositive(maxHeaderListSize, "maxHeaderListSize");
		this.maxHeaderListSizeGoAway = Http2CodecUtil.calculateMaxHeaderListSizeGoAway(maxHeaderListSize);
		this.maxDynamicTableSize = this.encoderMaxDynamicTableSize = (long)maxHeaderTableSize;
		this.maxDynamicTableSizeChangeRequired = false;
		this.hpackDynamicTable = new HpackDynamicTable((long)maxHeaderTableSize);
		this.hpackHuffmanDecoder = new HpackHuffmanDecoder(initialHuffmanDecodeCapacity);
	}

	public void decode(int streamId, ByteBuf in, Http2Headers headers, boolean validateHeaders) throws Http2Exception {
		int index = 0;
		long headersLength = 0L;
		int nameLength = 0;
		int valueLength = 0;
		byte state = 0;
		boolean huffmanEncoded = false;
		CharSequence name = null;
		HpackDecoder.HeaderType headerType = null;
		IndexType indexType = IndexType.NONE;

		while (in.isReadable()) {
			switch (state) {
				case 0:
					byte bx = in.readByte();
					if (this.maxDynamicTableSizeChangeRequired && (bx & 224) != 32) {
						throw MAX_DYNAMIC_TABLE_SIZE_CHANGE_REQUIRED;
					}

					if (bx < 0) {
						index = bx & 127;
						switch (index) {
							case 0:
								throw DECODE_ILLEGAL_INDEX_VALUE;
							case 127:
								state = 2;
								continue;
							default:
								HpackHeaderField indexedHeaderx = this.getIndexedHeader(index);
								headerType = this.validate(indexedHeaderx.name, headerType, validateHeaders);
								headersLength = this.addHeader(headers, indexedHeaderx.name, indexedHeaderx.value, headersLength);
						}
					} else if ((bx & 64) == 64) {
						indexType = IndexType.INCREMENTAL;
						index = bx & 63;
						switch (index) {
							case 0:
								state = 4;
								continue;
							case 63:
								state = 3;
								continue;
							default:
								name = this.readName(index);
								headerType = this.validate(name, headerType, validateHeaders);
								nameLength = name.length();
								state = 7;
						}
					} else if ((bx & 32) == 32) {
						index = bx & 31;
						if (index == 31) {
							state = 1;
						} else {
							this.setDynamicTableSize((long)index);
							state = 0;
						}
					} else {
						indexType = (bx & 16) == 16 ? IndexType.NEVER : IndexType.NONE;
						index = bx & 15;
						switch (index) {
							case 0:
								state = 4;
								continue;
							case 15:
								state = 3;
								continue;
							default:
								name = this.readName(index);
								headerType = this.validate(name, headerType, validateHeaders);
								nameLength = name.length();
								state = 7;
						}
					}
					break;
				case 1:
					this.setDynamicTableSize(decodeULE128(in, (long)index));
					state = 0;
					break;
				case 2:
					HpackHeaderField indexedHeader = this.getIndexedHeader(decodeULE128(in, index));
					headerType = this.validate(indexedHeader.name, headerType, validateHeaders);
					headersLength = this.addHeader(headers, indexedHeader.name, indexedHeader.value, headersLength);
					state = 0;
					break;
				case 3:
					name = this.readName(decodeULE128(in, index));
					headerType = this.validate(name, headerType, validateHeaders);
					nameLength = name.length();
					state = 7;
					break;
				case 4:
					byte b = in.readByte();
					huffmanEncoded = (b & 128) == 128;
					index = b & 127;
					if (index == 127) {
						state = 5;
					} else {
						if ((long)index > this.maxHeaderListSizeGoAway - headersLength) {
							Http2CodecUtil.headerListSizeExceeded(this.maxHeaderListSizeGoAway);
						}

						nameLength = index;
						state = 6;
					}
					break;
				case 5:
					nameLength = decodeULE128(in, index);
					if ((long)nameLength > this.maxHeaderListSizeGoAway - headersLength) {
						Http2CodecUtil.headerListSizeExceeded(this.maxHeaderListSizeGoAway);
					}

					state = 6;
					break;
				case 6:
					if (in.readableBytes() < nameLength) {
						throw notEnoughDataException(in);
					}

					name = this.readStringLiteral(in, nameLength, huffmanEncoded);
					headerType = this.validate(name, headerType, validateHeaders);
					state = 7;
					break;
				case 7:
					byte b = in.readByte();
					huffmanEncoded = (b & 128) == 128;
					index = b & 127;
					switch (index) {
						case 0:
							headerType = this.validate(name, headerType, validateHeaders);
							headersLength = this.insertHeader(headers, name, AsciiString.EMPTY_STRING, indexType, headersLength);
							state = 0;
							continue;
						case 127:
							state = 8;
							continue;
						default:
							if ((long)index + (long)nameLength > this.maxHeaderListSizeGoAway - headersLength) {
								Http2CodecUtil.headerListSizeExceeded(this.maxHeaderListSizeGoAway);
							}

							valueLength = index;
							state = 9;
							continue;
					}
				case 8:
					valueLength = decodeULE128(in, index);
					if ((long)valueLength + (long)nameLength > this.maxHeaderListSizeGoAway - headersLength) {
						Http2CodecUtil.headerListSizeExceeded(this.maxHeaderListSizeGoAway);
					}

					state = 9;
					break;
				case 9:
					if (in.readableBytes() < valueLength) {
						throw notEnoughDataException(in);
					}

					CharSequence value = this.readStringLiteral(in, valueLength, huffmanEncoded);
					headerType = this.validate(name, headerType, validateHeaders);
					headersLength = this.insertHeader(headers, name, value, indexType, headersLength);
					state = 0;
					break;
				default:
					throw new Error("should not reach here state: " + state);
			}
		}

		if (headersLength > this.maxHeaderListSize) {
			Http2CodecUtil.headerListSizeExceeded(streamId, this.maxHeaderListSize, true);
		}

		if (state != 0) {
			throw Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "Incomplete header block fragment.");
		}
	}

	public void setMaxHeaderTableSize(long maxHeaderTableSize) throws Http2Exception {
		if (maxHeaderTableSize >= 0L && maxHeaderTableSize <= 4294967295L) {
			this.maxDynamicTableSize = maxHeaderTableSize;
			if (this.maxDynamicTableSize < this.encoderMaxDynamicTableSize) {
				this.maxDynamicTableSizeChangeRequired = true;
				this.hpackDynamicTable.setCapacity(this.maxDynamicTableSize);
			}
		} else {
			throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Header Table Size must be >= %d and <= %d but was %d", 0L, 4294967295L, maxHeaderTableSize);
		}
	}

	public void setMaxHeaderListSize(long maxHeaderListSize, long maxHeaderListSizeGoAway) throws Http2Exception {
		if (maxHeaderListSizeGoAway < maxHeaderListSize || maxHeaderListSizeGoAway < 0L) {
			throw Http2Exception.connectionError(
				Http2Error.INTERNAL_ERROR, "Header List Size GO_AWAY %d must be positive and >= %d", maxHeaderListSizeGoAway, maxHeaderListSize
			);
		} else if (maxHeaderListSize >= 0L && maxHeaderListSize <= 4294967295L) {
			this.maxHeaderListSize = maxHeaderListSize;
			this.maxHeaderListSizeGoAway = maxHeaderListSizeGoAway;
		} else {
			throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Header List Size must be >= %d and <= %d but was %d", 0L, 4294967295L, maxHeaderListSize);
		}
	}

	public long getMaxHeaderListSize() {
		return this.maxHeaderListSize;
	}

	public long getMaxHeaderListSizeGoAway() {
		return this.maxHeaderListSizeGoAway;
	}

	public long getMaxHeaderTableSize() {
		return this.hpackDynamicTable.capacity();
	}

	int length() {
		return this.hpackDynamicTable.length();
	}

	long size() {
		return this.hpackDynamicTable.size();
	}

	HpackHeaderField getHeaderField(int index) {
		return this.hpackDynamicTable.getEntry(index + 1);
	}

	private void setDynamicTableSize(long dynamicTableSize) throws Http2Exception {
		if (dynamicTableSize > this.maxDynamicTableSize) {
			throw INVALID_MAX_DYNAMIC_TABLE_SIZE;
		} else {
			this.encoderMaxDynamicTableSize = dynamicTableSize;
			this.maxDynamicTableSizeChangeRequired = false;
			this.hpackDynamicTable.setCapacity(dynamicTableSize);
		}
	}

	private HpackDecoder.HeaderType validate(CharSequence name, HpackDecoder.HeaderType previousHeaderType, boolean validateHeaders) throws Http2Exception {
		if (!validateHeaders) {
			return null;
		} else if (PseudoHeaderName.hasPseudoHeaderFormat(name)) {
			if (previousHeaderType == HpackDecoder.HeaderType.REGULAR_HEADER) {
				throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Pseudo-header field '%s' found after regular header.", name);
			} else {
				PseudoHeaderName pseudoHeader = PseudoHeaderName.getPseudoHeader(name);
				if (pseudoHeader == null) {
					throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Invalid HTTP/2 pseudo-header '%s' encountered.", name);
				} else {
					HpackDecoder.HeaderType currentHeaderType = pseudoHeader.isRequestOnly()
						? HpackDecoder.HeaderType.REQUEST_PSEUDO_HEADER
						: HpackDecoder.HeaderType.RESPONSE_PSEUDO_HEADER;
					if (previousHeaderType != null && currentHeaderType != previousHeaderType) {
						throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Mix of request and response pseudo-headers.");
					} else {
						return currentHeaderType;
					}
				}
			}
		} else {
			return HpackDecoder.HeaderType.REGULAR_HEADER;
		}
	}

	private CharSequence readName(int index) throws Http2Exception {
		if (index <= HpackStaticTable.length) {
			HpackHeaderField hpackHeaderField = HpackStaticTable.getEntry(index);
			return hpackHeaderField.name;
		} else if (index - HpackStaticTable.length <= this.hpackDynamicTable.length()) {
			HpackHeaderField hpackHeaderField = this.hpackDynamicTable.getEntry(index - HpackStaticTable.length);
			return hpackHeaderField.name;
		} else {
			throw READ_NAME_ILLEGAL_INDEX_VALUE;
		}
	}

	private HpackHeaderField getIndexedHeader(int index) throws Http2Exception {
		if (index <= HpackStaticTable.length) {
			return HpackStaticTable.getEntry(index);
		} else if (index - HpackStaticTable.length <= this.hpackDynamicTable.length()) {
			return this.hpackDynamicTable.getEntry(index - HpackStaticTable.length);
		} else {
			throw INDEX_HEADER_ILLEGAL_INDEX_VALUE;
		}
	}

	private long insertHeader(Http2Headers headers, CharSequence name, CharSequence value, IndexType indexType, long headerSize) throws Http2Exception {
		headerSize = this.addHeader(headers, name, value, headerSize);
		switch (indexType) {
			case INCREMENTAL:
				this.hpackDynamicTable.add(new HpackHeaderField(name, value));
			case NONE:
			case NEVER:
				return headerSize;
			default:
				throw new Error("should not reach here");
		}
	}

	private long addHeader(Http2Headers headers, CharSequence name, CharSequence value, long headersLength) throws Http2Exception {
		headersLength += HpackHeaderField.sizeOf(name, value);
		if (headersLength > this.maxHeaderListSizeGoAway) {
			Http2CodecUtil.headerListSizeExceeded(this.maxHeaderListSizeGoAway);
		}

		headers.add(name, value);
		return headersLength;
	}

	private CharSequence readStringLiteral(ByteBuf in, int length, boolean huffmanEncoded) throws Http2Exception {
		if (huffmanEncoded) {
			return this.hpackHuffmanDecoder.decode(in, length);
		} else {
			byte[] buf = new byte[length];
			in.readBytes(buf);
			return new AsciiString(buf, false);
		}
	}

	private static IllegalArgumentException notEnoughDataException(ByteBuf in) {
		return new IllegalArgumentException("decode only works with an entire header block! " + in);
	}

	static int decodeULE128(ByteBuf in, int result) throws Http2Exception {
		int readerIndex = in.readerIndex();
		long v = decodeULE128(in, (long)result);
		if (v > 2147483647L) {
			in.readerIndex(readerIndex);
			throw DECODE_ULE_128_TO_INT_DECOMPRESSION_EXCEPTION;
		} else {
			return (int)v;
		}
	}

	static long decodeULE128(ByteBuf in, long result) throws Http2Exception {
		assert result <= 127L && result >= 0L;

		boolean resultStartedAtZero = result == 0L;
		int writerIndex = in.writerIndex();
		int readerIndex = in.readerIndex();

		for (int shift = 0; readerIndex < writerIndex; shift += 7) {
			byte b = in.getByte(readerIndex);
			if (shift == 56 && ((b & 128) != 0 || b == 127 && !resultStartedAtZero)) {
				throw DECODE_ULE_128_TO_LONG_DECOMPRESSION_EXCEPTION;
			}

			if ((b & 128) == 0) {
				in.readerIndex(readerIndex + 1);
				return result + (((long)b & 127L) << shift);
			}

			result += ((long)b & 127L) << shift;
			readerIndex++;
		}

		throw DECODE_ULE_128_DECOMPRESSION_EXCEPTION;
	}

	private static enum HeaderType {
		REGULAR_HEADER,
		REQUEST_PSEUDO_HEADER,
		RESPONSE_PSEUDO_HEADER;
	}
}
