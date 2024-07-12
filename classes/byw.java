import java.util.Random;
import javax.annotation.Nullable;

public class byw extends byk implements bzf {
	protected static final dfg e = bvr.a(0.0, 0.0, 0.0, 16.0, 9.0, 16.0);

	protected byw(cfi.c c) {
		super(c, fz.UP, e, true, 0.14);
	}

	@Override
	protected boolean h(cfj cfj) {
		return cfj.a(bvs.A);
	}

	@Override
	protected bvr d() {
		return bvs.kd;
	}

	@Override
	protected boolean c(bvr bvr) {
		return bvr != bvs.iJ;
	}

	@Override
	public boolean a(bpg bpg, fu fu, cfj cfj, cwz cwz) {
		return false;
	}

	@Override
	public boolean a(bqc bqc, fu fu, cfj cfj, cxa cxa) {
		return false;
	}

	@Override
	protected int a(Random random) {
		return 1;
	}

	@Nullable
	@Override
	public cfj a(bin bin) {
		cxa cxa3 = bin.o().b(bin.a());
		return cxa3.a(acz.a) && cxa3.e() == 8 ? this.a(bin.o()) : null;
	}

	@Override
	public cxa d(cfj cfj) {
		return cxb.c.a(false);
	}
}
