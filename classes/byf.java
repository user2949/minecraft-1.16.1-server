import java.util.Random;

public class byf extends bvr {
	protected static final dfg a = bxs.b;

	protected byf(cfi.c c) {
		super(c);
	}

	@Override
	public boolean c_(cfj cfj) {
		return true;
	}

	@Override
	public cfj a(bin bin) {
		return !this.n().a((bqd)bin.o(), bin.a()) ? bvr.a(this.n(), bvs.j.n(), bin.o(), bin.a()) : super.a(bin);
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if (fz == fz.UP && !cfj1.a(bqc, fu5)) {
			bqc.G().a(fu5, this, 1);
		}

		return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		bxs.d(cfj, zd, fu);
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		cfj cfj5 = bqd.d_(fu.b());
		return !cfj5.c().b() || cfj5.b() instanceof bxu;
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return a;
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
