import java.util.List;
import java.util.function.Predicate;

public class bio extends bke {
	private static final Predicate<aom> a = aop.g.and(aom::aQ);
	private final bft.b b;

	public bio(bft.b b, bke.a a) {
		super(a);
		this.b = b;
	}

	@Override
	public anh<bki> a(bqb bqb, bec bec, anf anf) {
		bki bki5 = bec.b(anf);
		dej dej6 = a(bqb, bec, bpj.b.ANY);
		if (dej6.c() == dej.a.MISS) {
			return anh.c(bki5);
		} else {
			dem dem7 = bec.f(1.0F);
			double double8 = 5.0;
			List<aom> list10 = bqb.a(bec, bec.cb().b(dem7.a(5.0)).g(1.0), a);
			if (!list10.isEmpty()) {
				dem dem11 = bec.j(1.0F);

				for (aom aom13 : list10) {
					deg deg14 = aom13.cb().g((double)aom13.bc());
					if (deg14.d(dem11)) {
						return anh.c(bki5);
					}
				}
			}

			if (dej6.c() == dej.a.BLOCK) {
				bft bft11 = new bft(bqb, dej6.e().b, dej6.e().c, dej6.e().d);
				bft11.a(this.b);
				bft11.p = bec.p;
				if (!bqb.a_(bft11, bft11.cb().g(-0.1))) {
					return anh.d(bki5);
				} else {
					if (!bqb.v) {
						bqb.c(bft11);
						if (!bec.bJ.d) {
							bki5.g(1);
						}
					}

					bec.b(acu.c.b(this));
					return anh.a(bki5, bqb.s_());
				}
			} else {
				return anh.c(bki5);
			}
		}
	}
}
