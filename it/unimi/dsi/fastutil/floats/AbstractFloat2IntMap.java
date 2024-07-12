package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.Float2IntMap.Entry;
import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractFloat2IntMap extends AbstractFloat2IntFunction implements Float2IntMap, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractFloat2IntMap() {
	}

	@Override
	public boolean containsValue(int v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(float k) {
		ObjectIterator<Entry> i = this.float2IntEntrySet().iterator();

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
				return AbstractFloat2IntMap.this.containsKey(k);
			}

			public int size() {
				return AbstractFloat2IntMap.this.size();
			}

			public void clear() {
				AbstractFloat2IntMap.this.clear();
			}

			@Override
			public FloatIterator iterator() {
				return new FloatIterator() {
					private final ObjectIterator<Entry> i = Float2IntMaps.fastIterator(AbstractFloat2IntMap.this);

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
	public IntCollection values() {
		return new AbstractIntCollection() {
			@Override
			public boolean contains(int k) {
				return AbstractFloat2IntMap.this.containsValue(k);
			}

			public int size() {
				return AbstractFloat2IntMap.this.size();
			}

			public void clear() {
				AbstractFloat2IntMap.this.clear();
			}

			@Override
			public IntIterator iterator() {
				return new IntIterator() {
					private final ObjectIterator<Entry> i = Float2IntMaps.fastIterator(AbstractFloat2IntMap.this);

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

	public void putAll(Map<? extends Float, ? extends Integer> m) {
		if (m instanceof Float2IntMap) {
			ObjectIterator<Entry> i = Float2IntMaps.fastIterator((Float2IntMap)m);

			while (i.hasNext()) {
				Entry e = (Entry)i.next();
				this.put(e.getFloatKey(), e.getIntValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Float, ? extends Integer>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Float, ? extends Integer> e = (java.util.Map.Entry<? extends Float, ? extends Integer>)i.next();
				this.put((Float)e.getKey(), (Integer)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry> i = Float2IntMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.float2IntEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry> i = Float2IntMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getIntValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry implements Entry {
		protected float key;
		protected int value;

		public BasicEntry() {
		}

		public BasicEntry(Float key, Integer value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(float key, int value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public float getFloatKey() {
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
				return Float.floatToIntBits(this.key) == Float.floatToIntBits(e.getFloatKey()) && this.value == e.getIntValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Float) {
					Object value = e.getValue();
					return value != null && value instanceof Integer
						? Float.floatToIntBits(this.key) == Float.floatToIntBits((Float)key) && this.value == (Integer)value
						: false;
				} else {
					return false;
				}
			}
		}

		public int hashCode() {
			return HashCommon.float2int(this.key) ^ this.value;
		}

		public String toString() {
			return this.key + "->" + this.value;
		}
	}

	public abstract static class BasicEntrySet extends AbstractObjectSet<Entry> {
		protected final Float2IntMap map;

		public BasicEntrySet(Float2IntMap map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				float k = e.getFloatKey();
				return this.map.containsKey(k) && this.map.get(k) == e.getIntValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Float) {
					float k = (Float)key;
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
				return this.map.remove(e.getFloatKey(), e.getIntValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Float) {
					float k = (Float)key;
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
