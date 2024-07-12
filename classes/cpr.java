import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.function.Supplier;

public class cpr extends cpo {
	public static final Codec<cpr> b = Codec.unit((Supplier<cpr>)(() -> cpr.c));
	public static final cpr c = new cpr();
	private static final cfj[] d = new cfj[]{bvs.bv.n(), bvs.bu.n(), bvs.bx.n(), bvs.bw.n()};
	private static final cfj[] e = new cfj[]{bvs.bq.n(), bvs.bt.n(), bvs.by.n(), bvs.bz.n()};

	@Override
	protected cpp<?> a() {
		return cpp.c;
	}

	@Override
	public cfj a(Random random, fu fu) {
		double double4 = bre.f.a((double)fu.u() / 200.0, (double)fu.w() / 200.0, false);
		if (double4 < -0.8) {
			return v.a(d, random);
		} else {
			return random.nextInt(3) > 0 ? v.a(e, random) : bvs.bp.n();
		}
	}
}
