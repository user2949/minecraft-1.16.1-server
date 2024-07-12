import com.google.common.collect.ImmutableMap;

public class aqe<E extends aok> extends aqh<E> {
	private final adx b;
	private final float c;

	public aqe(adx adx, float float2) {
		super(ImmutableMap.of(awp.I, awq.VALUE_PRESENT, awp.m, awq.VALUE_ABSENT));
		this.b = adx;
		this.c = float2;
	}

	protected boolean a(zd zd, E aok) {
		if (!aok.x_()) {
			return false;
		} else {
			aok aok4 = this.a(aok);
			return aok.a(aok4, (double)(this.b.b() + 1)) && !aok.a(aok4, (double)this.b.a());
		}
	}

	protected void a(zd zd, E aok, long long3) {
		aqi.a(aok, this.a(aok), this.c, this.b.a() - 1);
	}

	private aok a(E aok) {
		return (aok)aok.cI().c(awp.I).get();
	}
}
