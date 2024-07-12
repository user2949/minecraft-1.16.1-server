import javax.annotation.Nullable;

public class bar extends bae {
	private boolean b;
	private czf c;
	private dem d;

	public bar(bac bac) {
		super(bac);
	}

	@Override
	public void c() {
		if (!this.b && this.c != null) {
			fu fu2 = this.a.l.a(cio.a.MOTION_BLOCKING_NO_LEAVES, cks.a);
			if (!fu2.a(this.a.cz(), 10.0)) {
				this.a.eL().a(bas.a);
			}
		} else {
			this.b = false;
			this.j();
		}
	}

	@Override
	public void d() {
		this.b = true;
		this.c = null;
		this.d = null;
	}

	private void j() {
		int integer2 = this.a.eJ();
		dem dem3 = this.a.t(1.0F);
		int integer4 = this.a.o(-dem3.b * 40.0, 105.0, -dem3.d * 40.0);
		if (this.a.eM() != null && this.a.eM().c() > 0) {
			integer4 %= 12;
			if (integer4 < 0) {
				integer4 += 12;
			}
		} else {
			integer4 -= 12;
			integer4 &= 7;
			integer4 += 12;
		}

		this.c = this.a.a(integer2, integer4, null);
		this.k();
	}

	private void k() {
		if (this.c != null) {
			this.c.a();
			if (!this.c.b()) {
				gr gr2 = this.c.g();
				this.c.a();

				double double3;
				do {
					double3 = (double)((float)gr2.v() + this.a.cX().nextFloat() * 20.0F);
				} while (double3 < (double)gr2.v());

				this.d = new dem((double)gr2.u(), double3, (double)gr2.w());
			}
		}
	}

	@Nullable
	@Override
	public dem g() {
		return this.d;
	}

	@Override
	public bas<bar> i() {
		return bas.e;
	}
}
