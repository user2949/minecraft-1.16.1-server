import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class col implements cnr {
	public static final Codec<col> a = RecordCodecBuilder.create(
		instance -> instance.group(Codec.INT.fieldOf("count").forGetter(col -> col.b), Codec.DOUBLE.fieldOf("probability").forGetter(col -> col.c))
				.apply(instance, col::new)
	);
	public final int b;
	public final double c;

	public col(int integer, double double2) {
		this.b = integer;
		this.c = double2;
	}
}
