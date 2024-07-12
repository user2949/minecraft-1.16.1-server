public class boc extends bnw {
	protected boc(bnw.a a, bnx bnx, aor... arr) {
		super(a, bnx, arr);
	}

	@Override
	public int a(int integer) {
		return 15 + (integer - 1) * 9;
	}

	@Override
	public int b(int integer) {
		return super.a(integer) + 50;
	}

	@Override
	public int a() {
		return 3;
	}
}
