import java.util.Random;
import javax.annotation.Nullable;

public class bux extends bvr implements bvt {
	protected static final dfg a = bvr.a(5.0, 0.0, 5.0, 11.0, 16.0, 11.0);
	protected static final dfg b = bvr.a(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
	protected static final dfg c = bvr.a(6.5, 0.0, 6.5, 9.5, 16.0, 9.5);
	public static final cgi d = cfz.ae;
	public static final cgg<cfw> e = cfz.aN;
	public static final cgi f = cfz.aA;

	public bux(cfi.c c) {
		super(c);
		this.j(this.n.b().a(d, Integer.valueOf(0)).a(e, cfw.NONE).a(f, Integer.valueOf(0)));
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(d, e, f);
	}

	@Override
	public cfi.b aj_() {
		return cfi.b.XZ;
	}

	@Override
	public boolean b(cfj cfj, bpg bpg, fu fu) {
		return true;
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		dfg dfg6 = cfj.c(e) == cfw.LARGE ? b : a;
		dem dem7 = cfj.n(bpg, fu);
		return dfg6.a(dem7.b, dem7.c, dem7.d);
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}

	@Override
	public dfg c(cfj cfj, bpg bpg, fu fu, der der) {
		dem dem6 = cfj.n(bpg, fu);
		return c.a(dem6.b, dem6.c, dem6.d);
	}

	@Nullable
	@Override
	public cfj a(bin bin) {
		cxa cxa3 = bin.o().b(bin.a());
		if (!cxa3.c()) {
			return null;
		} else {
			cfj cfj4 = bin.o().d_(bin.a().c());
			if (cfj4.a(acx.ab)) {
				if (cfj4.a(bvs.kX)) {
					return this.n().a(d, Integer.valueOf(0));
				} else if (cfj4.a(bvs.kY)) {
					int integer5 = cfj4.c(d) > 0 ? 1 : 0;
					return this.n().a(d, Integer.valueOf(integer5));
				} else {
					return bvs.kX.n();
				}
			} else {
				return null;
			}
		}
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		if (!cfj.a(zd, fu)) {
			zd.b(fu, true);
		}
	}

	@Override
	public boolean a_(cfj cfj) {
		return (Integer)cfj.c(f) == 0;
	}

	@Override
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		if ((Integer)cfj.c(f) == 0) {
			if (random.nextInt(3) == 0 && zd.w(fu.b()) && zd.b(fu.b(), 0) >= 9) {
				int integer6 = this.b(zd, fu) + 1;
				if (integer6 < 16) {
					this.a(cfj, zd, fu, random, integer6);
				}
			}
		}
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		return bqd.d_(fu.c()).a(acx.ab);
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if (!cfj1.a(bqc, fu5)) {
			bqc.G().a(fu5, this, 1);
		}

		if (fz == fz.UP && cfj3.a(bvs.kY) && (Integer)cfj3.c(d) > (Integer)cfj1.c(d)) {
			bqc.a(fu5, cfj1.a(d), 2);
		}

		return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public boolean a(bpg bpg, fu fu, cfj cfj, boolean boolean4) {
		int integer6 = this.a(bpg, fu);
		int integer7 = this.b(bpg, fu);
		return integer6 + integer7 + 1 < 16 && (Integer)bpg.d_(fu.b(integer6)).c(f) != 1;
	}

	@Override
	public boolean a(bqb bqb, Random random, fu fu, cfj cfj) {
		return true;
	}

	@Override
	public void a(zd zd, Random random, fu fu, cfj cfj) {
		int integer6 = this.a(zd, fu);
		int integer7 = this.b(zd, fu);
		int integer8 = integer6 + integer7 + 1;
		int integer9 = 1 + random.nextInt(2);

		for (int integer10 = 0; integer10 < integer9; integer10++) {
			fu fu11 = fu.b(integer6);
			cfj cfj12 = zd.d_(fu11);
			if (integer8 >= 16 || (Integer)cfj12.c(f) == 1 || !zd.w(fu11.b())) {
				return;
			}

			this.a(cfj12, zd, fu11, random, integer8);
			integer6++;
			integer8++;
		}
	}

	@Override
	public float a(cfj cfj, bec bec, bpg bpg, fu fu) {
		return bec.dC().b() instanceof blm ? 1.0F : super.a(cfj, bec, bpg, fu);
	}

	protected void a(cfj cfj, bqb bqb, fu fu, Random random, int integer) {
		cfj cfj7 = bqb.d_(fu.c());
		fu fu8 = fu.c(2);
		cfj cfj9 = bqb.d_(fu8);
		cfw cfw10 = cfw.NONE;
		if (integer >= 1) {
			if (!cfj7.a(bvs.kY) || cfj7.c(e) == cfw.NONE) {
				cfw10 = cfw.SMALL;
			} else if (cfj7.a(bvs.kY) && cfj7.c(e) != cfw.NONE) {
				cfw10 = cfw.LARGE;
				if (cfj9.a(bvs.kY)) {
					bqb.a(fu.c(), cfj7.a(e, cfw.SMALL), 3);
					bqb.a(fu8, cfj9.a(e, cfw.NONE), 3);
				}
			}
		}

		int integer11 = cfj.c(d) != 1 && !cfj9.a(bvs.kY) ? 0 : 1;
		int integer12 = (integer < 11 || !(random.nextFloat() < 0.25F)) && integer != 15 ? 0 : 1;
		bqb.a(fu.b(), this.n().a(d, Integer.valueOf(integer11)).a(e, cfw10).a(f, Integer.valueOf(integer12)), 3);
	}

	protected int a(bpg bpg, fu fu) {
		int integer4 = 0;

		while (integer4 < 16 && bpg.d_(fu.b(integer4 + 1)).a(bvs.kY)) {
			integer4++;
		}

		return integer4;
	}

	protected int b(bpg bpg, fu fu) {
		int integer4 = 0;

		while (integer4 < 16 && bpg.d_(fu.c(integer4 + 1)).a(bvs.kY)) {
			integer4++;
		}

		return integer4;
	}
}
