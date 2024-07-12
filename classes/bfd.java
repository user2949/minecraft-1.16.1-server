import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class bfd extends bey {
	public static final Predicate<aoy> b = aoy::dN;

	public bfd(aoq<? extends bfd> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public bfd(bqb bqb, aoy aoy) {
		super(aoq.aI, aoy, bqb);
	}

	public bfd(bqb bqb, double double2, double double3, double double4) {
		super(aoq.aI, double2, double3, double4, bqb);
	}

	@Override
	protected bke h() {
		return bkk.qi;
	}

	@Override
	protected float k() {
		return 0.05F;
	}

	@Override
	protected void a(deh deh) {
		super.a(deh);
		if (!this.l.v) {
			bki bki3 = this.g();
			bmb bmb4 = bmd.d(bki3);
			List<aog> list5 = bmd.a(bki3);
			boolean boolean6 = bmb4 == bme.b && list5.isEmpty();
			fz fz7 = deh.b();
			fu fu8 = deh.a();
			fu fu9 = fu8.a(fz7);
			if (boolean6) {
				this.a(fu9, fz7);
				this.a(fu9.a(fz7.f()), fz7);

				for (fz fz11 : fz.c.HORIZONTAL) {
					this.a(fu9.a(fz11), fz11);
				}
			}
		}
	}

	@Override
	protected void a(dej dej) {
		super.a(dej);
		if (!this.l.v) {
			bki bki3 = this.g();
			bmb bmb4 = bmd.d(bki3);
			List<aog> list5 = bmd.a(bki3);
			boolean boolean6 = bmb4 == bme.b && list5.isEmpty();
			if (boolean6) {
				this.m();
			} else if (!list5.isEmpty()) {
				if (this.n()) {
					this.a(bki3, bmb4);
				} else {
					this.a(list5, dej.c() == dej.a.ENTITY ? ((dei)dej).a() : null);
				}
			}

			int integer7 = bmb4.b() ? 2007 : 2002;
			this.l.c(integer7, this.cA(), bmd.c(bki3));
			this.aa();
		}
	}

	private void m() {
		deg deg2 = this.cb().c(4.0, 2.0, 4.0);
		List<aoy> list3 = this.l.a(aoy.class, deg2, b);
		if (!list3.isEmpty()) {
			for (aoy aoy5 : list3) {
				double double6 = this.h(aoy5);
				if (double6 < 16.0 && aoy5.dN()) {
					aoy5.a(anw.c(aoy5, this.v()), 1.0F);
				}
			}
		}
	}

	private void a(List<aog> list, @Nullable aom aom) {
		deg deg4 = this.cb().c(4.0, 2.0, 4.0);
		List<aoy> list5 = this.l.a(aoy.class, deg4);
		if (!list5.isEmpty()) {
			for (aoy aoy7 : list5) {
				if (aoy7.eg()) {
					double double8 = this.h(aoy7);
					if (double8 < 16.0) {
						double double10 = 1.0 - Math.sqrt(double8) / 4.0;
						if (aoy7 == aom) {
							double10 = 1.0;
						}

						for (aog aog13 : list) {
							aoe aoe14 = aog13.a();
							if (aoe14.a()) {
								aoe14.a(this, this.v(), aoy7, aog13.c(), double10);
							} else {
								int integer15 = (int)(double10 * (double)aog13.b() + 0.5);
								if (integer15 > 20) {
									aoy7.c(new aog(aoe14, integer15, aog13.c(), aog13.d(), aog13.e()));
								}
							}
						}
					}
				}
			}
		}
	}

	private void a(bki bki, bmb bmb) {
		aol aol4 = new aol(this.l, this.cC(), this.cD(), this.cG());
		aom aom5 = this.v();
		if (aom5 instanceof aoy) {
			aol4.a((aoy)aom5);
		}

		aol4.a(3.0F);
		aol4.b(-0.5F);
		aol4.d(10);
		aol4.c(-aol4.g() / (float)aol4.m());
		aol4.a(bmb);

		for (aog aog7 : bmd.b(bki)) {
			aol4.a(new aog(aog7));
		}

		le le6 = bki.o();
		if (le6 != null && le6.c("CustomPotionColor", 99)) {
			aol4.a(le6.h("CustomPotionColor"));
		}

		this.l.c(aol4);
	}

	private boolean n() {
		return this.g().b() == bkk.ql;
	}

	private void a(fu fu, fz fz) {
		cfj cfj4 = this.l.d_(fu);
		if (cfj4.a(acx.am)) {
			this.l.a(fu, false);
		} else if (bwb.g(cfj4)) {
			this.l.a(null, 1009, fu, 0);
			bwb.c(this.l, fu, cfj4);
			this.l.a(fu, cfj4.a(bwb.b, Boolean.valueOf(false)));
		}
	}
}
