import java.util.Random;

public class cua extends ctu {
	private boolean e;
	private boolean f;

	public cua(Random random, int integer2, int integer3) {
		super(cmm.M, random, integer2, 64, integer3, 7, 7, 9);
	}

	public cua(cva cva, le le) {
		super(cmm.M, le);
		this.e = le.q("Witch");
		this.f = le.q("Cat");
	}

	@Override
	protected void a(le le) {
		super.a(le);
		le.a("Witch", this.e);
		le.a("Cat", this.f);
	}

	@Override
	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
		if (!this.a(bqu, ctd, 0)) {
			return false;
		} else {
			this.a(bqu, ctd, 1, 1, 1, 5, 1, 7, bvs.o.n(), bvs.o.n(), false);
			this.a(bqu, ctd, 1, 4, 2, 5, 4, 7, bvs.o.n(), bvs.o.n(), false);
			this.a(bqu, ctd, 2, 1, 0, 4, 1, 0, bvs.o.n(), bvs.o.n(), false);
			this.a(bqu, ctd, 2, 2, 2, 3, 3, 2, bvs.o.n(), bvs.o.n(), false);
			this.a(bqu, ctd, 1, 2, 3, 1, 3, 6, bvs.o.n(), bvs.o.n(), false);
			this.a(bqu, ctd, 5, 2, 3, 5, 3, 6, bvs.o.n(), bvs.o.n(), false);
			this.a(bqu, ctd, 2, 2, 7, 4, 3, 7, bvs.o.n(), bvs.o.n(), false);
			this.a(bqu, ctd, 1, 0, 2, 1, 3, 2, bvs.J.n(), bvs.J.n(), false);
			this.a(bqu, ctd, 5, 0, 2, 5, 3, 2, bvs.J.n(), bvs.J.n(), false);
			this.a(bqu, ctd, 1, 0, 7, 1, 3, 7, bvs.J.n(), bvs.J.n(), false);
			this.a(bqu, ctd, 5, 0, 7, 5, 3, 7, bvs.J.n(), bvs.J.n(), false);
			this.a(bqu, bvs.cJ.n(), 2, 3, 2, ctd);
			this.a(bqu, bvs.cJ.n(), 3, 3, 7, ctd);
			this.a(bqu, bvs.a.n(), 1, 3, 4, ctd);
			this.a(bqu, bvs.a.n(), 5, 3, 4, ctd);
			this.a(bqu, bvs.a.n(), 5, 3, 5, ctd);
			this.a(bqu, bvs.eQ.n(), 1, 3, 5, ctd);
			this.a(bqu, bvs.bV.n(), 3, 2, 6, ctd);
			this.a(bqu, bvs.eb.n(), 4, 2, 6, ctd);
			this.a(bqu, bvs.cJ.n(), 1, 2, 1, ctd);
			this.a(bqu, bvs.cJ.n(), 5, 2, 1, ctd);
			cfj cfj9 = bvs.eo.n().a(cbn.a, fz.NORTH);
			cfj cfj10 = bvs.eo.n().a(cbn.a, fz.EAST);
			cfj cfj11 = bvs.eo.n().a(cbn.a, fz.WEST);
			cfj cfj12 = bvs.eo.n().a(cbn.a, fz.SOUTH);
			this.a(bqu, ctd, 0, 4, 1, 6, 4, 1, cfj9, cfj9, false);
			this.a(bqu, ctd, 0, 4, 2, 0, 4, 7, cfj10, cfj10, false);
			this.a(bqu, ctd, 6, 4, 2, 6, 4, 7, cfj11, cfj11, false);
			this.a(bqu, ctd, 0, 4, 8, 6, 4, 8, cfj12, cfj12, false);
			this.a(bqu, cfj9.a(cbn.c, cgp.OUTER_RIGHT), 0, 4, 1, ctd);
			this.a(bqu, cfj9.a(cbn.c, cgp.OUTER_LEFT), 6, 4, 1, ctd);
			this.a(bqu, cfj12.a(cbn.c, cgp.OUTER_LEFT), 0, 4, 8, ctd);
			this.a(bqu, cfj12.a(cbn.c, cgp.OUTER_RIGHT), 6, 4, 8, ctd);

			for (int integer13 = 2; integer13 <= 7; integer13 += 5) {
				for (int integer14 = 1; integer14 <= 5; integer14 += 4) {
					this.b(bqu, bvs.J.n(), integer14, -1, integer13, ctd);
				}
			}

			if (!this.e) {
				int integer13 = this.a(2, 5);
				int integer14 = this.d(2);
				int integer15 = this.b(2, 5);
				if (ctd.b(new fu(integer13, integer14, integer15))) {
					this.e = true;
					bcr bcr16 = aoq.aR.a(bqu.n());
					bcr16.et();
					bcr16.b((double)integer13 + 0.5, (double)integer14, (double)integer15 + 0.5, 0.0F, 0.0F);
					bcr16.a(bqu, bqu.d(new fu(integer13, integer14, integer15)), apb.STRUCTURE, null, null);
					bqu.c(bcr16);
				}
			}

			this.a(bqu, ctd);
			return true;
		}
	}

	private void a(bqc bqc, ctd ctd) {
		if (!this.f) {
			int integer4 = this.a(2, 5);
			int integer5 = this.d(2);
			int integer6 = this.b(2, 5);
			if (ctd.b(new fu(integer4, integer5, integer6))) {
				this.f = true;
				aym aym7 = aoq.h.a(bqc.n());
				aym7.et();
				aym7.b((double)integer4 + 0.5, (double)integer5, (double)integer6 + 0.5, 0.0F, 0.0F);
				aym7.a(bqc, bqc.d(new fu(integer4, integer5, integer6)), apb.STRUCTURE, null, null);
				bqc.c(aym7);
			}
		}
	}
}
