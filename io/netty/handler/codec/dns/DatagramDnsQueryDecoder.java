package io.netty.handler.codec.dns;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.internal.ObjectUtil;
import java.util.List;

@Sharable
public class DatagramDnsQueryDecoder extends MessageToMessageDecoder<DatagramPacket> {
	private final DnsRecordDecoder recordDecoder;

	public DatagramDnsQueryDecoder() {
		this(DnsRecordDecoder.DEFAULT);
	}

	public DatagramDnsQueryDecoder(DnsRecordDecoder recordDecoder) {
		this.recordDecoder = ObjectUtil.checkNotNull(recordDecoder, "recordDecoder");
	}

	protected void decode(ChannelHandlerContext ctx, DatagramPacket packet, List<Object> out) throws Exception {
		ByteBuf buf = packet.content();
		DnsQuery query = newQuery(packet, buf);
		boolean success = false;

		try {
			int questionCount = buf.readUnsignedShort();
			int answerCount = buf.readUnsignedShort();
			int authorityRecordCount = buf.readUnsignedShort();
			int additionalRecordCount = buf.readUnsignedShort();
			this.decodeQuestions(query, buf, questionCount);
			this.decodeRecords(query, DnsSection.ANSWER, buf, answerCount);
			this.decodeRecords(query, DnsSection.AUTHORITY, buf, authorityRecordCount);
			this.decodeRecords(query, DnsSection.ADDITIONAL, buf, additionalRecordCount);
			out.add(query);
			success = true;
		} finally {
			if (!success) {
				query.release();
			}
		}
	}

	private static DnsQuery newQuery(DatagramPacket packet, ByteBuf buf) {
		int id = buf.readUnsignedShort();
		int flags = buf.readUnsignedShort();
		if (flags >> 15 == 1) {
			throw new CorruptedFrameException("not a query");
		} else {
			DnsQuery query = new DatagramDnsQuery(packet.sender(), packet.recipient(), id, DnsOpCode.valueOf((byte)(flags >> 11 & 15)));
			query.setRecursionDesired((flags >> 8 & 1) == 1);
			query.setZ(flags >> 4 & 7);
			return query;
		}
	}

	private void decodeQuestions(DnsQuery query, ByteBuf buf, int questionCount) throws Exception {
		for (int i = questionCount; i > 0; i--) {
			query.addRecord(DnsSection.QUESTION, this.recordDecoder.decodeQuestion(buf));
		}
	}

	private void decodeRecords(DnsQuery query, DnsSection section, ByteBuf buf, int count) throws Exception {
		for (int i = count; i > 0; i--) {
			DnsRecord r = this.recordDecoder.decodeRecord(buf);
			if (r == null) {
				break;
			}

			query.addRecord(section, r);
		}
	}
}
