package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import java.io.DataOutput;

@GwtIncompatible
public interface ByteArrayDataOutput extends DataOutput {
	void write(int integer);

	void write(byte[] arr);

	void write(byte[] arr, int integer2, int integer3);

	void writeBoolean(boolean boolean1);

	void writeByte(int integer);

	void writeShort(int integer);

	void writeChar(int integer);

	void writeInt(int integer);

	void writeLong(long long1);

	void writeFloat(float float1);

	void writeDouble(double double1);

	void writeChars(String string);

	void writeUTF(String string);

	@Deprecated
	void writeBytes(String string);

	byte[] toByteArray();
}
