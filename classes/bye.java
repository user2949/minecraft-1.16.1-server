import java.util.List;
import java.util.Random;

public class bye extends cbk implements bvt {
	public bye(cfi.c c) {
		super(c);
	}

	@Override
	public boolean a(bpg bpg, fu fu, cfj cfj, boolean boolean4) {
		return bpg.d_(fu.b()).g();
	}

	@Override
	public boolean a(bqb bqb, Random random, fu fu, cfj cfj) {
		return true;
	}

	@Override
	public void a(zd zd, Random random, fu fu, cfj cfj) {
		fu fu6 = fu.b();
		cfj cfj7 = bvs.aR.n();

		label48:
		for (int integer8 = 0; integer8 < 128; integer8++) {
			fu fu9 = fu6;

			for (int integer10 = 0; integer10 < integer8 / 16; integer10++) {
				fu9 = fu9.b(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
				if (!zd.d_(fu9.c()).a(this) || zd.d_(fu9).r(zd, fu9)) {
					continue label48;
				}
			}

			cfj cfj10 = zd.d_(fu9);
			if (cfj10.a(cfj7.b()) && random.nextInt(10) == 0) {
				((bvt)cfj7.b()).a(zd, random, fu9, cfj10);
			}

			if (cfj10.g()) {
				cfj cfj11;
				if (random.nextInt(8) == 0) {
					List<ckb<?, ?>> list12 = zd.v(fu9).h();
					if (list12.isEmpty()) {
						continue;
					}

					ckb<?, ?> ckb13 = ((cnm)((ckb)list12.get(0)).e).b;
					cfj11 = ((cjj)ckb13.d).b(random, fu9, ckb13.e);
				} else {
					cfj11 = cfj7;
				}

				if (cfj11.a(zd, fu9)) {
					zd.a(fu9, cfj11, 3);
				}
			}
		}
	}
}
