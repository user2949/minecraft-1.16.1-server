package it.unimi.dsi.fastutil.floats;

import java.io.Serializable;
import java.util.NoSuchElementException;

public final class FloatBigListIterators {
	public static final FloatBigListIterators.EmptyBigListIterator EMPTY_BIG_LIST_ITERATOR = new FloatBigListIterators.EmptyBigListIterator();

	private FloatBigListIterators() {
	}

	public static FloatBigListIterator singleton(float element) {
		return new FloatBigListIterators.SingletonBigListIterator(element);
	}

	public static FloatBigListIterator unmodifiable(FloatBigListIterator i) {
		return new FloatBigListIterators.UnmodifiableBigListIterator(i);
	}

	public static FloatBigListIterator asBigListIterator(FloatListIterator i) {
		return new FloatBigListIterators.BigListIteratorListIterator(i);
	}

	public static class BigListIteratorListIterator implements FloatBigListIterator {
		protected final FloatListIterator i;

		protected BigListIteratorListIterator(FloatListIterator i) {
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
		public void set(float ok) {
			this.i.set(ok);
		}

		@Override
		public void add(float ok) {
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
		public float nextFloat() {
			return this.i.nextFloat();
		}

		@Override
		public float previousFloat() {
			return this.i.previousFloat();
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

	public static class EmptyBigListIterator implements FloatBigListIterator, Serializable, Cloneable {
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
		public float nextFloat() {
			throw new NoSuchElementException();
		}

		@Override
		public float previousFloat() {
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
			return FloatBigListIterators.EMPTY_BIG_LIST_ITERATOR;
		}

		private Object readResolve() {
			return FloatBigListIterators.EMPTY_BIG_LIST_ITERATOR;
		}
	}

	private static class SingletonBigListIterator implements FloatBigListIterator {
		private final float element;
		private int curr;

		public SingletonBigListIterator(float element) {
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
		public float nextFloat() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.curr = 1;
				return this.element;
			}
		}

		@Override
		public float previousFloat() {
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

	public static class UnmodifiableBigListIterator implements FloatBigListIterator {
		protected final FloatBigListIterator i;

		public UnmodifiableBigListIterator(FloatBigListIterator i) {
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
		public float nextFloat() {
			return this.i.nextFloat();
		}

		@Override
		public float previousFloat() {
			return this.i.previousFloat();
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
