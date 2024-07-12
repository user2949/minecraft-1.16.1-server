import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

public class cag extends bvr {
	public static final cgg<cgn> a = cfz.X;
	public static final cgg<cgn> b = cfz.W;
	public static final cgg<cgn> c = cfz.Y;
	public static final cgg<cgn> d = cfz.Z;
	public static final cgi e = cfz.az;
	public static final Map<fz, cgg<cgn>> f = Maps.newEnumMap(ImmutableMap.of(fz.NORTH, a, fz.EAST, b, fz.SOUTH, c, fz.WEST, d));
	private static final dfg g = bvr.a(3.0, 0.0, 3.0, 13.0, 1.0, 13.0);
	private static final Map<fz, dfg> h = Maps.newEnumMap(
		ImmutableMap.of(
			fz.NORTH,
			bvr.a(3.0, 0.0, 0.0, 13.0, 1.0, 13.0),
			fz.SOUTH,
			bvr.a(3.0, 0.0, 3.0, 13.0, 1.0, 16.0),
			fz.EAST,
			bvr.a(3.0, 0.0, 3.0, 16.0, 1.0, 13.0),
			fz.WEST,
			bvr.a(0.0, 0.0, 3.0, 13.0, 1.0, 13.0)
		)
	);
	private static final Map<fz, dfg> i = Maps.newEnumMap(
		ImmutableMap.of(
			fz.NORTH,
			dfd.a((dfg)h.get(fz.NORTH), bvr.a(3.0, 0.0, 0.0, 13.0, 16.0, 1.0)),
			fz.SOUTH,
			dfd.a((dfg)h.get(fz.SOUTH), bvr.a(3.0, 0.0, 15.0, 13.0, 16.0, 16.0)),
			fz.EAST,
			dfd.a((dfg)h.get(fz.EAST), bvr.a(15.0, 0.0, 3.0, 16.0, 16.0, 13.0)),
			fz.WEST,
			dfd.a((dfg)h.get(fz.WEST), bvr.a(0.0, 0.0, 3.0, 1.0, 16.0, 13.0))
		)
	);
	private final Map<cfj, dfg> j = Maps.<cfj, dfg>newHashMap();
	private static final g[] k = new g[16];
	private final cfj o;
	private boolean p = true;

