import com.google.common.collect.Lists;
import com.google.common.math.DoubleMath;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.List;
import javax.annotation.Nullable;

public abstract class dfg {
	protected final dev a;
	@Nullable
	private dfg[] b;

	dfg(dev dev) {
		this.a = dev;
	}

	public double b(fz.a a) {
		int integer3 = this.a.a(a);
		return integer3 >= this.a.c(a) ? Double.POSITIVE_INFINITY : this.a(a, integer3);
	}

	public double c(fz.a a) {
		int integer3 = this.a.b(a);
		return integer3 <= 0 ? Double.NEGATIVE_INFINITY : this.a(a, integer3);
	}

	public deg a() {
		if (this.b()) {
			throw (UnsupportedOperationException)v.c(new UnsupportedOperationException("No bounds for empty shape."));
		} else {
			return new deg(this.b(fz.a.X), this.b(fz.a.Y), this.b(fz.a.Z), this.c(fz.a.X), this.c(fz.a.Y), this.c(fz.a.Z));
		}
	}

	protected double a(fz.a a, int integer) {
		return this.a(a).getDouble(integer);
	}

	protected abstract DoubleList a(fz.a a);

	public boolean b() {
		return this.a.a();
	}

	public dfg a(double double1, double double2, double double3) {
		return (dfg)(this.b() ? dfd.a() : new deo(this.a, new dfc(this.a(fz.a.X), double1), new dfc(this.a(fz.a.Y), double2), new dfc(this.a(fz.a.Z), double3)));
	}

	public dfg c() {
		dfg[] arr2 = new dfg[]{dfd.a()};
		this.b((double2, double3, double4, double5, double6, double7) -> arr2[0] = dfd.b(arr2[0], dfd.a(double2, double3, double4, double5, double6, double7), deq.o));
		return arr2[0];
	}

	public void b(dfd.a a) {
		DoubleList doubleList3 = this.a(fz.a.X);
		DoubleList doubleList4 = this.a(fz.a.Y);
		DoubleList doubleList5 = this.a(fz.a.Z);
		this.a
			.b(
				(integer5, integer6, integer7, integer8, integer9, integer10) -> a.consume(
						doubleList3.getDouble(integer5),
						doubleList4.getDouble(integer6),
						doubleList5.getDouble(integer7),
						doubleList3.getDouble(integer8),
						doubleList4.getDouble(integer9),
						doubleList5.getDouble(integer10)
					),
				true
			);
	}

	public List<deg> d() {
		List<deg> list2 = Lists.<deg>newArrayList();
		this.b((double2, double3, double4, double5, double6, double7) -> list2.add(new deg(double2, double3, double4, double5, double6, double7)));
		return list2;
	}

	protected int a(fz.a a, double double2) {
		return aec.a(0, this.a.c(a) + 1, integer -> {
			if (integer < 0) {
				return false;
			} else {
				return integer > this.a.c(a) ? true : double2 < this.a(a, integer);
			}
		}) - 1;
	}

	protected boolean b(double double1, double double2, double double3) {
		return this.a.c(this.a(fz.a.X, double1), this.a(fz.a.Y, double2), this.a(fz.a.Z, double3));
	}

	@Nullable
	public deh a(dem dem1, dem dem2, fu fu) {
		if (this.b()) {
			return null;
		} else {
			dem dem5 = dem2.d(dem1);
			if (dem5.g() < 1.0E-7) {
				return null;
			} else {
				dem dem6 = dem1.e(dem5.a(0.001));
				return this.b(dem6.b - (double)fu.u(), dem6.c - (double)fu.v(), dem6.d - (double)fu.w())
					? new deh(dem6, fz.a(dem5.b, dem5.c, dem5.d).f(), fu, true)
					: deg.a(this.d(), dem1, dem2, fu);
			}
		}
	}

	public dfg a(fz fz) {
		if (!this.b() && this != dfd.b()) {
			if (this.b != null) {
				dfg dfg3 = this.b[fz.ordinal()];
				if (dfg3 != null) {
					return dfg3;
				}
			} else {
				this.b = new dfg[6];
			}

			dfg dfg3 = this.b(fz);
			this.b[fz.ordinal()] = dfg3;
			return dfg3;
		} else {
			return this;
		}
	}

	private dfg b(fz fz) {
		fz.a a3 = fz.n();
		fz.b b4 = fz.e();
		DoubleList doubleList5 = this.a(a3);
		if (doubleList5.size() == 2 && DoubleMath.fuzzyEquals(doubleList5.getDouble(0), 0.0, 1.0E-7) && DoubleMath.fuzzyEquals(doubleList5.getDouble(1), 1.0, 1.0E-7)
			)
		 {
			return this;
		} else {
			int integer6 = this.a(a3, b4 == fz.b.POSITIVE ? 0.9999999 : 1.0E-7);
			return new dfe(this, a3, integer6);
		}
	}

	public double a(fz.a a, deg deg, double double3) {
		return this.a(fs.a(a, fz.a.X), deg, double3);
	}

	protected double a(fs fs, deg deg, double double3) {
		if (this.b()) {
			return double3;
		} else if (Math.abs(double3) < 1.0E-7) {
			return 0.0;
		} else {
			fs fs6 = fs.a();
			fz.a a7 = fs6.a(fz.a.X);
			fz.a a8 = fs6.a(fz.a.Y);
			fz.a a9 = fs6.a(fz.a.Z);
			double double10 = deg.b(a7);
			double double12 = deg.a(a7);
			int integer14 = this.a(a7, double12 + 1.0E-7);
			int integer15 = this.a(a7, double10 - 1.0E-7);
			int integer16 = Math.max(0, this.a(a8, deg.a(a8) + 1.0E-7));
			int integer17 = Math.min(this.a.c(a8), this.a(a8, deg.b(a8) - 1.0E-7) + 1);
			int integer18 = Math.max(0, this.a(a9, deg.a(a9) + 1.0E-7));
			int integer19 = Math.min(this.a.c(a9), this.a(a9, deg.b(a9) - 1.0E-7) + 1);
			int integer20 = this.a.c(a7);
			if (double3 > 0.0) {
				for (int integer21 = integer15 + 1; integer21 < integer20; integer21++) {
					for (int integer22 = integer16; integer22 < integer17; integer22++) {
						for (int integer23 = integer18; integer23 < integer19; integer23++) {
							if (this.a.a(fs6, integer21, integer22, integer23)) {
								double double24 = this.a(a7, integer21) - double10;
								if (double24 >= -1.0E-7) {
									double3 = Math.min(double3, double24);
								}

								return double3;
							}
						}
					}
				}
			} else if (double3 < 0.0) {
				for (int integer21 = integer14 - 1; integer21 >= 0; integer21--) {
					for (int integer22 = integer16; integer22 < integer17; integer22++) {
						for (int integer23x = integer18; integer23x < integer19; integer23x++) {
							if (this.a.a(fs6, integer21, integer22, integer23x)) {
								double double24 = this.a(a7, integer21 + 1) - double12;
								if (double24 <= 1.0E-7) {
									double3 = Math.max(double3, double24);
								}

								return double3;
							}
						}
					}
				}
			}

			return double3;
		}
	}

	public String toString() {
		return this.b() ? "EMPTY" : "VoxelShape[" + this.a() + "]";
	}
}
