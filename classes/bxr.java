import java.util.Random;

public class bxr extends bvr {
	public bxr(cfi.c c) {
		super(c);
	}

	@Override
	public void b(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		bqb.G().a(fu, this, this.c());
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		bqc.G().a(fu5, this, this.c());
		return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		if (h(zd.d_(fu.c())) && fu.v() >= 0) {
			bbf bbf6 = new bbf(zd, (double)fu.u() + 0.5, (double)fu.v(), (double)fu.w() + 0.5, zd.d_(fu));
			this.a(bbf6);
			zd.c(bbf6);
		}
	}

	protected void a(bbf bbf) {
	}

	protected int c() {
		return 2;
	}

	public static boolean h(cfj cfj) {
		cxd cxd2 = cfj.c();
		return cfj.g() || cfj.a(acx.am) || cxd2.a() || cxd2.e();
	}

	public void a(bqb bqb, fu fu, cfj cfj3, cfj cfj4, bbf bbf) {
	}

	public void a(bqb bqb, fu fu, bbf bbf) {
	}
}
