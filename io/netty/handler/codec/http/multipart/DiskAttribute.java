package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelException;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import java.io.IOException;
import java.nio.charset.Charset;

public class DiskAttribute extends AbstractDiskHttpData implements Attribute {
	public static String baseDirectory;
	public static boolean deleteOnExitTemporaryFile = true;
	public static final String prefix = "Attr_";
	public static final String postfix = ".att";

	public DiskAttribute(String name) {
		this(name, HttpConstants.DEFAULT_CHARSET);
	}

	public DiskAttribute(String name, long definedSize) {
		this(name, definedSize, HttpConstants.DEFAULT_CHARSET);
	}

	public DiskAttribute(String name, Charset charset) {
		super(name, charset, 0L);
	}

	public DiskAttribute(String name, long definedSize, Charset charset) {
		super(name, charset, definedSize);
	}

	public DiskAttribute(String name, String value) throws IOException {
		this(name, value, HttpConstants.DEFAULT_CHARSET);
	}

	public DiskAttribute(String name, String value, Charset charset) throws IOException {
		super(name, charset, 0L);
		this.setValue(value);
	}

	@Override
	public HttpDataType getHttpDataType() {
		return HttpDataType.Attribute;
	}

	@Override
	public String getValue() throws IOException {
		byte[] bytes = this.get();
		return new String(bytes, this.getCharset());
	}

	@Override
	public void setValue(String value) throws IOException {
		if (value == null) {
			throw new NullPointerException("value");
		} else {
			byte[] bytes = value.getBytes(this.getCharset());
			this.checkSize((long)bytes.length);
			ByteBuf buffer = Unpooled.wrappedBuffer(bytes);
			if (this.definedSize > 0L) {
				this.definedSize = (long)buffer.readableBytes();
			}

			this.setContent(buffer);
		}
	}

	@Override
	public void addContent(ByteBuf buffer, boolean last) throws IOException {
		long newDefinedSize = this.size + (long)buffer.readableBytes();
		this.checkSize(newDefinedSize);
		if (this.definedSize > 0L && this.definedSize < newDefinedSize) {
			this.definedSize = newDefinedSize;
		}

		super.addContent(buffer, last);
	}

	public int hashCode() {
		return this.getName().hashCode();
	}

	public boolean equals(Object o) {
		if (!(o instanceof Attribute)) {
			return false;
		} else {
			Attribute attribute = (Attribute)o;
			return this.getName().equalsIgnoreCase(attribute.getName());
		}
	}

	public int compareTo(InterfaceHttpData o) {
		if (!(o instanceof Attribute)) {
			throw new ClassCastException("Cannot compare " + this.getHttpDataType() + " with " + o.getHttpDataType());
		} else {
			return this.compareTo((Attribute)o);
		}
	}

	public int compareTo(Attribute o) {
		return this.getName().compareToIgnoreCase(o.getName());
	}

	public String toString() {
		try {
			return this.getName() + '=' + this.getValue();
		} catch (IOException var2) {
			return this.getName() + '=' + var2;
		}
	}

	@Override
	protected boolean deleteOnExit() {
		return deleteOnExitTemporaryFile;
	}

	@Override
	protected String getBaseDirectory() {
		return baseDirectory;
	}

	@Override
	protected String getDiskFilename() {
		return this.getName() + ".att";
	}

	@Override
	protected String getPostfix() {
		return ".att";
	}

	@Override
	protected String getPrefix() {
		return "Attr_";
	}

	@Override
	public Attribute copy() {
		ByteBuf content = this.content();
		return this.replace(content != null ? content.copy() : null);
	}

	@Override
	public Attribute duplicate() {
		ByteBuf content = this.content();
		return this.replace(content != null ? content.duplicate() : null);
	}

	@Override
	public Attribute retainedDuplicate() {
		ByteBuf content = this.content();
		if (content != null) {
			content = content.retainedDuplicate();
			boolean success = false;

			Attribute var4;
			try {
				Attribute duplicate = this.replace(content);
				success = true;
				var4 = duplicate;
			} finally {
				if (!success) {
					content.release();
				}
			}

			return var4;
		} else {
			return this.replace(null);
		}
	}

	@Override
	public Attribute replace(ByteBuf content) {
		DiskAttribute attr = new DiskAttribute(this.getName());
		attr.setCharset(this.getCharset());
		if (content != null) {
			try {
				attr.setContent(content);
			} catch (IOException var4) {
				throw new ChannelException(var4);
			}
		}

		return attr;
	}

	@Override
	public Attribute retain(int increment) {
		super.retain(increment);
		return this;
	}

	@Override
	public Attribute retain() {
		super.retain();
		return this;
	}

	@Override
	public Attribute touch() {
		super.touch();
		return this;
	}

	@Override
	public Attribute touch(Object hint) {
		super.touch(hint);
		return this;
	}
}
