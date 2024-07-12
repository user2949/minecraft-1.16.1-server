import com.mojang.serialization.Codec;

public class cnh implements cnr {
	public static final Codec<cnh> a = Codec.FLOAT.xmap(cnh::new, cnh -> cnh.b);
	public final float b;

	public cnh(float float1) {
		this.b = float1;
	}
}
