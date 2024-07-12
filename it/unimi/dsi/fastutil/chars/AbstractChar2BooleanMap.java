package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractChar2BooleanMap extends AbstractChar2BooleanFunction implements Char2BooleanMap, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractChar2BooleanMap() {
	}

	@Override
	public boolean containsValue(boolean v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(char k) {
		ObjectIterator<Entry> i = this.char2BooleanEntrySet().iterator();

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
				return AbstractChar2BooleanMap.this.containsKey(k);
			}

			public int size() {
				return AbstractChar2BooleanMap.this.size();
			}

			public void clear() {
				AbstractChar2BooleanMap.this.clear();
			}

			@Override
			public CharIterator iterator() {
				return new CharIterator() {
					private final ObjectIterator<Entry> i = Char2BooleanMaps.fastIterator(AbstractChar2BooleanMap.this);

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
	public BooleanCollection values() {
		return new AbstractBooleanCollection() {
			@Override
			public boolean contains(boolean k) {
				return AbstractChar2BooleanMap.this.containsValue(k);
			}

			public int size() {
				return AbstractChar2BooleanMap.this.size();
			}

			public void clear() {
				AbstractChar2BooleanMap.this.clear();
			}

			@Override
			public BooleanIterator iterator() {
				return new BooleanIterator() {
					private final ObjectIterator<Entry> i = Char2BooleanMaps.fastIterator(AbstractChar2BooleanMap.this);

					@Override
					public boolean nextBoolean() {
						return ((Entry)this.i.next()).getBooleanValue();
					}

					public boolean hasNext() {
						return this.i.hasNext();
					}
				};
			}
		};
	}

	public void putAll(Map<? extends Character, ? extends Boolean> m) {
		if (m instanceof Char2BooleanMap) {
			ObjectIterator<Entry> i = Char2BooleanMaps.fastIterator((Char2BooleanMap)m);

			while (i.hasNext()) {
				Entry e = (Entry)i.next();
				this.put(e.getCharKey(), e.getBooleanValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Character, ? extends Boolean>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Character, ? extends Boolean> e = (java.util.Map.Entry<? extends Character, ? extends Boolean>)i.next();
				this.put((Character)e.getKey(), (Boolean)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry> i = Char2BooleanMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.char2BooleanEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry> i = Char2BooleanMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getBooleanValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry implements Entry {
		protected char key;
		protected boolean value;

		public BasicEntry() {
		}

		public BasicEntry(Character key, Boolean value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(char key, boolean value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public char getCharKey() {
			return this.key;
		}

		@Override
		public boolean getBooleanValue() {
			return this.value;
		}

		@Override
		public boolean setValue(boolean value) {
			throw new UnsupportedOperationException();
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				return this.key == e.getCharKey() && this.value == e.getBooleanValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Character) {
					Object value = e.getValue();
					return value != null && value instanceof Boolean ? this.key == (Character)key && this.value == (Boolean)value : false;
				} else {
					return false;
				}
			}
		}

		public int hashCode() {
			return this.key ^ (this.value ? 1231 : 1237);
		}

		public String toString() {
			return this.key + "->" + this.value;
		}
	}

	public abstract static class BasicEntrySet extends AbstractObjectSet<Entry> {
		protected final Char2BooleanMap map;

		public BasicEntrySet(Char2BooleanMap map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				char k = e.getCharKey();
				return this.map.containsKey(k) && this.map.get(k) == e.getBooleanValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Character) {
					char k = (Character)key;
					Object value = e.getValue();
					return value != null && value instanceof Boolean ? this.map.containsKey(k) && this.map.get(k) == (Boolean)value : false;
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
				return this.map.remove(e.getCharKey(), e.getBooleanValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Character) {
					char k = (Character)key;
					Object value = e.getValue();
					if (value != null && value instanceof Boolean) {
						boolean v = (Boolean)value;
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
