public class bol extends bnw {
	public bol(bnw.a a, aor... arr) {
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
	public boolean b() {
		return true;
	}

	@Override
	public boolean h() {
		return false;
	}

	@Override
	public boolean i() {
		return false;
	}

	@Override
	public int a() {
		return 3;
	}
}
