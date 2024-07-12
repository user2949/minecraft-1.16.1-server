import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;

public class hc implements hf {
	public static final hf.a<hc> a = new hf.a<hc>() {
		public hc b(hg<hc> hg, StringReader stringReader) throws CommandSyntaxException {
			stringReader.expect(' ');
			return new hc(hg, new ef(stringReader, false).a(false).b());
		}

		public hc b(hg<hc> hg, mg mg) {
			return new hc(hg, bvr.m.a(mg.i()));
		}
	};
	private final hg<hc> b;
	private final cfj c;

	public static Codec<hc> a(hg<hc> hg) {
		return cfj.b.xmap(cfj -> new hc(hg, cfj), hc -> hc.c);
	}

	public hc(hg<hc> hg, cfj cfj) {
		this.b = hg;
		this.c = cfj;
	}

	@Override
	public void a(mg mg) {
		mg.d(bvr.m.a(this.c));
	}

	@Override
	public String a() {
		return gl.az.b(this.b()) + " " + ef.a(this.c);
	}

	@Override
	public hg<hc> b() {
		return this.b;
	}
}
