import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class baq extends bae {
	private static final Logger b = LogManager.getLogger();
	private int c;
	private czf d;
	private dem e;
	private aoy f;
	private boolean g;

	public baq(bac bac) {
		super(bac);
	}

	@Override
	public void c() {
		if (this.f == null) {
			b.warn("Skipping player strafe phase because no player was found");
			this.a.eL().a(bas.a);
		} else {
			if (this.d != null && this.d.b()) {
				double double2 = this.f.cC();
				double double4 = this.f.cG();
				double double6 = double2 - this.a.cC();
				double double8 = double4 - this.a.cG();
				double double10 = (double)aec.a(double6 * double6 + double8 * double8);
				double double12 = Math.min(0.4F + double10 / 80.0 - 1.0, 10.0);
				this.e = new dem(double2, this.f.cD() + double12, double4);
			}

			double double2 = this.e == null ? 0.0 : this.e.c(this.a.cC(), this.a.cD(), this.a.cG());
			if (double2 < 100.0 || double2 > 22500.0) {
				this.j();
			}

			double double4 = 64.0;
			if (this.f.h(this.a) < 4096.0) {
				if (this.a.D(this.f)) {
					this.c++;
					dem dem6 = new dem(this.f.cC() - this.a.cC(), 0.0, this.f.cG() - this.a.cG()).d();
					dem dem7 = new dem((double)aec.a(this.a.p * (float) (Math.PI / 180.0)), 0.0, (double)(-aec.b(this.a.p * (float) (Math.PI / 180.0)))).d();
					float float8 = (float)dem7.b(dem6);
					float float9 = (float)(Math.acos((double)float8) * 180.0F / (float)Math.PI);
					float9 += 0.5F;
					if (this.c >= 5 && float9 >= 0.0F && float9 < 10.0F) {
						double double10 = 1.0;
						dem dem12 = this.a.f(1.0F);
						double double13 = this.a.bv.cC() - dem12.b * 1.0;
						double double15 = this.a.bv.e(0.5) + 0.5;
						double double17 = this.a.bv.cG() - dem12.d * 1.0;
						double double19 = this.f.cC() - double13;
						double double21 = this.f.e(0.5) - double15;
						double double23 = this.f.cG() - double17;
						if (!this.a.av()) {
							this.a.l.a(null, 1017, this.a.cA(), 0);
						}

						bej bej25 = new bej(this.a.l, this.a, double19, double21, double23);
						bej25.b(double13, double15, double17, 0.0F, 0.0F);
						this.a.l.c(bej25);
						this.c = 0;
						if (this.d != null) {
							while (!this.d.b()) {
								this.d.a();
							}
						}

						this.a.eL().a(bas.a);
					}
				} else if (this.c > 0) {
					this.c--;
				}
			} else if (this.c > 0) {
				this.c--;
			}
		}
	}

	private void j() {
		if (this.d == null || this.d.b()) {
			int integer2 = this.a.eJ();
			int integer3 = integer2;
			if (this.a.cX().nextInt(8) == 0) {
				this.g = !this.g;
				integer3 = integer2 + 6;
			}

			if (this.g) {
				integer3++;
			} else {
				integer3--;
			}

			if (this.a.eM() != null && this.a.eM().c() > 0) {
				integer3 %= 12;
				if (integer3 < 0) {
					integer3 += 12;
				}
			} else {
				integer3 -= 12;
				integer3 &= 7;
				integer3 += 12;
			}

			this.d = this.a.a(integer2, integer3, null);
			if (this.d != null) {
				this.d.a();
			}
		}

		this.k();
	}

	private void k() {
		if (this.d != null && !this.d.b()) {
			gr gr2 = this.d.g();
			this.d.a();
			double double3 = (double)gr2.u();
			double double7 = (double)gr2.w();

			double double5;
			do {
				double5 = (double)((float)gr2.v() + this.a.cX().nextFloat() * 20.0F);
			} while (double5 < (double)gr2.v());

			this.e = new dem(double3, double5, double7);
		}
	}

	@Override
	public void d() {
		this.c = 0;
		this.e = null;
		this.d = null;
		this.f = null;
	}

	public void a(aoy aoy) {
		this.f = aoy;
		int integer3 = this.a.eJ();
		int integer4 = this.a.o(this.f.cC(), this.f.cD(), this.f.cG());
		int integer5 = aec.c(this.f.cC());
		int integer6 = aec.c(this.f.cG());
		double double7 = (double)integer5 - this.a.cC();
		double double9 = (double)integer6 - this.a.cG();
		double double11 = (double)aec.a(double7 * double7 + double9 * double9);
		double double13 = Math.min(0.4F + double11 / 80.0 - 1.0, 10.0);
		int integer15 = aec.c(this.f.cD() + double13);
		czd czd16 = new czd(integer5, integer15, integer6);
		this.d = this.a.a(integer3, integer4, czd16);
		if (this.d != null) {
			this.d.a();
			this.k();
		}
	}

	@Nullable
	@Override
	public dem g() {
		return this.e;
	}

	@Override
	public bas<baq> i() {
		return bas.b;
	}
}
