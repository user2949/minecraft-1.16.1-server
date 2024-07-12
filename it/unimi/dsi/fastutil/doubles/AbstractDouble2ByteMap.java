package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.doubles.Double2ByteMap.Entry;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractDouble2ByteMap extends AbstractDouble2ByteFunction implements Double2ByteMap, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractDouble2ByteMap() {
	}

	@Override
	public boolean containsValue(byte v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(double k) {
		ObjectIterator<Entry> i = this.double2ByteEntrySet().iterator();

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
				return AbstractDouble2ByteMap.this.containsKey(k);
			}

			public int size() {
				return AbstractDouble2ByteMap.this.size();
			}

			public void clear() {
				AbstractDouble2ByteMap.this.clear();
			}

			@Override
			public DoubleIterator iterator() {
				return new DoubleIterator() {
					private final ObjectIterator<Entry> i = Double2ByteMaps.fastIterator(AbstractDouble2ByteMap.this);

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
	public ByteCollection values() {
		return new AbstractByteCollection() {
			@Override
			public boolean contains(byte k) {
				return AbstractDouble2ByteMap.this.containsValue(k);
			}

			public int size() {
				return AbstractDouble2ByteMap.this.size();
			}

			public void clear() {
				AbstractDouble2ByteMap.this.clear();
			}

			@Override
			public ByteIterator iterator() {
				return new ByteIterator() {
					private final ObjectIterator<Entry> i = Double2ByteMaps.fastIterator(AbstractDouble2ByteMap.this);

					@Override
					public byte nextByte() {
						return ((Entry)this.i.next()).getByteValue();
					}

					public boolean hasNext() {
						return this.i.hasNext();
					}
				};
			}
		};
	}

	public void putAll(Map<? extends Double, ? extends Byte> m) {
		if (m instanceof Double2ByteMap) {
			ObjectIterator<Entry> i = Double2ByteMaps.fastIterator((Double2ByteMap)m);

			while (i.hasNext()) {
				Entry e = (Entry)i.next();
				this.put(e.getDoubleKey(), e.getByteValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Double, ? extends Byte>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Double, ? extends Byte> e = (java.util.Map.Entry<? extends Double, ? extends Byte>)i.next();
				this.put((Double)e.getKey(), (Byte)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry> i = Double2ByteMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.double2ByteEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry> i = Double2ByteMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getByteValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry implements Entry {
		protected double key;
		protected byte value;

		public BasicEntry() {
		}

		public BasicEntry(Double key, Byte value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(double key, byte value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public double getDoubleKey() {
			return this.key;
		}

		@Override
		public byte getByteValue() {
			return this.value;
		}

		@Override
		public byte setValue(byte value) {
			throw new UnsupportedOperationException();
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				return Double.doubleToLongBits(this.key) == Double.doubleToLongBits(e.getDoubleKey()) && this.value == e.getByteValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Double) {
					Object value = e.getValue();
					return value != null && value instanceof Byte
						? Double.doubleToLongBits(this.key) == Double.doubleToLongBits((Double)key) && this.value == (Byte)value
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
		protected final Double2ByteMap map;

		public BasicEntrySet(Double2ByteMap map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				double k = e.getDoubleKey();
				return this.map.containsKey(k) && this.map.get(k) == e.getByteValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Double) {
					double k = (Double)key;
					Object value = e.getValue();
					return value != null && value instanceof Byte ? this.map.containsKey(k) && this.map.get(k) == (Byte)value : false;
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
				return this.map.remove(e.getDoubleKey(), e.getByteValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Double) {
					double k = (Double)key;
					Object value = e.getValue();
					if (value != null && value instanceof Byte) {
						byte v = (Byte)value;
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
