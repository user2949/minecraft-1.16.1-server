package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.Object2ReferenceMap.Entry;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractObject2ReferenceMap<K, V> extends AbstractObject2ReferenceFunction<K, V> implements Object2ReferenceMap<K, V>, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractObject2ReferenceMap() {
	}

	public boolean containsValue(Object v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(Object k) {
		ObjectIterator<Entry<K, V>> i = this.object2ReferenceEntrySet().iterator();

		while (i.hasNext()) {
			if (((Entry)i.next()).getKey() == k) {
				return true;
			}
		}

		return false;
	}

	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public ObjectSet<K> keySet() {
		return new AbstractObjectSet<K>() {
			public boolean contains(Object k) {
				return AbstractObject2ReferenceMap.this.containsKey(k);
			}

			public int size() {
				return AbstractObject2ReferenceMap.this.size();
			}

			public void clear() {
				AbstractObject2ReferenceMap.this.clear();
			}

			@Override
			public ObjectIterator<K> iterator() {
				return new ObjectIterator<K>() {
					private final ObjectIterator<Entry<K, V>> i = Object2ReferenceMaps.fastIterator(AbstractObject2ReferenceMap.this);

					public K next() {
						return (K)((Entry)this.i.next()).getKey();
					}

					public boolean hasNext() {
						return this.i.hasNext();
					}

					public void remove() {
						this.i.remove();
					}
				};
			}
		};
	}

	@Override
	public ReferenceCollection<V> values() {
		return new AbstractReferenceCollection<V>() {
			public boolean contains(Object k) {
				return AbstractObject2ReferenceMap.this.containsValue(k);
			}

			public int size() {
				return AbstractObject2ReferenceMap.this.size();
			}

			public void clear() {
				AbstractObject2ReferenceMap.this.clear();
			}

			@Override
			public ObjectIterator<V> iterator() {
				return new ObjectIterator<V>() {
					private final ObjectIterator<Entry<K, V>> i = Object2ReferenceMaps.fastIterator(AbstractObject2ReferenceMap.this);

					public V next() {
						return (V)((Entry)this.i.next()).getValue();
					}

					public boolean hasNext() {
						return this.i.hasNext();
					}
				};
			}
		};
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		if (m instanceof Object2ReferenceMap) {
			ObjectIterator<Entry<K, V>> i = Object2ReferenceMaps.fastIterator((Object2ReferenceMap<K, V>)m);

			while (i.hasNext()) {
				Entry<? extends K, ? extends V> e = (Entry<? extends K, ? extends V>)i.next();
				this.put((K)e.getKey(), (V)e.getValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends K, ? extends V>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends K, ? extends V> e = (java.util.Map.Entry<? extends K, ? extends V>)i.next();
				this.put((K)e.getKey(), (V)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry<K, V>> i = Object2ReferenceMaps.fastIterator(this);

		while (n-- != 0) {
			h += ((Entry)i.next()).hashCode();
		}

		return h;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof Map)) {
			return false;
		} else {
			Map<?, ?> m = (Map<?, ?>)o;
			return m.size() != this.size() ? false : this.object2ReferenceEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry<K, V>> i = Object2ReferenceMaps.fastIterator(this);
		int n = this.size();
		boolean first = true;
		s.append("{");

		while (n-- != 0) {
			if (first) {
				first = false;
			} else {
				s.append(", ");
			}

			Entry<K, V> e = (Entry<K, V>)i.next();
			if (this == e.getKey()) {
				s.append("(this map)");
			} else {
				s.append(String.valueOf(e.getKey()));
			}

			s.append("=>");
			if (this == e.getValue()) {
				s.append("(this map)");
			} else {
				s.append(String.valueOf(e.getValue()));
			}
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry<K, V> implements Entry<K, V> {
		protected K key;
		protected V value;

		public BasicEntry() {
		}

		public BasicEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return this.key;
		}

		public V getValue() {
			return this.value;
		}

		public V setValue(V value) {
			throw new UnsupportedOperationException();
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry<K, V> e = (Entry<K, V>)o;
				return Objects.equals(this.key, e.getKey()) && this.value == e.getValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				Object value = e.getValue();
				return Objects.equals(this.key, key) && this.value == value;
			}
		}

		public int hashCode() {
			return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : System.identityHashCode(this.value));
		}

		public String toString() {
			return this.key + "->" + this.value;
		}
	}

	public abstract static class BasicEntrySet<K, V> extends AbstractObjectSet<Entry<K, V>> {
		protected final Object2ReferenceMap<K, V> map;

		public BasicEntrySet(Object2ReferenceMap<K, V> map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry<K, V> e = (Entry<K, V>)o;
				K k = (K)e.getKey();
				return this.map.containsKey(k) && this.map.get(k) == e.getValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object k = e.getKey();
				Object value = e.getValue();
				return this.map.containsKey(k) && this.map.get(k) == value;
			}
		}

		public boolean remove(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry<K, V> e = (Entry<K, V>)o;
				return this.map.remove(e.getKey(), e.getValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object k = e.getKey();
				Object v = e.getValue();
				return this.map.remove(k, v);
			}
		}

		public int size() {
			return this.map.size();
		}
	}
}
