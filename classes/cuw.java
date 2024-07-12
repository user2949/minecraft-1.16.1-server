import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;

public class cuw extends cuy {
	public static final Codec<cuw> a = RecordCodecBuilder.create(
		instance -> instance.group(cfj.b.fieldOf("block_state").forGetter(cuw -> cuw.b), Codec.FLOAT.fieldOf("probability").forGetter(cuw -> cuw.d))
				.apply(instance, cuw::new)
	);
	private final cfj b;
	private final float d;

	public cuw(cfj cfj, float float2) {
		this.b = cfj;
		this.d = float2;
	}

	@Override
	public boolean a(cfj cfj, Random random) {
		return cfj == this.b && random.nextFloat() < this.d;
	}

	@Override
	protected cuz<?> a() {
		return cuz.f;
	}
}
