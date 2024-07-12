package io.netty.util.internal.shaded.org.jctools.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import sun.misc.Unsafe;

public class UnsafeAccess {
	public static final boolean SUPPORTS_GET_AND_SET;
	public static final Unsafe UNSAFE;

	static {
		Unsafe instance;
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			instance = (Unsafe)field.get(null);
		} catch (Exception var5) {
			try {
				Constructor<Unsafe> c = Unsafe.class.getDeclaredConstructor();
				c.setAccessible(true);
				instance = (Unsafe)c.newInstance();
			} catch (Exception var4) {
				SUPPORTS_GET_AND_SET = false;
				throw new RuntimeException(var4);
			}
		}

		boolean getAndSetSupport = false;

		try {
			Unsafe.class.getMethod("getAndSetObject", Object.class, long.class, Object.class);
			getAndSetSupport = true;
		} catch (Exception var3) {
		}

		UNSAFE = instance;
		SUPPORTS_GET_AND_SET = getAndSetSupport;
	}
}
