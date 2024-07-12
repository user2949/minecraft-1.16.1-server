public class bao extends baf {
	private int b;
	private int c;
	private aol d;

	public bao(bac bac) {
		super(bac);
	}

	@Override
	public void b() {
		this.b++;
		if (this.b % 2 == 0 && this.b < 10) {
			dem dem2 = this.a.t(1.0F).d();
			dem2.b((float) (-Math.PI / 4));
			double double3 = this.a.bv.cC();
			double double5 = this.a.bv.e(0.5);
			double double7 = this.a.bv.cG();

			for (int integer9 = 0; integer9 < 8; integer9++) {
				double double10 = double3 + this.a.cX().nextGaussian() / 2.0;
				double double12 = double5 + this.a.cX().nextGaussian() / 2.0;
				double double14 = double7 + this.a.cX().nextGaussian() / 2.0;

				for (int integer16 = 0; integer16 < 6; integer16++) {
					this.a.l.a(hh.i, double10, double12, double14, -dem2.b * 0.08F * (double)integer16, -dem2.c * 0.6F, -dem2.d * 0.08F * (double)integer16);
				}

				dem2.b((float) (Math.PI / 16));
			}
		}
	}

	@Override
	public void c() {
		this.b++;
		if (this.b >= 200) {
			if (this.c >= 4) {
				this.a.eL().a(bas.e);
			} else {
				this.a.eL().a(bas.g);
			}
		} else if (this.b == 10) {
			dem dem2 = new dem(this.a.bv.cC() - this.a.cC(), 0.0, this.a.bv.cG() - this.a.cG()).d();
			float float3 = 5.0F;
			double double4 = this.a.bv.cC() + dem2.b * 5.0 / 2.0;
			double double6 = this.a.bv.cG() + dem2.d * 5.0 / 2.0;
			double double8 = this.a.bv.e(0.5);
			double double10 = double8;
			fu.a a12 = new fu.a(double4, double8, double6);

			while (this.a.l.w(a12)) {
				if (--double10 < 0.0) {
					double10 = double8;
					break;
				}

				a12.c(double4, double10, double6);
			}

			double10 = (double)(aec.c(double10) + 1);
			this.d = new aol(this.a.l, double4, double10, double6);
			this.d.a(this.a);
			this.d.a(5.0F);
			this.d.b(200);
			this.d.a(hh.i);
			this.d.a(new aog(aoi.g));
			this.a.l.c(this.d);
		}
	}

	@Override
	public void d() {
		this.b = 0;
		this.c++;
	}

	@Override
	public void e() {
		if (this.d != null) {
			this.d.aa();
			this.d = null;
		}
	}

	@Override
	public bas<bao> i() {
		return bas.f;
	}

	public void j() {
		this.c = 0;
	}
}
