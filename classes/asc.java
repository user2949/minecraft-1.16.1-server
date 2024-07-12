import com.google.common.collect.ImmutableMap;

public class asc extends aqh<aoz> {
	private final float b;

	public asc(float float1) {
		super(ImmutableMap.of(awp.m, awq.REGISTERED, awp.n, awq.REGISTERED, awp.o, awq.VALUE_PRESENT, awp.h, awq.REGISTERED));
		this.b = float1;
	}

	protected void a(zd zd, aoz aoz, long long3) {
		aoy aoy6 = (aoy)aoz.cI().c(awp.o).get();
		if (aqi.c(aoz, aoy6) && aqi.a(aoz, aoy6, 1)) {
			this.a(aoz);
		} else {
			this.a(aoz, aoy6);
		}
	}

	private void a(aoy aoy1, aoy aoy2) {
		apr apr4 = aoy1.cI();
		apr4.a(awp.n, new aqp(aoy2, true));
		awr awr5 = new awr(new aqp(aoy2, false), this.b, 0);
		apr4.a(awp.m, awr5);
	}

	private void a(aoy aoy) {
		aoy.cI().b(awp.m);
	}
}
