import java.util.Random;

public abstract class bvi extends bvr {
	protected static final dfg a = bvr.a(1.0, 0.0, 1.0, 15.0, 0.5, 15.0);
	protected static final dfg b = bvr.a(1.0, 0.0, 1.0, 15.0, 1.0, 15.0);
	protected static final deg c = new deg(0.125, 0.0, 0.125, 0.875, 0.25, 0.875);

	protected bvi(cfi.c c) {
		super(c);
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return this.g(cfj) > 0 ? a : b;
	}

	protected int c() {
		return 20;
	}

	@Override
	public boolean ak_() {
		return true;
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		return fz == fz.DOWN && !cfj1.a(bqc, fu5) ? bvs.a.n() : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		fu fu5 = fu.c();
		return c(bqd, fu5) || a(bqd, fu5, fz.UP);
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		int integer6 = this.g(cfj);
		if (integer6 > 0) {
			this.a(zd, fu, cfj, integer6);
		}
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu, aom aom) {
		if (!bqb.v) {
			int integer6 = this.g(cfj);
			if (integer6 == 0) {
				this.a(bqb, fu, cfj, integer6);
			}
		}
	}

	protected void a(bqb bqb, fu fu, cfj cfj, int integer) {
		int integer6 = this.b(bqb, fu);
		boolean boolean7 = integer > 0;
		boolean boolean8 = integer6 > 0;
		if (integer != integer6) {
			cfj cfj9 = this.a(cfj, integer6);
			bqb.a(fu, cfj9, 2);
			this.a(bqb, fu);
			bqb.b(fu, cfj, cfj9);
		}

		if (!boolean8 && boolean7) {
			this.b((bqc)bqb, fu);
		} else if (boolean8 && !boolean7) {
			this.a((bqc)bqb, fu);
		}

		if (boolean8) {
			bqb.G().a(new fu(fu), this, this.c());
		}
	}

	protected abstract void a(bqc bqc, fu fu);

	protected abstract void b(bqc bqc, fu fu);

	@Override
	public void a(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!boolean5 && !cfj1.a(cfj4.b())) {
			if (this.g(cfj1) > 0) {
				this.a(bqb, fu);
			}

			super.a(cfj1, bqb, fu, cfj4, boolean5);
		}
	}

	protected void a(bqb bqb, fu fu) {
		bqb.b(fu, this);
		bqb.b(fu.c(), this);
	}

	@Override
	public int a(cfj cfj, bpg bpg, fu fu, fz fz) {
		return this.g(cfj);
	}

	@Override
	public int b(cfj cfj, bpg bpg, fu fu, fz fz) {
		return fz == fz.UP ? this.g(cfj) : 0;
	}

	@Override
	public boolean b_(cfj cfj) {
		return true;
	}

	@Override
	public cxf f(cfj cfj) {
		return cxf.DESTROY;
	}

	protected abstract int b(bqb bqb, fu fu);

	protected abstract int g(cfj cfj);

	protected abstract cfj a(cfj cfj, int integer);
}
