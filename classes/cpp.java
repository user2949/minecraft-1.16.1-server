import com.mojang.serialization.Codec;

public class cpp<P extends cpo> {
	public static final cpp<cpt> a = a("simple_state_provider", cpt.b);
	public static final cpp<cpu> b = a("weighted_state_provider", cpu.b);
	public static final cpp<cpr> c = a("plain_flower_provider", cpr.b);
	public static final cpp<cpq> d = a("forest_flower_provider", cpq.b);
	public static final cpp<cps> e = a("rotated_block_provider", cps.b);
	private final Codec<P> f;

	private static <P extends cpo> cpp<P> a(String string, Codec<P> codec) {
		return gl.a(gl.at, string, new cpp<>(codec));
	}

	private cpp(Codec<P> codec) {
		this.f = codec;
	}

	public Codec<P> a() {
		return this.f;
	}
}
