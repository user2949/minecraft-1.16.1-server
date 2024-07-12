package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap.Entry;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractObject2DoubleMap<K> extends AbstractObject2DoubleFunction<K> implements Object2DoubleMap<K>, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractObject2DoubleMap() {
	}

	@Override
	public boolean containsValue(double v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(Object k) {
		ObjectIterator<Entry<K>> i = this.object2DoubleEntrySet().iterator();

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
				return AbstractObject2DoubleMap.this.containsKey(k);
			}

			public int size() {
				return AbstractObject2DoubleMap.this.size();
			}

			public void clear() {
				AbstractObject2DoubleMap.this.clear();
			}

			@Override
			public ObjectIterator<K> iterator() {
				return new ObjectIterator<K>() {
					private final ObjectIterator<Entry<K>> i = Object2DoubleMaps.fastIterator(AbstractObject2DoubleMap.this);

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
	public DoubleCollection values() {
		return new AbstractDoubleCollection() {
			@Override
			public boolean contains(double k) {
				return AbstractObject2DoubleMap.this.containsValue(k);
			}

			public int size() {
				return AbstractObject2DoubleMap.this.size();
			}

			public void clear() {
				AbstractObject2DoubleMap.this.clear();
			}

			@Override
			public DoubleIterator iterator() {
				return new DoubleIterator() {
					private final ObjectIterator<Entry<K>> i = Object2DoubleMaps.fastIterator(AbstractObject2DoubleMap.this);

					@Override
					public double nextDouble() {
						return ((Entry)this.i.next()).getDoubleValue();
					}

					public boolean hasNext() {
						return this.i.hasNext();
					}
				};
			}
		};
	}

	public void putAll(Map<? extends K, ? extends Double> m) {
		if (m instanceof Object2DoubleMap) {
			ObjectIterator<Entry<K>> i = Object2DoubleMaps.fastIterator((Object2DoubleMap<K>)m);

			while (i.hasNext()) {
				Entry<? extends K> e = (Entry<? extends K>)i.next();
				this.put((K)e.getKey(), e.getDoubleValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends K, ? extends Double>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends K, ? extends Double> e = (java.util.Map.Entry<? extends K, ? extends Double>)i.next();
				this.put((K)e.getKey(), (Double)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry<K>> i = Object2DoubleMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.object2DoubleEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry<K>> i = Object2DoubleMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getDoubleValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry<K> implements Entry<K> {
		protected K key;
		protected double value;

		public BasicEntry() {
		}

		public BasicEntry(K key, Double value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(K key, double value) {
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return this.key;
		}

		@Override
		public double getDoubleValue() {
			return this.value;
		}

		@Override
		public double setValue(double value) {
			throw new UnsupportedOperationException();
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry<K> e = (Entry<K>)o;
				return Objects.equals(this.key, e.getKey()) && Double.doubleToLongBits(this.value) == Double.doubleToLongBits(e.getDoubleValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				Object value = e.getValue();
				return value != null && value instanceof Double
					? Objects.equals(this.key, key) && Double.doubleToLongBits(this.value) == Double.doubleToLongBits((Double)value)
					: false;
			}
		}

		public int hashCode() {
			return (this.key == null ? 0 : this.key.hashCode()) ^ HashCommon.double2int(this.value);
		}

		public String toString() {
			return this.key + "->" + this.value;
		}
	}

	public abstract static class BasicEntrySet<K> extends AbstractObjectSet<Entry<K>> {
		protected final Object2DoubleMap<K> map;

		public BasicEntrySet(Object2DoubleMap<K> map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry<K> e = (Entry<K>)o;
				K k = (K)e.getKey();
				return this.map.containsKey(k) && Double.doubleToLongBits(this.map.getDouble(k)) == Double.doubleToLongBits(e.getDoubleValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object k = e.getKey();
				Object value = e.getValue();
				return value != null && value instanceof Double
					? this.map.containsKey(k) && Double.doubleToLongBits(this.map.getDouble(k)) == Double.doubleToLongBits((Double)value)
					: false;
			}
		}

		public boolean remove(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry<K> e = (Entry<K>)o;
				return this.map.remove(e.getKey(), e.getDoubleValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object k = e.getKey();
				Object value = e.getValue();
				if (value != null && value instanceof Double) {
					double v = (Double)value;
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
