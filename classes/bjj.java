public class bjj extends bke {
	public bjj(bke.a a) {
		super(a);
	}

	@Override
	public anh<bki> a(bqb bqb, bec bec, anf anf) {
		bki bki5 = bec.b(anf);
		bqb.a(null, bec.cC(), bec.cD(), bec.cG(), acl.dc, acm.PLAYERS, 0.5F, 0.4F / (h.nextFloat() * 0.4F + 0.8F));
		if (!bqb.v) {
			bfa bfa6 = new bfa(bqb, bec);
			bfa6.b(bki5);
			bfa6.a(bec, bec.q, bec.p, 0.0F, 1.5F, 1.0F);
			bqb.c(bfa6);
		}

		bec.b(acu.c.b(this));
		if (!bec.bJ.d) {
			bki5.g(1);
		}

		return anh.a(bki5, bqb.s_());
	}
}
