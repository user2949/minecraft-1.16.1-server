import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.types.Type;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cdm<T extends cdl> {
	private static final Logger H = LogManager.getLogger();
	public static final cdm<cdy> a = a("furnace", cdm.a.a(cdy::new, bvs.bY));
	public static final cdm<cdp> b = a("chest", cdm.a.a(cdp::new, bvs.bR));
	public static final cdm<cep> c = a("trapped_chest", cdm.a.a(cep::new, bvs.fr));
	public static final cdm<cdx> d = a("ender_chest", cdm.a.a(cdx::new, bvs.ek));
	public static final cdm<cec> e = a("jukebox", cdm.a.a(cec::new, bvs.cI));
	public static final cdm<cdu> f = a("dispenser", cdm.a.a(cdu::new, bvs.as));
	public static final cdm<cdv> g = a("dropper", cdm.a.a(cdv::new, bvs.fE));
	public static final cdm<ceh> h = a(
		"sign", cdm.a.a(ceh::new, bvs.bZ, bvs.ca, bvs.cb, bvs.cc, bvs.cd, bvs.ce, bvs.cj, bvs.ck, bvs.cl, bvs.cm, bvs.cn, bvs.co, bvs.mU, bvs.mW, bvs.mV, bvs.mX)
	);
	public static final cdm<cek> i = a("mob_spawner", cdm.a.a(cek::new, bvs.bP));
	public static final cdm<cff> j = a("piston", cdm.a.a(cff::new, bvs.bo));
	public static final cdm<cdn> k = a("brewing_stand", cdm.a.a(cdn::new, bvs.ea));
	public static final cdm<cdw> l = a("enchanting_table", cdm.a.a(cdw::new, bvs.dZ));
	public static final cdm<cen> m = a("end_portal", cdm.a.a(cen::new, bvs.ec));
	public static final cdm<cdg> n = a("beacon", cdm.a.a(cdg::new, bvs.es));
	public static final cdm<cei> o = a("skull", cdm.a.a(cei::new, bvs.fc, bvs.fd, bvs.fk, bvs.fl, bvs.fm, bvs.fn, bvs.fg, bvs.fh, bvs.fe, bvs.ff, bvs.fi, bvs.fj));
	public static final cdm<cdt> p = a("daylight_detector", cdm.a.a(cdt::new, bvs.fv));
	public static final cdm<cea> q = a("hopper", cdm.a.a(cea::new, bvs.fy));
	public static final cdm<cdr> r = a("comparator", cdm.a.a(cdr::new, bvs.fu));
	public static final cdm<cdc> s = a(
		"banner",
		cdm.a.a(
			cdc::new,
			bvs.ha,
			bvs.hb,
			bvs.hc,
			bvs.hd,
			bvs.he,
			bvs.hf,
			bvs.hg,
			bvs.hh,
			bvs.hi,
			bvs.hj,
			bvs.hk,
			bvs.hl,
			bvs.hm,
			bvs.hn,
			bvs.ho,
			bvs.hp,
			bvs.hq,
			bvs.hr,
			bvs.hs,
			bvs.ht,
			bvs.hu,
			bvs.hv,
			bvs.hw,
			bvs.hx,
			bvs.hy,
			bvs.hz,
			bvs.hA,
			bvs.hB,
			bvs.hC,
			bvs.hD,
			bvs.hE,
			bvs.hF
		)
	);
	public static final cdm<cel> t = a("structure_block", cdm.a.a(cel::new, bvs.mY));
	public static final cdm<cem> u = a("end_gateway", cdm.a.a(cem::new, bvs.iF));
	public static final cdm<cdq> v = a("command_block", cdm.a.a(cdq::new, bvs.er, bvs.iH, bvs.iG));
	public static final cdm<ceg> w = a(
		"shulker_box",
		cdm.a.a(ceg::new, bvs.iP, bvs.jf, bvs.jb, bvs.jc, bvs.iZ, bvs.iX, bvs.jd, bvs.iT, bvs.iY, bvs.iV, bvs.iS, bvs.iR, bvs.iW, bvs.ja, bvs.je, bvs.iQ, bvs.iU)
	);
	public static final cdm<cdh> x = a(
		"bed", cdm.a.a(cdh::new, bvs.aL, bvs.aM, bvs.aI, bvs.aJ, bvs.aG, bvs.aE, bvs.aK, bvs.aA, bvs.aF, bvs.aC, bvs.az, bvs.ay, bvs.aD, bvs.aH, bvs.ax, bvs.aB)
	);
	public static final cdm<cds> y = a("conduit", cdm.a.a(cds::new, bvs.kW));
	public static final cdm<cde> z = a("barrel", cdm.a.a(cde::new, bvs.lS));
	public static final cdm<cej> A = a("smoker", cdm.a.a(cej::new, bvs.lT));
	public static final cdm<cdk> B = a("blast_furnace", cdm.a.a(cdk::new, bvs.lU));
	public static final cdm<ced> C = a("lectern", cdm.a.a(ced::new, bvs.lY));
	public static final cdm<cdj> D = a("bell", cdm.a.a(cdj::new, bvs.mb));
	public static final cdm<ceb> E = a("jigsaw", cdm.a.a(ceb::new, bvs.mZ));
	public static final cdm<cdo> F = a("campfire", cdm.a.a(cdo::new, bvs.me, bvs.mf));
	public static final cdm<cdi> G = a("beehive", cdm.a.a(cdi::new, bvs.nc, bvs.nd));
	private final Supplier<? extends T> I;
	private final Set<bvr> J;
	private final Type<?> K;

	@Nullable
	public static uh a(cdm<?> cdm) {
		return gl.aC.b(cdm);
	}

	private static <T extends cdl> cdm<T> a(String string, cdm.a<T> a) {
		if (a.b.isEmpty()) {
			H.warn("Block entity type {} requires at least one valid block to be defined!", string);
		}

		Type<?> type3 = v.a(ajb.k, string);
		return gl.a(gl.aC, string, a.a(type3));
	}

	public cdm(Supplier<? extends T> supplier, Set<bvr> set, Type<?> type) {
		this.I = supplier;
		this.J = set;
		this.K = type;
	}

	@Nullable
	public T a() {
		return (T)this.I.get();
	}

	public boolean a(bvr bvr) {
		return this.J.contains(bvr);
	}

	@Nullable
	public T a(bpg bpg, fu fu) {
		cdl cdl4 = bpg.c(fu);
		return (T)(cdl4 != null && cdl4.u() == this ? cdl4 : null);
	}

	public static final class a<T extends cdl> {
		private final Supplier<? extends T> a;
		private final Set<bvr> b;

		private a(Supplier<? extends T> supplier, Set<bvr> set) {
			this.a = supplier;
			this.b = set;
		}

		public static <T extends cdl> cdm.a<T> a(Supplier<? extends T> supplier, bvr... arr) {
			return new cdm.a<>(supplier, ImmutableSet.copyOf(arr));
		}

		public cdm<T> a(Type<?> type) {
			return new cdm<>(this.a, this.b, type);
		}
	}
}
