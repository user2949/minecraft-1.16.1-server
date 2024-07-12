package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import io.netty.util.AbstractReferenceCounted;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

final class InternalAttribute extends AbstractReferenceCounted implements InterfaceHttpData {
	private final List<ByteBuf> value = new ArrayList();
	private final Charset charset;
	private int size;

	InternalAttribute(Charset charset) {
		this.charset = charset;
	}

	@Override
	public HttpDataType getHttpDataType() {
		return HttpDataType.InternalAttribute;
	}

	public void addValue(String value) {
		if (value == null) {
			throw new NullPointerException("value");
		} else {
			ByteBuf buf = Unpooled.copiedBuffer(value, this.charset);
			this.value.add(buf);
			this.size = this.size + buf.readableBytes();
		}
	}

	public void addValue(String value, int rank) {
		if (value == null) {
			throw new NullPointerException("value");
		} else {
			ByteBuf buf = Unpooled.copiedBuffer(value, this.charset);
			this.value.add(rank, buf);
			this.size = this.size + buf.readableBytes();
		}
	}

	public void setValue(String value, int rank) {
		if (value == null) {
			throw new NullPointerException("value");
		} else {
			ByteBuf buf = Unpooled.copiedBuffer(value, this.charset);
			ByteBuf old = (ByteBuf)this.value.set(rank, buf);
			if (old != null) {
				this.size = this.size - old.readableBytes();
				old.release();
			}

			this.size = this.size + buf.readableBytes();
		}
	}

	public int hashCode() {
		return this.getName().hashCode();
	}

	public boolean equals(Object o) {
		if (!(o instanceof InternalAttribute)) {
			return false;
		} else {
			InternalAttribute attribute = (InternalAttribute)o;
			return this.getName().equalsIgnoreCase(attribute.getName());
		}
	}

	public int compareTo(InterfaceHttpData o) {
		if (!(o instanceof InternalAttribute)) {
			throw new ClassCastException("Cannot compare " + this.getHttpDataType() + " with " + o.getHttpDataType());
		} else {
			return this.compareTo((InternalAttribute)o);
		}
	}

	public int compareTo(InternalAttribute o) {
		return this.getName().compareToIgnoreCase(o.getName());
	}

	public String toString() {
		StringBuilder result = new StringBuilder();

		for (ByteBuf elt : this.value) {
			result.append(elt.toString(this.charset));
		}

		return result.toString();
	}

	public int size() {
		return this.size;
	}

	public ByteBuf toByteBuf() {
		return Unpooled.compositeBuffer().addComponents(this.value).writerIndex(this.size()).readerIndex(0);
	}

	@Override
	public String getName() {
		return "InternalAttribute";
	}

	@Override
	protected void deallocate() {
	}

	@Override
	public InterfaceHttpData retain() {
		for (ByteBuf buf : this.value) {
			buf.retain();
		}

		return this;
	}

	@Override
	public InterfaceHttpData retain(int increment) {
		for (ByteBuf buf : this.value) {
			buf.retain(increment);
		}

		return this;
	}

	@Override
	public InterfaceHttpData touch() {
		for (ByteBuf buf : this.value) {
			buf.touch();
		}

		return this;
	}

	@Override
	public InterfaceHttpData touch(Object hint) {
		for (ByteBuf buf : this.value) {
			buf.touch(hint);
		}

		return this;
	}
}
