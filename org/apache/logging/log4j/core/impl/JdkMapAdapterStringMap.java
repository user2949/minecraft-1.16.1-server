package org.apache.logging.log4j.core.impl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.logging.log4j.util.BiConsumer;
import org.apache.logging.log4j.util.ReadOnlyStringMap;
import org.apache.logging.log4j.util.StringMap;
import org.apache.logging.log4j.util.TriConsumer;

class JdkMapAdapterStringMap implements StringMap {
	private static final long serialVersionUID = -7348247784983193612L;
	private static final String FROZEN = "Frozen collection cannot be modified";
	private static final Comparator<? super String> NULL_FIRST_COMPARATOR = new Comparator<String>() {
		public int compare(String left, String right) {
			if (left == null) {
				return -1;
			} else {
				return right == null ? 1 : left.compareTo(right);
			}
		}
	};
	private final Map<String, String> map;
	private boolean immutable = false;
	private transient String[] sortedKeys;
	private static TriConsumer<String, String, Map<String, String>> PUT_ALL = new TriConsumer<String, String, Map<String, String>>() {
		public void accept(String key, String value, Map<String, String> stringStringMap) {
			stringStringMap.put(key, value);
		}
	};

	public JdkMapAdapterStringMap() {
		this(new HashMap());
	}

	public JdkMapAdapterStringMap(Map<String, String> map) {
		this.map = (Map<String, String>)Objects.requireNonNull(map, "map");
	}

	@Override
	public Map<String, String> toMap() {
		return this.map;
	}

	private void assertNotFrozen() {
		if (this.immutable) {
			throw new UnsupportedOperationException("Frozen collection cannot be modified");
		}
	}

	@Override
	public boolean containsKey(String key) {
		return this.map.containsKey(key);
	}

	@Override
	public <V> void forEach(BiConsumer<String, ? super V> action) {
		String[] keys = this.getSortedKeys();

		for (int i = 0; i < keys.length; i++) {
			action.accept(keys[i], (V)this.map.get(keys[i]));
		}
	}

	@Override
	public <V, S> void forEach(TriConsumer<String, ? super V, S> action, S state) {
		String[] keys = this.getSortedKeys();

		for (int i = 0; i < keys.length; i++) {
			action.accept(keys[i], (V)this.map.get(keys[i]), state);
		}
	}

	private String[] getSortedKeys() {
		if (this.sortedKeys == null) {
			this.sortedKeys = (String[])this.map.keySet().toArray(new String[this.map.size()]);
			Arrays.sort(this.sortedKeys, NULL_FIRST_COMPARATOR);
		}

		return this.sortedKeys;
	}

	@Override
	public <V> V getValue(String key) {
		return (V)this.map.get(key);
	}

	@Override
	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	@Override
	public int size() {
		return this.map.size();
	}

	@Override
	public void clear() {
		if (!this.map.isEmpty()) {
			this.assertNotFrozen();
			this.map.clear();
			this.sortedKeys = null;
		}
	}

	@Override
	public void freeze() {
		this.immutable = true;
	}

	@Override
	public boolean isFrozen() {
		return this.immutable;
	}

	@Override
	public void putAll(ReadOnlyStringMap source) {
		this.assertNotFrozen();
		source.forEach(PUT_ALL, this.map);
		this.sortedKeys = null;
	}

	@Override
	public void putValue(String key, Object value) {
		this.assertNotFrozen();
		this.map.put(key, value == null ? null : String.valueOf(value));
		this.sortedKeys = null;
	}

	@Override
	public void remove(String key) {
		if (this.map.containsKey(key)) {
			this.assertNotFrozen();
			this.map.remove(key);
			this.sortedKeys = null;
		}
	}

	public String toString() {
		StringBuilder result = new StringBuilder(this.map.size() * 13);
		result.append('{');
		String[] keys = this.getSortedKeys();

		for (int i = 0; i < keys.length; i++) {
			if (i > 0) {
				result.append(", ");
			}

			result.append(keys[i]).append('=').append((String)this.map.get(keys[i]));
		}

		result.append('}');
		return result.toString();
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		} else if (!(object instanceof JdkMapAdapterStringMap)) {
			return false;
		} else {
			JdkMapAdapterStringMap other = (JdkMapAdapterStringMap)object;
			return this.map.equals(other.map) && this.immutable == other.immutable;
		}
	}

	@Override
	public int hashCode() {
		return this.map.hashCode() + (this.immutable ? 31 : 0);
	}
}