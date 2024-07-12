import com.google.common.collect.ImmutableMap;

public class ase extends aqh<aoy> {
	private final float b;
	private final int c;

	public ase(float float1, int integer) {
		super(ImmutableMap.of(awp.m, awq.VALUE_ABSENT, awp.n, awq.VALUE_PRESENT));
		this.b = float1;
		this.c = integer;
	}

	@Override
	protected void a(zd zd, aoy aoy, long long3) {
		apr<?> apr6 = aoy.cI();
		arn arn7 = (arn)apr6.c(awp.n).get();
		apr6.a(awp.m, new awr(arn7, this.b, this.c));
	}
}
