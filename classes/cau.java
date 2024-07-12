import java.util.Random;
import javax.annotation.Nullable;

public class cau extends bvx implements bvt, bzf {
	protected static final dfg a = bvr.a(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);

	protected cau(cfi.c c) {
		super(c);
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return a;
	}

	@Override
	protected boolean c(cfj cfj, bpg bpg, fu fu) {
		return cfj.d(bpg, fu, fz.UP) && !cfj.a(bvs.iJ);
	}

	@Nullable
	@Override
	public cfj a(bin bin) {
		cxa cxa3 = bin.o().b(bin.a());
		return cxa3.a(acz.a) && cxa3.e() == 8 ? super.a(bin) : null;
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		cfj cfj8 = super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
		if (!cfj8.g()) {
			bqc.F().a(fu5, cxb.c, cxb.c.a(bqc));
		}

		return cfj8;
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
	public cxa d(cfj cfj) {
		return cxb.c.a(false);
	}

	@Override
	public void a(zd zd, Random random, fu fu, cfj cfj) {
		cfj cfj6 = bvs.aV.n();
		cfj cfj7 = cfj6.a(cbz.b, cgf.UPPER);
		fu fu8 = fu.b();
		if (zd.d_(fu8).a(bvs.A)) {
			zd.a(fu, cfj6, 2);
			zd.a(fu8, cfj7, 2);
		}
	}

	@Override
	public boolean a(bpg bpg, fu fu, cfj cfj, cwz cwz) {
		return false;
	}

	@Override
	public boolean a(bqc bqc, fu fu, cfj cfj, cxa cxa) {
		return false;
	}
}
