import com.google.common.collect.ImmutableMap;

public class bdg<E extends bdc> extends aqh<E> {
	public bdg() {
		super(ImmutableMap.of(awp.S, awq.VALUE_PRESENT, awp.L, awq.VALUE_ABSENT, awp.P, awq.VALUE_ABSENT, awp.X, awq.REGISTERED));
	}

	protected boolean a(zd zd, bdc bdc) {
		return !bdc.x_() && !bdd.e(bdc);
	}

	protected void a(zd zd, E bdc, long long3) {
		bcx bcx6 = (bcx)bdc.cI().c(awp.S).get();
		bdd.c(bdc, bcx6);
		bdd.j(bdc);
		bdd.b(bdc, bcx6);
		bdd.g(bdc);
	}
}
