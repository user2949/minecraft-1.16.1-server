package org.apache.logging.log4j.core.net.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LogEventListener;

public class ObjectInputStreamLogEventBridge extends AbstractLogEventBridge<ObjectInputStream> {
	public void logEvents(ObjectInputStream inputStream, LogEventListener logEventListener) throws IOException {
		try {
			logEventListener.log((LogEvent)inputStream.readObject());
		} catch (ClassNotFoundException var4) {
			throw new IOException(var4);
		}
	}

	public ObjectInputStream wrapStream(InputStream inputStream) throws IOException {
		return new ObjectInputStream(inputStream);
	}
}
