import com.mojang.serialization.Codec;
import java.util.Random;

public class clg extends ckt<coa> {
	public clg(Codec<coa> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, coa coa) {
		int integer8 = 0;
		int integer9 = bqu.a(cio.a.OCEAN_FLOOR, fu.u(), fu.w());
		fu fu10 = new fu(fu.u(), integer9, fu.w());
		if (bqu.d_(fu10).a(bvs.A)) {
			cfj cfj11 = bvs.kc.n();
			cfj cfj12 = bvs.kd.n();
			int integer13 = 1 + random.nextInt(10);

			for (int integer14 = 0; integer14 <= integer13; integer14++) {
				if (bqu.d_(fu10).a(bvs.A) && bqu.d_(fu10.b()).a(bvs.A) && cfj12.a(bqu, fu10)) {
					if (integer14 == integer13) {
						bqu.a(fu10, cfj11.a(byw.d, Integer.valueOf(random.nextInt(4) + 20)), 2);
						integer8++;
					} else {
						bqu.a(fu10, cfj12, 2);
					}
				} else if (integer14 > 0) {
					fu fu15 = fu10.c();
					if (cfj11.a(bqu, fu15) && !bqu.d_(fu15.c()).a(bvs.kc)) {
						bqu.a(fu15, cfj11.a(byw.d, Integer.valueOf(random.nextInt(4) + 20)), 2);
						integer8++;
					}
					break;
				}

				fu10 = fu10.b();
			}
		}

		return integer8 > 0;
	}
}
