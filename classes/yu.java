public class yu extends zf {
	private boolean c;
	private boolean d;
	private int e;
	private int f;

	public yu(zd zd) {
		super(zd);
	}

	@Override
	public void a() {
		super.a();
		this.f++;
		long long2 = this.a.Q();
		long long4 = long2 / 24000L + 1L;
		if (!this.c && this.f > 20) {
			this.c = true;
			this.b.b.a(new oq(oq.f, 0.0F));
		}

		this.d = long2 > 120500L;
		if (this.d) {
			this.e++;
		}

		if (long2 % 24000L == 500L) {
			if (long4 <= 6L) {
				if (long4 == 6L) {
					this.b.b.a(new oq(oq.f, 104.0F));
				} else {
					this.b.a(new ne("demo.day." + long4), v.b);
				}
			}
		} else if (long4 == 1L) {
			if (long2 == 100L) {
				this.b.b.a(new oq(oq.f, 101.0F));
			} else if (long2 == 175L) {
				this.b.b.a(new oq(oq.f, 102.0F));
			} else if (long2 == 250L) {
				this.b.b.a(new oq(oq.f, 103.0F));
			}
		} else if (long4 == 5L && long2 % 24000L == 22000L) {
			this.b.a(new ne("demo.day.warning"), v.b);
		}
	}

	private void f() {
		if (this.e > 100) {
			this.b.a(new ne("demo.reminder"), v.b);
			this.e = 0;
		}
	}

	@Override
	public void a(fu fu, ry.a a, fz fz, int integer) {
		if (this.d) {
			this.f();
		} else {
			super.a(fu, a, fz, integer);
		}
	}

	@Override
	public ang a(ze ze, bqb bqb, bki bki, anf anf) {
		if (this.d) {
			this.f();
			return ang.PASS;
		} else {
			return super.a(ze, bqb, bki, anf);
		}
	}

	@Override
	public ang a(ze ze, bqb bqb, bki bki, anf anf, deh deh) {
		if (this.d) {
			this.f();
			return ang.PASS;
		} else {
			return super.a(ze, bqb, bki, anf, deh);
		}
	}
}
