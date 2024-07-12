package org.apache.commons.lang3.time;

import java.text.FieldPosition;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public interface DatePrinter {
	String format(long long1);

	String format(Date date);

	String format(Calendar calendar);

	@Deprecated
	StringBuffer format(long long1, StringBuffer stringBuffer);

	@Deprecated
	StringBuffer format(Date date, StringBuffer stringBuffer);

	@Deprecated
	StringBuffer format(Calendar calendar, StringBuffer stringBuffer);

	<B extends Appendable> B format(long long1, B appendable);

	<B extends Appendable> B format(Date date, B appendable);

	<B extends Appendable> B format(Calendar calendar, B appendable);

	String getPattern();

	TimeZone getTimeZone();

	Locale getLocale();

	StringBuffer format(Object object, StringBuffer stringBuffer, FieldPosition fieldPosition);
}
