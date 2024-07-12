package io.netty.handler.codec.xml;

public class XmlDocumentStart {
	private final String encoding;
	private final String version;
	private final boolean standalone;
	private final String encodingScheme;

	public XmlDocumentStart(String encoding, String version, boolean standalone, String encodingScheme) {
		this.encoding = encoding;
		this.version = version;
		this.standalone = standalone;
		this.encodingScheme = encodingScheme;
	}

	public String encoding() {
		return this.encoding;
	}

	public String version() {
		return this.version;
	}

	public boolean standalone() {
		return this.standalone;
	}

	public String encodingScheme() {
		return this.encodingScheme;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o != null && this.getClass() == o.getClass()) {
			XmlDocumentStart that = (XmlDocumentStart)o;
			if (this.standalone != that.standalone) {
				return false;
			} else if (this.encoding != null ? this.encoding.equals(that.encoding) : that.encoding == null) {
				if (this.encodingScheme != null ? this.encodingScheme.equals(that.encodingScheme) : that.encodingScheme == null) {
					return this.version != null ? this.version.equals(that.version) : that.version == null;
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

	public int hashCode() {
		int result = this.encoding != null ? this.encoding.hashCode() : 0;
		result = 31 * result + (this.version != null ? this.version.hashCode() : 0);
		result = 31 * result + (this.standalone ? 1 : 0);
		return 31 * result + (this.encodingScheme != null ? this.encodingScheme.hashCode() : 0);
	}

	public String toString() {
		return "XmlDocumentStart{encoding='"
			+ this.encoding
			+ '\''
			+ ", version='"
			+ this.version
			+ '\''
			+ ", standalone="
			+ this.standalone
			+ ", encodingScheme='"
			+ this.encodingScheme
			+ '\''
			+ '}';
	}
}
