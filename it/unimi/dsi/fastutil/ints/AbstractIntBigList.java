package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class AbstractIntBigList extends AbstractIntCollection implements IntBigList, IntStack {
	protected AbstractIntBigList() {
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
	public void add(long index, int k) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(int k) {
		this.add(this.size64(), k);
		return true;
	}

	@Override
	public int removeInt(long i) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int set(long index, int k) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(long index, Collection<? extends Integer> c) {
		this.ensureIndex(index);
		Iterator<? extends Integer> i = c.iterator();
		boolean retVal = i.hasNext();

		while (i.hasNext()) {
			this.add(index++, (Integer)i.next());
		}

		return retVal;
	}

	public boolean addAll(Collection<? extends Integer> c) {
		return this.addAll(this.size64(), c);
	}

	@Override
	public IntBigListIterator iterator() {
		return this.listIterator();
	}

	@Override
	public IntBigListIterator listIterator() {
		return this.listIterator(0L);
	}

	@Override
	public IntBigListIterator listIterator(long index) {
		this.ensureIndex(index);
		return new IntBigListIterator() {
			long pos = index;
			long last = -1L;

			public boolean hasNext() {
				return this.pos < AbstractIntBigList.this.size64();
			}

			@Override
			public boolean hasPrevious() {
				return this.pos > 0L;
			}

			@Override
			public int nextInt() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				} else {
					return AbstractIntBigList.this.getInt(this.last = this.pos++);
				}
			}

			@Override
			public int previousInt() {
				if (!this.hasPrevious()) {
					throw new NoSuchElementException();
				} else {
					return AbstractIntBigList.this.getInt(this.last = --this.pos);
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
			public void add(int k) {
				AbstractIntBigList.this.add(this.pos++, k);
				this.last = -1L;
			}

			@Override
			public void set(int k) {
				if (this.last == -1L) {
					throw new IllegalStateException();
				} else {
					AbstractIntBigList.this.set(this.last, k);
				}
			}

			public void remove() {
				if (this.last == -1L) {
					throw new IllegalStateException();
				} else {
					AbstractIntBigList.this.removeInt(this.last);
					if (this.last < this.pos) {
						this.pos--;
					}

					this.last = -1L;
				}
			}
		};
	}

	@Override
	public boolean contains(int k) {
		return this.indexOf(k) >= 0L;
	}

	@Override
	public long indexOf(int k) {
		IntBigListIterator i = this.listIterator();

		while (i.hasNext()) {
			int e = i.nextInt();
			if (k == e) {
				return i.previousIndex();
			}
		}

		return -1L;
	}

	@Override
	public long lastIndexOf(int k) {
		IntBigListIterator i = this.listIterator(this.size64());

		while (i.hasPrevious()) {
			int e = i.previousInt();
			if (k == e) {
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
				this.add(0);
			}
		} else {
			while (i-- != size) {
				this.remove(i);
			}
		}
	}

	@Override
	public IntBigList subList(long from, long to) {
		this.ensureIndex(from);
		this.ensureIndex(to);
		if (from > to) {
			throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
		} else {
			return new AbstractIntBigList.IntSubList(this, from, to);
		}
	}

	@Override
	public void removeElements(long from, long to) {
		this.ensureIndex(to);
		IntBigListIterator i = this.listIterator(from);
		long n = to - from;
		if (n < 0L) {
			throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
		} else {
			while (n-- != 0L) {
				i.nextInt();
				i.remove();
			}
		}
	}

	@Override
	public void addElements(long index, int[][] a, long offset, long length) {
		this.ensureIndex(index);
		IntBigArrays.ensureOffsetLength(a, offset, length);

		while (length-- != 0L) {
			this.add(index++, IntBigArrays.get(a, offset++));
		}
	}

	@Override
	public void addElements(long index, int[][] a) {
		this.addElements(index, a, 0L, IntBigArrays.length(a));
	}

	@Override
	public void getElements(long from, int[][] a, long offset, long length) {
		IntBigListIterator i = this.listIterator(from);
		IntBigArrays.ensureOffsetLength(a, offset, length);
		if (from + length > this.size64()) {
			throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + this.size64() + ")");
		} else {
			while (length-- != 0L) {
				IntBigArrays.set(a, offset++, i.nextInt());
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
		IntIterator i = this.iterator();
		int h = 1;
		long s = this.size64();

		while (s-- != 0L) {
			int k = i.nextInt();
			h = 31 * h + k;
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
			} else if (l instanceof IntBigList) {
				IntBigListIterator i1 = this.listIterator();
				IntBigListIterator i2 = ((IntBigList)l).listIterator();

				while (s-- != 0L) {
					if (i1.nextInt() != i2.nextInt()) {
						return false;
					}
				}

				return true;
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

	public int compareTo(BigList<? extends Integer> l) {
		if (l == this) {
			return 0;
		} else if (l instanceof IntBigList) {
			IntBigListIterator i1 = this.listIterator();
			IntBigListIterator i2 = ((IntBigList)l).listIterator();

			while (i1.hasNext() && i2.hasNext()) {
				int e1 = i1.nextInt();
				int e2 = i2.nextInt();
				int r;
				if ((r = Integer.compare(e1, e2)) != 0) {
					return r;
				}
			}

			return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
		} else {
			BigListIterator<? extends Integer> i1 = this.listIterator();
			BigListIterator<? extends Integer> i2 = l.listIterator();

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
	public void push(int o) {
		this.add(o);
	}

	@Override
	public int popInt() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return this.removeInt(this.size64() - 1L);
		}
	}

	@Override
	public int topInt() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return this.getInt(this.size64() - 1L);
		}
	}

	@Override
	public int peekInt(int i) {
		return this.getInt(this.size64() - 1L - (long)i);
	}

	@Override
	public boolean rem(int k) {
		long index = this.indexOf(k);
		if (index == -1L) {
			return false;
		} else {
			this.removeInt(index);
			return true;
		}
	}

	@Override
	public boolean addAll(long index, IntCollection c) {
		return this.addAll(index, c);
	}

	@Override
	public boolean addAll(long index, IntBigList l) {
		return this.addAll(index, l);
	}

	@Override
	public boolean addAll(IntCollection c) {
		return this.addAll(this.size64(), c);
	}

	@Override
	public boolean addAll(IntBigList l) {
		return this.addAll(this.size64(), l);
	}

	@Deprecated
	@Override
	public void add(long index, Integer ok) {
		this.add(index, ok.intValue());
	}

	@Deprecated
	@Override
	public Integer set(long index, Integer ok) {
		return this.set(index, ok.intValue());
	}

	@Deprecated
	@Override
	public Integer get(long index) {
		return this.getInt(index);
	}

	@Deprecated
	@Override
	public long indexOf(Object ok) {
		return this.indexOf(((Integer)ok).intValue());
	}

	@Deprecated
	@Override
	public long lastIndexOf(Object ok) {
		return this.lastIndexOf(((Integer)ok).intValue());
	}

	@Deprecated
	@Override
	public Integer remove(long index) {
		return this.removeInt(index);
	}

	@Deprecated
	@Override
	public void push(Integer o) {
		this.push(o.intValue());
	}

	@Deprecated
	@Override
	public Integer pop() {
		return this.popInt();
	}

	@Deprecated
	@Override
	public Integer top() {
		return this.topInt();
	}

	@Deprecated
	@Override
	public Integer peek(int i) {
		return this.peekInt(i);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		IntIterator i = this.iterator();
		long n = this.size64();
		boolean first = true;
		s.append("[");

		while (n-- != 0L) {
			if (first) {
				first = false;
			} else {
				s.append(", ");
			}

			int k = i.nextInt();
			s.append(String.valueOf(k));
		}

		s.append("]");
		return s.toString();
	}

	public static class IntSubList extends AbstractIntBigList implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final IntBigList l;
		protected final long from;
		protected long to;

		public IntSubList(IntBigList l, long from, long to) {
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
		public boolean add(int k) {
			this.l.add(this.to, k);
			this.to++;

			assert this.assertRange();

			return true;
		}

		@Override
		public void add(long index, int k) {
			this.ensureIndex(index);
			this.l.add(this.from + index, k);
			this.to++;

			assert this.assertRange();
		}

		@Override
		public boolean addAll(long index, Collection<? extends Integer> c) {
			this.ensureIndex(index);
			this.to = this.to + (long)c.size();
			return this.l.addAll(this.from + index, c);
		}

		@Override
		public int getInt(long index) {
			this.ensureRestrictedIndex(index);
			return this.l.getInt(this.from + index);
		}

		@Override
		public int removeInt(long index) {
			this.ensureRestrictedIndex(index);
			this.to--;
			return this.l.removeInt(this.from + index);
		}

		@Override
		public int set(long index, int k) {
			this.ensureRestrictedIndex(index);
			return this.l.set(this.from + index, k);
		}

		@Override
		public long size64() {
			return this.to - this.from;
		}

		@Override
		public void getElements(long from, int[][] a, long offset, long length) {
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
		public void addElements(long index, int[][] a, long offset, long length) {
			this.ensureIndex(index);
			this.l.addElements(this.from + index, a, offset, length);
			this.to += length;

			assert this.assertRange();
		}

		@Override
		public IntBigListIterator listIterator(long index) {
			this.ensureIndex(index);
			return new IntBigListIterator() {
				long pos = index;
				long last = -1L;

				public boolean hasNext() {
					return this.pos < IntSubList.this.size64();
				}

				@Override
				public boolean hasPrevious() {
					return this.pos > 0L;
				}

				@Override
				public int nextInt() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return IntSubList.this.l.getInt(IntSubList.this.from + (this.last = this.pos++));
					}
				}

				@Override
				public int previousInt() {
					if (!this.hasPrevious()) {
						throw new NoSuchElementException();
					} else {
						return IntSubList.this.l.getInt(IntSubList.this.from + (this.last = --this.pos));
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
				public void add(int k) {
					if (this.last == -1L) {
						throw new IllegalStateException();
					} else {
						IntSubList.this.add(this.pos++, k);
						this.last = -1L;

						assert IntSubList.this.assertRange();
					}
				}

				@Override
				public void set(int k) {
					if (this.last == -1L) {
						throw new IllegalStateException();
					} else {
						IntSubList.this.set(this.last, k);
					}
				}

				public void remove() {
					if (this.last == -1L) {
						throw new IllegalStateException();
					} else {
						IntSubList.this.removeInt(this.last);
						if (this.last < this.pos) {
							this.pos--;
						}

						this.last = -1L;

						assert IntSubList.this.assertRange();
					}
				}
			};
		}

		@Override
		public IntBigList subList(long from, long to) {
			this.ensureIndex(from);
			this.ensureIndex(to);
			if (from > to) {
				throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
			} else {
				return new AbstractIntBigList.IntSubList(this, from, to);
			}
		}

		@Override
		public boolean rem(int k) {
			long index = this.indexOf(k);
			if (index == -1L) {
				return false;
			} else {
				this.to--;
				this.l.removeInt(this.from + index);

				assert this.assertRange();

				return true;
			}
		}

		@Override
		public boolean addAll(long index, IntCollection c) {
			this.ensureIndex(index);
			return super.addAll(index, c);
		}

		@Override
		public boolean addAll(long index, IntBigList l) {
			this.ensureIndex(index);
			return super.addAll(index, l);
		}
	}
}
