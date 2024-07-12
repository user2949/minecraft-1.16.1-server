import com.mojang.serialization.Codec;
import java.util.Random;

public class cjx extends ckt<coa> {
	public cjx(Codec<coa> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, coa coa) {
		if (fu.v() > bqu.t_() - 1) {
			return false;
		} else if (!bqu.d_(fu).a(bvs.A) && !bqu.d_(fu.c()).a(bvs.A)) {
			return false;
		} else {
			boolean boolean8 = false;

			for (fz fz12 : fz.values()) {
				if (fz12 != fz.DOWN && bqu.d_(fu.a(fz12)).a(bvs.gT)) {
					boolean8 = true;
					break;
				}
			}

			if (!boolean8) {
				return false;
			} else {
				bqu.a(fu, bvs.kV.n(), 2);

				for (int integer9 = 0; integer9 < 200; integer9++) {
					int integer10 = random.nextInt(5) - random.nextInt(6);
					int integer11 = 3;
					if (integer10 < 2) {
						integer11 += integer10 / 2;
					}

					if (integer11 >= 1) {
						fu fu12 = fu.b(random.nextInt(integer11) - random.nextInt(integer11), integer10, random.nextInt(integer11) - random.nextInt(integer11));
						cfj cfj13 = bqu.d_(fu12);
						if (cfj13.c() == cxd.a || cfj13.a(bvs.A) || cfj13.a(bvs.gT) || cfj13.a(bvs.cD)) {
							for (fz fz17 : fz.values()) {
								cfj cfj18 = bqu.d_(fu12.a(fz17));
								if (cfj18.a(bvs.kV)) {
									bqu.a(fu12, bvs.kV.n(), 2);
									break;
								}
							}
						}
					}
				}

				return true;
			}
		}
	}
}
