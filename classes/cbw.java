import java.util.Random;

public class cbw extends bvx implements bvt {
	public static final cgi a = cfz.ag;
	private static final dfg b = bvr.a(3.0, 0.0, 3.0, 13.0, 8.0, 13.0);
	private static final dfg c = bvr.a(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

	public cbw(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, Integer.valueOf(0)));
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		if ((Integer)cfj.c(a) == 0) {
			return b;
		} else {
			return cfj.c(a) < 3 ? c : super.b(cfj, bpg, fu, der);
		}
	}

	@Override
	public boolean a_(cfj cfj) {
		return (Integer)cfj.c(a) < 3;
	}

	@Override
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		int integer6 = (Integer)cfj.c(a);
		if (integer6 < 3 && random.nextInt(5) == 0 && zd.b(fu.b(), 0) >= 9) {
			zd.a(fu, cfj.a(a, Integer.valueOf(integer6 + 1)), 2);
		}
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu, aom aom) {
		if (aom instanceof aoy && aom.U() != aoq.C && aom.U() != aoq.e) {
			aom.a(cfj, new dem(0.8F, 0.75, 0.8F));
			if (!bqb.v && (Integer)cfj.c(a) > 0 && (aom.D != aom.cC() || aom.F != aom.cG())) {
				double double6 = Math.abs(aom.cC() - aom.D);
				double double8 = Math.abs(aom.cG() - aom.F);
				if (double6 >= 0.003F || double8 >= 0.003F) {
					aom.a(anw.u, 1.0F);
				}
			}
		}
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		int integer8 = (Integer)cfj.c(a);
		boolean boolean9 = integer8 == 3;
		if (!boolean9 && bec.b(anf).b() == bkk.mG) {
			return ang.PASS;
		} else if (integer8 > 1) {
			int integer10 = 1 + bqb.t.nextInt(2);
			a(bqb, fu, new bki(bkk.rl, integer10 + (boolean9 ? 1 : 0)));
			bqb.a(null, fu, acl.oS, acm.BLOCKS, 1.0F, 0.8F + bqb.t.nextFloat() * 0.4F);
			bqb.a(fu, cfj.a(a, Integer.valueOf(1)), 2);
			return ang.a(bqb.v);
		} else {
			return super.a(cfj, bqb, fu, bec, anf, deh);
		}
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(cbw.a);
	}

	@Override
	public boolean a(bpg bpg, fu fu, cfj cfj, boolean boolean4) {
		return (Integer)cfj.c(a) < 3;
	}

	@Override
	public boolean a(bqb bqb, Random random, fu fu, cfj cfj) {
		return true;
	}

	@Override
	public void a(zd zd, Random random, fu fu, cfj cfj) {
		int integer6 = Math.min(3, (Integer)cfj.c(a) + 1);
		zd.a(fu, cfj.a(a, Integer.valueOf(integer6)), 2);
	}
}
