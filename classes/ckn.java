import com.mojang.serialization.Codec;
import java.util.Random;

public class ckn extends ckt<coa> {
	private static final cft a = cft.a(bvs.C);
	private final cfj ac = bvs.hS.n();
	private final cfj ad = bvs.at.n();
	private final cfj ae = bvs.A.n();

	public ckn(Codec<coa> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, coa coa) {
		fu = fu.b();

		while (bqu.w(fu) && fu.v() > 2) {
			fu = fu.c();
		}

		if (!a.a(bqu.d_(fu))) {
			return false;
		} else {
			for (int integer8 = -2; integer8 <= 2; integer8++) {
				for (int integer9 = -2; integer9 <= 2; integer9++) {
					if (bqu.w(fu.b(integer8, -1, integer9)) && bqu.w(fu.b(integer8, -2, integer9))) {
						return false;
					}
				}
			}

			for (int integer8 = -1; integer8 <= 0; integer8++) {
				for (int integer9x = -2; integer9x <= 2; integer9x++) {
					for (int integer10 = -2; integer10 <= 2; integer10++) {
						bqu.a(fu.b(integer9x, integer8, integer10), this.ad, 2);
					}
				}
			}

			bqu.a(fu, this.ae, 2);

			for (fz fz9 : fz.c.HORIZONTAL) {
				bqu.a(fu.a(fz9), this.ae, 2);
			}

			for (int integer8 = -2; integer8 <= 2; integer8++) {
				for (int integer9x = -2; integer9x <= 2; integer9x++) {
					if (integer8 == -2 || integer8 == 2 || integer9x == -2 || integer9x == 2) {
						bqu.a(fu.b(integer8, 1, integer9x), this.ad, 2);
					}
				}
			}

			bqu.a(fu.b(2, 1, 0), this.ac, 2);
			bqu.a(fu.b(-2, 1, 0), this.ac, 2);
			bqu.a(fu.b(0, 1, 2), this.ac, 2);
			bqu.a(fu.b(0, 1, -2), this.ac, 2);

			for (int integer8 = -1; integer8 <= 1; integer8++) {
				for (int integer9xx = -1; integer9xx <= 1; integer9xx++) {
					if (integer8 == 0 && integer9xx == 0) {
						bqu.a(fu.b(integer8, 4, integer9xx), this.ad, 2);
					} else {
						bqu.a(fu.b(integer8, 4, integer9xx), this.ac, 2);
					}
				}
			}

			for (int integer8 = 1; integer8 <= 3; integer8++) {
				bqu.a(fu.b(-1, integer8, -1), this.ad, 2);
				bqu.a(fu.b(-1, integer8, 1), this.ad, 2);
				bqu.a(fu.b(1, integer8, -1), this.ad, 2);
				bqu.a(fu.b(1, integer8, 1), this.ad, 2);
			}

			return true;
		}
	}
}
