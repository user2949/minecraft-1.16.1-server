package it.unimi.dsi.fastutil.objects;

import java.io.Serializable;
import java.util.NoSuchElementException;

public final class ObjectBigListIterators {
	public static final ObjectBigListIterators.EmptyBigListIterator EMPTY_BIG_LIST_ITERATOR = new ObjectBigListIterators.EmptyBigListIterator();

	private ObjectBigListIterators() {
	}

	public static <K> ObjectBigListIterator<K> singleton(K element) {
		return new ObjectBigListIterators.SingletonBigListIterator<>(element);
	}

	public static <K> ObjectBigListIterator<K> unmodifiable(ObjectBigListIterator<K> i) {
		return new ObjectBigListIterators.UnmodifiableBigListIterator<>(i);
	}

	public static <K> ObjectBigListIterator<K> asBigListIterator(ObjectListIterator<K> i) {
		return new ObjectBigListIterators.BigListIteratorListIterator<>(i);
	}

	public static class BigListIteratorListIterator<K> implements ObjectBigListIterator<K> {
		protected final ObjectListIterator<K> i;

		protected BigListIteratorListIterator(ObjectListIterator<K> i) {
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
		public void set(K ok) {
			this.i.set(ok);
		}

		@Override
		public void add(K ok) {
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

		public K next() {
			return (K)this.i.next();
		}

		@Override
		public K previous() {
			return this.i.previous();
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

	public static class EmptyBigListIterator<K> implements ObjectBigListIterator<K>, Serializable, Cloneable {
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

		public K next() {
			throw new NoSuchElementException();
		}

		@Override
		public K previous() {
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
			return ObjectBigListIterators.EMPTY_BIG_LIST_ITERATOR;
		}

		private Object readResolve() {
			return ObjectBigListIterators.EMPTY_BIG_LIST_ITERATOR;
		}
	}

	private static class SingletonBigListIterator<K> implements ObjectBigListIterator<K> {
		private final K element;
		private int curr;

		public SingletonBigListIterator(K element) {
			this.element = element;
		}

		public boolean hasNext() {
			return this.curr == 0;
		}

		@Override
		public boolean hasPrevious() {
			return this.curr == 1;
		}

		public K next() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.curr = 1;
				return this.element;
			}
		}

		@Override
		public K previous() {
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

	public static class UnmodifiableBigListIterator<K> implements ObjectBigListIterator<K> {
		protected final ObjectBigListIterator<K> i;

		public UnmodifiableBigListIterator(ObjectBigListIterator<K> i) {
			this.i = i;
		}

		public boolean hasNext() {
			return this.i.hasNext();
		}

		@Override
		public boolean hasPrevious() {
			return this.i.hasPrevious();
		}

		public K next() {
			return (K)this.i.next();
		}

		@Override
		public K previous() {
			return this.i.previous();
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
