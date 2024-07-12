import com.mojang.serialization.Codec;

public class cns implements cnr {
	public static final Codec<cns> a = Codec.INT.fieldOf("radius").<cns>xmap(cns::new, cns -> cns.b).codec();
	public final int b;

	public cns(int integer) {
		this.b = integer;
	}
}
