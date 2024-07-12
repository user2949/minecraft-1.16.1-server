package org.apache.logging.log4j.core.util.datetime;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public interface DateParser {
	Date parse(String string) throws ParseException;

	Date parse(String string, ParsePosition parsePosition);

	boolean parse(String string, ParsePosition parsePosition, Calendar calendar);

	String getPattern();

	TimeZone getTimeZone();

	Locale getLocale();

	Object parseObject(String string) throws ParseException;

	Object parseObject(String string, ParsePosition parsePosition);
}
