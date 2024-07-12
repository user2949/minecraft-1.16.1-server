package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import java.io.IOException;

public interface Attribute extends HttpData {
	String getValue() throws IOException;

	void setValue(String string) throws IOException;

	Attribute copy();

	Attribute duplicate();

	Attribute retainedDuplicate();

	Attribute replace(ByteBuf byteBuf);

	Attribute retain();

	Attribute retain(int integer);

	Attribute touch();

	Attribute touch(Object object);
}
