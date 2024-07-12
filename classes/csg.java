import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class csg implements cnn {
	public static final Codec<csg> a = RecordCodecBuilder.create(
		instance -> instance.group(
					Codec.INT.fieldOf("count").forGetter(csg -> csg.b),
					Codec.FLOAT.fieldOf("extra_chance").forGetter(csg -> csg.c),
					Codec.INT.fieldOf("extra_count").forGetter(csg -> csg.d)
				)
				.apply(instance, csg::new)
	);
	public final int b;
	public final float c;
	public final int d;

	public csg(int integer1, float float2, int integer3) {
		this.b = integer1;
		this.c = float2;
		this.d = integer3;
	}
}
