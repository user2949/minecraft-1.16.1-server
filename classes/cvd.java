import com.mojang.serialization.Codec;

public interface cvd<P extends cvc> {
	cvd<cui> a = a("block_ignore", cui.a);
	cvd<cuk> b = a("block_rot", cuk.a);
	cvd<cum> c = a("gravity", cum.a);
	cvd<cun> d = a("jigsaw_replacement", cun.a);
	cvd<cux> e = a("rule", cux.a);
	cvd<cuq> f = a("nop", cuq.a);
	cvd<cuh> g = a("block_age", cuh.a);
	cvd<cug> h = a("blackstone_replace", cug.a);
	cvd<cuo> i = a("lava_submerged_block", cuo.a);
	Codec<cvc> j = gl.aK.dispatch("processor_type", cvc::a, cvd::codec);

	Codec<P> codec();

	static <P extends cvc> cvd<P> a(String string, Codec<P> codec) {
		return gl.a(gl.aK, string, () -> codec);
	}
}
