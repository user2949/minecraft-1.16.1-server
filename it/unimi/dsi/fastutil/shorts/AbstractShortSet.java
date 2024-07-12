package it.unimi.dsi.fastutil.shorts;

import java.util.Set;

public abstract class AbstractShortSet extends AbstractShortCollection implements Cloneable, ShortSet {
	protected AbstractShortSet() {
	}

	@Override
	public abstract ShortIterator iterator();

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
		ShortIterator i = this.iterator();

		while (n-- != 0) {
			short k = i.nextShort();
			h += k;
		}

		return h;
	}

	@Override
	public boolean remove(short k) {
		return super.rem(k);
	}

	@Deprecated
	@Override
	public boolean rem(short k) {
		return this.remove(k);
	}
}
