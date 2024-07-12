public class bog extends bnw {
	public bog(bnw.a a, aor... arr) {
		super(a, bnx.BREAKABLE, arr);
	}

	@Override
	public int a(int integer) {
		return integer * 25;
	}

	@Override
	public int b(int integer) {
		return this.a(integer) + 50;
	}

	@Override
	public boolean b() {
		return true;
	}

	@Override
	public int a() {
		return 1;
	}
}
