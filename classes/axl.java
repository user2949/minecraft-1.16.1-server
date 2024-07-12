import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;

public class axl extends axn<bdp> {
	public axl() {
		super(40);
	}

	protected void a(zd zd, bdp bdp) {
		ug<bqb> ug4 = zd.W();
		fu fu5 = bdp.cA();
		List<gc> list6 = Lists.<gc>newArrayList();
		int integer7 = 4;

		for (int integer8 = -4; integer8 <= 4; integer8++) {
			for (int integer9 = -2; integer9 <= 2; integer9++) {
				for (int integer10 = -4; integer10 <= 4; integer10++) {
					fu fu11 = fu5.b(integer8, integer9, integer10);
					if (bdp.eY().b().d().contains(zd.d_(fu11).b())) {
						list6.add(gc.a(ug4, fu11));
					}
				}
			}
		}

		apr<?> apr8 = bdp.cI();
		if (!list6.isEmpty()) {
			apr8.a(awp.f, list6);
		} else {
			apr8.b(awp.f);
		}
	}

	@Override
	public Set<awp<?>> a() {
		return ImmutableSet.of(awp.f);
	}
}
