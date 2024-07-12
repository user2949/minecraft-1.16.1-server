import javax.annotation.Nullable;

public class buv extends bxr {
	public static final cgd a = byp.aq;
	private static final dfg b = bvr.a(2.0, 0.0, 2.0, 14.0, 4.0, 14.0);
	private static final dfg c = bvr.a(3.0, 4.0, 4.0, 13.0, 5.0, 12.0);
	private static final dfg d = bvr.a(4.0, 5.0, 6.0, 12.0, 10.0, 10.0);
	private static final dfg e = bvr.a(0.0, 10.0, 3.0, 16.0, 16.0, 13.0);
	private static final dfg f = bvr.a(4.0, 4.0, 3.0, 12.0, 5.0, 13.0);
	private static final dfg g = bvr.a(6.0, 5.0, 4.0, 10.0, 10.0, 12.0);
	private static final dfg h = bvr.a(3.0, 10.0, 0.0, 13.0, 16.0, 16.0);
	private static final dfg i = dfd.a(b, c, d, e);
	private static final dfg j = dfd.a(b, f, g, h);
	private static final ne k = new ne("container.repair");

	public buv(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, fz.NORTH));
	}

	@Override
	public cfj a(bin bin) {
		return this.n().a(a, bin.f().g());
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if (bqb.v) {
			return ang.SUCCESS;
		} else {
			bec.a(cfj.b(bqb, fu));
			bec.a(acu.aB);
			return ang.CONSUME;
		}
	}

	@Nullable
	@Override
	public anj b(cfj cfj, bqb bqb, fu fu) {
		return new ann((integer, beb, bec) -> new bgk(integer, beb, bgs.a(bqb, fu)), k);
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		fz fz6 = cfj.c(a);
		return fz6.n() == fz.a.X ? i : j;
	}

	@Override
	protected void a(bbf bbf) {
		bbf.a(true);
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj3, cfj cfj4, bbf bbf) {
		if (!bbf.av()) {
			bqb.c(1031, fu, 0);
		}
	}

	@Override
	public void a(bqb bqb, fu fu, bbf bbf) {
		if (!bbf.av()) {
			bqb.c(1029, fu, 0);
		}
	}

	@Nullable
	public static cfj c(cfj cfj) {
		if (cfj.a(bvs.fo)) {
			return bvs.fp.n().a(a, cfj.c(a));
		} else {
			return cfj.a(bvs.fp) ? bvs.fq.n().a(a, cfj.c(a)) : null;
		}
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		return cfj.a(a, cap.a(cfj.c(a)));
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(buv.a);
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
