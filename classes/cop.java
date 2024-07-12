import com.mojang.serialization.Codec;
import java.util.List;

public class cop implements cnr {
	public static final Codec<cop> a = ckb.b.listOf().fieldOf("features").<cop>xmap(cop::new, cop -> cop.b).codec();
	public final List<ckb<?, ?>> b;

	public cop(List<ckb<?, ?>> list) {
		this.b = list;
	}
}
