package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.doubles.Double2CharMap.Entry;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractDouble2CharMap extends AbstractDouble2CharFunction implements Double2CharMap, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractDouble2CharMap() {
	}

	@Override
	public boolean containsValue(char v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(double k) {
		ObjectIterator<Entry> i = this.double2CharEntrySet().iterator();

		while (i.hasNext()) {
			if (((Entry)i.next()).getDoubleKey() == k) {
				return true;
			}
		}

		return false;
	}

	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public DoubleSet keySet() {
		return new AbstractDoubleSet() {
			@Override
			public boolean contains(double k) {
				return AbstractDouble2CharMap.this.containsKey(k);
			}

			public int size() {
				return AbstractDouble2CharMap.this.size();
			}

			public void clear() {
				AbstractDouble2CharMap.this.clear();
			}

			@Override
			public DoubleIterator iterator() {
				return new DoubleIterator() {
					private final ObjectIterator<Entry> i = Double2CharMaps.fastIterator(AbstractDouble2CharMap.this);

					@Override
					public double nextDouble() {
						return ((Entry)this.i.next()).getDoubleKey();
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
				return AbstractDouble2CharMap.this.containsValue(k);
			}

			public int size() {
				return AbstractDouble2CharMap.this.size();
			}

			public void clear() {
				AbstractDouble2CharMap.this.clear();
			}

			@Override
			public CharIterator iterator() {
				return new CharIterator() {
					private final ObjectIterator<Entry> i = Double2CharMaps.fastIterator(AbstractDouble2CharMap.this);

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

	public void putAll(Map<? extends Double, ? extends Character> m) {
		if (m instanceof Double2CharMap) {
			ObjectIterator<Entry> i = Double2CharMaps.fastIterator((Double2CharMap)m);

			while (i.hasNext()) {
				Entry e = (Entry)i.next();
				this.put(e.getDoubleKey(), e.getCharValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Double, ? extends Character>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Double, ? extends Character> e = (java.util.Map.Entry<? extends Double, ? extends Character>)i.next();
				this.put((Double)e.getKey(), (Character)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry> i = Double2CharMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.double2CharEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry> i = Double2CharMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getDoubleKey()));
			s.append("=>");
			s.append(String.valueOf(e.getCharValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry implements Entry {
		protected double key;
		protected char value;

		public BasicEntry() {
		}

		public BasicEntry(Double key, Character value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(double key, char value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public double getDoubleKey() {
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
				return Double.doubleToLongBits(this.key) == Double.doubleToLongBits(e.getDoubleKey()) && this.value == e.getCharValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Double) {
					Object value = e.getValue();
					return value != null && value instanceof Character
						? Double.doubleToLongBits(this.key) == Double.doubleToLongBits((Double)key) && this.value == (Character)value
						: false;
				} else {
					return false;
				}
			}
		}

		public int hashCode() {
			return HashCommon.double2int(this.key) ^ this.value;
		}

		public String toString() {
			return this.key + "->" + this.value;
		}
	}

	public abstract static class BasicEntrySet extends AbstractObjectSet<Entry> {
		protected final Double2CharMap map;

		public BasicEntrySet(Double2CharMap map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				double k = e.getDoubleKey();
				return this.map.containsKey(k) && this.map.get(k) == e.getCharValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Double) {
					double k = (Double)key;
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
				return this.map.remove(e.getDoubleKey(), e.getCharValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Double) {
					double k = (Double)key;
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
