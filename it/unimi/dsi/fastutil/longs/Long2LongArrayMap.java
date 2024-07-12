package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.AbstractLong2LongMap.BasicEntry;
import it.unimi.dsi.fastutil.longs.Long2LongMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

public class Long2LongArrayMap extends AbstractLong2LongMap implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private transient long[] key;
	private transient long[] value;
	private int size;

	public Long2LongArrayMap(long[] key, long[] value) {
		this.key = key;
		this.value = value;
		this.size = key.length;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		}
	}

	public Long2LongArrayMap() {
		this.key = LongArrays.EMPTY_ARRAY;
		this.value = LongArrays.EMPTY_ARRAY;
	}

	public Long2LongArrayMap(int capacity) {
		this.key = new long[capacity];
		this.value = new long[capacity];
	}

	public Long2LongArrayMap(Long2LongMap m) {
		this(m.size());
		this.putAll(m);
	}

	public Long2LongArrayMap(Map<? extends Long, ? extends Long> m) {
		this(m.size());
		this.putAll(m);
	}

	public Long2LongArrayMap(long[] key, long[] value, int size) {
		this.key = key;
		this.value = value;
		this.size = size;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		} else if (size > key.length) {
			throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
		}
	}

	public FastEntrySet long2LongEntrySet() {
		return new Long2LongArrayMap.EntrySet();
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
	public long get(long k) {
		long[] key = this.key;
		int i = this.size;

		while (i-- != 0) {
			if (key[i] == k) {
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
		this.size = 0;
	}

	@Override
	public boolean containsKey(long k) {
		return this.findKey(k) != -1;
	}

	@Override
	public boolean containsValue(long v) {
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
	public long put(long k, long v) {
		int oldKey = this.findKey(k);
		if (oldKey != -1) {
			long oldValue = this.value[oldKey];
			this.value[oldKey] = v;
			return oldValue;
		} else {
			if (this.size == this.key.length) {
				long[] newKey = new long[this.size == 0 ? 2 : this.size * 2];
				long[] newValue = new long[this.size == 0 ? 2 : this.size * 2];

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
	public long remove(long k) {
		int oldPos = this.findKey(k);
		if (oldPos == -1) {
			return this.defRetValue;
		} else {
			long oldValue = this.value[oldPos];
			int tail = this.size - oldPos - 1;
			System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
			System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
			this.size--;
			return oldValue;
		}
	}

	@Override
	public LongSet keySet() {
		return new AbstractLongSet() {
			@Override
			public boolean contains(long k) {
				return Long2LongArrayMap.this.findKey(k) != -1;
			}

			@Override
			public boolean remove(long k) {
				int oldPos = Long2LongArrayMap.this.findKey(k);
				if (oldPos == -1) {
					return false;
				} else {
					int tail = Long2LongArrayMap.this.size - oldPos - 1;
					System.arraycopy(Long2LongArrayMap.this.key, oldPos + 1, Long2LongArrayMap.this.key, oldPos, tail);
					System.arraycopy(Long2LongArrayMap.this.value, oldPos + 1, Long2LongArrayMap.this.value, oldPos, tail);
					Long2LongArrayMap.this.size--;
					return true;
				}
			}

			@Override
			public LongIterator iterator() {
				return new LongIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Long2LongArrayMap.this.size;
					}

					@Override
					public long nextLong() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Long2LongArrayMap.this.key[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Long2LongArrayMap.this.size - this.pos;
							System.arraycopy(Long2LongArrayMap.this.key, this.pos, Long2LongArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Long2LongArrayMap.this.value, this.pos, Long2LongArrayMap.this.value, this.pos - 1, tail);
							Long2LongArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Long2LongArrayMap.this.size;
			}

			public void clear() {
				Long2LongArrayMap.this.clear();
			}
		};
	}

	@Override
	public LongCollection values() {
		return new AbstractLongCollection() {
			@Override
			public boolean contains(long v) {
				return Long2LongArrayMap.this.containsValue(v);
			}

			@Override
			public LongIterator iterator() {
				return new LongIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Long2LongArrayMap.this.size;
					}

					@Override
					public long nextLong() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Long2LongArrayMap.this.value[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Long2LongArrayMap.this.size - this.pos;
							System.arraycopy(Long2LongArrayMap.this.key, this.pos, Long2LongArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Long2LongArrayMap.this.value, this.pos, Long2LongArrayMap.this.value, this.pos - 1, tail);
							Long2LongArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Long2LongArrayMap.this.size;
			}

			public void clear() {
				Long2LongArrayMap.this.clear();
			}
		};
	}

	public Long2LongArrayMap clone() {
		Long2LongArrayMap c;
		try {
			c = (Long2LongArrayMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (long[])this.key.clone();
		c.value = (long[])this.value.clone();
		return c;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; i < this.size; i++) {
			s.writeLong(this.key[i]);
			s.writeLong(this.value[i]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.key = new long[this.size];
		this.value = new long[this.size];

		for (int i = 0; i < this.size; i++) {
			this.key[i] = s.readLong();
			this.value[i] = s.readLong();
		}
	}

	private final class EntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.longs.Long2LongMap.Entry> implements FastEntrySet {
		private EntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.longs.Long2LongMap.Entry> iterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.longs.Long2LongMap.Entry>() {
				int curr = -1;
				int next = 0;

				public boolean hasNext() {
					return this.next < Long2LongArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.longs.Long2LongMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return new BasicEntry(Long2LongArrayMap.this.key[this.curr = this.next], Long2LongArrayMap.this.value[this.next++]);
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Long2LongArrayMap.this.size-- - this.next--;
						System.arraycopy(Long2LongArrayMap.this.key, this.next + 1, Long2LongArrayMap.this.key, this.next, tail);
						System.arraycopy(Long2LongArrayMap.this.value, this.next + 1, Long2LongArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.longs.Long2LongMap.Entry> fastIterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.longs.Long2LongMap.Entry>() {
				int next = 0;
				int curr = -1;
				final BasicEntry entry = new BasicEntry();

				public boolean hasNext() {
					return this.next < Long2LongArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.longs.Long2LongMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						this.entry.key = Long2LongArrayMap.this.key[this.curr = this.next];
						this.entry.value = Long2LongArrayMap.this.value[this.next++];
						return this.entry;
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Long2LongArrayMap.this.size-- - this.next--;
						System.arraycopy(Long2LongArrayMap.this.key, this.next + 1, Long2LongArrayMap.this.key, this.next, tail);
						System.arraycopy(Long2LongArrayMap.this.value, this.next + 1, Long2LongArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		public int size() {
			return Long2LongArrayMap.this.size;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Long)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Long) {
					long k = (Long)e.getKey();
					return Long2LongArrayMap.this.containsKey(k) && Long2LongArrayMap.this.get(k) == (Long)e.getValue();
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
				if (e.getKey() == null || !(e.getKey() instanceof Long)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Long) {
					long k = (Long)e.getKey();
					long v = (Long)e.getValue();
					int oldPos = Long2LongArrayMap.this.findKey(k);
					if (oldPos != -1 && v == Long2LongArrayMap.this.value[oldPos]) {
						int tail = Long2LongArrayMap.this.size - oldPos - 1;
						System.arraycopy(Long2LongArrayMap.this.key, oldPos + 1, Long2LongArrayMap.this.key, oldPos, tail);
						System.arraycopy(Long2LongArrayMap.this.value, oldPos + 1, Long2LongArrayMap.this.value, oldPos, tail);
						Long2LongArrayMap.this.size--;
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
