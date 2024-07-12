import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class asn extends aqh<apg> {
	private final awp<gc> b;
	private final int c;
	private final int d;
	private long e;

	public asn(awp<gc> awp, int integer2, int integer3) {
		super(ImmutableMap.of(awp.m, awq.REGISTERED, awp, awq.VALUE_PRESENT));
		this.b = awp;
		this.c = integer2;
		this.d = integer3;
	}

	protected boolean a(zd zd, apg apg) {
		Optional<gc> optional4 = apg.cI().c(this.b);
		return optional4.isPresent() && zd.W() == ((gc)optional4.get()).a() && ((gc)optional4.get()).b().a(apg.cz(), (double)this.d);
	}

	protected void a(zd zd, apg apg, long long3) {
		if (long3 > this.e) {
			apr<?> apr6 = apg.cI();
			Optional<gc> optional7 = apr6.c(this.b);
			optional7.ifPresent(gc -> apr6.a(awp.m, new awr(gc.b(), 0.4F, this.c)));
			this.e = long3 + 80L;
		}
	}
}
