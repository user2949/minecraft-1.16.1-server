import com.mojang.serialization.Codec;
import java.util.Random;

public abstract class ckt<FC extends cnr> {
	public static final ckt<coa> b = a("no_op", new clm(coa.a));
	public static final ckt<cou> c = a("tree", new cmp(cou.a));
	public static final cjj<cog> d = a("flower", new ckj(cog.a));
	public static final ckt<cog> e = a("random_patch", new clt(cog.a));
	public static final ckt<cnf> f = a("block_pile", new cjw(cnf.a));
	public static final ckt<cor> g = a("spring_feature", new cmj(cor.a));
	public static final ckt<coa> h = a("chorus_plant", new cka(coa.a));
	public static final ckt<coi> i = a("emerald_ore", new clx(coi.a));
	public static final ckt<coa> j = a("void_start_platform", new cmu(coa.a));
	public static final ckt<coa> k = a("desert_well", new ckn(coa.a));
	public static final ckt<coa> l = a("fossil", new ckv(coa.a));
	public static final ckt<cnt> m = a("huge_red_mushroom", new cla(cnt.a));
	public static final ckt<cnt> n = a("huge_brown_mushroom", new ckx(cnt.a));
	public static final ckt<coa> o = a("ice_spike", new clc(coa.a));
	public static final ckt<coa> p = a("glowstone_blob", new ckw(coa.a));
	public static final ckt<coa> q = a("freeze_top_layer", new cmg(coa.a));
	public static final ckt<coa> r = a("vines", new cmt(coa.a));
	public static final ckt<coa> s = a("monster_room", new clj(coa.a));
	public static final ckt<coa> t = a("blue_ice", new cjx(coa.a));
	public static final ckt<cng> u = a("iceberg", new cld(cng.a));
	public static final ckt<cne> v = a("forest_rock", new cjv(cne.a));
	public static final ckt<cnp> w = a("disk", new cko(cnp.a));
	public static final ckt<cns> x = a("ice_patch", new clb(cns.a));
	public static final ckt<cng> y = a("lake", new clh(cng.a));
	public static final ckt<coc> z = a("ore", new clp(coc.a));
	public static final ckt<coq> A = a("end_spike", new cmi(coq.a));
	public static final ckt<coa> B = a("end_island", new ckr(coa.a));
	public static final ckt<cnq> C = a("end_gateway", new ckq(cnq.a));
	public static final ckt<col> D = a("seagrass", new cmb(col.a));
	public static final ckt<coa> E = a("kelp", new clg(coa.a));
	public static final ckt<coa> F = a("coral_tree", new ckg(coa.a));
	public static final ckt<coa> G = a("coral_mushroom", new ckf(coa.a));
	public static final ckt<coa> H = a("coral_claw", new ckd(coa.a));
	public static final ckt<cnk> I = a("sea_pickle", new cma(cnk.a));
	public static final ckt<coo> J = a("simple_block", new cmd(coo.a));
	public static final ckt<cod> K = a("bamboo", new cjl(cod.b));
	public static final ckt<cky> L = a("huge_fungus", new ckz(cky.a));
	public static final ckt<cnf> M = a("nether_forest_vegetation", new clk(cnf.a));
	public static final ckt<coa> N = a("weeping_vines", new cmv(coa.a));
	public static final ckt<coa> O = a("twisting_vines", new cmq(coa.a));
	public static final ckt<cnj> P = a("basalt_columns", new cjm(cnj.a));
	public static final ckt<cno> Q = a("delta_feature", new ckk(cno.a));
	public static final ckt<coj> R = a("netherrack_replace_blobs", new clw(coj.a));
	public static final ckt<cnv> S = a("fill_layer", new cku(cnv.a));
	public static final cjy T = a("bonus_chest", new cjy(coa.a));
	public static final ckt<coa> U = a("basalt_pillar", new cjn(coa.a));
	public static final ckt<coc> V = a("no_surface_ore", new cln(coc.a));
	public static final ckt<coh> W = a("random_random_selector", new clu(coh.a));
	public static final ckt<cof> X = a("random_selector", new clv(cof.a));
	public static final ckt<cop> Y = a("simple_random_selector", new cme(cop.a));
	public static final ckt<coe> Z = a("random_boolean_selector", new cls(coe.a));
	public static final ckt<cnm> aa = a("decorated", new ckh(cnm.a));
	public static final ckt<cnm> ab = a("decorated_flower", new cki(cnm.a));
	private final Codec<ckb<FC, ckt<FC>>> a;

	private static <C extends cnr, F extends ckt<C>> F a(String string, F ckt) {
		return gl.a(gl.aq, string, ckt);
	}

	public ckt(Codec<FC> codec) {
		this.a = codec.fieldOf("config").<ckb<FC, ckt<FC>>>xmap(cnr -> new ckb<>(this, cnr), ckb -> ckb.e).codec();
	}

	public Codec<ckb<FC, ckt<FC>>> a() {
		return this.a;
	}

	public ckb<FC, ?> b(FC cnr) {
		return new ckb<>(this, cnr);
	}

	protected void a(bqh bqh, fu fu, cfj cfj) {
		bqh.a(fu, cfj, 3);
	}

	public abstract boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, FC cnr);

	protected static boolean a(bvr bvr) {
		return bvr == bvs.b || bvr == bvs.c || bvr == bvs.e || bvr == bvs.g;
	}

	public static boolean b(bvr bvr) {
		return bvr == bvs.j || bvr == bvs.i || bvr == bvs.l || bvr == bvs.k || bvr == bvs.dT;
	}

	public static boolean a(bqg bqg, fu fu) {
		return bqg.a(fu, cfj -> b(cfj.b()));
	}

	public static boolean b(bqg bqg, fu fu) {
		return bqg.a(fu, cfi.a::g);
	}
}
