package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class AbstractCharBigList extends AbstractCharCollection implements CharBigList, CharStack {
	protected AbstractCharBigList() {
	}

	protected void ensureIndex(long index) {
		if (index < 0L) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
		} else if (index > this.size64()) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + this.size64() + ")");
		}
	}

	protected void ensureRestrictedIndex(long index) {
		if (index < 0L) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
		} else if (index >= this.size64()) {
			throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size64() + ")");
		}
	}

	@Override
	public void add(long index, char k) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(char k) {
		this.add(this.size64(), k);
		return true;
	}

	@Override
	public char removeChar(long i) {
		throw new UnsupportedOperationException();
	}

	@Override
	public char set(long index, char k) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(long index, Collection<? extends Character> c) {
		this.ensureIndex(index);
		Iterator<? extends Character> i = c.iterator();
		boolean retVal = i.hasNext();

		while (i.hasNext()) {
			this.add(index++, (Character)i.next());
		}

		return retVal;
	}

	public boolean addAll(Collection<? extends Character> c) {
		return this.addAll(this.size64(), c);
	}

	@Override
	public CharBigListIterator iterator() {
		return this.listIterator();
	}

	@Override
	public CharBigListIterator listIterator() {
		return this.listIterator(0L);
	}

	@Override
	public CharBigListIterator listIterator(long index) {
		this.ensureIndex(index);
		return new CharBigListIterator() {
			long pos = index;
			long last = -1L;

			public boolean hasNext() {
				return this.pos < AbstractCharBigList.this.size64();
			}

			@Override
			public boolean hasPrevious() {
				return this.pos > 0L;
			}

			@Override
			public char nextChar() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				} else {
					return AbstractCharBigList.this.getChar(this.last = this.pos++);
				}
			}

			@Override
			public char previousChar() {
				if (!this.hasPrevious()) {
					throw new NoSuchElementException();
				} else {
					return AbstractCharBigList.this.getChar(this.last = --this.pos);
				}
			}

			@Override
			public long nextIndex() {
				return this.pos;
			}

			@Override
			public long previousIndex() {
				return this.pos - 1L;
			}

			@Override
			public void add(char k) {
				AbstractCharBigList.this.add(this.pos++, k);
				this.last = -1L;
			}

			@Override
			public void set(char k) {
				if (this.last == -1L) {
					throw new IllegalStateException();
				} else {
					AbstractCharBigList.this.set(this.last, k);
				}
			}

			public void remove() {
				if (this.last == -1L) {
					throw new IllegalStateException();
				} else {
					AbstractCharBigList.this.removeChar(this.last);
					if (this.last < this.pos) {
						this.pos--;
					}

					this.last = -1L;
				}
			}
		};
	}

	@Override
	public boolean contains(char k) {
		return this.indexOf(k) >= 0L;
	}

	@Override
	public long indexOf(char k) {
		CharBigListIterator i = this.listIterator();

		while (i.hasNext()) {
			char e = i.nextChar();
			if (k == e) {
				return i.previousIndex();
			}
		}

		return -1L;
	}

	@Override
	public long lastIndexOf(char k) {
		CharBigListIterator i = this.listIterator(this.size64());

		while (i.hasPrevious()) {
			char e = i.previousChar();
			if (k == e) {
				return i.nextIndex();
			}
		}

		return -1L;
	}

	@Override
	public void size(long size) {
		long i = this.size64();
		if (size > i) {
			while (i++ < size) {
				this.add('\u0000');
			}
		} else {
			while (i-- != size) {
				this.remove(i);
			}
		}
	}

	@Override
	public CharBigList subList(long from, long to) {
		this.ensureIndex(from);
		this.ensureIndex(to);
		if (from > to) {
			throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
		} else {
			return new AbstractCharBigList.CharSubList(this, from, to);
		}
	}

	@Override
	public void removeElements(long from, long to) {
		this.ensureIndex(to);
		CharBigListIterator i = this.listIterator(from);
		long n = to - from;
		if (n < 0L) {
			throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
		} else {
			while (n-- != 0L) {
				i.nextChar();
				i.remove();
			}
		}
	}

	@Override
	public void addElements(long index, char[][] a, long offset, long length) {
		this.ensureIndex(index);
		CharBigArrays.ensureOffsetLength(a, offset, length);

		while (length-- != 0L) {
			this.add(index++, CharBigArrays.get(a, offset++));
		}
	}

	@Override
	public void addElements(long index, char[][] a) {
		this.addElements(index, a, 0L, CharBigArrays.length(a));
	}

	@Override
	public void getElements(long from, char[][] a, long offset, long length) {
		CharBigListIterator i = this.listIterator(from);
		CharBigArrays.ensureOffsetLength(a, offset, length);
		if (from + length > this.size64()) {
			throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + this.size64() + ")");
		} else {
			while (length-- != 0L) {
				CharBigArrays.set(a, offset++, i.nextChar());
			}
		}
	}

	public void clear() {
		this.removeElements(0L, this.size64());
	}

	@Deprecated
	@Override
	public int size() {
		return (int)Math.min(2147483647L, this.size64());
	}

	private boolean valEquals(Object a, Object b) {
		return a == null ? b == null : a.equals(b);
	}

	public int hashCode() {
		CharIterator i = this.iterator();
		int h = 1;
		long s = this.size64();

		while (s-- != 0L) {
			char k = i.nextChar();
			h = 31 * h + k;
		}

		return h;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof BigList)) {
			return false;
		} else {
			BigList<?> l = (BigList<?>)o;
			long s = this.size64();
			if (s != l.size64()) {
				return false;
			} else if (l instanceof CharBigList) {
				CharBigListIterator i1 = this.listIterator();
				CharBigListIterator i2 = ((CharBigList)l).listIterator();

				while (s-- != 0L) {
					if (i1.nextChar() != i2.nextChar()) {
						return false;
					}
				}

				return true;
			} else {
				BigListIterator<?> i1 = this.listIterator();
				BigListIterator<?> i2 = l.listIterator();

				while (s-- != 0L) {
					if (!this.valEquals(i1.next(), i2.next())) {
						return false;
					}
				}

				return true;
			}
		}
	}

	public int compareTo(BigList<? extends Character> l) {
		if (l == this) {
			return 0;
		} else if (l instanceof CharBigList) {
			CharBigListIterator i1 = this.listIterator();
			CharBigListIterator i2 = ((CharBigList)l).listIterator();

			while (i1.hasNext() && i2.hasNext()) {
				char e1 = i1.nextChar();
				char e2 = i2.nextChar();
				int r;
				if ((r = Character.compare(e1, e2)) != 0) {
					return r;
				}
			}

			return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
		} else {
			BigListIterator<? extends Character> i1 = this.listIterator();
			BigListIterator<? extends Character> i2 = l.listIterator();

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
	public void push(char o) {
		this.add(o);
	}

	@Override
	public char popChar() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return this.removeChar(this.size64() - 1L);
		}
	}

	@Override
	public char topChar() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return this.getChar(this.size64() - 1L);
		}
	}

	@Override
	public char peekChar(int i) {
		return this.getChar(this.size64() - 1L - (long)i);
	}

	@Override
	public boolean rem(char k) {
		long index = this.indexOf(k);
		if (index == -1L) {
			return false;
		} else {
			this.removeChar(index);
			return true;
		}
	}

	@Override
	public boolean addAll(long index, CharCollection c) {
		return this.addAll(index, c);
	}

	@Override
	public boolean addAll(long index, CharBigList l) {
		return this.addAll(index, l);
	}

	@Override
	public boolean addAll(CharCollection c) {
		return this.addAll(this.size64(), c);
	}

	@Override
	public boolean addAll(CharBigList l) {
		return this.addAll(this.size64(), l);
	}

	@Deprecated
	@Override
	public void add(long index, Character ok) {
		this.add(index, ok.charValue());
	}

	@Deprecated
	@Override
	public Character set(long index, Character ok) {
		return this.set(index, ok.charValue());
	}

	@Deprecated
	@Override
	public Character get(long index) {
		return this.getChar(index);
	}

	@Deprecated
	@Override
	public long indexOf(Object ok) {
		return this.indexOf(((Character)ok).charValue());
	}

	@Deprecated
	@Override
	public long lastIndexOf(Object ok) {
		return this.lastIndexOf(((Character)ok).charValue());
	}

	@Deprecated
	@Override
	public Character remove(long index) {
		return this.removeChar(index);
	}

	@Deprecated
	@Override
	public void push(Character o) {
		this.push(o.charValue());
	}

	@Deprecated
	@Override
	public Character pop() {
		return this.popChar();
	}

	@Deprecated
	@Override
	public Character top() {
		return this.topChar();
	}

	@Deprecated
	@Override
	public Character peek(int i) {
		return this.peekChar(i);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		CharIterator i = this.iterator();
		long n = this.size64();
		boolean first = true;
		s.append("[");

		while (n-- != 0L) {
			if (first) {
				first = false;
			} else {
				s.append(", ");
			}

			char k = i.nextChar();
			s.append(String.valueOf(k));
		}

		s.append("]");
		return s.toString();
	}

	public static class CharSubList extends AbstractCharBigList implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final CharBigList l;
		protected final long from;
		protected long to;

		public CharSubList(CharBigList l, long from, long to) {
			this.l = l;
			this.from = from;
			this.to = to;
		}

		private boolean assertRange() {
			assert this.from <= this.l.size64();

			assert this.to <= this.l.size64();

			assert this.to >= this.from;

			return true;
		}

		@Override
		public boolean add(char k) {
			this.l.add(this.to, k);
			this.to++;

			assert this.assertRange();

			return true;
		}

		@Override
		public void add(long index, char k) {
			this.ensureIndex(index);
			this.l.add(this.from + index, k);
			this.to++;

			assert this.assertRange();
		}

		@Override
		public boolean addAll(long index, Collection<? extends Character> c) {
			this.ensureIndex(index);
			this.to = this.to + (long)c.size();
			return this.l.addAll(this.from + index, c);
		}

		@Override
		public char getChar(long index) {
			this.ensureRestrictedIndex(index);
			return this.l.getChar(this.from + index);
		}

		@Override
		public char removeChar(long index) {
			this.ensureRestrictedIndex(index);
			this.to--;
			return this.l.removeChar(this.from + index);
		}

		@Override
		public char set(long index, char k) {
			this.ensureRestrictedIndex(index);
			return this.l.set(this.from + index, k);
		}

		@Override
		public long size64() {
			return this.to - this.from;
		}

		@Override
		public void getElements(long from, char[][] a, long offset, long length) {
			this.ensureIndex(from);
			if (from + length > this.size64()) {
				throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + this.size64() + ")");
			} else {
				this.l.getElements(this.from + from, a, offset, length);
			}
		}

		@Override
		public void removeElements(long from, long to) {
			this.ensureIndex(from);
			this.ensureIndex(to);
			this.l.removeElements(this.from + from, this.from + to);
			this.to -= to - from;

			assert this.assertRange();
		}

		@Override
		public void addElements(long index, char[][] a, long offset, long length) {
			this.ensureIndex(index);
			this.l.addElements(this.from + index, a, offset, length);
			this.to += length;

			assert this.assertRange();
		}

		@Override
		public CharBigListIterator listIterator(long index) {
			this.ensureIndex(index);
			return new CharBigListIterator() {
				long pos = index;
				long last = -1L;

				public boolean hasNext() {
					return this.pos < CharSubList.this.size64();
				}

				@Override
				public boolean hasPrevious() {
					return this.pos > 0L;
				}

				@Override
				public char nextChar() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						return CharSubList.this.l.getChar(CharSubList.this.from + (this.last = this.pos++));
					}
				}

				@Override
				public char previousChar() {
					if (!this.hasPrevious()) {
						throw new NoSuchElementException();
					} else {
						return CharSubList.this.l.getChar(CharSubList.this.from + (this.last = --this.pos));
					}
				}

				@Override
				public long nextIndex() {
					return this.pos;
				}

				@Override
				public long previousIndex() {
					return this.pos - 1L;
				}

				@Override
				public void add(char k) {
					if (this.last == -1L) {
						throw new IllegalStateException();
					} else {
						CharSubList.this.add(this.pos++, k);
						this.last = -1L;

						assert CharSubList.this.assertRange();
					}
				}

				@Override
				public void set(char k) {
					if (this.last == -1L) {
						throw new IllegalStateException();
					} else {
						CharSubList.this.set(this.last, k);
					}
				}

				public void remove() {
					if (this.last == -1L) {
						throw new IllegalStateException();
					} else {
						CharSubList.this.removeChar(this.last);
						if (this.last < this.pos) {
							this.pos--;
						}

						this.last = -1L;

						assert CharSubList.this.assertRange();
					}
				}
			};
		}

		@Override
		public CharBigList subList(long from, long to) {
			this.ensureIndex(from);
			this.ensureIndex(to);
			if (from > to) {
				throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
			} else {
				return new AbstractCharBigList.CharSubList(this, from, to);
			}
		}

		@Override
		public boolean rem(char k) {
			long index = this.indexOf(k);
			if (index == -1L) {
				return false;
			} else {
				this.to--;
				this.l.removeChar(this.from + index);

				assert this.assertRange();

				return true;
			}
		}

		@Override
		public boolean addAll(long index, CharCollection c) {
			this.ensureIndex(index);
			return super.addAll(index, c);
		}

		@Override
		public boolean addAll(long index, CharBigList l) {
			this.ensureIndex(index);
			return super.addAll(index, l);
		}
	}
}
