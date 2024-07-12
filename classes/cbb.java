public class cbb extends bwu {
	private static final ne a = new ne("container.upgrade");

	protected cbb(cfi.c c) {
		super(c);
	}

	@Override
	public anj b(cfj cfj, bqb bqb, fu fu) {
		return new ann((integer, beb, bec) -> new bhx(integer, beb, bgs.a(bqb, fu)), a);
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if (bqb.v) {
			return ang.SUCCESS;
		} else {
			bec.a(cfj.b(bqb, fu));
			bec.a(acu.aE);
			return ang.CONSUME;
		}
	}
}
