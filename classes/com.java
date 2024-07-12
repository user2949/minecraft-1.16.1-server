import com.mojang.serialization.Codec;

public class com implements cnr {
	public static final Codec<com> a = Codec.BOOL.fieldOf("is_beached").withDefault(false).<com>xmap(com::new, com -> com.b).codec();
	public final boolean b;

	public com(boolean boolean1) {
		this.b = boolean1;
	}
}
