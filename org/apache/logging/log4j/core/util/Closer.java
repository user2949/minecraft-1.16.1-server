package org.apache.logging.log4j.core.util;

public final class Closer {
	private Closer() {
	}

	public static void close(AutoCloseable closeable) throws Exception {
		if (closeable != null) {
			closeable.close();
		}
	}

	public static boolean closeSilently(AutoCloseable closeable) {
		try {
			close(closeable);
			return true;
		} catch (Exception var2) {
			return false;
		}
	}
}
