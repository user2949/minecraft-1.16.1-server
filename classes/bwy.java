public class bwy extends bvg {
	public static final cgi a = cfz.az;
	public static final cga b = cfz.p;
	protected static final dfg c = bvr.a(0.0, 0.0, 0.0, 16.0, 6.0, 16.0);

	public bwy(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, Integer.valueOf(0)).a(b, Boolean.valueOf(false)));
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return c;
	}

	@Override
	public boolean c_(cfj cfj) {
		return true;
	}

	@Override
	public int a(cfj cfj, bpg bpg, fu fu, fz fz) {
		return (Integer)cfj.c(a);
	}

	public static void d(cfj cfj, bqb bqb, fu fu) {
		if (bqb.m().d()) {
			int integer4 = bqb.a(bqi.SKY, fu) - bqb.c();
			float float5 = bqb.a(1.0F);
			boolean boolean6 = (Boolean)cfj.c(b);
			if (boolean6) {
				integer4 = 15 - integer4;
			} else if (integer4 > 0) {
				float float7 = float5 < (float) Math.PI ? 0.0F : (float) (Math.PI * 2);
				float5 += (float7 - float5) * 0.2F;
				integer4 = Math.round((float)integer4 * aec.b(float5));
			}

			integer4 = aec.a(integer4, 0, 15);
			if ((Integer)cfj.c(a) != integer4) {
				bqb.a(fu, cfj.a(a, Integer.valueOf(integer4)), 3);
			}
		}
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if (bec.eJ()) {
			if (bqb.v) {
				return ang.SUCCESS;
			} else {
				cfj cfj8 = cfj.a(b);
				bqb.a(fu, cfj8, 4);
				d(cfj8, bqb, fu);
				return ang.CONSUME;
			}
		} else {
			return super.a(cfj, bqb, fu, bec, anf, deh);
		}
	}

	@Override
	public cak b(cfj cfj) {
		return cak.MODEL;
	}

	@Override
	public boolean b_(cfj cfj) {
		return true;
	}

	@Override
	public cdl a(bpg bpg) {
		return new cdt();
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(bwy.a, b);
	}
}
