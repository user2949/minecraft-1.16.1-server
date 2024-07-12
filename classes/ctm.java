import com.mojang.serialization.Codec;

public class ctm extends cml<coa> {
	public ctm(Codec<coa> codec) {
		super(codec);
	}

	@Override
	public cml.a<coa> a() {
		return ctm.a::new;
	}

	public static class a extends ctc<coa> {
		public a(cml<coa> cml, int integer2, int integer3, ctd ctd, int integer5, long long6) {
			super(cml, integer2, integer3, ctd, integer5, long6);
		}

		public void a(cha cha, cva cva, int integer3, int integer4, bre bre, coa coa) {
			bph bph8 = new bph(integer3, integer4);
			int integer9 = bph8.d() + this.d.nextInt(16);
			int integer10 = bph8.e() + this.d.nextInt(16);
			int integer11 = cha.f();
			int integer12 = integer11 + this.d.nextInt(cha.e() - 2 - integer11);
			bpg bpg13 = cha.a(integer9, integer10);

			for (fu.a a14 = new fu.a(integer9, integer12, integer10); integer12 > integer11; integer12--) {
				cfj cfj15 = bpg13.d_(a14);
				a14.c(fz.DOWN);
				cfj cfj16 = bpg13.d_(a14);
				if (cfj15.g() && (cfj16.a(bvs.cM) || cfj16.d(bpg13, a14, fz.UP))) {
					break;
				}
			}

			if (integer12 > integer11) {
				ctn.a(cva, this.b, this.d, new fu(integer9, integer12, integer10));
				this.b();
			}
		}
	}
}
