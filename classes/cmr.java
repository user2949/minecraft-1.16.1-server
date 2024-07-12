import com.mojang.serialization.Codec;

public class cmr extends cml<cnu> {
	public cmr(Codec<cnu> codec) {
		super(codec);
	}

	@Override
	public cml.a<cnu> a() {
		return cmr.a::new;
	}

	public static class a extends ctc<cnu> {
		public a(cml<cnu> cml, int integer2, int integer3, ctd ctd, int integer5, long long6) {
			super(cml, integer2, integer3, ctd, integer5, long6);
		}

		public void a(cha cha, cva cva, int integer3, int integer4, bre bre, cnu cnu) {
			fu fu8 = new fu(integer3 * 16, 0, integer4 * 16);
			cms.a(cha, cva, fu8, this.b, this.d, cnu);
			this.b();
		}
	}
}
