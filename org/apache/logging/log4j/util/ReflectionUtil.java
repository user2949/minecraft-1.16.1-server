package org.apache.logging.log4j.util;

import java.lang.reflect.Method;
import java.util.Stack;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.status.StatusLogger;

public final class ReflectionUtil {
	static final int JDK_7u25_OFFSET;
	private static final Logger LOGGER = StatusLogger.getLogger();
	private static final boolean SUN_REFLECTION_SUPPORTED;
	private static final Method GET_CALLER_CLASS;
	private static final ReflectionUtil.PrivateSecurityManager SECURITY_MANAGER;

	private ReflectionUtil() {
	}

	public static boolean supportsFastReflection() {
		return SUN_REFLECTION_SUPPORTED;
	}

	@PerformanceSensitive
	public static Class<?> getCallerClass(int depth) {
		if (depth < 0) {
			throw new IndexOutOfBoundsException(Integer.toString(depth));
		} else if (supportsFastReflection()) {
			try {
				return (Class<?>)GET_CALLER_CLASS.invoke(null, depth + 1 + JDK_7u25_OFFSET);
			} catch (Exception var3) {
				LOGGER.error("Error in ReflectionUtil.getCallerClass({}).", depth, var3);
				return null;
			}
		} else {
			StackTraceElement element = getEquivalentStackTraceElement(depth + 1);

			try {
				return LoaderUtil.loadClass(element.getClassName());
			} catch (ClassNotFoundException var4) {
				LOGGER.error("Could not find class in ReflectionUtil.getCallerClass({}).", depth, var4);
				return null;
			}
		}
	}

	static StackTraceElement getEquivalentStackTraceElement(int depth) {
		StackTraceElement[] elements = new Throwable().getStackTrace();
		int i = 0;

		for (StackTraceElement element : elements) {
			if (isValid(element)) {
				if (i == depth) {
					return element;
				}

				i++;
			}
		}

		LOGGER.error("Could not find an appropriate StackTraceElement at index {}", depth);
		throw new IndexOutOfBoundsException(Integer.toString(depth));
	}

	private static boolean isValid(StackTraceElement element) {
		if (element.isNativeMethod()) {
			return false;
		} else {
			String cn = element.getClassName();
			if (cn.startsWith("sun.reflect.")) {
				return false;
			} else {
				String mn = element.getMethodName();
				if (!cn.startsWith("java.lang.reflect.") || !mn.equals("invoke") && !mn.equals("newInstance")) {
					if (cn.startsWith("jdk.internal.reflect.")) {
						return false;
					} else {
						return cn.equals("java.lang.Class") && mn.equals("newInstance") ? false : !cn.equals("java.lang.invoke.MethodHandle") || !mn.startsWith("invoke");
					}
				} else {
					return false;
				}
			}
		}
	}

	@PerformanceSensitive
	public static Class<?> getCallerClass(String fqcn) {
		return getCallerClass(fqcn, "");
	}

	@PerformanceSensitive
	public static Class<?> getCallerClass(String fqcn, String pkg) {
		if (supportsFastReflection()) {
			boolean next = false;

			Class<?> clazz;
			for (int i = 2; null != (clazz = getCallerClass(i)); i++) {
				if (fqcn.equals(clazz.getName())) {
					next = true;
				} else if (next && clazz.getName().startsWith(pkg)) {
					return clazz;
				}
			}

			return null;
		} else if (SECURITY_MANAGER != null) {
			return SECURITY_MANAGER.getCallerClass(fqcn, pkg);
		} else {
			try {
				return LoaderUtil.loadClass(getCallerClassName(fqcn, pkg, new Throwable().getStackTrace()));
			} catch (ClassNotFoundException var5) {
				return null;
			}
		}
	}

	@PerformanceSensitive
	public static Class<?> getCallerClass(Class<?> anchor) {
		if (supportsFastReflection()) {
			boolean next = false;

			Class<?> clazz;
			for (int i = 2; null != (clazz = getCallerClass(i)); i++) {
				if (anchor.equals(clazz)) {
					next = true;
				} else if (next) {
					return clazz;
				}
			}

			return Object.class;
		} else if (SECURITY_MANAGER != null) {
			return SECURITY_MANAGER.getCallerClass(anchor);
		} else {
			try {
				return LoaderUtil.loadClass(getCallerClassName(anchor.getName(), "", new Throwable().getStackTrace()));
			} catch (ClassNotFoundException var4) {
				return Object.class;
			}
		}
	}

