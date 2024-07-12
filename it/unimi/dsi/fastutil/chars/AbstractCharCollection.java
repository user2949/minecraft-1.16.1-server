package it.unimi.dsi.fastutil.chars;

import java.util.AbstractCollection;

public abstract class AbstractCharCollection extends AbstractCollection<Character> implements CharCollection {
	protected AbstractCharCollection() {
	}

	@Override
	public abstract CharIterator iterator();

	@Override
	public boolean add(char k) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean contains(char k) {
		CharIterator iterator = this.iterator();

		while (iterator.hasNext()) {
			if (k == iterator.nextChar()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean rem(char k) {
		CharIterator iterator = this.iterator();

		while (iterator.hasNext()) {
			if (k == iterator.nextChar()) {
				iterator.remove();
				return true;
			}
		}

		return false;
	}

	@Deprecated
	@Override
	public boolean add(Character key) {
		return CharCollection.super.add(key);
	}

	@Deprecated
	@Override
	public boolean contains(Object key) {
		return CharCollection.super.contains(key);
	}

	@Deprecated
	@Override
	public boolean remove(Object key) {
		return CharCollection.super.remove(key);
	}

	@Override
	public char[] toArray(char[] a) {
		if (a == null || a.length < this.size()) {
			a = new char[this.size()];
		}

		CharIterators.unwrap(this.iterator(), a);
		return a;
	}

	@Override
	public char[] toCharArray() {
		return this.toArray((char[])null);
	}

	@Deprecated
	@Override
	public char[] toCharArray(char[] a) {
		return this.toArray(a);
	}

	@Override
	public boolean addAll(CharCollection c) {
		boolean retVal = false;
		CharIterator i = c.iterator();

		while (i.hasNext()) {
			if (this.add(i.nextChar())) {
				retVal = true;
			}
		}

		return retVal;
	}

	@Override
	public boolean containsAll(CharCollection c) {
		CharIterator i = c.iterator();

		while (i.hasNext()) {
			if (!this.contains(i.nextChar())) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean removeAll(CharCollection c) {
		boolean retVal = false;
		CharIterator i = c.iterator();

		while (i.hasNext()) {
			if (this.rem(i.nextChar())) {
				retVal = true;
			}
		}

		return retVal;
	}

	@Override
	public boolean retainAll(CharCollection c) {
		boolean retVal = false;
		CharIterator i = this.iterator();

		while (i.hasNext()) {
			if (!c.contains(i.nextChar())) {
				i.remove();
				retVal = true;
			}
		}

		return retVal;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		CharIterator i = this.iterator();
		int n = this.size();
		boolean first = true;
		s.append("{");

		while (n-- != 0) {
			if (first) {
				first = false;
			} else {
				s.append(", ");
			}

			char k = i.nextChar();
			s.append(String.valueOf(k));
		}

		s.append("}");
		return s.toString();
	}
}
