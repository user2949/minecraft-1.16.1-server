import com.mojang.serialization.Codec;
import java.util.Random;

public class clk extends ckt<cnf> {
	public clk(Codec<cnf> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, cnf cnf) {
		return a(bqu, random, fu, cnf, 8, 4);
	}

	public static boolean a(bqc bqc, Random random, fu fu, cnf cnf, int integer5, int integer6) {
		for (bvr bvr7 = bqc.d_(fu.c()).b(); !bvr7.a(acx.an) && fu.v() > 0; bvr7 = bqc.d_(fu).b()) {
			fu = fu.c();
		}

		int integer8 = fu.v();
		if (integer8 >= 1 && integer8 + 1 < 256) {
			int integer9 = 0;

			for (int integer10 = 0; integer10 < integer5 * integer5; integer10++) {
				fu fu11 = fu.b(
					random.nextInt(integer5) - random.nextInt(integer5),
					random.nextInt(integer6) - random.nextInt(integer6),
					random.nextInt(integer5) - random.nextInt(integer5)
				);
				cfj cfj12 = cnf.b.a(random, fu11);
				if (bqc.w(fu11) && fu11.v() > 0 && cfj12.a(bqc, fu11)) {
					bqc.a(fu11, cfj12, 2);
					integer9++;
				}
			}

			return integer9 > 0;
		} else {
			return false;
		}
	}
}
