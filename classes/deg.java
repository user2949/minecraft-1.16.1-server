import java.util.Optional;
import javax.annotation.Nullable;

public class deg {
	public final double a;
	public final double b;
	public final double c;
	public final double d;
	public final double e;
	public final double f;

	public deg(double double1, double double2, double double3, double double4, double double5, double double6) {
		this.a = Math.min(double1, double4);
		this.b = Math.min(double2, double5);
		this.c = Math.min(double3, double6);
		this.d = Math.max(double1, double4);
		this.e = Math.max(double2, double5);
		this.f = Math.max(double3, double6);
	}

	public deg(fu fu) {
		this((double)fu.u(), (double)fu.v(), (double)fu.w(), (double)(fu.u() + 1), (double)(fu.v() + 1), (double)(fu.w() + 1));
	}

	public deg(fu fu1, fu fu2) {
		this((double)fu1.u(), (double)fu1.v(), (double)fu1.w(), (double)fu2.u(), (double)fu2.v(), (double)fu2.w());
	}

	public deg(dem dem1, dem dem2) {
		this(dem1.b, dem1.c, dem1.d, dem2.b, dem2.c, dem2.d);
	}

	public static deg a(ctd ctd) {
		return new deg((double)ctd.a, (double)ctd.b, (double)ctd.c, (double)(ctd.d + 1), (double)(ctd.e + 1), (double)(ctd.f + 1));
	}

	public static deg a(dem dem) {
		return new deg(dem.b, dem.c, dem.d, dem.b + 1.0, dem.c + 1.0, dem.d + 1.0);
	}

	public double a(fz.a a) {
		return a.a(this.a, this.b, this.c);
	}

