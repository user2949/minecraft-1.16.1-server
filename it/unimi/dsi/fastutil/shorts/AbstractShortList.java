package it.unimi.dsi.fastutil.shorts;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public abstract class AbstractShortList extends AbstractShortCollection implements ShortList, ShortStack {
	protected AbstractShortList() {
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
	public void add(int index, short k) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(short k) {
		this.add(this.size(), k);
		return true;
	}

	@Override
	public short removeShort(int i) {
		throw new UnsupportedOperationException();
	}

	@Override
	public short set(int index, short k) {
		throw new UnsupportedOperationException();
	}

	public boolean addAll(int index, Collection<? extends Short> c) {
		this.ensureIndex(index);
		Iterator<? extends Short> i = c.iterator();
		boolean retVal = i.hasNext();

		while (i.hasNext()) {
			this.add(index++, (Short)i.next());
		}

		return retVal;
	}

	public boolean addAll(Collection<? extends Short> c) {
		return this.addAll(this.size(), c);
	}

	@Override
	public ShortListIterator iterator() {
		return this.listIterator();
	}

	@Override
	public ShortListIterator listIterator() {
		return this.listIterator(0);
	}

	@Override
	public ShortListIterator listIterator(int index) {
		this.ensureIndex(index);
		return new ShortListIterator() {
			int pos = index;
			int last = -1;

			public boolean hasNext() {
				return this.pos < AbstractShortList.this.size();
			}

			@Override
			public boolean hasPrevious() {
				return this.pos > 0;
			}

			@Override
			public short nextShort() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				} else {
					return AbstractShortList.this.getShort(this.last = this.pos++);
				}
			}

			@Override
			public short previousShort() {
				if (!this.hasPrevious()) {
					throw new NoSuchElementException();
				} else {
					return AbstractShortList.this.getShort(this.last = --this.pos);
				}
			}

			public int nextIndex() {
				return this.pos;
			}

			public int previousIndex() {
				return this.pos - 1;
			}

			@Override
			public void add(short k) {
				AbstractShortList.this.add(this.pos++, k);
				this.last = -1;
			}

			@Override
			public void set(short k) {
				if (this.last == -1) {
					throw new IllegalStateException();
				} else {
					AbstractShortList.this.set(this.last, k);
				}
			}

			@Override
			public void remove() {
				if (this.last == -1) {
					throw new IllegalStateException();
				} else {
					AbstractShortList.this.removeShort(this.last);
					if (this.last < this.pos) {
						this.pos--;
					}

					this.last = -1;
				}
			}
		};
	}

	@Override
	public boolean contains(short k) {
		return this.indexOf(k) >= 0;
	}

	@Override
	public int indexOf(short k) {
		ShortListIterator i = this.listIterator();

		while (i.hasNext()) {
			short e = i.nextShort();
			if (k == e) {
				return i.previousIndex();
			}
		}

		return -1;
	}

	@Override
	public int lastIndexOf(short k) {
		ShortListIterator i = this.listIterator(this.size());

		while (i.hasPrevious()) {
			short e = i.previousShort();
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
				this.add((short)0);
			}
		} else {
			while (i-- != size) {
				this.removeShort(i);
			}
		}
	}

	@Override
	public ShortList subList(int from, int to) {
		this.ensureIndex(from);
		this.ensureIndex(to);
		if (from > to) {
			throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
		} else {
			return new AbstractShortList.ShortSubList(this, from, to);
		}
	}

	@Override
	public void removeElements(int from, int to) {
		this.ensureIndex(to);
		ShortListIterator i = this.listIterator(from);
		int n = to - from;
		if (n < 0) {
			throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
		} else {
			while (n-- != 0) {
				i.nextShort();
				i.remove();
			}
		}
	}

	@Override
	public void addElements(int index, short[] a, int offset, int length) {
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
	public void addElements(int index, short[] a) {
		this.addElements(index, a, 0, a.length);
	}

	@Override
	public void getElements(int from, short[] a, int offset, int length) {
		ShortListIterator i = this.listIterator(from);
		if (offset < 0) {
			throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative");
		} else if (offset + length > a.length) {
			throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
		} else if (from + length > this.size()) {
			throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + this.size() + ")");
		} else {
			while (length-- != 0) {
				a[offset++] = i.nextShort();
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
		ShortIterator i = this.iterator();
		int h = 1;
		int s = this.size();

		while (s-- != 0) {
			short k = i.nextShort();
			h = 31 * h + k;
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
			} else if (l instanceof ShortList) {
				ShortListIterator i1 = this.listIterator();
				ShortListIterator i2 = ((ShortList)l).listIterator();

				while (s-- != 0) {
					if (i1.nextShort() != i2.nextShort()) {
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

	public int compareTo(List<? extends Short> l) {
		if (l == this) {
			return 0;
		} else if (l instanceof ShortList) {
			ShortListIterator i1 = this.listIterator();
			ShortListIterator i2 = ((ShortList)l).listIterator();

			while (i1.hasNext() && i2.hasNext()) {
				short e1 = i1.nextShort();
				short e2 = i2.nextShort();
				int r;
				if ((r = Short.compare(e1, e2)) != 0) {
					return r;
				}
			}

			return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
		} else {
			ListIterator<? extends Short> i1 = this.listIterator();
			ListIterator<? extends Short> i2 = l.listIterator();

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
	public void push(short o) {
		this.add(o);
	}

	@Override
	public short popShort() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return this.removeShort(this.size() - 1);
		}
	}

	@Override
	public short topShort() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return this.getShort(this.size() - 1);
		}
	}

	@Override
	public short peekShort(int i) {
		return this.getShort(this.size() - 1 - i);
	}

	@Override
	public boolean rem(short k) {
		int index = this.indexOf(k);
		if (index == -1) {
			return false;
		} else {
			this.removeShort(index);
			return true;
		}
	}

	@Override
	public boolean addAll(int index, ShortCollection c) {
		this.ensureIndex(index);
		ShortIterator i = c.iterator();
		boolean retVal = i.hasNext();

		while (i.hasNext()) {
			this.add(index++, i.nextShort());
		}

		return retVal;
	}

	@Override
	public boolean addAll(int index, ShortList l) {
		return this.addAll(index, l);
	}

	@Override
	public boolean addAll(ShortCollection c) {
		return this.addAll(this.size(), c);
	}

	@Override
	public boolean addAll(ShortList l) {
		return this.addAll(this.size(), l);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		ShortIterator i = this.iterator();
		int n = this.size();
		boolean first = true;
		s.append("[");

		while (n-- != 0) {
			if (first) {
				first = false;
			} else {
				s.append(", ");
			}

			short k = i.nextShort();
			s.append(String.valueOf(k));
		}

		s.append("]");
		return s.toString();
	}

	public static class ShortSubList extends AbstractShortList implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final ShortList l;
		protected final int from;
		protected int to;

		public ShortSubList(ShortList l, int from, int to) {
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
		public boolean add(short k) {
			this.l.add(this.to, k);
			this.to++;

			assert this.assertRange();

			return true;
		}

		@Override
		public void add(int index, short k) {
			this.ensureIndex(index);
			this.l.add(this.from + index, k);
			this.to++;

			assert this.assertRange();
		}

		@Override
		public boolean addAll(int index, Collection<? extends Short> c) {
			this.ensureIndex(index);
			this.to = this.to + c.size();
			return this.l.addAll(this.from + index, c);
		}

		@Override
		public short getShort(int index) {
			this.ensureRestrictedIndex(index);
			return this.l.getShort(this.from + index);
		}

		@Override
		public short removeShort(int index) {
			this.ensureRestrictedIndex(index);
			this.to--;
			return this.l.removeShort(this.from + index);
		}

		@Override
		public short set(int index, short k) {
			this.ensureRestrictedIndex(index);
			return this.l.set(this.from + index, k);
		}

		public int size() {
			return this.to - this.from;
		}

		@Override
		public void getElements(int from, short[] a, int offset, int length) {
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
		public void addElements(int index, short[] a, int offset, int length) {
			this.ensureIndex(index);
			this.l.addElements(this.from + index, a, offset, length);
			this.to += length;

			assert this.assertRange();
		}

		@Override
		public ShortListIterator listIterator(int index) {
			this.ensureIndex(index);
			return new ShortListIterator() {
				int pos = index;
				int last = -1;

				public boolean hasNext() {
					return this.pos < ShortSubList.this.size();
				}

				@Override
				public boolean hasPrevious() {
					return this.pos > 0;
				}

				@Override
				public short nextShort() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return ShortSubList.this.l.getShort(ShortSubList.this.from + (this.last = this.pos++));
					}
				}

				@Override
				public short previousShort() {
					if (!this.hasPrevious()) {
						throw new NoSuchElementException();
					} else {
						return ShortSubList.this.l.getShort(ShortSubList.this.from + (this.last = --this.pos));
					}
				}

				public int nextIndex() {
					return this.pos;
				}

				public int previousIndex() {
					return this.pos - 1;
				}

				@Override
				public void add(short k) {
					if (this.last == -1) {
						throw new IllegalStateException();
					} else {
						ShortSubList.this.add(this.pos++, k);
						this.last = -1;

						assert ShortSubList.this.assertRange();
					}
				}

				@Override
				public void set(short k) {
					if (this.last == -1) {
						throw new IllegalStateException();
					} else {
						ShortSubList.this.set(this.last, k);
					}
				}

				@Override
				public void remove() {
					if (this.last == -1) {
						throw new IllegalStateException();
					} else {
						ShortSubList.this.removeShort(this.last);
						if (this.last < this.pos) {
							this.pos--;
						}

						this.last = -1;

						assert ShortSubList.this.assertRange();
					}
				}
			};
		}

		@Override
		public ShortList subList(int from, int to) {
			this.ensureIndex(from);
			this.ensureIndex(to);
			if (from > to) {
				throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
			} else {
				return new AbstractShortList.ShortSubList(this, from, to);
			}
		}

		@Override
		public boolean rem(short k) {
			int index = this.indexOf(k);
			if (index == -1) {
				return false;
			} else {
				this.to--;
				this.l.removeShort(this.from + index);

				assert this.assertRange();

				return true;
			}
		}

		@Override
		public boolean addAll(int index, ShortCollection c) {
			this.ensureIndex(index);
			return super.addAll(index, c);
		}

		@Override
		public boolean addAll(int index, ShortList l) {
			this.ensureIndex(index);
			return super.addAll(index, l);
		}
	}
}
