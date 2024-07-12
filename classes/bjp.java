public class bjp extends bke {
	public bjp(bke.a a) {
		super(a);
	}

	@Override
	public ang a(blv blv) {
		bqb bqb3 = blv.o();
		fu fu4 = blv.a();
		cfj cfj5 = bqb3.d_(fu4);
		if (!cfj5.a(bvs.ed) || (Boolean)cfj5.c(bxm.b)) {
			return ang.PASS;
		} else if (bqb3.v) {
			return ang.SUCCESS;
		} else {
			cfj cfj6 = cfj5.a(bxm.b, Boolean.valueOf(true));
			bvr.a(cfj5, cfj6, bqb3, fu4);
			bqb3.a(fu4, cfj6, 2);
			bqb3.c(fu4, bvs.ed);
			blv.l().g(1);
			bqb3.c(1503, fu4, 0);
			cfo.b b7 = bxm.c().a(bqb3, fu4);
			if (b7 != null) {
				fu fu8 = b7.a().b(-3, 0, -3);

				for (int integer9 = 0; integer9 < 3; integer9++) {
					for (int integer10 = 0; integer10 < 3; integer10++) {
						bqb3.a(fu8.b(integer9, 0, integer10), bvs.ec.n(), 2);
					}
				}

				bqb3.b(1038, fu8.b(1, 0, 1), 0);
			}

			return ang.CONSUME;
		}
	}

	@Override
	public anh<bki> a(bqb bqb, bec bec, anf anf) {
		bki bki5 = bec.b(anf);
		dej dej6 = a(bqb, bec, bpj.b.NONE);
		if (dej6.c() == dej.a.BLOCK && bqb.d_(((deh)dej6).a()).a(bvs.ed)) {
			return anh.c(bki5);
		} else {
			bec.c(anf);
			if (bqb instanceof zd) {
				fu fu7 = ((zd)bqb).i().g().a((zd)bqb, cml.k, bec.cA(), 100, false);
				if (fu7 != null) {
					bel bel8 = new bel(bqb, bec.cC(), bec.e(0.5), bec.cG());
					bel8.b(bki5);
					bel8.a(fu7);
					bqb.c(bel8);
					if (bec instanceof ze) {
						aa.m.a((ze)bec, fu7);
					}

					bqb.a(null, bec.cC(), bec.cD(), bec.cG(), acl.dx, acm.NEUTRAL, 0.5F, 0.4F / (h.nextFloat() * 0.4F + 0.8F));
					bqb.a(null, 1003, bec.cA(), 0);
					if (!bec.bJ.d) {
						bki5.g(1);
					}

					bec.b(acu.c.b(this));
					bec.a(anf, true);
					return anh.a(bki5);
				}
			}

			return anh.b(bki5);
		}
	}
}
