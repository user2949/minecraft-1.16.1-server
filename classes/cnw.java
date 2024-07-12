import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cnw implements cnr {
	public static final Codec<cnw> a = RecordCodecBuilder.create(
		instance -> instance.group(Codec.DOUBLE.fieldOf("probability").forGetter(cnw -> cnw.b), cli.b.c.fieldOf("type").forGetter(cnw -> cnw.c))
				.apply(instance, cnw::new)
	);
	public final double b;
	public final cli.b c;

	public cnw(double double1, cli.b b) {
		this.b = double1;
		this.c = b;
	}
}
