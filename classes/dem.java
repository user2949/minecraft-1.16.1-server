import java.util.EnumSet;

public class dem implements gj {
	public static final dem a = new dem(0.0, 0.0, 0.0);
	public final double b;
	public final double c;
	public final double d;

	public static dem a(gr gr) {
		return new dem((double)gr.u() + 0.5, (double)gr.v() + 0.5, (double)gr.w() + 0.5);
	}

	public static dem b(gr gr) {
		return new dem((double)gr.u(), (double)gr.v(), (double)gr.w());
	}

	public static dem c(gr gr) {
		return new dem((double)gr.u() + 0.5, (double)gr.v(), (double)gr.w() + 0.5);
	}

	public static dem a(gr gr, double double2) {
		return new dem((double)gr.u() + 0.5, (double)gr.v() + double2, (double)gr.w() + 0.5);
	}

	public dem(double double1, double double2, double double3) {
		this.b = double1;
		this.c = double2;
		this.d = double3;
	}

	public dem(g g) {
		this((double)g.a(), (double)g.b(), (double)g.c());
	}

	public dem a(dem dem) {
		return new dem(dem.b - this.b, dem.c - this.c, dem.d - this.d);
	}

	public dem d() {
		double double2 = (double)aec.a(this.b * this.b + this.c * this.c + this.d * this.d);
		return double2 < 1.0E-4 ? a : new dem(this.b / double2, this.c / double2, this.d / double2);
	}

	public double b(dem dem) {
		return this.b * dem.b + this.c * dem.c + this.d * dem.d;
	}

	public dem c(dem dem) {
		return new dem(this.c * dem.d - this.d * dem.c, this.d * dem.b - this.b * dem.d, this.b * dem.c - this.c * dem.b);
	}

	public dem d(dem dem) {
		return this.a(dem.b, dem.c, dem.d);
	}

	public dem a(double double1, double double2, double double3) {
		return this.b(-double1, -double2, -double3);
	}

	public dem e(dem dem) {
		return this.b(dem.b, dem.c, dem.d);
	}

	public dem b(double double1, double double2, double double3) {
		return new dem(this.b + double1, this.c + double2, this.d + double3);
	}

	public boolean a(gj gj, double double2) {
		return this.c(gj.a(), gj.b(), gj.c()) < double2 * double2;
	}

	public double f(dem dem) {
		double double3 = dem.b - this.b;
		double double5 = dem.c - this.c;
		double double7 = dem.d - this.d;
		return (double)aec.a(double3 * double3 + double5 * double5 + double7 * double7);
	}

	public double g(dem dem) {
		double double3 = dem.b - this.b;
		double double5 = dem.c - this.c;
		double double7 = dem.d - this.d;
		return double3 * double3 + double5 * double5 + double7 * double7;
	}

	public double c(double double1, double double2, double double3) {
		double double8 = double1 - this.b;
		double double10 = double2 - this.c;
		double double12 = double3 - this.d;
		return double8 * double8 + double10 * double10 + double12 * double12;
	}

	public dem a(double double1) {
		return this.d(double1, double1, double1);
	}

	public dem h(dem dem) {
		return this.d(dem.b, dem.c, dem.d);
	}

	public dem d(double double1, double double2, double double3) {
		return new dem(this.b * double1, this.c * double2, this.d * double3);
	}

	public double f() {
		return (double)aec.a(this.b * this.b + this.c * this.c + this.d * this.d);
	}

	public double g() {
		return this.b * this.b + this.c * this.c + this.d * this.d;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof dem)) {
			return false;
		} else {
			dem dem3 = (dem)object;
			if (Double.compare(dem3.b, this.b) != 0) {
				return false;
			} else {
				return Double.compare(dem3.c, this.c) != 0 ? false : Double.compare(dem3.d, this.d) == 0;
			}
		}
	}

	public int hashCode() {
		long long3 = Double.doubleToLongBits(this.b);
		int integer2 = (int)(long3 ^ long3 >>> 32);
		long3 = Double.doubleToLongBits(this.c);
		integer2 = 31 * integer2 + (int)(long3 ^ long3 >>> 32);
		long3 = Double.doubleToLongBits(this.d);
		return 31 * integer2 + (int)(long3 ^ long3 >>> 32);
	}

	public String toString() {
		return "(" + this.b + ", " + this.c + ", " + this.d + ")";
	}

	public dem a(float float1) {
		float float3 = aec.b(float1);
		float float4 = aec.a(float1);
		double double5 = this.b;
		double double7 = this.c * (double)float3 + this.d * (double)float4;
		double double9 = this.d * (double)float3 - this.c * (double)float4;
		return new dem(double5, double7, double9);
	}

	public dem b(float float1) {
		float float3 = aec.b(float1);
		float float4 = aec.a(float1);
		double double5 = this.b * (double)float3 + this.d * (double)float4;
		double double7 = this.c;
		double double9 = this.d * (double)float3 - this.b * (double)float4;
		return new dem(double5, double7, double9);
	}

	public dem a(EnumSet<fz.a> enumSet) {
		double double3 = enumSet.contains(fz.a.X) ? (double)aec.c(this.b) : this.b;
		double double5 = enumSet.contains(fz.a.Y) ? (double)aec.c(this.c) : this.c;
		double double7 = enumSet.contains(fz.a.Z) ? (double)aec.c(this.d) : this.d;
		return new dem(double3, double5, double7);
	}

	public double a(fz.a a) {
		return a.a(this.b, this.c, this.d);
	}

	@Override
	public final double a() {
		return this.b;
	}

	@Override
	public final double b() {
		return this.c;
	}

	@Override
	public final double c() {
		return this.d;
	}
}
