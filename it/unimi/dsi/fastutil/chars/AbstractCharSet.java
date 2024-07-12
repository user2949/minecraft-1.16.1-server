package it.unimi.dsi.fastutil.chars;

import java.util.Set;

public abstract class AbstractCharSet extends AbstractCharCollection implements Cloneable, CharSet {
	protected AbstractCharSet() {
	}

	@Override
	public abstract CharIterator iterator();

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
		CharIterator i = this.iterator();

		while (n-- != 0) {
			char k = i.nextChar();
			h += k;
		}

		return h;
	}

	@Override
	public boolean remove(char k) {
		return super.rem(k);
	}

	@Deprecated
	@Override
	public boolean rem(char k) {
		return this.remove(k);
	}
}
