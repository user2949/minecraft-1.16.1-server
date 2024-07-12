import com.google.common.collect.ImmutableMap;

public class arh extends aqh<aoz> {
	private final int b;

	public arh(int integer) {
		super(ImmutableMap.of(awp.n, awq.REGISTERED, awp.o, awq.VALUE_PRESENT, awp.p, awq.VALUE_ABSENT));
		this.b = integer;
	}

	protected boolean a(zd zd, aoz aoz) {
		aoy aoy4 = this.b(aoz);
		return !this.a(aoz) && aqi.c(aoz, aoy4) && aqi.b(aoz, aoy4);
	}

	private boolean a(aoz aoz) {
		return aoz.a(bke -> bke instanceof bkv && aoz.a((bkv)bke));
	}

	protected void a(zd zd, aoz aoz, long long3) {
		aoy aoy6 = this.b(aoz);
		aqi.a(aoz, aoy6);
		aoz.a(anf.MAIN_HAND);
		aoz.B(aoy6);
		aoz.cI().a(awp.p, true, (long)this.b);
	}

	private aoy b(aoz aoz) {
		return (aoy)aoz.cI().c(awp.o).get();
	}
}
