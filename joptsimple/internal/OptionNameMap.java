package joptsimple.internal;

import java.util.Map;

public interface OptionNameMap<V> {
	boolean contains(String string);

	V get(String string);

	void put(String string, V object);

	void putAll(Iterable<String> iterable, V object);

	void remove(String string);

	Map<String, V> toJavaUtilMap();
}
