import com.mojang.serialization.Codec;
import java.util.Random;

public class cps extends cpo {
	public static final Codec<cps> b = cfj.b.fieldOf("state").xmap(cfi.a::b, bvr::n).<cps>xmap(cps::new, cps -> cps.c).codec();
	private final bvr c;

	public cps(bvr bvr) {
		this.c = bvr;
	}

	@Override
	protected cpp<?> a() {
		return cpp.e;
	}

	@Override
	public cfj a(Random random, fu fu) {
		fz.a a4 = fz.a.a(random);
		return this.c.n().a(cao.a, a4);
	}
}
