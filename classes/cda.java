public class cda extends bvr {
	protected static final dfg a = bvr.a(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
	private final bje b;

	protected cda(bje bje, cfi.c c) {
		super(c);
		this.b = bje;
	}

	public bje c() {
		return this.b;
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return a;
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		return !cfj1.a(bqc, fu5) ? bvs.a.n() : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		return !bqd.w(fu.c());
	}
}
