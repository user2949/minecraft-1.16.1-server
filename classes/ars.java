import com.google.common.collect.ImmutableMap;

public class ars extends aqh<aoy> {
	public ars() {
		super(ImmutableMap.of(awp.e, awq.VALUE_PRESENT));
	}

	@Override
	protected boolean a(zd zd, aoy aoy) {
		return zd.t.nextFloat() > 0.95F;
	}

	@Override
	protected void a(zd zd, aoy aoy, long long3) {
		apr<?> apr6 = aoy.cI();
		fu fu7 = ((gc)apr6.c(awp.e).get()).b();
		if (fu7.a(aoy.cA(), 3.0)) {
			cfj cfj8 = zd.d_(fu7);
			if (cfj8.a(bvs.mb)) {
				bvp bvp9 = (bvp)cfj8.b();
				bvp9.a(zd, fu7, null);
			}
		}
	}
}
