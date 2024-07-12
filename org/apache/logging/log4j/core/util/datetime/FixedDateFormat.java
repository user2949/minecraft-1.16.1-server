package org.apache.logging.log4j.core.util.datetime;

import java.util.Calendar;
import java.util.Objects;
import java.util.TimeZone;

public class FixedDateFormat {
	private final FixedDateFormat.FixedFormat fixedFormat;
	private final TimeZone timeZone;
	private final int length;
	private final FastDateFormat fastDateFormat;
	private final char timeSeparatorChar;
	private final char millisSeparatorChar;
	private final int timeSeparatorLength;
	private final int millisSeparatorLength;
	private volatile long midnightToday = 0L;
	private volatile long midnightTomorrow = 0L;
	private char[] cachedDate;
	private int dateLength;

	FixedDateFormat(FixedDateFormat.FixedFormat fixedFormat, TimeZone tz) {
		this.fixedFormat = (FixedDateFormat.FixedFormat)Objects.requireNonNull(fixedFormat);
		this.timeZone = (TimeZone)Objects.requireNonNull(tz);
		this.timeSeparatorChar = fixedFormat.timeSeparatorChar;
		this.timeSeparatorLength = fixedFormat.timeSeparatorLength;
		this.millisSeparatorChar = fixedFormat.millisSeparatorChar;
		this.millisSeparatorLength = fixedFormat.millisSeparatorLength;
		this.length = fixedFormat.getLength();
		this.fastDateFormat = fixedFormat.getFastDateFormat(tz);
	}

	public static FixedDateFormat createIfSupported(String... options) {
		if (options != null && options.length != 0 && options[0] != null) {
			TimeZone tz;
			if (options.length > 1) {
				if (options[1] != null) {
					tz = TimeZone.getTimeZone(options[1]);
				} else {
					tz = TimeZone.getDefault();
				}
			} else {
				if (options.length > 2) {
					return null;
				}

				tz = TimeZone.getDefault();
			}

			FixedDateFormat.FixedFormat type = FixedDateFormat.FixedFormat.lookup(options[0]);
			return type == null ? null : new FixedDateFormat(type, tz);
		} else {
			return new FixedDateFormat(FixedDateFormat.FixedFormat.DEFAULT, TimeZone.getDefault());
		}
	}

	public static FixedDateFormat create(FixedDateFormat.FixedFormat format) {
		return new FixedDateFormat(format, TimeZone.getDefault());
	}

	public static FixedDateFormat create(FixedDateFormat.FixedFormat format, TimeZone tz) {
		return new FixedDateFormat(format, tz != null ? tz : TimeZone.getDefault());
	}

	public String getFormat() {
		return this.fixedFormat.getPattern();
	}

	public TimeZone getTimeZone() {
		return this.timeZone;
	}

	public long millisSinceMidnight(long currentTime) {
		if (currentTime >= this.midnightTomorrow || currentTime < this.midnightToday) {
			this.updateMidnightMillis(currentTime);
		}

		return currentTime - this.midnightToday;
	}

	private void updateMidnightMillis(long now) {
		if (now >= this.midnightTomorrow || now < this.midnightToday) {
			synchronized (this) {
				this.updateCachedDate(now);
				this.midnightToday = this.calcMidnightMillis(now, 0);
				this.midnightTomorrow = this.calcMidnightMillis(now, 1);
			}
		}
	}

	private long calcMidnightMillis(long time, int addDays) {
		Calendar cal = Calendar.getInstance(this.timeZone);
		cal.setTimeInMillis(time);
		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.set(14, 0);
		cal.add(5, addDays);
		return cal.getTimeInMillis();
	}

	private void updateCachedDate(long now) {
		if (this.fastDateFormat != null) {
			StringBuilder result = this.fastDateFormat.format(now, new StringBuilder());
			this.cachedDate = result.toString().toCharArray();
			this.dateLength = result.length();
		}
	}

	public String format(long time) {
		char[] result = new char[this.length << 1];
		int written = this.format(time, result, 0);
		return new String(result, 0, written);
	}

	public int format(long time, char[] buffer, int startPos) {
		int ms = (int)this.millisSinceMidnight(time);
		this.writeDate(buffer, startPos);
		return this.writeTime(ms, buffer, startPos + this.dateLength) - startPos;
	}

	private void writeDate(char[] buffer, int startPos) {
		if (this.cachedDate != null) {
			System.arraycopy(this.cachedDate, 0, buffer, startPos, this.dateLength);
		}
	}

