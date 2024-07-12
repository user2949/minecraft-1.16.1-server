import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cni implements cnn {
	public static final Codec<cni> a = RecordCodecBuilder.create(
		instance -> instance.group(
					Codec.FLOAT.fieldOf("chance").forGetter(cni -> cni.b),
					Codec.INT.fieldOf("bottom_offset").withDefault(0).forGetter(cni -> cni.c),
					Codec.INT.fieldOf("top_offset").withDefault(0).forGetter(cni -> cni.d),
					Codec.INT.fieldOf("top").withDefault(0).forGetter(cni -> cni.e)
				)
				.apply(instance, cni::new)
	);
	public final float b;
	public final int c;
	public final int d;
	public final int e;

	public cni(float float1, int integer2, int integer3, int integer4) {
		this.b = float1;
		this.c = integer2;
		this.d = integer3;
		this.e = integer4;
	}
}
