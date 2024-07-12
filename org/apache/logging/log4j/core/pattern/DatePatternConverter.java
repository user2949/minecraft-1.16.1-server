package org.apache.logging.log4j.core.pattern;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.util.Constants;
import org.apache.logging.log4j.core.util.datetime.FastDateFormat;
import org.apache.logging.log4j.core.util.datetime.FixedDateFormat;
import org.apache.logging.log4j.core.util.datetime.FixedDateFormat.FixedFormat;
import org.apache.logging.log4j.util.PerformanceSensitive;

@Plugin(
	name = "DatePatternConverter",
	category = "Converter"
)
@ConverterKeys({"d", "date"})
@PerformanceSensitive({"allocation"})
public final class DatePatternConverter extends LogEventPatternConverter implements ArrayPatternConverter {
	private static final String UNIX_FORMAT = "UNIX";
	private static final String UNIX_MILLIS_FORMAT = "UNIX_MILLIS";
	private final String[] options;
	private final ThreadLocal<DatePatternConverter.Formatter> threadLocalFormatter = new ThreadLocal();
	private final AtomicReference<DatePatternConverter.CachedTime> cachedTime;
	private final DatePatternConverter.Formatter formatter;

	private DatePatternConverter(String[] options) {
		super("Date", "date");
		this.options = options == null ? null : (String[])Arrays.copyOf(options, options.length);
		this.formatter = this.createFormatter(options);
		this.cachedTime = new AtomicReference(new DatePatternConverter.CachedTime(System.currentTimeMillis()));
	}

	private DatePatternConverter.Formatter createFormatter(String[] options) {
		FixedDateFormat fixedDateFormat = FixedDateFormat.createIfSupported(options);
		return fixedDateFormat != null ? createFixedFormatter(fixedDateFormat) : createNonFixedFormatter(options);
	}

	public static DatePatternConverter newInstance(String[] options) {
		return new DatePatternConverter(options);
	}

	private static DatePatternConverter.Formatter createFixedFormatter(FixedDateFormat fixedDateFormat) {
		return new DatePatternConverter.FixedFormatter(fixedDateFormat);
	}

	private static DatePatternConverter.Formatter createNonFixedFormatter(String[] options) {
		Objects.requireNonNull(options);
		if (options.length == 0) {
			throw new IllegalArgumentException("options array must have at least one element");
		} else {
			Objects.requireNonNull(options[0]);
			String patternOption = options[0];
			if ("UNIX".equals(patternOption)) {
				return new DatePatternConverter.UnixFormatter();
			} else if ("UNIX_MILLIS".equals(patternOption)) {
				return new DatePatternConverter.UnixMillisFormatter();
			} else {
				FixedFormat fixedFormat = FixedFormat.lookup(patternOption);
				String pattern = fixedFormat == null ? patternOption : fixedFormat.getPattern();
				TimeZone tz = null;
				if (options.length > 1 && options[1] != null) {
					tz = TimeZone.getTimeZone(options[1]);
				}

				try {
					FastDateFormat tempFormat = FastDateFormat.getInstance(pattern, tz);
					return new DatePatternConverter.PatternFormatter(tempFormat);
				} catch (IllegalArgumentException var6) {
					LOGGER.warn("Could not instantiate FastDateFormat with pattern " + pattern, (Throwable)var6);
					return createFixedFormatter(FixedDateFormat.create(FixedFormat.DEFAULT, tz));
				}
			}
		}
	}

	public void format(Date date, StringBuilder toAppendTo) {
		this.format(date.getTime(), toAppendTo);
	}

	@Override
	public void format(LogEvent event, StringBuilder output) {
		this.format(event.getTimeMillis(), output);
	}

	public void format(long timestampMillis, StringBuilder output) {
		if (Constants.ENABLE_THREADLOCALS) {
			this.formatWithoutAllocation(timestampMillis, output);
		} else {
			this.formatWithoutThreadLocals(timestampMillis, output);
		}
	}

	private void formatWithoutAllocation(long timestampMillis, StringBuilder output) {
		this.getThreadLocalFormatter().formatToBuffer(timestampMillis, output);
	}

