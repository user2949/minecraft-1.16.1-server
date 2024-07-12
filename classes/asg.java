import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class asg extends aqh<aoy> {
	private long b;

	public asg() {
		super(ImmutableMap.of(awp.b, awq.VALUE_PRESENT, awp.G, awq.REGISTERED));
	}

	@Override
	protected boolean a(zd zd, aoy aoy) {
		if (aoy.bn()) {
			return false;
		} else {
			apr<?> apr4 = aoy.cI();
			gc gc5 = (gc)apr4.c(awp.b).get();
			if (zd.W() != gc5.a()) {
				return false;
			} else {
				Optional<Long> optional6 = apr4.c(awp.G);
				if (optional6.isPresent()) {
					long long7 = zd.Q() - (Long)optional6.get();
					if (long7 > 0L && long7 < 100L) {
						return false;
					}
				}

				cfj cfj7 = zd.d_(gc5.b());
				return gc5.b().a(aoy.cz(), 2.0) && cfj7.b().a(acx.K) && !(Boolean)cfj7.c(bvm.b);
			}
		}
	}

	@Override
	protected boolean b(zd zd, aoy aoy, long long3) {
		Optional<gc> optional6 = aoy.cI().c(awp.b);
		if (!optional6.isPresent()) {
			return false;
		} else {
			fu fu7 = ((gc)optional6.get()).b();
			return aoy.cI().c(bfl.e) && aoy.cD() > (double)fu7.v() + 0.4 && fu7.a(aoy.cz(), 1.14);
		}
	}

	@Override
	protected void a(zd zd, aoy aoy, long long3) {
		if (long3 > this.b) {
			aoy.cI().c(awp.v).ifPresent(set -> arb.a(zd, ImmutableList.of(), 0, aoy, aoy.cI()));
			aoy.b(((gc)aoy.cI().c(awp.b).get()).b());
		}
	}

	@Override
	protected boolean a(long long1) {
		return false;
	}

	@Override
	protected void c(zd zd, aoy aoy, long long3) {
		if (aoy.el()) {
			aoy.em();
			this.b = long3 + 40L;
		}
	}
}
