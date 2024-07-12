import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

public class cnp implements cnr {
	public static final Codec<cnp> a = RecordCodecBuilder.create(
		instance -> instance.group(
					cfj.b.fieldOf("state").forGetter(cnp -> cnp.b),
					Codec.INT.fieldOf("radius").withDefault(0).forGetter(cnp -> cnp.c),
					Codec.INT.fieldOf("y_size").withDefault(0).forGetter(cnp -> cnp.d),
					cfj.b.listOf().fieldOf("targets").forGetter(cnp -> cnp.e)
				)
				.apply(instance, cnp::new)
	);
	public final cfj b;
	public final int c;
	public final int d;
	public final List<cfj> e;

	public cnp(cfj cfj, int integer2, int integer3, List<cfj> list) {
		this.b = cfj;
		this.c = integer2;
		this.d = integer3;
		this.e = list;
	}
}
