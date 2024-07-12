import it.unimi.dsi.fastutil.doubles.AbstractDoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleList;

public class dfb extends AbstractDoubleList implements dey {
	private final DoubleList a;
	private final DoubleList b;
	private final boolean c;

	public dfb(DoubleList doubleList1, DoubleList doubleList2, boolean boolean3) {
		this.a = doubleList1;
		this.b = doubleList2;
		this.c = boolean3;
	}

	public int size() {
		return this.a.size() + this.b.size();
	}

	@Override
	public boolean a(dey.a a) {
		return this.c ? this.b((integer2, integer3, integer4) -> a.merge(integer3, integer2, integer4)) : this.b(a);
	}

	private boolean b(dey.a a) {
		int integer3 = this.a.size() - 1;

		for (int integer4 = 0; integer4 < integer3; integer4++) {
			if (!a.merge(integer4, -1, integer4)) {
				return false;
			}
		}

		if (!a.merge(integer3, -1, integer3)) {
			return false;
		} else {
			for (int integer4x = 0; integer4x < this.b.size(); integer4x++) {
				if (!a.merge(integer3, integer4x, integer3 + 1 + integer4x)) {
					return false;
				}
			}

			return true;
		}
	}

	@Override
	public double getDouble(int integer) {
		return integer < this.a.size() ? this.a.getDouble(integer) : this.b.getDouble(integer - this.a.size());
	}

	@Override
	public DoubleList a() {
		return this;
	}
}
