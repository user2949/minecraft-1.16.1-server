import java.util.Random;

public class bxs extends bvr {
	public static final cgi a = cfz.aw;
	protected static final dfg b = bvr.a(0.0, 0.0, 0.0, 16.0, 15.0, 16.0);

	protected bxs(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, Integer.valueOf(0)));
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if (fz == fz.UP && !cfj1.a(bqc, fu5)) {
			bqc.G().a(fu5, this, 1);
		}

		return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		cfj cfj5 = bqd.d_(fu.b());
		return !cfj5.c().b() || cfj5.b() instanceof bxu || cfj5.b() instanceof cfb;
	}

	@Override
	public cfj a(bin bin) {
		return !this.n().a((bqd)bin.o(), bin.a()) ? bvs.j.n() : super.a(bin);
	}

	@Override
	public boolean c_(cfj cfj) {
		return true;
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return b;
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		if (!cfj.a(zd, fu)) {
			d(cfj, zd, fu);
		}
	}

	@Override
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		int integer6 = (Integer)cfj.c(a);
		if (!a((bqd)zd, fu) && !zd.t(fu.b())) {
			if (integer6 > 0) {
				zd.a(fu, cfj.a(a, Integer.valueOf(integer6 - 1)), 2);
			} else if (!a((bpg)zd, fu)) {
				d(cfj, zd, fu);
			}
		} else if (integer6 < 7) {
			zd.a(fu, cfj.a(a, Integer.valueOf(7)), 2);
		}
	}

	@Override
	public void a(bqb bqb, fu fu, aom aom, float float4) {
		if (!bqb.v && bqb.t.nextFloat() < float4 - 0.5F && aom instanceof aoy && (aom instanceof bec || bqb.S().b(bpx.b)) && aom.cx() * aom.cx() * aom.cy() > 0.512F) {
			d(bqb.d_(fu), bqb, fu);
		}

		super.a(bqb, fu, aom, float4);
	}

	public static void d(cfj cfj, bqb bqb, fu fu) {
		bqb.a(fu, a(cfj, bvs.j.n(), bqb, fu));
	}

	private static boolean a(bpg bpg, fu fu) {
		bvr bvr3 = bpg.d_(fu.b()).b();
		return bvr3 instanceof bwv || bvr3 instanceof cbp || bvr3 instanceof buw;
	}

	private static boolean a(bqd bqd, fu fu) {
		for (fu fu4 : fu.a(fu.b(-4, 0, -4), fu.b(4, 1, 4))) {
			if (bqd.b(fu4).a(acz.a)) {
				return true;
			}
		}

		return false;
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(bxs.a);
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
