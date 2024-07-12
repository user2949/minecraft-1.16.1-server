import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ayc {
	private static final Supplier<Set<ayc>> y = Suppliers.memoize(() -> (Set<ayc>)gl.aS.e().map(bds::b).collect(Collectors.toSet()));
	public static final Predicate<ayc> a = ayc -> ((Set)y.get()).contains(ayc);
	public static final Predicate<ayc> b = ayc -> true;
	private static final Set<cfj> z = (Set<cfj>)ImmutableList.of(
			bvs.aL, bvs.aM, bvs.aI, bvs.aJ, bvs.aG, bvs.aE, bvs.aK, bvs.aA, bvs.aF, bvs.aC, bvs.az, bvs.ay, bvs.aD, bvs.aH, bvs.ax, bvs.aB
		)
		.stream()
		.flatMap(bvr -> bvr.m().a().stream())
		.filter(cfj -> cfj.c(bvm.a) == cfx.HEAD)
		.collect(ImmutableSet.toImmutableSet());
	private static final Map<cfj, ayc> A = Maps.<cfj, ayc>newHashMap();
	public static final ayc c = a("unemployed", ImmutableSet.of(), 1, a, 1);
	public static final ayc d = a("armorer", a(bvs.lU), 1, 1);
	public static final ayc e = a("butcher", a(bvs.lT), 1, 1);
	public static final ayc f = a("cartographer", a(bvs.lV), 1, 1);
	public static final ayc g = a("cleric", a(bvs.ea), 1, 1);
	public static final ayc h = a("farmer", a(bvs.na), 1, 1);
	public static final ayc i = a("fisherman", a(bvs.lS), 1, 1);
	public static final ayc j = a("fletcher", a(bvs.lW), 1, 1);
	public static final ayc k = a("leatherworker", a(bvs.eb), 1, 1);
	public static final ayc l = a("librarian", a(bvs.lY), 1, 1);
	public static final ayc m = a("mason", a(bvs.ma), 1, 1);
	public static final ayc n = a("nitwit", ImmutableSet.of(), 1, 1);
	public static final ayc o = a("shepherd", a(bvs.lR), 1, 1);
	public static final ayc p = a("toolsmith", a(bvs.lZ), 1, 1);
	public static final ayc q = a("weaponsmith", a(bvs.lX), 1, 1);
	public static final ayc r = a("home", z, 1, 1);
	public static final ayc s = a("meeting", a(bvs.mb), 32, 6);
	public static final ayc t = a("beehive", a(bvs.nd), 0, 1);
	public static final ayc u = a("bee_nest", a(bvs.nc), 0, 1);
	public static final ayc v = a("nether_portal", a(bvs.cT), 0, 1);
	public static final ayc w = a("lodestone", a(bvs.no), 0, 1);
	protected static final Set<cfj> x = new ObjectOpenHashSet<>(A.keySet());
	private final String B;
	private final Set<cfj> C;
	private final int D;
	private final Predicate<ayc> E;
	private final int F;

	private static Set<cfj> a(bvr bvr) {
		return ImmutableSet.copyOf(bvr.m().a());
	}

	private ayc(String string, Set<cfj> set, int integer3, Predicate<ayc> predicate, int integer5) {
		this.B = string;
		this.C = ImmutableSet.copyOf(set);
		this.D = integer3;
		this.E = predicate;
		this.F = integer5;
	}

	private ayc(String string, Set<cfj> set, int integer3, int integer4) {
		this.B = string;
		this.C = ImmutableSet.copyOf(set);
		this.D = integer3;
		this.E = ayc -> ayc == this;
		this.F = integer4;
	}

	public int b() {
		return this.D;
	}

	public Predicate<ayc> c() {
		return this.E;
	}

	public int d() {
		return this.F;
	}

	public String toString() {
		return this.B;
	}

	private static ayc a(String string, Set<cfj> set, int integer3, int integer4) {
		return a(gl.a(gl.aT, new uh(string), new ayc(string, set, integer3, integer4)));
	}

	private static ayc a(String string, Set<cfj> set, int integer3, Predicate<ayc> predicate, int integer5) {
		return a(gl.a(gl.aT, new uh(string), new ayc(string, set, integer3, predicate, integer5)));
	}

	private static ayc a(ayc ayc) {
		ayc.C.forEach(cfj -> {
			ayc ayc3 = (ayc)A.put(cfj, ayc);
			if (ayc3 != null) {
				throw (IllegalStateException)v.c(new IllegalStateException(String.format("%s is defined in too many tags", cfj)));
			}
		});
		return ayc;
	}

	public static Optional<ayc> b(cfj cfj) {
		return Optional.ofNullable(A.get(cfj));
	}
}
