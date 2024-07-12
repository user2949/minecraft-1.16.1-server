import com.mojang.serialization.Codec;
import java.util.Random;

public class cmq extends ckt<coa> {
	public cmq(Codec<coa> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, coa coa) {
		return a(bqu, random, fu, 8, 4, 8);
	}

	public static boolean a(bqc bqc, Random random, fu fu, int integer4, int integer5, int integer6) {
		if (a(bqc, fu)) {
			return false;
		} else {
			b(bqc, random, fu, integer4, integer5, integer6);
			return true;
		}
	}

	private static void b(bqc bqc, Random random, fu fu, int integer4, int integer5, int integer6) {
		fu.a a7 = new fu.a();

		for (int integer8 = 0; integer8 < integer4 * integer4; integer8++) {
			a7.g(fu).e(aec.a(random, -integer4, integer4), aec.a(random, -integer5, integer5), aec.a(random, -integer4, integer4));
			if (a(bqc, a7) && !a(bqc, (fu)a7)) {
				int integer9 = aec.a(random, 1, integer6);
				if (random.nextInt(6) == 0) {
					integer9 *= 2;
				}

				if (random.nextInt(5) == 0) {
					integer9 = 1;
				}

				int integer10 = 17;
				int integer11 = 25;
				a(bqc, random, a7, integer9, 17, 25);
			}
		}
	}

	private static boolean a(bqc bqc, fu.a a) {
		do {
			a.e(0, -1, 0);
			if (bqb.l(a)) {
				return false;
			}
		} while (bqc.d_(a).g());

		a.e(0, 1, 0);
		return true;
	}

	public static void a(bqc bqc, Random random, fu.a a, int integer4, int integer5, int integer6) {
		for (int integer7 = 1; integer7 <= integer4; integer7++) {
			if (bqc.w(a)) {
				if (integer7 == integer4 || !bqc.w(a.b())) {
					bqc.a(a, bvs.mz.n().a(byk.d, Integer.valueOf(aec.a(random, integer5, integer6))), 2);
					break;
				}

				bqc.a(a, bvs.mA.n(), 2);
			}

			a.c(fz.UP);
		}
	}

	private static boolean a(bqc bqc, fu fu) {
		if (!bqc.w(fu)) {
			return true;
		} else {
			cfj cfj3 = bqc.d_(fu.c());
			return !cfj3.a(bvs.cL) && !cfj3.a(bvs.ml) && !cfj3.a(bvs.mn);
		}
	}
}
