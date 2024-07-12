import java.util.Random;
import java.util.function.Supplier;

public class bya extends bvx implements bvt {
	protected static final dfg a = bvr.a(4.0, 0.0, 4.0, 12.0, 9.0, 12.0);
	private final Supplier<ckb<cky, ?>> b;

	protected bya(cfi.c c, Supplier<ckb<cky, ?>> supplier) {
		super(c);
		this.b = supplier;
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return a;
	}

	@Override
	protected boolean c(cfj cfj, bpg bpg, fu fu) {
		return cfj.a(acx.an) || cfj.a(bvs.cN) || super.c(cfj, bpg, fu);
	}

	@Override
	public boolean a(bpg bpg, fu fu, cfj cfj, boolean boolean4) {
		bvr bvr6 = ((cky)((ckb)this.b.get()).e).f.b();
		bvr bvr7 = bpg.d_(fu.c()).b();
		return bvr7 == bvr6;
	}

	@Override
	public boolean a(bqb bqb, Random random, fu fu, cfj cfj) {
		return (double)random.nextFloat() < 0.4;
	}

	@Override
	public void a(zd zd, Random random, fu fu, cfj cfj) {
		((ckb)this.b.get()).a(zd, zd.a(), zd.i().g(), random, fu);
	}
}
