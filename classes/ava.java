public class ava extends atx {
	private final boolean a;
	private int b;

	public ava(aoz aoz, boolean boolean2) {
		super(aoz);
		this.d = aoz;
		this.a = boolean2;
	}

	@Override
	public boolean b() {
		return this.a && this.b > 0 && super.b();
	}

	@Override
	public void c() {
		this.b = 20;
		this.a(true);
	}

	@Override
	public void d() {
		this.a(false);
	}

	@Override
	public void e() {
		this.b--;
		super.e();
	}
}
