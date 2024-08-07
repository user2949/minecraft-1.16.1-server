package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.Float2ReferenceMap.Entry;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractFloat2ReferenceMap<V> extends AbstractFloat2ReferenceFunction<V> implements Float2ReferenceMap<V>, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractFloat2ReferenceMap() {
	}

	public boolean containsValue(Object v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(float k) {
		ObjectIterator<Entry<V>> i = this.float2ReferenceEntrySet().iterator();

		while (i.hasNext()) {
			if (((Entry)i.next()).getFloatKey() == k) {
				return true;
			}
		}

		return false;
	}

	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public FloatSet keySet() {
		return new AbstractFloatSet() {
			@Override
			public boolean contains(float k) {
				return AbstractFloat2ReferenceMap.this.containsKey(k);
			}

			public int size() {
				return AbstractFloat2ReferenceMap.this.size();
			}

			public void clear() {
				AbstractFloat2ReferenceMap.this.clear();
			}

			@Override
			public FloatIterator iterator() {
				return new FloatIterator() {
					private final ObjectIterator<Entry<V>> i = Float2ReferenceMaps.fastIterator(AbstractFloat2ReferenceMap.this);

					@Override
					public float nextFloat() {
						return ((Entry)this.i.next()).getFloatKey();
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
				return AbstractFloat2ReferenceMap.this.containsValue(k);
			}

			public int size() {
				return AbstractFloat2ReferenceMap.this.size();
			}

			public void clear() {
				AbstractFloat2ReferenceMap.this.clear();
			}

			@Override
			public ObjectIterator<V> iterator() {
				return new ObjectIterator<V>() {
					private final ObjectIterator<Entry<V>> i = Float2ReferenceMaps.fastIterator(AbstractFloat2ReferenceMap.this);

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

	public void putAll(Map<? extends Float, ? extends V> m) {
		if (m instanceof Float2ReferenceMap) {
			ObjectIterator<Entry<V>> i = Float2ReferenceMaps.fastIterator((Float2ReferenceMap<V>)m);

			while (i.hasNext()) {
				Entry<? extends V> e = (Entry<? extends V>)i.next();
				this.put(e.getFloatKey(), (V)e.getValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Float, ? extends V>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Float, ? extends V> e = (java.util.Map.Entry<? extends Float, ? extends V>)i.next();
				this.put((Float)e.getKey(), (V)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry<V>> i = Float2ReferenceMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.float2ReferenceEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry<V>> i = Float2ReferenceMaps.fastIterator(this);
		int n = this.size();
		boolean first = true;
		s.append("{");

		while (n-- != 0) {
			if (first) {
				first = false;
			} else {
				s.append(", ");
			}

			Entry<V> e = (Entry<V>)i.next();
			s.append(String.valueOf(e.getFloatKey()));
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

	public static class BasicEntry<V> implements Entry<V> {
		protected float key;
		protected V value;

		public BasicEntry() {
		}

		public BasicEntry(Float key, V value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(float key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public float getFloatKey() {
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
				Entry<V> e = (Entry<V>)o;
				return Float.floatToIntBits(this.key) == Float.floatToIntBits(e.getFloatKey()) && this.value == e.getValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Float) {
					Object value = e.getValue();
					return Float.floatToIntBits(this.key) == Float.floatToIntBits((Float)key) && this.value == value;
				} else {
					return false;
				}
			}
		}

		public int hashCode() {
			return HashCommon.float2int(this.key) ^ (this.value == null ? 0 : System.identityHashCode(this.value));
		}

		public String toString() {
			return this.key + "->" + this.value;
		}
	}

	public abstract static class BasicEntrySet<V> extends AbstractObjectSet<Entry<V>> {
		protected final Float2ReferenceMap<V> map;

		public BasicEntrySet(Float2ReferenceMap<V> map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry<V> e = (Entry<V>)o;
				float k = e.getFloatKey();
				return this.map.containsKey(k) && this.map.get(k) == e.getValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Float) {
					float k = (Float)key;
					Object value = e.getValue();
					return this.map.containsKey(k) && this.map.get(k) == value;
				} else {
					return false;
				}
			}
		}

		public boolean remove(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry<V> e = (Entry<V>)o;
				return this.map.remove(e.getFloatKey(), e.getValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Float) {
					float k = (Float)key;
					Object v = e.getValue();
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
