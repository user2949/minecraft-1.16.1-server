import java.util.Random;

public class bzq extends bvr implements bvt {
	public bzq(cfi.c c) {
		super(c);
	}

	@Override
	public boolean a(bpg bpg, fu fu, cfj cfj, boolean boolean4) {
		if (!bpg.d_(fu.b()).a(bpg, fu)) {
			return false;
		} else {
			for (fu fu7 : fu.a(fu.b(-1, -1, -1), fu.b(1, 1, 1))) {
				if (bpg.d_(fu7).a(acx.an)) {
					return true;
				}
			}

			return false;
		}
	}

	@Override
	public boolean a(bqb bqb, Random random, fu fu, cfj cfj) {
		return true;
	}

	@Override
	public void a(zd zd, Random random, fu fu, cfj cfj) {
		boolean boolean6 = false;
		boolean boolean7 = false;

		for (fu fu9 : fu.a(fu.b(-1, -1, -1), fu.b(1, 1, 1))) {
			cfj cfj10 = zd.d_(fu9);
			if (cfj10.a(bvs.ml)) {
				boolean7 = true;
			}

			if (cfj10.a(bvs.mu)) {
				boolean6 = true;
			}

			if (boolean7 && boolean6) {
				break;
			}
		}

		if (boolean7 && boolean6) {
			zd.a(fu, random.nextBoolean() ? bvs.ml.n() : bvs.mu.n(), 3);
		} else if (boolean7) {
			zd.a(fu, bvs.ml.n(), 3);
		} else if (boolean6) {
			zd.a(fu, bvs.mu.n(), 3);
		}
	}
}
