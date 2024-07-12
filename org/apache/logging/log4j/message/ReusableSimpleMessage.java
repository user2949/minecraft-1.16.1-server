package org.apache.logging.log4j.message;

import org.apache.logging.log4j.util.PerformanceSensitive;

@PerformanceSensitive({"allocation"})
public class ReusableSimpleMessage implements ReusableMessage, CharSequence {
	private static final long serialVersionUID = -9199974506498249809L;
	private static Object[] EMPTY_PARAMS = new Object[0];
	private CharSequence charSequence;

	public void set(String message) {
		this.charSequence = message;
	}

	public void set(CharSequence charSequence) {
		this.charSequence = charSequence;
	}

	@Override
	public String getFormattedMessage() {
		return String.valueOf(this.charSequence);
	}

	@Override
	public String getFormat() {
		return this.getFormattedMessage();
	}

	@Override
	public Object[] getParameters() {
		return EMPTY_PARAMS;
	}

	@Override
	public Throwable getThrowable() {
		return null;
	}

	@Override
	public void formatTo(StringBuilder buffer) {
		buffer.append(this.charSequence);
	}

	@Override
	public Object[] swapParameters(Object[] emptyReplacement) {
		return emptyReplacement;
	}

	@Override
	public short getParameterCount() {
		return 0;
	}

	@Override
	public Message memento() {
		return new SimpleMessage(this.charSequence);
	}

	public int length() {
		return this.charSequence == null ? 0 : this.charSequence.length();
	}

	public char charAt(int index) {
		return this.charSequence.charAt(index);
	}

	public CharSequence subSequence(int start, int end) {
		return this.charSequence.subSequence(start, end);
	}
}
