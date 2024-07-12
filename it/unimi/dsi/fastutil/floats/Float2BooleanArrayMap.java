package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanArrays;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloat2BooleanMap.BasicEntry;
import it.unimi.dsi.fastutil.floats.Float2BooleanMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

public class Float2BooleanArrayMap extends AbstractFloat2BooleanMap implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private transient float[] key;
	private transient boolean[] value;
	private int size;

	public Float2BooleanArrayMap(float[] key, boolean[] value) {
		this.key = key;
		this.value = value;
		this.size = key.length;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		}
	}

	public Float2BooleanArrayMap() {
		this.key = FloatArrays.EMPTY_ARRAY;
		this.value = BooleanArrays.EMPTY_ARRAY;
	}

	public Float2BooleanArrayMap(int capacity) {
		this.key = new float[capacity];
		this.value = new boolean[capacity];
	}

	public Float2BooleanArrayMap(Float2BooleanMap m) {
		this(m.size());
		this.putAll(m);
	}

	public Float2BooleanArrayMap(Map<? extends Float, ? extends Boolean> m) {
		this(m.size());
		this.putAll(m);
	}

	public Float2BooleanArrayMap(float[] key, boolean[] value, int size) {
		this.key = key;
		this.value = value;
		this.size = size;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		} else if (size > key.length) {
			throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
		}
	}

	public FastEntrySet float2BooleanEntrySet() {
		return new Float2BooleanArrayMap.EntrySet();
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
	public boolean get(float k) {
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
	public boolean containsValue(boolean v) {
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
	public boolean put(float k, boolean v) {
		int oldKey = this.findKey(k);
		if (oldKey != -1) {
			boolean oldValue = this.value[oldKey];
			this.value[oldKey] = v;
			return oldValue;
		} else {
			if (this.size == this.key.length) {
				float[] newKey = new float[this.size == 0 ? 2 : this.size * 2];
				boolean[] newValue = new boolean[this.size == 0 ? 2 : this.size * 2];

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
	public boolean remove(float k) {
		int oldPos = this.findKey(k);
		if (oldPos == -1) {
			return this.defRetValue;
		} else {
			boolean oldValue = this.value[oldPos];
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
				return Float2BooleanArrayMap.this.findKey(k) != -1;
			}

			@Override
			public boolean remove(float k) {
				int oldPos = Float2BooleanArrayMap.this.findKey(k);
				if (oldPos == -1) {
					return false;
				} else {
					int tail = Float2BooleanArrayMap.this.size - oldPos - 1;
					System.arraycopy(Float2BooleanArrayMap.this.key, oldPos + 1, Float2BooleanArrayMap.this.key, oldPos, tail);
					System.arraycopy(Float2BooleanArrayMap.this.value, oldPos + 1, Float2BooleanArrayMap.this.value, oldPos, tail);
					Float2BooleanArrayMap.this.size--;
					return true;
				}
			}

			@Override
			public FloatIterator iterator() {
				return new FloatIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Float2BooleanArrayMap.this.size;
					}

					@Override
					public float nextFloat() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Float2BooleanArrayMap.this.key[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Float2BooleanArrayMap.this.size - this.pos;
							System.arraycopy(Float2BooleanArrayMap.this.key, this.pos, Float2BooleanArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Float2BooleanArrayMap.this.value, this.pos, Float2BooleanArrayMap.this.value, this.pos - 1, tail);
							Float2BooleanArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Float2BooleanArrayMap.this.size;
			}

			public void clear() {
				Float2BooleanArrayMap.this.clear();
			}
		};
	}

	@Override
	public BooleanCollection values() {
		return new AbstractBooleanCollection() {
			@Override
			public boolean contains(boolean v) {
				return Float2BooleanArrayMap.this.containsValue(v);
			}

			@Override
			public BooleanIterator iterator() {
				return new BooleanIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Float2BooleanArrayMap.this.size;
					}

					@Override
					public boolean nextBoolean() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Float2BooleanArrayMap.this.value[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Float2BooleanArrayMap.this.size - this.pos;
							System.arraycopy(Float2BooleanArrayMap.this.key, this.pos, Float2BooleanArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Float2BooleanArrayMap.this.value, this.pos, Float2BooleanArrayMap.this.value, this.pos - 1, tail);
							Float2BooleanArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Float2BooleanArrayMap.this.size;
			}

			public void clear() {
				Float2BooleanArrayMap.this.clear();
			}
		};
	}

	public Float2BooleanArrayMap clone() {
		Float2BooleanArrayMap c;
		try {
			c = (Float2BooleanArrayMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (float[])this.key.clone();
		c.value = (boolean[])this.value.clone();
		return c;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; i < this.size; i++) {
			s.writeFloat(this.key[i]);
			s.writeBoolean(this.value[i]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.key = new float[this.size];
		this.value = new boolean[this.size];

		for (int i = 0; i < this.size; i++) {
			this.key[i] = s.readFloat();
			this.value[i] = s.readBoolean();
		}
	}

	private final class EntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry> implements FastEntrySet {
		private EntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry> iterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry>() {
				int curr = -1;
				int next = 0;

				public boolean hasNext() {
					return this.next < Float2BooleanArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return new BasicEntry(Float2BooleanArrayMap.this.key[this.curr = this.next], Float2BooleanArrayMap.this.value[this.next++]);
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Float2BooleanArrayMap.this.size-- - this.next--;
						System.arraycopy(Float2BooleanArrayMap.this.key, this.next + 1, Float2BooleanArrayMap.this.key, this.next, tail);
						System.arraycopy(Float2BooleanArrayMap.this.value, this.next + 1, Float2BooleanArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry> fastIterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry>() {
				int next = 0;
				int curr = -1;
				final BasicEntry entry = new BasicEntry();

				public boolean hasNext() {
					return this.next < Float2BooleanArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						this.entry.key = Float2BooleanArrayMap.this.key[this.curr = this.next];
						this.entry.value = Float2BooleanArrayMap.this.value[this.next++];
						return this.entry;
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Float2BooleanArrayMap.this.size-- - this.next--;
						System.arraycopy(Float2BooleanArrayMap.this.key, this.next + 1, Float2BooleanArrayMap.this.key, this.next, tail);
						System.arraycopy(Float2BooleanArrayMap.this.value, this.next + 1, Float2BooleanArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		public int size() {
			return Float2BooleanArrayMap.this.size;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Float)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Boolean) {
					float k = (Float)e.getKey();
					return Float2BooleanArrayMap.this.containsKey(k) && Float2BooleanArrayMap.this.get(k) == (Boolean)e.getValue();
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
				} else if (e.getValue() != null && e.getValue() instanceof Boolean) {
					float k = (Float)e.getKey();
					boolean v = (Boolean)e.getValue();
					int oldPos = Float2BooleanArrayMap.this.findKey(k);
					if (oldPos != -1 && v == Float2BooleanArrayMap.this.value[oldPos]) {
						int tail = Float2BooleanArrayMap.this.size - oldPos - 1;
						System.arraycopy(Float2BooleanArrayMap.this.key, oldPos + 1, Float2BooleanArrayMap.this.key, oldPos, tail);
						System.arraycopy(Float2BooleanArrayMap.this.value, oldPos + 1, Float2BooleanArrayMap.this.value, oldPos, tail);
						Float2BooleanArrayMap.this.size--;
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
