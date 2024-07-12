import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class asm extends aqh<apg> {
	private final awp<gc> b;
	private long c;
	private final int d;

	public asm(awp<gc> awp, int integer) {
		super(ImmutableMap.of(awp.m, awq.REGISTERED, awp, awq.VALUE_PRESENT));
		this.b = awp;
		this.d = integer;
	}

	protected boolean a(zd zd, apg apg) {
		Optional<gc> optional4 = apg.cI().c(this.b);
		return optional4.isPresent() && zd.W() == ((gc)optional4.get()).a() && ((gc)optional4.get()).b().a(apg.cz(), (double)this.d);
	}

	protected void a(zd zd, apg apg, long long3) {
		if (long3 > this.c) {
			Optional<dem> optional6 = Optional.ofNullable(axu.b(apg, 8, 6));
			apg.cI().a(awp.m, optional6.map(dem -> new awr(dem, 0.4F, 1)));
			this.c = long3 + 180L;
		}
	}
}
