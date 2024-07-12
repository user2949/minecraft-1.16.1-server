import javax.annotation.Nullable;

public class byz extends bvr {
	public static final cga a = cfz.j;
	protected static final dfg b = dfd.a(bvr.a(5.0, 0.0, 5.0, 11.0, 7.0, 11.0), bvr.a(6.0, 7.0, 6.0, 10.0, 9.0, 10.0));
	protected static final dfg c = dfd.a(bvr.a(5.0, 1.0, 5.0, 11.0, 8.0, 11.0), bvr.a(6.0, 8.0, 6.0, 10.0, 10.0, 10.0));

	public byz(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, Boolean.valueOf(false)));
	}

	@Nullable
	@Override
	public cfj a(bin bin) {
		for (fz fz6 : bin.e()) {
			if (fz6.n() == fz.a.Y) {
				cfj cfj7 = this.n().a(a, Boolean.valueOf(fz6 == fz.UP));
				if (cfj7.a((bqd)bin.o(), bin.a())) {
					return cfj7;
				}
			}
		}

		return null;
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return cfj.c(a) ? c : b;
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(byz.a);
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		fz fz5 = h(cfj).f();
		return bvr.a(bqd, fu.a(fz5), fz5.f());
	}

	protected static fz h(cfj cfj) {
		return cfj.c(a) ? fz.DOWN : fz.UP;
	}

	@Override
	public cxf f(cfj cfj) {
		return cxf.DESTROY;
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		return h(cfj1).f() == fz && !cfj1.a(bqc, fu5) ? bvs.a.n() : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
