public class bwu extends bvr {
	private static final mr a = new ne("container.crafting");

	protected bwu(cfi.c c) {
		super(c);
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if (bqb.v) {
			return ang.SUCCESS;
		} else {
			bec.a(cfj.b(bqb, fu));
			bec.a(acu.am);
			return ang.CONSUME;
		}
	}

	@Override
	public anj b(cfj cfj, bqb bqb, fu fu) {
		return new ann((integer, beb, bec) -> new bgv(integer, beb, bgs.a(bqb, fu)), a);
	}
}
