package org.apache.logging.log4j.message;

import java.util.Map;
import org.apache.logging.log4j.util.EnglishEnums;
import org.apache.logging.log4j.util.StringBuilderFormattable;
import org.apache.logging.log4j.util.StringBuilders;

@AsynchronouslyFormattable
public class StructuredDataMessage extends MapMessage implements StringBuilderFormattable {
	private static final long serialVersionUID = 1703221292892071920L;
	private static final int MAX_LENGTH = 32;
	private static final int HASHVAL = 31;
	private StructuredDataId id;
	private String message;
	private String type;

	public StructuredDataMessage(String id, String msg, String type) {
		this.id = new StructuredDataId(id, null, null);
		this.message = msg;
		this.type = type;
	}

	public StructuredDataMessage(String id, String msg, String type, Map<String, String> data) {
		super(data);
		this.id = new StructuredDataId(id, null, null);
		this.message = msg;
		this.type = type;
	}

	public StructuredDataMessage(StructuredDataId id, String msg, String type) {
		this.id = id;
		this.message = msg;
		this.type = type;
	}

	public StructuredDataMessage(StructuredDataId id, String msg, String type, Map<String, String> data) {
		super(data);
		this.id = id;
		this.message = msg;
		this.type = type;
	}

	private StructuredDataMessage(StructuredDataMessage msg, Map<String, String> map) {
		super(map);
		this.id = msg.id;
		this.message = msg.message;
		this.type = msg.type;
	}

	protected StructuredDataMessage() {
	}

	public StructuredDataMessage with(String key, String value) {
		this.put(key, value);
		return this;
	}

	@Override
	public String[] getFormats() {
		String[] formats = new String[StructuredDataMessage.Format.values().length];
		int i = 0;

		for (StructuredDataMessage.Format format : StructuredDataMessage.Format.values()) {
			formats[i++] = format.name();
		}

		return formats;
	}

	public StructuredDataId getId() {
		return this.id;
	}

	protected void setId(String id) {
		this.id = new StructuredDataId(id, null, null);
	}

	protected void setId(StructuredDataId id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	protected void setType(String type) {
		if (type.length() > 32) {
			throw new IllegalArgumentException("structured data type exceeds maximum length of 32 characters: " + type);
		} else {
			this.type = type;
		}
	}

	@Override
	public void formatTo(StringBuilder buffer) {
		this.asString(StructuredDataMessage.Format.FULL, null, buffer);
	}

	@Override
	public String getFormat() {
		return this.message;
	}

	protected void setMessageFormat(String msg) {
		this.message = msg;
	}

	@Override
	protected void validate(String key, String value) {
		this.validateKey(key);
	}

	private void validateKey(String key) {
		if (key.length() > 32) {
			throw new IllegalArgumentException("Structured data keys are limited to 32 characters. key: " + key);
		} else {
			for (int i = 0; i < key.length(); i++) {
				char c = key.charAt(i);
				if (c < '!' || c > '~' || c == '=' || c == ']' || c == '"') {
					throw new IllegalArgumentException("Structured data keys must contain printable US ASCII charactersand may not contain a space, =, ], or \"");
				}
			}
		}
	}

	@Override
	public String asString() {
		return this.asString(StructuredDataMessage.Format.FULL, null);
	}

	@Override
	public String asString(String format) {
		try {
			return this.asString(EnglishEnums.valueOf(StructuredDataMessage.Format.class, format), null);
		} catch (IllegalArgumentException var3) {
			return this.asString();
		}
	}

	public final String asString(StructuredDataMessage.Format format, StructuredDataId structuredDataId) {
		StringBuilder sb = new StringBuilder();
		this.asString(format, structuredDataId, sb);
		return sb.toString();
	}

	public final void asString(StructuredDataMessage.Format format, StructuredDataId structuredDataId, StringBuilder sb) {
		boolean full = StructuredDataMessage.Format.FULL.equals(format);
		if (full) {
			String myType = this.getType();
			if (myType == null) {
				return;
			}

			sb.append(this.getType()).append(' ');
		}

		StructuredDataId sdId = this.getId();
		if (sdId != null) {
			sdId = sdId.makeId(structuredDataId);
		} else {
			sdId = structuredDataId;
		}

		if (sdId != null && sdId.getName() != null) {
			sb.append('[');
			StringBuilders.appendValue(sb, sdId);
			sb.append(' ');
			this.appendMap(sb);
			sb.append(']');
			if (full) {
				String msg = this.getFormat();
				if (msg != null) {
					sb.append(' ').append(msg);
				}
			}
		}
	}

	@Override
	public String getFormattedMessage() {
		return this.asString(StructuredDataMessage.Format.FULL, null);
	}

	@Override
	public String getFormattedMessage(String[] formats) {
		if (formats != null && formats.length > 0) {
			for (int i = 0; i < formats.length; i++) {
				String format = formats[i];
				if (StructuredDataMessage.Format.XML.name().equalsIgnoreCase(format)) {
					return this.asXml();
				}

				if (StructuredDataMessage.Format.FULL.name().equalsIgnoreCase(format)) {
					return this.asString(StructuredDataMessage.Format.FULL, null);
				}
			}

			return this.asString(null, null);
		} else {
			return this.asString(StructuredDataMessage.Format.FULL, null);
		}
	}

	private String asXml() {
		StringBuilder sb = new StringBuilder();
		StructuredDataId sdId = this.getId();
		if (sdId != null && sdId.getName() != null && this.type != null) {
			sb.append("<StructuredData>\n");
			sb.append("<type>").append(this.type).append("</type>\n");
			sb.append("<id>").append(sdId).append("</id>\n");
			super.asXml(sb);
			sb.append("</StructuredData>\n");
			return sb.toString();
		} else {
			return sb.toString();
		}
	}

	@Override
	public String toString() {
		return this.asString(null, null);
	}

	@Override
	public MapMessage newInstance(Map<String, String> map) {
		return new StructuredDataMessage(this, map);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o != null && this.getClass() == o.getClass()) {
			StructuredDataMessage that = (StructuredDataMessage)o;
			if (!super.equals(o)) {
				return false;
			} else if (this.type != null ? this.type.equals(that.type) : that.type == null) {
				if (this.id != null ? this.id.equals(that.id) : that.id == null) {
					return this.message != null ? this.message.equals(that.message) : that.message == null;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (this.type != null ? this.type.hashCode() : 0);
		result = 31 * result + (this.id != null ? this.id.hashCode() : 0);
		return 31 * result + (this.message != null ? this.message.hashCode() : 0);
	}

	public static enum Format {
		XML,
		FULL;
	}
}
