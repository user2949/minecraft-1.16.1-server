import javax.annotation.Nullable;

public class axx implements bpm {
	private boolean a;
	private axx.a b = axx.a.SIEGE_DONE;
	private int c;
	private int d;
	private int e;
	private int f;
	private int g;

	@Override
	public int a(zd zd, boolean boolean2, boolean boolean3) {
		if (!zd.J() && boolean2) {
			float float5 = zd.f(0.0F);
			if ((double)float5 == 0.5) {
				this.b = zd.t.nextInt(10) == 0 ? axx.a.SIEGE_TONIGHT : axx.a.SIEGE_DONE;
			}

			if (this.b == axx.a.SIEGE_DONE) {
				return 0;
			} else {
				if (!this.a) {
					if (!this.a(zd)) {
						return 0;
					}

					this.a = true;
				}

				if (this.d > 0) {
					this.d--;
					return 0;
				} else {
					this.d = 2;
					if (this.c > 0) {
						this.b(zd);
						this.c--;
					} else {
						this.b = axx.a.SIEGE_DONE;
					}

					return 1;
				}
			}
		} else {
			this.b = axx.a.SIEGE_DONE;
			this.a = false;
			return 0;
		}
	}

	private boolean a(zd zd) {
		for (bec bec4 : zd.w()) {
			if (!bec4.a_()) {
				fu fu5 = bec4.cA();
				if (zd.b_(fu5) && zd.v(fu5).y() != bre.b.MUSHROOM) {
					for (int integer6 = 0; integer6 < 10; integer6++) {
						float float7 = zd.t.nextFloat() * (float) (Math.PI * 2);
						this.e = fu5.u() + aec.d(aec.b(float7) * 32.0F);
						this.f = fu5.v();
						this.g = fu5.w() + aec.d(aec.a(float7) * 32.0F);
						if (this.a(zd, new fu(this.e, this.f, this.g)) != null) {
							this.d = 0;
							this.c = 20;
							break;
						}
					}

					return true;
				}
			}
		}

		return false;
	}

	private void b(zd zd) {
		dem dem3 = this.a(zd, new fu(this.e, this.f, this.g));
		if (dem3 != null) {
			bcu bcu4;
			try {
				bcu4 = new bcu(zd);
				bcu4.a(zd, zd.d(bcu4.cA()), apb.EVENT, null, null);
			} catch (Exception var5) {
				var5.printStackTrace();
				return;
			}

			bcu4.b(dem3.b, dem3.c, dem3.d, zd.t.nextFloat() * 360.0F, 0.0F);
			zd.c(bcu4);
		}
	}

	@Nullable
	private dem a(zd zd, fu fu) {
		for (int integer4 = 0; integer4 < 10; integer4++) {
			int integer5 = fu.u() + zd.t.nextInt(16) - 8;
			int integer6 = fu.w() + zd.t.nextInt(16) - 8;
			int integer7 = zd.a(cio.a.WORLD_SURFACE, integer5, integer6);
			fu fu8 = new fu(integer5, integer7, integer6);
			if (zd.b_(fu8) && bcb.c(aoq.aX, zd, apb.EVENT, fu8, zd.t)) {
				return dem.c(fu8);
			}
		}

		return null;
	}

	static enum a {
		SIEGE_CAN_ACTIVATE,
		SIEGE_TONIGHT,
		SIEGE_DONE;
	}
}
