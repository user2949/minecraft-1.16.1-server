import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class coi implements cnr {
	public static final Codec<coi> a = RecordCodecBuilder.create(
		instance -> instance.group(cfj.b.fieldOf("target").forGetter(coi -> coi.b), cfj.b.fieldOf("state").forGetter(coi -> coi.c)).apply(instance, coi::new)
	);
	public final cfj b;
	public final cfj c;

	public coi(cfj cfj1, cfj cfj2) {
		this.b = cfj1;
		this.c = cfj2;
	}
}
