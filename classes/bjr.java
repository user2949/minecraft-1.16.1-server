public class bjr extends bke {
	public bjr(bke.a a) {
		super(a);
	}

	@Override
	public boolean e(bki bki) {
		return true;
	}

	@Override
	public anh<bki> a(bqb bqb, bec bec, anf anf) {
		bki bki5 = bec.b(anf);
		bqb.a(null, bec.cC(), bec.cD(), bec.cG(), acl.dV, acm.NEUTRAL, 0.5F, 0.4F / (h.nextFloat() * 0.4F + 0.8F));
		if (!bqb.v) {
			bfc bfc6 = new bfc(bqb, bec);
			bfc6.b(bki5);
			bfc6.a(bec, bec.q, bec.p, -20.0F, 0.7F, 1.0F);
			bqb.c(bfc6);
		}

		bec.b(acu.c.b(this));
		if (!bec.bJ.d) {
			bki5.g(1);
		}

		return anh.a(bki5, bqb.s_());
	}
}
