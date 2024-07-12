package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2ByteMap.BasicEntry;
import it.unimi.dsi.fastutil.bytes.Byte2ByteMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

public class Byte2ByteArrayMap extends AbstractByte2ByteMap implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private transient byte[] key;
	private transient byte[] value;
	private int size;

	public Byte2ByteArrayMap(byte[] key, byte[] value) {
		this.key = key;
		this.value = value;
		this.size = key.length;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		}
	}

	public Byte2ByteArrayMap() {
		this.key = ByteArrays.EMPTY_ARRAY;
		this.value = ByteArrays.EMPTY_ARRAY;
	}

	public Byte2ByteArrayMap(int capacity) {
		this.key = new byte[capacity];
		this.value = new byte[capacity];
	}

	public Byte2ByteArrayMap(Byte2ByteMap m) {
		this(m.size());
		this.putAll(m);
	}

	public Byte2ByteArrayMap(Map<? extends Byte, ? extends Byte> m) {
		this(m.size());
		this.putAll(m);
	}

	public Byte2ByteArrayMap(byte[] key, byte[] value, int size) {
		this.key = key;
		this.value = value;
		this.size = size;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		} else if (size > key.length) {
			throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
		}
	}

	public FastEntrySet byte2ByteEntrySet() {
		return new Byte2ByteArrayMap.EntrySet();
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
	public byte get(byte k) {
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
	public byte put(byte k, byte v) {
		int oldKey = this.findKey(k);
		if (oldKey != -1) {
			byte oldValue = this.value[oldKey];
			this.value[oldKey] = v;
			return oldValue;
		} else {
			if (this.size == this.key.length) {
				byte[] newKey = new byte[this.size == 0 ? 2 : this.size * 2];
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
	public byte remove(byte k) {
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
	public ByteSet keySet() {
		return new AbstractByteSet() {
			@Override
			public boolean contains(byte k) {
				return Byte2ByteArrayMap.this.findKey(k) != -1;
			}

			@Override
			public boolean remove(byte k) {
				int oldPos = Byte2ByteArrayMap.this.findKey(k);
				if (oldPos == -1) {
					return false;
				} else {
					int tail = Byte2ByteArrayMap.this.size - oldPos - 1;
					System.arraycopy(Byte2ByteArrayMap.this.key, oldPos + 1, Byte2ByteArrayMap.this.key, oldPos, tail);
					System.arraycopy(Byte2ByteArrayMap.this.value, oldPos + 1, Byte2ByteArrayMap.this.value, oldPos, tail);
					Byte2ByteArrayMap.this.size--;
					return true;
				}
			}

			@Override
			public ByteIterator iterator() {
				return new ByteIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Byte2ByteArrayMap.this.size;
					}

					@Override
					public byte nextByte() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Byte2ByteArrayMap.this.key[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Byte2ByteArrayMap.this.size - this.pos;
							System.arraycopy(Byte2ByteArrayMap.this.key, this.pos, Byte2ByteArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Byte2ByteArrayMap.this.value, this.pos, Byte2ByteArrayMap.this.value, this.pos - 1, tail);
							Byte2ByteArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Byte2ByteArrayMap.this.size;
			}

			public void clear() {
				Byte2ByteArrayMap.this.clear();
			}
		};
	}

	@Override
	public ByteCollection values() {
		return new AbstractByteCollection() {
			@Override
			public boolean contains(byte v) {
				return Byte2ByteArrayMap.this.containsValue(v);
			}

			@Override
			public ByteIterator iterator() {
				return new ByteIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Byte2ByteArrayMap.this.size;
					}

					@Override
					public byte nextByte() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Byte2ByteArrayMap.this.value[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Byte2ByteArrayMap.this.size - this.pos;
							System.arraycopy(Byte2ByteArrayMap.this.key, this.pos, Byte2ByteArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Byte2ByteArrayMap.this.value, this.pos, Byte2ByteArrayMap.this.value, this.pos - 1, tail);
							Byte2ByteArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Byte2ByteArrayMap.this.size;
			}

			public void clear() {
				Byte2ByteArrayMap.this.clear();
			}
		};
	}

	public Byte2ByteArrayMap clone() {
		Byte2ByteArrayMap c;
		try {
			c = (Byte2ByteArrayMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (byte[])this.key.clone();
		c.value = (byte[])this.value.clone();
		return c;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; i < this.size; i++) {
			s.writeByte(this.key[i]);
			s.writeByte(this.value[i]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.key = new byte[this.size];
		this.value = new byte[this.size];

		for (int i = 0; i < this.size; i++) {
			this.key[i] = s.readByte();
			this.value[i] = s.readByte();
		}
	}

	private final class EntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.bytes.Byte2ByteMap.Entry> implements FastEntrySet {
		private EntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.bytes.Byte2ByteMap.Entry> iterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.bytes.Byte2ByteMap.Entry>() {
				int curr = -1;
				int next = 0;

				public boolean hasNext() {
					return this.next < Byte2ByteArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.bytes.Byte2ByteMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return new BasicEntry(Byte2ByteArrayMap.this.key[this.curr = this.next], Byte2ByteArrayMap.this.value[this.next++]);
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Byte2ByteArrayMap.this.size-- - this.next--;
						System.arraycopy(Byte2ByteArrayMap.this.key, this.next + 1, Byte2ByteArrayMap.this.key, this.next, tail);
						System.arraycopy(Byte2ByteArrayMap.this.value, this.next + 1, Byte2ByteArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.bytes.Byte2ByteMap.Entry> fastIterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.bytes.Byte2ByteMap.Entry>() {
				int next = 0;
				int curr = -1;
				final BasicEntry entry = new BasicEntry();

				public boolean hasNext() {
					return this.next < Byte2ByteArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.bytes.Byte2ByteMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						this.entry.key = Byte2ByteArrayMap.this.key[this.curr = this.next];
						this.entry.value = Byte2ByteArrayMap.this.value[this.next++];
						return this.entry;
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Byte2ByteArrayMap.this.size-- - this.next--;
						System.arraycopy(Byte2ByteArrayMap.this.key, this.next + 1, Byte2ByteArrayMap.this.key, this.next, tail);
						System.arraycopy(Byte2ByteArrayMap.this.value, this.next + 1, Byte2ByteArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		public int size() {
			return Byte2ByteArrayMap.this.size;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Byte)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Byte) {
					byte k = (Byte)e.getKey();
					return Byte2ByteArrayMap.this.containsKey(k) && Byte2ByteArrayMap.this.get(k) == (Byte)e.getValue();
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
				} else if (e.getValue() != null && e.getValue() instanceof Byte) {
					byte k = (Byte)e.getKey();
					byte v = (Byte)e.getValue();
					int oldPos = Byte2ByteArrayMap.this.findKey(k);
					if (oldPos != -1 && v == Byte2ByteArrayMap.this.value[oldPos]) {
						int tail = Byte2ByteArrayMap.this.size - oldPos - 1;
						System.arraycopy(Byte2ByteArrayMap.this.key, oldPos + 1, Byte2ByteArrayMap.this.key, oldPos, tail);
						System.arraycopy(Byte2ByteArrayMap.this.value, oldPos + 1, Byte2ByteArrayMap.this.value, oldPos, tail);
						Byte2ByteArrayMap.this.size--;
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
