package io.netty.handler.codec.sctp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.sctp.SctpMessage;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SctpMessageCompletionHandler extends MessageToMessageDecoder<SctpMessage> {
	private final Map<Integer, ByteBuf> fragments = new HashMap();

	protected void decode(ChannelHandlerContext ctx, SctpMessage msg, List<Object> out) throws Exception {
		ByteBuf byteBuf = msg.content();
		int protocolIdentifier = msg.protocolIdentifier();
		int streamIdentifier = msg.streamIdentifier();
		boolean isComplete = msg.isComplete();
		boolean isUnordered = msg.isUnordered();
		ByteBuf frag = (ByteBuf)this.fragments.remove(streamIdentifier);
		if (frag == null) {
			frag = Unpooled.EMPTY_BUFFER;
		}

		if (isComplete && !frag.isReadable()) {
			out.add(msg);
		} else if (!isComplete && frag.isReadable()) {
			this.fragments.put(streamIdentifier, Unpooled.wrappedBuffer(frag, byteBuf));
		} else if (isComplete && frag.isReadable()) {
			SctpMessage assembledMsg = new SctpMessage(protocolIdentifier, streamIdentifier, isUnordered, Unpooled.wrappedBuffer(frag, byteBuf));
			out.add(assembledMsg);
		} else {
			this.fragments.put(streamIdentifier, byteBuf);
		}

		byteBuf.retain();
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		for (ByteBuf buffer : this.fragments.values()) {
			buffer.release();
		}

		this.fragments.clear();
		super.handlerRemoved(ctx);
	}
}
