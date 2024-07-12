import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class azi extends ayk {
	private static final tq<fu> bw = tt.a(azi.class, ts.l);
	private static final tq<Boolean> bx = tt.a(azi.class, ts.i);
	private static final tq<Boolean> by = tt.a(azi.class, ts.i);
	private static final tq<fu> bz = tt.a(azi.class, ts.l);
	private static final tq<Boolean> bA = tt.a(azi.class, ts.i);
	private static final tq<Boolean> bB = tt.a(azi.class, ts.i);
	private int bC;
	public static final Predicate<aoy> bv = aoy -> aoy.x_() && !aoy.aA();

	public azi(aoq<? extends azi> aoq, bqb bqb) {
		super(aoq, bqb);
		this.a(czb.WATER, 0.0F);
		this.bo = new azi.e(this);
		this.G = 1.0F;
	}

	public void g(fu fu) {
		this.S.b(bw, fu);
	}

	private fu eO() {
		return this.S.a(bw);
	}

	private void h(fu fu) {
		this.S.b(bz, fu);
	}

	private fu eP() {
		return this.S.a(bz);
	}

	public boolean eL() {
		return this.S.a(bx);
	}

	private void t(boolean boolean1) {
		this.S.b(bx, boolean1);
	}

	public boolean eM() {
		return this.S.a(by);
	}

	private void u(boolean boolean1) {
		this.bC = boolean1 ? 1 : 0;
		this.S.b(by, boolean1);
	}

	private boolean eV() {
		return this.S.a(bA);
	}

	private void v(boolean boolean1) {
		this.S.b(bA, boolean1);
	}

	private boolean eW() {
		return this.S.a(bB);
	}

	private void w(boolean boolean1) {
		this.S.b(bB, boolean1);
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(bw, fu.b);
		this.S.a(bx, false);
		this.S.a(bz, fu.b);
		this.S.a(bA, false);
		this.S.a(bB, false);
		this.S.a(by, false);
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.b("HomePosX", this.eO().u());
		le.b("HomePosY", this.eO().v());
		le.b("HomePosZ", this.eO().w());
		le.a("HasEgg", this.eL());
		le.b("TravelPosX", this.eP().u());
		le.b("TravelPosY", this.eP().v());
		le.b("TravelPosZ", this.eP().w());
	}

	@Override
	public void a(le le) {
		int integer3 = le.h("HomePosX");
		int integer4 = le.h("HomePosY");
		int integer5 = le.h("HomePosZ");
		this.g(new fu(integer3, integer4, integer5));
		super.a(le);
		this.t(le.q("HasEgg"));
		int integer6 = le.h("TravelPosX");
		int integer7 = le.h("TravelPosY");
		int integer8 = le.h("TravelPosZ");
		this.h(new fu(integer6, integer7, integer8));
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		this.g(this.cA());
		this.h(fu.b);
		return super.a(bqc, ane, apb, apo, le);
	}

	public static boolean c(aoq<azi> aoq, bqc bqc, apb apb, fu fu, Random random) {
		return fu.v() < bqc.t_() + 4 && cch.a(bqc, fu) && bqc.b(fu, 0) > 8;
	}

	@Override
	protected void o() {
		this.br.a(0, new azi.f(this, 1.2));
		this.br.a(1, new azi.a(this, 1.0));
		this.br.a(1, new azi.d(this, 1.0));
		this.br.a(2, new azi.i(this, 1.1, bvs.aU.h()));
		this.br.a(3, new azi.c(this, 1.0));
		this.br.a(4, new azi.b(this, 1.0));
		this.br.a(7, new azi.j(this, 1.0));
		this.br.a(8, new auo(this, bec.class, 8.0F));
		this.br.a(9, new azi.h(this, 1.0, 100));
	}

	public static apw.a eN() {
		return aoz.p().a(apx.a, 30.0).a(apx.d, 0.25);
	}

	@Override
	public boolean bU() {
		return false;
	}

	@Override
	public boolean cL() {
		return true;
	}

	@Override
	public apc dB() {
		return apc.e;
	}

	@Override
	public int D() {
		return 200;
	}

	@Nullable
	@Override
	protected ack I() {
		return !this.aA() && this.t && !this.x_() ? acl.pm : super.I();
	}

	@Override
	protected void d(float float1) {
		super.d(float1 * 1.5F);
	}

	@Override
	protected ack aq() {
		return acl.px;
	}

	@Nullable
	@Override
	protected ack e(anw anw) {
		return this.x_() ? acl.pt : acl.ps;
	}

	@Nullable
	@Override
	protected ack dp() {
		return this.x_() ? acl.po : acl.pn;
	}

	@Override
	protected void a(fu fu, cfj cfj) {
		ack ack4 = this.x_() ? acl.pw : acl.pv;
		this.a(ack4, 0.15F, 1.0F);
	}

	@Override
	public boolean eQ() {
		return super.eQ() && !this.eL();
	}

	@Override
	protected float ao() {
		return this.B + 0.15F;
	}

	@Override
	public float cR() {
		return this.x_() ? 0.3F : 1.0F;
	}

	@Override
	protected awv b(bqb bqb) {
		return new azi.g(this, bqb);
	}

	@Nullable
	@Override
	public aok a(aok aok) {
		return aoq.aM.a(this.l);
	}

	@Override
	public boolean k(bki bki) {
		return bki.b() == bvs.aU.h();
	}

	@Override
	public float a(fu fu, bqd bqd) {
		if (!this.eV() && bqd.b(fu).a(acz.a)) {
			return 10.0F;
		} else {
			return cch.a(bqd, fu) ? 10.0F : bqd.y(fu) - 0.5F;
		}
	}

	@Override
	public void k() {
		super.k();
		if (this.aU() && this.eM() && this.bC >= 1 && this.bC % 5 == 0) {
			fu fu2 = this.cA();
			if (cch.a(this.l, fu2)) {
				this.l.c(2001, fu2, bvr.i(bvs.C.n()));
			}
		}
	}

	@Override
	protected void m() {
		super.m();
		if (!this.x_() && this.l.S().b(bpx.e)) {
			this.a(bkk.jZ, 1);
		}
	}

	@Override
	public void f(dem dem) {
		if (this.dR() && this.aA()) {
			this.a(0.1F, dem);
			this.a(apd.SELF, this.cB());
			this.e(this.cB().a(0.9));
			if (this.A() == null && (!this.eV() || !this.eO().a(this.cz(), 20.0))) {
				this.e(this.cB().b(0.0, -0.005, 0.0));
			}
		} else {
			super.f(dem);
		}
	}

	@Override
	public boolean a(bec bec) {
		return false;
	}

	@Override
	public void a(aox aox) {
		this.a(anw.b, Float.MAX_VALUE);
	}

	static class a extends att {
		private final azi d;

		a(azi azi, double double2) {
			super(azi, double2);
			this.d = azi;
		}

		@Override
		public boolean a() {
			return super.a() && !this.d.eL();
		}

		@Override
		protected void g() {
			ze ze2 = this.a.eS();
			if (ze2 == null && this.c.eS() != null) {
				ze2 = this.c.eS();
			}

			if (ze2 != null) {
				ze2.a(acu.O);
				aa.o.a(ze2, this.a, this.c, null);
			}

			this.d.t(true);
			this.a.eU();
			this.c.eU();
			Random random3 = this.a.cX();
			if (this.b.S().b(bpx.e)) {
				this.b.c(new aos(this.b, this.a.cC(), this.a.cD(), this.a.cG(), random3.nextInt(7) + 1));
			}
		}
	}

	static class b extends aug {
		private final azi a;
		private final double b;
		private boolean c;
		private int d;

		b(azi azi, double double2) {
			this.a = azi;
			this.b = double2;
		}

		@Override
		public boolean a() {
			if (this.a.x_()) {
				return false;
			} else if (this.a.eL()) {
				return true;
			} else {
				return this.a.cX().nextInt(700) != 0 ? false : !this.a.eO().a(this.a.cz(), 64.0);
			}
		}

		@Override
		public void c() {
			this.a.v(true);
			this.c = false;
			this.d = 0;
		}

		@Override
		public void d() {
			this.a.v(false);
		}

		@Override
		public boolean b() {
			return !this.a.eO().a(this.a.cz(), 7.0) && !this.c && this.d <= 600;
		}

		@Override
		public void e() {
			fu fu2 = this.a.eO();
			boolean boolean3 = fu2.a(this.a.cz(), 16.0);
			if (boolean3) {
				this.d++;
			}

			if (this.a.x().m()) {
				dem dem4 = dem.c(fu2);
				dem dem5 = axu.a(this.a, 16, 3, dem4, (float) (Math.PI / 10));
				if (dem5 == null) {
					dem5 = axu.b(this.a, 8, 7, dem4);
				}

				if (dem5 != null && !boolean3 && !this.a.l.d_(new fu(dem5)).a(bvs.A)) {
					dem5 = axu.b(this.a, 16, 5, dem4);
				}

				if (dem5 == null) {
					this.c = true;
					return;
				}

				this.a.x().a(dem5.b, dem5.c, dem5.d, this.b);
			}
		}
	}

	static class c extends auu {
		private final azi g;

		private c(azi azi, double double2) {
			super(azi, azi.x_() ? 2.0 : double2, 24);
			this.g = azi;
			this.f = -1;
		}

		@Override
		public boolean b() {
			return !this.g.aA() && this.d <= 1200 && this.a(this.g.l, this.e);
		}

		@Override
		public boolean a() {
			if (this.g.x_() && !this.g.aA()) {
				return super.a();
			} else {
				return !this.g.eV() && !this.g.aA() && !this.g.eL() ? super.a() : false;
			}
		}

		@Override
		public boolean j() {
			return this.d % 160 == 0;
		}

		@Override
		protected boolean a(bqd bqd, fu fu) {
			return bqd.d_(fu).a(bvs.A);
		}
	}

	static class d extends auu {
		private final azi g;

		d(azi azi, double double2) {
			super(azi, double2, 16);
			this.g = azi;
		}

		@Override
		public boolean a() {
			return this.g.eL() && this.g.eO().a(this.g.cz(), 9.0) ? super.a() : false;
		}

		@Override
		public boolean b() {
			return super.b() && this.g.eL() && this.g.eO().a(this.g.cz(), 9.0);
		}

		@Override
		public void e() {
			super.e();
			fu fu2 = this.g.cA();
			if (!this.g.aA() && this.k()) {
				if (this.g.bC < 1) {
					this.g.u(true);
				} else if (this.g.bC > 200) {
					bqb bqb3 = this.g.l;
					bqb3.a(null, fu2, acl.pu, acm.BLOCKS, 0.3F, 0.9F + bqb3.t.nextFloat() * 0.2F);
					bqb3.a(this.e.b(), bvs.kf.n().a(cch.b, Integer.valueOf(this.g.J.nextInt(4) + 1)), 3);
					this.g.t(false);
					this.g.u(false);
					this.g.s(600);
				}

				if (this.g.eM()) {
					this.g.bC++;
				}
			}
		}

		@Override
		protected boolean a(bqd bqd, fu fu) {
			return !bqd.w(fu.b()) ? false : cch.b(bqd, fu);
		}
	}

	static class e extends atm {
		private final azi i;

		e(azi azi) {
			super(azi);
			this.i = azi;
		}

		private void g() {
			if (this.i.aA()) {
				this.i.e(this.i.cB().b(0.0, 0.005, 0.0));
				if (!this.i.eO().a(this.i.cz(), 16.0)) {
					this.i.n(Math.max(this.i.dM() / 2.0F, 0.08F));
				}

				if (this.i.x_()) {
					this.i.n(Math.max(this.i.dM() / 3.0F, 0.06F));
				}
			} else if (this.i.t) {
				this.i.n(Math.max(this.i.dM() / 2.0F, 0.06F));
			}
		}

		@Override
		public void a() {
			this.g();
			if (this.h == atm.a.MOVE_TO && !this.i.x().m()) {
				double double2 = this.b - this.i.cC();
				double double4 = this.c - this.i.cD();
				double double6 = this.d - this.i.cG();
				double double8 = (double)aec.a(double2 * double2 + double4 * double4 + double6 * double6);
				double4 /= double8;
				float float10 = (float)(aec.d(double6, double2) * 180.0F / (float)Math.PI) - 90.0F;
				this.i.p = this.a(this.i.p, float10, 90.0F);
				this.i.aH = this.i.p;
				float float11 = (float)(this.e * this.i.b(apx.d));
				this.i.n(aec.g(0.125F, this.i.dM(), float11));
				this.i.e(this.i.cB().b(0.0, (double)this.i.dM() * double4 * 0.1, 0.0));
			} else {
				this.i.n(0.0F);
			}
		}
	}

	static class f extends avb {
		f(azi azi, double double2) {
			super(azi, double2);
		}

		@Override
		public boolean a() {
			if (this.a.cY() == null && !this.a.bm()) {
				return false;
			} else {
				fu fu2 = this.a(this.a.l, this.a, 7, 4);
				if (fu2 != null) {
					this.c = (double)fu2.u();
					this.d = (double)fu2.v();
					this.e = (double)fu2.w();
					return true;
				} else {
					return this.g();
				}
			}
		}
	}

	static class g extends awx {
		g(azi azi, bqb bqb) {
			super(azi, bqb);
		}

		@Override
		protected boolean a() {
			return true;
		}

		@Override
		protected czh a(int integer) {
			this.o = new czk();
			return new czh(this.o, integer);
		}

		@Override
		public boolean a(fu fu) {
			if (this.a instanceof azi) {
				azi azi3 = (azi)this.a;
				if (azi3.eW()) {
					return this.b.d_(fu).a(bvs.A);
				}
			}

			return !this.b.d_(fu.c()).g();
		}
	}

	static class h extends avf {
		private final azi h;

		private h(azi azi, double double2, int integer) {
			super(azi, double2, integer);
			this.h = azi;
		}

		@Override
		public boolean a() {
			return !this.a.aA() && !this.h.eV() && !this.h.eL() ? super.a() : false;
		}
	}

	static class i extends aug {
		private static final axs a = new axs().a(10.0).b().a();
		private final azi b;
		private final double c;
		private bec d;
		private int e;
		private final Set<bke> f;

		i(azi azi, double double2, bke bke) {
			this.b = azi;
			this.c = double2;
			this.f = Sets.<bke>newHashSet(bke);
			this.a(EnumSet.of(aug.a.MOVE, aug.a.LOOK));
		}

		@Override
		public boolean a() {
			if (this.e > 0) {
				this.e--;
				return false;
			} else {
				this.d = this.b.l.a(a, this.b);
				return this.d == null ? false : this.a(this.d.dC()) || this.a(this.d.dD());
			}
		}

		private boolean a(bki bki) {
			return this.f.contains(bki.b());
		}

		@Override
		public boolean b() {
			return this.a();
		}

		@Override
		public void d() {
			this.d = null;
			this.b.x().o();
			this.e = 100;
		}

		@Override
		public void e() {
			this.b.t().a(this.d, (float)(this.b.ep() + 20), (float)this.b.eo());
			if (this.b.h(this.d) < 6.25) {
				this.b.x().o();
			} else {
				this.b.x().a(this.d, this.c);
			}
		}
	}

	static class j extends aug {
		private final azi a;
		private final double b;
		private boolean c;

		j(azi azi, double double2) {
			this.a = azi;
			this.b = double2;
		}

		@Override
		public boolean a() {
			return !this.a.eV() && !this.a.eL() && this.a.aA();
		}

		@Override
		public void c() {
			int integer2 = 512;
			int integer3 = 4;
			Random random4 = this.a.J;
			int integer5 = random4.nextInt(1025) - 512;
			int integer6 = random4.nextInt(9) - 4;
			int integer7 = random4.nextInt(1025) - 512;
			if ((double)integer6 + this.a.cD() > (double)(this.a.l.t_() - 1)) {
				integer6 = 0;
			}

			fu fu8 = new fu((double)integer5 + this.a.cC(), (double)integer6 + this.a.cD(), (double)integer7 + this.a.cG());
			this.a.h(fu8);
			this.a.w(true);
			this.c = false;
		}

		@Override
		public void e() {
			if (this.a.x().m()) {
				dem dem2 = dem.c(this.a.eP());
				dem dem3 = axu.a(this.a, 16, 3, dem2, (float) (Math.PI / 10));
				if (dem3 == null) {
					dem3 = axu.b(this.a, 8, 7, dem2);
				}

				if (dem3 != null) {
					int integer4 = aec.c(dem3.b);
					int integer5 = aec.c(dem3.d);
					int integer6 = 34;
					if (!this.a.l.a(integer4 - 34, 0, integer5 - 34, integer4 + 34, 0, integer5 + 34)) {
						dem3 = null;
					}
				}

				if (dem3 == null) {
					this.c = true;
					return;
				}

				this.a.x().a(dem3.b, dem3.c, dem3.d, this.b);
			}
		}

		@Override
		public boolean b() {
			return !this.a.x().m() && !this.c && !this.a.eV() && !this.a.eT() && !this.a.eL();
		}

		@Override
		public void d() {
			this.a.w(false);
			super.d();
		}
	}
}
