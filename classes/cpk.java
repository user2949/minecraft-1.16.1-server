import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.Set;

public class cpk extends cpg {
	public static final Codec<cpk> a = RecordCodecBuilder.create(
		instance -> b(instance)
				.<Integer, Integer>and(instance.group(Codec.INT.fieldOf("height").forGetter(cpk -> cpk.b), Codec.INT.fieldOf("height_random").forGetter(cpk -> cpk.c)))
				.apply(instance, cpk::new)
	);
	private final int b;
	private final int c;

	public cpk(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
		super(integer1, integer2, integer3, integer4);
		this.b = integer5;
		this.c = integer6;
	}

	@Override
	protected cph<?> a() {
		return cph.c;
	}

	@Override
	protected void a(bqf bqf, Random random, cou cou, int integer4, cpg.b b, int integer6, int integer7, Set<fu> set, int integer9, ctd ctd) {
		int integer12 = 0;

		for (int integer13 = integer9; integer13 >= integer9 - integer6; integer13--) {
			this.a(bqf, random, cou, b.a(), integer12, set, integer13, b.c(), ctd);
			if (integer12 >= 1 && integer13 == integer9 - integer6 + 1) {
				integer12--;
			} else if (integer12 < integer7 + b.b()) {
				integer12++;
			}
		}
	}

	@Override
	public int a(Random random, int integer) {
		return super.a(random, integer) + random.nextInt(integer + 1);
	}

	@Override
	public int a(Random random, int integer, cou cou) {
		return this.b + random.nextInt(this.c + 1);
	}

	@Override
	protected boolean a(Random random, int integer2, int integer3, int integer4, int integer5, boolean boolean6) {
		return integer2 == integer5 && integer4 == integer5 && integer5 > 0;
	}
}
