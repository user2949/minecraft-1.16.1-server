import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class axd extends axn<bcx> {
	@Override
	public Set<awp<?>> a() {
		return ImmutableSet.of(awp.h, awp.af, awp.Z, awp.Y, awp.ab, awp.ac);
	}

	protected void a(zd zd, bcx bcx) {
		apr<?> apr4 = bcx.cI();
		apr4.a(awp.af, this.b(zd, bcx));
		Optional<bdc> optional5 = Optional.empty();
		int integer6 = 0;
		List<bcx> list7 = Lists.<bcx>newArrayList();

		for (aoy aoy10 : (List)apr4.c(awp.h).orElse(Lists.newArrayList())) {
			if (aoy10 instanceof bdc && !aoy10.x_()) {
				integer6++;
				if (!optional5.isPresent()) {
					optional5 = Optional.of((bdc)aoy10);
				}
			}

			if (aoy10 instanceof bcx && !aoy10.x_()) {
				list7.add((bcx)aoy10);
			}
		}

		apr4.a(awp.Z, optional5);
		apr4.a(awp.Y, list7);
		apr4.a(awp.ab, integer6);
		apr4.a(awp.ac, list7.size());
	}

	private Optional<fu> b(zd zd, bcx bcx) {
		return fu.a(bcx.cA(), 8, 4, fu -> zd.d_(fu).a(acx.au));
	}
}
