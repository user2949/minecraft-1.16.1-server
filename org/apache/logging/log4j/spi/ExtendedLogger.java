package org.apache.logging.log4j.spi;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.MessageSupplier;
import org.apache.logging.log4j.util.Supplier;

public interface ExtendedLogger extends Logger {
	boolean isEnabled(Level level, Marker marker, Message message, Throwable throwable);

	boolean isEnabled(Level level, Marker marker, CharSequence charSequence, Throwable throwable);

	boolean isEnabled(Level level, Marker marker, Object object, Throwable throwable);

	boolean isEnabled(Level level, Marker marker, String string, Throwable throwable);

	boolean isEnabled(Level level, Marker marker, String string);

	boolean isEnabled(Level level, Marker marker, String string, Object... arr);

	boolean isEnabled(Level level, Marker marker, String string, Object object);

	boolean isEnabled(Level level, Marker marker, String string, Object object4, Object object5);

	boolean isEnabled(Level level, Marker marker, String string, Object object4, Object object5, Object object6);

	boolean isEnabled(Level level, Marker marker, String string, Object object4, Object object5, Object object6, Object object7);

	boolean isEnabled(Level level, Marker marker, String string, Object object4, Object object5, Object object6, Object object7, Object object8);

	boolean isEnabled(Level level, Marker marker, String string, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9);

	boolean isEnabled(
		Level level, Marker marker, String string, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10
	);

	boolean isEnabled(
		Level level,
		Marker marker,
		String string,
		Object object4,
		Object object5,
		Object object6,
		Object object7,
		Object object8,
		Object object9,
		Object object10,
		Object object11
	);

	boolean isEnabled(
		Level level,
		Marker marker,
		String string,
		Object object4,
		Object object5,
		Object object6,
		Object object7,
		Object object8,
		Object object9,
		Object object10,
		Object object11,
		Object object12
	);

	boolean isEnabled(
		Level level,
		Marker marker,
		String string,
		Object object4,
		Object object5,
		Object object6,
		Object object7,
		Object object8,
		Object object9,
		Object object10,
		Object object11,
		Object object12,
		Object object13
	);

	void logIfEnabled(String string, Level level, Marker marker, Message message, Throwable throwable);

	void logIfEnabled(String string, Level level, Marker marker, CharSequence charSequence, Throwable throwable);

	void logIfEnabled(String string, Level level, Marker marker, Object object, Throwable throwable);

	void logIfEnabled(String string1, Level level, Marker marker, String string4, Throwable throwable);

	void logIfEnabled(String string1, Level level, Marker marker, String string4);

	void logIfEnabled(String string1, Level level, Marker marker, String string4, Object... arr);

	void logIfEnabled(String string1, Level level, Marker marker, String string4, Object object);

	void logIfEnabled(String string1, Level level, Marker marker, String string4, Object object5, Object object6);

	void logIfEnabled(String string1, Level level, Marker marker, String string4, Object object5, Object object6, Object object7);

	void logIfEnabled(String string1, Level level, Marker marker, String string4, Object object5, Object object6, Object object7, Object object8);

	void logIfEnabled(String string1, Level level, Marker marker, String string4, Object object5, Object object6, Object object7, Object object8, Object object9);

	void logIfEnabled(
		String string1, Level level, Marker marker, String string4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10
	);

	void logIfEnabled(
		String string1,
		Level level,
		Marker marker,
		String string4,
		Object object5,
		Object object6,
		Object object7,
		Object object8,
		Object object9,
		Object object10,
		Object object11
	);

	void logIfEnabled(
		String string1,
		Level level,
		Marker marker,
		String string4,
		Object object5,
		Object object6,
		Object object7,
		Object object8,
		Object object9,
		Object object10,
		Object object11,
		Object object12
	);

	void logIfEnabled(
		String string1,
		Level level,
		Marker marker,
		String string4,
		Object object5,
		Object object6,
		Object object7,
		Object object8,
		Object object9,
		Object object10,
		Object object11,
		Object object12,
		Object object13
	);

	void logIfEnabled(
		String string1,
		Level level,
		Marker marker,
		String string4,
		Object object5,
		Object object6,
		Object object7,
		Object object8,
		Object object9,
		Object object10,
		Object object11,
		Object object12,
		Object object13,
		Object object14
	);

	void logMessage(String string, Level level, Marker marker, Message message, Throwable throwable);

	void logIfEnabled(String string, Level level, Marker marker, MessageSupplier messageSupplier, Throwable throwable);

	void logIfEnabled(String string1, Level level, Marker marker, String string4, Supplier<?>... arr);

	void logIfEnabled(String string, Level level, Marker marker, Supplier<?> supplier, Throwable throwable);
}
