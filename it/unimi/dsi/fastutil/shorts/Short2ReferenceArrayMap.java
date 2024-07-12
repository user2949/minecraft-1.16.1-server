package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import it.unimi.dsi.fastutil.shorts.AbstractShort2ReferenceMap.BasicEntry;
import it.unimi.dsi.fastutil.shorts.Short2ReferenceMap.FastEntrySet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

public class Short2ReferenceArrayMap<V> extends AbstractShort2ReferenceMap<V> implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private transient short[] key;
	private transient Object[] value;
	private int size;

	public Short2ReferenceArrayMap(short[] key, Object[] value) {
		this.key = key;
		this.value = value;
		this.size = key.length;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		}
	}

	public Short2ReferenceArrayMap() {
		this.key = ShortArrays.EMPTY_ARRAY;
		this.value = ObjectArrays.EMPTY_ARRAY;
	}

	public Short2ReferenceArrayMap(int capacity) {
		this.key = new short[capacity];
		this.value = new Object[capacity];
	}

	public Short2ReferenceArrayMap(Short2ReferenceMap<V> m) {
		this(m.size());
		this.putAll(m);
	}

	public Short2ReferenceArrayMap(Map<? extends Short, ? extends V> m) {
		this(m.size());
		this.putAll(m);
	}

	public Short2ReferenceArrayMap(short[] key, Object[] value, int size) {
		this.key = key;
		this.value = value;
		this.size = size;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		} else if (size > key.length) {
			throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
		}
	}

	public FastEntrySet<V> short2ReferenceEntrySet() {
		return new Short2ReferenceArrayMap.EntrySet();
	}

	private int findKey(short k) {
		short[] key = this.key;
		int i = this.size;

		while (i-- != 0) {
			if (key[i] == k) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public V get(short k) {
		short[] key = this.key;
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
		int i = this.size;

		while (i-- != 0) {
			this.value[i] = null;
		}

		this.size = 0;
	}

	@Override
	public boolean containsKey(short k) {
		return this.findKey(k) != -1;
	}

	@Override
	public boolean containsValue(Object v) {
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
	public V put(short k, V v) {
		int oldKey = this.findKey(k);
		if (oldKey != -1) {
			V oldValue = (V)this.value[oldKey];
			this.value[oldKey] = v;
			return oldValue;
		} else {
			if (this.size == this.key.length) {
				short[] newKey = new short[this.size == 0 ? 2 : this.size * 2];
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
	public V remove(short k) {
		int oldPos = this.findKey(k);
		if (oldPos == -1) {
			return this.defRetValue;
		} else {
			V oldValue = (V)this.value[oldPos];
			int tail = this.size - oldPos - 1;
			System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
			System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
			this.size--;
			this.value[this.size] = null;
			return oldValue;
		}
	}

	@Override
	public ShortSet keySet() {
		return new AbstractShortSet() {
			@Override
			public boolean contains(short k) {
				return Short2ReferenceArrayMap.this.findKey(k) != -1;
			}

			@Override
			public boolean remove(short k) {
				int oldPos = Short2ReferenceArrayMap.this.findKey(k);
				if (oldPos == -1) {
					return false;
				} else {
					int tail = Short2ReferenceArrayMap.this.size - oldPos - 1;
					System.arraycopy(Short2ReferenceArrayMap.this.key, oldPos + 1, Short2ReferenceArrayMap.this.key, oldPos, tail);
					System.arraycopy(Short2ReferenceArrayMap.this.value, oldPos + 1, Short2ReferenceArrayMap.this.value, oldPos, tail);
					Short2ReferenceArrayMap.this.size--;
					return true;
				}
			}

			@Override
			public ShortIterator iterator() {
				return new ShortIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Short2ReferenceArrayMap.this.size;
					}

					@Override
					public short nextShort() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Short2ReferenceArrayMap.this.key[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Short2ReferenceArrayMap.this.size - this.pos;
							System.arraycopy(Short2ReferenceArrayMap.this.key, this.pos, Short2ReferenceArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Short2ReferenceArrayMap.this.value, this.pos, Short2ReferenceArrayMap.this.value, this.pos - 1, tail);
							Short2ReferenceArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Short2ReferenceArrayMap.this.size;
			}

			public void clear() {
				Short2ReferenceArrayMap.this.clear();
			}
		};
	}

	@Override
	public ReferenceCollection<V> values() {
		return new AbstractReferenceCollection<V>() {
			public boolean contains(Object v) {
				return Short2ReferenceArrayMap.this.containsValue(v);
			}

			@Override
			public ObjectIterator<V> iterator() {
				return new ObjectIterator<V>() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Short2ReferenceArrayMap.this.size;
					}

					public V next() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return (V)Short2ReferenceArrayMap.this.value[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Short2ReferenceArrayMap.this.size - this.pos;
							System.arraycopy(Short2ReferenceArrayMap.this.key, this.pos, Short2ReferenceArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Short2ReferenceArrayMap.this.value, this.pos, Short2ReferenceArrayMap.this.value, this.pos - 1, tail);
							Short2ReferenceArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Short2ReferenceArrayMap.this.size;
			}

			public void clear() {
				Short2ReferenceArrayMap.this.clear();
			}
		};
	}

	public Short2ReferenceArrayMap<V> clone() {
		Short2ReferenceArrayMap<V> c;
		try {
			c = (Short2ReferenceArrayMap<V>)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (short[])this.key.clone();
		c.value = (Object[])this.value.clone();
		return c;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; i < this.size; i++) {
			s.writeShort(this.key[i]);
			s.writeObject(this.value[i]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.key = new short[this.size];
		this.value = new Object[this.size];

		for (int i = 0; i < this.size; i++) {
			this.key[i] = s.readShort();
			this.value[i] = s.readObject();
		}
	}

	private final class EntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.shorts.Short2ReferenceMap.Entry<V>> implements FastEntrySet<V> {
		private EntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.shorts.Short2ReferenceMap.Entry<V>> iterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.shorts.Short2ReferenceMap.Entry<V>>() {
				int curr = -1;
				int next = 0;

				public boolean hasNext() {
					return this.next < Short2ReferenceArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.shorts.Short2ReferenceMap.Entry<V> next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return new BasicEntry<>(Short2ReferenceArrayMap.this.key[this.curr = this.next], (V)Short2ReferenceArrayMap.this.value[this.next++]);
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Short2ReferenceArrayMap.this.size-- - this.next--;
						System.arraycopy(Short2ReferenceArrayMap.this.key, this.next + 1, Short2ReferenceArrayMap.this.key, this.next, tail);
						System.arraycopy(Short2ReferenceArrayMap.this.value, this.next + 1, Short2ReferenceArrayMap.this.value, this.next, tail);
						Short2ReferenceArrayMap.this.value[Short2ReferenceArrayMap.this.size] = null;
					}
				}
			};
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.shorts.Short2ReferenceMap.Entry<V>> fastIterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.shorts.Short2ReferenceMap.Entry<V>>() {
				int next = 0;
				int curr = -1;
				final BasicEntry<V> entry = new BasicEntry<>();

				public boolean hasNext() {
					return this.next < Short2ReferenceArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.shorts.Short2ReferenceMap.Entry<V> next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						this.entry.key = Short2ReferenceArrayMap.this.key[this.curr = this.next];
						this.entry.value = (V)Short2ReferenceArrayMap.this.value[this.next++];
						return this.entry;
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Short2ReferenceArrayMap.this.size-- - this.next--;
						System.arraycopy(Short2ReferenceArrayMap.this.key, this.next + 1, Short2ReferenceArrayMap.this.key, this.next, tail);
						System.arraycopy(Short2ReferenceArrayMap.this.value, this.next + 1, Short2ReferenceArrayMap.this.value, this.next, tail);
						Short2ReferenceArrayMap.this.value[Short2ReferenceArrayMap.this.size] = null;
					}
				}
			};
		}

		public int size() {
			return Short2ReferenceArrayMap.this.size;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() != null && e.getKey() instanceof Short) {
					short k = (Short)e.getKey();
					return Short2ReferenceArrayMap.this.containsKey(k) && Short2ReferenceArrayMap.this.get(k) == e.getValue();
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
				if (e.getKey() != null && e.getKey() instanceof Short) {
					short k = (Short)e.getKey();
					V v = (V)e.getValue();
					int oldPos = Short2ReferenceArrayMap.this.findKey(k);
					if (oldPos != -1 && v == Short2ReferenceArrayMap.this.value[oldPos]) {
						int tail = Short2ReferenceArrayMap.this.size - oldPos - 1;
						System.arraycopy(Short2ReferenceArrayMap.this.key, oldPos + 1, Short2ReferenceArrayMap.this.key, oldPos, tail);
						System.arraycopy(Short2ReferenceArrayMap.this.value, oldPos + 1, Short2ReferenceArrayMap.this.value, oldPos, tail);
						Short2ReferenceArrayMap.this.size--;
						Short2ReferenceArrayMap.this.value[Short2ReferenceArrayMap.this.size] = null;
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
