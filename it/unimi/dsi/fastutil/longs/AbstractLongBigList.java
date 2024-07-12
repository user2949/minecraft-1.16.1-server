package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.HashCommon;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class AbstractLongBigList extends AbstractLongCollection implements LongBigList, LongStack {
	protected AbstractLongBigList() {
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
	public void add(long index, long k) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(long k) {
		this.add(this.size64(), k);
		return true;
	}

	@Override
	public long removeLong(long i) {
		throw new UnsupportedOperationException();
	}

	@Override
	public long set(long index, long k) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(long index, Collection<? extends Long> c) {
		this.ensureIndex(index);
		Iterator<? extends Long> i = c.iterator();
		boolean retVal = i.hasNext();

		while (i.hasNext()) {
			this.add(index++, (Long)i.next());
		}

		return retVal;
	}

	public boolean addAll(Collection<? extends Long> c) {
		return this.addAll(this.size64(), c);
	}

	@Override
	public LongBigListIterator iterator() {
		return this.listIterator();
	}

	@Override
	public LongBigListIterator listIterator() {
		return this.listIterator(0L);
	}

	@Override
	public LongBigListIterator listIterator(long index) {
		this.ensureIndex(index);
		return new LongBigListIterator() {
			long pos = index;
			long last = -1L;

			public boolean hasNext() {
				return this.pos < AbstractLongBigList.this.size64();
			}

			@Override
			public boolean hasPrevious() {
				return this.pos > 0L;
			}

			@Override
			public long nextLong() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				} else {
					return AbstractLongBigList.this.getLong(this.last = this.pos++);
				}
			}

			@Override
			public long previousLong() {
				if (!this.hasPrevious()) {
					throw new NoSuchElementException();
				} else {
					return AbstractLongBigList.this.getLong(this.last = --this.pos);
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
			public void add(long k) {
				AbstractLongBigList.this.add(this.pos++, k);
				this.last = -1L;
			}

			@Override
			public void set(long k) {
				if (this.last == -1L) {
					throw new IllegalStateException();
				} else {
					AbstractLongBigList.this.set(this.last, k);
				}
			}

			public void remove() {
				if (this.last == -1L) {
					throw new IllegalStateException();
				} else {
					AbstractLongBigList.this.removeLong(this.last);
					if (this.last < this.pos) {
						this.pos--;
					}

					this.last = -1L;
				}
			}
		};
	}

	@Override
	public boolean contains(long k) {
		return this.indexOf(k) >= 0L;
	}

	@Override
	public long indexOf(long k) {
		LongBigListIterator i = this.listIterator();

		while (i.hasNext()) {
			long e = i.nextLong();
			if (k == e) {
				return i.previousIndex();
			}
		}

		return -1L;
	}

	@Override
	public long lastIndexOf(long k) {
		LongBigListIterator i = this.listIterator(this.size64());

		while (i.hasPrevious()) {
			long e = i.previousLong();
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
				this.add(0L);
			}
		} else {
			while (i-- != size) {
				this.remove(i);
			}
		}
	}

	@Override
	public LongBigList subList(long from, long to) {
		this.ensureIndex(from);
		this.ensureIndex(to);
		if (from > to) {
			throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
		} else {
			return new AbstractLongBigList.LongSubList(this, from, to);
		}
	}

	@Override
	public void removeElements(long from, long to) {
		this.ensureIndex(to);
		LongBigListIterator i = this.listIterator(from);
		long n = to - from;
		if (n < 0L) {
			throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
		} else {
			while (n-- != 0L) {
				i.nextLong();
				i.remove();
			}
		}
	}

	@Override
	public void addElements(long index, long[][] a, long offset, long length) {
		this.ensureIndex(index);
		LongBigArrays.ensureOffsetLength(a, offset, length);

		while (length-- != 0L) {
			this.add(index++, LongBigArrays.get(a, offset++));
		}
	}

	@Override
	public void addElements(long index, long[][] a) {
		this.addElements(index, a, 0L, LongBigArrays.length(a));
	}

	@Override
	public void getElements(long from, long[][] a, long offset, long length) {
		LongBigListIterator i = this.listIterator(from);
		LongBigArrays.ensureOffsetLength(a, offset, length);
		if (from + length > this.size64()) {
			throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + this.size64() + ")");
		} else {
			while (length-- != 0L) {
				LongBigArrays.set(a, offset++, i.nextLong());
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
		LongIterator i = this.iterator();
		int h = 1;
		long s = this.size64();

		while (s-- != 0L) {
			long k = i.nextLong();
			h = 31 * h + HashCommon.long2int(k);
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
			} else if (l instanceof LongBigList) {
				LongBigListIterator i1 = this.listIterator();
				LongBigListIterator i2 = ((LongBigList)l).listIterator();

				while (s-- != 0L) {
					if (i1.nextLong() != i2.nextLong()) {
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

	public int compareTo(BigList<? extends Long> l) {
		if (l == this) {
			return 0;
		} else if (l instanceof LongBigList) {
			LongBigListIterator i1 = this.listIterator();
			LongBigListIterator i2 = ((LongBigList)l).listIterator();

			while (i1.hasNext() && i2.hasNext()) {
				long e1 = i1.nextLong();
				long e2 = i2.nextLong();
				int r;
				if ((r = Long.compare(e1, e2)) != 0) {
					return r;
				}
			}

			return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
		} else {
			BigListIterator<? extends Long> i1 = this.listIterator();
			BigListIterator<? extends Long> i2 = l.listIterator();

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
	public void push(long o) {
		this.add(o);
	}

	@Override
	public long popLong() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return this.removeLong(this.size64() - 1L);
		}
	}

	@Override
	public long topLong() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return this.getLong(this.size64() - 1L);
		}
	}

	@Override
	public long peekLong(int i) {
		return this.getLong(this.size64() - 1L - (long)i);
	}

	@Override
	public boolean rem(long k) {
		long index = this.indexOf(k);
		if (index == -1L) {
			return false;
		} else {
			this.removeLong(index);
			return true;
		}
	}

	@Override
	public boolean addAll(long index, LongCollection c) {
		return this.addAll(index, c);
	}

	@Override
	public boolean addAll(long index, LongBigList l) {
		return this.addAll(index, l);
	}

	@Override
	public boolean addAll(LongCollection c) {
		return this.addAll(this.size64(), c);
	}

	@Override
	public boolean addAll(LongBigList l) {
		return this.addAll(this.size64(), l);
	}

	@Deprecated
	@Override
	public void add(long index, Long ok) {
		this.add(index, ok.longValue());
	}

	@Deprecated
	@Override
	public Long set(long index, Long ok) {
		return this.set(index, ok.longValue());
	}

	@Deprecated
	@Override
	public Long get(long index) {
		return this.getLong(index);
	}

	@Deprecated
	@Override
	public long indexOf(Object ok) {
		return this.indexOf(((Long)ok).longValue());
	}

	@Deprecated
	@Override
	public long lastIndexOf(Object ok) {
		return this.lastIndexOf(((Long)ok).longValue());
	}

	@Deprecated
	@Override
	public Long remove(long index) {
		return this.removeLong(index);
	}

	@Deprecated
	@Override
	public void push(Long o) {
		this.push(o.longValue());
	}

	@Deprecated
	@Override
	public Long pop() {
		return this.popLong();
	}

	@Deprecated
	@Override
	public Long top() {
		return this.topLong();
	}

	@Deprecated
	@Override
	public Long peek(int i) {
		return this.peekLong(i);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		LongIterator i = this.iterator();
		long n = this.size64();
		boolean first = true;
		s.append("[");

		while (n-- != 0L) {
			if (first) {
				first = false;
			} else {
				s.append(", ");
			}

			long k = i.nextLong();
			s.append(String.valueOf(k));
		}

		s.append("]");
		return s.toString();
	}

	public static class LongSubList extends AbstractLongBigList implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final LongBigList l;
		protected final long from;
		protected long to;

		public LongSubList(LongBigList l, long from, long to) {
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
		public boolean add(long k) {
			this.l.add(this.to, k);
			this.to++;

			assert this.assertRange();

			return true;
		}

		@Override
		public void add(long index, long k) {
			this.ensureIndex(index);
			this.l.add(this.from + index, k);
			this.to++;

			assert this.assertRange();
		}

		@Override
		public boolean addAll(long index, Collection<? extends Long> c) {
			this.ensureIndex(index);
			this.to = this.to + (long)c.size();
			return this.l.addAll(this.from + index, c);
		}

		@Override
		public long getLong(long index) {
			this.ensureRestrictedIndex(index);
			return this.l.getLong(this.from + index);
		}

		@Override
		public long removeLong(long index) {
			this.ensureRestrictedIndex(index);
			this.to--;
			return this.l.removeLong(this.from + index);
		}

		@Override
		public long set(long index, long k) {
			this.ensureRestrictedIndex(index);
			return this.l.set(this.from + index, k);
		}

		@Override
		public long size64() {
			return this.to - this.from;
		}

		@Override
		public void getElements(long from, long[][] a, long offset, long length) {
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
		public void addElements(long index, long[][] a, long offset, long length) {
			this.ensureIndex(index);
			this.l.addElements(this.from + index, a, offset, length);
			this.to += length;

			assert this.assertRange();
		}

		@Override
		public LongBigListIterator listIterator(long index) {
			this.ensureIndex(index);
			return new LongBigListIterator() {
				long pos = index;
				long last = -1L;

				public boolean hasNext() {
					return this.pos < LongSubList.this.size64();
				}

				@Override
				public boolean hasPrevious() {
					return this.pos > 0L;
				}

				@Override
				public long nextLong() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return LongSubList.this.l.getLong(LongSubList.this.from + (this.last = this.pos++));
					}
				}

				@Override
				public long previousLong() {
					if (!this.hasPrevious()) {
						throw new NoSuchElementException();
					} else {
						return LongSubList.this.l.getLong(LongSubList.this.from + (this.last = --this.pos));
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
				public void add(long k) {
					if (this.last == -1L) {
						throw new IllegalStateException();
					} else {
						LongSubList.this.add(this.pos++, k);
						this.last = -1L;

						assert LongSubList.this.assertRange();
					}
				}

				@Override
				public void set(long k) {
					if (this.last == -1L) {
						throw new IllegalStateException();
					} else {
						LongSubList.this.set(this.last, k);
					}
				}

				public void remove() {
					if (this.last == -1L) {
						throw new IllegalStateException();
					} else {
						LongSubList.this.removeLong(this.last);
						if (this.last < this.pos) {
							this.pos--;
						}

						this.last = -1L;

						assert LongSubList.this.assertRange();
					}
				}
			};
		}

		@Override
		public LongBigList subList(long from, long to) {
			this.ensureIndex(from);
			this.ensureIndex(to);
			if (from > to) {
				throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
			} else {
				return new AbstractLongBigList.LongSubList(this, from, to);
			}
		}

		@Override
		public boolean rem(long k) {
			long index = this.indexOf(k);
			if (index == -1L) {
				return false;
			} else {
				this.to--;
				this.l.removeLong(this.from + index);

				assert this.assertRange();

				return true;
			}
		}

		@Override
		public boolean addAll(long index, LongCollection c) {
			this.ensureIndex(index);
			return super.addAll(index, c);
		}

		@Override
		public boolean addAll(long index, LongBigList l) {
			this.ensureIndex(index);
			return super.addAll(index, l);
		}
	}
}
