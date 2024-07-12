package it.unimi.dsi.fastutil.booleans;

import java.util.Set;

public abstract class AbstractBooleanSet extends AbstractBooleanCollection implements Cloneable, BooleanSet {
	protected AbstractBooleanSet() {
	}

	@Override
	public abstract BooleanIterator iterator();

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
		BooleanIterator i = this.iterator();

		while (n-- != 0) {
			boolean k = i.nextBoolean();
			h += k ? 1231 : 1237;
		}

		return h;
	}

	@Override
	public boolean remove(boolean k) {
		return super.rem(k);
	}

	@Deprecated
	@Override
	public boolean rem(boolean k) {
		return this.remove(k);
	}
}
