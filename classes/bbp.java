import java.util.EnumSet;
import java.util.Random;
import javax.annotation.Nullable;

public class bbp extends bcu implements bcf {
	private boolean d;
	protected final awx b;
	protected final awu c;

	public bbp(aoq<? extends bbp> aoq, bqb bqb) {
		super(aoq, bqb);
		this.G = 1.0F;
		this.bo = new bbp.d(this);
		this.a(czb.WATER, 0.0F);
		this.b = new awx(this, bqb);
		this.c = new awu(this, bqb);
	}

	@Override
	protected void m() {
		this.br.a(1, new bbp.c(this, 1.0));
		this.br.a(2, new bbp.f(this, 1.0, 40, 10.0F));
		this.br.a(2, new bbp.a(this, 1.0, false));
		this.br.a(5, new bbp.b(this, 1.0));
		this.br.a(6, new bbp.e(this, 1.0, this.l.t_()));
		this.br.a(7, new avf(this, 1.0));
		this.bs.a(1, new awb(this, bbp.class).a(bcw.class));
		this.bs.a(2, new awc(this, bec.class, 10, true, false, this::j));
		this.bs.a(3, new awc(this, bdk.class, false));
		this.bs.a(3, new awc(this, ayt.class, true));
		this.bs.a(5, new awc(this, azi.class, 10, true, false, azi.bv));
	}

	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		apo = super.a(bqc, ane, apb, apo, le);
		if (this.b(aor.OFFHAND).a() && this.J.nextFloat() < 0.03F) {
			this.a(aor.OFFHAND, new bki(bkk.qN));
			this.bt[aor.OFFHAND.b()] = 2.0F;
		}

