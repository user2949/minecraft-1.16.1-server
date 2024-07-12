public class bap extends baf {
	private static final axs b = new axs().a(150.0);
	private final axs c;
	private int d;

	public bap(bac bac) {
		super(bac);
		this.c = new axs().a(20.0).a(aoy -> Math.abs(aoy.cD() - bac.cD()) <= 10.0);
	}

	@Override
	public void c() {
		this.d++;
		aoy aoy2 = this.a.l.a(this.c, this.a, this.a.cC(), this.a.cD(), this.a.cG());
		if (aoy2 != null) {
			if (this.d > 25) {
				this.a.eL().a(bas.h);
			} else {
				dem dem3 = new dem(aoy2.cC() - this.a.cC(), 0.0, aoy2.cG() - this.a.cG()).d();
				dem dem4 = new dem((double)aec.a(this.a.p * (float) (Math.PI / 180.0)), 0.0, (double)(-aec.b(this.a.p * (float) (Math.PI / 180.0)))).d();
				float float5 = (float)dem4.b(dem3);
				float float6 = (float)(Math.acos((double)float5) * 180.0F / (float)Math.PI) + 0.5F;
				if (float6 < 0.0F || float6 > 10.0F) {
					double double7 = aoy2.cC() - this.a.bv.cC();
					double double9 = aoy2.cG() - this.a.bv.cG();
					double double11 = aec.a(aec.g(180.0 - aec.d(double7, double9) * 180.0F / (float)Math.PI - (double)this.a.p), -100.0, 100.0);
					this.a.bA *= 0.8F;
					float float13 = aec.a(double7 * double7 + double9 * double9) + 1.0F;
					float float14 = float13;
					if (float13 > 40.0F) {
						float13 = 40.0F;
					}

					this.a.bA = (float)((double)this.a.bA + double11 * (double)(0.7F / float13 / float14));
					this.a.p = this.a.p + this.a.bA;
				}
			}
		} else if (this.d >= 100) {
			aoy2 = this.a.l.a(b, this.a, this.a.cC(), this.a.cD(), this.a.cG());
			this.a.eL().a(bas.e);
			if (aoy2 != null) {
				this.a.eL().a(bas.i);
				this.a.eL().b(bas.i).a(new dem(aoy2.cC(), aoy2.cD(), aoy2.cG()));
			}
		}
	}

	@Override
	public void d() {
		this.d = 0;
	}

	@Override
	public bas<bap> i() {
		return bas.g;
	}
}
