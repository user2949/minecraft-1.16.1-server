import com.mojang.serialization.Codec;
import java.util.Random;

public class ckw extends ckt<coa> {
	public ckw(Codec<coa> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, coa coa) {
		if (!bqu.w(fu)) {
			return false;
		} else {
			cfj cfj8 = bqu.d_(fu.b());
			if (!cfj8.a(bvs.cL) && !cfj8.a(bvs.cO) && !cfj8.a(bvs.np)) {
				return false;
			} else {
				bqu.a(fu, bvs.cS.n(), 2);

				for (int integer9 = 0; integer9 < 1500; integer9++) {
					fu fu10 = fu.b(random.nextInt(8) - random.nextInt(8), -random.nextInt(12), random.nextInt(8) - random.nextInt(8));
					if (bqu.d_(fu10).g()) {
						int integer11 = 0;

						for (fz fz15 : fz.values()) {
							if (bqu.d_(fu10.a(fz15)).a(bvs.cS)) {
								integer11++;
							}

							if (integer11 > 1) {
								break;
							}
						}

						if (integer11 == 1) {
							bqu.a(fu10, bvs.cS.n(), 2);
						}
					}
				}

				return true;
			}
		}
	}
}
