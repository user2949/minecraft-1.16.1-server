package io.netty.handler.codec.smtp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public interface LastSmtpContent extends SmtpContent {
	LastSmtpContent EMPTY_LAST_CONTENT = new LastSmtpContent() {
		@Override
		public LastSmtpContent copy() {
			return this;
		}

		@Override
		public LastSmtpContent duplicate() {
			return this;
		}

		@Override
		public LastSmtpContent retainedDuplicate() {
			return this;
		}

		@Override
		public LastSmtpContent replace(ByteBuf content) {
			return new DefaultLastSmtpContent(content);
		}

		@Override
		public LastSmtpContent retain() {
			return this;
		}

		@Override
		public LastSmtpContent retain(int increment) {
			return this;
		}

		@Override
		public LastSmtpContent touch() {
			return this;
		}

		@Override
		public LastSmtpContent touch(Object hint) {
			return this;
		}

		@Override
		public ByteBuf content() {
			return Unpooled.EMPTY_BUFFER;
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
	};

	LastSmtpContent copy();

	LastSmtpContent duplicate();

	LastSmtpContent retainedDuplicate();

	LastSmtpContent replace(ByteBuf byteBuf);

	LastSmtpContent retain();

	LastSmtpContent retain(int integer);

	LastSmtpContent touch();

	LastSmtpContent touch(Object object);
}
