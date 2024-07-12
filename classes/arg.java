import com.google.common.collect.ImmutableMap;

public class arg extends aqh<aoz> {
	public arg(int integer1, int integer2) {
		super(ImmutableMap.of(awp.n, awq.VALUE_PRESENT), integer1, integer2);
	}

	protected boolean b(zd zd, aoz aoz, long long3) {
		return aoz.cI().c(awp.n).filter(arn -> arn.a(aoz)).isPresent();
	}

	protected void c(zd zd, aoz aoz, long long3) {
		aoz.cI().b(awp.n);
	}

	protected void c(zd zd, aoz aoz, long long3) {
		aoz.cI().c(awp.n).ifPresent(arn -> aoz.t().a(arn.a()));
	}
}
