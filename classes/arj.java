import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;

public class arj extends aqh<aoy> {
	private final float b;

	public arj(float float1) {
		super(ImmutableMap.of(awp.m, awq.VALUE_ABSENT));
		this.b = float1;
	}

	@Override
	protected void a(zd zd, aoy aoy, long long3) {
		Optional<dem> optional6 = Optional.ofNullable(this.b(zd, aoy));
		if (optional6.isPresent()) {
			aoy.cI().a(awp.m, optional6.map(dem -> new awr(dem, this.b, 0)));
		}
	}

	@Override
	protected boolean a(zd zd, aoy aoy) {
		return !zd.f(aoy.cA());
	}

	@Nullable
	private dem b(zd zd, aoy aoy) {
		Random random4 = aoy.cX();
		fu fu5 = aoy.cA();

		for (int integer6 = 0; integer6 < 10; integer6++) {
			fu fu7 = fu5.b(random4.nextInt(20) - 10, random4.nextInt(6) - 3, random4.nextInt(20) - 10);
			if (a(zd, aoy, fu7)) {
				return dem.c(fu7);
			}
		}

		return null;
	}

	public static boolean a(zd zd, aoy aoy, fu fu) {
		return zd.f(fu) && (double)zd.a(cio.a.MOTION_BLOCKING, fu).v() <= aoy.cD();
	}
}
