package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleArrays;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.ints.AbstractInt2DoubleMap.BasicEntry;
import it.unimi.dsi.fastutil.ints.Int2DoubleMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

public class Int2DoubleArrayMap extends AbstractInt2DoubleMap implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private transient int[] key;
	private transient double[] value;
	private int size;

	public Int2DoubleArrayMap(int[] key, double[] value) {
		this.key = key;
		this.value = value;
		this.size = key.length;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		}
	}

	public Int2DoubleArrayMap() {
		this.key = IntArrays.EMPTY_ARRAY;
		this.value = DoubleArrays.EMPTY_ARRAY;
	}

	public Int2DoubleArrayMap(int capacity) {
		this.key = new int[capacity];
		this.value = new double[capacity];
	}

	public Int2DoubleArrayMap(Int2DoubleMap m) {
		this(m.size());
		this.putAll(m);
	}

	public Int2DoubleArrayMap(Map<? extends Integer, ? extends Double> m) {
		this(m.size());
		this.putAll(m);
	}

	public Int2DoubleArrayMap(int[] key, double[] value, int size) {
		this.key = key;
		this.value = value;
		this.size = size;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		} else if (size > key.length) {
			throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
		}
	}

	public FastEntrySet int2DoubleEntrySet() {
		return new Int2DoubleArrayMap.EntrySet();
	}

	private int findKey(int k) {
		int[] key = this.key;
		int i = this.size;

		while (i-- != 0) {
			if (key[i] == k) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public double get(int k) {
		int[] key = this.key;
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
	public boolean containsKey(int k) {
		return this.findKey(k) != -1;
	}

	@Override
	public boolean containsValue(double v) {
		int i = this.size;

		while (i-- != 0) {
			if (Double.doubleToLongBits(this.value[i]) == Double.doubleToLongBits(v)) {
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
	public double put(int k, double v) {
		int oldKey = this.findKey(k);
		if (oldKey != -1) {
			double oldValue = this.value[oldKey];
			this.value[oldKey] = v;
			return oldValue;
		} else {
			if (this.size == this.key.length) {
				int[] newKey = new int[this.size == 0 ? 2 : this.size * 2];
				double[] newValue = new double[this.size == 0 ? 2 : this.size * 2];

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
	public double remove(int k) {
		int oldPos = this.findKey(k);
		if (oldPos == -1) {
			return this.defRetValue;
		} else {
			double oldValue = this.value[oldPos];
			int tail = this.size - oldPos - 1;
			System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
			System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
			this.size--;
			return oldValue;
		}
	}

	@Override
	public IntSet keySet() {
		return new AbstractIntSet() {
			@Override
			public boolean contains(int k) {
				return Int2DoubleArrayMap.this.findKey(k) != -1;
			}

			@Override
			public boolean remove(int k) {
				int oldPos = Int2DoubleArrayMap.this.findKey(k);
				if (oldPos == -1) {
					return false;
				} else {
					int tail = Int2DoubleArrayMap.this.size - oldPos - 1;
					System.arraycopy(Int2DoubleArrayMap.this.key, oldPos + 1, Int2DoubleArrayMap.this.key, oldPos, tail);
					System.arraycopy(Int2DoubleArrayMap.this.value, oldPos + 1, Int2DoubleArrayMap.this.value, oldPos, tail);
					Int2DoubleArrayMap.this.size--;
					return true;
				}
			}

			@Override
			public IntIterator iterator() {
				return new IntIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Int2DoubleArrayMap.this.size;
					}

					@Override
					public int nextInt() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Int2DoubleArrayMap.this.key[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Int2DoubleArrayMap.this.size - this.pos;
							System.arraycopy(Int2DoubleArrayMap.this.key, this.pos, Int2DoubleArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Int2DoubleArrayMap.this.value, this.pos, Int2DoubleArrayMap.this.value, this.pos - 1, tail);
							Int2DoubleArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Int2DoubleArrayMap.this.size;
			}

			public void clear() {
				Int2DoubleArrayMap.this.clear();
			}
		};
	}

	@Override
	public DoubleCollection values() {
		return new AbstractDoubleCollection() {
			@Override
			public boolean contains(double v) {
				return Int2DoubleArrayMap.this.containsValue(v);
			}

			@Override
			public DoubleIterator iterator() {
				return new DoubleIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Int2DoubleArrayMap.this.size;
					}

					@Override
					public double nextDouble() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Int2DoubleArrayMap.this.value[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Int2DoubleArrayMap.this.size - this.pos;
							System.arraycopy(Int2DoubleArrayMap.this.key, this.pos, Int2DoubleArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Int2DoubleArrayMap.this.value, this.pos, Int2DoubleArrayMap.this.value, this.pos - 1, tail);
							Int2DoubleArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Int2DoubleArrayMap.this.size;
			}

			public void clear() {
				Int2DoubleArrayMap.this.clear();
			}
		};
	}

	public Int2DoubleArrayMap clone() {
		Int2DoubleArrayMap c;
		try {
			c = (Int2DoubleArrayMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (int[])this.key.clone();
		c.value = (double[])this.value.clone();
		return c;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; i < this.size; i++) {
			s.writeInt(this.key[i]);
			s.writeDouble(this.value[i]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.key = new int[this.size];
		this.value = new double[this.size];

		for (int i = 0; i < this.size; i++) {
			this.key[i] = s.readInt();
			this.value[i] = s.readDouble();
		}
	}

	private final class EntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.ints.Int2DoubleMap.Entry> implements FastEntrySet {
		private EntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.ints.Int2DoubleMap.Entry> iterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.ints.Int2DoubleMap.Entry>() {
				int curr = -1;
				int next = 0;

				public boolean hasNext() {
					return this.next < Int2DoubleArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.ints.Int2DoubleMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return new BasicEntry(Int2DoubleArrayMap.this.key[this.curr = this.next], Int2DoubleArrayMap.this.value[this.next++]);
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Int2DoubleArrayMap.this.size-- - this.next--;
						System.arraycopy(Int2DoubleArrayMap.this.key, this.next + 1, Int2DoubleArrayMap.this.key, this.next, tail);
						System.arraycopy(Int2DoubleArrayMap.this.value, this.next + 1, Int2DoubleArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.ints.Int2DoubleMap.Entry> fastIterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.ints.Int2DoubleMap.Entry>() {
				int next = 0;
				int curr = -1;
				final BasicEntry entry = new BasicEntry();

				public boolean hasNext() {
					return this.next < Int2DoubleArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.ints.Int2DoubleMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						this.entry.key = Int2DoubleArrayMap.this.key[this.curr = this.next];
						this.entry.value = Int2DoubleArrayMap.this.value[this.next++];
						return this.entry;
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Int2DoubleArrayMap.this.size-- - this.next--;
						System.arraycopy(Int2DoubleArrayMap.this.key, this.next + 1, Int2DoubleArrayMap.this.key, this.next, tail);
						System.arraycopy(Int2DoubleArrayMap.this.value, this.next + 1, Int2DoubleArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		public int size() {
			return Int2DoubleArrayMap.this.size;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Integer)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Double) {
					int k = (Integer)e.getKey();
					return Int2DoubleArrayMap.this.containsKey(k) && Double.doubleToLongBits(Int2DoubleArrayMap.this.get(k)) == Double.doubleToLongBits((Double)e.getValue());
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
				if (e.getKey() == null || !(e.getKey() instanceof Integer)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Double) {
					int k = (Integer)e.getKey();
					double v = (Double)e.getValue();
					int oldPos = Int2DoubleArrayMap.this.findKey(k);
					if (oldPos != -1 && Double.doubleToLongBits(v) == Double.doubleToLongBits(Int2DoubleArrayMap.this.value[oldPos])) {
						int tail = Int2DoubleArrayMap.this.size - oldPos - 1;
						System.arraycopy(Int2DoubleArrayMap.this.key, oldPos + 1, Int2DoubleArrayMap.this.key, oldPos, tail);
						System.arraycopy(Int2DoubleArrayMap.this.value, oldPos + 1, Int2DoubleArrayMap.this.value, oldPos, tail);
						Int2DoubleArrayMap.this.size--;
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
