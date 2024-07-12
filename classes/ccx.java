import javax.annotation.Nullable;

public class ccx extends cay {
	@Nullable
	private static cfo c;
	@Nullable
	private static cfo d;

	protected ccx(cfi.c c) {
		super(cay.b.WITHER_SKELETON, c);
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, @Nullable aoy aoy, bki bki) {
		super.a(bqb, fu, cfj, aoy, bki);
		cdl cdl7 = bqb.c(fu);
		if (cdl7 instanceof cei) {
			a(bqb, fu, (cei)cdl7);
		}
	}

	public static void a(bqb bqb, fu fu, cei cei) {
		if (!bqb.v) {
			cfj cfj4 = cei.p();
			boolean boolean5 = cfj4.a(bvs.fe) || cfj4.a(bvs.ff);
			if (boolean5 && fu.v() >= 0 && bqb.ac() != and.PEACEFUL) {
				cfo cfo6 = c();
				cfo.b b7 = cfo6.a(bqb, fu);
				if (b7 != null) {
					for (int integer8 = 0; integer8 < cfo6.c(); integer8++) {
						for (int integer9 = 0; integer9 < cfo6.b(); integer9++) {
							cfn cfn10 = b7.a(integer8, integer9, 0);
							bqb.a(cfn10.d(), bvs.a.n(), 2);
							bqb.c(2001, cfn10.d(), bvr.i(cfn10.a()));
						}
					}

					baw baw8 = aoq.aS.a(bqb);
					fu fu9 = b7.a(1, 2, 0).d();
					baw8.b((double)fu9.u() + 0.5, (double)fu9.v() + 0.55, (double)fu9.w() + 0.5, b7.b().n() == fz.a.X ? 0.0F : 90.0F, 0.0F);
					baw8.aH = b7.b().n() == fz.a.X ? 0.0F : 90.0F;
					baw8.m();

					for (ze ze11 : bqb.a(ze.class, baw8.cb().g(50.0))) {
						aa.n.a(ze11, baw8);
					}

					bqb.c(baw8);

					for (int integer10 = 0; integer10 < cfo6.c(); integer10++) {
						for (int integer11 = 0; integer11 < cfo6.b(); integer11++) {
							bqb.a(b7.a(integer10, integer11, 0).d(), bvs.a);
						}
					}
				}
			}
		}
	}

	public static boolean b(bqb bqb, fu fu, bki bki) {
		return bki.b() == bkk.pe && fu.v() >= 2 && bqb.ac() != and.PEACEFUL && !bqb.v ? d().a(bqb, fu) != null : false;
	}

	private static cfo c() {
		if (c == null) {
			c = cfp.a().a("^^^", "###", "~#~").a('#', cfn -> cfn.a().a(acx.ah)).a('^', cfn.a(cft.a(bvs.fe).or(cft.a(bvs.ff)))).a('~', cfn.a(cfr.a(cxd.a))).b();
		}

		return c;
	}

	private static cfo d() {
		if (d == null) {
			d = cfp.a().a("   ", "###", "~#~").a('#', cfn -> cfn.a().a(acx.ah)).a('~', cfn.a(cfr.a(cxd.a))).b();
		}

		return d;
	}
}
