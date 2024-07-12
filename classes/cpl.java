import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.Set;

public class cpl extends cpg {
	public static final Codec<cpl> a = RecordCodecBuilder.create(
		instance -> b(instance)
				.<Integer, Integer>and(
					instance.group(Codec.INT.fieldOf("trunk_height").forGetter(cpl -> cpl.b), Codec.INT.fieldOf("trunk_height_random").forGetter(cpl -> cpl.c))
				)
				.apply(instance, cpl::new)
	);
	private final int b;
	private final int c;

	public cpl(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
		super(integer1, integer2, integer3, integer4);
		this.b = integer5;
		this.c = integer6;
	}

	@Override
	protected cph<?> a() {
		return cph.b;
	}

	@Override
	protected void a(bqf bqf, Random random, cou cou, int integer4, cpg.b b, int integer6, int integer7, Set<fu> set, int integer9, ctd ctd) {
		fu fu12 = b.a();
		int integer13 = random.nextInt(2);
		int integer14 = 1;
		int integer15 = 0;

		for (int integer16 = integer9; integer16 >= -integer6; integer16--) {
			this.a(bqf, random, cou, fu12, integer13, set, integer16, b.c(), ctd);
			if (integer13 >= integer14) {
				integer13 = integer15;
				integer15 = 1;
				integer14 = Math.min(integer14 + 1, integer7 + b.b());
			} else {
				integer13++;
			}
		}
	}

	@Override
	public int a(Random random, int integer, cou cou) {
		return Math.max(4, integer - this.b - random.nextInt(this.c + 1));
	}

	@Override
	protected boolean a(Random random, int integer2, int integer3, int integer4, int integer5, boolean boolean6) {
		return integer2 == integer5 && integer4 == integer5 && integer5 > 0;
	}
}
