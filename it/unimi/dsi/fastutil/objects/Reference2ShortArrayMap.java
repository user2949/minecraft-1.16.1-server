package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractReference2ShortMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Reference2ShortMap.FastEntrySet;
import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortArrays;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

public class Reference2ShortArrayMap<K> extends AbstractReference2ShortMap<K> implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private transient Object[] key;
	private transient short[] value;
	private int size;

	public Reference2ShortArrayMap(Object[] key, short[] value) {
		this.key = key;
		this.value = value;
		this.size = key.length;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		}
	}

	public Reference2ShortArrayMap() {
		this.key = ObjectArrays.EMPTY_ARRAY;
		this.value = ShortArrays.EMPTY_ARRAY;
	}

	public Reference2ShortArrayMap(int capacity) {
		this.key = new Object[capacity];
		this.value = new short[capacity];
	}

	public Reference2ShortArrayMap(Reference2ShortMap<K> m) {
		this(m.size());
		this.putAll(m);
	}

	public Reference2ShortArrayMap(Map<? extends K, ? extends Short> m) {
		this(m.size());
		this.putAll(m);
	}

	public Reference2ShortArrayMap(Object[] key, short[] value, int size) {
		this.key = key;
		this.value = value;
		this.size = size;
		if (key.length != value.length) {
			throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
		} else if (size > key.length) {
			throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
		}
	}

	public FastEntrySet<K> reference2ShortEntrySet() {
		return new Reference2ShortArrayMap.EntrySet();
	}

	private int findKey(Object k) {
		Object[] key = this.key;
		int i = this.size;

		while (i-- != 0) {
			if (key[i] == k) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public short getShort(Object k) {
		Object[] key = this.key;
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
		int i = this.size;

		while (i-- != 0) {
			this.key[i] = null;
		}

		this.size = 0;
	}

	@Override
	public boolean containsKey(Object k) {
		return this.findKey(k) != -1;
	}

	@Override
	public boolean containsValue(short v) {
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
	public short put(K k, short v) {
		int oldKey = this.findKey(k);
		if (oldKey != -1) {
			short oldValue = this.value[oldKey];
			this.value[oldKey] = v;
			return oldValue;
		} else {
			if (this.size == this.key.length) {
				Object[] newKey = new Object[this.size == 0 ? 2 : this.size * 2];
				short[] newValue = new short[this.size == 0 ? 2 : this.size * 2];

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
	public short removeShort(Object k) {
		int oldPos = this.findKey(k);
		if (oldPos == -1) {
			return this.defRetValue;
		} else {
			short oldValue = this.value[oldPos];
			int tail = this.size - oldPos - 1;
			System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
			System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
			this.size--;
			this.key[this.size] = null;
			return oldValue;
		}
	}

	@Override
	public ReferenceSet<K> keySet() {
		return new AbstractReferenceSet<K>() {
			public boolean contains(Object k) {
				return Reference2ShortArrayMap.this.findKey(k) != -1;
			}

			public boolean remove(Object k) {
				int oldPos = Reference2ShortArrayMap.this.findKey(k);
				if (oldPos == -1) {
					return false;
				} else {
					int tail = Reference2ShortArrayMap.this.size - oldPos - 1;
					System.arraycopy(Reference2ShortArrayMap.this.key, oldPos + 1, Reference2ShortArrayMap.this.key, oldPos, tail);
					System.arraycopy(Reference2ShortArrayMap.this.value, oldPos + 1, Reference2ShortArrayMap.this.value, oldPos, tail);
					Reference2ShortArrayMap.this.size--;
					return true;
				}
			}

			@Override
			public ObjectIterator<K> iterator() {
				return new ObjectIterator<K>() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Reference2ShortArrayMap.this.size;
					}

					public K next() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return (K)Reference2ShortArrayMap.this.key[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Reference2ShortArrayMap.this.size - this.pos;
							System.arraycopy(Reference2ShortArrayMap.this.key, this.pos, Reference2ShortArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Reference2ShortArrayMap.this.value, this.pos, Reference2ShortArrayMap.this.value, this.pos - 1, tail);
							Reference2ShortArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Reference2ShortArrayMap.this.size;
			}

			public void clear() {
				Reference2ShortArrayMap.this.clear();
			}
		};
	}

	@Override
	public ShortCollection values() {
		return new AbstractShortCollection() {
			@Override
			public boolean contains(short v) {
				return Reference2ShortArrayMap.this.containsValue(v);
			}

			@Override
			public ShortIterator iterator() {
				return new ShortIterator() {
					int pos = 0;

					public boolean hasNext() {
						return this.pos < Reference2ShortArrayMap.this.size;
					}

					@Override
					public short nextShort() {
						if (!this.hasNext()) {
							throw new NoSuchElementException();
						} else {
							return Reference2ShortArrayMap.this.value[this.pos++];
						}
					}

					public void remove() {
						if (this.pos == 0) {
							throw new IllegalStateException();
						} else {
							int tail = Reference2ShortArrayMap.this.size - this.pos;
							System.arraycopy(Reference2ShortArrayMap.this.key, this.pos, Reference2ShortArrayMap.this.key, this.pos - 1, tail);
							System.arraycopy(Reference2ShortArrayMap.this.value, this.pos, Reference2ShortArrayMap.this.value, this.pos - 1, tail);
							Reference2ShortArrayMap.this.size--;
						}
					}
				};
			}

			public int size() {
				return Reference2ShortArrayMap.this.size;
			}

			public void clear() {
				Reference2ShortArrayMap.this.clear();
			}
		};
	}

	public Reference2ShortArrayMap<K> clone() {
		Reference2ShortArrayMap<K> c;
		try {
			c = (Reference2ShortArrayMap<K>)super.clone();
		} catch (CloneNotSupportedException var3) {
			throw new InternalError();
		}

		c.key = (Object[])this.key.clone();
		c.value = (short[])this.value.clone();
		return c;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int i = 0; i < this.size; i++) {
			s.writeObject(this.key[i]);
			s.writeShort(this.value[i]);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.key = new Object[this.size];
		this.value = new short[this.size];

		for (int i = 0; i < this.size; i++) {
			this.key[i] = s.readObject();
			this.value[i] = s.readShort();
		}
	}

	private final class EntrySet extends AbstractObjectSet<it.unimi.dsi.fastutil.objects.Reference2ShortMap.Entry<K>> implements FastEntrySet<K> {
		private EntrySet() {
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.objects.Reference2ShortMap.Entry<K>> iterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.objects.Reference2ShortMap.Entry<K>>() {
				int curr = -1;
				int next = 0;

				public boolean hasNext() {
					return this.next < Reference2ShortArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.objects.Reference2ShortMap.Entry<K> next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return new BasicEntry<>((K)Reference2ShortArrayMap.this.key[this.curr = this.next], Reference2ShortArrayMap.this.value[this.next++]);
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Reference2ShortArrayMap.this.size-- - this.next--;
						System.arraycopy(Reference2ShortArrayMap.this.key, this.next + 1, Reference2ShortArrayMap.this.key, this.next, tail);
						System.arraycopy(Reference2ShortArrayMap.this.value, this.next + 1, Reference2ShortArrayMap.this.value, this.next, tail);
						Reference2ShortArrayMap.this.key[Reference2ShortArrayMap.this.size] = null;
					}
				}
			};
		}

		@Override
		public ObjectIterator<it.unimi.dsi.fastutil.objects.Reference2ShortMap.Entry<K>> fastIterator() {
			return new ObjectIterator<it.unimi.dsi.fastutil.objects.Reference2ShortMap.Entry<K>>() {
				int next = 0;
				int curr = -1;
				final BasicEntry<K> entry = new BasicEntry<>();

				public boolean hasNext() {
					return this.next < Reference2ShortArrayMap.this.size;
				}

				public it.unimi.dsi.fastutil.objects.Reference2ShortMap.Entry<K> next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						this.entry.key = (K)Reference2ShortArrayMap.this.key[this.curr = this.next];
						this.entry.value = Reference2ShortArrayMap.this.value[this.next++];
						return this.entry;
					}
				}

				public void remove() {
					if (this.curr == -1) {
						throw new IllegalStateException();
					} else {
						this.curr = -1;
						int tail = Reference2ShortArrayMap.this.size-- - this.next--;
						System.arraycopy(Reference2ShortArrayMap.this.key, this.next + 1, Reference2ShortArrayMap.this.key, this.next, tail);
						System.arraycopy(Reference2ShortArrayMap.this.value, this.next + 1, Reference2ShortArrayMap.this.value, this.next, tail);
						Reference2ShortArrayMap.this.key[Reference2ShortArrayMap.this.size] = null;
					}
				}
			};
		}

		public int size() {
			return Reference2ShortArrayMap.this.size;
		}

		public boolean contains(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
				if (e.getValue() != null && e.getValue() instanceof Short) {
					K k = (K)e.getKey();
					return Reference2ShortArrayMap.this.containsKey(k) && Reference2ShortArrayMap.this.getShort(k) == (Short)e.getValue();
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
				if (e.getValue() != null && e.getValue() instanceof Short) {
					K k = (K)e.getKey();
					short v = (Short)e.getValue();
					int oldPos = Reference2ShortArrayMap.this.findKey(k);
					if (oldPos != -1 && v == Reference2ShortArrayMap.this.value[oldPos]) {
						int tail = Reference2ShortArrayMap.this.size - oldPos - 1;
						System.arraycopy(Reference2ShortArrayMap.this.key, oldPos + 1, Reference2ShortArrayMap.this.key, oldPos, tail);
						System.arraycopy(Reference2ShortArrayMap.this.value, oldPos + 1, Reference2ShortArrayMap.this.value, oldPos, tail);
						Reference2ShortArrayMap.this.size--;
						Reference2ShortArrayMap.this.key[Reference2ShortArrayMap.this.size] = null;
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
