import javax.annotation.Nullable;

public class ccb extends bvr {
	public static final cga a = cfz.B;

	public ccb(cfi.c c) {
		super(c);
		this.j(this.n().a(a, Boolean.valueOf(false)));
	}

	@Override
	public void b(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!cfj4.a(cfj1.b())) {
			if (bqb.r(fu)) {
				a(bqb, fu);
				bqb.a(fu, false);
			}
		}
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu3, bvr bvr, fu fu5, boolean boolean6) {
		if (bqb.r(fu3)) {
			a(bqb, fu3);
			bqb.a(fu3, false);
		}
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, bec bec) {
		if (!bqb.s_() && !bec.b_() && (Boolean)cfj.c(a)) {
			a(bqb, fu);
		}

		super.a(bqb, fu, cfj, bec);
	}

	@Override
	public void a(bqb bqb, fu fu, bpt bpt) {
		if (!bqb.v) {
			bbh bbh5 = new bbh(bqb, (double)fu.u() + 0.5, (double)fu.v(), (double)fu.w() + 0.5, bpt.d());
			bbh5.a((short)(bqb.t.nextInt(bbh5.i() / 4) + bbh5.i() / 8));
			bqb.c(bbh5);
		}
	}

	public static void a(bqb bqb, fu fu) {
		a(bqb, fu, null);
	}

	private static void a(bqb bqb, fu fu, @Nullable aoy aoy) {
		if (!bqb.v) {
			bbh bbh4 = new bbh(bqb, (double)fu.u() + 0.5, (double)fu.v(), (double)fu.w() + 0.5, aoy);
			bqb.c(bbh4);
			bqb.a(null, bbh4.cC(), bbh4.cD(), bbh4.cG(), acl.oU, acm.BLOCKS, 1.0F, 1.0F);
		}
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		bki bki8 = bec.b(anf);
		bke bke9 = bki8.b();
		if (bke9 != bkk.kd && bke9 != bkk.oR) {
			return super.a(cfj, bqb, fu, bec, anf, deh);
		} else {
			a(bqb, fu, bec);
			bqb.a(fu, bvs.a.n(), 11);
			if (!bec.b_()) {
				if (bke9 == bkk.kd) {
					bki8.a(1, bec, becx -> becx.d(anf));
				} else {
					bki8.g(1);
				}
			}

			return ang.a(bqb.v);
		}
	}

	@Override
	public void a(bqb bqb, cfj cfj, deh deh, bes bes) {
		if (!bqb.v) {
			aom aom6 = bes.v();
			if (bes.bm()) {
				fu fu7 = deh.a();
				a(bqb, fu7, aom6 instanceof aoy ? (aoy)aom6 : null);
				bqb.a(fu7, false);
			}
		}
	}

	@Override
	public boolean a(bpt bpt) {
		return false;
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(ccb.a);
	}
}
