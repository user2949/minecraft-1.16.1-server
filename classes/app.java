import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;

public class app {
	private static final Map<aoq<?>, app.a> a = Maps.<aoq<?>, app.a>newHashMap();

	private static <T extends aoz> void a(aoq<T> aoq, app.c c, cio.a a, app.b<T> b) {
		app.a a5 = (app.a)app.a.put(aoq, new app.a(a, c, b));
		if (a5 != null) {
			throw new IllegalStateException("Duplicate registration for type " + gl.al.b(aoq));
		}
	}

	public static app.c a(aoq<?> aoq) {
		app.a a2 = (app.a)a.get(aoq);
		return a2 == null ? app.c.NO_RESTRICTIONS : a2.b;
	}

	public static cio.a b(@Nullable aoq<?> aoq) {
		app.a a2 = (app.a)a.get(aoq);
		return a2 == null ? cio.a.MOTION_BLOCKING_NO_LEAVES : a2.a;
	}

	public static <T extends aom> boolean a(aoq<T> aoq, bqc bqc, apb apb, fu fu, Random random) {
		app.a a6 = (app.a)a.get(aoq);
		return a6 == null || a6.c.test(aoq, bqc, apb, fu, random);
	}

	static {
		a(aoq.k, app.c.IN_WATER, cio.a.MOTION_BLOCKING_NO_LEAVES, ayh::b);
		a(aoq.n, app.c.IN_WATER, cio.a.MOTION_BLOCKING_NO_LEAVES, ayq::b);
		a(aoq.q, app.c.IN_WATER, cio.a.MOTION_BLOCKING_NO_LEAVES, bbp::b);
		a(aoq.F, app.c.IN_WATER, cio.a.MOTION_BLOCKING_NO_LEAVES, bbx::b);
		a(aoq.am, app.c.IN_WATER, cio.a.MOTION_BLOCKING_NO_LEAVES, ayh::b);
		a(aoq.ap, app.c.IN_WATER, cio.a.MOTION_BLOCKING_NO_LEAVES, ayh::b);
		a(aoq.aC, app.c.IN_WATER, cio.a.MOTION_BLOCKING_NO_LEAVES, azg::b);
		a(aoq.aL, app.c.IN_WATER, cio.a.MOTION_BLOCKING_NO_LEAVES, ayh::b);
		a(aoq.d, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, ayf::b);
		a(aoq.f, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, bcb::d);
		a(aoq.i, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, bcb::c);
		a(aoq.j, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, ayk::b);
		a(aoq.l, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, ayk::b);
		a(aoq.m, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, bcb::c);
		a(aoq.o, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, ayk::b);
		a(aoq.u, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, bcb::c);
		a(aoq.v, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, bbs::b);
		a(aoq.t, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, aoz::a);
		a(aoq.D, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, bbv::b);
		a(aoq.E, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, bcb::c);
		a(aoq.H, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, ayk::b);
		a(aoq.I, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, bby::b);
		a(aoq.K, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, aoz::a);
		a(aoq.Q, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, ayk::b);
		a(aoq.S, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, bca::b);
		a(aoq.ab, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, ayu::c);
		a(aoq.aa, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, ayk::b);
		a(aoq.ac, app.c.ON_GROUND, cio.a.MOTION_BLOCKING, ayv::c);
		a(aoq.af, app.c.ON_GROUND, cio.a.MOTION_BLOCKING, ayx::c);
		a(aoq.ah, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, ayk::b);
		a(aoq.G, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, bcx::c);
		a(aoq.ai, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, bdc::b);
		a(aoq.aj, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, bcc::b);
		a(aoq.ak, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, ayz::c);
		a(aoq.an, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, azb::c);
		a(aoq.aq, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, ayk::b);
		a(aoq.at, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, bci::b);
		a(aoq.au, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, bcb::c);
		a(aoq.av, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, ayk::b);
		a(aoq.aw, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, bck::c);
		a(aoq.ay, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, aoz::a);
		a(aoq.aB, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, bcb::c);
		a(aoq.aD, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, bcn::b);
		a(aoq.aE, app.c.IN_LAVA, cio.a.MOTION_BLOCKING_NO_LEAVES, bco::c);
		a(aoq.aM, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, azi::c);
		a(aoq.aO, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, aoz::a);
		a(aoq.aR, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, bcb::c);
		a(aoq.aS, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, bcb::c);
		a(aoq.aT, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, bcb::c);
		a(aoq.aV, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, ayk::b);
		a(aoq.aX, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, bcb::c);
		a(aoq.aY, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, ayk::b);
		a(aoq.ba, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, bcw::b);
		a(aoq.aZ, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, bcb::c);
		a(aoq.h, app.c.ON_GROUND, cio.a.MOTION_BLOCKING_NO_LEAVES, ayk::b);
		a(aoq.r, app.c.IN_WATER, cio.a.MOTION_BLOCKING_NO_LEAVES, bbx::b);
		a(aoq.w, app.c.NO_RESTRICTIONS, cio.a.MOTION_BLOCKING_NO_LEAVES, bcb::c);
		a(aoq.C, app.c.NO_RESTRICTIONS, cio.a.MOTION_BLOCKING_NO_LEAVES, ayk::b);
		a(aoq.J, app.c.NO_RESTRICTIONS, cio.a.MOTION_BLOCKING_NO_LEAVES, bcb::c);
		a(aoq.ae, app.c.NO_RESTRICTIONS, cio.a.MOTION_BLOCKING_NO_LEAVES, ayk::b);
		a(aoq.ag, app.c.NO_RESTRICTIONS, cio.a.MOTION_BLOCKING_NO_LEAVES, aoz::a);
		a(aoq.ao, app.c.NO_RESTRICTIONS, cio.a.MOTION_BLOCKING_NO_LEAVES, bcb::c);
		a(aoq.ar, app.c.NO_RESTRICTIONS, cio.a.MOTION_BLOCKING_NO_LEAVES, aoz::a);
		a(aoq.aK, app.c.NO_RESTRICTIONS, cio.a.MOTION_BLOCKING_NO_LEAVES, ayk::b);
		a(aoq.aN, app.c.NO_RESTRICTIONS, cio.a.MOTION_BLOCKING_NO_LEAVES, bcb::c);
		a(aoq.aP, app.c.NO_RESTRICTIONS, cio.a.MOTION_BLOCKING_NO_LEAVES, bcb::c);
		a(aoq.aQ, app.c.NO_RESTRICTIONS, cio.a.MOTION_BLOCKING_NO_LEAVES, aoz::a);
	}

	static class a {
		private final cio.a a;
		private final app.c b;
		private final app.b<?> c;

		public a(cio.a a, app.c c, app.b<?> b) {
			this.a = a;
			this.b = c;
			this.c = b;
		}
	}

	@FunctionalInterface
	public interface b<T extends aom> {
		boolean test(aoq<T> aoq, bqc bqc, apb apb, fu fu, Random random);
	}

	public static enum c {
		ON_GROUND,
		IN_WATER,
		NO_RESTRICTIONS,
		IN_LAVA;
	}
}
