import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class chr implements cgy {
	private static final Logger a = LogManager.getLogger();
	private final bph b;
	private volatile boolean c;
	@Nullable
	private cgz d;
	@Nullable
	private volatile cwr e;
	private final Map<cio.a, cio> f = Maps.newEnumMap(cio.a.class);
	private volatile chc g = chc.a;
	private final Map<fu, cdl> h = Maps.<fu, cdl>newHashMap();
	private final Map<fu, le> i = Maps.<fu, le>newHashMap();
	private final chk[] j = new chk[16];
	private final List<le> k = Lists.<le>newArrayList();
	private final List<fu> l = Lists.<fu>newArrayList();
	private final ShortList[] m = new ShortList[16];
	private final Map<cml<?>, ctz<?>> n = Maps.<cml<?>, ctz<?>>newHashMap();
	private final Map<cml<?>, LongSet> o = Maps.<cml<?>, LongSet>newHashMap();
	private final cht p;
	private final chs<bvr> q;
	private final chs<cwz> r;
	private long s;
	private final Map<cin.a, BitSet> t = new Object2ObjectArrayMap<>();
	private volatile boolean u;

	public chr(bph bph, cht cht) {
		this(bph, cht, null, new chs<>(bvr -> bvr == null || bvr.n().g(), bph), new chs<>(cwz -> cwz == null || cwz == cxb.a, bph));
	}

	public chr(bph bph, cht cht, @Nullable chk[] arr, chs<bvr> chs4, chs<cwz> chs5) {
		this.b = bph;
		this.p = cht;
		this.q = chs4;
		this.r = chs5;
		if (arr != null) {
			if (this.j.length == arr.length) {
				System.arraycopy(arr, 0, this.j, 0, this.j.length);
			} else {
				a.warn("Could not set level chunk sections, array length is {} instead of {}", arr.length, this.j.length);
			}
		}
	}

	@Override
	public cfj d_(fu fu) {
		int integer3 = fu.v();
		if (bqb.b(integer3)) {
			return bvs.la.n();
		} else {
			chk chk4 = this.d()[integer3 >> 4];
			return chk.a(chk4) ? bvs.a.n() : chk4.a(fu.u() & 15, integer3 & 15, fu.w() & 15);
		}
	}

	@Override
	public cxa b(fu fu) {
		int integer3 = fu.v();
		if (bqb.b(integer3)) {
			return cxb.a.h();
		} else {
			chk chk4 = this.d()[integer3 >> 4];
			return chk.a(chk4) ? cxb.a.h() : chk4.b(fu.u() & 15, integer3 & 15, fu.w() & 15);
		}
	}

	@Override
	public Stream<fu> m() {
		return this.l.stream();
	}

	public ShortList[] w() {
		ShortList[] arr2 = new ShortList[16];

		for (fu fu4 : this.l) {
			cgy.a(arr2, fu4.v() >> 4).add(k(fu4));
		}

		return arr2;
	}

	public void b(short short1, int integer) {
		this.j(a(short1, integer, this.b));
	}

	public void j(fu fu) {
		this.l.add(fu.h());
	}

	@Nullable
	@Override
	public cfj a(fu fu, cfj cfj, boolean boolean3) {
		int integer5 = fu.u();
		int integer6 = fu.v();
		int integer7 = fu.w();
		if (integer6 >= 0 && integer6 < 256) {
			if (this.j[integer6 >> 4] == chj.a && cfj.a(bvs.a)) {
				return cfj;
			} else {
				if (cfj.f() > 0) {
					this.l.add(new fu((integer5 & 15) + this.g().d(), integer6, (integer7 & 15) + this.g().e()));
				}

				chk chk8 = this.a(integer6 >> 4);
				cfj cfj9 = chk8.a(integer5 & 15, integer6 & 15, integer7 & 15, cfj);
				if (this.g.b(chc.i) && cfj != cfj9 && (cfj.b(this, fu) != cfj9.b(this, fu) || cfj.f() != cfj9.f() || cfj.e() || cfj9.e())) {
					cwr cwr10 = this.e();
					cwr10.a(fu);
				}

				EnumSet<cio.a> enumSet10 = this.k().h();
				EnumSet<cio.a> enumSet11 = null;

				for (cio.a a13 : enumSet10) {
					cio cio14 = (cio)this.f.get(a13);
					if (cio14 == null) {
						if (enumSet11 == null) {
							enumSet11 = EnumSet.noneOf(cio.a.class);
						}

						enumSet11.add(a13);
					}
				}

				if (enumSet11 != null) {
					cio.a(this, enumSet11);
				}

				for (cio.a a13x : enumSet10) {
					((cio)this.f.get(a13x)).a(integer5 & 15, integer6, integer7 & 15, cfj);
				}

				return cfj9;
			}
		} else {
			return bvs.la.n();
		}
	}

	public chk a(int integer) {
		if (this.j[integer] == chj.a) {
			this.j[integer] = new chk(integer << 4);
		}

		return this.j[integer];
	}

	@Override
	public void a(fu fu, cdl cdl) {
		cdl.a(fu);
		this.h.put(fu, cdl);
	}

	@Override
	public Set<fu> c() {
		Set<fu> set2 = Sets.<fu>newHashSet(this.i.keySet());
		set2.addAll(this.h.keySet());
		return set2;
	}

	@Nullable
	@Override
	public cdl c(fu fu) {
		return (cdl)this.h.get(fu);
	}

	public Map<fu, cdl> x() {
		return this.h;
	}

	public void b(le le) {
		this.k.add(le);
	}

	@Override
	public void a(aom aom) {
		if (!aom.bn()) {
			le le3 = new le();
			aom.d(le3);
			this.b(le3);
		}
	}

	public List<le> y() {
		return this.k;
	}

	public void a(cgz cgz) {
		this.d = cgz;
	}

	@Nullable
	@Override
	public cgz i() {
		return this.d;
	}

	@Override
	public void a(boolean boolean1) {
		this.c = boolean1;
	}

	@Override
	public boolean j() {
		return this.c;
	}

	@Override
	public chc k() {
		return this.g;
	}

	public void a(chc chc) {
		this.g = chc;
		this.a(true);
	}

	@Override
	public chk[] d() {
		return this.j;
	}

	@Nullable
	public cwr e() {
		return this.e;
	}

	@Override
	public Collection<Entry<cio.a, cio>> f() {
		return Collections.unmodifiableSet(this.f.entrySet());
	}

	@Override
	public void a(cio.a a, long[] arr) {
		this.a(a).a(arr);
	}

	@Override
	public cio a(cio.a a) {
		return (cio)this.f.computeIfAbsent(a, ax -> new cio(this, ax));
	}

	@Override
	public int a(cio.a a, int integer2, int integer3) {
		cio cio5 = (cio)this.f.get(a);
		if (cio5 == null) {
			cio.a(this, EnumSet.of(a));
			cio5 = (cio)this.f.get(a);
		}

		return cio5.a(integer2 & 15, integer3 & 15) - 1;
	}

	@Override
	public bph g() {
		return this.b;
	}

	@Override
	public void a(long long1) {
	}

	@Nullable
	@Override
	public ctz<?> a(cml<?> cml) {
		return (ctz<?>)this.n.get(cml);
	}

	@Override
	public void a(cml<?> cml, ctz<?> ctz) {
		this.n.put(cml, ctz);
		this.c = true;
	}

	@Override
	public Map<cml<?>, ctz<?>> h() {
		return Collections.unmodifiableMap(this.n);
	}

	@Override
	public void a(Map<cml<?>, ctz<?>> map) {
		this.n.clear();
		this.n.putAll(map);
		this.c = true;
	}

	@Override
	public LongSet b(cml<?> cml) {
		return (LongSet)this.o.computeIfAbsent(cml, cmlx -> new LongOpenHashSet());
	}

	@Override
	public void a(cml<?> cml, long long2) {
		((LongSet)this.o.computeIfAbsent(cml, cmlx -> new LongOpenHashSet())).add(long2);
		this.c = true;
	}

	@Override
	public Map<cml<?>, LongSet> v() {
		return Collections.unmodifiableMap(this.o);
	}

	@Override
	public void b(Map<cml<?>, LongSet> map) {
		this.o.clear();
		this.o.putAll(map);
		this.c = true;
	}

	public static short k(fu fu) {
		int integer2 = fu.u();
		int integer3 = fu.v();
		int integer4 = fu.w();
		int integer5 = integer2 & 15;
		int integer6 = integer3 & 15;
		int integer7 = integer4 & 15;
		return (short)(integer5 | integer6 << 4 | integer7 << 8);
	}

	public static fu a(short short1, int integer, bph bph) {
		int integer4 = (short1 & 15) + (bph.b << 4);
		int integer5 = (short1 >>> 4 & 15) + (integer << 4);
		int integer6 = (short1 >>> 8 & 15) + (bph.c << 4);
		return new fu(integer4, integer5, integer6);
	}

	@Override
	public void e(fu fu) {
		if (!bqb.l(fu)) {
			cgy.a(this.m, fu.v() >> 4).add(k(fu));
		}
	}

	@Override
	public ShortList[] l() {
		return this.m;
	}

	@Override
	public void a(short short1, int integer) {
		cgy.a(this.m, integer).add(short1);
	}

	public chs<bvr> n() {
		return this.q;
	}

	public chs<cwz> o() {
		return this.r;
	}

	@Override
	public cht p() {
		return this.p;
	}

	@Override
	public void b(long long1) {
		this.s = long1;
	}

	@Override
	public long q() {
		return this.s;
	}

	@Override
	public void a(le le) {
		this.i.put(new fu(le.h("x"), le.h("y"), le.h("z")), le);
	}

	public Map<fu, le> z() {
		return Collections.unmodifiableMap(this.i);
	}

	@Override
	public le f(fu fu) {
		return (le)this.i.get(fu);
	}

	@Nullable
	@Override
	public le i(fu fu) {
		cdl cdl3 = this.c(fu);
		return cdl3 != null ? cdl3.a(new le()) : (le)this.i.get(fu);
	}

	@Override
	public void d(fu fu) {
		this.h.remove(fu);
		this.i.remove(fu);
	}

	@Nullable
	public BitSet a(cin.a a) {
		return (BitSet)this.t.get(a);
	}

	public BitSet b(cin.a a) {
		return (BitSet)this.t.computeIfAbsent(a, ax -> new BitSet(65536));
	}

	public void a(cin.a a, BitSet bitSet) {
		this.t.put(a, bitSet);
	}

	public void a(cwr cwr) {
		this.e = cwr;
	}

	@Override
	public boolean r() {
		return this.u;
	}

	@Override
	public void b(boolean boolean1) {
		this.u = boolean1;
		this.a(true);
	}
}
