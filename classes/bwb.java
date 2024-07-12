import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;

public class bwb extends bvg implements cax {
	protected static final dfg a = bvr.a(0.0, 0.0, 0.0, 16.0, 7.0, 16.0);
	public static final cga b = cfz.r;
	public static final cga c = cfz.y;
	public static final cga d = cfz.C;
	public static final cgd e = cfz.O;
	private static final dfg f = bvr.a(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
	private final boolean g;
	private final int h;

	public bwb(boolean boolean1, int integer, cfi.c c) {
		super(c);
		this.g = boolean1;
		this.h = integer;
		this.j(this.n.b().a(b, Boolean.valueOf(true)).a(bwb.c, Boolean.valueOf(false)).a(d, Boolean.valueOf(false)).a(e, fz.NORTH));
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		cdl cdl8 = bqb.c(fu);
		if (cdl8 instanceof cdo) {
			cdo cdo9 = (cdo)cdl8;
			bki bki10 = bec.b(anf);
			Optional<bml> optional11 = cdo9.a(bki10);
			if (optional11.isPresent()) {
				if (!bqb.v && cdo9.a(bec.bJ.d ? bki10.i() : bki10, ((bml)optional11.get()).e())) {
					bec.a(acu.au);
					return ang.SUCCESS;
				}

				return ang.CONSUME;
			}
		}

		return ang.PASS;
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu, aom aom) {
		if (!aom.az() && (Boolean)cfj.c(b) && aom instanceof aoy && !bny.i((aoy)aom)) {
			aom.a(anw.a, (float)this.h);
		}

		super.a(cfj, bqb, fu, aom);
	}

	@Override
	public void a(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!cfj1.a(cfj4.b())) {
			cdl cdl7 = bqb.c(fu);
			if (cdl7 instanceof cdo) {
				anc.a(bqb, fu, ((cdo)cdl7).d());
			}

			super.a(cfj1, bqb, fu, cfj4, boolean5);
		}
	}

	@Nullable
	@Override
	public cfj a(bin bin) {
		bqc bqc3 = bin.o();
		fu fu4 = bin.a();
		boolean boolean5 = bqc3.b(fu4).a() == cxb.c;
		return this.n().a(d, Boolean.valueOf(boolean5)).a(c, Boolean.valueOf(this.l(bqc3.d_(fu4.c())))).a(b, Boolean.valueOf(!boolean5)).a(e, bin.f());
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if ((Boolean)cfj1.c(d)) {
			bqc.F().a(fu5, cxb.c, cxb.c.a(bqc));
		}

		return fz == fz.DOWN ? cfj1.a(c, Boolean.valueOf(this.l(cfj3))) : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	private boolean l(cfj cfj) {
		return cfj.a(bvs.gA);
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return a;
	}

	@Override
	public cak b(cfj cfj) {
		return cak.MODEL;
	}

	public static void c(bqc bqc, fu fu, cfj cfj) {
		if (bqc.s_()) {
			for (int integer4 = 0; integer4 < 20; integer4++) {
				a(bqc.n(), fu, (Boolean)cfj.c(c), true);
			}
		}

		cdl cdl4 = bqc.c(fu);
		if (cdl4 instanceof cdo) {
			((cdo)cdl4).f();
		}
	}

	@Override
	public boolean a(bqc bqc, fu fu, cfj cfj, cxa cxa) {
		if (!(Boolean)cfj.c(cfz.C) && cxa.a() == cxb.c) {
			boolean boolean6 = (Boolean)cfj.c(b);
			if (boolean6) {
				if (!bqc.s_()) {
					bqc.a(null, fu, acl.eM, acm.BLOCKS, 1.0F, 1.0F);
				}

				c(bqc, fu, cfj);
			}

			bqc.a(fu, cfj.a(d, Boolean.valueOf(true)).a(b, Boolean.valueOf(false)), 3);
			bqc.F().a(fu, cxa.a(), cxa.a().a(bqc));
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void a(bqb bqb, cfj cfj, deh deh, bes bes) {
		if (!bqb.v && bes.bm()) {
			aom aom6 = bes.v();
			boolean boolean7 = aom6 == null || aom6 instanceof bec || bqb.S().b(bpx.b);
			if (boolean7 && !(Boolean)cfj.c(b) && !(Boolean)cfj.c(d)) {
				fu fu8 = deh.a();
				bqb.a(fu8, cfj.a(cfz.r, Boolean.valueOf(true)), 11);
			}
		}
	}

	public static void a(bqb bqb, fu fu, boolean boolean3, boolean boolean4) {
		Random random5 = bqb.v_();
		hi hi6 = boolean3 ? hh.ah : hh.ag;
		bqb.b(
			hi6,
			true,
			(double)fu.u() + 0.5 + random5.nextDouble() / 3.0 * (double)(random5.nextBoolean() ? 1 : -1),
			(double)fu.v() + random5.nextDouble() + random5.nextDouble(),
			(double)fu.w() + 0.5 + random5.nextDouble() / 3.0 * (double)(random5.nextBoolean() ? 1 : -1),
			0.0,
			0.07,
			0.0
		);
		if (boolean4) {
			bqb.a(
				hh.S,
				(double)fu.u() + 0.25 + random5.nextDouble() / 2.0 * (double)(random5.nextBoolean() ? 1 : -1),
				(double)fu.v() + 0.4,
				(double)fu.w() + 0.25 + random5.nextDouble() / 2.0 * (double)(random5.nextBoolean() ? 1 : -1),
				0.0,
				0.005,
				0.0
			);
		}
	}

	public static boolean a(bqb bqb, fu fu) {
		for (int integer3 = 1; integer3 <= 5; integer3++) {
			fu fu4 = fu.c(integer3);
			cfj cfj5 = bqb.d_(fu4);
			if (g(cfj5)) {
				return true;
			}

			boolean boolean6 = dfd.c(f, cfj5.b(bqb, fu, der.a()), deq.i);
			if (boolean6) {
				cfj cfj7 = bqb.d_(fu4.c());
				return g(cfj7);
			}
		}

		return false;
	}

	public static boolean g(cfj cfj) {
		return cfj.b(b) && cfj.a(acx.ax) && (Boolean)cfj.c(b);
	}

	@Override
	public cxa d(cfj cfj) {
		return cfj.c(d) ? cxb.c.a(false) : super.d(cfj);
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		return cfj.a(e, cap.a(cfj.c(e)));
	}

	@Override
	public cfj a(cfj cfj, bzj bzj) {
		return cfj.a(bzj.a(cfj.c(e)));
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(b, c, d, e);
	}

	@Override
	public cdl a(bpg bpg) {
		return new cdo();
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}

	public static boolean h(cfj cfj) {
		return cfj.a(acx.ax, a -> a.b(cfz.C) && a.b(cfz.r)) && !(Boolean)cfj.c(cfz.C) && !(Boolean)cfj.c(cfz.r);
	}
}
