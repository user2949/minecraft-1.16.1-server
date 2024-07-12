package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDouble2DoubleMap.BasicEntry;
import it.unimi.dsi.fastutil.doubles.Double2DoubleMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

public class Double2DoubleArrayMap extends AbstractDouble2DoubleMap implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private transient double[] key;
	private transient double[] value;
	private int size;

	public Double2DoubleArrayMap(double[] key, double[] value) {
		this.key = key;
		this.value = value;
		this.size = key.length;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		}
	}

	public Double2DoubleArrayMap() {
		this.key = DoubleArrays.EMPTY_ARRAY;
		this.value = DoubleArrays.EMPTY_ARRAY;
	}

	public Double2DoubleArrayMap(int capacity) {
		this.key = new double[capacity];
		this.value = new double[capacity];
	}

	public Double2DoubleArrayMap(Double2DoubleMap m) {
		this(m.size());
		this.putAll(m);
	}

	public Double2DoubleArrayMap(Map<? extends Double, ? extends Double> m) {
		this(m.size());
		this.putAll(m);
	}

	public Double2DoubleArrayMap(double[] key, double[] value, int size) {
		this.key = key;
		this.value = value;
		this.size = size;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		} else if (size > key.length) {
			throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
		}
	}

	public FastEntrySet double2DoubleEntrySet() {
		return new Double2DoubleArrayMap.EntrySet();
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
	public double get(double k) {
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
	public double put(double k, double v) {
		int oldKey = this.findKey(k);
		if (oldKey != -1) {
			double oldValue = this.value[oldKey];
			this.value[oldKey] = v;
			return oldValue;
		} else {
			if (this.size == this.key.length) {
				double[] newKey = new double[this.size == 0 ? 2 : this.size * 2];
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
	public double remove(double k) {
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
	public DoubleSet keySet() {
		return new AbstractDoubleSet() {
			@Override
			public boolean contains(double k) {
				return Double2DoubleArrayMap.this.findKey(k) != -1;
			}

			@Override
			public boolean remove(double k) {
				int oldPos = Double2DoubleArrayMap.this.findKey(k);
				if (oldPos == -1) {
					return false;
				} else {
					int tail = Double2DoubleArrayMap.this.size - oldPos - 1;
					System.arraycopy(Double2DoubleArrayMap.this.key, oldPos + 1, Double2DoubleArrayMap.this.key, oldPos, tail);
					System.arraycopy(Double2DoubleArrayMap.this.value, oldPos + 1, Double2DoubleArrayMap.this.value, oldPos, tail);
					Double2DoubleArrayMap.this.size--;
					return true;
				}
			}

			@Override
			public DoubleIterator iterator() {
				return new DoubleIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Double2DoubleArrayMap.this.size;
					}

					@Override
					public double nextDouble() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Double2DoubleArrayMap.this.key[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Double2DoubleArrayMap.this.size - this.pos;
							System.arraycopy(Double2DoubleArrayMap.this.key, this.pos, Double2DoubleArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Double2DoubleArrayMap.this.value, this.pos, Double2DoubleArrayMap.this.value, this.pos - 1, tail);
							Double2DoubleArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Double2DoubleArrayMap.this.size;
			}

			public void clear() {
				Double2DoubleArrayMap.this.clear();
			}
		};
	}

	@Override
	public DoubleCollection values() {
		return new AbstractDoubleCollection() {
			@Override
			public boolean contains(double v) {
				return Double2DoubleArrayMap.this.containsValue(v);
			}

			@Override
			public DoubleIterator iterator() {
				return new DoubleIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Double2DoubleArrayMap.this.size;
					}

					@Override
					public double nextDouble() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Double2DoubleArrayMap.this.value[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Double2DoubleArrayMap.this.size - this.pos;
							System.arraycopy(Double2DoubleArrayMap.this.key, this.pos, Double2DoubleArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Double2DoubleArrayMap.this.value, this.pos, Double2DoubleArrayMap.this.value, this.pos - 1, tail);
							Double2DoubleArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Double2DoubleArrayMap.this.size;
			}

			public void clear() {
				Double2DoubleArrayMap.this.clear();
			}
		};
	}

	public Double2DoubleArrayMap clone() {
		Double2DoubleArrayMap c;
		try {
			c = (Double2DoubleArrayMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (double[])this.key.clone();
		c.value = (double[])this.value.clone();
		return c;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; i < this.size; i++) {
			s.writeDouble(this.key[i]);
			s.writeDouble(this.value[i]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.key = new double[this.size];
		this.value = new double[this.size];

		for (int i = 0; i < this.size; i++) {
			this.key[i] = s.readDouble();
			this.value[i] = s.readDouble();
		}
	}

	private final class EntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> implements FastEntrySet {
		private EntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> iterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry>() {
				int curr = -1;
				int next = 0;

				public boolean hasNext() {
					return this.next < Double2DoubleArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return new BasicEntry(Double2DoubleArrayMap.this.key[this.curr = this.next], Double2DoubleArrayMap.this.value[this.next++]);
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Double2DoubleArrayMap.this.size-- - this.next--;
						System.arraycopy(Double2DoubleArrayMap.this.key, this.next + 1, Double2DoubleArrayMap.this.key, this.next, tail);
						System.arraycopy(Double2DoubleArrayMap.this.value, this.next + 1, Double2DoubleArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry> fastIterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry>() {
				int next = 0;
				int curr = -1;
				final BasicEntry entry = new BasicEntry();

				public boolean hasNext() {
					return this.next < Double2DoubleArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.doubles.Double2DoubleMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						this.entry.key = Double2DoubleArrayMap.this.key[this.curr = this.next];
						this.entry.value = Double2DoubleArrayMap.this.value[this.next++];
						return this.entry;
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Double2DoubleArrayMap.this.size-- - this.next--;
						System.arraycopy(Double2DoubleArrayMap.this.key, this.next + 1, Double2DoubleArrayMap.this.key, this.next, tail);
						System.arraycopy(Double2DoubleArrayMap.this.value, this.next + 1, Double2DoubleArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		public int size() {
			return Double2DoubleArrayMap.this.size;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Double)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Double) {
					double k = (Double)e.getKey();
					return Double2DoubleArrayMap.this.containsKey(k)
						&& Double.doubleToLongBits(Double2DoubleArrayMap.this.get(k)) == Double.doubleToLongBits((Double)e.getValue());
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
				} else if (e.getValue() != null && e.getValue() instanceof Double) {
					double k = (Double)e.getKey();
					double v = (Double)e.getValue();
					int oldPos = Double2DoubleArrayMap.this.findKey(k);
					if (oldPos != -1 && Double.doubleToLongBits(v) == Double.doubleToLongBits(Double2DoubleArrayMap.this.value[oldPos])) {
						int tail = Double2DoubleArrayMap.this.size - oldPos - 1;
						System.arraycopy(Double2DoubleArrayMap.this.key, oldPos + 1, Double2DoubleArrayMap.this.key, oldPos, tail);
						System.arraycopy(Double2DoubleArrayMap.this.value, oldPos + 1, Double2DoubleArrayMap.this.value, oldPos, tail);
						Double2DoubleArrayMap.this.size--;
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
