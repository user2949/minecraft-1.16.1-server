package org.apache.logging.log4j.message;

import java.util.Arrays;
import org.apache.logging.log4j.util.StringBuilderFormattable;

public class ParameterizedMessage implements Message, StringBuilderFormattable {
	private static final int DEFAULT_STRING_BUILDER_SIZE = 255;
	public static final String RECURSION_PREFIX = "[...";
	public static final String RECURSION_SUFFIX = "...]";
	public static final String ERROR_PREFIX = "[!!!";
	public static final String ERROR_SEPARATOR = "=>";
	public static final String ERROR_MSG_SEPARATOR = ":";
	public static final String ERROR_SUFFIX = "!!!]";
	private static final long serialVersionUID = -665975803997290697L;
	private static final int HASHVAL = 31;
	private static ThreadLocal<StringBuilder> threadLocalStringBuilder = new ThreadLocal();
	private String messagePattern;
	private transient Object[] argArray;
	private String formattedMessage;
	private transient Throwable throwable;
	private int[] indices;
	private int usedCount;

	@Deprecated
	public ParameterizedMessage(String messagePattern, String[] arguments, Throwable throwable) {
		this.argArray = arguments;
		this.throwable = throwable;
		this.init(messagePattern);
	}

	public ParameterizedMessage(String messagePattern, Object[] arguments, Throwable throwable) {
		this.argArray = arguments;
		this.throwable = throwable;
		this.init(messagePattern);
	}

	public ParameterizedMessage(String messagePattern, Object... arguments) {
		this.argArray = arguments;
		this.init(messagePattern);
	}

	public ParameterizedMessage(String messagePattern, Object arg) {
		this(messagePattern, arg);
	}

	public ParameterizedMessage(String messagePattern, Object arg0, Object arg1) {
		this(messagePattern, arg0, arg1);
	}

	private void init(String messagePattern) {
		this.messagePattern = messagePattern;
		int len = Math.max(1, messagePattern == null ? 0 : messagePattern.length() >> 1);
		this.indices = new int[len];
		int placeholders = ParameterFormatter.countArgumentPlaceholders2(messagePattern, this.indices);
		this.initThrowable(this.argArray, placeholders);
		this.usedCount = Math.min(placeholders, this.argArray == null ? 0 : this.argArray.length);
	}

	private void initThrowable(Object[] params, int usedParams) {
		if (params != null) {
			int argCount = params.length;
			if (usedParams < argCount && this.throwable == null && params[argCount - 1] instanceof Throwable) {
				this.throwable = (Throwable)params[argCount - 1];
			}
		}
	}

	@Override
	public String getFormat() {
		return this.messagePattern;
	}

	@Override
	public Object[] getParameters() {
		return this.argArray;
	}

	@Override
	public Throwable getThrowable() {
		return this.throwable;
	}

	@Override
	public String getFormattedMessage() {
		if (this.formattedMessage == null) {
			StringBuilder buffer = getThreadLocalStringBuilder();
			this.formatTo(buffer);
			this.formattedMessage = buffer.toString();
		}

		return this.formattedMessage;
	}

	private static StringBuilder getThreadLocalStringBuilder() {
		StringBuilder buffer = (StringBuilder)threadLocalStringBuilder.get();
		if (buffer == null) {
			buffer = new StringBuilder(255);
			threadLocalStringBuilder.set(buffer);
		}

		buffer.setLength(0);
		return buffer;
	}

	@Override
	public void formatTo(StringBuilder buffer) {
		if (this.formattedMessage != null) {
			buffer.append(this.formattedMessage);
		} else if (this.indices[0] < 0) {
			ParameterFormatter.formatMessage(buffer, this.messagePattern, this.argArray, this.usedCount);
		} else {
			ParameterFormatter.formatMessage2(buffer, this.messagePattern, this.argArray, this.usedCount, this.indices);
		}
	}

	public static String format(String messagePattern, Object[] arguments) {
		return ParameterFormatter.format(messagePattern, arguments);
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o != null && this.getClass() == o.getClass()) {
			ParameterizedMessage that = (ParameterizedMessage)o;
			return (this.messagePattern != null ? this.messagePattern.equals(that.messagePattern) : that.messagePattern == null)
				? Arrays.equals(this.argArray, that.argArray)
				: false;
		} else {
			return false;
		}
	}

	public int hashCode() {
		int result = this.messagePattern != null ? this.messagePattern.hashCode() : 0;
		return 31 * result + (this.argArray != null ? Arrays.hashCode(this.argArray) : 0);
	}

	public static int countArgumentPlaceholders(String messagePattern) {
		return ParameterFormatter.countArgumentPlaceholders(messagePattern);
	}

	public static String deepToString(Object o) {
		return ParameterFormatter.deepToString(o);
	}

	public static String identityToString(Object obj) {
		return ParameterFormatter.identityToString(obj);
	}

	public String toString() {
		return "ParameterizedMessage[messagePattern="
			+ this.messagePattern
			+ ", stringArgs="
			+ Arrays.toString(this.argArray)
			+ ", throwable="
			+ this.throwable
			+ ']';
	}
}
