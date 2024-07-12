public class boe extends bnw {
	protected boe(bnw.a a, aor... arr) {
		super(a, bnx.WEAPON, arr);
	}

	@Override
	public int a(int integer) {
		return 5 + 20 * (integer - 1);
	}

	@Override
	public int b(int integer) {
		return super.a(integer) + 50;
	}

	@Override
	public int a() {
		return 2;
	}
}
