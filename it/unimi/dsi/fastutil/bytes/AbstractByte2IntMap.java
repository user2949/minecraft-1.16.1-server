package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2IntMap.Entry;
import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractByte2IntMap extends AbstractByte2IntFunction implements Byte2IntMap, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractByte2IntMap() {
	}

	@Override
	public boolean containsValue(int v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(byte k) {
		ObjectIterator<Entry> i = this.byte2IntEntrySet().iterator();

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
				return AbstractByte2IntMap.this.containsKey(k);
			}

			public int size() {
				return AbstractByte2IntMap.this.size();
			}

			public void clear() {
				AbstractByte2IntMap.this.clear();
			}

			@Override
			public ByteIterator iterator() {
				return new ByteIterator() {
					private final ObjectIterator<Entry> i = Byte2IntMaps.fastIterator(AbstractByte2IntMap.this);

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
	public IntCollection values() {
		return new AbstractIntCollection() {
			@Override
			public boolean contains(int k) {
				return AbstractByte2IntMap.this.containsValue(k);
			}

			public int size() {
				return AbstractByte2IntMap.this.size();
			}

			public void clear() {
				AbstractByte2IntMap.this.clear();
			}

			@Override
			public IntIterator iterator() {
				return new IntIterator() {
					private final ObjectIterator<Entry> i = Byte2IntMaps.fastIterator(AbstractByte2IntMap.this);

					@Override
					public int nextInt() {
						return ((Entry)this.i.next()).getIntValue();
					}

					public boolean hasNext() {
						return this.i.hasNext();
					}
				};
			}
		};
	}

	public void putAll(Map<? extends Byte, ? extends Integer> m) {
		if (m instanceof Byte2IntMap) {
			ObjectIterator<Entry> i = Byte2IntMaps.fastIterator((Byte2IntMap)m);

			while (i.hasNext()) {
				Entry e = (Entry)i.next();
				this.put(e.getByteKey(), e.getIntValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Byte, ? extends Integer>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Byte, ? extends Integer> e = (java.util.Map.Entry<? extends Byte, ? extends Integer>)i.next();
				this.put((Byte)e.getKey(), (Integer)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry> i = Byte2IntMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.byte2IntEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry> i = Byte2IntMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getIntValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry implements Entry {
		protected byte key;
		protected int value;

		public BasicEntry() {
		}

		public BasicEntry(Byte key, Integer value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(byte key, int value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public byte getByteKey() {
			return this.key;
		}

		@Override
		public int getIntValue() {
			return this.value;
		}

		@Override
		public int setValue(int value) {
			throw new UnsupportedOperationException();
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				return this.key == e.getByteKey() && this.value == e.getIntValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Byte) {
					Object value = e.getValue();
					return value != null && value instanceof Integer ? this.key == (Byte)key && this.value == (Integer)value : false;
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
		protected final Byte2IntMap map;

		public BasicEntrySet(Byte2IntMap map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				byte k = e.getByteKey();
				return this.map.containsKey(k) && this.map.get(k) == e.getIntValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Byte) {
					byte k = (Byte)key;
					Object value = e.getValue();
					return value != null && value instanceof Integer ? this.map.containsKey(k) && this.map.get(k) == (Integer)value : false;
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
				return this.map.remove(e.getByteKey(), e.getIntValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Byte) {
					byte k = (Byte)key;
					Object value = e.getValue();
					if (value != null && value instanceof Integer) {
						int v = (Integer)value;
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
