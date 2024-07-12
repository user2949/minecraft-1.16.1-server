package io.netty.handler.codec.redis;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public interface LastBulkStringRedisContent extends BulkStringRedisContent {
	LastBulkStringRedisContent EMPTY_LAST_CONTENT = new LastBulkStringRedisContent() {
		@Override
		public ByteBuf content() {
			return Unpooled.EMPTY_BUFFER;
		}

		@Override
		public LastBulkStringRedisContent copy() {
			return this;
		}

		@Override
		public LastBulkStringRedisContent duplicate() {
			return this;
		}

		@Override
		public LastBulkStringRedisContent retainedDuplicate() {
			return this;
		}

		@Override
		public LastBulkStringRedisContent replace(ByteBuf content) {
			return new DefaultLastBulkStringRedisContent(content);
		}

		@Override
		public LastBulkStringRedisContent retain(int increment) {
			return this;
		}

		@Override
		public LastBulkStringRedisContent retain() {
			return this;
		}

		@Override
		public int refCnt() {
			return 1;
		}

		@Override
		public LastBulkStringRedisContent touch() {
			return this;
		}

		@Override
		public LastBulkStringRedisContent touch(Object hint) {
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

	LastBulkStringRedisContent copy();

	LastBulkStringRedisContent duplicate();

	LastBulkStringRedisContent retainedDuplicate();

	LastBulkStringRedisContent replace(ByteBuf byteBuf);

	LastBulkStringRedisContent retain();

	LastBulkStringRedisContent retain(int integer);

	LastBulkStringRedisContent touch();

	LastBulkStringRedisContent touch(Object object);
}
