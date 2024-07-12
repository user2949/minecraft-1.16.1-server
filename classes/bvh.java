public abstract class bvh extends bvr {
	private final float g;
	protected static final dfg a = bvr.a(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
	protected static final dfg b = bvr.a(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
	protected static final dfg c = bvr.a(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
	protected static final dfg d = bvr.a(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
	protected static final dfg e = bvr.a(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
	protected static final dfg f = bvr.a(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);

	public bvh(cfi.c c, float float2) {
		super(c);
		this.g = float2;
	}

	@Override
	public cfj a(bin bin) {
		return a((bpg)bin.o(), bin.a());
	}

	public static cfj a(bpg bpg, fu fu) {
		fu fu3 = fu.c();
		cfj cfj4 = bpg.d_(fu3);
		return cbf.c(cfj4.b()) ? bvs.bO.n() : ((bxv)bvs.bN).b(bpg, fu);
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return b;
	}

	protected abstract boolean e(cfj cfj);

	@Override
	public void a(cfj cfj, bqb bqb, fu fu, aom aom) {
		if (!aom.az()) {
			aom.g(aom.ag() + 1);
			if (aom.ag() == 0) {
				aom.f(8);
			}

			aom.a(anw.a, this.g);
		}

		super.a(cfj, bqb, fu, aom);
	}

	@Override
	public void b(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!cfj4.a(cfj1.b())) {
			if (bqb.W() != bqb.g && bqb.W() != bqb.h || !bzm.a(bqb, fu)) {
				if (!cfj1.a((bqd)bqb, fu)) {
					bqb.a(fu, false);
				}
			}
		}
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, bec bec) {
		if (!bqb.s_()) {
			bqb.a(null, 1009, fu, 0);
		}
	}

	public static boolean a(bqc bqc, fu fu) {
		cfj cfj3 = bqc.d_(fu);
		cfj cfj4 = a((bpg)bqc, fu);
		return cfj3.g() && (cfj4.a(bqc, fu) || b(bqc, fu));
	}

	private static boolean b(bqc bqc, fu fu) {
		for (fz fz4 : fz.c.HORIZONTAL) {
			if (bqc.d_(fu.a(fz4)).a(bvs.bK) && bzm.b(bqc, fu) != null) {
				return true;
			}
		}

		return false;
	}
}
