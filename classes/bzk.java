import java.util.Random;

public class bzk extends bvx implements bvt {
	protected static final dfg a = bvr.a(5.0, 0.0, 5.0, 11.0, 6.0, 11.0);

	public bzk(cfi.c c) {
		super(c);
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return a;
	}

	@Override
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		if (random.nextInt(25) == 0) {
			int integer6 = 5;
			int integer7 = 4;

			for (fu fu9 : fu.a(fu.b(-4, -1, -4), fu.b(4, 1, 4))) {
				if (zd.d_(fu9).a(this)) {
					if (--integer6 <= 0) {
						return;
					}
				}
			}

			fu fu8 = fu.b(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);

			for (int integer9 = 0; integer9 < 4; integer9++) {
				if (zd.w(fu8) && cfj.a(zd, fu8)) {
					fu = fu8;
				}

				fu8 = fu.b(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
			}

			if (zd.w(fu8) && cfj.a(zd, fu8)) {
				zd.a(fu8, cfj, 2);
			}
		}
	}

	@Override
	protected boolean c(cfj cfj, bpg bpg, fu fu) {
		return cfj.i(bpg, fu);
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		fu fu5 = fu.c();
		cfj cfj6 = bqd.d_(fu5);
		return !cfj6.a(bvs.dT) && !cfj6.a(bvs.l) ? bqd.b(fu, 0) < 13 && this.c(cfj6, bqd, fu5) : true;
	}

	public boolean a(zd zd, fu fu, cfj cfj, Random random) {
		zd.a(fu, false);
		ckb<cnt, ?> ckb6;
		if (this == bvs.bC) {
			ckb6 = ckt.n.b(brf.aX);
		} else {
			if (this != bvs.bD) {
				zd.a(fu, cfj, 3);
				return false;
			}

			ckb6 = ckt.m.b(brf.aW);
		}

		if (ckb6.a(zd, zd.a(), zd.i().g(), random, fu)) {
			return true;
		} else {
			zd.a(fu, cfj, 3);
			return false;
		}
	}

	@Override
	public boolean a(bpg bpg, fu fu, cfj cfj, boolean boolean4) {
		return true;
	}

	@Override
	public boolean a(bqb bqb, Random random, fu fu, cfj cfj) {
		return (double)random.nextFloat() < 0.4;
	}

	@Override
	public void a(zd zd, Random random, fu fu, cfj cfj) {
		this.a(zd, fu, cfj, random);
	}
}
