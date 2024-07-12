import com.google.common.collect.ImmutableMap;

public class asw extends aqh<bdp> {
	public asw() {
		super(ImmutableMap.of());
	}

	protected void a(zd zd, bdp bdp, long long3) {
		boolean boolean6 = asz.b(bdp) || asz.a(bdp) || a(bdp);
		if (!boolean6) {
			bdp.cI().b(awp.x);
			bdp.cI().b(awp.y);
			bdp.cI().a(zd.R(), zd.Q());
		}
	}

	private static boolean a(bdp bdp) {
		return bdp.cI().c(awp.y).filter(aoy -> aoy.h(bdp) <= 36.0).isPresent();
	}
}
