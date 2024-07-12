import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class cqq extends cqw {
	public static final Codec<cqq> a = RecordCodecBuilder.create(instance -> a(instance).apply(instance, cqq::new));

	public cqq(int integer1, int integer2, int integer3) {
		super(integer1, integer2, integer3);
	}

	@Override
	protected cqx<?> a() {
		return cqx.e;
	}

	@Override
	public List<cpg.b> a(bqf bqf, Random random, int integer, fu fu, Set<fu> set, ctd ctd, cou cou) {
		List<cpg.b> list9 = Lists.<cpg.b>newArrayList();
		fu fu10 = fu.c();
		a(bqf, fu10);
		a(bqf, fu10.g());
		a(bqf, fu10.e());
		a(bqf, fu10.e().g());
		fz fz11 = fz.c.HORIZONTAL.a(random);
		int integer12 = integer - random.nextInt(4);
		int integer13 = 2 - random.nextInt(3);
		int integer14 = fu.u();
		int integer15 = fu.v();
		int integer16 = fu.w();
		int integer17 = integer14;
		int integer18 = integer16;
		int integer19 = integer15 + integer - 1;

		for (int integer20 = 0; integer20 < integer; integer20++) {
			if (integer20 >= integer12 && integer13 > 0) {
				integer17 += fz11.i();
				integer18 += fz11.k();
				integer13--;
			}

			int integer21 = integer15 + integer20;
			fu fu22 = new fu(integer17, integer21, integer18);
			if (cmp.d(bqf, fu22)) {
				a(bqf, random, fu22, set, ctd, cou);
				a(bqf, random, fu22.g(), set, ctd, cou);
				a(bqf, random, fu22.e(), set, ctd, cou);
				a(bqf, random, fu22.g().e(), set, ctd, cou);
			}
		}

		list9.add(new cpg.b(new fu(integer17, integer19, integer18), 0, true));

		for (int integer20 = -1; integer20 <= 2; integer20++) {
			for (int integer21 = -1; integer21 <= 2; integer21++) {
				if ((integer20 < 0 || integer20 > 1 || integer21 < 0 || integer21 > 1) && random.nextInt(3) <= 0) {
					int integer22 = random.nextInt(3) + 2;

					for (int integer23 = 0; integer23 < integer22; integer23++) {
						a(bqf, random, new fu(integer14 + integer20, integer19 - integer23 - 1, integer16 + integer21), set, ctd, cou);
					}

					list9.add(new cpg.b(new fu(integer17 + integer20, integer19, integer18 + integer21), 0, false));
				}
			}
		}

		return list9;
	}
}
