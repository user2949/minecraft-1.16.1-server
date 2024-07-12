import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cne implements cnr {
	public static final Codec<cne> a = RecordCodecBuilder.create(
		instance -> instance.group(cfj.b.fieldOf("state").forGetter(cne -> cne.b), Codec.INT.fieldOf("start_radius").withDefault(0).forGetter(cne -> cne.c))
				.apply(instance, cne::new)
	);
	public final cfj b;
	public final int c;

	public cne(cfj cfj, int integer) {
		this.b = cfj;
		this.c = integer;
	}
}
