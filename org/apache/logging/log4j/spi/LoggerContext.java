package org.apache.logging.log4j.spi;

import org.apache.logging.log4j.message.MessageFactory;

public interface LoggerContext {
	Object getExternalContext();

	ExtendedLogger getLogger(String string);

	ExtendedLogger getLogger(String string, MessageFactory messageFactory);

	boolean hasLogger(String string);

	boolean hasLogger(String string, MessageFactory messageFactory);

	boolean hasLogger(String string, Class<? extends MessageFactory> class2);
}
