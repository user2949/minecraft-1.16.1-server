import com.mojang.serialization.Codec;

public class clf extends cml<coa> {
	public clf(Codec<coa> codec) {
		super(codec);
	}

	@Override
	public cml.a<coa> a() {
		return clf.a::new;
	}

	public static class a extends ctz<coa> {
		public a(cml<coa> cml, int integer2, int integer3, ctd ctd, int integer5, long long6) {
			super(cml, integer2, integer3, ctd, integer5, long6);
		}

		public void a(cha cha, cva cva, int integer3, int integer4, bre bre, coa coa) {
			cti cti8 = new cti(this.d, integer3 * 16, integer4 * 16);
			this.b.add(cti8);
			this.b();
		}
	}
}
