import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.Set;

public class cpj extends cpg {
	public static final Codec<cpj> a = RecordCodecBuilder.create(
		instance -> b(instance)
				.<Integer, Integer>and(
					instance.group(Codec.INT.fieldOf("height_random").forGetter(cpj -> cpj.b), Codec.INT.fieldOf("crown_height").forGetter(cpj -> cpj.c))
				)
				.apply(instance, cpj::new)
	);
	private final int b;
	private final int c;

	public cpj(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
		super(integer1, integer2, integer3, integer4);
		this.b = integer5;
		this.c = integer6;
	}

	@Override
	protected cph<?> a() {
		return cph.h;
	}

	@Override
	protected void a(bqf bqf, Random random, cou cou, int integer4, cpg.b b, int integer6, int integer7, Set<fu> set, int integer9, ctd ctd) {
		fu fu12 = b.a();
		int integer13 = 0;

		for (int integer14 = fu12.v() - integer6 + integer9; integer14 <= fu12.v() + integer9; integer14++) {
			int integer15 = fu12.v() - integer14;
			int integer16 = integer7 + b.b() + aec.d((float)integer15 / (float)integer6 * 3.5F);
			int integer17;
			if (integer15 > 0 && integer16 == integer13 && (integer14 & 1) == 0) {
				integer17 = integer16 + 1;
			} else {
				integer17 = integer16;
			}

			this.a(bqf, random, cou, new fu(fu12.u(), integer14, fu12.w()), integer17, set, 0, b.c(), ctd);
			integer13 = integer16;
		}
	}

	@Override
	public int a(Random random, int integer, cou cou) {
		return random.nextInt(this.b + 1) + this.c;
	}

	@Override
	protected boolean a(Random random, int integer2, int integer3, int integer4, int integer5, boolean boolean6) {
		return integer2 + integer4 >= 7 ? true : integer2 * integer2 + integer4 * integer4 > integer5 * integer5;
	}
}
