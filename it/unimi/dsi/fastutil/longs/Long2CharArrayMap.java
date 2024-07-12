package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.longs.AbstractLong2CharMap.BasicEntry;
import it.unimi.dsi.fastutil.longs.Long2CharMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

public class Long2CharArrayMap extends AbstractLong2CharMap implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private transient long[] key;
	private transient char[] value;
	private int size;

	public Long2CharArrayMap(long[] key, char[] value) {
		this.key = key;
		this.value = value;
		this.size = key.length;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		}
	}

	public Long2CharArrayMap() {
		this.key = LongArrays.EMPTY_ARRAY;
		this.value = CharArrays.EMPTY_ARRAY;
	}

	public Long2CharArrayMap(int capacity) {
		this.key = new long[capacity];
		this.value = new char[capacity];
	}

	public Long2CharArrayMap(Long2CharMap m) {
		this(m.size());
		this.putAll(m);
	}

	public Long2CharArrayMap(Map<? extends Long, ? extends Character> m) {
		this(m.size());
		this.putAll(m);
	}

	public Long2CharArrayMap(long[] key, char[] value, int size) {
		this.key = key;
		this.value = value;
		this.size = size;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		} else if (size > key.length) {
			throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
		}
	}

	public FastEntrySet long2CharEntrySet() {
		return new Long2CharArrayMap.EntrySet();
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
	public char get(long k) {
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
	public boolean containsValue(char v) {
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
	public char put(long k, char v) {
		int oldKey = this.findKey(k);
		if (oldKey != -1) {
			char oldValue = this.value[oldKey];
			this.value[oldKey] = v;
			return oldValue;
		} else {
			if (this.size == this.key.length) {
				long[] newKey = new long[this.size == 0 ? 2 : this.size * 2];
				char[] newValue = new char[this.size == 0 ? 2 : this.size * 2];

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
	public char remove(long k) {
		int oldPos = this.findKey(k);
		if (oldPos == -1) {
			return this.defRetValue;
		} else {
			char oldValue = this.value[oldPos];
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
				return Long2CharArrayMap.this.findKey(k) != -1;
			}

			@Override
			public boolean remove(long k) {
				int oldPos = Long2CharArrayMap.this.findKey(k);
				if (oldPos == -1) {
					return false;
				} else {
					int tail = Long2CharArrayMap.this.size - oldPos - 1;
					System.arraycopy(Long2CharArrayMap.this.key, oldPos + 1, Long2CharArrayMap.this.key, oldPos, tail);
					System.arraycopy(Long2CharArrayMap.this.value, oldPos + 1, Long2CharArrayMap.this.value, oldPos, tail);
					Long2CharArrayMap.this.size--;
					return true;
				}
			}

			@Override
			public LongIterator iterator() {
				return new LongIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Long2CharArrayMap.this.size;
					}

					@Override
					public long nextLong() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Long2CharArrayMap.this.key[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Long2CharArrayMap.this.size - this.pos;
							System.arraycopy(Long2CharArrayMap.this.key, this.pos, Long2CharArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Long2CharArrayMap.this.value, this.pos, Long2CharArrayMap.this.value, this.pos - 1, tail);
							Long2CharArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Long2CharArrayMap.this.size;
			}

			public void clear() {
				Long2CharArrayMap.this.clear();
			}
		};
	}

	@Override
	public CharCollection values() {
		return new AbstractCharCollection() {
			@Override
			public boolean contains(char v) {
				return Long2CharArrayMap.this.containsValue(v);
			}

			@Override
			public CharIterator iterator() {
				return new CharIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Long2CharArrayMap.this.size;
					}

					@Override
					public char nextChar() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Long2CharArrayMap.this.value[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Long2CharArrayMap.this.size - this.pos;
							System.arraycopy(Long2CharArrayMap.this.key, this.pos, Long2CharArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Long2CharArrayMap.this.value, this.pos, Long2CharArrayMap.this.value, this.pos - 1, tail);
							Long2CharArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Long2CharArrayMap.this.size;
			}

			public void clear() {
				Long2CharArrayMap.this.clear();
			}
		};
	}

	public Long2CharArrayMap clone() {
		Long2CharArrayMap c;
		try {
			c = (Long2CharArrayMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (long[])this.key.clone();
		c.value = (char[])this.value.clone();
		return c;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; i < this.size; i++) {
			s.writeLong(this.key[i]);
			s.writeChar(this.value[i]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.key = new long[this.size];
		this.value = new char[this.size];

		for (int i = 0; i < this.size; i++) {
			this.key[i] = s.readLong();
			this.value[i] = s.readChar();
		}
	}

	private final class EntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.longs.Long2CharMap.Entry> implements FastEntrySet {
		private EntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.longs.Long2CharMap.Entry> iterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.longs.Long2CharMap.Entry>() {
				int curr = -1;
				int next = 0;

				public boolean hasNext() {
					return this.next < Long2CharArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.longs.Long2CharMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return new BasicEntry(Long2CharArrayMap.this.key[this.curr = this.next], Long2CharArrayMap.this.value[this.next++]);
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Long2CharArrayMap.this.size-- - this.next--;
						System.arraycopy(Long2CharArrayMap.this.key, this.next + 1, Long2CharArrayMap.this.key, this.next, tail);
						System.arraycopy(Long2CharArrayMap.this.value, this.next + 1, Long2CharArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.longs.Long2CharMap.Entry> fastIterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.longs.Long2CharMap.Entry>() {
				int next = 0;
				int curr = -1;
				final BasicEntry entry = new BasicEntry();

				public boolean hasNext() {
					return this.next < Long2CharArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.longs.Long2CharMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						this.entry.key = Long2CharArrayMap.this.key[this.curr = this.next];
						this.entry.value = Long2CharArrayMap.this.value[this.next++];
						return this.entry;
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Long2CharArrayMap.this.size-- - this.next--;
						System.arraycopy(Long2CharArrayMap.this.key, this.next + 1, Long2CharArrayMap.this.key, this.next, tail);
						System.arraycopy(Long2CharArrayMap.this.value, this.next + 1, Long2CharArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		public int size() {
			return Long2CharArrayMap.this.size;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Long)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Character) {
					long k = (Long)e.getKey();
					return Long2CharArrayMap.this.containsKey(k) && Long2CharArrayMap.this.get(k) == (Character)e.getValue();
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
				} else if (e.getValue() != null && e.getValue() instanceof Character) {
					long k = (Long)e.getKey();
					char v = (Character)e.getValue();
					int oldPos = Long2CharArrayMap.this.findKey(k);
					if (oldPos != -1 && v == Long2CharArrayMap.this.value[oldPos]) {
						int tail = Long2CharArrayMap.this.size - oldPos - 1;
						System.arraycopy(Long2CharArrayMap.this.key, oldPos + 1, Long2CharArrayMap.this.key, oldPos, tail);
						System.arraycopy(Long2CharArrayMap.this.value, oldPos + 1, Long2CharArrayMap.this.value, oldPos, tail);
						Long2CharArrayMap.this.size--;
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
