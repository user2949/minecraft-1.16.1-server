import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import java.util.Set;

public class bii extends bjb {
	private static final Set<cxd> c = Sets.<cxd>newHashSet(cxd.x, cxd.y, cxd.e, cxd.g, cxd.A, cxd.O);
	private static final Set<bvr> d = Sets.<bvr>newHashSet(bvs.cg, bvs.lQ, bvs.eW, bvs.eX, bvs.eY, bvs.eZ, bvs.fb, bvs.fa, bvs.mQ, bvs.mR);
	protected static final Map<bvr, bvr> a = new Builder<bvr, bvr>()
		.put(bvs.V, bvs.ab)
		.put(bvs.J, bvs.U)
		.put(bvs.aa, bvs.ag)
		.put(bvs.O, bvs.T)
		.put(bvs.Z, bvs.af)
		.put(bvs.N, bvs.S)
		.put(bvs.X, bvs.ad)
		.put(bvs.L, bvs.Q)
		.put(bvs.Y, bvs.ae)
		.put(bvs.M, bvs.R)
		.put(bvs.W, bvs.ac)
		.put(bvs.K, bvs.P)
		.put(bvs.mh, bvs.mi)
		.put(bvs.mj, bvs.mk)
		.put(bvs.mq, bvs.mr)
		.put(bvs.ms, bvs.mt)
		.build();

	protected bii(blo blo, float float2, float float3, bke.a a) {
		super(float2, float3, blo, d, a);
	}

	@Override
	public float a(bki bki, cfj cfj) {
		cxd cxd4 = cfj.c();
		return c.contains(cxd4) ? this.b : super.a(bki, cfj);
	}

	@Override
	public ang a(blv blv) {
		bqb bqb3 = blv.o();
		fu fu4 = blv.a();
		cfj cfj5 = bqb3.d_(fu4);
		bvr bvr6 = (bvr)a.get(cfj5.b());
		if (bvr6 != null) {
			bec bec7 = blv.m();
			bqb3.a(bec7, fu4, acl.Z, acm.BLOCKS, 1.0F, 1.0F);
			if (!bqb3.v) {
				bqb3.a(fu4, bvr6.n().a(cao.a, cfj5.c(cao.a)), 11);
				if (bec7 != null) {
					blv.l().a(1, bec7, bec -> bec.d(blv.n()));
				}
			}

			return ang.a(bqb3.v);
		} else {
			return ang.PASS;
		}
	}
}
