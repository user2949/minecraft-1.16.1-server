import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public interface bps {
	List<aom> a(@Nullable aom aom, deg deg, @Nullable Predicate<? super aom> predicate);

	<T extends aom> List<T> a(Class<? extends T> class1, deg deg, @Nullable Predicate<? super T> predicate);

	default <T extends aom> List<T> b(Class<? extends T> class1, deg deg, @Nullable Predicate<? super T> predicate) {
		return this.a(class1, deg, predicate);
	}

	List<? extends bec> w();

	default List<aom> a(@Nullable aom aom, deg deg) {
		return this.a(aom, deg, aop.g);
	}

	default boolean a(@Nullable aom aom, dfg dfg) {
		if (dfg.b()) {
			return true;
		} else {
			for (aom aom5 : this.a(aom, dfg.a())) {
				if (!aom5.y && aom5.i && (aom == null || !aom5.x(aom)) && dfd.c(dfg, dfd.a(aom5.cb()), deq.i)) {
					return false;
				}
			}

			return true;
		}
	}

	default <T extends aom> List<T> a(Class<? extends T> class1, deg deg) {
		return this.a(class1, deg, aop.g);
	}

	default <T extends aom> List<T> b(Class<? extends T> class1, deg deg) {
		return this.b(class1, deg, aop.g);
	}

	default Stream<dfg> c(@Nullable aom aom, deg deg, Predicate<aom> predicate) {
		if (deg.a() < 1.0E-7) {
			return Stream.empty();
		} else {
			deg deg5 = deg.g(1.0E-7);
			return this.a(aom, deg5, predicate.and(aom2 -> aom == null || !aom.x(aom2))).stream().flatMap(aom3 -> {
				if (aom != null) {
					deg deg4 = aom.j(aom3);
					if (deg4 != null && deg4.c(deg5)) {
						return Stream.of(aom3.ay(), deg4);
					}
				}

				return Stream.of(aom3.ay());
			}).filter(Objects::nonNull).map(dfd::a);
		}
	}

	@Nullable
	default bec a(double double1, double double2, double double3, double double4, @Nullable Predicate<aom> predicate) {
		double double11 = -1.0;
		bec bec13 = null;

		for (bec bec15 : this.w()) {
			if (predicate == null || predicate.test(bec15)) {
				double double16 = bec15.g(double1, double2, double3);
				if ((double4 < 0.0 || double16 < double4 * double4) && (double11 == -1.0 || double16 < double11)) {
					double11 = double16;
					bec13 = bec15;
				}
			}
		}

		return bec13;
	}

	@Nullable
	default bec a(aom aom, double double2) {
		return this.a(aom.cC(), aom.cD(), aom.cG(), double2, false);
	}

	@Nullable
	default bec a(double double1, double double2, double double3, double double4, boolean boolean5) {
		Predicate<aom> predicate11 = boolean5 ? aop.e : aop.g;
		return this.a(double1, double2, double3, double4, predicate11);
	}

	default boolean a(double double1, double double2, double double3, double double4) {
		for (bec bec11 : this.w()) {
			if (aop.g.test(bec11) && aop.b.test(bec11)) {
				double double12 = bec11.g(double1, double2, double3);
				if (double4 < 0.0 || double12 < double4 * double4) {
					return true;
				}
			}
		}

		return false;
	}

	@Nullable
	default bec a(axs axs, aoy aoy) {
		return this.a(this.w(), axs, aoy, aoy.cC(), aoy.cD(), aoy.cG());
	}

	@Nullable
	default bec a(axs axs, aoy aoy, double double3, double double4, double double5) {
		return this.a(this.w(), axs, aoy, double3, double4, double5);
	}

	@Nullable
	default bec a(axs axs, double double2, double double3, double double4) {
		return this.a(this.w(), axs, null, double2, double3, double4);
	}

	@Nullable
	default <T extends aoy> T a(Class<? extends T> class1, axs axs, @Nullable aoy aoy, double double4, double double5, double double6, deg deg) {
		return this.a(this.a(class1, deg, null), axs, aoy, double4, double5, double6);
	}

	@Nullable
	default <T extends aoy> T b(Class<? extends T> class1, axs axs, @Nullable aoy aoy, double double4, double double5, double double6, deg deg) {
		return this.a(this.b(class1, deg, null), axs, aoy, double4, double5, double6);
	}

	@Nullable
	default <T extends aoy> T a(List<? extends T> list, axs axs, @Nullable aoy aoy, double double4, double double5, double double6) {
		double double11 = -1.0;
		T aoy13 = null;

		for (T aoy15 : list) {
			if (axs.a(aoy, aoy15)) {
				double double16 = aoy15.g(double4, double5, double6);
				if (double11 == -1.0 || double16 < double11) {
					double11 = double16;
					aoy13 = aoy15;
				}
			}
		}

		return aoy13;
	}

	default List<bec> a(axs axs, aoy aoy, deg deg) {
		List<bec> list5 = Lists.<bec>newArrayList();

		for (bec bec7 : this.w()) {
			if (deg.e(bec7.cC(), bec7.cD(), bec7.cG()) && axs.a(aoy, bec7)) {
				list5.add(bec7);
			}
		}

		return list5;
	}

	default <T extends aoy> List<T> a(Class<? extends T> class1, axs axs, aoy aoy, deg deg) {
		List<T> list6 = this.a(class1, deg, null);
		List<T> list7 = Lists.<T>newArrayList();

		for (T aoy9 : list6) {
			if (axs.a(aoy, aoy9)) {
				list7.add(aoy9);
			}
		}

		return list7;
	}

	@Nullable
	default bec b(UUID uUID) {
		for (int integer3 = 0; integer3 < this.w().size(); integer3++) {
			bec bec4 = (bec)this.w().get(integer3);
			if (uUID.equals(bec4.bR())) {
				return bec4;
			}
		}

		return null;
	}
}
