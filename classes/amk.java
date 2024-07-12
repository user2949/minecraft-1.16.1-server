public final class amk implements Comparable<amk> {
	public final double a;
	public final double b;
	public final long c;
	public final String d;

	public amk(String string, double double2, double double3, long long4) {
		this.d = string;
		this.a = double2;
		this.b = double3;
		this.c = long4;
	}

	public int compareTo(amk amk) {
		if (amk.a < this.a) {
			return -1;
		} else {
			return amk.a > this.a ? 1 : amk.d.compareTo(this.d);
		}
	}
}
