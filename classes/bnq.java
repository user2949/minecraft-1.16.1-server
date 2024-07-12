public class bnq extends bnw {
	public bnq(bnw.a a, aor... arr) {
		super(a, bnx.BOW, arr);
	}

	@Override
	public int a(int integer) {
		return 12 + (integer - 1) * 20;
	}

	@Override
	public int b(int integer) {
		return this.a(integer) + 25;
	}

	@Override
	public int a() {
		return 2;
	}
}
