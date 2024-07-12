package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.nio.charset.Charset;

@Beta
@CanIgnoreReturnValue
public interface Hasher extends PrimitiveSink {
	Hasher putByte(byte byte1);

	Hasher putBytes(byte[] arr);

	Hasher putBytes(byte[] arr, int integer2, int integer3);

	Hasher putShort(short short1);

	Hasher putInt(int integer);

	Hasher putLong(long long1);

	Hasher putFloat(float float1);

	Hasher putDouble(double double1);

	Hasher putBoolean(boolean boolean1);

	Hasher putChar(char character);

	Hasher putUnencodedChars(CharSequence charSequence);

	Hasher putString(CharSequence charSequence, Charset charset);

	<T> Hasher putObject(T object, Funnel<? super T> funnel);

	HashCode hash();

	@Deprecated
	int hashCode();
}
