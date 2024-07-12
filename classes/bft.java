import java.util.List;
import javax.annotation.Nullable;

public class bft extends aom {
	private static final tq<Integer> b = tt.a(bft.class, ts.b);
	private static final tq<Integer> c = tt.a(bft.class, ts.b);
	private static final tq<Float> d = tt.a(bft.class, ts.c);
	private static final tq<Integer> e = tt.a(bft.class, ts.b);
	private static final tq<Boolean> f = tt.a(bft.class, ts.i);
	private static final tq<Boolean> g = tt.a(bft.class, ts.i);
	private static final tq<Integer> an = tt.a(bft.class, ts.b);
	private final float[] ao = new float[2];
	private float ap;
	private float aq;
	private float ar;
	private int as;
	private double at;
	private double au;
	private double av;
	private double aw;
	private double ax;
	private boolean ay;
	private boolean az;
	private boolean aA;
	private boolean aB;
	private double aC;
	private float aD;
	private bft.a aE;
	private bft.a aF;
	private double aG;
	private boolean aH;
	private boolean aI;
	private float aJ;
	private float aK;
	private float aL;

	public bft(aoq<? extends bft> aoq, bqb bqb) {
		super(aoq, bqb);
		this.i = true;
	}

	public bft(bqb bqb, double double2, double double3, double double4) {
		this(aoq.g, bqb);
		this.d(double2, double3, double4);
		this.e(dem.a);
		this.m = double2;
		this.n = double3;
		this.o = double4;
	}

	@Override
	protected float a(apj apj, aon aon) {
		return aon.b;
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
		this.S.a(e, bft.b.OAK.ordinal());
		this.S.a(f, false);
		this.S.a(g, false);
		this.S.a(an, 0);
	}

	@Nullable
	@Override
	public deg j(aom aom) {
		return aom.aR() ? aom.cb() : null;
	}

	@Nullable
	@Override
	public deg ay() {
		return this.cb();
	}

	@Override
	public boolean aR() {
		return true;
	}

	@Override
	public double aY() {
		return -0.1;
	}

	@Override
	public boolean a(anw anw, float float2) {
		if (this.b(anw)) {
			return false;
		} else if (!this.l.v && !this.y) {
			this.c(-this.o());
			this.b(10);
			this.a(this.m() + float2 * 10.0F);
			this.aP();
			boolean boolean4 = anw.k() instanceof bec && ((bec)anw.k()).bJ.d;
			if (boolean4 || this.m() > 40.0F) {
				if (!boolean4 && this.l.S().b(bpx.g)) {
					this.a(this.g());
				}

				this.aa();
			}

			return true;
		} else {
			return true;
		}
	}

	@Override
	public void k(boolean boolean1) {
		if (!this.l.v) {
			this.aH = true;
			this.aI = boolean1;
			if (this.z() == 0) {
				this.d(60);
			}
		}

		this.l.a(hh.Z, this.cC() + (double)this.J.nextFloat(), this.cD() + 0.7, this.cG() + (double)this.J.nextFloat(), 0.0, 0.0, 0.0);
		if (this.J.nextInt(20) == 0) {
			this.l.a(this.cC(), this.cD(), this.cG(), this.ar(), this.ct(), 1.0F, 0.8F + 0.4F * this.J.nextFloat(), false);
		}
	}

	@Override
	public void i(aom aom) {
		if (aom instanceof bft) {
			if (aom.cb().b < this.cb().e) {
				super.i(aom);
			}
		} else if (aom.cb().b <= this.cb().b) {
			super.i(aom);
		}
	}

	public bke g() {
		switch (this.p()) {
			case OAK:
			default:
				return bkk.lR;
			case SPRUCE:
				return bkk.qo;
			case BIRCH:
				return bkk.qp;
			case JUNGLE:
				return bkk.qq;
			case ACACIA:
				return bkk.qr;
			case DARK_OAK:
				return bkk.qs;
		}
	}

