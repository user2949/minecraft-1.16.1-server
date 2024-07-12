package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.Double2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractDouble2ObjectMap<V> extends AbstractDouble2ObjectFunction<V> implements Double2ObjectMap<V>, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractDouble2ObjectMap() {
	}

	public boolean containsValue(Object v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(double k) {
		ObjectIterator<Entry<V>> i = this.double2ObjectEntrySet().iterator();

		while (i.hasNext()) {
			if (((Entry)i.next()).getDoubleKey() == k) {
				return true;
			}
		}

		return false;
	}

	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public DoubleSet keySet() {
		return new AbstractDoubleSet() {
			@Override
			public boolean contains(double k) {
				return AbstractDouble2ObjectMap.this.containsKey(k);
			}

			public int size() {
				return AbstractDouble2ObjectMap.this.size();
			}

			public void clear() {
				AbstractDouble2ObjectMap.this.clear();
			}

			@Override
			public DoubleIterator iterator() {
				return new DoubleIterator() {
					private final ObjectIterator<Entry<V>> i = Double2ObjectMaps.fastIterator(AbstractDouble2ObjectMap.this);

					@Override
					public double nextDouble() {
						return ((Entry)this.i.next()).getDoubleKey();
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
	public ObjectCollection<V> values() {
		return new AbstractObjectCollection<V>() {
			public boolean contains(Object k) {
				return AbstractDouble2ObjectMap.this.containsValue(k);
			}

			public int size() {
				return AbstractDouble2ObjectMap.this.size();
			}

			public void clear() {
				AbstractDouble2ObjectMap.this.clear();
			}

			@Override
			public ObjectIterator<V> iterator() {
				return new ObjectIterator<V>() {
					private final ObjectIterator<Entry<V>> i = Double2ObjectMaps.fastIterator(AbstractDouble2ObjectMap.this);

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

	public void putAll(Map<? extends Double, ? extends V> m) {
		if (m instanceof Double2ObjectMap) {
			ObjectIterator<Entry<V>> i = Double2ObjectMaps.fastIterator((Double2ObjectMap<V>)m);

			while (i.hasNext()) {
				Entry<? extends V> e = (Entry<? extends V>)i.next();
				this.put(e.getDoubleKey(), (V)e.getValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Double, ? extends V>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Double, ? extends V> e = (java.util.Map.Entry<? extends Double, ? extends V>)i.next();
				this.put((Double)e.getKey(), (V)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry<V>> i = Double2ObjectMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.double2ObjectEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry<V>> i = Double2ObjectMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getDoubleKey()));
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
		protected double key;
		protected V value;

		public BasicEntry() {
		}

		public BasicEntry(Double key, V value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(double key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public double getDoubleKey() {
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
				return Double.doubleToLongBits(this.key) == Double.doubleToLongBits(e.getDoubleKey()) && Objects.equals(this.value, e.getValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Double) {
					Object value = e.getValue();
					return Double.doubleToLongBits(this.key) == Double.doubleToLongBits((Double)key) && Objects.equals(this.value, value);
				} else {
					return false;
				}
			}
		}

		public int hashCode() {
			return HashCommon.double2int(this.key) ^ (this.value == null ? 0 : this.value.hashCode());
		}

		public String toString() {
			return this.key + "->" + this.value;
		}
	}

	public abstract static class BasicEntrySet<V> extends AbstractObjectSet<Entry<V>> {
		protected final Double2ObjectMap<V> map;

		public BasicEntrySet(Double2ObjectMap<V> map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry<V> e = (Entry<V>)o;
				double k = e.getDoubleKey();
				return this.map.containsKey(k) && Objects.equals(this.map.get(k), e.getValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Double) {
					double k = (Double)key;
					Object value = e.getValue();
					return this.map.containsKey(k) && Objects.equals(this.map.get(k), value);
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
				return this.map.remove(e.getDoubleKey(), e.getValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Double) {
					double k = (Double)key;
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
