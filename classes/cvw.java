import com.mojang.serialization.Codec;
import java.util.Random;

public abstract class cvw<C extends cvy> {
	public static final cfj f = bvs.a.n();
	public static final cfj g = bvs.j.n();
	public static final cfj h = bvs.i.n();
	public static final cfj i = bvs.l.n();
	public static final cfj j = bvs.E.n();
	public static final cfj k = bvs.b.n();
	public static final cfj l = bvs.k.n();
	public static final cfj m = bvs.C.n();
	public static final cfj n = bvs.D.n();
	public static final cfj o = bvs.fF.n();
	public static final cfj p = bvs.dT.n();
	public static final cfj q = bvs.cM.n();
	public static final cfj r = bvs.cL.n();
	public static final cfj s = bvs.ee.n();
	public static final cfj t = bvs.mu.n();
	public static final cfj u = bvs.ml.n();
	public static final cfj v = bvs.iK.n();
	public static final cfj w = bvs.mn.n();
	public static final cfj x = bvs.np.n();
	public static final cfj y = bvs.cO.n();
	public static final cfj z = bvs.iJ.n();
	public static final cvx A = new cvx(f, f, f);
	public static final cvx B = new cvx(i, g, j);
	public static final cvx C = new cvx(j, j, j);
	public static final cvx D = new cvx(h, g, j);
	public static final cvx E = new cvx(g, g, j);
	public static final cvx F = new cvx(k, k, j);
	public static final cvx G = new cvx(l, g, j);
	public static final cvx H = new cvx(m, m, j);
	public static final cvx I = new cvx(h, g, m);
	public static final cvx J = new cvx(m, m, m);
	public static final cvx K = new cvx(n, o, j);
	public static final cvx L = new cvx(p, g, j);
	public static final cvx M = new cvx(r, r, r);
	public static final cvx N = new cvx(q, q, q);
	public static final cvx O = new cvx(s, s, s);
	public static final cvx P = new cvx(t, r, v);
	public static final cvx Q = new cvx(u, r, w);
	public static final cvx R = new cvx(x, y, z);
	public static final cvw<cvx> S = a("default", new cvk(cvx.a));
	public static final cvw<cvx> T = a("mountain", new cvp(cvx.a));
	public static final cvw<cvx> U = a("shattered_savanna", new cvu(cvx.a));
	public static final cvw<cvx> V = a("gravelly_mountain", new cvo(cvx.a));
	public static final cvw<cvx> W = a("giant_tree_taiga", new cvn(cvx.a));
	public static final cvw<cvx> X = a("swamp", new cvz(cvx.a));
	public static final cvw<cvx> Y = a("badlands", new cvh(cvx.a));
	public static final cvw<cvx> Z = a("wooded_badlands", new cwa(cvx.a));
	public static final cvw<cvx> aa = a("eroded_badlands", new cvl(cvx.a));
	public static final cvw<cvx> ab = a("frozen_ocean", new cvm(cvx.a));
	public static final cvw<cvx> ac = a("nether", new cvs(cvx.a));
	public static final cvw<cvx> ad = a("nether_forest", new cvr(cvx.a));
	public static final cvw<cvx> ae = a("soul_sand_valley", new cvv(cvx.a));
	public static final cvw<cvx> af = a("basalt_deltas", new cvi(cvx.a));
	public static final cvw<cvx> ag = a("nope", new cvt(cvx.a));
	private final Codec<cvj<C>> a;

	private static <C extends cvy, F extends cvw<C>> F a(String string, F cvw) {
		return gl.a(gl.ap, string, cvw);
	}

	public cvw(Codec<C> codec) {
		this.a = codec.fieldOf("config").<cvj<C>>xmap(cvy -> new cvj<>(this, (C)cvy), cvj -> cvj.c).codec();
	}

	public Codec<cvj<C>> d() {
		return this.a;
	}

	public abstract void a(
		Random random, cgy cgy, bre bre, int integer4, int integer5, int integer6, double double7, cfj cfj8, cfj cfj9, int integer10, long long11, C cvy
	);

	public void a(long long1) {
	}
}
