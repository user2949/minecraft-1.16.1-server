import com.mojang.serialization.Codec;
import java.util.Random;

public class cpt extends cpo {
	public static final Codec<cpt> b = cfj.b.fieldOf("state").<cpt>xmap(cpt::new, cpt -> cpt.c).codec();
	private final cfj c;

	public cpt(cfj cfj) {
		this.c = cfj;
	}

	@Override
	protected cpp<?> a() {
		return cpp.a;
	}

	@Override
	public cfj a(Random random, fu fu) {
		return this.c;
	}
}
