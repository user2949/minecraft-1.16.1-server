package it.unimi.dsi.fastutil.shorts;

import java.io.Serializable;
import java.util.NoSuchElementException;

public final class ShortBigListIterators {
	public static final ShortBigListIterators.EmptyBigListIterator EMPTY_BIG_LIST_ITERATOR = new ShortBigListIterators.EmptyBigListIterator();

	private ShortBigListIterators() {
	}

	public static ShortBigListIterator singleton(short element) {
		return new ShortBigListIterators.SingletonBigListIterator(element);
	}

	public static ShortBigListIterator unmodifiable(ShortBigListIterator i) {
		return new ShortBigListIterators.UnmodifiableBigListIterator(i);
	}

	public static ShortBigListIterator asBigListIterator(ShortListIterator i) {
		return new ShortBigListIterators.BigListIteratorListIterator(i);
	}

	public static class BigListIteratorListIterator implements ShortBigListIterator {
		protected final ShortListIterator i;

		protected BigListIteratorListIterator(ShortListIterator i) {
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
		public void set(short ok) {
			this.i.set(ok);
		}

		@Override
		public void add(short ok) {
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
		public short nextShort() {
			return this.i.nextShort();
		}

		@Override
		public short previousShort() {
			return this.i.previousShort();
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

	public static class EmptyBigListIterator implements ShortBigListIterator, Serializable, Cloneable {
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
		public short nextShort() {
			throw new NoSuchElementException();
		}

		@Override
		public short previousShort() {
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
			return ShortBigListIterators.EMPTY_BIG_LIST_ITERATOR;
		}

		private Object readResolve() {
			return ShortBigListIterators.EMPTY_BIG_LIST_ITERATOR;
		}
	}

	private static class SingletonBigListIterator implements ShortBigListIterator {
		private final short element;
		private int curr;

		public SingletonBigListIterator(short element) {
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
		public short nextShort() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.curr = 1;
				return this.element;
			}
		}

		@Override
		public short previousShort() {
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

	public static class UnmodifiableBigListIterator implements ShortBigListIterator {
		protected final ShortBigListIterator i;

		public UnmodifiableBigListIterator(ShortBigListIterator i) {
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
		public short nextShort() {
			return this.i.nextShort();
		}

		@Override
		public short previousShort() {
			return this.i.previousShort();
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
