import com.mojang.serialization.Codec;
import java.util.Random;

public class cmg extends ckt<coa> {
	public cmg(Codec<coa> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, coa coa) {
		fu.a a8 = new fu.a();
		fu.a a9 = new fu.a();

		for (int integer10 = 0; integer10 < 16; integer10++) {
			for (int integer11 = 0; integer11 < 16; integer11++) {
				int integer12 = fu.u() + integer10;
				int integer13 = fu.w() + integer11;
				int integer14 = bqu.a(cio.a.MOTION_BLOCKING, integer12, integer13);
				a8.d(integer12, integer14, integer13);
				a9.g(a8).c(fz.DOWN, 1);
				bre bre15 = bqu.v(a8);
				if (bre15.a(bqu, a9, false)) {
					bqu.a(a9, bvs.cD.n(), 2);
				}

				if (bre15.b(bqu, a8)) {
					bqu.a(a8, bvs.cC.n(), 2);
					cfj cfj16 = bqu.d_(a9);
					if (cfj16.b(cbe.a)) {
						bqu.a(a9, cfj16.a(cbe.a, Boolean.valueOf(true)), 2);
					}
				}
			}
		}

		return true;
	}
}
