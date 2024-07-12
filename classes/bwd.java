import javax.annotation.Nullable;

public class bwd extends bvr {
	private static final ne a = new ne("container.cartography_table");

	protected bwd(cfi.c c) {
		super(c);
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if (bqb.v) {
			return ang.SUCCESS;
		} else {
			bec.a(cfj.b(bqb, fu));
			bec.a(acu.av);
			return ang.CONSUME;
		}
	}

	@Nullable
	@Override
	public anj b(cfj cfj, bqb bqb, fu fu) {
		return new ann((integer, beb, bec) -> new bgo(integer, beb, bgs.a(bqb, fu)), a);
	}
}
