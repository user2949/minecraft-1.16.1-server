package io.netty.handler.codec.xml;

import java.util.LinkedList;
import java.util.List;

public abstract class XmlElement {
	private final String name;
	private final String namespace;
	private final String prefix;
	private final List<XmlNamespace> namespaces = new LinkedList();

	protected XmlElement(String name, String namespace, String prefix) {
		this.name = name;
		this.namespace = namespace;
		this.prefix = prefix;
	}

	public String name() {
		return this.name;
	}

	public String namespace() {
		return this.namespace;
	}

	public String prefix() {
		return this.prefix;
	}

	public List<XmlNamespace> namespaces() {
		return this.namespaces;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o != null && this.getClass() == o.getClass()) {
			XmlElement that = (XmlElement)o;
			if (!this.name.equals(that.name)) {
				return false;
			} else if (this.namespace != null ? this.namespace.equals(that.namespace) : that.namespace == null) {
				if (this.namespaces != null ? this.namespaces.equals(that.namespaces) : that.namespaces == null) {
					return this.prefix != null ? this.prefix.equals(that.prefix) : that.prefix == null;
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
		int result = this.name.hashCode();
		result = 31 * result + (this.namespace != null ? this.namespace.hashCode() : 0);
		result = 31 * result + (this.prefix != null ? this.prefix.hashCode() : 0);
		return 31 * result + (this.namespaces != null ? this.namespaces.hashCode() : 0);
	}

	public String toString() {
		return ", name='" + this.name + '\'' + ", namespace='" + this.namespace + '\'' + ", prefix='" + this.prefix + '\'' + ", namespaces=" + this.namespaces;
	}
}
