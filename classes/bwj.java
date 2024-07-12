import java.util.Random;

public class bwj extends bzv {
	protected bwj(cfi.c c) {
		super(0.3125F, c);
		this.j(
			this.n
				.b()
				.a(a, Boolean.valueOf(false))
				.a(b, Boolean.valueOf(false))
				.a(bwj.c, Boolean.valueOf(false))
				.a(d, Boolean.valueOf(false))
				.a(e, Boolean.valueOf(false))
				.a(f, Boolean.valueOf(false))
		);
	}

	@Override
	public cfj a(bin bin) {
		return this.a(bin.o(), bin.a());
	}

	public cfj a(bpg bpg, fu fu) {
		bvr bvr4 = bpg.d_(fu.c()).b();
		bvr bvr5 = bpg.d_(fu.b()).b();
		bvr bvr6 = bpg.d_(fu.d()).b();
		bvr bvr7 = bpg.d_(fu.g()).b();
		bvr bvr8 = bpg.d_(fu.e()).b();
		bvr bvr9 = bpg.d_(fu.f()).b();
		return this.n()
			.a(f, Boolean.valueOf(bvr4 == this || bvr4 == bvs.iy || bvr4 == bvs.ee))
			.a(e, Boolean.valueOf(bvr5 == this || bvr5 == bvs.iy))
			.a(a, Boolean.valueOf(bvr6 == this || bvr6 == bvs.iy))
			.a(b, Boolean.valueOf(bvr7 == this || bvr7 == bvs.iy))
			.a(c, Boolean.valueOf(bvr8 == this || bvr8 == bvs.iy))
			.a(d, Boolean.valueOf(bvr9 == this || bvr9 == bvs.iy));
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if (!cfj1.a(bqc, fu5)) {
			bqc.G().a(fu5, this, 1);
			return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
		} else {
			boolean boolean8 = cfj3.b() == this || cfj3.a(bvs.iy) || fz == fz.DOWN && cfj3.a(bvs.ee);
			return cfj1.a((cgl)g.get(fz), Boolean.valueOf(boolean8));
		}
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		if (!cfj.a(zd, fu)) {
			zd.b(fu, true);
		}
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		cfj cfj5 = bqd.d_(fu.c());
		boolean boolean6 = !bqd.d_(fu.b()).g() && !cfj5.g();

		for (fz fz8 : fz.c.HORIZONTAL) {
			fu fu9 = fu.a(fz8);
			bvr bvr10 = bqd.d_(fu9).b();
			if (bvr10 == this) {
				if (boolean6) {
					return false;
				}

				bvr bvr11 = bqd.d_(fu9.c()).b();
				if (bvr11 == this || bvr11 == bvs.ee) {
					return true;
				}
			}
		}

		bvr bvr7 = cfj5.b();
		return bvr7 == this || bvr7 == bvs.ee;
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(bwj.a, b, c, d, e, f);
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