	@Override
	public boolean aQ() {
		return !this.y;
	}

	@Override
	public fz bZ() {
		return this.bY().g();
	}

	@Override
	public void j() {
		this.aF = this.aE;
		this.aE = this.s();
		if (this.aE != bft.a.UNDER_WATER && this.aE != bft.a.UNDER_FLOWING_WATER) {
			this.aq = 0.0F;
		} else {
			this.aq++;
		}

		if (!this.l.v && this.aq >= 60.0F) {
			this.ba();
		}

		if (this.n() > 0) {
			this.b(this.n() - 1);
		}

		if (this.m() > 0.0F) {
			this.a(this.m() - 1.0F);
		}

		super.j();
		this.r();
		if (this.cr()) {
			if (this.cm().isEmpty() || !(this.cm().get(0) instanceof bec)) {
				this.a(false, false);
			}

			this.v();
			if (this.l.v) {
				this.x();
				this.l.a(new ru(this.a(0), this.a(1)));
			}

			this.a(apd.SELF, this.cB());
		} else {
			this.e(dem.a);
		}

		this.q();

		for (int integer2 = 0; integer2 <= 1; integer2++) {
			if (this.a(integer2)) {
				if (!this.av()
					&& (double)(this.ao[integer2] % (float) (Math.PI * 2)) <= (float) (Math.PI / 4)
					&& ((double)this.ao[integer2] + (float) (Math.PI / 8)) % (float) (Math.PI * 2) >= (float) (Math.PI / 4)) {
					ack ack3 = this.h();
					if (ack3 != null) {
						dem dem4 = this.f(1.0F);
						double double5 = integer2 == 1 ? -dem4.d : dem4.d;
						double double7 = integer2 == 1 ? dem4.b : -dem4.b;
						this.l.a(null, this.cC() + double5, this.cD(), this.cG() + double7, ack3, this.ct(), 1.0F, 0.8F + 0.4F * this.J.nextFloat());
					}
				}

				this.ao[integer2] = (float)((double)this.ao[integer2] + (float) (Math.PI / 8));
			} else {
				this.ao[integer2] = 0.0F;
			}
		}

		this.at();
		List<aom> list2 = this.l.a(this, this.cb().c(0.2F, -0.01F, 0.2F), aop.a(this));
		if (!list2.isEmpty()) {
			boolean boolean3 = !this.l.v && !(this.cl() instanceof bec);

			for (int integer4 = 0; integer4 < list2.size(); integer4++) {
				aom aom5 = (aom)list2.get(integer4);
				if (!aom5.w(this)) {
					if (boolean3 && this.cm().size() < 2 && !aom5.bn() && aom5.cx() < this.cx() && aom5 instanceof aoy && !(aom5 instanceof azj) && !(aom5 instanceof bec)) {
						aom5.m(this);
					} else {
						this.i(aom5);
					}
				}
			}
		}
	}

	private void q() {
		if (this.l.v) {
			int integer2 = this.z();
			if (integer2 > 0) {
				this.aJ += 0.05F;
			} else {
				this.aJ -= 0.1F;
			}

			this.aJ = aec.a(this.aJ, 0.0F, 1.0F);
			this.aL = this.aK;
			this.aK = 10.0F * (float)Math.sin((double)(0.5F * (float)this.l.Q())) * this.aJ;
		} else {
			if (!this.aH) {
				this.d(0);
			}

			int integer2 = this.z();
			if (integer2 > 0) {
				this.d(--integer2);
				int integer3 = 60 - integer2 - 1;
				if (integer3 > 0 && integer2 == 0) {
					this.d(0);
					dem dem4 = this.cB();
					if (this.aI) {
						this.e(dem4.b(0.0, -0.7, 0.0));
						this.ba();
					} else {
						this.m(dem4.b, this.a(bec.class) ? 2.7 : 0.6, dem4.d);
					}
				}

				this.aH = false;
			}
		}
	}

