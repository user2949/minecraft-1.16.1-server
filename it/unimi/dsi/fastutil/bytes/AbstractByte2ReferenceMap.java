package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2ReferenceMap.Entry;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractByte2ReferenceMap<V> extends AbstractByte2ReferenceFunction<V> implements Byte2ReferenceMap<V>, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractByte2ReferenceMap() {
	}

	public boolean containsValue(Object v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(byte k) {
		ObjectIterator<Entry<V>> i = this.byte2ReferenceEntrySet().iterator();

		while (i.hasNext()) {
			if (((Entry)i.next()).getByteKey() == k) {
				return true;
			}
		}

		return false;
	}

	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public ByteSet keySet() {
		return new AbstractByteSet() {
			@Override
			public boolean contains(byte k) {
				return AbstractByte2ReferenceMap.this.containsKey(k);
			}

			public int size() {
				return AbstractByte2ReferenceMap.this.size();
			}

			public void clear() {
				AbstractByte2ReferenceMap.this.clear();
			}

			@Override
			public ByteIterator iterator() {
				return new ByteIterator() {
					private final ObjectIterator<Entry<V>> i = Byte2ReferenceMaps.fastIterator(AbstractByte2ReferenceMap.this);

					@Override
					public byte nextByte() {
						return ((Entry)this.i.next()).getByteKey();
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
	public ReferenceCollection<V> values() {
		return new AbstractReferenceCollection<V>() {
			public boolean contains(Object k) {
				return AbstractByte2ReferenceMap.this.containsValue(k);
			}

			public int size() {
				return AbstractByte2ReferenceMap.this.size();
			}

			public void clear() {
				AbstractByte2ReferenceMap.this.clear();
			}

			@Override
			public ObjectIterator<V> iterator() {
				return new ObjectIterator<V>() {
					private final ObjectIterator<Entry<V>> i = Byte2ReferenceMaps.fastIterator(AbstractByte2ReferenceMap.this);

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

	public void putAll(Map<? extends Byte, ? extends V> m) {
		if (m instanceof Byte2ReferenceMap) {
			ObjectIterator<Entry<V>> i = Byte2ReferenceMaps.fastIterator((Byte2ReferenceMap<V>)m);

			while (i.hasNext()) {
				Entry<? extends V> e = (Entry<? extends V>)i.next();
				this.put(e.getByteKey(), (V)e.getValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends Byte, ? extends V>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends Byte, ? extends V> e = (java.util.Map.Entry<? extends Byte, ? extends V>)i.next();
				this.put((Byte)e.getKey(), (V)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry<V>> i = Byte2ReferenceMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.byte2ReferenceEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry<V>> i = Byte2ReferenceMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getByteKey()));
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
		protected byte key;
		protected V value;

		public BasicEntry() {
		}

		public BasicEntry(Byte key, V value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(byte key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public byte getByteKey() {
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
				return this.key == e.getByteKey() && this.value == e.getValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Byte) {
					Object value = e.getValue();
					return this.key == (Byte)key && this.value == value;
				} else {
					return false;
				}
			}
		}

		public int hashCode() {
			return this.key ^ (this.value == null ? 0 : System.identityHashCode(this.value));
		}

		public String toString() {
			return this.key + "->" + this.value;
		}
	}

	public abstract static class BasicEntrySet<V> extends AbstractObjectSet<Entry<V>> {
		protected final Byte2ReferenceMap<V> map;

		public BasicEntrySet(Byte2ReferenceMap<V> map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry<V> e = (Entry<V>)o;
				byte k = e.getByteKey();
				return this.map.containsKey(k) && this.map.get(k) == e.getValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Byte) {
					byte k = (Byte)key;
					Object value = e.getValue();
					return this.map.containsKey(k) && this.map.get(k) == value;
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
				return this.map.remove(e.getByteKey(), e.getValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				if (key != null && key instanceof Byte) {
					byte k = (Byte)key;
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
