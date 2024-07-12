public class bop extends bnw {
	public bop(bnw.a a, aor... arr) {
		super(a, bnx.TRIDENT, arr);
	}

	@Override
	public int a(int integer) {
		return 1 + (integer - 1) * 8;
	}

	@Override
	public int b(int integer) {
		return this.a(integer) + 20;
	}

	@Override
	public int a() {
		return 5;
	}

	@Override
	public float a(int integer, apc apc) {
		return apc == apc.e ? (float)integer * 2.5F : 0.0F;
	}
}
