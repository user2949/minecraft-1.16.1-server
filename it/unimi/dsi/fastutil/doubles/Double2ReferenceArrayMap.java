package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDouble2ReferenceMap.BasicEntry;
import it.unimi.dsi.fastutil.doubles.Double2ReferenceMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

public class Double2ReferenceArrayMap<V> extends AbstractDouble2ReferenceMap<V> implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private transient double[] key;
	private transient Object[] value;
	private int size;

	public Double2ReferenceArrayMap(double[] key, Object[] value) {
		this.key = key;
		this.value = value;
		this.size = key.length;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		}
	}

	public Double2ReferenceArrayMap() {
		this.key = DoubleArrays.EMPTY_ARRAY;
		this.value = ObjectArrays.EMPTY_ARRAY;
	}

	public Double2ReferenceArrayMap(int capacity) {
		this.key = new double[capacity];
		this.value = new Object[capacity];
	}

	public Double2ReferenceArrayMap(Double2ReferenceMap<V> m) {
		this(m.size());
		this.putAll(m);
	}

	public Double2ReferenceArrayMap(Map<? extends Double, ? extends V> m) {
		this(m.size());
		this.putAll(m);
	}

	public Double2ReferenceArrayMap(double[] key, Object[] value, int size) {
		this.key = key;
		this.value = value;
		this.size = size;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		} else if (size > key.length) {
			throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
		}
	}

	public FastEntrySet<V> double2ReferenceEntrySet() {
		return new Double2ReferenceArrayMap.EntrySet();
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
	public V get(double k) {
		double[] key = this.key;
		int i = this.size;

		while (i-- != 0) {
			if (Double.doubleToLongBits(key[i]) == Double.doubleToLongBits(k)) {
				return (V)this.value[i];
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
		int i = this.size;

		while (i-- != 0) {
			this.value[i] = null;
		}

		this.size = 0;
	}

	@Override
	public boolean containsKey(double k) {
		return this.findKey(k) != -1;
	}

	@Override
	public boolean containsValue(Object v) {
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
	public V put(double k, V v) {
		int oldKey = this.findKey(k);
		if (oldKey != -1) {
			V oldValue = (V)this.value[oldKey];
			this.value[oldKey] = v;
			return oldValue;
		} else {
			if (this.size == this.key.length) {
				double[] newKey = new double[this.size == 0 ? 2 : this.size * 2];
				Object[] newValue = new Object[this.size == 0 ? 2 : this.size * 2];

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
	public V remove(double k) {
		int oldPos = this.findKey(k);
		if (oldPos == -1) {
			return this.defRetValue;
		} else {
			V oldValue = (V)this.value[oldPos];
			int tail = this.size - oldPos - 1;
			System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
			System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
			this.size--;
			this.value[this.size] = null;
			return oldValue;
		}
	}

	@Override
	public DoubleSet keySet() {
		return new AbstractDoubleSet() {
			@Override
			public boolean contains(double k) {
				return Double2ReferenceArrayMap.this.findKey(k) != -1;
			}

			@Override
			public boolean remove(double k) {
				int oldPos = Double2ReferenceArrayMap.this.findKey(k);
				if (oldPos == -1) {
					return false;
				} else {
					int tail = Double2ReferenceArrayMap.this.size - oldPos - 1;
					System.arraycopy(Double2ReferenceArrayMap.this.key, oldPos + 1, Double2ReferenceArrayMap.this.key, oldPos, tail);
					System.arraycopy(Double2ReferenceArrayMap.this.value, oldPos + 1, Double2ReferenceArrayMap.this.value, oldPos, tail);
					Double2ReferenceArrayMap.this.size--;
					return true;
				}
			}

			@Override
			public DoubleIterator iterator() {
				return new DoubleIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Double2ReferenceArrayMap.this.size;
					}

					@Override
					public double nextDouble() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Double2ReferenceArrayMap.this.key[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Double2ReferenceArrayMap.this.size - this.pos;
							System.arraycopy(Double2ReferenceArrayMap.this.key, this.pos, Double2ReferenceArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Double2ReferenceArrayMap.this.value, this.pos, Double2ReferenceArrayMap.this.value, this.pos - 1, tail);
							Double2ReferenceArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Double2ReferenceArrayMap.this.size;
			}

			public void clear() {
				Double2ReferenceArrayMap.this.clear();
			}
		};
	}

	@Override
	public ReferenceCollection<V> values() {
		return new AbstractReferenceCollection<V>() {
			public boolean contains(Object v) {
				return Double2ReferenceArrayMap.this.containsValue(v);
			}

			@Override
			public ObjectIterator<V> iterator() {
				return new ObjectIterator<V>() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Double2ReferenceArrayMap.this.size;
					}

					public V next() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return (V)Double2ReferenceArrayMap.this.value[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Double2ReferenceArrayMap.this.size - this.pos;
							System.arraycopy(Double2ReferenceArrayMap.this.key, this.pos, Double2ReferenceArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Double2ReferenceArrayMap.this.value, this.pos, Double2ReferenceArrayMap.this.value, this.pos - 1, tail);
							Double2ReferenceArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Double2ReferenceArrayMap.this.size;
			}

			public void clear() {
				Double2ReferenceArrayMap.this.clear();
			}
		};
	}

	public Double2ReferenceArrayMap<V> clone() {
		Double2ReferenceArrayMap<V> c;
		try {
			c = (Double2ReferenceArrayMap<V>)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (double[])this.key.clone();
		c.value = (Object[])this.value.clone();
		return c;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; i < this.size; i++) {
			s.writeDouble(this.key[i]);
			s.writeObject(this.value[i]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.key = new double[this.size];
		this.value = new Object[this.size];

		for (int i = 0; i < this.size; i++) {
			this.key[i] = s.readDouble();
			this.value[i] = s.readObject();
		}
	}

	private final class EntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.doubles.Double2ReferenceMap.Entry<V>> implements FastEntrySet<V> {
		private EntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.doubles.Double2ReferenceMap.Entry<V>> iterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.doubles.Double2ReferenceMap.Entry<V>>() {
				int curr = -1;
				int next = 0;

				public boolean hasNext() {
					return this.next < Double2ReferenceArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.doubles.Double2ReferenceMap.Entry<V> next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return new BasicEntry<>(Double2ReferenceArrayMap.this.key[this.curr = this.next], (V)Double2ReferenceArrayMap.this.value[this.next++]);
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Double2ReferenceArrayMap.this.size-- - this.next--;
						System.arraycopy(Double2ReferenceArrayMap.this.key, this.next + 1, Double2ReferenceArrayMap.this.key, this.next, tail);
						System.arraycopy(Double2ReferenceArrayMap.this.value, this.next + 1, Double2ReferenceArrayMap.this.value, this.next, tail);
						Double2ReferenceArrayMap.this.value[Double2ReferenceArrayMap.this.size] = null;
					}
				}
			};
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.doubles.Double2ReferenceMap.Entry<V>> fastIterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.doubles.Double2ReferenceMap.Entry<V>>() {
				int next = 0;
				int curr = -1;
				final BasicEntry<V> entry = new BasicEntry<>();

				public boolean hasNext() {
					return this.next < Double2ReferenceArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.doubles.Double2ReferenceMap.Entry<V> next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						this.entry.key = Double2ReferenceArrayMap.this.key[this.curr = this.next];
						this.entry.value = (V)Double2ReferenceArrayMap.this.value[this.next++];
						return this.entry;
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Double2ReferenceArrayMap.this.size-- - this.next--;
						System.arraycopy(Double2ReferenceArrayMap.this.key, this.next + 1, Double2ReferenceArrayMap.this.key, this.next, tail);
						System.arraycopy(Double2ReferenceArrayMap.this.value, this.next + 1, Double2ReferenceArrayMap.this.value, this.next, tail);
						Double2ReferenceArrayMap.this.value[Double2ReferenceArrayMap.this.size] = null;
					}
				}
			};
		}

		public int size() {
			return Double2ReferenceArrayMap.this.size;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getKey() != null && e.getKey() instanceof Double) {
					double k = (Double)e.getKey();
					return Double2ReferenceArrayMap.this.containsKey(k) && Double2ReferenceArrayMap.this.get(k) == e.getValue();
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
				if (e.getKey() != null && e.getKey() instanceof Double) {
					double k = (Double)e.getKey();
					V v = (V)e.getValue();
					int oldPos = Double2ReferenceArrayMap.this.findKey(k);
					if (oldPos != -1 && v == Double2ReferenceArrayMap.this.value[oldPos]) {
						int tail = Double2ReferenceArrayMap.this.size - oldPos - 1;
						System.arraycopy(Double2ReferenceArrayMap.this.key, oldPos + 1, Double2ReferenceArrayMap.this.key, oldPos, tail);
						System.arraycopy(Double2ReferenceArrayMap.this.value, oldPos + 1, Double2ReferenceArrayMap.this.value, oldPos, tail);
						Double2ReferenceArrayMap.this.size--;
						Double2ReferenceArrayMap.this.value[Double2ReferenceArrayMap.this.size] = null;
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
