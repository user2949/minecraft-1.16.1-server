package io.netty.handler.codec.xml;

public class XmlAttribute {
	private final String type;
	private final String name;
	private final String prefix;
	private final String namespace;
	private final String value;

	public XmlAttribute(String type, String name, String prefix, String namespace, String value) {
		this.type = type;
		this.name = name;
		this.prefix = prefix;
		this.namespace = namespace;
		this.value = value;
	}

	public String type() {
		return this.type;
	}

	public String name() {
		return this.name;
	}

	public String prefix() {
		return this.prefix;
	}

	public String namespace() {
		return this.namespace;
	}

	public String value() {
		return this.value;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o != null && this.getClass() == o.getClass()) {
			XmlAttribute that = (XmlAttribute)o;
			if (!this.name.equals(that.name)) {
				return false;
			} else if (this.namespace != null ? this.namespace.equals(that.namespace) : that.namespace == null) {
				if (this.prefix != null ? this.prefix.equals(that.prefix) : that.prefix == null) {
					if (this.type != null ? this.type.equals(that.type) : that.type == null) {
						return this.value != null ? this.value.equals(that.value) : that.value == null;
					} else {
						return false;
					}
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
		int result = this.type != null ? this.type.hashCode() : 0;
		result = 31 * result + this.name.hashCode();
		result = 31 * result + (this.prefix != null ? this.prefix.hashCode() : 0);
		result = 31 * result + (this.namespace != null ? this.namespace.hashCode() : 0);
		return 31 * result + (this.value != null ? this.value.hashCode() : 0);
	}

	public String toString() {
		return "XmlAttribute{type='"
			+ this.type
			+ '\''
			+ ", name='"
			+ this.name
			+ '\''
			+ ", prefix='"
			+ this.prefix
			+ '\''
			+ ", namespace='"
			+ this.namespace
			+ '\''
			+ ", value='"
			+ this.value
			+ '\''
			+ '}';
	}
}
