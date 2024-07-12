import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class ary extends aqh<aoy> {
	private final int b;
	private final int c;
	private int d;

	public ary(int integer1, int integer2) {
		super(ImmutableMap.of(awp.B, awq.VALUE_PRESENT, awp.C, awq.VALUE_PRESENT));
		this.c = integer1 * 20;
		this.d = 0;
		this.b = integer2;
	}

	@Override
	protected void a(zd zd, aoy aoy, long long3) {
		apr<?> apr6 = aoy.cI();
		Optional<Long> optional7 = apr6.c(awp.C);
		boolean boolean8 = (Long)optional7.get() + 300L <= long3;
		if (this.d <= this.c && !boolean8) {
			fu fu9 = ((gc)apr6.c(awp.B).get()).b();
			if (fu9.a(aoy.cA(), (double)this.b)) {
				this.d++;
			}
		} else {
			apr6.b(awp.C);
			apr6.b(awp.B);
			apr6.a(zd.R(), zd.Q());
			this.d = 0;
		}
	}
}
