package io.netty.handler.codec.xml;

public class XmlNamespace {
	private final String prefix;
	private final String uri;

	public XmlNamespace(String prefix, String uri) {
		this.prefix = prefix;
		this.uri = uri;
	}

	public String prefix() {
		return this.prefix;
	}

	public String uri() {
		return this.uri;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o != null && this.getClass() == o.getClass()) {
			XmlNamespace that = (XmlNamespace)o;
			if (this.prefix != null ? this.prefix.equals(that.prefix) : that.prefix == null) {
				return this.uri != null ? this.uri.equals(that.uri) : that.uri == null;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public int hashCode() {
		int result = this.prefix != null ? this.prefix.hashCode() : 0;
		return 31 * result + (this.uri != null ? this.uri.hashCode() : 0);
	}

	public String toString() {
		return "XmlNamespace{prefix='" + this.prefix + '\'' + ", uri='" + this.uri + '\'' + '}';
	}
}
