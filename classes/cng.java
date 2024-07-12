import com.mojang.serialization.Codec;

public class cng implements cnr {
	public static final Codec<cng> a = cfj.b.fieldOf("state").<cng>xmap(cng::new, cng -> cng.b).codec();
	public final cfj b;

	public cng(cfj cfj) {
		this.b = cfj;
	}
}
