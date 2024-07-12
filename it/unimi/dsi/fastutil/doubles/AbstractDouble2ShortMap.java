package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.Double2ShortMap.Entry;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractDouble2ShortMap extends AbstractDouble2ShortFunction implements Double2ShortMap, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractDouble2ShortMap() {
	}

	@Override
	public boolean containsValue(short v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(double k) {
		ObjectIterator<Entry> i = this.double2ShortEntrySet().iterator();

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
				return AbstractDouble2ShortMap.this.containsKey(k);
			}

			public int size() {
				return AbstractDouble2ShortMap.this.size();
			}

			public void clear() {
				AbstractDouble2ShortMap.this.clear();
			}

			@Override
			public DoubleIterator iterator() {
				return new DoubleIterator() {
					private final ObjectIterator<Entry> i = Double2ShortMaps.fastIterator(AbstractDouble2ShortMap.this);

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
	public ShortCollection values() {
		return new AbstractShortCollection() {
			@Override
			public boolean contains(short k) {
				return AbstractDouble2ShortMap.this.containsValue(k);
			}

			public int size() {
				return AbstractDouble2ShortMap.this.size();
			}

			public void clear() {
				AbstractDouble2ShortMap.this.clear();
			}

			@Override
			public ShortIterator iterator() {
				return new ShortIterator() {
					private final ObjectIterator<Entry> i = Double2ShortMaps.fastIterator(AbstractDouble2ShortMap.this);

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

	public void putAll(Map<? extends Double, ? extends Short> m) {
		if (m instanceof Double2ShortMap) {
			ObjectIterator<Entry> i = Double2ShortMaps.fastIterator((Double2ShortMap)m);

			while (i.hasNext()) {
				Entry e = (Entry)i.next();
				this.put(e.getDoubleKey(), e.getShortValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Double, ? extends Short>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Double, ? extends Short> e = (java.util.Map.Entry<? extends Double, ? extends Short>)i.next();
				this.put((Double)e.getKey(), (Short)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry> i = Double2ShortMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.double2ShortEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry> i = Double2ShortMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getShortValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry implements Entry {
		protected double key;
		protected short value;

		public BasicEntry() {
		}

		public BasicEntry(Double key, Short value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(double key, short value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public double getDoubleKey() {
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
				Entry e = (Entry)o;
				return Double.doubleToLongBits(this.key) == Double.doubleToLongBits(e.getDoubleKey()) && this.value == e.getShortValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Double) {
					Object value = e.getValue();
					return value != null && value instanceof Short
						? Double.doubleToLongBits(this.key) == Double.doubleToLongBits((Double)key) && this.value == (Short)value
						: false;
				} else {
					return false;
				}
			}
		}

		public int hashCode() {
			return HashCommon.double2int(this.key) ^ this.value;
		}

		public String toString() {
			return this.key + "->" + this.value;
		}
	}

	public abstract static class BasicEntrySet extends AbstractObjectSet<Entry> {
		protected final Double2ShortMap map;

		public BasicEntrySet(Double2ShortMap map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				double k = e.getDoubleKey();
				return this.map.containsKey(k) && this.map.get(k) == e.getShortValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Double) {
					double k = (Double)key;
					Object value = e.getValue();
					return value != null && value instanceof Short ? this.map.containsKey(k) && this.map.get(k) == (Short)value : false;
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
				return this.map.remove(e.getDoubleKey(), e.getShortValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Double) {
					double k = (Double)key;
					Object value = e.getValue();
					if (value != null && value instanceof Short) {
						short v = (Short)value;
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
