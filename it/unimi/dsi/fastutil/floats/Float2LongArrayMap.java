package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloat2LongMap.BasicEntry;
import it.unimi.dsi.fastutil.floats.Float2LongMap.FastEntrySet;
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

public class Float2LongArrayMap extends AbstractFloat2LongMap implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private transient float[] key;
	private transient long[] value;
	private int size;

	public Float2LongArrayMap(float[] key, long[] value) {
		this.key = key;
		this.value = value;
		this.size = key.length;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		}
	}

	public Float2LongArrayMap() {
		this.key = FloatArrays.EMPTY_ARRAY;
		this.value = LongArrays.EMPTY_ARRAY;
	}

	public Float2LongArrayMap(int capacity) {
		this.key = new float[capacity];
		this.value = new long[capacity];
	}

	public Float2LongArrayMap(Float2LongMap m) {
		this(m.size());
		this.putAll(m);
	}

	public Float2LongArrayMap(Map<? extends Float, ? extends Long> m) {
		this(m.size());
		this.putAll(m);
	}

	public Float2LongArrayMap(float[] key, long[] value, int size) {
		this.key = key;
		this.value = value;
		this.size = size;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		} else if (size > key.length) {
			throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
		}
	}

	public FastEntrySet float2LongEntrySet() {
		return new Float2LongArrayMap.EntrySet();
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
	public long get(float k) {
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
	public long put(float k, long v) {
		int oldKey = this.findKey(k);
		if (oldKey != -1) {
			long oldValue = this.value[oldKey];
			this.value[oldKey] = v;
			return oldValue;
		} else {
			if (this.size == this.key.length) {
				float[] newKey = new float[this.size == 0 ? 2 : this.size * 2];
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
	public long remove(float k) {
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
	public FloatSet keySet() {
		return new AbstractFloatSet() {
			@Override
			public boolean contains(float k) {
				return Float2LongArrayMap.this.findKey(k) != -1;
			}

			@Override
			public boolean remove(float k) {
				int oldPos = Float2LongArrayMap.this.findKey(k);
				if (oldPos == -1) {
					return false;
				} else {
					int tail = Float2LongArrayMap.this.size - oldPos - 1;
					System.arraycopy(Float2LongArrayMap.this.key, oldPos + 1, Float2LongArrayMap.this.key, oldPos, tail);
					System.arraycopy(Float2LongArrayMap.this.value, oldPos + 1, Float2LongArrayMap.this.value, oldPos, tail);
					Float2LongArrayMap.this.size--;
					return true;
				}
			}

			@Override
			public FloatIterator iterator() {
				return new FloatIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Float2LongArrayMap.this.size;
					}

					@Override
					public float nextFloat() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Float2LongArrayMap.this.key[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Float2LongArrayMap.this.size - this.pos;
							System.arraycopy(Float2LongArrayMap.this.key, this.pos, Float2LongArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Float2LongArrayMap.this.value, this.pos, Float2LongArrayMap.this.value, this.pos - 1, tail);
							Float2LongArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Float2LongArrayMap.this.size;
			}

			public void clear() {
				Float2LongArrayMap.this.clear();
			}
		};
	}

	@Override
	public LongCollection values() {
		return new AbstractLongCollection() {
			@Override
			public boolean contains(long v) {
				return Float2LongArrayMap.this.containsValue(v);
			}

			@Override
			public LongIterator iterator() {
				return new LongIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Float2LongArrayMap.this.size;
					}

					@Override
					public long nextLong() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Float2LongArrayMap.this.value[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Float2LongArrayMap.this.size - this.pos;
							System.arraycopy(Float2LongArrayMap.this.key, this.pos, Float2LongArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Float2LongArrayMap.this.value, this.pos, Float2LongArrayMap.this.value, this.pos - 1, tail);
							Float2LongArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Float2LongArrayMap.this.size;
			}

			public void clear() {
				Float2LongArrayMap.this.clear();
			}
		};
	}

	public Float2LongArrayMap clone() {
		Float2LongArrayMap c;
		try {
			c = (Float2LongArrayMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (float[])this.key.clone();
		c.value = (long[])this.value.clone();
		return c;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; i < this.size; i++) {
			s.writeFloat(this.key[i]);
			s.writeLong(this.value[i]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.key = new float[this.size];
		this.value = new long[this.size];

		for (int i = 0; i < this.size; i++) {
			this.key[i] = s.readFloat();
			this.value[i] = s.readLong();
		}
	}

	private final class EntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.floats.Float2LongMap.Entry> implements FastEntrySet {
		private EntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.floats.Float2LongMap.Entry> iterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.floats.Float2LongMap.Entry>() {
				int curr = -1;
				int next = 0;

				public boolean hasNext() {
					return this.next < Float2LongArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.floats.Float2LongMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return new BasicEntry(Float2LongArrayMap.this.key[this.curr = this.next], Float2LongArrayMap.this.value[this.next++]);
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Float2LongArrayMap.this.size-- - this.next--;
						System.arraycopy(Float2LongArrayMap.this.key, this.next + 1, Float2LongArrayMap.this.key, this.next, tail);
						System.arraycopy(Float2LongArrayMap.this.value, this.next + 1, Float2LongArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.floats.Float2LongMap.Entry> fastIterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.floats.Float2LongMap.Entry>() {
				int next = 0;
				int curr = -1;
				final BasicEntry entry = new BasicEntry();

				public boolean hasNext() {
					return this.next < Float2LongArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.floats.Float2LongMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						this.entry.key = Float2LongArrayMap.this.key[this.curr = this.next];
						this.entry.value = Float2LongArrayMap.this.value[this.next++];
						return this.entry;
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Float2LongArrayMap.this.size-- - this.next--;
						System.arraycopy(Float2LongArrayMap.this.key, this.next + 1, Float2LongArrayMap.this.key, this.next, tail);
						System.arraycopy(Float2LongArrayMap.this.value, this.next + 1, Float2LongArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		public int size() {
			return Float2LongArrayMap.this.size;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Float)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Long) {
					float k = (Float)e.getKey();
					return Float2LongArrayMap.this.containsKey(k) && Float2LongArrayMap.this.get(k) == (Long)e.getValue();
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
				} else if (e.getValue() != null && e.getValue() instanceof Long) {
					float k = (Float)e.getKey();
					long v = (Long)e.getValue();
					int oldPos = Float2LongArrayMap.this.findKey(k);
					if (oldPos != -1 && v == Float2LongArrayMap.this.value[oldPos]) {
						int tail = Float2LongArrayMap.this.size - oldPos - 1;
						System.arraycopy(Float2LongArrayMap.this.key, oldPos + 1, Float2LongArrayMap.this.key, oldPos, tail);
						System.arraycopy(Float2LongArrayMap.this.value, oldPos + 1, Float2LongArrayMap.this.value, oldPos, tail);
						Float2LongArrayMap.this.size--;
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
