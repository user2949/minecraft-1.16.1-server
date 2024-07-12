public class gy extends gx {
	@Override
	protected bki a(fv fv, bki bki) {
		bqb bqb4 = fv.h();
		if (!bqb4.s_()) {
			fu fu5 = fv.d().a(fv.e().c(bxd.a));
			this.a(a((zd)bqb4, fu5) || b((zd)bqb4, fu5));
			if (this.a() && bki.a(1, bqb4.v_(), null)) {
				bki.e(0);
			}
		}

		return bki;
	}

	private static boolean a(zd zd, fu fu) {
		cfj cfj3 = zd.d_(fu);
		if (cfj3.a(acx.ai)) {
			int integer4 = (Integer)cfj3.c(bvn.b);
			if (integer4 >= 5) {
				zd.a(null, fu, acl.aH, acm.BLOCKS, 1.0F, 1.0F);
				bvn.a(zd, fu);
				((bvn)cfj3.b()).a(zd, cfj3, fu, null, cdi.b.BEE_RELEASED);
				return true;
			}
		}

		return false;
	}

	private static boolean b(zd zd, fu fu) {
		for (aoy aoy5 : zd.a(aoy.class, new deg(fu), aop.g)) {
			if (aoy5 instanceof apn) {
				apn apn6 = (apn)aoy5;
				if (apn6.L_()) {
					apn6.a(acm.BLOCKS);
					return true;
				}
			}
		}

		return false;
	}
}
