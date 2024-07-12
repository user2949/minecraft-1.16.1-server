import java.util.Random;

public class cti extends ctu {
	private boolean e;
	private boolean f;
	private boolean g;
	private boolean h;
	private static final cti.a i = new cti.a();

	public cti(Random random, int integer2, int integer3) {
		super(cmm.I, random, integer2, 64, integer3, 12, 10, 15);
	}

	public cti(cva cva, le le) {
		super(cmm.I, le);
		this.e = le.q("placedMainChest");
		this.f = le.q("placedHiddenChest");
		this.g = le.q("placedTrap1");
		this.h = le.q("placedTrap2");
	}

	@Override
	protected void a(le le) {
		super.a(le);
		le.a("placedMainChest", this.e);
		le.a("placedHiddenChest", this.f);
		le.a("placedTrap1", this.g);
		le.a("placedTrap2", this.h);
	}

	@Override
	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
		if (!this.a(bqu, ctd, 0)) {
			return false;
		} else {
			this.a(bqu, ctd, 0, -4, 0, this.a - 1, 0, this.c - 1, false, random, i);
			this.a(bqu, ctd, 2, 1, 2, 9, 2, 2, false, random, i);
			this.a(bqu, ctd, 2, 1, 12, 9, 2, 12, false, random, i);
			this.a(bqu, ctd, 2, 1, 3, 2, 2, 11, false, random, i);
			this.a(bqu, ctd, 9, 1, 3, 9, 2, 11, false, random, i);
			this.a(bqu, ctd, 1, 3, 1, 10, 6, 1, false, random, i);
			this.a(bqu, ctd, 1, 3, 13, 10, 6, 13, false, random, i);
			this.a(bqu, ctd, 1, 3, 2, 1, 6, 12, false, random, i);
			this.a(bqu, ctd, 10, 3, 2, 10, 6, 12, false, random, i);
			this.a(bqu, ctd, 2, 3, 2, 9, 3, 12, false, random, i);
			this.a(bqu, ctd, 2, 6, 2, 9, 6, 12, false, random, i);
			this.a(bqu, ctd, 3, 7, 3, 8, 7, 11, false, random, i);
			this.a(bqu, ctd, 4, 8, 4, 7, 8, 10, false, random, i);
			this.b(bqu, ctd, 3, 1, 3, 8, 2, 11);
			this.b(bqu, ctd, 4, 3, 6, 7, 3, 9);
			this.b(bqu, ctd, 2, 4, 2, 9, 5, 12);
			this.b(bqu, ctd, 4, 6, 5, 7, 6, 9);
			this.b(bqu, ctd, 5, 7, 6, 6, 7, 8);
			this.b(bqu, ctd, 5, 1, 2, 6, 2, 2);
			this.b(bqu, ctd, 5, 2, 12, 6, 2, 12);
			this.b(bqu, ctd, 5, 5, 1, 6, 5, 1);
			this.b(bqu, ctd, 5, 5, 13, 6, 5, 13);
			this.a(bqu, bvs.a.n(), 1, 5, 5, ctd);
			this.a(bqu, bvs.a.n(), 10, 5, 5, ctd);
			this.a(bqu, bvs.a.n(), 1, 5, 9, ctd);
			this.a(bqu, bvs.a.n(), 10, 5, 9, ctd);

			for (int integer9 = 0; integer9 <= 14; integer9 += 14) {
				this.a(bqu, ctd, 2, 4, integer9, 2, 5, integer9, false, random, i);
				this.a(bqu, ctd, 4, 4, integer9, 4, 5, integer9, false, random, i);
				this.a(bqu, ctd, 7, 4, integer9, 7, 5, integer9, false, random, i);
				this.a(bqu, ctd, 9, 4, integer9, 9, 5, integer9, false, random, i);
			}

			this.a(bqu, ctd, 5, 6, 0, 6, 6, 0, false, random, i);

			for (int integer9 = 0; integer9 <= 11; integer9 += 11) {
				for (int integer10 = 2; integer10 <= 12; integer10 += 2) {
					this.a(bqu, ctd, integer9, 4, integer10, integer9, 5, integer10, false, random, i);
				}

				this.a(bqu, ctd, integer9, 6, 5, integer9, 6, 5, false, random, i);
				this.a(bqu, ctd, integer9, 6, 9, integer9, 6, 9, false, random, i);
			}

			this.a(bqu, ctd, 2, 7, 2, 2, 9, 2, false, random, i);
			this.a(bqu, ctd, 9, 7, 2, 9, 9, 2, false, random, i);
			this.a(bqu, ctd, 2, 7, 12, 2, 9, 12, false, random, i);
			this.a(bqu, ctd, 9, 7, 12, 9, 9, 12, false, random, i);
			this.a(bqu, ctd, 4, 9, 4, 4, 9, 4, false, random, i);
			this.a(bqu, ctd, 7, 9, 4, 7, 9, 4, false, random, i);
			this.a(bqu, ctd, 4, 9, 10, 4, 9, 10, false, random, i);
			this.a(bqu, ctd, 7, 9, 10, 7, 9, 10, false, random, i);
			this.a(bqu, ctd, 5, 9, 7, 6, 9, 7, false, random, i);
			cfj cfj9 = bvs.ci.n().a(cbn.a, fz.EAST);
			cfj cfj10 = bvs.ci.n().a(cbn.a, fz.WEST);
			cfj cfj11 = bvs.ci.n().a(cbn.a, fz.SOUTH);
			cfj cfj12 = bvs.ci.n().a(cbn.a, fz.NORTH);
			this.a(bqu, cfj12, 5, 9, 6, ctd);
			this.a(bqu, cfj12, 6, 9, 6, ctd);
			this.a(bqu, cfj11, 5, 9, 8, ctd);
			this.a(bqu, cfj11, 6, 9, 8, ctd);
			this.a(bqu, cfj12, 4, 0, 0, ctd);
			this.a(bqu, cfj12, 5, 0, 0, ctd);
			this.a(bqu, cfj12, 6, 0, 0, ctd);
			this.a(bqu, cfj12, 7, 0, 0, ctd);
			this.a(bqu, cfj12, 4, 1, 8, ctd);
			this.a(bqu, cfj12, 4, 2, 9, ctd);
			this.a(bqu, cfj12, 4, 3, 10, ctd);
			this.a(bqu, cfj12, 7, 1, 8, ctd);
			this.a(bqu, cfj12, 7, 2, 9, ctd);
			this.a(bqu, cfj12, 7, 3, 10, ctd);
			this.a(bqu, ctd, 4, 1, 9, 4, 1, 9, false, random, i);
			this.a(bqu, ctd, 7, 1, 9, 7, 1, 9, false, random, i);
			this.a(bqu, ctd, 4, 1, 10, 7, 2, 10, false, random, i);
			this.a(bqu, ctd, 5, 4, 5, 6, 4, 5, false, random, i);
			this.a(bqu, cfj9, 4, 4, 5, ctd);
			this.a(bqu, cfj10, 7, 4, 5, ctd);

			for (int integer13 = 0; integer13 < 4; integer13++) {
				this.a(bqu, cfj11, 5, 0 - integer13, 6 + integer13, ctd);
				this.a(bqu, cfj11, 6, 0 - integer13, 6 + integer13, ctd);
				this.b(bqu, ctd, 5, 0 - integer13, 7 + integer13, 6, 0 - integer13, 9 + integer13);
			}

			this.b(bqu, ctd, 1, -3, 12, 10, -1, 13);
			this.b(bqu, ctd, 1, -3, 1, 3, -1, 13);
			this.b(bqu, ctd, 1, -3, 1, 9, -1, 5);

			for (int integer13 = 1; integer13 <= 13; integer13 += 2) {
				this.a(bqu, ctd, 1, -3, integer13, 1, -2, integer13, false, random, i);
			}

			for (int integer13 = 2; integer13 <= 12; integer13 += 2) {
				this.a(bqu, ctd, 1, -1, integer13, 3, -1, integer13, false, random, i);
			}

			this.a(bqu, ctd, 2, -2, 1, 5, -2, 1, false, random, i);
			this.a(bqu, ctd, 7, -2, 1, 9, -2, 1, false, random, i);
			this.a(bqu, ctd, 6, -3, 1, 6, -3, 1, false, random, i);
			this.a(bqu, ctd, 6, -1, 1, 6, -1, 1, false, random, i);
			this.a(bqu, bvs.el.n().a(ccg.a, fz.EAST).a(ccg.c, Boolean.valueOf(true)), 1, -3, 8, ctd);
			this.a(bqu, bvs.el.n().a(ccg.a, fz.WEST).a(ccg.c, Boolean.valueOf(true)), 4, -3, 8, ctd);
			this.a(bqu, bvs.em.n().a(ccf.e, Boolean.valueOf(true)).a(ccf.g, Boolean.valueOf(true)).a(ccf.b, Boolean.valueOf(true)), 2, -3, 8, ctd);
			this.a(bqu, bvs.em.n().a(ccf.e, Boolean.valueOf(true)).a(ccf.g, Boolean.valueOf(true)).a(ccf.b, Boolean.valueOf(true)), 3, -3, 8, ctd);
			cfj cfj13 = bvs.bS.n().a(cag.a, cgn.SIDE).a(cag.c, cgn.SIDE);
			this.a(bqu, cfj13, 5, -3, 7, ctd);
			this.a(bqu, cfj13, 5, -3, 6, ctd);
			this.a(bqu, cfj13, 5, -3, 5, ctd);
			this.a(bqu, cfj13, 5, -3, 4, ctd);
			this.a(bqu, cfj13, 5, -3, 3, ctd);
			this.a(bqu, cfj13, 5, -3, 2, ctd);
			this.a(bqu, bvs.bS.n().a(cag.a, cgn.SIDE).a(cag.d, cgn.SIDE), 5, -3, 1, ctd);
			this.a(bqu, bvs.bS.n().a(cag.b, cgn.SIDE).a(cag.d, cgn.SIDE), 4, -3, 1, ctd);
			this.a(bqu, bvs.bJ.n(), 3, -3, 1, ctd);
			if (!this.g) {
				this.g = this.a(bqu, ctd, random, 3, -2, 1, fz.NORTH, dao.B);
			}

			this.a(bqu, bvs.dP.n().a(cck.d, Boolean.valueOf(true)), 3, -2, 2, ctd);
			this.a(bqu, bvs.el.n().a(ccg.a, fz.NORTH).a(ccg.c, Boolean.valueOf(true)), 7, -3, 1, ctd);
			this.a(bqu, bvs.el.n().a(ccg.a, fz.SOUTH).a(ccg.c, Boolean.valueOf(true)), 7, -3, 5, ctd);
			this.a(bqu, bvs.em.n().a(ccf.d, Boolean.valueOf(true)).a(ccf.f, Boolean.valueOf(true)).a(ccf.b, Boolean.valueOf(true)), 7, -3, 2, ctd);
			this.a(bqu, bvs.em.n().a(ccf.d, Boolean.valueOf(true)).a(ccf.f, Boolean.valueOf(true)).a(ccf.b, Boolean.valueOf(true)), 7, -3, 3, ctd);
			this.a(bqu, bvs.em.n().a(ccf.d, Boolean.valueOf(true)).a(ccf.f, Boolean.valueOf(true)).a(ccf.b, Boolean.valueOf(true)), 7, -3, 4, ctd);
			this.a(bqu, bvs.bS.n().a(cag.b, cgn.SIDE).a(cag.d, cgn.SIDE), 8, -3, 6, ctd);
			this.a(bqu, bvs.bS.n().a(cag.d, cgn.SIDE).a(cag.c, cgn.SIDE), 9, -3, 6, ctd);
			this.a(bqu, bvs.bS.n().a(cag.a, cgn.SIDE).a(cag.c, cgn.UP), 9, -3, 5, ctd);
			this.a(bqu, bvs.bJ.n(), 9, -3, 4, ctd);
			this.a(bqu, cfj13, 9, -2, 4, ctd);
			if (!this.h) {
				this.h = this.a(bqu, ctd, random, 9, -2, 3, fz.WEST, dao.B);
			}

			this.a(bqu, bvs.dP.n().a(cck.c, Boolean.valueOf(true)), 8, -1, 3, ctd);
			this.a(bqu, bvs.dP.n().a(cck.c, Boolean.valueOf(true)), 8, -2, 3, ctd);
			if (!this.e) {
				this.e = this.a(bqu, ctd, random, 8, -3, 3, dao.A);
			}

			this.a(bqu, bvs.bJ.n(), 9, -3, 2, ctd);
			this.a(bqu, bvs.bJ.n(), 8, -3, 1, ctd);
			this.a(bqu, bvs.bJ.n(), 4, -3, 5, ctd);
			this.a(bqu, bvs.bJ.n(), 5, -2, 5, ctd);
			this.a(bqu, bvs.bJ.n(), 5, -1, 5, ctd);
			this.a(bqu, bvs.bJ.n(), 6, -3, 5, ctd);
			this.a(bqu, bvs.bJ.n(), 7, -2, 5, ctd);
			this.a(bqu, bvs.bJ.n(), 7, -1, 5, ctd);
			this.a(bqu, bvs.bJ.n(), 8, -3, 5, ctd);
			this.a(bqu, ctd, 9, -1, 1, 9, -1, 5, false, random, i);
			this.b(bqu, ctd, 8, -3, 8, 10, -1, 10);
			this.a(bqu, bvs.dx.n(), 8, -2, 11, ctd);
			this.a(bqu, bvs.dx.n(), 9, -2, 11, ctd);
			this.a(bqu, bvs.dx.n(), 10, -2, 11, ctd);
			cfj cfj14 = bvs.cp.n().a(bzd.aq, fz.NORTH).a(bzd.u, cfv.WALL);
			this.a(bqu, cfj14, 8, -2, 12, ctd);
			this.a(bqu, cfj14, 9, -2, 12, ctd);
			this.a(bqu, cfj14, 10, -2, 12, ctd);
			this.a(bqu, ctd, 8, -3, 8, 8, -3, 10, false, random, i);
			this.a(bqu, ctd, 10, -3, 8, 10, -3, 10, false, random, i);
			this.a(bqu, bvs.bJ.n(), 10, -2, 9, ctd);
			this.a(bqu, cfj13, 8, -2, 9, ctd);
			this.a(bqu, cfj13, 8, -2, 10, ctd);
			this.a(bqu, bvs.bS.n().a(cag.a, cgn.SIDE).a(cag.c, cgn.SIDE).a(cag.b, cgn.SIDE).a(cag.d, cgn.SIDE), 10, -1, 9, ctd);
			this.a(bqu, bvs.aP.n().a(cfc.a, fz.UP), 9, -2, 8, ctd);
			this.a(bqu, bvs.aP.n().a(cfc.a, fz.WEST), 10, -2, 8, ctd);
			this.a(bqu, bvs.aP.n().a(cfc.a, fz.WEST), 10, -1, 8, ctd);
			this.a(bqu, bvs.cX.n().a(cal.aq, fz.NORTH), 10, -2, 10, ctd);
			if (!this.f) {
				this.f = this.a(bqu, ctd, random, 9, -3, 10, dao.A);
			}

			return true;
		}
	}

	static class a extends cty.a {
		private a() {
		}

		@Override
		public void a(Random random, int integer2, int integer3, int integer4, boolean boolean5) {
			if (random.nextFloat() < 0.4F) {
				this.a = bvs.m.n();
			} else {
				this.a = bvs.bJ.n();
			}
		}
	}
}
