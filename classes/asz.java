import com.google.common.collect.ImmutableMap;

public class asz extends aqh<bdp> {
	public asz() {
		super(ImmutableMap.of());
	}

	protected boolean b(zd zd, bdp bdp, long long3) {
		return b(bdp) || a(bdp);
	}

	protected void a(zd zd, bdp bdp, long long3) {
		if (b(bdp) || a(bdp)) {
			apr<?> apr6 = bdp.cI();
			if (!apr6.c(bfl.g)) {
				apr6.b(awp.t);
				apr6.b(awp.m);
				apr6.b(awp.n);
				apr6.b(awp.r);
				apr6.b(awp.q);
			}

			apr6.a(bfl.g);
		}
	}

	protected void d(zd zd, bdp bdp, long long3) {
		if (long3 % 100L == 0L) {
			bdp.a(long3, 3);
		}
	}

	public static boolean a(aoy aoy) {
		return aoy.cI().a(awp.A);
	}

	public static boolean b(aoy aoy) {
		return aoy.cI().a(awp.x);
	}
}
