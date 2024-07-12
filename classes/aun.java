import java.util.EnumSet;
import java.util.List;

public class aun extends aug {
	public final azp a;
	private double b;
	private int c;

	public aun(azp azp, double double2) {
		this.a = azp;
		this.b = double2;
		this.a(EnumSet.of(aug.a.MOVE));
	}

	@Override
	public boolean a() {
		if (!this.a.eC() && !this.a.fD()) {
			List<aom> list2 = this.a.l.a(this.a, this.a.cb().c(9.0, 4.0, 9.0), aom -> {
				aoq<?> aoq2 = aom.U();
				return aoq2 == aoq.Q || aoq2 == aoq.aK;
			});
			azp azp3 = null;
			double double4 = Double.MAX_VALUE;

			for (aom aom7 : list2) {
				azp azp8 = (azp)aom7;
				if (azp8.fD() && !azp8.fC()) {
					double double9 = this.a.h(azp8);
					if (!(double9 > double4)) {
						double4 = double9;
						azp3 = azp8;
					}
				}
			}

			if (azp3 == null) {
				for (aom aom7x : list2) {
					azp azp8 = (azp)aom7x;
					if (azp8.eC() && !azp8.fC()) {
						double double9 = this.a.h(azp8);
						if (!(double9 > double4)) {
							double4 = double9;
							azp3 = azp8;
						}
					}
				}
			}

			if (azp3 == null) {
				return false;
			} else if (double4 < 4.0) {
				return false;
			} else if (!azp3.eC() && !this.a(azp3, 1)) {
				return false;
			} else {
				this.a.a(azp3);
				return true;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean b() {
		if (this.a.fD() && this.a.fE().aU() && this.a(this.a, 0)) {
			double double2 = this.a.h(this.a.fE());
			if (double2 > 676.0) {
				if (this.b <= 3.0) {
					this.b *= 1.2;
					this.c = 40;
					return true;
				}

				if (this.c == 0) {
					return false;
				}
			}

			if (this.c > 0) {
				this.c--;
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	public void d() {
		this.a.fB();
		this.b = 2.1;
	}

	@Override
	public void e() {
		if (this.a.fD()) {
			azp azp2 = this.a.fE();
			double double3 = (double)this.a.g(azp2);
			float float5 = 2.0F;
			dem dem6 = new dem(azp2.cC() - this.a.cC(), azp2.cD() - this.a.cD(), azp2.cG() - this.a.cG()).d().a(Math.max(double3 - 2.0, 0.0));
			this.a.x().a(this.a.cC() + dem6.b, this.a.cD() + dem6.c, this.a.cG() + dem6.d, this.b);
		}
	}

	private boolean a(azp azp, int integer) {
		if (integer > 8) {
			return false;
		} else if (azp.fD()) {
			return azp.fE().eC() ? true : this.a(azp.fE(), ++integer);
		} else {
			return false;
		}
	}
}
