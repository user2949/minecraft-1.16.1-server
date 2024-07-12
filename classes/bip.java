import javax.annotation.Nullable;

public class bip extends bke {
	public bip(bke.a a) {
		super(a);
	}

	@Override
	public ang a(blv blv) {
		bqb bqb3 = blv.o();
		fu fu4 = blv.a();
		fu fu5 = fu4.a(blv.i());
		if (a(blv.l(), bqb3, fu4)) {
			if (!bqb3.v) {
				bqb3.c(2005, fu4, 0);
			}

			return ang.a(bqb3.v);
		} else {
			cfj cfj6 = bqb3.d_(fu4);
			boolean boolean7 = cfj6.d(bqb3, fu4, blv.i());
			if (boolean7 && a(blv.l(), bqb3, fu5, blv.i())) {
				if (!bqb3.v) {
					bqb3.c(2005, fu5, 0);
				}

				return ang.a(bqb3.v);
			} else {
				return ang.PASS;
			}
		}
	}

	public static boolean a(bki bki, bqb bqb, fu fu) {
		cfj cfj4 = bqb.d_(fu);
		if (cfj4.b() instanceof bvt) {
			bvt bvt5 = (bvt)cfj4.b();
			if (bvt5.a(bqb, fu, cfj4, bqb.v)) {
				if (bqb instanceof zd) {
					if (bvt5.a(bqb, bqb.t, fu, cfj4)) {
						bvt5.a((zd)bqb, bqb.t, fu, cfj4);
					}

					bki.g(1);
				}

				return true;
			}
		}

		return false;
	}

	public static boolean a(bki bki, bqb bqb, fu fu, @Nullable fz fz) {
		if (bqb.d_(fu).a(bvs.A) && bqb.b(fu).e() == 8) {
			if (!(bqb instanceof zd)) {
				return true;
			} else {
				label80:
				for (int integer5 = 0; integer5 < 128; integer5++) {
					fu fu6 = fu;
					bre bre7 = bqb.v(fu);
					cfj cfj8 = bvs.aU.n();

					for (int integer9 = 0; integer9 < integer5 / 16; integer9++) {
						fu6 = fu6.b(h.nextInt(3) - 1, (h.nextInt(3) - 1) * h.nextInt(3) / 2, h.nextInt(3) - 1);
						bre7 = bqb.v(fu6);
						if (bqb.d_(fu6).r(bqb, fu6)) {
							continue label80;
						}
					}

					if (bre7 == brk.T || bre7 == brk.W) {
						if (integer5 == 0 && fz != null && fz.n().d()) {
							cfj8 = acx.Y.a(bqb.t).n().a(bvf.a, fz);
						} else if (h.nextInt(4) == 0) {
							cfj8 = acx.W.a(h).n();
						}
					}

					if (cfj8.b().a(acx.Y)) {
						for (int integer9x = 0; !cfj8.a((bqd)bqb, fu6) && integer9x < 4; integer9x++) {
							cfj8 = cfj8.a(bvf.a, fz.c.HORIZONTAL.a(h));
						}
					}

					if (cfj8.a((bqd)bqb, fu6)) {
						cfj cfj9 = bqb.d_(fu6);
						if (cfj9.a(bvs.A) && bqb.b(fu6).e() == 8) {
							bqb.a(fu6, cfj8, 3);
						} else if (cfj9.a(bvs.aU) && h.nextInt(10) == 0) {
							((bvt)bvs.aU).a((zd)bqb, h, fu6, cfj9);
						}
					}
				}

				bki.g(1);
				return true;
			}
		} else {
			return false;
		}
	}
}