	public cag(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, cgn.NONE).a(b, cgn.NONE).a(cag.c, cgn.NONE).a(d, cgn.NONE).a(e, Integer.valueOf(0)));
		this.o = this.n().a(a, cgn.SIDE).a(b, cgn.SIDE).a(cag.c, cgn.SIDE).a(d, cgn.SIDE);

		for (cfj cfj4 : this.m().a()) {
			if ((Integer)cfj4.c(e) == 0) {
				this.j.put(cfj4, this.l(cfj4));
			}
		}
	}

	private dfg l(cfj cfj) {
		dfg dfg3 = g;

		for (fz fz5 : fz.c.HORIZONTAL) {
			cgn cgn6 = cfj.c((cgl<cgn>)f.get(fz5));
			if (cgn6 == cgn.SIDE) {
				dfg3 = dfd.a(dfg3, (dfg)h.get(fz5));
			} else if (cgn6 == cgn.UP) {
				dfg3 = dfd.a(dfg3, (dfg)i.get(fz5));
			}
		}

		return dfg3;
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return (dfg)this.j.get(cfj.a(e, Integer.valueOf(0)));
	}

	@Override
	public cfj a(bin bin) {
		return this.a(bin.o(), this.o, bin.a());
	}

	private cfj a(bpg bpg, cfj cfj, fu fu) {
		boolean boolean5 = n(cfj);
		cfj = this.b(bpg, this.n().a(e, cfj.c(e)), fu);
		if (boolean5 && n(cfj)) {
			return cfj;
		} else {
			boolean boolean6 = ((cgn)cfj.c(a)).b();
			boolean boolean7 = ((cgn)cfj.c(c)).b();
			boolean boolean8 = ((cgn)cfj.c(b)).b();
			boolean boolean9 = ((cgn)cfj.c(d)).b();
			boolean boolean10 = !boolean6 && !boolean7;
			boolean boolean11 = !boolean8 && !boolean9;
			if (!boolean9 && boolean10) {
				cfj = cfj.a(d, cgn.SIDE);
			}

			if (!boolean8 && boolean10) {
				cfj = cfj.a(b, cgn.SIDE);
			}

			if (!boolean6 && boolean11) {
				cfj = cfj.a(a, cgn.SIDE);
			}

			if (!boolean7 && boolean11) {
				cfj = cfj.a(c, cgn.SIDE);
			}

			return cfj;
		}
	}

	private cfj b(bpg bpg, cfj cfj, fu fu) {
		boolean boolean5 = !bpg.d_(fu.b()).g(bpg, fu);

		for (fz fz7 : fz.c.HORIZONTAL) {
			if (!((cgn)cfj.c((cgl)f.get(fz7))).b()) {
				cgn cgn8 = this.a(bpg, fu, fz7, boolean5);
				cfj = cfj.a((cgl)f.get(fz7), cgn8);
			}
		}

		return cfj;
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if (fz == fz.DOWN) {
			return cfj1;
		} else if (fz == fz.UP) {
			return this.a(bqc, cfj1, fu5);
		} else {
			cgn cgn8 = this.a(bqc, fu5, fz);
			return cgn8.b() == ((cgn)cfj1.c((cgl)f.get(fz))).b() && !m(cfj1)
				? cfj1.a((cgl)f.get(fz), cgn8)
				: this.a(bqc, this.o.a(e, cfj1.c(e)).a((cgl)f.get(fz), cgn8), fu5);
		}
	}

	private static boolean m(cfj cfj) {
		return ((cgn)cfj.c(a)).b() && ((cgn)cfj.c(c)).b() && ((cgn)cfj.c(b)).b() && ((cgn)cfj.c(d)).b();
	}

	private static boolean n(cfj cfj) {
		return !((cgn)cfj.c(a)).b() && !((cgn)cfj.c(c)).b() && !((cgn)cfj.c(b)).b() && !((cgn)cfj.c(d)).b();
	}

	@Override
	public void a(cfj cfj, bqc bqc, fu fu, int integer4, int integer5) {
		fu.a a7 = new fu.a();

		for (fz fz9 : fz.c.HORIZONTAL) {
			cgn cgn10 = cfj.c((cgl<cgn>)f.get(fz9));
			if (cgn10 != cgn.NONE && !bqc.d_(a7.a(fu, fz9)).a(this)) {
				a7.c(fz.DOWN);
				cfj cfj11 = bqc.d_(a7);
				if (!cfj11.a(bvs.iO)) {
					fu fu12 = a7.a(fz9.f());
					cfj cfj13 = cfj11.a(fz9.f(), bqc.d_(fu12), bqc, a7, fu12);
					a(cfj11, cfj13, bqc, a7, integer4, integer5);
				}

				a7.a(fu, fz9).c(fz.UP);
				cfj cfj12 = bqc.d_(a7);
				if (!cfj12.a(bvs.iO)) {
					fu fu13 = a7.a(fz9.f());
					cfj cfj14 = cfj12.a(fz9.f(), bqc.d_(fu13), bqc, a7, fu13);
					a(cfj12, cfj14, bqc, a7, integer4, integer5);
				}
			}
		}
	}

	private cgn a(bpg bpg, fu fu, fz fz) {
		return this.a(bpg, fu, fz, !bpg.d_(fu.b()).g(bpg, fu));
	}

	private cgn a(bpg bpg, fu fu, fz fz, boolean boolean4) {
		fu fu6 = fu.a(fz);
		cfj cfj7 = bpg.d_(fu6);
		if (boolean4) {
			boolean boolean8 = this.b(bpg, fu6, cfj7);
			if (boolean8 && h(bpg.d_(fu6.b()))) {
				if (cfj7.d(bpg, fu6, fz.f())) {
					return cgn.UP;
				}

				return cgn.SIDE;
			}
		}

		return !a(cfj7, fz) && (cfj7.g(bpg, fu6) || !h(bpg.d_(fu6.c()))) ? cgn.NONE : cgn.SIDE;
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		fu fu5 = fu.c();
		cfj cfj6 = bqd.d_(fu5);
		return this.b(bqd, fu5, cfj6);
	}

	private boolean b(bpg bpg, fu fu, cfj cfj) {
		return cfj.d(bpg, fu, fz.UP) || cfj.a(bvs.fy);
	}

	private void a(bqb bqb, fu fu, cfj cfj) {
		int integer5 = this.a(bqb, fu);
		if ((Integer)cfj.c(e) != integer5) {
			if (bqb.d_(fu) == cfj) {
				bqb.a(fu, cfj.a(e, Integer.valueOf(integer5)), 2);
			}

			Set<fu> set6 = Sets.<fu>newHashSet();
			set6.add(fu);

			for (fz fz10 : fz.values()) {
				set6.add(fu.a(fz10));
			}

			for (fu fu8 : set6) {
				bqb.b(fu8, this);
			}
		}
	}

	private int a(bqb bqb, fu fu) {
		this.p = false;
		int integer4 = bqb.s(fu);
		this.p = true;
		int integer5 = 0;
		if (integer4 < 15) {
			for (fz fz7 : fz.c.HORIZONTAL) {
				fu fu8 = fu.a(fz7);
				cfj cfj9 = bqb.d_(fu8);
				integer5 = Math.max(integer5, this.o(cfj9));
				fu fu10 = fu.b();
				if (cfj9.g(bqb, fu8) && !bqb.d_(fu10).g(bqb, fu10)) {
					integer5 = Math.max(integer5, this.o(bqb.d_(fu8.b())));
				} else if (!cfj9.g(bqb, fu8)) {
					integer5 = Math.max(integer5, this.o(bqb.d_(fu8.c())));
				}
			}
		}

		return Math.max(integer4, integer5 - 1);
	}

	private int o(cfj cfj) {
		return cfj.a(this) ? (Integer)cfj.c(e) : 0;
	}

	private void b(bqb bqb, fu fu) {
		if (bqb.d_(fu).a(this)) {
			bqb.b(fu, this);

			for (fz fz7 : fz.values()) {
				bqb.b(fu.a(fz7), this);
			}
		}
	}

	@Override
	public void b(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!cfj4.a(cfj1.b()) && !bqb.v) {
			this.a(bqb, fu, cfj1);

			for (fz fz8 : fz.c.VERTICAL) {
				bqb.b(fu.a(fz8), this);
			}

			this.d(bqb, fu);
		}
	}

	@Override
	public void a(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!boolean5 && !cfj1.a(cfj4.b())) {
			super.a(cfj1, bqb, fu, cfj4, boolean5);
			if (!bqb.v) {
				for (fz fz10 : fz.values()) {
					bqb.b(fu.a(fz10), this);
				}

				this.a(bqb, fu, cfj1);
				this.d(bqb, fu);
			}
		}
	}

	private void d(bqb bqb, fu fu) {
		for (fz fz5 : fz.c.HORIZONTAL) {
			this.b(bqb, fu.a(fz5));
		}

		for (fz fz5 : fz.c.HORIZONTAL) {
			fu fu6 = fu.a(fz5);
			if (bqb.d_(fu6).g(bqb, fu6)) {
				this.b(bqb, fu6.b());
			} else {
				this.b(bqb, fu6.c());
			}
		}
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu3, bvr bvr, fu fu5, boolean boolean6) {
		if (!bqb.v) {
			if (cfj.a((bqd)bqb, fu3)) {
				this.a(bqb, fu3, cfj);
			} else {
				c(cfj, bqb, fu3);
				bqb.a(fu3, false);
			}
		}
	}

	@Override
	public int b(cfj cfj, bpg bpg, fu fu, fz fz) {
		return !this.p ? 0 : cfj.b(bpg, fu, fz);
	}

	@Override
	public int a(cfj cfj, bpg bpg, fu fu, fz fz) {
		if (this.p && fz != fz.DOWN) {
			int integer6 = (Integer)cfj.c(e);
			if (integer6 == 0) {
				return 0;
			} else {
				return fz != fz.UP && !((cgn)this.a(bpg, cfj, fu).c((cgl)f.get(fz.f()))).b() ? 0 : integer6;
			}
		} else {
			return 0;
		}
	}

	protected static boolean h(cfj cfj) {
		return a(cfj, null);
	}

	protected static boolean a(cfj cfj, @Nullable fz fz) {
		if (cfj.a(bvs.bS)) {
			return true;
		} else if (cfj.a(bvs.cX)) {
			fz fz3 = cfj.c(cal.aq);
			return fz3 == fz || fz3.f() == fz;
		} else {
			return cfj.a(bvs.iO) ? fz == cfj.c(bzt.a) : cfj.i() && fz != null;
		}
	}

	@Override
	public boolean b_(cfj cfj) {
		return this.p;
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		switch (cap) {
			case CLOCKWISE_180:
				return cfj.a(a, cfj.c(c)).a(b, cfj.c(d)).a(c, cfj.c(a)).a(d, cfj.c(b));
			case COUNTERCLOCKWISE_90:
				return cfj.a(a, cfj.c(b)).a(b, cfj.c(c)).a(c, cfj.c(d)).a(d, cfj.c(a));
			case CLOCKWISE_90:
				return cfj.a(a, cfj.c(d)).a(b, cfj.c(a)).a(c, cfj.c(b)).a(d, cfj.c(c));
			default:
				return cfj;
		}
	}

	@Override
	public cfj a(cfj cfj, bzj bzj) {
		switch (bzj) {
			case LEFT_RIGHT:
				return cfj.a(a, cfj.c(c)).a(c, cfj.c(a));
			case FRONT_BACK:
				return cfj.a(b, cfj.c(d)).a(d, cfj.c(b));
			default:
				return super.a(cfj, bzj);
		}
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(cag.a, b, c, d, e);
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if (!bec.bJ.e) {
			return ang.PASS;
		} else {
			if (m(cfj) || n(cfj)) {
				cfj cfj8 = m(cfj) ? this.n() : this.o;
				cfj8 = cfj8.a(e, cfj.c(e));
				cfj8 = this.a(bqb, cfj8, fu);
				if (cfj8 != cfj) {
					bqb.a(fu, cfj8, 3);
					this.a(bqb, fu, cfj, cfj8);
					return ang.SUCCESS;
				}
			}

			return ang.PASS;
		}
	}

	private void a(bqb bqb, fu fu, cfj cfj3, cfj cfj4) {
		for (fz fz7 : fz.c.HORIZONTAL) {
			fu fu8 = fu.a(fz7);
			if (((cgn)cfj3.c((cgl)f.get(fz7))).b() != ((cgn)cfj4.c((cgl)f.get(fz7))).b() && bqb.d_(fu8).g(bqb, fu8)) {
				bqb.a(fu8, cfj4.b(), fz7.f());
			}
		}
	}

	static {
		for (int integer1 = 0; integer1 <= 15; integer1++) {
			float float2 = (float)integer1 / 15.0F;
			float float3 = float2 * 0.6F + (float2 > 0.0F ? 0.4F : 0.3F);
			float float4 = aec.a(float2 * float2 * 0.7F - 0.5F, 0.0F, 1.0F);
			float float5 = aec.a(float2 * float2 * 0.6F - 0.7F, 0.0F, 1.0F);
			k[integer1] = new g(float3, float4, float5);
		}
	}
}
