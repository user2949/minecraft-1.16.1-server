import javax.annotation.Nullable;

public class bxg extends bvx {
	public static final cgg<cgf> a = cfz.aa;

	public bxg(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, cgf.LOWER));
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		cgf cgf8 = cfj1.c(a);
		if (fz.n() != fz.a.Y || cgf8 == cgf.LOWER != (fz == fz.UP) || cfj3.a(this) && cfj3.c(a) != cgf8) {
			return cgf8 == cgf.LOWER && fz == fz.DOWN && !cfj1.a(bqc, fu5) ? bvs.a.n() : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
		} else {
			return bvs.a.n();
		}
	}

	@Nullable
	@Override
	public cfj a(bin bin) {
		fu fu3 = bin.a();
		return fu3.v() < 255 && bin.o().d_(fu3.b()).a(bin) ? super.a(bin) : null;
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, aoy aoy, bki bki) {
		bqb.a(fu.b(), this.n().a(a, cgf.UPPER), 3);
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		if (cfj.c(a) != cgf.UPPER) {
			return super.a(cfj, bqd, fu);
		} else {
			cfj cfj5 = bqd.d_(fu.c());
			return cfj5.a(this) && cfj5.c(a) == cgf.LOWER;
		}
	}

	public void a(bqc bqc, fu fu, int integer) {
		bqc.a(fu, this.n().a(a, cgf.LOWER), integer);
		bqc.a(fu.b(), this.n().a(a, cgf.UPPER), integer);
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, bec bec) {
		if (!bqb.v) {
			if (bec.b_()) {
				b(bqb, fu, cfj, bec);
			} else {
				a(cfj, bqb, fu, null, bec, bec.dC());
			}
		}

		super.a(bqb, fu, cfj, bec);
	}

	@Override
	public void a(bqb bqb, bec bec, fu fu, cfj cfj, @Nullable cdl cdl, bki bki) {
		super.a(bqb, bec, fu, bvs.a.n(), cdl, bki);
	}

	protected static void b(bqb bqb, fu fu, cfj cfj, bec bec) {
		cgf cgf5 = cfj.c(a);
		if (cgf5 == cgf.UPPER) {
			fu fu6 = fu.c();
			cfj cfj7 = bqb.d_(fu6);
			if (cfj7.b() == cfj.b() && cfj7.c(a) == cgf.LOWER) {
				bqb.a(fu6, bvs.a.n(), 35);
				bqb.a(bec, 2001, fu6, bvr.i(cfj7));
			}
		}
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(bxg.a);
	}

	@Override
	public cfi.b aj_() {
		return cfi.b.XZ;
	}
}
