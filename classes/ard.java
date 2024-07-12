import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class ard extends aqh<aoy> {
	private final float b;
	private final int c;
	private final int d;
	private Optional<fu> e = Optional.empty();

	public ard(int integer1, float float2, int integer3) {
		super(ImmutableMap.of(awp.m, awq.VALUE_ABSENT, awp.b, awq.REGISTERED, awp.B, awq.REGISTERED));
		this.c = integer1;
		this.b = float2;
		this.d = integer3;
	}

	@Override
	protected boolean a(zd zd, aoy aoy) {
		Optional<fu> optional4 = zd.x().b(ayc -> ayc == ayc.r, fu -> true, aoy.cA(), this.d + 1, axz.b.ANY);
		if (optional4.isPresent() && ((fu)optional4.get()).a(aoy.cz(), (double)this.d)) {
			this.e = optional4;
		} else {
			this.e = Optional.empty();
		}

		return true;
	}

	@Override
	protected void a(zd zd, aoy aoy, long long3) {
		apr<?> apr6 = aoy.cI();
		Optional<fu> optional7 = this.e;
		if (!optional7.isPresent()) {
			optional7 = zd.x().a(ayc -> ayc == ayc.r, fu -> true, axz.b.ANY, aoy.cA(), this.c, aoy.cX());
			if (!optional7.isPresent()) {
				Optional<gc> optional8 = apr6.c(awp.b);
				if (optional8.isPresent()) {
					optional7 = Optional.of(((gc)optional8.get()).b());
				}
			}
		}

		if (optional7.isPresent()) {
			apr6.b(awp.t);
			apr6.b(awp.n);
			apr6.b(awp.r);
			apr6.b(awp.q);
			apr6.a(awp.B, gc.a(zd.W(), (fu)optional7.get()));
			if (!((fu)optional7.get()).a(aoy.cz(), (double)this.d)) {
				apr6.a(awp.m, new awr((fu)optional7.get(), this.b, this.d));
			}
		}
	}
}
