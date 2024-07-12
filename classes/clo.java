import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import cto.h;
import java.util.List;
import java.util.Random;

public class clo extends cml<coa> {
	private static final List<bre.g> u = Lists.<bre.g>newArrayList(new bre.g(aoq.F, 1, 2, 4));

	public clo(Codec<coa> codec) {
		super(codec);
	}

	@Override
	protected boolean b() {
		return false;
	}

	protected boolean a(cha cha, brh brh, long long3, ciy ciy, int integer5, int integer6, bre bre, bph bph, coa coa) {
		for (bre bre14 : brh.a(integer5 * 16 + 9, cha.f(), integer6 * 16 + 9, 16)) {
			if (!bre14.a(this)) {
				return false;
			}
		}

		for (bre bre15 : brh.a(integer5 * 16 + 9, cha.f(), integer6 * 16 + 9, 29)) {
			if (bre15.y() != bre.b.OCEAN && bre15.y() != bre.b.RIVER) {
				return false;
			}
		}

		return true;
	}

	@Override
	public cml.a<coa> a() {
		return clo.a::new;
	}

	@Override
	public List<bre.g> c() {
		return u;
	}

	public static class a extends ctz<coa> {
		private boolean e;

		public a(cml<coa> cml, int integer2, int integer3, ctd ctd, int integer5, long long6) {
			super(cml, integer2, integer3, ctd, integer5, long6);
		}

		public void a(cha cha, cva cva, int integer3, int integer4, bre bre, coa coa) {
			this.b(integer3, integer4);
		}

		private void b(int integer1, int integer2) {
			int integer4 = integer1 * 16 - 29;
			int integer5 = integer2 * 16 - 29;
			fz fz6 = fz.c.HORIZONTAL.a(this.d);
			this.b.add(new h(this.d, integer4, integer5, fz6));
			this.b();
			this.e = true;
		}

		@Override
		public void a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph) {
			if (!this.e) {
				this.b.clear();
				this.b(this.f(), this.g());
			}

			super.a(bqu, bqq, cha, random, ctd, bph);
		}
	}
}
