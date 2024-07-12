package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;

public class DefaultBinaryMemcacheResponse extends AbstractBinaryMemcacheMessage implements BinaryMemcacheResponse {
	public static final byte RESPONSE_MAGIC_BYTE = -127;
	private short status;

	public DefaultBinaryMemcacheResponse() {
		this(null, null);
	}

	public DefaultBinaryMemcacheResponse(ByteBuf key) {
		this(key, null);
	}

	public DefaultBinaryMemcacheResponse(ByteBuf key, ByteBuf extras) {
		super(key, extras);
		this.setMagic((byte)-127);
	}

	@Override
	public short status() {
		return this.status;
	}

	@Override
	public BinaryMemcacheResponse setStatus(short status) {
		this.status = status;
		return this;
	}

	@Override
	public BinaryMemcacheResponse retain() {
		super.retain();
		return this;
	}

	@Override
	public BinaryMemcacheResponse retain(int increment) {
		super.retain(increment);
		return this;
	}

	@Override
	public BinaryMemcacheResponse touch() {
		super.touch();
		return this;
	}

	@Override
	public BinaryMemcacheResponse touch(Object hint) {
		super.touch(hint);
		return this;
	}
}
