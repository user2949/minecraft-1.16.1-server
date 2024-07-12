import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class bvr extends cfi implements bqa {
	protected static final Logger l = LogManager.getLogger();
	public static final ge<cfj> m = new ge<>();
	private static final LoadingCache<dfg, Boolean> a = CacheBuilder.newBuilder().maximumSize(512L).weakKeys().build(new CacheLoader<dfg, Boolean>() {
		public Boolean load(dfg dfg) {
			return !dfd.c(dfd.b(), dfg, deq.g);
		}
	});
	private static final dfg b = dfd.a(dfd.b(), a(2.0, 0.0, 2.0, 14.0, 16.0, 14.0), deq.e);
	private static final dfg c = a(7.0, 0.0, 7.0, 9.0, 10.0, 9.0);
	protected final cfk<bvr, cfj> n;
	private cfj d;
	@Nullable
	private String e;
	@Nullable
	private bke f;
	private static final ThreadLocal<Object2ByteLinkedOpenHashMap<bvr.a>> g = ThreadLocal.withInitial(() -> {
		Object2ByteLinkedOpenHashMap<bvr.a> object2ByteLinkedOpenHashMap1 = new Object2ByteLinkedOpenHashMap<bvr.a>(2048, 0.25F) {
			@Override
			protected void rehash(int integer) {
			}
		};
		object2ByteLinkedOpenHashMap1.defaultReturnValue((byte)127);
		return object2ByteLinkedOpenHashMap1;
	});

	public static int i(@Nullable cfj cfj) {
		if (cfj == null) {
			return 0;
		} else {
			int integer2 = m.a(cfj);
			return integer2 == -1 ? 0 : integer2;
		}
	}

	public static cfj a(int integer) {
		cfj cfj2 = m.a(integer);
		return cfj2 == null ? bvs.a.n() : cfj2;
	}

	public static bvr a(@Nullable bke bke) {
		return bke instanceof bim ? ((bim)bke).e() : bvs.a;
	}

	public static cfj a(cfj cfj1, cfj cfj2, bqb bqb, fu fu) {
		dfg dfg5 = dfd.b(cfj1.k(bqb, fu), cfj2.k(bqb, fu), deq.c).a((double)fu.u(), (double)fu.v(), (double)fu.w());

		for (aom aom8 : bqb.a(null, dfg5.a())) {
			double double9 = dfd.a(fz.a.Y, aom8.cb().d(0.0, 1.0, 0.0), Stream.of(dfg5), -1.0);
			aom8.a(aom8.cC(), aom8.cD() + 1.0 + double9, aom8.cG());
		}

		return cfj2;
	}

	public static dfg a(double double1, double double2, double double3, double double4, double double5, double double6) {
		return dfd.a(double1 / 16.0, double2 / 16.0, double3 / 16.0, double4 / 16.0, double5 / 16.0, double6 / 16.0);
	}

	public boolean a(adf<bvr> adf) {
		return adf.a(this);
	}

	public boolean a(bvr bvr) {
		return this == bvr;
	}

	public static cfj b(cfj cfj, bqc bqc, fu fu) {
		cfj cfj4 = cfj;
		fu.a a5 = new fu.a();

		for (fz fz9 : ar) {
			a5.a(fu, fz9);
			cfj4 = cfj4.a(fz9, bqc.d_(a5), bqc, fu, a5);
		}

		return cfj4;
	}

	public static void a(cfj cfj1, cfj cfj2, bqc bqc, fu fu, int integer) {
		a(cfj1, cfj2, bqc, fu, integer, 512);
	}

	public static void a(cfj cfj1, cfj cfj2, bqc bqc, fu fu, int integer5, int integer6) {
		if (cfj2 != cfj1) {
			if (cfj2.g()) {
				if (!bqc.s_()) {
					bqc.a(fu, (integer5 & 32) == 0, null, integer6);
				}
			} else {
				bqc.a(fu, cfj2, integer5 & -33, integer6);
			}
		}
	}

	public bvr(cfi.c c) {
		super(c);
		cfk.a<bvr, cfj> a3 = new cfk.a<>(this);
		this.a(a3);
		this.n = a3.a(bvr::n, cfj::new);
		this.j(this.n.b());
	}

	public static boolean b(bvr bvr) {
		return bvr instanceof bza || bvr == bvs.go || bvr == bvs.cU || bvr == bvs.cV || bvr == bvs.dK || bvr == bvs.cK || bvr.a(acx.at);
	}

	public boolean a_(cfj cfj) {
		return this.av;
	}

	public static boolean c(bpg bpg, fu fu) {
		cfj cfj3 = bpg.d_(fu);
		return cfj3.r(bpg, fu) && cfj3.d(bpg, fu, fz.UP) || !dfd.c(cfj3.l(bpg, fu).a(fz.UP), b, deq.c);
	}

	public static boolean a(bqd bqd, fu fu, fz fz) {
		cfj cfj4 = bqd.d_(fu);
		return fz == fz.DOWN && cfj4.a(acx.aB) ? false : !dfd.c(cfj4.l(bqd, fu).a(fz), c, deq.c);
	}

	public static boolean d(cfj cfj, bpg bpg, fu fu, fz fz) {
		return a(cfj.l(bpg, fu), fz);
	}

	public static boolean a(dfg dfg, fz fz) {
		dfg dfg3 = dfg.a(fz);
		return a(dfg3);
	}

	public static boolean a(dfg dfg) {
		return a.getUnchecked(dfg);
	}

	public boolean b(cfj cfj, bpg bpg, fu fu) {
		return !a(cfj.j(bpg, fu)) && cfj.m().c();
	}

	public void a(bqc bqc, fu fu, cfj cfj) {
	}

	public static List<bki> a(cfj cfj, zd zd, fu fu, @Nullable cdl cdl) {
		dat.a a5 = new dat.a(zd).a(zd.t).a(dda.f, fu).a(dda.j, bki.b).b(dda.i, cdl);
		return cfj.a(a5);
	}

	public static List<bki> a(cfj cfj, zd zd, fu fu, @Nullable cdl cdl, @Nullable aom aom, bki bki) {
		dat.a a7 = new dat.a(zd).a(zd.t).a(dda.f, fu).a(dda.j, bki).b(dda.a, aom).b(dda.i, cdl);
		return cfj.a(a7);
	}

	public static void c(cfj cfj, bqb bqb, fu fu) {
		if (bqb instanceof zd) {
			a(cfj, (zd)bqb, fu, null).forEach(bki -> a(bqb, fu, bki));
		}

		cfj.a(bqb, fu, bki.b);
	}

	public static void a(cfj cfj, bqb bqb, fu fu, @Nullable cdl cdl) {
		if (bqb instanceof zd) {
			a(cfj, (zd)bqb, fu, cdl).forEach(bki -> a(bqb, fu, bki));
		}

		cfj.a(bqb, fu, bki.b);
	}

	public static void a(cfj cfj, bqb bqb, fu fu, @Nullable cdl cdl, aom aom, bki bki) {
		if (bqb instanceof zd) {
			a(cfj, (zd)bqb, fu, cdl, aom, bki).forEach(bkix -> a(bqb, fu, bkix));
		}

		cfj.a(bqb, fu, bki);
	}

	public static void a(bqb bqb, fu fu, bki bki) {
		if (!bqb.v && !bki.a() && bqb.S().b(bpx.f)) {
			float float4 = 0.5F;
			double double5 = (double)(bqb.t.nextFloat() * 0.5F) + 0.25;
			double double7 = (double)(bqb.t.nextFloat() * 0.5F) + 0.25;
			double double9 = (double)(bqb.t.nextFloat() * 0.5F) + 0.25;
			bbg bbg11 = new bbg(bqb, (double)fu.u() + double5, (double)fu.v() + double7, (double)fu.w() + double9, bki);
			bbg11.m();
			bqb.c(bbg11);
		}
	}

	protected void a(bqb bqb, fu fu, int integer) {
		if (!bqb.v && bqb.S().b(bpx.f)) {
			while (integer > 0) {
				int integer5 = aos.a(integer);
				integer -= integer5;
				bqb.c(new aos(bqb, (double)fu.u() + 0.5, (double)fu.v() + 0.5, (double)fu.w() + 0.5, integer5));
			}
		}
	}

	public float f() {
		return this.au;
	}

	public void a(bqb bqb, fu fu, bpt bpt) {
	}

	public void a(bqb bqb, fu fu, aom aom) {
	}

	@Nullable
	public cfj a(bin bin) {
		return this.n();
	}

	public void a(bqb bqb, bec bec, fu fu, cfj cfj, @Nullable cdl cdl, bki bki) {
		bec.b(acu.a.b(this));
		bec.q(0.005F);
		a(cfj, bqb, fu, cdl, bec, bki);
	}

	public void a(bqb bqb, fu fu, cfj cfj, @Nullable aoy aoy, bki bki) {
	}

	public boolean ak_() {
		return !this.as.b() && !this.as.a();
	}

	public String i() {
		if (this.e == null) {
			this.e = v.a("block", gl.aj.b(this));
		}

		return this.e;
	}

	public void a(bqb bqb, fu fu, aom aom, float float4) {
		aom.b(float4, 1.0F);
	}

	public void a(bpg bpg, aom aom) {
		aom.e(aom.cB().d(1.0, 0.0, 1.0));
	}

	public void a(biy biy, gi<bki> gi) {
		gi.add(new bki(this));
	}

	public float j() {
		return this.ax;
	}

	public float k() {
		return this.ay;
	}

	public float l() {
		return this.az;
	}

	public void a(bqb bqb, fu fu, cfj cfj, bec bec) {
		bqb.a(bec, 2001, fu, i(cfj));
		if (this.a(acx.ay)) {
			bdd.a(bec, false);
		}
	}

	public void c(bqb bqb, fu fu) {
	}

	public boolean a(bpt bpt) {
		return true;
	}

	protected void a(cfk.a<bvr, cfj> a) {
	}

	public cfk<bvr, cfj> m() {
		return this.n;
	}

	protected final void j(cfj cfj) {
		this.d = cfj;
	}

	public final cfj n() {
		return this.d;
	}

	public cbh k(cfj cfj) {
		return this.aw;
	}

	@Override
	public bke h() {
		if (this.f == null) {
			this.f = bke.a(this);
		}

		return this.f;
	}

	public boolean o() {
		return this.aA;
	}

	public String toString() {
		return "Block{" + gl.aj.b(this) + "}";
	}

	@Override
	protected bvr p() {
		return this;
	}

	public static final class a {
		private final cfj a;
		private final cfj b;
		private final fz c;

		public a(cfj cfj1, cfj cfj2, fz fz) {
			this.a = cfj1;
			this.b = cfj2;
			this.c = fz;
		}

		public boolean equals(Object object) {
			if (this == object) {
				return true;
			} else if (!(object instanceof bvr.a)) {
				return false;
			} else {
				bvr.a a3 = (bvr.a)object;
				return this.a == a3.a && this.b == a3.b && this.c == a3.c;
			}
		}

		public int hashCode() {
			int integer2 = this.a.hashCode();
			integer2 = 31 * integer2 + this.b.hashCode();
			return 31 * integer2 + this.c.hashCode();
		}
	}
}
