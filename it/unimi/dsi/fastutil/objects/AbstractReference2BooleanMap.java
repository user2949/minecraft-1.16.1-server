package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.objects.Reference2BooleanMap.Entry;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractReference2BooleanMap<K> extends AbstractReference2BooleanFunction<K> implements Reference2BooleanMap<K>, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractReference2BooleanMap() {
	}

	@Override
	public boolean containsValue(boolean v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(Object k) {
		ObjectIterator<Entry<K>> i = this.reference2BooleanEntrySet().iterator();

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
				return AbstractReference2BooleanMap.this.containsKey(k);
			}

			public int size() {
				return AbstractReference2BooleanMap.this.size();
			}

			public void clear() {
				AbstractReference2BooleanMap.this.clear();
			}

			@Override
			public ObjectIterator<K> iterator() {
				return new ObjectIterator<K>() {
					private final ObjectIterator<Entry<K>> i = Reference2BooleanMaps.fastIterator(AbstractReference2BooleanMap.this);

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
	public BooleanCollection values() {
		return new AbstractBooleanCollection() {
			@Override
			public boolean contains(boolean k) {
				return AbstractReference2BooleanMap.this.containsValue(k);
			}

			public int size() {
				return AbstractReference2BooleanMap.this.size();
			}

			public void clear() {
				AbstractReference2BooleanMap.this.clear();
			}

			@Override
			public BooleanIterator iterator() {
				return new BooleanIterator() {
					private final ObjectIterator<Entry<K>> i = Reference2BooleanMaps.fastIterator(AbstractReference2BooleanMap.this);

					@Override
					public boolean nextBoolean() {
						return ((Entry)this.i.next()).getBooleanValue();
					}

					public boolean hasNext() {
						return this.i.hasNext();
					}
				};
			}
		};
	}

	public void putAll(Map<? extends K, ? extends Boolean> m) {
		if (m instanceof Reference2BooleanMap) {
			ObjectIterator<Entry<K>> i = Reference2BooleanMaps.fastIterator((Reference2BooleanMap<K>)m);

			while (i.hasNext()) {
				Entry<? extends K> e = (Entry<? extends K>)i.next();
				this.put((K)e.getKey(), e.getBooleanValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends K, ? extends Boolean>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends K, ? extends Boolean> e = (java.util.Map.Entry<? extends K, ? extends Boolean>)i.next();
				this.put((K)e.getKey(), (Boolean)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry<K>> i = Reference2BooleanMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.reference2BooleanEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry<K>> i = Reference2BooleanMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getBooleanValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry<K> implements Entry<K> {
		protected K key;
		protected boolean value;

		public BasicEntry() {
		}

		public BasicEntry(K key, Boolean value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(K key, boolean value) {
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return this.key;
		}

		@Override
		public boolean getBooleanValue() {
			return this.value;
		}

		@Override
		public boolean setValue(boolean value) {
			throw new UnsupportedOperationException();
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry<K> e = (Entry<K>)o;
				return this.key == e.getKey() && this.value == e.getBooleanValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				Object value = e.getValue();
				return value != null && value instanceof Boolean ? this.key == key && this.value == (Boolean)value : false;
			}
		}

		public int hashCode() {
			return System.identityHashCode(this.key) ^ (this.value ? 1231 : 1237);
		}

		public String toString() {
			return this.key + "->" + this.value;
		}
	}

	public abstract static class BasicEntrySet<K> extends AbstractObjectSet<Entry<K>> {
		protected final Reference2BooleanMap<K> map;

		public BasicEntrySet(Reference2BooleanMap<K> map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry<K> e = (Entry<K>)o;
				K k = (K)e.getKey();
				return this.map.containsKey(k) && this.map.getBoolean(k) == e.getBooleanValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object k = e.getKey();
				Object value = e.getValue();
				return value != null && value instanceof Boolean ? this.map.containsKey(k) && this.map.getBoolean(k) == (Boolean)value : false;
			}
		}

		public boolean remove(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry<K> e = (Entry<K>)o;
				return this.map.remove(e.getKey(), e.getBooleanValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object k = e.getKey();
				Object value = e.getValue();
				if (value != null && value instanceof Boolean) {
					boolean v = (Boolean)value;
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
