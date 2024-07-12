import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class chj implements cgy {
	private static final Logger b = LogManager.getLogger();
	@Nullable
	public static final chk a = null;
	private final chk[] c = new chk[16];
	private cgz d;
	private final Map<fu, le> e = Maps.<fu, le>newHashMap();
	private boolean f;
	private final bqb g;
	private final Map<cio.a, cio> h = Maps.newEnumMap(cio.a.class);
	private final cht i;
	private final Map<fu, cdl> j = Maps.<fu, cdl>newHashMap();
	private final adk<aom>[] k;
	private final Map<cml<?>, ctz<?>> l = Maps.<cml<?>, ctz<?>>newHashMap();
	private final Map<cml<?>, LongSet> m = Maps.<cml<?>, LongSet>newHashMap();
	private final ShortList[] n = new ShortList[16];
	private bqr<bvr> o;
	private bqr<cwz> p;
	private boolean q;
	private long r;
	private volatile boolean s;
	private long t;
	@Nullable
	private Supplier<yo.b> u;
	@Nullable
	private Consumer<chj> v;
	private final bph w;
	private volatile boolean x;

	public chj(bqb bqb, bph bph, cgz cgz) {
		this(bqb, bph, cgz, cht.a, bpq.b(), bpq.b(), 0L, null, null);
	}

	public chj(bqb bqb, bph bph, cgz cgz, cht cht, bqr<bvr> bqr5, bqr<cwz> bqr6, long long7, @Nullable chk[] arr, @Nullable Consumer<chj> consumer) {
		this.k = new adk[16];
		this.g = bqb;
		this.w = bph;
		this.i = cht;

		for (cio.a a15 : cio.a.values()) {
			if (chc.m.h().contains(a15)) {
				this.h.put(a15, new cio(this, a15));
			}
		}

		for (int integer12 = 0; integer12 < this.k.length; integer12++) {
			this.k[integer12] = new adk<>(aom.class);
		}

		this.d = cgz;
		this.o = bqr5;
		this.p = bqr6;
		this.t = long7;
		this.v = consumer;
		if (arr != null) {
			if (this.c.length == arr.length) {
				System.arraycopy(arr, 0, this.c, 0, this.c.length);
			} else {
				b.warn("Could not set level chunk sections, array length is {} instead of {}", arr.length, this.c.length);
			}
		}
	}

	public chj(bqb bqb, chr chr) {
		this(bqb, chr.g(), chr.i(), chr.p(), chr.s(), chr.t(), chr.q(), chr.d(), null);

		for (le le5 : chr.y()) {
			aoq.a(le5, bqb, aom -> {
				this.a(aom);
				return aom;
			});
		}

		for (cdl cdl5 : chr.x().values()) {
			this.a(cdl5);
		}

		this.e.putAll(chr.z());

		for (int integer4 = 0; integer4 < chr.l().length; integer4++) {
			this.n[integer4] = chr.l()[integer4];
		}

		this.a(chr.h());
		this.b(chr.v());

		for (Entry<cio.a, cio> entry5 : chr.f()) {
			if (chc.m.h().contains(entry5.getKey())) {
				this.a((cio.a)entry5.getKey()).a(((cio)entry5.getValue()).a());
			}
		}

		this.b(chr.r());
		this.s = true;
	}

	@Override
	public cio a(cio.a a) {
		return (cio)this.h.computeIfAbsent(a, ax -> new cio(this, ax));
	}

	@Override
	public Set<fu> c() {
		Set<fu> set2 = Sets.<fu>newHashSet(this.e.keySet());
		set2.addAll(this.j.keySet());
		return set2;
	}

	@Override
	public chk[] d() {
		return this.c;
	}

	@Override
	public cfj d_(fu fu) {
		int integer3 = fu.u();
		int integer4 = fu.v();
		int integer5 = fu.w();
		if (this.g.Z()) {
			cfj cfj6 = null;
			if (integer4 == 60) {
				cfj6 = bvs.go.n();
			}

			if (integer4 == 70) {
				cfj6 = cil.b(integer3, integer5);
			}

			return cfj6 == null ? bvs.a.n() : cfj6;
		} else {
			try {
				if (integer4 >= 0 && integer4 >> 4 < this.c.length) {
					chk chk6 = this.c[integer4 >> 4];
					if (!chk.a(chk6)) {
						return chk6.a(integer3 & 15, integer4 & 15, integer5 & 15);
					}
				}

				return bvs.a.n();
			} catch (Throwable var8) {
				j j7 = j.a(var8, "Getting block state");
				k k8 = j7.a("Block being got");
				k8.a("Location", (l<String>)(() -> k.a(integer3, integer4, integer5)));
				throw new s(j7);
			}
		}
	}

	@Override
	public cxa b(fu fu) {
		return this.a(fu.u(), fu.v(), fu.w());
	}

	public cxa a(int integer1, int integer2, int integer3) {
		try {
			if (integer2 >= 0 && integer2 >> 4 < this.c.length) {
				chk chk5 = this.c[integer2 >> 4];
				if (!chk.a(chk5)) {
					return chk5.b(integer1 & 15, integer2 & 15, integer3 & 15);
				}
			}

			return cxb.a.h();
		} catch (Throwable var7) {
			j j6 = j.a(var7, "Getting fluid state");
			k k7 = j6.a("Block being got");
			k7.a("Location", (l<String>)(() -> k.a(integer1, integer2, integer3)));
			throw new s(j6);
		}
	}

	@Nullable
	@Override
	public cfj a(fu fu, cfj cfj, boolean boolean3) {
		int integer5 = fu.u() & 15;
		int integer6 = fu.v();
		int integer7 = fu.w() & 15;
		chk chk8 = this.c[integer6 >> 4];
		if (chk8 == a) {
			if (cfj.g()) {
				return null;
			}

			chk8 = new chk(integer6 >> 4 << 4);
			this.c[integer6 >> 4] = chk8;
		}

		boolean boolean9 = chk8.c();
		cfj cfj10 = chk8.a(integer5, integer6 & 15, integer7, cfj);
		if (cfj10 == cfj) {
			return null;
		} else {
			bvr bvr11 = cfj.b();
			bvr bvr12 = cfj10.b();
			((cio)this.h.get(cio.a.MOTION_BLOCKING)).a(integer5, integer6, integer7, cfj);
			((cio)this.h.get(cio.a.MOTION_BLOCKING_NO_LEAVES)).a(integer5, integer6, integer7, cfj);
			((cio)this.h.get(cio.a.OCEAN_FLOOR)).a(integer5, integer6, integer7, cfj);
			((cio)this.h.get(cio.a.WORLD_SURFACE)).a(integer5, integer6, integer7, cfj);
			boolean boolean13 = chk8.c();
			if (boolean9 != boolean13) {
				this.g.E().l().a(fu, boolean13);
			}

			if (!this.g.v) {
				cfj10.b(this.g, fu, cfj, boolean3);
			} else if (bvr12 != bvr11 && bvr12 instanceof bxp) {
				this.g.o(fu);
			}

			if (!chk8.a(integer5, integer6 & 15, integer7).a(bvr11)) {
				return null;
			} else {
				if (bvr12 instanceof bxp) {
					cdl cdl14 = this.a(fu, chj.a.CHECK);
					if (cdl14 != null) {
						cdl14.s();
					}
				}

				if (!this.g.v) {
					cfj.a(this.g, fu, cfj10, boolean3);
				}

				if (bvr11 instanceof bxp) {
					cdl cdl14 = this.a(fu, chj.a.CHECK);
					if (cdl14 == null) {
						cdl14 = ((bxp)bvr11).a(this.g);
						this.g.a(fu, cdl14);
					} else {
						cdl14.s();
					}
				}

				this.s = true;
				return cfj10;
			}
		}
	}

	@Nullable
	public cwr e() {
		return this.g.E().l();
	}

	@Override
	public void a(aom aom) {
		this.q = true;
		int integer3 = aec.c(aom.cC() / 16.0);
		int integer4 = aec.c(aom.cG() / 16.0);
		if (integer3 != this.w.b || integer4 != this.w.c) {
			b.warn("Wrong location! ({}, {}) should be ({}, {}), {}", integer3, integer4, this.w.b, this.w.c, aom);
			aom.y = true;
		}

		int integer5 = aec.c(aom.cD() / 16.0);
		if (integer5 < 0) {
			integer5 = 0;
		}

		if (integer5 >= this.k.length) {
			integer5 = this.k.length - 1;
		}

		aom.V = true;
		aom.W = this.w.b;
		aom.X = integer5;
		aom.Y = this.w.c;
		this.k[integer5].add(aom);
	}

	@Override
	public void a(cio.a a, long[] arr) {
		((cio)this.h.get(a)).a(arr);
	}

	public void b(aom aom) {
		this.a(aom, aom.X);
	}

	public void a(aom aom, int integer) {
		if (integer < 0) {
			integer = 0;
		}

		if (integer >= this.k.length) {
			integer = this.k.length - 1;
		}

		this.k[integer].remove(aom);
	}

	@Override
	public int a(cio.a a, int integer2, int integer3) {
		return ((cio)this.h.get(a)).a(integer2 & 15, integer3 & 15) - 1;
	}

	@Nullable
	private cdl j(fu fu) {
		cfj cfj3 = this.d_(fu);
		bvr bvr4 = cfj3.b();
		return !bvr4.q() ? null : ((bxp)bvr4).a(this.g);
	}

	@Nullable
	@Override
	public cdl c(fu fu) {
		return this.a(fu, chj.a.CHECK);
	}

	@Nullable
	public cdl a(fu fu, chj.a a) {
		cdl cdl4 = (cdl)this.j.get(fu);
		if (cdl4 == null) {
			le le5 = (le)this.e.remove(fu);
			if (le5 != null) {
				cdl cdl6 = this.a(fu, le5);
				if (cdl6 != null) {
					return cdl6;
				}
			}
		}

		if (cdl4 == null) {
			if (a == chj.a.IMMEDIATE) {
				cdl4 = this.j(fu);
				this.g.a(fu, cdl4);
			}
		} else if (cdl4.q()) {
			this.j.remove(fu);
			return null;
		}

		return cdl4;
	}

	public void a(cdl cdl) {
		this.a(cdl.o(), cdl);
		if (this.f || this.g.s_()) {
			this.g.a(cdl.o(), cdl);
		}
	}

	@Override
	public void a(fu fu, cdl cdl) {
		if (this.d_(fu).b() instanceof bxp) {
			cdl.a(this.g, fu);
			cdl.r();
			cdl cdl4 = (cdl)this.j.put(fu.h(), cdl);
			if (cdl4 != null && cdl4 != cdl) {
				cdl4.an_();
			}
		}
	}

	@Override
	public void a(le le) {
		this.e.put(new fu(le.h("x"), le.h("y"), le.h("z")), le);
	}

	@Nullable
	@Override
	public le i(fu fu) {
		cdl cdl3 = this.c(fu);
		if (cdl3 != null && !cdl3.q()) {
			le le4 = cdl3.a(new le());
			le4.a("keepPacked", false);
			return le4;
		} else {
			le le4 = (le)this.e.get(fu);
			if (le4 != null) {
				le4 = le4.g();
				le4.a("keepPacked", true);
			}

			return le4;
		}
	}

	@Override
	public void d(fu fu) {
		if (this.f || this.g.s_()) {
			cdl cdl3 = (cdl)this.j.remove(fu);
			if (cdl3 != null) {
				cdl3.an_();
			}
		}
	}

	public void w() {
		if (this.v != null) {
			this.v.accept(this);
			this.v = null;
		}
	}

	public void s() {
		this.s = true;
	}

	public void a(@Nullable aom aom, deg deg, List<aom> list, @Nullable Predicate<? super aom> predicate) {
		int integer6 = aec.c((deg.b - 2.0) / 16.0);
		int integer7 = aec.c((deg.e + 2.0) / 16.0);
		integer6 = aec.a(integer6, 0, this.k.length - 1);
		integer7 = aec.a(integer7, 0, this.k.length - 1);

		for (int integer8 = integer6; integer8 <= integer7; integer8++) {
			adk<aom> adk9 = this.k[integer8];
			List<aom> list10 = adk9.a();
			int integer11 = list10.size();

			for (int integer12 = 0; integer12 < integer11; integer12++) {
				aom aom13 = (aom)list10.get(integer12);
				if (aom13.cb().c(deg) && aom13 != aom) {
					if (predicate == null || predicate.test(aom13)) {
						list.add(aom13);
					}

					if (aom13 instanceof bac) {
						for (baa baa17 : ((bac)aom13).eK()) {
							if (baa17 != aom && baa17.cb().c(deg) && (predicate == null || predicate.test(baa17))) {
								list.add(baa17);
							}
						}
					}
				}
			}
		}
	}

	public <T extends aom> void a(@Nullable aoq<?> aoq, deg deg, List<? super T> list, Predicate<? super T> predicate) {
		int integer6 = aec.c((deg.b - 2.0) / 16.0);
		int integer7 = aec.c((deg.e + 2.0) / 16.0);
		integer6 = aec.a(integer6, 0, this.k.length - 1);
		integer7 = aec.a(integer7, 0, this.k.length - 1);

		for (int integer8 = integer6; integer8 <= integer7; integer8++) {
			for (aom aom10 : this.k[integer8].a(aom.class)) {
				if ((aoq == null || aom10.U() == aoq) && aom10.cb().c(deg) && predicate.test(aom10)) {
					list.add(aom10);
				}
			}
		}
	}

	public <T extends aom> void a(Class<? extends T> class1, deg deg, List<T> list, @Nullable Predicate<? super T> predicate) {
		int integer6 = aec.c((deg.b - 2.0) / 16.0);
		int integer7 = aec.c((deg.e + 2.0) / 16.0);
		integer6 = aec.a(integer6, 0, this.k.length - 1);
		integer7 = aec.a(integer7, 0, this.k.length - 1);

		for (int integer8 = integer6; integer8 <= integer7; integer8++) {
			for (T aom10 : this.k[integer8].a(class1)) {
				if (aom10.cb().c(deg) && (predicate == null || predicate.test(aom10))) {
					list.add(aom10);
				}
			}
		}
	}

	public boolean t() {
		return false;
	}

	@Override
	public bph g() {
		return this.w;
	}

	@Override
	public cgz i() {
		return this.d;
	}

	public void c(boolean boolean1) {
		this.f = boolean1;
	}

	public bqb x() {
		return this.g;
	}

	@Override
	public Collection<Entry<cio.a, cio>> f() {
		return Collections.unmodifiableSet(this.h.entrySet());
	}

	public Map<fu, cdl> y() {
		return this.j;
	}

	public adk<aom>[] z() {
		return this.k;
	}

	@Override
	public le f(fu fu) {
		return (le)this.e.get(fu);
	}

	@Override
	public Stream<fu> m() {
		return StreamSupport.stream(fu.b(this.w.d(), 0, this.w.e(), this.w.f(), 255, this.w.g()).spliterator(), false).filter(fu -> this.d_(fu).f() != 0);
	}

	@Override
	public bqr<bvr> n() {
		return this.o;
	}

	@Override
	public bqr<cwz> o() {
		return this.p;
	}

	@Override
	public void a(boolean boolean1) {
		this.s = boolean1;
	}

	@Override
	public boolean j() {
		return this.s || this.q && this.g.Q() != this.r;
	}

	public void d(boolean boolean1) {
		this.q = boolean1;
	}

	@Override
	public void a(long long1) {
		this.r = long1;
	}

	@Nullable
	@Override
	public ctz<?> a(cml<?> cml) {
		return (ctz<?>)this.l.get(cml);
	}

	@Override
	public void a(cml<?> cml, ctz<?> ctz) {
		this.l.put(cml, ctz);
	}

	@Override
	public Map<cml<?>, ctz<?>> h() {
		return this.l;
	}

	@Override
	public void a(Map<cml<?>, ctz<?>> map) {
		this.l.clear();
		this.l.putAll(map);
	}

	@Override
	public LongSet b(cml<?> cml) {
		return (LongSet)this.m.computeIfAbsent(cml, cmlx -> new LongOpenHashSet());
	}

	@Override
	public void a(cml<?> cml, long long2) {
		((LongSet)this.m.computeIfAbsent(cml, cmlx -> new LongOpenHashSet())).add(long2);
	}

	@Override
	public Map<cml<?>, LongSet> v() {
		return this.m;
	}

	@Override
	public void b(Map<cml<?>, LongSet> map) {
		this.m.clear();
		this.m.putAll(map);
	}

	@Override
	public long q() {
		return this.t;
	}

	@Override
	public void b(long long1) {
		this.t = long1;
	}

	public void A() {
		bph bph2 = this.g();

		for (int integer3 = 0; integer3 < this.n.length; integer3++) {
			if (this.n[integer3] != null) {
				for (Short short5 : this.n[integer3]) {
					fu fu6 = chr.a(short5, integer3, bph2);
					cfj cfj7 = this.d_(fu6);
					cfj cfj8 = bvr.b(cfj7, (bqc)this.g, fu6);
					this.g.a(fu6, cfj8, 20);
				}

				this.n[integer3].clear();
			}
		}

		this.B();

		for (fu fu4 : Sets.newHashSet(this.e.keySet())) {
			this.c(fu4);
		}

		this.e.clear();
		this.i.a(this);
	}

	@Nullable
	private cdl a(fu fu, le le) {
		cfj cfj5 = this.d_(fu);
		cdl cdl4;
		if ("DUMMY".equals(le.l("id"))) {
			bvr bvr6 = cfj5.b();
			if (bvr6 instanceof bxp) {
				cdl4 = ((bxp)bvr6).a(this.g);
			} else {
				cdl4 = null;
				b.warn("Tried to load a DUMMY block entity @ {} but found not block entity block {} at location", fu, cfj5);
			}
		} else {
			cdl4 = cdl.b(cfj5, le);
		}

		if (cdl4 != null) {
			cdl4.a(this.g, fu);
			this.a(cdl4);
		} else {
			b.warn("Tried to load a block entity for block {} but failed at location {}", cfj5, fu);
		}

		return cdl4;
	}

	@Override
	public cht p() {
		return this.i;
	}

	@Override
	public ShortList[] l() {
		return this.n;
	}

	public void B() {
		if (this.o instanceof chs) {
			((chs)this.o).a(this.g.G(), fu -> this.d_(fu).b());
			this.o = bpq.b();
		} else if (this.o instanceof bpi) {
			((bpi)this.o).a(this.g.G());
			this.o = bpq.b();
		}

		if (this.p instanceof chs) {
			((chs)this.p).a(this.g.F(), fu -> this.b(fu).a());
			this.p = bpq.b();
		} else if (this.p instanceof bpi) {
			((bpi)this.p).a(this.g.F());
			this.p = bpq.b();
		}
	}

	public void a(zd zd) {
		if (this.o == bpq.b()) {
			this.o = new bpi<>(gl.aj::b, zd.j().a(this.w, true, false), zd.Q());
			this.a(true);
		}

		if (this.p == bpq.b()) {
			this.p = new bpi<>(gl.ah::b, zd.k().a(this.w, true, false), zd.Q());
			this.a(true);
		}
	}

	@Override
	public chc k() {
		return chc.m;
	}

	public yo.b u() {
		return this.u == null ? yo.b.BORDER : (yo.b)this.u.get();
	}

	public void a(Supplier<yo.b> supplier) {
		this.u = supplier;
	}

	@Override
	public boolean r() {
		return this.x;
	}

	@Override
	public void b(boolean boolean1) {
		this.x = boolean1;
		this.a(true);
	}

	public static enum a {
		IMMEDIATE,
		QUEUED,
		CHECK;
	}
}
