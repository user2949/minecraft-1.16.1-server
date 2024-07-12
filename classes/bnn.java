public class bnn extends bnw {
	public bnn(bnw.a a, aor... arr) {
		super(a, bnx.BOW, arr);
	}

	@Override
	public int a(int integer) {
		return 1 + (integer - 1) * 10;
	}

	@Override
	public int b(int integer) {
		return this.a(integer) + 15;
	}

	@Override
	public int a() {
		return 5;
	}
}
