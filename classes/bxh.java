public class bxh extends bxr {
	protected static final dfg a = bvr.a(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

	public bxh(cfi.c c) {
		super(c);
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return a;
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		this.d(cfj, bqb, fu);
		return ang.a(bqb.v);
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu, bec bec) {
		this.d(cfj, bqb, fu);
	}

	private void d(cfj cfj, bqb bqb, fu fu) {
		for (int integer5 = 0; integer5 < 1000; integer5++) {
			fu fu6 = fu.b(bqb.t.nextInt(16) - bqb.t.nextInt(16), bqb.t.nextInt(8) - bqb.t.nextInt(8), bqb.t.nextInt(16) - bqb.t.nextInt(16));
			if (bqb.d_(fu6).g()) {
				if (bqb.v) {
					for (int integer7 = 0; integer7 < 128; integer7++) {
						double double8 = bqb.t.nextDouble();
						float float10 = (bqb.t.nextFloat() - 0.5F) * 0.2F;
						float float11 = (bqb.t.nextFloat() - 0.5F) * 0.2F;
						float float12 = (bqb.t.nextFloat() - 0.5F) * 0.2F;
						double double13 = aec.d(double8, (double)fu6.u(), (double)fu.u()) + (bqb.t.nextDouble() - 0.5) + 0.5;
						double double15 = aec.d(double8, (double)fu6.v(), (double)fu.v()) + bqb.t.nextDouble() - 0.5;
						double double17 = aec.d(double8, (double)fu6.w(), (double)fu.w()) + (bqb.t.nextDouble() - 0.5) + 0.5;
						bqb.a(hh.Q, double13, double15, double17, (double)float10, (double)float11, (double)float12);
					}
				} else {
					bqb.a(fu6, cfj, 2);
					bqb.a(fu, false);
				}

				return;
			}
		}
	}

	@Override
	protected int c() {
		return 5;
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
