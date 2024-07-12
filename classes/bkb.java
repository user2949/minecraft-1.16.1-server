import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;

public class bkb extends bjb {
	private static final Set<bvr> c = ImmutableSet.of(
		bvs.iK, bvs.mn, bvs.gA, bvs.ke, bvs.nb, bvs.mw, bvs.an, bvs.ao, bvs.ak, bvs.ah, bvs.ai, bvs.am, bvs.al, bvs.aj
	);
	protected static final Map<bvr, cfj> a = Maps.<bvr, cfj>newHashMap(ImmutableMap.of(bvs.i, bvs.bX.n(), bvs.iE, bvs.bX.n(), bvs.j, bvs.bX.n(), bvs.k, bvs.j.n()));

	protected bkb(blo blo, int integer, float float3, bke.a a) {
		super((float)integer, float3, blo, c, a);
	}

	@Override
	public ang a(blv blv) {
		bqb bqb3 = blv.o();
		fu fu4 = blv.a();
		if (blv.i() != fz.DOWN && bqb3.d_(fu4.b()).g()) {
			cfj cfj5 = (cfj)a.get(bqb3.d_(fu4).b());
			if (cfj5 != null) {
				bec bec6 = blv.m();
				bqb3.a(bec6, fu4, acl.fA, acm.BLOCKS, 1.0F, 1.0F);
				if (!bqb3.v) {
					bqb3.a(fu4, cfj5, 11);
					if (bec6 != null) {
						blv.l().a(1, bec6, bec -> bec.d(blv.n()));
					}
				}

				return ang.a(bqb3.v);
			}
		}

		return ang.PASS;
	}
}
