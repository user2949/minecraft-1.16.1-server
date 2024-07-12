import com.google.common.annotations.VisibleForTesting;
import com.google.common.math.DoubleMath;
import com.google.common.math.IntMath;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class dfd {
	private static final dfg b = v.a((Supplier<dfg>)(() -> {
		dev dev1 = new dep(1, 1, 1);
		dev1.a(0, 0, 0, true, true);
		return new det(dev1);
	}));
	public static final dfg a = a(
		Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY
	);
	private static final dfg c = new deo(
		new dep(0, 0, 0), new DoubleArrayList(new double[]{0.0}), new DoubleArrayList(new double[]{0.0}), new DoubleArrayList(new double[]{0.0})
	);

	public static dfg a() {
		return c;
	}

	public static dfg b() {
		return b;
	}

	public static dfg a(double double1, double double2, double double3, double double4, double double5, double double6) {
		return a(new deg(double1, double2, double3, double4, double5, double6));
	}

	public static dfg a(deg deg) {
		int integer2 = a(deg.a, deg.d);
		int integer3 = a(deg.b, deg.e);
		int integer4 = a(deg.c, deg.f);
		if (integer2 >= 0 && integer3 >= 0 && integer4 >= 0) {
			if (integer2 == 0 && integer3 == 0 && integer4 == 0) {
				return deg.e(0.5, 0.5, 0.5) ? b() : a();
			} else {
				int integer5 = 1 << integer2;
				int integer6 = 1 << integer3;
				int integer7 = 1 << integer4;
				int integer8 = (int)Math.round(deg.a * (double)integer5);
				int integer9 = (int)Math.round(deg.d * (double)integer5);
				int integer10 = (int)Math.round(deg.b * (double)integer6);
				int integer11 = (int)Math.round(deg.e * (double)integer6);
				int integer12 = (int)Math.round(deg.c * (double)integer7);
				int integer13 = (int)Math.round(deg.f * (double)integer7);
				dep dep14 = new dep(integer5, integer6, integer7, integer8, integer10, integer12, integer9, integer11, integer13);

				for (long long15 = (long)integer8; long15 < (long)integer9; long15++) {
					for (long long17 = (long)integer10; long17 < (long)integer11; long17++) {
						for (long long19 = (long)integer12; long19 < (long)integer13; long19++) {
							dep14.a((int)long15, (int)long17, (int)long19, false, true);
						}
					}
				}

				return new det(dep14);
			}
		} else {
			return new deo(b.a, new double[]{deg.a, deg.d}, new double[]{deg.b, deg.e}, new double[]{deg.c, deg.f});
		}
	}

	private static int a(double double1, double double2) {
		if (!(double1 < -1.0E-7) && !(double2 > 1.0000001)) {
			for (int integer5 = 0; integer5 <= 3; integer5++) {
				double double6 = double1 * (double)(1 << integer5);
				double double8 = double2 * (double)(1 << integer5);
				boolean boolean10 = Math.abs(double6 - Math.floor(double6)) < 1.0E-7;
				boolean boolean11 = Math.abs(double8 - Math.floor(double8)) < 1.0E-7;
				if (boolean10 && boolean11) {
					return integer5;
				}
			}

			return -1;
		} else {
			return -1;
		}
	}

	protected static long a(int integer1, int integer2) {
		return (long)integer1 * (long)(integer2 / IntMath.gcd(integer1, integer2));
	}

	public static dfg a(dfg dfg1, dfg dfg2) {
		return a(dfg1, dfg2, deq.o);
	}

	public static dfg a(dfg dfg, dfg... arr) {
		return (dfg)Arrays.stream(arr).reduce(dfg, dfd::a);
	}

	public static dfg a(dfg dfg1, dfg dfg2, deq deq) {
		return b(dfg1, dfg2, deq).c();
	}

	public static dfg b(dfg dfg1, dfg dfg2, deq deq) {
		if (deq.apply(false, false)) {
			throw (IllegalArgumentException)v.c(new IllegalArgumentException());
		} else if (dfg1 == dfg2) {
			return deq.apply(true, true) ? dfg1 : a();
		} else {
			boolean boolean4 = deq.apply(true, false);
			boolean boolean5 = deq.apply(false, true);
			if (dfg1.b()) {
				return boolean5 ? dfg2 : a();
			} else if (dfg2.b()) {
				return boolean4 ? dfg1 : a();
			} else {
				dey dey6 = a(1, dfg1.a(fz.a.X), dfg2.a(fz.a.X), boolean4, boolean5);
				dey dey7 = a(dey6.a().size() - 1, dfg1.a(fz.a.Y), dfg2.a(fz.a.Y), boolean4, boolean5);
				dey dey8 = a((dey6.a().size() - 1) * (dey7.a().size() - 1), dfg1.a(fz.a.Z), dfg2.a(fz.a.Z), boolean4, boolean5);
				dep dep9 = dep.a(dfg1.a, dfg2.a, dey6, dey7, dey8, deq);
				return (dfg)(dey6 instanceof deu && dey7 instanceof deu && dey8 instanceof deu ? new det(dep9) : new deo(dep9, dey6.a(), dey7.a(), dey8.a()));
			}
		}
	}

	public static boolean c(dfg dfg1, dfg dfg2, deq deq) {
		if (deq.apply(false, false)) {
			throw (IllegalArgumentException)v.c(new IllegalArgumentException());
		} else if (dfg1 == dfg2) {
			return deq.apply(true, true);
		} else if (dfg1.b()) {
			return deq.apply(false, !dfg2.b());
		} else if (dfg2.b()) {
			return deq.apply(!dfg1.b(), false);
		} else {
			boolean boolean4 = deq.apply(true, false);
			boolean boolean5 = deq.apply(false, true);

			for (fz.a a9 : fs.d) {
				if (dfg1.c(a9) < dfg2.b(a9) - 1.0E-7) {
					return boolean4 || boolean5;
				}

				if (dfg2.c(a9) < dfg1.b(a9) - 1.0E-7) {
					return boolean4 || boolean5;
				}
			}

			dey dey6 = a(1, dfg1.a(fz.a.X), dfg2.a(fz.a.X), boolean4, boolean5);
			dey dey7 = a(dey6.a().size() - 1, dfg1.a(fz.a.Y), dfg2.a(fz.a.Y), boolean4, boolean5);
			dey dey8 = a((dey6.a().size() - 1) * (dey7.a().size() - 1), dfg1.a(fz.a.Z), dfg2.a(fz.a.Z), boolean4, boolean5);
			return a(dey6, dey7, dey8, dfg1.a, dfg2.a, deq);
		}
	}

	private static boolean a(dey dey1, dey dey2, dey dey3, dev dev4, dev dev5, deq deq) {
		return !dey1.a(
			(integer6, integer7, integer8) -> dey2.a(
					(integer7x, integer8x, integer9) -> dey3.a(
							(integer8xx, integer9x, integer10) -> !deq.apply(dev4.c(integer6, integer7x, integer8xx), dev5.c(integer7, integer8x, integer9x))
						)
				)
		);
	}

	public static double a(fz.a a, deg deg, Stream<dfg> stream, double double4) {
		Iterator<dfg> iterator6 = stream.iterator();

		while (iterator6.hasNext()) {
			if (Math.abs(double4) < 1.0E-7) {
				return 0.0;
			}

			double4 = ((dfg)iterator6.next()).a(a, deg, double4);
		}

		return double4;
	}

	public static double a(fz.a a, deg deg, bqd bqd, double double4, der der, Stream<dfg> stream) {
		return a(deg, bqd, double4, der, fs.a(a, fz.a.Z), stream);
	}

	private static double a(deg deg, bqd bqd, double double3, der der, fs fs, Stream<dfg> stream) {
		if (deg.b() < 1.0E-6 || deg.c() < 1.0E-6 || deg.d() < 1.0E-6) {
			return double3;
		} else if (Math.abs(double3) < 1.0E-7) {
			return 0.0;
		} else {
			fs fs8 = fs.a();
			fz.a a9 = fs8.a(fz.a.X);
			fz.a a10 = fs8.a(fz.a.Y);
			fz.a a11 = fs8.a(fz.a.Z);
			fu.a a12 = new fu.a();
			int integer13 = aec.c(deg.a(a9) - 1.0E-7) - 1;
			int integer14 = aec.c(deg.b(a9) + 1.0E-7) + 1;
			int integer15 = aec.c(deg.a(a10) - 1.0E-7) - 1;
			int integer16 = aec.c(deg.b(a10) + 1.0E-7) + 1;
			double double17 = deg.a(a11) - 1.0E-7;
			double double19 = deg.b(a11) + 1.0E-7;
			boolean boolean21 = double3 > 0.0;
			int integer22 = boolean21 ? aec.c(deg.b(a11) - 1.0E-7) - 1 : aec.c(deg.a(a11) + 1.0E-7) + 1;
			int integer23 = a(double3, double17, double19);
			int integer24 = boolean21 ? 1 : -1;

			for (int integer25 = integer22; boolean21 ? integer25 <= integer23 : integer25 >= integer23; integer25 += integer24) {
				for (int integer26 = integer13; integer26 <= integer14; integer26++) {
					for (int integer27 = integer15; integer27 <= integer16; integer27++) {
						int integer28 = 0;
						if (integer26 == integer13 || integer26 == integer14) {
							integer28++;
						}

						if (integer27 == integer15 || integer27 == integer16) {
							integer28++;
						}

						if (integer25 == integer22 || integer25 == integer23) {
							integer28++;
						}

						if (integer28 < 3) {
							a12.a(fs8, integer26, integer27, integer25);
							cfj cfj29 = bqd.d_(a12);
							if ((integer28 != 1 || cfj29.d()) && (integer28 != 2 || cfj29.a(bvs.bo))) {
								double3 = cfj29.b(bqd, a12, der).a(a11, deg.d((double)(-a12.u()), (double)(-a12.v()), (double)(-a12.w())), double3);
								if (Math.abs(double3) < 1.0E-7) {
									return 0.0;
								}

								integer23 = a(double3, double17, double19);
							}
						}
					}
				}
			}

			double[] arr25 = new double[]{double3};
			stream.forEach(dfg -> arr25[0] = dfg.a(a11, deg, arr25[0]));
			return arr25[0];
		}
	}

	private static int a(double double1, double double2, double double3) {
		return double1 > 0.0 ? aec.c(double3 + double1) + 1 : aec.c(double2 + double1) - 1;
	}

	public static dfg a(dfg dfg, fz fz) {
		if (dfg == b()) {
			return b();
		} else {
			fz.a a5 = fz.n();
			boolean boolean3;
			int integer4;
			if (fz.e() == fz.b.POSITIVE) {
				boolean3 = DoubleMath.fuzzyEquals(dfg.c(a5), 1.0, 1.0E-7);
				integer4 = dfg.a.c(a5) - 1;
			} else {
				boolean3 = DoubleMath.fuzzyEquals(dfg.b(a5), 0.0, 1.0E-7);
				integer4 = 0;
			}

			return (dfg)(!boolean3 ? a() : new dfe(dfg, a5, integer4));
		}
	}

	public static boolean b(dfg dfg1, dfg dfg2, fz fz) {
		if (dfg1 != b() && dfg2 != b()) {
			fz.a a4 = fz.n();
			fz.b b5 = fz.e();
			dfg dfg6 = b5 == fz.b.POSITIVE ? dfg1 : dfg2;
			dfg dfg7 = b5 == fz.b.POSITIVE ? dfg2 : dfg1;
			if (!DoubleMath.fuzzyEquals(dfg6.c(a4), 1.0, 1.0E-7)) {
				dfg6 = a();
			}

			if (!DoubleMath.fuzzyEquals(dfg7.b(a4), 0.0, 1.0E-7)) {
				dfg7 = a();
			}

			return !c(b(), b(new dfe(dfg6, a4, dfg6.a.c(a4) - 1), new dfe(dfg7, a4, 0), deq.o), deq.e);
		} else {
			return true;
		}
	}

	public static boolean b(dfg dfg1, dfg dfg2) {
		if (dfg1 == b() || dfg2 == b()) {
			return true;
		} else {
			return dfg1.b() && dfg2.b() ? false : !c(b(), b(dfg1, dfg2, deq.o), deq.e);
		}
	}

	@VisibleForTesting
	protected static dey a(int integer, DoubleList doubleList2, DoubleList doubleList3, boolean boolean4, boolean boolean5) {
		int integer6 = doubleList2.size() - 1;
		int integer7 = doubleList3.size() - 1;
		if (doubleList2 instanceof des && doubleList3 instanceof des) {
			long long8 = a(integer6, integer7);
			if ((long)integer * long8 <= 256L) {
				return new deu(integer6, integer7);
			}
		}

		if (doubleList2.getDouble(integer6) < doubleList3.getDouble(0) - 1.0E-7) {
			return new dfb(doubleList2, doubleList3, false);
		} else if (doubleList3.getDouble(integer7) < doubleList2.getDouble(0) - 1.0E-7) {
			return new dfb(doubleList3, doubleList2, true);
		} else if (integer6 != integer7 || !Objects.equals(doubleList2, doubleList3)) {
			return new dez(doubleList2, doubleList3, boolean4, boolean5);
		} else if (doubleList2 instanceof dex) {
			return (dey)doubleList2;
		} else {
			return (dey)(doubleList3 instanceof dex ? (dey)doubleList3 : new dex(doubleList2));
		}
	}

	public interface a {
		void consume(double double1, double double2, double double3, double double4, double double5, double double6);
	}
}
