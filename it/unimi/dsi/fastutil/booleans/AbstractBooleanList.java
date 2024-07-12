package it.unimi.dsi.fastutil.booleans;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public abstract class AbstractBooleanList extends AbstractBooleanCollection implements BooleanList, BooleanStack {
	protected AbstractBooleanList() {
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
	public void add(int index, boolean k) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(boolean k) {
		this.add(this.size(), k);
		return true;
	}

	@Override
	public boolean removeBoolean(int i) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean set(int index, boolean k) {
		throw new UnsupportedOperationException();
	}

	public boolean addAll(int index, Collection<? extends Boolean> c) {
		this.ensureIndex(index);
		Iterator<? extends Boolean> i = c.iterator();
		boolean retVal = i.hasNext();

		while (i.hasNext()) {
			this.add(index++, (Boolean)i.next());
		}

		return retVal;
	}

	public boolean addAll(Collection<? extends Boolean> c) {
		return this.addAll(this.size(), c);
	}

	@Override
	public BooleanListIterator iterator() {
		return this.listIterator();
	}

	@Override
	public BooleanListIterator listIterator() {
		return this.listIterator(0);
	}

	@Override
	public BooleanListIterator listIterator(int index) {
		this.ensureIndex(index);
		return new BooleanListIterator() {
			int pos = index;
			int last = -1;

			public boolean hasNext() {
				return this.pos < AbstractBooleanList.this.size();
			}

			@Override
			public boolean hasPrevious() {
				return this.pos > 0;
			}

			@Override
			public boolean nextBoolean() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				} else {
					return AbstractBooleanList.this.getBoolean(this.last = this.pos++);
				}
			}

			@Override
			public boolean previousBoolean() {
				if (!this.hasPrevious()) {
					throw new NoSuchElementException();
				} else {
					return AbstractBooleanList.this.getBoolean(this.last = --this.pos);
				}
			}

			public int nextIndex() {
				return this.pos;
			}

			public int previousIndex() {
				return this.pos - 1;
			}

			@Override
			public void add(boolean k) {
				AbstractBooleanList.this.add(this.pos++, k);
				this.last = -1;
			}

			@Override
			public void set(boolean k) {
				if (this.last == -1) {
					throw new IllegalStateException();
				} else {
					AbstractBooleanList.this.set(this.last, k);
				}
			}

			@Override
			public void remove() {
				if (this.last == -1) {
					throw new IllegalStateException();
				} else {
					AbstractBooleanList.this.removeBoolean(this.last);
					if (this.last < this.pos) {
						this.pos--;
					}

					this.last = -1;
				}
			}
		};
	}

	@Override
	public boolean contains(boolean k) {
		return this.indexOf(k) >= 0;
	}

	@Override
	public int indexOf(boolean k) {
		BooleanListIterator i = this.listIterator();

		while (i.hasNext()) {
			boolean e = i.nextBoolean();
			if (k == e) {
				return i.previousIndex();
			}
		}

		return -1;
	}

	@Override
	public int lastIndexOf(boolean k) {
		BooleanListIterator i = this.listIterator(this.size());

		while (i.hasPrevious()) {
			boolean e = i.previousBoolean();
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
				this.add(false);
			}
		} else {
			while (i-- != size) {
				this.removeBoolean(i);
			}
		}
	}

	@Override
	public BooleanList subList(int from, int to) {
		this.ensureIndex(from);
		this.ensureIndex(to);
		if (from > to) {
			throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
		} else {
			return new AbstractBooleanList.BooleanSubList(this, from, to);
		}
	}

	@Override
	public void removeElements(int from, int to) {
		this.ensureIndex(to);
		BooleanListIterator i = this.listIterator(from);
		int n = to - from;
		if (n < 0) {
			throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
		} else {
			while (n-- != 0) {
				i.nextBoolean();
				i.remove();
			}
		}
	}

	@Override
	public void addElements(int index, boolean[] a, int offset, int length) {
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
	public void addElements(int index, boolean[] a) {
		this.addElements(index, a, 0, a.length);
	}

	@Override
	public void getElements(int from, boolean[] a, int offset, int length) {
		BooleanListIterator i = this.listIterator(from);
		if (offset < 0) {
			throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative");
		} else if (offset + length > a.length) {
			throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
		} else if (from + length > this.size()) {
			throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + this.size() + ")");
		} else {
			while (length-- != 0) {
				a[offset++] = i.nextBoolean();
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
		BooleanIterator i = this.iterator();
		int h = 1;
		int s = this.size();

		while (s-- != 0) {
			boolean k = i.nextBoolean();
			h = 31 * h + (k ? 1231 : 1237);
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
			} else if (l instanceof BooleanList) {
				BooleanListIterator i1 = this.listIterator();
				BooleanListIterator i2 = ((BooleanList)l).listIterator();

				while (s-- != 0) {
					if (i1.nextBoolean() != i2.nextBoolean()) {
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

	public int compareTo(List<? extends Boolean> l) {
		if (l == this) {
			return 0;
		} else if (l instanceof BooleanList) {
			BooleanListIterator i1 = this.listIterator();
			BooleanListIterator i2 = ((BooleanList)l).listIterator();

			while (i1.hasNext() && i2.hasNext()) {
				boolean e1 = i1.nextBoolean();
				boolean e2 = i2.nextBoolean();
				int r;
				if ((r = Boolean.compare(e1, e2)) != 0) {
					return r;
				}
			}

			return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
		} else {
			ListIterator<? extends Boolean> i1 = this.listIterator();
			ListIterator<? extends Boolean> i2 = l.listIterator();

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
	public void push(boolean o) {
		this.add(o);
	}

	@Override
	public boolean popBoolean() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return this.removeBoolean(this.size() - 1);
		}
	}

	@Override
	public boolean topBoolean() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return this.getBoolean(this.size() - 1);
		}
	}

	@Override
	public boolean peekBoolean(int i) {
		return this.getBoolean(this.size() - 1 - i);
	}

	@Override
	public boolean rem(boolean k) {
		int index = this.indexOf(k);
		if (index == -1) {
			return false;
		} else {
			this.removeBoolean(index);
			return true;
		}
	}

	@Override
	public boolean addAll(int index, BooleanCollection c) {
		this.ensureIndex(index);
		BooleanIterator i = c.iterator();
		boolean retVal = i.hasNext();

		while (i.hasNext()) {
			this.add(index++, i.nextBoolean());
		}

		return retVal;
	}

	@Override
	public boolean addAll(int index, BooleanList l) {
		return this.addAll(index, l);
	}

	@Override
	public boolean addAll(BooleanCollection c) {
		return this.addAll(this.size(), c);
	}

	@Override
	public boolean addAll(BooleanList l) {
		return this.addAll(this.size(), l);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		BooleanIterator i = this.iterator();
		int n = this.size();
		boolean first = true;
		s.append("[");

		while (n-- != 0) {
			if (first) {
				first = false;
			} else {
				s.append(", ");
			}

			boolean k = i.nextBoolean();
			s.append(String.valueOf(k));
		}

		s.append("]");
		return s.toString();
	}

	public static class BooleanSubList extends AbstractBooleanList implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final BooleanList l;
		protected final int from;
		protected int to;

		public BooleanSubList(BooleanList l, int from, int to) {
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
		public boolean add(boolean k) {
			this.l.add(this.to, k);
			this.to++;

			assert this.assertRange();

			return true;
		}

		@Override
		public void add(int index, boolean k) {
			this.ensureIndex(index);
			this.l.add(this.from + index, k);
			this.to++;

			assert this.assertRange();
		}

		@Override
		public boolean addAll(int index, Collection<? extends Boolean> c) {
			this.ensureIndex(index);
			this.to = this.to + c.size();
			return this.l.addAll(this.from + index, c);
		}

		@Override
		public boolean getBoolean(int index) {
			this.ensureRestrictedIndex(index);
			return this.l.getBoolean(this.from + index);
		}

		@Override
		public boolean removeBoolean(int index) {
			this.ensureRestrictedIndex(index);
			this.to--;
			return this.l.removeBoolean(this.from + index);
		}

		@Override
		public boolean set(int index, boolean k) {
			this.ensureRestrictedIndex(index);
			return this.l.set(this.from + index, k);
		}

		public int size() {
			return this.to - this.from;
		}

		@Override
		public void getElements(int from, boolean[] a, int offset, int length) {
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
		public void addElements(int index, boolean[] a, int offset, int length) {
			this.ensureIndex(index);
			this.l.addElements(this.from + index, a, offset, length);
			this.to += length;

			assert this.assertRange();
		}

		@Override
		public BooleanListIterator listIterator(int index) {
			this.ensureIndex(index);
			return new BooleanListIterator() {
				int pos = index;
				int last = -1;

				public boolean hasNext() {
					return this.pos < BooleanSubList.this.size();
				}

				@Override
				public boolean hasPrevious() {
					return this.pos > 0;
				}

				@Override
				public boolean nextBoolean() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return BooleanSubList.this.l.getBoolean(BooleanSubList.this.from + (this.last = this.pos++));
					}
				}

				@Override
				public boolean previousBoolean() {
					if (!this.hasPrevious()) {
						throw new NoSuchElementException();
					} else {
						return BooleanSubList.this.l.getBoolean(BooleanSubList.this.from + (this.last = --this.pos));
					}
				}

				public int nextIndex() {
					return this.pos;
				}

				public int previousIndex() {
					return this.pos - 1;
				}

				@Override
				public void add(boolean k) {
					if (this.last == -1) {
						throw new IllegalStateException();
					} else {
						BooleanSubList.this.add(this.pos++, k);
						this.last = -1;

						assert BooleanSubList.this.assertRange();
					}
				}

				@Override
				public void set(boolean k) {
					if (this.last == -1) {
						throw new IllegalStateException();
					} else {
						BooleanSubList.this.set(this.last, k);
					}
				}

				@Override
				public void remove() {
					if (this.last == -1) {
						throw new IllegalStateException();
					} else {
						BooleanSubList.this.removeBoolean(this.last);
						if (this.last < this.pos) {
							this.pos--;
						}

						this.last = -1;

						assert BooleanSubList.this.assertRange();
					}
				}
			};
		}

		@Override
		public BooleanList subList(int from, int to) {
			this.ensureIndex(from);
			this.ensureIndex(to);
			if (from > to) {
				throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
			} else {
				return new AbstractBooleanList.BooleanSubList(this, from, to);
			}
		}

		@Override
		public boolean rem(boolean k) {
			int index = this.indexOf(k);
			if (index == -1) {
				return false;
			} else {
				this.to--;
				this.l.removeBoolean(this.from + index);

				assert this.assertRange();

				return true;
			}
		}

		@Override
		public boolean addAll(int index, BooleanCollection c) {
			this.ensureIndex(index);
			return super.addAll(index, c);
		}

		@Override
		public boolean addAll(int index, BooleanList l) {
			this.ensureIndex(index);
			return super.addAll(index, l);
		}
	}
}
