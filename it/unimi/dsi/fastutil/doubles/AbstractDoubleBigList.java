package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.HashCommon;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class AbstractDoubleBigList extends AbstractDoubleCollection implements DoubleBigList, DoubleStack {
	protected AbstractDoubleBigList() {
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
	public void add(long index, double k) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(double k) {
		this.add(this.size64(), k);
		return true;
	}

	@Override
	public double removeDouble(long i) {
		throw new UnsupportedOperationException();
	}

	@Override
	public double set(long index, double k) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(long index, Collection<? extends Double> c) {
		this.ensureIndex(index);
		Iterator<? extends Double> i = c.iterator();
		boolean retVal = i.hasNext();

		while (i.hasNext()) {
			this.add(index++, (Double)i.next());
		}

		return retVal;
	}

	public boolean addAll(Collection<? extends Double> c) {
		return this.addAll(this.size64(), c);
	}

	@Override
	public DoubleBigListIterator iterator() {
		return this.listIterator();
	}

	@Override
	public DoubleBigListIterator listIterator() {
		return this.listIterator(0L);
	}

	@Override
	public DoubleBigListIterator listIterator(long index) {
		this.ensureIndex(index);
		return new DoubleBigListIterator() {
			long pos = index;
			long last = -1L;

			public boolean hasNext() {
				return this.pos < AbstractDoubleBigList.this.size64();
			}

			@Override
			public boolean hasPrevious() {
				return this.pos > 0L;
			}

			@Override
			public double nextDouble() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				} else {
					return AbstractDoubleBigList.this.getDouble(this.last = this.pos++);
				}
			}

			@Override
			public double previousDouble() {
				if (!this.hasPrevious()) {
					throw new NoSuchElementException();
				} else {
					return AbstractDoubleBigList.this.getDouble(this.last = --this.pos);
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
			public void add(double k) {
				AbstractDoubleBigList.this.add(this.pos++, k);
				this.last = -1L;
			}

			@Override
			public void set(double k) {
				if (this.last == -1L) {
					throw new IllegalStateException();
				} else {
					AbstractDoubleBigList.this.set(this.last, k);
				}
			}

			public void remove() {
				if (this.last == -1L) {
					throw new IllegalStateException();
				} else {
					AbstractDoubleBigList.this.removeDouble(this.last);
					if (this.last < this.pos) {
						this.pos--;
					}

					this.last = -1L;
				}
			}
		};
	}

	@Override
	public boolean contains(double k) {
		return this.indexOf(k) >= 0L;
	}

	@Override
	public long indexOf(double k) {
		DoubleBigListIterator i = this.listIterator();

		while (i.hasNext()) {
			double e = i.nextDouble();
			if (Double.doubleToLongBits(k) == Double.doubleToLongBits(e)) {
				return i.previousIndex();
			}
		}

		return -1L;
	}

	@Override
	public long lastIndexOf(double k) {
		DoubleBigListIterator i = this.listIterator(this.size64());

		while (i.hasPrevious()) {
			double e = i.previousDouble();
			if (Double.doubleToLongBits(k) == Double.doubleToLongBits(e)) {
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
				this.add(0.0);
			}
		} else {
			while (i-- != size) {
				this.remove(i);
			}
		}
	}

	@Override
	public DoubleBigList subList(long from, long to) {
		this.ensureIndex(from);
		this.ensureIndex(to);
		if (from > to) {
			throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
		} else {
			return new AbstractDoubleBigList.DoubleSubList(this, from, to);
		}
	}

	@Override
	public void removeElements(long from, long to) {
		this.ensureIndex(to);
		DoubleBigListIterator i = this.listIterator(from);
		long n = to - from;
		if (n < 0L) {
			throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
		} else {
			while (n-- != 0L) {
				i.nextDouble();
				i.remove();
			}
		}
	}

	@Override
	public void addElements(long index, double[][] a, long offset, long length) {
		this.ensureIndex(index);
		DoubleBigArrays.ensureOffsetLength(a, offset, length);

		while (length-- != 0L) {
			this.add(index++, DoubleBigArrays.get(a, offset++));
		}
	}

	@Override
	public void addElements(long index, double[][] a) {
		this.addElements(index, a, 0L, DoubleBigArrays.length(a));
	}

	@Override
	public void getElements(long from, double[][] a, long offset, long length) {
		DoubleBigListIterator i = this.listIterator(from);
		DoubleBigArrays.ensureOffsetLength(a, offset, length);
		if (from + length > this.size64()) {
			throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + this.size64() + ")");
		} else {
			while (length-- != 0L) {
				DoubleBigArrays.set(a, offset++, i.nextDouble());
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
		DoubleIterator i = this.iterator();
		int h = 1;
		long s = this.size64();

		while (s-- != 0L) {
			double k = i.nextDouble();
			h = 31 * h + HashCommon.double2int(k);
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
			} else if (l instanceof DoubleBigList) {
				DoubleBigListIterator i1 = this.listIterator();
				DoubleBigListIterator i2 = ((DoubleBigList)l).listIterator();

				while (s-- != 0L) {
					if (i1.nextDouble() != i2.nextDouble()) {
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

	public int compareTo(BigList<? extends Double> l) {
		if (l == this) {
			return 0;
		} else if (l instanceof DoubleBigList) {
			DoubleBigListIterator i1 = this.listIterator();
			DoubleBigListIterator i2 = ((DoubleBigList)l).listIterator();

			while (i1.hasNext() && i2.hasNext()) {
				double e1 = i1.nextDouble();
				double e2 = i2.nextDouble();
				int r;
				if ((r = Double.compare(e1, e2)) != 0) {
					return r;
				}
			}

			return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
		} else {
			BigListIterator<? extends Double> i1 = this.listIterator();
			BigListIterator<? extends Double> i2 = l.listIterator();

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
	public void push(double o) {
		this.add(o);
	}

	@Override
	public double popDouble() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return this.removeDouble(this.size64() - 1L);
		}
	}

	@Override
	public double topDouble() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return this.getDouble(this.size64() - 1L);
		}
	}

	@Override
	public double peekDouble(int i) {
		return this.getDouble(this.size64() - 1L - (long)i);
	}

	@Override
	public boolean rem(double k) {
		long index = this.indexOf(k);
		if (index == -1L) {
			return false;
		} else {
			this.removeDouble(index);
			return true;
		}
	}

	@Override
	public boolean addAll(long index, DoubleCollection c) {
		return this.addAll(index, c);
	}

	@Override
	public boolean addAll(long index, DoubleBigList l) {
		return this.addAll(index, l);
	}

	@Override
	public boolean addAll(DoubleCollection c) {
		return this.addAll(this.size64(), c);
	}

	@Override
	public boolean addAll(DoubleBigList l) {
		return this.addAll(this.size64(), l);
	}

	@Deprecated
	@Override
	public void add(long index, Double ok) {
		this.add(index, ok.doubleValue());
	}

	@Deprecated
	@Override
	public Double set(long index, Double ok) {
		return this.set(index, ok.doubleValue());
	}

	@Deprecated
	@Override
	public Double get(long index) {
		return this.getDouble(index);
	}

	@Deprecated
	@Override
	public long indexOf(Object ok) {
		return this.indexOf(((Double)ok).doubleValue());
	}

	@Deprecated
	@Override
	public long lastIndexOf(Object ok) {
		return this.lastIndexOf(((Double)ok).doubleValue());
	}

	@Deprecated
	@Override
	public Double remove(long index) {
		return this.removeDouble(index);
	}

	@Deprecated
	@Override
	public void push(Double o) {
		this.push(o.doubleValue());
	}

	@Deprecated
	@Override
	public Double pop() {
		return this.popDouble();
	}

	@Deprecated
	@Override
	public Double top() {
		return this.topDouble();
	}

	@Deprecated
	@Override
	public Double peek(int i) {
		return this.peekDouble(i);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		DoubleIterator i = this.iterator();
		long n = this.size64();
		boolean first = true;
		s.append("[");

		while (n-- != 0L) {
			if (first) {
				first = false;
			} else {
				s.append(", ");
			}

			double k = i.nextDouble();
			s.append(String.valueOf(k));
		}

		s.append("]");
		return s.toString();
	}

	public static class DoubleSubList extends AbstractDoubleBigList implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final DoubleBigList l;
		protected final long from;
		protected long to;

		public DoubleSubList(DoubleBigList l, long from, long to) {
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
		public boolean add(double k) {
			this.l.add(this.to, k);
			this.to++;

			assert this.assertRange();

			return true;
		}

		@Override
		public void add(long index, double k) {
			this.ensureIndex(index);
			this.l.add(this.from + index, k);
			this.to++;

			assert this.assertRange();
		}

		@Override
		public boolean addAll(long index, Collection<? extends Double> c) {
			this.ensureIndex(index);
			this.to = this.to + (long)c.size();
			return this.l.addAll(this.from + index, c);
		}

		@Override
		public double getDouble(long index) {
			this.ensureRestrictedIndex(index);
			return this.l.getDouble(this.from + index);
		}

		@Override
		public double removeDouble(long index) {
			this.ensureRestrictedIndex(index);
			this.to--;
			return this.l.removeDouble(this.from + index);
		}

		@Override
		public double set(long index, double k) {
			this.ensureRestrictedIndex(index);
			return this.l.set(this.from + index, k);
		}

		@Override
		public long size64() {
			return this.to - this.from;
		}

		@Override
		public void getElements(long from, double[][] a, long offset, long length) {
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
		public void addElements(long index, double[][] a, long offset, long length) {
			this.ensureIndex(index);
			this.l.addElements(this.from + index, a, offset, length);
			this.to += length;

			assert this.assertRange();
		}

		@Override
		public DoubleBigListIterator listIterator(long index) {
			this.ensureIndex(index);
			return new DoubleBigListIterator() {
				long pos = index;
				long last = -1L;

				public boolean hasNext() {
					return this.pos < DoubleSubList.this.size64();
				}

				@Override
				public boolean hasPrevious() {
					return this.pos > 0L;
				}

				@Override
				public double nextDouble() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return DoubleSubList.this.l.getDouble(DoubleSubList.this.from + (this.last = this.pos++));
					}
				}

				@Override
				public double previousDouble() {
					if (!this.hasPrevious()) {
						throw new NoSuchElementException();
					} else {
						return DoubleSubList.this.l.getDouble(DoubleSubList.this.from + (this.last = --this.pos));
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
				public void add(double k) {
					if (this.last == -1L) {
						throw new IllegalStateException();
					} else {
						DoubleSubList.this.add(this.pos++, k);
						this.last = -1L;

						assert DoubleSubList.this.assertRange();
					}
				}

				@Override
				public void set(double k) {
					if (this.last == -1L) {
						throw new IllegalStateException();
					} else {
						DoubleSubList.this.set(this.last, k);
					}
				}

				public void remove() {
					if (this.last == -1L) {
						throw new IllegalStateException();
					} else {
						DoubleSubList.this.removeDouble(this.last);
						if (this.last < this.pos) {
							this.pos--;
						}

						this.last = -1L;

						assert DoubleSubList.this.assertRange();
					}
				}
			};
		}

		@Override
		public DoubleBigList subList(long from, long to) {
			this.ensureIndex(from);
			this.ensureIndex(to);
			if (from > to) {
				throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
			} else {
				return new AbstractDoubleBigList.DoubleSubList(this, from, to);
			}
		}

		@Override
		public boolean rem(double k) {
			long index = this.indexOf(k);
			if (index == -1L) {
				return false;
			} else {
				this.to--;
				this.l.removeDouble(this.from + index);

				assert this.assertRange();

				return true;
			}
		}

		@Override
		public boolean addAll(long index, DoubleCollection c) {
			this.ensureIndex(index);
			return super.addAll(index, c);
		}

		@Override
		public boolean addAll(long index, DoubleBigList l) {
			this.ensureIndex(index);
			return super.addAll(index, l);
		}
	}
}
