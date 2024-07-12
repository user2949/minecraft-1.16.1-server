public class bjk extends bke implements bly {
	public bjk(bke.a a) {
		super(a);
		bxd.a(this, bid.a);
	}

	public static boolean d(bki bki) {
		return bki.g() < bki.h() - 1;
	}

	@Override
	public boolean a(bki bki1, bki bki2) {
		return bki2.b() == bkk.qM;
	}

	@Override
	public anh<bki> a(bqb bqb, bec bec, anf anf) {
		bki bki5 = bec.b(anf);
		aor aor6 = aoz.j(bki5);
		bki bki7 = bec.b(aor6);
		if (bki7.a()) {
			bec.a(aor6, bki5.i());
			bki5.e(0);
			return anh.a(bki5, bqb.s_());
		} else {
			return anh.d(bki5);
		}
	}
}
