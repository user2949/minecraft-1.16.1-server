import java.util.Random;

public class cby extends bvx implements bvt {
	protected static final dfg a = bvr.a(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

	protected cby(cfi.c c) {
		super(c);
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return a;
	}

	@Override
	public boolean a(bpg bpg, fu fu, cfj cfj, boolean boolean4) {
		return true;
	}

	@Override
	public boolean a(bqb bqb, Random random, fu fu, cfj cfj) {
		return true;
	}

	@Override
	public void a(zd zd, Random random, fu fu, cfj cfj) {
		bxg bxg6 = (bxg)(this == bvs.aS ? bvs.gZ : bvs.gY);
		if (bxg6.n().a(zd, fu) && zd.w(fu.b())) {
			bxg6.a(zd, fu, 2);
		}
	}

	@Override
	public cfi.b aj_() {
		return cfi.b.XYZ;
	}
}
