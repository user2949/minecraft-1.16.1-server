import com.mojang.serialization.Codec;

public class cqn<P extends cqm> {
	public static final cqn<cqo> a = a("trunk_vine", cqo.a);
	public static final cqn<cql> b = a("leave_vine", cql.a);
	public static final cqn<cqk> c = a("cocoa", cqk.a);
	public static final cqn<cqj> d = a("beehive", cqj.a);
	public static final cqn<cqi> e = a("alter_ground", cqi.a);
	private final Codec<P> f;

	private static <P extends cqm> cqn<P> a(String string, Codec<P> codec) {
		return gl.a(gl.ax, string, new cqn<>(codec));
	}

	private cqn(Codec<P> codec) {
		this.f = codec;
	}

	public Codec<P> a() {
		return this.f;
	}
}
