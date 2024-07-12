import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cse implements cnn {
	public static final Codec<cse> a = RecordCodecBuilder.create(
		instance -> instance.group(Codec.INT.fieldOf("count").forGetter(cse -> cse.b), Codec.FLOAT.fieldOf("chance").forGetter(cse -> cse.c))
				.apply(instance, cse::new)
	);
	public final int b;
	public final float c;

	public cse(int integer, float float2) {
		this.b = integer;
		this.c = float2;
	}
}
