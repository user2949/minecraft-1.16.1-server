import java.util.Random;
import javax.annotation.Nullable;

public class bwi extends bvr {
	public static final cgi a = cfz.ah;
	private final bwj b;

	protected bwi(bwj bwj, cfi.c c) {
		super(c);
		this.b = bwj;
		this.j(this.n.b().a(a, Integer.valueOf(0)));
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		if (!cfj.a(zd, fu)) {
			zd.b(fu, true);
		}
	}

	@Override
	public boolean a_(cfj cfj) {
		return (Integer)cfj.c(a) < 5;
	}

	@Override
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		fu fu6 = fu.b();
		if (zd.w(fu6) && fu6.v() < 256) {
			int integer7 = (Integer)cfj.c(a);
			if (integer7 < 5) {
				boolean boolean8 = false;
				boolean boolean9 = false;
				cfj cfj10 = zd.d_(fu.c());
				bvr bvr11 = cfj10.b();
				if (bvr11 == bvs.ee) {
					boolean8 = true;
				} else if (bvr11 == this.b) {
					int integer12 = 1;

					for (int integer13 = 0; integer13 < 4; integer13++) {
						bvr bvr14 = zd.d_(fu.c(integer12 + 1)).b();
						if (bvr14 != this.b) {
							if (bvr14 == bvs.ee) {
								boolean9 = true;
							}
							break;
						}

						integer12++;
					}

					if (integer12 < 2 || integer12 <= random.nextInt(boolean9 ? 5 : 4)) {
						boolean8 = true;
					}
				} else if (cfj10.g()) {
					boolean8 = true;
				}

				if (boolean8 && b(zd, fu6, null) && zd.w(fu.b(2))) {
					zd.a(fu, this.b.a(zd, fu), 2);
					this.b(zd, fu6, integer7);
				} else if (integer7 < 4) {
					int integer12 = random.nextInt(4);
					if (boolean9) {
						integer12++;
					}

					boolean boolean13 = false;

					for (int integer14 = 0; integer14 < integer12; integer14++) {
						fz fz15 = fz.c.HORIZONTAL.a(random);
						fu fu16 = fu.a(fz15);
						if (zd.w(fu16) && zd.w(fu16.c()) && b(zd, fu16, fz15.f())) {
							this.b(zd, fu16, integer7 + 1);
							boolean13 = true;
						}
					}

					if (boolean13) {
						zd.a(fu, this.b.a(zd, fu), 2);
					} else {
						this.a(zd, fu);
					}
				} else {
					this.a(zd, fu);
				}
			}
		}
	}

	private void b(bqb bqb, fu fu, int integer) {
		bqb.a(fu, this.n().a(a, Integer.valueOf(integer)), 2);
		bqb.c(1033, fu, 0);
	}

	private void a(bqb bqb, fu fu) {
		bqb.a(fu, this.n().a(a, Integer.valueOf(5)), 2);
		bqb.c(1034, fu, 0);
	}

	private static boolean b(bqd bqd, fu fu, @Nullable fz fz) {
		for (fz fz5 : fz.c.HORIZONTAL) {
			if (fz5 != fz && !bqd.w(fu.a(fz5))) {
				return false;
			}
		}

		return true;
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if (fz != fz.UP && !cfj1.a(bqc, fu5)) {
			bqc.G().a(fu5, this, 1);
		}

		return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		cfj cfj5 = bqd.d_(fu.c());
		if (cfj5.b() != this.b && !cfj5.a(bvs.ee)) {
			if (!cfj5.g()) {
				return false;
			} else {
				boolean boolean6 = false;

				for (fz fz8 : fz.c.HORIZONTAL) {
					cfj cfj9 = bqd.d_(fu.a(fz8));
					if (cfj9.a(this.b)) {
						if (boolean6) {
							return false;
						}

						boolean6 = true;
					} else if (!cfj9.g()) {
						return false;
					}
				}

				return boolean6;
			}
		} else {
			return true;
		}
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(bwi.a);
	}

	public static void a(bqc bqc, fu fu, Random random, int integer) {
		bqc.a(fu, ((bwj)bvs.ix).a(bqc, fu), 2);
		a(bqc, fu, random, fu, integer, 0);
	}

	private static void a(bqc bqc, fu fu2, Random random, fu fu4, int integer5, int integer6) {
		bwj bwj7 = (bwj)bvs.ix;
		int integer8 = random.nextInt(4) + 1;
		if (integer6 == 0) {
			integer8++;
		}

		for (int integer9 = 0; integer9 < integer8; integer9++) {
			fu fu10 = fu2.b(integer9 + 1);
			if (!b(bqc, fu10, null)) {
				return;
			}

			bqc.a(fu10, bwj7.a(bqc, fu10), 2);
			bqc.a(fu10.c(), bwj7.a(bqc, fu10.c()), 2);
		}

		boolean boolean9 = false;
		if (integer6 < 4) {
			int integer10 = random.nextInt(4);
			if (integer6 == 0) {
				integer10++;
			}

			for (int integer11 = 0; integer11 < integer10; integer11++) {
				fz fz12 = fz.c.HORIZONTAL.a(random);
				fu fu13 = fu2.b(integer8).a(fz12);
				if (Math.abs(fu13.u() - fu4.u()) < integer5 && Math.abs(fu13.w() - fu4.w()) < integer5 && bqc.w(fu13) && bqc.w(fu13.c()) && b(bqc, fu13, fz12.f())) {
					boolean9 = true;
					bqc.a(fu13, bwj7.a(bqc, fu13), 2);
					bqc.a(fu13.a(fz12.f()), bwj7.a(bqc, fu13.a(fz12.f())), 2);
					a(bqc, fu13, random, fu4, integer5, integer6 + 1);
				}
			}
		}

		if (!boolean9) {
			bqc.a(fu2.b(integer8), bvs.iy.n().a(a, Integer.valueOf(5)), 2);
		}
	}

	@Override
	public void a(bqb bqb, cfj cfj, deh deh, bes bes) {
		if (bes.U().a(acy.e)) {
			fu fu6 = deh.a();
			bqb.a(fu6, true, bes);
		}
	}
}
