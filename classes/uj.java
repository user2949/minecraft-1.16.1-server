import bpx.f;
import java.io.PrintStream;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class uj {
	public static final PrintStream a = System.out;
	private static boolean b;
	private static final Logger c = LogManager.getLogger();

	public static void a() {
		if (!b) {
			b = true;
			if (gl.h.b().isEmpty()) {
				throw new IllegalStateException("Unable to load registries");
			} else {
				bxv.c();
				bwn.c();
				if (aoq.a(aoq.bb) == null) {
					throw new IllegalStateException("Failed loading EntityTypes");
				} else {
					bmc.a();
					fb.a();
					gw.c();
					fh.a();
					d();
				}
			}
		}
	}

	private static <T> void a(Iterable<T> iterable, Function<T, String> function, Set<String> set) {
		kz kz4 = kz.a();
		iterable.forEach(object -> {
			String string5 = (String)function.apply(object);
			if (!kz4.b(string5)) {
				set.add(string5);
			}
		});
	}

	private static void a(Set<String> set) {
		final kz kz2 = kz.a();
		bpx.a(new bpx.c() {
			@Override
			public <T extends bpx.g<T>> void a(bpx.e<T> e, f<T> f) {
				if (!kz2.b(e.b())) {
					set.add(e.a());
				}
			}
		});
	}

	public static Set<String> b() {
		Set<String> set1 = new TreeSet();
		a(gl.aP, aps::c, set1);
		a(gl.al, aoq::f, set1);
		a(gl.ai, aoe::c, set1);
		a(gl.am, bke::a, set1);
		a(gl.ak, bnw::g, set1);
		a(gl.as, bre::n, set1);
		a(gl.aj, bvr::i, set1);
		a(gl.aE, uh -> "stat." + uh.toString().replace(':', '.'), set1);
		a(set1);
		return set1;
	}

	public static void c() {
		if (!b) {
			throw new IllegalArgumentException("Not bootstrapped");
		} else {
			if (u.d) {
				b().forEach(string -> c.error("Missing translations: " + string));
			}

			apy.a();
		}
	}

	private static void d() {
		if (c.isDebugEnabled()) {
			System.setErr(new un("STDERR", System.err));
			System.setOut(new un("STDOUT", a));
		} else {
			System.setErr(new up("STDERR", System.err));
			System.setOut(new up("STDOUT", a));
		}
	}

	public static void a(String string) {
		a.println(string);
	}
}
