import com.google.common.collect.ImmutableMap;

public class asp extends aqh<aoz> {
	private final float b;

	public asp(float float1) {
		super(ImmutableMap.of());
		this.b = float1;
	}

	protected boolean a(zd zd, aoz aoz) {
		return aoz.aA() && aoz.b(acz.a) > aoz.cw() || aoz.aN();
	}

	protected boolean b(zd zd, aoz aoz, long long3) {
		return this.a(zd, aoz);
	}

	protected void d(zd zd, aoz aoz, long long3) {
		if (aoz.cX().nextFloat() < this.b) {
			aoz.v().a();
		}
	}
}
