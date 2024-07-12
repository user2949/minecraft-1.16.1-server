import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class cqv extends cqw {
	public static final Codec<cqv> a = RecordCodecBuilder.create(instance -> a(instance).apply(instance, cqv::new));

	public cqv(int integer1, int integer2, int integer3) {
		super(integer1, integer2, integer3);
	}

	@Override
	protected cqx<?> a() {
		return cqx.a;
	}

	@Override
	public List<cpg.b> a(bqf bqf, Random random, int integer, fu fu, Set<fu> set, ctd ctd, cou cou) {
		a(bqf, fu.c());

		for (int integer9 = 0; integer9 < integer; integer9++) {
			a(bqf, random, fu.b(integer9), set, ctd, cou);
		}

		return ImmutableList.of(new cpg.b(fu.b(integer), 0, false));
	}
}
