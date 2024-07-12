import java.util.Random;
import javax.annotation.Nullable;

public class bwk extends byp implements bvt {
	public static final cgi a = cfz.af;
	protected static final dfg[] b = new dfg[]{
		bvr.a(11.0, 7.0, 6.0, 15.0, 12.0, 10.0), bvr.a(9.0, 5.0, 5.0, 15.0, 12.0, 11.0), bvr.a(7.0, 3.0, 4.0, 15.0, 12.0, 12.0)
	};
	protected static final dfg[] c = new dfg[]{
		bvr.a(1.0, 7.0, 6.0, 5.0, 12.0, 10.0), bvr.a(1.0, 5.0, 5.0, 7.0, 12.0, 11.0), bvr.a(1.0, 3.0, 4.0, 9.0, 12.0, 12.0)
	};
	protected static final dfg[] d = new dfg[]{
		bvr.a(6.0, 7.0, 1.0, 10.0, 12.0, 5.0), bvr.a(5.0, 5.0, 1.0, 11.0, 12.0, 7.0), bvr.a(4.0, 3.0, 1.0, 12.0, 12.0, 9.0)
	};
	protected static final dfg[] e = new dfg[]{
		bvr.a(6.0, 7.0, 11.0, 10.0, 12.0, 15.0), bvr.a(5.0, 5.0, 9.0, 11.0, 12.0, 15.0), bvr.a(4.0, 3.0, 7.0, 12.0, 12.0, 15.0)
	};

	public bwk(cfi.c c) {
		super(c);
		this.j(this.n.b().a(aq, fz.NORTH).a(a, Integer.valueOf(0)));
	}

	@Override
	public boolean a_(cfj cfj) {
		return (Integer)cfj.c(a) < 2;
	}

	@Override
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		if (zd.t.nextInt(5) == 0) {
			int integer6 = (Integer)cfj.c(a);
			if (integer6 < 2) {
				zd.a(fu, cfj.a(a, Integer.valueOf(integer6 + 1)), 2);
			}
		}
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		bvr bvr5 = bqd.d_(fu.a(cfj.c(aq))).b();
		return bvr5.a(acx.w);
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		int integer6 = (Integer)cfj.c(a);
		switch ((fz)cfj.c(aq)) {
			case SOUTH:
				return e[integer6];
			case NORTH:
			default:
				return d[integer6];
			case WEST:
				return c[integer6];
			case EAST:
				return b[integer6];
		}
	}

	@Nullable
	@Override
	public cfj a(bin bin) {
		cfj cfj3 = this.n();
		bqd bqd4 = bin.o();
		fu fu5 = bin.a();

		for (fz fz9 : bin.e()) {
			if (fz9.n().d()) {
				cfj3 = cfj3.a(aq, fz9);
				if (cfj3.a(bqd4, fu5)) {
					return cfj3;
				}
			}
		}

		return null;
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		return fz == cfj1.c(aq) && !cfj1.a(bqc, fu5) ? bvs.a.n() : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public boolean a(bpg bpg, fu fu, cfj cfj, boolean boolean4) {
		return (Integer)cfj.c(a) < 2;
	}

	@Override
	public boolean a(bqb bqb, Random random, fu fu, cfj cfj) {
		return true;
	}

	@Override
	public void a(zd zd, Random random, fu fu, cfj cfj) {
		zd.a(fu, cfj.a(a, Integer.valueOf((Integer)cfj.c(a) + 1)), 2);
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(aq, bwk.a);
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
