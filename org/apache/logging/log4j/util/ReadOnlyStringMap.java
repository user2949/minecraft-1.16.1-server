package org.apache.logging.log4j.util;

import java.io.Serializable;
import java.util.Map;

public interface ReadOnlyStringMap extends Serializable {
	Map<String, String> toMap();

	boolean containsKey(String string);

	<V> void forEach(BiConsumer<String, ? super V> biConsumer);

	<V, S> void forEach(TriConsumer<String, ? super V, S> triConsumer, S object);

	<V> V getValue(String string);

	boolean isEmpty();

	int size();
}
