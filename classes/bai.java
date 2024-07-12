import javax.annotation.Nullable;

public class bai extends bae {
	private static final axs b = new axs().a(64.0);
	private czf c;
	private dem d;
	private boolean e;

	public bai(bac bac) {
		super(bac);
	}

	@Override
	public bas<bai> i() {
		return bas.a;
	}

	@Override
	public void c() {
		double double2 = this.d == null ? 0.0 : this.d.c(this.a.cC(), this.a.cD(), this.a.cG());
		if (double2 < 100.0 || double2 > 22500.0 || this.a.u || this.a.v) {
			this.j();
		}
	}

	@Override
	public void d() {
		this.c = null;
		this.d = null;
	}

	@Nullable
	@Override
	public dem g() {
		return this.d;
	}

	private void j() {
		if (this.c != null && this.c.b()) {
			fu fu2 = this.a.l.a(cio.a.MOTION_BLOCKING_NO_LEAVES, new fu(cks.a));
			int integer3 = this.a.eM() == null ? 0 : this.a.eM().c();
			if (this.a.cX().nextInt(integer3 + 3) == 0) {
				this.a.eL().a(bas.c);
				return;
			}

			double double4 = 64.0;
			bec bec6 = this.a.l.a(b, (double)fu2.u(), (double)fu2.v(), (double)fu2.w());
			if (bec6 != null) {
				double4 = fu2.a(bec6.cz(), true) / 512.0;
			}

			if (bec6 != null && !bec6.bJ.a && (this.a.cX().nextInt(aec.a((int)double4) + 2) == 0 || this.a.cX().nextInt(integer3 + 2) == 0)) {
				this.a(bec6);
				return;
			}
		}

		if (this.c == null || this.c.b()) {
			int integer2 = this.a.eJ();
			int integer3x = integer2;
			if (this.a.cX().nextInt(8) == 0) {
				this.e = !this.e;
				integer3x = integer2 + 6;
			}

			if (this.e) {
				integer3x++;
			} else {
				integer3x--;
			}

			if (this.a.eM() != null && this.a.eM().c() >= 0) {
				integer3x %= 12;
				if (integer3x < 0) {
					integer3x += 12;
				}
			} else {
				integer3x -= 12;
				integer3x &= 7;
				integer3x += 12;
			}

			this.c = this.a.a(integer2, integer3x, null);
			if (this.c != null) {
				this.c.a();
			}
		}

		this.k();
	}

	private void a(bec bec) {
		this.a.eL().a(bas.b);
		this.a.eL().b(bas.b).a(bec);
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

	@Override
	public void a(bab bab, fu fu, anw anw, @Nullable bec bec) {
		if (bec != null && !bec.bJ.a) {
			this.a(bec);
		}
	}
}
