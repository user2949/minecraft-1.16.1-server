package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShort2FloatMap.BasicEntry;
import it.unimi.dsi.fastutil.shorts.Short2FloatMap.FastEntrySet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

public class Short2FloatArrayMap extends AbstractShort2FloatMap implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private transient short[] key;
	private transient float[] value;
	private int size;

	public Short2FloatArrayMap(short[] key, float[] value) {
		this.key = key;
		this.value = value;
		this.size = key.length;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		}
	}

	public Short2FloatArrayMap() {
		this.key = ShortArrays.EMPTY_ARRAY;
		this.value = FloatArrays.EMPTY_ARRAY;
	}

	public Short2FloatArrayMap(int capacity) {
		this.key = new short[capacity];
		this.value = new float[capacity];
	}

	public Short2FloatArrayMap(Short2FloatMap m) {
		this(m.size());
		this.putAll(m);
	}

	public Short2FloatArrayMap(Map<? extends Short, ? extends Float> m) {
		this(m.size());
		this.putAll(m);
	}

	public Short2FloatArrayMap(short[] key, float[] value, int size) {
		this.key = key;
		this.value = value;
		this.size = size;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		} else if (size > key.length) {
			throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
		}
	}

	public FastEntrySet short2FloatEntrySet() {
		return new Short2FloatArrayMap.EntrySet();
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
	public float get(short k) {
		short[] key = this.key;
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
	public boolean containsKey(short k) {
		return this.findKey(k) != -1;
	}

	@Override
	public boolean containsValue(float v) {
		int i = this.size;

		while (i-- != 0) {
			if (Float.floatToIntBits(this.value[i]) == Float.floatToIntBits(v)) {
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
	public float put(short k, float v) {
		int oldKey = this.findKey(k);
		if (oldKey != -1) {
			float oldValue = this.value[oldKey];
			this.value[oldKey] = v;
			return oldValue;
		} else {
			if (this.size == this.key.length) {
				short[] newKey = new short[this.size == 0 ? 2 : this.size * 2];
				float[] newValue = new float[this.size == 0 ? 2 : this.size * 2];

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
	public float remove(short k) {
		int oldPos = this.findKey(k);
		if (oldPos == -1) {
			return this.defRetValue;
		} else {
			float oldValue = this.value[oldPos];
			int tail = this.size - oldPos - 1;
			System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
			System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
			this.size--;
			return oldValue;
		}
	}

	@Override
	public ShortSet keySet() {
		return new AbstractShortSet() {
			@Override
			public boolean contains(short k) {
				return Short2FloatArrayMap.this.findKey(k) != -1;
			}

			@Override
			public boolean remove(short k) {
				int oldPos = Short2FloatArrayMap.this.findKey(k);
				if (oldPos == -1) {
					return false;
				} else {
					int tail = Short2FloatArrayMap.this.size - oldPos - 1;
					System.arraycopy(Short2FloatArrayMap.this.key, oldPos + 1, Short2FloatArrayMap.this.key, oldPos, tail);
					System.arraycopy(Short2FloatArrayMap.this.value, oldPos + 1, Short2FloatArrayMap.this.value, oldPos, tail);
					Short2FloatArrayMap.this.size--;
					return true;
				}
			}

			@Override
			public ShortIterator iterator() {
				return new ShortIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Short2FloatArrayMap.this.size;
					}

					@Override
					public short nextShort() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Short2FloatArrayMap.this.key[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Short2FloatArrayMap.this.size - this.pos;
							System.arraycopy(Short2FloatArrayMap.this.key, this.pos, Short2FloatArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Short2FloatArrayMap.this.value, this.pos, Short2FloatArrayMap.this.value, this.pos - 1, tail);
							Short2FloatArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Short2FloatArrayMap.this.size;
			}

			public void clear() {
				Short2FloatArrayMap.this.clear();
			}
		};
	}

	@Override
	public FloatCollection values() {
		return new AbstractFloatCollection() {
			@Override
			public boolean contains(float v) {
				return Short2FloatArrayMap.this.containsValue(v);
			}

			@Override
			public FloatIterator iterator() {
				return new FloatIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Short2FloatArrayMap.this.size;
					}

					@Override
					public float nextFloat() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Short2FloatArrayMap.this.value[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Short2FloatArrayMap.this.size - this.pos;
							System.arraycopy(Short2FloatArrayMap.this.key, this.pos, Short2FloatArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Short2FloatArrayMap.this.value, this.pos, Short2FloatArrayMap.this.value, this.pos - 1, tail);
							Short2FloatArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Short2FloatArrayMap.this.size;
			}

			public void clear() {
				Short2FloatArrayMap.this.clear();
			}
		};
	}

	public Short2FloatArrayMap clone() {
		Short2FloatArrayMap c;
		try {
			c = (Short2FloatArrayMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (short[])this.key.clone();
		c.value = (float[])this.value.clone();
		return c;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; i < this.size; i++) {
			s.writeShort(this.key[i]);
			s.writeFloat(this.value[i]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.key = new short[this.size];
		this.value = new float[this.size];

		for (int i = 0; i < this.size; i++) {
			this.key[i] = s.readShort();
			this.value[i] = s.readFloat();
		}
	}

	private final class EntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry> implements FastEntrySet {
		private EntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry> iterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry>() {
				int curr = -1;
				int next = 0;

				public boolean hasNext() {
					return this.next < Short2FloatArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return new BasicEntry(Short2FloatArrayMap.this.key[this.curr = this.next], Short2FloatArrayMap.this.value[this.next++]);
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Short2FloatArrayMap.this.size-- - this.next--;
						System.arraycopy(Short2FloatArrayMap.this.key, this.next + 1, Short2FloatArrayMap.this.key, this.next, tail);
						System.arraycopy(Short2FloatArrayMap.this.value, this.next + 1, Short2FloatArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry> fastIterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry>() {
				int next = 0;
				int curr = -1;
				final BasicEntry entry = new BasicEntry();

				public boolean hasNext() {
					return this.next < Short2FloatArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						this.entry.key = Short2FloatArrayMap.this.key[this.curr = this.next];
						this.entry.value = Short2FloatArrayMap.this.value[this.next++];
						return this.entry;
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Short2FloatArrayMap.this.size-- - this.next--;
						System.arraycopy(Short2FloatArrayMap.this.key, this.next + 1, Short2FloatArrayMap.this.key, this.next, tail);
						System.arraycopy(Short2FloatArrayMap.this.value, this.next + 1, Short2FloatArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		public int size() {
			return Short2FloatArrayMap.this.size;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Short)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Float) {
					short k = (Short)e.getKey();
					return Short2FloatArrayMap.this.containsKey(k) && Float.floatToIntBits(Short2FloatArrayMap.this.get(k)) == Float.floatToIntBits((Float)e.getValue());
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
				if (e.getKey() == null || !(e.getKey() instanceof Short)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Float) {
					short k = (Short)e.getKey();
					float v = (Float)e.getValue();
					int oldPos = Short2FloatArrayMap.this.findKey(k);
					if (oldPos != -1 && Float.floatToIntBits(v) == Float.floatToIntBits(Short2FloatArrayMap.this.value[oldPos])) {
						int tail = Short2FloatArrayMap.this.size - oldPos - 1;
						System.arraycopy(Short2FloatArrayMap.this.key, oldPos + 1, Short2FloatArrayMap.this.key, oldPos, tail);
						System.arraycopy(Short2FloatArrayMap.this.value, oldPos + 1, Short2FloatArrayMap.this.value, oldPos, tail);
						Short2FloatArrayMap.this.size--;
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
