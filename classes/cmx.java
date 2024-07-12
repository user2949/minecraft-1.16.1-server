import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;

public class cmx extends cml<coa> {
	public cmx(Codec<coa> codec) {
		super(codec);
	}

	@Override
	protected boolean b() {
		return false;
	}

	protected boolean a(cha cha, brh brh, long long3, ciy ciy, int integer5, int integer6, bre bre, bph bph, coa coa) {
		for (bre bre14 : brh.a(integer5 * 16 + 9, cha.f(), integer6 * 16 + 9, 32)) {
			if (!bre14.a(this)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public cml.a<coa> a() {
		return cmx.a::new;
	}

	public static class a extends ctz<coa> {
		public a(cml<coa> cml, int integer2, int integer3, ctd ctd, int integer5, long long6) {
			super(cml, integer2, integer3, ctd, integer5, long6);
		}

		public void a(cha cha, cva cva, int integer3, int integer4, bre bre, coa coa) {
			cap cap8 = cap.a(this.d);
			int integer9 = 5;
			int integer10 = 5;
			if (cap8 == cap.CLOCKWISE_90) {
				integer9 = -5;
			} else if (cap8 == cap.CLOCKWISE_180) {
				integer9 = -5;
				integer10 = -5;
			} else if (cap8 == cap.COUNTERCLOCKWISE_90) {
				integer10 = -5;
			}

			int integer11 = (integer3 << 4) + 7;
			int integer12 = (integer4 << 4) + 7;
			int integer13 = cha.c(integer11, integer12, cio.a.WORLD_SURFACE_WG);
			int integer14 = cha.c(integer11, integer12 + integer10, cio.a.WORLD_SURFACE_WG);
			int integer15 = cha.c(integer11 + integer9, integer12, cio.a.WORLD_SURFACE_WG);
			int integer16 = cha.c(integer11 + integer9, integer12 + integer10, cio.a.WORLD_SURFACE_WG);
			int integer17 = Math.min(Math.min(integer13, integer14), Math.min(integer15, integer16));
			if (integer17 >= 60) {
				fu fu18 = new fu(integer3 * 16 + 8, integer17 + 1, integer4 * 16 + 8);
				List<cuc.i> list19 = Lists.<cuc.i>newLinkedList();
				cuc.a(cva, fu18, cap8, list19, this.d);
				this.b.addAll(list19);
				this.b();
			}
		}

		@Override
		public void a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph) {
			super.a(bqu, bqq, cha, random, ctd, bph);
			int integer8 = this.c.b;

			for (int integer9 = ctd.a; integer9 <= ctd.d; integer9++) {
				for (int integer10 = ctd.c; integer10 <= ctd.f; integer10++) {
					fu fu11 = new fu(integer9, integer8, integer10);
					if (!bqu.w(fu11) && this.c.b(fu11)) {
						boolean boolean12 = false;

						for (cty cty14 : this.b) {
							if (cty14.g().b(fu11)) {
								boolean12 = true;
								break;
							}
						}

						if (boolean12) {
							for (int integer13 = integer8 - 1; integer13 > 1; integer13--) {
								fu fu14 = new fu(integer9, integer13, integer10);
								if (!bqu.w(fu14) && !bqu.d_(fu14).c().a()) {
									break;
								}

								bqu.a(fu14, bvs.m.n(), 2);
							}
						}
					}
				}
			}
		}
	}
}
