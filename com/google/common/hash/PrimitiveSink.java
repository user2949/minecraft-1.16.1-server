package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.nio.charset.Charset;

@Beta
@CanIgnoreReturnValue
public interface PrimitiveSink {
	PrimitiveSink putByte(byte byte1);

	PrimitiveSink putBytes(byte[] arr);

	PrimitiveSink putBytes(byte[] arr, int integer2, int integer3);

	PrimitiveSink putShort(short short1);

	PrimitiveSink putInt(int integer);

	PrimitiveSink putLong(long long1);

	PrimitiveSink putFloat(float float1);

	PrimitiveSink putDouble(double double1);

	PrimitiveSink putBoolean(boolean boolean1);

	PrimitiveSink putChar(char character);

	PrimitiveSink putUnencodedChars(CharSequence charSequence);

	PrimitiveSink putString(CharSequence charSequence, Charset charset);
}