	private DatePatternConverter.Formatter getThreadLocalFormatter() {
		DatePatternConverter.Formatter result = (DatePatternConverter.Formatter)this.threadLocalFormatter.get();
		if (result == null) {
			result = this.createFormatter(this.options);
			this.threadLocalFormatter.set(result);
		}

		return result;
	}

	private void formatWithoutThreadLocals(long timestampMillis, StringBuilder output) {
		DatePatternConverter.CachedTime cached = (DatePatternConverter.CachedTime)this.cachedTime.get();
		if (timestampMillis != cached.timestampMillis) {
			DatePatternConverter.CachedTime newTime = new DatePatternConverter.CachedTime(timestampMillis);
			if (this.cachedTime.compareAndSet(cached, newTime)) {
				cached = newTime;
			} else {
				cached = (DatePatternConverter.CachedTime)this.cachedTime.get();
			}
		}

		output.append(cached.formatted);
	}

	@Override
	public void format(Object obj, StringBuilder output) {
		if (obj instanceof Date) {
			this.format((Date)obj, output);
		}

		super.format(obj, output);
	}

	@Override
	public void format(StringBuilder toAppendTo, Object... objects) {
		for (Object obj : objects) {
			if (obj instanceof Date) {
				this.format(obj, toAppendTo);
				break;
			}
		}
	}

	public String getPattern() {
		return this.formatter.toPattern();
	}

	private final class CachedTime {
		public long timestampMillis;
		public String formatted;

		public CachedTime(long timestampMillis) {
			this.timestampMillis = timestampMillis;
			this.formatted = DatePatternConverter.this.formatter.format(this.timestampMillis);
		}
	}

	private static final class FixedFormatter extends DatePatternConverter.Formatter {
		private final FixedDateFormat fixedDateFormat;
		private final char[] cachedBuffer = new char[64];
		private int length = 0;

		FixedFormatter(FixedDateFormat fixedDateFormat) {
			this.fixedDateFormat = fixedDateFormat;
		}

		@Override
		String format(long timeMillis) {
			return this.fixedDateFormat.format(timeMillis);
		}

		@Override
		void formatToBuffer(long timeMillis, StringBuilder destination) {
			if (this.previousTime != timeMillis) {
				this.length = this.fixedDateFormat.format(timeMillis, this.cachedBuffer, 0);
			}

			destination.append(this.cachedBuffer, 0, this.length);
		}

		@Override
		public String toPattern() {
			return this.fixedDateFormat.getFormat();
		}
	}

	private abstract static class Formatter {
		long previousTime;

		private Formatter() {
		}

		abstract String format(long long1);

		abstract void formatToBuffer(long long1, StringBuilder stringBuilder);

		public String toPattern() {
			return null;
		}
	}

	private static final class PatternFormatter extends DatePatternConverter.Formatter {
		private final FastDateFormat fastDateFormat;
		private final StringBuilder cachedBuffer = new StringBuilder(64);

		PatternFormatter(FastDateFormat fastDateFormat) {
			this.fastDateFormat = fastDateFormat;
		}

		@Override
		String format(long timeMillis) {
			return this.fastDateFormat.format(timeMillis);
		}

		@Override
		void formatToBuffer(long timeMillis, StringBuilder destination) {
			if (this.previousTime != timeMillis) {
				this.cachedBuffer.setLength(0);
				this.fastDateFormat.format(timeMillis, this.cachedBuffer);
			}

			destination.append(this.cachedBuffer);
		}

		@Override
		public String toPattern() {
			return this.fastDateFormat.getPattern();
		}
	}

	private static final class UnixFormatter extends DatePatternConverter.Formatter {
		private UnixFormatter() {
		}

		@Override
		String format(long timeMillis) {
			return Long.toString(timeMillis / 1000L);
		}

		@Override
		void formatToBuffer(long timeMillis, StringBuilder destination) {
			destination.append(timeMillis / 1000L);
		}
	}

	private static final class UnixMillisFormatter extends DatePatternConverter.Formatter {
		private UnixMillisFormatter() {
		}

		@Override
		String format(long timeMillis) {
			return Long.toString(timeMillis);
		}

		@Override
		void formatToBuffer(long timeMillis, StringBuilder destination) {
			destination.append(timeMillis);
		}
	}
}
