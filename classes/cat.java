import java.util.Random;
import javax.annotation.Nullable;

public class cat extends bvx implements bvt, cax {
	public static final cgi a = cfz.ay;
	public static final cga b = cfz.C;
	protected static final dfg c = bvr.a(6.0, 0.0, 6.0, 10.0, 6.0, 10.0);
	protected static final dfg d = bvr.a(3.0, 0.0, 3.0, 13.0, 6.0, 13.0);
	protected static final dfg e = bvr.a(2.0, 0.0, 2.0, 14.0, 6.0, 14.0);
	protected static final dfg f = bvr.a(2.0, 0.0, 2.0, 14.0, 7.0, 14.0);

	protected cat(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, Integer.valueOf(1)).a(b, Boolean.valueOf(true)));
	}

	@Nullable
	@Override
	public cfj a(bin bin) {
		cfj cfj3 = bin.o().d_(bin.a());
		if (cfj3.a(this)) {
			return cfj3.a(a, Integer.valueOf(Math.min(4, (Integer)cfj3.c(a) + 1)));
		} else {
			cxa cxa4 = bin.o().b(bin.a());
			boolean boolean5 = cxa4.a() == cxb.c;
			return super.a(bin).a(b, Boolean.valueOf(boolean5));
		}
	}

	public static boolean h(cfj cfj) {
		return !(Boolean)cfj.c(b);
	}

	@Override
	protected boolean c(cfj cfj, bpg bpg, fu fu) {
		return !cfj.k(bpg, fu).a(fz.UP).b();
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		fu fu5 = fu.c();
		return this.c(bqd.d_(fu5), bqd, fu5);
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if (!cfj1.a(bqc, fu5)) {
			return bvs.a.n();
		} else {
			if ((Boolean)cfj1.c(b)) {
				bqc.F().a(fu5, cxb.c, cxb.c.a(bqc));
			}

			return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
		}
	}

	@Override
	public boolean a(cfj cfj, bin bin) {
		return bin.l().b() == this.h() && cfj.c(a) < 4 ? true : super.a(cfj, bin);
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		switch (cfj.c(a)) {
			case 1:
			default:
				return c;
			case 2:
				return d;
			case 3:
				return e;
			case 4:
				return f;
		}
	}

	@Override
	public cxa d(cfj cfj) {
		return cfj.c(b) ? cxb.c.a(false) : super.d(cfj);
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(cat.a, b);
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
		if (!h(cfj) && zd.d_(fu.c()).a(acx.X)) {
			int integer6 = 5;
			int integer7 = 1;
			int integer8 = 2;
			int integer9 = 0;
			int integer10 = fu.u() - 2;
			int integer11 = 0;

			for (int integer12 = 0; integer12 < 5; integer12++) {
				for (int integer13 = 0; integer13 < integer7; integer13++) {
					int integer14 = 2 + fu.v() - 1;

					for (int integer15 = integer14 - 2; integer15 < integer14; integer15++) {
						fu fu16 = new fu(integer10 + integer12, integer15, fu.w() - integer11 + integer13);
						if (fu16 != fu && random.nextInt(6) == 0 && zd.d_(fu16).a(bvs.A)) {
							cfj cfj17 = zd.d_(fu16.c());
							if (cfj17.a(acx.X)) {
								zd.a(fu16, bvs.kU.n().a(a, Integer.valueOf(random.nextInt(4) + 1)), 3);
							}
						}
					}
				}

				if (integer9 < 2) {
					integer7 += 2;
					integer11++;
				} else {
					integer7 -= 2;
					integer11--;
				}

				integer9++;
			}

			zd.a(fu, cfj.a(a, Integer.valueOf(4)), 2);
		}
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
