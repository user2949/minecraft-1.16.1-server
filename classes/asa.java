import com.google.common.collect.ImmutableMap;

public class asa extends aqh<aoy> {
	public asa() {
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
		if (bfh7 != null) {
			if (bfh7.c() && !bfh7.b()) {
				apr6.b(bfl.h);
				apr6.a(bfl.h);
			} else {
				apr6.b(bfl.i);
				apr6.a(bfl.i);
			}
		}
	}
}
