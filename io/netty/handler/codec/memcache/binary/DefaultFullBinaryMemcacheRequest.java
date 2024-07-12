package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class DefaultFullBinaryMemcacheRequest extends DefaultBinaryMemcacheRequest implements FullBinaryMemcacheRequest {
	private final ByteBuf content;

	public DefaultFullBinaryMemcacheRequest(ByteBuf key, ByteBuf extras) {
		this(key, extras, Unpooled.buffer(0));
	}

	public DefaultFullBinaryMemcacheRequest(ByteBuf key, ByteBuf extras, ByteBuf content) {
		super(key, extras);
		if (content == null) {
			throw new NullPointerException("Supplied content is null.");
		} else {
			this.content = content;
			this.setTotalBodyLength(this.keyLength() + this.extrasLength() + content.readableBytes());
		}
	}

	@Override
	public ByteBuf content() {
		return this.content;
	}

	@Override
	public FullBinaryMemcacheRequest retain() {
		super.retain();
		return this;
	}

	@Override
	public FullBinaryMemcacheRequest retain(int increment) {
		super.retain(increment);
		return this;
	}

	@Override
	public FullBinaryMemcacheRequest touch() {
		super.touch();
		return this;
	}

	@Override
	public FullBinaryMemcacheRequest touch(Object hint) {
		super.touch(hint);
		this.content.touch(hint);
		return this;
	}

	@Override
	protected void deallocate() {
		super.deallocate();
		this.content.release();
	}

	@Override
	public FullBinaryMemcacheRequest copy() {
		ByteBuf key = this.key();
		if (key != null) {
			key = key.copy();
		}

		ByteBuf extras = this.extras();
		if (extras != null) {
			extras = extras.copy();
		}

		return new DefaultFullBinaryMemcacheRequest(key, extras, this.content().copy());
	}

	@Override
	public FullBinaryMemcacheRequest duplicate() {
		ByteBuf key = this.key();
		if (key != null) {
			key = key.duplicate();
		}

		ByteBuf extras = this.extras();
		if (extras != null) {
			extras = extras.duplicate();
		}

		return new DefaultFullBinaryMemcacheRequest(key, extras, this.content().duplicate());
	}

	@Override
	public FullBinaryMemcacheRequest retainedDuplicate() {
		return this.replace(this.content().retainedDuplicate());
	}

	@Override
	public FullBinaryMemcacheRequest replace(ByteBuf content) {
		ByteBuf key = this.key();
		if (key != null) {
			key = key.retainedDuplicate();
		}

		ByteBuf extras = this.extras();
		if (extras != null) {
			extras = extras.retainedDuplicate();
		}

		return new DefaultFullBinaryMemcacheRequest(key, extras, content);
	}
}
