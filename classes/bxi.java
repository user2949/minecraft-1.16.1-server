public class bxi extends bxd {
	private static final gw c = new gv();

	public bxi(cfi.c c) {
		super(c);
	}

	@Override
	protected gw a(bki bki) {
		return c;
	}

	@Override
	public cdl a(bpg bpg) {
		return new cdv();
	}

	@Override
	protected void a(bqb bqb, fu fu) {
		fw fw4 = new fw(bqb, fu);
		cdu cdu5 = fw4.g();
		int integer6 = cdu5.h();
		if (integer6 < 0) {
			bqb.c(1001, fu, 0);
		} else {
			bki bki7 = cdu5.a(integer6);
			if (!bki7.a()) {
				fz fz8 = bqb.d_(fu).c(a);
				amz amz9 = cea.b(bqb, fu.a(fz8));
				bki bki10;
				if (amz9 == null) {
					bki10 = c.dispense(fw4, bki7);
				} else {
					bki10 = cea.a(cdu5, amz9, bki7.i().a(1), fz8.f());
					if (bki10.a()) {
						bki10 = bki7.i();
						bki10.g(1);
					} else {
						bki10 = bki7.i();
					}
				}

				cdu5.a(integer6, bki10);
			}
		}
	}
}
