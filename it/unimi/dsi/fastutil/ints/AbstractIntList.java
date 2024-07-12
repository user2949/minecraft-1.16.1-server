package it.unimi.dsi.fastutil.ints;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public abstract class AbstractIntList extends AbstractIntCollection implements IntList, IntStack {
	protected AbstractIntList() {
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
	public void add(int index, int k) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(int k) {
		this.add(this.size(), k);
		return true;
	}

	@Override
	public int removeInt(int i) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int set(int index, int k) {
		throw new UnsupportedOperationException();
	}

	public boolean addAll(int index, Collection<? extends Integer> c) {
		this.ensureIndex(index);
		Iterator<? extends Integer> i = c.iterator();
		boolean retVal = i.hasNext();

		while (i.hasNext()) {
			this.add(index++, (Integer)i.next());
		}

		return retVal;
	}

	public boolean addAll(Collection<? extends Integer> c) {
		return this.addAll(this.size(), c);
	}

	@Override
	public IntListIterator iterator() {
		return this.listIterator();
	}

	@Override
	public IntListIterator listIterator() {
		return this.listIterator(0);
	}

	@Override
	public IntListIterator listIterator(int index) {
		this.ensureIndex(index);
		return new IntListIterator() {
			int pos = index;
			int last = -1;

			public boolean hasNext() {
				return this.pos < AbstractIntList.this.size();
			}

			@Override
			public boolean hasPrevious() {
				return this.pos > 0;
			}

			@Override
			public int nextInt() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				} else {
					return AbstractIntList.this.getInt(this.last = this.pos++);
				}
			}

			@Override
			public int previousInt() {
				if (!this.hasPrevious()) {
					throw new NoSuchElementException();
				} else {
					return AbstractIntList.this.getInt(this.last = --this.pos);
				}
			}

			public int nextIndex() {
				return this.pos;
			}

			public int previousIndex() {
				return this.pos - 1;
			}

			@Override
			public void add(int k) {
				AbstractIntList.this.add(this.pos++, k);
				this.last = -1;
			}

			@Override
			public void set(int k) {
				if (this.last == -1) {
					throw new IllegalStateException();
				} else {
					AbstractIntList.this.set(this.last, k);
				}
			}

			@Override
			public void remove() {
				if (this.last == -1) {
					throw new IllegalStateException();
				} else {
					AbstractIntList.this.removeInt(this.last);
					if (this.last < this.pos) {
						this.pos--;
					}

					this.last = -1;
				}
			}
		};
	}

	@Override
	public boolean contains(int k) {
		return this.indexOf(k) >= 0;
	}

	@Override
	public int indexOf(int k) {
		IntListIterator i = this.listIterator();

		while (i.hasNext()) {
			int e = i.nextInt();
			if (k == e) {
				return i.previousIndex();
			}
		}

		return -1;
	}

	@Override
	public int lastIndexOf(int k) {
		IntListIterator i = this.listIterator(this.size());

		while (i.hasPrevious()) {
			int e = i.previousInt();
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
				this.add(0);
			}
		} else {
			while (i-- != size) {
				this.removeInt(i);
			}
		}
	}

	@Override
	public IntList subList(int from, int to) {
		this.ensureIndex(from);
		this.ensureIndex(to);
		if (from > to) {
			throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
		} else {
			return new AbstractIntList.IntSubList(this, from, to);
		}
	}

	@Override
	public void removeElements(int from, int to) {
		this.ensureIndex(to);
		IntListIterator i = this.listIterator(from);
		int n = to - from;
		if (n < 0) {
			throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
		} else {
			while (n-- != 0) {
				i.nextInt();
				i.remove();
			}
		}
	}

	@Override
	public void addElements(int index, int[] a, int offset, int length) {
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
	public void addElements(int index, int[] a) {
		this.addElements(index, a, 0, a.length);
	}

	@Override
	public void getElements(int from, int[] a, int offset, int length) {
		IntListIterator i = this.listIterator(from);
		if (offset < 0) {
			throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative");
		} else if (offset + length > a.length) {
			throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
		} else if (from + length > this.size()) {
			throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + this.size() + ")");
		} else {
			while (length-- != 0) {
				a[offset++] = i.nextInt();
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
		IntIterator i = this.iterator();
		int h = 1;
		int s = this.size();

		while (s-- != 0) {
			int k = i.nextInt();
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
			} else if (l instanceof IntList) {
				IntListIterator i1 = this.listIterator();
				IntListIterator i2 = ((IntList)l).listIterator();

				while (s-- != 0) {
					if (i1.nextInt() != i2.nextInt()) {
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

	public int compareTo(List<? extends Integer> l) {
		if (l == this) {
			return 0;
		} else if (l instanceof IntList) {
			IntListIterator i1 = this.listIterator();
			IntListIterator i2 = ((IntList)l).listIterator();

			while (i1.hasNext() && i2.hasNext()) {
				int e1 = i1.nextInt();
				int e2 = i2.nextInt();
				int r;
				if ((r = Integer.compare(e1, e2)) != 0) {
					return r;
				}
			}

			return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
		} else {
			ListIterator<? extends Integer> i1 = this.listIterator();
			ListIterator<? extends Integer> i2 = l.listIterator();

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
	public void push(int o) {
		this.add(o);
	}

	@Override
	public int popInt() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return this.removeInt(this.size() - 1);
		}
	}

	@Override
	public int topInt() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return this.getInt(this.size() - 1);
		}
	}

	@Override
	public int peekInt(int i) {
		return this.getInt(this.size() - 1 - i);
	}

	@Override
	public boolean rem(int k) {
		int index = this.indexOf(k);
		if (index == -1) {
			return false;
		} else {
			this.removeInt(index);
			return true;
		}
	}

	@Override
	public boolean addAll(int index, IntCollection c) {
		this.ensureIndex(index);
		IntIterator i = c.iterator();
		boolean retVal = i.hasNext();

		while (i.hasNext()) {
			this.add(index++, i.nextInt());
		}

		return retVal;
	}

	@Override
	public boolean addAll(int index, IntList l) {
		return this.addAll(index, l);
	}

	@Override
	public boolean addAll(IntCollection c) {
		return this.addAll(this.size(), c);
	}

	@Override
	public boolean addAll(IntList l) {
		return this.addAll(this.size(), l);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		IntIterator i = this.iterator();
		int n = this.size();
		boolean first = true;
		s.append("[");

		while (n-- != 0) {
			if (first) {
				first = false;
			} else {
				s.append(", ");
			}

			int k = i.nextInt();
			s.append(String.valueOf(k));
		}

		s.append("]");
		return s.toString();
	}

	public static class IntSubList extends AbstractIntList implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final IntList l;
		protected final int from;
		protected int to;

		public IntSubList(IntList l, int from, int to) {
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
		public boolean add(int k) {
			this.l.add(this.to, k);
			this.to++;

			assert this.assertRange();

			return true;
		}

		@Override
		public void add(int index, int k) {
			this.ensureIndex(index);
			this.l.add(this.from + index, k);
			this.to++;

			assert this.assertRange();
		}

		@Override
		public boolean addAll(int index, Collection<? extends Integer> c) {
			this.ensureIndex(index);
			this.to = this.to + c.size();
			return this.l.addAll(this.from + index, c);
		}

		@Override
		public int getInt(int index) {
			this.ensureRestrictedIndex(index);
			return this.l.getInt(this.from + index);
		}

		@Override
		public int removeInt(int index) {
			this.ensureRestrictedIndex(index);
			this.to--;
			return this.l.removeInt(this.from + index);
		}

		@Override
		public int set(int index, int k) {
			this.ensureRestrictedIndex(index);
			return this.l.set(this.from + index, k);
		}

		public int size() {
			return this.to - this.from;
		}

		@Override
		public void getElements(int from, int[] a, int offset, int length) {
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
		public void addElements(int index, int[] a, int offset, int length) {
			this.ensureIndex(index);
			this.l.addElements(this.from + index, a, offset, length);
			this.to += length;

			assert this.assertRange();
		}

		@Override
		public IntListIterator listIterator(int index) {
			this.ensureIndex(index);
			return new IntListIterator() {
				int pos = index;
				int last = -1;

				public boolean hasNext() {
					return this.pos < IntSubList.this.size();
				}

				@Override
				public boolean hasPrevious() {
					return this.pos > 0;
				}

				@Override
				public int nextInt() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return IntSubList.this.l.getInt(IntSubList.this.from + (this.last = this.pos++));
					}
				}

				@Override
				public int previousInt() {
					if (!this.hasPrevious()) {
						throw new NoSuchElementException();
					} else {
						return IntSubList.this.l.getInt(IntSubList.this.from + (this.last = --this.pos));
					}
				}

				public int nextIndex() {
					return this.pos;
				}

				public int previousIndex() {
					return this.pos - 1;
				}

				@Override
				public void add(int k) {
					if (this.last == -1) {
						throw new IllegalStateException();
					} else {
						IntSubList.this.add(this.pos++, k);
						this.last = -1;

						assert IntSubList.this.assertRange();
					}
				}

				@Override
				public void set(int k) {
					if (this.last == -1) {
						throw new IllegalStateException();
					} else {
						IntSubList.this.set(this.last, k);
					}
				}

				@Override
				public void remove() {
					if (this.last == -1) {
						throw new IllegalStateException();
					} else {
						IntSubList.this.removeInt(this.last);
						if (this.last < this.pos) {
							this.pos--;
						}

						this.last = -1;

						assert IntSubList.this.assertRange();
					}
				}
			};
		}

		@Override
		public IntList subList(int from, int to) {
			this.ensureIndex(from);
			this.ensureIndex(to);
			if (from > to) {
				throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
			} else {
				return new AbstractIntList.IntSubList(this, from, to);
			}
		}

		@Override
		public boolean rem(int k) {
			int index = this.indexOf(k);
			if (index == -1) {
				return false;
			} else {
				this.to--;
				this.l.removeInt(this.from + index);

				assert this.assertRange();

				return true;
			}
		}

		@Override
		public boolean addAll(int index, IntCollection c) {
			this.ensureIndex(index);
			return super.addAll(index, c);
		}

		@Override
		public boolean addAll(int index, IntList l) {
			this.ensureIndex(index);
			return super.addAll(index, l);
		}
	}
}
