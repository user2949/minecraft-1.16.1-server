import com.mojang.serialization.Codec;
import java.util.List;

public class cmk extends cml<coa> {
	public cmk(Codec<coa> codec) {
		super(codec);
	}

	@Override
	public cml.a<coa> a() {
		return cmk.a::new;
	}

	protected boolean a(cha cha, brh brh, long long3, ciy ciy, int integer5, int integer6, bre bre, bph bph, coa coa) {
		return cha.a(new bph(integer5, integer6));
	}

	public static class a extends ctz<coa> {
		private final long e;

		public a(cml<coa> cml, int integer2, int integer3, ctd ctd, int integer5, long long6) {
			super(cml, integer2, integer3, ctd, integer5, long6);
			this.e = long6;
		}

		public void a(cha cha, cva cva, int integer3, int integer4, bre bre, coa coa) {
			int integer8 = 0;

			ctw.m m9;
			do {
				this.b.clear();
				this.c = ctd.a();
				this.d.c(this.e + (long)(integer8++), integer3, integer4);
				ctw.a();
				m9 = new ctw.m(this.d, (integer3 << 4) + 2, (integer4 << 4) + 2);
				this.b.add(m9);
				m9.a(m9, this.b, this.d);
				List<cty> list10 = m9.c;

				while (!list10.isEmpty()) {
					int integer11 = this.d.nextInt(list10.size());
					cty cty12 = (cty)list10.remove(integer11);
					cty12.a(m9, this.b, this.d);
				}

				this.b();
				this.a(cha.f(), this.d, 10);
			} while (this.b.isEmpty() || m9.b == null);
		}
	}
}
