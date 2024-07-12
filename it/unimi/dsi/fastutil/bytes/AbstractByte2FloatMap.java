package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.bytes.Byte2FloatMap.Entry;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractByte2FloatMap extends AbstractByte2FloatFunction implements Byte2FloatMap, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractByte2FloatMap() {
	}

	@Override
	public boolean containsValue(float v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(byte k) {
		ObjectIterator<Entry> i = this.byte2FloatEntrySet().iterator();

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
				return AbstractByte2FloatMap.this.containsKey(k);
			}

			public int size() {
				return AbstractByte2FloatMap.this.size();
			}

			public void clear() {
				AbstractByte2FloatMap.this.clear();
			}

			@Override
			public ByteIterator iterator() {
				return new ByteIterator() {
					private final ObjectIterator<Entry> i = Byte2FloatMaps.fastIterator(AbstractByte2FloatMap.this);

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
	public FloatCollection values() {
		return new AbstractFloatCollection() {
			@Override
			public boolean contains(float k) {
				return AbstractByte2FloatMap.this.containsValue(k);
			}

			public int size() {
				return AbstractByte2FloatMap.this.size();
			}

			public void clear() {
				AbstractByte2FloatMap.this.clear();
			}

			@Override
			public FloatIterator iterator() {
				return new FloatIterator() {
					private final ObjectIterator<Entry> i = Byte2FloatMaps.fastIterator(AbstractByte2FloatMap.this);

					@Override
					public float nextFloat() {
						return ((Entry)this.i.next()).getFloatValue();
					}

					public boolean hasNext() {
						return this.i.hasNext();
					}
				};
			}
		};
	}

	public void putAll(Map<? extends Byte, ? extends Float> m) {
		if (m instanceof Byte2FloatMap) {
			ObjectIterator<Entry> i = Byte2FloatMaps.fastIterator((Byte2FloatMap)m);

			while (i.hasNext()) {
				Entry e = (Entry)i.next();
				this.put(e.getByteKey(), e.getFloatValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Byte, ? extends Float>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Byte, ? extends Float> e = (java.util.Map.Entry<? extends Byte, ? extends Float>)i.next();
				this.put((Byte)e.getKey(), (Float)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry> i = Byte2FloatMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.byte2FloatEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry> i = Byte2FloatMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getFloatValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry implements Entry {
		protected byte key;
		protected float value;

		public BasicEntry() {
		}

		public BasicEntry(Byte key, Float value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(byte key, float value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public byte getByteKey() {
			return this.key;
		}

		@Override
		public float getFloatValue() {
			return this.value;
		}

		@Override
		public float setValue(float value) {
			throw new UnsupportedOperationException();
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				return this.key == e.getByteKey() && Float.floatToIntBits(this.value) == Float.floatToIntBits(e.getFloatValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Byte) {
					Object value = e.getValue();
					return value != null && value instanceof Float ? this.key == (Byte)key && Float.floatToIntBits(this.value) == Float.floatToIntBits((Float)value) : false;
				} else {
					return false;
				}
			}
		}

		public int hashCode() {
			return this.key ^ HashCommon.float2int(this.value);
		}

		public String toString() {
			return this.key + "->" + this.value;
		}
	}

	public abstract static class BasicEntrySet extends AbstractObjectSet<Entry> {
		protected final Byte2FloatMap map;

		public BasicEntrySet(Byte2FloatMap map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				byte k = e.getByteKey();
				return this.map.containsKey(k) && Float.floatToIntBits(this.map.get(k)) == Float.floatToIntBits(e.getFloatValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Byte) {
					byte k = (Byte)key;
					Object value = e.getValue();
					return value != null && value instanceof Float
						? this.map.containsKey(k) && Float.floatToIntBits(this.map.get(k)) == Float.floatToIntBits((Float)value)
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
				return this.map.remove(e.getByteKey(), e.getFloatValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Byte) {
					byte k = (Byte)key;
					Object value = e.getValue();
					if (value != null && value instanceof Float) {
						float v = (Float)value;
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
