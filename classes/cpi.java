import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.Set;

public class cpi extends cpg {
	public static final Codec<cpi> a = RecordCodecBuilder.create(
		instance -> b(instance).and(Codec.INT.fieldOf("height").forGetter(cpi -> cpi.b)).apply(instance, cpi::new)
	);
	protected final int b;

	public cpi(int integer1, int integer2, int integer3, int integer4, int integer5) {
		super(integer1, integer2, integer3, integer4);
		this.b = integer5;
	}

	@Override
	protected cph<?> a() {
		return cph.g;
	}

	@Override
	protected void a(bqf bqf, Random random, cou cou, int integer4, cpg.b b, int integer6, int integer7, Set<fu> set, int integer9, ctd ctd) {
		int integer12 = b.c() ? integer6 : 1 + random.nextInt(2);

		for (int integer13 = integer9; integer13 >= integer9 - integer12; integer13--) {
			int integer14 = integer7 + b.b() + 1 - integer13;
			this.a(bqf, random, cou, b.a(), integer14, set, integer13, b.c(), ctd);
		}
	}

	@Override
	public int a(Random random, int integer, cou cou) {
		return this.b;
	}

	@Override
	protected boolean a(Random random, int integer2, int integer3, int integer4, int integer5, boolean boolean6) {
		return integer2 + integer4 >= 7 ? true : integer2 * integer2 + integer4 * integer4 > integer5 * integer5;
	}
}
