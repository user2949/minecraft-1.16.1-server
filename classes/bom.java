public class bom extends bnw {
	public bom(bnw.a a, aor... arr) {
		super(a, bnx.WEAPON, arr);
	}

	@Override
	public int a(int integer) {
		return 5 + (integer - 1) * 9;
	}

	@Override
	public int b(int integer) {
		return this.a(integer) + 15;
	}

	@Override
	public int a() {
		return 3;
	}

	public static float e(int integer) {
		return 1.0F - 1.0F / (float)(integer + 1);
	}
}
