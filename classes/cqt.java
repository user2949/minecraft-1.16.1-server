import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class cqt extends cqw {
	public static final Codec<cqt> a = RecordCodecBuilder.create(instance -> a(instance).apply(instance, cqt::new));

	public cqt(int integer1, int integer2, int integer3) {
		super(integer1, integer2, integer3);
	}

	@Override
	protected cqx<?> a() {
		return cqx.c;
	}

	@Override
	public List<cpg.b> a(bqf bqf, Random random, int integer, fu fu, Set<fu> set, ctd ctd, cou cou) {
		fu fu9 = fu.c();
		a(bqf, fu9);
		a(bqf, fu9.g());
		a(bqf, fu9.e());
		a(bqf, fu9.e().g());
		fu.a a10 = new fu.a();

		for (int integer11 = 0; integer11 < integer; integer11++) {
			a(bqf, random, a10, set, ctd, cou, fu, 0, integer11, 0);
			if (integer11 < integer - 1) {
				a(bqf, random, a10, set, ctd, cou, fu, 1, integer11, 0);
				a(bqf, random, a10, set, ctd, cou, fu, 1, integer11, 1);
				a(bqf, random, a10, set, ctd, cou, fu, 0, integer11, 1);
			}
		}

		return ImmutableList.of(new cpg.b(fu.b(integer), 0, true));
	}

	private static void a(bqf bqf, Random random, fu.a a, Set<fu> set, ctd ctd, cou cou, fu fu, int integer8, int integer9, int integer10) {
		a.a(fu, integer8, integer9, integer10);
		a(bqf, random, a, set, ctd, cou);
	}
}
