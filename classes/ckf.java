import com.mojang.serialization.Codec;
import java.util.Random;

public class ckf extends cke {
	public ckf(Codec<coa> codec) {
		super(codec);
	}

	@Override
	protected boolean a(bqc bqc, Random random, fu fu, cfj cfj) {
		int integer6 = random.nextInt(3) + 3;
		int integer7 = random.nextInt(3) + 3;
		int integer8 = random.nextInt(3) + 3;
		int integer9 = random.nextInt(3) + 1;
		fu.a a10 = fu.i();

		for (int integer11 = 0; integer11 <= integer7; integer11++) {
			for (int integer12 = 0; integer12 <= integer6; integer12++) {
				for (int integer13 = 0; integer13 <= integer8; integer13++) {
					a10.d(integer11 + fu.u(), integer12 + fu.v(), integer13 + fu.w());
					a10.c(fz.DOWN, integer9);
					if ((integer11 != 0 && integer11 != integer7 || integer12 != 0 && integer12 != integer6)
						&& (integer13 != 0 && integer13 != integer8 || integer12 != 0 && integer12 != integer6)
						&& (integer11 != 0 && integer11 != integer7 || integer13 != 0 && integer13 != integer8)
						&& (integer11 == 0 || integer11 == integer7 || integer12 == 0 || integer12 == integer6 || integer13 == 0 || integer13 == integer8)
						&& !(random.nextFloat() < 0.1F)
						&& !this.b(bqc, random, a10, cfj)) {
					}
				}
			}
		}

		return true;
	}
}
