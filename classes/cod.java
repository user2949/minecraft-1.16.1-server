import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cod implements cja, cnr {
	public static final Codec<cod> b = RecordCodecBuilder.create(
		instance -> instance.group(Codec.FLOAT.fieldOf("probability").withDefault(0.0F).forGetter(cod -> cod.c)).apply(instance, cod::new)
	);
	public final float c;

	public cod(float float1) {
		this.c = float1;
	}
}
