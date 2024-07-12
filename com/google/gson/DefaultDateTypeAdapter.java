package com.google.gson;

import com.google.gson.internal.bind.util.ISO8601Utils;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

final class DefaultDateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
	private final DateFormat enUsFormat;
	private final DateFormat localFormat;

	DefaultDateTypeAdapter() {
		this(DateFormat.getDateTimeInstance(2, 2, Locale.US), DateFormat.getDateTimeInstance(2, 2));
	}

	DefaultDateTypeAdapter(String datePattern) {
		this(new SimpleDateFormat(datePattern, Locale.US), new SimpleDateFormat(datePattern));
	}

	DefaultDateTypeAdapter(int style) {
		this(DateFormat.getDateInstance(style, Locale.US), DateFormat.getDateInstance(style));
	}

	public DefaultDateTypeAdapter(int dateStyle, int timeStyle) {
		this(DateFormat.getDateTimeInstance(dateStyle, timeStyle, Locale.US), DateFormat.getDateTimeInstance(dateStyle, timeStyle));
	}

	DefaultDateTypeAdapter(DateFormat enUsFormat, DateFormat localFormat) {
		this.enUsFormat = enUsFormat;
		this.localFormat = localFormat;
	}

	public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
		synchronized (this.localFormat) {
			String dateFormatAsString = this.enUsFormat.format(src);
			return new JsonPrimitive(dateFormatAsString);
		}
	}

	public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		if (!(json instanceof JsonPrimitive)) {
			throw new JsonParseException("The date should be a string value");
		} else {
			Date date = this.deserializeToDate(json);
			if (typeOfT == Date.class) {
				return date;
			} else if (typeOfT == Timestamp.class) {
				return new Timestamp(date.getTime());
			} else if (typeOfT == java.sql.Date.class) {
				return new java.sql.Date(date.getTime());
			} else {
				throw new IllegalArgumentException(this.getClass() + " cannot deserialize to " + typeOfT);
			}
		}
	}

	private Date deserializeToDate(JsonElement json) {
		synchronized (this.localFormat) {
			Date var10;
			try {
				var10 = this.localFormat.parse(json.getAsString());
			} catch (ParseException var7) {
				try {
					var10 = this.enUsFormat.parse(json.getAsString());
				} catch (ParseException var6) {
					try {
						var10 = ISO8601Utils.parse(json.getAsString(), new ParsePosition(0));
					} catch (ParseException var5) {
						throw new JsonSyntaxException(json.getAsString(), var5);
					}

					return var10;
				}

				return var10;
			}

			return var10;
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(DefaultDateTypeAdapter.class.getSimpleName());
		sb.append('(').append(this.localFormat.getClass().getSimpleName()).append(')');
		return sb.toString();
	}
}
