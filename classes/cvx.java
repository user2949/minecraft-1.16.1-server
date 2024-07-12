import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cvx implements cvy {
	public static final Codec<cvx> a = RecordCodecBuilder.create(
		instance -> instance.group(
					cfj.b.fieldOf("top_material").forGetter(cvx -> cvx.b),
					cfj.b.fieldOf("under_material").forGetter(cvx -> cvx.c),
					cfj.b.fieldOf("underwater_material").forGetter(cvx -> cvx.d)
				)
				.apply(instance, cvx::new)
	);
	private final cfj b;
	private final cfj c;
	private final cfj d;

	public cvx(cfj cfj1, cfj cfj2, cfj cfj3) {
		this.b = cfj1;
		this.c = cfj2;
		this.d = cfj3;
	}

	@Override
	public cfj a() {
		return this.b;
	}

	@Override
	public cfj b() {
		return this.c;
	}

	public cfj c() {
		return this.d;
	}
}
