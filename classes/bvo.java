import java.util.Random;

public class bvo extends bwv {
	public static final cgi a = cfz.ag;
	private static final dfg[] c = new dfg[]{
		bvr.a(0.0, 0.0, 0.0, 16.0, 2.0, 16.0), bvr.a(0.0, 0.0, 0.0, 16.0, 4.0, 16.0), bvr.a(0.0, 0.0, 0.0, 16.0, 6.0, 16.0), bvr.a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0)
	};

	public bvo(cfi.c c) {
		super(c);
	}

	@Override
	public cgi c() {
		return a;
	}

	@Override
	public int d() {
		return 3;
	}

	@Override
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		if (random.nextInt(3) != 0) {
			super.b(cfj, zd, fu, random);
		}
	}

	@Override
	protected int a(bqb bqb) {
		return super.a(bqb) / 3;
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(bvo.a);
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return c[cfj.c(this.c())];
	}
}
