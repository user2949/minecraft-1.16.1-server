import com.mojang.serialization.Codec;

public class cnf implements cnr {
	public static final Codec<cnf> a = cpo.a.fieldOf("state_provider").<cnf>xmap(cnf::new, cnf -> cnf.b).codec();
	public final cpo b;

	public cnf(cpo cpo) {
		this.b = cpo;
	}
}