	private static String getCallerClassName(String fqcn, String pkg, StackTraceElement... elements) {
		boolean next = false;

		for (StackTraceElement element : elements) {
			String className = element.getClassName();
			if (className.equals(fqcn)) {
				next = true;
			} else if (next && className.startsWith(pkg)) {
				return className;
			}
		}

		return Object.class.getName();
	}

	@PerformanceSensitive
	public static Stack<Class<?>> getCurrentStackTrace() {
		if (SECURITY_MANAGER != null) {
			Class<?>[] array = SECURITY_MANAGER.getClassContext();
			Stack<Class<?>> classes = new Stack();
			classes.ensureCapacity(array.length);

			for (Class<?> clazz : array) {
				classes.push(clazz);
			}

			return classes;
		} else if (!supportsFastReflection()) {
			return new Stack();
		} else {
			Stack<Class<?>> classes = new Stack();

			Class<?> clazz;
			for (int i = 1; null != (clazz = getCallerClass(i)); i++) {
				classes.push(clazz);
			}

			return classes;
		}
	}

	static {
		int java7u25CompensationOffset = 0;

		Method getCallerClass;
		try {
			Class<?> sunReflectionClass = LoaderUtil.loadClass("sun.reflect.Reflection");
			getCallerClass = sunReflectionClass.getDeclaredMethod("getCallerClass", int.class);
			Object o = getCallerClass.invoke(null, 0);
			Object test1 = getCallerClass.invoke(null, 0);
			if (o != null && o == sunReflectionClass) {
				o = getCallerClass.invoke(null, 1);
				if (o == sunReflectionClass) {
					LOGGER.warn("You are using Java 1.7.0_25 which has a broken implementation of Reflection.getCallerClass.");
					LOGGER.warn("You should upgrade to at least Java 1.7.0_40 or later.");
					LOGGER.debug("Using stack depth compensation offset of 1 due to Java 7u25.");
					java7u25CompensationOffset = 1;
				}
			} else {
				LOGGER.warn("Unexpected return value from Reflection.getCallerClass(): {}", test1);
				getCallerClass = null;
				java7u25CompensationOffset = -1;
			}
		} catch (LinkageError | Exception var6) {
			LOGGER.info("sun.reflect.Reflection.getCallerClass is not supported. ReflectionUtil.getCallerClass will be much slower due to this.", (Throwable)var6);
			getCallerClass = null;
			java7u25CompensationOffset = -1;
		}

		SUN_REFLECTION_SUPPORTED = getCallerClass != null;
		GET_CALLER_CLASS = getCallerClass;
		JDK_7u25_OFFSET = java7u25CompensationOffset;

		ReflectionUtil.PrivateSecurityManager psm;
		try {
			SecurityManager sm = System.getSecurityManager();
			if (sm != null) {
				sm.checkPermission(new RuntimePermission("createSecurityManager"));
			}

			psm = new ReflectionUtil.PrivateSecurityManager();
		} catch (SecurityException var5) {
			LOGGER.debug("Not allowed to create SecurityManager. Falling back to slowest ReflectionUtil implementation.");
			psm = null;
		}

		SECURITY_MANAGER = psm;
	}

	static final class PrivateSecurityManager extends SecurityManager {
		protected Class<?>[] getClassContext() {
			return super.getClassContext();
		}

		protected Class<?> getCallerClass(String fqcn, String pkg) {
			boolean next = false;

			for (Class<?> clazz : this.getClassContext()) {
				if (fqcn.equals(clazz.getName())) {
					next = true;
				} else if (next && clazz.getName().startsWith(pkg)) {
					return clazz;
				}
			}

			return null;
		}

		protected Class<?> getCallerClass(Class<?> anchor) {
			boolean next = false;

			for (Class<?> clazz : this.getClassContext()) {
				if (anchor.equals(clazz)) {
					next = true;
				} else if (next) {
					return clazz;
				}
			}

			return Object.class;
		}
	}
}
