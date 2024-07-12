public class bjl extends bix {
	public bjl(bke.a a) {
		super(a);
	}

	@Override
	public anh<bki> a(bqb bqb, bec bec, anf anf) {
		bki bki5 = bko.a(bqb, aec.c(bec.cC()), aec.c(bec.cG()), (byte)0, true, false);
		bki bki6 = bec.b(anf);
		if (!bec.bJ.d) {
			bki6.g(1);
		}

		bec.b(acu.c.b(this));
		bec.a(acl.pB, 1.0F, 1.0F);
		if (bki6.a()) {
			return anh.a(bki5, bqb.s_());
		} else {
			if (!bec.bt.e(bki5.i())) {
				bec.a(bki5, false);
			}

			return anh.a(bki6, bqb.s_());
		}
	}
}
