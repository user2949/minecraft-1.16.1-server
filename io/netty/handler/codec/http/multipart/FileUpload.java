package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;

public interface FileUpload extends HttpData {
	String getFilename();

	void setFilename(String string);

	void setContentType(String string);

	String getContentType();

	void setContentTransferEncoding(String string);

	String getContentTransferEncoding();

	FileUpload copy();

	FileUpload duplicate();

	FileUpload retainedDuplicate();

	FileUpload replace(ByteBuf byteBuf);

	FileUpload retain();

	FileUpload retain(int integer);

	FileUpload touch();

	FileUpload touch(Object object);
}
