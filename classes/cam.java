import java.util.Optional;

public class cam extends bvr {
	public static final cgi a = cfz.aC;

	public cam(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, Integer.valueOf(0)));
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		bki bki8 = bec.b(anf);
		if (anf == anf.MAIN_HAND && !a(bki8) && a(bec.b(anf.OFF_HAND))) {
			return ang.PASS;
		} else if (a(bki8) && h(cfj)) {
			a(bqb, fu, cfj);
			if (!bec.bJ.d) {
				bki8.g(1);
			}

			return ang.a(bqb.v);
		} else if ((Integer)cfj.c(a) == 0) {
			return ang.PASS;
		} else if (!a(bqb)) {
			if (!bqb.v) {
				this.d(cfj, bqb, fu);
			}

			return ang.a(bqb.v);
		} else {
			if (!bqb.v) {
				ze ze9 = (ze)bec;
				if (ze9.L() != bqb.W() || !ze9.K().equals(fu)) {
					ze9.a(bqb.W(), fu, false, true);
					bqb.a(null, (double)fu.u() + 0.5, (double)fu.v() + 0.5, (double)fu.w() + 0.5, acl.mt, acm.BLOCKS, 1.0F, 1.0F);
					return ang.SUCCESS;
				}
			}

			return ang.CONSUME;
		}
	}

	private static boolean a(bki bki) {
		return bki.b() == bkk.dq;
	}

	private static boolean h(cfj cfj) {
		return (Integer)cfj.c(a) < 4;
	}

	private static boolean a(fu fu, bqb bqb) {
		cxa cxa3 = bqb.b(fu);
		if (!cxa3.a(acz.a)) {
			return false;
		} else if (cxa3.b()) {
			return true;
		} else {
			float float4 = (float)cxa3.e();
			if (float4 < 2.0F) {
				return false;
			} else {
				cxa cxa5 = bqb.b(fu.c());
				return !cxa5.a(acz.a);
			}
		}
	}

	private void d(cfj cfj, bqb bqb, fu fu) {
		bqb.a(fu, false);
		boolean boolean5 = fz.c.HORIZONTAL.a().map(fu::a).anyMatch(fux -> a(fux, bqb));
		final boolean boolean6 = boolean5 || bqb.b(fu.b()).a(acz.a);
		bpu bpu7 = new bpu() {
			@Override
			public Optional<Float> a(bpt bpt, bpg bpg, fu fu, cfj cfj, cxa cxa) {
				return fu.equals(fu) && boolean6 ? Optional.of(bvs.A.f()) : bpo.INSTANCE.a(bpt, bpg, fu, cfj, cxa);
			}

			@Override
			public boolean a(bpt bpt, bpg bpg, fu fu, cfj cfj, float float5) {
				return bpo.INSTANCE.a(bpt, bpg, fu, cfj, float5);
			}
		};
		bqb.a(null, anw.a(), bpu7, (double)fu.u() + 0.5, (double)fu.v() + 0.5, (double)fu.w() + 0.5, 5.0F, true, bpt.a.DESTROY);
	}

	public static boolean a(bqb bqb) {
		return bqb.m().k();
	}

	public static void a(bqb bqb, fu fu, cfj cfj) {
		bqb.a(fu, cfj.a(a, Integer.valueOf((Integer)cfj.c(a) + 1)), 3);
		bqb.a(null, (double)fu.u() + 0.5, (double)fu.v() + 0.5, (double)fu.w() + 0.5, acl.mr, acm.BLOCKS, 1.0F, 1.0F);
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(cam.a);
	}

	@Override
	public boolean a(cfj cfj) {
		return true;
	}

	public static int a(cfj cfj, int integer) {
		return aec.d((float)((Integer)cfj.c(a) - 0) / 4.0F * (float)integer);
	}

	@Override
	public int a(cfj cfj, bqb bqb, fu fu) {
		return a(cfj, 15);
	}

	public static Optional<dem> a(aoq<?> aoq, bqd bqd, fu fu) {
		for (fu fu5 : fu.a(fu.b(-1, -1, -1), fu.b(1, 1, 1))) {
			Optional<dem> optional6 = bvm.a(aoq, bqd, fu5);
			if (optional6.isPresent()) {
				return optional6;
			}
		}

		return Optional.empty();
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
