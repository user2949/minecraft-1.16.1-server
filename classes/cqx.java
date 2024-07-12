import com.mojang.serialization.Codec;

public class cqx<P extends cqw> {
	public static final cqx<cqv> a = a("straight_trunk_placer", cqv.a);
	public static final cqx<cqs> b = a("forking_trunk_placer", cqs.a);
	public static final cqx<cqt> c = a("giant_trunk_placer", cqt.a);
	public static final cqx<cqu> d = a("mega_jungle_trunk_placer", cqu.b);
	public static final cqx<cqq> e = a("dark_oak_trunk_placer", cqq.a);
	public static final cqx<cqr> f = a("fancy_trunk_placer", cqr.a);
	private final Codec<P> g;

	private static <P extends cqw> cqx<P> a(String string, Codec<P> codec) {
		return gl.a(gl.aw, string, new cqx<>(codec));
	}

	private cqx(Codec<P> codec) {
		this.g = codec;
	}

	public Codec<P> a() {
		return this.g;
	}
}
