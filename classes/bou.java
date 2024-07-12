public class bou extends bnw {
	public bou(bnw.a a, aor... arr) {
		super(a, bnx.ARMOR_FEET, arr);
	}

	@Override
	public int a(int integer) {
		return integer * 10;
	}

	@Override
	public int b(int integer) {
		return this.a(integer) + 15;
	}

	@Override
	public int a() {
		return 3;
	}

	@Override
	public boolean a(bnw bnw) {
		return super.a(bnw) && bnw != boa.j;
	}
}
