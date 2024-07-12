import it.unimi.dsi.fastutil.doubles.DoubleList;

public class dex implements dey {
	private final DoubleList a;

	public dex(DoubleList doubleList) {
		this.a = doubleList;
	}

	@Override
	public boolean a(dey.a a) {
		for (int integer3 = 0; integer3 <= this.a.size(); integer3++) {
			if (!a.merge(integer3, integer3, integer3)) {
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
