import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.ints.IntArrayList;

public final class dez implements dey {
	private final DoubleArrayList a;
	private final IntArrayList b;
	private final IntArrayList c;

	protected dez(DoubleList doubleList1, DoubleList doubleList2, boolean boolean3, boolean boolean4) {
		int integer6 = 0;
		int integer7 = 0;
		double double8 = Double.NaN;
		int integer10 = doubleList1.size();
		int integer11 = doubleList2.size();
		int integer12 = integer10 + integer11;
		this.a = new DoubleArrayList(integer12);
		this.b = new IntArrayList(integer12);
		this.c = new IntArrayList(integer12);

		while (true) {
			boolean boolean13 = integer6 < integer10;
			boolean boolean14 = integer7 < integer11;
			if (!boolean13 && !boolean14) {
				if (this.a.isEmpty()) {
					this.a.add(Math.min(doubleList1.getDouble(integer10 - 1), doubleList2.getDouble(integer11 - 1)));
				}

				return;
			}

			boolean boolean15 = boolean13 && (!boolean14 || doubleList1.getDouble(integer6) < doubleList2.getDouble(integer7) + 1.0E-7);
			double double16 = boolean15 ? doubleList1.getDouble(integer6++) : doubleList2.getDouble(integer7++);
			if ((integer6 != 0 && boolean13 || boolean15 || boolean4) && (integer7 != 0 && boolean14 || !boolean15 || boolean3)) {
				if (!(double8 >= double16 - 1.0E-7)) {
					this.b.add(integer6 - 1);
					this.c.add(integer7 - 1);
					this.a.add(double16);
					double8 = double16;
				} else if (!this.a.isEmpty()) {
					this.b.set(this.b.size() - 1, integer6 - 1);
					this.c.set(this.c.size() - 1, integer7 - 1);
				}
			}
		}
	}

	@Override
	public boolean a(dey.a a) {
		for (int integer3 = 0; integer3 < this.a.size() - 1; integer3++) {
			if (!a.merge(this.b.getInt(integer3), this.c.getInt(integer3), integer3)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public DoubleList a() {
		return this.a;
	}
}
