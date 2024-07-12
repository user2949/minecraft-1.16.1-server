import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;

public class axf extends axn<aoy> {
	@Override
	protected void a(zd zd, aoy aoy) {
		ug<bqb> ug4 = zd.W();
		fu fu5 = aoy.cA();
		List<gc> list6 = Lists.<gc>newArrayList();

		for (int integer7 = -1; integer7 <= 1; integer7++) {
			for (int integer8 = -1; integer8 <= 1; integer8++) {
				for (int integer9 = -1; integer9 <= 1; integer9++) {
					fu fu10 = fu5.b(integer7, integer8, integer9);
					if (zd.d_(fu10).a(acx.g)) {
						list6.add(gc.a(ug4, fu10));
					}
				}
			}
		}

		apr<?> apr7 = aoy.cI();
		if (!list6.isEmpty()) {
			apr7.a(awp.u, list6);
		} else {
			apr7.b(awp.u);
		}
	}

	@Override
	public Set<awp<?>> a() {
		return ImmutableSet.of(awp.u);
	}
}
