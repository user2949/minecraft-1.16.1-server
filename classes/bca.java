import java.util.Random;

public class bca extends bck {
	public bca(aoq<? extends bca> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public static apw.a m() {
		return bcb.eS().a(apx.d, 0.2F);
	}

	public static boolean b(aoq<bca> aoq, bqc bqc, apb apb, fu fu, Random random) {
		return bqc.ac() != and.PEACEFUL;
	}

	@Override
	public boolean a(bqd bqd) {
		return bqd.i(this) && !bqd.d(this.cb());
	}

	@Override
	protected void a(int integer, boolean boolean2) {
		super.a(integer, boolean2);
		this.a(apx.i).a((double)(integer * 3));
	}

	@Override
	public float aO() {
		return 1.0F;
	}

	@Override
	protected hf eJ() {
		return hh.A;
	}

	@Override
	protected uh J() {
		return this.eR() ? dao.a : this.U().i();
	}

	@Override
	public boolean bm() {
		return false;
	}

	@Override
	protected int eK() {
		return super.eK() * 4;
	}

	@Override
	protected void eL() {
		this.b *= 0.9F;
	}

	@Override
	protected void dJ() {
		dem dem2 = this.cB();
		this.m(dem2.b, (double)(this.dI() + (float)this.eQ() * 0.1F), dem2.d);
		this.ad = true;
	}

	@Override
	protected void c(adf<cwz> adf) {
		if (adf == acz.b) {
			dem dem3 = this.cB();
			this.m(dem3.b, (double)(0.22F + (float)this.eQ() * 0.05F), dem3.d);
			this.ad = true;
		} else {
			super.c(adf);
		}
	}

	@Override
	public boolean b(float float1, float float2) {
		return false;
	}

	@Override
	protected boolean eM() {
		return this.dR();
	}

	@Override
	protected float eN() {
		return super.eN() + 2.0F;
	}

	@Override
	protected ack e(anw anw) {
		return this.eR() ? acl.hx : acl.hw;
	}

	@Override
	protected ack dp() {
		return this.eR() ? acl.ho : acl.hv;
	}

	@Override
	protected ack eO() {
		return this.eR() ? acl.hA : acl.hz;
	}

	@Override
	protected ack eP() {
		return acl.hy;
	}
}
