package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.bytes.ByteIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.IntPredicate;

public final class ShortIterators {
	public static final ShortIterators.EmptyIterator EMPTY_ITERATOR = new ShortIterators.EmptyIterator();

	private ShortIterators() {
	}

	public static ShortListIterator singleton(short element) {
		return new ShortIterators.SingletonIterator(element);
	}

	public static ShortListIterator wrap(short[] array, int offset, int length) {
		ShortArrays.ensureOffsetLength(array, offset, length);
		return new ShortIterators.ArrayIterator(array, offset, length);
	}

	public static ShortListIterator wrap(short[] array) {
		return new ShortIterators.ArrayIterator(array, 0, array.length);
	}

	public static int unwrap(ShortIterator i, short[] array, int offset, int max) {
		if (max < 0) {
			throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
		} else if (offset >= 0 && offset + max <= array.length) {
			int j = max;

			while (j-- != 0 && i.hasNext()) {
				array[offset++] = i.nextShort();
			}

			return max - j - 1;
		} else {
			throw new IllegalArgumentException();
		}
	}

	public static int unwrap(ShortIterator i, short[] array) {
		return unwrap(i, array, 0, array.length);
	}

	public static short[] unwrap(ShortIterator i, int max) {
		if (max < 0) {
			throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
		} else {
			short[] array = new short[16];
			int j = 0;

			while (max-- != 0 && i.hasNext()) {
				if (j == array.length) {
					array = ShortArrays.grow(array, j + 1);
				}

				array[j++] = i.nextShort();
			}

			return ShortArrays.trim(array, j);
		}
	}

	public static short[] unwrap(ShortIterator i) {
		return unwrap(i, Integer.MAX_VALUE);
	}

	public static int unwrap(ShortIterator i, ShortCollection c, int max) {
		if (max < 0) {
			throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
		} else {
			int j = max;

			while (j-- != 0 && i.hasNext()) {
				c.add(i.nextShort());
			}

			return max - j - 1;
		}
	}

	public static long unwrap(ShortIterator i, ShortCollection c) {
		long n;
		for (n = 0L; i.hasNext(); n++) {
			c.add(i.nextShort());
		}

		return n;
	}

	public static int pour(ShortIterator i, ShortCollection s, int max) {
		if (max < 0) {
			throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
		} else {
			int j = max;

			while (j-- != 0 && i.hasNext()) {
				s.add(i.nextShort());
			}

			return max - j - 1;
		}
	}

	public static int pour(ShortIterator i, ShortCollection s) {
		return pour(i, s, Integer.MAX_VALUE);
	}

	public static ShortList pour(ShortIterator i, int max) {
		ShortArrayList l = new ShortArrayList();
		pour(i, l, max);
		l.trim();
		return l;
	}

	public static ShortList pour(ShortIterator i) {
		return pour(i, Integer.MAX_VALUE);
	}

	public static ShortIterator asShortIterator(Iterator i) {
		return (ShortIterator)(i instanceof ShortIterator ? (ShortIterator)i : new ShortIterators.IteratorWrapper(i));
	}

	public static ShortListIterator asShortIterator(ListIterator i) {
		return (ShortListIterator)(i instanceof ShortListIterator ? (ShortListIterator)i : new ShortIterators.ListIteratorWrapper(i));
	}

	public static boolean any(ShortIterator iterator, IntPredicate predicate) {
		return indexOf(iterator, predicate) != -1;
	}

	public static boolean all(ShortIterator iterator, IntPredicate predicate) {
		Objects.requireNonNull(predicate);

		while (iterator.hasNext()) {
			if (!predicate.test(iterator.nextShort())) {
				return false;
			}
		}

		return true;
	}

	public static int indexOf(ShortIterator iterator, IntPredicate predicate) {
		for (int i = 0; iterator.hasNext(); i++) {
			if (predicate.test(iterator.nextShort())) {
				return i;
			}
		}

		return -1;
	}

	public static ShortListIterator fromTo(short from, short to) {
		return new ShortIterators.IntervalIterator(from, to);
	}

