public class bln extends bku {
	public bln(bke.a a) {
		super(a);
	}

	@Override
	public anh<bki> a(bqb bqb, bec bec, anf anf) {
		bki bki5 = bec.b(anf);
		if (!bqb.v) {
			bfd bfd6 = new bfd(bqb, bec);
			bfd6.b(bki5);
			bfd6.a(bec, bec.q, bec.p, -20.0F, 0.5F, 1.0F);
			bqb.c(bfd6);
		}

		bec.b(acu.c.b(this));
		if (!bec.bJ.d) {
			bki5.g(1);
		}

		return anh.a(bki5, bqb.s_());
	}
}
