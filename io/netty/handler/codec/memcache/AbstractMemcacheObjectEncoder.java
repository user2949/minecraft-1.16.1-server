package io.netty.handler.codec.memcache;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.FileRegion;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.internal.StringUtil;
import java.util.List;

public abstract class AbstractMemcacheObjectEncoder<M extends MemcacheMessage> extends MessageToMessageEncoder<Object> {
	private boolean expectingMoreContent;

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
		if (msg instanceof MemcacheMessage) {
			if (this.expectingMoreContent) {
				throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(msg));
			}

			M m = (M)msg;
			out.add(this.encodeMessage(ctx, m));
		}

		if (msg instanceof MemcacheContent || msg instanceof ByteBuf || msg instanceof FileRegion) {
			int contentLength = contentLength(msg);
			if (contentLength > 0) {
				out.add(encodeAndRetain(msg));
			} else {
				out.add(Unpooled.EMPTY_BUFFER);
			}

			this.expectingMoreContent = !(msg instanceof LastMemcacheContent);
		}
	}

	@Override
	public boolean acceptOutboundMessage(Object msg) throws Exception {
		return msg instanceof MemcacheObject || msg instanceof ByteBuf || msg instanceof FileRegion;
	}

	protected abstract ByteBuf encodeMessage(ChannelHandlerContext channelHandlerContext, M memcacheMessage);

	private static int contentLength(Object msg) {
		if (msg instanceof MemcacheContent) {
			return ((MemcacheContent)msg).content().readableBytes();
		} else if (msg instanceof ByteBuf) {
			return ((ByteBuf)msg).readableBytes();
		} else if (msg instanceof FileRegion) {
			return (int)((FileRegion)msg).count();
		} else {
			throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(msg));
		}
	}

	private static Object encodeAndRetain(Object msg) {
		if (msg instanceof ByteBuf) {
			return ((ByteBuf)msg).retain();
		} else if (msg instanceof MemcacheContent) {
			return ((MemcacheContent)msg).content().retain();
		} else if (msg instanceof FileRegion) {
			return ((FileRegion)msg).retain();
		} else {
			throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(msg));
		}
	}
}
