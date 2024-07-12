import com.mojang.serialization.Codec;

public class cjp extends cml<cnx> {
	public cjp(Codec<cnx> codec) {
		super(codec);
	}

	protected boolean a(cha cha, brh brh, long long3, ciy ciy, int integer5, int integer6, bre bre, bph bph, cnx cnx) {
		return ciy.nextInt(5) >= 2;
	}

	@Override
	public cml.a<cnx> a() {
		return cjp.a::new;
	}

	public static class a extends ctc<cnx> {
		public a(cml<cnx> cml, int integer2, int integer3, ctd ctd, int integer5, long long6) {
			super(cml, integer2, integer3, ctd, integer5, long6);
		}

		public void a(cha cha, cva cva, int integer3, int integer4, bre bre, cnx cnx) {
			fu fu8 = new fu(integer3 * 16, 33, integer4 * 16);
			cjs.a(cha, cva, fu8, this.b, this.d, cnx);
			this.b();
		}
	}
}
