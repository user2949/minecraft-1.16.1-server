package org.apache.logging.log4j.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Objects;

public final class LoaderUtil {
	public static final String IGNORE_TCCL_PROPERTY = "log4j.ignoreTCL";
	private static final SecurityManager SECURITY_MANAGER = System.getSecurityManager();
	private static Boolean ignoreTCCL;
	private static final boolean GET_CLASS_LOADER_DISABLED;
	private static final PrivilegedAction<ClassLoader> TCCL_GETTER = new LoaderUtil.ThreadContextClassLoaderGetter();

	private LoaderUtil() {
	}

	public static ClassLoader getThreadContextClassLoader() {
		if (GET_CLASS_LOADER_DISABLED) {
			return LoaderUtil.class.getClassLoader();
		} else {
			return SECURITY_MANAGER == null ? (ClassLoader)TCCL_GETTER.run() : (ClassLoader)AccessController.doPrivileged(TCCL_GETTER);
		}
	}

	public static boolean isClassAvailable(String className) {
		try {
			Class<?> clazz = loadClass(className);
			return clazz != null;
		} catch (ClassNotFoundException var2) {
			return false;
		} catch (Throwable var3) {
			LowLevelLogUtil.logException("Unknown error checking for existence of class: " + className, var3);
			return false;
		}
	}

	public static Class<?> loadClass(String className) throws ClassNotFoundException {
		if (isIgnoreTccl()) {
			return Class.forName(className);
		} else {
			try {
				return getThreadContextClassLoader().loadClass(className);
			} catch (Throwable var2) {
				return Class.forName(className);
			}
		}
	}

	public static <T> T newInstanceOf(Class<T> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException {
		try {
			return (T)clazz.getConstructor().newInstance();
		} catch (NoSuchMethodException var2) {
			return (T)clazz.newInstance();
		}
	}

	public static <T> T newInstanceOf(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
		return newInstanceOf((Class<T>)loadClass(className));
	}

	public static <T> T newCheckedInstanceOf(String className, Class<T> clazz) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		return (T)clazz.cast(newInstanceOf(className));
	}

	public static <T> T newCheckedInstanceOfProperty(String propertyName, Class<T> clazz) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		String className = PropertiesUtil.getProperties().getStringProperty(propertyName);
		return className == null ? null : newCheckedInstanceOf(className, clazz);
	}

	private static boolean isIgnoreTccl() {
		if (ignoreTCCL == null) {
			String ignoreTccl = PropertiesUtil.getProperties().getStringProperty("log4j.ignoreTCL", null);
			ignoreTCCL = ignoreTccl != null && !"false".equalsIgnoreCase(ignoreTccl.trim());
		}

		return ignoreTCCL;
	}

	public static Collection<URL> findResources(String resource) {
		Collection<LoaderUtil.UrlResource> urlResources = findUrlResources(resource);
		Collection<URL> resources = new LinkedHashSet(urlResources.size());

		for (LoaderUtil.UrlResource urlResource : urlResources) {
			resources.add(urlResource.getUrl());
		}

		return resources;
	}

	static Collection<LoaderUtil.UrlResource> findUrlResources(String resource) {
		ClassLoader[] candidates = new ClassLoader[]{
			getThreadContextClassLoader(), LoaderUtil.class.getClassLoader(), GET_CLASS_LOADER_DISABLED ? null : ClassLoader.getSystemClassLoader()
		};
		Collection<LoaderUtil.UrlResource> resources = new LinkedHashSet();

		for (ClassLoader cl : candidates) {
			if (cl != null) {
				try {
					Enumeration<URL> resourceEnum = cl.getResources(resource);

					while (resourceEnum.hasMoreElements()) {
						resources.add(new LoaderUtil.UrlResource(cl, (URL)resourceEnum.nextElement()));
					}
				} catch (IOException var8) {
					LowLevelLogUtil.logException(var8);
				}
			}
		}

		return resources;
	}

	static {
		if (SECURITY_MANAGER != null) {
			boolean getClassLoaderDisabled;
			try {
				SECURITY_MANAGER.checkPermission(new RuntimePermission("getClassLoader"));
				getClassLoaderDisabled = false;
			} catch (SecurityException var2) {
				getClassLoaderDisabled = true;
			}

			GET_CLASS_LOADER_DISABLED = getClassLoaderDisabled;
		} else {
			GET_CLASS_LOADER_DISABLED = false;
		}
	}

	private static class ThreadContextClassLoaderGetter implements PrivilegedAction<ClassLoader> {
		private ThreadContextClassLoaderGetter() {
		}

		public ClassLoader run() {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			if (cl != null) {
				return cl;
			} else {
				ClassLoader ccl = LoaderUtil.class.getClassLoader();
				return ccl == null && !LoaderUtil.GET_CLASS_LOADER_DISABLED ? ClassLoader.getSystemClassLoader() : ccl;
			}
		}
	}

	static class UrlResource {
		private final ClassLoader classLoader;
		private final URL url;

		UrlResource(ClassLoader classLoader, URL url) {
			this.classLoader = classLoader;
			this.url = url;
		}

		public ClassLoader getClassLoader() {
			return this.classLoader;
		}

		public URL getUrl() {
			return this.url;
		}

		public boolean equals(Object o) {
			if (this == o) {
				return true;
			} else if (o != null && this.getClass() == o.getClass()) {
				LoaderUtil.UrlResource that = (LoaderUtil.UrlResource)o;
				if (this.classLoader != null ? this.classLoader.equals(that.classLoader) : that.classLoader == null) {
					return this.url != null ? this.url.equals(that.url) : that.url == null;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		public int hashCode() {
			return Objects.hashCode(this.classLoader) + Objects.hashCode(this.url);
		}
	}
}
