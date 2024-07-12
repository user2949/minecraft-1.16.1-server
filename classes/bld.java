import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;

public class bld extends bjb {
	private static final Set<bvr> c = Sets.<bvr>newHashSet(
		bvs.cG,
		bvs.j,
		bvs.k,
		bvs.l,
		bvs.bX,
		bvs.i,
		bvs.E,
		bvs.dT,
		bvs.C,
		bvs.D,
		bvs.cE,
		bvs.cC,
		bvs.cM,
		bvs.iE,
		bvs.jM,
		bvs.jN,
		bvs.jO,
		bvs.jP,
		bvs.jQ,
		bvs.jR,
		bvs.jS,
		bvs.jT,
		bvs.jU,
		bvs.jV,
		bvs.jW,
		bvs.jX,
		bvs.jY,
		bvs.jZ,
		bvs.ka,
		bvs.kb,
		bvs.cN
	);
	protected static final Map<bvr, cfj> a = Maps.<bvr, cfj>newHashMap(ImmutableMap.of(bvs.i, bvs.iE.n()));

	public bld(blo blo, float float2, float float3, bke.a a) {
		super(float2, float3, blo, c, a);
	}

	@Override
	public boolean b(cfj cfj) {
		return cfj.a(bvs.cC) || cfj.a(bvs.cE);
	}

	@Override
	public ang a(blv blv) {
		bqb bqb3 = blv.o();
		fu fu4 = blv.a();
		cfj cfj5 = bqb3.d_(fu4);
		if (blv.i() == fz.DOWN) {
			return ang.PASS;
		} else {
			bec bec6 = blv.m();
			cfj cfj7 = (cfj)a.get(cfj5.b());
			cfj cfj8 = null;
			if (cfj7 != null && bqb3.d_(fu4.b()).g()) {
				bqb3.a(bec6, fu4, acl.mU, acm.BLOCKS, 1.0F, 1.0F);
				cfj8 = cfj7;
			} else if (cfj5.b() instanceof bwb && (Boolean)cfj5.c(bwb.b)) {
				if (!bqb3.s_()) {
					bqb3.a(null, 1009, fu4, 0);
				}

				bwb.c(bqb3, fu4, cfj5);
				cfj8 = cfj5.a(bwb.b, Boolean.valueOf(false));
			}

			if (cfj8 != null) {
				if (!bqb3.v) {
					bqb3.a(fu4, cfj8, 11);
					if (bec6 != null) {
						blv.l().a(1, bec6, bec -> bec.d(blv.n()));
					}
				}

				return ang.a(bqb3.v);
			} else {
				return ang.PASS;
			}
		}
	}
}
