import java.util.Random;

public class bzh extends bvr {
	public bzh(cfi.c c) {
		super(c);
	}

	@Override
	public void a(bqb bqb, fu fu, aom aom) {
		if (!aom.az() && aom instanceof aoy && !bny.i((aoy)aom)) {
			aom.a(anw.e, 1.0F);
		}

		super.a(bqb, fu, aom);
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		bvv.a(zd, fu.b(), true);
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if (fz == fz.UP && cfj3.a(bvs.A)) {
			bqc.G().a(fu5, this, 20);
		}

		return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		fu fu6 = fu.b();
		if (zd.b(fu).a(acz.a)) {
			zd.a(null, fu, acl.ej, acm.BLOCKS, 0.5F, 2.6F + (zd.t.nextFloat() - zd.t.nextFloat()) * 0.8F);
			zd.a(hh.L, (double)fu6.u() + 0.5, (double)fu6.v() + 0.25, (double)fu6.w() + 0.5, 8, 0.5, 0.25, 0.5, 0.0);
		}
	}

	@Override
	public void b(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		bqb.G().a(fu, this, 20);
	}
}
