import com.mojang.serialization.Codec;
import java.util.Random;

public class cmb extends ckt<col> {
	public cmb(Codec<col> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, col col) {
		int integer8 = 0;

		for (int integer9 = 0; integer9 < col.b; integer9++) {
			int integer10 = random.nextInt(8) - random.nextInt(8);
			int integer11 = random.nextInt(8) - random.nextInt(8);
			int integer12 = bqu.a(cio.a.OCEAN_FLOOR, fu.u() + integer10, fu.w() + integer11);
			fu fu13 = new fu(fu.u() + integer10, integer12, fu.w() + integer11);
			if (bqu.d_(fu13).a(bvs.A)) {
				boolean boolean14 = random.nextDouble() < col.c;
				cfj cfj15 = boolean14 ? bvs.aV.n() : bvs.aU.n();
				if (cfj15.a(bqu, fu13)) {
					if (boolean14) {
						cfj cfj16 = cfj15.a(cbz.b, cgf.UPPER);
						fu fu17 = fu13.b();
						if (bqu.d_(fu17).a(bvs.A)) {
							bqu.a(fu13, cfj15, 2);
							bqu.a(fu17, cfj16, 2);
						}
					} else {
						bqu.a(fu13, cfj15, 2);
					}

					integer8++;
				}
			}
		}

		return integer8 > 0;
	}
}
