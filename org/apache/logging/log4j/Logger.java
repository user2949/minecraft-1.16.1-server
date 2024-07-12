package org.apache.logging.log4j;

import org.apache.logging.log4j.message.EntryMessage;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.util.MessageSupplier;
import org.apache.logging.log4j.util.Supplier;

public interface Logger {
	void catching(Level level, Throwable throwable);

	void catching(Throwable throwable);

	void debug(Marker marker, Message message);

	void debug(Marker marker, Message message, Throwable throwable);

	void debug(Marker marker, MessageSupplier messageSupplier);

	void debug(Marker marker, MessageSupplier messageSupplier, Throwable throwable);

	void debug(Marker marker, CharSequence charSequence);

	void debug(Marker marker, CharSequence charSequence, Throwable throwable);

	void debug(Marker marker, Object object);

	void debug(Marker marker, Object object, Throwable throwable);

	void debug(Marker marker, String string);

	void debug(Marker marker, String string, Object... arr);

	void debug(Marker marker, String string, Supplier<?>... arr);

	void debug(Marker marker, String string, Throwable throwable);

	void debug(Marker marker, Supplier<?> supplier);

	void debug(Marker marker, Supplier<?> supplier, Throwable throwable);

	void debug(Message message);

	void debug(Message message, Throwable throwable);

	void debug(MessageSupplier messageSupplier);

	void debug(MessageSupplier messageSupplier, Throwable throwable);

	void debug(CharSequence charSequence);

	void debug(CharSequence charSequence, Throwable throwable);

	void debug(Object object);

	void debug(Object object, Throwable throwable);

	void debug(String string);

	void debug(String string, Object... arr);

	void debug(String string, Supplier<?>... arr);

	void debug(String string, Throwable throwable);

	void debug(Supplier<?> supplier);

	void debug(Supplier<?> supplier, Throwable throwable);

	void debug(Marker marker, String string, Object object);

	void debug(Marker marker, String string, Object object3, Object object4);

	void debug(Marker marker, String string, Object object3, Object object4, Object object5);

	void debug(Marker marker, String string, Object object3, Object object4, Object object5, Object object6);

	void debug(Marker marker, String string, Object object3, Object object4, Object object5, Object object6, Object object7);

	void debug(Marker marker, String string, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8);

	void debug(Marker marker, String string, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9);

	void debug(
		Marker marker, String string, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10
	);

