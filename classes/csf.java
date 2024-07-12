import com.mojang.serialization.Codec;

public class csf implements cnn {
	public static final Codec<csf> a = Codec.INT.fieldOf("count").<csf>xmap(csf::new, csf -> csf.b).codec();
	public final int b;

	public csf(int integer) {
		this.b = integer;
	}
}
