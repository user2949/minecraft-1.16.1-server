package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Stack;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public abstract class AbstractObjectList<K> extends AbstractObjectCollection<K> implements ObjectList<K>, Stack<K> {
	protected AbstractObjectList() {
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

	public void add(int index, K k) {
		throw new UnsupportedOperationException();
	}

	public boolean add(K k) {
		this.add(this.size(), k);
		return true;
	}

	public K remove(int i) {
		throw new UnsupportedOperationException();
	}

	public K set(int index, K k) {
		throw new UnsupportedOperationException();
	}

	public boolean addAll(int index, Collection<? extends K> c) {
		this.ensureIndex(index);
		Iterator<? extends K> i = c.iterator();
		boolean retVal = i.hasNext();

		while (i.hasNext()) {
			this.add(index++, (K)i.next());
		}

		return retVal;
	}

	public boolean addAll(Collection<? extends K> c) {
		return this.addAll(this.size(), c);
	}

	@Override
	public ObjectListIterator<K> iterator() {
		return this.listIterator();
	}

	@Override
	public ObjectListIterator<K> listIterator() {
		return this.listIterator(0);
	}

	@Override
	public ObjectListIterator<K> listIterator(int index) {
		this.ensureIndex(index);
		return new ObjectListIterator<K>() {
			int pos = index;
			int last = -1;

			public boolean hasNext() {
				return this.pos < AbstractObjectList.this.size();
			}

			@Override
			public boolean hasPrevious() {
				return this.pos > 0;
			}

			public K next() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				} else {
					return (K)AbstractObjectList.this.get(this.last = this.pos++);
				}
			}

			@Override
			public K previous() {
				if (!this.hasPrevious()) {
					throw new NoSuchElementException();
				} else {
					return (K)AbstractObjectList.this.get(this.last = --this.pos);
				}
			}

			public int nextIndex() {
				return this.pos;
			}

			public int previousIndex() {
				return this.pos - 1;
			}

			@Override
			public void add(K k) {
				AbstractObjectList.this.add(this.pos++, k);
				this.last = -1;
			}

			@Override
			public void set(K k) {
				if (this.last == -1) {
					throw new IllegalStateException();
				} else {
					AbstractObjectList.this.set(this.last, k);
				}
			}

			@Override
			public void remove() {
				if (this.last == -1) {
					throw new IllegalStateException();
				} else {
					AbstractObjectList.this.remove(this.last);
					if (this.last < this.pos) {
						this.pos--;
					}

					this.last = -1;
				}
			}
		};
	}

	public boolean contains(Object k) {
		return this.indexOf(k) >= 0;
	}

	public int indexOf(Object k) {
		ObjectListIterator<K> i = this.listIterator();

		while (i.hasNext()) {
			K e = (K)i.next();
			if (Objects.equals(k, e)) {
				return i.previousIndex();
			}
		}

		return -1;
	}

	public int lastIndexOf(Object k) {
		ObjectListIterator<K> i = this.listIterator(this.size());

		while (i.hasPrevious()) {
			K e = i.previous();
			if (Objects.equals(k, e)) {
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
				this.add(null);
			}
		} else {
			while (i-- != size) {
				this.remove(i);
			}
		}
	}

	@Override
	public ObjectList<K> subList(int from, int to) {
		this.ensureIndex(from);
		this.ensureIndex(to);
		if (from > to) {
			throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
		} else {
			return new AbstractObjectList.ObjectSubList<>(this, from, to);
		}
	}

	@Override
	public void removeElements(int from, int to) {
		this.ensureIndex(to);
		ObjectListIterator<K> i = this.listIterator(from);
		int n = to - from;
		if (n < 0) {
			throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
		} else {
			while (n-- != 0) {
				i.next();
				i.remove();
			}
		}
	}

	@Override
	public void addElements(int index, K[] a, int offset, int length) {
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
	public void addElements(int index, K[] a) {
		this.addElements(index, a, 0, a.length);
	}

	@Override
	public void getElements(int from, Object[] a, int offset, int length) {
		ObjectListIterator<K> i = this.listIterator(from);
		if (offset < 0) {
			throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative");
		} else if (offset + length > a.length) {
			throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
		} else if (from + length > this.size()) {
			throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + this.size() + ")");
		} else {
			while (length-- != 0) {
				a[offset++] = i.next();
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
		ObjectIterator<K> i = this.iterator();
		int h = 1;
		int s = this.size();

		while (s-- != 0) {
			K k = (K)i.next();
			h = 31 * h + (k == null ? 0 : k.hashCode());
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

	public int compareTo(List<? extends K> l) {
		if (l == this) {
			return 0;
		} else if (l instanceof ObjectList) {
			ObjectListIterator<K> i1 = this.listIterator();
			ObjectListIterator<K> i2 = ((ObjectList)l).listIterator();

			while (i1.hasNext() && i2.hasNext()) {
				K e1 = (K)i1.next();
				K e2 = (K)i2.next();
				int r;
				if ((r = ((Comparable)e1).compareTo(e2)) != 0) {
					return r;
				}
			}

			return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
		} else {
			ListIterator<? extends K> i1 = this.listIterator();
			ListIterator<? extends K> i2 = l.listIterator();

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
	public void push(K o) {
		this.add(o);
	}

	@Override
	public K pop() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return this.remove(this.size() - 1);
		}
	}

	@Override
	public K top() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return (K)this.get(this.size() - 1);
		}
	}

	@Override
	public K peek(int i) {
		return (K)this.get(this.size() - 1 - i);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		ObjectIterator<K> i = this.iterator();
		int n = this.size();
		boolean first = true;
		s.append("[");

		while (n-- != 0) {
			if (first) {
				first = false;
			} else {
				s.append(", ");
			}

			K k = (K)i.next();
			if (this == k) {
				s.append("(this list)");
			} else {
				s.append(String.valueOf(k));
			}
		}

		s.append("]");
		return s.toString();
	}

	public static class ObjectSubList<K> extends AbstractObjectList<K> implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final ObjectList<K> l;
		protected final int from;
		protected int to;

		public ObjectSubList(ObjectList<K> l, int from, int to) {
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
		public boolean add(K k) {
			this.l.add(this.to, k);
			this.to++;

			assert this.assertRange();

			return true;
		}

		@Override
		public void add(int index, K k) {
			this.ensureIndex(index);
			this.l.add(this.from + index, k);
			this.to++;

			assert this.assertRange();
		}

		@Override
		public boolean addAll(int index, Collection<? extends K> c) {
			this.ensureIndex(index);
			this.to = this.to + c.size();
			return this.l.addAll(this.from + index, c);
		}

		public K get(int index) {
			this.ensureRestrictedIndex(index);
			return (K)this.l.get(this.from + index);
		}

		@Override
		public K remove(int index) {
			this.ensureRestrictedIndex(index);
			this.to--;
			return (K)this.l.remove(this.from + index);
		}

		@Override
		public K set(int index, K k) {
			this.ensureRestrictedIndex(index);
			return (K)this.l.set(this.from + index, k);
		}

		public int size() {
			return this.to - this.from;
		}

		@Override
		public void getElements(int from, Object[] a, int offset, int length) {
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
		public void addElements(int index, K[] a, int offset, int length) {
			this.ensureIndex(index);
			this.l.addElements(this.from + index, a, offset, length);
			this.to += length;

			assert this.assertRange();
		}

		@Override
		public ObjectListIterator<K> listIterator(int index) {
			this.ensureIndex(index);
			return new ObjectListIterator<K>() {
				int pos = index;
				int last = -1;

				public boolean hasNext() {
					return this.pos < ObjectSubList.this.size();
				}

				@Override
				public boolean hasPrevious() {
					return this.pos > 0;
				}

				public K next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return (K)ObjectSubList.this.l.get(ObjectSubList.this.from + (this.last = this.pos++));
					}
				}

				@Override
				public K previous() {
					if (!this.hasPrevious()) {
						throw new NoSuchElementException();
					} else {
						return (K)ObjectSubList.this.l.get(ObjectSubList.this.from + (this.last = --this.pos));
					}
				}

				public int nextIndex() {
					return this.pos;
				}

				public int previousIndex() {
					return this.pos - 1;
				}

				@Override
				public void add(K k) {
					if (this.last == -1) {
						throw new IllegalStateException();
					} else {
						ObjectSubList.this.add(this.pos++, k);
						this.last = -1;

						assert ObjectSubList.this.assertRange();
					}
				}

				@Override
				public void set(K k) {
					if (this.last == -1) {
						throw new IllegalStateException();
					} else {
						ObjectSubList.this.set(this.last, k);
					}
				}

				@Override
				public void remove() {
					if (this.last == -1) {
						throw new IllegalStateException();
					} else {
						ObjectSubList.this.remove(this.last);
						if (this.last < this.pos) {
							this.pos--;
						}

						this.last = -1;

						assert ObjectSubList.this.assertRange();
					}
				}
			};
		}

		@Override
		public ObjectList<K> subList(int from, int to) {
			this.ensureIndex(from);
			this.ensureIndex(to);
			if (from > to) {
				throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
			} else {
				return new AbstractObjectList.ObjectSubList<>(this, from, to);
			}
		}
	}
}
