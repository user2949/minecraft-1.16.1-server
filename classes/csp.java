import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class csp implements cnn {
	public static final Codec<csp> a = RecordCodecBuilder.create(
		instance -> instance.group(Codec.INT.fieldOf("min").forGetter(csp -> csp.b), Codec.INT.fieldOf("max").forGetter(csp -> csp.c)).apply(instance, csp::new)
	);
	public final int b;
	public final int c;

	public csp(int integer1, int integer2) {
		this.b = integer1;
		this.c = integer2;
	}
}
