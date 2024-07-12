package org.apache.logging.log4j.spi;

import java.util.Map;

public interface ObjectThreadContextMap extends CleanableThreadContextMap {
	<V> V getValue(String string);

	<V> void putValue(String string, V object);

	<V> void putAllValues(Map<String, V> map);
}
