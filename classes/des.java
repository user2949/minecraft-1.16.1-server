import it.unimi.dsi.fastutil.doubles.AbstractDoubleList;

public class des extends AbstractDoubleList {
	private final int a;

	des(int integer) {
		this.a = integer;
	}

	@Override
	public double getDouble(int integer) {
		return (double)integer / (double)this.a;
	}

	public int size() {
		return this.a + 1;
	}
}
