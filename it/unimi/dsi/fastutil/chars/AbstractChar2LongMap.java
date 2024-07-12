package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.chars.Char2LongMap.Entry;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractChar2LongMap extends AbstractChar2LongFunction implements Char2LongMap, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractChar2LongMap() {
	}

	@Override
	public boolean containsValue(long v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(char k) {
		ObjectIterator<Entry> i = this.char2LongEntrySet().iterator();

		while (i.hasNext()) {
			if (((Entry)i.next()).getCharKey() == k) {
				return true;
			}
		}

		return false;
	}

	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public CharSet keySet() {
		return new AbstractCharSet() {
			@Override
			public boolean contains(char k) {
				return AbstractChar2LongMap.this.containsKey(k);
			}

			public int size() {
				return AbstractChar2LongMap.this.size();
			}

			public void clear() {
				AbstractChar2LongMap.this.clear();
			}

			@Override
			public CharIterator iterator() {
				return new CharIterator() {
					private final ObjectIterator<Entry> i = Char2LongMaps.fastIterator(AbstractChar2LongMap.this);

					@Override
					public char nextChar() {
						return ((Entry)this.i.next()).getCharKey();
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
				return AbstractChar2LongMap.this.containsValue(k);
			}

			public int size() {
				return AbstractChar2LongMap.this.size();
			}

			public void clear() {
				AbstractChar2LongMap.this.clear();
			}

			@Override
			public LongIterator iterator() {
				return new LongIterator() {
					private final ObjectIterator<Entry> i = Char2LongMaps.fastIterator(AbstractChar2LongMap.this);

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

	public void putAll(Map<? extends Character, ? extends Long> m) {
		if (m instanceof Char2LongMap) {
			ObjectIterator<Entry> i = Char2LongMaps.fastIterator((Char2LongMap)m);

			while (i.hasNext()) {
				Entry e = (Entry)i.next();
				this.put(e.getCharKey(), e.getLongValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Character, ? extends Long>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Character, ? extends Long> e = (java.util.Map.Entry<? extends Character, ? extends Long>)i.next();
				this.put((Character)e.getKey(), (Long)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry> i = Char2LongMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.char2LongEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry> i = Char2LongMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getCharKey()));
			s.append("=>");
			s.append(String.valueOf(e.getLongValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry implements Entry {
		protected char key;
		protected long value;

		public BasicEntry() {
		}

		public BasicEntry(Character key, Long value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(char key, long value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public char getCharKey() {
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
				return this.key == e.getCharKey() && this.value == e.getLongValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Character) {
					Object value = e.getValue();
					return value != null && value instanceof Long ? this.key == (Character)key && this.value == (Long)value : false;
				} else {
					return false;
				}
			}
		}

		public int hashCode() {
			return this.key ^ HashCommon.long2int(this.value);
		}

		public String toString() {
			return this.key + "->" + this.value;
		}
	}

	public abstract static class BasicEntrySet extends AbstractObjectSet<Entry> {
		protected final Char2LongMap map;

		public BasicEntrySet(Char2LongMap map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				char k = e.getCharKey();
				return this.map.containsKey(k) && this.map.get(k) == e.getLongValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Character) {
					char k = (Character)key;
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
				return this.map.remove(e.getCharKey(), e.getLongValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Character) {
					char k = (Character)key;
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
