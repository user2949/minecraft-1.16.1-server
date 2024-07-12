import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class bwm extends bxb implements bxp {
	public static final cgg<cgc> a = cfz.aG;

	public bwm(cfi.c c) {
		super(c);
		this.j(this.n.b().a(aq, fz.NORTH).a(bwm.c, Boolean.valueOf(false)).a(a, cgc.COMPARE));
	}

	@Override
	protected int g(cfj cfj) {
		return 2;
	}

	@Override
	protected int b(bpg bpg, fu fu, cfj cfj) {
		cdl cdl5 = bpg.c(fu);
		return cdl5 instanceof cdr ? ((cdr)cdl5).d() : 0;
	}

	private int e(bqb bqb, fu fu, cfj cfj) {
		return cfj.c(a) == cgc.SUBTRACT ? Math.max(this.b(bqb, fu, cfj) - this.b((bqd)bqb, fu, cfj), 0) : this.b(bqb, fu, cfj);
	}

	@Override
	protected boolean a(bqb bqb, fu fu, cfj cfj) {
		int integer5 = this.b(bqb, fu, cfj);
		if (integer5 == 0) {
			return false;
		} else {
			int integer6 = this.b((bqd)bqb, fu, cfj);
			return integer5 > integer6 ? true : integer5 == integer6 && cfj.c(a) == cgc.COMPARE;
		}
	}

	@Override
	protected int b(bqb bqb, fu fu, cfj cfj) {
		int integer5 = super.b(bqb, fu, cfj);
		fz fz6 = cfj.c(aq);
		fu fu7 = fu.a(fz6);
		cfj cfj8 = bqb.d_(fu7);
		if (cfj8.j()) {
			integer5 = cfj8.a(bqb, fu7);
		} else if (integer5 < 15 && cfj8.g(bqb, fu7)) {
			fu7 = fu7.a(fz6);
			cfj8 = bqb.d_(fu7);
			bba bba9 = this.a(bqb, fz6, fu7);
			int integer10 = Math.max(bba9 == null ? Integer.MIN_VALUE : bba9.q(), cfj8.j() ? cfj8.a(bqb, fu7) : Integer.MIN_VALUE);
			if (integer10 != Integer.MIN_VALUE) {
				integer5 = integer10;
			}
		}

		return integer5;
	}

	@Nullable
	private bba a(bqb bqb, fz fz, fu fu) {
		List<bba> list5 = bqb.a(
			bba.class,
			new deg((double)fu.u(), (double)fu.v(), (double)fu.w(), (double)(fu.u() + 1), (double)(fu.v() + 1), (double)(fu.w() + 1)),
			bba -> bba != null && bba.bY() == fz
		);
		return list5.size() == 1 ? (bba)list5.get(0) : null;
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if (!bec.bJ.e) {
			return ang.PASS;
		} else {
			cfj = cfj.a(a);
			float float8 = cfj.c(a) == cgc.SUBTRACT ? 0.55F : 0.5F;
			bqb.a(bec, fu, acl.bT, acm.BLOCKS, 0.3F, float8);
			bqb.a(fu, cfj, 2);
			this.f(bqb, fu, cfj);
			return ang.a(bqb.v);
		}
	}

	@Override
	protected void c(bqb bqb, fu fu, cfj cfj) {
		if (!bqb.G().b(fu, this)) {
			int integer5 = this.e(bqb, fu, cfj);
			cdl cdl6 = bqb.c(fu);
			int integer7 = cdl6 instanceof cdr ? ((cdr)cdl6).d() : 0;
			if (integer5 != integer7 || (Boolean)cfj.c(c) != this.a(bqb, fu, cfj)) {
				bqt bqt8 = this.c(bqb, fu, cfj) ? bqt.HIGH : bqt.NORMAL;
				bqb.G().a(fu, this, 2, bqt8);
			}
		}
	}

	private void f(bqb bqb, fu fu, cfj cfj) {
		int integer5 = this.e(bqb, fu, cfj);
		cdl cdl6 = bqb.c(fu);
		int integer7 = 0;
		if (cdl6 instanceof cdr) {
			cdr cdr8 = (cdr)cdl6;
			integer7 = cdr8.d();
			cdr8.a(integer5);
		}

		if (integer7 != integer5 || cfj.c(a) == cgc.COMPARE) {
			boolean boolean8 = this.a(bqb, fu, cfj);
			boolean boolean9 = (Boolean)cfj.c(c);
			if (boolean9 && !boolean8) {
				bqb.a(fu, cfj.a(c, Boolean.valueOf(false)), 2);
			} else if (!boolean9 && boolean8) {
				bqb.a(fu, cfj.a(c, Boolean.valueOf(true)), 2);
			}

			this.d(bqb, fu, cfj);
		}
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		this.f(zd, fu, cfj);
	}

	@Override
	public boolean a(cfj cfj, bqb bqb, fu fu, int integer4, int integer5) {
		super.a(cfj, bqb, fu, integer4, integer5);
		cdl cdl7 = bqb.c(fu);
		return cdl7 != null && cdl7.a_(integer4, integer5);
	}

	@Override
	public cdl a(bpg bpg) {
		return new cdr();
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(aq, bwm.a, c);
	}
}
