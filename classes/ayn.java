public class ayn extends ayk {
	private static final bmr bC = bmr.a(bkk.kV, bkk.nk, bkk.nj, bkk.qf);
	public float bv;
	public float bw;
	public float bx;
	public float by;
	public float bz = 1.0F;
	public int bA = this.J.nextInt(6000) + 6000;
	public boolean bB;

	public ayn(aoq<? extends ayn> aoq, bqb bqb) {
		super(aoq, bqb);
		this.a(czb.WATER, 0.0F);
	}

	@Override
	protected void o() {
		this.br.a(0, new aua(this));
		this.br.a(1, new avb(this, 1.4));
		this.br.a(2, new att(this, 1.0));
		this.br.a(3, new avr(this, 1.0, false, bC));
		this.br.a(4, new auf(this, 1.1));
		this.br.a(5, new avw(this, 1.0));
		this.br.a(6, new auo(this, bec.class, 6.0F));
		this.br.a(7, new ave(this));
	}

	@Override
	protected float b(apj apj, aon aon) {
		return this.x_() ? aon.b * 0.85F : aon.b * 0.92F;
	}

	public static apw.a eL() {
		return aoz.p().a(apx.a, 4.0).a(apx.d, 0.25);
	}

	@Override
	public void k() {
		super.k();
		this.by = this.bv;
		this.bx = this.bw;
		this.bw = (float)((double)this.bw + (double)(this.t ? -1 : 4) * 0.3);
		this.bw = aec.a(this.bw, 0.0F, 1.0F);
		if (!this.t && this.bz < 1.0F) {
			this.bz = 1.0F;
		}

		this.bz = (float)((double)this.bz * 0.9);
		dem dem2 = this.cB();
		if (!this.t && dem2.c < 0.0) {
			this.e(dem2.d(1.0, 0.6, 1.0));
		}

		this.bv = this.bv + this.bz * 2.0F;
		if (!this.l.v && this.aU() && !this.x_() && !this.eM() && --this.bA <= 0) {
			this.a(acl.bJ, 1.0F, (this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F);
			this.a(bkk.mg);
			this.bA = this.J.nextInt(6000) + 6000;
		}
	}

	@Override
	public boolean b(float float1, float float2) {
		return false;
	}

	@Override
	protected ack I() {
		return acl.bH;
	}

	@Override
	protected ack e(anw anw) {
		return acl.bK;
	}

	@Override
	protected ack dp() {
		return acl.bI;
	}

	@Override
	protected void a(fu fu, cfj cfj) {
		this.a(acl.bL, 0.15F, 1.0F);
	}

	public ayn a(aok aok) {
		return aoq.j.a(this.l);
	}

	@Override
	public boolean k(bki bki) {
		return bC.a(bki);
	}

	@Override
	protected int d(bec bec) {
		return this.eM() ? 10 : super.d(bec);
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.bB = le.q("IsChickenJockey");
		if (le.e("EggLayTime")) {
			this.bA = le.h("EggLayTime");
		}
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.a("IsChickenJockey", this.bB);
		le.b("EggLayTime", this.bA);
	}

	@Override
	public boolean h(double double1) {
		return this.eM();
	}

	@Override
	public void k(aom aom) {
		super.k(aom);
		float float3 = aec.a(this.aH * (float) (Math.PI / 180.0));
		float float4 = aec.b(this.aH * (float) (Math.PI / 180.0));
		float float5 = 0.1F;
		float float6 = 0.0F;
		aom.d(this.cC() + (double)(0.1F * float3), this.e(0.5) + aom.aX() + 0.0, this.cG() - (double)(0.1F * float4));
		if (aom instanceof aoy) {
			((aoy)aom).aH = this.aH;
		}
	}

	public boolean eM() {
		return this.bB;
	}

	public void t(boolean boolean1) {
		this.bB = boolean1;
	}
}
