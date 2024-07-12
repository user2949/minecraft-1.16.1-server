public class bos extends bnw {
	protected bos(bnw.a a, aor... arr) {
		super(a, bnx.DIGGER, arr);
	}

	@Override
	public int a(int integer) {
		return 15;
	}

	@Override
	public int b(int integer) {
		return super.a(integer) + 50;
	}

	@Override
	public int a() {
		return 1;
	}

	@Override
	public boolean a(bnw bnw) {
		return super.a(bnw) && bnw != boa.w;
	}
}
