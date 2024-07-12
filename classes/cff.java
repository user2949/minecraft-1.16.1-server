import java.util.List;

public class cff extends cdl implements ceo {
	private cfj a;
	private fz b;
	private boolean c;
	private boolean g;
	private static final ThreadLocal<fz> h = ThreadLocal.withInitial(() -> null);
	private float i;
	private float j;
	private long k;

	public cff() {
		super(cdm.j);
	}

	public cff(cfj cfj, fz fz, boolean boolean3, boolean boolean4) {
		this();
		this.a = cfj;
		this.b = fz;
		this.c = boolean3;
		this.g = boolean4;
	}

	@Override
	public le b() {
		return this.a(new le());
	}

	public boolean d() {
		return this.c;
	}

	public fz f() {
		return this.b;
	}

	public boolean h() {
		return this.g;
	}

	public float a(float float1) {
		if (float1 > 1.0F) {
			float1 = 1.0F;
		}

		return aec.g(float1, this.j, this.i);
	}

	private float e(float float1) {
		return this.c ? float1 - 1.0F : 1.0F - float1;
	}

	private cfj x() {
		return !this.d() && this.h() && this.a.b() instanceof cfc
			? bvs.aX.n().a(cfd.c, Boolean.valueOf(this.i > 0.25F)).a(cfd.b, this.a.a(bvs.aP) ? cgk.STICKY : cgk.DEFAULT).a(cfd.a, this.a.c(cfc.a))
			: this.a;
	}

	private void f(float float1) {
		fz fz3 = this.j();
		double double4 = (double)(float1 - this.i);
		dfg dfg6 = this.x().k(this.d, this.o());
		if (!dfg6.b()) {
			deg deg7 = this.a(dfg6.a());
			List<aom> list8 = this.d.a(null, cfe.a(deg7, fz3, double4).b(deg7));
			if (!list8.isEmpty()) {
				List<deg> list9 = dfg6.d();
				boolean boolean10 = this.a.a(bvs.gn);

				for (aom aom12 : list8) {
					if (aom12.z_() != cxf.IGNORE) {
						if (boolean10) {
							dem dem13 = aom12.cB();
							double double14 = dem13.b;
							double double16 = dem13.c;
							double double18 = dem13.d;
							switch (fz3.n()) {
								case X:
									double14 = (double)fz3.i();
									break;
								case Y:
									double16 = (double)fz3.j();
									break;
								case Z:
									double18 = (double)fz3.k();
							}

							aom12.m(double14, double16, double18);
							if (aom12 instanceof ze) {
								((ze)aom12).b.a(new qb(aom12));
							}
						}

						double double13 = 0.0;

						for (deg deg16 : list9) {
							deg deg17 = cfe.a(this.a(deg16), fz3, double4);
							deg deg18 = aom12.cb();
							if (deg17.c(deg18)) {
								double13 = Math.max(double13, a(deg17, fz3, deg18));
								if (double13 >= double4) {
									break;
								}
							}
						}

						if (!(double13 <= 0.0)) {
							double13 = Math.min(double13, double4) + 0.01;
							a(fz3, aom12, double13, fz3);
							if (!this.c && this.g) {
								this.a(aom12, fz3, double4);
							}
						}
					}
				}
			}
		}
	}

	private static void a(fz fz1, aom aom, double double3, fz fz4) {
		h.set(fz1);
		aom.a(apd.PISTON, new dem(double3 * (double)fz4.i(), double3 * (double)fz4.j(), double3 * (double)fz4.k()));
		h.set(null);
	}

	private void g(float float1) {
		if (this.y()) {
			fz fz3 = this.j();
			if (fz3.n().d()) {
				double double4 = this.a.k(this.d, this.e).c(fz.a.Y);
				deg deg6 = this.a(new deg(0.0, double4, 0.0, 1.0, 1.5000000999999998, 1.0));
				double double7 = (double)(float1 - this.i);

				for (aom aom11 : this.d.a((aom)null, deg6, aom -> a(deg6, aom))) {
					a(fz3, aom11, double7, fz3);
				}
			}
		}
	}

	private static boolean a(deg deg, aom aom) {
		return aom.z_() == cxf.NORMAL && aom.aj() && aom.cC() >= deg.a && aom.cC() <= deg.d && aom.cG() >= deg.c && aom.cG() <= deg.f;
	}

	private boolean y() {
		return this.a.a(bvs.ne);
	}

	public fz j() {
		return this.c ? this.b : this.b.f();
	}

