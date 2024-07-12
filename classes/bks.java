import com.google.common.collect.ImmutableSet;
import java.util.Set;

public class bks extends bjb {
	private static final Set<bvr> a = ImmutableSet.of(
		bvs.fD,
		bvs.H,
		bvs.m,
		bvs.aO,
		bvs.bU,
		bvs.bT,
		bvs.aN,
		bvs.bE,
		bvs.F,
		bvs.I,
		bvs.cD,
		bvs.bF,
		bvs.G,
		bvs.ar,
		bvs.aq,
		bvs.bJ,
		bvs.cL,
		bvs.gT,
		bvs.kV,
		bvs.ch,
		bvs.cy,
		bvs.at,
		bvs.au,
		bvs.av,
		bvs.hH,
		bvs.hI,
		bvs.hG,
		bvs.b,
		bvs.c,
		bvs.d,
		bvs.e,
		bvs.f,
		bvs.g,
		bvs.h,
		bvs.hQ,
		bvs.hR,
		bvs.hS,
		bvs.hU,
		bvs.hV,
		bvs.hW,
		bvs.hX,
		bvs.hY,
		bvs.hZ,
		bvs.ia,
		bvs.ic,
		bvs.if,
		bvs.ig,
		bvs.ie,
		bvs.id,
		bvs.cB,
		bvs.cq,
		bvs.lr,
		bvs.ls,
		bvs.lt,
		bvs.lu,
		bvs.lv,
		bvs.lw,
		bvs.lx,
		bvs.ly,
		bvs.lz,
		bvs.lA,
		bvs.lB,
		bvs.lC,
		bvs.lD,
		bvs.iP,
		bvs.jf,
		bvs.jb,
		bvs.jc,
		bvs.iZ,
		bvs.iX,
		bvs.jd,
		bvs.iT,
		bvs.iY,
		bvs.iV,
		bvs.iS,
		bvs.iR,
		bvs.iW,
		bvs.ja,
		bvs.je,
		bvs.iQ,
		bvs.iU,
		bvs.aW,
		bvs.aP,
		bvs.aX
	);

	protected bks(blo blo, int integer, float float3, bke.a a) {
		super((float)integer, float3, blo, bks.a, a);
	}

	@Override
	public boolean b(cfj cfj) {
		int integer3 = this.g().d();
		if (cfj.a(bvs.bK) || cfj.a(bvs.ni) || cfj.a(bvs.ng) || cfj.a(bvs.nj) || cfj.a(bvs.nh)) {
			return integer3 >= 3;
		} else if (cfj.a(bvs.bU) || cfj.a(bvs.bT) || cfj.a(bvs.ej) || cfj.a(bvs.en) || cfj.a(bvs.bE) || cfj.a(bvs.F) || cfj.a(bvs.cy)) {
			return integer3 >= 2;
		} else if (!cfj.a(bvs.bF) && !cfj.a(bvs.G) && !cfj.a(bvs.ar) && !cfj.a(bvs.aq)) {
			cxd cxd4 = cfj.c();
			return cxd4 == cxd.H || cxd4 == cxd.I || cxd4 == cxd.K || cfj.a(bvs.I);
		} else {
			return integer3 >= 1;
		}
	}

	@Override
	public float a(bki bki, cfj cfj) {
		cxd cxd4 = cfj.c();
		return cxd4 != cxd.I && cxd4 != cxd.K && cxd4 != cxd.H ? super.a(bki, cfj) : this.b;
	}
}
