package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.shorts.Short2ByteMap.Entry;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractShort2ByteMap extends AbstractShort2ByteFunction implements Short2ByteMap, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractShort2ByteMap() {
	}

	@Override
	public boolean containsValue(byte v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(short k) {
		ObjectIterator<Entry> i = this.short2ByteEntrySet().iterator();

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
				return AbstractShort2ByteMap.this.containsKey(k);
			}

			public int size() {
				return AbstractShort2ByteMap.this.size();
			}

			public void clear() {
				AbstractShort2ByteMap.this.clear();
			}

			@Override
			public ShortIterator iterator() {
				return new ShortIterator() {
					private final ObjectIterator<Entry> i = Short2ByteMaps.fastIterator(AbstractShort2ByteMap.this);

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
	public ByteCollection values() {
		return new AbstractByteCollection() {
			@Override
			public boolean contains(byte k) {
				return AbstractShort2ByteMap.this.containsValue(k);
			}

			public int size() {
				return AbstractShort2ByteMap.this.size();
			}

			public void clear() {
				AbstractShort2ByteMap.this.clear();
			}

			@Override
			public ByteIterator iterator() {
				return new ByteIterator() {
					private final ObjectIterator<Entry> i = Short2ByteMaps.fastIterator(AbstractShort2ByteMap.this);

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

	public void putAll(Map<? extends Short, ? extends Byte> m) {
		if (m instanceof Short2ByteMap) {
			ObjectIterator<Entry> i = Short2ByteMaps.fastIterator((Short2ByteMap)m);

			while (i.hasNext()) {
				Entry e = (Entry)i.next();
				this.put(e.getShortKey(), e.getByteValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Short, ? extends Byte>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Short, ? extends Byte> e = (java.util.Map.Entry<? extends Short, ? extends Byte>)i.next();
				this.put((Short)e.getKey(), (Byte)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry> i = Short2ByteMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.short2ByteEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry> i = Short2ByteMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getByteValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry implements Entry {
		protected short key;
		protected byte value;

		public BasicEntry() {
		}

		public BasicEntry(Short key, Byte value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(short key, byte value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public short getShortKey() {
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
				return this.key == e.getShortKey() && this.value == e.getByteValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Short) {
					Object value = e.getValue();
					return value != null && value instanceof Byte ? this.key == (Short)key && this.value == (Byte)value : false;
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
		protected final Short2ByteMap map;

		public BasicEntrySet(Short2ByteMap map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				short k = e.getShortKey();
				return this.map.containsKey(k) && this.map.get(k) == e.getByteValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Short) {
					short k = (Short)key;
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
				return this.map.remove(e.getShortKey(), e.getByteValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Short) {
					short k = (Short)key;
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
