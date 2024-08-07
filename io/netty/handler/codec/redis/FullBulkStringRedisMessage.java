package io.netty.handler.codec.redis;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.DefaultByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.util.internal.StringUtil;

public class FullBulkStringRedisMessage extends DefaultByteBufHolder implements LastBulkStringRedisContent {
	public static final FullBulkStringRedisMessage NULL_INSTANCE = new FullBulkStringRedisMessage() {
		@Override
		public boolean isNull() {
			return true;
		}

		@Override
		public ByteBuf content() {
			return Unpooled.EMPTY_BUFFER;
		}

		@Override
		public FullBulkStringRedisMessage copy() {
			return this;
		}

		@Override
		public FullBulkStringRedisMessage duplicate() {
			return this;
		}

		@Override
		public FullBulkStringRedisMessage retainedDuplicate() {
			return this;
		}

		@Override
		public int refCnt() {
			return 1;
		}

		@Override
		public FullBulkStringRedisMessage retain() {
			return this;
		}

		@Override
		public FullBulkStringRedisMessage retain(int increment) {
			return this;
		}

		@Override
		public FullBulkStringRedisMessage touch() {
			return this;
		}

		@Override
		public FullBulkStringRedisMessage touch(Object hint) {
			return this;
		}

		@Override
		public boolean release() {
			return false;
		}

		@Override
		public boolean release(int decrement) {
			return false;
		}
	};
	public static final FullBulkStringRedisMessage EMPTY_INSTANCE = new FullBulkStringRedisMessage() {
		@Override
		public ByteBuf content() {
			return Unpooled.EMPTY_BUFFER;
		}

		@Override
		public FullBulkStringRedisMessage copy() {
			return this;
		}

		@Override
		public FullBulkStringRedisMessage duplicate() {
			return this;
		}

		@Override
		public FullBulkStringRedisMessage retainedDuplicate() {
			return this;
		}

		@Override
		public int refCnt() {
			return 1;
		}

		@Override
		public FullBulkStringRedisMessage retain() {
			return this;
		}

		@Override
		public FullBulkStringRedisMessage retain(int increment) {
			return this;
		}

		@Override
		public FullBulkStringRedisMessage touch() {
			return this;
		}

		@Override
		public FullBulkStringRedisMessage touch(Object hint) {
			return this;
		}

		@Override
		public boolean release() {
			return false;
		}

		@Override
		public boolean release(int decrement) {
			return false;
		}
	};

	private FullBulkStringRedisMessage() {
		this(Unpooled.EMPTY_BUFFER);
	}

	public FullBulkStringRedisMessage(ByteBuf content) {
		super(content);
	}

	public boolean isNull() {
		return false;
	}

	@Override
	public String toString() {
		return StringUtil.simpleClassName(this) + '[' + "content=" + this.content() + ']';
	}

	public FullBulkStringRedisMessage copy() {
		return (FullBulkStringRedisMessage)super.copy();
	}

	public FullBulkStringRedisMessage duplicate() {
		return (FullBulkStringRedisMessage)super.duplicate();
	}

	public FullBulkStringRedisMessage retainedDuplicate() {
		return (FullBulkStringRedisMessage)super.retainedDuplicate();
	}

	public FullBulkStringRedisMessage replace(ByteBuf content) {
		return new FullBulkStringRedisMessage(content);
	}

	public FullBulkStringRedisMessage retain() {
		super.retain();
		return this;
	}

	public FullBulkStringRedisMessage retain(int increment) {
		super.retain(increment);
		return this;
	}

	public FullBulkStringRedisMessage touch() {
		super.touch();
		return this;
	}

	public FullBulkStringRedisMessage touch(Object hint) {
		super.touch(hint);
		return this;
	}
}
