package org.apache.logging.log4j.core.util;

import javax.naming.Context;
import javax.naming.NamingException;

public final class JndiCloser {
	private JndiCloser() {
	}

	public static void close(Context context) throws NamingException {
		if (context != null) {
			context.close();
		}
	}

	public static boolean closeSilently(Context context) {
		try {
			close(context);
			return true;
		} catch (NamingException var2) {
			return false;
		}
	}
}