import it.unimi.dsi.fastutil.doubles.AbstractDoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleList;

public class dfc extends AbstractDoubleList {
	private final DoubleList a;
	private final double b;

	public dfc(DoubleList doubleList, double double2) {
		this.a = doubleList;
		this.b = double2;
	}

	@Override
	public double getDouble(int integer) {
		return this.a.getDouble(integer) + this.b;
	}

	public int size() {
		return this.a.size();
	}
}
