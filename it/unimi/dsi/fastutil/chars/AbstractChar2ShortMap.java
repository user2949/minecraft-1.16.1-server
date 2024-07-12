package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2ShortMap.Entry;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractChar2ShortMap extends AbstractChar2ShortFunction implements Char2ShortMap, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractChar2ShortMap() {
	}

	@Override
	public boolean containsValue(short v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(char k) {
		ObjectIterator<Entry> i = this.char2ShortEntrySet().iterator();

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
				return AbstractChar2ShortMap.this.containsKey(k);
			}

			public int size() {
				return AbstractChar2ShortMap.this.size();
			}

			public void clear() {
				AbstractChar2ShortMap.this.clear();
			}

			@Override
			public CharIterator iterator() {
				return new CharIterator() {
					private final ObjectIterator<Entry> i = Char2ShortMaps.fastIterator(AbstractChar2ShortMap.this);

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
	public ShortCollection values() {
		return new AbstractShortCollection() {
			@Override
			public boolean contains(short k) {
				return AbstractChar2ShortMap.this.containsValue(k);
			}

			public int size() {
				return AbstractChar2ShortMap.this.size();
			}

			public void clear() {
				AbstractChar2ShortMap.this.clear();
			}

			@Override
			public ShortIterator iterator() {
				return new ShortIterator() {
					private final ObjectIterator<Entry> i = Char2ShortMaps.fastIterator(AbstractChar2ShortMap.this);

					@Override
					public short nextShort() {
						return ((Entry)this.i.next()).getShortValue();
					}

					public boolean hasNext() {
						return this.i.hasNext();
					}
				};
			}
		};
	}

	public void putAll(Map<? extends Character, ? extends Short> m) {
		if (m instanceof Char2ShortMap) {
			ObjectIterator<Entry> i = Char2ShortMaps.fastIterator((Char2ShortMap)m);

			while (i.hasNext()) {
				Entry e = (Entry)i.next();
				this.put(e.getCharKey(), e.getShortValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Character, ? extends Short>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Character, ? extends Short> e = (java.util.Map.Entry<? extends Character, ? extends Short>)i.next();
				this.put((Character)e.getKey(), (Short)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry> i = Char2ShortMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.char2ShortEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry> i = Char2ShortMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getShortValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry implements Entry {
		protected char key;
		protected short value;

		public BasicEntry() {
		}

		public BasicEntry(Character key, Short value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(char key, short value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public char getCharKey() {
			return this.key;
		}

		@Override
		public short getShortValue() {
			return this.value;
		}

		@Override
		public short setValue(short value) {
			throw new UnsupportedOperationException();
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				return this.key == e.getCharKey() && this.value == e.getShortValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Character) {
					Object value = e.getValue();
					return value != null && value instanceof Short ? this.key == (Character)key && this.value == (Short)value : false;
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
		protected final Char2ShortMap map;

		public BasicEntrySet(Char2ShortMap map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				char k = e.getCharKey();
				return this.map.containsKey(k) && this.map.get(k) == e.getShortValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Character) {
					char k = (Character)key;
					Object value = e.getValue();
					return value != null && value instanceof Short ? this.map.containsKey(k) && this.map.get(k) == (Short)value : false;
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
				return this.map.remove(e.getCharKey(), e.getShortValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Character) {
					char k = (Character)key;
					Object value = e.getValue();
					if (value != null && value instanceof Short) {
						short v = (Short)value;
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
