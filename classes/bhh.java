public class bhh extends bgi {
	private final amz c;
	private final bgr d;

	public bhh(int integer) {
		this(integer, new anm(1), new bhv(1));
	}

	public bhh(int integer, amz amz, bgr bgr) {
		super(bhk.q, integer);
		a(amz, 1);
		a(bgr, 1);
		this.c = amz;
		this.d = bgr;
		this.a(new bhw(amz, 0, 0, 0) {
			@Override
			public void d() {
				super.d();
				bhh.this.a(this.c);
			}
		});
		this.a(bgr);
	}

	@Override
	public boolean a(bec bec, int integer) {
		if (integer >= 100) {
			int integer4 = integer - 100;
			this.a(0, integer4);
			return true;
		} else {
			switch (integer) {
				case 1: {
					int integer4 = this.d.a(0);
					this.a(0, integer4 - 1);
					return true;
				}
				case 2: {
					int integer4 = this.d.a(0);
					this.a(0, integer4 + 1);
					return true;
				}
				case 3:
					if (!bec.eJ()) {
						return false;
					}

					bki bki4 = this.c.b(0);
					this.c.Z_();
					if (!bec.bt.e(bki4)) {
						bec.a(bki4, false);
					}

					return true;
				default:
					return false;
			}
		}
	}

	@Override
	public void a(int integer1, int integer2) {
		super.a(integer1, integer2);
		this.c();
	}

	@Override
	public boolean a(bec bec) {
		return this.c.a(bec);
	}
}
