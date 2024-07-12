package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public interface HttpData extends InterfaceHttpData, ByteBufHolder {
	long getMaxSize();

	void setMaxSize(long long1);

	void checkSize(long long1) throws IOException;

	void setContent(ByteBuf byteBuf) throws IOException;

	void addContent(ByteBuf byteBuf, boolean boolean2) throws IOException;

	void setContent(File file) throws IOException;

	void setContent(InputStream inputStream) throws IOException;

	boolean isCompleted();

	long length();

	long definedLength();

	void delete();

	byte[] get() throws IOException;

	ByteBuf getByteBuf() throws IOException;

	ByteBuf getChunk(int integer) throws IOException;

	String getString() throws IOException;

	String getString(Charset charset) throws IOException;

	void setCharset(Charset charset);

	Charset getCharset();

	boolean renameTo(File file) throws IOException;

	boolean isInMemory();

	File getFile() throws IOException;

	HttpData copy();

	HttpData duplicate();

	HttpData retainedDuplicate();

	HttpData replace(ByteBuf byteBuf);

	HttpData retain();

	HttpData retain(int integer);

	HttpData touch();

	HttpData touch(Object object);
}
