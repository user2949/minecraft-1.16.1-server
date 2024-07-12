import com.mojang.serialization.Codec;

public class crf implements cnn {
	public static final Codec<crf> a = Codec.INT.fieldOf("chance").<crf>xmap(crf::new, crf -> crf.b).codec();
	public final int b;

	public crf(int integer) {
		this.b = integer;
	}
}
