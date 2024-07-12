public class bvl extends bvg implements bvk {
	public bvl(cfi.c c) {
		super(c);
	}

	@Override
	public bje a() {
		return bje.WHITE;
	}

	@Override
	public cdl a(bpg bpg) {
		return new cdg();
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if (bqb.v) {
			return ang.SUCCESS;
		} else {
			cdl cdl8 = bqb.c(fu);
			if (cdl8 instanceof cdg) {
				bec.a((cdg)cdl8);
				bec.a(acu.aa);
			}

			return ang.CONSUME;
		}
	}

	@Override
	public cak b(cfj cfj) {
		return cak.MODEL;
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, aoy aoy, bki bki) {
		if (bki.t()) {
			cdl cdl7 = bqb.c(fu);
			if (cdl7 instanceof cdg) {
				((cdg)cdl7).a(bki.r());
			}
		}
	}
}
