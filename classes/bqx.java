import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class bqx {
	public static final Codec<bqx> a = RecordCodecBuilder.create(
		instance -> instance.group(hh.au.fieldOf("options").forGetter(bqx -> bqx.b), Codec.FLOAT.fieldOf("probability").forGetter(bqx -> bqx.c))
				.apply(instance, bqx::new)
	);
	private final hf b;
	private final float c;

	public bqx(hf hf, float float2) {
		this.b = hf;
		this.c = float2;
	}
}
