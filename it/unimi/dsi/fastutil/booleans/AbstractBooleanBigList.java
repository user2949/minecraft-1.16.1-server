package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class AbstractBooleanBigList extends AbstractBooleanCollection implements BooleanBigList, BooleanStack {
	protected AbstractBooleanBigList() {
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
	public void add(long index, boolean k) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(boolean k) {
		this.add(this.size64(), k);
		return true;
	}

	@Override
	public boolean removeBoolean(long i) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean set(long index, boolean k) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(long index, Collection<? extends Boolean> c) {
		this.ensureIndex(index);
		Iterator<? extends Boolean> i = c.iterator();
		boolean retVal = i.hasNext();

		while (i.hasNext()) {
			this.add(index++, (Boolean)i.next());
		}

		return retVal;
	}

	public boolean addAll(Collection<? extends Boolean> c) {
		return this.addAll(this.size64(), c);
	}

	@Override
	public BooleanBigListIterator iterator() {
		return this.listIterator();
	}

	@Override
	public BooleanBigListIterator listIterator() {
		return this.listIterator(0L);
	}

	@Override
	public BooleanBigListIterator listIterator(long index) {
		this.ensureIndex(index);
		return new BooleanBigListIterator() {
			long pos = index;
			long last = -1L;

			public boolean hasNext() {
				return this.pos < AbstractBooleanBigList.this.size64();
			}

			@Override
			public boolean hasPrevious() {
				return this.pos > 0L;
			}

			@Override
			public boolean nextBoolean() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				} else {
					return AbstractBooleanBigList.this.getBoolean(this.last = this.pos++);
				}
			}

			@Override
			public boolean previousBoolean() {
				if (!this.hasPrevious()) {
					throw new NoSuchElementException();
				} else {
					return AbstractBooleanBigList.this.getBoolean(this.last = --this.pos);
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
			public void add(boolean k) {
				AbstractBooleanBigList.this.add(this.pos++, k);
				this.last = -1L;
			}

			@Override
			public void set(boolean k) {
				if (this.last == -1L) {
					throw new IllegalStateException();
				} else {
					AbstractBooleanBigList.this.set(this.last, k);
				}
			}

			public void remove() {
				if (this.last == -1L) {
					throw new IllegalStateException();
				} else {
					AbstractBooleanBigList.this.removeBoolean(this.last);
					if (this.last < this.pos) {
						this.pos--;
					}

					this.last = -1L;
				}
			}
		};
	}

	@Override
	public boolean contains(boolean k) {
		return this.indexOf(k) >= 0L;
	}

	@Override
	public long indexOf(boolean k) {
		BooleanBigListIterator i = this.listIterator();

		while (i.hasNext()) {
			boolean e = i.nextBoolean();
			if (k == e) {
				return i.previousIndex();
			}
		}

		return -1L;
	}

	@Override
	public long lastIndexOf(boolean k) {
		BooleanBigListIterator i = this.listIterator(this.size64());

		while (i.hasPrevious()) {
			boolean e = i.previousBoolean();
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
				this.add(false);
			}
		} else {
			while (i-- != size) {
				this.remove(i);
			}
		}
	}

	@Override
	public BooleanBigList subList(long from, long to) {
		this.ensureIndex(from);
		this.ensureIndex(to);
		if (from > to) {
			throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
		} else {
			return new AbstractBooleanBigList.BooleanSubList(this, from, to);
		}
	}

	@Override
	public void removeElements(long from, long to) {
		this.ensureIndex(to);
		BooleanBigListIterator i = this.listIterator(from);
		long n = to - from;
		if (n < 0L) {
			throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
		} else {
			while (n-- != 0L) {
				i.nextBoolean();
				i.remove();
			}
		}
	}

	@Override
	public void addElements(long index, boolean[][] a, long offset, long length) {
		this.ensureIndex(index);
		BooleanBigArrays.ensureOffsetLength(a, offset, length);

		while (length-- != 0L) {
			this.add(index++, BooleanBigArrays.get(a, offset++));
		}
	}

	@Override
	public void addElements(long index, boolean[][] a) {
		this.addElements(index, a, 0L, BooleanBigArrays.length(a));
	}

	@Override
	public void getElements(long from, boolean[][] a, long offset, long length) {
		BooleanBigListIterator i = this.listIterator(from);
		BooleanBigArrays.ensureOffsetLength(a, offset, length);
		if (from + length > this.size64()) {
			throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + this.size64() + ")");
		} else {
			while (length-- != 0L) {
				BooleanBigArrays.set(a, offset++, i.nextBoolean());
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
		BooleanIterator i = this.iterator();
		int h = 1;
		long s = this.size64();

		while (s-- != 0L) {
			boolean k = i.nextBoolean();
			h = 31 * h + (k ? 1231 : 1237);
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
			} else if (l instanceof BooleanBigList) {
				BooleanBigListIterator i1 = this.listIterator();
				BooleanBigListIterator i2 = ((BooleanBigList)l).listIterator();

				while (s-- != 0L) {
					if (i1.nextBoolean() != i2.nextBoolean()) {
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

	public int compareTo(BigList<? extends Boolean> l) {
		if (l == this) {
			return 0;
		} else if (l instanceof BooleanBigList) {
			BooleanBigListIterator i1 = this.listIterator();
			BooleanBigListIterator i2 = ((BooleanBigList)l).listIterator();

			while (i1.hasNext() && i2.hasNext()) {
				boolean e1 = i1.nextBoolean();
				boolean e2 = i2.nextBoolean();
				int r;
				if ((r = Boolean.compare(e1, e2)) != 0) {
					return r;
				}
			}

			return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
		} else {
			BigListIterator<? extends Boolean> i1 = this.listIterator();
			BigListIterator<? extends Boolean> i2 = l.listIterator();

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
	public void push(boolean o) {
		this.add(o);
	}

	@Override
	public boolean popBoolean() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return this.removeBoolean(this.size64() - 1L);
		}
	}

	@Override
	public boolean topBoolean() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return this.getBoolean(this.size64() - 1L);
		}
	}

	@Override
	public boolean peekBoolean(int i) {
		return this.getBoolean(this.size64() - 1L - (long)i);
	}

	@Override
	public boolean rem(boolean k) {
		long index = this.indexOf(k);
		if (index == -1L) {
			return false;
		} else {
			this.removeBoolean(index);
			return true;
		}
	}

	@Override
	public boolean addAll(long index, BooleanCollection c) {
		return this.addAll(index, c);
	}

	@Override
	public boolean addAll(long index, BooleanBigList l) {
		return this.addAll(index, l);
	}

	@Override
	public boolean addAll(BooleanCollection c) {
		return this.addAll(this.size64(), c);
	}

	@Override
	public boolean addAll(BooleanBigList l) {
		return this.addAll(this.size64(), l);
	}

	@Deprecated
	@Override
	public void add(long index, Boolean ok) {
		this.add(index, ok.booleanValue());
	}

	@Deprecated
	@Override
	public Boolean set(long index, Boolean ok) {
		return this.set(index, ok.booleanValue());
	}

	@Deprecated
	@Override
	public Boolean get(long index) {
		return this.getBoolean(index);
	}

	@Deprecated
	@Override
	public long indexOf(Object ok) {
		return this.indexOf(((Boolean)ok).booleanValue());
	}

	@Deprecated
	@Override
	public long lastIndexOf(Object ok) {
		return this.lastIndexOf(((Boolean)ok).booleanValue());
	}

	@Deprecated
	@Override
	public Boolean remove(long index) {
		return this.removeBoolean(index);
	}

	@Deprecated
	@Override
	public void push(Boolean o) {
		this.push(o.booleanValue());
	}

	@Deprecated
	@Override
	public Boolean pop() {
		return this.popBoolean();
	}

	@Deprecated
	@Override
	public Boolean top() {
		return this.topBoolean();
	}

	@Deprecated
	@Override
	public Boolean peek(int i) {
		return this.peekBoolean(i);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		BooleanIterator i = this.iterator();
		long n = this.size64();
		boolean first = true;
		s.append("[");

		while (n-- != 0L) {
			if (first) {
				first = false;
			} else {
				s.append(", ");
			}

			boolean k = i.nextBoolean();
			s.append(String.valueOf(k));
		}

		s.append("]");
		return s.toString();
	}

	public static class BooleanSubList extends AbstractBooleanBigList implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final BooleanBigList l;
		protected final long from;
		protected long to;

		public BooleanSubList(BooleanBigList l, long from, long to) {
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
		public boolean add(boolean k) {
			this.l.add(this.to, k);
			this.to++;

			assert this.assertRange();

			return true;
		}

		@Override
		public void add(long index, boolean k) {
			this.ensureIndex(index);
			this.l.add(this.from + index, k);
			this.to++;

			assert this.assertRange();
		}

		@Override
		public boolean addAll(long index, Collection<? extends Boolean> c) {
			this.ensureIndex(index);
			this.to = this.to + (long)c.size();
			return this.l.addAll(this.from + index, c);
		}

		@Override
		public boolean getBoolean(long index) {
			this.ensureRestrictedIndex(index);
			return this.l.getBoolean(this.from + index);
		}

		@Override
		public boolean removeBoolean(long index) {
			this.ensureRestrictedIndex(index);
			this.to--;
			return this.l.removeBoolean(this.from + index);
		}

		@Override
		public boolean set(long index, boolean k) {
			this.ensureRestrictedIndex(index);
			return this.l.set(this.from + index, k);
		}

		@Override
		public long size64() {
			return this.to - this.from;
		}

		@Override
		public void getElements(long from, boolean[][] a, long offset, long length) {
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
		public void addElements(long index, boolean[][] a, long offset, long length) {
			this.ensureIndex(index);
			this.l.addElements(this.from + index, a, offset, length);
			this.to += length;

			assert this.assertRange();
		}

		@Override
		public BooleanBigListIterator listIterator(long index) {
			this.ensureIndex(index);
			return new BooleanBigListIterator() {
				long pos = index;
				long last = -1L;

				public boolean hasNext() {
					return this.pos < BooleanSubList.this.size64();
				}

				@Override
				public boolean hasPrevious() {
					return this.pos > 0L;
				}

				@Override
				public boolean nextBoolean() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return BooleanSubList.this.l.getBoolean(BooleanSubList.this.from + (this.last = this.pos++));
					}
				}

				@Override
				public boolean previousBoolean() {
					if (!this.hasPrevious()) {
						throw new NoSuchElementException();
					} else {
						return BooleanSubList.this.l.getBoolean(BooleanSubList.this.from + (this.last = --this.pos));
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
				public void add(boolean k) {
					if (this.last == -1L) {
						throw new IllegalStateException();
					} else {
						BooleanSubList.this.add(this.pos++, k);
						this.last = -1L;

						assert BooleanSubList.this.assertRange();
					}
				}

				@Override
				public void set(boolean k) {
					if (this.last == -1L) {
						throw new IllegalStateException();
					} else {
						BooleanSubList.this.set(this.last, k);
					}
				}

				public void remove() {
					if (this.last == -1L) {
						throw new IllegalStateException();
					} else {
						BooleanSubList.this.removeBoolean(this.last);
						if (this.last < this.pos) {
							this.pos--;
						}

						this.last = -1L;

						assert BooleanSubList.this.assertRange();
					}
				}
			};
		}

		@Override
		public BooleanBigList subList(long from, long to) {
			this.ensureIndex(from);
			this.ensureIndex(to);
			if (from > to) {
				throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
			} else {
				return new AbstractBooleanBigList.BooleanSubList(this, from, to);
			}
		}

		@Override
		public boolean rem(boolean k) {
			long index = this.indexOf(k);
			if (index == -1L) {
				return false;
			} else {
				this.to--;
				this.l.removeBoolean(this.from + index);

				assert this.assertRange();

				return true;
			}
		}

		@Override
		public boolean addAll(long index, BooleanCollection c) {
			this.ensureIndex(index);
			return super.addAll(index, c);
		}

		@Override
		public boolean addAll(long index, BooleanBigList l) {
			this.ensureIndex(index);
			return super.addAll(index, l);
		}
	}
}
