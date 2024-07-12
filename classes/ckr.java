import com.mojang.serialization.Codec;
import java.util.Random;

public class ckr extends ckt<coa> {
	public ckr(Codec<coa> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, coa coa) {
		float float8 = (float)(random.nextInt(3) + 4);

		for (int integer9 = 0; float8 > 0.5F; integer9--) {
			for (int integer10 = aec.d(-float8); integer10 <= aec.f(float8); integer10++) {
				for (int integer11 = aec.d(-float8); integer11 <= aec.f(float8); integer11++) {
					if ((float)(integer10 * integer10 + integer11 * integer11) <= (float8 + 1.0F) * (float8 + 1.0F)) {
						this.a(bqu, fu.b(integer10, integer9, integer11), bvs.ee.n());
					}
				}
			}

			float8 = (float)((double)float8 - ((double)random.nextInt(2) + 0.5));
		}

		return true;
	}
}
