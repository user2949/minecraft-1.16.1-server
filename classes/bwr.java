import java.util.Random;

public class bwr extends bvc {
	private final bvr a;

	protected bwr(bvr bvr, cfi.c c) {
		super(c);
		this.a = bvr;
	}

	@Override
	public void b(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		this.a(cfj1, (bqc)bqb, fu);
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		if (!c(cfj, zd, fu)) {
			zd.a(fu, this.a.n().a(b, Boolean.valueOf(false)), 2);
		}
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if (fz == fz.DOWN && !cfj1.a(bqc, fu5)) {
			return bvs.a.n();
		} else {
			this.a(cfj1, bqc, fu5);
			if ((Boolean)cfj1.c(b)) {
				bqc.F().a(fu5, cxb.c, cxb.c.a(bqc));
			}

			return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
		}
	}
}
