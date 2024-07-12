import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class arw extends aqh<aoy> {
	private final float b;
	private final Long2LongMap c = new Long2LongOpenHashMap();
	private int d;
	private long e;

	public arw(float float1) {
		super(ImmutableMap.of(awp.m, awq.VALUE_ABSENT, awp.b, awq.VALUE_ABSENT));
		this.b = float1;
	}

	@Override
	protected boolean a(zd zd, aoy aoy) {
		if (zd.Q() - this.e < 20L) {
			return false;
		} else {
			apg apg4 = (apg)aoy;
			axz axz5 = zd.x();
			Optional<fu> optional6 = axz5.d(ayc.r.c(), aoy.cA(), 48, axz.b.ANY);
			return optional6.isPresent() && !(((fu)optional6.get()).j(apg4.cA()) <= 4.0);
		}
	}

	@Override
	protected void a(zd zd, aoy aoy, long long3) {
		this.d = 0;
		this.e = zd.Q() + (long)zd.v_().nextInt(20);
		apg apg6 = (apg)aoy;
		axz axz7 = zd.x();
		Predicate<fu> predicate8 = fu -> {
			long long3x = fu.a();
			if (this.c.containsKey(long3x)) {
				return false;
			} else if (++this.d >= 5) {
				return false;
			} else {
				this.c.put(long3x, this.e + 40L);
				return true;
			}
		};
		Stream<fu> stream9 = axz7.a(ayc.r.c(), predicate8, aoy.cA(), 48, axz.b.ANY);
		czf czf10 = apg6.x().a(stream9, ayc.r.d());
		if (czf10 != null && czf10.i()) {
			fu fu11 = czf10.m();
			Optional<ayc> optional12 = axz7.c(fu11);
			if (optional12.isPresent()) {
				aoy.cI().a(awp.m, new awr(fu11, this.b, 1));
				qy.c(zd, fu11);
			}
		} else if (this.d < 5) {
			this.c.long2LongEntrySet().removeIf(entry -> entry.getLongValue() < this.e);
		}
	}
}
