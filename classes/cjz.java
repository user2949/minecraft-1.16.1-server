import com.mojang.serialization.Codec;

public class cjz extends cml<cnh> {
	public cjz(Codec<cnh> codec) {
		super(codec);
	}

	protected boolean a(cha cha, brh brh, long long3, ciy ciy, int integer5, int integer6, bre bre, bph bph, cnh cnh) {
		ciy.a(long3, integer5, integer6, 10387320);
		return ciy.nextFloat() < cnh.b;
	}

	@Override
	public cml.a<cnh> a() {
		return cjz.a::new;
	}

	public static class a extends ctz<cnh> {
		public a(cml<cnh> cml, int integer2, int integer3, ctd ctd, int integer5, long long6) {
			super(cml, integer2, integer3, ctd, integer5, long6);
		}

		public void a(cha cha, cva cva, int integer3, int integer4, bre bre, cnh cnh) {
			int integer8 = integer3 * 16;
			int integer9 = integer4 * 16;
			fu fu10 = new fu(integer8 + 9, 90, integer9 + 9);
			this.b.add(new cte.a(fu10));
			this.b();
		}

		@Override
		public fu a() {
			return new fu((this.f() << 4) + 9, 0, (this.g() << 4) + 9);
		}
	}
}
