package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2LongMap.BasicEntry;
import it.unimi.dsi.fastutil.bytes.Byte2LongMap.FastEntrySet;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

public class Byte2LongArrayMap extends AbstractByte2LongMap implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private transient byte[] key;
	private transient long[] value;
	private int size;

	public Byte2LongArrayMap(byte[] key, long[] value) {
		this.key = key;
		this.value = value;
		this.size = key.length;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		}
	}

	public Byte2LongArrayMap() {
		this.key = ByteArrays.EMPTY_ARRAY;
		this.value = LongArrays.EMPTY_ARRAY;
	}

	public Byte2LongArrayMap(int capacity) {
		this.key = new byte[capacity];
		this.value = new long[capacity];
	}

	public Byte2LongArrayMap(Byte2LongMap m) {
		this(m.size());
		this.putAll(m);
	}

	public Byte2LongArrayMap(Map<? extends Byte, ? extends Long> m) {
		this(m.size());
		this.putAll(m);
	}

	public Byte2LongArrayMap(byte[] key, long[] value, int size) {
		this.key = key;
		this.value = value;
		this.size = size;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		} else if (size > key.length) {
			throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
		}
	}

	public FastEntrySet byte2LongEntrySet() {
		return new Byte2LongArrayMap.EntrySet();
	}

	private int findKey(byte k) {
		byte[] key = this.key;
		int i = this.size;

		while (i-- != 0) {
			if (key[i] == k) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public long get(byte k) {
		byte[] key = this.key;
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
	public boolean containsKey(byte k) {
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
	public long put(byte k, long v) {
		int oldKey = this.findKey(k);
		if (oldKey != -1) {
			long oldValue = this.value[oldKey];
			this.value[oldKey] = v;
			return oldValue;
		} else {
			if (this.size == this.key.length) {
				byte[] newKey = new byte[this.size == 0 ? 2 : this.size * 2];
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
	public long remove(byte k) {
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
	public ByteSet keySet() {
		return new AbstractByteSet() {
			@Override
			public boolean contains(byte k) {
				return Byte2LongArrayMap.this.findKey(k) != -1;
			}

			@Override
			public boolean remove(byte k) {
				int oldPos = Byte2LongArrayMap.this.findKey(k);
				if (oldPos == -1) {
					return false;
				} else {
					int tail = Byte2LongArrayMap.this.size - oldPos - 1;
					System.arraycopy(Byte2LongArrayMap.this.key, oldPos + 1, Byte2LongArrayMap.this.key, oldPos, tail);
					System.arraycopy(Byte2LongArrayMap.this.value, oldPos + 1, Byte2LongArrayMap.this.value, oldPos, tail);
					Byte2LongArrayMap.this.size--;
					return true;
				}
			}

			@Override
			public ByteIterator iterator() {
				return new ByteIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Byte2LongArrayMap.this.size;
					}

					@Override
					public byte nextByte() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Byte2LongArrayMap.this.key[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Byte2LongArrayMap.this.size - this.pos;
							System.arraycopy(Byte2LongArrayMap.this.key, this.pos, Byte2LongArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Byte2LongArrayMap.this.value, this.pos, Byte2LongArrayMap.this.value, this.pos - 1, tail);
							Byte2LongArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Byte2LongArrayMap.this.size;
			}

			public void clear() {
				Byte2LongArrayMap.this.clear();
			}
		};
	}

	@Override
	public LongCollection values() {
		return new AbstractLongCollection() {
			@Override
			public boolean contains(long v) {
				return Byte2LongArrayMap.this.containsValue(v);
			}

			@Override
			public LongIterator iterator() {
				return new LongIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Byte2LongArrayMap.this.size;
					}

					@Override
					public long nextLong() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Byte2LongArrayMap.this.value[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Byte2LongArrayMap.this.size - this.pos;
							System.arraycopy(Byte2LongArrayMap.this.key, this.pos, Byte2LongArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Byte2LongArrayMap.this.value, this.pos, Byte2LongArrayMap.this.value, this.pos - 1, tail);
							Byte2LongArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Byte2LongArrayMap.this.size;
			}

			public void clear() {
				Byte2LongArrayMap.this.clear();
			}
		};
	}

	public Byte2LongArrayMap clone() {
		Byte2LongArrayMap c;
		try {
			c = (Byte2LongArrayMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (byte[])this.key.clone();
		c.value = (long[])this.value.clone();
		return c;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; i < this.size; i++) {
			s.writeByte(this.key[i]);
			s.writeLong(this.value[i]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.key = new byte[this.size];
		this.value = new long[this.size];

		for (int i = 0; i < this.size; i++) {
			this.key[i] = s.readByte();
			this.value[i] = s.readLong();
		}
	}

	private final class EntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.bytes.Byte2LongMap.Entry> implements FastEntrySet {
		private EntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.bytes.Byte2LongMap.Entry> iterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.bytes.Byte2LongMap.Entry>() {
				int curr = -1;
				int next = 0;

				public boolean hasNext() {
					return this.next < Byte2LongArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.bytes.Byte2LongMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return new BasicEntry(Byte2LongArrayMap.this.key[this.curr = this.next], Byte2LongArrayMap.this.value[this.next++]);
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Byte2LongArrayMap.this.size-- - this.next--;
						System.arraycopy(Byte2LongArrayMap.this.key, this.next + 1, Byte2LongArrayMap.this.key, this.next, tail);
						System.arraycopy(Byte2LongArrayMap.this.value, this.next + 1, Byte2LongArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.bytes.Byte2LongMap.Entry> fastIterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.bytes.Byte2LongMap.Entry>() {
				int next = 0;
				int curr = -1;
				final BasicEntry entry = new BasicEntry();

				public boolean hasNext() {
					return this.next < Byte2LongArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.bytes.Byte2LongMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						this.entry.key = Byte2LongArrayMap.this.key[this.curr = this.next];
						this.entry.value = Byte2LongArrayMap.this.value[this.next++];
						return this.entry;
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Byte2LongArrayMap.this.size-- - this.next--;
						System.arraycopy(Byte2LongArrayMap.this.key, this.next + 1, Byte2LongArrayMap.this.key, this.next, tail);
						System.arraycopy(Byte2LongArrayMap.this.value, this.next + 1, Byte2LongArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		public int size() {
			return Byte2LongArrayMap.this.size;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Byte)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Long) {
					byte k = (Byte)e.getKey();
					return Byte2LongArrayMap.this.containsKey(k) && Byte2LongArrayMap.this.get(k) == (Long)e.getValue();
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
				if (e.getKey() == null || !(e.getKey() instanceof Byte)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Long) {
					byte k = (Byte)e.getKey();
					long v = (Long)e.getValue();
					int oldPos = Byte2LongArrayMap.this.findKey(k);
					if (oldPos != -1 && v == Byte2LongArrayMap.this.value[oldPos]) {
						int tail = Byte2LongArrayMap.this.size - oldPos - 1;
						System.arraycopy(Byte2LongArrayMap.this.key, oldPos + 1, Byte2LongArrayMap.this.key, oldPos, tail);
						System.arraycopy(Byte2LongArrayMap.this.value, oldPos + 1, Byte2LongArrayMap.this.value, oldPos, tail);
						Byte2LongArrayMap.this.size--;
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
