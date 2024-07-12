import com.mojang.serialization.Codec;

public class ckl extends cml<coa> {
	public ckl(Codec<coa> codec) {
		super(codec);
	}

	@Override
	public cml.a<coa> a() {
		return ckl.a::new;
	}

	public static class a extends ctz<coa> {
		public a(cml<coa> cml, int integer2, int integer3, ctd ctd, int integer5, long long6) {
			super(cml, integer2, integer3, ctd, integer5, long6);
		}

		public void a(cha cha, cva cva, int integer3, int integer4, bre bre, coa coa) {
			ctf ctf8 = new ctf(this.d, integer3 * 16, integer4 * 16);
			this.b.add(ctf8);
			this.b();
		}
	}
}
