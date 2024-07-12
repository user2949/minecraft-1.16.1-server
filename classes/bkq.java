public class bkq extends bke {
	private static final gw a = new gv() {
		private final gv b = new gv();

		@Override
		public bki a(fv fv, bki bki) {
			fz fz4 = fv.e().c(bxd.a);
			bqb bqb5 = fv.h();
			double double6 = fv.a() + (double)fz4.i() * 1.125;
			double double8 = Math.floor(fv.b()) + (double)fz4.j();
			double double10 = fv.c() + (double)fz4.k() * 1.125;
			fu fu12 = fv.d().a(fz4);
			cfj cfj13 = bqb5.d_(fu12);
			cgm cgm14 = cfj13.b() instanceof bvj ? cfj13.c(((bvj)cfj13.b()).d()) : cgm.NORTH_SOUTH;
			double double15;
			if (cfj13.a(acx.G)) {
				if (cgm14.c()) {
					double15 = 0.6;
				} else {
					double15 = 0.1;
				}
			} else {
				if (!cfj13.g() || !bqb5.d_(fu12.c()).a(acx.G)) {
					return this.b.dispense(fv, bki);
				}

				cfj cfj17 = bqb5.d_(fu12.c());
				cgm cgm18 = cfj17.b() instanceof bvj ? cfj17.c(((bvj)cfj17.b()).d()) : cgm.NORTH_SOUTH;
				if (fz4 != fz.DOWN && cgm18.c()) {
					double15 = -0.4;
				} else {
					double15 = -0.9;
				}
			}

			bfr bfr17 = bfr.a(bqb5, double6, double8 + double15, double10, ((bkq)bki.b()).b);
			if (bki.t()) {
				bfr17.a(bki.r());
			}

			bqb5.c(bfr17);
			bki.g(1);
			return bki;
		}

		@Override
		protected void a(fv fv) {
			fv.h().c(1000, fv.d(), 0);
		}
	};
	private final bfr.a b;

	public bkq(bfr.a a, bke.a a) {
		super(a);
		this.b = a;
		bxd.a(this, bkq.a);
	}

	@Override
	public ang a(blv blv) {
		bqb bqb3 = blv.o();
		fu fu4 = blv.a();
		cfj cfj5 = bqb3.d_(fu4);
		if (!cfj5.a(acx.G)) {
			return ang.FAIL;
		} else {
			bki bki6 = blv.l();
			if (!bqb3.v) {
				cgm cgm7 = cfj5.b() instanceof bvj ? cfj5.c(((bvj)cfj5.b()).d()) : cgm.NORTH_SOUTH;
				double double8 = 0.0;
				if (cgm7.c()) {
					double8 = 0.5;
				}

				bfr bfr10 = bfr.a(bqb3, (double)fu4.u() + 0.5, (double)fu4.v() + 0.0625 + double8, (double)fu4.w() + 0.5, this.b);
				if (bki6.t()) {
					bfr10.a(bki6.r());
				}

				bqb3.c(bfr10);
			}

			bki6.g(1);
			return ang.a(bqb3.v);
		}
	}
}
