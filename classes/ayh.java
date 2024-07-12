import java.util.Random;

public abstract class ayh extends azj {
	private static final tq<Boolean> b = tt.a(ayh.class, ts.i);

	public ayh(aoq<? extends ayh> aoq, bqb bqb) {
		super(aoq, bqb);
		this.bo = new ayh.a(this);
	}

	@Override
	protected float b(apj apj, aon aon) {
		return aon.b * 0.65F;
	}

	public static apw.a m() {
		return aoz.p().a(apx.a, 3.0);
	}

	@Override
	public boolean K() {
		return super.K() || this.eO();
	}

	public static boolean b(aoq<? extends ayh> aoq, bqc bqc, apb apb, fu fu, Random random) {
		return bqc.d_(fu).a(bvs.A) && bqc.d_(fu.b()).a(bvs.A);
	}

	@Override
	public boolean h(double double1) {
		return !this.eO() && !this.Q();
	}

	@Override
	public int er() {
		return 8;
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(b, false);
	}

	private boolean eO() {
		return this.S.a(b);
	}

	public void t(boolean boolean1) {
		this.S.b(b, boolean1);
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.a("FromBucket", this.eO());
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.t(le.q("FromBucket"));
	}

	@Override
	protected void o() {
		super.o();
		this.br.a(0, new avb(this, 1.25));
		this.br.a(2, new ato(this, bec.class, 8.0F, 1.6, 1.4, aop.g::test));
		this.br.a(4, new ayh.b(this));
	}

	@Override
	protected awv b(bqb bqb) {
		return new awx(this, bqb);
	}

	@Override
	public void f(dem dem) {
		if (this.dR() && this.aA()) {
			this.a(0.01F, dem);
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
	public void k() {
		if (!this.aA() && this.t && this.v) {
			this.e(this.cB().b((double)((this.J.nextFloat() * 2.0F - 1.0F) * 0.05F), 0.4F, (double)((this.J.nextFloat() * 2.0F - 1.0F) * 0.05F)));
			this.t = false;
			this.ad = true;
			this.a(this.eN(), this.dF(), this.dG());
		}

		super.k();
	}

	@Override
	protected ang b(bec bec, anf anf) {
		bki bki4 = bec.b(anf);
		if (bki4.b() == bkk.lL && this.aU()) {
			this.a(acl.bn, 1.0F, 1.0F);
			bki4.g(1);
			bki bki5 = this.eL();
			this.k(bki5);
			if (!this.l.v) {
				aa.j.a((ze)bec, bki5);
			}

			if (bki4.a()) {
				bec.a(anf, bki5);
			} else if (!bec.bt.e(bki5)) {
				bec.a(bki5, false);
			}

			this.aa();
			return ang.a(this.l.v);
		} else {
			return super.b(bec, anf);
		}
	}

	protected void k(bki bki) {
		if (this.Q()) {
			bki.a(this.R());
		}
	}

	protected abstract bki eL();

	protected boolean eM() {
		return true;
	}

	protected abstract ack eN();

	@Override
	protected ack aq() {
		return acl.ek;
	}

	@Override
	protected void a(fu fu, cfj cfj) {
	}

	static class a extends atm {
		private final ayh i;

		a(ayh ayh) {
			super(ayh);
			this.i = ayh;
		}

		@Override
		public void a() {
			if (this.i.a(acz.a)) {
				this.i.e(this.i.cB().b(0.0, 0.005, 0.0));
			}

			if (this.h == atm.a.MOVE_TO && !this.i.x().m()) {
				float float2 = (float)(this.e * this.i.b(apx.d));
				this.i.n(aec.g(0.125F, this.i.dM(), float2));
				double double3 = this.b - this.i.cC();
				double double5 = this.c - this.i.cD();
				double double7 = this.d - this.i.cG();
				if (double5 != 0.0) {
					double double9 = (double)aec.a(double3 * double3 + double5 * double5 + double7 * double7);
					this.i.e(this.i.cB().b(0.0, (double)this.i.dM() * (double5 / double9) * 0.1, 0.0));
				}

				if (double3 != 0.0 || double7 != 0.0) {
					float float9 = (float)(aec.d(double7, double3) * 180.0F / (float)Math.PI) - 90.0F;
					this.i.p = this.a(this.i.p, float9, 90.0F);
					this.i.aH = this.i.p;
				}
			} else {
				this.i.n(0.0F);
			}
		}
	}

	static class b extends avg {
		private final ayh h;

		public b(ayh ayh) {
			super(ayh, 1.0, 40);
			this.h = ayh;
		}

		@Override
		public boolean a() {
			return this.h.eM() && super.a();
		}
	}
}
