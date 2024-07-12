package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanArrays;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2BooleanMap.BasicEntry;
import it.unimi.dsi.fastutil.doubles.Double2BooleanMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

public class Double2BooleanArrayMap extends AbstractDouble2BooleanMap implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private transient double[] key;
	private transient boolean[] value;
	private int size;

	public Double2BooleanArrayMap(double[] key, boolean[] value) {
		this.key = key;
		this.value = value;
		this.size = key.length;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		}
	}

	public Double2BooleanArrayMap() {
		this.key = DoubleArrays.EMPTY_ARRAY;
		this.value = BooleanArrays.EMPTY_ARRAY;
	}

	public Double2BooleanArrayMap(int capacity) {
		this.key = new double[capacity];
		this.value = new boolean[capacity];
	}

	public Double2BooleanArrayMap(Double2BooleanMap m) {
		this(m.size());
		this.putAll(m);
	}

	public Double2BooleanArrayMap(Map<? extends Double, ? extends Boolean> m) {
		this(m.size());
		this.putAll(m);
	}

	public Double2BooleanArrayMap(double[] key, boolean[] value, int size) {
		this.key = key;
		this.value = value;
		this.size = size;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		} else if (size > key.length) {
			throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
		}
	}

	public FastEntrySet double2BooleanEntrySet() {
		return new Double2BooleanArrayMap.EntrySet();
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
	public boolean get(double k) {
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
	public boolean put(double k, boolean v) {
		int oldKey = this.findKey(k);
		if (oldKey != -1) {
			boolean oldValue = this.value[oldKey];
			this.value[oldKey] = v;
			return oldValue;
		} else {
			if (this.size == this.key.length) {
				double[] newKey = new double[this.size == 0 ? 2 : this.size * 2];
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
	public boolean remove(double k) {
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
	public DoubleSet keySet() {
		return new AbstractDoubleSet() {
			@Override
			public boolean contains(double k) {
				return Double2BooleanArrayMap.this.findKey(k) != -1;
			}

			@Override
			public boolean remove(double k) {
				int oldPos = Double2BooleanArrayMap.this.findKey(k);
				if (oldPos == -1) {
					return false;
				} else {
					int tail = Double2BooleanArrayMap.this.size - oldPos - 1;
					System.arraycopy(Double2BooleanArrayMap.this.key, oldPos + 1, Double2BooleanArrayMap.this.key, oldPos, tail);
					System.arraycopy(Double2BooleanArrayMap.this.value, oldPos + 1, Double2BooleanArrayMap.this.value, oldPos, tail);
					Double2BooleanArrayMap.this.size--;
					return true;
				}
			}

			@Override
			public DoubleIterator iterator() {
				return new DoubleIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Double2BooleanArrayMap.this.size;
					}

					@Override
					public double nextDouble() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Double2BooleanArrayMap.this.key[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Double2BooleanArrayMap.this.size - this.pos;
							System.arraycopy(Double2BooleanArrayMap.this.key, this.pos, Double2BooleanArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Double2BooleanArrayMap.this.value, this.pos, Double2BooleanArrayMap.this.value, this.pos - 1, tail);
							Double2BooleanArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Double2BooleanArrayMap.this.size;
			}

			public void clear() {
				Double2BooleanArrayMap.this.clear();
			}
		};
	}

	@Override
	public BooleanCollection values() {
		return new AbstractBooleanCollection() {
			@Override
			public boolean contains(boolean v) {
				return Double2BooleanArrayMap.this.containsValue(v);
			}

			@Override
			public BooleanIterator iterator() {
				return new BooleanIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Double2BooleanArrayMap.this.size;
					}

					@Override
					public boolean nextBoolean() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Double2BooleanArrayMap.this.value[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Double2BooleanArrayMap.this.size - this.pos;
							System.arraycopy(Double2BooleanArrayMap.this.key, this.pos, Double2BooleanArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Double2BooleanArrayMap.this.value, this.pos, Double2BooleanArrayMap.this.value, this.pos - 1, tail);
							Double2BooleanArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Double2BooleanArrayMap.this.size;
			}

			public void clear() {
				Double2BooleanArrayMap.this.clear();
			}
		};
	}

	public Double2BooleanArrayMap clone() {
		Double2BooleanArrayMap c;
		try {
			c = (Double2BooleanArrayMap)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (double[])this.key.clone();
		c.value = (boolean[])this.value.clone();
		return c;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; i < this.size; i++) {
			s.writeDouble(this.key[i]);
			s.writeBoolean(this.value[i]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.key = new double[this.size];
		this.value = new boolean[this.size];

		for (int i = 0; i < this.size; i++) {
			this.key[i] = s.readDouble();
			this.value[i] = s.readBoolean();
		}
	}

	private final class EntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry> implements FastEntrySet {
		private EntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry> iterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry>() {
				int curr = -1;
				int next = 0;

				public boolean hasNext() {
					return this.next < Double2BooleanArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return new BasicEntry(Double2BooleanArrayMap.this.key[this.curr = this.next], Double2BooleanArrayMap.this.value[this.next++]);
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Double2BooleanArrayMap.this.size-- - this.next--;
						System.arraycopy(Double2BooleanArrayMap.this.key, this.next + 1, Double2BooleanArrayMap.this.key, this.next, tail);
						System.arraycopy(Double2BooleanArrayMap.this.value, this.next + 1, Double2BooleanArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry> fastIterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry>() {
				int next = 0;
				int curr = -1;
				final BasicEntry entry = new BasicEntry();

				public boolean hasNext() {
					return this.next < Double2BooleanArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						this.entry.key = Double2BooleanArrayMap.this.key[this.curr = this.next];
						this.entry.value = Double2BooleanArrayMap.this.value[this.next++];
						return this.entry;
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Double2BooleanArrayMap.this.size-- - this.next--;
						System.arraycopy(Double2BooleanArrayMap.this.key, this.next + 1, Double2BooleanArrayMap.this.key, this.next, tail);
						System.arraycopy(Double2BooleanArrayMap.this.value, this.next + 1, Double2BooleanArrayMap.this.value, this.next, tail);
					}
				}
			};
		}

		public int size() {
			return Double2BooleanArrayMap.this.size;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() == null || !(e.getKey() instanceof Double)) {
					return false;
				} else if (e.getValue() != null && e.getValue() instanceof Boolean) {
					double k = (Double)e.getKey();
					return Double2BooleanArrayMap.this.containsKey(k) && Double2BooleanArrayMap.this.get(k) == (Boolean)e.getValue();
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
				} else if (e.getValue() != null && e.getValue() instanceof Boolean) {
					double k = (Double)e.getKey();
					boolean v = (Boolean)e.getValue();
					int oldPos = Double2BooleanArrayMap.this.findKey(k);
					if (oldPos != -1 && v == Double2BooleanArrayMap.this.value[oldPos]) {
						int tail = Double2BooleanArrayMap.this.size - oldPos - 1;
						System.arraycopy(Double2BooleanArrayMap.this.key, oldPos + 1, Double2BooleanArrayMap.this.key, oldPos, tail);
						System.arraycopy(Double2BooleanArrayMap.this.value, oldPos + 1, Double2BooleanArrayMap.this.value, oldPos, tail);
						Double2BooleanArrayMap.this.size--;
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
