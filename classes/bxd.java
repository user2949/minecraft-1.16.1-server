import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import java.util.Random;

public class bxd extends bvg {
	public static final cgd a = bxc.a;
	public static final cga b = cfz.A;
	private static final Map<bke, gw> c = v.a(new Object2ObjectOpenHashMap<>(), object2ObjectOpenHashMap -> object2ObjectOpenHashMap.defaultReturnValue(new gv()));

	public static void a(bqa bqa, gw gw) {
		c.put(bqa.h(), gw);
	}

	protected bxd(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, fz.NORTH).a(b, Boolean.valueOf(false)));
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if (bqb.v) {
			return ang.SUCCESS;
		} else {
			cdl cdl8 = bqb.c(fu);
			if (cdl8 instanceof cdu) {
				bec.a((cdu)cdl8);
				if (cdl8 instanceof cdv) {
					bec.a(acu.ab);
				} else {
					bec.a(acu.ad);
				}
			}

			return ang.CONSUME;
		}
	}

	protected void a(bqb bqb, fu fu) {
		fw fw4 = new fw(bqb, fu);
		cdu cdu5 = fw4.g();
		int integer6 = cdu5.h();
		if (integer6 < 0) {
			bqb.c(1001, fu, 0);
		} else {
			bki bki7 = cdu5.a(integer6);
			gw gw8 = this.a(bki7);
			if (gw8 != gw.a) {
				cdu5.a(integer6, gw8.dispense(fw4, bki7));
			}
		}
	}

	protected gw a(bki bki) {
		return (gw)c.get(bki.b());
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu3, bvr bvr, fu fu5, boolean boolean6) {
		boolean boolean8 = bqb.r(fu3) || bqb.r(fu3.b());
		boolean boolean9 = (Boolean)cfj.c(b);
		if (boolean8 && !boolean9) {
			bqb.G().a(fu3, this, 4);
			bqb.a(fu3, cfj.a(b, Boolean.valueOf(true)), 4);
		} else if (!boolean8 && boolean9) {
			bqb.a(fu3, cfj.a(b, Boolean.valueOf(false)), 4);
		}
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		this.a(zd, fu);
	}

	@Override
	public cdl a(bpg bpg) {
		return new cdu();
	}

	@Override
	public cfj a(bin bin) {
		return this.n().a(a, bin.d().f());
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, aoy aoy, bki bki) {
		if (bki.t()) {
			cdl cdl7 = bqb.c(fu);
			if (cdl7 instanceof cdu) {
				((cdu)cdl7).a(bki.r());
			}
		}
	}

	@Override
	public void a(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!cfj1.a(cfj4.b())) {
			cdl cdl7 = bqb.c(fu);
			if (cdl7 instanceof cdu) {
				anc.a(bqb, fu, (cdu)cdl7);
				bqb.c(fu, this);
			}

			super.a(cfj1, bqb, fu, cfj4, boolean5);
		}
	}

	public static gj a(fv fv) {
		fz fz2 = fv.e().c(a);
		double double3 = fv.a() + 0.7 * (double)fz2.i();
		double double5 = fv.b() + 0.7 * (double)fz2.j();
		double double7 = fv.c() + 0.7 * (double)fz2.k();
		return new gk(double3, double5, double7);
	}

	@Override
	public boolean a(cfj cfj) {
		return true;
	}

	@Override
	public int a(cfj cfj, bqb bqb, fu fu) {
		return bgi.a(bqb.c(fu));
	}

	@Override
	public cak b(cfj cfj) {
		return cak.MODEL;
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		return cfj.a(a, cap.a(cfj.c(a)));
	}

	@Override
	public cfj a(cfj cfj, bzj bzj) {
		return cfj.a(bzj.a(cfj.c(a)));
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(bxd.a, b);
	}
}
