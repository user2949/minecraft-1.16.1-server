package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractReference2ObjectMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap.FastEntrySet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Map.Entry;

public class Reference2ObjectArrayMap<K, V> extends AbstractReference2ObjectMap<K, V> implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private transient Object[] key;
	private transient Object[] value;
	private int size;

	public Reference2ObjectArrayMap(Object[] key, Object[] value) {
		this.key = key;
		this.value = value;
		this.size = key.length;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		}
	}

	public Reference2ObjectArrayMap() {
		this.key = ObjectArrays.EMPTY_ARRAY;
		this.value = ObjectArrays.EMPTY_ARRAY;
	}

	public Reference2ObjectArrayMap(int capacity) {
		this.key = new Object[capacity];
		this.value = new Object[capacity];
	}

	public Reference2ObjectArrayMap(Reference2ObjectMap<K, V> m) {
		this(m.size());
		this.putAll(m);
	}

	public Reference2ObjectArrayMap(Map<? extends K, ? extends V> m) {
		this(m.size());
		this.putAll(m);
	}

	public Reference2ObjectArrayMap(Object[] key, Object[] value, int size) {
		this.key = key;
		this.value = value;
		this.size = size;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		} else if (size > key.length) {
			throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
		}
	}

	public FastEntrySet<K, V> reference2ObjectEntrySet() {
		return new Reference2ObjectArrayMap.EntrySet();
	}

	private int findKey(Object k) {
		Object[] key = this.key;
		int i = this.size;

		while (i-- != 0) {
			if (key[i] == k) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public V get(Object k) {
		Object[] key = this.key;
		int i = this.size;

		while (i-- != 0) {
			if (key[i] == k) {
				return (V)this.value[i];
			}
		}

		return this.defRetValue;
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public void clear() {
		for (int i = this.size; i-- != 0; this.value[i] = null) {
			this.key[i] = null;
		}

		this.size = 0;
	}

	@Override
	public boolean containsKey(Object k) {
		return this.findKey(k) != -1;
	}

	@Override
	public boolean containsValue(Object v) {
		int i = this.size;

		while (i-- != 0) {
			if (Objects.equals(this.value[i], v)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

	@Override
	public V put(K k, V v) {
		int oldKey = this.findKey(k);
		if (oldKey != -1) {
			V oldValue = (V)this.value[oldKey];
			this.value[oldKey] = v;
			return oldValue;
		} else {
			if (this.size == this.key.length) {
				Object[] newKey = new Object[this.size == 0 ? 2 : this.size * 2];
				Object[] newValue = new Object[this.size == 0 ? 2 : this.size * 2];

				for (int i = this.size; i-- != 0; newValue[i] = this.value[i]) {
					newKey[i] = this.key[i];
				}

				this.key = newKey;
				this.value = newValue;
			}

			this.key[this.size] = k;
			this.value[this.size] = v;
			this.size++;
			return this.defRetValue;
		}
	}

	@Override
	public V remove(Object k) {
		int oldPos = this.findKey(k);
		if (oldPos == -1) {
			return this.defRetValue;
		} else {
			V oldValue = (V)this.value[oldPos];
			int tail = this.size - oldPos - 1;
			System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
			System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
			this.size--;
			this.key[this.size] = null;
			this.value[this.size] = null;
			return oldValue;
		}
	}

	@Override
	public ReferenceSet<K> keySet() {
		return new AbstractReferenceSet<K>() {
			public boolean contains(Object k) {
				return Reference2ObjectArrayMap.this.findKey(k) != -1;
			}

			public boolean remove(Object k) {
				int oldPos = Reference2ObjectArrayMap.this.findKey(k);
				if (oldPos == -1) {
					return false;
				} else {
					int tail = Reference2ObjectArrayMap.this.size - oldPos - 1;
					System.arraycopy(Reference2ObjectArrayMap.this.key, oldPos + 1, Reference2ObjectArrayMap.this.key, oldPos, tail);
					System.arraycopy(Reference2ObjectArrayMap.this.value, oldPos + 1, Reference2ObjectArrayMap.this.value, oldPos, tail);
					Reference2ObjectArrayMap.this.size--;
					return true;
				}
			}

			@Override
			public ObjectIterator<K> iterator() {
				return new ObjectIterator<K>() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Reference2ObjectArrayMap.this.size;
					}

					public K next() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return (K)Reference2ObjectArrayMap.this.key[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Reference2ObjectArrayMap.this.size - this.pos;
							System.arraycopy(Reference2ObjectArrayMap.this.key, this.pos, Reference2ObjectArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Reference2ObjectArrayMap.this.value, this.pos, Reference2ObjectArrayMap.this.value, this.pos - 1, tail);
							Reference2ObjectArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Reference2ObjectArrayMap.this.size;
			}

			public void clear() {
				Reference2ObjectArrayMap.this.clear();
			}
		};
	}

	@Override
	public ObjectCollection<V> values() {
		return new AbstractObjectCollection<V>() {
			public boolean contains(Object v) {
				return Reference2ObjectArrayMap.this.containsValue(v);
			}

			@Override
			public ObjectIterator<V> iterator() {
				return new ObjectIterator<V>() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Reference2ObjectArrayMap.this.size;
					}

					public V next() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return (V)Reference2ObjectArrayMap.this.value[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Reference2ObjectArrayMap.this.size - this.pos;
							System.arraycopy(Reference2ObjectArrayMap.this.key, this.pos, Reference2ObjectArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Reference2ObjectArrayMap.this.value, this.pos, Reference2ObjectArrayMap.this.value, this.pos - 1, tail);
							Reference2ObjectArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Reference2ObjectArrayMap.this.size;
			}

			public void clear() {
				Reference2ObjectArrayMap.this.clear();
			}
		};
	}

	public Reference2ObjectArrayMap<K, V> clone() {
		Reference2ObjectArrayMap<K, V> c;
		try {
			c = (Reference2ObjectArrayMap<K, V>)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (Object[])this.key.clone();
		c.value = (Object[])this.value.clone();
		return c;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; i < this.size; i++) {
			s.writeObject(this.key[i]);
			s.writeObject(this.value[i]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.key = new Object[this.size];
		this.value = new Object[this.size];

		for (int i = 0; i < this.size; i++) {
			this.key[i] = s.readObject();
			this.value[i] = s.readObject();
		}
	}

	private final class EntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V>> implements FastEntrySet<K, V> {
		private EntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V>> iterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V>>() {
				int curr = -1;
				int next = 0;

				public boolean hasNext() {
					return this.next < Reference2ObjectArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V> next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return new BasicEntry<>((K)Reference2ObjectArrayMap.this.key[this.curr = this.next], (V)Reference2ObjectArrayMap.this.value[this.next++]);
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Reference2ObjectArrayMap.this.size-- - this.next--;
						System.arraycopy(Reference2ObjectArrayMap.this.key, this.next + 1, Reference2ObjectArrayMap.this.key, this.next, tail);
						System.arraycopy(Reference2ObjectArrayMap.this.value, this.next + 1, Reference2ObjectArrayMap.this.value, this.next, tail);
						Reference2ObjectArrayMap.this.key[Reference2ObjectArrayMap.this.size] = null;
						Reference2ObjectArrayMap.this.value[Reference2ObjectArrayMap.this.size] = null;
					}
				}
			};
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V>> fastIterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V>>() {
				int next = 0;
				int curr = -1;
				final BasicEntry<K, V> entry = new BasicEntry<>();

				public boolean hasNext() {
					return this.next < Reference2ObjectArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<K, V> next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						this.entry.key = (K)Reference2ObjectArrayMap.this.key[this.curr = this.next];
						this.entry.value = (V)Reference2ObjectArrayMap.this.value[this.next++];
						return this.entry;
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Reference2ObjectArrayMap.this.size-- - this.next--;
						System.arraycopy(Reference2ObjectArrayMap.this.key, this.next + 1, Reference2ObjectArrayMap.this.key, this.next, tail);
						System.arraycopy(Reference2ObjectArrayMap.this.value, this.next + 1, Reference2ObjectArrayMap.this.value, this.next, tail);
						Reference2ObjectArrayMap.this.key[Reference2ObjectArrayMap.this.size] = null;
						Reference2ObjectArrayMap.this.value[Reference2ObjectArrayMap.this.size] = null;
					}
				}
			};
		}

		public int size() {
			return Reference2ObjectArrayMap.this.size;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				K k = (K)e.getKey();
				return Reference2ObjectArrayMap.this.containsKey(k) && Objects.equals(Reference2ObjectArrayMap.this.get(k), e.getValue());
			}
		}

		public boolean remove(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				K k = (K)e.getKey();
				V v = (V)e.getValue();
				int oldPos = Reference2ObjectArrayMap.this.findKey(k);
				if (oldPos != -1 && Objects.equals(v, Reference2ObjectArrayMap.this.value[oldPos])) {
					int tail = Reference2ObjectArrayMap.this.size - oldPos - 1;
					System.arraycopy(Reference2ObjectArrayMap.this.key, oldPos + 1, Reference2ObjectArrayMap.this.key, oldPos, tail);
					System.arraycopy(Reference2ObjectArrayMap.this.value, oldPos + 1, Reference2ObjectArrayMap.this.value, oldPos, tail);
					Reference2ObjectArrayMap.this.size--;
					Reference2ObjectArrayMap.this.key[Reference2ObjectArrayMap.this.size] = null;
					Reference2ObjectArrayMap.this.value[Reference2ObjectArrayMap.this.size] = null;
					return true;
				} else {
					return false;
				}
			}
		}
	}
}
