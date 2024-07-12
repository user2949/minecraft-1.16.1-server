public class bvx extends bvr {
	protected bvx(cfi.c c) {
		super(c);
	}

	protected boolean c(cfj cfj, bpg bpg, fu fu) {
		return cfj.a(bvs.i) || cfj.a(bvs.j) || cfj.a(bvs.k) || cfj.a(bvs.l) || cfj.a(bvs.bX);
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		return !cfj1.a(bqc, fu5) ? bvs.a.n() : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		fu fu5 = fu.c();
		return this.c(bqd.d_(fu5), bqd, fu5);
	}

	@Override
	public boolean b(cfj cfj, bpg bpg, fu fu) {
		return cfj.m().c();
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return czg == czg.AIR && !this.at ? true : super.a(cfj, bpg, fu, czg);
	}
}
