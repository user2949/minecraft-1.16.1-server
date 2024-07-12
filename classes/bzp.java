import java.util.Random;

public class bzp extends bvx {
	public static final cgi a = cfz.ag;
	private static final dfg[] b = new dfg[]{
		bvr.a(0.0, 0.0, 0.0, 16.0, 5.0, 16.0), bvr.a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0), bvr.a(0.0, 0.0, 0.0, 16.0, 11.0, 16.0), bvr.a(0.0, 0.0, 0.0, 16.0, 14.0, 16.0)
	};

	protected bzp(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, Integer.valueOf(0)));
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return b[cfj.c(a)];
	}

	@Override
	protected boolean c(cfj cfj, bpg bpg, fu fu) {
		return cfj.a(bvs.cM);
	}

	@Override
	public boolean a_(cfj cfj) {
		return (Integer)cfj.c(a) < 3;
	}

	@Override
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		int integer6 = (Integer)cfj.c(a);
		if (integer6 < 3 && random.nextInt(10) == 0) {
			cfj = cfj.a(a, Integer.valueOf(integer6 + 1));
			zd.a(fu, cfj, 2);
		}
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(bzp.a);
	}
}
