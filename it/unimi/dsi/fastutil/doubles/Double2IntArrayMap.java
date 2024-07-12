package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDouble2IntMap.BasicEntry;
import it.unimi.dsi.fastutil.doubles.Double2IntMap.FastEntrySet;
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

public class Double2IntArrayMap extends AbstractDouble2IntMap implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private transient double[] key;
	private transient int[] value;
	private int size;

	public Double2IntArrayMap(double[] key, int[] value) {
		this.key = key;
		this.value = value;
		this.size = key.length;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		}
	}

	public Double2IntArrayMap() {
		this.key = DoubleArrays.EMPTY_ARRAY;
		this.value = IntArrays.EMPTY_ARRAY;
	}

	public Double2IntArrayMap(int capacity) {
		this.key = new double[capacity];
		this.value = new int[capacity];
	}

	public Double2IntArrayMap(Double2IntMap m) {
		this(m.size());
		this.putAll(m);
	}

	public Double2IntArrayMap(Map<? extends Double, ? extends Integer> m) {
		this(m.size());
		this.putAll(m);
	}

	public Double2IntArrayMap(double[] key, int[] value, int size) {
		this.key = key;
		this.value = value;
		this.size = size;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		} else if (size > key.length) {
			throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
		}
	}

	public FastEntrySet double2IntEntrySet() {
		return new Double2IntArrayMap.EntrySet();
	}

	private int findKey(double k) {
		double[] key = this.key;
		int i = this.size;

		while (i-- != 0) {
			if (Double.doubleToLongBits(key[i]) == Double.doubleToLongBits(k)) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public int get(double k) {
		double[] key = this.key;
		int i = this.size;

		while (i-- != 0) {
			if (Double.doubleToLongBits(key[i]) == Double.doubleToLongBits(k)) {
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
	public boolean containsKey(double k) {
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
	public int put(double k, int v) {
		int oldKey = this.findKey(k);
		if (oldKey != -1) {
			int oldValue = this.value[oldKey];
			this.value[oldKey] = v;
			return oldValue;
		} else {
			if (this.size == this.key.length) {
				double[] newKey = new double[this.size == 0 ? 2 : this.size * 2];
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
	public int remove(double k) {
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
	public DoubleSet keySet() {
		return new AbstractDoubleSet() {
			@Override
			public boolean contains(double k) {
				return Double2IntArrayMap.this.findKey(k) != -1;
			}

			@Override
			public boolean remove(double k) {
				int oldPos = Double2IntArrayMap.this.findKey(k);
				if (oldPos == -1) {
					return false;
				} else {
					int tail = Double2IntArrayMap.this.size - oldPos - 1;
					System.arraycopy(Double2IntArrayMap.this.key, oldPos + 1, Double2IntArrayMap.this.key, oldPos, tail);
					System.arraycopy(Double2IntArrayMap.this.value, oldPos + 1, Double2IntArrayMap.this.value, oldPos, tail);
					Double2IntArrayMap.this.size--;
					return true;
				}
			}

			@Override
			public DoubleIterator iterator() {
				return new DoubleIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Double2IntArrayMap.this.size;
					}

					@Override
					public double nextDouble() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Double2IntArrayMap.this.key[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Double2IntArrayMap.this.size - this.pos;
							System.arraycopy(Double2IntArrayMap.this.key, this.pos, Double2IntArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Double2IntArrayMap.this.value, this.pos, Double2IntArrayMap.this.value, this.pos - 1, tail);
							Double2IntArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Double2IntArrayMap.this.size;
			}

			public void clear() {
				Double2IntArrayMap.this.clear();
			}
		};
	}

	@Override
	public IntCollection values() {
		return new AbstractIntCollection() {
			@Override
			public boolean contains(int v) {
				return Double2IntArrayMap.this.containsValue(v);
			}

			@Override
			public IntIterator iterator() {
				return new IntIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Double2IntArrayMap.this.size;
					}

					@Override
					public int nextInt() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Double2IntArrayMap.this.value[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Double2IntArrayMap.this.size - this.pos;
							System.arraycopy(Double2IntArrayMap.this.key, this.pos, Double2IntArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Double2IntArrayMap.this.value, this.pos, Double2IntArrayMap.this.value, this.pos - 1, tail);
							Double2IntArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Double2IntArrayMap.this.size;
			}

			public void clear() {
				Double2IntArrayMap.this.clear();
			}
		};
	}

	public Double2IntArrayMap clone() {
		Double2IntArrayMap c;
		try {
			c = (Double2IntArrayMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (double[])this.key.clone();
		c.value = (int[])this.value.clone();
		return c;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; i < this.size; i++) {
			s.writeDouble(this.key[i]);
			s.writeInt(this.value[i]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.key = new double[this.size];
		this.value = new int[this.size];

		for (int i = 0; i < this.size; i++) {
			this.key[i] = s.readDouble();
			this.value[i] = s.readInt();
		}
	}

	private final class EntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.doubles.Double2IntMap.Entry> implements FastEntrySet {
		private EntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.doubles.Double2IntMap.Entry> iterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.doubles.Double2IntMap.Entry>() {
				int curr = -1;
				int next = 0;

				public boolean hasNext() {
					return this.next < Double2IntArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.doubles.Double2IntMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return new BasicEntry(Double2IntArrayMap.this.key[this.curr = this.next], Double2IntArrayMap.this.value[this.next++]);
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Double2IntArrayMap.this.size-- - this.next--;
						System.arraycopy(Double2IntArrayMap.this.key, this.next + 1, Double2IntArrayMap.this.key, this.next, tail);
						System.arraycopy(Double2IntArrayMap.this.value, this.next + 1, Double2IntArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.doubles.Double2IntMap.Entry> fastIterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.doubles.Double2IntMap.Entry>() {
				int next = 0;
				int curr = -1;
				final BasicEntry entry = new BasicEntry();

				public boolean hasNext() {
					return this.next < Double2IntArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.doubles.Double2IntMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						this.entry.key = Double2IntArrayMap.this.key[this.curr = this.next];
						this.entry.value = Double2IntArrayMap.this.value[this.next++];
						return this.entry;
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Double2IntArrayMap.this.size-- - this.next--;
						System.arraycopy(Double2IntArrayMap.this.key, this.next + 1, Double2IntArrayMap.this.key, this.next, tail);
						System.arraycopy(Double2IntArrayMap.this.value, this.next + 1, Double2IntArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		public int size() {
			return Double2IntArrayMap.this.size;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Double)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Integer) {
					double k = (Double)e.getKey();
					return Double2IntArrayMap.this.containsKey(k) && Double2IntArrayMap.this.get(k) == (Integer)e.getValue();
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
				if (e.getKey() == null || !(e.getKey() instanceof Double)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Integer) {
					double k = (Double)e.getKey();
					int v = (Integer)e.getValue();
					int oldPos = Double2IntArrayMap.this.findKey(k);
					if (oldPos != -1 && v == Double2IntArrayMap.this.value[oldPos]) {
						int tail = Double2IntArrayMap.this.size - oldPos - 1;
						System.arraycopy(Double2IntArrayMap.this.key, oldPos + 1, Double2IntArrayMap.this.key, oldPos, tail);
						System.arraycopy(Double2IntArrayMap.this.value, oldPos + 1, Double2IntArrayMap.this.value, oldPos, tail);
						Double2IntArrayMap.this.size--;
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
