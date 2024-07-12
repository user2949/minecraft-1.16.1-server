import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;

public class he implements hf {
	public static final hf.a<he> a = new hf.a<he>() {
		public he b(hg<he> hg, StringReader stringReader) throws CommandSyntaxException {
			stringReader.expect(' ');
			ev ev4 = new ev(stringReader, false).h();
			bki bki5 = new eu(ev4.b(), ev4.c()).a(1, false);
			return new he(hg, bki5);
		}

		public he b(hg<he> hg, mg mg) {
			return new he(hg, mg.m());
		}
	};
	private final hg<he> b;
	private final bki c;

	public static Codec<he> a(hg<he> hg) {
		return bki.a.xmap(bki -> new he(hg, bki), he -> he.c);
	}

	public he(hg<he> hg, bki bki) {
		this.b = hg;
		this.c = bki;
	}

	@Override
	public void a(mg mg) {
		mg.a(this.c);
	}

	@Override
	public String a() {
		return gl.az.b(this.b()) + " " + new eu(this.c.b(), this.c.o()).c();
	}

	@Override
	public hg<he> b() {
		return this.b;
	}
}
