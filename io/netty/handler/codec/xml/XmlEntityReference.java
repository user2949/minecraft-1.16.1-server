package io.netty.handler.codec.xml;

public class XmlEntityReference {
	private final String name;
	private final String text;

	public XmlEntityReference(String name, String text) {
		this.name = name;
		this.text = text;
	}

	public String name() {
		return this.name;
	}

	public String text() {
		return this.text;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o != null && this.getClass() == o.getClass()) {
			XmlEntityReference that = (XmlEntityReference)o;
			if (this.name != null ? this.name.equals(that.name) : that.name == null) {
				return this.text != null ? this.text.equals(that.text) : that.text == null;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public int hashCode() {
		int result = this.name != null ? this.name.hashCode() : 0;
		return 31 * result + (this.text != null ? this.text.hashCode() : 0);
	}

	public String toString() {
		return "XmlEntityReference{name='" + this.name + '\'' + ", text='" + this.text + '\'' + '}';
	}
}
