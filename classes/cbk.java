import java.util.Random;

public abstract class cbk extends cbe {
	protected cbk(cfi.c c) {
		super(c);
	}

	private static boolean b(cfj cfj, bqd bqd, fu fu) {
		fu fu4 = fu.b();
		cfj cfj5 = bqd.d_(fu4);
		if (cfj5.a(bvs.cC) && (Integer)cfj5.c(cbd.a) == 1) {
			return true;
		} else if (cfj5.m().e() == 8) {
			return false;
		} else {
			int integer6 = cwo.a(bqd, cfj, fu, cfj5, fu4, fz.UP, cfj5.b(bqd, fu4));
			return integer6 < bqd.H();
		}
	}

	private static boolean c(cfj cfj, bqd bqd, fu fu) {
		fu fu4 = fu.b();
		return b(cfj, bqd, fu) && !bqd.b(fu4).a(acz.a);
	}

	@Override
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		if (!b(cfj, zd, fu)) {
			zd.a(fu, bvs.j.n());
		} else {
			if (zd.B(fu.b()) >= 9) {
				cfj cfj6 = this.n();

				for (int integer7 = 0; integer7 < 4; integer7++) {
					fu fu8 = fu.b(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
					if (zd.d_(fu8).a(bvs.j) && c(cfj6, zd, fu8)) {
						zd.a(fu8, cfj6.a(a, Boolean.valueOf(zd.d_(fu8.b()).a(bvs.cC))));
					}
				}
			}
		}
	}
}