	public double b(fz.a a) {
		return a.a(this.d, this.e, this.f);
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof deg)) {
			return false;
		} else {
			deg deg3 = (deg)object;
			if (Double.compare(deg3.a, this.a) != 0) {
				return false;
			} else if (Double.compare(deg3.b, this.b) != 0) {
				return false;
			} else if (Double.compare(deg3.c, this.c) != 0) {
				return false;
			} else if (Double.compare(deg3.d, this.d) != 0) {
				return false;
			} else {
				return Double.compare(deg3.e, this.e) != 0 ? false : Double.compare(deg3.f, this.f) == 0;
			}
		}
	}

	public int hashCode() {
		long long2 = Double.doubleToLongBits(this.a);
		int integer4 = (int)(long2 ^ long2 >>> 32);
		long2 = Double.doubleToLongBits(this.b);
		integer4 = 31 * integer4 + (int)(long2 ^ long2 >>> 32);
		long2 = Double.doubleToLongBits(this.c);
		integer4 = 31 * integer4 + (int)(long2 ^ long2 >>> 32);
		long2 = Double.doubleToLongBits(this.d);
		integer4 = 31 * integer4 + (int)(long2 ^ long2 >>> 32);
		long2 = Double.doubleToLongBits(this.e);
		integer4 = 31 * integer4 + (int)(long2 ^ long2 >>> 32);
		long2 = Double.doubleToLongBits(this.f);
		return 31 * integer4 + (int)(long2 ^ long2 >>> 32);
	}

	public deg a(double double1, double double2, double double3) {
		double double8 = this.a;
		double double10 = this.b;
		double double12 = this.c;
		double double14 = this.d;
		double double16 = this.e;
		double double18 = this.f;
		if (double1 < 0.0) {
			double8 -= double1;
		} else if (double1 > 0.0) {
			double14 -= double1;
		}

		if (double2 < 0.0) {
			double10 -= double2;
		} else if (double2 > 0.0) {
			double16 -= double2;
		}

		if (double3 < 0.0) {
			double12 -= double3;
		} else if (double3 > 0.0) {
			double18 -= double3;
		}

		return new deg(double8, double10, double12, double14, double16, double18);
	}

	public deg b(dem dem) {
		return this.b(dem.b, dem.c, dem.d);
	}

	public deg b(double double1, double double2, double double3) {
		double double8 = this.a;
		double double10 = this.b;
		double double12 = this.c;
		double double14 = this.d;
		double double16 = this.e;
		double double18 = this.f;
		if (double1 < 0.0) {
			double8 += double1;
		} else if (double1 > 0.0) {
			double14 += double1;
		}

		if (double2 < 0.0) {
			double10 += double2;
		} else if (double2 > 0.0) {
			double16 += double2;
		}

		if (double3 < 0.0) {
			double12 += double3;
		} else if (double3 > 0.0) {
			double18 += double3;
		}

		return new deg(double8, double10, double12, double14, double16, double18);
	}

	public deg c(double double1, double double2, double double3) {
		double double8 = this.a - double1;
		double double10 = this.b - double2;
		double double12 = this.c - double3;
		double double14 = this.d + double1;
		double double16 = this.e + double2;
		double double18 = this.f + double3;
		return new deg(double8, double10, double12, double14, double16, double18);
	}

	public deg g(double double1) {
		return this.c(double1, double1, double1);
	}

	public deg a(deg deg) {
		double double3 = Math.max(this.a, deg.a);
		double double5 = Math.max(this.b, deg.b);
		double double7 = Math.max(this.c, deg.c);
		double double9 = Math.min(this.d, deg.d);
		double double11 = Math.min(this.e, deg.e);
		double double13 = Math.min(this.f, deg.f);
		return new deg(double3, double5, double7, double9, double11, double13);
	}

	public deg b(deg deg) {
		double double3 = Math.min(this.a, deg.a);
		double double5 = Math.min(this.b, deg.b);
		double double7 = Math.min(this.c, deg.c);
		double double9 = Math.max(this.d, deg.d);
		double double11 = Math.max(this.e, deg.e);
		double double13 = Math.max(this.f, deg.f);
		return new deg(double3, double5, double7, double9, double11, double13);
	}

	public deg d(double double1, double double2, double double3) {
		return new deg(this.a + double1, this.b + double2, this.c + double3, this.d + double1, this.e + double2, this.f + double3);
	}

	public deg a(fu fu) {
		return new deg(
			this.a + (double)fu.u(), this.b + (double)fu.v(), this.c + (double)fu.w(), this.d + (double)fu.u(), this.e + (double)fu.v(), this.f + (double)fu.w()
		);
	}

	public deg c(dem dem) {
		return this.d(dem.b, dem.c, dem.d);
	}

	public boolean c(deg deg) {
		return this.a(deg.a, deg.b, deg.c, deg.d, deg.e, deg.f);
	}

	public boolean a(double double1, double double2, double double3, double double4, double double5, double double6) {
		return this.a < double4 && this.d > double1 && this.b < double5 && this.e > double2 && this.c < double6 && this.f > double3;
	}

	public boolean d(dem dem) {
		return this.e(dem.b, dem.c, dem.d);
	}

	public boolean e(double double1, double double2, double double3) {
		return double1 >= this.a && double1 < this.d && double2 >= this.b && double2 < this.e && double3 >= this.c && double3 < this.f;
	}

	public double a() {
		double double2 = this.b();
		double double4 = this.c();
		double double6 = this.d();
		return (double2 + double4 + double6) / 3.0;
	}

	public double b() {
		return this.d - this.a;
	}

	public double c() {
		return this.e - this.b;
	}

	public double d() {
		return this.f - this.c;
	}

	public deg h(double double1) {
		return this.g(-double1);
	}

	public Optional<dem> b(dem dem1, dem dem2) {
		double[] arr4 = new double[]{1.0};
		double double5 = dem2.b - dem1.b;
		double double7 = dem2.c - dem1.c;
		double double9 = dem2.d - dem1.d;
		fz fz11 = a(this, dem1, arr4, null, double5, double7, double9);
		if (fz11 == null) {
			return Optional.empty();
		} else {
			double double12 = arr4[0];
			return Optional.of(dem1.b(double12 * double5, double12 * double7, double12 * double9));
		}
	}

	@Nullable
	public static deh a(Iterable<deg> iterable, dem dem2, dem dem3, fu fu) {
		double[] arr5 = new double[]{1.0};
		fz fz6 = null;
		double double7 = dem3.b - dem2.b;
		double double9 = dem3.c - dem2.c;
		double double11 = dem3.d - dem2.d;

		for (deg deg14 : iterable) {
			fz6 = a(deg14.a(fu), dem2, arr5, fz6, double7, double9, double11);
		}

		if (fz6 == null) {
			return null;
		} else {
			double double13 = arr5[0];
			return new deh(dem2.b(double13 * double7, double13 * double9, double13 * double11), fz6, fu, false);
		}
	}

	@Nullable
	private static fz a(deg deg, dem dem, double[] arr, @Nullable fz fz, double double5, double double6, double double7) {
		if (double5 > 1.0E-7) {
			fz = a(arr, fz, double5, double6, double7, deg.a, deg.b, deg.e, deg.c, deg.f, fz.WEST, dem.b, dem.c, dem.d);
		} else if (double5 < -1.0E-7) {
			fz = a(arr, fz, double5, double6, double7, deg.d, deg.b, deg.e, deg.c, deg.f, fz.EAST, dem.b, dem.c, dem.d);
		}

		if (double6 > 1.0E-7) {
			fz = a(arr, fz, double6, double7, double5, deg.b, deg.c, deg.f, deg.a, deg.d, fz.DOWN, dem.c, dem.d, dem.b);
		} else if (double6 < -1.0E-7) {
			fz = a(arr, fz, double6, double7, double5, deg.e, deg.c, deg.f, deg.a, deg.d, fz.UP, dem.c, dem.d, dem.b);
		}

		if (double7 > 1.0E-7) {
			fz = a(arr, fz, double7, double5, double6, deg.c, deg.a, deg.d, deg.b, deg.e, fz.NORTH, dem.d, dem.b, dem.c);
		} else if (double7 < -1.0E-7) {
			fz = a(arr, fz, double7, double5, double6, deg.f, deg.a, deg.d, deg.b, deg.e, fz.SOUTH, dem.d, dem.b, dem.c);
		}

		return fz;
	}

	@Nullable
	private static fz a(
		double[] arr,
		@Nullable fz fz2,
		double double3,
		double double4,
		double double5,
		double double6,
		double double7,
		double double8,
		double double9,
		double double10,
		fz fz11,
		double double12,
		double double13,
		double double14
	) {
		double double26 = (double6 - double12) / double3;
		double double28 = double13 + double26 * double4;
		double double30 = double14 + double26 * double5;
		if (0.0 < double26
			&& double26 < arr[0]
			&& double7 - 1.0E-7 < double28
			&& double28 < double8 + 1.0E-7
			&& double9 - 1.0E-7 < double30
			&& double30 < double10 + 1.0E-7) {
			arr[0] = double26;
			return fz11;
		} else {
			return fz2;
		}
	}

	public String toString() {
		return "AABB[" + this.a + ", " + this.b + ", " + this.c + "] -> [" + this.d + ", " + this.e + ", " + this.f + "]";
	}

	public dem f() {
		return new dem(aec.d(0.5, this.a, this.d), aec.d(0.5, this.b, this.e), aec.d(0.5, this.c, this.f));
	}

	public static deg g(double double1, double double2, double double3) {
		return new deg(-double1 / 2.0, -double2 / 2.0, -double3 / 2.0, double1 / 2.0, double2 / 2.0, double3 / 2.0);
	}
}