	void debug(
		Marker marker,
		String string,
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

	void debug(
		Marker marker,
		String string,
		Object object3,
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

	void debug(String string, Object object);

	void debug(String string, Object object2, Object object3);

	void debug(String string, Object object2, Object object3, Object object4);

	void debug(String string, Object object2, Object object3, Object object4, Object object5);

	void debug(String string, Object object2, Object object3, Object object4, Object object5, Object object6);

	void debug(String string, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7);

	void debug(String string, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8);

	void debug(String string, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9);

	void debug(
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

	void debug(
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

	@Deprecated
	void entry();

	void entry(Object... arr);

	void error(Marker marker, Message message);

	void error(Marker marker, Message message, Throwable throwable);

	void error(Marker marker, MessageSupplier messageSupplier);

	void error(Marker marker, MessageSupplier messageSupplier, Throwable throwable);

	void error(Marker marker, CharSequence charSequence);

	void error(Marker marker, CharSequence charSequence, Throwable throwable);

	void error(Marker marker, Object object);

	void error(Marker marker, Object object, Throwable throwable);

	void error(Marker marker, String string);

	void error(Marker marker, String string, Object... arr);

	void error(Marker marker, String string, Supplier<?>... arr);

	void error(Marker marker, String string, Throwable throwable);

	void error(Marker marker, Supplier<?> supplier);

	void error(Marker marker, Supplier<?> supplier, Throwable throwable);

	void error(Message message);

	void error(Message message, Throwable throwable);

	void error(MessageSupplier messageSupplier);

	void error(MessageSupplier messageSupplier, Throwable throwable);

	void error(CharSequence charSequence);

	void error(CharSequence charSequence, Throwable throwable);

	void error(Object object);

	void error(Object object, Throwable throwable);

	void error(String string);

	void error(String string, Object... arr);

	void error(String string, Supplier<?>... arr);

	void error(String string, Throwable throwable);

	void error(Supplier<?> supplier);

	void error(Supplier<?> supplier, Throwable throwable);

	void error(Marker marker, String string, Object object);

	void error(Marker marker, String string, Object object3, Object object4);

	void error(Marker marker, String string, Object object3, Object object4, Object object5);

	void error(Marker marker, String string, Object object3, Object object4, Object object5, Object object6);

	void error(Marker marker, String string, Object object3, Object object4, Object object5, Object object6, Object object7);

	void error(Marker marker, String string, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8);

	void error(Marker marker, String string, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9);

	void error(
		Marker marker, String string, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10
	);

	void error(
		Marker marker,
		String string,
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

	void error(
		Marker marker,
		String string,
		Object object3,
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

	void error(String string, Object object);

	void error(String string, Object object2, Object object3);

	void error(String string, Object object2, Object object3, Object object4);

	void error(String string, Object object2, Object object3, Object object4, Object object5);

	void error(String string, Object object2, Object object3, Object object4, Object object5, Object object6);

	void error(String string, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7);

	void error(String string, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8);

	void error(String string, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9);

	void error(
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

	void error(
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

	@Deprecated
	void exit();

	@Deprecated
	<R> R exit(R object);

	void fatal(Marker marker, Message message);

	void fatal(Marker marker, Message message, Throwable throwable);

	void fatal(Marker marker, MessageSupplier messageSupplier);

	void fatal(Marker marker, MessageSupplier messageSupplier, Throwable throwable);

	void fatal(Marker marker, CharSequence charSequence);

	void fatal(Marker marker, CharSequence charSequence, Throwable throwable);

	void fatal(Marker marker, Object object);

	void fatal(Marker marker, Object object, Throwable throwable);

	void fatal(Marker marker, String string);

	void fatal(Marker marker, String string, Object... arr);

	void fatal(Marker marker, String string, Supplier<?>... arr);

	void fatal(Marker marker, String string, Throwable throwable);

	void fatal(Marker marker, Supplier<?> supplier);

	void fatal(Marker marker, Supplier<?> supplier, Throwable throwable);

	void fatal(Message message);

	void fatal(Message message, Throwable throwable);

	void fatal(MessageSupplier messageSupplier);

	void fatal(MessageSupplier messageSupplier, Throwable throwable);

	void fatal(CharSequence charSequence);

	void fatal(CharSequence charSequence, Throwable throwable);

	void fatal(Object object);

	void fatal(Object object, Throwable throwable);

	void fatal(String string);

	void fatal(String string, Object... arr);

	void fatal(String string, Supplier<?>... arr);

	void fatal(String string, Throwable throwable);

	void fatal(Supplier<?> supplier);

	void fatal(Supplier<?> supplier, Throwable throwable);

	void fatal(Marker marker, String string, Object object);

	void fatal(Marker marker, String string, Object object3, Object object4);

	void fatal(Marker marker, String string, Object object3, Object object4, Object object5);

	void fatal(Marker marker, String string, Object object3, Object object4, Object object5, Object object6);

	void fatal(Marker marker, String string, Object object3, Object object4, Object object5, Object object6, Object object7);

	void fatal(Marker marker, String string, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8);

	void fatal(Marker marker, String string, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9);

	void fatal(
		Marker marker, String string, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10
	);

	void fatal(
		Marker marker,
		String string,
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

	void fatal(
		Marker marker,
		String string,
		Object object3,
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

	void fatal(String string, Object object);

	void fatal(String string, Object object2, Object object3);

	void fatal(String string, Object object2, Object object3, Object object4);

	void fatal(String string, Object object2, Object object3, Object object4, Object object5);

	void fatal(String string, Object object2, Object object3, Object object4, Object object5, Object object6);

	void fatal(String string, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7);

	void fatal(String string, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8);

	void fatal(String string, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9);

	void fatal(
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

	void fatal(
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

	Level getLevel();

	<MF extends MessageFactory> MF getMessageFactory();

	String getName();

	void info(Marker marker, Message message);

	void info(Marker marker, Message message, Throwable throwable);

	void info(Marker marker, MessageSupplier messageSupplier);

	void info(Marker marker, MessageSupplier messageSupplier, Throwable throwable);

	void info(Marker marker, CharSequence charSequence);

	void info(Marker marker, CharSequence charSequence, Throwable throwable);

	void info(Marker marker, Object object);

	void info(Marker marker, Object object, Throwable throwable);

	void info(Marker marker, String string);

	void info(Marker marker, String string, Object... arr);

	void info(Marker marker, String string, Supplier<?>... arr);

	void info(Marker marker, String string, Throwable throwable);

	void info(Marker marker, Supplier<?> supplier);

	void info(Marker marker, Supplier<?> supplier, Throwable throwable);

	void info(Message message);

	void info(Message message, Throwable throwable);

	void info(MessageSupplier messageSupplier);

	void info(MessageSupplier messageSupplier, Throwable throwable);

	void info(CharSequence charSequence);

	void info(CharSequence charSequence, Throwable throwable);

	void info(Object object);

	void info(Object object, Throwable throwable);

	void info(String string);

	void info(String string, Object... arr);

	void info(String string, Supplier<?>... arr);

	void info(String string, Throwable throwable);

	void info(Supplier<?> supplier);

	void info(Supplier<?> supplier, Throwable throwable);

	void info(Marker marker, String string, Object object);

	void info(Marker marker, String string, Object object3, Object object4);

	void info(Marker marker, String string, Object object3, Object object4, Object object5);

	void info(Marker marker, String string, Object object3, Object object4, Object object5, Object object6);

	void info(Marker marker, String string, Object object3, Object object4, Object object5, Object object6, Object object7);

	void info(Marker marker, String string, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8);

	void info(Marker marker, String string, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9);

	void info(
		Marker marker, String string, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10
	);

	void info(
		Marker marker,
		String string,
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

	void info(
		Marker marker,
		String string,
		Object object3,
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

	void info(String string, Object object);

	void info(String string, Object object2, Object object3);

	void info(String string, Object object2, Object object3, Object object4);

	void info(String string, Object object2, Object object3, Object object4, Object object5);

	void info(String string, Object object2, Object object3, Object object4, Object object5, Object object6);

	void info(String string, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7);

	void info(String string, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8);

	void info(String string, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9);

	void info(
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

	void info(
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

	boolean isDebugEnabled();

	boolean isDebugEnabled(Marker marker);

	boolean isEnabled(Level level);

	boolean isEnabled(Level level, Marker marker);

	boolean isErrorEnabled();

	boolean isErrorEnabled(Marker marker);

	boolean isFatalEnabled();

	boolean isFatalEnabled(Marker marker);

	boolean isInfoEnabled();

	boolean isInfoEnabled(Marker marker);

	boolean isTraceEnabled();

	boolean isTraceEnabled(Marker marker);

	boolean isWarnEnabled();

	boolean isWarnEnabled(Marker marker);

	void log(Level level, Marker marker, Message message);

	void log(Level level, Marker marker, Message message, Throwable throwable);

	void log(Level level, Marker marker, MessageSupplier messageSupplier);

	void log(Level level, Marker marker, MessageSupplier messageSupplier, Throwable throwable);

	void log(Level level, Marker marker, CharSequence charSequence);

	void log(Level level, Marker marker, CharSequence charSequence, Throwable throwable);

	void log(Level level, Marker marker, Object object);

	void log(Level level, Marker marker, Object object, Throwable throwable);

	void log(Level level, Marker marker, String string);

	void log(Level level, Marker marker, String string, Object... arr);

	void log(Level level, Marker marker, String string, Supplier<?>... arr);

	void log(Level level, Marker marker, String string, Throwable throwable);

	void log(Level level, Marker marker, Supplier<?> supplier);

	void log(Level level, Marker marker, Supplier<?> supplier, Throwable throwable);

	void log(Level level, Message message);

	void log(Level level, Message message, Throwable throwable);

	void log(Level level, MessageSupplier messageSupplier);

	void log(Level level, MessageSupplier messageSupplier, Throwable throwable);

	void log(Level level, CharSequence charSequence);

	void log(Level level, CharSequence charSequence, Throwable throwable);

	void log(Level level, Object object);

	void log(Level level, Object object, Throwable throwable);

	void log(Level level, String string);

	void log(Level level, String string, Object... arr);

	void log(Level level, String string, Supplier<?>... arr);

	void log(Level level, String string, Throwable throwable);

	void log(Level level, Supplier<?> supplier);

	void log(Level level, Supplier<?> supplier, Throwable throwable);

	void log(Level level, Marker marker, String string, Object object);

	void log(Level level, Marker marker, String string, Object object4, Object object5);

	void log(Level level, Marker marker, String string, Object object4, Object object5, Object object6);

	void log(Level level, Marker marker, String string, Object object4, Object object5, Object object6, Object object7);

	void log(Level level, Marker marker, String string, Object object4, Object object5, Object object6, Object object7, Object object8);

	void log(Level level, Marker marker, String string, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9);

	void log(
		Level level, Marker marker, String string, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10
	);

	void log(
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

	void log(
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

	void log(
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

	void log(Level level, String string, Object object);

	void log(Level level, String string, Object object3, Object object4);

	void log(Level level, String string, Object object3, Object object4, Object object5);

	void log(Level level, String string, Object object3, Object object4, Object object5, Object object6);

	void log(Level level, String string, Object object3, Object object4, Object object5, Object object6, Object object7);

	void log(Level level, String string, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8);

	void log(Level level, String string, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9);

	void log(
		Level level, String string, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10
	);

	void log(
		Level level,
		String string,
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

	void log(
		Level level,
		String string,
		Object object3,
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

	void printf(Level level, Marker marker, String string, Object... arr);

	void printf(Level level, String string, Object... arr);

	<T extends Throwable> T throwing(Level level, T throwable);

	<T extends Throwable> T throwing(T throwable);

	void trace(Marker marker, Message message);

	void trace(Marker marker, Message message, Throwable throwable);

	void trace(Marker marker, MessageSupplier messageSupplier);

	void trace(Marker marker, MessageSupplier messageSupplier, Throwable throwable);

	void trace(Marker marker, CharSequence charSequence);

	void trace(Marker marker, CharSequence charSequence, Throwable throwable);

	void trace(Marker marker, Object object);

	void trace(Marker marker, Object object, Throwable throwable);

	void trace(Marker marker, String string);

	void trace(Marker marker, String string, Object... arr);

	void trace(Marker marker, String string, Supplier<?>... arr);

	void trace(Marker marker, String string, Throwable throwable);

	void trace(Marker marker, Supplier<?> supplier);

	void trace(Marker marker, Supplier<?> supplier, Throwable throwable);

	void trace(Message message);

	void trace(Message message, Throwable throwable);

	void trace(MessageSupplier messageSupplier);

	void trace(MessageSupplier messageSupplier, Throwable throwable);

	void trace(CharSequence charSequence);

	void trace(CharSequence charSequence, Throwable throwable);

	void trace(Object object);

	void trace(Object object, Throwable throwable);

	void trace(String string);

	void trace(String string, Object... arr);

	void trace(String string, Supplier<?>... arr);

	void trace(String string, Throwable throwable);

	void trace(Supplier<?> supplier);

	void trace(Supplier<?> supplier, Throwable throwable);

	void trace(Marker marker, String string, Object object);

	void trace(Marker marker, String string, Object object3, Object object4);

	void trace(Marker marker, String string, Object object3, Object object4, Object object5);

	void trace(Marker marker, String string, Object object3, Object object4, Object object5, Object object6);

	void trace(Marker marker, String string, Object object3, Object object4, Object object5, Object object6, Object object7);

	void trace(Marker marker, String string, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8);

	void trace(Marker marker, String string, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9);

	void trace(
		Marker marker, String string, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10
	);

	void trace(
		Marker marker,
		String string,
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

	void trace(
		Marker marker,
		String string,
		Object object3,
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

	void trace(String string, Object object);

	void trace(String string, Object object2, Object object3);

	void trace(String string, Object object2, Object object3, Object object4);

	void trace(String string, Object object2, Object object3, Object object4, Object object5);

	void trace(String string, Object object2, Object object3, Object object4, Object object5, Object object6);

	void trace(String string, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7);

	void trace(String string, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8);

	void trace(String string, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9);

	void trace(
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

	void trace(
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

	EntryMessage traceEntry();

	EntryMessage traceEntry(String string, Object... arr);

	EntryMessage traceEntry(Supplier<?>... arr);

	EntryMessage traceEntry(String string, Supplier<?>... arr);

	EntryMessage traceEntry(Message message);

	void traceExit();

	<R> R traceExit(R object);

	<R> R traceExit(String string, R object);

	void traceExit(EntryMessage entryMessage);

	<R> R traceExit(EntryMessage entryMessage, R object);

	<R> R traceExit(Message message, R object);

	void warn(Marker marker, Message message);

	void warn(Marker marker, Message message, Throwable throwable);

	void warn(Marker marker, MessageSupplier messageSupplier);

	void warn(Marker marker, MessageSupplier messageSupplier, Throwable throwable);

	void warn(Marker marker, CharSequence charSequence);

	void warn(Marker marker, CharSequence charSequence, Throwable throwable);

	void warn(Marker marker, Object object);

	void warn(Marker marker, Object object, Throwable throwable);

	void warn(Marker marker, String string);

	void warn(Marker marker, String string, Object... arr);

	void warn(Marker marker, String string, Supplier<?>... arr);

	void warn(Marker marker, String string, Throwable throwable);

	void warn(Marker marker, Supplier<?> supplier);

	void warn(Marker marker, Supplier<?> supplier, Throwable throwable);

	void warn(Message message);

	void warn(Message message, Throwable throwable);

	void warn(MessageSupplier messageSupplier);

	void warn(MessageSupplier messageSupplier, Throwable throwable);

	void warn(CharSequence charSequence);

	void warn(CharSequence charSequence, Throwable throwable);

	void warn(Object object);

	void warn(Object object, Throwable throwable);

	void warn(String string);

	void warn(String string, Object... arr);

	void warn(String string, Supplier<?>... arr);

	void warn(String string, Throwable throwable);

	void warn(Supplier<?> supplier);

	void warn(Supplier<?> supplier, Throwable throwable);

	void warn(Marker marker, String string, Object object);

	void warn(Marker marker, String string, Object object3, Object object4);

	void warn(Marker marker, String string, Object object3, Object object4, Object object5);

	void warn(Marker marker, String string, Object object3, Object object4, Object object5, Object object6);

	void warn(Marker marker, String string, Object object3, Object object4, Object object5, Object object6, Object object7);

	void warn(Marker marker, String string, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8);

	void warn(Marker marker, String string, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9);

	void warn(
		Marker marker, String string, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10
	);

	void warn(
		Marker marker,
		String string,
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

	void warn(
		Marker marker,
		String string,
		Object object3,
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

	void warn(String string, Object object);

	void warn(String string, Object object2, Object object3);

	void warn(String string, Object object2, Object object3, Object object4);

	void warn(String string, Object object2, Object object3, Object object4, Object object5);

	void warn(String string, Object object2, Object object3, Object object4, Object object5, Object object6);

	void warn(String string, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7);

	void warn(String string, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8);

	void warn(String string, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9);

	void warn(
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

	void warn(
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
