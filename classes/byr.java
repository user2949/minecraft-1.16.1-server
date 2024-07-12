import java.util.Random;
import javax.annotation.Nullable;

public class byr extends byl {
	public byr(cfi.c c) {
		super(c);
	}

	@Override
	public void a(bqb bqb, bec bec, fu fu, cfj cfj, @Nullable cdl cdl, bki bki) {
		super.a(bqb, bec, fu, cfj, cdl, bki);
		if (bny.a(boa.u, bki) == 0) {
			if (bqb.m().f()) {
				bqb.a(fu, false);
				return;
			}

			cxd cxd8 = bqb.d_(fu.c()).c();
			if (cxd8.c() || cxd8.a()) {
				bqb.a(fu, bvs.A.n());
			}
		}
	}

	@Override
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		if (zd.a(bqi.BLOCK, fu) > 11 - cfj.b(zd, fu)) {
			this.d(cfj, zd, fu);
		}
	}

	protected void d(cfj cfj, bqb bqb, fu fu) {
		if (bqb.m().f()) {
			bqb.a(fu, false);
		} else {
			bqb.a(fu, bvs.A.n());
			bqb.a(fu, bvs.A, fu);
		}
	}

	@Override
	public cxf f(cfj cfj) {
		return cxf.NORMAL;
	}
}
