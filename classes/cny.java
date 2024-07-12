import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cny implements cnn {
	public static final Codec<cny> a = RecordCodecBuilder.create(
		instance -> instance.group(
					Codec.DOUBLE.fieldOf("noise_level").forGetter(cny -> cny.b),
					Codec.INT.fieldOf("below_noise").forGetter(cny -> cny.c),
					Codec.INT.fieldOf("above_noise").forGetter(cny -> cny.d)
				)
				.apply(instance, cny::new)
	);
	public final double b;
	public final int c;
	public final int d;

	public cny(double double1, int integer2, int integer3) {
		this.b = double1;
		this.c = integer2;
		this.d = integer3;
	}
}
