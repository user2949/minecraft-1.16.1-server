import com.google.common.collect.ImmutableMap;

public class arq extends aqh<bdp> {
	public arq() {
		super(ImmutableMap.of(awp.c, awq.VALUE_ABSENT));
	}

	protected boolean a(zd zd, bdp bdp) {
		bdq bdq4 = bdp.eY();
		return bdq4.b() != bds.a && bdq4.b() != bds.l && bdp.eM() == 0 && bdq4.c() <= 1;
	}

	protected void a(zd zd, bdp bdp, long long3) {
		bdp.a(bdp.eY().a(bds.a));
		bdp.b(zd);
	}
}
