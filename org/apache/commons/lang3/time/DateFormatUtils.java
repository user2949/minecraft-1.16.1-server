package org.apache.commons.lang3.time;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateFormatUtils {
	private static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("GMT");
	public static final FastDateFormat ISO_8601_EXTENDED_DATETIME_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss");
	@Deprecated
	public static final FastDateFormat ISO_DATETIME_FORMAT = ISO_8601_EXTENDED_DATETIME_FORMAT;
	public static final FastDateFormat ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ssZZ");
	@Deprecated
	public static final FastDateFormat ISO_DATETIME_TIME_ZONE_FORMAT = ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT;
	public static final FastDateFormat ISO_8601_EXTENDED_DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd");
	@Deprecated
	public static final FastDateFormat ISO_DATE_FORMAT = ISO_8601_EXTENDED_DATE_FORMAT;
	@Deprecated
	public static final FastDateFormat ISO_DATE_TIME_ZONE_FORMAT = FastDateFormat.getInstance("yyyy-MM-ddZZ");
	@Deprecated
	public static final FastDateFormat ISO_TIME_FORMAT = FastDateFormat.getInstance("'T'HH:mm:ss");
	@Deprecated
	public static final FastDateFormat ISO_TIME_TIME_ZONE_FORMAT = FastDateFormat.getInstance("'T'HH:mm:ssZZ");
	public static final FastDateFormat ISO_8601_EXTENDED_TIME_FORMAT = FastDateFormat.getInstance("HH:mm:ss");
	@Deprecated
	public static final FastDateFormat ISO_TIME_NO_T_FORMAT = ISO_8601_EXTENDED_TIME_FORMAT;
	public static final FastDateFormat ISO_8601_EXTENDED_TIME_TIME_ZONE_FORMAT = FastDateFormat.getInstance("HH:mm:ssZZ");
	@Deprecated
	public static final FastDateFormat ISO_TIME_NO_T_TIME_ZONE_FORMAT = ISO_8601_EXTENDED_TIME_TIME_ZONE_FORMAT;
	public static final FastDateFormat SMTP_DATETIME_FORMAT = FastDateFormat.getInstance("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);

	public static String formatUTC(long millis, String pattern) {
		return format(new Date(millis), pattern, UTC_TIME_ZONE, null);
	}

	public static String formatUTC(Date date, String pattern) {
		return format(date, pattern, UTC_TIME_ZONE, null);
	}

	public static String formatUTC(long millis, String pattern, Locale locale) {
		return format(new Date(millis), pattern, UTC_TIME_ZONE, locale);
	}

	public static String formatUTC(Date date, String pattern, Locale locale) {
		return format(date, pattern, UTC_TIME_ZONE, locale);
	}

	public static String format(long millis, String pattern) {
		return format(new Date(millis), pattern, null, null);
	}

	public static String format(Date date, String pattern) {
		return format(date, pattern, null, null);
	}

	public static String format(Calendar calendar, String pattern) {
		return format(calendar, pattern, null, null);
	}

	public static String format(long millis, String pattern, TimeZone timeZone) {
		return format(new Date(millis), pattern, timeZone, null);
	}

	public static String format(Date date, String pattern, TimeZone timeZone) {
		return format(date, pattern, timeZone, null);
	}

	public static String format(Calendar calendar, String pattern, TimeZone timeZone) {
		return format(calendar, pattern, timeZone, null);
	}

	public static String format(long millis, String pattern, Locale locale) {
		return format(new Date(millis), pattern, null, locale);
	}

	public static String format(Date date, String pattern, Locale locale) {
		return format(date, pattern, null, locale);
	}

	public static String format(Calendar calendar, String pattern, Locale locale) {
		return format(calendar, pattern, null, locale);
	}

	public static String format(long millis, String pattern, TimeZone timeZone, Locale locale) {
		return format(new Date(millis), pattern, timeZone, locale);
	}

	public static String format(Date date, String pattern, TimeZone timeZone, Locale locale) {
		FastDateFormat df = FastDateFormat.getInstance(pattern, timeZone, locale);
		return df.format(date);
	}

	public static String format(Calendar calendar, String pattern, TimeZone timeZone, Locale locale) {
		FastDateFormat df = FastDateFormat.getInstance(pattern, timeZone, locale);
		return df.format(calendar);
	}
}
