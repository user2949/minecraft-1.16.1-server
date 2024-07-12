import java.util.Random;

public class car extends bvx implements bvt {
	public static final cgi a = cfz.aA;
	protected static final dfg b = bvr.a(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);
	private final ces c;

	protected car(ces ces, cfi.c c) {
		super(c);
		this.c = ces;
		this.j(this.n.b().a(a, Integer.valueOf(0)));
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return b;
	}

	@Override
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		if (zd.B(fu.b()) >= 9 && random.nextInt(7) == 0) {
			this.a(zd, fu, cfj, random);
		}
	}

	public void a(zd zd, fu fu, cfj cfj, Random random) {
		if ((Integer)cfj.c(a) == 0) {
			zd.a(fu, cfj.a(a), 4);
		} else {
			this.c.a(zd, zd.i().g(), fu, cfj, random);
		}
	}

	@Override
	public boolean a(bpg bpg, fu fu, cfj cfj, boolean boolean4) {
		return true;
	}

	@Override
	public boolean a(bqb bqb, Random random, fu fu, cfj cfj) {
		return (double)bqb.t.nextFloat() < 0.45;
	}

	@Override
	public void a(zd zd, Random random, fu fu, cfj cfj) {
		this.a(zd, fu, cfj, random);
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(car.a);
	}
}
