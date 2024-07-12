import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cnv implements cnr {
	public static final Codec<cnv> a = RecordCodecBuilder.create(
		instance -> instance.group(Codec.INT.fieldOf("height").forGetter(cnv -> cnv.b), cfj.b.fieldOf("state").forGetter(cnv -> cnv.c)).apply(instance, cnv::new)
	);
	public final int b;
	public final cfj c;

	public cnv(int integer, cfj cfj) {
		this.b = integer;
		this.c = cfj;
	}
}
