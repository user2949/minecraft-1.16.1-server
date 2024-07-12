package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2IntMap.BasicEntry;
import it.unimi.dsi.fastutil.bytes.Byte2IntMap.FastEntrySet;
import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

public class Byte2IntArrayMap extends AbstractByte2IntMap implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private transient byte[] key;
	private transient int[] value;
	private int size;

	public Byte2IntArrayMap(byte[] key, int[] value) {
		this.key = key;
		this.value = value;
		this.size = key.length;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		}
	}

	public Byte2IntArrayMap() {
		this.key = ByteArrays.EMPTY_ARRAY;
		this.value = IntArrays.EMPTY_ARRAY;
	}

	public Byte2IntArrayMap(int capacity) {
		this.key = new byte[capacity];
		this.value = new int[capacity];
	}

	public Byte2IntArrayMap(Byte2IntMap m) {
		this(m.size());
		this.putAll(m);
	}

	public Byte2IntArrayMap(Map<? extends Byte, ? extends Integer> m) {
		this(m.size());
		this.putAll(m);
	}

	public Byte2IntArrayMap(byte[] key, int[] value, int size) {
		this.key = key;
		this.value = value;
		this.size = size;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		} else if (size > key.length) {
			throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
		}
	}

	public FastEntrySet byte2IntEntrySet() {
		return new Byte2IntArrayMap.EntrySet();
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
	public int get(byte k) {
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
	public int put(byte k, int v) {
		int oldKey = this.findKey(k);
		if (oldKey != -1) {
			int oldValue = this.value[oldKey];
			this.value[oldKey] = v;
			return oldValue;
		} else {
			if (this.size == this.key.length) {
				byte[] newKey = new byte[this.size == 0 ? 2 : this.size * 2];
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
	public int remove(byte k) {
		int oldPos = this.findKey(k);
		if (oldPos == -1) {
			return this.defRetValue;
		} else {
			int oldValue = this.value[oldPos];
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
				return Byte2IntArrayMap.this.findKey(k) != -1;
			}

			@Override
			public boolean remove(byte k) {
				int oldPos = Byte2IntArrayMap.this.findKey(k);
				if (oldPos == -1) {
					return false;
				} else {
					int tail = Byte2IntArrayMap.this.size - oldPos - 1;
					System.arraycopy(Byte2IntArrayMap.this.key, oldPos + 1, Byte2IntArrayMap.this.key, oldPos, tail);
					System.arraycopy(Byte2IntArrayMap.this.value, oldPos + 1, Byte2IntArrayMap.this.value, oldPos, tail);
					Byte2IntArrayMap.this.size--;
					return true;
				}
			}

			@Override
			public ByteIterator iterator() {
				return new ByteIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Byte2IntArrayMap.this.size;
					}

					@Override
					public byte nextByte() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Byte2IntArrayMap.this.key[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Byte2IntArrayMap.this.size - this.pos;
							System.arraycopy(Byte2IntArrayMap.this.key, this.pos, Byte2IntArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Byte2IntArrayMap.this.value, this.pos, Byte2IntArrayMap.this.value, this.pos - 1, tail);
							Byte2IntArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Byte2IntArrayMap.this.size;
			}

			public void clear() {
				Byte2IntArrayMap.this.clear();
			}
		};
	}

	@Override
	public IntCollection values() {
		return new AbstractIntCollection() {
			@Override
			public boolean contains(int v) {
				return Byte2IntArrayMap.this.containsValue(v);
			}

			@Override
			public IntIterator iterator() {
				return new IntIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Byte2IntArrayMap.this.size;
					}

					@Override
					public int nextInt() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Byte2IntArrayMap.this.value[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Byte2IntArrayMap.this.size - this.pos;
							System.arraycopy(Byte2IntArrayMap.this.key, this.pos, Byte2IntArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Byte2IntArrayMap.this.value, this.pos, Byte2IntArrayMap.this.value, this.pos - 1, tail);
							Byte2IntArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Byte2IntArrayMap.this.size;
			}

			public void clear() {
				Byte2IntArrayMap.this.clear();
			}
		};
	}

	public Byte2IntArrayMap clone() {
		Byte2IntArrayMap c;
		try {
			c = (Byte2IntArrayMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (byte[])this.key.clone();
		c.value = (int[])this.value.clone();
		return c;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; i < this.size; i++) {
			s.writeByte(this.key[i]);
			s.writeInt(this.value[i]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.key = new byte[this.size];
		this.value = new int[this.size];

		for (int i = 0; i < this.size; i++) {
			this.key[i] = s.readByte();
			this.value[i] = s.readInt();
		}
	}

	private final class EntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.bytes.Byte2IntMap.Entry> implements FastEntrySet {
		private EntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.bytes.Byte2IntMap.Entry> iterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.bytes.Byte2IntMap.Entry>() {
				int curr = -1;
				int next = 0;

				public boolean hasNext() {
					return this.next < Byte2IntArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.bytes.Byte2IntMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return new BasicEntry(Byte2IntArrayMap.this.key[this.curr = this.next], Byte2IntArrayMap.this.value[this.next++]);
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Byte2IntArrayMap.this.size-- - this.next--;
						System.arraycopy(Byte2IntArrayMap.this.key, this.next + 1, Byte2IntArrayMap.this.key, this.next, tail);
						System.arraycopy(Byte2IntArrayMap.this.value, this.next + 1, Byte2IntArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.bytes.Byte2IntMap.Entry> fastIterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.bytes.Byte2IntMap.Entry>() {
				int next = 0;
				int curr = -1;
				final BasicEntry entry = new BasicEntry();

				public boolean hasNext() {
					return this.next < Byte2IntArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.bytes.Byte2IntMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						this.entry.key = Byte2IntArrayMap.this.key[this.curr = this.next];
						this.entry.value = Byte2IntArrayMap.this.value[this.next++];
						return this.entry;
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Byte2IntArrayMap.this.size-- - this.next--;
						System.arraycopy(Byte2IntArrayMap.this.key, this.next + 1, Byte2IntArrayMap.this.key, this.next, tail);
						System.arraycopy(Byte2IntArrayMap.this.value, this.next + 1, Byte2IntArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		public int size() {
			return Byte2IntArrayMap.this.size;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Byte)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Integer) {
					byte k = (Byte)e.getKey();
					return Byte2IntArrayMap.this.containsKey(k) && Byte2IntArrayMap.this.get(k) == (Integer)e.getValue();
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
				} else if (e.getValue() != null && e.getValue() instanceof Integer) {
					byte k = (Byte)e.getKey();
					int v = (Integer)e.getValue();
					int oldPos = Byte2IntArrayMap.this.findKey(k);
					if (oldPos != -1 && v == Byte2IntArrayMap.this.value[oldPos]) {
						int tail = Byte2IntArrayMap.this.size - oldPos - 1;
						System.arraycopy(Byte2IntArrayMap.this.key, oldPos + 1, Byte2IntArrayMap.this.key, oldPos, tail);
						System.arraycopy(Byte2IntArrayMap.this.value, oldPos + 1, Byte2IntArrayMap.this.value, oldPos, tail);
						Byte2IntArrayMap.this.size--;
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
