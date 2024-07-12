package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.longs.Long2LongMap.Entry;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractLong2LongMap extends AbstractLong2LongFunction implements Long2LongMap, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractLong2LongMap() {
	}

	@Override
	public boolean containsValue(long v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(long k) {
		ObjectIterator<Entry> i = this.long2LongEntrySet().iterator();

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
				return AbstractLong2LongMap.this.containsKey(k);
			}

			public int size() {
				return AbstractLong2LongMap.this.size();
			}

			public void clear() {
				AbstractLong2LongMap.this.clear();
			}

			@Override
			public LongIterator iterator() {
				return new LongIterator() {
					private final ObjectIterator<Entry> i = Long2LongMaps.fastIterator(AbstractLong2LongMap.this);

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
	public LongCollection values() {
		return new AbstractLongCollection() {
			@Override
			public boolean contains(long k) {
				return AbstractLong2LongMap.this.containsValue(k);
			}

			public int size() {
				return AbstractLong2LongMap.this.size();
			}

			public void clear() {
				AbstractLong2LongMap.this.clear();
			}

			@Override
			public LongIterator iterator() {
				return new LongIterator() {
					private final ObjectIterator<Entry> i = Long2LongMaps.fastIterator(AbstractLong2LongMap.this);

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

	public void putAll(Map<? extends Long, ? extends Long> m) {
		if (m instanceof Long2LongMap) {
			ObjectIterator<Entry> i = Long2LongMaps.fastIterator((Long2LongMap)m);

			while (i.hasNext()) {
				Entry e = (Entry)i.next();
				this.put(e.getLongKey(), e.getLongValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Long, ? extends Long>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Long, ? extends Long> e = (java.util.Map.Entry<? extends Long, ? extends Long>)i.next();
				this.put((Long)e.getKey(), (Long)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry> i = Long2LongMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.long2LongEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry> i = Long2LongMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getLongValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry implements Entry {
		protected long key;
		protected long value;

		public BasicEntry() {
		}

		public BasicEntry(Long key, Long value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(long key, long value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public long getLongKey() {
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
				return this.key == e.getLongKey() && this.value == e.getLongValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Long) {
					Object value = e.getValue();
					return value != null && value instanceof Long ? this.key == (Long)key && this.value == (Long)value : false;
				} else {
					return false;
				}
			}
		}

		public int hashCode() {
			return HashCommon.long2int(this.key) ^ HashCommon.long2int(this.value);
		}

		public String toString() {
			return this.key + "->" + this.value;
		}
	}

	public abstract static class BasicEntrySet extends AbstractObjectSet<Entry> {
		protected final Long2LongMap map;

		public BasicEntrySet(Long2LongMap map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				long k = e.getLongKey();
				return this.map.containsKey(k) && this.map.get(k) == e.getLongValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Long) {
					long k = (Long)key;
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
				return this.map.remove(e.getLongKey(), e.getLongValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Long) {
					long k = (Long)key;
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
