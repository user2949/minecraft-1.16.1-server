import java.util.Random;

public class cte {
	public static class a extends cty {
		public a(fu fu) {
			super(cmm.ac, 0);
			this.n = new ctd(fu.u(), fu.v(), fu.w(), fu.u(), fu.v(), fu.w());
		}

		public a(cva cva, le le) {
			super(cmm.ac, le);
		}

		@Override
		protected void a(le le) {
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			int integer9 = bqu.a(cio.a.OCEAN_FLOOR_WG, this.n.a, this.n.c);
			fu.a a10 = new fu.a(this.n.a, integer9, this.n.c);

			while (a10.v() > 0) {
				cfj cfj11 = bqu.d_(a10);
				cfj cfj12 = bqu.d_(a10.c());
				if (cfj12 == bvs.at.n() || cfj12 == bvs.b.n() || cfj12 == bvs.g.n() || cfj12 == bvs.c.n() || cfj12 == bvs.e.n()) {
					cfj cfj13 = !cfj11.g() && !this.a(cfj11) ? cfj11 : bvs.C.n();

					for (fz fz17 : fz.values()) {
						fu fu18 = a10.a(fz17);
						cfj cfj19 = bqu.d_(fu18);
						if (cfj19.g() || this.a(cfj19)) {
							fu fu20 = fu18.c();
							cfj cfj21 = bqu.d_(fu20);
							if ((cfj21.g() || this.a(cfj21)) && fz17 != fz.UP) {
								bqu.a(fu18, cfj12, 3);
							} else {
								bqu.a(fu18, cfj13, 3);
							}
						}
					}

					this.n = new ctd(a10.u(), a10.v(), a10.w(), a10.u(), a10.v(), a10.w());
					return this.a(bqu, ctd, random, a10, dao.G, null);
				}

				a10.e(0, -1, 0);
			}

			return false;
		}

		private boolean a(cfj cfj) {
			return cfj == bvs.A.n() || cfj == bvs.B.n();
		}
	}
}
