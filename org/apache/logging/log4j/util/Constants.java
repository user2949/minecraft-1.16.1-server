package org.apache.logging.log4j.util;

public final class Constants {
	public static final boolean IS_WEB_APP = PropertiesUtil.getProperties().getBooleanProperty("log4j2.is.webapp", isClassAvailable("javax.servlet.Servlet"));
	public static final boolean ENABLE_THREADLOCALS = !IS_WEB_APP && PropertiesUtil.getProperties().getBooleanProperty("log4j2.enable.threadlocals", true);
	public static final int JAVA_MAJOR_VERSION = getMajorVersion();

	private static boolean isClassAvailable(String className) {
		try {
			return LoaderUtil.loadClass(className) != null;
		} catch (Throwable var2) {
			return false;
		}
	}

	private Constants() {
	}

	private static int getMajorVersion() {
		String version = System.getProperty("java.version");
		String[] parts = version.split("-|\\.");

		try {
			int token = Integer.parseInt(parts[0]);
			boolean isJEP223 = token != 1;
			return isJEP223 ? token : Integer.parseInt(parts[1]);
		} catch (Exception var4) {
			return 0;
		}
	}
}
