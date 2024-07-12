package io.netty.util.internal;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

final class CleanerJava9 implements Cleaner {
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(CleanerJava9.class);
	private static final Method INVOKE_CLEANER;

	static boolean isSupported() {
		return INVOKE_CLEANER != null;
	}

	@Override
	public void freeDirectBuffer(ByteBuffer buffer) {
		try {
			INVOKE_CLEANER.invoke(PlatformDependent0.UNSAFE, buffer);
		} catch (Throwable var3) {
			PlatformDependent0.throwException(var3);
		}
	}

	static {
		Method method;
		Throwable error;
		if (PlatformDependent0.hasUnsafe()) {
			ByteBuffer buffer = ByteBuffer.allocateDirect(1);

			Object maybeInvokeMethod;
			try {
				Method m = PlatformDependent0.UNSAFE.getClass().getDeclaredMethod("invokeCleaner", ByteBuffer.class);
				m.invoke(PlatformDependent0.UNSAFE, buffer);
				maybeInvokeMethod = m;
			} catch (NoSuchMethodException var5) {
				maybeInvokeMethod = var5;
			} catch (InvocationTargetException var6) {
				maybeInvokeMethod = var6;
			} catch (IllegalAccessException var7) {
				maybeInvokeMethod = var7;
			}

			if (maybeInvokeMethod instanceof Throwable) {
				method = null;
				error = (Throwable)maybeInvokeMethod;
			} else {
				method = (Method)maybeInvokeMethod;
				error = null;
			}
		} else {
			method = null;
			error = new UnsupportedOperationException("sun.misc.Unsafe unavailable");
		}

		if (error == null) {
			logger.debug("java.nio.ByteBuffer.cleaner(): available");
		} else {
			logger.debug("java.nio.ByteBuffer.cleaner(): unavailable", error);
		}

		INVOKE_CLEANER = method;
	}
}
