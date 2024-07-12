import com.google.common.collect.ImmutableMap;

public class arr extends aqh<aoy> {
	public arr() {
		super(ImmutableMap.of());
	}

	@Override
	protected boolean a(zd zd, aoy aoy) {
		return zd.t.nextInt(20) == 0;
	}

	@Override
	protected void a(zd zd, aoy aoy, long long3) {
		apr<?> apr6 = aoy.cI();
		bfh bfh7 = zd.c_(aoy.cA());
		if (bfh7 == null || bfh7.d() || bfh7.f()) {
			apr6.b(bfl.b);
			apr6.a(zd.R(), zd.Q());
		}
	}
}
