public class azt extends aug {
	private final azs a;

	public azt(azs azs) {
		this.a = azs;
	}

	@Override
	public boolean a() {
		return this.a.l.a(this.a.cC(), this.a.cD(), this.a.cG(), 10.0);
	}

	@Override
	public void e() {
		ane ane2 = this.a.l.d(this.a.cA());
		this.a.t(false);
		this.a.u(true);
		this.a.c_(0);
		aox aox3 = aoq.P.a(this.a.l);
		aox3.b(this.a.cC(), this.a.cD(), this.a.cG());
		aox3.a(true);
		this.a.l.c(aox3);
		bcj bcj4 = this.a(ane2, this.a);
		bcj4.m(this.a);

		for (int integer5 = 0; integer5 < 3; integer5++) {
			azm azm6 = this.a(ane2);
			bcj bcj7 = this.a(ane2, azm6);
			bcj7.m(azm6);
			azm6.h(this.a.cX().nextGaussian() * 0.5, 0.0, this.a.cX().nextGaussian() * 0.5);
		}
	}

	private azm a(ane ane) {
		azs azs3 = aoq.av.a(this.a.l);
		azs3.a(this.a.l, ane, apb.TRIGGERED, null, null);
		azs3.d(this.a.cC(), this.a.cD(), this.a.cG());
		azs3.Q = 60;
		azs3.et();
		azs3.u(true);
		azs3.c_(0);
		azs3.l.c(azs3);
		return azs3;
	}

	private bcj a(ane ane, azm azm) {
		bcj bcj4 = aoq.au.a(azm.l);
		bcj4.a(azm.l, ane, apb.TRIGGERED, null, null);
		bcj4.d(azm.cC(), azm.cD(), azm.cG());
		bcj4.Q = 60;
		bcj4.et();
		if (bcj4.b(aor.HEAD).a()) {
			bcj4.a(aor.HEAD, new bki(bkk.lg));
		}

		bcj4.a(aor.MAINHAND, bny.a(bcj4.cX(), bcj4.dC(), (int)(5.0F + ane.d() * (float)bcj4.cX().nextInt(18)), false));
		bcj4.a(aor.HEAD, bny.a(bcj4.cX(), bcj4.b(aor.HEAD), (int)(5.0F + ane.d() * (float)bcj4.cX().nextInt(18)), false));
		bcj4.l.c(bcj4);
		return bcj4;
	}
}
