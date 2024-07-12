import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cre implements cnn {
	public static final Codec<cre> a = RecordCodecBuilder.create(
		instance -> instance.group(cin.a.c.fieldOf("step").forGetter(cre -> cre.b), Codec.FLOAT.fieldOf("probability").forGetter(cre -> cre.c))
				.apply(instance, cre::new)
	);
	protected final cin.a b;
	protected final float c;

	public cre(cin.a a, float float2) {
		this.b = a;
		this.c = float2;
	}
}
