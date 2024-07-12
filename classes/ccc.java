public class ccc extends bvr {
	protected static final dfg d = bvr.a(6.0, 0.0, 6.0, 10.0, 10.0, 10.0);
	protected final hf e;

	protected ccc(cfi.c c, hf hf) {
		super(c);
		this.e = hf;
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return d;
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		return fz == fz.DOWN && !this.a(cfj1, bqc, fu5) ? bvs.a.n() : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		return a(bqd, fu.c(), fz.UP);
	}
}
