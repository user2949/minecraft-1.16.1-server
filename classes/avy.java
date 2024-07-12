public class avy extends auq {
	private final bcu b;
	private int c;

	public avy(bcu bcu, double double2, boolean boolean3) {
		super(bcu, double2, boolean3);
		this.b = bcu;
	}

	@Override
	public void c() {
		super.c();
		this.c = 0;
	}

	@Override
	public void d() {
		super.d();
		this.b.s(false);
	}

	@Override
	public void e() {
		super.e();
		this.c++;
		if (this.c >= 5 && this.j() < this.k() / 2) {
			this.b.s(true);
		} else {
			this.b.s(false);
		}
	}
}
