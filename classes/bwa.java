public class bwa extends bvr {
	public static final cgi a = cfz.al;
	protected static final dfg[] b = new dfg[]{
		bvr.a(1.0, 0.0, 1.0, 15.0, 8.0, 15.0),
		bvr.a(3.0, 0.0, 1.0, 15.0, 8.0, 15.0),
		bvr.a(5.0, 0.0, 1.0, 15.0, 8.0, 15.0),
		bvr.a(7.0, 0.0, 1.0, 15.0, 8.0, 15.0),
		bvr.a(9.0, 0.0, 1.0, 15.0, 8.0, 15.0),
		bvr.a(11.0, 0.0, 1.0, 15.0, 8.0, 15.0),
		bvr.a(13.0, 0.0, 1.0, 15.0, 8.0, 15.0)
	};

	protected bwa(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, Integer.valueOf(0)));
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return b[cfj.c(a)];
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if (bqb.v) {
			bki bki8 = bec.b(anf);
			if (this.a(bqb, fu, cfj, bec).a()) {
				return ang.SUCCESS;
			}

			if (bki8.a()) {
				return ang.CONSUME;
			}
		}

		return this.a(bqb, fu, cfj, bec);
	}

	private ang a(bqc bqc, fu fu, cfj cfj, bec bec) {
		if (!bec.q(false)) {
			return ang.PASS;
		} else {
			bec.a(acu.T);
			bec.eH().a(2, 0.1F);
			int integer6 = (Integer)cfj.c(a);
			if (integer6 < 6) {
				bqc.a(fu, cfj.a(a, Integer.valueOf(integer6 + 1)), 3);
			} else {
				bqc.a(fu, false);
			}

			return ang.SUCCESS;
		}
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		return fz == fz.DOWN && !cfj1.a(bqc, fu5) ? bvs.a.n() : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		return bqd.d_(fu.c()).c().b();
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(bwa.a);
	}

	@Override
	public int a(cfj cfj, bqb bqb, fu fu) {
		return (7 - (Integer)cfj.c(a)) * 2;
	}

	@Override
	public boolean a(cfj cfj) {
		return true;
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
