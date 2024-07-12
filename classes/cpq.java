import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.function.Supplier;

public class cpq extends cpo {
	public static final Codec<cpq> b = Codec.unit((Supplier<cpq>)(() -> cpq.c));
	private static final cfj[] d = new cfj[]{
		bvs.bp.n(), bvs.bq.n(), bvs.bs.n(), bvs.bt.n(), bvs.bu.n(), bvs.bv.n(), bvs.bw.n(), bvs.bx.n(), bvs.by.n(), bvs.bz.n(), bvs.bB.n()
	};
	public static final cpq c = new cpq();

	@Override
	protected cpp<?> a() {
		return cpp.d;
	}

	@Override
	public cfj a(Random random, fu fu) {
		double double4 = aec.a((1.0 + bre.f.a((double)fu.u() / 48.0, (double)fu.w() / 48.0, false)) / 2.0, 0.0, 0.9999);
		return d[(int)(double4 * (double)d.length)];
	}
}
