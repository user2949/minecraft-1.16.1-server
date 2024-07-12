package org.apache.logging.log4j.util;

public interface TriConsumer<K, V, S> {
	void accept(K object1, V object2, S object3);
}
