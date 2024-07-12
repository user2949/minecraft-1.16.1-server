public abstract class byi extends bvr {
	protected final fz a;
	protected final boolean b;
	protected final dfg c;

	protected byi(cfi.c c, fz fz, dfg dfg, boolean boolean4) {
		super(c);
		this.a = fz;
		this.c = dfg;
		this.b = boolean4;
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		fu fu5 = fu.a(this.a.f());
		cfj cfj6 = bqd.d_(fu5);
		bvr bvr7 = cfj6.b();
		return !this.c(bvr7) ? false : bvr7 == this.c() || bvr7 == this.d() || cfj6.d(bqd, fu5, this.a);
	}

	protected boolean c(bvr bvr) {
		return true;
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return this.c;
	}

	protected abstract byk c();

	protected abstract bvr d();
}
