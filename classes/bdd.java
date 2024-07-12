import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

public class bdd {
	public static final bke a = bkk.kl;
	private static final adx b = aej.a(30, 120);
	private static final adx c = aej.a(10, 40);
	private static final adx d = aej.a(10, 30);
	private static final adx e = aej.a(5, 20);
	private static final adx f = aej.a(5, 7);
	private static final adx g = aej.a(5, 7);
	private static final Set<bke> h = ImmutableSet.of(bkk.lx, bkk.ly);

	protected static apr<?> a(bdc bdc, apr<bdc> apr) {
		a(apr);
		b(apr);
		d(apr);
		b(bdc, apr);
		c(apr);
		e(apr);
		f(apr);
		apr.a(ImmutableSet.of(bfl.a));
		apr.b(bfl.b);
		apr.e();
		return apr;
	}

	protected static void a(bdc bdc) {
		int integer2 = b.a(bdc.l.t);
		bdc.cI().a(awp.P, true, (long)integer2);
	}

	private static void a(apr<bdc> apr) {
		apr.a(bfl.a, 0, ImmutableList.of(new arg(45, 90), new ark(200), new arb(), d(), e(), new bdi(), new bdf(120), new asj(300, bdd::a), new asl()));
	}

	private static void b(apr<bdc> apr) {
		apr.a(bfl.b, 10, ImmutableList.of(new arx(bdd::b, 14.0F), new asi<>(bdc::eM, bdd::o), new art(bdc::eN, new bdg<>()), c(), f(), a(), b(), new arz(aoq.bb, 4)));
	}

	private static void b(bdc bdc, apr<bdc> apr) {
		apr.a(
			bfl.k,
			10,
			ImmutableList.of(
				new ask<>(aoy -> !d(bdc, aoy)), new art(bdd::c, new aqf<>(5, 0.75F)), new asc(1.0F), new arh(20), new aqm(), new bde(), new aqq(bdd::n, awp.o)
			),
			awp.o
		);
	}

	private static void c(apr<bdc> apr) {
		apr.a(
			bfl.l,
			10,
			ImmutableList.of(
				c(),
				new arx(bdd::b, 14.0F),
				new asi(bdc::eM, bdd::o),
				new art(bdc -> !bdc.eQ(), new aqu(2, 1.0F)),
				new art(bdc::eQ, new aqu(4, 0.6F)),
				new aru(ImmutableList.of(Pair.of(new arx(aoq.ai, 8.0F), 1), Pair.of(new aro(0.6F, 2, 1), 1), Pair.of(new aqo(10, 20), 1)))
			),
			awp.Q
		);
	}

	private static void d(apr<bdc> apr) {
		apr.a(bfl.m, 10, ImmutableList.of(new aqx<>(bdd::F, 1.0F, true, 9), new bdh(9)), awp.N);
	}

	private static void e(apr<bdc> apr) {
		apr.a(bfl.n, 10, ImmutableList.of(asb.b(awp.z, 1.0F, 12, true), a(), b(), new aqq(bdd::u, awp.z)), awp.z);
	}

	private static void f(apr<bdc> apr) {
		apr.a(bfl.o, 10, ImmutableList.of(new ari<>(0.8F), new arx(bdd::b, 8.0F), new art(aom::bn, a()), new aqn(8, bdd::a)), awp.s);
	}

	private static aru<bdc> a() {
		return new aru<>(
			ImmutableList.of(Pair.of(new arx(aoq.bb, 8.0F), 1), Pair.of(new arx(aoq.ai, 8.0F), 1), Pair.of(new arx(8.0F), 1), Pair.of(new aqo(30, 60), 1))
		);
	}

	private static aru<bdc> b() {
		return new aru<>(
			ImmutableList.of(
				Pair.of(new aro(0.6F), 2), Pair.of(ara.a(aoq.ai, 8, awp.q, 0.6F, 2), 2), Pair.of(new art<>(bdd::g, new ase(0.6F, 3)), 2), Pair.of(new aqo(30, 60), 1)
			)
		);
	}

	private static asb<fu> c() {
		return asb.a(awp.af, 1.0F, 8, false);
	}

	private static aql<bdc, aoy> d() {
		return new aql<>(bdc::x_, awp.K, awp.z, g);
	}

