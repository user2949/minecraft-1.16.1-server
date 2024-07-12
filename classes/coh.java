import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

public class coh implements cnr {
	public static final Codec<coh> a = RecordCodecBuilder.create(
		instance -> instance.group(ckb.b.listOf().fieldOf("features").forGetter(coh -> coh.b), Codec.INT.fieldOf("count").withDefault(0).forGetter(coh -> coh.c))
				.apply(instance, coh::new)
	);
	public final List<ckb<?, ?>> b;
	public final int c;

	public coh(List<ckb<?, ?>> list, int integer) {
		this.b = list;
		this.c = integer;
	}
}
