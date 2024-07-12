public class bhs extends bhw {
	private final bgu a;
	private final bec b;
	private int g;

	public bhs(bec bec, bgu bgu, amz amz, int integer4, int integer5, int integer6) {
		super(amz, integer4, integer5, integer6);
		this.b = bec;
		this.a = bgu;
	}

	@Override
	public boolean a(bki bki) {
		return false;
	}

	@Override
	public bki a(int integer) {
		if (this.f()) {
			this.g = this.g + Math.min(integer, this.e().E());
		}

		return super.a(integer);
	}

	@Override
	protected void a(bki bki, int integer) {
		this.g += integer;
		this.c(bki);
	}

	@Override
	protected void b(int integer) {
		this.g += integer;
	}

	@Override
	protected void c(bki bki) {
		if (this.g > 0) {
			bki.a(this.b.l, this.b, this.g);
		}

		if (this.c instanceof bhq) {
			((bhq)this.c).b(this.b);
		}

		this.g = 0;
	}

	@Override
	public bki a(bec bec, bki bki) {
		this.c(bki);
		gi<bki> gi4 = bec.l.o().c(bmx.a, this.a, bec.l);

		for (int integer5 = 0; integer5 < gi4.size(); integer5++) {
			bki bki6 = this.a.a(integer5);
			bki bki7 = gi4.get(integer5);
			if (!bki6.a()) {
				this.a.a(integer5, 1);
				bki6 = this.a.a(integer5);
			}

			if (!bki7.a()) {
				if (bki6.a()) {
					this.a.a(integer5, bki7);
				} else if (bki.c(bki6, bki7) && bki.a(bki6, bki7)) {
					bki7.f(bki6.E());
					this.a.a(integer5, bki7);
				} else if (!this.b.bt.e(bki7)) {
					this.b.a(bki7, false);
				}
			}
		}

		return bki;
	}
}
