package org.apache.commons.io;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class Java7Support {
	private static final boolean IS_JAVA7;
	private static Method isSymbolicLink;
	private static Method delete;
	private static Method toPath;
	private static Method exists;
	private static Method toFile;
	private static Method readSymlink;
	private static Method createSymlink;
	private static Object emptyLinkOpts;
	private static Object emptyFileAttributes;

	public static boolean isSymLink(File file) {
		try {
			Object path = toPath.invoke(file);
			Boolean result = (Boolean)isSymbolicLink.invoke(null, path);
			return result;
		} catch (IllegalAccessException var3) {
			throw new RuntimeException(var3);
		} catch (InvocationTargetException var4) {
			throw new RuntimeException(var4);
		}
	}

	public static File readSymbolicLink(File symlink) throws IOException {
		try {
			Object path = toPath.invoke(symlink);
			Object resultPath = readSymlink.invoke(null, path);
			return (File)toFile.invoke(resultPath);
		} catch (IllegalAccessException var3) {
			throw new RuntimeException(var3);
		} catch (InvocationTargetException var4) {
			throw new RuntimeException(var4);
		}
	}

	private static boolean exists(File file) throws IOException {
		try {
			Object path = toPath.invoke(file);
			Boolean result = (Boolean)exists.invoke(null, path, emptyLinkOpts);
			return result;
		} catch (IllegalAccessException var3) {
			throw new RuntimeException(var3);
		} catch (InvocationTargetException var4) {
			throw (RuntimeException)var4.getTargetException();
		}
	}

	public static File createSymbolicLink(File symlink, File target) throws IOException {
		try {
			if (!exists(symlink)) {
				Object link = toPath.invoke(symlink);
				Object path = createSymlink.invoke(null, link, toPath.invoke(target), emptyFileAttributes);
				return (File)toFile.invoke(path);
			} else {
				return symlink;
			}
		} catch (IllegalAccessException var4) {
			throw new RuntimeException(var4);
		} catch (InvocationTargetException var5) {
			Throwable targetException = var5.getTargetException();
			throw (IOException)targetException;
		}
	}

	public static void delete(File file) throws IOException {
		try {
			Object path = toPath.invoke(file);
			delete.invoke(null, path);
		} catch (IllegalAccessException var2) {
			throw new RuntimeException(var2);
		} catch (InvocationTargetException var3) {
			throw (IOException)var3.getTargetException();
		}
	}

	public static boolean isAtLeastJava7() {
		return IS_JAVA7;
	}

	static {
		boolean isJava7x = true;

		try {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			Class<?> files = cl.loadClass("java.nio.file.Files");
			Class<?> path = cl.loadClass("java.nio.file.Path");
			Class<?> fa = cl.loadClass("java.nio.file.attribute.FileAttribute");
			Class<?> linkOption = cl.loadClass("java.nio.file.LinkOption");
			isSymbolicLink = files.getMethod("isSymbolicLink", path);
			delete = files.getMethod("delete", path);
			readSymlink = files.getMethod("readSymbolicLink", path);
			emptyFileAttributes = Array.newInstance(fa, 0);
			createSymlink = files.getMethod("createSymbolicLink", path, path, emptyFileAttributes.getClass());
			emptyLinkOpts = Array.newInstance(linkOption, 0);
			exists = files.getMethod("exists", path, emptyLinkOpts.getClass());
			toPath = File.class.getMethod("toPath");
			toFile = path.getMethod("toFile");
		} catch (ClassNotFoundException var6) {
			isJava7x = false;
		} catch (NoSuchMethodException var7) {
			isJava7x = false;
		}

		IS_JAVA7 = isJava7x;
	}
}
