public class aps {
	private final double a;
	private boolean b;
	private final String c;

	protected aps(String string, double double2) {
		this.a = double2;
		this.c = string;
	}

	public double a() {
		return this.a;
	}

	public boolean b() {
		return this.b;
	}

	public aps a(boolean boolean1) {
		this.b = boolean1;
		return this;
	}

	public double a(double double1) {
		return double1;
	}

	public String c() {
		return this.c;
	}
}
