import com.mojang.serialization.Codec;
import java.util.Random;

public class cjv extends ckt<cne> {
	public cjv(Codec<cne> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, cne cne) {
		while (fu.v() > 3) {
			if (!bqu.w(fu.c())) {
				bvr bvr8 = bqu.d_(fu.c()).b();
				if (b(bvr8) || a(bvr8)) {
					break;
				}
			}

			fu = fu.c();
		}

		if (fu.v() <= 3) {
			return false;
		} else {
			int integer8 = cne.c;

			for (int integer9 = 0; integer8 >= 0 && integer9 < 3; integer9++) {
				int integer10 = integer8 + random.nextInt(2);
				int integer11 = integer8 + random.nextInt(2);
				int integer12 = integer8 + random.nextInt(2);
				float float13 = (float)(integer10 + integer11 + integer12) * 0.333F + 0.5F;

				for (fu fu15 : fu.a(fu.b(-integer10, -integer11, -integer12), fu.b(integer10, integer11, integer12))) {
					if (fu15.j(fu) <= (double)(float13 * float13)) {
						bqu.a(fu15, cne.b, 4);
					}
				}

				fu = fu.b(-(integer8 + 1) + random.nextInt(2 + integer8 * 2), 0 - random.nextInt(2), -(integer8 + 1) + random.nextInt(2 + integer8 * 2));
			}

			return true;
		}
	}
}