	@Nullable
	protected ack h() {
		switch (this.s()) {
			case IN_WATER:
			case UNDER_WATER:
			case UNDER_FLOWING_WATER:
				return acl.aR;
			case ON_LAND:
				return acl.aQ;
			case IN_AIR:
			default:
				return null;
		}
	}

	private void r() {
		if (this.cr()) {
			this.as = 0;
			this.c(this.cC(), this.cD(), this.cG());
		}

		if (this.as > 0) {
			double double2 = this.cC() + (this.at - this.cC()) / (double)this.as;
			double double4 = this.cD() + (this.au - this.cD()) / (double)this.as;
			double double6 = this.cG() + (this.av - this.cG()) / (double)this.as;
			double double8 = aec.g(this.aw - (double)this.p);
			this.p = (float)((double)this.p + double8 / (double)this.as);
			this.q = (float)((double)this.q + (this.ax - (double)this.q) / (double)this.as);
			this.as--;
			this.d(double2, double4, double6);
			this.a(this.p, this.q);
		}
	}

	public void a(boolean boolean1, boolean boolean2) {
		this.S.b(f, boolean1);
		this.S.b(g, boolean2);
	}

	private bft.a s() {
		bft.a a2 = this.u();
		if (a2 != null) {
			this.aC = this.cb().e;
			return a2;
		} else if (this.t()) {
			return bft.a.IN_WATER;
		} else {
			float float3 = this.k();
			if (float3 > 0.0F) {
				this.aD = float3;
				return bft.a.ON_LAND;
			} else {
				return bft.a.IN_AIR;
			}
		}
	}

	public float i() {
		deg deg2 = this.cb();
		int integer3 = aec.c(deg2.a);
		int integer4 = aec.f(deg2.d);
		int integer5 = aec.c(deg2.e);
		int integer6 = aec.f(deg2.e - this.aG);
		int integer7 = aec.c(deg2.c);
		int integer8 = aec.f(deg2.f);
		fu.a a9 = new fu.a();

		label39:
		for (int integer10 = integer5; integer10 < integer6; integer10++) {
			float float11 = 0.0F;

			for (int integer12 = integer3; integer12 < integer4; integer12++) {
				for (int integer13 = integer7; integer13 < integer8; integer13++) {
					a9.d(integer12, integer10, integer13);
					cxa cxa14 = this.l.b(a9);
					if (cxa14.a(acz.a)) {
						float11 = Math.max(float11, cxa14.a((bpg)this.l, a9));
					}

					if (float11 >= 1.0F) {
						continue label39;
					}
				}
			}

			if (float11 < 1.0F) {
				return (float)a9.v() + float11;
			}
		}

		return (float)(integer6 + 1);
	}

	public float k() {
		deg deg2 = this.cb();
		deg deg3 = new deg(deg2.a, deg2.b - 0.001, deg2.c, deg2.d, deg2.b, deg2.f);
		int integer4 = aec.c(deg3.a) - 1;
		int integer5 = aec.f(deg3.d) + 1;
		int integer6 = aec.c(deg3.b) - 1;
		int integer7 = aec.f(deg3.e) + 1;
		int integer8 = aec.c(deg3.c) - 1;
		int integer9 = aec.f(deg3.f) + 1;
		dfg dfg10 = dfd.a(deg3);
		float float11 = 0.0F;
		int integer12 = 0;
		fu.a a13 = new fu.a();

		for (int integer14 = integer4; integer14 < integer5; integer14++) {
			for (int integer15 = integer8; integer15 < integer9; integer15++) {
				int integer16 = (integer14 != integer4 && integer14 != integer5 - 1 ? 0 : 1) + (integer15 != integer8 && integer15 != integer9 - 1 ? 0 : 1);
				if (integer16 != 2) {
					for (int integer17 = integer6; integer17 < integer7; integer17++) {
						if (integer16 <= 0 || integer17 != integer6 && integer17 != integer7 - 1) {
							a13.d(integer14, integer17, integer15);
							cfj cfj18 = this.l.d_(a13);
							if (!(cfj18.b() instanceof ccq) && dfd.c(cfj18.k(this.l, a13).a((double)integer14, (double)integer17, (double)integer15), dfg10, deq.i)) {
								float11 += cfj18.b().j();
								integer12++;
							}
						}
					}
				}
			}
		}

		return float11 / (float)integer12;
	}

