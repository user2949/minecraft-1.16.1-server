package org.apache.logging.log4j.message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.apache.logging.log4j.util.StringBuilderFormattable;

public class SimpleMessage implements Message, StringBuilderFormattable, CharSequence {
	private static final long serialVersionUID = -8398002534962715992L;
	private String message;
	private transient CharSequence charSequence;

	public SimpleMessage() {
		this(null);
	}

	public SimpleMessage(String message) {
		this.message = message;
		this.charSequence = message;
	}

	public SimpleMessage(CharSequence charSequence) {
		this.charSequence = charSequence;
	}

	@Override
	public String getFormattedMessage() {
		if (this.message == null) {
			this.message = String.valueOf(this.charSequence);
		}

		return this.message;
	}

	@Override
	public void formatTo(StringBuilder buffer) {
		if (this.message != null) {
			buffer.append(this.message);
		} else {
			buffer.append(this.charSequence);
		}
	}

	@Override
	public String getFormat() {
		return this.getFormattedMessage();
	}

	@Override
	public Object[] getParameters() {
		return null;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o != null && this.getClass() == o.getClass()) {
			SimpleMessage that = (SimpleMessage)o;
			return this.charSequence != null ? this.charSequence.equals(that.charSequence) : that.charSequence == null;
		} else {
			return false;
		}
	}

	public int hashCode() {
		return this.charSequence != null ? this.charSequence.hashCode() : 0;
	}

	public String toString() {
		return this.getFormattedMessage();
	}

	@Override
	public Throwable getThrowable() {
		return null;
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

	private void writeObject(ObjectOutputStream out) throws IOException {
		this.getFormattedMessage();
		out.defaultWriteObject();
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		this.charSequence = this.message;
	}
}
