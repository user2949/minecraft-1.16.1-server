import javax.annotation.Nullable;

public class azp extends azl implements bcf {
	private static final bmr bD = bmr.a(bkk.kW, bvs.gA.h());
	private static final tq<Integer> bE = tt.a(azp.class, ts.b);
	private static final tq<Integer> bF = tt.a(azp.class, ts.b);
	private static final tq<Integer> bG = tt.a(azp.class, ts.b);
	private boolean bH;
	@Nullable
	private azp bI;
	@Nullable
	private azp bJ;

	public azp(aoq<? extends azp> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	private void x(int integer) {
		this.S.b(bE, Math.max(1, Math.min(5, integer)));
	}

	private void fF() {
		int integer2 = this.J.nextFloat() < 0.04F ? 5 : 3;
		this.x(1 + this.J.nextInt(integer2));
	}

	public int fw() {
		return this.S.a(bE);
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.b("Variant", this.fy());
		le.b("Strength", this.fw());
		if (!this.by.a(1).a()) {
			le.a("DecorItem", this.by.a(1).b(new le()));
		}
	}

	@Override
	public void a(le le) {
		this.x(le.h("Strength"));
		super.a(le);
		this.w(le.h("Variant"));
		if (le.c("DecorItem", 10)) {
			this.by.a(1, bki.a(le.p("DecorItem")));
		}

		this.ff();
	}

	@Override
	protected void o() {
		this.br.a(0, new aua(this));
		this.br.a(1, new avm(this, 1.2));
		this.br.a(2, new aun(this, 2.1F));
		this.br.a(3, new avh(this, 1.25, 40, 20.0F));
		this.br.a(3, new avb(this, 1.2));
		this.br.a(4, new att(this, 1.0));
		this.br.a(5, new auf(this, 1.0));
		this.br.a(6, new avw(this, 0.7));
		this.br.a(7, new auo(this, bec.class, 6.0F));
		this.br.a(8, new ave(this));
		this.bs.a(1, new azp.c(this));
		this.bs.a(2, new azp.a(this));
	}

	public static apw.a fx() {
		return eM().a(apx.b, 40.0);
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(bE, 0);
		this.S.a(bF, -1);
		this.S.a(bG, 0);
	}

	public int fy() {
		return aec.a(this.S.a(bG), 0, 3);
	}

	public void w(int integer) {
		this.S.b(bG, integer);
	}

	@Override
	protected int eO() {
		return this.eN() ? 2 + 3 * this.eV() : super.eO();
	}

	@Override
	public void k(aom aom) {
		if (this.w(aom)) {
			float float3 = aec.b(this.aH * (float) (Math.PI / 180.0));
			float float4 = aec.a(this.aH * (float) (Math.PI / 180.0));
			float float5 = 0.3F;
			aom.d(this.cC() + (double)(0.3F * float4), this.cD() + this.aY() + aom.aX(), this.cG() - (double)(0.3F * float3));
		}
	}

	@Override
	public double aY() {
		return (double)this.cy() * 0.67;
	}

	@Override
	public boolean es() {
		return false;
	}

	@Override
	public boolean k(bki bki) {
		return bD.a(bki);
	}

	@Override
	protected boolean c(bec bec, bki bki) {
		int integer4 = 0;
		int integer5 = 0;
		float float6 = 0.0F;
		boolean boolean7 = false;
		bke bke8 = bki.b();
		if (bke8 == bkk.kW) {
			integer4 = 10;
			integer5 = 3;
			float6 = 2.0F;
		} else if (bke8 == bvs.gA.h()) {
			integer4 = 90;
			integer5 = 6;
			float6 = 10.0F;
			if (this.eX() && this.i() == 0 && this.eQ()) {
				boolean7 = true;
				this.g(bec);
			}
		}

		if (this.dj() < this.dw() && float6 > 0.0F) {
			this.b(float6);
			boolean7 = true;
		}

		if (this.x_() && integer4 > 0) {
			this.l.a(hh.E, this.d(1.0), this.cE() + 0.5, this.g(1.0), 0.0, 0.0, 0.0);
			if (!this.l.v) {
				this.a(integer4);
			}

			boolean7 = true;
		}

		if (integer5 > 0 && (boolean7 || !this.eX()) && this.fd() < this.fk()) {
			boolean7 = true;
			if (!this.l.v) {
				this.v(integer5);
			}
		}

		if (boolean7 && !this.av()) {
			ack ack9 = this.fh();
			if (ack9 != null) {
				this.l.a(null, this.cC(), this.cD(), this.cG(), this.fh(), this.ct(), 1.0F, 1.0F + (this.J.nextFloat() - this.J.nextFloat()) * 0.2F);
			}
		}

		return boolean7;
	}

	@Override
	protected boolean dH() {
		return this.dk() || this.fa();
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		this.fF();
		int integer7;
		if (apo instanceof azp.b) {
			integer7 = ((azp.b)apo).a;
		} else {
			integer7 = this.J.nextInt(4);
			apo = new azp.b(integer7);
		}

		this.w(integer7);
		return super.a(bqc, ane, apb, apo, le);
	}

	@Override
	protected ack fi() {
		return acl.hg;
	}

	@Override
	protected ack I() {
		return acl.hf;
	}

	@Override
	protected ack e(anw anw) {
		return acl.hk;
	}

	@Override
	protected ack dp() {
		return acl.hi;
	}

	@Nullable
	@Override
	protected ack fh() {
		return acl.hj;
	}

	@Override
	protected void a(fu fu, cfj cfj) {
		this.a(acl.hm, 0.15F, 1.0F);
	}

	@Override
	protected void eP() {
		this.a(acl.hh, 1.0F, (this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F);
	}

	@Override
	public void fn() {
		ack ack2 = this.fi();
		if (ack2 != null) {
			this.a(ack2, this.dF(), this.dG());
		}
	}

	@Override
	public int eV() {
		return this.fw();
	}

	@Override
	public boolean ft() {
		return true;
	}

	@Override
	public boolean fu() {
		return !this.by.a(1).a();
	}

	@Override
	public boolean l(bki bki) {
		bke bke3 = bki.b();
		return ada.f.a(bke3);
	}

	@Override
	public boolean M_() {
		return false;
	}

	@Override
	public void a(amz amz) {
		bje bje3 = this.fz();
		super.a(amz);
		bje bje4 = this.fz();
		if (this.K > 20 && bje4 != null && bje4 != bje3) {
			this.a(acl.hn, 0.5F, 1.0F);
		}
	}

	@Override
	protected void ff() {
		if (!this.l.v) {
			super.ff();
			this.a(m(this.by.a(1)));
		}
	}

	private void a(@Nullable bje bje) {
		this.S.b(bF, bje == null ? -1 : bje.b());
	}

	@Nullable
	private static bje m(bki bki) {
		bvr bvr2 = bvr.a(bki.b());
		return bvr2 instanceof cda ? ((cda)bvr2).c() : null;
	}

	@Nullable
	public bje fz() {
		int integer2 = this.S.a(bF);
		return integer2 == -1 ? null : bje.a(integer2);
	}

	@Override
	public int fk() {
		return 30;
	}

	@Override
	public boolean a(ayk ayk) {
		return ayk != this && ayk instanceof azp && this.fp() && ((azp)ayk).fp();
	}

	public azp a(aok aok) {
		azp azp3 = this.fA();
		this.a(aok, azp3);
		azp azp4 = (azp)aok;
		int integer5 = this.J.nextInt(Math.max(this.fw(), azp4.fw())) + 1;
		if (this.J.nextFloat() < 0.03F) {
			integer5++;
		}

		azp3.x(integer5);
		azp3.w(this.J.nextBoolean() ? this.fy() : azp4.fy());
		return azp3;
	}

	protected azp fA() {
		return aoq.Q.a(this.l);
	}

	private void j(aoy aoy) {
		ber ber3 = new ber(this.l, this);
		double double4 = aoy.cC() - this.cC();
		double double6 = aoy.e(0.3333333333333333) - ber3.cD();
		double double8 = aoy.cG() - this.cG();
		float float10 = aec.a(double4 * double4 + double8 * double8) * 0.2F;
		ber3.c(double4, double6 + (double)float10, double8, 1.5F, 10.0F);
		if (!this.av()) {
			this.l.a(null, this.cC(), this.cD(), this.cG(), acl.hl, this.ct(), 1.0F, 1.0F + (this.J.nextFloat() - this.J.nextFloat()) * 0.2F);
		}

		this.l.c(ber3);
		this.bH = true;
	}

	private void A(boolean boolean1) {
		this.bH = boolean1;
	}

	@Override
	public boolean b(float float1, float float2) {
		int integer4 = this.e(float1, float2);
		if (integer4 <= 0) {
			return false;
		} else {
			if (float1 >= 6.0F) {
				this.a(anw.k, (float)integer4);
				if (this.bo()) {
					for (aom aom6 : this.cn()) {
						aom6.a(anw.k, (float)integer4);
					}
				}
			}

			this.ds();
			return true;
		}
	}

	public void fB() {
		if (this.bI != null) {
			this.bI.bJ = null;
		}

		this.bI = null;
	}

	public void a(azp azp) {
		this.bI = azp;
		this.bI.bJ = this;
	}

	public boolean fC() {
		return this.bJ != null;
	}

	public boolean fD() {
		return this.bI != null;
	}

	@Nullable
	public azp fE() {
		return this.bI;
	}

	@Override
	protected double eK() {
		return 2.0;
	}

	@Override
	protected void fl() {
		if (!this.fD() && this.x_()) {
			super.fl();
		}
	}

	@Override
	public boolean fm() {
		return false;
	}

	@Override
	public void a(aoy aoy, float float2) {
		this.j(aoy);
	}

	static class a extends awc<azk> {
		public a(azp azp) {
			super(azp, azk.class, 16, false, true, aoy -> !((azk)aoy).eL());
		}

		@Override
		protected double k() {
			return super.k() * 0.25;
		}
	}

	static class b extends aok.a {
		public final int a;

		private b(int integer) {
			this.a = integer;
		}
	}

	static class c extends awb {
		public c(azp azp) {
			super(azp);
		}

		@Override
		public boolean b() {
			if (this.e instanceof azp) {
				azp azp2 = (azp)this.e;
				if (azp2.bH) {
					azp2.A(false);
					return false;
				}
			}

			return super.b();
		}
	}
}
