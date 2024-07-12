package io.netty.util;

public interface AttributeMap {
	<T> Attribute<T> attr(AttributeKey<T> attributeKey);

	<T> boolean hasAttr(AttributeKey<T> attributeKey);
}
