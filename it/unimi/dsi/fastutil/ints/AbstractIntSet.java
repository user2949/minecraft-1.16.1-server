package it.unimi.dsi.fastutil.ints;

import java.util.Set;

public abstract class AbstractIntSet extends AbstractIntCollection implements Cloneable, IntSet {
	protected AbstractIntSet() {
	}

	@Override
	public abstract IntIterator iterator();

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof Set)) {
			return false;
		} else {
			Set<?> s = (Set<?>)o;
			return s.size() != this.size() ? false : this.containsAll(s);
		}
	}

	public int hashCode() {
		int h = 0;
		int n = this.size();
		IntIterator i = this.iterator();

		while (n-- != 0) {
			int k = i.nextInt();
			h += k;
		}

		return h;
	}

	@Override
	public boolean remove(int k) {
		return super.rem(k);
	}

	@Deprecated
	@Override
	public boolean rem(int k) {
		return this.remove(k);
	}
}
