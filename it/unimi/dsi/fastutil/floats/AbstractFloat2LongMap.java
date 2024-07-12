package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.Float2LongMap.Entry;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractFloat2LongMap extends AbstractFloat2LongFunction implements Float2LongMap, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractFloat2LongMap() {
	}

	@Override
	public boolean containsValue(long v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(float k) {
		ObjectIterator<Entry> i = this.float2LongEntrySet().iterator();

		while (i.hasNext()) {
			if (((Entry)i.next()).getFloatKey() == k) {
				return true;
			}
		}

		return false;
	}

	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public FloatSet keySet() {
		return new AbstractFloatSet() {
			@Override
			public boolean contains(float k) {
				return AbstractFloat2LongMap.this.containsKey(k);
			}

			public int size() {
				return AbstractFloat2LongMap.this.size();
			}

			public void clear() {
				AbstractFloat2LongMap.this.clear();
			}

			@Override
			public FloatIterator iterator() {
				return new FloatIterator() {
					private final ObjectIterator<Entry> i = Float2LongMaps.fastIterator(AbstractFloat2LongMap.this);

					@Override
					public float nextFloat() {
						return ((Entry)this.i.next()).getFloatKey();
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
				return AbstractFloat2LongMap.this.containsValue(k);
			}

			public int size() {
				return AbstractFloat2LongMap.this.size();
			}

			public void clear() {
				AbstractFloat2LongMap.this.clear();
			}

			@Override
			public LongIterator iterator() {
				return new LongIterator() {
					private final ObjectIterator<Entry> i = Float2LongMaps.fastIterator(AbstractFloat2LongMap.this);

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

	public void putAll(Map<? extends Float, ? extends Long> m) {
		if (m instanceof Float2LongMap) {
			ObjectIterator<Entry> i = Float2LongMaps.fastIterator((Float2LongMap)m);

			while (i.hasNext()) {
				Entry e = (Entry)i.next();
				this.put(e.getFloatKey(), e.getLongValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Float, ? extends Long>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Float, ? extends Long> e = (java.util.Map.Entry<? extends Float, ? extends Long>)i.next();
				this.put((Float)e.getKey(), (Long)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry> i = Float2LongMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.float2LongEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry> i = Float2LongMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getFloatKey()));
			s.append("=>");
			s.append(String.valueOf(e.getLongValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry implements Entry {
		protected float key;
		protected long value;

		public BasicEntry() {
		}

		public BasicEntry(Float key, Long value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(float key, long value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public float getFloatKey() {
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
				return Float.floatToIntBits(this.key) == Float.floatToIntBits(e.getFloatKey()) && this.value == e.getLongValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Float) {
					Object value = e.getValue();
					return value != null && value instanceof Long ? Float.floatToIntBits(this.key) == Float.floatToIntBits((Float)key) && this.value == (Long)value : false;
				} else {
					return false;
				}
			}
		}

		public int hashCode() {
			return HashCommon.float2int(this.key) ^ HashCommon.long2int(this.value);
		}

		public String toString() {
			return this.key + "->" + this.value;
		}
	}

	public abstract static class BasicEntrySet extends AbstractObjectSet<Entry> {
		protected final Float2LongMap map;

		public BasicEntrySet(Float2LongMap map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				float k = e.getFloatKey();
				return this.map.containsKey(k) && this.map.get(k) == e.getLongValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Float) {
					float k = (Float)key;
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
				return this.map.remove(e.getFloatKey(), e.getLongValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Float) {
					float k = (Float)key;
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
