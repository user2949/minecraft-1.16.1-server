package org.apache.logging.log4j.message;

public interface MessageFactory {
	Message newMessage(Object object);

	Message newMessage(String string);

	Message newMessage(String string, Object... arr);
}
