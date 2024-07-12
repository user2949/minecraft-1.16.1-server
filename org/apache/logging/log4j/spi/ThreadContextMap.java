package org.apache.logging.log4j.spi;

import java.util.Map;

public interface ThreadContextMap {
	void clear();

	boolean containsKey(String string);

	String get(String string);

	Map<String, String> getCopy();

	Map<String, String> getImmutableMapOrNull();

	boolean isEmpty();

	void put(String string1, String string2);

	void remove(String string);
}
