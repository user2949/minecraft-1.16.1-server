import java.util.Random;

public class bzs extends bvr implements bvt {
	protected bzs(cfi.c c) {
		super(c);
	}

	private static boolean b(cfj cfj, bqd bqd, fu fu) {
		fu fu4 = fu.b();
		cfj cfj5 = bqd.d_(fu4);
		int integer6 = cwo.a(bqd, cfj, fu, cfj5, fu4, fz.UP, cfj5.b(bqd, fu4));
		return integer6 < bqd.H();
	}

	@Override
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		if (!b(cfj, zd, fu)) {
			zd.a(fu, bvs.cL.n());
		}
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
		cfj cfj6 = zd.d_(fu);
		fu fu7 = fu.b();
		if (cfj6.a(bvs.mu)) {
			clk.a(zd, random, fu7, brf.aY, 3, 1);
		} else if (cfj6.a(bvs.ml)) {
			clk.a(zd, random, fu7, brf.aZ, 3, 1);
			clk.a(zd, random, fu7, brf.ba, 3, 1);
			if (random.nextInt(8) == 0) {
				cmq.a(zd, random, fu7, 3, 1, 2);
			}
		}
	}
}
