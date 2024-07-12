package org.apache.logging.log4j.core.util.datetime;

import java.text.FieldPosition;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public interface DatePrinter {
	String format(long long1);

	String format(Date date);

	String format(Calendar calendar);

	<B extends Appendable> B format(long long1, B appendable);

	<B extends Appendable> B format(Date date, B appendable);

	<B extends Appendable> B format(Calendar calendar, B appendable);

	String getPattern();

	TimeZone getTimeZone();

	Locale getLocale();

	StringBuilder format(Object object, StringBuilder stringBuilder, FieldPosition fieldPosition);
}
