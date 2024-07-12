import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cnt implements cnr {
	public static final Codec<cnt> a = RecordCodecBuilder.create(
		instance -> instance.group(
					cpo.a.fieldOf("cap_provider").forGetter(cnt -> cnt.b),
					cpo.a.fieldOf("stem_provider").forGetter(cnt -> cnt.c),
					Codec.INT.fieldOf("foliage_radius").withDefault(2).forGetter(cnt -> cnt.d)
				)
				.apply(instance, cnt::new)
	);
	public final cpo b;
	public final cpo c;
	public final int d;

	public cnt(cpo cpo1, cpo cpo2, int integer) {
		this.b = cpo1;
		this.c = cpo2;
		this.d = integer;
	}
}
