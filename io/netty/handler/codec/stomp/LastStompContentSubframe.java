package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderResult;

public interface LastStompContentSubframe extends StompContentSubframe {
	LastStompContentSubframe EMPTY_LAST_CONTENT = new LastStompContentSubframe() {
		@Override
		public ByteBuf content() {
			return Unpooled.EMPTY_BUFFER;
		}

		@Override
		public LastStompContentSubframe copy() {
			return EMPTY_LAST_CONTENT;
		}

		@Override
		public LastStompContentSubframe duplicate() {
			return this;
		}

		@Override
		public LastStompContentSubframe retainedDuplicate() {
			return this;
		}

		@Override
		public LastStompContentSubframe replace(ByteBuf content) {
			return new DefaultLastStompContentSubframe(content);
		}

		@Override
		public LastStompContentSubframe retain() {
			return this;
		}

		@Override
		public LastStompContentSubframe retain(int increment) {
			return this;
		}

		@Override
		public LastStompContentSubframe touch() {
			return this;
		}

		@Override
		public LastStompContentSubframe touch(Object hint) {
			return this;
		}

		@Override
		public int refCnt() {
			return 1;
		}

		@Override
		public boolean release() {
			return false;
		}

		@Override
		public boolean release(int decrement) {
			return false;
		}

		@Override
		public DecoderResult decoderResult() {
			return DecoderResult.SUCCESS;
		}

		@Override
		public void setDecoderResult(DecoderResult result) {
			throw new UnsupportedOperationException("read only");
		}
	};

	LastStompContentSubframe copy();

	LastStompContentSubframe duplicate();

	LastStompContentSubframe retainedDuplicate();

	LastStompContentSubframe replace(ByteBuf byteBuf);

	LastStompContentSubframe retain();

	LastStompContentSubframe retain(int integer);

	LastStompContentSubframe touch();

	LastStompContentSubframe touch(Object object);
}
