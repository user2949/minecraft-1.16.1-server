import com.mojang.serialization.Codec;
import java.util.Random;

public class clb extends ckt<cns> {
	private final bvr a = bvs.gT;

	public clb(Codec<cns> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, cns cns) {
		while (bqu.w(fu) && fu.v() > 2) {
			fu = fu.c();
		}

		if (!bqu.d_(fu).a(bvs.cE)) {
			return false;
		} else {
			int integer8 = random.nextInt(cns.b) + 2;
			int integer9 = 1;

			for (int integer10 = fu.u() - integer8; integer10 <= fu.u() + integer8; integer10++) {
				for (int integer11 = fu.w() - integer8; integer11 <= fu.w() + integer8; integer11++) {
					int integer12 = integer10 - fu.u();
					int integer13 = integer11 - fu.w();
					if (integer12 * integer12 + integer13 * integer13 <= integer8 * integer8) {
						for (int integer14 = fu.v() - 1; integer14 <= fu.v() + 1; integer14++) {
							fu fu15 = new fu(integer10, integer14, integer11);
							bvr bvr16 = bqu.d_(fu15).b();
							if (b(bvr16) || bvr16 == bvs.cE || bvr16 == bvs.cD) {
								bqu.a(fu15, this.a.n(), 2);
							}
						}
					}
				}
			}

			return true;
		}
	}
}
