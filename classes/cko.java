import com.mojang.serialization.Codec;
import java.util.Random;

public class cko extends ckt<cnp> {
	public cko(Codec<cnp> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, cnp cnp) {
		if (!bqu.b(fu).a(acz.a)) {
			return false;
		} else {
			int integer8 = 0;
			int integer9 = random.nextInt(cnp.c - 2) + 2;

			for (int integer10 = fu.u() - integer9; integer10 <= fu.u() + integer9; integer10++) {
				for (int integer11 = fu.w() - integer9; integer11 <= fu.w() + integer9; integer11++) {
					int integer12 = integer10 - fu.u();
					int integer13 = integer11 - fu.w();
					if (integer12 * integer12 + integer13 * integer13 <= integer9 * integer9) {
						for (int integer14 = fu.v() - cnp.d; integer14 <= fu.v() + cnp.d; integer14++) {
							fu fu15 = new fu(integer10, integer14, integer11);
							cfj cfj16 = bqu.d_(fu15);

							for (cfj cfj18 : cnp.e) {
								if (cfj18.a(cfj16.b())) {
									bqu.a(fu15, cnp.b, 2);
									integer8++;
									break;
								}
							}
						}
					}
				}
			}

			return integer8 > 0;
		}
	}
}
