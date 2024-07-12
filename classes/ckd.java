import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ckd extends cke {
	public ckd(Codec<coa> codec) {
		super(codec);
	}

	@Override
	protected boolean a(bqc bqc, Random random, fu fu, cfj cfj) {
		if (!this.b(bqc, random, fu, cfj)) {
			return false;
		} else {
			fz fz6 = fz.c.HORIZONTAL.a(random);
			int integer7 = random.nextInt(2) + 2;
			List<fz> list8 = Lists.<fz>newArrayList(fz6, fz6.g(), fz6.h());
			Collections.shuffle(list8, random);

			for (fz fz11 : list8.subList(0, integer7)) {
				fu.a a12 = fu.i();
				int integer13 = random.nextInt(2) + 1;
				a12.c(fz11);
				int integer14;
				fz fz15;
				if (fz11 == fz6) {
					fz15 = fz6;
					integer14 = random.nextInt(3) + 2;
				} else {
					a12.c(fz.UP);
					fz[] arr16 = new fz[]{fz11, fz.UP};
					fz15 = v.a(arr16, random);
					integer14 = random.nextInt(3) + 3;
				}

				for (int integer16 = 0; integer16 < integer13 && this.b(bqc, random, a12, cfj); integer16++) {
					a12.c(fz15);
				}

				a12.c(fz15.f());
				a12.c(fz.UP);

				for (int integer16 = 0; integer16 < integer14; integer16++) {
					a12.c(fz6);
					if (!this.b(bqc, random, a12, cfj)) {
						break;
					}

					if (random.nextFloat() < 0.25F) {
						a12.c(fz.UP);
					}
				}
			}

			return true;
		}
	}
}
