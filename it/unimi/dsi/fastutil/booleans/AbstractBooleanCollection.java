package it.unimi.dsi.fastutil.booleans;

import java.util.AbstractCollection;

public abstract class AbstractBooleanCollection extends AbstractCollection<Boolean> implements BooleanCollection {
	protected AbstractBooleanCollection() {
	}

	@Override
	public abstract BooleanIterator iterator();

	@Override
	public boolean add(boolean k) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean contains(boolean k) {
		BooleanIterator iterator = this.iterator();

		while (iterator.hasNext()) {
			if (k == iterator.nextBoolean()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean rem(boolean k) {
		BooleanIterator iterator = this.iterator();

		while (iterator.hasNext()) {
			if (k == iterator.nextBoolean()) {
				iterator.remove();
				return true;
			}
		}

		return false;
	}

	@Deprecated
	@Override
	public boolean add(Boolean key) {
		return BooleanCollection.super.add(key);
	}

	@Deprecated
	@Override
	public boolean contains(Object key) {
		return BooleanCollection.super.contains(key);
	}

	@Deprecated
	@Override
	public boolean remove(Object key) {
		return BooleanCollection.super.remove(key);
	}

	@Override
	public boolean[] toArray(boolean[] a) {
		if (a == null || a.length < this.size()) {
			a = new boolean[this.size()];
		}

		BooleanIterators.unwrap(this.iterator(), a);
		return a;
	}

	@Override
	public boolean[] toBooleanArray() {
		return this.toArray((boolean[])null);
	}

	@Deprecated
	@Override
	public boolean[] toBooleanArray(boolean[] a) {
		return this.toArray(a);
	}

	@Override
	public boolean addAll(BooleanCollection c) {
		boolean retVal = false;
		BooleanIterator i = c.iterator();

		while (i.hasNext()) {
			if (this.add(i.nextBoolean())) {
				retVal = true;
			}
		}

		return retVal;
	}

	@Override
	public boolean containsAll(BooleanCollection c) {
		BooleanIterator i = c.iterator();

		while (i.hasNext()) {
			if (!this.contains(i.nextBoolean())) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean removeAll(BooleanCollection c) {
		boolean retVal = false;
		BooleanIterator i = c.iterator();

		while (i.hasNext()) {
			if (this.rem(i.nextBoolean())) {
				retVal = true;
			}
		}

		return retVal;
	}

	@Override
	public boolean retainAll(BooleanCollection c) {
		boolean retVal = false;
		BooleanIterator i = this.iterator();

		while (i.hasNext()) {
			if (!c.contains(i.nextBoolean())) {
				i.remove();
				retVal = true;
			}
		}

		return retVal;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		BooleanIterator i = this.iterator();
		int n = this.size();
		boolean first = true;
		s.append("{");

		while (n-- != 0) {
			if (first) {
				first = false;
			} else {
				s.append(", ");
			}

			boolean k = i.nextBoolean();
			s.append(String.valueOf(k));
		}

		s.append("}");
		return s.toString();
	}
}
