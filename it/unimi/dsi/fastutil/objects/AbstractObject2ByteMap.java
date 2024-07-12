package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.objects.Object2ByteMap.Entry;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractObject2ByteMap<K> extends AbstractObject2ByteFunction<K> implements Object2ByteMap<K>, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;

	protected AbstractObject2ByteMap() {
	}

	@Override
	public boolean containsValue(byte v) {
		return this.values().contains(v);
	}

	@Override
	public boolean containsKey(Object k) {
		ObjectIterator<Entry<K>> i = this.object2ByteEntrySet().iterator();

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
				return AbstractObject2ByteMap.this.containsKey(k);
			}

			public int size() {
				return AbstractObject2ByteMap.this.size();
			}

			public void clear() {
				AbstractObject2ByteMap.this.clear();
			}

			@Override
			public ObjectIterator<K> iterator() {
				return new ObjectIterator<K>() {
					private final ObjectIterator<Entry<K>> i = Object2ByteMaps.fastIterator(AbstractObject2ByteMap.this);

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
	public ByteCollection values() {
		return new AbstractByteCollection() {
			@Override
			public boolean contains(byte k) {
				return AbstractObject2ByteMap.this.containsValue(k);
			}

			public int size() {
				return AbstractObject2ByteMap.this.size();
			}

			public void clear() {
				AbstractObject2ByteMap.this.clear();
			}

			@Override
			public ByteIterator iterator() {
				return new ByteIterator() {
					private final ObjectIterator<Entry<K>> i = Object2ByteMaps.fastIterator(AbstractObject2ByteMap.this);

					@Override
					public byte nextByte() {
						return ((Entry)this.i.next()).getByteValue();
					}

					public boolean hasNext() {
						return this.i.hasNext();
					}
				};
			}
		};
	}

	public void putAll(Map<? extends K, ? extends Byte> m) {
		if (m instanceof Object2ByteMap) {
			ObjectIterator<Entry<K>> i = Object2ByteMaps.fastIterator((Object2ByteMap<K>)m);

			while (i.hasNext()) {
				Entry<? extends K> e = (Entry<? extends K>)i.next();
				this.put((K)e.getKey(), e.getByteValue());
			}
		} else {
			int n = m.size();
			Iterator<? extends java.util.Map.Entry<? extends K, ? extends Byte>> i = m.entrySet().iterator();

			while (n-- != 0) {
				java.util.Map.Entry<? extends K, ? extends Byte> e = (java.util.Map.Entry<? extends K, ? extends Byte>)i.next();
				this.put((K)e.getKey(), (Byte)e.getValue());
			}
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		ObjectIterator<Entry<K>> i = Object2ByteMaps.fastIterator(this);

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
			return m.size() != this.size() ? false : this.object2ByteEntrySet().containsAll(m.entrySet());
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<Entry<K>> i = Object2ByteMaps.fastIterator(this);
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
			s.append(String.valueOf(e.getByteValue()));
		}

		s.append("}");
		return s.toString();
	}

	public static class BasicEntry<K> implements Entry<K> {
		protected K key;
		protected byte value;

		public BasicEntry() {
		}

		public BasicEntry(K key, Byte value) {
			this.key = key;
			this.value = value;
		}

		public BasicEntry(K key, byte value) {
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return this.key;
		}

		@Override
		public byte getByteValue() {
			return this.value;
		}

		@Override
		public byte setValue(byte value) {
			throw new UnsupportedOperationException();
		}

		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry<K> e = (Entry<K>)o;
				return Objects.equals(this.key, e.getKey()) && this.value == e.getByteValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object key = e.getKey();
				Object value = e.getValue();
				return value != null && value instanceof Byte ? Objects.equals(this.key, key) && this.value == (Byte)value : false;
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
		protected final Object2ByteMap<K> map;

		public BasicEntrySet(Object2ByteMap<K> map) {
			this.map = map;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry<K> e = (Entry<K>)o;
				K k = (K)e.getKey();
				return this.map.containsKey(k) && this.map.getByte(k) == e.getByteValue();
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object k = e.getKey();
				Object value = e.getValue();
				return value != null && value instanceof Byte ? this.map.containsKey(k) && this.map.getByte(k) == (Byte)value : false;
			}
		}

		public boolean remove(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else if (o instanceof Entry) {
				Entry<K> e = (Entry<K>)o;
				return this.map.remove(e.getKey(), e.getByteValue());
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				Object k = e.getKey();
				Object value = e.getValue();
				if (value != null && value instanceof Byte) {
					byte v = (Byte)value;
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
