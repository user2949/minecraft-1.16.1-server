public class bxo extends buq<cdx> implements cax {
	public static final cgd b = byp.aq;
	public static final cga c = cfz.C;
	protected static final dfg d = bvr.a(1.0, 0.0, 1.0, 15.0, 14.0, 15.0);
	public static final ne e = new ne("container.enderchest");

	protected bxo(cfi.c c) {
		super(c, () -> cdm.d);
		this.j(this.n.b().a(b, fz.NORTH).a(bxo.c, Boolean.valueOf(false)));
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return d;
	}

	@Override
	public cak b(cfj cfj) {
		return cak.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public cfj a(bin bin) {
		cxa cxa3 = bin.o().b(bin.a());
		return this.n().a(b, bin.f().f()).a(c, Boolean.valueOf(cxa3.a() == cxb.c));
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		bho bho8 = bec.eK();
		cdl cdl9 = bqb.c(fu);
		if (bho8 != null && cdl9 instanceof cdx) {
			fu fu10 = fu.b();
			if (bqb.d_(fu10).g(bqb, fu10)) {
				return ang.a(bqb.v);
			} else if (bqb.v) {
				return ang.SUCCESS;
			} else {
				cdx cdx11 = (cdx)cdl9;
				bho8.a(cdx11);
				bec.a(new ann((integer, beb, becx) -> bgp.a(integer, beb, bho8), e));
				bec.a(acu.ai);
				bdd.a(bec, true);
				return ang.CONSUME;
			}
		} else {
			return ang.a(bqb.v);
		}
	}

	@Override
	public cdl a(bpg bpg) {
		return new cdx();
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		return cfj.a(b, cap.a(cfj.c(b)));
	}

	@Override
	public cfj a(cfj cfj, bzj bzj) {
		return cfj.a(bzj.a(cfj.c(b)));
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(b, c);
	}

	@Override
	public cxa d(cfj cfj) {
		return cfj.c(c) ? cxb.c.a(false) : super.d(cfj);
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if ((Boolean)cfj1.c(c)) {
			bqc.F().a(fu5, cxb.c, cxb.c.a(bqc));
		}

		return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
