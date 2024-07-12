import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.List;

public class cll extends cml<coa> {
	private static final List<bre.g> u = Lists.<bre.g>newArrayList(
		new bre.g(aoq.f, 10, 2, 3), new bre.g(aoq.ba, 5, 4, 4), new bre.g(aoq.aT, 8, 5, 5), new bre.g(aoq.au, 2, 5, 5), new bre.g(aoq.S, 3, 4, 4)
	);

	public cll(Codec<coa> codec) {
		super(codec);
	}

	protected boolean a(cha cha, brh brh, long long3, ciy ciy, int integer5, int integer6, bre bre, bph bph, coa coa) {
		return ciy.nextInt(5) < 2;
	}

	@Override
	public cml.a<coa> a() {
		return cll.a::new;
	}

	@Override
	public List<bre.g> c() {
		return u;
	}

	public static class a extends ctz<coa> {
		public a(cml<coa> cml, int integer2, int integer3, ctd ctd, int integer5, long long6) {
			super(cml, integer2, integer3, ctd, integer5, long6);
		}

		public void a(cha cha, cva cva, int integer3, int integer4, bre bre, coa coa) {
			ctl.q q8 = new ctl.q(this.d, (integer3 << 4) + 2, (integer4 << 4) + 2);
			this.b.add(q8);
			q8.a(q8, this.b, this.d);
			List<cty> list9 = q8.d;

			while (!list9.isEmpty()) {
				int integer10 = this.d.nextInt(list9.size());
				cty cty11 = (cty)list9.remove(integer10);
				cty11.a(q8, this.b, this.d);
			}

			this.b();
			this.a(this.d, 48, 70);
		}
	}
}
