import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class bze extends bvr implements bvw {
	public static final cgi a = cfz.av;
	protected final cwy b;
	private final List<cxa> d;
	public static final dfg c = bvr.a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);

	protected bze(cwy cwy, cfi.c c) {
		super(c);
		this.b = cwy;
		this.d = Lists.<cxa>newArrayList();
		this.d.add(cwy.a(false));

		for (int integer4 = 1; integer4 < 8; integer4++) {
			this.d.add(cwy.a(8 - integer4, false));
		}

		this.d.add(cwy.a(8, true));
		this.j(this.n.b().a(a, Integer.valueOf(0)));
	}

	@Override
	public dfg c(cfj cfj, bpg bpg, fu fu, der der) {
		return der.a(c, fu, true) && cfj.c(a) == 0 && der.a(bpg.b(fu.b()), this.b) ? c : dfd.a();
	}

	@Override
	public boolean a_(cfj cfj) {
		return cfj.m().f();
	}

	@Override
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		cfj.m().b(zd, fu, random);
	}

	@Override
	public boolean b(cfj cfj, bpg bpg, fu fu) {
		return false;
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return !this.b.a(acz.b);
	}

	@Override
	public cxa d(cfj cfj) {
		int integer3 = (Integer)cfj.c(a);
		return (cxa)this.d.get(Math.min(integer3, 8));
	}

	@Override
	public cak b(cfj cfj) {
		return cak.INVISIBLE;
	}

	@Override
	public List<bki> a(cfj cfj, dat.a a) {
		return Collections.emptyList();
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return dfd.a();
	}

	@Override
	public void b(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (this.a(bqb, fu, cfj1)) {
			bqb.F().a(fu, cfj1.m().a(), this.b.a(bqb));
		}
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if (cfj1.m().b() || cfj3.m().b()) {
			bqc.F().a(fu5, cfj1.m().a(), this.b.a(bqc));
		}

		return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu3, bvr bvr, fu fu5, boolean boolean6) {
		if (this.a(bqb, fu3, cfj)) {
			bqb.F().a(fu3, cfj.m().a(), this.b.a(bqb));
		}
	}

	private boolean a(bqb bqb, fu fu, cfj cfj) {
		if (this.b.a(acz.b)) {
			boolean boolean5 = bqb.d_(fu.c()).a(bvs.cN);

			for (fz fz9 : fz.values()) {
				if (fz9 != fz.DOWN) {
					fu fu10 = fu.a(fz9);
					if (bqb.b(fu10).a(acz.a)) {
						bvr bvr11 = bqb.b(fu).b() ? bvs.bK : bvs.m;
						bqb.a(fu, bvr11.n());
						this.a(bqb, fu);
						return false;
					}

					if (boolean5 && bqb.d_(fu10).a(bvs.kV)) {
						bqb.a(fu, bvs.cO.n());
						this.a(bqb, fu);
						return false;
					}
				}
			}
		}

		return true;
	}

	private void a(bqc bqc, fu fu) {
		bqc.c(1501, fu, 0);
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(bze.a);
	}

	@Override
	public cwz b(bqc bqc, fu fu, cfj cfj) {
		if ((Integer)cfj.c(a) == 0) {
			bqc.a(fu, bvs.a.n(), 11);
			return this.b;
		} else {
			return cxb.a;
		}
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu, aom aom) {
		if (this.b.a(acz.b)) {
			float float6 = (float)fu.v() + cfj.m().a((bpg)bqb, fu);
			deg deg7 = aom.cb();
			if (deg7.b < (double)float6 || (double)float6 > deg7.e) {
				aom.aM();
			}
		}
	}
}
