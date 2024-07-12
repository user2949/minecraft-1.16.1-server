package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.Int2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractInt2IntMap extends AbstractInt2IntFunction implements Int2IntMap, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractInt2IntMap() {
	}

	@Override
	public boolean containsValue(int v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(int k) {
		ObjectIterator<Entry> i = this.int2IntEntrySet().iterator();

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
				return AbstractInt2IntMap.this.containsKey(k);
			}

			public int size() {
				return AbstractInt2IntMap.this.size();
			}

			public void clear() {
				AbstractInt2IntMap.this.clear();
			}

			@Override
			public IntIterator iterator() {
				return new IntIterator() {
					private final ObjectIterator<Entry> i = Int2IntMaps.fastIterator(AbstractInt2IntMap.this);

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
	public IntCollection values() {
		return new AbstractIntCollection() {
			@Override
			public boolean contains(int k) {
				return AbstractInt2IntMap.this.containsValue(k);
			}

			public int size() {
				return AbstractInt2IntMap.this.size();
			}

			public void clear() {
				AbstractInt2IntMap.this.clear();
			}

			@Override
			public IntIterator iterator() {
				return new IntIterator() {
					private final ObjectIterator<Entry> i = Int2IntMaps.fastIterator(AbstractInt2IntMap.this);

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

	public void putAll(Map<? extends Integer, ? extends Integer> m) {
		if (m instanceof Int2IntMap) {
			ObjectIterator<Entry> i = Int2IntMaps.fastIterator((Int2IntMap)m);

			while (i.hasNext()) {
				Entry e = (Entry)i.next();
				this.put(e.getIntKey(), e.getIntValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Integer, ? extends Integer>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Integer, ? extends Integer> e = (java.util.Map.Entry<? extends Integer, ? extends Integer>)i.next();
				this.put((Integer)e.getKey(), (Integer)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry> i = Int2IntMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.int2IntEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry> i = Int2IntMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getIntValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry implements Entry {
		protected int key;
		protected int value;

		public BasicEntry() {
		}

		public BasicEntry(Integer key, Integer value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(int key, int value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public int getIntKey() {
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
				return this.key == e.getIntKey() && this.value == e.getIntValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Integer) {
					Object value = e.getValue();
					return value != null && value instanceof Integer ? this.key == (Integer)key && this.value == (Integer)value : false;
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
		protected final Int2IntMap map;

		public BasicEntrySet(Int2IntMap map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				int k = e.getIntKey();
				return this.map.containsKey(k) && this.map.get(k) == e.getIntValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Integer) {
					int k = (Integer)key;
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
				return this.map.remove(e.getIntKey(), e.getIntValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Integer) {
					int k = (Integer)key;
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
