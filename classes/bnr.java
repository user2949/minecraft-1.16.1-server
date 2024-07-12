public class bnr extends bnw {
	public bnr(bnw.a a, aor... arr) {
		super(a, bnx.CROSSBOW, arr);
	}

	@Override
	public int a(int integer) {
		return 1 + (integer - 1) * 10;
	}

	@Override
	public int b(int integer) {
		return 50;
	}

	@Override
	public int a() {
		return 4;
	}

	@Override
	public boolean a(bnw bnw) {
		return super.a(bnw) && bnw != boa.H;
	}
}
