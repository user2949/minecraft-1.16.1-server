import com.mojang.serialization.Codec;

public class cle extends cml<coa> {
	public cle(Codec<coa> codec) {
		super(codec);
	}

	@Override
	public cml.a<coa> a() {
		return cle.a::new;
	}

	public static class a extends ctz<coa> {
		public a(cml<coa> cml, int integer2, int integer3, ctd ctd, int integer5, long long6) {
			super(cml, integer2, integer3, ctd, integer5, long6);
		}

		public void a(cha cha, cva cva, int integer3, int integer4, bre bre, coa coa) {
			int integer8 = integer3 * 16;
			int integer9 = integer4 * 16;
			fu fu10 = new fu(integer8, 90, integer9);
			cap cap11 = cap.a(this.d);
			cth.a(cva, fu10, cap11, this.b, this.d);
			this.b();
		}
	}
}
