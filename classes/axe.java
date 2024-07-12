import com.google.common.collect.ImmutableSet;
import java.util.Set;

public class axe extends axn<aoy> {
	@Override
	public Set<awp<?>> a() {
		return ImmutableSet.of(awp.x, awp.y);
	}

	@Override
	protected void a(zd zd, aoy aoy) {
		apr<?> apr4 = aoy.cI();
		anw anw5 = aoy.dl();
		if (anw5 != null) {
			apr4.a(awp.x, aoy.dl());
			aom aom6 = anw5.k();
			if (aom6 instanceof aoy) {
				apr4.a(awp.y, (aoy)aom6);
			}
		} else {
			apr4.b(awp.x);
		}

		apr4.c(awp.y).ifPresent(aoyx -> {
			if (!aoyx.aU() || aoyx.l != zd) {
				apr4.b(awp.y);
			}
		});
	}
}
