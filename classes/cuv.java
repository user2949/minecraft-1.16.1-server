import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;

public class cuv extends cuy {
	public static final Codec<cuv> a = RecordCodecBuilder.create(
		instance -> instance.group(gl.aj.fieldOf("block").forGetter(cuv -> cuv.b), Codec.FLOAT.fieldOf("probability").forGetter(cuv -> cuv.d))
				.apply(instance, cuv::new)
	);
	private final bvr b;
	private final float d;

	public cuv(bvr bvr, float float2) {
		this.b = bvr;
		this.d = float2;
	}

	@Override
	public boolean a(cfj cfj, Random random) {
		return cfj.a(this.b) && random.nextFloat() < this.d;
	}

	@Override
	protected cuz<?> a() {
		return cuz.e;
	}
}
