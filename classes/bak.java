import javax.annotation.Nullable;

public class bak extends bae {
	private static final axs b = new axs().a(128.0);
	private czf c;
	private dem d;

	public bak(bac bac) {
		super(bac);
	}

	@Override
	public bas<bak> i() {
		return bas.c;
	}

	@Override
	public void d() {
		this.c = null;
		this.d = null;
	}

	@Override
	public void c() {
		double double2 = this.d == null ? 0.0 : this.d.c(this.a.cC(), this.a.cD(), this.a.cG());
		if (double2 < 100.0 || double2 > 22500.0 || this.a.u || this.a.v) {
			this.j();
		}
	}

	@Nullable
	@Override
	public dem g() {
		return this.d;
	}

	private void j() {
		if (this.c == null || this.c.b()) {
			int integer2 = this.a.eJ();
			fu fu3 = this.a.l.a(cio.a.MOTION_BLOCKING_NO_LEAVES, cks.a);
			bec bec4 = this.a.l.a(b, (double)fu3.u(), (double)fu3.v(), (double)fu3.w());
			int integer5;
			if (bec4 != null) {
				dem dem6 = new dem(bec4.cC(), 0.0, bec4.cG()).d();
				integer5 = this.a.o(-dem6.b * 40.0, 105.0, -dem6.d * 40.0);
			} else {
				integer5 = this.a.o(40.0, (double)fu3.v(), 0.0);
			}

			czd czd6 = new czd(fu3.u(), fu3.v(), fu3.w());
			this.c = this.a.a(integer2, integer5, czd6);
			if (this.c != null) {
				this.c.a();
			}
		}

		this.k();
		if (this.c != null && this.c.b()) {
			this.a.eL().a(bas.d);
		}
	}

	private void k() {
		if (this.c != null && !this.c.b()) {
			gr gr2 = this.c.g();
			this.c.a();
			double double3 = (double)gr2.u();
			double double5 = (double)gr2.w();

			double double7;
			do {
				double7 = (double)((float)gr2.v() + this.a.cX().nextFloat() * 20.0F);
			} while (double7 < (double)gr2.v());

			this.d = new dem(double3, double7, double5);
		}
	}
}
