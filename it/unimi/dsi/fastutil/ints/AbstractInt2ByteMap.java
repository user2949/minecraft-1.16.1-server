package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.ints.Int2ByteMap.Entry;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractInt2ByteMap extends AbstractInt2ByteFunction implements Int2ByteMap, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractInt2ByteMap() {
	}

	@Override
	public boolean containsValue(byte v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(int k) {
		ObjectIterator<Entry> i = this.int2ByteEntrySet().iterator();

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
				return AbstractInt2ByteMap.this.containsKey(k);
			}

			public int size() {
				return AbstractInt2ByteMap.this.size();
			}

			public void clear() {
				AbstractInt2ByteMap.this.clear();
			}

			@Override
			public IntIterator iterator() {
				return new IntIterator() {
					private final ObjectIterator<Entry> i = Int2ByteMaps.fastIterator(AbstractInt2ByteMap.this);

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
	public ByteCollection values() {
		return new AbstractByteCollection() {
			@Override
			public boolean contains(byte k) {
				return AbstractInt2ByteMap.this.containsValue(k);
			}

			public int size() {
				return AbstractInt2ByteMap.this.size();
			}

			public void clear() {
				AbstractInt2ByteMap.this.clear();
			}

			@Override
			public ByteIterator iterator() {
				return new ByteIterator() {
					private final ObjectIterator<Entry> i = Int2ByteMaps.fastIterator(AbstractInt2ByteMap.this);

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

	public void putAll(Map<? extends Integer, ? extends Byte> m) {
		if (m instanceof Int2ByteMap) {
			ObjectIterator<Entry> i = Int2ByteMaps.fastIterator((Int2ByteMap)m);

			while (i.hasNext()) {
				Entry e = (Entry)i.next();
				this.put(e.getIntKey(), e.getByteValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Integer, ? extends Byte>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Integer, ? extends Byte> e = (java.util.Map.Entry<? extends Integer, ? extends Byte>)i.next();
				this.put((Integer)e.getKey(), (Byte)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry> i = Int2ByteMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.int2ByteEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry> i = Int2ByteMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getByteValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry implements Entry {
		protected int key;
		protected byte value;

		public BasicEntry() {
		}

		public BasicEntry(Integer key, Byte value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(int key, byte value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public int getIntKey() {
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
				return this.key == e.getIntKey() && this.value == e.getByteValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Integer) {
					Object value = e.getValue();
					return value != null && value instanceof Byte ? this.key == (Integer)key && this.value == (Byte)value : false;
				} else {
					return false;
				}
			}
		}

		public int hashCode() {
			return this.key ^ this.value;
		}

		public String toString() {
			return this.key + "->" + this.value;
		}
	}

	public abstract static class BasicEntrySet extends AbstractObjectSet<Entry> {
		protected final Int2ByteMap map;

		public BasicEntrySet(Int2ByteMap map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				int k = e.getIntKey();
				return this.map.containsKey(k) && this.map.get(k) == e.getByteValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Integer) {
					int k = (Integer)key;
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
				return this.map.remove(e.getIntKey(), e.getByteValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Integer) {
					int k = (Integer)key;
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
