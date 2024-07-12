import java.util.List;

public class bjo extends bke {
	public bjo(bke.a a) {
		super(a);
	}

	@Override
	public ang a(blv blv) {
		bqb bqb3 = blv.o();
		fu fu4 = blv.a();
		cfj cfj5 = bqb3.d_(fu4);
		if (!cfj5.a(bvs.bK) && !cfj5.a(bvs.z)) {
			return ang.FAIL;
		} else {
			fu fu6 = fu4.b();
			if (!bqb3.w(fu6)) {
				return ang.FAIL;
			} else {
				double double7 = (double)fu6.u();
				double double9 = (double)fu6.v();
				double double11 = (double)fu6.w();
				List<aom> list13 = bqb3.a(null, new deg(double7, double9, double11, double7 + 1.0, double9 + 2.0, double11 + 1.0));
				if (!list13.isEmpty()) {
					return ang.FAIL;
				} else {
					if (bqb3 instanceof zd) {
						bab bab14 = new bab(bqb3, double7 + 0.5, double9, double11 + 0.5);
						bab14.a(false);
						bqb3.c(bab14);
						cii cii15 = ((zd)bqb3).C();
						if (cii15 != null) {
							cii15.e();
						}
					}

					blv.l().g(1);
					return ang.a(bqb3.v);
				}
			}
		}
	}

	@Override
	public boolean e(bki bki) {
		return true;
	}
}