	private boolean t() {
		deg deg2 = this.cb();
		int integer3 = aec.c(deg2.a);
		int integer4 = aec.f(deg2.d);
		int integer5 = aec.c(deg2.b);
		int integer6 = aec.f(deg2.b + 0.001);
		int integer7 = aec.c(deg2.c);
		int integer8 = aec.f(deg2.f);
		boolean boolean9 = false;
		this.aC = Double.MIN_VALUE;
		fu.a a10 = new fu.a();

		for (int integer11 = integer3; integer11 < integer4; integer11++) {
			for (int integer12 = integer5; integer12 < integer6; integer12++) {
				for (int integer13 = integer7; integer13 < integer8; integer13++) {
					a10.d(integer11, integer12, integer13);
					cxa cxa14 = this.l.b(a10);
					if (cxa14.a(acz.a)) {
						float float15 = (float)integer12 + cxa14.a((bpg)this.l, a10);
						this.aC = Math.max((double)float15, this.aC);
						boolean9 |= deg2.b < (double)float15;
					}
				}
			}
		}

		return boolean9;
	}

	@Nullable
	private bft.a u() {
		deg deg2 = this.cb();
		double double3 = deg2.e + 0.001;
		int integer5 = aec.c(deg2.a);
		int integer6 = aec.f(deg2.d);
		int integer7 = aec.c(deg2.e);
		int integer8 = aec.f(double3);
		int integer9 = aec.c(deg2.c);
		int integer10 = aec.f(deg2.f);
		boolean boolean11 = false;
		fu.a a12 = new fu.a();

		for (int integer13 = integer5; integer13 < integer6; integer13++) {
			for (int integer14 = integer7; integer14 < integer8; integer14++) {
				for (int integer15 = integer9; integer15 < integer10; integer15++) {
					a12.d(integer13, integer14, integer15);
					cxa cxa16 = this.l.b(a12);
					if (cxa16.a(acz.a) && double3 < (double)((float)a12.v() + cxa16.a((bpg)this.l, a12))) {
						if (!cxa16.b()) {
							return bft.a.UNDER_FLOWING_WATER;
						}

						boolean11 = true;
					}
				}
			}
		}

		return boolean11 ? bft.a.UNDER_WATER : null;
	}

	private void v() {
		double double2 = -0.04F;
		double double4 = this.aw() ? 0.0 : -0.04F;
		double double6 = 0.0;
		this.ap = 0.05F;
		if (this.aF == bft.a.IN_AIR && this.aE != bft.a.IN_AIR && this.aE != bft.a.ON_LAND) {
			this.aC = this.e(1.0);
			this.d(this.cC(), (double)(this.i() - this.cy()) + 0.101, this.cG());
			this.e(this.cB().d(1.0, 0.0, 1.0));
			this.aG = 0.0;
			this.aE = bft.a.IN_WATER;
		} else {
			if (this.aE == bft.a.IN_WATER) {
				double6 = (this.aC - this.cD()) / (double)this.cy();
				this.ap = 0.9F;
			} else if (this.aE == bft.a.UNDER_FLOWING_WATER) {
				double4 = -7.0E-4;
				this.ap = 0.9F;
			} else if (this.aE == bft.a.UNDER_WATER) {
				double6 = 0.01F;
				this.ap = 0.45F;
			} else if (this.aE == bft.a.IN_AIR) {
				this.ap = 0.9F;
			} else if (this.aE == bft.a.ON_LAND) {
				this.ap = this.aD;
				if (this.cl() instanceof bec) {
					this.aD /= 2.0F;
				}
			}

			dem dem8 = this.cB();
			this.m(dem8.b * (double)this.ap, dem8.c + double4, dem8.d * (double)this.ap);
			this.ar = this.ar * this.ap;
			if (double6 > 0.0) {
				dem dem9 = this.cB();
				this.m(dem9.b, (dem9.c + double6 * 0.06153846016296973) * 0.75, dem9.d);
			}
		}
	}

