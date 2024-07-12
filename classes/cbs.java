import javax.annotation.Nullable;

public class cbs extends bvr {
	private static final ne c = new ne("container.stonecutter");
	public static final cgd a = byp.aq;
	protected static final dfg b = bvr.a(0.0, 0.0, 0.0, 16.0, 9.0, 16.0);

	public cbs(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, fz.NORTH));
	}

	@Override
	public cfj a(bin bin) {
		return this.n().a(a, bin.f().f());
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if (bqb.v) {
			return ang.SUCCESS;
		} else {
			bec.a(cfj.b(bqb, fu));
			bec.a(acu.ax);
			return ang.CONSUME;
		}
	}

	@Nullable
	@Override
	public anj b(cfj cfj, bqb bqb, fu fu) {
		return new ann((integer, beb, bec) -> new bia(integer, beb, bgs.a(bqb, fu)), c);
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return b;
	}

	@Override
	public boolean c_(cfj cfj) {
		return true;
	}

	@Override
	public cak b(cfj cfj) {
		return cak.MODEL;
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		return cfj.a(a, cap.a(cfj.c(a)));
	}

	@Override
	public cfj a(cfj cfj, bzj bzj) {
		return cfj.a(bzj.a(cfj.c(a)));
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(cbs.a);
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
