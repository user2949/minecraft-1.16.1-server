package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.bytes.Byte2BooleanMap.Entry;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractByte2BooleanMap extends AbstractByte2BooleanFunction implements Byte2BooleanMap, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractByte2BooleanMap() {
	}

	@Override
	public boolean containsValue(boolean v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(byte k) {
		ObjectIterator<Entry> i = this.byte2BooleanEntrySet().iterator();

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
				return AbstractByte2BooleanMap.this.containsKey(k);
			}

			public int size() {
				return AbstractByte2BooleanMap.this.size();
			}

			public void clear() {
				AbstractByte2BooleanMap.this.clear();
			}

			@Override
			public ByteIterator iterator() {
				return new ByteIterator() {
					private final ObjectIterator<Entry> i = Byte2BooleanMaps.fastIterator(AbstractByte2BooleanMap.this);

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
	public BooleanCollection values() {
		return new AbstractBooleanCollection() {
			@Override
			public boolean contains(boolean k) {
				return AbstractByte2BooleanMap.this.containsValue(k);
			}

			public int size() {
				return AbstractByte2BooleanMap.this.size();
			}

			public void clear() {
				AbstractByte2BooleanMap.this.clear();
			}

			@Override
			public BooleanIterator iterator() {
				return new BooleanIterator() {
					private final ObjectIterator<Entry> i = Byte2BooleanMaps.fastIterator(AbstractByte2BooleanMap.this);

					@Override
					public boolean nextBoolean() {
						return ((Entry)this.i.next()).getBooleanValue();
					}

					public boolean hasNext() {
						return this.i.hasNext();
					}
				};
			}
		};
	}

	public void putAll(Map<? extends Byte, ? extends Boolean> m) {
		if (m instanceof Byte2BooleanMap) {
			ObjectIterator<Entry> i = Byte2BooleanMaps.fastIterator((Byte2BooleanMap)m);

			while (i.hasNext()) {
				Entry e = (Entry)i.next();
				this.put(e.getByteKey(), e.getBooleanValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Byte, ? extends Boolean>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Byte, ? extends Boolean> e = (java.util.Map.Entry<? extends Byte, ? extends Boolean>)i.next();
				this.put((Byte)e.getKey(), (Boolean)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry> i = Byte2BooleanMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.byte2BooleanEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry> i = Byte2BooleanMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getBooleanValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry implements Entry {
		protected byte key;
		protected boolean value;

		public BasicEntry() {
		}

		public BasicEntry(Byte key, Boolean value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(byte key, boolean value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public byte getByteKey() {
			return this.key;
		}

		@Override
		public boolean getBooleanValue() {
			return this.value;
		}

		@Override
		public boolean setValue(boolean value) {
			throw new UnsupportedOperationException();
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				return this.key == e.getByteKey() && this.value == e.getBooleanValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Byte) {
					Object value = e.getValue();
					return value != null && value instanceof Boolean ? this.key == (Byte)key && this.value == (Boolean)value : false;
				} else {
					return false;
				}
			}
		}

		public int hashCode() {
			return this.key ^ (this.value ? 1231 : 1237);
		}

		public String toString() {
			return this.key + "->" + this.value;
		}
	}

	public abstract static class BasicEntrySet extends AbstractObjectSet<Entry> {
		protected final Byte2BooleanMap map;

		public BasicEntrySet(Byte2BooleanMap map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				byte k = e.getByteKey();
				return this.map.containsKey(k) && this.map.get(k) == e.getBooleanValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Byte) {
					byte k = (Byte)key;
					Object value = e.getValue();
					return value != null && value instanceof Boolean ? this.map.containsKey(k) && this.map.get(k) == (Boolean)value : false;
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
				return this.map.remove(e.getByteKey(), e.getBooleanValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Byte) {
					byte k = (Byte)key;
					Object value = e.getValue();
					if (value != null && value instanceof Boolean) {
						boolean v = (Boolean)value;
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
