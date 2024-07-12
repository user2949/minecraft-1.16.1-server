package it.unimi.dsi.fastutil.longs;

import java.io.Serializable;
import java.util.NoSuchElementException;

public final class LongBigListIterators {
	public static final LongBigListIterators.EmptyBigListIterator EMPTY_BIG_LIST_ITERATOR = new LongBigListIterators.EmptyBigListIterator();

	private LongBigListIterators() {
	}

	public static LongBigListIterator singleton(long element) {
		return new LongBigListIterators.SingletonBigListIterator(element);
	}

	public static LongBigListIterator unmodifiable(LongBigListIterator i) {
		return new LongBigListIterators.UnmodifiableBigListIterator(i);
	}

	public static LongBigListIterator asBigListIterator(LongListIterator i) {
		return new LongBigListIterators.BigListIteratorListIterator(i);
	}

	public static class BigListIteratorListIterator implements LongBigListIterator {
		protected final LongListIterator i;

		protected BigListIteratorListIterator(LongListIterator i) {
			this.i = i;
		}

		private int intDisplacement(long n) {
			if (n >= -2147483648L && n <= 2147483647L) {
				return (int)n;
			} else {
				throw new IndexOutOfBoundsException("This big iterator is restricted to 32-bit displacements");
			}
		}

		@Override
		public void set(long ok) {
			this.i.set(ok);
		}

		@Override
		public void add(long ok) {
			this.i.add(ok);
		}

		@Override
		public int back(int n) {
			return this.i.back(n);
		}

		@Override
		public long back(long n) {
			return (long)this.i.back(this.intDisplacement(n));
		}

		public void remove() {
			this.i.remove();
		}

		@Override
		public int skip(int n) {
			return this.i.skip(n);
		}

		@Override
		public long skip(long n) {
			return (long)this.i.skip(this.intDisplacement(n));
		}

		public boolean hasNext() {
			return this.i.hasNext();
		}

		@Override
		public boolean hasPrevious() {
			return this.i.hasPrevious();
		}

		@Override
		public long nextLong() {
			return this.i.nextLong();
		}

		@Override
		public long previousLong() {
			return this.i.previousLong();
		}

		@Override
		public long nextIndex() {
			return (long)this.i.nextIndex();
		}

		@Override
		public long previousIndex() {
			return (long)this.i.previousIndex();
		}
	}

	public static class EmptyBigListIterator implements LongBigListIterator, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyBigListIterator() {
		}

		public boolean hasNext() {
			return false;
		}

		@Override
		public boolean hasPrevious() {
			return false;
		}

		@Override
		public long nextLong() {
			throw new NoSuchElementException();
		}

		@Override
		public long previousLong() {
			throw new NoSuchElementException();
		}

		@Override
		public long nextIndex() {
			return 0L;
		}

		@Override
		public long previousIndex() {
			return -1L;
		}

		@Override
		public long skip(long n) {
			return 0L;
		}

		@Override
		public long back(long n) {
			return 0L;
		}

		public Object clone() {
			return LongBigListIterators.EMPTY_BIG_LIST_ITERATOR;
		}

		private Object readResolve() {
			return LongBigListIterators.EMPTY_BIG_LIST_ITERATOR;
		}
	}

	private static class SingletonBigListIterator implements LongBigListIterator {
		private final long element;
		private int curr;

		public SingletonBigListIterator(long element) {
			this.element = element;
		}

		public boolean hasNext() {
			return this.curr == 0;
		}

		@Override
		public boolean hasPrevious() {
			return this.curr == 1;
		}

		@Override
		public long nextLong() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.curr = 1;
				return this.element;
			}
		}

		@Override
		public long previousLong() {
			if (!this.hasPrevious()) {
				throw new NoSuchElementException();
			} else {
				this.curr = 0;
				return this.element;
			}
		}

		@Override
		public long nextIndex() {
			return (long)this.curr;
		}

		@Override
		public long previousIndex() {
			return (long)(this.curr - 1);
		}
	}

	public static class UnmodifiableBigListIterator implements LongBigListIterator {
		protected final LongBigListIterator i;

		public UnmodifiableBigListIterator(LongBigListIterator i) {
			this.i = i;
		}

		public boolean hasNext() {
			return this.i.hasNext();
		}

		@Override
		public boolean hasPrevious() {
			return this.i.hasPrevious();
		}

		@Override
		public long nextLong() {
			return this.i.nextLong();
		}

		@Override
		public long previousLong() {
			return this.i.previousLong();
		}

		@Override
		public long nextIndex() {
			return this.i.nextIndex();
		}

		@Override
		public long previousIndex() {
			return this.i.previousIndex();
		}
	}
}
