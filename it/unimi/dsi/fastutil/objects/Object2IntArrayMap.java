package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.objects.AbstractObject2IntMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Object2IntMap.FastEntrySet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Map.Entry;

public class Object2IntArrayMap<K> extends AbstractObject2IntMap<K> implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private transient Object[] key;
	private transient int[] value;
	private int size;

	public Object2IntArrayMap(Object[] key, int[] value) {
		this.key = key;
		this.value = value;
		this.size = key.length;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		}
	}

	public Object2IntArrayMap() {
		this.key = ObjectArrays.EMPTY_ARRAY;
		this.value = IntArrays.EMPTY_ARRAY;
	}

	public Object2IntArrayMap(int capacity) {
		this.key = new Object[capacity];
		this.value = new int[capacity];
	}

	public Object2IntArrayMap(Object2IntMap<K> m) {
		this(m.size());
		this.putAll(m);
	}

	public Object2IntArrayMap(Map<? extends K, ? extends Integer> m) {
		this(m.size());
		this.putAll(m);
	}

	public Object2IntArrayMap(Object[] key, int[] value, int size) {
		this.key = key;
		this.value = value;
		this.size = size;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		} else if (size > key.length) {
			throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
		}
	}

	public FastEntrySet<K> object2IntEntrySet() {
		return new Object2IntArrayMap.EntrySet();
	}

	private int findKey(Object k) {
		Object[] key = this.key;
		int i = this.size;

		while (i-- != 0) {
			if (Objects.equals(key[i], k)) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public int getInt(Object k) {
		Object[] key = this.key;
		int i = this.size;

		while (i-- != 0) {
			if (Objects.equals(key[i], k)) {
				return this.value[i];
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
		int i = this.size;

		while (i-- != 0) {
			this.key[i] = null;
		}

		this.size = 0;
	}

	@Override
	public boolean containsKey(Object k) {
		return this.findKey(k) != -1;
	}

	@Override
	public boolean containsValue(int v) {
		int i = this.size;

		while (i-- != 0) {
			if (this.value[i] == v) {
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
	public int put(K k, int v) {
		int oldKey = this.findKey(k);
		if (oldKey != -1) {
			int oldValue = this.value[oldKey];
			this.value[oldKey] = v;
			return oldValue;
		} else {
			if (this.size == this.key.length) {
				Object[] newKey = new Object[this.size == 0 ? 2 : this.size * 2];
				int[] newValue = new int[this.size == 0 ? 2 : this.size * 2];

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
	public int removeInt(Object k) {
		int oldPos = this.findKey(k);
		if (oldPos == -1) {
			return this.defRetValue;
		} else {
			int oldValue = this.value[oldPos];
			int tail = this.size - oldPos - 1;
			System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
			System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
			this.size--;
			this.key[this.size] = null;
			return oldValue;
		}
	}

	@Override
	public ObjectSet<K> keySet() {
		return new AbstractObjectSet<K>() {
			public boolean contains(Object k) {
				return Object2IntArrayMap.this.findKey(k) != -1;
			}

			public boolean remove(Object k) {
				int oldPos = Object2IntArrayMap.this.findKey(k);
				if (oldPos == -1) {
					return false;
				} else {
					int tail = Object2IntArrayMap.this.size - oldPos - 1;
					System.arraycopy(Object2IntArrayMap.this.key, oldPos + 1, Object2IntArrayMap.this.key, oldPos, tail);
					System.arraycopy(Object2IntArrayMap.this.value, oldPos + 1, Object2IntArrayMap.this.value, oldPos, tail);
					Object2IntArrayMap.this.size--;
					return true;
				}
			}

			@Override
			public ObjectIterator<K> iterator() {
				return new ObjectIterator<K>() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Object2IntArrayMap.this.size;
					}

					public K next() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return (K)Object2IntArrayMap.this.key[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Object2IntArrayMap.this.size - this.pos;
							System.arraycopy(Object2IntArrayMap.this.key, this.pos, Object2IntArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Object2IntArrayMap.this.value, this.pos, Object2IntArrayMap.this.value, this.pos - 1, tail);
							Object2IntArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Object2IntArrayMap.this.size;
			}

			public void clear() {
				Object2IntArrayMap.this.clear();
			}
		};
	}

	@Override
	public IntCollection values() {
		return new AbstractIntCollection() {
			@Override
			public boolean contains(int v) {
				return Object2IntArrayMap.this.containsValue(v);
			}

			@Override
			public IntIterator iterator() {
				return new IntIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Object2IntArrayMap.this.size;
					}

					@Override
					public int nextInt() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Object2IntArrayMap.this.value[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Object2IntArrayMap.this.size - this.pos;
							System.arraycopy(Object2IntArrayMap.this.key, this.pos, Object2IntArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Object2IntArrayMap.this.value, this.pos, Object2IntArrayMap.this.value, this.pos - 1, tail);
							Object2IntArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Object2IntArrayMap.this.size;
			}

			public void clear() {
				Object2IntArrayMap.this.clear();
			}
		};
	}

	public Object2IntArrayMap<K> clone() {
		Object2IntArrayMap<K> c;
		try {
			c = (Object2IntArrayMap<K>)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (Object[])this.key.clone();
		c.value = (int[])this.value.clone();
		return c;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; i < this.size; i++) {
			s.writeObject(this.key[i]);
			s.writeInt(this.value[i]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.key = new Object[this.size];
		this.value = new int[this.size];

		for (int i = 0; i < this.size; i++) {
			this.key[i] = s.readObject();
			this.value[i] = s.readInt();
		}
	}

	private final class EntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<K>> implements FastEntrySet<K> {
		private EntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<K>> iterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<K>>() {
				int curr = -1;
				int next = 0;

				public boolean hasNext() {
					return this.next < Object2IntArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<K> next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return new BasicEntry<>((K)Object2IntArrayMap.this.key[this.curr = this.next], Object2IntArrayMap.this.value[this.next++]);
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Object2IntArrayMap.this.size-- - this.next--;
						System.arraycopy(Object2IntArrayMap.this.key, this.next + 1, Object2IntArrayMap.this.key, this.next, tail);
						System.arraycopy(Object2IntArrayMap.this.value, this.next + 1, Object2IntArrayMap.this.value, this.next, tail);
						Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
					}
				}
			};
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<K>> fastIterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<K>>() {
				int next = 0;
				int curr = -1;
				final BasicEntry<K> entry = new BasicEntry<>();

				public boolean hasNext() {
					return this.next < Object2IntArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<K> next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						this.entry.key = (K)Object2IntArrayMap.this.key[this.curr = this.next];
						this.entry.value = Object2IntArrayMap.this.value[this.next++];
						return this.entry;
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Object2IntArrayMap.this.size-- - this.next--;
						System.arraycopy(Object2IntArrayMap.this.key, this.next + 1, Object2IntArrayMap.this.key, this.next, tail);
						System.arraycopy(Object2IntArrayMap.this.value, this.next + 1, Object2IntArrayMap.this.value, this.next, tail);
						Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
					}
				}
			};
		}

		public int size() {
			return Object2IntArrayMap.this.size;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getValue() != null && e.getValue() instanceof Integer) {
					K k = (K)e.getKey();
					return Object2IntArrayMap.this.containsKey(k) && Object2IntArrayMap.this.getInt(k) == (Integer)e.getValue();
				} else {
					return false;
				}
			}
		}

		public boolean remove(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getValue() != null && e.getValue() instanceof Integer) {
					K k = (K)e.getKey();
					int v = (Integer)e.getValue();
					int oldPos = Object2IntArrayMap.this.findKey(k);
					if (oldPos != -1 && v == Object2IntArrayMap.this.value[oldPos]) {
						int tail = Object2IntArrayMap.this.size - oldPos - 1;
						System.arraycopy(Object2IntArrayMap.this.key, oldPos + 1, Object2IntArrayMap.this.key, oldPos, tail);
						System.arraycopy(Object2IntArrayMap.this.value, oldPos + 1, Object2IntArrayMap.this.value, oldPos, tail);
						Object2IntArrayMap.this.size--;
						Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		}
	}
}
