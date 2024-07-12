import com.mojang.serialization.Codec;
import java.util.Random;

public class cjl extends ckt<cod> {
	private static final cfj a = bvs.kY.n().a(bux.d, Integer.valueOf(1)).a(bux.e, cfw.NONE).a(bux.f, Integer.valueOf(0));
	private static final cfj ac = a.a(bux.e, cfw.LARGE).a(bux.f, Integer.valueOf(1));
	private static final cfj ad = a.a(bux.e, cfw.LARGE);
	private static final cfj ae = a.a(bux.e, cfw.SMALL);

	public cjl(Codec<cod> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, cod cod) {
		int integer8 = 0;
		fu.a a9 = fu.i();
		fu.a a10 = fu.i();
		if (bqu.w(a9)) {
			if (bvs.kY.n().a(bqu, a9)) {
				int integer11 = random.nextInt(12) + 5;
				if (random.nextFloat() < cod.c) {
					int integer12 = random.nextInt(4) + 1;

					for (int integer13 = fu.u() - integer12; integer13 <= fu.u() + integer12; integer13++) {
						for (int integer14 = fu.w() - integer12; integer14 <= fu.w() + integer12; integer14++) {
							int integer15 = integer13 - fu.u();
							int integer16 = integer14 - fu.w();
							if (integer15 * integer15 + integer16 * integer16 <= integer12 * integer12) {
								a10.d(integer13, bqu.a(cio.a.WORLD_SURFACE, integer13, integer14) - 1, integer14);
								if (b(bqu.d_(a10).b())) {
									bqu.a(a10, bvs.l.n(), 2);
								}
							}
						}
					}
				}

				for (int integer12 = 0; integer12 < integer11 && bqu.w(a9); integer12++) {
					bqu.a(a9, a, 2);
					a9.c(fz.UP, 1);
				}

				if (a9.v() - fu.v() >= 3) {
					bqu.a(a9, ac, 2);
					bqu.a(a9.c(fz.DOWN, 1), ad, 2);
					bqu.a(a9.c(fz.DOWN, 1), ae, 2);
				}
			}

			integer8++;
		}

		return integer8 > 0;
	}
}
