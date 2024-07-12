package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloat2CharMap.BasicEntry;
import it.unimi.dsi.fastutil.floats.Float2CharMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

public class Float2CharArrayMap extends AbstractFloat2CharMap implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private transient float[] key;
	private transient char[] value;
	private int size;

	public Float2CharArrayMap(float[] key, char[] value) {
		this.key = key;
		this.value = value;
		this.size = key.length;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		}
	}

	public Float2CharArrayMap() {
		this.key = FloatArrays.EMPTY_ARRAY;
		this.value = CharArrays.EMPTY_ARRAY;
	}

	public Float2CharArrayMap(int capacity) {
		this.key = new float[capacity];
		this.value = new char[capacity];
	}

	public Float2CharArrayMap(Float2CharMap m) {
		this(m.size());
		this.putAll(m);
	}

	public Float2CharArrayMap(Map<? extends Float, ? extends Character> m) {
		this(m.size());
		this.putAll(m);
	}

	public Float2CharArrayMap(float[] key, char[] value, int size) {
		this.key = key;
		this.value = value;
		this.size = size;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		} else if (size > key.length) {
			throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
		}
	}

	public FastEntrySet float2CharEntrySet() {
		return new Float2CharArrayMap.EntrySet();
	}

	private int findKey(float k) {
		float[] key = this.key;
		int i = this.size;

		while (i-- != 0) {
			if (Float.floatToIntBits(key[i]) == Float.floatToIntBits(k)) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public char get(float k) {
		float[] key = this.key;
		int i = this.size;

		while (i-- != 0) {
			if (Float.floatToIntBits(key[i]) == Float.floatToIntBits(k)) {
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
	public boolean containsKey(float k) {
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
	public char put(float k, char v) {
		int oldKey = this.findKey(k);
		if (oldKey != -1) {
			char oldValue = this.value[oldKey];
			this.value[oldKey] = v;
			return oldValue;
		} else {
			if (this.size == this.key.length) {
				float[] newKey = new float[this.size == 0 ? 2 : this.size * 2];
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
	public char remove(float k) {
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
	public FloatSet keySet() {
		return new AbstractFloatSet() {
			@Override
			public boolean contains(float k) {
				return Float2CharArrayMap.this.findKey(k) != -1;
			}

			@Override
			public boolean remove(float k) {
				int oldPos = Float2CharArrayMap.this.findKey(k);
				if (oldPos == -1) {
					return false;
				} else {
					int tail = Float2CharArrayMap.this.size - oldPos - 1;
					System.arraycopy(Float2CharArrayMap.this.key, oldPos + 1, Float2CharArrayMap.this.key, oldPos, tail);
					System.arraycopy(Float2CharArrayMap.this.value, oldPos + 1, Float2CharArrayMap.this.value, oldPos, tail);
					Float2CharArrayMap.this.size--;
					return true;
				}
			}

			@Override
			public FloatIterator iterator() {
				return new FloatIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Float2CharArrayMap.this.size;
					}

					@Override
					public float nextFloat() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Float2CharArrayMap.this.key[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Float2CharArrayMap.this.size - this.pos;
							System.arraycopy(Float2CharArrayMap.this.key, this.pos, Float2CharArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Float2CharArrayMap.this.value, this.pos, Float2CharArrayMap.this.value, this.pos - 1, tail);
							Float2CharArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Float2CharArrayMap.this.size;
			}

			public void clear() {
				Float2CharArrayMap.this.clear();
			}
		};
	}

	@Override
	public CharCollection values() {
		return new AbstractCharCollection() {
			@Override
			public boolean contains(char v) {
				return Float2CharArrayMap.this.containsValue(v);
			}

			@Override
			public CharIterator iterator() {
				return new CharIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Float2CharArrayMap.this.size;
					}

					@Override
					public char nextChar() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Float2CharArrayMap.this.value[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Float2CharArrayMap.this.size - this.pos;
							System.arraycopy(Float2CharArrayMap.this.key, this.pos, Float2CharArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Float2CharArrayMap.this.value, this.pos, Float2CharArrayMap.this.value, this.pos - 1, tail);
							Float2CharArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Float2CharArrayMap.this.size;
			}

			public void clear() {
				Float2CharArrayMap.this.clear();
			}
		};
	}

	public Float2CharArrayMap clone() {
		Float2CharArrayMap c;
		try {
			c = (Float2CharArrayMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (float[])this.key.clone();
		c.value = (char[])this.value.clone();
		return c;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; i < this.size; i++) {
			s.writeFloat(this.key[i]);
			s.writeChar(this.value[i]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.key = new float[this.size];
		this.value = new char[this.size];

		for (int i = 0; i < this.size; i++) {
			this.key[i] = s.readFloat();
			this.value[i] = s.readChar();
		}
	}

	private final class EntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.floats.Float2CharMap.Entry> implements FastEntrySet {
		private EntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.floats.Float2CharMap.Entry> iterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.floats.Float2CharMap.Entry>() {
				int curr = -1;
				int next = 0;

				public boolean hasNext() {
					return this.next < Float2CharArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.floats.Float2CharMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return new BasicEntry(Float2CharArrayMap.this.key[this.curr = this.next], Float2CharArrayMap.this.value[this.next++]);
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Float2CharArrayMap.this.size-- - this.next--;
						System.arraycopy(Float2CharArrayMap.this.key, this.next + 1, Float2CharArrayMap.this.key, this.next, tail);
						System.arraycopy(Float2CharArrayMap.this.value, this.next + 1, Float2CharArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.floats.Float2CharMap.Entry> fastIterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.floats.Float2CharMap.Entry>() {
				int next = 0;
				int curr = -1;
				final BasicEntry entry = new BasicEntry();

				public boolean hasNext() {
					return this.next < Float2CharArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.floats.Float2CharMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						this.entry.key = Float2CharArrayMap.this.key[this.curr = this.next];
						this.entry.value = Float2CharArrayMap.this.value[this.next++];
						return this.entry;
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Float2CharArrayMap.this.size-- - this.next--;
						System.arraycopy(Float2CharArrayMap.this.key, this.next + 1, Float2CharArrayMap.this.key, this.next, tail);
						System.arraycopy(Float2CharArrayMap.this.value, this.next + 1, Float2CharArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		public int size() {
			return Float2CharArrayMap.this.size;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Float)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Character) {
					float k = (Float)e.getKey();
					return Float2CharArrayMap.this.containsKey(k) && Float2CharArrayMap.this.get(k) == (Character)e.getValue();
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
				if (e.getKey() == null || !(e.getKey() instanceof Float)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Character) {
					float k = (Float)e.getKey();
					char v = (Character)e.getValue();
					int oldPos = Float2CharArrayMap.this.findKey(k);
					if (oldPos != -1 && v == Float2CharArrayMap.this.value[oldPos]) {
						int tail = Float2CharArrayMap.this.size - oldPos - 1;
						System.arraycopy(Float2CharArrayMap.this.key, oldPos + 1, Float2CharArrayMap.this.key, oldPos, tail);
						System.arraycopy(Float2CharArrayMap.this.value, oldPos + 1, Float2CharArrayMap.this.value, oldPos, tail);
						Float2CharArrayMap.this.size--;
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