	private static aql<bdc, aoy> e() {
		return new aql<>(bdd::n, awp.aa, awp.z, f);
	}

	protected static void b(bdc bdc) {
		apr<bdc> apr2 = bdc.cI();
		bfl bfl3 = (bfl)apr2.f().orElse(null);
		apr2.a(ImmutableList.of(bfl.m, bfl.k, bfl.n, bfl.l, bfl.o, bfl.b));
		bfl bfl4 = (bfl)apr2.f().orElse(null);
		if (bfl3 != bfl4) {
			d(bdc).ifPresent(bdc::a);
		}

		bdc.s(apr2.a(awp.o));
		if (!apr2.a(awp.s) && l(bdc)) {
			bdc.l();
		}

		if (!apr2.a(awp.Q)) {
			apr2.b(awp.R);
		}

		bdc.u(apr2.a(awp.R));
	}

	private static boolean l(bdc bdc) {
		if (!bdc.x_()) {
			return false;
		} else {
			aom aom2 = bdc.cs();
			return aom2 instanceof bdc && ((bdc)aom2).x_() || aom2 instanceof bcx && ((bcx)aom2).x_();
		}
	}

	protected static void a(bdc bdc, bbg bbg) {
		s(bdc);
		bki bki3;
		if (bbg.g().b() == bkk.nt) {
			bdc.a(bbg, bbg.g().E());
			bki3 = bbg.g();
			bbg.aa();
		} else {
			bdc.a(bbg, 1);
			bki3 = a(bbg);
		}

		bke bke4 = bki3.b();
		if (a(bke4)) {
			c(bdc, bki3);
			d((aoy)bdc);
		} else if (c(bke4) && !A(bdc)) {
			y(bdc);
		} else {
			boolean boolean5 = bdc.g(bki3);
			if (!boolean5) {
				d(bdc, bki3);
			}
		}
	}

	private static void c(bdc bdc, bki bki) {
		if (E(bdc)) {
			bdc.a(bdc.b(anf.OFF_HAND));
		}

		bdc.n(bki);
	}

	private static bki a(bbg bbg) {
		bki bki2 = bbg.g();
		bki bki3 = bki2.a(1);
		if (bki2.a()) {
			bbg.aa();
		} else {
			bbg.b(bki2);
		}

		return bki3;
	}

	protected static void a(bdc bdc, boolean boolean2) {
		bki bki3 = bdc.b(anf.OFF_HAND);
		bdc.a(anf.OFF_HAND, bki.b);
		if (bdc.eM()) {
			boolean boolean4 = b(bki3.b());
			if (boolean2 && boolean4) {
				a(bdc, m(bdc));
			} else if (!boolean4) {
				boolean boolean5 = bdc.g(bki3);
				if (!boolean5) {
					d(bdc, bki3);
				}
			}
		} else {
			boolean boolean4 = bdc.g(bki3);
			if (!boolean4) {
				bki bki5 = bdc.dC();
				if (a(bki5.b())) {
					d(bdc, bki5);
				} else {
					a(bdc, Collections.singletonList(bki5));
				}

				bdc.m(bki3);
			}
		}
	}

	protected static void c(bdc bdc) {
		if (B(bdc) && !bdc.dD().a()) {
			bdc.a(bdc.dD());
			bdc.a(anf.OFF_HAND, bki.b);
		}
	}

	private static void d(bdc bdc, bki bki) {
		bki bki3 = bdc.k(bki);
		b(bdc, Collections.singletonList(bki3));
	}

	private static void a(bdc bdc, List<bki> list) {
		Optional<bec> optional3 = bdc.cI().c(awp.k);
		if (optional3.isPresent()) {
			a(bdc, (bec)optional3.get(), list);
		} else {
			b(bdc, list);
		}
	}

	private static void b(bdc bdc, List<bki> list) {
		a(bdc, list, z(bdc));
	}

	private static void a(bdc bdc, bec bec, List<bki> list) {
		a(bdc, list, bec.cz());
	}

	private static void a(bdc bdc, List<bki> list, dem dem) {
		if (!list.isEmpty()) {
			bdc.a(anf.OFF_HAND);

			for (bki bki5 : list) {
				aqi.a(bdc, bki5, dem.b(0.0, 1.0, 0.0));
			}
		}
	}