	private static double a(deg deg1, fz fz, deg deg3) {
		switch (fz) {
			case EAST:
				return deg1.d - deg3.a;
			case WEST:
				return deg3.d - deg1.a;
			case UP:
			default:
				return deg1.e - deg3.b;
			case DOWN:
				return deg3.e - deg1.b;
			case SOUTH:
				return deg1.f - deg3.c;
			case NORTH:
				return deg3.f - deg1.c;
		}
	}

	private deg a(deg deg) {
		double double3 = (double)this.e(this.i);
		return deg.d(
			(double)this.e.u() + double3 * (double)this.b.i(), (double)this.e.v() + double3 * (double)this.b.j(), (double)this.e.w() + double3 * (double)this.b.k()
		);
	}

	private void a(aom aom, fz fz, double double3) {
		deg deg6 = aom.cb();
		deg deg7 = dfd.b().a().a(this.e);
		if (deg6.c(deg7)) {
			fz fz8 = fz.f();
			double double9 = a(deg7, fz8, deg6) + 0.01;
			double double11 = a(deg7, fz8, deg6.a(deg7)) + 0.01;
			if (Math.abs(double9 - double11) < 0.01) {
				double9 = Math.min(double9, double3) + 0.01;
				a(fz, aom, double9, fz8);
			}
		}
	}

	public cfj k() {
		return this.a;
	}

	public void l() {
		if (this.j < 1.0F && this.d != null) {
			this.i = 1.0F;
			this.j = this.i;
			this.d.o(this.e);
			this.an_();
			if (this.d.d_(this.e).a(bvs.bo)) {
				cfj cfj2;
				if (this.g) {
					cfj2 = bvs.a.n();
				} else {
					cfj2 = bvr.b(this.a, (bqc)this.d, this.e);
				}

				this.d.a(this.e, cfj2, 3);
				this.d.a(this.e, cfj2.b(), this.e);
			}
		}
	}

	@Override
	public void al_() {
		this.k = this.d.Q();
		this.j = this.i;
		if (this.j >= 1.0F) {
			this.d.o(this.e);
			this.an_();
			if (this.a != null && this.d.d_(this.e).a(bvs.bo)) {
				cfj cfj2 = bvr.b(this.a, (bqc)this.d, this.e);
				if (cfj2.g()) {
					this.d.a(this.e, this.a, 84);
					bvr.a(this.a, cfj2, this.d, this.e, 3);
				} else {
					if (cfj2.b(cfz.C) && (Boolean)cfj2.c(cfz.C)) {
						cfj2 = cfj2.a(cfz.C, Boolean.valueOf(false));
					}

					this.d.a(this.e, cfj2, 67);
					this.d.a(this.e, cfj2.b(), this.e);
				}
			}
		} else {
			float float2 = this.i + 0.5F;
			this.f(float2);
			this.g(float2);
			this.i = float2;
			if (this.i >= 1.0F) {
				this.i = 1.0F;
			}
		}
	}

	@Override
	public void a(cfj cfj, le le) {
		super.a(cfj, le);
		this.a = lq.c(le.p("blockState"));
		this.b = fz.a(le.h("facing"));
		this.i = le.j("progress");
		this.j = this.i;
		this.c = le.q("extending");
		this.g = le.q("source");
	}

	@Override
	public le a(le le) {
		super.a(le);
		le.a("blockState", lq.a(this.a));
		le.b("facing", this.b.c());
		le.a("progress", this.j);
		le.a("extending", this.c);
		le.a("source", this.g);
		return le;
	}

	public dfg a(bpg bpg, fu fu) {
		dfg dfg4;
		if (!this.c && this.g) {
			dfg4 = this.a.a(cfc.b, Boolean.valueOf(true)).k(bpg, fu);
		} else {
			dfg4 = dfd.a();
		}

		fz fz5 = (fz)h.get();
		if ((double)this.i < 1.0 && fz5 == this.j()) {
			return dfg4;
		} else {
			cfj cfj6;
			if (this.h()) {
				cfj6 = bvs.aX.n().a(cfd.a, this.b).a(cfd.c, Boolean.valueOf(this.c != 1.0F - this.i < 0.25F));
			} else {
				cfj6 = this.a;
			}

			float float7 = this.e(this.i);
			double double8 = (double)((float)this.b.i() * float7);
			double double10 = (double)((float)this.b.j() * float7);
			double double12 = (double)((float)this.b.k() * float7);
			return dfd.a(dfg4, cfj6.k(bpg, fu).a(double8, double10, double12));
		}
	}

	public long m() {
		return this.k;
	}
}
