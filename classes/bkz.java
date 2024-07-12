import javax.annotation.Nullable;

public class bkz extends bim {
	public bkz(bvr bvr, bke.a a) {
		super(bvr, a);
	}

	@Nullable
	@Override
	public bin b(bin bin) {
		fu fu3 = bin.a();
		bqb bqb4 = bin.o();
		cfj cfj5 = bqb4.d_(fu3);
		bvr bvr6 = this.e();
		if (!cfj5.a(bvr6)) {
			return cas.a(bqb4, fu3) == 7 ? null : bin;
		} else {
			fz fz7;
			if (bin.g()) {
				fz7 = bin.k() ? bin.i().f() : bin.i();
			} else {
				fz7 = bin.i() == fz.UP ? bin.f() : fz.UP;
			}

			int integer8 = 0;
			fu.a a9 = fu3.i().c(fz7);

			while (integer8 < 7) {
				if (!bqb4.v && !bqb.j(a9)) {
					bec bec10 = bin.m();
					int integer11 = bqb4.I();
					if (bec10 instanceof ze && a9.v() >= integer11) {
						oa oa12 = new oa(new ne("build.tooHigh", integer11).a(i.RED), mo.GAME_INFO, v.b);
						((ze)bec10).b.a(oa12);
					}
					break;
				}

				cfj5 = bqb4.d_(a9);
				if (!cfj5.a(this.e())) {
					if (cfj5.a(bin)) {
						return bin.a(bin, a9, fz7);
					}
					break;
				}

				a9.c(fz7);
				if (fz7.n().d()) {
					integer8++;
				}
			}

			return null;
		}
	}

	@Override
	protected boolean d() {
		return false;
	}
}