	private static List<bki> m(bdc bdc) {
		daw daw2 = bdc.l.l().aH().a(dao.ay);
		return daw2.a(new dat.a((zd)bdc.l).a(dda.a, bdc).a(bdc.l.t).a(dcz.h));
	}

	private static boolean a(aoy aoy1, aoy aoy2) {
		return aoy2.U() != aoq.G ? false : new Random(aoy1.l.Q()).nextFloat() < 0.1F;
	}

	protected static boolean a(bdc bdc, bki bki) {
		bke bke3 = bki.b();
		if (bke3.a(ada.M)) {
			return false;
		} else if (D(bdc) && bdc.cI().a(awp.o)) {
			return false;
		} else if (b(bke3)) {
			return F(bdc);
		} else {
			boolean boolean4 = bdc.l(bki);
			if (bke3 == bkk.nt) {
				return boolean4;
			} else if (c(bke3)) {
				return !A(bdc) && boolean4;
			} else {
				return !a(bke3) ? bdc.o(bki) : F(bdc) && boolean4;
			}
		}
	}

	protected static boolean a(bke bke) {
		return bke.a(ada.N);
	}

	private static boolean a(bdc bdc, aom aom) {
		if (!(aom instanceof aoz)) {
			return false;
		} else {
			aoz aoz3 = (aoz)aom;
			return !aoz3.x_() || !aoz3.aU() || h((aoy)bdc) || h(aoz3) || aoz3 instanceof bdc && aoz3.cs() == null;
		}
	}

	private static boolean d(bdc bdc, aoy aoy) {
		return o(bdc).filter(aoy2 -> aoy2 == aoy).isPresent();
	}

	private static boolean n(bdc bdc) {
		apr<bdc> apr2 = bdc.cI();
		if (apr2.a(awp.aa)) {
			aoy aoy3 = (aoy)apr2.c(awp.aa).get();
			return bdc.a(aoy3, 6.0);
		} else {
			return false;
		}
	}

	private static Optional<? extends aoy> o(bdc bdc) {
		apr<bdc> apr2 = bdc.cI();
		if (n(bdc)) {
			return Optional.empty();
		} else {
			Optional<aoy> optional3 = aqi.a(bdc, awp.L);
			if (optional3.isPresent() && e((aoy)optional3.get())) {
				return optional3;
			} else {
				if (apr2.a(awp.M)) {
					Optional<bec> optional4 = apr2.c(awp.l);
					if (optional4.isPresent()) {
						return optional4;
					}
				}

				Optional<aoz> optional4 = apr2.c(awp.K);
				if (optional4.isPresent()) {
					return optional4;
				} else {
					Optional<bec> optional5 = apr2.c(awp.V);
					return optional5.isPresent() && e((aoy)optional5.get()) ? optional5 : Optional.empty();
				}
			}
		}
	}

	public static void a(bec bec, boolean boolean2) {
		List<bdc> list3 = bec.l.a(bdc.class, bec.cb().g(16.0));
		list3.stream().filter(bdd::k).filter(bdc -> !boolean2 || aqi.c(bdc, bec)).forEach(bdc -> {
			if (bdc.l.S().b(bpx.G)) {
				f(bdc, bec);
			} else {
				c(bdc, bec);
			}
		});
	}

	public static ang a(bdc bdc, bec bec, anf anf) {
		bki bki4 = bec.b(anf);
		if (b(bdc, bki4)) {
			bki bki5 = bki4.a(1);
			c(bdc, bki5);
			d((aoy)bdc);
			s(bdc);
			return ang.CONSUME;
		} else {
			return ang.PASS;
		}
	}

	protected static boolean b(bdc bdc, bki bki) {
		return !D(bdc) && !B(bdc) && bdc.eM() && b(bki.b());
	}

