import com.google.common.base.MoreObjects;
import java.util.Random;
import javax.annotation.Nullable;

public class ccg extends bvr {
	public static final cgd a = byp.aq;
	public static final cga b = cfz.w;
	public static final cga c = cfz.a;
	protected static final dfg d = bvr.a(5.0, 0.0, 10.0, 11.0, 10.0, 16.0);
	protected static final dfg e = bvr.a(5.0, 0.0, 0.0, 11.0, 10.0, 6.0);
	protected static final dfg f = bvr.a(10.0, 0.0, 5.0, 16.0, 10.0, 11.0);
	protected static final dfg g = bvr.a(0.0, 0.0, 5.0, 6.0, 10.0, 11.0);

	public ccg(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, fz.NORTH).a(b, Boolean.valueOf(false)).a(ccg.c, Boolean.valueOf(false)));
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		switch ((fz)cfj.c(a)) {
			case EAST:
			default:
				return g;
			case WEST:
				return f;
			case SOUTH:
				return e;
			case NORTH:
				return d;
		}
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		fz fz5 = cfj.c(a);
		fu fu6 = fu.a(fz5.f());
		cfj cfj7 = bqd.d_(fu6);
		return fz5.n().d() && cfj7.d(bqd, fu6, fz5);
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		return fz.f() == cfj1.c(a) && !cfj1.a(bqc, fu5) ? bvs.a.n() : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Nullable
	@Override
	public cfj a(bin bin) {
		cfj cfj3 = this.n().a(b, Boolean.valueOf(false)).a(c, Boolean.valueOf(false));
		bqd bqd4 = bin.o();
		fu fu5 = bin.a();
		fz[] arr6 = bin.e();

		for (fz fz10 : arr6) {
			if (fz10.n().d()) {
				fz fz11 = fz10.f();
				cfj3 = cfj3.a(a, fz11);
				if (cfj3.a(bqd4, fu5)) {
					return cfj3;
				}
			}
		}

		return null;
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, aoy aoy, bki bki) {
		this.a(bqb, fu, cfj, false, false, -1, null);
	}

	public void a(bqb bqb, fu fu, cfj cfj3, boolean boolean4, boolean boolean5, int integer, @Nullable cfj cfj7) {
		fz fz9 = cfj3.c(a);
		boolean boolean10 = (Boolean)cfj3.c(c);
		boolean boolean11 = (Boolean)cfj3.c(b);
		boolean boolean12 = !boolean4;
		boolean boolean13 = false;
		int integer14 = 0;
		cfj[] arr15 = new cfj[42];

		for (int integer16 = 1; integer16 < 42; integer16++) {
			fu fu17 = fu.a(fz9, integer16);
			cfj cfj18 = bqb.d_(fu17);
			if (cfj18.a(bvs.el)) {
				if (cfj18.c(a) == fz9.f()) {
					integer14 = integer16;
				}
				break;
			}

			if (!cfj18.a(bvs.em) && integer16 != integer) {
				arr15[integer16] = null;
				boolean12 = false;
			} else {
				if (integer16 == integer) {
					cfj18 = MoreObjects.firstNonNull(cfj7, cfj18);
				}

				boolean boolean19 = !(Boolean)cfj18.c(ccf.c);
				boolean boolean20 = (Boolean)cfj18.c(ccf.a);
				boolean13 |= boolean19 && boolean20;
				arr15[integer16] = cfj18;
				if (integer16 == integer) {
					bqb.G().a(fu, this, 10);
					boolean12 &= boolean19;
				}
			}
		}

		boolean12 &= integer14 > 1;
		boolean13 &= boolean12;
		cfj cfj16 = this.n().a(c, Boolean.valueOf(boolean12)).a(b, Boolean.valueOf(boolean13));
		if (integer14 > 0) {
			fu fu17x = fu.a(fz9, integer14);
			fz fz18 = fz9.f();
			bqb.a(fu17x, cfj16.a(a, fz18), 3);
			this.a(bqb, fu17x, fz18);
			this.a(bqb, fu17x, boolean12, boolean13, boolean10, boolean11);
		}

		this.a(bqb, fu, boolean12, boolean13, boolean10, boolean11);
		if (!boolean4) {
			bqb.a(fu, cfj16.a(a, fz9), 3);
			if (boolean5) {
				this.a(bqb, fu, fz9);
			}
		}

		if (boolean10 != boolean12) {
			for (int integer17 = 1; integer17 < integer14; integer17++) {
				fu fu18 = fu.a(fz9, integer17);
				cfj cfj19 = arr15[integer17];
				if (cfj19 != null) {
					bqb.a(fu18, cfj19.a(c, Boolean.valueOf(boolean12)), 3);
					if (!bqb.d_(fu18).g()) {
					}
				}
			}
		}
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		this.a(zd, fu, cfj, false, true, -1, null);
	}

	private void a(bqb bqb, fu fu, boolean boolean3, boolean boolean4, boolean boolean5, boolean boolean6) {
		if (boolean4 && !boolean6) {
			bqb.a(null, fu, acl.pg, acm.BLOCKS, 0.4F, 0.6F);
		} else if (!boolean4 && boolean6) {
			bqb.a(null, fu, acl.pf, acm.BLOCKS, 0.4F, 0.5F);
		} else if (boolean3 && !boolean5) {
			bqb.a(null, fu, acl.pe, acm.BLOCKS, 0.4F, 0.7F);
		} else if (!boolean3 && boolean5) {
			bqb.a(null, fu, acl.ph, acm.BLOCKS, 0.4F, 1.2F / (bqb.t.nextFloat() * 0.2F + 0.9F));
		}
	}

	private void a(bqb bqb, fu fu, fz fz) {
		bqb.b(fu, this);
		bqb.b(fu.a(fz.f()), this);
	}

	@Override
	public void a(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!boolean5 && !cfj1.a(cfj4.b())) {
			boolean boolean7 = (Boolean)cfj1.c(c);
			boolean boolean8 = (Boolean)cfj1.c(b);
			if (boolean7 || boolean8) {
				this.a(bqb, fu, cfj1, true, false, -1, null);
			}

			if (boolean8) {
				bqb.b(fu, this);
				bqb.b(fu.a(((fz)cfj1.c(a)).f()), this);
			}

			super.a(cfj1, bqb, fu, cfj4, boolean5);
		}
	}

	@Override
	public int a(cfj cfj, bpg bpg, fu fu, fz fz) {
		return cfj.c(b) ? 15 : 0;
	}

	@Override
	public int b(cfj cfj, bpg bpg, fu fu, fz fz) {
		if (!(Boolean)cfj.c(b)) {
			return 0;
		} else {
			return cfj.c(a) == fz ? 15 : 0;
		}
	}

	@Override
	public boolean b_(cfj cfj) {
		return true;
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
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(ccg.a, b, c);
	}
}
