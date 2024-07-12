public abstract class bur extends bvg {
	public static final cgd a = byp.aq;
	public static final cga b = cfz.r;

	protected bur(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, fz.NORTH).a(b, Boolean.valueOf(false)));
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if (bqb.v) {
			return ang.SUCCESS;
		} else {
			this.a(bqb, fu, bec);
			return ang.CONSUME;
		}
	}

	protected abstract void a(bqb bqb, fu fu, bec bec);

	@Override
	public cfj a(bin bin) {
		return this.n().a(a, bin.f().f());
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, aoy aoy, bki bki) {
		if (bki.t()) {
			cdl cdl7 = bqb.c(fu);
			if (cdl7 instanceof cdb) {
				((cdb)cdl7).a(bki.r());
			}
		}
	}

	@Override
	public void a(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!cfj1.a(cfj4.b())) {
			cdl cdl7 = bqb.c(fu);
			if (cdl7 instanceof cdb) {
				anc.a(bqb, fu, (cdb)cdl7);
				((cdb)cdl7).a(bqb, dem.a(fu));
				bqb.c(fu, this);
			}

			super.a(cfj1, bqb, fu, cfj4, boolean5);
		}
	}

	@Override
	public boolean a(cfj cfj) {
		return true;
	}

	@Override
	public int a(cfj cfj, bqb bqb, fu fu) {
		return bgi.a(bqb.c(fu));
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
		a.a(bur.a, b);
	}
}
