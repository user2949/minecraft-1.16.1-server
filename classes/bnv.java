public class bnv extends bnw {
	protected bnv(bnw.a a, aor... arr) {
		super(a, bnx.DIGGER, arr);
	}

	@Override
	public int a(int integer) {
		return 1 + 10 * (integer - 1);
	}

	@Override
	public int b(int integer) {
		return super.a(integer) + 50;
	}

	@Override
	public int a() {
		return 5;
	}

	@Override
	public boolean a(bki bki) {
		return bki.b() == bkk.ng ? true : super.a(bki);
	}
}
