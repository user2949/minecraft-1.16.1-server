import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class cth {
	private static final uh a = new uh("igloo/top");
	private static final uh b = new uh("igloo/middle");
	private static final uh c = new uh("igloo/bottom");
	private static final Map<uh, fu> d = ImmutableMap.of(a, new fu(3, 5, 5), b, new fu(1, 3, 1), c, new fu(3, 6, 7));
	private static final Map<uh, fu> e = ImmutableMap.of(a, fu.b, b, new fu(2, -3, 4), c, new fu(0, -3, -2));

	public static void a(cva cva, fu fu, cap cap, List<cty> list, Random random) {
		if (random.nextDouble() < 0.5) {
			int integer6 = random.nextInt(8) + 4;
			list.add(new cth.a(cva, c, fu, cap, integer6 * 3));

			for (int integer7 = 0; integer7 < integer6 - 1; integer7++) {
				list.add(new cth.a(cva, b, fu, cap, integer7 * 3));
			}
		}

		list.add(new cth.a(cva, a, fu, cap, 0));
	}

	public static class a extends cub {
		private final uh d;
		private final cap e;

		public a(cva cva, uh uh, fu fu, cap cap, int integer) {
			super(cmm.K, 0);
			this.d = uh;
			fu fu7 = (fu)cth.e.get(uh);
			this.c = fu.b(fu7.u(), fu7.v() - integer, fu7.w());
			this.e = cap;
			this.a(cva);
		}

		public a(cva cva, le le) {
			super(cmm.K, le);
			this.d = new uh(le.l("Template"));
			this.e = cap.valueOf(le.l("Rot"));
			this.a(cva);
		}

		private void a(cva cva) {
			cve cve3 = cva.a(this.d);
			cvb cvb4 = new cvb().a(this.e).a(bzj.NONE).a((fu)cth.d.get(this.d)).a(cui.b);
			this.a(cve3, this.c, cvb4);
		}

		@Override
		protected void a(le le) {
			super.a(le);
			le.a("Template", this.d.toString());
			le.a("Rot", this.e.name());
		}

		@Override
		protected void a(String string, fu fu, bqc bqc, Random random, ctd ctd) {
			if ("chest".equals(string)) {
				bqc.a(fu, bvs.a.n(), 3);
				cdl cdl7 = bqc.c(fu.c());
				if (cdl7 instanceof cdp) {
					((cdp)cdl7).a(dao.C, random.nextLong());
				}
			}
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			cvb cvb9 = new cvb().a(this.e).a(bzj.NONE).a((fu)cth.d.get(this.d)).a(cui.b);
			fu fu10 = (fu)cth.e.get(this.d);
			fu fu11 = this.c.a(cve.a(cvb9, new fu(3 - fu10.u(), 0, 0 - fu10.w())));
			int integer12 = bqu.a(cio.a.WORLD_SURFACE_WG, fu11.u(), fu11.w());
			fu fu13 = this.c;
			this.c = this.c.b(0, integer12 - 90 - 1, 0);
			boolean boolean14 = super.a(bqu, bqq, cha, random, ctd, bph, fu);
			if (this.d.equals(cth.a)) {
				fu fu15 = this.c.a(cve.a(cvb9, new fu(3, 0, 5)));
				cfj cfj16 = bqu.d_(fu15.c());
				if (!cfj16.g() && !cfj16.a(bvs.cg)) {
					bqu.a(fu15, bvs.cE.n(), 3);
				}
			}

			this.c = fu13;
			return boolean14;
		}
	}
}
