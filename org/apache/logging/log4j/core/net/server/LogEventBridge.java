package org.apache.logging.log4j.core.net.server;

import java.io.IOException;
import java.io.InputStream;
import org.apache.logging.log4j.core.LogEventListener;

public interface LogEventBridge<T extends InputStream> {
	void logEvents(T inputStream, LogEventListener logEventListener) throws IOException;

	T wrapStream(InputStream inputStream) throws IOException;
}
