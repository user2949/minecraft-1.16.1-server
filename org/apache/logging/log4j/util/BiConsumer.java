package org.apache.logging.log4j.util;

public interface BiConsumer<K, V> {
	void accept(K object1, V object2);
}
