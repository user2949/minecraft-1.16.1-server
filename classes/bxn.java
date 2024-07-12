public class bxn extends bxc {
	protected static final dfg b = bvr.a(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
	protected static final dfg c = bvr.a(6.0, 6.0, 0.0, 10.0, 10.0, 16.0);
	protected static final dfg d = bvr.a(0.0, 6.0, 6.0, 16.0, 10.0, 10.0);

	protected bxn(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, fz.UP));
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		return cfj.a(a, cap.a(cfj.c(a)));
	}

	@Override
	public cfj a(cfj cfj, bzj bzj) {
		return cfj.a(a, bzj.b(cfj.c(a)));
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		switch (((fz)cfj.c(a)).n()) {
			case X:
			default:
				return d;
			case Z:
				return c;
			case Y:
				return b;
		}
	}

	@Override
	public cfj a(bin bin) {
		fz fz3 = bin.i();
		cfj cfj4 = bin.o().d_(bin.a().a(fz3.f()));
		return cfj4.a(this) && cfj4.c(a) == fz3 ? this.n().a(a, fz3.f()) : this.n().a(a, fz3);
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(bxn.a);
	}

	@Override
	public cxf f(cfj cfj) {
		return cxf.NORMAL;
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
