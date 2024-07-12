package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.ints.Int2DoubleMap.Entry;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractInt2DoubleMap extends AbstractInt2DoubleFunction implements Int2DoubleMap, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractInt2DoubleMap() {
	}

	@Override
	public boolean containsValue(double v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(int k) {
		ObjectIterator<Entry> i = this.int2DoubleEntrySet().iterator();

		while (i.hasNext()) {
			if (((Entry)i.next()).getIntKey() == k) {
				return true;
			}
		}

		return false;
	}

	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public IntSet keySet() {
		return new AbstractIntSet() {
			@Override
			public boolean contains(int k) {
				return AbstractInt2DoubleMap.this.containsKey(k);
			}

			public int size() {
				return AbstractInt2DoubleMap.this.size();
			}

			public void clear() {
				AbstractInt2DoubleMap.this.clear();
			}

			@Override
			public IntIterator iterator() {
				return new IntIterator() {
					private final ObjectIterator<Entry> i = Int2DoubleMaps.fastIterator(AbstractInt2DoubleMap.this);

					@Override
					public int nextInt() {
						return ((Entry)this.i.next()).getIntKey();
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
				return AbstractInt2DoubleMap.this.containsValue(k);
			}

			public int size() {
				return AbstractInt2DoubleMap.this.size();
			}

			public void clear() {
				AbstractInt2DoubleMap.this.clear();
			}

			@Override
			public DoubleIterator iterator() {
				return new DoubleIterator() {
					private final ObjectIterator<Entry> i = Int2DoubleMaps.fastIterator(AbstractInt2DoubleMap.this);

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

	public void putAll(Map<? extends Integer, ? extends Double> m) {
		if (m instanceof Int2DoubleMap) {
			ObjectIterator<Entry> i = Int2DoubleMaps.fastIterator((Int2DoubleMap)m);

			while (i.hasNext()) {
				Entry e = (Entry)i.next();
				this.put(e.getIntKey(), e.getDoubleValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Integer, ? extends Double>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Integer, ? extends Double> e = (java.util.Map.Entry<? extends Integer, ? extends Double>)i.next();
				this.put((Integer)e.getKey(), (Double)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry> i = Int2DoubleMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.int2DoubleEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry> i = Int2DoubleMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getIntKey()));
			s.append("=>");
			s.append(String.valueOf(e.getDoubleValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry implements Entry {
		protected int key;
		protected double value;

		public BasicEntry() {
		}

		public BasicEntry(Integer key, Double value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(int key, double value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public int getIntKey() {
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
				return this.key == e.getIntKey() && Double.doubleToLongBits(this.value) == Double.doubleToLongBits(e.getDoubleValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Integer) {
					Object value = e.getValue();
					return value != null && value instanceof Double
						? this.key == (Integer)key && Double.doubleToLongBits(this.value) == Double.doubleToLongBits((Double)value)
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
		protected final Int2DoubleMap map;

		public BasicEntrySet(Int2DoubleMap map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				int k = e.getIntKey();
				return this.map.containsKey(k) && Double.doubleToLongBits(this.map.get(k)) == Double.doubleToLongBits(e.getDoubleValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Integer) {
					int k = (Integer)key;
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
				return this.map.remove(e.getIntKey(), e.getDoubleValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Integer) {
					int k = (Integer)key;
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
