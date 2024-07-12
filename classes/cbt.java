import javax.annotation.Nullable;

public class cbt extends bvg {
	public static final cgg<cgq> a = cfz.aM;

	protected cbt(cfi.c c) {
		super(c);
	}

	@Override
	public cdl a(bpg bpg) {
		return new cel();
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		cdl cdl8 = bqb.c(fu);
		if (cdl8 instanceof cel) {
			return ((cel)cdl8).a(bec) ? ang.a(bqb.v) : ang.PASS;
		} else {
			return ang.PASS;
		}
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, @Nullable aoy aoy, bki bki) {
		if (!bqb.v) {
			if (aoy != null) {
				cdl cdl7 = bqb.c(fu);
				if (cdl7 instanceof cel) {
					((cel)cdl7).a(aoy);
				}
			}
		}
	}

	@Override
	public cak b(cfj cfj) {
		return cak.MODEL;
	}

	@Override
	public cfj a(bin bin) {
		return this.n().a(a, cgq.DATA);
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(cbt.a);
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu3, bvr bvr, fu fu5, boolean boolean6) {
		if (!bqb.v) {
			cdl cdl8 = bqb.c(fu3);
			if (cdl8 instanceof cel) {
				cel cel9 = (cel)cdl8;
				boolean boolean10 = bqb.r(fu3);
				boolean boolean11 = cel9.H();
				if (boolean10 && !boolean11) {
					cel9.d(true);
					this.a(cel9);
				} else if (!boolean10 && boolean11) {
					cel9.d(false);
				}
			}
		}
	}

	private void a(cel cel) {
		switch (cel.x()) {
			case SAVE:
				cel.b(false);
				break;
			case LOAD:
				cel.c(false);
				break;
			case CORNER:
				cel.F();
			case DATA:
		}
	}
}
