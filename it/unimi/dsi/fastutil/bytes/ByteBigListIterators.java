package it.unimi.dsi.fastutil.bytes;

import java.io.Serializable;
import java.util.NoSuchElementException;

public final class ByteBigListIterators {
	public static final ByteBigListIterators.EmptyBigListIterator EMPTY_BIG_LIST_ITERATOR = new ByteBigListIterators.EmptyBigListIterator();

	private ByteBigListIterators() {
	}

	public static ByteBigListIterator singleton(byte element) {
		return new ByteBigListIterators.SingletonBigListIterator(element);
	}

	public static ByteBigListIterator unmodifiable(ByteBigListIterator i) {
		return new ByteBigListIterators.UnmodifiableBigListIterator(i);
	}

	public static ByteBigListIterator asBigListIterator(ByteListIterator i) {
		return new ByteBigListIterators.BigListIteratorListIterator(i);
	}

	public static class BigListIteratorListIterator implements ByteBigListIterator {
		protected final ByteListIterator i;

		protected BigListIteratorListIterator(ByteListIterator i) {
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
		public void set(byte ok) {
			this.i.set(ok);
		}

		@Override
		public void add(byte ok) {
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
		public byte nextByte() {
			return this.i.nextByte();
		}

		@Override
		public byte previousByte() {
			return this.i.previousByte();
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

	public static class EmptyBigListIterator implements ByteBigListIterator, Serializable, Cloneable {
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
		public byte nextByte() {
			throw new NoSuchElementException();
		}

		@Override
		public byte previousByte() {
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
			return ByteBigListIterators.EMPTY_BIG_LIST_ITERATOR;
		}

		private Object readResolve() {
			return ByteBigListIterators.EMPTY_BIG_LIST_ITERATOR;
		}
	}

	private static class SingletonBigListIterator implements ByteBigListIterator {
		private final byte element;
		private int curr;

		public SingletonBigListIterator(byte element) {
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
		public byte nextByte() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.curr = 1;
				return this.element;
			}
		}

		@Override
		public byte previousByte() {
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

	public static class UnmodifiableBigListIterator implements ByteBigListIterator {
		protected final ByteBigListIterator i;

		public UnmodifiableBigListIterator(ByteBigListIterator i) {
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
		public byte nextByte() {
			return this.i.nextByte();
		}

		@Override
		public byte previousByte() {
			return this.i.previousByte();
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
