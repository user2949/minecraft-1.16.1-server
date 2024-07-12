import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class iy {
	private final Map<iz, uh> a = Maps.<iz, uh>newHashMap();
	private final Set<iz> b = Sets.<iz>newHashSet();

	public iy a(iz iz, uh uh) {
		this.a.put(iz, uh);
		return this;
	}

	public Stream<iz> a() {
		return this.b.stream();
	}

	public iy b(iz iz1, iz iz2) {
		this.a.put(iz2, this.a.get(iz1));
		this.b.add(iz2);
		return this;
	}

	public uh a(iz iz) {
		for (iz iz3 = iz; iz3 != null; iz3 = iz3.b()) {
			uh uh4 = (uh)this.a.get(iz3);
			if (uh4 != null) {
				return uh4;
			}
		}

		throw new IllegalStateException("Can't find texture for slot " + iz);
	}

	public iy c(iz iz, uh uh) {
		iy iy4 = new iy();
		iy4.a.putAll(this.a);
		iy4.b.addAll(this.b);
		iy4.a(iz, uh);
		return iy4;
	}

	public static iy a(bvr bvr) {
		uh uh2 = C(bvr);
		return b(uh2);
	}

	public static iy b(bvr bvr) {
		uh uh2 = C(bvr);
		return a(uh2);
	}

	public static iy a(uh uh) {
		return new iy().a(iz.b, uh);
	}

	public static iy b(uh uh) {
		return new iy().a(iz.a, uh);
	}

	public static iy c(bvr bvr) {
		return d(iz.p, C(bvr));
	}

	public static iy c(uh uh) {
		return d(iz.p, uh);
	}

	public static iy d(bvr bvr) {
		return d(iz.q, C(bvr));
	}

	public static iy d(uh uh) {
		return d(iz.q, uh);
	}

	public static iy e(bvr bvr) {
		return d(iz.s, C(bvr));
	}

	public static iy e(uh uh) {
		return d(iz.s, uh);
	}

	public static iy f(bvr bvr) {
		return d(iz.t, C(bvr));
	}

	public static iy g(bvr bvr) {
		return d(iz.y, C(bvr));
	}

	public static iy a(bvr bvr1, bvr bvr2) {
		return new iy().a(iz.y, C(bvr1)).a(iz.z, C(bvr2));
	}

	public static iy h(bvr bvr) {
		return d(iz.u, C(bvr));
	}

	public static iy i(bvr bvr) {
		return d(iz.x, C(bvr));
	}

	public static iy g(uh uh) {
		return d(iz.A, uh);
	}

	public static iy b(bvr bvr1, bvr bvr2) {
		return new iy().a(iz.v, C(bvr1)).a(iz.w, a(bvr2, "_top"));
	}

	public static iy d(iz iz, uh uh) {
		return new iy().a(iz, uh);
	}

	public static iy j(bvr bvr) {
		return new iy().a(iz.i, a(bvr, "_side")).a(iz.d, a(bvr, "_top"));
	}

	public static iy k(bvr bvr) {
		return new iy().a(iz.i, a(bvr, "_side")).a(iz.f, a(bvr, "_top"));
	}

	public static iy l(bvr bvr) {
		return new iy().a(iz.i, C(bvr)).a(iz.d, a(bvr, "_top"));
	}

	public static iy a(uh uh1, uh uh2) {
		return new iy().a(iz.i, uh1).a(iz.d, uh2);
	}

	public static iy m(bvr bvr) {
		return new iy().a(iz.i, a(bvr, "_side")).a(iz.f, a(bvr, "_top")).a(iz.e, a(bvr, "_bottom"));
	}

	public static iy n(bvr bvr) {
		uh uh2 = C(bvr);
		return new iy().a(iz.r, uh2).a(iz.i, uh2).a(iz.f, a(bvr, "_top")).a(iz.e, a(bvr, "_bottom"));
	}

	public static iy o(bvr bvr) {
		uh uh2 = C(bvr);
		return new iy().a(iz.r, uh2).a(iz.i, uh2).a(iz.d, a(bvr, "_top"));
	}

	public static iy p(bvr bvr) {
		return new iy().a(iz.f, a(bvr, "_top")).a(iz.e, a(bvr, "_bottom"));
	}

	public static iy q(bvr bvr) {
		return new iy().a(iz.c, C(bvr));
	}

	public static iy h(uh uh) {
		return new iy().a(iz.c, uh);
	}

	public static iy r(bvr bvr) {
		return new iy().a(iz.C, a(bvr, "_0"));
	}

	public static iy s(bvr bvr) {
		return new iy().a(iz.C, a(bvr, "_1"));
	}

	public static iy t(bvr bvr) {
		return new iy().a(iz.D, C(bvr));
	}

	public static iy u(bvr bvr) {
		return new iy().a(iz.G, C(bvr));
	}

	public static iy i(uh uh) {
		return new iy().a(iz.G, uh);
	}

	public static iy a(bke bke) {
		return new iy().a(iz.c, c(bke));
	}

	public static iy v(bvr bvr) {
		return new iy().a(iz.i, a(bvr, "_side")).a(iz.g, a(bvr, "_front")).a(iz.h, a(bvr, "_back"));
	}

	public static iy w(bvr bvr) {
		return new iy().a(iz.i, a(bvr, "_side")).a(iz.g, a(bvr, "_front")).a(iz.f, a(bvr, "_top")).a(iz.e, a(bvr, "_bottom"));
	}

	public static iy x(bvr bvr) {
		return new iy().a(iz.i, a(bvr, "_side")).a(iz.g, a(bvr, "_front")).a(iz.f, a(bvr, "_top"));
	}

	public static iy y(bvr bvr) {
		return new iy().a(iz.i, a(bvr, "_side")).a(iz.g, a(bvr, "_front")).a(iz.d, a(bvr, "_end"));
	}

	public static iy z(bvr bvr) {
		return new iy().a(iz.f, a(bvr, "_top"));
	}

	public static iy c(bvr bvr1, bvr bvr2) {
		return new iy()
			.a(iz.c, a(bvr1, "_front"))
			.a(iz.o, C(bvr2))
			.a(iz.n, a(bvr1, "_top"))
			.a(iz.j, a(bvr1, "_front"))
			.a(iz.l, a(bvr1, "_side"))
			.a(iz.k, a(bvr1, "_side"))
			.a(iz.m, a(bvr1, "_front"));
	}

	public static iy d(bvr bvr1, bvr bvr2) {
		return new iy()
			.a(iz.c, a(bvr1, "_front"))
			.a(iz.o, C(bvr2))
			.a(iz.n, a(bvr1, "_top"))
			.a(iz.j, a(bvr1, "_front"))
			.a(iz.k, a(bvr1, "_front"))
			.a(iz.l, a(bvr1, "_side"))
			.a(iz.m, a(bvr1, "_side"));
	}

	public static iy A(bvr bvr) {
		return new iy().a(iz.I, a(bvr, "_log_lit")).a(iz.C, a(bvr, "_fire"));
	}

	public static iy b(bke bke) {
		return new iy().a(iz.H, c(bke));
	}

	public static iy B(bvr bvr) {
		return new iy().a(iz.H, C(bvr));
	}

	public static iy j(uh uh) {
		return new iy().a(iz.H, uh);
	}

	public static uh C(bvr bvr) {
		uh uh2 = gl.aj.b(bvr);
		return new uh(uh2.b(), "block/" + uh2.a());
	}

	public static uh a(bvr bvr, String string) {
		uh uh3 = gl.aj.b(bvr);
		return new uh(uh3.b(), "block/" + uh3.a() + string);
	}

	public static uh c(bke bke) {
		uh uh2 = gl.am.b(bke);
		return new uh(uh2.b(), "item/" + uh2.a());
	}

	public static uh a(bke bke, String string) {
		uh uh3 = gl.am.b(bke);
		return new uh(uh3.b(), "item/" + uh3.a() + string);
	}
}
