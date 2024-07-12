public class ccu extends bvi {
	public static final cgi d = cfz.az;
	private final int e;

	protected ccu(int integer, cfi.c c) {
		super(c);
		this.j(this.n.b().a(d, Integer.valueOf(0)));
		this.e = integer;
	}

	@Override
	protected int b(bqb bqb, fu fu) {
		int integer4 = Math.min(bqb.a(aom.class, c.a(fu)).size(), this.e);
		if (integer4 > 0) {
			float float5 = (float)Math.min(this.e, integer4) / (float)this.e;
			return aec.f(float5 * 15.0F);
		} else {
			return 0;
		}
	}

	@Override
	protected void a(bqc bqc, fu fu) {
		bqc.a(null, fu, acl.hG, acm.BLOCKS, 0.3F, 0.90000004F);
	}

	@Override
	protected void b(bqc bqc, fu fu) {
		bqc.a(null, fu, acl.hF, acm.BLOCKS, 0.3F, 0.75F);
	}

	@Override
	protected int g(cfj cfj) {
		return (Integer)cfj.c(d);
	}

	@Override
	protected cfj a(cfj cfj, int integer) {
		return cfj.a(d, Integer.valueOf(integer));
	}

	@Override
	protected int c() {
		return 10;
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(d);
	}
}
