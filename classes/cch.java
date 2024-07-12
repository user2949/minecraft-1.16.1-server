import java.util.Random;
import javax.annotation.Nullable;

public class cch extends bvr {
	private static final dfg c = bvr.a(3.0, 0.0, 3.0, 12.0, 7.0, 12.0);
	private static final dfg d = bvr.a(1.0, 0.0, 1.0, 15.0, 7.0, 15.0);
	public static final cgi a = cfz.ap;
	public static final cgi b = cfz.ao;

	public cch(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, Integer.valueOf(0)).a(b, Integer.valueOf(1)));
	}

	@Override
	public void a(bqb bqb, fu fu, aom aom) {
		this.a(bqb, fu, aom, 100);
		super.a(bqb, fu, aom);
	}

	@Override
	public void a(bqb bqb, fu fu, aom aom, float float4) {
		if (!(aom instanceof bcu)) {
			this.a(bqb, fu, aom, 3);
		}

		super.a(bqb, fu, aom, float4);
	}

	private void a(bqb bqb, fu fu, aom aom, int integer) {
		if (this.a(bqb, aom)) {
			if (!bqb.v && bqb.t.nextInt(integer) == 0) {
				cfj cfj6 = bqb.d_(fu);
				if (cfj6.a(bvs.kf)) {
					this.a(bqb, fu, cfj6);
				}
			}
		}
	}

	private void a(bqb bqb, fu fu, cfj cfj) {
		bqb.a(null, fu, acl.pp, acm.BLOCKS, 0.7F, 0.9F + bqb.t.nextFloat() * 0.2F);
		int integer5 = (Integer)cfj.c(b);
		if (integer5 <= 1) {
			bqb.b(fu, false);
		} else {
			bqb.a(fu, cfj.a(b, Integer.valueOf(integer5 - 1)), 2);
			bqb.c(2001, fu, bvr.i(cfj));
		}
	}

	@Override
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		if (this.a(zd) && a(zd, fu)) {
			int integer6 = (Integer)cfj.c(a);
			if (integer6 < 2) {
				zd.a(null, fu, acl.pq, acm.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
				zd.a(fu, cfj.a(a, Integer.valueOf(integer6 + 1)), 2);
			} else {
				zd.a(null, fu, acl.pr, acm.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
				zd.a(fu, false);

				for (int integer7 = 0; integer7 < cfj.c(b); integer7++) {
					zd.c(2001, fu, bvr.i(cfj));
					azi azi8 = aoq.aM.a(zd);
					azi8.c_(-24000);
					azi8.g(fu);
					azi8.b((double)fu.u() + 0.3 + (double)integer7 * 0.2, (double)fu.v(), (double)fu.w() + 0.3, 0.0F, 0.0F);
					zd.c(azi8);
				}
			}
		}
	}

	public static boolean a(bpg bpg, fu fu) {
		return b(bpg, fu.c());
	}

	public static boolean b(bpg bpg, fu fu) {
		return bpg.d_(fu).a(acx.B);
	}

	@Override
	public void b(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (a(bqb, fu) && !bqb.v) {
			bqb.c(2005, fu, 0);
		}
	}

	private boolean a(bqb bqb) {
		float float3 = bqb.f(1.0F);
		return (double)float3 < 0.69 && (double)float3 > 0.65 ? true : bqb.t.nextInt(500) == 0;
	}

	@Override
	public void a(bqb bqb, bec bec, fu fu, cfj cfj, @Nullable cdl cdl, bki bki) {
		super.a(bqb, bec, fu, cfj, cdl, bki);
		this.a(bqb, fu, cfj);
	}

	@Override
	public boolean a(cfj cfj, bin bin) {
		return bin.l().b() == this.h() && cfj.c(b) < 4 ? true : super.a(cfj, bin);
	}

	@Nullable
	@Override
	public cfj a(bin bin) {
		cfj cfj3 = bin.o().d_(bin.a());
		return cfj3.a(this) ? cfj3.a(b, Integer.valueOf(Math.min(4, (Integer)cfj3.c(b) + 1))) : super.a(bin);
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return cfj.c(b) > 1 ? d : c;
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(cch.a, b);
	}

	private boolean a(bqb bqb, aom aom) {
		if (aom instanceof azi || aom instanceof ayf) {
			return false;
		} else {
			return !(aom instanceof aoy) ? false : aom instanceof bec || bqb.S().b(bpx.b);
		}
	}
}
