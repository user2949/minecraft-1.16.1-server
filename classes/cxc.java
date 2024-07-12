import java.util.Random;

public abstract class cxc extends cwy {
	@Override
	public cwz d() {
		return cxb.d;
	}

	@Override
	public cwz e() {
		return cxb.e;
	}

	@Override
	public bke a() {
		return bkk.lM;
	}

	@Override
	public void b(bqb bqb, fu fu, cxa cxa, Random random) {
		if (bqb.S().b(bpx.a)) {
			int integer6 = random.nextInt(3);
			if (integer6 > 0) {
				fu fu7 = fu;

				for (int integer8 = 0; integer8 < integer6; integer8++) {
					fu7 = fu7.b(random.nextInt(3) - 1, 1, random.nextInt(3) - 1);
					if (!bqb.p(fu7)) {
						return;
					}

					cfj cfj9 = bqb.d_(fu7);
					if (cfj9.g()) {
						if (this.a((bqd)bqb, fu7)) {
							bqb.a(fu7, bvh.a((bpg)bqb, fu7));
							return;
						}
					} else if (cfj9.c().c()) {
						return;
					}
				}
			} else {
				for (int integer7 = 0; integer7 < 3; integer7++) {
					fu fu8 = fu.b(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
					if (!bqb.p(fu8)) {
						return;
					}

					if (bqb.w(fu8.b()) && this.b(bqb, fu8)) {
						bqb.a(fu8.b(), bvh.a((bpg)bqb, fu8));
					}
				}
			}
		}
	}

	private boolean a(bqd bqd, fu fu) {
		for (fz fz7 : fz.values()) {
			if (this.b(bqd, fu.a(fz7))) {
				return true;
			}
		}

		return false;
	}

	private boolean b(bqd bqd, fu fu) {
		return fu.v() >= 0 && fu.v() < 256 && !bqd.C(fu) ? false : bqd.d_(fu).c().d();
	}

	@Override
	protected void a(bqc bqc, fu fu, cfj cfj) {
		this.a(bqc, fu);
	}

	@Override
	public int b(bqd bqd) {
		return bqd.m().f() ? 4 : 2;
	}

	@Override
	public cfj b(cxa cxa) {
		return bvs.B.n().a(bze.a, Integer.valueOf(e(cxa)));
	}

	@Override
	public boolean a(cwz cwz) {
		return cwz == cxb.e || cwz == cxb.d;
	}

	@Override
	public int c(bqd bqd) {
		return bqd.m().f() ? 1 : 2;
	}

	@Override
	public boolean a(cxa cxa, bpg bpg, fu fu, cwz cwz, fz fz) {
		return cxa.a(bpg, fu) >= 0.44444445F && cwz.a(acz.a);
	}

	@Override
	public int a(bqd bqd) {
		return bqd.m().f() ? 10 : 30;
	}

	@Override
	public int a(bqb bqb, fu fu, cxa cxa3, cxa cxa4) {
		int integer6 = this.a(bqb);
		if (!cxa3.c() && !cxa4.c() && !(Boolean)cxa3.c(a) && !(Boolean)cxa4.c(a) && cxa4.a((bpg)bqb, fu) > cxa3.a((bpg)bqb, fu) && bqb.v_().nextInt(4) != 0) {
			integer6 *= 4;
		}

		return integer6;
	}

	private void a(bqc bqc, fu fu) {
		bqc.c(1501, fu, 0);
	}

	@Override
	protected boolean f() {
		return false;
	}

	@Override
	protected void a(bqc bqc, fu fu, cfj cfj, fz fz, cxa cxa) {
		if (fz == fz.DOWN) {
			cxa cxa7 = bqc.b(fu);
			if (this.a(acz.b) && cxa7.a(acz.a)) {
				if (cfj.b() instanceof bze) {
					bqc.a(fu, bvs.b.n(), 3);
				}

				this.a(bqc, fu);
				return;
			}
		}

		super.a(bqc, fu, cfj, fz, cxa);
	}

	@Override
	protected boolean j() {
		return true;
	}

	@Override
	protected float c() {
		return 100.0F;
	}

	public static class a extends cxc {
		@Override
		protected void a(cfk.a<cwz, cxa> a) {
			super.a(a);
			a.a(b);
		}

		@Override
		public int d(cxa cxa) {
			return (Integer)cxa.c(b);
		}

		@Override
		public boolean c(cxa cxa) {
			return false;
		}
	}

	public static class b extends cxc {
		@Override
		public int d(cxa cxa) {
			return 8;
		}

		@Override
		public boolean c(cxa cxa) {
			return true;
		}
	}
}
