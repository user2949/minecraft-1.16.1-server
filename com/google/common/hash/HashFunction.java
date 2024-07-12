package com.google.common.hash;

import com.google.common.annotations.Beta;
import java.nio.charset.Charset;

@Beta
public interface HashFunction {
	Hasher newHasher();

	Hasher newHasher(int integer);

	HashCode hashInt(int integer);

	HashCode hashLong(long long1);

	HashCode hashBytes(byte[] arr);

	HashCode hashBytes(byte[] arr, int integer2, int integer3);

	HashCode hashUnencodedChars(CharSequence charSequence);

	HashCode hashString(CharSequence charSequence, Charset charset);

	<T> HashCode hashObject(T object, Funnel<? super T> funnel);

	int bits();
}
