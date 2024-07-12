public class ccq extends bvx {
	protected static final dfg a = bvr.a(1.0, 0.0, 1.0, 15.0, 1.5, 15.0);

	protected ccq(cfi.c c) {
		super(c);
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu, aom aom) {
		super.a(cfj, bqb, fu, aom);
		if (bqb instanceof zd && aom instanceof bft) {
			bqb.a(new fu(fu), true, aom);
		}
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return a;
	}

	@Override
	protected boolean c(cfj cfj, bpg bpg, fu fu) {
		cxa cxa5 = bpg.b(fu);
		cxa cxa6 = bpg.b(fu.b());
		return (cxa5.a() == cxb.c || cfj.c() == cxd.F) && cxa6.a() == cxb.a;
	}
}
