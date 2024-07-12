public class bjq extends bke {
	public bjq(bke.a a) {
		super(a);
	}

	@Override
	public anh<bki> a(bqb bqb, bec bec, anf anf) {
		bki bki5 = bec.b(anf);
		bqb.a(null, bec.cC(), bec.cD(), bec.cG(), acl.dI, acm.NEUTRAL, 0.5F, 0.4F / (h.nextFloat() * 0.4F + 0.8F));
		bec.eT().a(this, 20);
		if (!bqb.v) {
			bfb bfb6 = new bfb(bqb, bec);
			bfb6.b(bki5);
			bfb6.a(bec, bec.q, bec.p, 0.0F, 1.5F, 1.0F);
			bqb.c(bfb6);
		}

		bec.b(acu.c.b(this));
		if (!bec.bJ.d) {
			bki5.g(1);
		}

		return anh.a(bki5, bqb.s_());
	}
}
