import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class apy {
	private static final Logger a = LogManager.getLogger();
	private static final Map<aoq<? extends aoy>, apw> b = ImmutableMap.<aoq<? extends aoy>, apw>builder()
		.put(aoq.b, aoy.cK().a())
		.put(aoq.d, ayf.m().a())
		.put(aoq.e, ayl.fa().a())
		.put(aoq.f, bbl.m().a())
		.put(aoq.h, aym.fb().a())
		.put(aoq.i, bbm.m().a())
		.put(aoq.j, ayn.eL().a())
		.put(aoq.k, ayh.m().a())
		.put(aoq.l, ayp.eL().a())
		.put(aoq.m, bbn.m().a())
		.put(aoq.n, ayq.eN().a())
		.put(aoq.o, azl.eM().a())
		.put(aoq.q, bcu.eT().a())
		.put(aoq.r, bbq.m().a())
		.put(aoq.u, bbr.m().a())
		.put(aoq.v, bbs.m().a())
		.put(aoq.t, bac.m().a())
		.put(aoq.w, bbu.eL().a())
		.put(aoq.C, ays.eL().a())
		.put(aoq.D, bbv.eK().a())
		.put(aoq.E, bbw.m().a())
		.put(aoq.F, bbx.eN().a())
		.put(aoq.G, bcx.eL().a())
		.put(aoq.H, azm.fj().a())
		.put(aoq.I, bcu.eT().a())
		.put(aoq.J, bbz.eL().a())
		.put(aoq.K, ayt.m().a())
		.put(aoq.Q, azp.fx().a())
		.put(aoq.S, bca.m().a())
		.put(aoq.ab, ayp.eL().a())
		.put(aoq.aa, azl.eM().a())
		.put(aoq.ac, ayv.eL().a())
		.put(aoq.ae, ayw.eZ().a())
		.put(aoq.af, ayx.eV().a())
		.put(aoq.ag, bcb.eS().a())
		.put(aoq.ah, ayy.eL().a())
		.put(aoq.ai, bdc.eL().a())
		.put(aoq.aj, bce.eL().a())
		.put(aoq.bb, bec.eo().a())
		.put(aoq.ak, ayz.eL().a())
		.put(aoq.am, ayh.m().a())
		.put(aoq.an, azb.eM().a())
		.put(aoq.ao, bcg.m().a())
		.put(aoq.ap, ayh.m().a())
		.put(aoq.aq, azd.eL().a())
		.put(aoq.ar, bch.m().a())
		.put(aoq.at, bci.m().a())
		.put(aoq.au, bbk.m().a())
		.put(aoq.av, azs.eM().a())
		.put(aoq.aw, bcb.eS().a())
		.put(aoq.ay, azf.m().a())
		.put(aoq.aB, bcm.eL().a())
		.put(aoq.aC, azg.m().a())
		.put(aoq.aD, bbk.m().a())
		.put(aoq.aE, bco.eN().a())
		.put(aoq.aK, azp.fx().a())
		.put(aoq.aL, ayh.m().a())
		.put(aoq.aM, azi.eN().a())
		.put(aoq.aN, bcp.m().a())
		.put(aoq.aO, bdp.eX().a())
		.put(aoq.aP, bcq.eL().a())
		.put(aoq.aQ, aoz.p().a())
		.put(aoq.aR, bcr.eL().a())
		.put(aoq.aS, baw.eL().a())
		.put(aoq.aT, bbk.m().a())
		.put(aoq.aV, azk.eV().a())
		.put(aoq.aW, bct.m().a())
		.put(aoq.aX, bcu.eT().a())
		.put(aoq.aY, azw.eM().a())
		.put(aoq.aZ, bcu.eT().a())
		.put(aoq.ba, bcw.eX().a())
		.build();

	public static apw a(aoq<? extends aoy> aoq) {
		return (apw)b.get(aoq);
	}

	public static boolean b(aoq<?> aoq) {
		return b.containsKey(aoq);
	}

	public static void a() {
		gl.al.e().filter(aoq -> aoq.e() != apa.MISC).filter(aoq -> !b(aoq)).map(gl.al::b).forEach(uh -> {
			if (u.d) {
				throw new IllegalStateException("Entity " + uh + " has no attributes");
			} else {
				a.error("Entity {} has no attributes", uh);
			}
		});
	}
}
