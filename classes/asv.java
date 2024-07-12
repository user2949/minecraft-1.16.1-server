import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class asv extends aqh<apg> {
	private final float b;
	private final int c;
	private final int d;

	public asv(float float1) {
		this(float1, 10, 7);
	}

	public asv(float float1, int integer2, int integer3) {
		super(ImmutableMap.of(awp.m, awq.VALUE_ABSENT));
		this.b = float1;
		this.c = integer2;
		this.d = integer3;
	}

	protected void a(zd zd, apg apg, long long3) {
		fu fu6 = apg.cA();
		if (zd.b_(fu6)) {
			this.a(apg);
		} else {
			go go7 = go.a(fu6);
			go go8 = aqi.a(zd, go7, 2);
			if (go8 != go7) {
				this.a(apg, go8);
			} else {
				this.a(apg);
			}
		}
	}

	private void a(apg apg, go go) {
		Optional<dem> optional4 = Optional.ofNullable(axu.b(apg, this.c, this.d, dem.c(go.q())));
		apg.cI().a(awp.m, optional4.map(dem -> new awr(dem, this.b, 0)));
	}

	private void a(apg apg) {
		Optional<dem> optional3 = Optional.ofNullable(axu.b(apg, this.c, this.d));
		apg.cI().a(awp.m, optional3.map(dem -> new awr(dem, this.b, 0)));
	}
}
