import com.google.common.base.Predicates;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public final class aop {
	public static final Predicate<aom> a = aom::aU;
	public static final Predicate<aoy> b = aoy::aU;
	public static final Predicate<aom> c = aom -> aom.aU() && !aom.bo() && !aom.bn();
	public static final Predicate<aom> d = aom -> aom instanceof amz && aom.aU();
	public static final Predicate<aom> e = aom -> !(aom instanceof bec) || !aom.a_() && !((bec)aom).b_();
	public static final Predicate<aom> f = aom -> !(aom instanceof bec) || !aom.a_() && !((bec)aom).b_() && aom.l.ac() != and.PEACEFUL;
	public static final Predicate<aom> g = aom -> !aom.a_();

	public static Predicate<aom> a(double double1, double double2, double double3, double double4) {
		double double9 = double4 * double4;
		return aom -> aom != null && aom.g(double1, double2, double3) <= double9;
	}

	public static Predicate<aom> a(aom aom) {
		dfo dfo2 = aom.bC();
		dfo.a a3 = dfo2 == null ? dfo.a.ALWAYS : dfo2.l();
		return (Predicate<aom>)(a3 == dfo.a.NEVER
			? Predicates.alwaysFalse()
			: g.and(
				aom4 -> {
					if (!aom4.aR()) {
						return false;
					} else if (!aom.l.v || aom4 instanceof bec && ((bec)aom4).ey()) {
						dfo dfo5 = aom4.bC();
						dfo.a a6 = dfo5 == null ? dfo.a.ALWAYS : dfo5.l();
						if (a6 == dfo.a.NEVER) {
							return false;
						} else {
							boolean boolean7 = dfo2 != null && dfo2.a(dfo5);
							return (a3 == dfo.a.PUSH_OWN_TEAM || a6 == dfo.a.PUSH_OWN_TEAM) && boolean7
								? false
								: a3 != dfo.a.PUSH_OTHER_TEAMS && a6 != dfo.a.PUSH_OTHER_TEAMS || boolean7;
						}
					} else {
						return false;
					}
				}
			));
	}

	public static Predicate<aom> b(aom aom) {
		return aom2 -> {
			while (aom2.bn()) {
				aom2 = aom2.cs();
				if (aom2 == aom) {
					return false;
				}
			}

			return true;
		};
	}

	public static class a implements Predicate<aom> {
		private final bki a;

		public a(bki bki) {
			this.a = bki;
		}

		public boolean test(@Nullable aom aom) {
			if (!aom.aU()) {
				return false;
			} else if (!(aom instanceof aoy)) {
				return false;
			} else {
				aoy aoy3 = (aoy)aom;
				return aoy3.e(this.a);
			}
		}
	}
}
