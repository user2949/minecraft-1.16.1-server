package io.netty.handler.codec.dns;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.util.CharsetUtil;

public class DefaultDnsRecordDecoder implements DnsRecordDecoder {
	static final String ROOT = ".";

	protected DefaultDnsRecordDecoder() {
	}

	@Override
	public final DnsQuestion decodeQuestion(ByteBuf in) throws Exception {
		String name = decodeName(in);
		DnsRecordType type = DnsRecordType.valueOf(in.readUnsignedShort());
		int qClass = in.readUnsignedShort();
		return new DefaultDnsQuestion(name, type, qClass);
	}

	@Override
	public final <T extends DnsRecord> T decodeRecord(ByteBuf in) throws Exception {
		int startOffset = in.readerIndex();
		String name = decodeName(in);
		int endOffset = in.writerIndex();
		if (endOffset - startOffset < 10) {
			in.readerIndex(startOffset);
			return null;
		} else {
			DnsRecordType type = DnsRecordType.valueOf(in.readUnsignedShort());
			int aClass = in.readUnsignedShort();
			long ttl = in.readUnsignedInt();
			int length = in.readUnsignedShort();
			int offset = in.readerIndex();
			if (endOffset - offset < length) {
				in.readerIndex(startOffset);
				return null;
			} else {
				T record = (T)this.decodeRecord(name, type, aClass, ttl, in, offset, length);
				in.readerIndex(offset + length);
				return record;
			}
		}
	}

	protected DnsRecord decodeRecord(String name, DnsRecordType type, int dnsClass, long timeToLive, ByteBuf in, int offset, int length) throws Exception {
		return (DnsRecord)(type == DnsRecordType.PTR
			? new DefaultDnsPtrRecord(name, dnsClass, timeToLive, this.decodeName0(in.duplicate().setIndex(offset, offset + length)))
			: new DefaultDnsRawRecord(name, type, dnsClass, timeToLive, in.retainedDuplicate().setIndex(offset, offset + length)));
	}

	protected String decodeName0(ByteBuf in) {
		return decodeName(in);
	}

	public static String decodeName(ByteBuf in) {
		int position = -1;
		int checked = 0;
		int end = in.writerIndex();
		int readable = in.readableBytes();
		if (readable == 0) {
			return ".";
		} else {
			StringBuilder name = new StringBuilder(readable << 1);

			while (in.isReadable()) {
				int len = in.readUnsignedByte();
				boolean pointer = (len & 192) == 192;
				if (pointer) {
					if (position == -1) {
						position = in.readerIndex() + 1;
					}

					if (!in.isReadable()) {
						throw new CorruptedFrameException("truncated pointer in a name");
					}

					int next = (len & 63) << 8 | in.readUnsignedByte();
					if (next >= end) {
						throw new CorruptedFrameException("name has an out-of-range pointer");
					}

					in.readerIndex(next);
					checked += 2;
					if (checked >= end) {
						throw new CorruptedFrameException("name contains a loop.");
					}
				} else {
					if (len == 0) {
						break;
					}

					if (!in.isReadable(len)) {
						throw new CorruptedFrameException("truncated label in a name");
					}

					name.append(in.toString(in.readerIndex(), len, CharsetUtil.UTF_8)).append('.');
					in.skipBytes(len);
				}
			}

			if (position != -1) {
				in.readerIndex(position);
			}

			if (name.length() == 0) {
				return ".";
			} else {
				if (name.charAt(name.length() - 1) != '.') {
					name.append('.');
				}

				return name.toString();
			}
		}
	}
}
