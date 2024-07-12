import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class cqs extends cqw {
	public static final Codec<cqs> a = RecordCodecBuilder.create(instance -> a(instance).apply(instance, cqs::new));

	public cqs(int integer1, int integer2, int integer3) {
		super(integer1, integer2, integer3);
	}

	@Override
	protected cqx<?> a() {
		return cqx.b;
	}

	@Override
	public List<cpg.b> a(bqf bqf, Random random, int integer, fu fu, Set<fu> set, ctd ctd, cou cou) {
		a(bqf, fu.c());
		List<cpg.b> list9 = Lists.<cpg.b>newArrayList();
		fz fz10 = fz.c.HORIZONTAL.a(random);
		int integer11 = integer - random.nextInt(4) - 1;
		int integer12 = 3 - random.nextInt(3);
		fu.a a13 = new fu.a();
		int integer14 = fu.u();
		int integer15 = fu.w();
		int integer16 = 0;

		for (int integer17 = 0; integer17 < integer; integer17++) {
			int integer18 = fu.v() + integer17;
			if (integer17 >= integer11 && integer12 > 0) {
				integer14 += fz10.i();
				integer15 += fz10.k();
				integer12--;
			}

			if (a(bqf, random, (fu)a13.d(integer14, integer18, integer15), set, ctd, cou)) {
				integer16 = integer18 + 1;
			}
		}

		list9.add(new cpg.b(new fu(integer14, integer16, integer15), 1, false));
		integer14 = fu.u();
		integer15 = fu.w();
		fz fz17 = fz.c.HORIZONTAL.a(random);
		if (fz17 != fz10) {
			int integer18x = integer11 - random.nextInt(2) - 1;
			int integer19 = 1 + random.nextInt(3);
			integer16 = 0;

			for (int integer20 = integer18x; integer20 < integer && integer19 > 0; integer19--) {
				if (integer20 >= 1) {
					int integer21 = fu.v() + integer20;
					integer14 += fz17.i();
					integer15 += fz17.k();
					if (a(bqf, random, (fu)a13.d(integer14, integer21, integer15), set, ctd, cou)) {
						integer16 = integer21 + 1;
					}
				}

				integer20++;
			}

			if (integer16 > 1) {
				list9.add(new cpg.b(new fu(integer14, integer16, integer15), 0, false));
			}
		}

		return list9;
	}
}
