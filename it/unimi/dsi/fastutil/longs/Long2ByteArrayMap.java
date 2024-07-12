package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.longs.AbstractLong2ByteMap.BasicEntry;
import it.unimi.dsi.fastutil.longs.Long2ByteMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

public class Long2ByteArrayMap extends AbstractLong2ByteMap implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private transient long[] key;
	private transient byte[] value;
	private int size;

	public Long2ByteArrayMap(long[] key, byte[] value) {
		this.key = key;
		this.value = value;
		this.size = key.length;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		}
	}

	public Long2ByteArrayMap() {
		this.key = LongArrays.EMPTY_ARRAY;
		this.value = ByteArrays.EMPTY_ARRAY;
	}

	public Long2ByteArrayMap(int capacity) {
		this.key = new long[capacity];
		this.value = new byte[capacity];
	}

	public Long2ByteArrayMap(Long2ByteMap m) {
		this(m.size());
		this.putAll(m);
	}

	public Long2ByteArrayMap(Map<? extends Long, ? extends Byte> m) {
		this(m.size());
		this.putAll(m);
	}

	public Long2ByteArrayMap(long[] key, byte[] value, int size) {
		this.key = key;
		this.value = value;
		this.size = size;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		} else if (size > key.length) {
			throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
		}
	}

	public FastEntrySet long2ByteEntrySet() {
		return new Long2ByteArrayMap.EntrySet();
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
	public byte get(long k) {
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
	public boolean containsValue(byte v) {
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
	public byte put(long k, byte v) {
		int oldKey = this.findKey(k);
		if (oldKey != -1) {
			byte oldValue = this.value[oldKey];
			this.value[oldKey] = v;
			return oldValue;
		} else {
			if (this.size == this.key.length) {
				long[] newKey = new long[this.size == 0 ? 2 : this.size * 2];
				byte[] newValue = new byte[this.size == 0 ? 2 : this.size * 2];

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
	public byte remove(long k) {
		int oldPos = this.findKey(k);
		if (oldPos == -1) {
			return this.defRetValue;
		} else {
			byte oldValue = this.value[oldPos];
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
				return Long2ByteArrayMap.this.findKey(k) != -1;
			}

			@Override
			public boolean remove(long k) {
				int oldPos = Long2ByteArrayMap.this.findKey(k);
				if (oldPos == -1) {
					return false;
				} else {
					int tail = Long2ByteArrayMap.this.size - oldPos - 1;
					System.arraycopy(Long2ByteArrayMap.this.key, oldPos + 1, Long2ByteArrayMap.this.key, oldPos, tail);
					System.arraycopy(Long2ByteArrayMap.this.value, oldPos + 1, Long2ByteArrayMap.this.value, oldPos, tail);
					Long2ByteArrayMap.this.size--;
					return true;
				}
			}

			@Override
			public LongIterator iterator() {
				return new LongIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Long2ByteArrayMap.this.size;
					}

					@Override
					public long nextLong() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Long2ByteArrayMap.this.key[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Long2ByteArrayMap.this.size - this.pos;
							System.arraycopy(Long2ByteArrayMap.this.key, this.pos, Long2ByteArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Long2ByteArrayMap.this.value, this.pos, Long2ByteArrayMap.this.value, this.pos - 1, tail);
							Long2ByteArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Long2ByteArrayMap.this.size;
			}

			public void clear() {
				Long2ByteArrayMap.this.clear();
			}
		};
	}

	@Override
	public ByteCollection values() {
		return new AbstractByteCollection() {
			@Override
			public boolean contains(byte v) {
				return Long2ByteArrayMap.this.containsValue(v);
			}

			@Override
			public ByteIterator iterator() {
				return new ByteIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Long2ByteArrayMap.this.size;
					}

					@Override
					public byte nextByte() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Long2ByteArrayMap.this.value[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Long2ByteArrayMap.this.size - this.pos;
							System.arraycopy(Long2ByteArrayMap.this.key, this.pos, Long2ByteArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Long2ByteArrayMap.this.value, this.pos, Long2ByteArrayMap.this.value, this.pos - 1, tail);
							Long2ByteArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Long2ByteArrayMap.this.size;
			}

			public void clear() {
				Long2ByteArrayMap.this.clear();
			}
		};
	}

	public Long2ByteArrayMap clone() {
		Long2ByteArrayMap c;
		try {
			c = (Long2ByteArrayMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (long[])this.key.clone();
		c.value = (byte[])this.value.clone();
		return c;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; i < this.size; i++) {
			s.writeLong(this.key[i]);
			s.writeByte(this.value[i]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.key = new long[this.size];
		this.value = new byte[this.size];

		for (int i = 0; i < this.size; i++) {
			this.key[i] = s.readLong();
			this.value[i] = s.readByte();
		}
	}

	private final class EntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry> implements FastEntrySet {
		private EntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry> iterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry>() {
				int curr = -1;
				int next = 0;

				public boolean hasNext() {
					return this.next < Long2ByteArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return new BasicEntry(Long2ByteArrayMap.this.key[this.curr = this.next], Long2ByteArrayMap.this.value[this.next++]);
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Long2ByteArrayMap.this.size-- - this.next--;
						System.arraycopy(Long2ByteArrayMap.this.key, this.next + 1, Long2ByteArrayMap.this.key, this.next, tail);
						System.arraycopy(Long2ByteArrayMap.this.value, this.next + 1, Long2ByteArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry> fastIterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry>() {
				int next = 0;
				int curr = -1;
				final BasicEntry entry = new BasicEntry();

				public boolean hasNext() {
					return this.next < Long2ByteArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						this.entry.key = Long2ByteArrayMap.this.key[this.curr = this.next];
						this.entry.value = Long2ByteArrayMap.this.value[this.next++];
						return this.entry;
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Long2ByteArrayMap.this.size-- - this.next--;
						System.arraycopy(Long2ByteArrayMap.this.key, this.next + 1, Long2ByteArrayMap.this.key, this.next, tail);
						System.arraycopy(Long2ByteArrayMap.this.value, this.next + 1, Long2ByteArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		public int size() {
			return Long2ByteArrayMap.this.size;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Long)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Byte) {
					long k = (Long)e.getKey();
					return Long2ByteArrayMap.this.containsKey(k) && Long2ByteArrayMap.this.get(k) == (Byte)e.getValue();
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
				} else if (e.getValue() != null && e.getValue() instanceof Byte) {
					long k = (Long)e.getKey();
					byte v = (Byte)e.getValue();
					int oldPos = Long2ByteArrayMap.this.findKey(k);
					if (oldPos != -1 && v == Long2ByteArrayMap.this.value[oldPos]) {
						int tail = Long2ByteArrayMap.this.size - oldPos - 1;
						System.arraycopy(Long2ByteArrayMap.this.key, oldPos + 1, Long2ByteArrayMap.this.key, oldPos, tail);
						System.arraycopy(Long2ByteArrayMap.this.value, oldPos + 1, Long2ByteArrayMap.this.value, oldPos, tail);
						Long2ByteArrayMap.this.size--;
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
