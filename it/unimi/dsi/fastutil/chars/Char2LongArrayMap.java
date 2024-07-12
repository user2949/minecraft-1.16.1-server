package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2LongMap.BasicEntry;
import it.unimi.dsi.fastutil.chars.Char2LongMap.FastEntrySet;
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

public class Char2LongArrayMap extends AbstractChar2LongMap implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private transient char[] key;
	private transient long[] value;
	private int size;

	public Char2LongArrayMap(char[] key, long[] value) {
		this.key = key;
		this.value = value;
		this.size = key.length;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		}
	}

	public Char2LongArrayMap() {
		this.key = CharArrays.EMPTY_ARRAY;
		this.value = LongArrays.EMPTY_ARRAY;
	}

	public Char2LongArrayMap(int capacity) {
		this.key = new char[capacity];
		this.value = new long[capacity];
	}

	public Char2LongArrayMap(Char2LongMap m) {
		this(m.size());
		this.putAll(m);
	}

	public Char2LongArrayMap(Map<? extends Character, ? extends Long> m) {
		this(m.size());
		this.putAll(m);
	}

	public Char2LongArrayMap(char[] key, long[] value, int size) {
		this.key = key;
		this.value = value;
		this.size = size;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		} else if (size > key.length) {
			throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
		}
	}

	public FastEntrySet char2LongEntrySet() {
		return new Char2LongArrayMap.EntrySet();
	}

	private int findKey(char k) {
		char[] key = this.key;
		int i = this.size;

		while (i-- != 0) {
			if (key[i] == k) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public long get(char k) {
		char[] key = this.key;
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
	public boolean containsKey(char k) {
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
	public long put(char k, long v) {
		int oldKey = this.findKey(k);
		if (oldKey != -1) {
			long oldValue = this.value[oldKey];
			this.value[oldKey] = v;
			return oldValue;
		} else {
			if (this.size == this.key.length) {
				char[] newKey = new char[this.size == 0 ? 2 : this.size * 2];
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
	public long remove(char k) {
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
	public CharSet keySet() {
		return new AbstractCharSet() {
			@Override
			public boolean contains(char k) {
				return Char2LongArrayMap.this.findKey(k) != -1;
			}

			@Override
			public boolean remove(char k) {
				int oldPos = Char2LongArrayMap.this.findKey(k);
				if (oldPos == -1) {
					return false;
				} else {
					int tail = Char2LongArrayMap.this.size - oldPos - 1;
					System.arraycopy(Char2LongArrayMap.this.key, oldPos + 1, Char2LongArrayMap.this.key, oldPos, tail);
					System.arraycopy(Char2LongArrayMap.this.value, oldPos + 1, Char2LongArrayMap.this.value, oldPos, tail);
					Char2LongArrayMap.this.size--;
					return true;
				}
			}

			@Override
			public CharIterator iterator() {
				return new CharIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Char2LongArrayMap.this.size;
					}

					@Override
					public char nextChar() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Char2LongArrayMap.this.key[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Char2LongArrayMap.this.size - this.pos;
							System.arraycopy(Char2LongArrayMap.this.key, this.pos, Char2LongArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Char2LongArrayMap.this.value, this.pos, Char2LongArrayMap.this.value, this.pos - 1, tail);
							Char2LongArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Char2LongArrayMap.this.size;
			}

			public void clear() {
				Char2LongArrayMap.this.clear();
			}
		};
	}

	@Override
	public LongCollection values() {
		return new AbstractLongCollection() {
			@Override
			public boolean contains(long v) {
				return Char2LongArrayMap.this.containsValue(v);
			}

			@Override
			public LongIterator iterator() {
				return new LongIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Char2LongArrayMap.this.size;
					}

					@Override
					public long nextLong() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Char2LongArrayMap.this.value[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Char2LongArrayMap.this.size - this.pos;
							System.arraycopy(Char2LongArrayMap.this.key, this.pos, Char2LongArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Char2LongArrayMap.this.value, this.pos, Char2LongArrayMap.this.value, this.pos - 1, tail);
							Char2LongArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Char2LongArrayMap.this.size;
			}

			public void clear() {
				Char2LongArrayMap.this.clear();
			}
		};
	}

	public Char2LongArrayMap clone() {
		Char2LongArrayMap c;
		try {
			c = (Char2LongArrayMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (char[])this.key.clone();
		c.value = (long[])this.value.clone();
		return c;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; i < this.size; i++) {
			s.writeChar(this.key[i]);
			s.writeLong(this.value[i]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.key = new char[this.size];
		this.value = new long[this.size];

		for (int i = 0; i < this.size; i++) {
			this.key[i] = s.readChar();
			this.value[i] = s.readLong();
		}
	}

	private final class EntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.chars.Char2LongMap.Entry> implements FastEntrySet {
		private EntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.chars.Char2LongMap.Entry> iterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.chars.Char2LongMap.Entry>() {
				int curr = -1;
				int next = 0;

				public boolean hasNext() {
					return this.next < Char2LongArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.chars.Char2LongMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return new BasicEntry(Char2LongArrayMap.this.key[this.curr = this.next], Char2LongArrayMap.this.value[this.next++]);
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Char2LongArrayMap.this.size-- - this.next--;
						System.arraycopy(Char2LongArrayMap.this.key, this.next + 1, Char2LongArrayMap.this.key, this.next, tail);
						System.arraycopy(Char2LongArrayMap.this.value, this.next + 1, Char2LongArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.chars.Char2LongMap.Entry> fastIterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.chars.Char2LongMap.Entry>() {
				int next = 0;
				int curr = -1;
				final BasicEntry entry = new BasicEntry();

				public boolean hasNext() {
					return this.next < Char2LongArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.chars.Char2LongMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						this.entry.key = Char2LongArrayMap.this.key[this.curr = this.next];
						this.entry.value = Char2LongArrayMap.this.value[this.next++];
						return this.entry;
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Char2LongArrayMap.this.size-- - this.next--;
						System.arraycopy(Char2LongArrayMap.this.key, this.next + 1, Char2LongArrayMap.this.key, this.next, tail);
						System.arraycopy(Char2LongArrayMap.this.value, this.next + 1, Char2LongArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		public int size() {
			return Char2LongArrayMap.this.size;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Character)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Long) {
					char k = (Character)e.getKey();
					return Char2LongArrayMap.this.containsKey(k) && Char2LongArrayMap.this.get(k) == (Long)e.getValue();
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
				if (e.getKey() == null || !(e.getKey() instanceof Character)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Long) {
					char k = (Character)e.getKey();
					long v = (Long)e.getValue();
					int oldPos = Char2LongArrayMap.this.findKey(k);
					if (oldPos != -1 && v == Char2LongArrayMap.this.value[oldPos]) {
						int tail = Char2LongArrayMap.this.size - oldPos - 1;
						System.arraycopy(Char2LongArrayMap.this.key, oldPos + 1, Char2LongArrayMap.this.key, oldPos, tail);
						System.arraycopy(Char2LongArrayMap.this.value, oldPos + 1, Char2LongArrayMap.this.value, oldPos, tail);
						Char2LongArrayMap.this.size--;
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
