package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.bytes.Byte2DoubleMap.Entry;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractByte2DoubleMap extends AbstractByte2DoubleFunction implements Byte2DoubleMap, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractByte2DoubleMap() {
	}

	@Override
	public boolean containsValue(double v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(byte k) {
		ObjectIterator<Entry> i = this.byte2DoubleEntrySet().iterator();

		while (i.hasNext()) {
			if (((Entry)i.next()).getByteKey() == k) {
				return true;
			}
		}

		return false;
	}

	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public ByteSet keySet() {
		return new AbstractByteSet() {
			@Override
			public boolean contains(byte k) {
				return AbstractByte2DoubleMap.this.containsKey(k);
			}

			public int size() {
				return AbstractByte2DoubleMap.this.size();
			}

			public void clear() {
				AbstractByte2DoubleMap.this.clear();
			}

			@Override
			public ByteIterator iterator() {
				return new ByteIterator() {
					private final ObjectIterator<Entry> i = Byte2DoubleMaps.fastIterator(AbstractByte2DoubleMap.this);

					@Override
					public byte nextByte() {
						return ((Entry)this.i.next()).getByteKey();
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
				return AbstractByte2DoubleMap.this.containsValue(k);
			}

			public int size() {
				return AbstractByte2DoubleMap.this.size();
			}

			public void clear() {
				AbstractByte2DoubleMap.this.clear();
			}

			@Override
			public DoubleIterator iterator() {
				return new DoubleIterator() {
					private final ObjectIterator<Entry> i = Byte2DoubleMaps.fastIterator(AbstractByte2DoubleMap.this);

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

	public void putAll(Map<? extends Byte, ? extends Double> m) {
		if (m instanceof Byte2DoubleMap) {
			ObjectIterator<Entry> i = Byte2DoubleMaps.fastIterator((Byte2DoubleMap)m);

			while (i.hasNext()) {
				Entry e = (Entry)i.next();
				this.put(e.getByteKey(), e.getDoubleValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Byte, ? extends Double>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Byte, ? extends Double> e = (java.util.Map.Entry<? extends Byte, ? extends Double>)i.next();
				this.put((Byte)e.getKey(), (Double)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry> i = Byte2DoubleMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.byte2DoubleEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry> i = Byte2DoubleMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getByteKey()));
			s.append("=>");
			s.append(String.valueOf(e.getDoubleValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry implements Entry {
		protected byte key;
		protected double value;

		public BasicEntry() {
		}

		public BasicEntry(Byte key, Double value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(byte key, double value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public byte getByteKey() {
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
				return this.key == e.getByteKey() && Double.doubleToLongBits(this.value) == Double.doubleToLongBits(e.getDoubleValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Byte) {
					Object value = e.getValue();
					return value != null && value instanceof Double
						? this.key == (Byte)key && Double.doubleToLongBits(this.value) == Double.doubleToLongBits((Double)value)
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
		protected final Byte2DoubleMap map;

		public BasicEntrySet(Byte2DoubleMap map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				byte k = e.getByteKey();
				return this.map.containsKey(k) && Double.doubleToLongBits(this.map.get(k)) == Double.doubleToLongBits(e.getDoubleValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Byte) {
					byte k = (Byte)key;
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
				return this.map.remove(e.getByteKey(), e.getDoubleValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Byte) {
					byte k = (Byte)key;
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
