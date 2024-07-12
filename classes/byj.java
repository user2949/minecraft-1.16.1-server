import java.util.Optional;
import java.util.Random;

public abstract class byj extends byi implements bvt {
	protected byj(cfi.c c, fz fz, dfg dfg, boolean boolean4) {
		super(c, fz, dfg, boolean4);
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		if (!cfj.a(zd, fu)) {
			zd.b(fu, true);
		}
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if (fz == this.a.f() && !cfj1.a(bqc, fu5)) {
			bqc.G().a(fu5, this, 1);
		}

		byk byk8 = this.c();
		if (fz == this.a) {
			bvr bvr9 = cfj3.b();
			if (bvr9 != this && bvr9 != byk8) {
				return byk8.a(bqc);
			}
		}

		if (this.b) {
			bqc.F().a(fu5, cxb.c, cxb.c.a(bqc));
		}

		return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public boolean a(bpg bpg, fu fu, cfj cfj, boolean boolean4) {
		Optional<fu> optional6 = this.b(bpg, fu, cfj);
		return optional6.isPresent() && this.c().h(bpg.d_(((fu)optional6.get()).a(this.a)));
	}

	@Override
	public boolean a(bqb bqb, Random random, fu fu, cfj cfj) {
		return true;
	}

	@Override
	public void a(zd zd, Random random, fu fu, cfj cfj) {
		Optional<fu> optional6 = this.b(zd, fu, cfj);
		if (optional6.isPresent()) {
			cfj cfj7 = zd.d_((fu)optional6.get());
			((byk)cfj7.b()).a(zd, random, (fu)optional6.get(), cfj7);
		}
	}

	private Optional<fu> b(bpg bpg, fu fu, cfj cfj) {
		fu fu5 = fu;

		bvr bvr6;
		do {
			fu5 = fu5.a(this.a);
			bvr6 = bpg.d_(fu5).b();
		} while (bvr6 == cfj.b());

		return bvr6 == this.c() ? Optional.of(fu5) : Optional.empty();
	}

	@Override
	public boolean a(cfj cfj, bin bin) {
		boolean boolean4 = super.a(cfj, bin);
		return boolean4 && bin.l().b() == this.c().h() ? false : boolean4;
	}

	@Override
	protected bvr d() {
		return this;
	}
}
