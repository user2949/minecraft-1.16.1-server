package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.shorts.Short2DoubleMap.Entry;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractShort2DoubleMap extends AbstractShort2DoubleFunction implements Short2DoubleMap, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractShort2DoubleMap() {
	}

	@Override
	public boolean containsValue(double v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(short k) {
		ObjectIterator<Entry> i = this.short2DoubleEntrySet().iterator();

		while (i.hasNext()) {
			if (((Entry)i.next()).getShortKey() == k) {
				return true;
			}
		}

		return false;
	}

	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public ShortSet keySet() {
		return new AbstractShortSet() {
			@Override
			public boolean contains(short k) {
				return AbstractShort2DoubleMap.this.containsKey(k);
			}

			public int size() {
				return AbstractShort2DoubleMap.this.size();
			}

			public void clear() {
				AbstractShort2DoubleMap.this.clear();
			}

			@Override
			public ShortIterator iterator() {
				return new ShortIterator() {
					private final ObjectIterator<Entry> i = Short2DoubleMaps.fastIterator(AbstractShort2DoubleMap.this);

					@Override
					public short nextShort() {
						return ((Entry)this.i.next()).getShortKey();
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
				return AbstractShort2DoubleMap.this.containsValue(k);
			}

			public int size() {
				return AbstractShort2DoubleMap.this.size();
			}

			public void clear() {
				AbstractShort2DoubleMap.this.clear();
			}

			@Override
			public DoubleIterator iterator() {
				return new DoubleIterator() {
					private final ObjectIterator<Entry> i = Short2DoubleMaps.fastIterator(AbstractShort2DoubleMap.this);

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

	public void putAll(Map<? extends Short, ? extends Double> m) {
		if (m instanceof Short2DoubleMap) {
			ObjectIterator<Entry> i = Short2DoubleMaps.fastIterator((Short2DoubleMap)m);

			while (i.hasNext()) {
				Entry e = (Entry)i.next();
				this.put(e.getShortKey(), e.getDoubleValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Short, ? extends Double>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Short, ? extends Double> e = (java.util.Map.Entry<? extends Short, ? extends Double>)i.next();
				this.put((Short)e.getKey(), (Double)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry> i = Short2DoubleMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.short2DoubleEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry> i = Short2DoubleMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getShortKey()));
			s.append("=>");
			s.append(String.valueOf(e.getDoubleValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry implements Entry {
		protected short key;
		protected double value;

		public BasicEntry() {
		}

		public BasicEntry(Short key, Double value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(short key, double value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public short getShortKey() {
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
				return this.key == e.getShortKey() && Double.doubleToLongBits(this.value) == Double.doubleToLongBits(e.getDoubleValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Short) {
					Object value = e.getValue();
					return value != null && value instanceof Double
						? this.key == (Short)key && Double.doubleToLongBits(this.value) == Double.doubleToLongBits((Double)value)
						: false;
				} else {
					return false;
				}
			}
		}

		public int hashCode() {
			return this.key ^ HashCommon.double2int(this.value);
		}

		public String toString() {
			return this.key + "->" + this.value;
		}
	}

	public abstract static class BasicEntrySet extends AbstractObjectSet<Entry> {
		protected final Short2DoubleMap map;

		public BasicEntrySet(Short2DoubleMap map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				short k = e.getShortKey();
				return this.map.containsKey(k) && Double.doubleToLongBits(this.map.get(k)) == Double.doubleToLongBits(e.getDoubleValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Short) {
					short k = (Short)key;
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
				return this.map.remove(e.getShortKey(), e.getDoubleValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Short) {
					short k = (Short)key;
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