	private int writeTime(int ms, char[] buffer, int pos) {
		int hours = ms / 3600000;
		ms -= 3600000 * hours;
		int minutes = ms / 60000;
		ms -= 60000 * minutes;
		int seconds = ms / 1000;
		ms -= 1000 * seconds;
		int temp = hours / 10;
		buffer[pos++] = (char)(temp + 48);
		buffer[pos++] = (char)(hours - 10 * temp + 48);
		buffer[pos] = this.timeSeparatorChar;
		pos += this.timeSeparatorLength;
		temp = minutes / 10;
		buffer[pos++] = (char)(temp + 48);
		buffer[pos++] = (char)(minutes - 10 * temp + 48);
		buffer[pos] = this.timeSeparatorChar;
		pos += this.timeSeparatorLength;
		temp = seconds / 10;
		buffer[pos++] = (char)(temp + 48);
		buffer[pos++] = (char)(seconds - 10 * temp + 48);
		buffer[pos] = this.millisSeparatorChar;
		pos += this.millisSeparatorLength;
		temp = ms / 100;
		buffer[pos++] = (char)(temp + 48);
		ms -= 100 * temp;
		temp = ms / 10;
		buffer[pos++] = (char)(temp + 48);
		ms -= 10 * temp;
		buffer[pos++] = (char)(ms + 48);
		return pos;
	}

	public static enum FixedFormat {
		ABSOLUTE("HH:mm:ss,SSS", null, 0, ':', 1, ',', 1),
		ABSOLUTE_PERIOD("HH:mm:ss.SSS", null, 0, ':', 1, '.', 1),
		COMPACT("yyyyMMddHHmmssSSS", "yyyyMMdd", 0, ' ', 0, ' ', 0),
		DATE("dd MMM yyyy HH:mm:ss,SSS", "dd MMM yyyy ", 0, ':', 1, ',', 1),
		DATE_PERIOD("dd MMM yyyy HH:mm:ss.SSS", "dd MMM yyyy ", 0, ':', 1, '.', 1),
		DEFAULT("yyyy-MM-dd HH:mm:ss,SSS", "yyyy-MM-dd ", 0, ':', 1, ',', 1),
		DEFAULT_PERIOD("yyyy-MM-dd HH:mm:ss.SSS", "yyyy-MM-dd ", 0, ':', 1, '.', 1),
		ISO8601_BASIC("yyyyMMdd'T'HHmmss,SSS", "yyyyMMdd'T'", 2, ' ', 0, ',', 1),
		ISO8601_BASIC_PERIOD("yyyyMMdd'T'HHmmss.SSS", "yyyyMMdd'T'", 2, ' ', 0, '.', 1),
		ISO8601("yyyy-MM-dd'T'HH:mm:ss,SSS", "yyyy-MM-dd'T'", 2, ':', 1, ',', 1),
		ISO8601_PERIOD("yyyy-MM-dd'T'HH:mm:ss.SSS", "yyyy-MM-dd'T'", 2, ':', 1, '.', 1);

		private final String pattern;
		private final String datePattern;
		private final int escapeCount;
		private final char timeSeparatorChar;
		private final int timeSeparatorLength;
		private final char millisSeparatorChar;
		private final int millisSeparatorLength;

		private FixedFormat(String pattern, String datePattern, int escapeCount, char timeSeparator, int timeSepLength, char millisSeparator, int millisSepLength) {
			this.timeSeparatorChar = timeSeparator;
			this.timeSeparatorLength = timeSepLength;
			this.millisSeparatorChar = millisSeparator;
			this.millisSeparatorLength = millisSepLength;
			this.pattern = (String)Objects.requireNonNull(pattern);
			this.datePattern = datePattern;
			this.escapeCount = escapeCount;
		}

		public String getPattern() {
			return this.pattern;
		}

		public String getDatePattern() {
			return this.datePattern;
		}

		public static FixedDateFormat.FixedFormat lookup(String nameOrPattern) {
			for (FixedDateFormat.FixedFormat type : values()) {
				if (type.name().equals(nameOrPattern) || type.getPattern().equals(nameOrPattern)) {
					return type;
				}
			}

			return null;
		}

		public int getLength() {
			return this.pattern.length() - this.escapeCount;
		}

		public int getDatePatternLength() {
			return this.getDatePattern() == null ? 0 : this.getDatePattern().length() - this.escapeCount;
		}

		public FastDateFormat getFastDateFormat() {
			return this.getFastDateFormat(null);
		}

		public FastDateFormat getFastDateFormat(TimeZone tz) {
			return this.getDatePattern() == null ? null : FastDateFormat.getInstance(this.getDatePattern(), tz);
		}
	}
}
