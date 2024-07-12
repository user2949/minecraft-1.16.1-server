import java.util.List;

public class bej extends beh {
	public bej(aoq<? extends bej> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public bej(bqb bqb, aoy aoy, double double3, double double4, double double5) {
		super(aoq.p, aoy, double3, double4, double5, bqb);
	}

	@Override
	protected void a(dej dej) {
		super.a(dej);
		aom aom3 = this.v();
		if (dej.c() != dej.a.ENTITY || !((dei)dej).a().s(aom3)) {
			if (!this.l.v) {
				List<aoy> list4 = this.l.a(aoy.class, this.cb().c(4.0, 2.0, 4.0));
				aol aol5 = new aol(this.l, this.cC(), this.cD(), this.cG());
				if (aom3 instanceof aoy) {
					aol5.a((aoy)aom3);
				}

				aol5.a(hh.i);
				aol5.a(3.0F);
				aol5.b(600);
				aol5.c((7.0F - aol5.g()) / (float)aol5.m());
				aol5.a(new aog(aoi.g, 1, 1));
				if (!list4.isEmpty()) {
					for (aoy aoy7 : list4) {
						double double8 = this.h(aoy7);
						if (double8 < 16.0) {
							aol5.d(aoy7.cC(), aoy7.cD(), aoy7.cG());
							break;
						}
					}
				}

				this.l.c(2006, this.cA(), this.av() ? -1 : 1);
				this.l.c(aol5);
				this.aa();
			}
		}
	}

	@Override
	public boolean aQ() {
		return false;
	}

	@Override
	public boolean a(anw anw, float float2) {
		return false;
	}

	@Override
	protected hf h() {
		return hh.i;
	}

	@Override
	protected boolean Y_() {
		return false;
	}
}
