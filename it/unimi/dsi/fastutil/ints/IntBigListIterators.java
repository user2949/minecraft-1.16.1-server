package it.unimi.dsi.fastutil.ints;

import java.io.Serializable;
import java.util.NoSuchElementException;

public final class IntBigListIterators {
	public static final IntBigListIterators.EmptyBigListIterator EMPTY_BIG_LIST_ITERATOR = new IntBigListIterators.EmptyBigListIterator();

	private IntBigListIterators() {
	}

	public static IntBigListIterator singleton(int element) {
		return new IntBigListIterators.SingletonBigListIterator(element);
	}

	public static IntBigListIterator unmodifiable(IntBigListIterator i) {
		return new IntBigListIterators.UnmodifiableBigListIterator(i);
	}

	public static IntBigListIterator asBigListIterator(IntListIterator i) {
		return new IntBigListIterators.BigListIteratorListIterator(i);
	}

	public static class BigListIteratorListIterator implements IntBigListIterator {
		protected final IntListIterator i;

		protected BigListIteratorListIterator(IntListIterator i) {
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
		public void set(int ok) {
			this.i.set(ok);
		}

		@Override
		public void add(int ok) {
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
		public int nextInt() {
			return this.i.nextInt();
		}

		@Override
		public int previousInt() {
			return this.i.previousInt();
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

	public static class EmptyBigListIterator implements IntBigListIterator, Serializable, Cloneable {
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
		public int nextInt() {
			throw new NoSuchElementException();
		}

		@Override
		public int previousInt() {
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
			return IntBigListIterators.EMPTY_BIG_LIST_ITERATOR;
		}

		private Object readResolve() {
			return IntBigListIterators.EMPTY_BIG_LIST_ITERATOR;
		}
	}

	private static class SingletonBigListIterator implements IntBigListIterator {
		private final int element;
		private int curr;

		public SingletonBigListIterator(int element) {
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
		public int nextInt() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.curr = 1;
				return this.element;
			}
		}

		@Override
		public int previousInt() {
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

	public static class UnmodifiableBigListIterator implements IntBigListIterator {
		protected final IntBigListIterator i;

		public UnmodifiableBigListIterator(IntBigListIterator i) {
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
		public int nextInt() {
			return this.i.nextInt();
		}

		@Override
		public int previousInt() {
			return this.i.previousInt();
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
