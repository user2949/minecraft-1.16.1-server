import java.util.Random;

public class bby extends bcu {
	public bby(aoq<? extends bby> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public static boolean b(aoq<bby> aoq, bqc bqc, apb apb, fu fu, Random random) {
		return c(aoq, bqc, apb, fu, random) && (apb == apb.SPAWNER || bqc.f(fu));
	}

	@Override
	protected boolean U_() {
		return false;
	}

	@Override
	protected ack I() {
		return acl.gj;
	}

	@Override
	protected ack e(anw anw) {
		return acl.gm;
	}

	@Override
	protected ack dp() {
		return acl.gl;
	}

	@Override
	protected ack eM() {
		return acl.gn;
	}

	@Override
	public boolean B(aom aom) {
		boolean boolean3 = super.B(aom);
		if (boolean3 && this.dC().a() && aom instanceof aoy) {
			float float4 = this.l.d(this.cA()).b();
			((aoy)aom).c(new aog(aoi.q, 140 * (int)float4));
		}

		return boolean3;
	}

	@Override
	protected boolean eO() {
		return true;
	}

	@Override
	protected void eQ() {
		this.c(aoq.aX);
		if (!this.av()) {
			this.l.a(null, 1041, this.cA(), 0);
		}
	}

	@Override
	protected bki eN() {
		return bki.b;
	}
}
