import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import javax.annotation.Nullable;

public abstract class cfi {
	protected static final fz[] ar = new fz[]{fz.WEST, fz.EAST, fz.NORTH, fz.SOUTH, fz.DOWN, fz.UP};
	protected final cxd as;
	protected final boolean at;
	protected final float au;
	protected final boolean av;
	protected final cbh aw;
	protected final float ax;
	protected final float ay;
	protected final float az;
	protected final boolean aA;
	protected final cfi.c aB;
	@Nullable
	protected uh aC;

	public cfi(cfi.c c) {
		this.as = c.a;
		this.at = c.c;
		this.aC = c.m;
		this.au = c.f;
		this.av = c.i;
		this.aw = c.d;
		this.ax = c.j;
		this.ay = c.k;
		this.az = c.l;
		this.aA = c.v;
		this.aB = c;
	}

	@Deprecated
	public void a(cfj cfj, bqc bqc, fu fu, int integer4, int integer5) {
	}

	@Deprecated
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		switch (czg) {
			case LAND:
				return !cfj.r(bpg, fu);
			case WATER:
				return bpg.b(fu).a(acz.a);
			case AIR:
				return !cfj.r(bpg, fu);
			default:
				return false;
		}
	}

	@Deprecated
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		return cfj1;
	}

	@Deprecated
	public void a(cfj cfj, bqb bqb, fu fu3, bvr bvr, fu fu5, boolean boolean6) {
		qy.a(bqb, fu3);
	}

	@Deprecated
	public void b(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
	}

	@Deprecated
	public void a(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (this.q() && !cfj1.a(cfj4.b())) {
			bqb.o(fu);
		}
	}

	@Deprecated
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		return ang.PASS;
	}

	@Deprecated
	public boolean a(cfj cfj, bqb bqb, fu fu, int integer4, int integer5) {
		return false;
	}

	@Deprecated
	public cak b(cfj cfj) {
		return cak.MODEL;
	}

	@Deprecated
	public boolean c_(cfj cfj) {
		return false;
	}

	@Deprecated
	public boolean b_(cfj cfj) {
		return false;
	}

	@Deprecated
	public cxf f(cfj cfj) {
		return this.as.g();
	}

	@Deprecated
	public cxa d(cfj cfj) {
		return cxb.a.h();
	}

	@Deprecated
	public boolean a(cfj cfj) {
		return false;
	}

	public cfi.b aj_() {
		return cfi.b.NONE;
	}

	@Deprecated
	public cfj a(cfj cfj, cap cap) {
		return cfj;
	}

	@Deprecated
	public cfj a(cfj cfj, bzj bzj) {
		return cfj;
	}

	@Deprecated
	public boolean a(cfj cfj, bin bin) {
		return this.as.e() && (bin.l().a() || bin.l().b() != this.h());
	}

	@Deprecated
	public boolean a(cfj cfj, cwz cwz) {
		return this.as.e() || !this.as.b();
	}

	@Deprecated
	public List<bki> a(cfj cfj, dat.a a) {
		uh uh4 = this.r();
		if (uh4 == dao.a) {
			return Collections.emptyList();
		} else {
			dat dat5 = a.a(dda.h, cfj).a(dcz.l);
			zd zd6 = dat5.c();
			daw daw7 = zd6.l().aH().a(uh4);
			return daw7.a(dat5);
		}
	}

	@Deprecated
	public dfg d(cfj cfj, bpg bpg, fu fu) {
		return cfj.j(bpg, fu);
	}

	@Deprecated
	public dfg e(cfj cfj, bpg bpg, fu fu) {
		return this.c(cfj, bpg, fu, der.a());
	}

	@Deprecated
	public dfg a_(cfj cfj, bpg bpg, fu fu) {
		return dfd.a();
	}

	@Deprecated
	public int f(cfj cfj, bpg bpg, fu fu) {
		if (cfj.i(bpg, fu)) {
			return bpg.H();
		} else {
			return cfj.a(bpg, fu) ? 0 : 1;
		}
	}

	@Nullable
	@Deprecated
	public anj b(cfj cfj, bqb bqb, fu fu) {
		return null;
	}

	@Deprecated
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		return true;
	}

	@Deprecated
	public int a(cfj cfj, bqb bqb, fu fu) {
		return 0;
	}

	@Deprecated
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return dfd.b();
	}

	@Deprecated
	public dfg c(cfj cfj, bpg bpg, fu fu, der der) {
		return this.at ? cfj.j(bpg, fu) : dfd.a();
	}

	@Deprecated
	public dfg a(cfj cfj, bpg bpg, fu fu, der der) {
		return this.c(cfj, bpg, fu, der);
	}

	@Deprecated
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		this.a(cfj, zd, fu, random);
	}

	@Deprecated
	public void a(cfj cfj, zd zd, fu fu, Random random) {
	}

	@Deprecated
	public float a(cfj cfj, bec bec, bpg bpg, fu fu) {
		float float6 = cfj.h(bpg, fu);
		if (float6 == -1.0F) {
			return 0.0F;
		} else {
			int integer7 = bec.d(cfj) ? 30 : 100;
			return bec.c(cfj) / float6 / (float)integer7;
		}
	}

	@Deprecated
	public void a(cfj cfj, bqb bqb, fu fu, bki bki) {
	}

	@Deprecated
	public void a(cfj cfj, bqb bqb, fu fu, bec bec) {
	}

	@Deprecated
	public int a(cfj cfj, bpg bpg, fu fu, fz fz) {
		return 0;
	}

	@Deprecated
	public void a(cfj cfj, bqb bqb, fu fu, aom aom) {
	}

	@Deprecated
	public int b(cfj cfj, bpg bpg, fu fu, fz fz) {
		return 0;
	}

	public final boolean q() {
		return this instanceof bxp;
	}

	public final uh r() {
		if (this.aC == null) {
			uh uh2 = gl.aj.b(this.p());
			this.aC = new uh(uh2.b(), "blocks/" + uh2.a());
		}

		return this.aC;
	}

	@Deprecated
	public void a(bqb bqb, cfj cfj, deh deh, bes bes) {
	}

	public abstract bke h();

	protected abstract bvr p();

	public cxe s() {
		return (cxe)this.aB.b.apply(this.p().n());
	}

	public abstract static class a extends cfl<bvr, cfj> {
		private final int b;
		private final boolean e;
		private final boolean f;
		private final cxd g;
		private final cxe h;
		private final float i;
		private final boolean j;
		private final boolean k;
		private final cfi.e l;
		private final cfi.e m;
		private final cfi.e n;
		private final cfi.e o;
		private final cfi.e p;
		@Nullable
		protected cfi.a.a a;

		protected a(bvr bvr, ImmutableMap<cgl<?>, Comparable<?>> immutableMap, MapCodec<cfj> mapCodec) {
			super(bvr, immutableMap, mapCodec);
			cfi.c c5 = bvr.aB;
			this.b = c5.e.applyAsInt(this.p());
			this.e = bvr.c_(this.p());
			this.f = c5.o;
			this.g = c5.a;
			this.h = (cxe)c5.b.apply(this.p());
			this.i = c5.g;
			this.j = c5.h;
			this.k = c5.n;
			this.l = c5.q;
			this.m = c5.r;
			this.n = c5.s;
			this.o = c5.t;
			this.p = c5.u;
		}

		public void a() {
			if (!this.b().o()) {
				this.a = new cfi.a.a(this.p());
			}
		}

		public bvr b() {
			return this.c;
		}

		public cxd c() {
			return this.g;
		}

		public boolean a(bpg bpg, fu fu, aoq<?> aoq) {
			return this.b().aB.p.test(this.p(), bpg, fu, aoq);
		}

		public boolean a(bpg bpg, fu fu) {
			return this.a != null ? this.a.g : this.b().b(this.p(), bpg, fu);
		}

		public int b(bpg bpg, fu fu) {
			return this.a != null ? this.a.h : this.b().f(this.p(), bpg, fu);
		}

		public dfg a(bpg bpg, fu fu, fz fz) {
			return this.a != null && this.a.i != null ? this.a.i[fz.ordinal()] : dfd.a(this.c(bpg, fu), fz);
		}

		public dfg c(bpg bpg, fu fu) {
			return this.b().d(this.p(), bpg, fu);
		}

		public boolean d() {
			return this.a == null || this.a.c;
		}

		public boolean e() {
			return this.e;
		}

		public int f() {
			return this.b;
		}

		public boolean g() {
			return this.f;
		}

		public cxe d(bpg bpg, fu fu) {
			return this.h;
		}

		public cfj a(cap cap) {
			return this.b().a(this.p(), cap);
		}

		public cfj a(bzj bzj) {
			return this.b().a(this.p(), bzj);
		}

		public cak h() {
			return this.b().b(this.p());
		}

		public boolean g(bpg bpg, fu fu) {
			return this.l.test(this.p(), bpg, fu);
		}

		public boolean i() {
			return this.b().b_(this.p());
		}

		public int b(bpg bpg, fu fu, fz fz) {
			return this.b().a(this.p(), bpg, fu, fz);
		}

		public boolean j() {
			return this.b().a(this.p());
		}

		public int a(bqb bqb, fu fu) {
			return this.b().a(this.p(), bqb, fu);
		}

		public float h(bpg bpg, fu fu) {
			return this.i;
		}

		public float a(bec bec, bpg bpg, fu fu) {
			return this.b().a(this.p(), bec, bpg, fu);
		}

		public int c(bpg bpg, fu fu, fz fz) {
			return this.b().b(this.p(), bpg, fu, fz);
		}

		public cxf k() {
			return this.b().f(this.p());
		}

		public boolean i(bpg bpg, fu fu) {
			if (this.a != null) {
				return this.a.a;
			} else {
				cfj cfj4 = this.p();
				return cfj4.l() ? bvr.a(cfj4.c(bpg, fu)) : false;
			}
		}

		public boolean l() {
			return this.k;
		}

		public dfg j(bpg bpg, fu fu) {
			return this.a(bpg, fu, der.a());
		}

		public dfg a(bpg bpg, fu fu, der der) {
			return this.b().b(this.p(), bpg, fu, der);
		}

		public dfg k(bpg bpg, fu fu) {
			return this.a != null ? this.a.b : this.b(bpg, fu, der.a());
		}

		public dfg b(bpg bpg, fu fu, der der) {
			return this.b().c(this.p(), bpg, fu, der);
		}

		public dfg l(bpg bpg, fu fu) {
			return this.b().e(this.p(), bpg, fu);
		}

		public dfg c(bpg bpg, fu fu, der der) {
			return this.b().a(this.p(), bpg, fu, der);
		}

		public dfg m(bpg bpg, fu fu) {
			return this.b().a_(this.p(), bpg, fu);
		}

		public final boolean a(bpg bpg, fu fu, aom aom) {
			return this.a(bpg, fu, aom, fz.UP);
		}

		public final boolean a(bpg bpg, fu fu, aom aom, fz fz) {
			return bvr.a(this.b(bpg, fu, der.a(aom)), fz);
		}

		public dem n(bpg bpg, fu fu) {
			cfi.b b4 = this.b().aj_();
			if (b4 == cfi.b.NONE) {
				return dem.a;
			} else {
				long long5 = aec.c(fu.u(), 0, fu.w());
				return new dem(
					((double)((float)(long5 & 15L) / 15.0F) - 0.5) * 0.5,
					b4 == cfi.b.XYZ ? ((double)((float)(long5 >> 4 & 15L) / 15.0F) - 1.0) * 0.2 : 0.0,
					((double)((float)(long5 >> 8 & 15L) / 15.0F) - 0.5) * 0.5
				);
			}
		}

		public boolean a(bqb bqb, fu fu, int integer3, int integer4) {
			return this.b().a(this.p(), bqb, fu, integer3, integer4);
		}

		public void a(bqb bqb, fu fu2, bvr bvr, fu fu4, boolean boolean5) {
			this.b().a(this.p(), bqb, fu2, bvr, fu4, boolean5);
		}

		public final void a(bqc bqc, fu fu, int integer) {
			this.a(bqc, fu, integer, 512);
		}

		public final void a(bqc bqc, fu fu, int integer3, int integer4) {
			this.b();
			fu.a a6 = new fu.a();

			for (fz fz10 : cfi.ar) {
				a6.a(fu, fz10);
				cfj cfj11 = bqc.d_(a6);
				cfj cfj12 = cfj11.a(fz10.f(), this.p(), bqc, a6, fu);
				bvr.a(cfj11, cfj12, bqc, a6, integer3, integer4);
			}
		}

		public final void b(bqc bqc, fu fu, int integer) {
			this.b(bqc, fu, integer, 512);
		}

		public void b(bqc bqc, fu fu, int integer3, int integer4) {
			this.b().a(this.p(), bqc, fu, integer3, integer4);
		}

		public void a(bqb bqb, fu fu, cfj cfj, boolean boolean4) {
			this.b().b(this.p(), bqb, fu, cfj, boolean4);
		}

		public void b(bqb bqb, fu fu, cfj cfj, boolean boolean4) {
			this.b().a(this.p(), bqb, fu, cfj, boolean4);
		}

		public void a(zd zd, fu fu, Random random) {
			this.b().a(this.p(), zd, fu, random);
		}

		public void b(zd zd, fu fu, Random random) {
			this.b().b(this.p(), zd, fu, random);
		}

		public void a(bqb bqb, fu fu, aom aom) {
			this.b().a(this.p(), bqb, fu, aom);
		}

		public void a(bqb bqb, fu fu, bki bki) {
			this.b().a(this.p(), bqb, fu, bki);
		}

		public List<bki> a(dat.a a) {
			return this.b().a(this.p(), a);
		}

		public ang a(bqb bqb, bec bec, anf anf, deh deh) {
			return this.b().a(this.p(), bqb, deh.a(), bec, anf, deh);
		}

		public void a(bqb bqb, fu fu, bec bec) {
			this.b().a(this.p(), bqb, fu, bec);
		}

		public boolean o(bpg bpg, fu fu) {
			return this.m.test(this.p(), bpg, fu);
		}

		public cfj a(fz fz, cfj cfj, bqc bqc, fu fu4, fu fu5) {
			return this.b().a(this.p(), fz, cfj, bqc, fu4, fu5);
		}

		public boolean a(bpg bpg, fu fu, czg czg) {
			return this.b().a(this.p(), bpg, fu, czg);
		}

		public boolean a(bin bin) {
			return this.b().a(this.p(), bin);
		}

		public boolean a(cwz cwz) {
			return this.b().a(this.p(), cwz);
		}

		public boolean a(bqd bqd, fu fu) {
			return this.b().a(this.p(), bqd, fu);
		}

		public boolean q(bpg bpg, fu fu) {
			return this.o.test(this.p(), bpg, fu);
		}

		@Nullable
		public anj b(bqb bqb, fu fu) {
			return this.b().b(this.p(), bqb, fu);
		}

		public boolean a(adf<bvr> adf) {
			return this.b().a(adf);
		}

		public boolean a(adf<bvr> adf, Predicate<cfi.a> predicate) {
			return this.b().a(adf) && predicate.test(this);
		}

		public boolean a(bvr bvr) {
			return this.b().a(bvr);
		}

		public cxa m() {
			return this.b().d(this.p());
		}

		public boolean n() {
			return this.b().a_(this.p());
		}

		public cbh o() {
			return this.b().k(this.p());
		}

		public void a(bqb bqb, cfj cfj, deh deh, bes bes) {
			this.b().a(bqb, cfj, deh, bes);
		}

		public boolean d(bpg bpg, fu fu, fz fz) {
			return this.a != null ? this.a.d[fz.ordinal()] : bvr.d(this.p(), bpg, fu, fz);
		}

		public boolean r(bpg bpg, fu fu) {
			return this.a != null ? this.a.e : bvr.a(this.k(bpg, fu));
		}

		protected abstract cfj p();

		public boolean q() {
			return this.j;
		}

		static final class a {
			private static final fz[] f = fz.values();
			protected final boolean a;
			private final boolean g;
			private final int h;
			@Nullable
			private final dfg[] i;
			protected final dfg b;
			protected final boolean c;
			protected final boolean[] d;
			protected final boolean e;

			private a(cfj cfj) {
				bvr bvr3 = cfj.b();
				this.a = cfj.i(bpp.INSTANCE, fu.b);
				this.g = bvr3.b(cfj, bpp.INSTANCE, fu.b);
				this.h = bvr3.f(cfj, bpp.INSTANCE, fu.b);
				if (!cfj.l()) {
					this.i = null;
				} else {
					this.i = new dfg[f.length];
					dfg dfg4 = bvr3.d(cfj, bpp.INSTANCE, fu.b);

					for (fz fz8 : f) {
						this.i[fz8.ordinal()] = dfd.a(dfg4, fz8);
					}
				}

				this.b = bvr3.c(cfj, bpp.INSTANCE, fu.b, der.a());
				this.c = Arrays.stream(fz.a.values()).anyMatch(a -> this.b.b(a) < 0.0 || this.b.c(a) > 1.0);
				this.d = new boolean[6];

				for (fz fz7 : f) {
					this.d[fz7.ordinal()] = bvr.d(cfj, bpp.INSTANCE, fu.b, fz7);
				}

				this.e = bvr.a(cfj.k(bpp.INSTANCE, fu.b));
			}
		}
	}

	public static enum b {
		NONE,
		XZ,
		XYZ;
	}

	public static class c {
		private cxd a;
		private Function<cfj, cxe> b;
		private boolean c = true;
		private cbh d = cbh.e;
		private ToIntFunction<cfj> e = cfj -> 0;
		private float f;
		private float g;
		private boolean h;
		private boolean i;
		private float j = 0.6F;
		private float k = 1.0F;
		private float l = 1.0F;
		private uh m;
		private boolean n = true;
		private boolean o;
		private cfi.d<aoq<?>> p = (cfj, bpg, fu, aoq) -> cfj.d(bpg, fu, fz.UP) && cfj.f() < 14;
		private cfi.e q = (cfj, bpg, fu) -> cfj.c().f() && cfj.r(bpg, fu);
		private cfi.e r = (cfj, bpg, fu) -> this.a.c() && cfj.r(bpg, fu);
		private cfi.e s = this.r;
		private cfi.e t = (cfj, bpg, fu) -> false;
		private cfi.e u = (cfj, bpg, fu) -> false;
		private boolean v;

		private c(cxd cxd, cxe cxe) {
			this(cxd, cfj -> cxe);
		}

		private c(cxd cxd, Function<cfj, cxe> function) {
			this.a = cxd;
			this.b = function;
		}

		public static cfi.c a(cxd cxd) {
			return a(cxd, cxd.h());
		}

		public static cfi.c a(cxd cxd, bje bje) {
			return a(cxd, bje.f());
		}

		public static cfi.c a(cxd cxd, cxe cxe) {
			return new cfi.c(cxd, cxe);
		}

		public static cfi.c a(cxd cxd, Function<cfj, cxe> function) {
			return new cfi.c(cxd, function);
		}

		public static cfi.c a(cfi cfi) {
			cfi.c c2 = new cfi.c(cfi.as, cfi.aB.b);
			c2.a = cfi.aB.a;
			c2.g = cfi.aB.g;
			c2.f = cfi.aB.f;
			c2.c = cfi.aB.c;
			c2.i = cfi.aB.i;
			c2.e = cfi.aB.e;
			c2.b = cfi.aB.b;
			c2.d = cfi.aB.d;
			c2.j = cfi.aB.j;
			c2.k = cfi.aB.k;
			c2.v = cfi.aB.v;
			c2.n = cfi.aB.n;
			c2.o = cfi.aB.o;
			c2.h = cfi.aB.h;
			return c2;
		}

		public cfi.c a() {
			this.c = false;
			this.n = false;
			return this;
		}

		public cfi.c b() {
			this.n = false;
			return this;
		}

		public cfi.c a(float float1) {
			this.j = float1;
			return this;
		}

		public cfi.c b(float float1) {
			this.k = float1;
			return this;
		}

		public cfi.c c(float float1) {
			this.l = float1;
			return this;
		}

		public cfi.c a(cbh cbh) {
			this.d = cbh;
			return this;
		}

		public cfi.c a(ToIntFunction<cfj> toIntFunction) {
			this.e = toIntFunction;
			return this;
		}

		public cfi.c a(float float1, float float2) {
			this.g = float1;
			this.f = Math.max(0.0F, float2);
			return this;
		}

		public cfi.c c() {
			return this.d(0.0F);
		}

		public cfi.c d(float float1) {
			this.a(float1, float1);
			return this;
		}

		public cfi.c d() {
			this.i = true;
			return this;
		}

		public cfi.c e() {
			this.v = true;
			return this;
		}

		public cfi.c f() {
			this.m = dao.a;
			return this;
		}

		public cfi.c a(bvr bvr) {
			this.m = bvr.r();
			return this;
		}

		public cfi.c g() {
			this.o = true;
			return this;
		}

		public cfi.c a(cfi.d<aoq<?>> d) {
			this.p = d;
			return this;
		}

		public cfi.c a(cfi.e e) {
			this.q = e;
			return this;
		}

		public cfi.c b(cfi.e e) {
			this.r = e;
			return this;
		}

		public cfi.c c(cfi.e e) {
			this.s = e;
			return this;
		}

		public cfi.c d(cfi.e e) {
			this.t = e;
			return this;
		}

		public cfi.c e(cfi.e e) {
			this.u = e;
			return this;
		}

		public cfi.c h() {
			this.h = true;
			return this;
		}
	}

	public interface d<A> {
		boolean test(cfj cfj, bpg bpg, fu fu, A object);
	}

	public interface e {
		boolean test(cfj cfj, bpg bpg, fu fu);
	}
}
