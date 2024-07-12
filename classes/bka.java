public class bka extends bke {
	private final aoq<? extends baz> a;

	public bka(aoq<? extends baz> aoq, bke.a a) {
		super(a);
		this.a = aoq;
	}

	@Override
	public ang a(blv blv) {
		fu fu3 = blv.a();
		fz fz4 = blv.i();
		fu fu5 = fu3.a(fz4);
		bec bec6 = blv.m();
		bki bki7 = blv.l();
		if (bec6 != null && !this.a(bec6, fz4, bki7, fu5)) {
			return ang.FAIL;
		} else {
			bqb bqb8 = blv.o();
			baz baz9;
			if (this.a == aoq.ad) {
				baz9 = new bbd(bqb8, fu5, fz4);
			} else {
				if (this.a != aoq.M) {
					return ang.a(bqb8.v);
				}

				baz9 = new bba(bqb8, fu5, fz4);
			}

			le le10 = bki7.o();
			if (le10 != null) {
				aoq.a(bqb8, bec6, baz9, le10);
			}

			if (baz9.h()) {
				if (!bqb8.v) {
					baz9.m();
					bqb8.c(baz9);
				}

				bki7.g(1);
				return ang.a(bqb8.v);
			} else {
				return ang.CONSUME;
			}
		}
	}

	protected boolean a(bec bec, fz fz, bki bki, fu fu) {
		return !fz.n().c() && bec.a(fu, fz, bki);
	}
}
