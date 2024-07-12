package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.longs.Long2CharMap.Entry;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractLong2CharMap extends AbstractLong2CharFunction implements Long2CharMap, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractLong2CharMap() {
	}

	@Override
	public boolean containsValue(char v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(long k) {
		ObjectIterator<Entry> i = this.long2CharEntrySet().iterator();

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
				return AbstractLong2CharMap.this.containsKey(k);
			}

			public int size() {
				return AbstractLong2CharMap.this.size();
			}

			public void clear() {
				AbstractLong2CharMap.this.clear();
			}

			@Override
			public LongIterator iterator() {
				return new LongIterator() {
					private final ObjectIterator<Entry> i = Long2CharMaps.fastIterator(AbstractLong2CharMap.this);

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
	public CharCollection values() {
		return new AbstractCharCollection() {
			@Override
			public boolean contains(char k) {
				return AbstractLong2CharMap.this.containsValue(k);
			}

			public int size() {
				return AbstractLong2CharMap.this.size();
			}

			public void clear() {
				AbstractLong2CharMap.this.clear();
			}

			@Override
			public CharIterator iterator() {
				return new CharIterator() {
					private final ObjectIterator<Entry> i = Long2CharMaps.fastIterator(AbstractLong2CharMap.this);

					@Override
					public char nextChar() {
						return ((Entry)this.i.next()).getCharValue();
					}

					public boolean hasNext() {
						return this.i.hasNext();
					}
				};
			}
		};
	}

	public void putAll(Map<? extends Long, ? extends Character> m) {
		if (m instanceof Long2CharMap) {
			ObjectIterator<Entry> i = Long2CharMaps.fastIterator((Long2CharMap)m);

			while (i.hasNext()) {
				Entry e = (Entry)i.next();
				this.put(e.getLongKey(), e.getCharValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Long, ? extends Character>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Long, ? extends Character> e = (java.util.Map.Entry<? extends Long, ? extends Character>)i.next();
				this.put((Long)e.getKey(), (Character)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry> i = Long2CharMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.long2CharEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry> i = Long2CharMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getCharValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry implements Entry {
		protected long key;
		protected char value;

		public BasicEntry() {
		}

		public BasicEntry(Long key, Character value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(long key, char value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public long getLongKey() {
			return this.key;
		}

		@Override
		public char getCharValue() {
			return this.value;
		}

		@Override
		public char setValue(char value) {
			throw new UnsupportedOperationException();
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				return this.key == e.getLongKey() && this.value == e.getCharValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Long) {
					Object value = e.getValue();
					return value != null && value instanceof Character ? this.key == (Long)key && this.value == (Character)value : false;
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
		protected final Long2CharMap map;

		public BasicEntrySet(Long2CharMap map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				long k = e.getLongKey();
				return this.map.containsKey(k) && this.map.get(k) == e.getCharValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Long) {
					long k = (Long)key;
					Object value = e.getValue();
					return value != null && value instanceof Character ? this.map.containsKey(k) && this.map.get(k) == (Character)value : false;
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
				return this.map.remove(e.getLongKey(), e.getCharValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Long) {
					long k = (Long)key;
					Object value = e.getValue();
					if (value != null && value instanceof Character) {
						char v = (Character)value;
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
