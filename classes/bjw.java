public class bjw extends bke implements blw {
	public bjw(bke.a a) {
		super(a);
	}

	@Override
	public anh<bki> a(bqb bqb, bec bec, anf anf) {
		bki bki5 = bec.b(anf);
		if (bec.bP != null) {
			if (!bqb.v) {
				int integer6 = bec.bP.b(bki5);
				bki5.a(integer6, bec, becx -> becx.d(anf));
			}

			bqb.a(null, bec.cC(), bec.cD(), bec.cG(), acl.el, acm.NEUTRAL, 1.0F, 0.4F / (h.nextFloat() * 0.4F + 0.8F));
		} else {
			bqb.a(null, bec.cC(), bec.cD(), bec.cG(), acl.en, acm.NEUTRAL, 0.5F, 0.4F / (h.nextFloat() * 0.4F + 0.8F));
			if (!bqb.v) {
				int integer6 = bny.c(bki5);
				int integer7 = bny.b(bki5);
				bqb.c(new beo(bec, bqb, integer7, integer6));
			}

			bec.b(acu.c.b(this));
		}

		return anh.a(bki5, bqb.s_());
	}

	@Override
	public int c() {
		return 1;
	}
}
