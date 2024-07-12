public class bov extends bnw {
	public bov(bnw.a a, aor... arr) {
		super(a, bnx.ARMOR_HEAD, arr);
	}

	@Override
	public int a(int integer) {
		return 1;
	}

	@Override
	public int b(int integer) {
		return this.a(integer) + 40;
	}

	@Override
	public int a() {
		return 1;
	}
}
