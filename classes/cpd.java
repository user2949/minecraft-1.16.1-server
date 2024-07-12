import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.Set;

public class cpd extends cpc {
	public static final Codec<cpd> c = RecordCodecBuilder.create(instance -> a(instance).apply(instance, cpd::new));

	public cpd(int integer1, int integer2, int integer3, int integer4, int integer5) {
		super(integer1, integer2, integer3, integer4, integer5, cph.e);
	}

	@Override
	protected cph<?> a() {
		return cph.e;
	}

	@Override
	protected void a(bqf bqf, Random random, cou cou, int integer4, cpg.b b, int integer6, int integer7, Set<fu> set, int integer9, ctd ctd) {
		for (int integer12 = integer9; integer12 >= integer9 - integer6; integer12--) {
			int integer13 = integer7 + b.b() - 1 - integer12;
			this.a(bqf, random, cou, b.a(), integer13, set, integer12, b.c(), ctd);
		}
	}

	@Override
	protected boolean a(Random random, int integer2, int integer3, int integer4, int integer5, boolean boolean6) {
		return integer2 == integer5 && integer4 == integer5 && random.nextInt(2) == 0;
	}
}
