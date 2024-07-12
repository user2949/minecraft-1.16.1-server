package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.DataInput;

@GwtIncompatible
public interface ByteArrayDataInput extends DataInput {
	void readFully(byte[] arr);

	void readFully(byte[] arr, int integer2, int integer3);

	int skipBytes(int integer);

	@CanIgnoreReturnValue
	boolean readBoolean();

	@CanIgnoreReturnValue
	byte readByte();

	@CanIgnoreReturnValue
	int readUnsignedByte();

	@CanIgnoreReturnValue
	short readShort();

	@CanIgnoreReturnValue
	int readUnsignedShort();

	@CanIgnoreReturnValue
	char readChar();

	@CanIgnoreReturnValue
	int readInt();

	@CanIgnoreReturnValue
	long readLong();

	@CanIgnoreReturnValue
	float readFloat();

	@CanIgnoreReturnValue
	double readDouble();

	@CanIgnoreReturnValue
	String readLine();

	@CanIgnoreReturnValue
	String readUTF();
}
