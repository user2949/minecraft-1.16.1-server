package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.floats.Float2DoubleMap.Entry;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractFloat2DoubleMap extends AbstractFloat2DoubleFunction implements Float2DoubleMap, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractFloat2DoubleMap() {
	}

	@Override
	public boolean containsValue(double v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(float k) {
		ObjectIterator<Entry> i = this.float2DoubleEntrySet().iterator();

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
				return AbstractFloat2DoubleMap.this.containsKey(k);
			}

			public int size() {
				return AbstractFloat2DoubleMap.this.size();
			}

			public void clear() {
				AbstractFloat2DoubleMap.this.clear();
			}

			@Override
			public FloatIterator iterator() {
				return new FloatIterator() {
					private final ObjectIterator<Entry> i = Float2DoubleMaps.fastIterator(AbstractFloat2DoubleMap.this);

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
	public DoubleCollection values() {
		return new AbstractDoubleCollection() {
			@Override
			public boolean contains(double k) {
				return AbstractFloat2DoubleMap.this.containsValue(k);
			}

			public int size() {
				return AbstractFloat2DoubleMap.this.size();
			}

			public void clear() {
				AbstractFloat2DoubleMap.this.clear();
			}

			@Override
			public DoubleIterator iterator() {
				return new DoubleIterator() {
					private final ObjectIterator<Entry> i = Float2DoubleMaps.fastIterator(AbstractFloat2DoubleMap.this);

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

	public void putAll(Map<? extends Float, ? extends Double> m) {
		if (m instanceof Float2DoubleMap) {
			ObjectIterator<Entry> i = Float2DoubleMaps.fastIterator((Float2DoubleMap)m);

			while (i.hasNext()) {
				Entry e = (Entry)i.next();
				this.put(e.getFloatKey(), e.getDoubleValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Float, ? extends Double>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Float, ? extends Double> e = (java.util.Map.Entry<? extends Float, ? extends Double>)i.next();
				this.put((Float)e.getKey(), (Double)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry> i = Float2DoubleMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.float2DoubleEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry> i = Float2DoubleMaps.fastIterator(this);
		int n = this.size();
		boolean first = true;
		s.append("{");

		while (n-- != 0) {
			if (first) {
				first = false;
			} else {
				s.append(", ");
			}

			Entry e = (Entry)i.next();
			s.append(String.valueOf(e.getFloatKey()));
			s.append("=>");
			s.append(String.valueOf(e.getDoubleValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry implements Entry {
		protected float key;
		protected double value;

		public BasicEntry() {
		}

		public BasicEntry(Float key, Double value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(float key, double value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public float getFloatKey() {
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
				Entry e = (Entry)o;
				return Float.floatToIntBits(this.key) == Float.floatToIntBits(e.getFloatKey())
					&& Double.doubleToLongBits(this.value) == Double.doubleToLongBits(e.getDoubleValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Float) {
					Object value = e.getValue();
					return value != null && value instanceof Double
						? Float.floatToIntBits(this.key) == Float.floatToIntBits((Float)key) && Double.doubleToLongBits(this.value) == Double.doubleToLongBits((Double)value)
						: false;
				} else {
					return false;
				}
			}
		}

		public int hashCode() {
			return HashCommon.float2int(this.key) ^ HashCommon.double2int(this.value);
		}

		public String toString() {
			return this.key + "->" + this.value;
		}
	}

	public abstract static class BasicEntrySet extends AbstractObjectSet<Entry> {
		protected final Float2DoubleMap map;

		public BasicEntrySet(Float2DoubleMap map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				float k = e.getFloatKey();
				return this.map.containsKey(k) && Double.doubleToLongBits(this.map.get(k)) == Double.doubleToLongBits(e.getDoubleValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Float) {
					float k = (Float)key;
					Object value = e.getValue();
					return value != null && value instanceof Double
						? this.map.containsKey(k) && Double.doubleToLongBits(this.map.get(k)) == Double.doubleToLongBits((Double)value)
						: false;
				} else {
					return false;
				}
			}
		}

		public boolean remove(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				return this.map.remove(e.getFloatKey(), e.getDoubleValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Float) {
					float k = (Float)key;
					Object value = e.getValue();
					if (value != null && value instanceof Double) {
						double v = (Double)value;
						return this.map.remove(k, v);
					} else {
						return false;
					}
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
