package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractChar2ObjectMap<V> extends AbstractChar2ObjectFunction<V> implements Char2ObjectMap<V>, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractChar2ObjectMap() {
	}

	public boolean containsValue(Object v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(char k) {
		ObjectIterator<Entry<V>> i = this.char2ObjectEntrySet().iterator();

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
				return AbstractChar2ObjectMap.this.containsKey(k);
			}

			public int size() {
				return AbstractChar2ObjectMap.this.size();
			}

			public void clear() {
				AbstractChar2ObjectMap.this.clear();
			}

			@Override
			public CharIterator iterator() {
				return new CharIterator() {
					private final ObjectIterator<Entry<V>> i = Char2ObjectMaps.fastIterator(AbstractChar2ObjectMap.this);

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
	public ObjectCollection<V> values() {
		return new AbstractObjectCollection<V>() {
			public boolean contains(Object k) {
				return AbstractChar2ObjectMap.this.containsValue(k);
			}

			public int size() {
				return AbstractChar2ObjectMap.this.size();
			}

			public void clear() {
				AbstractChar2ObjectMap.this.clear();
			}

			@Override
			public ObjectIterator<V> iterator() {
				return new ObjectIterator<V>() {
					private final ObjectIterator<Entry<V>> i = Char2ObjectMaps.fastIterator(AbstractChar2ObjectMap.this);

					public V next() {
						return (V)((Entry)this.i.next()).getValue();
					}

					public boolean hasNext() {
						return this.i.hasNext();
					}
				};
			}
		};
	}

	public void putAll(Map<? extends Character, ? extends V> m) {
		if (m instanceof Char2ObjectMap) {
			ObjectIterator<Entry<V>> i = Char2ObjectMaps.fastIterator((Char2ObjectMap<V>)m);

			while (i.hasNext()) {
				Entry<? extends V> e = (Entry<? extends V>)i.next();
				this.put(e.getCharKey(), (V)e.getValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Character, ? extends V>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Character, ? extends V> e = (java.util.Map.Entry<? extends Character, ? extends V>)i.next();
				this.put((Character)e.getKey(), (V)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry<V>> i = Char2ObjectMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.char2ObjectEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry<V>> i = Char2ObjectMaps.fastIterator(this);
		int n = this.size();
		boolean first = true;
		s.append("{");

		while (n-- != 0) {
			if (first) {
				first = false;
			} else {
				s.append(", ");
			}

			Entry<V> e = (Entry<V>)i.next();
			s.append(String.valueOf(e.getCharKey()));
			s.append("=>");
			if (this == e.getValue()) {
				s.append("(this map)");
			} else {
				s.append(String.valueOf(e.getValue()));
			}
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry<V> implements Entry<V> {
		protected char key;
		protected V value;

		public BasicEntry() {
		}

		public BasicEntry(Character key, V value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(char key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public char getCharKey() {
			return this.key;
		}

		public V getValue() {
			return this.value;
		}

		public V setValue(V value) {
			throw new UnsupportedOperationException();
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry<V> e = (Entry<V>)o;
				return this.key == e.getCharKey() && Objects.equals(this.value, e.getValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Character) {
					Object value = e.getValue();
					return this.key == (Character)key && Objects.equals(this.value, value);
				} else {
					return false;
				}
			}
		}

		public int hashCode() {
			return this.key ^ (this.value == null ? 0 : this.value.hashCode());
		}

		public String toString() {
			return this.key + "->" + this.value;
		}
	}

	public abstract static class BasicEntrySet<V> extends AbstractObjectSet<Entry<V>> {
		protected final Char2ObjectMap<V> map;

		public BasicEntrySet(Char2ObjectMap<V> map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry<V> e = (Entry<V>)o;
				char k = e.getCharKey();
				return this.map.containsKey(k) && Objects.equals(this.map.get(k), e.getValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Character) {
					char k = (Character)key;
					Object value = e.getValue();
					return this.map.containsKey(k) && Objects.equals(this.map.get(k), value);
				} else {
					return false;
				}
			}
		}

		public boolean remove(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry<V> e = (Entry<V>)o;
				return this.map.remove(e.getCharKey(), e.getValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Character) {
					char k = (Character)key;
					Object v = e.getValue();
					return this.map.remove(k, v);
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
