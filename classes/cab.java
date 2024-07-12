import java.util.List;

public class cab extends bvi {
	public static final cga d = cfz.w;
	private final cab.a e;

	protected cab(cab.a a, cfi.c c) {
		super(c);
		this.j(this.n.b().a(d, Boolean.valueOf(false)));
		this.e = a;
	}

	@Override
	protected int g(cfj cfj) {
		return cfj.c(d) ? 15 : 0;
	}

	@Override
	protected cfj a(cfj cfj, int integer) {
		return cfj.a(d, Boolean.valueOf(integer > 0));
	}

	@Override
	protected void a(bqc bqc, fu fu) {
		if (this.as != cxd.x && this.as != cxd.y) {
			bqc.a(null, fu, acl.oK, acm.BLOCKS, 0.3F, 0.6F);
		} else {
			bqc.a(null, fu, acl.rn, acm.BLOCKS, 0.3F, 0.8F);
		}
	}

	@Override
	protected void b(bqc bqc, fu fu) {
		if (this.as != cxd.x && this.as != cxd.y) {
			bqc.a(null, fu, acl.oJ, acm.BLOCKS, 0.3F, 0.5F);
		} else {
			bqc.a(null, fu, acl.rm, acm.BLOCKS, 0.3F, 0.7F);
		}
	}

	@Override
	protected int b(bqb bqb, fu fu) {
		deg deg4 = c.a(fu);
		List<? extends aom> list5;
		switch (this.e) {
			case EVERYTHING:
				list5 = bqb.a(null, deg4);
				break;
			case MOBS:
				list5 = bqb.a(aoy.class, deg4);
				break;
			default:
				return 0;
		}

		if (!list5.isEmpty()) {
			for (aom aom7 : list5) {
				if (!aom7.bP()) {
					return 15;
				}
			}
		}

		return 0;
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(d);
	}

	public static enum a {
		EVERYTHING,
		MOBS;
	}
}
