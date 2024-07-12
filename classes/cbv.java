import java.util.Random;

public class cbv extends bvr {
	public static final cgi a = cfz.aj;
	protected static final dfg b = bvr.a(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);

	protected cbv(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, Integer.valueOf(0)));
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return b;
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		if (!cfj.a(zd, fu)) {
			zd.b(fu, true);
		}
	}

	@Override
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		if (zd.w(fu.b())) {
			int integer6 = 1;

			while (zd.d_(fu.c(integer6)).a(this)) {
				integer6++;
			}

			if (integer6 < 3) {
				int integer7 = (Integer)cfj.c(a);
				if (integer7 == 15) {
					zd.a(fu.b(), this.n());
					zd.a(fu, cfj.a(a, Integer.valueOf(0)), 4);
				} else {
					zd.a(fu, cfj.a(a, Integer.valueOf(integer7 + 1)), 4);
				}
			}
		}
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if (!cfj1.a(bqc, fu5)) {
			bqc.G().a(fu5, this, 1);
		}

		return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		cfj cfj5 = bqd.d_(fu.c());
		if (cfj5.b() == this) {
			return true;
		} else {
			if (cfj5.a(bvs.i) || cfj5.a(bvs.j) || cfj5.a(bvs.k) || cfj5.a(bvs.l) || cfj5.a(bvs.C) || cfj5.a(bvs.D)) {
				fu fu6 = fu.c();

				for (fz fz8 : fz.c.HORIZONTAL) {
					cfj cfj9 = bqd.d_(fu6.a(fz8));
					cxa cxa10 = bqd.b(fu6.a(fz8));
					if (cxa10.a(acz.a) || cfj9.a(bvs.iI)) {
						return true;
					}
				}
			}

			return false;
		}
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(cbv.a);
	}
}
