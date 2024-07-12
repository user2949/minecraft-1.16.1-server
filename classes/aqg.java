import com.google.common.collect.ImmutableMap;

public class aqg extends aqh<aoy> {
	private final int b;

	public aqg(awp<?> awp, int integer) {
		super(ImmutableMap.of(awp.o, awq.REGISTERED, awp.ag, awq.VALUE_ABSENT, awp, awq.VALUE_PRESENT));
		this.b = integer;
	}

	@Override
	protected void a(zd zd, aoy aoy, long long3) {
		aoy.cI().a(awp.ag, true, (long)this.b);
		aoy.cI().b(awp.o);
	}
}
