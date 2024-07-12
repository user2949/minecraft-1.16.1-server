package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2CharMap.Entry;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractChar2CharMap extends AbstractChar2CharFunction implements Char2CharMap, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractChar2CharMap() {
	}

	@Override
	public boolean containsValue(char v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(char k) {
		ObjectIterator<Entry> i = this.char2CharEntrySet().iterator();

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
				return AbstractChar2CharMap.this.containsKey(k);
			}

			public int size() {
				return AbstractChar2CharMap.this.size();
			}

			public void clear() {
				AbstractChar2CharMap.this.clear();
			}

			@Override
			public CharIterator iterator() {
				return new CharIterator() {
					private final ObjectIterator<Entry> i = Char2CharMaps.fastIterator(AbstractChar2CharMap.this);

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
	public CharCollection values() {
		return new AbstractCharCollection() {
			@Override
			public boolean contains(char k) {
				return AbstractChar2CharMap.this.containsValue(k);
			}

			public int size() {
				return AbstractChar2CharMap.this.size();
			}

			public void clear() {
				AbstractChar2CharMap.this.clear();
			}

			@Override
			public CharIterator iterator() {
				return new CharIterator() {
					private final ObjectIterator<Entry> i = Char2CharMaps.fastIterator(AbstractChar2CharMap.this);

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

	public void putAll(Map<? extends Character, ? extends Character> m) {
		if (m instanceof Char2CharMap) {
			ObjectIterator<Entry> i = Char2CharMaps.fastIterator((Char2CharMap)m);

			while (i.hasNext()) {
				Entry e = (Entry)i.next();
				this.put(e.getCharKey(), e.getCharValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Character, ? extends Character>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Character, ? extends Character> e = (java.util.Map.Entry<? extends Character, ? extends Character>)i.next();
				this.put((Character)e.getKey(), (Character)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry> i = Char2CharMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.char2CharEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry> i = Char2CharMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getCharValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry implements Entry {
		protected char key;
		protected char value;

		public BasicEntry() {
		}

		public BasicEntry(Character key, Character value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(char key, char value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public char getCharKey() {
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
				return this.key == e.getCharKey() && this.value == e.getCharValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Character) {
					Object value = e.getValue();
					return value != null && value instanceof Character ? this.key == (Character)key && this.value == (Character)value : false;
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
		protected final Char2CharMap map;

		public BasicEntrySet(Char2CharMap map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				char k = e.getCharKey();
				return this.map.containsKey(k) && this.map.get(k) == e.getCharValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Character) {
					char k = (Character)key;
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
				return this.map.remove(e.getCharKey(), e.getCharValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Character) {
					char k = (Character)key;
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
