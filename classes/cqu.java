import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class cqu extends cqt {
	public static final Codec<cqu> b = RecordCodecBuilder.create(instance -> a(instance).apply(instance, cqu::new));

	public cqu(int integer1, int integer2, int integer3) {
		super(integer1, integer2, integer3);
	}

	@Override
	protected cqx<?> a() {
		return cqx.d;
	}

	@Override
	public List<cpg.b> a(bqf bqf, Random random, int integer, fu fu, Set<fu> set, ctd ctd, cou cou) {
		List<cpg.b> list9 = Lists.<cpg.b>newArrayList();
		list9.addAll(super.a(bqf, random, integer, fu, set, ctd, cou));

		for (int integer10 = integer - 2 - random.nextInt(4); integer10 > integer / 2; integer10 -= 2 + random.nextInt(4)) {
			float float11 = random.nextFloat() * (float) (Math.PI * 2);
			int integer12 = 0;
			int integer13 = 0;

			for (int integer14 = 0; integer14 < 5; integer14++) {
				integer12 = (int)(1.5F + aec.b(float11) * (float)integer14);
				integer13 = (int)(1.5F + aec.a(float11) * (float)integer14);
				fu fu15 = fu.b(integer12, integer10 - 3 + integer14 / 2, integer13);
				a(bqf, random, fu15, set, ctd, cou);
			}

			list9.add(new cpg.b(fu.b(integer12, integer10, integer13), -2, false));
		}

		return list9;
	}
}
