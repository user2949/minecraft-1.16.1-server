package org.apache.logging.log4j.spi;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.apache.logging.log4j.util.ReadOnlyStringMap;
import org.apache.logging.log4j.util.SortedArrayStringMap;
import org.apache.logging.log4j.util.StringMap;

class GarbageFreeSortedArrayThreadContextMap implements ReadOnlyThreadContextMap, ObjectThreadContextMap {
	public static final String INHERITABLE_MAP = "isThreadContextMapInheritable";
	protected static final int DEFAULT_INITIAL_CAPACITY = 16;
	protected static final String PROPERTY_NAME_INITIAL_CAPACITY = "log4j2.ThreadContext.initial.capacity";
	protected final ThreadLocal<StringMap> localMap = this.createThreadLocalMap();

	public GarbageFreeSortedArrayThreadContextMap() {
	}

	private ThreadLocal<StringMap> createThreadLocalMap() {
		PropertiesUtil managerProps = PropertiesUtil.getProperties();
		boolean inheritable = managerProps.getBooleanProperty("isThreadContextMapInheritable");
		return (ThreadLocal<StringMap>)(inheritable ? new InheritableThreadLocal<StringMap>() {
			protected StringMap childValue(StringMap parentValue) {
				return parentValue != null ? GarbageFreeSortedArrayThreadContextMap.this.createStringMap(parentValue) : null;
			}
		} : new ThreadLocal());
	}

	protected StringMap createStringMap() {
		return new SortedArrayStringMap(PropertiesUtil.getProperties().getIntegerProperty("log4j2.ThreadContext.initial.capacity", 16));
	}

	protected StringMap createStringMap(ReadOnlyStringMap original) {
		return new SortedArrayStringMap(original);
	}

	private StringMap getThreadLocalMap() {
		StringMap map = (StringMap)this.localMap.get();
		if (map == null) {
			map = this.createStringMap();
			this.localMap.set(map);
		}

		return map;
	}

	@Override
	public void put(String key, String value) {
		this.getThreadLocalMap().putValue(key, value);
	}

	@Override
	public void putValue(String key, Object value) {
		this.getThreadLocalMap().putValue(key, value);
	}

	@Override
	public void putAll(Map<String, String> values) {
		if (values != null && !values.isEmpty()) {
			StringMap map = this.getThreadLocalMap();

			for (Entry<String, String> entry : values.entrySet()) {
				map.putValue((String)entry.getKey(), entry.getValue());
			}
		}
	}

	@Override
	public <V> void putAllValues(Map<String, V> values) {
		if (values != null && !values.isEmpty()) {
			StringMap map = this.getThreadLocalMap();

			for (Entry<String, V> entry : values.entrySet()) {
				map.putValue((String)entry.getKey(), entry.getValue());
			}
		}
	}

	@Override
	public String get(String key) {
		return (String)this.getValue(key);
	}

	@Override
	public Object getValue(String key) {
		StringMap map = (StringMap)this.localMap.get();
		return map == null ? null : map.getValue(key);
	}

	@Override
	public void remove(String key) {
		StringMap map = (StringMap)this.localMap.get();
		if (map != null) {
			map.remove(key);
		}
	}

	@Override
	public void removeAll(Iterable<String> keys) {
		StringMap map = (StringMap)this.localMap.get();
		if (map != null) {
			for (String key : keys) {
				map.remove(key);
			}
		}
	}

	@Override
	public void clear() {
		StringMap map = (StringMap)this.localMap.get();
		if (map != null) {
			map.clear();
		}
	}

	@Override
	public boolean containsKey(String key) {
		StringMap map = (StringMap)this.localMap.get();
		return map != null && map.containsKey(key);
	}

	@Override
	public Map<String, String> getCopy() {
		StringMap map = (StringMap)this.localMap.get();
		return (Map<String, String>)(map == null ? new HashMap() : map.toMap());
	}

	@Override
	public StringMap getReadOnlyContextData() {
		StringMap map = (StringMap)this.localMap.get();
		if (map == null) {
			map = this.createStringMap();
			this.localMap.set(map);
		}

		return map;
	}

	@Override
	public Map<String, String> getImmutableMapOrNull() {
		StringMap map = (StringMap)this.localMap.get();
		return map == null ? null : Collections.unmodifiableMap(map.toMap());
	}

	@Override
	public boolean isEmpty() {
		StringMap map = (StringMap)this.localMap.get();
		return map == null || map.size() == 0;
	}

	public String toString() {
		StringMap map = (StringMap)this.localMap.get();
		return map == null ? "{}" : map.toString();
	}

	public int hashCode() {
		int prime = 31;
		int result = 1;
		StringMap map = (StringMap)this.localMap.get();
		return 31 * result + (map == null ? 0 : map.hashCode());
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (!(obj instanceof ThreadContextMap)) {
			return false;
		} else {
			ThreadContextMap other = (ThreadContextMap)obj;
			Map<String, String> map = this.getImmutableMapOrNull();
			Map<String, String> otherMap = other.getImmutableMapOrNull();
			if (map == null) {
				if (otherMap != null) {
					return false;
				}
			} else if (!map.equals(otherMap)) {
				return false;
			}

			return true;
		}
	}
}
