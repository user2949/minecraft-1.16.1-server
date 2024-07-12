public class apz extends aps {
	private final double a;
	private final double b;

	public apz(String string, double double2, double double3, double double4) {
		super(string, double2);
		this.a = double3;
		this.b = double4;
		if (double3 > double4) {
			throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!");
		} else if (double2 < double3) {
			throw new IllegalArgumentException("Default value cannot be lower than minimum value!");
		} else if (double2 > double4) {
			throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
		}
	}

	@Override
	public double a(double double1) {
		return aec.a(double1, this.a, this.b);
	}
}
