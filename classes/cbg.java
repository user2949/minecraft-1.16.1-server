import java.util.Random;

public class cbg extends bvr {
	protected static final dfg a = bvr.a(0.0, 0.0, 0.0, 16.0, 14.0, 16.0);

	public cbg(cfi.c c) {
		super(c);
	}

	@Override
	public dfg c(cfj cfj, bpg bpg, fu fu, der der) {
		return a;
	}

	@Override
	public dfg e(cfj cfj, bpg bpg, fu fu) {
		return dfd.b();
	}

	@Override
	public dfg a(cfj cfj, bpg bpg, fu fu, der der) {
		return dfd.b();
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		bvv.a(zd, fu.b(), false);
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if (fz == fz.UP && cfj3.a(bvs.A)) {
			bqc.G().a(fu5, this, 20);
		}

		return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public void b(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		bqb.G().a(fu, this, 20);
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
