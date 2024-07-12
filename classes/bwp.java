import javax.annotation.Nullable;

public class bwp extends bvg implements cax {
	public static final cga a = cfz.C;
	protected static final dfg b = bvr.a(5.0, 5.0, 5.0, 11.0, 11.0, 11.0);

	public bwp(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, Boolean.valueOf(true)));
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(bwp.a);
	}

	@Override
	public cdl a(bpg bpg) {
		return new cds();
	}

	@Override
	public cak b(cfj cfj) {
		return cak.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public cxa d(cfj cfj) {
		return cfj.c(a) ? cxb.c.a(false) : super.d(cfj);
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if ((Boolean)cfj1.c(a)) {
			bqc.F().a(fu5, cxb.c, cxb.c.a(bqc));
		}

		return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return b;
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, @Nullable aoy aoy, bki bki) {
		if (bki.t()) {
			cdl cdl7 = bqb.c(fu);
			if (cdl7 instanceof cdg) {
				((cdg)cdl7).a(bki.r());
			}
		}
	}

	@Nullable
	@Override
	public cfj a(bin bin) {
		cxa cxa3 = bin.o().b(bin.a());
		return this.n().a(a, Boolean.valueOf(cxa3.a(acz.a) && cxa3.e() == 8));
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
