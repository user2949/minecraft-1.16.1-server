package io.netty.handler.codec.xml;

import java.util.LinkedList;
import java.util.List;

public class XmlElementStart extends XmlElement {
	private final List<XmlAttribute> attributes = new LinkedList();

	public XmlElementStart(String name, String namespace, String prefix) {
		super(name, namespace, prefix);
	}

	public List<XmlAttribute> attributes() {
		return this.attributes;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o == null || this.getClass() != o.getClass()) {
			return false;
		} else if (!super.equals(o)) {
			return false;
		} else {
			XmlElementStart that = (XmlElementStart)o;
			return this.attributes != null ? this.attributes.equals(that.attributes) : that.attributes == null;
		}
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		return 31 * result + (this.attributes != null ? this.attributes.hashCode() : 0);
	}

	@Override
	public String toString() {
		return "XmlElementStart{attributes=" + this.attributes + super.toString() + "} ";
	}
}
