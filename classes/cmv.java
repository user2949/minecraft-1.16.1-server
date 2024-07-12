import com.mojang.serialization.Codec;
import java.util.Random;

public class cmv extends ckt<coa> {
	private static final fz[] a = fz.values();

	public cmv(Codec<coa> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, coa coa) {
		if (!bqu.w(fu)) {
			return false;
		} else {
			cfj cfj8 = bqu.d_(fu.b());
			if (!cfj8.a(bvs.cL) && !cfj8.a(bvs.iK)) {
				return false;
			} else {
				this.a(bqu, random, fu);
				this.b(bqu, random, fu);
				return true;
			}
		}
	}

	private void a(bqc bqc, Random random, fu fu) {
		bqc.a(fu, bvs.iK.n(), 2);
		fu.a a5 = new fu.a();
		fu.a a6 = new fu.a();

		for (int integer7 = 0; integer7 < 200; integer7++) {
			a5.a(fu, random.nextInt(6) - random.nextInt(6), random.nextInt(2) - random.nextInt(5), random.nextInt(6) - random.nextInt(6));
			if (bqc.w(a5)) {
				int integer8 = 0;

				for (fz fz12 : a) {
					cfj cfj13 = bqc.d_(a6.a(a5, fz12));
					if (cfj13.a(bvs.cL) || cfj13.a(bvs.iK)) {
						integer8++;
					}

					if (integer8 > 1) {
						break;
					}
				}

				if (integer8 == 1) {
					bqc.a(a5, bvs.iK.n(), 2);
				}
			}
		}
	}

	private void b(bqc bqc, Random random, fu fu) {
		fu.a a5 = new fu.a();

		for (int integer6 = 0; integer6 < 100; integer6++) {
			a5.a(fu, random.nextInt(8) - random.nextInt(8), random.nextInt(2) - random.nextInt(7), random.nextInt(8) - random.nextInt(8));
			if (bqc.w(a5)) {
				cfj cfj7 = bqc.d_(a5.b());
				if (cfj7.a(bvs.cL) || cfj7.a(bvs.iK)) {
					int integer8 = aec.a(random, 1, 8);
					if (random.nextInt(6) == 0) {
						integer8 *= 2;
					}

					if (random.nextInt(5) == 0) {
						integer8 = 1;
					}

					int integer9 = 17;
					int integer10 = 25;
					a(bqc, random, a5, integer8, 17, 25);
				}
			}
		}
	}

	public static void a(bqc bqc, Random random, fu.a a, int integer4, int integer5, int integer6) {
		for (int integer7 = 0; integer7 <= integer4; integer7++) {
			if (bqc.w(a)) {
				if (integer7 == integer4 || !bqc.w(a.c())) {
					bqc.a(a, bvs.mx.n().a(byk.d, Integer.valueOf(aec.a(random, integer5, integer6))), 2);
					break;
				}

				bqc.a(a, bvs.my.n(), 2);
			}

			a.c(fz.DOWN);
		}
	}
}
