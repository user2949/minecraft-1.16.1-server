package org.apache.logging.log4j.message;

public interface MessageFactory2 extends MessageFactory {
	Message newMessage(CharSequence charSequence);

	Message newMessage(String string, Object object);

	Message newMessage(String string, Object object2, Object object3);

	Message newMessage(String string, Object object2, Object object3, Object object4);

	Message newMessage(String string, Object object2, Object object3, Object object4, Object object5);

	Message newMessage(String string, Object object2, Object object3, Object object4, Object object5, Object object6);

	Message newMessage(String string, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7);

	Message newMessage(String string, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8);

	Message newMessage(
		String string, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9
	);

	Message newMessage(
		String string,
		Object object2,
		Object object3,
		Object object4,
		Object object5,
		Object object6,
		Object object7,
		Object object8,
		Object object9,
		Object object10
	);

	Message newMessage(
		String string,
		Object object2,
		Object object3,
		Object object4,
		Object object5,
		Object object6,
		Object object7,
		Object object8,
		Object object9,
		Object object10,
		Object object11
	);
}