	private void x() {
		if (this.bo()) {
			float float2 = 0.0F;
			if (this.ay) {
				this.ar--;
			}

			if (this.az) {
				this.ar++;
			}

			if (this.az != this.ay && !this.aA && !this.aB) {
				float2 += 0.005F;
			}

			this.p = this.p + this.ar;
			if (this.aA) {
				float2 += 0.04F;
			}

			if (this.aB) {
				float2 -= 0.005F;
			}

			this.e(this.cB().b((double)(aec.a(-this.p * (float) (Math.PI / 180.0)) * float2), 0.0, (double)(aec.b(this.p * (float) (Math.PI / 180.0)) * float2)));
			this.a(this.az && !this.ay || this.aA, this.ay && !this.az || this.aA);
		}
	}

	@Override
	public void k(aom aom) {
		if (this.w(aom)) {
			float float3 = 0.0F;
			float float4 = (float)((this.y ? 0.01F : this.aY()) + aom.aX());
			if (this.cm().size() > 1) {
				int integer5 = this.cm().indexOf(aom);
				if (integer5 == 0) {
					float3 = 0.2F;
				} else {
					float3 = -0.6F;
				}

				if (aom instanceof ayk) {
					float3 = (float)((double)float3 + 0.2);
				}
			}

			dem dem5 = new dem((double)float3, 0.0, 0.0).b(-this.p * (float) (Math.PI / 180.0) - (float) (Math.PI / 2));
			aom.d(this.cC() + dem5.b, this.cD() + (double)float4, this.cG() + dem5.d);
			aom.p = aom.p + this.ar;
			aom.k(aom.bG() + this.ar);
			this.a(aom);
			if (aom instanceof ayk && this.cm().size() > 1) {
				int integer6 = aom.V() % 2 == 0 ? 90 : 270;
				aom.l(((ayk)aom).aH + (float)integer6);
				aom.k(aom.bG() + (float)integer6);
			}
		}
	}

	@Override
	public dem c(aoy aoy) {
		dem dem3 = a((double)(this.cx() * aec.a), (double)aoy.cx(), this.p);
		double double4 = this.cC() + dem3.b;
		double double6 = this.cG() + dem3.d;
		fu fu8 = new fu(double4, this.cb().e, double6);
		fu fu9 = fu8.c();
		if (!this.l.A(fu9)) {
			for (apj apj11 : aoy.ei()) {
				deg deg12 = aoy.f(apj11);
				double double13 = this.l.m(fu8);
				if (bfu.a(double13)) {
					dem dem15 = new dem(double4, (double)fu8.v() + double13, double6);
					if (bfu.a(this.l, aoy, deg12.c(dem15))) {
						aoy.b(apj11);
						return dem15;
					}
				}

				double double15 = this.l.m(fu9);
				if (bfu.a(double15)) {
					dem dem17 = new dem(double4, (double)fu9.v() + double15, double6);
					if (bfu.a(this.l, aoy, deg12.c(dem17))) {
						aoy.b(apj11);
						return dem17;
					}
				}
			}
		}

		return super.c(aoy);
	}

	protected void a(aom aom) {
		aom.l(this.p);
		float float3 = aec.g(aom.p - this.p);
		float float4 = aec.a(float3, -105.0F, 105.0F);
		aom.r += float4 - float3;
		aom.p += float4 - float3;
		aom.k(aom.p);
	}

	@Override
	protected void b(le le) {
		le.a("Type", this.p().a());
	}

