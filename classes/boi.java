public class boi extends bnw {
	public boi(bnw.a a, aor... arr) {
		super(a, bnx.ARMOR_HEAD, arr);
	}

	@Override
	public int a(int integer) {
		return 10 * integer;
	}

	@Override
	public int b(int integer) {
		return this.a(integer) + 30;
	}

	@Override
	public int a() {
		return 3;
	}
}
