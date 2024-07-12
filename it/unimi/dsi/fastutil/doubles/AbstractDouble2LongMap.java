package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.Double2LongMap.Entry;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractDouble2LongMap extends AbstractDouble2LongFunction implements Double2LongMap, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractDouble2LongMap() {
	}

	@Override
	public boolean containsValue(long v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(double k) {
		ObjectIterator<Entry> i = this.double2LongEntrySet().iterator();

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
				return AbstractDouble2LongMap.this.containsKey(k);
			}

			public int size() {
				return AbstractDouble2LongMap.this.size();
			}

			public void clear() {
				AbstractDouble2LongMap.this.clear();
			}

			@Override
			public DoubleIterator iterator() {
				return new DoubleIterator() {
					private final ObjectIterator<Entry> i = Double2LongMaps.fastIterator(AbstractDouble2LongMap.this);

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
	public LongCollection values() {
		return new AbstractLongCollection() {
			@Override
			public boolean contains(long k) {
				return AbstractDouble2LongMap.this.containsValue(k);
			}

			public int size() {
				return AbstractDouble2LongMap.this.size();
			}

			public void clear() {
				AbstractDouble2LongMap.this.clear();
			}

			@Override
			public LongIterator iterator() {
				return new LongIterator() {
					private final ObjectIterator<Entry> i = Double2LongMaps.fastIterator(AbstractDouble2LongMap.this);

					@Override
					public long nextLong() {
						return ((Entry)this.i.next()).getLongValue();
					}

					public boolean hasNext() {
						return this.i.hasNext();
					}
				};
			}
		};
	}

	public void putAll(Map<? extends Double, ? extends Long> m) {
		if (m instanceof Double2LongMap) {
			ObjectIterator<Entry> i = Double2LongMaps.fastIterator((Double2LongMap)m);

			while (i.hasNext()) {
				Entry e = (Entry)i.next();
				this.put(e.getDoubleKey(), e.getLongValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Double, ? extends Long>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Double, ? extends Long> e = (java.util.Map.Entry<? extends Double, ? extends Long>)i.next();
				this.put((Double)e.getKey(), (Long)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry> i = Double2LongMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.double2LongEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry> i = Double2LongMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getDoubleKey()));
			s.append("=>");
			s.append(String.valueOf(e.getLongValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry implements Entry {
		protected double key;
		protected long value;

		public BasicEntry() {
		}

		public BasicEntry(Double key, Long value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(double key, long value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public double getDoubleKey() {
			return this.key;
		}

		@Override
		public long getLongValue() {
			return this.value;
		}

		@Override
		public long setValue(long value) {
			throw new UnsupportedOperationException();
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				return Double.doubleToLongBits(this.key) == Double.doubleToLongBits(e.getDoubleKey()) && this.value == e.getLongValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Double) {
					Object value = e.getValue();
					return value != null && value instanceof Long
						? Double.doubleToLongBits(this.key) == Double.doubleToLongBits((Double)key) && this.value == (Long)value
						: false;
				} else {
					return false;
				}
			}
		}

		public int hashCode() {
			return HashCommon.double2int(this.key) ^ HashCommon.long2int(this.value);
		}

		public String toString() {
			return this.key + "->" + this.value;
		}
	}

	public abstract static class BasicEntrySet extends AbstractObjectSet<Entry> {
		protected final Double2LongMap map;

		public BasicEntrySet(Double2LongMap map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				double k = e.getDoubleKey();
				return this.map.containsKey(k) && this.map.get(k) == e.getLongValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Double) {
					double k = (Double)key;
					Object value = e.getValue();
					return value != null && value instanceof Long ? this.map.containsKey(k) && this.map.get(k) == (Long)value : false;
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
				return this.map.remove(e.getDoubleKey(), e.getLongValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Double) {
					double k = (Double)key;
					Object value = e.getValue();
					if (value != null && value instanceof Long) {
						long v = (Long)value;
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
