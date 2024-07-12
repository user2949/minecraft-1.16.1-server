import javax.annotation.Nullable;

public class avv extends avw {
	public avv(apg apg, double double2) {
		super(apg, double2);
	}

	@Nullable
	@Override
	protected dem g() {
		dem dem2 = null;
		if (this.a.aA()) {
			dem2 = axu.b(this.a, 15, 15);
		}

		if (this.a.cX().nextFloat() >= this.h) {
			dem2 = this.j();
		}

		return dem2 == null ? super.g() : dem2;
	}

	@Nullable
	private dem j() {
		fu fu2 = this.a.cA();
		fu.a a3 = new fu.a();
		fu.a a4 = new fu.a();

		for (fu fu7 : fu.b(
			aec.c(this.a.cC() - 3.0), aec.c(this.a.cD() - 6.0), aec.c(this.a.cG() - 3.0), aec.c(this.a.cC() + 3.0), aec.c(this.a.cD() + 6.0), aec.c(this.a.cG() + 3.0)
		)) {
			if (!fu2.equals(fu7)) {
				bvr bvr8 = this.a.l.d_(a4.a(fu7, fz.DOWN)).b();
				boolean boolean9 = bvr8 instanceof bza || bvr8.a(acx.r);
				if (boolean9 && this.a.l.w(fu7) && this.a.l.w(a3.a(fu7, fz.UP))) {
					return dem.c(fu7);
				}
			}
		}

		return null;
	}
}
