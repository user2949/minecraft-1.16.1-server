import com.mojang.serialization.Codec;

public interface cqe<P extends cqd> {
	cqe<cqc> a = a("single_pool_element", cqc.b);
	cqe<cqb> b = a("list_pool_element", cqb.a);
	cqe<cpx> c = a("feature_pool_element", cpx.a);
	cqe<cpw> d = a("empty_pool_element", cpw.a);
	cqe<cqa> e = a("legacy_single_pool_element", cqa.a);

	Codec<P> codec();

	static <P extends cqd> cqe<P> a(String string, Codec<P> codec) {
		return gl.a(gl.aL, string, () -> codec);
	}
}
