import com.google.common.collect.Lists;
import java.util.Queue;

public class cbj extends bvr {
	protected cbj(cfi.c c) {
		super(c);
	}

	@Override
	public void b(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!cfj4.a(cfj1.b())) {
			this.a(bqb, fu);
		}
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu3, bvr bvr, fu fu5, boolean boolean6) {
		this.a(bqb, fu3);
		super.a(cfj, bqb, fu3, bvr, fu5, boolean6);
	}

	protected void a(bqb bqb, fu fu) {
		if (this.b(bqb, fu)) {
			bqb.a(fu, bvs.ao.n(), 2);
			bqb.c(2001, fu, bvr.i(bvs.A.n()));
		}
	}

	private boolean b(bqb bqb, fu fu) {
		Queue<aek<fu, Integer>> queue4 = Lists.<aek<fu, Integer>>newLinkedList();
		queue4.add(new aek<>(fu, 0));
		int integer5 = 0;

		while (!queue4.isEmpty()) {
			aek<fu, Integer> aek6 = (aek<fu, Integer>)queue4.poll();
			fu fu7 = aek6.a();
			int integer8 = aek6.b();

			for (fz fz12 : fz.values()) {
				fu fu13 = fu7.a(fz12);
				cfj cfj14 = bqb.d_(fu13);
				cxa cxa15 = bqb.b(fu13);
				cxd cxd16 = cfj14.c();
				if (cxa15.a(acz.a)) {
					if (cfj14.b() instanceof bvw && ((bvw)cfj14.b()).b(bqb, fu13, cfj14) != cxb.a) {
						integer5++;
						if (integer8 < 6) {
							queue4.add(new aek<>(fu13, integer8 + 1));
						}
					} else if (cfj14.b() instanceof bze) {
						bqb.a(fu13, bvs.a.n(), 3);
						integer5++;
						if (integer8 < 6) {
							queue4.add(new aek<>(fu13, integer8 + 1));
						}
					} else if (cxd16 == cxd.f || cxd16 == cxd.h) {
						cdl cdl17 = cfj14.b().q() ? bqb.c(fu13) : null;
						a(cfj14, bqb, fu13, cdl17);
						bqb.a(fu13, bvs.a.n(), 3);
						integer5++;
						if (integer8 < 6) {
							queue4.add(new aek<>(fu13, integer8 + 1));
						}
					}
				}
			}

			if (integer5 > 64) {
				break;
			}
		}

		return integer5 > 0;
	}
}
