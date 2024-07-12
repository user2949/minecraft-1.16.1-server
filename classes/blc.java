public class blc extends bke {
	public blc(bke.a a) {
		super(a);
		bxd.a(this, bid.a);
	}

	@Override
	public String f(bki bki) {
		return bki.b("BlockEntityTag") != null ? this.a() + '.' + d(bki).c() : super.f(bki);
	}

	@Override
	public blu d_(bki bki) {
		return blu.BLOCK;
	}

	@Override
	public int e_(bki bki) {
		return 72000;
	}

	@Override
	public anh<bki> a(bqb bqb, bec bec, anf anf) {
		bki bki5 = bec.b(anf);
		bec.c(anf);
		return anh.b(bki5);
	}

	@Override
	public boolean a(bki bki1, bki bki2) {
		return ada.b.a(bki2.b()) || super.a(bki1, bki2);
	}

	public static bje d(bki bki) {
		return bje.a(bki.a("BlockEntityTag").h("Base"));
	}
}
