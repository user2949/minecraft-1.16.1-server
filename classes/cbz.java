import javax.annotation.Nullable;

public class cbz extends bxg implements bzf {
	public static final cgg<cgf> b = bxg.a;
	protected static final dfg c = bvr.a(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);

	public cbz(cfi.c c) {
		super(c);
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return c;
	}

	@Override
	protected boolean c(cfj cfj, bpg bpg, fu fu) {
		return cfj.d(bpg, fu, fz.UP) && !cfj.a(bvs.iJ);
	}

	@Nullable
	@Override
	public cfj a(bin bin) {
		cfj cfj3 = super.a(bin);
		if (cfj3 != null) {
			cxa cxa4 = bin.o().b(bin.a().b());
			if (cxa4.a(acz.a) && cxa4.e() == 8) {
				return cfj3;
			}
		}

		return null;
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		if (cfj.c(b) == cgf.UPPER) {
			cfj cfj5 = bqd.d_(fu.c());
			return cfj5.a(this) && cfj5.c(b) == cgf.LOWER;
		} else {
			cxa cxa5 = bqd.b(fu);
			return super.a(cfj, bqd, fu) && cxa5.a(acz.a) && cxa5.e() == 8;
		}
	}

	@Override
	public cxa d(cfj cfj) {
		return cxb.c.a(false);
	}

	@Override
	public boolean a(bpg bpg, fu fu, cfj cfj, cwz cwz) {
		return false;
	}

	@Override
	public boolean a(bqc bqc, fu fu, cfj cfj, cxa cxa) {
		return false;
	}
}
