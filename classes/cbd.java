import java.util.Random;
import javax.annotation.Nullable;

public class cbd extends bvr {
	public static final cgi a = cfz.aq;
	protected static final dfg[] b = new dfg[]{
		dfd.a(),
		bvr.a(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
		bvr.a(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
		bvr.a(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
		bvr.a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
		bvr.a(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
		bvr.a(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
		bvr.a(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
		bvr.a(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
	};

	protected cbd(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, Integer.valueOf(1)));
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		switch (czg) {
			case LAND:
				return (Integer)cfj.c(a) < 5;
			case WATER:
				return false;
			case AIR:
				return false;
			default:
				return false;
		}
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return b[cfj.c(a)];
	}

	@Override
	public dfg c(cfj cfj, bpg bpg, fu fu, der der) {
		return b[cfj.c(a) - 1];
	}

	@Override
	public dfg e(cfj cfj, bpg bpg, fu fu) {
		return b[cfj.c(a)];
	}

	@Override
	public dfg a(cfj cfj, bpg bpg, fu fu, der der) {
		return b[cfj.c(a)];
	}

	@Override
	public boolean c_(cfj cfj) {
		return true;
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		cfj cfj5 = bqd.d_(fu.c());
		if (cfj5.a(bvs.cD) || cfj5.a(bvs.gT) || cfj5.a(bvs.go)) {
			return false;
		} else {
			return !cfj5.a(bvs.ne) && !cfj5.a(bvs.cM) ? bvr.a(cfj5.k(bqd, fu.c()), fz.UP) || cfj5.b() == this && (Integer)cfj5.c(a) == 8 : true;
		}
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		return !cfj1.a(bqc, fu5) ? bvs.a.n() : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		if (zd.a(bqi.BLOCK, fu) > 11) {
			c(cfj, zd, fu);
			zd.a(fu, false);
		}
	}

	@Override
	public boolean a(cfj cfj, bin bin) {
		int integer4 = (Integer)cfj.c(a);
		if (bin.l().b() != this.h() || integer4 >= 8) {
			return integer4 == 1;
		} else {
			return bin.c() ? bin.i() == fz.UP : true;
		}
	}

	@Nullable
	@Override
	public cfj a(bin bin) {
		cfj cfj3 = bin.o().d_(bin.a());
		if (cfj3.a(this)) {
			int integer4 = (Integer)cfj3.c(a);
			return cfj3.a(a, Integer.valueOf(Math.min(8, integer4 + 1)));
		} else {
			return super.a(bin);
		}
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(cbd.a);
	}
}
