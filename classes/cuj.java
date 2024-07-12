import com.mojang.serialization.Codec;
import java.util.Random;

public class cuj extends cuy {
	public static final Codec<cuj> a = gl.aj.fieldOf("block").<cuj>xmap(cuj::new, cuj -> cuj.b).codec();
	private final bvr b;

	public cuj(bvr bvr) {
		this.b = bvr;
	}

	@Override
	public boolean a(cfj cfj, Random random) {
		return cfj.a(this.b);
	}

	@Override
	protected cuz<?> a() {
		return cuz.b;
	}
}
