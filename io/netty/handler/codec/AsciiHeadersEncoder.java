package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;
import java.util.Map.Entry;

public final class AsciiHeadersEncoder {
	private final ByteBuf buf;
	private final AsciiHeadersEncoder.SeparatorType separatorType;
	private final AsciiHeadersEncoder.NewlineType newlineType;

	public AsciiHeadersEncoder(ByteBuf buf) {
		this(buf, AsciiHeadersEncoder.SeparatorType.COLON_SPACE, AsciiHeadersEncoder.NewlineType.CRLF);
	}

	public AsciiHeadersEncoder(ByteBuf buf, AsciiHeadersEncoder.SeparatorType separatorType, AsciiHeadersEncoder.NewlineType newlineType) {
		if (buf == null) {
			throw new NullPointerException("buf");
		} else if (separatorType == null) {
			throw new NullPointerException("separatorType");
		} else if (newlineType == null) {
			throw new NullPointerException("newlineType");
		} else {
			this.buf = buf;
			this.separatorType = separatorType;
			this.newlineType = newlineType;
		}
	}

	public void encode(Entry<CharSequence, CharSequence> entry) {
		CharSequence name = (CharSequence)entry.getKey();
		CharSequence value = (CharSequence)entry.getValue();
		ByteBuf buf = this.buf;
		int nameLen = name.length();
		int valueLen = value.length();
		int entryLen = nameLen + valueLen + 4;
		int offset = buf.writerIndex();
		buf.ensureWritable(entryLen);
		writeAscii(buf, offset, name);
		offset += nameLen;
		switch (this.separatorType) {
			case COLON:
				buf.setByte(offset++, 58);
				break;
			case COLON_SPACE:
				buf.setByte(offset++, 58);
				buf.setByte(offset++, 32);
				break;
			default:
				throw new Error();
		}

		writeAscii(buf, offset, value);
		offset += valueLen;
		switch (this.newlineType) {
			case LF:
				buf.setByte(offset++, 10);
				break;
			case CRLF:
				buf.setByte(offset++, 13);
				buf.setByte(offset++, 10);
				break;
			default:
				throw new Error();
		}

		buf.writerIndex(offset);
	}

	private static void writeAscii(ByteBuf buf, int offset, CharSequence value) {
		if (value instanceof AsciiString) {
			ByteBufUtil.copy((AsciiString)value, 0, buf, offset, value.length());
		} else {
			buf.setCharSequence(offset, value, CharsetUtil.US_ASCII);
		}
	}

	public static enum NewlineType {
		LF,
		CRLF;
	}

	public static enum SeparatorType {
		COLON,
		COLON_SPACE;
	}
}
