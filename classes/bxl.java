public class bxl extends bvg {
	protected static final dfg a = bvr.a(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);

	protected bxl(cfi.c c) {
		super(c);
	}

	@Override
	public cdl a(bpg bpg) {
		return new cen();
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return a;
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu, aom aom) {
		if (bqb instanceof zd
			&& !aom.bn()
			&& !aom.bo()
			&& aom.bK()
			&& dfd.c(dfd.a(aom.cb().d((double)(-fu.u()), (double)(-fu.v()), (double)(-fu.w()))), cfj.j(bqb, fu), deq.i)) {
			ug<bqb> ug6 = bqb.W() == bqb.i ? bqb.g : bqb.i;
			zd zd7 = ((zd)bqb).l().a(ug6);
			if (zd7 == null) {
				return;
			}

			aom.a(zd7);
		}
	}

	@Override
	public boolean a(cfj cfj, cwz cwz) {
		return false;
	}
}
