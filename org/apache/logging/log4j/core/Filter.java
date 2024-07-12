package org.apache.logging.log4j.core;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.EnglishEnums;

public interface Filter extends LifeCycle {
	String ELEMENT_TYPE = "filter";

	Filter.Result getOnMismatch();

	Filter.Result getOnMatch();

	Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object... arr);

	Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object);

	Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object5, Object object6);

	Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object5, Object object6, Object object7);

	Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object5, Object object6, Object object7, Object object8);

	Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object5, Object object6, Object object7, Object object8, Object object9);

	Filter.Result filter(
		Logger logger, Level level, Marker marker, String string, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10
	);

	Filter.Result filter(
		Logger logger,
		Level level,
		Marker marker,
		String string,
		Object object5,
		Object object6,
		Object object7,
		Object object8,
		Object object9,
		Object object10,
		Object object11
	);

	Filter.Result filter(
		Logger logger,
		Level level,
		Marker marker,
		String string,
		Object object5,
		Object object6,
		Object object7,
		Object object8,
		Object object9,
		Object object10,
		Object object11,
		Object object12
	);

	Filter.Result filter(
		Logger logger,
		Level level,
		Marker marker,
		String string,
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

	Filter.Result filter(
		Logger logger,
		Level level,
		Marker marker,
		String string,
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

	Filter.Result filter(Logger logger, Level level, Marker marker, Object object, Throwable throwable);

	Filter.Result filter(Logger logger, Level level, Marker marker, Message message, Throwable throwable);

	Filter.Result filter(LogEvent logEvent);

	public static enum Result {
		ACCEPT,
		NEUTRAL,
		DENY;

		public static Filter.Result toResult(String name) {
			return toResult(name, null);
		}

		public static Filter.Result toResult(String name, Filter.Result defaultResult) {
			return EnglishEnums.valueOf(Filter.Result.class, name, defaultResult);
		}
	}
}
