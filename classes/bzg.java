public class bzg extends byp {
	private static final ne a = new ne("container.loom");

	protected bzg(cfi.c c) {
		super(c);
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if (bqb.v) {
			return ang.SUCCESS;
		} else {
			bec.a(cfj.b(bqb, fu));
			bec.a(acu.aw);
			return ang.CONSUME;
		}
	}

	@Override
	public anj b(cfj cfj, bqb bqb, fu fu) {
		return new ann((integer, beb, bec) -> new bhi(integer, beb, bgs.a(bqb, fu)), a);
	}

	@Override
	public cfj a(bin bin) {
		return this.n().a(aq, bin.f().f());
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(aq);
	}
}
