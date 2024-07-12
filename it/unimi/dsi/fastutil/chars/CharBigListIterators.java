package it.unimi.dsi.fastutil.chars;

import java.io.Serializable;
import java.util.NoSuchElementException;

public final class CharBigListIterators {
	public static final CharBigListIterators.EmptyBigListIterator EMPTY_BIG_LIST_ITERATOR = new CharBigListIterators.EmptyBigListIterator();

	private CharBigListIterators() {
	}

	public static CharBigListIterator singleton(char element) {
		return new CharBigListIterators.SingletonBigListIterator(element);
	}

	public static CharBigListIterator unmodifiable(CharBigListIterator i) {
		return new CharBigListIterators.UnmodifiableBigListIterator(i);
	}

	public static CharBigListIterator asBigListIterator(CharListIterator i) {
		return new CharBigListIterators.BigListIteratorListIterator(i);
	}

	public static class BigListIteratorListIterator implements CharBigListIterator {
		protected final CharListIterator i;

		protected BigListIteratorListIterator(CharListIterator i) {
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
		public void set(char ok) {
			this.i.set(ok);
		}

		@Override
		public void add(char ok) {
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
		public char nextChar() {
			return this.i.nextChar();
		}

		@Override
		public char previousChar() {
			return this.i.previousChar();
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

	public static class EmptyBigListIterator implements CharBigListIterator, Serializable, Cloneable {
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
		public char nextChar() {
			throw new NoSuchElementException();
		}

		@Override
		public char previousChar() {
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
			return CharBigListIterators.EMPTY_BIG_LIST_ITERATOR;
		}

		private Object readResolve() {
			return CharBigListIterators.EMPTY_BIG_LIST_ITERATOR;
		}
	}

	private static class SingletonBigListIterator implements CharBigListIterator {
		private final char element;
		private int curr;

		public SingletonBigListIterator(char element) {
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
		public char nextChar() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.curr = 1;
				return this.element;
			}
		}

		@Override
		public char previousChar() {
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

	public static class UnmodifiableBigListIterator implements CharBigListIterator {
		protected final CharBigListIterator i;

		public UnmodifiableBigListIterator(CharBigListIterator i) {
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
		public char nextChar() {
			return this.i.nextChar();
		}

		@Override
		public char previousChar() {
			return this.i.previousChar();
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
