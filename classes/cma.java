import com.mojang.serialization.Codec;
import java.util.Random;

public class cma extends ckt<cnk> {
	public cma(Codec<cnk> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, cnk cnk) {
		int integer8 = 0;

		for (int integer9 = 0; integer9 < cnk.b; integer9++) {
			int integer10 = random.nextInt(8) - random.nextInt(8);
			int integer11 = random.nextInt(8) - random.nextInt(8);
			int integer12 = bqu.a(cio.a.OCEAN_FLOOR, fu.u() + integer10, fu.w() + integer11);
			fu fu13 = new fu(fu.u() + integer10, integer12, fu.w() + integer11);
			cfj cfj14 = bvs.kU.n().a(cat.a, Integer.valueOf(random.nextInt(4) + 1));
			if (bqu.d_(fu13).a(bvs.A) && cfj14.a(bqu, fu13)) {
				bqu.a(fu13, cfj14, 2);
				integer8++;
			}
		}

		return integer8 > 0;
	}
}
