import java.util.Random;

public class cbp extends bvx implements bvt {
	public static final cgi a = cfz.ai;
	protected static final dfg[] b = new dfg[]{
		bvr.a(7.0, 0.0, 7.0, 9.0, 2.0, 9.0),
		bvr.a(7.0, 0.0, 7.0, 9.0, 4.0, 9.0),
		bvr.a(7.0, 0.0, 7.0, 9.0, 6.0, 9.0),
		bvr.a(7.0, 0.0, 7.0, 9.0, 8.0, 9.0),
		bvr.a(7.0, 0.0, 7.0, 9.0, 10.0, 9.0),
		bvr.a(7.0, 0.0, 7.0, 9.0, 12.0, 9.0),
		bvr.a(7.0, 0.0, 7.0, 9.0, 14.0, 9.0),
		bvr.a(7.0, 0.0, 7.0, 9.0, 16.0, 9.0)
	};
	private final cbq c;

	protected cbp(cbq cbq, cfi.c c) {
		super(c);
		this.c = cbq;
		this.j(this.n.b().a(a, Integer.valueOf(0)));
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return b[cfj.c(a)];
	}

	@Override
	protected boolean c(cfj cfj, bpg bpg, fu fu) {
		return cfj.a(bvs.bX);
	}

	@Override
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		if (zd.b(fu, 0) >= 9) {
			float float6 = bwv.a(this, zd, fu);
			if (random.nextInt((int)(25.0F / float6) + 1) == 0) {
				int integer7 = (Integer)cfj.c(a);
				if (integer7 < 7) {
					cfj = cfj.a(a, Integer.valueOf(integer7 + 1));
					zd.a(fu, cfj, 2);
				} else {
					fz fz8 = fz.c.HORIZONTAL.a(random);
					fu fu9 = fu.a(fz8);
					cfj cfj10 = zd.d_(fu9.c());
					if (zd.d_(fu9).g() && (cfj10.a(bvs.bX) || cfj10.a(bvs.j) || cfj10.a(bvs.k) || cfj10.a(bvs.l) || cfj10.a(bvs.i))) {
						zd.a(fu9, this.c.n());
						zd.a(fu, this.c.d().n().a(byp.aq, fz8));
					}
				}
			}
		}
	}

	@Override
	public boolean a(bpg bpg, fu fu, cfj cfj, boolean boolean4) {
		return (Integer)cfj.c(a) != 7;
	}

	@Override
	public boolean a(bqb bqb, Random random, fu fu, cfj cfj) {
		return true;
	}

	@Override
	public void a(zd zd, Random random, fu fu, cfj cfj) {
		int integer6 = Math.min(7, (Integer)cfj.c(a) + aec.a(zd.t, 2, 5));
		cfj cfj7 = cfj.a(a, Integer.valueOf(integer6));
		zd.a(fu, cfj7, 2);
		if (integer6 == 7) {
			cfj7.b(zd, fu, zd.t);
		}
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(cbp.a);
	}

	public cbq d() {
		return this.c;
	}
}
