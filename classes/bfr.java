import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public abstract class bfr extends aom {
	private static final tq<Integer> b = tt.a(bfr.class, ts.b);
	private static final tq<Integer> c = tt.a(bfr.class, ts.b);
	private static final tq<Float> d = tt.a(bfr.class, ts.c);
	private static final tq<Integer> e = tt.a(bfr.class, ts.b);
	private static final tq<Integer> f = tt.a(bfr.class, ts.b);
	private static final tq<Boolean> g = tt.a(bfr.class, ts.i);
	private static final ImmutableMap<apj, ImmutableList<Integer>> an = ImmutableMap.of(
		apj.STANDING, ImmutableList.of(0, 1, -1), apj.CROUCHING, ImmutableList.of(0, 1, -1), apj.SWIMMING, ImmutableList.of(0, 1)
	);
	private boolean ao;
	private static final Map<cgm, Pair<gr, gr>> ap = v.a(Maps.newEnumMap(cgm.class), enumMap -> {
		gr gr2 = fz.WEST.p();
		gr gr3 = fz.EAST.p();
		gr gr4 = fz.NORTH.p();
		gr gr5 = fz.SOUTH.p();
		gr gr6 = gr2.n();
		gr gr7 = gr3.n();
		gr gr8 = gr4.n();
		gr gr9 = gr5.n();
		enumMap.put(cgm.NORTH_SOUTH, Pair.of(gr4, gr5));
		enumMap.put(cgm.EAST_WEST, Pair.of(gr2, gr3));
		enumMap.put(cgm.ASCENDING_EAST, Pair.of(gr6, gr3));
		enumMap.put(cgm.ASCENDING_WEST, Pair.of(gr2, gr7));
		enumMap.put(cgm.ASCENDING_NORTH, Pair.of(gr4, gr9));
		enumMap.put(cgm.ASCENDING_SOUTH, Pair.of(gr8, gr5));
		enumMap.put(cgm.SOUTH_EAST, Pair.of(gr5, gr3));
		enumMap.put(cgm.SOUTH_WEST, Pair.of(gr5, gr2));
		enumMap.put(cgm.NORTH_WEST, Pair.of(gr4, gr2));
		enumMap.put(cgm.NORTH_EAST, Pair.of(gr4, gr3));
	});
	private int aq;
	private double ar;
	private double as;
	private double at;
	private double au;
	private double av;

	protected bfr(aoq<?> aoq, bqb bqb) {
		super(aoq, bqb);
		this.i = true;
	}

	protected bfr(aoq<?> aoq, bqb bqb, double double3, double double4, double double5) {
		this(aoq, bqb);
		this.d(double3, double4, double5);
		this.e(dem.a);
		this.m = double3;
		this.n = double4;
		this.o = double5;
	}

	public static bfr a(bqb bqb, double double2, double double3, double double4, bfr.a a) {
		if (a == bfr.a.CHEST) {
			return new bfw(bqb, double2, double3, double4);
		} else if (a == bfr.a.FURNACE) {
			return new bfy(bqb, double2, double3, double4);
		} else if (a == bfr.a.TNT) {
			return new bgb(bqb, double2, double3, double4);
		} else if (a == bfr.a.SPAWNER) {
			return new bga(bqb, double2, double3, double4);
		} else if (a == bfr.a.HOPPER) {
			return new bfz(bqb, double2, double3, double4);
		} else {
			return (bfr)(a == bfr.a.COMMAND_BLOCK ? new bfx(bqb, double2, double3, double4) : new bfv(bqb, double2, double3, double4));
		}
	}

	@Override
	protected boolean ax() {
		return false;
	}

	@Override
	protected void e() {
		this.S.a(b, 0);
		this.S.a(c, 1);
		this.S.a(d, 0.0F);
		this.S.a(e, bvr.i(bvs.a.n()));
		this.S.a(f, 6);
		this.S.a(g, false);
	}

	@Nullable
	@Override
	public deg j(aom aom) {
		return aom.aR() ? aom.cb() : null;
	}

	@Override
	public boolean aR() {
		return true;
	}

	@Override
	public double aY() {
		return 0.0;
	}

	@Override
	public dem c(aoy aoy) {
		fz fz3 = this.bZ();
		if (fz3.n() == fz.a.Y) {
			return super.c(aoy);
		} else {
			int[][] arr4 = bfu.a(fz3);
			fu fu5 = this.cA();
			fu.a a6 = new fu.a();
			ImmutableList<apj> immutableList7 = aoy.ei();

			for (apj apj9 : immutableList7) {
				aon aon10 = aoy.a(apj9);
				float float11 = Math.min(aon10.a, 1.0F) / 2.0F;

				for (int integer13 : an.get(apj9)) {
					for (int[] arr17 : arr4) {
						a6.d(fu5.u() + arr17[0], fu5.v() + integer13, fu5.w() + arr17[1]);
						double double18 = this.l.c(a6, cfj -> cfj.a(acx.as) ? true : cfj.b() instanceof ccd && (Boolean)cfj.c(ccd.a));
						if (bfu.a(double18)) {
							deg deg20 = new deg((double)(-float11), double18, (double)(-float11), (double)float11, double18 + (double)aon10.b, (double)float11);
							dem dem21 = dem.a(a6, double18);
							if (bfu.a(this.l, aoy, deg20.c(dem21))) {
								aoy.b(apj9);
								return dem21;
							}
						}
					}
				}
			}

			double double8 = this.cb().e;
			a6.c((double)fu5.u(), double8, (double)fu5.w());

			for (apj apj11 : immutableList7) {
				double double12 = (double)aoy.a(apj11).b;
				double double14 = (double)a6.v() + this.l.a(a6, double8 - (double)a6.v() + double12);
				if (double8 + double12 <= double14) {
					aoy.b(apj11);
					break;
				}
			}

			return super.c(aoy);
		}
	}

	@Override
	public boolean a(anw anw, float float2) {
		if (this.l.v || this.y) {
			return true;
		} else if (this.b(anw)) {
			return false;
		} else {
			this.d(-this.n());
			this.c(10);
			this.aP();
			this.a(this.k() + float2 * 10.0F);
			boolean boolean4 = anw.k() instanceof bec && ((bec)anw.k()).bJ.d;
			if (boolean4 || this.k() > 40.0F) {
				this.ba();
				if (boolean4 && !this.Q()) {
					this.aa();
				} else {
					this.a(anw);
				}
			}

			return true;
		}
	}

	@Override
	protected float am() {
		cfj cfj2 = this.l.d_(this.cA());
		return cfj2.a(acx.G) ? 1.0F : super.am();
	}

	public void a(anw anw) {
		this.aa();
		if (this.l.S().b(bpx.g)) {
			bki bki3 = new bki(bkk.lN);
			if (this.Q()) {
				bki3.a(this.R());
			}

			this.a(bki3);
		}
	}

	@Override
	public boolean aQ() {
		return !this.y;
	}

	private static Pair<gr, gr> a(cgm cgm) {
		return (Pair<gr, gr>)ap.get(cgm);
	}

	@Override
	public fz bZ() {
		return this.ao ? this.bY().f().g() : this.bY().g();
	}

	@Override
	public void j() {
		if (this.m() > 0) {
			this.c(this.m() - 1);
		}

		if (this.k() > 0.0F) {
			this.a(this.k() - 1.0F);
		}

		if (this.cD() < -64.0) {
			this.ai();
		}

		this.bg();
		if (this.l.v) {
			if (this.aq > 0) {
				double double2 = this.cC() + (this.ar - this.cC()) / (double)this.aq;
				double double4 = this.cD() + (this.as - this.cD()) / (double)this.aq;
				double double6 = this.cG() + (this.at - this.cG()) / (double)this.aq;
				double double8 = aec.g(this.au - (double)this.p);
				this.p = (float)((double)this.p + double8 / (double)this.aq);
				this.q = (float)((double)this.q + (this.av - (double)this.q) / (double)this.aq);
				this.aq--;
				this.d(double2, double4, double6);
				this.a(this.p, this.q);
			} else {
				this.ac();
				this.a(this.p, this.q);
			}
		} else {
			if (!this.aw()) {
				this.e(this.cB().b(0.0, -0.04, 0.0));
			}

			int integer2 = aec.c(this.cC());
			int integer3 = aec.c(this.cD());
			int integer4 = aec.c(this.cG());
			if (this.l.d_(new fu(integer2, integer3 - 1, integer4)).a(acx.G)) {
				integer3--;
			}

			fu fu5 = new fu(integer2, integer3, integer4);
			cfj cfj6 = this.l.d_(fu5);
			if (bvj.g(cfj6)) {
				this.b(fu5, cfj6);
				if (cfj6.a(bvs.fD)) {
					this.a(integer2, integer3, integer4, (Boolean)cfj6.c(caa.d));
				}
			} else {
				this.h();
			}

			this.at();
			this.q = 0.0F;
			double double7 = this.m - this.cC();
			double double9 = this.o - this.cG();
			if (double7 * double7 + double9 * double9 > 0.001) {
				this.p = (float)(aec.d(double9, double7) * 180.0 / Math.PI);
				if (this.ao) {
					this.p += 180.0F;
				}
			}

			double double11 = (double)aec.g(this.p - this.r);
			if (double11 < -170.0 || double11 >= 170.0) {
				this.p += 180.0F;
				this.ao = !this.ao;
			}

			this.a(this.p, this.q);
			if (this.o() == bfr.a.RIDEABLE && b(this.cB()) > 0.01) {
				List<aom> list13 = this.l.a(this, this.cb().c(0.2F, 0.0, 0.2F), aop.a(this));
				if (!list13.isEmpty()) {
					for (int integer14 = 0; integer14 < list13.size(); integer14++) {
						aom aom15 = (aom)list13.get(integer14);
						if (!(aom15 instanceof bec) && !(aom15 instanceof ayt) && !(aom15 instanceof bfr) && !this.bo() && !aom15.bn()) {
							aom15.m(this);
						} else {
							aom15.i(this);
						}
					}
				}
			} else {
				for (aom aom14 : this.l.a(this, this.cb().c(0.2F, 0.0, 0.2F))) {
					if (!this.w(aom14) && aom14.aR() && aom14 instanceof bfr) {
						aom14.i(this);
					}
				}
			}

			this.aG();
		}
	}

	protected double g() {
		return 0.4;
	}

	public void a(int integer1, int integer2, int integer3, boolean boolean4) {
	}

	protected void h() {
		double double2 = this.g();
		dem dem4 = this.cB();
		this.m(aec.a(dem4.b, -double2, double2), dem4.c, aec.a(dem4.d, -double2, double2));
		if (this.t) {
			this.e(this.cB().a(0.5));
		}

		this.a(apd.SELF, this.cB());
		if (!this.t) {
			this.e(this.cB().a(0.95));
		}
	}

	protected void b(fu fu, cfj cfj) {
		this.C = 0.0F;
		double double4 = this.cC();
		double double6 = this.cD();
		double double8 = this.cG();
		dem dem10 = this.o(double4, double6, double8);
		double6 = (double)fu.v();
		boolean boolean11 = false;
		boolean boolean12 = false;
		bvj bvj13 = (bvj)cfj.b();
		if (bvj13 == bvs.aN) {
			boolean11 = (Boolean)cfj.c(caa.d);
			boolean12 = !boolean11;
		}

		double double14 = 0.0078125;
		dem dem16 = this.cB();
		cgm cgm17 = cfj.c(bvj13.d());
		switch (cgm17) {
			case ASCENDING_EAST:
				this.e(dem16.b(-0.0078125, 0.0, 0.0));
				double6++;
				break;
			case ASCENDING_WEST:
				this.e(dem16.b(0.0078125, 0.0, 0.0));
				double6++;
				break;
			case ASCENDING_NORTH:
				this.e(dem16.b(0.0, 0.0, 0.0078125));
				double6++;
				break;
			case ASCENDING_SOUTH:
				this.e(dem16.b(0.0, 0.0, -0.0078125));
				double6++;
		}

		dem16 = this.cB();
		Pair<gr, gr> pair18 = a(cgm17);
		gr gr19 = pair18.getFirst();
		gr gr20 = pair18.getSecond();
		double double21 = (double)(gr20.u() - gr19.u());
		double double23 = (double)(gr20.w() - gr19.w());
		double double25 = Math.sqrt(double21 * double21 + double23 * double23);
		double double27 = dem16.b * double21 + dem16.d * double23;
		if (double27 < 0.0) {
			double21 = -double21;
			double23 = -double23;
		}

		double double29 = Math.min(2.0, Math.sqrt(b(dem16)));
		dem16 = new dem(double29 * double21 / double25, dem16.c, double29 * double23 / double25);
		this.e(dem16);
		aom aom31 = this.cm().isEmpty() ? null : (aom)this.cm().get(0);
		if (aom31 instanceof bec) {
			dem dem32 = aom31.cB();
			double double33 = b(dem32);
			double double35 = b(this.cB());
			if (double33 > 1.0E-4 && double35 < 0.01) {
				this.e(this.cB().b(dem32.b * 0.1, 0.0, dem32.d * 0.1));
				boolean12 = false;
			}
		}

		if (boolean12) {
			double double32 = Math.sqrt(b(this.cB()));
			if (double32 < 0.03) {
				this.e(dem.a);
			} else {
				this.e(this.cB().d(0.5, 0.0, 0.5));
			}
		}

		double double32 = (double)fu.u() + 0.5 + (double)gr19.u() * 0.5;
		double double34 = (double)fu.w() + 0.5 + (double)gr19.w() * 0.5;
		double double36 = (double)fu.u() + 0.5 + (double)gr20.u() * 0.5;
		double double38 = (double)fu.w() + 0.5 + (double)gr20.w() * 0.5;
		double21 = double36 - double32;
		double23 = double38 - double34;
		double double40;
		if (double21 == 0.0) {
			double40 = double8 - (double)fu.w();
		} else if (double23 == 0.0) {
			double40 = double4 - (double)fu.u();
		} else {
			double double42 = double4 - double32;
			double double44 = double8 - double34;
			double40 = (double42 * double21 + double44 * double23) * 2.0;
		}

		double4 = double32 + double21 * double40;
		double8 = double34 + double23 * double40;
		this.d(double4, double6, double8);
		double double42 = this.bo() ? 0.75 : 1.0;
		double double44 = this.g();
		dem16 = this.cB();
		this.a(apd.SELF, new dem(aec.a(double42 * dem16.b, -double44, double44), 0.0, aec.a(double42 * dem16.d, -double44, double44)));
		if (gr19.v() != 0 && aec.c(this.cC()) - fu.u() == gr19.u() && aec.c(this.cG()) - fu.w() == gr19.w()) {
			this.d(this.cC(), this.cD() + (double)gr19.v(), this.cG());
		} else if (gr20.v() != 0 && aec.c(this.cC()) - fu.u() == gr20.u() && aec.c(this.cG()) - fu.w() == gr20.w()) {
			this.d(this.cC(), this.cD() + (double)gr20.v(), this.cG());
		}

		this.i();
		dem dem46 = this.o(this.cC(), this.cD(), this.cG());
		if (dem46 != null && dem10 != null) {
			double double47 = (dem10.c - dem46.c) * 0.05;
			dem dem49 = this.cB();
			double double50 = Math.sqrt(b(dem49));
			if (double50 > 0.0) {
				this.e(dem49.d((double50 + double47) / double50, 1.0, (double50 + double47) / double50));
			}

			this.d(this.cC(), dem46.c, this.cG());
		}

		int integer47 = aec.c(this.cC());
		int integer48 = aec.c(this.cG());
		if (integer47 != fu.u() || integer48 != fu.w()) {
			dem dem49 = this.cB();
			double double50 = Math.sqrt(b(dem49));
			this.m(double50 * (double)(integer47 - fu.u()), dem49.c, double50 * (double)(integer48 - fu.w()));
		}

		if (boolean11) {
			dem dem49 = this.cB();
			double double50 = Math.sqrt(b(dem49));
			if (double50 > 0.01) {
				double double52 = 0.06;
				this.e(dem49.b(dem49.b / double50 * 0.06, 0.0, dem49.d / double50 * 0.06));
			} else {
				dem dem52 = this.cB();
				double double53 = dem52.b;
				double double55 = dem52.d;
				if (cgm17 == cgm.EAST_WEST) {
					if (this.a(fu.f())) {
						double53 = 0.02;
					} else if (this.a(fu.g())) {
						double53 = -0.02;
					}
				} else {
					if (cgm17 != cgm.NORTH_SOUTH) {
						return;
					}

					if (this.a(fu.d())) {
						double55 = 0.02;
					} else if (this.a(fu.e())) {
						double55 = -0.02;
					}
				}

				this.m(double53, dem52.c, double55);
			}
		}
	}

	private boolean a(fu fu) {
		return this.l.d_(fu).g(this.l, fu);
	}

	protected void i() {
		double double2 = this.bo() ? 0.997 : 0.96;
		this.e(this.cB().d(double2, 0.0, double2));
	}

	@Nullable
	public dem o(double double1, double double2, double double3) {
		int integer8 = aec.c(double1);
		int integer9 = aec.c(double2);
		int integer10 = aec.c(double3);
		if (this.l.d_(new fu(integer8, integer9 - 1, integer10)).a(acx.G)) {
			integer9--;
		}

		cfj cfj11 = this.l.d_(new fu(integer8, integer9, integer10));
		if (bvj.g(cfj11)) {
			cgm cgm12 = cfj11.c(((bvj)cfj11.b()).d());
			Pair<gr, gr> pair13 = a(cgm12);
			gr gr14 = pair13.getFirst();
			gr gr15 = pair13.getSecond();
			double double16 = (double)integer8 + 0.5 + (double)gr14.u() * 0.5;
			double double18 = (double)integer9 + 0.0625 + (double)gr14.v() * 0.5;
			double double20 = (double)integer10 + 0.5 + (double)gr14.w() * 0.5;
			double double22 = (double)integer8 + 0.5 + (double)gr15.u() * 0.5;
			double double24 = (double)integer9 + 0.0625 + (double)gr15.v() * 0.5;
			double double26 = (double)integer10 + 0.5 + (double)gr15.w() * 0.5;
			double double28 = double22 - double16;
			double double30 = (double24 - double18) * 2.0;
			double double32 = double26 - double20;
			double double34;
			if (double28 == 0.0) {
				double34 = double3 - (double)integer10;
			} else if (double32 == 0.0) {
				double34 = double1 - (double)integer8;
			} else {
				double double36 = double1 - double16;
				double double38 = double3 - double20;
				double34 = (double36 * double28 + double38 * double32) * 2.0;
			}

			double1 = double16 + double28 * double34;
			double2 = double18 + double30 * double34;
			double3 = double20 + double32 * double34;
			if (double30 < 0.0) {
				double2++;
			} else if (double30 > 0.0) {
				double2 += 0.5;
			}

			return new dem(double1, double2, double3);
		} else {
			return null;
		}
	}

	@Override
	protected void a(le le) {
		if (le.q("CustomDisplayTile")) {
			this.b(lq.c(le.p("DisplayState")));
			this.l(le.h("DisplayOffset"));
		}
	}

	@Override
	protected void b(le le) {
		if (this.t()) {
			le.a("CustomDisplayTile", true);
			le.a("DisplayState", lq.a(this.p()));
			le.b("DisplayOffset", this.r());
		}
	}

	@Override
	public void i(aom aom) {
		if (!this.l.v) {
			if (!aom.H && !this.H) {
				if (!this.w(aom)) {
					double double3 = aom.cC() - this.cC();
					double double5 = aom.cG() - this.cG();
					double double7 = double3 * double3 + double5 * double5;
					if (double7 >= 1.0E-4F) {
						double7 = (double)aec.a(double7);
						double3 /= double7;
						double5 /= double7;
						double double9 = 1.0 / double7;
						if (double9 > 1.0) {
							double9 = 1.0;
						}

						double3 *= double9;
						double5 *= double9;
						double3 *= 0.1F;
						double5 *= 0.1F;
						double3 *= (double)(1.0F - this.I);
						double5 *= (double)(1.0F - this.I);
						double3 *= 0.5;
						double5 *= 0.5;
						if (aom instanceof bfr) {
							double double11 = aom.cC() - this.cC();
							double double13 = aom.cG() - this.cG();
							dem dem15 = new dem(double11, 0.0, double13).d();
							dem dem16 = new dem((double)aec.b(this.p * (float) (Math.PI / 180.0)), 0.0, (double)aec.a(this.p * (float) (Math.PI / 180.0))).d();
							double double17 = Math.abs(dem15.b(dem16));
							if (double17 < 0.8F) {
								return;
							}

							dem dem19 = this.cB();
							dem dem20 = aom.cB();
							if (((bfr)aom).o() == bfr.a.FURNACE && this.o() != bfr.a.FURNACE) {
								this.e(dem19.d(0.2, 1.0, 0.2));
								this.h(dem20.b - double3, 0.0, dem20.d - double5);
								aom.e(dem20.d(0.95, 1.0, 0.95));
							} else if (((bfr)aom).o() != bfr.a.FURNACE && this.o() == bfr.a.FURNACE) {
								aom.e(dem20.d(0.2, 1.0, 0.2));
								aom.h(dem19.b + double3, 0.0, dem19.d + double5);
								this.e(dem19.d(0.95, 1.0, 0.95));
							} else {
								double double21 = (dem20.b + dem19.b) / 2.0;
								double double23 = (dem20.d + dem19.d) / 2.0;
								this.e(dem19.d(0.2, 1.0, 0.2));
								this.h(double21 - double3, 0.0, double23 - double5);
								aom.e(dem20.d(0.2, 1.0, 0.2));
								aom.h(double21 + double3, 0.0, double23 + double5);
							}
						} else {
							this.h(-double3, 0.0, -double5);
							aom.h(double3 / 4.0, 0.0, double5 / 4.0);
						}
					}
				}
			}
		}
	}

	public void a(float float1) {
		this.S.b(d, float1);
	}

	public float k() {
		return this.S.a(d);
	}

	public void c(int integer) {
		this.S.b(b, integer);
	}

	public int m() {
		return this.S.a(b);
	}

	public void d(int integer) {
		this.S.b(c, integer);
	}

	public int n() {
		return this.S.a(c);
	}

	public abstract bfr.a o();

	public cfj p() {
		return !this.t() ? this.q() : bvr.a(this.Y().a(e));
	}

	public cfj q() {
		return bvs.a.n();
	}

	public int r() {
		return !this.t() ? this.s() : this.Y().a(f);
	}

	public int s() {
		return 6;
	}

	public void b(cfj cfj) {
		this.Y().b(e, bvr.i(cfj));
		this.a(true);
	}

	public void l(int integer) {
		this.Y().b(f, integer);
		this.a(true);
	}

	public boolean t() {
		return this.Y().a(g);
	}

	public void a(boolean boolean1) {
		this.Y().b(g, boolean1);
	}

	@Override
	public ni<?> O() {
		return new nm(this);
	}

	public static enum a {
		RIDEABLE,
		CHEST,
		FURNACE,
		TNT,
		SPAWNER,
		HOPPER,
		COMMAND_BLOCK;
	}
}
