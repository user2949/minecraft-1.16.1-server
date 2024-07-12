package it.unimi.dsi.fastutil.bytes;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public abstract class AbstractByteList extends AbstractByteCollection implements ByteList, ByteStack {
	protected AbstractByteList() {
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
	public void add(int index, byte k) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(byte k) {
		this.add(this.size(), k);
		return true;
	}

	@Override
	public byte removeByte(int i) {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte set(int index, byte k) {
		throw new UnsupportedOperationException();
	}

	public boolean addAll(int index, Collection<? extends Byte> c) {
		this.ensureIndex(index);
		Iterator<? extends Byte> i = c.iterator();
		boolean retVal = i.hasNext();

		while (i.hasNext()) {
			this.add(index++, (Byte)i.next());
		}

		return retVal;
	}

	public boolean addAll(Collection<? extends Byte> c) {
		return this.addAll(this.size(), c);
	}

	@Override
	public ByteListIterator iterator() {
		return this.listIterator();
	}

	@Override
	public ByteListIterator listIterator() {
		return this.listIterator(0);
	}

	@Override
	public ByteListIterator listIterator(int index) {
		this.ensureIndex(index);
		return new ByteListIterator() {
			int pos = index;
			int last = -1;

			public boolean hasNext() {
				return this.pos < AbstractByteList.this.size();
			}

			@Override
			public boolean hasPrevious() {
				return this.pos > 0;
			}

			@Override
			public byte nextByte() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				} else {
					return AbstractByteList.this.getByte(this.last = this.pos++);
				}
			}

			@Override
			public byte previousByte() {
				if (!this.hasPrevious()) {
					throw new NoSuchElementException();
				} else {
					return AbstractByteList.this.getByte(this.last = --this.pos);
				}
			}

			public int nextIndex() {
				return this.pos;
			}

			public int previousIndex() {
				return this.pos - 1;
			}

			@Override
			public void add(byte k) {
				AbstractByteList.this.add(this.pos++, k);
				this.last = -1;
			}

			@Override
			public void set(byte k) {
				if (this.last == -1) {
					throw new IllegalStateException();
				} else {
					AbstractByteList.this.set(this.last, k);
				}
			}

			@Override
			public void remove() {
				if (this.last == -1) {
					throw new IllegalStateException();
				} else {
					AbstractByteList.this.removeByte(this.last);
					if (this.last < this.pos) {
						this.pos--;
					}

					this.last = -1;
				}
			}
		};
	}

	@Override
	public boolean contains(byte k) {
		return this.indexOf(k) >= 0;
	}

	@Override
	public int indexOf(byte k) {
		ByteListIterator i = this.listIterator();

		while (i.hasNext()) {
			byte e = i.nextByte();
			if (k == e) {
				return i.previousIndex();
			}
		}

		return -1;
	}

	@Override
	public int lastIndexOf(byte k) {
		ByteListIterator i = this.listIterator(this.size());

		while (i.hasPrevious()) {
			byte e = i.previousByte();
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
				this.add((byte)0);
			}
		} else {
			while (i-- != size) {
				this.removeByte(i);
			}
		}
	}

	@Override
	public ByteList subList(int from, int to) {
		this.ensureIndex(from);
		this.ensureIndex(to);
		if (from > to) {
			throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
		} else {
			return new AbstractByteList.ByteSubList(this, from, to);
		}
	}

	@Override
	public void removeElements(int from, int to) {
		this.ensureIndex(to);
		ByteListIterator i = this.listIterator(from);
		int n = to - from;
		if (n < 0) {
			throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
		} else {
			while (n-- != 0) {
				i.nextByte();
				i.remove();
			}
		}
	}

	@Override
	public void addElements(int index, byte[] a, int offset, int length) {
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
	public void addElements(int index, byte[] a) {
		this.addElements(index, a, 0, a.length);
	}

	@Override
	public void getElements(int from, byte[] a, int offset, int length) {
		ByteListIterator i = this.listIterator(from);
		if (offset < 0) {
			throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative");
		} else if (offset + length > a.length) {
			throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
		} else if (from + length > this.size()) {
			throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + this.size() + ")");
		} else {
			while (length-- != 0) {
				a[offset++] = i.nextByte();
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
		ByteIterator i = this.iterator();
		int h = 1;
		int s = this.size();

		while (s-- != 0) {
			byte k = i.nextByte();
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
			} else if (l instanceof ByteList) {
				ByteListIterator i1 = this.listIterator();
				ByteListIterator i2 = ((ByteList)l).listIterator();

				while (s-- != 0) {
					if (i1.nextByte() != i2.nextByte()) {
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

	public int compareTo(List<? extends Byte> l) {
		if (l == this) {
			return 0;
		} else if (l instanceof ByteList) {
			ByteListIterator i1 = this.listIterator();
			ByteListIterator i2 = ((ByteList)l).listIterator();

			while (i1.hasNext() && i2.hasNext()) {
				byte e1 = i1.nextByte();
				byte e2 = i2.nextByte();
				int r;
				if ((r = Byte.compare(e1, e2)) != 0) {
					return r;
				}
			}

			return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
		} else {
			ListIterator<? extends Byte> i1 = this.listIterator();
			ListIterator<? extends Byte> i2 = l.listIterator();

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
	public void push(byte o) {
		this.add(o);
	}

	@Override
	public byte popByte() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return this.removeByte(this.size() - 1);
		}
	}

	@Override
	public byte topByte() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return this.getByte(this.size() - 1);
		}
	}

	@Override
	public byte peekByte(int i) {
		return this.getByte(this.size() - 1 - i);
	}

	@Override
	public boolean rem(byte k) {
		int index = this.indexOf(k);
		if (index == -1) {
			return false;
		} else {
			this.removeByte(index);
			return true;
		}
	}

	@Override
	public boolean addAll(int index, ByteCollection c) {
		this.ensureIndex(index);
		ByteIterator i = c.iterator();
		boolean retVal = i.hasNext();

		while (i.hasNext()) {
			this.add(index++, i.nextByte());
		}

		return retVal;
	}

	@Override
	public boolean addAll(int index, ByteList l) {
		return this.addAll(index, l);
	}

	@Override
	public boolean addAll(ByteCollection c) {
		return this.addAll(this.size(), c);
	}

	@Override
	public boolean addAll(ByteList l) {
		return this.addAll(this.size(), l);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		ByteIterator i = this.iterator();
		int n = this.size();
		boolean first = true;
		s.append("[");

		while (n-- != 0) {
			if (first) {
				first = false;
			} else {
				s.append(", ");
			}

			byte k = i.nextByte();
			s.append(String.valueOf(k));
		}

		s.append("]");
		return s.toString();
	}

	public static class ByteSubList extends AbstractByteList implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final ByteList l;
		protected final int from;
		protected int to;

		public ByteSubList(ByteList l, int from, int to) {
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
		public boolean add(byte k) {
			this.l.add(this.to, k);
			this.to++;

			assert this.assertRange();

			return true;
		}

		@Override
		public void add(int index, byte k) {
			this.ensureIndex(index);
			this.l.add(this.from + index, k);
			this.to++;

			assert this.assertRange();
		}

		@Override
		public boolean addAll(int index, Collection<? extends Byte> c) {
			this.ensureIndex(index);
			this.to = this.to + c.size();
			return this.l.addAll(this.from + index, c);
		}

		@Override
		public byte getByte(int index) {
			this.ensureRestrictedIndex(index);
			return this.l.getByte(this.from + index);
		}

		@Override
		public byte removeByte(int index) {
			this.ensureRestrictedIndex(index);
			this.to--;
			return this.l.removeByte(this.from + index);
		}

		@Override
		public byte set(int index, byte k) {
			this.ensureRestrictedIndex(index);
			return this.l.set(this.from + index, k);
		}

		public int size() {
			return this.to - this.from;
		}

		@Override
		public void getElements(int from, byte[] a, int offset, int length) {
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
		public void addElements(int index, byte[] a, int offset, int length) {
			this.ensureIndex(index);
			this.l.addElements(this.from + index, a, offset, length);
			this.to += length;

			assert this.assertRange();
		}

		@Override
		public ByteListIterator listIterator(int index) {
			this.ensureIndex(index);
			return new ByteListIterator() {
				int pos = index;
				int last = -1;

				public boolean hasNext() {
					return this.pos < ByteSubList.this.size();
				}

				@Override
				public boolean hasPrevious() {
					return this.pos > 0;
				}

				@Override
				public byte nextByte() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return ByteSubList.this.l.getByte(ByteSubList.this.from + (this.last = this.pos++));
					}
				}

				@Override
				public byte previousByte() {
					if (!this.hasPrevious()) {
						throw new NoSuchElementException();
					} else {
						return ByteSubList.this.l.getByte(ByteSubList.this.from + (this.last = --this.pos));
					}
				}

				public int nextIndex() {
					return this.pos;
				}

				public int previousIndex() {
					return this.pos - 1;
				}

				@Override
				public void add(byte k) {
					if (this.last == -1) {
						throw new IllegalStateException();
					} else {
						ByteSubList.this.add(this.pos++, k);
						this.last = -1;

						assert ByteSubList.this.assertRange();
					}
				}

				@Override
				public void set(byte k) {
					if (this.last == -1) {
						throw new IllegalStateException();
					} else {
						ByteSubList.this.set(this.last, k);
					}
				}

				@Override
				public void remove() {
					if (this.last == -1) {
						throw new IllegalStateException();
					} else {
						ByteSubList.this.removeByte(this.last);
						if (this.last < this.pos) {
							this.pos--;
						}

						this.last = -1;

						assert ByteSubList.this.assertRange();
					}
				}
			};
		}

		@Override
		public ByteList subList(int from, int to) {
			this.ensureIndex(from);
			this.ensureIndex(to);
			if (from > to) {
				throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
			} else {
				return new AbstractByteList.ByteSubList(this, from, to);
			}
		}

		@Override
		public boolean rem(byte k) {
			int index = this.indexOf(k);
			if (index == -1) {
				return false;
			} else {
				this.to--;
				this.l.removeByte(this.from + index);

				assert this.assertRange();

				return true;
			}
		}

		@Override
		public boolean addAll(int index, ByteCollection c) {
			this.ensureIndex(index);
			return super.addAll(index, c);
		}

		@Override
		public boolean addAll(int index, ByteList l) {
			this.ensureIndex(index);
			return super.addAll(index, l);
		}
	}
}
