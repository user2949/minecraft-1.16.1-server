import com.mojang.serialization.Codec;

public interface cuz<P extends cuy> {
	cuz<cue> a = a("always_true", cue.a);
	cuz<cuj> b = a("block_match", cuj.a);
	cuz<cul> c = a("blockstate_match", cul.a);
	cuz<cvf> d = a("tag_match", cvf.a);
	cuz<cuv> e = a("random_block_match", cuv.a);
	cuz<cuw> f = a("random_blockstate_match", cuw.a);

	Codec<P> codec();

	static <P extends cuy> cuz<P> a(String string, Codec<P> codec) {
		return gl.a(gl.aI, string, () -> codec);
	}
}
