package io.netty.handler.codec.redis;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.ReferenceCountUtil;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public final class RedisArrayAggregator extends MessageToMessageDecoder<RedisMessage> {
	private final Deque<RedisArrayAggregator.AggregateState> depths = new ArrayDeque(4);

	protected void decode(ChannelHandlerContext ctx, RedisMessage msg, List<Object> out) throws Exception {
		if (msg instanceof ArrayHeaderRedisMessage) {
			msg = this.decodeRedisArrayHeader((ArrayHeaderRedisMessage)msg);
			if (msg == null) {
				return;
			}
		} else {
			ReferenceCountUtil.retain(msg);
		}

		while (!this.depths.isEmpty()) {
			RedisArrayAggregator.AggregateState current = (RedisArrayAggregator.AggregateState)this.depths.peek();
			current.children.add(msg);
			if (current.children.size() != current.length) {
				return;
			}

			msg = new ArrayRedisMessage(current.children);
			this.depths.pop();
		}

		out.add(msg);
	}

	private RedisMessage decodeRedisArrayHeader(ArrayHeaderRedisMessage header) {
		if (header.isNull()) {
			return ArrayRedisMessage.NULL_INSTANCE;
		} else if (header.length() == 0L) {
			return ArrayRedisMessage.EMPTY_INSTANCE;
		} else if (header.length() > 0L) {
			if (header.length() > 2147483647L) {
				throw new CodecException("this codec doesn't support longer length than 2147483647");
			} else {
				this.depths.push(new RedisArrayAggregator.AggregateState((int)header.length()));
				return null;
			}
		} else {
			throw new CodecException("bad length: " + header.length());
		}
	}

	private static final class AggregateState {
		private final int length;
		private final List<RedisMessage> children;

		AggregateState(int length) {
			this.length = length;
			this.children = new ArrayList(length);
		}
	}
}
