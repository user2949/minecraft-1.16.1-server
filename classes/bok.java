public class bok extends bnw {
	public bok(bnw.a a, aor... arr) {
		super(a, bnx.CROSSBOW, arr);
	}

	@Override
	public int a(int integer) {
		return 12 + (integer - 1) * 20;
	}

	@Override
	public int b(int integer) {
		return 50;
	}

	@Override
	public int a() {
		return 3;
	}
}
