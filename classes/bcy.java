import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Optional;

public class bcy {
	private static final adx a = aej.a(5, 20);
	private static final adx b = adx.a(5, 16);

	protected static apr<?> a(apr<bcx> apr) {
		b(apr);
		c(apr);
		d(apr);
		e(apr);
		apr.a(ImmutableSet.of(bfl.a));
		apr.b(bfl.b);
		apr.e();
		return apr;
	}

	private static void b(apr<bcx> apr) {
		apr.a(bfl.a, 0, ImmutableList.of(new arg(45, 90), new ark(200)));
	}

	private static void c(apr<bcx> apr) {
		apr.a(
			bfl.b,
			10,
			ImmutableList.of(
				new aqg(awp.af, 200),
				new aqc(aoq.G, 0.6F),
				asb.a(awp.af, 1.0F, 8, true),
				new asi(bcy::d),
				new art<apg>(bcx::eM, (aqh<? super apg>)asb.b(awp.Z, 0.4F, 8, false)),
				new arv<aoy>(new arx(8.0F), adx.a(30, 60)),
				new aqe(b, 0.6F),
				a()
			)
		);
	}

	private static void d(apr<bcx> apr) {
		apr.a(
			bfl.k,
			10,
			ImmutableList.of(
				new aqg(awp.af, 200),
				new aqc(aoq.G, 0.6F),
				new asc(1.0F),
				new art<>(bcx::eM, new arh(40)),
				new art<>(aok::x_, new arh(15)),
				new ask(),
				new aqq(bcy::i, awp.o)
			),
			awp.o
		);
	}

	private static void e(apr<bcx> apr) {
		apr.a(bfl.n, 10, ImmutableList.of(asb.b(awp.z, 1.3F, 15, false), a(), new arv<aoy>(new arx(8.0F), adx.a(30, 60)), new aqq(bcy::e, awp.z)), awp.z);
	}

	private static aru<bcx> a() {
		return new aru<>(ImmutableList.of(Pair.of(new aro(0.4F), 2), Pair.of(new ase(0.4F, 3), 2), Pair.of(new aqo(30, 60), 1)));
	}

	protected static void a(bcx bcx) {
		apr<bcx> apr2 = bcx.cI();
		bfl bfl3 = (bfl)apr2.f().orElse(null);
		apr2.a(ImmutableList.of(bfl.k, bfl.n, bfl.b));
		bfl bfl4 = (bfl)apr2.f().orElse(null);
		if (bfl3 != bfl4) {
			b(bcx).ifPresent(bcx::a);
		}

		bcx.s(apr2.a(awp.o));
	}

	protected static void a(bcx bcx, aoy aoy) {
		if (!bcx.x_()) {
			if (aoy.U() == aoq.ai && f(bcx)) {
				e(bcx, aoy);
				c(bcx, aoy);
			} else {
				h(bcx, aoy);
			}
		}
	}

	private static void c(bcx bcx, aoy aoy) {
		g(bcx).forEach(bcxx -> d(bcxx, aoy));
	}

	private static void d(bcx bcx, aoy aoy) {
		apr<bcx> apr4 = bcx.cI();
		aoy aoy3 = aqi.a(bcx, apr4.c(awp.z), aoy);
		aoy3 = aqi.a(bcx, apr4.c(awp.o), aoy3);
		e(bcx, aoy3);
	}

	private static void e(bcx bcx, aoy aoy) {
		bcx.cI().b(awp.o);
		bcx.cI().b(awp.m);
		bcx.cI().a(awp.z, aoy, (long)a.a(bcx.l.t));
	}

	private static Optional<? extends aoy> d(bcx bcx) {
		return !c(bcx) && !i(bcx) ? bcx.cI().c(awp.l) : Optional.empty();
	}

	static boolean a(bcx bcx, fu fu) {
		Optional<fu> optional3 = bcx.cI().c(awp.af);
		return optional3.isPresent() && ((fu)optional3.get()).a(fu, 8.0);
	}

	private static boolean e(bcx bcx) {
		return bcx.eM() && !f(bcx);
	}

	private static boolean f(bcx bcx) {
		if (bcx.x_()) {
			return false;
		} else {
			int integer2 = (Integer)bcx.cI().c(awp.ab).orElse(0);
			int integer3 = (Integer)bcx.cI().c(awp.ac).orElse(0) + 1;
			return integer2 > integer3;
		}
	}

	protected static void b(bcx bcx, aoy aoy) {
		apr<bcx> apr3 = bcx.cI();
		apr3.b(awp.ag);
		apr3.b(awp.r);
		if (bcx.x_()) {
			d(bcx, aoy);
		} else {
			f(bcx, aoy);
		}
	}

	private static void f(bcx bcx, aoy aoy) {
		if (!bcx.cI().c(bfl.n) || aoy.U() != aoq.ai) {
			if (aop.f.test(aoy)) {
				if (aoy.U() != aoq.G) {
					if (!aqi.a(bcx, aoy, 4.0)) {
						g(bcx, aoy);
						h(bcx, aoy);
					}
				}
			}
		}
	}

	private static void g(bcx bcx, aoy aoy) {
		apr<bcx> apr3 = bcx.cI();
		apr3.b(awp.D);
		apr3.b(awp.r);
		apr3.a(awp.o, aoy, 200L);
	}

	private static void h(bcx bcx, aoy aoy) {
		g(bcx).forEach(bcxx -> i(bcxx, aoy));
	}

	private static void i(bcx bcx, aoy aoy) {
		if (!c(bcx)) {
			Optional<aoy> optional3 = bcx.cI().c(awp.o);
			aoy aoy4 = aqi.a(bcx, optional3, aoy);
			g(bcx, aoy4);
		}
	}

	public static Optional<ack> b(bcx bcx) {
		return bcx.cI().f().map(bfl -> a(bcx, bfl));
	}

	private static ack a(bcx bcx, bfl bfl) {
		if (bfl == bfl.n || bcx.eO()) {
			return acl.fH;
		} else if (bfl == bfl.k) {
			return acl.fC;
		} else {
			return h(bcx) ? acl.fH : acl.fB;
		}
	}

	private static List<bcx> g(bcx bcx) {
		return (List<bcx>)bcx.cI().c(awp.Y).orElse(ImmutableList.of());
	}

	private static boolean h(bcx bcx) {
		return bcx.cI().a(awp.af);
	}

	private static boolean i(bcx bcx) {
		return bcx.cI().a(awp.r);
	}

	protected static boolean c(bcx bcx) {
		return bcx.cI().a(awp.ag);
	}
}
