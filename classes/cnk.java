import com.mojang.serialization.Codec;

public class cnk implements cnr {
	public static final Codec<cnk> a = Codec.INT.fieldOf("count").<cnk>xmap(cnk::new, cnk -> cnk.b).codec();
	public final int b;

	public cnk(int integer) {
		this.b = integer;
	}
}
