package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.HashCommon;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public abstract class AbstractLongList extends AbstractLongCollection implements LongList, LongStack {
	protected AbstractLongList() {
	}

	protected void ensureIndex(int index) {
		if (index < 0) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
		} else if (index > this.size()) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + this.size() + ")");
		}
	}

	protected void ensureRestrictedIndex(int index) {
		if (index < 0) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
		} else if (index >= this.size()) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size() + ")");
		}
	}

	@Override
	public void add(int index, long k) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(long k) {
		this.add(this.size(), k);
		return true;
	}

	@Override
	public long removeLong(int i) {
		throw new UnsupportedOperationException();
	}

	@Override
	public long set(int index, long k) {
		throw new UnsupportedOperationException();
	}

	public boolean addAll(int index, Collection<? extends Long> c) {
		this.ensureIndex(index);
		Iterator<? extends Long> i = c.iterator();
		boolean retVal = i.hasNext();

		while (i.hasNext()) {
			this.add(index++, (Long)i.next());
		}

		return retVal;
	}

	public boolean addAll(Collection<? extends Long> c) {
		return this.addAll(this.size(), c);
	}

	@Override
	public LongListIterator iterator() {
		return this.listIterator();
	}

	@Override
	public LongListIterator listIterator() {
		return this.listIterator(0);
	}

	@Override
	public LongListIterator listIterator(int index) {
		this.ensureIndex(index);
		return new LongListIterator() {
			int pos = index;
			int last = -1;

			public boolean hasNext() {
				return this.pos < AbstractLongList.this.size();
			}

			@Override
			public boolean hasPrevious() {
				return this.pos > 0;
			}

			@Override
			public long nextLong() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				} else {
					return AbstractLongList.this.getLong(this.last = this.pos++);
				}
			}

			@Override
			public long previousLong() {
				if (!this.hasPrevious()) {
					throw new NoSuchElementException();
				} else {
					return AbstractLongList.this.getLong(this.last = --this.pos);
				}
			}

			public int nextIndex() {
				return this.pos;
			}

			public int previousIndex() {
				return this.pos - 1;
			}

			@Override
			public void add(long k) {
				AbstractLongList.this.add(this.pos++, k);
				this.last = -1;
			}

			@Override
			public void set(long k) {
				if (this.last == -1) {
					throw new IllegalStateException();
				} else {
					AbstractLongList.this.set(this.last, k);
				}
			}

			@Override
			public void remove() {
				if (this.last == -1) {
					throw new IllegalStateException();
				} else {
					AbstractLongList.this.removeLong(this.last);
					if (this.last < this.pos) {
						this.pos--;
					}

					this.last = -1;
				}
			}
		};
	}

	@Override
	public boolean contains(long k) {
		return this.indexOf(k) >= 0;
	}

	@Override
	public int indexOf(long k) {
		LongListIterator i = this.listIterator();

		while (i.hasNext()) {
			long e = i.nextLong();
			if (k == e) {
				return i.previousIndex();
			}
		}

		return -1;
	}

	@Override
	public int lastIndexOf(long k) {
		LongListIterator i = this.listIterator(this.size());

		while (i.hasPrevious()) {
			long e = i.previousLong();
			if (k == e) {
				return i.nextIndex();
			}
		}

		return -1;
	}

	@Override
	public void size(int size) {
		int i = this.size();
		if (size > i) {
			while (i++ < size) {
				this.add(0L);
			}
		} else {
			while (i-- != size) {
				this.removeLong(i);
			}
		}
	}

	@Override
	public LongList subList(int from, int to) {
		this.ensureIndex(from);
		this.ensureIndex(to);
		if (from > to) {
			throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
		} else {
			return new AbstractLongList.LongSubList(this, from, to);
		}
	}

	@Override
	public void removeElements(int from, int to) {
		this.ensureIndex(to);
		LongListIterator i = this.listIterator(from);
		int n = to - from;
		if (n < 0) {
			throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
		} else {
			while (n-- != 0) {
				i.nextLong();
				i.remove();
			}
		}
	}

	@Override
	public void addElements(int index, long[] a, int offset, int length) {
		this.ensureIndex(index);
		if (offset < 0) {
			throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative");
		} else if (offset + length > a.length) {
			throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
		} else {
			while (length-- != 0) {
				this.add(index++, a[offset++]);
			}
		}
	}

	@Override
	public void addElements(int index, long[] a) {
		this.addElements(index, a, 0, a.length);
	}

	@Override
	public void getElements(int from, long[] a, int offset, int length) {
		LongListIterator i = this.listIterator(from);
		if (offset < 0) {
			throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative");
		} else if (offset + length > a.length) {
			throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
		} else if (from + length > this.size()) {
			throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + this.size() + ")");
		} else {
			while (length-- != 0) {
				a[offset++] = i.nextLong();
			}
		}
	}

	public void clear() {
		this.removeElements(0, this.size());
	}

	private boolean valEquals(Object a, Object b) {
		return a == null ? b == null : a.equals(b);
	}

	public int hashCode() {
		LongIterator i = this.iterator();
		int h = 1;
		int s = this.size();

		while (s-- != 0) {
			long k = i.nextLong();
			h = 31 * h + HashCommon.long2int(k);
		}

		return h;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof List)) {
			return false;
		} else {
			List<?> l = (List<?>)o;
			int s = this.size();
			if (s != l.size()) {
				return false;
			} else if (l instanceof LongList) {
				LongListIterator i1 = this.listIterator();
				LongListIterator i2 = ((LongList)l).listIterator();

				while (s-- != 0) {
					if (i1.nextLong() != i2.nextLong()) {
						return false;
					}
				}

				return true;
			} else {
				ListIterator<?> i1 = this.listIterator();
				ListIterator<?> i2 = l.listIterator();

				while (s-- != 0) {
					if (!this.valEquals(i1.next(), i2.next())) {
						return false;
					}
				}

				return true;
			}
		}
	}

	public int compareTo(List<? extends Long> l) {
		if (l == this) {
			return 0;
		} else if (l instanceof LongList) {
			LongListIterator i1 = this.listIterator();
			LongListIterator i2 = ((LongList)l).listIterator();

			while (i1.hasNext() && i2.hasNext()) {
				long e1 = i1.nextLong();
				long e2 = i2.nextLong();
				int r;
				if ((r = Long.compare(e1, e2)) != 0) {
					return r;
				}
			}

			return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
		} else {
			ListIterator<? extends Long> i1 = this.listIterator();
			ListIterator<? extends Long> i2 = l.listIterator();

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
	public void push(long o) {
		this.add(o);
	}

	@Override
	public long popLong() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return this.removeLong(this.size() - 1);
		}
	}

	@Override
	public long topLong() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return this.getLong(this.size() - 1);
		}
	}

	@Override
	public long peekLong(int i) {
		return this.getLong(this.size() - 1 - i);
	}

	@Override
	public boolean rem(long k) {
		int index = this.indexOf(k);
		if (index == -1) {
			return false;
		} else {
			this.removeLong(index);
			return true;
		}
	}

	@Override
	public boolean addAll(int index, LongCollection c) {
		this.ensureIndex(index);
		LongIterator i = c.iterator();
		boolean retVal = i.hasNext();

		while (i.hasNext()) {
			this.add(index++, i.nextLong());
		}

		return retVal;
	}

	@Override
	public boolean addAll(int index, LongList l) {
		return this.addAll(index, l);
	}

	@Override
	public boolean addAll(LongCollection c) {
		return this.addAll(this.size(), c);
	}

	@Override
	public boolean addAll(LongList l) {
		return this.addAll(this.size(), l);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		LongIterator i = this.iterator();
		int n = this.size();
		boolean first = true;
		s.append("[");

		while (n-- != 0) {
			if (first) {
				first = false;
			} else {
				s.append(", ");
			}

			long k = i.nextLong();
			s.append(String.valueOf(k));
		}

		s.append("]");
		return s.toString();
	}

	public static class LongSubList extends AbstractLongList implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final LongList l;
		protected final int from;
		protected int to;

		public LongSubList(LongList l, int from, int to) {
			this.l = l;
			this.from = from;
			this.to = to;
		}

		private boolean assertRange() {
			assert this.from <= this.l.size();

			assert this.to <= this.l.size();

			assert this.to >= this.from;

			return true;
		}

		@Override
		public boolean add(long k) {
			this.l.add(this.to, k);
			this.to++;

			assert this.assertRange();

			return true;
		}

		@Override
		public void add(int index, long k) {
			this.ensureIndex(index);
			this.l.add(this.from + index, k);
			this.to++;

			assert this.assertRange();
		}

		@Override
		public boolean addAll(int index, Collection<? extends Long> c) {
			this.ensureIndex(index);
			this.to = this.to + c.size();
			return this.l.addAll(this.from + index, c);
		}

		@Override
		public long getLong(int index) {
			this.ensureRestrictedIndex(index);
			return this.l.getLong(this.from + index);
		}

		@Override
		public long removeLong(int index) {
			this.ensureRestrictedIndex(index);
			this.to--;
			return this.l.removeLong(this.from + index);
		}

		@Override
		public long set(int index, long k) {
			this.ensureRestrictedIndex(index);
			return this.l.set(this.from + index, k);
		}

		public int size() {
			return this.to - this.from;
		}

		@Override
		public void getElements(int from, long[] a, int offset, int length) {
			this.ensureIndex(from);
			if (from + length > this.size()) {
				throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + this.size() + ")");
			} else {
				this.l.getElements(this.from + from, a, offset, length);
			}
		}

		@Override
		public void removeElements(int from, int to) {
			this.ensureIndex(from);
			this.ensureIndex(to);
			this.l.removeElements(this.from + from, this.from + to);
			this.to -= to - from;

			assert this.assertRange();
		}

		@Override
		public void addElements(int index, long[] a, int offset, int length) {
			this.ensureIndex(index);
			this.l.addElements(this.from + index, a, offset, length);
			this.to += length;

			assert this.assertRange();
		}

		@Override
		public LongListIterator listIterator(int index) {
			this.ensureIndex(index);
			return new LongListIterator() {
				int pos = index;
				int last = -1;

				public boolean hasNext() {
					return this.pos < LongSubList.this.size();
				}

				@Override
				public boolean hasPrevious() {
					return this.pos > 0;
				}

				@Override
				public long nextLong() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return LongSubList.this.l.getLong(LongSubList.this.from + (this.last = this.pos++));
					}
				}

				@Override
				public long previousLong() {
					if (!this.hasPrevious()) {
						throw new NoSuchElementException();
					} else {
						return LongSubList.this.l.getLong(LongSubList.this.from + (this.last = --this.pos));
					}
				}

				public int nextIndex() {
					return this.pos;
				}

				public int previousIndex() {
					return this.pos - 1;
				}

				@Override
				public void add(long k) {
					if (this.last == -1) {
						throw new IllegalStateException();
					} else {
						LongSubList.this.add(this.pos++, k);
						this.last = -1;

						assert LongSubList.this.assertRange();
					}
				}

				@Override
				public void set(long k) {
					if (this.last == -1) {
						throw new IllegalStateException();
					} else {
						LongSubList.this.set(this.last, k);
					}
				}

				@Override
				public void remove() {
					if (this.last == -1) {
						throw new IllegalStateException();
					} else {
						LongSubList.this.removeLong(this.last);
						if (this.last < this.pos) {
							this.pos--;
						}

						this.last = -1;

						assert LongSubList.this.assertRange();
					}
				}
			};
		}

		@Override
		public LongList subList(int from, int to) {
			this.ensureIndex(from);
			this.ensureIndex(to);
			if (from > to) {
				throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
			} else {
				return new AbstractLongList.LongSubList(this, from, to);
			}
		}

		@Override
		public boolean rem(long k) {
			int index = this.indexOf(k);
			if (index == -1) {
				return false;
			} else {
				this.to--;
				this.l.removeLong(this.from + index);

				assert this.assertRange();

				return true;
			}
		}

		@Override
		public boolean addAll(int index, LongCollection c) {
			this.ensureIndex(index);
			return super.addAll(index, c);
		}

		@Override
		public boolean addAll(int index, LongList l) {
			this.ensureIndex(index);
			return super.addAll(index, l);
		}
	}
}
