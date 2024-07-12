import java.util.Random;

public class buy extends bvr implements bvt {
	protected static final dfg a = bvr.a(4.0, 0.0, 4.0, 12.0, 12.0, 12.0);

	public buy(cfi.c c) {
		super(c);
	}

	@Override
	public cfi.b aj_() {
		return cfi.b.XZ;
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		dem dem6 = cfj.n(bpg, fu);
		return a.a(dem6.b, dem6.c, dem6.d);
	}

	@Override
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		if (random.nextInt(3) == 0 && zd.w(fu.b()) && zd.b(fu.b(), 0) >= 9) {
			this.a(zd, fu);
		}
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		return bqd.d_(fu.c()).a(acx.ab);
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if (!cfj1.a(bqc, fu5)) {
			return bvs.a.n();
		} else {
			if (fz == fz.UP && cfj3.a(bvs.kY)) {
				bqc.a(fu5, bvs.kY.n(), 2);
			}

			return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
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
		this.a(zd, fu);
	}

	@Override
	public float a(cfj cfj, bec bec, bpg bpg, fu fu) {
		return bec.dC().b() instanceof blm ? 1.0F : super.a(cfj, bec, bpg, fu);
	}

	protected void a(bqb bqb, fu fu) {
		bqb.a(fu.b(), bvs.kY.n().a(bux.e, cfw.SMALL), 3);
	}
}
