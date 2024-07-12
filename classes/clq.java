import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.List;

public class clq extends cml<coa> {
	private static final List<bre.g> u = Lists.<bre.g>newArrayList(new bre.g(aoq.aj, 1, 1, 1));

	public clq(Codec<coa> codec) {
		super(codec);
	}

	@Override
	public List<bre.g> c() {
		return u;
	}

	protected boolean a(cha cha, brh brh, long long3, ciy ciy, int integer5, int integer6, bre bre, bph bph, coa coa) {
		int integer12 = integer5 >> 4;
		int integer13 = integer6 >> 4;
		ciy.setSeed((long)(integer12 ^ integer13 << 4) ^ long3);
		ciy.nextInt();
		if (ciy.nextInt(5) != 0) {
			return false;
		} else {
			for (int integer14 = integer5 - 10; integer14 <= integer5 + 10; integer14++) {
				for (int integer15 = integer6 - 10; integer15 <= integer6 + 10; integer15++) {
					bph bph16 = cml.q.a(cha.b().a(cml.q), long3, ciy, integer14, integer15);
					if (integer14 == bph16.b && integer15 == bph16.c) {
						return false;
					}
				}
			}

			return true;
		}
	}

	@Override
	public cml.a<coa> a() {
		return clq.a::new;
	}

	public static class a extends ctc<coa> {
		public a(cml<coa> cml, int integer2, int integer3, ctd ctd, int integer5, long long6) {
			super(cml, integer2, integer3, ctd, integer5, long6);
		}

		public void a(cha cha, cva cva, int integer3, int integer4, bre bre, coa coa) {
			fu fu8 = new fu(integer3 * 16, 0, integer4 * 16);
			ctr.a(cha, cva, fu8, this.b, this.d);
			this.b();
		}
	}
}
