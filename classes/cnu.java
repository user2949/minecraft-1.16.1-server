import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cnu implements cnr {
	public static final Codec<cnu> a = RecordCodecBuilder.create(
		instance -> instance.group(uh.a.fieldOf("start_pool").forGetter(cnu::b), Codec.INT.fieldOf("size").forGetter(cnu::a)).apply(instance, cnu::new)
	);
	public final uh b;
	public final int c;

	public cnu(uh uh, int integer) {
		this.b = uh;
		this.c = integer;
	}

	public int a() {
		return this.c;
	}

	public uh b() {
		return this.b;
	}
}
