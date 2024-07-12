public class cao extends bvr {
	public static final cgg<fz.a> a = cfz.F;

	public cao(cfi.c c) {
		super(c);
		this.j(this.n().a(a, fz.a.Y));
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		switch (cap) {
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:
				switch ((fz.a)cfj.c(a)) {
					case X:
						return cfj.a(a, fz.a.Z);
					case Z:
						return cfj.a(a, fz.a.X);
					default:
						return cfj;
				}
			default:
				return cfj;
		}
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(cao.a);
	}

	@Override
	public cfj a(bin bin) {
		return this.n().a(a, bin.i().n());
	}
}
