package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.HashCommon;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class AbstractFloatBigList extends AbstractFloatCollection implements FloatBigList, FloatStack {
	protected AbstractFloatBigList() {
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
	public void add(long index, float k) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(float k) {
		this.add(this.size64(), k);
		return true;
	}

	@Override
	public float removeFloat(long i) {
		throw new UnsupportedOperationException();
	}

	@Override
	public float set(long index, float k) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(long index, Collection<? extends Float> c) {
		this.ensureIndex(index);
		Iterator<? extends Float> i = c.iterator();
		boolean retVal = i.hasNext();

		while (i.hasNext()) {
			this.add(index++, (Float)i.next());
		}

		return retVal;
	}

	public boolean addAll(Collection<? extends Float> c) {
		return this.addAll(this.size64(), c);
	}

	@Override
	public FloatBigListIterator iterator() {
		return this.listIterator();
	}

	@Override
	public FloatBigListIterator listIterator() {
		return this.listIterator(0L);
	}

	@Override
	public FloatBigListIterator listIterator(long index) {
		this.ensureIndex(index);
		return new FloatBigListIterator() {
			long pos = index;
			long last = -1L;

			public boolean hasNext() {
				return this.pos < AbstractFloatBigList.this.size64();
			}

			@Override
			public boolean hasPrevious() {
				return this.pos > 0L;
			}

			@Override
			public float nextFloat() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				} else {
					return AbstractFloatBigList.this.getFloat(this.last = this.pos++);
				}
			}

			@Override
			public float previousFloat() {
				if (!this.hasPrevious()) {
					throw new NoSuchElementException();
				} else {
					return AbstractFloatBigList.this.getFloat(this.last = --this.pos);
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
			public void add(float k) {
				AbstractFloatBigList.this.add(this.pos++, k);
				this.last = -1L;
			}

			@Override
			public void set(float k) {
				if (this.last == -1L) {
					throw new IllegalStateException();
				} else {
					AbstractFloatBigList.this.set(this.last, k);
				}
			}

			public void remove() {
				if (this.last == -1L) {
					throw new IllegalStateException();
				} else {
					AbstractFloatBigList.this.removeFloat(this.last);
					if (this.last < this.pos) {
						this.pos--;
					}

					this.last = -1L;
				}
			}
		};
	}

	@Override
	public boolean contains(float k) {
		return this.indexOf(k) >= 0L;
	}

	@Override
	public long indexOf(float k) {
		FloatBigListIterator i = this.listIterator();

		while (i.hasNext()) {
			float e = i.nextFloat();
			if (Float.floatToIntBits(k) == Float.floatToIntBits(e)) {
				return i.previousIndex();
			}
		}

		return -1L;
	}

	@Override
	public long lastIndexOf(float k) {
		FloatBigListIterator i = this.listIterator(this.size64());

		while (i.hasPrevious()) {
			float e = i.previousFloat();
			if (Float.floatToIntBits(k) == Float.floatToIntBits(e)) {
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
				this.add(0.0F);
			}
		} else {
			while (i-- != size) {
				this.remove(i);
			}
		}
	}

	@Override
	public FloatBigList subList(long from, long to) {
		this.ensureIndex(from);
		this.ensureIndex(to);
		if (from > to) {
			throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
		} else {
			return new AbstractFloatBigList.FloatSubList(this, from, to);
		}
	}

	@Override
	public void removeElements(long from, long to) {
		this.ensureIndex(to);
		FloatBigListIterator i = this.listIterator(from);
		long n = to - from;
		if (n < 0L) {
			throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
		} else {
			while (n-- != 0L) {
				i.nextFloat();
				i.remove();
			}
		}
	}

	@Override
	public void addElements(long index, float[][] a, long offset, long length) {
		this.ensureIndex(index);
		FloatBigArrays.ensureOffsetLength(a, offset, length);

		while (length-- != 0L) {
			this.add(index++, FloatBigArrays.get(a, offset++));
		}
	}

	@Override
	public void addElements(long index, float[][] a) {
		this.addElements(index, a, 0L, FloatBigArrays.length(a));
	}

	@Override
	public void getElements(long from, float[][] a, long offset, long length) {
		FloatBigListIterator i = this.listIterator(from);
		FloatBigArrays.ensureOffsetLength(a, offset, length);
		if (from + length > this.size64()) {
			throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + this.size64() + ")");
		} else {
			while (length-- != 0L) {
				FloatBigArrays.set(a, offset++, i.nextFloat());
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
		FloatIterator i = this.iterator();
		int h = 1;
		long s = this.size64();

		while (s-- != 0L) {
			float k = i.nextFloat();
			h = 31 * h + HashCommon.float2int(k);
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
			} else if (l instanceof FloatBigList) {
				FloatBigListIterator i1 = this.listIterator();
				FloatBigListIterator i2 = ((FloatBigList)l).listIterator();

				while (s-- != 0L) {
					if (i1.nextFloat() != i2.nextFloat()) {
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

	public int compareTo(BigList<? extends Float> l) {
		if (l == this) {
			return 0;
		} else if (l instanceof FloatBigList) {
			FloatBigListIterator i1 = this.listIterator();
			FloatBigListIterator i2 = ((FloatBigList)l).listIterator();

			while (i1.hasNext() && i2.hasNext()) {
				float e1 = i1.nextFloat();
				float e2 = i2.nextFloat();
				int r;
				if ((r = Float.compare(e1, e2)) != 0) {
					return r;
				}
			}

			return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
		} else {
			BigListIterator<? extends Float> i1 = this.listIterator();
			BigListIterator<? extends Float> i2 = l.listIterator();

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
	public void push(float o) {
		this.add(o);
	}

	@Override
	public float popFloat() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return this.removeFloat(this.size64() - 1L);
		}
	}

	@Override
	public float topFloat() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return this.getFloat(this.size64() - 1L);
		}
	}

	@Override
	public float peekFloat(int i) {
		return this.getFloat(this.size64() - 1L - (long)i);
	}

	@Override
	public boolean rem(float k) {
		long index = this.indexOf(k);
		if (index == -1L) {
			return false;
		} else {
			this.removeFloat(index);
			return true;
		}
	}

	@Override
	public boolean addAll(long index, FloatCollection c) {
		return this.addAll(index, c);
	}

	@Override
	public boolean addAll(long index, FloatBigList l) {
		return this.addAll(index, l);
	}

	@Override
	public boolean addAll(FloatCollection c) {
		return this.addAll(this.size64(), c);
	}

	@Override
	public boolean addAll(FloatBigList l) {
		return this.addAll(this.size64(), l);
	}

	@Deprecated
	@Override
	public void add(long index, Float ok) {
		this.add(index, ok.floatValue());
	}

	@Deprecated
	@Override
	public Float set(long index, Float ok) {
		return this.set(index, ok.floatValue());
	}

	@Deprecated
	@Override
	public Float get(long index) {
		return this.getFloat(index);
	}

	@Deprecated
	@Override
	public long indexOf(Object ok) {
		return this.indexOf(((Float)ok).floatValue());
	}

	@Deprecated
	@Override
	public long lastIndexOf(Object ok) {
		return this.lastIndexOf(((Float)ok).floatValue());
	}

	@Deprecated
	@Override
	public Float remove(long index) {
		return this.removeFloat(index);
	}

	@Deprecated
	@Override
	public void push(Float o) {
		this.push(o.floatValue());
	}

	@Deprecated
	@Override
	public Float pop() {
		return this.popFloat();
	}

	@Deprecated
	@Override
	public Float top() {
		return this.topFloat();
	}

	@Deprecated
	@Override
	public Float peek(int i) {
		return this.peekFloat(i);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		FloatIterator i = this.iterator();
		long n = this.size64();
		boolean first = true;
		s.append("[");

		while (n-- != 0L) {
			if (first) {
				first = false;
			} else {
				s.append(", ");
			}

			float k = i.nextFloat();
			s.append(String.valueOf(k));
		}

		s.append("]");
		return s.toString();
	}

	public static class FloatSubList extends AbstractFloatBigList implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final FloatBigList l;
		protected final long from;
		protected long to;

		public FloatSubList(FloatBigList l, long from, long to) {
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
		public boolean add(float k) {
			this.l.add(this.to, k);
			this.to++;

			assert this.assertRange();

			return true;
		}

		@Override
		public void add(long index, float k) {
			this.ensureIndex(index);
			this.l.add(this.from + index, k);
			this.to++;

			assert this.assertRange();
		}

		@Override
		public boolean addAll(long index, Collection<? extends Float> c) {
			this.ensureIndex(index);
			this.to = this.to + (long)c.size();
			return this.l.addAll(this.from + index, c);
		}

		@Override
		public float getFloat(long index) {
			this.ensureRestrictedIndex(index);
			return this.l.getFloat(this.from + index);
		}

		@Override
		public float removeFloat(long index) {
			this.ensureRestrictedIndex(index);
			this.to--;
			return this.l.removeFloat(this.from + index);
		}

		@Override
		public float set(long index, float k) {
			this.ensureRestrictedIndex(index);
			return this.l.set(this.from + index, k);
		}

		@Override
		public long size64() {
			return this.to - this.from;
		}

		@Override
		public void getElements(long from, float[][] a, long offset, long length) {
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
		public void addElements(long index, float[][] a, long offset, long length) {
			this.ensureIndex(index);
			this.l.addElements(this.from + index, a, offset, length);
			this.to += length;

			assert this.assertRange();
		}

		@Override
		public FloatBigListIterator listIterator(long index) {
			this.ensureIndex(index);
			return new FloatBigListIterator() {
				long pos = index;
				long last = -1L;

				public boolean hasNext() {
					return this.pos < FloatSubList.this.size64();
				}

				@Override
				public boolean hasPrevious() {
					return this.pos > 0L;
				}

				@Override
				public float nextFloat() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return FloatSubList.this.l.getFloat(FloatSubList.this.from + (this.last = this.pos++));
					}
				}

				@Override
				public float previousFloat() {
					if (!this.hasPrevious()) {
						throw new NoSuchElementException();
					} else {
						return FloatSubList.this.l.getFloat(FloatSubList.this.from + (this.last = --this.pos));
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
				public void add(float k) {
					if (this.last == -1L) {
						throw new IllegalStateException();
					} else {
						FloatSubList.this.add(this.pos++, k);
						this.last = -1L;

						assert FloatSubList.this.assertRange();
					}
				}

				@Override
				public void set(float k) {
					if (this.last == -1L) {
						throw new IllegalStateException();
					} else {
						FloatSubList.this.set(this.last, k);
					}
				}

				public void remove() {
					if (this.last == -1L) {
						throw new IllegalStateException();
					} else {
						FloatSubList.this.removeFloat(this.last);
						if (this.last < this.pos) {
							this.pos--;
						}

						this.last = -1L;

						assert FloatSubList.this.assertRange();
					}
				}
			};
		}

		@Override
		public FloatBigList subList(long from, long to) {
			this.ensureIndex(from);
			this.ensureIndex(to);
			if (from > to) {
				throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
			} else {
				return new AbstractFloatBigList.FloatSubList(this, from, to);
			}
		}

		@Override
		public boolean rem(float k) {
			long index = this.indexOf(k);
			if (index == -1L) {
				return false;
			} else {
				this.to--;
				this.l.removeFloat(this.from + index);

				assert this.assertRange();

				return true;
			}
		}

		@Override
		public boolean addAll(long index, FloatCollection c) {
			this.ensureIndex(index);
			return super.addAll(index, c);
		}

		@Override
		public boolean addAll(long index, FloatBigList l) {
			this.ensureIndex(index);
			return super.addAll(index, l);
		}
	}
}
