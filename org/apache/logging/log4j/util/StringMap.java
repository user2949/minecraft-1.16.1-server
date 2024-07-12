package org.apache.logging.log4j.util;

public interface StringMap extends ReadOnlyStringMap {
	void clear();

	boolean equals(Object object);

	void freeze();

	int hashCode();

	boolean isFrozen();

	void putAll(ReadOnlyStringMap readOnlyStringMap);

	void putValue(String string, Object object);

	void remove(String string);
}
