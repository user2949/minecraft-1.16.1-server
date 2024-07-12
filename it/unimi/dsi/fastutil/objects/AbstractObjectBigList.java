package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.Stack;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public abstract class AbstractObjectBigList<K> extends AbstractObjectCollection<K> implements ObjectBigList<K>, Stack<K> {
	protected AbstractObjectBigList() {
	}

	protected void ensureIndex(long index) {
		if (index < 0L) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
		} else if (index > this.size64()) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + this.size64() + ")");
		}
	}

	protected void ensureRestrictedIndex(long index) {
		if (index < 0L) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
		} else if (index >= this.size64()) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size64() + ")");
		}
	}

	@Override
	public void add(long index, K k) {
		throw new UnsupportedOperationException();
	}

	public boolean add(K k) {
		this.add(this.size64(), k);
		return true;
	}

	@Override
	public K remove(long i) {
		throw new UnsupportedOperationException();
	}

	@Override
	public K set(long index, K k) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(long index, Collection<? extends K> c) {
		this.ensureIndex(index);
		Iterator<? extends K> i = c.iterator();
		boolean retVal = i.hasNext();

		while (i.hasNext()) {
			this.add(index++, (K)i.next());
		}

		return retVal;
	}

	public boolean addAll(Collection<? extends K> c) {
		return this.addAll(this.size64(), c);
	}

	@Override
	public ObjectBigListIterator<K> iterator() {
		return this.listIterator();
	}

	@Override
	public ObjectBigListIterator<K> listIterator() {
		return this.listIterator(0L);
	}

	@Override
	public ObjectBigListIterator<K> listIterator(long index) {
		this.ensureIndex(index);
		return new ObjectBigListIterator<K>() {
			long pos = index;
			long last = -1L;

			public boolean hasNext() {
				return this.pos < AbstractObjectBigList.this.size64();
			}

			@Override
			public boolean hasPrevious() {
				return this.pos > 0L;
			}

			public K next() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				} else {
					return (K)AbstractObjectBigList.this.get(this.last = this.pos++);
				}
			}

			@Override
			public K previous() {
				if (!this.hasPrevious()) {
					throw new NoSuchElementException();
				} else {
					return (K)AbstractObjectBigList.this.get(this.last = --this.pos);
				}
			}

			@Override
			public long nextIndex() {
				return this.pos;
			}

			@Override
			public long previousIndex() {
				return this.pos - 1L;
			}

			@Override
			public void add(K k) {
				AbstractObjectBigList.this.add(this.pos++, k);
				this.last = -1L;
			}

			@Override
			public void set(K k) {
				if (this.last == -1L) {
					throw new IllegalStateException();
				} else {
					AbstractObjectBigList.this.set(this.last, k);
				}
			}

			public void remove() {
				if (this.last == -1L) {
					throw new IllegalStateException();
				} else {
					AbstractObjectBigList.this.remove(this.last);
					if (this.last < this.pos) {
						this.pos--;
					}

					this.last = -1L;
				}
			}
		};
	}

	public boolean contains(Object k) {
		return this.indexOf(k) >= 0L;
	}

	@Override
	public long indexOf(Object k) {
		ObjectBigListIterator<K> i = this.listIterator();

		while (i.hasNext()) {
			K e = (K)i.next();
			if (Objects.equals(k, e)) {
				return i.previousIndex();
			}
		}

		return -1L;
	}

	@Override
	public long lastIndexOf(Object k) {
		ObjectBigListIterator<K> i = this.listIterator(this.size64());

		while (i.hasPrevious()) {
			K e = i.previous();
			if (Objects.equals(k, e)) {
				return i.nextIndex();
			}
		}

		return -1L;
	}

	@Override
	public void size(long size) {
		long i = this.size64();
		if (size > i) {
			while (i++ < size) {
				this.add(null);
			}
		} else {
			while (i-- != size) {
				this.remove(i);
			}
		}
	}

	@Override
	public ObjectBigList<K> subList(long from, long to) {
		this.ensureIndex(from);
		this.ensureIndex(to);
		if (from > to) {
			throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
		} else {
			return new AbstractObjectBigList.ObjectSubList<>(this, from, to);
		}
	}

	@Override
	public void removeElements(long from, long to) {
		this.ensureIndex(to);
		ObjectBigListIterator<K> i = this.listIterator(from);
		long n = to - from;
		if (n < 0L) {
			throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
		} else {
			while (n-- != 0L) {
				i.next();
				i.remove();
			}
		}
	}

	@Override
	public void addElements(long index, K[][] a, long offset, long length) {
		this.ensureIndex(index);
		ObjectBigArrays.ensureOffsetLength(a, offset, length);

		while (length-- != 0L) {
			this.add(index++, ObjectBigArrays.get(a, offset++));
		}
	}

	@Override
	public void addElements(long index, K[][] a) {
		this.addElements(index, a, 0L, ObjectBigArrays.length(a));
	}

	@Override
	public void getElements(long from, Object[][] a, long offset, long length) {
		ObjectBigListIterator<K> i = this.listIterator(from);
		ObjectBigArrays.ensureOffsetLength(a, offset, length);
		if (from + length > this.size64()) {
			throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + this.size64() + ")");
		} else {
			while (length-- != 0L) {
				ObjectBigArrays.set(a, offset++, i.next());
			}
		}
	}

	public void clear() {
		this.removeElements(0L, this.size64());
	}

	@Deprecated
	@Override
	public int size() {
		return (int)Math.min(2147483647L, this.size64());
	}

	private boolean valEquals(Object a, Object b) {
		return a == null ? b == null : a.equals(b);
	}

	public int hashCode() {
		ObjectIterator<K> i = this.iterator();
		int h = 1;
		long s = this.size64();

		while (s-- != 0L) {
			K k = (K)i.next();
			h = 31 * h + (k == null ? 0 : k.hashCode());
		}

		return h;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof BigList)) {
			return false;
		} else {
			BigList<?> l = (BigList<?>)o;
			long s = this.size64();
			if (s != l.size64()) {
				return false;
			} else {
				BigListIterator<?> i1 = this.listIterator();
				BigListIterator<?> i2 = l.listIterator();

				while (s-- != 0L) {
					if (!this.valEquals(i1.next(), i2.next())) {
						return false;
					}
				}

				return true;
			}
		}
	}

	public int compareTo(BigList<? extends K> l) {
		if (l == this) {
			return 0;
		} else if (l instanceof ObjectBigList) {
			ObjectBigListIterator<K> i1 = this.listIterator();
			ObjectBigListIterator<K> i2 = (ObjectBigListIterator<K>)((ObjectBigList)l).listIterator();

			while (i1.hasNext() && i2.hasNext()) {
				K e1 = (K)i1.next();
				K e2 = (K)i2.next();
				int r;
				if ((r = ((Comparable)e1).compareTo(e2)) != 0) {
					return r;
				}
			}

			return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
		} else {
			BigListIterator<? extends K> i1 = this.listIterator();
			BigListIterator<? extends K> i2 = l.listIterator();

			while (i1.hasNext() && i2.hasNext()) {
				int r;
				if ((r = ((Comparable)i1.next()).compareTo(i2.next())) != 0) {
					return r;
				}
			}

			return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
		}
	}

	@Override
	public void push(K o) {
		this.add(o);
	}

	@Override
	public K pop() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return this.remove(this.size64() - 1L);
		}
	}

	@Override
	public K top() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return this.get(this.size64() - 1L);
		}
	}

	@Override
	public K peek(int i) {
		return this.get(this.size64() - 1L - (long)i);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<K> i = this.iterator();
		long n = this.size64();
		boolean first = true;
		s.append("[");

		while (n-- != 0L) {
			if (first) {
				first = false;
			} else {
				s.append(", ");
			}

			K k = (K)i.next();
			if (this == k) {
				s.append("(this big list)");
			} else {
				s.append(String.valueOf(k));
			}
		}

		s.append("]");
		return s.toString();
	}

	public static class ObjectSubList<K> extends AbstractObjectBigList<K> implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final ObjectBigList<K> l;
		protected final long from;
		protected long to;

		public ObjectSubList(ObjectBigList<K> l, long from, long to) {
			this.l = l;
			this.from = from;
			this.to = to;
		}

		private boolean assertRange() {
			assert this.from <= this.l.size64();

			assert this.to <= this.l.size64();

			assert this.to >= this.from;

			return true;
		}

		@Override
		public boolean add(K k) {
			this.l.add(this.to, k);
			this.to++;

			assert this.assertRange();

			return true;
		}

		@Override
		public void add(long index, K k) {
			this.ensureIndex(index);
			this.l.add(this.from + index, k);
			this.to++;

			assert this.assertRange();
		}

		@Override
		public boolean addAll(long index, Collection<? extends K> c) {
			this.ensureIndex(index);
			this.to = this.to + (long)c.size();
			return this.l.addAll(this.from + index, c);
		}

		@Override
		public K get(long index) {
			this.ensureRestrictedIndex(index);
			return this.l.get(this.from + index);
		}

		@Override
		public K remove(long index) {
			this.ensureRestrictedIndex(index);
			this.to--;
			return this.l.remove(this.from + index);
		}

		@Override
		public K set(long index, K k) {
			this.ensureRestrictedIndex(index);
			return this.l.set(this.from + index, k);
		}

		@Override
		public long size64() {
			return this.to - this.from;
		}

		@Override
		public void getElements(long from, Object[][] a, long offset, long length) {
			this.ensureIndex(from);
			if (from + length > this.size64()) {
				throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + this.size64() + ")");
			} else {
				this.l.getElements(this.from + from, a, offset, length);
			}
		}

		@Override
		public void removeElements(long from, long to) {
			this.ensureIndex(from);
			this.ensureIndex(to);
			this.l.removeElements(this.from + from, this.from + to);
			this.to -= to - from;

			assert this.assertRange();
		}

		@Override
		public void addElements(long index, K[][] a, long offset, long length) {
			this.ensureIndex(index);
			this.l.addElements(this.from + index, a, offset, length);
			this.to += length;

			assert this.assertRange();
		}

		@Override
		public ObjectBigListIterator<K> listIterator(long index) {
			this.ensureIndex(index);
			return new ObjectBigListIterator<K>() {
				long pos = index;
				long last = -1L;

				public boolean hasNext() {
					return this.pos < ObjectSubList.this.size64();
				}

				@Override
				public boolean hasPrevious() {
					return this.pos > 0L;
				}

				public K next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return ObjectSubList.this.l.get(ObjectSubList.this.from + (this.last = this.pos++));
					}
				}

				@Override
				public K previous() {
					if (!this.hasPrevious()) {
						throw new NoSuchElementException();
					} else {
						return ObjectSubList.this.l.get(ObjectSubList.this.from + (this.last = --this.pos));
					}
				}

				@Override
				public long nextIndex() {
					return this.pos;
				}

				@Override
				public long previousIndex() {
					return this.pos - 1L;
				}

				@Override
				public void add(K k) {
					if (this.last == -1L) {
						throw new IllegalStateException();
					} else {
						ObjectSubList.this.add(this.pos++, k);
						this.last = -1L;

						assert ObjectSubList.this.assertRange();
					}
				}

				@Override
				public void set(K k) {
					if (this.last == -1L) {
						throw new IllegalStateException();
					} else {
						ObjectSubList.this.set(this.last, k);
					}
				}

				public void remove() {
					if (this.last == -1L) {
						throw new IllegalStateException();
					} else {
						ObjectSubList.this.remove(this.last);
						if (this.last < this.pos) {
							this.pos--;
						}

						this.last = -1L;

						assert ObjectSubList.this.assertRange();
					}
				}
			};
		}

		@Override
		public ObjectBigList<K> subList(long from, long to) {
			this.ensureIndex(from);
			this.ensureIndex(to);
			if (from > to) {
				throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
			} else {
				return new AbstractObjectBigList.ObjectSubList<>(this, from, to);
			}
		}
	}
}
