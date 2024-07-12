import com.google.common.collect.ImmutableMap;

public class bdf<E extends bdc> extends aqh<E> {
	private final int b;

	public bdf(int integer) {
		super(ImmutableMap.of(awp.J, awq.VALUE_PRESENT, awp.N, awq.VALUE_ABSENT, awp.O, awq.VALUE_ABSENT));
		this.b = integer;
	}

	protected boolean a(zd zd, E bdc) {
		bbg bbg4 = (bbg)bdc.cI().c(awp.J).get();
		return bdd.a(bbg4.g().b());
	}

	protected void a(zd zd, E bdc, long long3) {
		bdc.cI().a(awp.N, true, (long)this.b);
	}
}
