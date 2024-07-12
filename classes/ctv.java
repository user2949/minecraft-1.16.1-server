import java.util.List;
import java.util.Random;

public class ctv {
	private static final fu a = new fu(4, 0, 15);
	private static final uh[] b = new uh[]{
		new uh("shipwreck/with_mast"),
		new uh("shipwreck/sideways_full"),
		new uh("shipwreck/sideways_fronthalf"),
		new uh("shipwreck/sideways_backhalf"),
		new uh("shipwreck/rightsideup_full"),
		new uh("shipwreck/rightsideup_fronthalf"),
		new uh("shipwreck/rightsideup_backhalf"),
		new uh("shipwreck/with_mast_degraded"),
		new uh("shipwreck/rightsideup_full_degraded"),
		new uh("shipwreck/rightsideup_fronthalf_degraded"),
		new uh("shipwreck/rightsideup_backhalf_degraded")
	};
	private static final uh[] c = new uh[]{
		new uh("shipwreck/with_mast"),
		new uh("shipwreck/upsidedown_full"),
		new uh("shipwreck/upsidedown_fronthalf"),
		new uh("shipwreck/upsidedown_backhalf"),
		new uh("shipwreck/sideways_full"),
		new uh("shipwreck/sideways_fronthalf"),
		new uh("shipwreck/sideways_backhalf"),
		new uh("shipwreck/rightsideup_full"),
		new uh("shipwreck/rightsideup_fronthalf"),
		new uh("shipwreck/rightsideup_backhalf"),
		new uh("shipwreck/with_mast_degraded"),
		new uh("shipwreck/upsidedown_full_degraded"),
		new uh("shipwreck/upsidedown_fronthalf_degraded"),
		new uh("shipwreck/upsidedown_backhalf_degraded"),
		new uh("shipwreck/sideways_full_degraded"),
		new uh("shipwreck/sideways_fronthalf_degraded"),
		new uh("shipwreck/sideways_backhalf_degraded"),
		new uh("shipwreck/rightsideup_full_degraded"),
		new uh("shipwreck/rightsideup_fronthalf_degraded"),
		new uh("shipwreck/rightsideup_backhalf_degraded")
	};

	public static void a(cva cva, fu fu, cap cap, List<cty> list, Random random, com com) {
		uh uh7 = v.a(com.b ? b : c, random);
		list.add(new ctv.a(cva, uh7, fu, cap, com.b));
	}

	public static class a extends cub {
		private final cap d;
		private final uh e;
		private final boolean f;

		public a(cva cva, uh uh, fu fu, cap cap, boolean boolean5) {
			super(cmm.ad, 0);
			this.c = fu;
			this.d = cap;
			this.e = uh;
			this.f = boolean5;
			this.a(cva);
		}

		public a(cva cva, le le) {
			super(cmm.ad, le);
			this.e = new uh(le.l("Template"));
			this.f = le.q("isBeached");
			this.d = cap.valueOf(le.l("Rot"));
			this.a(cva);
		}

		@Override
		protected void a(le le) {
			super.a(le);
			le.a("Template", this.e.toString());
			le.a("isBeached", this.f);
			le.a("Rot", this.d.name());
		}

		private void a(cva cva) {
			cve cve3 = cva.a(this.e);
			cvb cvb4 = new cvb().a(this.d).a(bzj.NONE).a(ctv.a).a(cui.d);
			this.a(cve3, this.c, cvb4);
		}

		@Override
		protected void a(String string, fu fu, bqc bqc, Random random, ctd ctd) {
			if ("map_chest".equals(string)) {
				cef.a(bqc, random, fu.c(), dao.H);
			} else if ("treasure_chest".equals(string)) {
				cef.a(bqc, random, fu.c(), dao.J);
			} else if ("supply_chest".equals(string)) {
				cef.a(bqc, random, fu.c(), dao.I);
			}
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			int integer9 = 256;
			int integer10 = 0;
			fu fu11 = this.a.a();
			cio.a a12 = this.f ? cio.a.WORLD_SURFACE_WG : cio.a.OCEAN_FLOOR_WG;
			int integer13 = fu11.u() * fu11.w();
			if (integer13 == 0) {
				integer10 = bqu.a(a12, this.c.u(), this.c.w());
			} else {
				fu fu14 = this.c.b(fu11.u() - 1, 0, fu11.w() - 1);

				for (fu fu16 : fu.a(this.c, fu14)) {
					int integer17 = bqu.a(a12, fu16.u(), fu16.w());
					integer10 += integer17;
					integer9 = Math.min(integer9, integer17);
				}

				integer10 /= integer13;
			}

			int integer14 = this.f ? integer9 - fu11.v() / 2 - random.nextInt(3) : integer10;
			this.c = new fu(this.c.u(), integer14, this.c.w());
			return super.a(bqu, bqq, cha, random, ctd, bph, fu);
		}
	}
}
