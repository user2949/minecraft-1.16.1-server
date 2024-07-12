package io.netty.util.internal;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public final class NativeLibraryLoader {
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(NativeLibraryLoader.class);
	private static final String NATIVE_RESOURCE_HOME = "META-INF/native/";
	private static final File WORKDIR;
	private static final boolean DELETE_NATIVE_LIB_AFTER_LOADING;

	public static void loadFirstAvailable(ClassLoader loader, String... names) {
		List<Throwable> suppressed = new ArrayList();

		for (String name : names) {
			try {
				load(name, loader);
				return;
			} catch (Throwable var8) {
				suppressed.add(var8);
				logger.debug("Unable to load the library '{}', trying next name...", name, var8);
			}
		}

		IllegalArgumentException iae = new IllegalArgumentException("Failed to load any of the given libraries: " + Arrays.toString(names));
		ThrowableUtil.addSuppressedAndClear(iae, suppressed);
		throw iae;
	}

	private static String calculatePackagePrefix() {
		String maybeShaded = NativeLibraryLoader.class.getName();
		String expected = "io!netty!util!internal!NativeLibraryLoader".replace('!', '.');
		if (!maybeShaded.endsWith(expected)) {
			throw new UnsatisfiedLinkError(
				String.format("Could not find prefix added to %s to get %s. When shading, only adding a package prefix is supported", expected, maybeShaded)
			);
		} else {
			return maybeShaded.substring(0, maybeShaded.length() - expected.length());
		}
	}

	public static void load(String originalName, ClassLoader loader) {
		String name = calculatePackagePrefix().replace('.', '_') + originalName;
		List<Throwable> suppressed = new ArrayList();

		try {
			loadLibrary(loader, name, false);
		} catch (Throwable var25) {
			suppressed.add(var25);
			logger.debug("{} cannot be loaded from java.libary.path, now trying export to -Dio.netty.native.workdir: {}", name, WORKDIR, var25);
			String libname = System.mapLibraryName(name);
			String path = "META-INF/native/" + libname;
			InputStream in = null;
			OutputStream out = null;
			File tmpFile = null;
			URL url;
			if (loader == null) {
				url = ClassLoader.getSystemResource(path);
			} else {
				url = loader.getResource(path);
			}

			try {
				if (url == null) {
					if (!PlatformDependent.isOsx()) {
						FileNotFoundException fnf = new FileNotFoundException(path);
						ThrowableUtil.addSuppressedAndClear(fnf, suppressed);
						throw fnf;
					}

					String fileName = path.endsWith(".jnilib") ? "META-INF/native/lib" + name + ".dynlib" : "META-INF/native/lib" + name + ".jnilib";
					if (loader == null) {
						url = ClassLoader.getSystemResource(fileName);
					} else {
						url = loader.getResource(fileName);
					}

					if (url == null) {
						FileNotFoundException fnf = new FileNotFoundException(fileName);
						ThrowableUtil.addSuppressedAndClear(fnf, suppressed);
						throw fnf;
					}
				}

				int index = libname.lastIndexOf(46);
				String prefix = libname.substring(0, index);
				String suffix = libname.substring(index, libname.length());
				tmpFile = File.createTempFile(prefix, suffix, WORKDIR);
				in = url.openStream();
				out = new FileOutputStream(tmpFile);
				byte[] buffer = new byte[8192];

				int length;
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}

				out.flush();
				closeQuietly(out);
				out = null;
				loadLibrary(loader, tmpFile.getPath(), true);
			} catch (UnsatisfiedLinkError var22) {
				try {
					if (tmpFile != null && tmpFile.isFile() && tmpFile.canRead() && !NativeLibraryLoader.NoexecVolumeDetector.canExecuteExecutable(tmpFile)) {
						logger.info(
							"{} exists but cannot be executed even when execute permissions set; check volume for \"noexec\" flag; use -Dio.netty.native.workdir=[path] to set native working directory separately.",
							tmpFile.getPath()
						);
					}
				} catch (Throwable var21) {
					suppressed.add(var21);
					logger.debug("Error checking if {} is on a file store mounted with noexec", tmpFile, var21);
				}

				ThrowableUtil.addSuppressedAndClear(var22, suppressed);
				throw var22;
			} catch (Exception var23) {
				UnsatisfiedLinkError ule = new UnsatisfiedLinkError("could not load a native library: " + name);
				ule.initCause(var23);
				ThrowableUtil.addSuppressedAndClear(ule, suppressed);
				throw ule;
			} finally {
				closeQuietly(in);
				closeQuietly(out);
				if (tmpFile != null && (!DELETE_NATIVE_LIB_AFTER_LOADING || !tmpFile.delete())) {
					tmpFile.deleteOnExit();
				}
			}
		}
	}

	private static void loadLibrary(ClassLoader loader, String name, boolean absolute) {
		Throwable suppressed = null;

		try {
			try {
				Class<?> newHelper = tryToLoadClass(loader, NativeLibraryUtil.class);
				loadLibraryByHelper(newHelper, name, absolute);
				logger.debug("Successfully loaded the library {}", name);
				return;
			} catch (UnsatisfiedLinkError var5) {
				suppressed = var5;
				logger.debug("Unable to load the library '{}', trying other loading mechanism.", name, var5);
			} catch (Exception var6) {
				suppressed = var6;
				logger.debug("Unable to load the library '{}', trying other loading mechanism.", name, var6);
			}

			NativeLibraryUtil.loadLibrary(name, absolute);
			logger.debug("Successfully loaded the library {}", name);
		} catch (UnsatisfiedLinkError var7) {
			if (suppressed != null) {
				ThrowableUtil.addSuppressed(var7, suppressed);
			}

			throw var7;
		}
	}

	private static void loadLibraryByHelper(Class<?> helper, String name, boolean absolute) throws UnsatisfiedLinkError {
		Object ret = AccessController.doPrivileged(new PrivilegedAction<Object>() {
			public Object run() {
				try {
					Method method = helper.getMethod("loadLibrary", String.class, boolean.class);
					method.setAccessible(true);
					return method.invoke(null, name, absolute);
				} catch (Exception var2) {
					return var2;
				}
			}
		});
		if (ret instanceof Throwable) {
			Throwable t = (Throwable)ret;

			assert !(t instanceof UnsatisfiedLinkError) : t + " should be a wrapper throwable";

			Throwable cause = t.getCause();
			if (cause instanceof UnsatisfiedLinkError) {
				throw (UnsatisfiedLinkError)cause;
			} else {
				UnsatisfiedLinkError ule = new UnsatisfiedLinkError(t.getMessage());
				ule.initCause(t);
				throw ule;
			}
		}
	}

	private static Class<?> tryToLoadClass(ClassLoader loader, Class<?> helper) throws ClassNotFoundException {
		try {
			return Class.forName(helper.getName(), false, loader);
		} catch (ClassNotFoundException var7) {
			if (loader == null) {
				throw var7;
			} else {
				try {
					final byte[] classBinary = classToByteArray(helper);
					return (Class<?>)AccessController.doPrivileged(new PrivilegedAction<Class<?>>() {
						public Class<?> run() {
							try {
								Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class);
								defineClass.setAccessible(true);
								return (Class<?>)defineClass.invoke(loader, helper.getName(), classBinary, 0, classBinary.length);
							} catch (Exception var2) {
								throw new IllegalStateException("Define class failed!", var2);
							}
						}
					});
				} catch (ClassNotFoundException var4) {
					ThrowableUtil.addSuppressed(var4, var7);
					throw var4;
				} catch (RuntimeException var5) {
					ThrowableUtil.addSuppressed(var5, var7);
					throw var5;
				} catch (Error var6) {
					ThrowableUtil.addSuppressed(var6, var7);
					throw var6;
				}
			}
		}
	}

	private static byte[] classToByteArray(Class<?> clazz) throws ClassNotFoundException {
		String fileName = clazz.getName();
		int lastDot = fileName.lastIndexOf(46);
		if (lastDot > 0) {
			fileName = fileName.substring(lastDot + 1);
		}

		URL classUrl = clazz.getResource(fileName + ".class");
		if (classUrl == null) {
			throw new ClassNotFoundException(clazz.getName());
		} else {
			byte[] buf = new byte[1024];
			ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
			InputStream in = null;

			byte[] var13;
			try {
				in = classUrl.openStream();

				while ((r = in.read(buf)) != -1) {
					out.write(buf, 0, r);
				}

				var13 = out.toByteArray();
			} catch (IOException var11) {
				throw new ClassNotFoundException(clazz.getName(), var11);
			} finally {
				closeQuietly(in);
				closeQuietly(out);
			}

			return var13;
		}
	}

	private static void closeQuietly(Closeable c) {
		if (c != null) {
			try {
				c.close();
			} catch (IOException var2) {
			}
		}
	}

	private NativeLibraryLoader() {
	}

	static {
		String workdir = SystemPropertyUtil.get("io.netty.native.workdir");
		if (workdir != null) {
			File f = new File(workdir);
			f.mkdirs();

			try {
				f = f.getAbsoluteFile();
			} catch (Exception var3) {
			}

			WORKDIR = f;
			logger.debug("-Dio.netty.native.workdir: " + WORKDIR);
		} else {
			WORKDIR = PlatformDependent.tmpdir();
			logger.debug("-Dio.netty.native.workdir: " + WORKDIR + " (io.netty.tmpdir)");
		}

		DELETE_NATIVE_LIB_AFTER_LOADING = SystemPropertyUtil.getBoolean("io.netty.native.deleteLibAfterLoading", true);
	}

	private static final class NoexecVolumeDetector {
		private static boolean canExecuteExecutable(File file) throws IOException {
			if (PlatformDependent.javaVersion() < 7) {
				return true;
			} else if (file.canExecute()) {
				return true;
			} else {
				Set<PosixFilePermission> existingFilePermissions = Files.getPosixFilePermissions(file.toPath());
				Set<PosixFilePermission> executePermissions = EnumSet.of(
					PosixFilePermission.OWNER_EXECUTE, PosixFilePermission.GROUP_EXECUTE, PosixFilePermission.OTHERS_EXECUTE
				);
				if (existingFilePermissions.containsAll(executePermissions)) {
					return false;
				} else {
					Set<PosixFilePermission> newPermissions = EnumSet.copyOf(existingFilePermissions);
					newPermissions.addAll(executePermissions);
					Files.setPosixFilePermissions(file.toPath(), newPermissions);
					return file.canExecute();
				}
			}
		}
	}
}