		return apo;
	}

	public static boolean b(aoq<bbp> aoq, bqc bqc, apb apb, fu fu, Random random) {
		bre bre6 = bqc.v(fu);
		boolean boolean7 = bqc.ac() != and.PEACEFUL && a(bqc, fu, random) && (apb == apb.SPAWNER || bqc.b(fu).a(acz.a));
		return bre6 != brk.i && bre6 != brk.m ? random.nextInt(40) == 0 && a(bqc, fu) && boolean7 : random.nextInt(15) == 0 && boolean7;
	}

	private static boolean a(bqc bqc, fu fu) {
		return fu.v() < bqc.t_() - 5;
	}

	@Override
	protected boolean eL() {
		return false;
	}

	@Override
	protected ack I() {
		return this.aA() ? acl.cU : acl.cT;
	}

	@Override
	protected ack e(anw anw) {
		return this.aA() ? acl.cY : acl.cX;
	}

	@Override
	protected ack dp() {
		return this.aA() ? acl.cW : acl.cV;
	}

	@Override
	protected ack eM() {
		return acl.da;
	}

	@Override
	protected ack aq() {
		return acl.db;
	}

	@Override
	protected bki eN() {
		return bki.b;
	}

	@Override
	protected void a(ane ane) {
		if ((double)this.J.nextFloat() > 0.9) {
			int integer3 = this.J.nextInt(16);
			if (integer3 < 10) {
				this.a(aor.MAINHAND, new bki(bkk.qL));
			} else {
				this.a(aor.MAINHAND, new bki(bkk.mi));
			}
		}
	}

	@Override
	protected boolean a(bki bki1, bki bki2) {
		if (bki2.b() == bkk.qN) {
			return false;
		} else if (bki2.b() == bkk.qL) {
			return bki1.b() == bkk.qL ? bki1.g() < bki2.g() : false;
		} else {
			return bki1.b() == bkk.qL ? true : super.a(bki1, bki2);
		}
	}

	@Override
	protected boolean eO() {
		return false;
	}

	@Override
	public boolean a(bqd bqd) {
		return bqd.i(this);
	}

	public boolean j(@Nullable aoy aoy) {
		return aoy != null ? !this.l.J() || aoy.aA() : false;
	}

	@Override
	public boolean bU() {
		return !this.bx();
	}

	private boolean eX() {
		if (this.d) {
			return true;
		} else {
			aoy aoy2 = this.A();
			return aoy2 != null && aoy2.aA();
		}
	}

	@Override
	public void f(dem dem) {
		if (this.dR() && this.aA() && this.eX()) {
			this.a(0.01F, dem);
			this.a(apd.SELF, this.cB());
			this.e(this.cB().a(0.9));
		} else {
			super.f(dem);
		}
	}

	@Override
	public void aF() {
		if (!this.l.v) {
			if (this.dR() && this.aA() && this.eX()) {
				this.bq = this.b;
				this.h(true);
			} else {
				this.bq = this.c;
				this.h(false);
			}
		}
	}

	protected boolean eP() {
		czf czf2 = this.x().k();
		if (czf2 != null) {
			fu fu3 = czf2.m();
			if (fu3 != null) {
				double double4 = this.g((double)fu3.u(), (double)fu3.v(), (double)fu3.w());
				if (double4 < 4.0) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public void a(aoy aoy, float float2) {
		bfe bfe4 = new bfe(this.l, this, new bki(bkk.qL));
		double double5 = aoy.cC() - this.cC();
		double double7 = aoy.e(0.3333333333333333) - bfe4.cD();
		double double9 = aoy.cG() - this.cG();
		double double11 = (double)aec.a(double5 * double5 + double9 * double9);
		bfe4.c(double5, double7 + double11 * 0.2F, double9, 1.6F, (float)(14 - this.l.ac().a() * 4));
		this.a(acl.cZ, 1.0F, 1.0F / (this.cX().nextFloat() * 0.4F + 0.8F));
		this.l.c(bfe4);
	}

	public void t(boolean boolean1) {
		this.d = boolean1;
	}

	static class a extends avy {
		private final bbp b;

		public a(bbp bbp, double double2, boolean boolean3) {
			super(bbp, double2, boolean3);
			this.b = bbp;
		}

		@Override
		public boolean a() {
			return super.a() && this.b.j(this.b.A());
		}

		@Override
		public boolean b() {
			return super.b() && this.b.j(this.b.A());
		}
	}

	static class b extends auu {
		private final bbp g;

		public b(bbp bbp, double double2) {
			super(bbp, double2, 8, 2);
			this.g = bbp;
		}

		@Override
		public boolean a() {
			return super.a() && !this.g.l.J() && this.g.aA() && this.g.cD() >= (double)(this.g.l.t_() - 3);
		}

		@Override
		public boolean b() {
			return super.b();
		}

		@Override
		protected boolean a(bqd bqd, fu fu) {
			fu fu4 = fu.b();
			return bqd.w(fu4) && bqd.w(fu4.b()) ? bqd.d_(fu).a(bqd, fu, this.g) : false;
		}

		@Override
		public void c() {
			this.g.t(false);
			this.g.bq = this.g.c;
			super.c();
		}

		@Override
		public void d() {
			super.d();
		}
	}

	static class c extends aug {
		private final apg a;
		private double b;
		private double c;
		private double d;
		private final double e;
		private final bqb f;

		public c(apg apg, double double2) {
			this.a = apg;
			this.e = double2;
			this.f = apg.l;
			this.a(EnumSet.of(aug.a.MOVE));
		}

		@Override
		public boolean a() {
			if (!this.f.J()) {
				return false;
			} else if (this.a.aA()) {
				return false;
			} else {
				dem dem2 = this.g();
				if (dem2 == null) {
					return false;
				} else {
					this.b = dem2.b;
					this.c = dem2.c;
					this.d = dem2.d;
					return true;
				}
			}
		}

		@Override
		public boolean b() {
			return !this.a.x().m();
		}

		@Override
		public void c() {
			this.a.x().a(this.b, this.c, this.d, this.e);
		}

		@Nullable
		private dem g() {
			Random random2 = this.a.cX();
			fu fu3 = this.a.cA();

			for (int integer4 = 0; integer4 < 10; integer4++) {
				fu fu5 = fu3.b(random2.nextInt(20) - 10, 2 - random2.nextInt(8), random2.nextInt(20) - 10);
				if (this.f.d_(fu5).a(bvs.A)) {
					return dem.c(fu5);
				}
			}

			return null;
		}
	}

	static class d extends atm {
		private final bbp i;

		public d(bbp bbp) {
			super(bbp);
			this.i = bbp;
		}

		@Override
		public void a() {
			aoy aoy2 = this.i.A();
			if (this.i.eX() && this.i.aA()) {
				if (aoy2 != null && aoy2.cD() > this.i.cD() || this.i.d) {
					this.i.e(this.i.cB().b(0.0, 0.002, 0.0));
				}

				if (this.h != atm.a.MOVE_TO || this.i.x().m()) {
					this.i.n(0.0F);
					return;
				}

				double double3 = this.b - this.i.cC();
				double double5 = this.c - this.i.cD();
				double double7 = this.d - this.i.cG();
				double double9 = (double)aec.a(double3 * double3 + double5 * double5 + double7 * double7);
				double5 /= double9;
				float float11 = (float)(aec.d(double7, double3) * 180.0F / (float)Math.PI) - 90.0F;
				this.i.p = this.a(this.i.p, float11, 90.0F);
				this.i.aH = this.i.p;
				float float12 = (float)(this.e * this.i.b(apx.d));
				float float13 = aec.g(0.125F, this.i.dM(), float12);
				this.i.n(float13);
				this.i.e(this.i.cB().b((double)float13 * double3 * 0.005, (double)float13 * double5 * 0.1, (double)float13 * double7 * 0.005));
			} else {
				if (!this.i.t) {
					this.i.e(this.i.cB().b(0.0, -0.008, 0.0));
				}

				super.a();
			}
		}
	}

	static class e extends aug {
		private final bbp a;
		private final double b;
		private final int c;
		private boolean d;

		public e(bbp bbp, double double2, int integer) {
			this.a = bbp;
			this.b = double2;
			this.c = integer;
		}

		@Override
		public boolean a() {
			return !this.a.l.J() && this.a.aA() && this.a.cD() < (double)(this.c - 2);
		}

		@Override
		public boolean b() {
			return this.a() && !this.d;
		}

		@Override
		public void e() {
			if (this.a.cD() < (double)(this.c - 1) && (this.a.x().m() || this.a.eP())) {
				dem dem2 = axu.b(this.a, 4, 8, new dem(this.a.cC(), (double)(this.c - 1), this.a.cG()));
				if (dem2 == null) {
					this.d = true;
					return;
				}

				this.a.x().a(dem2.b, dem2.c, dem2.d, this.b);
			}
		}

		@Override
		public void c() {
			this.a.t(true);
			this.d = false;
		}

		@Override
		public void d() {
			this.a.t(false);
		}
	}

	static class f extends avh {
		private final bbp a;

		public f(bcf bcf, double double2, int integer, float float4) {
			super(bcf, double2, integer, float4);
			this.a = (bbp)bcf;
		}

		@Override
		public boolean a() {
			return super.a() && this.a.dC().b() == bkk.qL;
		}

		@Override
		public void c() {
			super.c();
			this.a.s(true);
			this.a.c(anf.MAIN_HAND);
		}

		@Override
		public void d() {
			super.d();
			this.a.eb();
			this.a.s(false);
		}
	}
}
