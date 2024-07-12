import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class csl implements cnn {
	public static final Codec<csl> a = RecordCodecBuilder.create(
		instance -> instance.group(
					Codec.INT.fieldOf("noise_to_count_ratio").forGetter(csl -> csl.b),
					Codec.DOUBLE.fieldOf("noise_factor").forGetter(csl -> csl.c),
					Codec.DOUBLE.fieldOf("noise_offset").withDefault(0.0).forGetter(csl -> csl.d),
					cio.a.g.fieldOf("heightmap").forGetter(csl -> csl.e)
				)
				.apply(instance, csl::new)
	);
	private static final Logger g = LogManager.getLogger();
	public final int b;
	public final double c;
	public final double d;
	public final cio.a e;

	public csl(int integer, double double2, double double3, cio.a a) {
		this.b = integer;
		this.c = double2;
		this.d = double3;
		this.e = a;
	}
}
