import com.mojang.datafixers.Products.P5;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import java.util.Random;
import java.util.Set;

public class cpc extends cpg {
	public static final Codec<cpc> a = RecordCodecBuilder.create(instance -> a(instance).apply(instance, cpc::new));
	protected final int b;

	protected static <P extends cpc> P5<Mu<P>, Integer, Integer, Integer, Integer, Integer> a(Instance<P> instance) {
		return b(instance).and(Codec.INT.fieldOf("height").forGetter(cpc -> cpc.b));
	}

	protected cpc(int integer1, int integer2, int integer3, int integer4, int integer5, cph<?> cph) {
		super(integer1, integer2, integer3, integer4);
		this.b = integer5;
	}

	public cpc(int integer1, int integer2, int integer3, int integer4, int integer5) {
		this(integer1, integer2, integer3, integer4, integer5, cph.a);
	}

	@Override
	protected cph<?> a() {
		return cph.a;
	}

	@Override
	protected void a(bqf bqf, Random random, cou cou, int integer4, cpg.b b, int integer6, int integer7, Set<fu> set, int integer9, ctd ctd) {
		for (int integer12 = integer9; integer12 >= integer9 - integer6; integer12--) {
			int integer13 = Math.max(integer7 + b.b() - 1 - integer12 / 2, 0);
			this.a(bqf, random, cou, b.a(), integer13, set, integer12, b.c(), ctd);
		}
	}

	@Override
	public int a(Random random, int integer, cou cou) {
		return this.b;
	}

	@Override
	protected boolean a(Random random, int integer2, int integer3, int integer4, int integer5, boolean boolean6) {
		return integer2 == integer5 && integer4 == integer5 && (random.nextInt(2) == 0 || integer3 == 0);
	}
}
