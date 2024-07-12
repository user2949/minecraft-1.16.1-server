import com.mojang.serialization.Codec;

public class cmc extends cml<com> {
	public cmc(Codec<com> codec) {
		super(codec);
	}

	@Override
	public cml.a<com> a() {
		return cmc.a::new;
	}

	public static class a extends ctz<com> {
		public a(cml<com> cml, int integer2, int integer3, ctd ctd, int integer5, long long6) {
			super(cml, integer2, integer3, ctd, integer5, long6);
		}

		public void a(cha cha, cva cva, int integer3, int integer4, bre bre, com com) {
			cap cap8 = cap.a(this.d);
			fu fu9 = new fu(integer3 * 16, 90, integer4 * 16);
			ctv.a(cva, fu9, cap8, this.b, this.d, com);
			this.b();
		}
	}
}
