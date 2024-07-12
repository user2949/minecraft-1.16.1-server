package org.apache.logging.log4j.util;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Objects;

final class LowLevelLogUtil {
	private static PrintWriter writer = new PrintWriter(System.err, true);

	public static void logException(Throwable exception) {
		exception.printStackTrace(writer);
	}

	public static void logException(String message, Throwable exception) {
		if (message != null) {
			writer.println(message);
		}

		logException(exception);
	}

	public static void setOutputStream(OutputStream out) {
		writer = new PrintWriter((OutputStream)Objects.requireNonNull(out), true);
	}

	public static void setWriter(Writer writer) {
		LowLevelLogUtil.writer = new PrintWriter((Writer)Objects.requireNonNull(writer), true);
	}

	private LowLevelLogUtil() {
	}
}
