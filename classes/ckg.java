import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ckg extends cke {
	public ckg(Codec<coa> codec) {
		super(codec);
	}

	@Override
	protected boolean a(bqc bqc, Random random, fu fu, cfj cfj) {
		fu.a a6 = fu.i();
		int integer7 = random.nextInt(3) + 1;

		for (int integer8 = 0; integer8 < integer7; integer8++) {
			if (!this.b(bqc, random, a6, cfj)) {
				return true;
			}

			a6.c(fz.UP);
		}

		fu fu8 = a6.h();
		int integer9 = random.nextInt(3) + 2;
		List<fz> list10 = Lists.<fz>newArrayList(fz.c.HORIZONTAL);
		Collections.shuffle(list10, random);

		for (fz fz13 : list10.subList(0, integer9)) {
			a6.g(fu8);
			a6.c(fz13);
			int integer14 = random.nextInt(5) + 2;
			int integer15 = 0;

			for (int integer16 = 0; integer16 < integer14 && this.b(bqc, random, a6, cfj); integer16++) {
				integer15++;
				a6.c(fz.UP);
				if (integer16 == 0 || integer15 >= 2 && random.nextFloat() < 0.25F) {
					a6.c(fz13);
					integer15 = 0;
				}
			}
		}

		return true;
	}
}
