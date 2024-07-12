import com.google.common.collect.ImmutableMap;

public class aqv extends aqh<bdp> {
	private final float b;
	private final int c;

	public aqv(float float1, int integer) {
		super(ImmutableMap.of(awp.m, awq.VALUE_ABSENT));
		this.b = float1;
		this.c = integer;
	}

	protected boolean a(zd zd, bdp bdp) {
		return !zd.b_(bdp.cA());
	}

	protected void a(zd zd, bdp bdp, long long3) {
		axz axz6 = zd.x();
		int integer7 = axz6.a(go.a(bdp.cA()));
		dem dem8 = null;

		for (int integer9 = 0; integer9 < 5; integer9++) {
			dem dem10 = axu.a(bdp, 15, 7, fu -> (double)(-zd.b(go.a(fu))));
			if (dem10 != null) {
				int integer11 = axz6.a(go.a(new fu(dem10)));
				if (integer11 < integer7) {
					dem8 = dem10;
					break;
				}

				if (integer11 == integer7) {
					dem8 = dem10;
				}
			}
		}

		if (dem8 != null) {
			bdp.cI().a(awp.m, new awr(dem8, this.b, this.c));
		}
	}
}
