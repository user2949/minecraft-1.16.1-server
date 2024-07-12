package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.longs.Long2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractLong2IntMap extends AbstractLong2IntFunction implements Long2IntMap, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractLong2IntMap() {
	}

	@Override
	public boolean containsValue(int v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(long k) {
		ObjectIterator<Entry> i = this.long2IntEntrySet().iterator();

		while (i.hasNext()) {
			if (((Entry)i.next()).getLongKey() == k) {
				return true;
			}
		}

		return false;
	}

	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public LongSet keySet() {
		return new AbstractLongSet() {
			@Override
			public boolean contains(long k) {
				return AbstractLong2IntMap.this.containsKey(k);
			}

			public int size() {
				return AbstractLong2IntMap.this.size();
			}

			public void clear() {
				AbstractLong2IntMap.this.clear();
			}

			@Override
			public LongIterator iterator() {
				return new LongIterator() {
					private final ObjectIterator<Entry> i = Long2IntMaps.fastIterator(AbstractLong2IntMap.this);

					@Override
					public long nextLong() {
						return ((Entry)this.i.next()).getLongKey();
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
				return AbstractLong2IntMap.this.containsValue(k);
			}

			public int size() {
				return AbstractLong2IntMap.this.size();
			}

			public void clear() {
				AbstractLong2IntMap.this.clear();
			}

			@Override
			public IntIterator iterator() {
				return new IntIterator() {
					private final ObjectIterator<Entry> i = Long2IntMaps.fastIterator(AbstractLong2IntMap.this);

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

	public void putAll(Map<? extends Long, ? extends Integer> m) {
		if (m instanceof Long2IntMap) {
			ObjectIterator<Entry> i = Long2IntMaps.fastIterator((Long2IntMap)m);

			while (i.hasNext()) {
				Entry e = (Entry)i.next();
				this.put(e.getLongKey(), e.getIntValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Long, ? extends Integer>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Long, ? extends Integer> e = (java.util.Map.Entry<? extends Long, ? extends Integer>)i.next();
				this.put((Long)e.getKey(), (Integer)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry> i = Long2IntMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.long2IntEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry> i = Long2IntMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getLongKey()));
			s.append("=>");
			s.append(String.valueOf(e.getIntValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry implements Entry {
		protected long key;
		protected int value;

		public BasicEntry() {
		}

		public BasicEntry(Long key, Integer value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(long key, int value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public long getLongKey() {
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
				return this.key == e.getLongKey() && this.value == e.getIntValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Long) {
					Object value = e.getValue();
					return value != null && value instanceof Integer ? this.key == (Long)key && this.value == (Integer)value : false;
				} else {
					return false;
				}
			}
		}

		public int hashCode() {
			return HashCommon.long2int(this.key) ^ this.value;
		}

		public String toString() {
			return this.key + "->" + this.value;
		}
	}

	public abstract static class BasicEntrySet extends AbstractObjectSet<Entry> {
		protected final Long2IntMap map;

		public BasicEntrySet(Long2IntMap map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				long k = e.getLongKey();
				return this.map.containsKey(k) && this.map.get(k) == e.getIntValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Long) {
					long k = (Long)key;
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
				return this.map.remove(e.getLongKey(), e.getIntValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Long) {
					long k = (Long)key;
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
