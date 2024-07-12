import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class axj extends axn<aoy> {
	@Override
	public Set<awp<?>> a() {
		return ImmutableSet.of(awp.h, awp.g, awp.K, awp.V, awp.ad, awp.S, awp.T, awp.X, awp.W, awp.ab, awp.ac, awp.af);
	}

	@Override
	protected void a(zd zd, aoy aoy) {
		apr<?> apr4 = aoy.cI();
		apr4.a(awp.af, c(zd, aoy));
		Optional<aoz> optional5 = Optional.empty();
		Optional<bcx> optional6 = Optional.empty();
		Optional<bcx> optional7 = Optional.empty();
		Optional<bdc> optional8 = Optional.empty();
		Optional<aoy> optional9 = Optional.empty();
		Optional<bec> optional10 = Optional.empty();
		Optional<bec> optional11 = Optional.empty();
		int integer12 = 0;
		List<bdc> list13 = Lists.<bdc>newArrayList();
		List<bdc> list14 = Lists.<bdc>newArrayList();

		for (aoy aoy17 : (List)apr4.c(awp.h).orElse(ImmutableList.of())) {
			if (aoy17 instanceof bcx) {
				bcx bcx18 = (bcx)aoy17;
				if (bcx18.x_() && !optional7.isPresent()) {
					optional7 = Optional.of(bcx18);
				} else if (bcx18.eM()) {
					integer12++;
					if (!optional6.isPresent() && bcx18.eP()) {
						optional6 = Optional.of(bcx18);
					}
				}
			} else if (aoy17 instanceof bdc) {
				bdc bdc18 = (bdc)aoy17;
				if (bdc18.x_() && !optional8.isPresent()) {
					optional8 = Optional.of(bdc18);
				} else if (bdc18.eM()) {
					list13.add(bdc18);
				}
			} else if (aoy17 instanceof bec) {
				bec bec18 = (bec)aoy17;
				if (!optional10.isPresent() && aop.f.test(aoy17) && !bdd.a(bec18)) {
					optional10 = Optional.of(bec18);
				}

				if (!optional11.isPresent() && !bec18.a_() && bdd.b(bec18)) {
					optional11 = Optional.of(bec18);
				}
			} else if (optional5.isPresent() || !(aoy17 instanceof bcs) && !(aoy17 instanceof baw)) {
				if (!optional9.isPresent() && bdd.a(aoy17.U())) {
					optional9 = Optional.of(aoy17);
				}
			} else {
				optional5 = Optional.of((aoz)aoy17);
			}
		}

		for (aoy aoy18 : (List)apr4.c(awp.g).orElse(ImmutableList.of())) {
			if (aoy18 instanceof bdc && ((bdc)aoy18).eM()) {
				list14.add((bdc)aoy18);
			}
		}

		apr4.a(awp.K, optional5);
		apr4.a(awp.S, optional6);
		apr4.a(awp.T, optional7);
		apr4.a(awp.U, optional8);
		apr4.a(awp.aa, optional9);
		apr4.a(awp.V, optional10);
		apr4.a(awp.ad, optional11);
		apr4.a(awp.W, list14);
		apr4.a(awp.X, list13);
		apr4.a(awp.ab, list13.size());
		apr4.a(awp.ac, integer12);
	}

	private static Optional<fu> c(zd zd, aoy aoy) {
		return fu.a(aoy.cA(), 8, 4, fu -> a(zd, fu));
	}

	private static boolean a(zd zd, fu fu) {
		cfj cfj3 = zd.d_(fu);
		boolean boolean4 = cfj3.a(acx.O);
		return boolean4 && cfj3.a(bvs.mf) ? bwb.g(cfj3) : boolean4;
	}
}
