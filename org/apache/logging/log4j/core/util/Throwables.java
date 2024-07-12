package org.apache.logging.log4j.core.util;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public final class Throwables {
	private Throwables() {
	}

	public static Throwable getRootCause(Throwable throwable) {
		Throwable root = throwable;

		Throwable cause;
		while ((cause = root.getCause()) != null) {
			root = cause;
		}

		return root;
	}

	public static List<String> toStringList(Throwable throwable) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		try {
			throwable.printStackTrace(pw);
		} catch (RuntimeException var10) {
		}

		pw.flush();
		List<String> lines = new ArrayList();
		LineNumberReader reader = new LineNumberReader(new StringReader(sw.toString()));

		try {
			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				lines.add(line);
			}
		} catch (IOException var11) {
			if (var11 instanceof InterruptedIOException) {
				Thread.currentThread().interrupt();
			}

			lines.add(var11.toString());
		} finally {
			Closer.closeSilently(reader);
		}

		return lines;
	}

	public static void rethrow(Throwable t) {
		rethrow0(t);
	}

	private static <T extends Throwable> void rethrow0(Throwable t) throws T {
		throw t;
	}
}
