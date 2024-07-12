package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMap.Entry;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractShort2BooleanMap extends AbstractShort2BooleanFunction implements Short2BooleanMap, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractShort2BooleanMap() {
	}

	@Override
	public boolean containsValue(boolean v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(short k) {
		ObjectIterator<Entry> i = this.short2BooleanEntrySet().iterator();

		while (i.hasNext()) {
			if (((Entry)i.next()).getShortKey() == k) {
				return true;
			}
		}

		return false;
	}

	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public ShortSet keySet() {
		return new AbstractShortSet() {
			@Override
			public boolean contains(short k) {
				return AbstractShort2BooleanMap.this.containsKey(k);
			}

			public int size() {
				return AbstractShort2BooleanMap.this.size();
			}

			public void clear() {
				AbstractShort2BooleanMap.this.clear();
			}

			@Override
			public ShortIterator iterator() {
				return new ShortIterator() {
					private final ObjectIterator<Entry> i = Short2BooleanMaps.fastIterator(AbstractShort2BooleanMap.this);

					@Override
					public short nextShort() {
						return ((Entry)this.i.next()).getShortKey();
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
				return AbstractShort2BooleanMap.this.containsValue(k);
			}

			public int size() {
				return AbstractShort2BooleanMap.this.size();
			}

			public void clear() {
				AbstractShort2BooleanMap.this.clear();
			}

			@Override
			public BooleanIterator iterator() {
				return new BooleanIterator() {
					private final ObjectIterator<Entry> i = Short2BooleanMaps.fastIterator(AbstractShort2BooleanMap.this);

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

	public void putAll(Map<? extends Short, ? extends Boolean> m) {
		if (m instanceof Short2BooleanMap) {
			ObjectIterator<Entry> i = Short2BooleanMaps.fastIterator((Short2BooleanMap)m);

			while (i.hasNext()) {
				Entry e = (Entry)i.next();
				this.put(e.getShortKey(), e.getBooleanValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Short, ? extends Boolean>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Short, ? extends Boolean> e = (java.util.Map.Entry<? extends Short, ? extends Boolean>)i.next();
				this.put((Short)e.getKey(), (Boolean)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry> i = Short2BooleanMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.short2BooleanEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry> i = Short2BooleanMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getShortKey()));
			s.append("=>");
			s.append(String.valueOf(e.getBooleanValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry implements Entry {
		protected short key;
		protected boolean value;

		public BasicEntry() {
		}

		public BasicEntry(Short key, Boolean value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(short key, boolean value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public short getShortKey() {
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
				return this.key == e.getShortKey() && this.value == e.getBooleanValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Short) {
					Object value = e.getValue();
					return value != null && value instanceof Boolean ? this.key == (Short)key && this.value == (Boolean)value : false;
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
		protected final Short2BooleanMap map;

		public BasicEntrySet(Short2BooleanMap map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry e = (Entry)o;
				short k = e.getShortKey();
				return this.map.containsKey(k) && this.map.get(k) == e.getBooleanValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Short) {
					short k = (Short)key;
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
				return this.map.remove(e.getShortKey(), e.getBooleanValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Short) {
					short k = (Short)key;
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
