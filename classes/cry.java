import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cry implements cnn {
	public static final Codec<cry> a = RecordCodecBuilder.create(
		instance -> instance.group(
					Codec.INT.fieldOf("count").forGetter(cry -> cry.b),
					Codec.INT.fieldOf("baseline").forGetter(cry -> cry.c),
					Codec.INT.fieldOf("spread").forGetter(cry -> cry.d)
				)
				.apply(instance, cry::new)
	);
	public final int b;
	public final int c;
	public final int d;

	public cry(int integer1, int integer2, int integer3) {
		this.b = integer1;
		this.c = integer2;
		this.d = integer3;
	}
}
