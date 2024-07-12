import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class aro extends aqh<apg> {
	private final float b;
	private final int c;
	private final int d;

	public aro(float float1) {
		this(float1, 10, 7);
	}

	public aro(float float1, int integer2, int integer3) {
		super(ImmutableMap.of(awp.m, awq.VALUE_ABSENT));
		this.b = float1;
		this.c = integer2;
		this.d = integer3;
	}

	protected void a(zd zd, apg apg, long long3) {
		Optional<dem> optional6 = Optional.ofNullable(axu.b(apg, this.c, this.d));
		apg.cI().a(awp.m, optional6.map(dem -> new awr(dem, this.b, 0)));
	}
}
