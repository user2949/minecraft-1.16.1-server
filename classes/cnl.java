import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cnl implements cnn {
	public static final Codec<cnl> a = RecordCodecBuilder.create(
		instance -> instance.group(
					Codec.INT.fieldOf("count").forGetter(cnl -> cnl.b),
					Codec.INT.fieldOf("bottom_offset").withDefault(0).forGetter(cnl -> cnl.c),
					Codec.INT.fieldOf("top_offset").withDefault(0).forGetter(cnl -> cnl.d),
					Codec.INT.fieldOf("maximum").withDefault(0).forGetter(cnl -> cnl.e)
				)
				.apply(instance, cnl::new)
	);
	public final int b;
	public final int c;
	public final int d;
	public final int e;

	public cnl(int integer1, int integer2, int integer3, int integer4) {
		this.b = integer1;
		this.c = integer2;
		this.d = integer3;
		this.e = integer4;
	}
}
