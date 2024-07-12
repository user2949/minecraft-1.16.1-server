import com.mojang.serialization.Codec;
import java.util.Random;

public class cmt extends ckt<coa> {
	private static final fz[] a = fz.values();

	public cmt(Codec<coa> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, coa coa) {
		fu.a a8 = fu.i();

		for (int integer9 = fu.v(); integer9 < 256; integer9++) {
			a8.g(fu);
			a8.e(random.nextInt(4) - random.nextInt(4), 0, random.nextInt(4) - random.nextInt(4));
			a8.p(integer9);
			if (bqu.w(a8)) {
				for (fz fz13 : a) {
					if (fz13 != fz.DOWN && cck.a(bqu, a8, fz13)) {
						bqu.a(a8, bvs.dP.n().a(cck.a(fz13), Boolean.valueOf(true)), 2);
						break;
					}
				}
			}
		}

		return true;
	}
}