	protected static void a(bdc bdc, aoy aoy) {
		if (!(aoy instanceof bdc)) {
			if (E(bdc)) {
				a(bdc, false);
			}

			apr<bdc> apr3 = bdc.cI();
			apr3.b(awp.Q);
			apr3.b(awp.R);
			apr3.b(awp.N);
			if (aoy instanceof bec) {
				apr3.a(awp.O, true, 400L);
			}

			h(bdc).ifPresent(aoy3 -> {
				if (aoy3.U() != aoy.U()) {
					apr3.b(awp.z);
				}
			});
			if (bdc.x_()) {
				apr3.a(awp.z, aoy, 100L);
				if (e(aoy)) {
					b(bdc, aoy);
				}
			} else if (aoy.U() == aoq.G && w(bdc)) {
				j(bdc, aoy);
				h(bdc, aoy);
			} else {
				e(bdc, aoy);
			}
		}
	}

	private static void e(bdc bdc, aoy aoy) {
		if (!bdc.cI().c(bfl.n)) {
			if (e(aoy)) {
				if (!aqi.a(bdc, aoy, 4.0)) {
					if (aoy.U() == aoq.bb && bdc.l.S().b(bpx.G)) {
						f(bdc, aoy);
						f(bdc);
					} else {
						c(bdc, aoy);
						b(bdc, aoy);
					}
				}
			}
		}
	}

	public static Optional<ack> d(bdc bdc) {
		return bdc.cI().f().map(bfl -> a(bdc, bfl));
	}

	private static ack a(bdc bdc, bfl bfl) {
		if (bfl == bfl.k) {
			return acl.kT;
		} else if (bdc.eO()) {
			return acl.kY;
		} else if (bfl == bfl.n && p(bdc)) {
			return acl.kY;
		} else if (bfl == bfl.m) {
			return acl.kR;
		} else if (bfl == bfl.l) {
			return acl.kU;
		} else if (f((aoy)bdc)) {
			return acl.kW;
		} else {
			return C(bdc) ? acl.kY : acl.kS;
		}
	}

	private static boolean p(bdc bdc) {
		apr<bdc> apr2 = bdc.cI();
		return !apr2.a(awp.z) ? false : ((aoy)apr2.c(awp.z).get()).a(bdc, 12.0);
	}

	protected static boolean e(bdc bdc) {
		return bdc.cI().a(awp.P) || q(bdc).stream().anyMatch(bdcx -> bdcx.cI().a(awp.P));
	}

	private static List<bdc> q(bdc bdc) {
		return (List<bdc>)bdc.cI().c(awp.X).orElse(ImmutableList.of());
	}

	private static List<bdc> r(bdc bdc) {
		return (List<bdc>)bdc.cI().c(awp.W).orElse(ImmutableList.of());
	}

	public static boolean a(aoy aoy) {
		for (bki bki4 : aoy.bk()) {
			bke bke5 = bki4.b();
			if (bke5 instanceof bid && ((bid)bke5).ad_() == bif.GOLD) {
				return true;
			}
		}

		return false;
	}

	private static void s(bdc bdc) {
		bdc.cI().b(awp.m);
		bdc.x().o();
	}

	private static arv<bdc> f() {
		return new arv<>(new aql<>(bdc::x_, awp.T, awp.s, d), c);
	}

	protected static void b(bdc bdc, aoy aoy) {
		r(bdc).forEach(bdcx -> {
			if (aoy.U() != aoq.G || bdcx.eN() && ((bcx)aoy).eP()) {
				g(bdcx, aoy);
			}
		});
	}

	protected static void f(bdc bdc) {
		r(bdc).forEach(bdcx -> i(bdcx).ifPresent(bec -> c(bdcx, bec)));
	}

	protected static void g(bdc bdc) {
		q(bdc).forEach(bdd::j);
	}

	protected static void c(bdc bdc, aoy aoy) {
		if (e(aoy)) {
			bdc.cI().b(awp.D);
			bdc.cI().a(awp.L, aoy.bR(), 600L);
			if (aoy.U() == aoq.G) {
				j(bdc);
			}

			if (aoy.U() == aoq.bb && bdc.l.S().b(bpx.G)) {
				bdc.cI().a(awp.M, true, 600L);
			}
		}
	}

	private static void f(bdc bdc, aoy aoy) {
		Optional<bec> optional3 = i(bdc);
		if (optional3.isPresent()) {
			c(bdc, (aoy)optional3.get());
		} else {
			c(bdc, aoy);
		}
	}

