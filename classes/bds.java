import com.google.common.collect.ImmutableSet;
import javax.annotation.Nullable;

public class bds {
	public static final bds a = a("none", ayc.c, null);
	public static final bds b = a("armorer", ayc.d, acl.pS);
	public static final bds c = a("butcher", ayc.e, acl.pT);
	public static final bds d = a("cartographer", ayc.f, acl.pU);
	public static final bds e = a("cleric", ayc.g, acl.pV);
	public static final bds f = a("farmer", ayc.h, ImmutableSet.of(bkk.kW, bkk.kV, bkk.qf, bkk.mG), ImmutableSet.of(bvs.bX), acl.pW);
	public static final bds g = a("fisherman", ayc.i, acl.pX);
	public static final bds h = a("fletcher", ayc.j, acl.pY);
	public static final bds i = a("leatherworker", ayc.k, acl.pZ);
	public static final bds j = a("librarian", ayc.l, acl.qa);
	public static final bds k = a("mason", ayc.m, acl.qb);
	public static final bds l = a("nitwit", ayc.n, null);
	public static final bds m = a("shepherd", ayc.o, acl.qc);
	public static final bds n = a("toolsmith", ayc.p, acl.qd);
	public static final bds o = a("weaponsmith", ayc.q, acl.qe);
	private final String p;
	private final ayc q;
	private final ImmutableSet<bke> r;
	private final ImmutableSet<bvr> s;
	@Nullable
	private final ack t;

	private bds(String string, ayc ayc, ImmutableSet<bke> immutableSet3, ImmutableSet<bvr> immutableSet4, @Nullable ack ack) {
		this.p = string;
		this.q = ayc;
		this.r = immutableSet3;
		this.s = immutableSet4;
		this.t = ack;
	}

	public ayc b() {
		return this.q;
	}

	public ImmutableSet<bke> c() {
		return this.r;
	}

	public ImmutableSet<bvr> d() {
		return this.s;
	}

	@Nullable
	public ack e() {
		return this.t;
	}

	public String toString() {
		return this.p;
	}

	static bds a(String string, ayc ayc, @Nullable ack ack) {
		return a(string, ayc, ImmutableSet.of(), ImmutableSet.of(), ack);
	}

	static bds a(String string, ayc ayc, ImmutableSet<bke> immutableSet3, ImmutableSet<bvr> immutableSet4, @Nullable ack ack) {
		return gl.a(gl.aS, new uh(string), new bds(string, ayc, immutableSet3, immutableSet4, ack));
	}
}
