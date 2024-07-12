import com.mojang.serialization.Codec;
import java.util.Random;

public class cul extends cuy {
	public static final Codec<cul> a = cfj.b.fieldOf("block_state").<cul>xmap(cul::new, cul -> cul.b).codec();
	private final cfj b;

	public cul(cfj cfj) {
		this.b = cfj;
	}

	@Override
	public boolean a(cfj cfj, Random random) {
		return cfj == this.b;
	}

	@Override
	protected cuz<?> a() {
		return cuz.c;
	}
}
