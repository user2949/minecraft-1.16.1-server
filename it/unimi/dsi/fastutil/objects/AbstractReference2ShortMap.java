package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.Reference2ShortMap.Entry;
import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractReference2ShortMap<K> extends AbstractReference2ShortFunction<K> implements Reference2ShortMap<K>, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractReference2ShortMap() {
	}

	@Override
	public boolean containsValue(short v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(Object k) {
		ObjectIterator<Entry<K>> i = this.reference2ShortEntrySet().iterator();

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
	public ReferenceSet<K> keySet() {
		return new AbstractReferenceSet<K>() {
			public boolean contains(Object k) {
				return AbstractReference2ShortMap.this.containsKey(k);
			}

			public int size() {
				return AbstractReference2ShortMap.this.size();
			}

			public void clear() {
				AbstractReference2ShortMap.this.clear();
			}

			@Override
			public ObjectIterator<K> iterator() {
				return new ObjectIterator<K>() {
					private final ObjectIterator<Entry<K>> i = Reference2ShortMaps.fastIterator(AbstractReference2ShortMap.this);

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
	public ShortCollection values() {
		return new AbstractShortCollection() {
			@Override
			public boolean contains(short k) {
				return AbstractReference2ShortMap.this.containsValue(k);
			}

			public int size() {
				return AbstractReference2ShortMap.this.size();
			}

			public void clear() {
				AbstractReference2ShortMap.this.clear();
			}

			@Override
			public ShortIterator iterator() {
				return new ShortIterator() {
					private final ObjectIterator<Entry<K>> i = Reference2ShortMaps.fastIterator(AbstractReference2ShortMap.this);

					@Override
					public short nextShort() {
						return ((Entry)this.i.next()).getShortValue();
					}

					public boolean hasNext() {
						return this.i.hasNext();
					}
				};
			}
		};
	}

	public void putAll(Map<? extends K, ? extends Short> m) {
		if (m instanceof Reference2ShortMap) {
			ObjectIterator<Entry<K>> i = Reference2ShortMaps.fastIterator((Reference2ShortMap<K>)m);

			while (i.hasNext()) {
				Entry<? extends K> e = (Entry<? extends K>)i.next();
				this.put((K)e.getKey(), e.getShortValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends K, ? extends Short>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends K, ? extends Short> e = (java.util.Map.Entry<? extends K, ? extends Short>)i.next();
				this.put((K)e.getKey(), (Short)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry<K>> i = Reference2ShortMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.reference2ShortEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry<K>> i = Reference2ShortMaps.fastIterator(this);
		int n = this.size();
		boolean first = true;
		s.append("{");

		while (n-- != 0) {
			if (first) {
				first = false;
			} else {
				s.append(", ");
			}

			Entry<K> e = (Entry<K>)i.next();
			if (this == e.getKey()) {
				s.append("(this map)");
			} else {
				s.append(String.valueOf(e.getKey()));
			}

			s.append("=>");
			s.append(String.valueOf(e.getShortValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry<K> implements Entry<K> {
		protected K key;
		protected short value;

		public BasicEntry() {
		}

		public BasicEntry(K key, Short value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(K key, short value) {
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return this.key;
		}

		@Override
		public short getShortValue() {
			return this.value;
		}

		@Override
		public short setValue(short value) {
			throw new UnsupportedOperationException();
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry<K> e = (Entry<K>)o;
				return this.key == e.getKey() && this.value == e.getShortValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				Object value = e.getValue();
				return value != null && value instanceof Short ? this.key == key && this.value == (Short)value : false;
			}
		}

		public int hashCode() {
			return System.identityHashCode(this.key) ^ this.value;
		}

		public String toString() {
			return this.key + "->" + this.value;
		}
	}

	public abstract static class BasicEntrySet<K> extends AbstractObjectSet<Entry<K>> {
		protected final Reference2ShortMap<K> map;

		public BasicEntrySet(Reference2ShortMap<K> map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry<K> e = (Entry<K>)o;
				K k = (K)e.getKey();
				return this.map.containsKey(k) && this.map.getShort(k) == e.getShortValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object k = e.getKey();
				Object value = e.getValue();
				return value != null && value instanceof Short ? this.map.containsKey(k) && this.map.getShort(k) == (Short)value : false;
			}
		}

		public boolean remove(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry<K> e = (Entry<K>)o;
				return this.map.remove(e.getKey(), e.getShortValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object k = e.getKey();
				Object value = e.getValue();
				if (value != null && value instanceof Short) {
					short v = (Short)value;
					return this.map.remove(k, v);
				} else {
					return false;
				}
			}
		}

		public int size() {
			return this.map.size();
		}
	}
}
