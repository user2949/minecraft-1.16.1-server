import java.util.Random;

public class bzt extends bxc {
	public static final cga b = cfz.w;

	public bzt(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, fz.SOUTH).a(b, Boolean.valueOf(false)));
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(bzt.a, b);
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		return cfj.a(a, cap.a(cfj.c(a)));
	}

	@Override
	public cfj a(cfj cfj, bzj bzj) {
		return cfj.a(bzj.a(cfj.c(a)));
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		if ((Boolean)cfj.c(b)) {
			zd.a(fu, cfj.a(b, Boolean.valueOf(false)), 2);
		} else {
			zd.a(fu, cfj.a(b, Boolean.valueOf(true)), 2);
			zd.j().a(fu, this, 2);
		}

		this.a(zd, fu, cfj);
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if (cfj1.c(a) == fz && !(Boolean)cfj1.c(b)) {
			this.a(bqc, fu5);
		}

		return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	private void a(bqc bqc, fu fu) {
		if (!bqc.s_() && !bqc.G().a(fu, this)) {
			bqc.G().a(fu, this, 2);
		}
	}

	protected void a(bqb bqb, fu fu, cfj cfj) {
		fz fz5 = cfj.c(a);
		fu fu6 = fu.a(fz5.f());
		bqb.a(fu6, this, fu);
		bqb.a(fu6, this, fz5);
	}

	@Override
	public boolean b_(cfj cfj) {
		return true;
	}

	@Override
	public int b(cfj cfj, bpg bpg, fu fu, fz fz) {
		return cfj.b(bpg, fu, fz);
	}

	@Override
	public int a(cfj cfj, bpg bpg, fu fu, fz fz) {
		return cfj.c(b) && cfj.c(a) == fz ? 15 : 0;
	}

	@Override
	public void b(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!cfj1.a(cfj4.b())) {
			if (!bqb.s_() && (Boolean)cfj1.c(b) && !bqb.G().a(fu, this)) {
				cfj cfj7 = cfj1.a(b, Boolean.valueOf(false));
				bqb.a(fu, cfj7, 18);
				this.a(bqb, fu, cfj7);
			}
		}
	}

	@Override
	public void a(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!cfj1.a(cfj4.b())) {
			if (!bqb.v && (Boolean)cfj1.c(b) && bqb.G().a(fu, this)) {
				this.a(bqb, fu, cfj1.a(b, Boolean.valueOf(false)));
			}
		}
	}

	@Override
	public cfj a(bin bin) {
		return this.n().a(a, bin.d().f().f());
	}
}
