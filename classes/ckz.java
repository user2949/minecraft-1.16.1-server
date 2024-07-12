import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;

public class ckz extends ckt<cky> {
	public ckz(Codec<cky> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, cky cky) {
		bvr bvr8 = cky.f.b();
		fu fu9 = null;
		if (cky.j) {
			bvr bvr10 = bqu.d_(fu.c()).b();
			if (bvr10 == bvr8) {
				fu9 = fu;
			}
		} else {
			fu9 = a(bqu, fu, bvr8);
		}

		if (fu9 == null) {
			return false;
		} else {
			int integer10 = aec.a(random, 4, 13);
			if (random.nextInt(12) == 0) {
				integer10 *= 2;
			}

			if (!cky.j) {
				int integer11 = cha.e();
				if (fu9.v() + integer10 + 1 >= integer11) {
					return false;
				}
			}

			boolean boolean11 = !cky.j && random.nextFloat() < 0.06F;
			bqu.a(fu, bvs.a.n(), 4);
			this.a(bqu, random, cky, fu9, integer10, boolean11);
			this.b(bqu, random, cky, fu9, integer10, boolean11);
			return true;
		}
	}

	private static boolean a(bqc bqc, fu fu, boolean boolean3) {
		return bqc.a(fu, cfj -> {
			cxd cxd3 = cfj.c();
			return cfj.g() || cfj.a(bvs.A) || cfj.a(bvs.B) || cxd3 == cxd.g || boolean3 && cxd3 == cxd.e;
		});
	}

	private void a(bqc bqc, Random random, cky cky, fu fu, int integer, boolean boolean6) {
		fu.a a8 = new fu.a();
		cfj cfj9 = cky.g;
		int integer10 = boolean6 ? 1 : 0;

		for (int integer11 = -integer10; integer11 <= integer10; integer11++) {
			for (int integer12 = -integer10; integer12 <= integer10; integer12++) {
				boolean boolean13 = boolean6 && aec.a(integer11) == integer10 && aec.a(integer12) == integer10;

				for (int integer14 = 0; integer14 < integer; integer14++) {
					a8.a(fu, integer11, integer14, integer12);
					if (a(bqc, a8, true)) {
						if (cky.j) {
							if (!bqc.d_(a8.c()).g()) {
								bqc.b(a8, true);
							}

							bqc.a(a8, cfj9, 3);
						} else if (boolean13) {
							if (random.nextFloat() < 0.1F) {
								this.a(bqc, a8, cfj9);
							}
						} else {
							this.a(bqc, a8, cfj9);
						}
					}
				}
			}
		}
	}

	private void b(bqc bqc, Random random, cky cky, fu fu, int integer, boolean boolean6) {
		fu.a a8 = new fu.a();
		boolean boolean9 = cky.h.a(bvs.iK);
		int integer10 = Math.min(random.nextInt(1 + integer / 3) + 5, integer);
		int integer11 = integer - integer10;

		for (int integer12 = integer11; integer12 <= integer; integer12++) {
			int integer13 = integer12 < integer - random.nextInt(3) ? 2 : 1;
			if (integer10 > 8 && integer12 < integer11 + 4) {
				integer13 = 3;
			}

			if (boolean6) {
				integer13++;
			}

			for (int integer14 = -integer13; integer14 <= integer13; integer14++) {
				for (int integer15 = -integer13; integer15 <= integer13; integer15++) {
					boolean boolean16 = integer14 == -integer13 || integer14 == integer13;
					boolean boolean17 = integer15 == -integer13 || integer15 == integer13;
					boolean boolean18 = !boolean16 && !boolean17 && integer12 != integer;
					boolean boolean19 = boolean16 && boolean17;
					boolean boolean20 = integer12 < integer11 + 3;
					a8.a(fu, integer14, integer12, integer15);
					if (a(bqc, a8, false)) {
						if (cky.j && !bqc.d_(a8.c()).g()) {
							bqc.b(a8, true);
						}

						if (boolean20) {
							if (!boolean18) {
								this.a(bqc, random, a8, cky.h, boolean9);
							}
						} else if (boolean18) {
							this.a(bqc, random, cky, a8, 0.1F, 0.2F, boolean9 ? 0.1F : 0.0F);
						} else if (boolean19) {
							this.a(bqc, random, cky, a8, 0.01F, 0.7F, boolean9 ? 0.083F : 0.0F);
						} else {
							this.a(bqc, random, cky, a8, 5.0E-4F, 0.98F, boolean9 ? 0.07F : 0.0F);
						}
					}
				}
			}
		}
	}

	private void a(bqc bqc, Random random, cky cky, fu.a a, float float5, float float6, float float7) {
		if (random.nextFloat() < float5) {
			this.a(bqc, a, cky.i);
		} else if (random.nextFloat() < float6) {
			this.a(bqc, a, cky.h);
			if (random.nextFloat() < float7) {
				a(a, bqc, random);
			}
		}
	}

	private void a(bqc bqc, Random random, fu fu, cfj cfj, boolean boolean5) {
		if (bqc.d_(fu.c()).a(cfj.b())) {
			this.a(bqc, fu, cfj);
		} else if ((double)random.nextFloat() < 0.15) {
			this.a(bqc, fu, cfj);
			if (boolean5 && random.nextInt(11) == 0) {
				a(fu, bqc, random);
			}
		}
	}

	@Nullable
	private static fu.a a(bqc bqc, fu fu, bvr bvr) {
		fu.a a4 = fu.i();

		for (int integer5 = fu.v(); integer5 >= 1; integer5--) {
			a4.p(integer5);
			bvr bvr6 = bqc.d_(a4.c()).b();
			if (bvr6 == bvr) {
				return a4;
			}
		}

		return null;
	}

	private static void a(fu fu, bqc bqc, Random random) {
		fu.a a4 = fu.i().c(fz.DOWN);
		if (bqc.w(a4)) {
			int integer5 = aec.a(random, 1, 5);
			if (random.nextInt(7) == 0) {
				integer5 *= 2;
			}

			int integer6 = 23;
			int integer7 = 25;
			cmv.a(bqc, random, a4, integer5, 23, 25);
		}
	}
}
