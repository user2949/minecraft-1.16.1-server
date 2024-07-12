package it.unimi.dsi.fastutil.booleans;

import java.io.Serializable;
import java.util.NoSuchElementException;

public final class BooleanBigListIterators {
	public static final BooleanBigListIterators.EmptyBigListIterator EMPTY_BIG_LIST_ITERATOR = new BooleanBigListIterators.EmptyBigListIterator();

	private BooleanBigListIterators() {
	}

	public static BooleanBigListIterator singleton(boolean element) {
		return new BooleanBigListIterators.SingletonBigListIterator(element);
	}

	public static BooleanBigListIterator unmodifiable(BooleanBigListIterator i) {
		return new BooleanBigListIterators.UnmodifiableBigListIterator(i);
	}

	public static BooleanBigListIterator asBigListIterator(BooleanListIterator i) {
		return new BooleanBigListIterators.BigListIteratorListIterator(i);
	}

	public static class BigListIteratorListIterator implements BooleanBigListIterator {
		protected final BooleanListIterator i;

		protected BigListIteratorListIterator(BooleanListIterator i) {
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
		public void set(boolean ok) {
			this.i.set(ok);
		}

		@Override
		public void add(boolean ok) {
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
		public boolean nextBoolean() {
			return this.i.nextBoolean();
		}

		@Override
		public boolean previousBoolean() {
			return this.i.previousBoolean();
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

	public static class EmptyBigListIterator implements BooleanBigListIterator, Serializable, Cloneable {
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
		public boolean nextBoolean() {
			throw new NoSuchElementException();
		}

		@Override
		public boolean previousBoolean() {
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
			return BooleanBigListIterators.EMPTY_BIG_LIST_ITERATOR;
		}

		private Object readResolve() {
			return BooleanBigListIterators.EMPTY_BIG_LIST_ITERATOR;
		}
	}

	private static class SingletonBigListIterator implements BooleanBigListIterator {
		private final boolean element;
		private int curr;

		public SingletonBigListIterator(boolean element) {
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
		public boolean nextBoolean() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.curr = 1;
				return this.element;
			}
		}

		@Override
		public boolean previousBoolean() {
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

	public static class UnmodifiableBigListIterator implements BooleanBigListIterator {
		protected final BooleanBigListIterator i;

		public UnmodifiableBigListIterator(BooleanBigListIterator i) {
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
		public boolean nextBoolean() {
			return this.i.nextBoolean();
		}

		@Override
		public boolean previousBoolean() {
			return this.i.previousBoolean();
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
