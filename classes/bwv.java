import java.util.Random;

public class bwv extends bvx implements bvt {
	public static final cgi b = cfz.ai;
	private static final dfg[] a = new dfg[]{
		bvr.a(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
		bvr.a(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
		bvr.a(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
		bvr.a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
		bvr.a(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
		bvr.a(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
		bvr.a(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
		bvr.a(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
	};

	protected bwv(cfi.c c) {
		super(c);
		this.j(this.n.b().a(this.c(), Integer.valueOf(0)));
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return a[cfj.c(this.c())];
	}

	@Override
	protected boolean c(cfj cfj, bpg bpg, fu fu) {
		return cfj.a(bvs.bX);
	}

	public cgi c() {
		return b;
	}

	public int d() {
		return 7;
	}

	protected int g(cfj cfj) {
		return (Integer)cfj.c(this.c());
	}

	public cfj b(int integer) {
		return this.n().a(this.c(), Integer.valueOf(integer));
	}

	public boolean h(cfj cfj) {
		return (Integer)cfj.c(this.c()) >= this.d();
	}

	@Override
	public boolean a_(cfj cfj) {
		return !this.h(cfj);
	}

	@Override
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		if (zd.b(fu, 0) >= 9) {
			int integer6 = this.g(cfj);
			if (integer6 < this.d()) {
				float float7 = a(this, zd, fu);
				if (random.nextInt((int)(25.0F / float7) + 1) == 0) {
					zd.a(fu, this.b(integer6 + 1), 2);
				}
			}
		}
	}

	public void a(bqb bqb, fu fu, cfj cfj) {
		int integer5 = this.g(cfj) + this.a(bqb);
		int integer6 = this.d();
		if (integer5 > integer6) {
			integer5 = integer6;
		}

		bqb.a(fu, this.b(integer5), 2);
	}

	protected int a(bqb bqb) {
		return aec.a(bqb.t, 2, 5);
	}

	protected static float a(bvr bvr, bpg bpg, fu fu) {
		float float4 = 1.0F;
		fu fu5 = fu.c();

		for (int integer6 = -1; integer6 <= 1; integer6++) {
			for (int integer7 = -1; integer7 <= 1; integer7++) {
				float float8 = 0.0F;
				cfj cfj9 = bpg.d_(fu5.b(integer6, 0, integer7));
				if (cfj9.a(bvs.bX)) {
					float8 = 1.0F;
					if ((Integer)cfj9.c(bxs.a) > 0) {
						float8 = 3.0F;
					}
				}

				if (integer6 != 0 || integer7 != 0) {
					float8 /= 4.0F;
				}

				float4 += float8;
			}
		}

		fu fu6 = fu.d();
		fu fu7 = fu.e();
		fu fu8 = fu.f();
		fu fu9 = fu.g();
		boolean boolean10 = bvr == bpg.d_(fu8).b() || bvr == bpg.d_(fu9).b();
		boolean boolean11 = bvr == bpg.d_(fu6).b() || bvr == bpg.d_(fu7).b();
		if (boolean10 && boolean11) {
			float4 /= 2.0F;
		} else {
			boolean boolean12 = bvr == bpg.d_(fu8.d()).b() || bvr == bpg.d_(fu9.d()).b() || bvr == bpg.d_(fu9.e()).b() || bvr == bpg.d_(fu8.e()).b();
			if (boolean12) {
				float4 /= 2.0F;
			}
		}

		return float4;
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		return (bqd.b(fu, 0) >= 8 || bqd.f(fu)) && super.a(cfj, bqd, fu);
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu, aom aom) {
		if (aom instanceof bcg && bqb.S().b(bpx.b)) {
			bqb.a(fu, true, aom);
		}

		super.a(cfj, bqb, fu, aom);
	}

	@Override
	public boolean a(bpg bpg, fu fu, cfj cfj, boolean boolean4) {
		return !this.h(cfj);
	}

	@Override
	public boolean a(bqb bqb, Random random, fu fu, cfj cfj) {
		return true;
	}

	@Override
	public void a(zd zd, Random random, fu fu, cfj cfj) {
		this.a(zd, fu, cfj);
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(b);
	}
}