	@Override
	protected void a(le le) {
		if (le.c("Type", 8)) {
			this.a(bft.b.a(le.l("Type")));
		}
	}

	@Override
	public ang a(bec bec, anf anf) {
		if (bec.ep()) {
			return ang.PASS;
		} else if (this.aq < 60.0F) {
			if (!this.l.v) {
				return bec.m(this) ? ang.CONSUME : ang.PASS;
			} else {
				return ang.SUCCESS;
			}
		} else {
			return ang.PASS;
		}
	}

	@Override
	protected void a(double double1, boolean boolean2, cfj cfj, fu fu) {
		this.aG = this.cB().c;
		if (!this.bn()) {
			if (boolean2) {
				if (this.C > 3.0F) {
					if (this.aE != bft.a.ON_LAND) {
						this.C = 0.0F;
						return;
					}

					this.b(this.C, 1.0F);
					if (!this.l.v && !this.y) {
						this.aa();
						if (this.l.S().b(bpx.g)) {
							for (int integer7 = 0; integer7 < 3; integer7++) {
								this.a(this.p().b());
							}

							for (int integer7 = 0; integer7 < 2; integer7++) {
								this.a(bkk.kB);
							}
						}
					}
				}

				this.C = 0.0F;
			} else if (!this.l.b(this.cA().c()).a(acz.a) && double1 < 0.0) {
				this.C = (float)((double)this.C - double1);
			}
		}
	}

	public boolean a(int integer) {
		return this.S.a(integer == 0 ? f : g) && this.cl() != null;
	}

	public void a(float float1) {
		this.S.b(d, float1);
	}

	public float m() {
		return this.S.a(d);
	}

	public void b(int integer) {
		this.S.b(b, integer);
	}

	public int n() {
		return this.S.a(b);
	}

	private void d(int integer) {
		this.S.b(an, integer);
	}

	private int z() {
		return this.S.a(an);
	}

	public void c(int integer) {
		this.S.b(c, integer);
	}

	public int o() {
		return this.S.a(c);
	}

	public void a(bft.b b) {
		this.S.b(e, b.ordinal());
	}

	public bft.b p() {
		return bft.b.a(this.S.a(e));
	}

	@Override
	protected boolean q(aom aom) {
		return this.cm().size() < 2 && !this.a(acz.a);
	}

	@Nullable
	@Override
	public aom cl() {
		List<aom> list2 = this.cm();
		return list2.isEmpty() ? null : (aom)list2.get(0);
	}

	@Override
	public ni<?> O() {
		return new nm(this);
	}

	@Override
	public boolean aE() {
		return this.aE == bft.a.UNDER_WATER || this.aE == bft.a.UNDER_FLOWING_WATER;
	}

	public static enum a {
		IN_WATER,
		UNDER_WATER,
		UNDER_FLOWING_WATER,
		ON_LAND,
		IN_AIR;
	}

	public static enum b {
		OAK(bvs.n, "oak"),
		SPRUCE(bvs.o, "spruce"),
		BIRCH(bvs.p, "birch"),
		JUNGLE(bvs.q, "jungle"),
		ACACIA(bvs.r, "acacia"),
		DARK_OAK(bvs.s, "dark_oak");

		private final String g;
		private final bvr h;

		private b(bvr bvr, String string4) {
			this.g = string4;
			this.h = bvr;
		}

		public String a() {
			return this.g;
		}

		public bvr b() {
			return this.h;
		}

		public String toString() {
			return this.g;
		}

		public static bft.b a(int integer) {
			bft.b[] arr2 = values();
			if (integer < 0 || integer >= arr2.length) {
				integer = 0;
			}

			return arr2[integer];
		}

		public static bft.b a(String string) {
			bft.b[] arr2 = values();

			for (int integer3 = 0; integer3 < arr2.length; integer3++) {
				if (arr2[integer3].a().equals(string)) {
					return arr2[integer3];
				}
			}

			return arr2[0];
		}
	}
}