	public static ShortIterator concat(ShortIterator[] a) {
		return concat(a, 0, a.length);
	}

	public static ShortIterator concat(ShortIterator[] a, int offset, int length) {
		return new ShortIterators.IteratorConcatenator(a, offset, length);
	}

	public static ShortIterator unmodifiable(ShortIterator i) {
		return new ShortIterators.UnmodifiableIterator(i);
	}

	public static ShortBidirectionalIterator unmodifiable(ShortBidirectionalIterator i) {
		return new ShortIterators.UnmodifiableBidirectionalIterator(i);
	}

	public static ShortListIterator unmodifiable(ShortListIterator i) {
		return new ShortIterators.UnmodifiableListIterator(i);
	}

	public static ShortIterator wrap(ByteIterator iterator) {
		return new ShortIterators.ByteIteratorWrapper(iterator);
	}

	private static class ArrayIterator implements ShortListIterator {
		private final short[] array;
		private final int offset;
		private final int length;
		private int curr;

		public ArrayIterator(short[] array, int offset, int length) {
			this.array = array;
			this.offset = offset;
			this.length = length;
		}

		public boolean hasNext() {
			return this.curr < this.length;
		}

		@Override
		public boolean hasPrevious() {
			return this.curr > 0;
		}

		@Override
		public short nextShort() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				return this.array[this.offset + this.curr++];
			}
		}

		@Override
		public short previousShort() {
			if (!this.hasPrevious()) {
				throw new NoSuchElementException();
			} else {
				return this.array[this.offset + --this.curr];
			}
		}

		@Override
		public int skip(int n) {
			if (n <= this.length - this.curr) {
				this.curr += n;
				return n;
			} else {
				n = this.length - this.curr;
				this.curr = this.length;
				return n;
			}
		}

		@Override
		public int back(int n) {
			if (n <= this.curr) {
				this.curr -= n;
				return n;
			} else {
				n = this.curr;
				this.curr = 0;
				return n;
			}
		}

		public int nextIndex() {
			return this.curr;
		}

		public int previousIndex() {
			return this.curr - 1;
		}
	}

	protected static class ByteIteratorWrapper implements ShortIterator {
		final ByteIterator iterator;

		public ByteIteratorWrapper(ByteIterator iterator) {
			this.iterator = iterator;
		}

		public boolean hasNext() {
			return this.iterator.hasNext();
		}

		@Deprecated
		@Override
		public Short next() {
			return (short)this.iterator.nextByte();
		}

		@Override
		public short nextShort() {
			return (short)this.iterator.nextByte();
		}

		public void remove() {
			this.iterator.remove();
		}

		@Override
		public int skip(int n) {
			return this.iterator.skip(n);
		}
	}

	public static class EmptyIterator implements ShortListIterator, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyIterator() {
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

		public int nextIndex() {
			return 0;
		}

		public int previousIndex() {
			return -1;
		}

		@Override
		public int skip(int n) {
			return 0;
		}

		@Override
		public int back(int n) {
			return 0;
		}

		public Object clone() {
			return ShortIterators.EMPTY_ITERATOR;
		}

		private Object readResolve() {
			return ShortIterators.EMPTY_ITERATOR;
		}
	}

	private static class IntervalIterator implements ShortListIterator {
		private final short from;
		private final short to;
		short curr;

		public IntervalIterator(short from, short to) {
			this.from = this.curr = from;
			this.to = to;
		}

		public boolean hasNext() {
			return this.curr < this.to;
		}

		@Override
		public boolean hasPrevious() {
			return this.curr > this.from;
		}

		@Override
		public short nextShort() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				return this.curr++;
			}
		}

		@Override
		public short previousShort() {
			if (!this.hasPrevious()) {
				throw new NoSuchElementException();
			} else {
				return --this.curr;
			}
		}

		public int nextIndex() {
			return this.curr - this.from;
		}

		public int previousIndex() {
			return this.curr - this.from - 1;
		}

		@Override
		public int skip(int n) {
			if (this.curr + n <= this.to) {
				this.curr = (short)(this.curr + n);
				return n;
			} else {
				n = this.to - this.curr;
				this.curr = this.to;
				return n;
			}
		}

		@Override
		public int back(int n) {
			if (this.curr - n >= this.from) {
				this.curr = (short)(this.curr - n);
				return n;
			} else {
				n = this.curr - this.from;
				this.curr = this.from;
				return n;
			}
		}
	}

	private static class IteratorConcatenator implements ShortIterator {
		final ShortIterator[] a;
		int offset;
		int length;
		int lastOffset = -1;

		public IteratorConcatenator(ShortIterator[] a, int offset, int length) {
			this.a = a;
			this.offset = offset;
			this.length = length;
			this.advance();
		}

		private void advance() {
			while (this.length != 0 && !this.a[this.offset].hasNext()) {
				this.length--;
				this.offset++;
			}
		}

		public boolean hasNext() {
			return this.length > 0;
		}

		@Override
		public short nextShort() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				short next = this.a[this.lastOffset = this.offset].nextShort();
				this.advance();
				return next;
			}
		}

		public void remove() {
			if (this.lastOffset == -1) {
				throw new IllegalStateException();
			} else {
				this.a[this.lastOffset].remove();
			}
		}

		@Override
		public int skip(int n) {
			this.lastOffset = -1;

			int skipped;
			for (skipped = 0; skipped < n && this.length != 0; this.offset++) {
				skipped += this.a[this.offset].skip(n - skipped);
				if (this.a[this.offset].hasNext()) {
					break;
				}

				this.length--;
			}

			return skipped;
		}
	}

	private static class IteratorWrapper implements ShortIterator {
		final Iterator<Short> i;

		public IteratorWrapper(Iterator<Short> i) {
			this.i = i;
		}

		public boolean hasNext() {
			return this.i.hasNext();
		}

		public void remove() {
			this.i.remove();
		}

		@Override
		public short nextShort() {
			return (Short)this.i.next();
		}
	}

	private static class ListIteratorWrapper implements ShortListIterator {
		final ListIterator<Short> i;

		public ListIteratorWrapper(ListIterator<Short> i) {
			this.i = i;
		}

		public boolean hasNext() {
			return this.i.hasNext();
		}

		@Override
		public boolean hasPrevious() {
			return this.i.hasPrevious();
		}

		public int nextIndex() {
			return this.i.nextIndex();
		}

		public int previousIndex() {
			return this.i.previousIndex();
		}

		@Override
		public void set(short k) {
			this.i.set(k);
		}

		@Override
		public void add(short k) {
			this.i.add(k);
		}

		@Override
		public void remove() {
			this.i.remove();
		}

		@Override
		public short nextShort() {
			return (Short)this.i.next();
		}

		@Override
		public short previousShort() {
			return (Short)this.i.previous();
		}
	}

	private static class SingletonIterator implements ShortListIterator {
		private final short element;
		private int curr;

		public SingletonIterator(short element) {
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

		public int nextIndex() {
			return this.curr;
		}

		public int previousIndex() {
			return this.curr - 1;
		}
	}

	public static class UnmodifiableBidirectionalIterator implements ShortBidirectionalIterator {
		protected final ShortBidirectionalIterator i;

		public UnmodifiableBidirectionalIterator(ShortBidirectionalIterator i) {
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
	}

	public static class UnmodifiableIterator implements ShortIterator {
		protected final ShortIterator i;

		public UnmodifiableIterator(ShortIterator i) {
			this.i = i;
		}

		public boolean hasNext() {
			return this.i.hasNext();
		}

		@Override
		public short nextShort() {
			return this.i.nextShort();
		}
	}

	public static class UnmodifiableListIterator implements ShortListIterator {
		protected final ShortListIterator i;

		public UnmodifiableListIterator(ShortListIterator i) {
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

		public int nextIndex() {
			return this.i.nextIndex();
		}

		public int previousIndex() {
			return this.i.previousIndex();
		}
	}
}
