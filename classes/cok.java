import com.mojang.serialization.Codec;

public class cok implements cnr {
	public static final Codec<cok> a = cly.b.h.fieldOf("portal_type").<cok>xmap(cok::new, cok -> cok.b).codec();
	public final cly.b b;

	public cok(cly.b b) {
		this.b = b;
	}
}
