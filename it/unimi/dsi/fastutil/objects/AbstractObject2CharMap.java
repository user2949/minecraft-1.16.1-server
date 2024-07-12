package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.objects.Object2CharMap.Entry;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractObject2CharMap<K> extends AbstractObject2CharFunction<K> implements Object2CharMap<K>, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractObject2CharMap() {
	}

	@Override
	public boolean containsValue(char v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(Object k) {
		ObjectIterator<Entry<K>> i = this.object2CharEntrySet().iterator();

		while (i.hasNext()) {
			if (((Entry)i.next()).getKey() == k) {
				return true;
			}
		}

		return false;
	}

	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public ObjectSet<K> keySet() {
		return new AbstractObjectSet<K>() {
			public boolean contains(Object k) {
				return AbstractObject2CharMap.this.containsKey(k);
			}

			public int size() {
				return AbstractObject2CharMap.this.size();
			}

			public void clear() {
				AbstractObject2CharMap.this.clear();
			}

			@Override
			public ObjectIterator<K> iterator() {
				return new ObjectIterator<K>() {
					private final ObjectIterator<Entry<K>> i = Object2CharMaps.fastIterator(AbstractObject2CharMap.this);

					public K next() {
						return (K)((Entry)this.i.next()).getKey();
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
				return AbstractObject2CharMap.this.containsValue(k);
			}

			public int size() {
				return AbstractObject2CharMap.this.size();
			}

			public void clear() {
				AbstractObject2CharMap.this.clear();
			}

			@Override
			public CharIterator iterator() {
				return new CharIterator() {
					private final ObjectIterator<Entry<K>> i = Object2CharMaps.fastIterator(AbstractObject2CharMap.this);

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

	public void putAll(Map<? extends K, ? extends Character> m) {
		if (m instanceof Object2CharMap) {
			ObjectIterator<Entry<K>> i = Object2CharMaps.fastIterator((Object2CharMap<K>)m);

			while (i.hasNext()) {
				Entry<? extends K> e = (Entry<? extends K>)i.next();
				this.put((K)e.getKey(), e.getCharValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends K, ? extends Character>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends K, ? extends Character> e = (java.util.Map.Entry<? extends K, ? extends Character>)i.next();
				this.put((K)e.getKey(), (Character)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry<K>> i = Object2CharMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.object2CharEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry<K>> i = Object2CharMaps.fastIterator(this);
		int n = this.size();
		boolean first = true;
		s.append("{");

		while (n-- != 0) {
			if (first) {
				first = false;
			} else {
				s.append(", ");
			}

			Entry<K> e = (Entry<K>)i.next();
			if (this == e.getKey()) {
				s.append("(this map)");
			} else {
				s.append(String.valueOf(e.getKey()));
			}

			s.append("=>");
			s.append(String.valueOf(e.getCharValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry<K> implements Entry<K> {
		protected K key;
		protected char value;

		public BasicEntry() {
		}

		public BasicEntry(K key, Character value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(K key, char value) {
			this.key = key;
			this.value = value;
		}

		public K getKey() {
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
				Entry<K> e = (Entry<K>)o;
				return Objects.equals(this.key, e.getKey()) && this.value == e.getCharValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				Object value = e.getValue();
				return value != null && value instanceof Character ? Objects.equals(this.key, key) && this.value == (Character)value : false;
			}
		}

		public int hashCode() {
			return (this.key == null ? 0 : this.key.hashCode()) ^ this.value;
		}

		public String toString() {
			return this.key + "->" + this.value;
		}
	}

	public abstract static class BasicEntrySet<K> extends AbstractObjectSet<Entry<K>> {
		protected final Object2CharMap<K> map;

		public BasicEntrySet(Object2CharMap<K> map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry<K> e = (Entry<K>)o;
				K k = (K)e.getKey();
				return this.map.containsKey(k) && this.map.getChar(k) == e.getCharValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object k = e.getKey();
				Object value = e.getValue();
				return value != null && value instanceof Character ? this.map.containsKey(k) && this.map.getChar(k) == (Character)value : false;
			}
		}

		public boolean remove(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry<K> e = (Entry<K>)o;
				return this.map.remove(e.getKey(), e.getCharValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object k = e.getKey();
				Object value = e.getValue();
				if (value != null && value instanceof Character) {
					char v = (Character)value;
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
