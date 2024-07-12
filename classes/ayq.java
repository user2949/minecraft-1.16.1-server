import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class ayq extends azj {
	private static final tq<fu> c = tt.a(ayq.class, ts.l);
	private static final tq<Boolean> d = tt.a(ayq.class, ts.i);
	private static final tq<Integer> bv = tt.a(ayq.class, ts.b);
	private static final axs bw = new axs().a(10.0).b().a().c();
	public static final Predicate<bbg> b = bbg -> !bbg.p() && bbg.aU() && bbg.aA();

	public ayq(aoq<? extends ayq> aoq, bqb bqb) {
		super(aoq, bqb);
		this.bo = new ayq.a(this);
		this.g = new ati(this, 10);
		this.p(true);
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		this.j(this.bD());
		this.q = 0.0F;
		return super.a(bqc, ane, apb, apo, le);
	}

	@Override
	public boolean cL() {
		return false;
	}

	@Override
	protected void a(int integer) {
	}

	public void g(fu fu) {
		this.S.b(c, fu);
	}

	public fu m() {
		return this.S.a(c);
	}

	public boolean eL() {
		return this.S.a(d);
	}

	public void t(boolean boolean1) {
		this.S.b(d, boolean1);
	}

	public int eM() {
		return this.S.a(bv);
	}

	public void b(int integer) {
		this.S.b(bv, integer);
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(c, fu.b);
		this.S.a(d, false);
		this.S.a(bv, 2400);
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.b("TreasurePosX", this.m().u());
		le.b("TreasurePosY", this.m().v());
		le.b("TreasurePosZ", this.m().w());
		le.a("GotFish", this.eL());
		le.b("Moistness", this.eM());
	}

	@Override
	public void a(le le) {
		int integer3 = le.h("TreasurePosX");
		int integer4 = le.h("TreasurePosY");
		int integer5 = le.h("TreasurePosZ");
		this.g(new fu(integer3, integer4, integer5));
		super.a(le);
		this.t(le.q("GotFish"));
		this.b(le.h("Moistness"));
	}

	@Override
	protected void o() {
		this.br.a(0, new ats(this));
		this.br.a(0, new avt(this));
		this.br.a(1, new ayq.b(this));
		this.br.a(2, new ayq.c(this, 4.0));
		this.br.a(4, new avg(this, 1.0, 10));
		this.br.a(4, new ave(this));
		this.br.a(5, new auo(this, bec.class, 6.0F));
		this.br.a(5, new atw(this, 10));
		this.br.a(6, new auq(this, 1.2F, true));
		this.br.a(8, new ayq.d());
		this.br.a(8, new aub(this));
		this.br.a(9, new ato(this, bbx.class, 8.0F, 1.0, 1.0));
		this.bs.a(1, new awb(this, bbx.class).a());
	}

	public static apw.a eN() {
		return aoz.p().a(apx.a, 10.0).a(apx.d, 1.2F).a(apx.f, 3.0);
	}

	@Override
	protected awv b(bqb bqb) {
		return new awx(this, bqb);
	}

	@Override
	public boolean B(aom aom) {
		boolean boolean3 = aom.a(anw.c(this), (float)((int)this.b(apx.f)));
		if (boolean3) {
			this.a(this, aom);
			this.a(acl.cF, 1.0F, 1.0F);
		}

		return boolean3;
	}

	@Override
	public int bD() {
		return 4800;
	}

	@Override
	protected int m(int integer) {
		return this.bD();
	}

	@Override
	protected float b(apj apj, aon aon) {
		return 0.3F;
	}

	@Override
	public int eo() {
		return 1;
	}

	@Override
	public int ep() {
		return 1;
	}

	@Override
	protected boolean n(aom aom) {
		return true;
	}

	@Override
	public boolean e(bki bki) {
		aor aor3 = aoz.j(bki);
		return !this.b(aor3).a() ? false : aor3 == aor.MAINHAND && super.e(bki);
	}

	@Override
	protected void b(bbg bbg) {
		if (this.b(aor.MAINHAND).a()) {
			bki bki3 = bbg.g();
			if (this.h(bki3)) {
				this.a(bbg);
				this.a(aor.MAINHAND, bki3);
				this.bt[aor.MAINHAND.b()] = 2.0F;
				this.a(bbg, bki3.E());
				bbg.aa();
			}
		}
	}

	@Override
	public void j() {
		super.j();
		if (this.eE()) {
			this.j(this.bD());
		} else {
			if (this.aC()) {
				this.b(2400);
			} else {
				this.b(this.eM() - 1);
				if (this.eM() <= 0) {
					this.a(anw.t, 1.0F);
				}

				if (this.t) {
					this.e(this.cB().b((double)((this.J.nextFloat() * 2.0F - 1.0F) * 0.2F), 0.5, (double)((this.J.nextFloat() * 2.0F - 1.0F) * 0.2F)));
					this.p = this.J.nextFloat() * 360.0F;
					this.t = false;
					this.ad = true;
				}
			}

			if (this.l.v && this.aA() && this.cB().g() > 0.03) {
				dem dem2 = this.f(0.0F);
				float float3 = aec.b(this.p * (float) (Math.PI / 180.0)) * 0.3F;
				float float4 = aec.a(this.p * (float) (Math.PI / 180.0)) * 0.3F;
				float float5 = 1.2F - this.J.nextFloat() * 0.7F;

				for (int integer6 = 0; integer6 < 2; integer6++) {
					this.l
						.a(hh.af, this.cC() - dem2.b * (double)float5 + (double)float3, this.cD() - dem2.c, this.cG() - dem2.d * (double)float5 + (double)float4, 0.0, 0.0, 0.0);
					this.l
						.a(hh.af, this.cC() - dem2.b * (double)float5 - (double)float3, this.cD() - dem2.c, this.cG() - dem2.d * (double)float5 - (double)float4, 0.0, 0.0, 0.0);
				}
			}
		}
	}

	@Override
	protected ang b(bec bec, anf anf) {
		bki bki4 = bec.b(anf);
		if (!bki4.a() && bki4.b().a(ada.S)) {
			if (!this.l.v) {
				this.a(acl.cH, 1.0F, 1.0F);
			}

			this.t(true);
			if (!bec.bJ.d) {
				bki4.g(1);
			}

			return ang.a(this.l.v);
		} else {
			return super.b(bec, anf);
		}
	}

	public static boolean b(aoq<ayq> aoq, bqc bqc, apb apb, fu fu, Random random) {
		return fu.v() > 45 && fu.v() < bqc.t_() && (bqc.v(fu) != brk.a || bqc.v(fu) != brk.z) && bqc.b(fu).a(acz.a);
	}

	@Override
	protected ack e(anw anw) {
		return acl.cI;
	}

	@Nullable
	@Override
	protected ack dp() {
		return acl.cG;
	}

	@Nullable
	@Override
	protected ack I() {
		return this.aA() ? acl.cE : acl.cD;
	}

	@Override
	protected ack ar() {
		return acl.cL;
	}

	@Override
	protected ack aq() {
		return acl.cM;
	}

	protected boolean eO() {
		fu fu2 = this.x().h();
		return fu2 != null ? fu2.a(this.cz(), 12.0) : false;
	}

	@Override
	public void f(dem dem) {
		if (this.dR() && this.aA()) {
			this.a(this.dM(), dem);
			this.a(apd.SELF, this.cB());
			this.e(this.cB().a(0.9));
			if (this.A() == null) {
				this.e(this.cB().b(0.0, -0.005, 0.0));
			}
		} else {
			super.f(dem);
		}
	}

	@Override
	public boolean a(bec bec) {
		return true;
	}

	static class a extends atm {
		private final ayq i;

		public a(ayq ayq) {
			super(ayq);
			this.i = ayq;
		}

		@Override
		public void a() {
			if (this.i.aA()) {
				this.i.e(this.i.cB().b(0.0, 0.005, 0.0));
			}

			if (this.h == atm.a.MOVE_TO && !this.i.x().m()) {
				double double2 = this.b - this.i.cC();
				double double4 = this.c - this.i.cD();
				double double6 = this.d - this.i.cG();
				double double8 = double2 * double2 + double4 * double4 + double6 * double6;
				if (double8 < 2.5000003E-7F) {
					this.a.q(0.0F);
				} else {
					float float10 = (float)(aec.d(double6, double2) * 180.0F / (float)Math.PI) - 90.0F;
					this.i.p = this.a(this.i.p, float10, 10.0F);
					this.i.aH = this.i.p;
					this.i.aJ = this.i.p;
					float float11 = (float)(this.e * this.i.b(apx.d));
					if (this.i.aA()) {
						this.i.n(float11 * 0.02F);
						float float12 = -((float)(aec.d(double4, (double)aec.a(double2 * double2 + double6 * double6)) * 180.0F / (float)Math.PI));
						float12 = aec.a(aec.g(float12), -85.0F, 85.0F);
						this.i.q = this.a(this.i.q, float12, 5.0F);
						float float13 = aec.b(this.i.q * (float) (Math.PI / 180.0));
						float float14 = aec.a(this.i.q * (float) (Math.PI / 180.0));
						this.i.ba = float13 * float11;
						this.i.aZ = -float14 * float11;
					} else {
						this.i.n(float11 * 0.1F);
					}
				}
			} else {
				this.i.n(0.0F);
				this.i.s(0.0F);
				this.i.r(0.0F);
				this.i.q(0.0F);
			}
		}
	}

	static class b extends aug {
		private final ayq a;
		private boolean b;

		b(ayq ayq) {
			this.a = ayq;
			this.a(EnumSet.of(aug.a.MOVE, aug.a.LOOK));
		}

		@Override
		public boolean D_() {
			return false;
		}

		@Override
		public boolean a() {
			return this.a.eL() && this.a.bE() >= 100;
		}

		@Override
		public boolean b() {
			fu fu2 = this.a.m();
			return !new fu((double)fu2.u(), this.a.cD(), (double)fu2.w()).a(this.a.cz(), 4.0) && !this.b && this.a.bE() >= 100;
		}

		@Override
		public void c() {
			if (this.a.l instanceof zd) {
				zd zd2 = (zd)this.a.l;
				this.b = false;
				this.a.x().o();
				fu fu3 = this.a.cA();
				cml<?> cml4 = (double)zd2.t.nextFloat() >= 0.5 ? cml.m : cml.i;
				fu fu5 = zd2.a(cml4, fu3, 50, false);
				if (fu5 == null) {
					cml<?> cml6 = cml4.equals(cml.m) ? cml.i : cml.m;
					fu fu7 = zd2.a(cml6, fu3, 50, false);
					if (fu7 == null) {
						this.b = true;
						return;
					}

					this.a.g(fu7);
				} else {
					this.a.g(fu5);
				}

				zd2.a(this.a, (byte)38);
			}
		}

		@Override
		public void d() {
			fu fu2 = this.a.m();
			if (new fu((double)fu2.u(), this.a.cD(), (double)fu2.w()).a(this.a.cz(), 4.0) || this.b) {
				this.a.t(false);
			}
		}

		@Override
		public void e() {
			bqb bqb2 = this.a.l;
			if (this.a.eO() || this.a.x().m()) {
				dem dem3 = dem.a(this.a.m());
				dem dem4 = axu.a(this.a, 16, 1, dem3, (float) (Math.PI / 8));
				if (dem4 == null) {
					dem4 = axu.b(this.a, 8, 4, dem3);
				}

				if (dem4 != null) {
					fu fu5 = new fu(dem4);
					if (!bqb2.b(fu5).a(acz.a) || !bqb2.d_(fu5).a(bqb2, fu5, czg.WATER)) {
						dem4 = axu.b(this.a, 8, 5, dem3);
					}
				}

				if (dem4 == null) {
					this.b = true;
					return;
				}

				this.a.t().a(dem4.b, dem4.c, dem4.d, (float)(this.a.ep() + 20), (float)this.a.eo());
				this.a.x().a(dem4.b, dem4.c, dem4.d, 1.3);
				if (bqb2.t.nextInt(80) == 0) {
					bqb2.a(this.a, (byte)38);
				}
			}
		}
	}

	static class c extends aug {
		private final ayq a;
		private final double b;
		private bec c;

		c(ayq ayq, double double2) {
			this.a = ayq;
			this.b = double2;
			this.a(EnumSet.of(aug.a.MOVE, aug.a.LOOK));
		}

		@Override
		public boolean a() {
			this.c = this.a.l.a(ayq.bw, this.a);
			return this.c == null ? false : this.c.bx() && this.a.A() != this.c;
		}

		@Override
		public boolean b() {
			return this.c != null && this.c.bx() && this.a.h(this.c) < 256.0;
		}

		@Override
		public void c() {
			this.c.c(new aog(aoi.D, 100));
		}

		@Override
		public void d() {
			this.c = null;
			this.a.x().o();
		}

		@Override
		public void e() {
			this.a.t().a(this.c, (float)(this.a.ep() + 20), (float)this.a.eo());
			if (this.a.h(this.c) < 6.25) {
				this.a.x().o();
			} else {
				this.a.x().a(this.c, this.b);
			}

			if (this.c.bx() && this.c.l.t.nextInt(6) == 0) {
				this.c.c(new aog(aoi.D, 100));
			}
		}
	}

	class d extends aug {
		private int b;

		private d() {
		}

		@Override
		public boolean a() {
			if (this.b > ayq.this.K) {
				return false;
			} else {
				List<bbg> list2 = ayq.this.l.a(bbg.class, ayq.this.cb().c(8.0, 8.0, 8.0), ayq.b);
				return !list2.isEmpty() || !ayq.this.b(aor.MAINHAND).a();
			}
		}

		@Override
		public void c() {
			List<bbg> list2 = ayq.this.l.a(bbg.class, ayq.this.cb().c(8.0, 8.0, 8.0), ayq.b);
			if (!list2.isEmpty()) {
				ayq.this.x().a((aom)list2.get(0), 1.2F);
				ayq.this.a(acl.cK, 1.0F, 1.0F);
			}

			this.b = 0;
		}

		@Override
		public void d() {
			bki bki2 = ayq.this.b(aor.MAINHAND);
			if (!bki2.a()) {
				this.a(bki2);
				ayq.this.a(aor.MAINHAND, bki.b);
				this.b = ayq.this.K + ayq.this.J.nextInt(100);
			}
		}

		@Override
		public void e() {
			List<bbg> list2 = ayq.this.l.a(bbg.class, ayq.this.cb().c(8.0, 8.0, 8.0), ayq.b);
			bki bki3 = ayq.this.b(aor.MAINHAND);
			if (!bki3.a()) {
				this.a(bki3);
				ayq.this.a(aor.MAINHAND, bki.b);
			} else if (!list2.isEmpty()) {
				ayq.this.x().a((aom)list2.get(0), 1.2F);
			}
		}

		private void a(bki bki) {
			if (!bki.a()) {
				double double3 = ayq.this.cF() - 0.3F;
				bbg bbg5 = new bbg(ayq.this.l, ayq.this.cC(), double3, ayq.this.cG(), bki);
				bbg5.a(40);
				bbg5.c(ayq.this.bR());
				float float6 = 0.3F;
				float float7 = ayq.this.J.nextFloat() * (float) (Math.PI * 2);
				float float8 = 0.02F * ayq.this.J.nextFloat();
				bbg5.m(
					(double)(0.3F * -aec.a(ayq.this.p * (float) (Math.PI / 180.0)) * aec.b(ayq.this.q * (float) (Math.PI / 180.0)) + aec.b(float7) * float8),
					(double)(0.3F * aec.a(ayq.this.q * (float) (Math.PI / 180.0)) * 1.5F),
					(double)(0.3F * aec.b(ayq.this.p * (float) (Math.PI / 180.0)) * aec.b(ayq.this.q * (float) (Math.PI / 180.0)) + aec.a(float7) * float8)
				);
				ayq.this.l.c(bbg5);
			}
		}
	}
}
