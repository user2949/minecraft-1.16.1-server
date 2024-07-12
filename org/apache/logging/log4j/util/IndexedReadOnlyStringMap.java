package org.apache.logging.log4j.util;

public interface IndexedReadOnlyStringMap extends ReadOnlyStringMap {
	String getKeyAt(int integer);

	<V> V getValueAt(int integer);

	int indexOfKey(String string);
}
