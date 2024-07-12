import com.mojang.serialization.Codec;
import java.util.Random;

public abstract class cke extends ckt<coa> {
	public cke(Codec<coa> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, coa coa) {
		cfj cfj8 = acx.X.a(random).n();
		return this.a(bqu, random, fu, cfj8);
	}

	protected abstract boolean a(bqc bqc, Random random, fu fu, cfj cfj);

	protected boolean b(bqc bqc, Random random, fu fu, cfj cfj) {
		fu fu6 = fu.b();
		cfj cfj7 = bqc.d_(fu);
		if ((cfj7.a(bvs.A) || cfj7.a(acx.aa)) && bqc.d_(fu6).a(bvs.A)) {
			bqc.a(fu, cfj, 3);
			if (random.nextFloat() < 0.25F) {
				bqc.a(fu6, acx.aa.a(random).n(), 2);
			} else if (random.nextFloat() < 0.05F) {
				bqc.a(fu6, bvs.kU.n().a(cat.a, Integer.valueOf(random.nextInt(4) + 1)), 2);
			}

			for (fz fz9 : fz.c.HORIZONTAL) {
				if (random.nextFloat() < 0.2F) {
					fu fu10 = fu.a(fz9);
					if (bqc.d_(fu10).a(bvs.A)) {
						cfj cfj11 = acx.Y.a(random).n().a(bvf.a, fz9);
						bqc.a(fu10, cfj11, 2);
					}
				}
			}

			return true;
		} else {
			return false;
		}
	}
}
