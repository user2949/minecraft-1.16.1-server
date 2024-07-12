import javax.annotation.Nullable;

public class bwg extends bvr implements cax {
	public static final dfg a = bvr.a(6.5, 0.0, 6.5, 9.5, 16.0, 9.5);
	public static final cga b = cfz.C;

	public bwg(cfi.c c) {
		super(c);
		this.j(this.n.b().a(b, Boolean.valueOf(false)));
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return a;
	}

	@Nullable
	@Override
	public cfj a(bin bin) {
		cxa cxa3 = bin.o().b(bin.a());
		boolean boolean4 = cxa3.a() == cxb.c;
		return super.a(bin).a(b, Boolean.valueOf(boolean4));
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if ((Boolean)cfj1.c(b)) {
			bqc.F().a(fu5, cxb.c, cxb.c.a(bqc));
		}

		return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(b);
	}

	@Override
	public cxa d(cfj cfj) {
		return cfj.c(b) ? cxb.c.a(false) : super.d(cfj);
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
