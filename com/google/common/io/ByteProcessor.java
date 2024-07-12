package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;

@Beta
@GwtIncompatible
public interface ByteProcessor<T> {
	@CanIgnoreReturnValue
	boolean processBytes(byte[] arr, int integer2, int integer3) throws IOException;

	T getResult();
}
