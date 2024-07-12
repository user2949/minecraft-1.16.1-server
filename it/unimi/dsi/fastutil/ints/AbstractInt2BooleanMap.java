package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.ints.Int2BooleanMap.Entry;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractInt2BooleanMap extends AbstractInt2BooleanFunction implements Int2BooleanMap, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractInt2BooleanMap() {
	}

	@Override
	public boolean containsValue(boolean v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(int k) {
		ObjectIterator<Entry> i = this.int2BooleanEntrySet().iterator();

		while (i.hasNext()) {
			if (((Entry)i.next()).getIntKey() == k) {
				return true;
			}
		}

		return false;
	}

	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public IntSet keySet() {
		return new AbstractIntSet() {
			@Override
			public boolean contains(int k) {
				return AbstractInt2BooleanMap.this.containsKey(k);
			}

			public int size() {
				return AbstractInt2BooleanMap.this.size();
			}

			public void clear() {
				AbstractInt2BooleanMap.this.clear();
			}

			@Override
			public IntIterator iterator() {
				return new IntIterator() {
					private final ObjectIterator<Entry> i = Int2BooleanMaps.fastIterator(AbstractInt2BooleanMap.this);

					@Override
					public int nextInt() {
						return ((Entry)this.i.next()).getIntKey();
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
				return AbstractInt2BooleanMap.this.containsValue(k);
			}

			public int size() {
				return AbstractInt2BooleanMap.this.size();
			}

			public void clear() {
				AbstractInt2BooleanMap.this.clear();
			}

			@Override
			public BooleanIterator iterator() {
				return new BooleanIterator() {
					private final ObjectIterator<Entry> i = Int2BooleanMaps.fastIterator(AbstractInt2BooleanMap.this);

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

	public void putAll(Map<? extends Integer, ? extends Boolean> m) {
		if (m instanceof Int2BooleanMap) {
			ObjectIterator<Entry> i = Int2BooleanMaps.fastIterator((Int2BooleanMap)m);

			while (i.hasNext()) {
				Entry e = (Entry)i.next();
				this.put(e.getIntKey(), e.getBooleanValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Integer, ? extends Boolean>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Integer, ? extends Boolean> e = (java.util.Map.Entry<? extends Integer, ? extends Boolean>)i.next();
				this.put((Integer)e.getKey(), (Boolean)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry> i = Int2BooleanMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.int2BooleanEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry> i = Int2BooleanMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getIntKey()));
			s.append("=>");
			s.append(String.valueOf(e.getBooleanValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry implements Entry {
		protected int key;
		protected boolean value;

		public BasicEntry() {
		}

		public BasicEntry(Integer key, Boolean value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(int key, boolean value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public int getIntKey() {
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
				return this.key == e.getIntKey() && this.value == e.getBooleanValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Integer) {
					Object value = e.getValue();
					return value != null && value instanceof Boolean ? this.key == (Integer)key && this.value == (Boolean)value : false;
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
		protected final Int2BooleanMap map;

		public BasicEntrySet(Int2BooleanMap map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				int k = e.getIntKey();
				return this.map.containsKey(k) && this.map.get(k) == e.getBooleanValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Integer) {
					int k = (Integer)key;
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
				return this.map.remove(e.getIntKey(), e.getBooleanValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Integer) {
					int k = (Integer)key;
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
