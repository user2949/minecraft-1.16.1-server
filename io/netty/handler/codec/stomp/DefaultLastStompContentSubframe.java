package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;

public class DefaultLastStompContentSubframe extends DefaultStompContentSubframe implements LastStompContentSubframe {
	public DefaultLastStompContentSubframe(ByteBuf content) {
		super(content);
	}

	@Override
	public LastStompContentSubframe copy() {
		return (LastStompContentSubframe)super.copy();
	}

	@Override
	public LastStompContentSubframe duplicate() {
		return (LastStompContentSubframe)super.duplicate();
	}

	@Override
	public LastStompContentSubframe retainedDuplicate() {
		return (LastStompContentSubframe)super.retainedDuplicate();
	}

	@Override
	public LastStompContentSubframe replace(ByteBuf content) {
		return new DefaultLastStompContentSubframe(content);
	}

	public DefaultLastStompContentSubframe retain() {
		super.retain();
		return this;
	}

	@Override
	public LastStompContentSubframe retain(int increment) {
		super.retain(increment);
		return this;
	}

	@Override
	public LastStompContentSubframe touch() {
		super.touch();
		return this;
	}

	@Override
	public LastStompContentSubframe touch(Object hint) {
		super.touch(hint);
		return this;
	}

	@Override
	public String toString() {
		return "DefaultLastStompContent{decoderResult=" + this.decoderResult() + '}';
	}
}
