package org.apache.logging.log4j.spi;

import java.util.HashMap;
import java.util.Map;

public class NoOpThreadContextMap implements ThreadContextMap {
	@Override
	public void clear() {
	}

	@Override
	public boolean containsKey(String key) {
		return false;
	}

	@Override
	public String get(String key) {
		return null;
	}

	@Override
	public Map<String, String> getCopy() {
		return new HashMap();
	}

	@Override
	public Map<String, String> getImmutableMapOrNull() {
		return null;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public void put(String key, String value) {
	}

	@Override
	public void remove(String key) {
	}
}