	private static void g(bdc bdc, aoy aoy) {
		Optional<aoy> optional3 = t(bdc);
		aoy aoy4 = aqi.a(bdc, optional3, aoy);
		if (!optional3.isPresent() || optional3.get() != aoy4) {
			c(bdc, aoy4);
		}
	}

	private static Optional<aoy> t(bdc bdc) {
		return aqi.a(bdc, awp.L);
	}

	public static Optional<aoy> h(bdc bdc) {
		return bdc.cI().a(awp.z) ? bdc.cI().c(awp.z) : Optional.empty();
	}

	public static Optional<bec> i(bdc bdc) {
		return bdc.cI().a(awp.l) ? bdc.cI().c(awp.l) : Optional.empty();
	}

	private static void h(bdc bdc, aoy aoy) {
		q(bdc).forEach(bdcx -> i(bdcx, aoy));
	}

	private static void i(bdc bdc, aoy aoy) {
		apr<bdc> apr3 = bdc.cI();
		aoy aoy4 = aqi.a(bdc, apr3.c(awp.z), aoy);
		aoy4 = aqi.a(bdc, apr3.c(awp.o), aoy4);
		j(bdc, aoy4);
	}

	private static boolean u(bdc bdc) {
		apr<bdc> apr2 = bdc.cI();
		if (!apr2.a(awp.z)) {
			return true;
		} else {
			aoy aoy3 = (aoy)apr2.c(awp.z).get();
			aoq<?> aoq4 = aoy3.U();
			if (aoq4 == aoq.G) {
				return v(bdc);
			} else {
				return a(aoq4) ? !apr2.b(awp.aa, aoy3) : false;
			}
		}
	}

	private static boolean v(bdc bdc) {
		return !w(bdc);
	}

	private static boolean w(bdc bdc) {
		int integer2 = (Integer)bdc.cI().c(awp.ab).orElse(0) + 1;
		int integer3 = (Integer)bdc.cI().c(awp.ac).orElse(0);
		return integer3 > integer2;
	}

	private static void j(bdc bdc, aoy aoy) {
		bdc.cI().b(awp.L);
		bdc.cI().b(awp.o);
		bdc.cI().b(awp.m);
		bdc.cI().a(awp.z, aoy, (long)e.a(bdc.l.t));
		j(bdc);
	}

	protected static void j(bdc bdc) {
		bdc.cI().a(awp.P, true, (long)b.a(bdc.l.t));
	}

	private static void y(bdc bdc) {
		bdc.cI().a(awp.ae, true, 200L);
	}

	private static dem z(bdc bdc) {
		dem dem2 = axu.b(bdc, 4, 2);
		return dem2 == null ? bdc.cz() : dem2;
	}

	private static boolean A(bdc bdc) {
		return bdc.cI().a(awp.ae);
	}

	protected static boolean k(bdc bdc) {
		return bdc.cI().c(bfl.b);
	}

	private static boolean c(aoy aoy) {
		return aoy.a(bkk.qP);
	}

	private static void d(aoy aoy) {
		aoy.cI().a(awp.N, true, 120L);
	}

	private static boolean B(bdc bdc) {
		return bdc.cI().a(awp.N);
	}

	private static boolean b(bke bke) {
		return bke == a;
	}

	private static boolean c(bke bke) {
		return h.contains(bke);
	}

	private static boolean e(aoy aoy) {
		return aop.f.test(aoy);
	}

	private static boolean C(bdc bdc) {
		return bdc.cI().a(awp.af);
	}

	private static boolean f(aoy aoy) {
		return aoy.cI().a(awp.ad);
	}

	private static boolean g(aoy aoy) {
		return !f(aoy);
	}

	public static boolean b(aoy aoy) {
		return aoy.U() == aoq.bb && aoy.a(bdd::a);
	}

	private static boolean D(bdc bdc) {
		return bdc.cI().a(awp.O);
	}

	private static boolean h(aoy aoy) {
		return aoy.cI().a(awp.x);
	}

	private static boolean E(bdc bdc) {
		return !bdc.dD().a();
	}

	private static boolean F(bdc bdc) {
		return bdc.dD().a() || !a(bdc.dD().b());
	}

	public static boolean a(aoq aoq) {
		return aoq == aoq.ba || aoq == aoq.aW;
	}
}
