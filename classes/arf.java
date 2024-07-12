import com.google.common.collect.ImmutableMap;

public class arf extends aqh<bdp> {
	private final float b;

	public arf(float float1) {
		super(ImmutableMap.of(awp.m, awq.REGISTERED, awp.n, awq.REGISTERED), Integer.MAX_VALUE);
		this.b = float1;
	}

	protected boolean a(zd zd, bdp bdp) {
		bec bec4 = bdp.eN();
		return bdp.aU() && bec4 != null && !bdp.aA() && !bdp.w && bdp.h(bec4) <= 16.0 && bec4.bw != null;
	}

	protected boolean b(zd zd, bdp bdp, long long3) {
		return this.a(zd, bdp);
	}

	protected void a(zd zd, bdp bdp, long long3) {
		this.a(bdp);
	}

	protected void c(zd zd, bdp bdp, long long3) {
		apr<?> apr6 = bdp.cI();
		apr6.b(awp.m);
		apr6.b(awp.n);
	}

	protected void d(zd zd, bdp bdp, long long3) {
		this.a(bdp);
	}

	@Override
	protected boolean a(long long1) {
		return false;
	}

	private void a(bdp bdp) {
		apr<?> apr3 = bdp.cI();
		apr3.a(awp.m, new awr(new aqp(bdp.eN(), false), this.b, 2));
		apr3.a(awp.n, new aqp(bdp.eN(), true));
	}
}
