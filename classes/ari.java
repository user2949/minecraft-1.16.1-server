import com.google.common.collect.ImmutableMap;

public class ari<E extends aoy> extends aqh<E> {
	private final float b;

	public ari(float float1) {
		super(ImmutableMap.of(awp.n, awq.REGISTERED, awp.m, awq.VALUE_ABSENT, awp.s, awq.VALUE_PRESENT));
		this.b = float1;
	}

	@Override
	protected boolean a(zd zd, E aoy) {
		return !aoy.bn();
	}

	@Override
	protected void a(zd zd, E aoy, long long3) {
		if (this.a(aoy)) {
			aoy.m(this.b(aoy));
		} else {
			aqi.a(aoy, this.b(aoy), this.b, 1);
		}
	}

	private boolean a(E aoy) {
		return this.b(aoy).a(aoy, 1.0);
	}

	private aom b(E aoy) {
		return (aom)aoy.cI().c(awp.s).get();
	}
}
