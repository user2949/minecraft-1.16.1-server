import java.util.Random;
import javax.annotation.Nullable;

public class bzb extends bvg {
	public static final cgd a = byp.aq;
	public static final cga b = cfz.w;
	public static final cga c = cfz.o;
	public static final dfg d = bvr.a(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
	public static final dfg e = bvr.a(4.0, 2.0, 4.0, 12.0, 14.0, 12.0);
	public static final dfg f = dfd.a(d, e);
	public static final dfg g = bvr.a(0.0, 15.0, 0.0, 16.0, 15.0, 16.0);
	public static final dfg h = dfd.a(f, g);
	public static final dfg i = dfd.a(
		bvr.a(1.0, 10.0, 0.0, 5.333333, 14.0, 16.0), bvr.a(5.333333, 12.0, 0.0, 9.666667, 16.0, 16.0), bvr.a(9.666667, 14.0, 0.0, 14.0, 18.0, 16.0), f
	);
	public static final dfg j = dfd.a(
		bvr.a(0.0, 10.0, 1.0, 16.0, 14.0, 5.333333), bvr.a(0.0, 12.0, 5.333333, 16.0, 16.0, 9.666667), bvr.a(0.0, 14.0, 9.666667, 16.0, 18.0, 14.0), f
	);
	public static final dfg k = dfd.a(
		bvr.a(15.0, 10.0, 0.0, 10.666667, 14.0, 16.0), bvr.a(10.666667, 12.0, 0.0, 6.333333, 16.0, 16.0), bvr.a(6.333333, 14.0, 0.0, 2.0, 18.0, 16.0), f
	);
	public static final dfg o = dfd.a(
		bvr.a(0.0, 10.0, 15.0, 16.0, 14.0, 10.666667), bvr.a(0.0, 12.0, 10.666667, 16.0, 16.0, 6.333333), bvr.a(0.0, 14.0, 6.333333, 16.0, 18.0, 2.0), f
	);

	protected bzb(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, fz.NORTH).a(b, Boolean.valueOf(false)).a(bzb.c, Boolean.valueOf(false)));
	}

	@Override
	public cak b(cfj cfj) {
		return cak.MODEL;
	}

	@Override
	public dfg d(cfj cfj, bpg bpg, fu fu) {
		return f;
	}

	@Override
	public boolean c_(cfj cfj) {
		return true;
	}

	@Override
	public cfj a(bin bin) {
		bqb bqb3 = bin.o();
		bki bki4 = bin.l();
		le le5 = bki4.o();
		bec bec6 = bin.m();
		boolean boolean7 = false;
		if (!bqb3.v && bec6 != null && le5 != null && bec6.eV() && le5.e("BlockEntityTag")) {
			le le8 = le5.p("BlockEntityTag");
			if (le8.e("Book")) {
				boolean7 = true;
			}
		}

		return this.n().a(a, bin.f().f()).a(c, Boolean.valueOf(boolean7));
	}

	@Override
	public dfg c(cfj cfj, bpg bpg, fu fu, der der) {
		return h;
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		switch ((fz)cfj.c(a)) {
			case NORTH:
				return j;
			case SOUTH:
				return o;
			case EAST:
				return k;
			case WEST:
				return i;
			default:
				return f;
		}
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
		a.a(bzb.a, b, c);
	}

	@Nullable
	@Override
	public cdl a(bpg bpg) {
		return new ced();
	}

	public static boolean a(bqb bqb, fu fu, cfj cfj, bki bki) {
		if (!(Boolean)cfj.c(c)) {
			if (!bqb.v) {
				b(bqb, fu, cfj, bki);
			}

			return true;
		} else {
			return false;
		}
	}

	private static void b(bqb bqb, fu fu, cfj cfj, bki bki) {
		cdl cdl5 = bqb.c(fu);
		if (cdl5 instanceof ced) {
			ced ced6 = (ced)cdl5;
			ced6.a(bki.a(1));
			a(bqb, fu, cfj, true);
			bqb.a(null, fu, acl.aY, acm.BLOCKS, 1.0F, 1.0F);
		}
	}

	public static void a(bqb bqb, fu fu, cfj cfj, boolean boolean4) {
		bqb.a(fu, cfj.a(b, Boolean.valueOf(false)).a(c, Boolean.valueOf(boolean4)), 3);
		b(bqb, fu, cfj);
	}

	public static void a(bqb bqb, fu fu, cfj cfj) {
		b(bqb, fu, cfj, true);
		bqb.G().a(fu, cfj.b(), 2);
		bqb.c(1043, fu, 0);
	}

	private static void b(bqb bqb, fu fu, cfj cfj, boolean boolean4) {
		bqb.a(fu, cfj.a(b, Boolean.valueOf(boolean4)), 3);
		b(bqb, fu, cfj);
	}

	private static void b(bqb bqb, fu fu, cfj cfj) {
		bqb.b(fu.c(), cfj.b());
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		b(zd, fu, cfj, false);
	}

	@Override
	public void a(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!cfj1.a(cfj4.b())) {
			if ((Boolean)cfj1.c(c)) {
				this.d(cfj1, bqb, fu);
			}

			if ((Boolean)cfj1.c(b)) {
				bqb.b(fu.c(), this);
			}

			super.a(cfj1, bqb, fu, cfj4, boolean5);
		}
	}

	private void d(cfj cfj, bqb bqb, fu fu) {
		cdl cdl5 = bqb.c(fu);
		if (cdl5 instanceof ced) {
			ced ced6 = (ced)cdl5;
			fz fz7 = cfj.c(a);
			bki bki8 = ced6.f().i();
			float float9 = 0.25F * (float)fz7.i();
			float float10 = 0.25F * (float)fz7.k();
			bbg bbg11 = new bbg(bqb, (double)fu.u() + 0.5 + (double)float9, (double)(fu.v() + 1), (double)fu.w() + 0.5 + (double)float10, bki8);
			bbg11.m();
			bqb.c(bbg11);
			ced6.aa_();
		}
	}

	@Override
	public boolean b_(cfj cfj) {
		return true;
	}

	@Override
	public int a(cfj cfj, bpg bpg, fu fu, fz fz) {
		return cfj.c(b) ? 15 : 0;
	}

	@Override
	public int b(cfj cfj, bpg bpg, fu fu, fz fz) {
		return fz == fz.UP && cfj.c(b) ? 15 : 0;
	}

	@Override
	public boolean a(cfj cfj) {
		return true;
	}

	@Override
	public int a(cfj cfj, bqb bqb, fu fu) {
		if ((Boolean)cfj.c(c)) {
			cdl cdl5 = bqb.c(fu);
			if (cdl5 instanceof ced) {
				return ((ced)cdl5).j();
			}
		}

		return 0;
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if ((Boolean)cfj.c(c)) {
			if (!bqb.v) {
				this.a(bqb, fu, bec);
			}

			return ang.a(bqb.v);
		} else {
			bki bki8 = bec.b(anf);
			return !bki8.a() && !bki8.b().a(ada.Y) ? ang.CONSUME : ang.PASS;
		}
	}

	@Nullable
	@Override
	public anj b(cfj cfj, bqb bqb, fu fu) {
		return !cfj.c(c) ? null : super.b(cfj, bqb, fu);
	}

	private void a(bqb bqb, fu fu, bec bec) {
		cdl cdl5 = bqb.c(fu);
		if (cdl5 instanceof ced) {
			bec.a((ced)cdl5);
			bec.a(acu.at);
		}
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
