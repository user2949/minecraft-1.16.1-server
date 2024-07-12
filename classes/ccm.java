import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;

public class ccm extends bvr implements cax {
	public static final cga a = cfz.G;
	public static final cgg<cgr> b = cfz.S;
	public static final cgg<cgr> c = cfz.T;
	public static final cgg<cgr> d = cfz.U;
	public static final cgg<cgr> e = cfz.V;
	public static final cga f = cfz.C;
	private final Map<cfj, dfg> g;
	private final Map<cfj, dfg> h;
	private static final dfg i = bvr.a(7.0, 0.0, 7.0, 9.0, 16.0, 9.0);
	private static final dfg j = bvr.a(7.0, 0.0, 0.0, 9.0, 16.0, 9.0);
	private static final dfg k = bvr.a(7.0, 0.0, 7.0, 9.0, 16.0, 16.0);
	private static final dfg o = bvr.a(0.0, 0.0, 7.0, 9.0, 16.0, 9.0);
	private static final dfg p = bvr.a(7.0, 0.0, 7.0, 16.0, 16.0, 9.0);

	public ccm(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, Boolean.valueOf(true)).a(ccm.c, cgr.NONE).a(b, cgr.NONE).a(d, cgr.NONE).a(e, cgr.NONE).a(f, Boolean.valueOf(false)));
		this.g = this.a(4.0F, 3.0F, 16.0F, 0.0F, 14.0F, 16.0F);
		this.h = this.a(4.0F, 3.0F, 24.0F, 0.0F, 24.0F, 24.0F);
	}

	private static dfg a(dfg dfg1, cgr cgr, dfg dfg3, dfg dfg4) {
		if (cgr == cgr.TALL) {
			return dfd.a(dfg1, dfg4);
		} else {
			return cgr == cgr.LOW ? dfd.a(dfg1, dfg3) : dfg1;
		}
	}

	private Map<cfj, dfg> a(float float1, float float2, float float3, float float4, float float5, float float6) {
		float float8 = 8.0F - float1;
		float float9 = 8.0F + float1;
		float float10 = 8.0F - float2;
		float float11 = 8.0F + float2;
		dfg dfg12 = bvr.a((double)float8, 0.0, (double)float8, (double)float9, (double)float3, (double)float9);
		dfg dfg13 = bvr.a((double)float10, (double)float4, 0.0, (double)float11, (double)float5, (double)float11);
		dfg dfg14 = bvr.a((double)float10, (double)float4, (double)float10, (double)float11, (double)float5, 16.0);
		dfg dfg15 = bvr.a(0.0, (double)float4, (double)float10, (double)float11, (double)float5, (double)float11);
		dfg dfg16 = bvr.a((double)float10, (double)float4, (double)float10, 16.0, (double)float5, (double)float11);
		dfg dfg17 = bvr.a((double)float10, (double)float4, 0.0, (double)float11, (double)float6, (double)float11);
		dfg dfg18 = bvr.a((double)float10, (double)float4, (double)float10, (double)float11, (double)float6, 16.0);
		dfg dfg19 = bvr.a(0.0, (double)float4, (double)float10, (double)float11, (double)float6, (double)float11);
		dfg dfg20 = bvr.a((double)float10, (double)float4, (double)float10, 16.0, (double)float6, (double)float11);
		Builder<cfj, dfg> builder21 = ImmutableMap.builder();

		for (Boolean boolean23 : a.a()) {
			for (cgr cgr25 : b.a()) {
				for (cgr cgr27 : c.a()) {
					for (cgr cgr29 : e.a()) {
						for (cgr cgr31 : d.a()) {
							dfg dfg32 = dfd.a();
							dfg32 = a(dfg32, cgr25, dfg16, dfg20);
							dfg32 = a(dfg32, cgr29, dfg15, dfg19);
							dfg32 = a(dfg32, cgr27, dfg13, dfg17);
							dfg32 = a(dfg32, cgr31, dfg14, dfg18);
							if (boolean23) {
								dfg32 = dfd.a(dfg32, dfg12);
							}

							cfj cfj33 = this.n().a(a, boolean23).a(b, cgr25).a(e, cgr29).a(c, cgr27).a(d, cgr31);
							builder21.put(cfj33.a(f, Boolean.valueOf(false)), dfg32);
							builder21.put(cfj33.a(f, Boolean.valueOf(true)), dfg32);
						}
					}
				}
			}
		}

		return builder21.build();
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return (dfg)this.g.get(cfj);
	}

	@Override
	public dfg c(cfj cfj, bpg bpg, fu fu, der der) {
		return (dfg)this.h.get(cfj);
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}

	private boolean a(cfj cfj, boolean boolean2, fz fz) {
		bvr bvr5 = cfj.b();
		boolean boolean6 = bvr5 instanceof bxu && bxu.a(cfj, fz);
		return cfj.a(acx.E) || !b(bvr5) && boolean2 || bvr5 instanceof byt || boolean6;
	}

	@Override
	public cfj a(bin bin) {
		bqd bqd3 = bin.o();
		fu fu4 = bin.a();
		cxa cxa5 = bin.o().b(bin.a());
		fu fu6 = fu4.d();
		fu fu7 = fu4.g();
		fu fu8 = fu4.e();
		fu fu9 = fu4.f();
		fu fu10 = fu4.b();
		cfj cfj11 = bqd3.d_(fu6);
		cfj cfj12 = bqd3.d_(fu7);
		cfj cfj13 = bqd3.d_(fu8);
		cfj cfj14 = bqd3.d_(fu9);
		cfj cfj15 = bqd3.d_(fu10);
		boolean boolean16 = this.a(cfj11, cfj11.d(bqd3, fu6, fz.SOUTH), fz.SOUTH);
		boolean boolean17 = this.a(cfj12, cfj12.d(bqd3, fu7, fz.WEST), fz.WEST);
		boolean boolean18 = this.a(cfj13, cfj13.d(bqd3, fu8, fz.NORTH), fz.NORTH);
		boolean boolean19 = this.a(cfj14, cfj14.d(bqd3, fu9, fz.EAST), fz.EAST);
		cfj cfj20 = this.n().a(f, Boolean.valueOf(cxa5.a() == cxb.c));
		return this.a(bqd3, cfj20, fu10, cfj15, boolean16, boolean17, boolean18, boolean19);
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if ((Boolean)cfj1.c(f)) {
			bqc.F().a(fu5, cxb.c, cxb.c.a(bqc));
		}

		if (fz == fz.DOWN) {
			return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
		} else {
			return fz == fz.UP ? this.a(bqc, cfj1, fu6, cfj3) : this.a(bqc, fu5, cfj1, fu6, cfj3, fz);
		}
	}

	private static boolean a(cfj cfj, cgl<cgr> cgl) {
		return cfj.c(cgl) != cgr.NONE;
	}

	private static boolean a(dfg dfg1, dfg dfg2) {
		return !dfd.c(dfg2, dfg1, deq.e);
	}

	private cfj a(bqd bqd, cfj cfj2, fu fu, cfj cfj4) {
		boolean boolean6 = a(cfj2, c);
		boolean boolean7 = a(cfj2, b);
		boolean boolean8 = a(cfj2, d);
		boolean boolean9 = a(cfj2, e);
		return this.a(bqd, cfj2, fu, cfj4, boolean6, boolean7, boolean8, boolean9);
	}

	private cfj a(bqd bqd, fu fu2, cfj cfj3, fu fu4, cfj cfj5, fz fz) {
		fz fz8 = fz.f();
		boolean boolean9 = fz == fz.NORTH ? this.a(cfj5, cfj5.d(bqd, fu4, fz8), fz8) : a(cfj3, c);
		boolean boolean10 = fz == fz.EAST ? this.a(cfj5, cfj5.d(bqd, fu4, fz8), fz8) : a(cfj3, b);
		boolean boolean11 = fz == fz.SOUTH ? this.a(cfj5, cfj5.d(bqd, fu4, fz8), fz8) : a(cfj3, d);
		boolean boolean12 = fz == fz.WEST ? this.a(cfj5, cfj5.d(bqd, fu4, fz8), fz8) : a(cfj3, e);
		fu fu13 = fu2.b();
		cfj cfj14 = bqd.d_(fu13);
		return this.a(bqd, cfj3, fu13, cfj14, boolean9, boolean10, boolean11, boolean12);
	}

	private cfj a(bqd bqd, cfj cfj2, fu fu, cfj cfj4, boolean boolean5, boolean boolean6, boolean boolean7, boolean boolean8) {
		dfg dfg10 = cfj4.k(bqd, fu).a(fz.DOWN);
		cfj cfj11 = this.a(cfj2, boolean5, boolean6, boolean7, boolean8, dfg10);
		return cfj11.a(a, Boolean.valueOf(this.a(cfj11, cfj4, dfg10)));
	}

	private boolean a(cfj cfj1, cfj cfj2, dfg dfg) {
		boolean boolean5 = cfj2.b() instanceof ccm && (Boolean)cfj2.c(a);
		if (boolean5) {
			return true;
		} else {
			cgr cgr6 = cfj1.c(c);
			cgr cgr7 = cfj1.c(d);
			cgr cgr8 = cfj1.c(b);
			cgr cgr9 = cfj1.c(e);
			boolean boolean10 = cgr7 == cgr.NONE;
			boolean boolean11 = cgr9 == cgr.NONE;
			boolean boolean12 = cgr8 == cgr.NONE;
			boolean boolean13 = cgr6 == cgr.NONE;
			boolean boolean14 = boolean13 && boolean10 && boolean11 && boolean12 || boolean13 != boolean10 || boolean11 != boolean12;
			if (boolean14) {
				return true;
			} else {
				boolean boolean15 = cgr6 == cgr.TALL && cgr7 == cgr.TALL || cgr8 == cgr.TALL && cgr9 == cgr.TALL;
				return boolean15 ? false : cfj2.b().a(acx.ar) || a(dfg, i);
			}
		}
	}

	private cfj a(cfj cfj, boolean boolean2, boolean boolean3, boolean boolean4, boolean boolean5, dfg dfg) {
		return cfj.a(c, this.a(boolean2, dfg, j)).a(b, this.a(boolean3, dfg, p)).a(d, this.a(boolean4, dfg, k)).a(e, this.a(boolean5, dfg, o));
	}

	private cgr a(boolean boolean1, dfg dfg2, dfg dfg3) {
		if (boolean1) {
			return a(dfg2, dfg3) ? cgr.TALL : cgr.LOW;
		} else {
			return cgr.NONE;
		}
	}

	@Override
	public cxa d(cfj cfj) {
		return cfj.c(f) ? cxb.c.a(false) : super.d(cfj);
	}

	@Override
	public boolean b(cfj cfj, bpg bpg, fu fu) {
		return !(Boolean)cfj.c(f);
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(ccm.a, c, b, e, d, f);
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		switch (cap) {
			case CLOCKWISE_180:
				return cfj.a(c, cfj.c(d)).a(b, cfj.c(e)).a(d, cfj.c(c)).a(e, cfj.c(b));
			case COUNTERCLOCKWISE_90:
				return cfj.a(c, cfj.c(b)).a(b, cfj.c(d)).a(d, cfj.c(e)).a(e, cfj.c(c));
			case CLOCKWISE_90:
				return cfj.a(c, cfj.c(e)).a(b, cfj.c(c)).a(d, cfj.c(b)).a(e, cfj.c(d));
			default:
				return cfj;
		}
	}

	@Override
	public cfj a(cfj cfj, bzj bzj) {
		switch (bzj) {
			case LEFT_RIGHT:
				return cfj.a(c, cfj.c(d)).a(d, cfj.c(c));
			case FRONT_BACK:
				return cfj.a(b, cfj.c(e)).a(e, cfj.c(b));
			default:
				return super.a(cfj, bzj);
		}
	}
}
