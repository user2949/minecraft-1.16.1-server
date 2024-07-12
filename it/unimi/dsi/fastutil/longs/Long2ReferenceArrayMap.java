package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.AbstractLong2ReferenceMap.BasicEntry;
import it.unimi.dsi.fastutil.longs.Long2ReferenceMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

public class Long2ReferenceArrayMap<V> extends AbstractLong2ReferenceMap<V> implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private transient long[] key;
	private transient Object[] value;
	private int size;

	public Long2ReferenceArrayMap(long[] key, Object[] value) {
		this.key = key;
		this.value = value;
		this.size = key.length;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		}
	}

	public Long2ReferenceArrayMap() {
		this.key = LongArrays.EMPTY_ARRAY;
		this.value = ObjectArrays.EMPTY_ARRAY;
	}

	public Long2ReferenceArrayMap(int capacity) {
		this.key = new long[capacity];
		this.value = new Object[capacity];
	}

	public Long2ReferenceArrayMap(Long2ReferenceMap<V> m) {
		this(m.size());
		this.putAll(m);
	}

	public Long2ReferenceArrayMap(Map<? extends Long, ? extends V> m) {
		this(m.size());
		this.putAll(m);
	}

	public Long2ReferenceArrayMap(long[] key, Object[] value, int size) {
		this.key = key;
		this.value = value;
		this.size = size;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		} else if (size > key.length) {
			throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
		}
	}

	public FastEntrySet<V> long2ReferenceEntrySet() {
		return new Long2ReferenceArrayMap.EntrySet();
	}

	private int findKey(long k) {
		long[] key = this.key;
		int i = this.size;

		while (i-- != 0) {
			if (key[i] == k) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public V get(long k) {
		long[] key = this.key;
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
	public boolean containsKey(long k) {
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
	public V put(long k, V v) {
		int oldKey = this.findKey(k);
		if (oldKey != -1) {
			V oldValue = (V)this.value[oldKey];
			this.value[oldKey] = v;
			return oldValue;
		} else {
			if (this.size == this.key.length) {
				long[] newKey = new long[this.size == 0 ? 2 : this.size * 2];
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
	public V remove(long k) {
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
	public LongSet keySet() {
		return new AbstractLongSet() {
			@Override
			public boolean contains(long k) {
				return Long2ReferenceArrayMap.this.findKey(k) != -1;
			}

			@Override
			public boolean remove(long k) {
				int oldPos = Long2ReferenceArrayMap.this.findKey(k);
				if (oldPos == -1) {
					return false;
				} else {
					int tail = Long2ReferenceArrayMap.this.size - oldPos - 1;
					System.arraycopy(Long2ReferenceArrayMap.this.key, oldPos + 1, Long2ReferenceArrayMap.this.key, oldPos, tail);
					System.arraycopy(Long2ReferenceArrayMap.this.value, oldPos + 1, Long2ReferenceArrayMap.this.value, oldPos, tail);
					Long2ReferenceArrayMap.this.size--;
					return true;
				}
			}

			@Override
			public LongIterator iterator() {
				return new LongIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Long2ReferenceArrayMap.this.size;
					}

					@Override
					public long nextLong() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Long2ReferenceArrayMap.this.key[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Long2ReferenceArrayMap.this.size - this.pos;
							System.arraycopy(Long2ReferenceArrayMap.this.key, this.pos, Long2ReferenceArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Long2ReferenceArrayMap.this.value, this.pos, Long2ReferenceArrayMap.this.value, this.pos - 1, tail);
							Long2ReferenceArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Long2ReferenceArrayMap.this.size;
			}

			public void clear() {
				Long2ReferenceArrayMap.this.clear();
			}
		};
	}

	@Override
	public ReferenceCollection<V> values() {
		return new AbstractReferenceCollection<V>() {
			public boolean contains(Object v) {
				return Long2ReferenceArrayMap.this.containsValue(v);
			}

			@Override
			public ObjectIterator<V> iterator() {
				return new ObjectIterator<V>() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Long2ReferenceArrayMap.this.size;
					}

					public V next() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return (V)Long2ReferenceArrayMap.this.value[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Long2ReferenceArrayMap.this.size - this.pos;
							System.arraycopy(Long2ReferenceArrayMap.this.key, this.pos, Long2ReferenceArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Long2ReferenceArrayMap.this.value, this.pos, Long2ReferenceArrayMap.this.value, this.pos - 1, tail);
							Long2ReferenceArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Long2ReferenceArrayMap.this.size;
			}

			public void clear() {
				Long2ReferenceArrayMap.this.clear();
			}
		};
	}

	public Long2ReferenceArrayMap<V> clone() {
		Long2ReferenceArrayMap<V> c;
		try {
			c = (Long2ReferenceArrayMap<V>)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (long[])this.key.clone();
		c.value = (Object[])this.value.clone();
		return c;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; i < this.size; i++) {
			s.writeLong(this.key[i]);
			s.writeObject(this.value[i]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.key = new long[this.size];
		this.value = new Object[this.size];

		for (int i = 0; i < this.size; i++) {
			this.key[i] = s.readLong();
			this.value[i] = s.readObject();
		}
	}

	private final class EntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.longs.Long2ReferenceMap.Entry<V>> implements FastEntrySet<V> {
		private EntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.longs.Long2ReferenceMap.Entry<V>> iterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.longs.Long2ReferenceMap.Entry<V>>() {
				int curr = -1;
				int next = 0;

				public boolean hasNext() {
					return this.next < Long2ReferenceArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.longs.Long2ReferenceMap.Entry<V> next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return new BasicEntry<>(Long2ReferenceArrayMap.this.key[this.curr = this.next], (V)Long2ReferenceArrayMap.this.value[this.next++]);
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Long2ReferenceArrayMap.this.size-- - this.next--;
						System.arraycopy(Long2ReferenceArrayMap.this.key, this.next + 1, Long2ReferenceArrayMap.this.key, this.next, tail);
						System.arraycopy(Long2ReferenceArrayMap.this.value, this.next + 1, Long2ReferenceArrayMap.this.value, this.next, tail);
						Long2ReferenceArrayMap.this.value[Long2ReferenceArrayMap.this.size] = null;
					}
				}
			};
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.longs.Long2ReferenceMap.Entry<V>> fastIterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.longs.Long2ReferenceMap.Entry<V>>() {
				int next = 0;
				int curr = -1;
				final BasicEntry<V> entry = new BasicEntry<>();

				public boolean hasNext() {
					return this.next < Long2ReferenceArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.longs.Long2ReferenceMap.Entry<V> next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						this.entry.key = Long2ReferenceArrayMap.this.key[this.curr = this.next];
						this.entry.value = (V)Long2ReferenceArrayMap.this.value[this.next++];
						return this.entry;
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Long2ReferenceArrayMap.this.size-- - this.next--;
						System.arraycopy(Long2ReferenceArrayMap.this.key, this.next + 1, Long2ReferenceArrayMap.this.key, this.next, tail);
						System.arraycopy(Long2ReferenceArrayMap.this.value, this.next + 1, Long2ReferenceArrayMap.this.value, this.next, tail);
						Long2ReferenceArrayMap.this.value[Long2ReferenceArrayMap.this.size] = null;
					}
				}
			};
		}

		public int size() {
			return Long2ReferenceArrayMap.this.size;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() != null && e.getKey() instanceof Long) {
					long k = (Long)e.getKey();
					return Long2ReferenceArrayMap.this.containsKey(k) && Long2ReferenceArrayMap.this.get(k) == e.getValue();
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
				if (e.getKey() != null && e.getKey() instanceof Long) {
					long k = (Long)e.getKey();
					V v = (V)e.getValue();
					int oldPos = Long2ReferenceArrayMap.this.findKey(k);
					if (oldPos != -1 && v == Long2ReferenceArrayMap.this.value[oldPos]) {
						int tail = Long2ReferenceArrayMap.this.size - oldPos - 1;
						System.arraycopy(Long2ReferenceArrayMap.this.key, oldPos + 1, Long2ReferenceArrayMap.this.key, oldPos, tail);
						System.arraycopy(Long2ReferenceArrayMap.this.value, oldPos + 1, Long2ReferenceArrayMap.this.value, oldPos, tail);
						Long2ReferenceArrayMap.this.size--;
						Long2ReferenceArrayMap.this.value[Long2ReferenceArrayMap.this.size] = null;
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
