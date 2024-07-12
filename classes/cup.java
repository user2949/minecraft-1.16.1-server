import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;

public class cup extends cus {
	public static final Codec<cup> a = RecordCodecBuilder.create(
		instance -> instance.group(
					Codec.FLOAT.fieldOf("min_chance").withDefault(0.0F).forGetter(cup -> cup.b),
					Codec.FLOAT.fieldOf("max_chance").withDefault(0.0F).forGetter(cup -> cup.d),
					Codec.INT.fieldOf("min_dist").withDefault(0).forGetter(cup -> cup.e),
					Codec.INT.fieldOf("max_dist").withDefault(0).forGetter(cup -> cup.f)
				)
				.apply(instance, cup::new)
	);
	private final float b;
	private final float d;
	private final int e;
	private final int f;

	public cup(float float1, float float2, int integer3, int integer4) {
		if (integer3 >= integer4) {
			throw new IllegalArgumentException("Invalid range: [" + integer3 + "," + integer4 + "]");
		} else {
			this.b = float1;
			this.d = float2;
			this.e = integer3;
			this.f = integer4;
		}
	}

	@Override
	public boolean a(fu fu1, fu fu2, fu fu3, Random random) {
		int integer6 = fu2.k(fu3);
		float float7 = random.nextFloat();
		return (double)float7 <= aec.b((double)this.b, (double)this.d, aec.c((double)integer6, (double)this.e, (double)this.f));
	}

	@Override
	protected cut<?> a() {
		return cut.b;
	}
}
