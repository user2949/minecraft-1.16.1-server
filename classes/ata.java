import com.google.common.collect.ImmutableMap;

public class ata extends aqh<aoy> {
	public ata() {
		super(ImmutableMap.of());
	}

	@Override
	protected boolean a(zd zd, aoy aoy) {
		return !aoy.cI().c(bfl.e) && aoy.el();
	}

	@Override
	protected void a(zd zd, aoy aoy, long long3) {
		aoy.em();
	}
}
