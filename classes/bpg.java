import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public interface bpg {
	@Nullable
	cdl c(fu fu);

	cfj d_(fu fu);

	cxa b(fu fu);

	default int h(fu fu) {
		return this.d_(fu).f();
	}

	default int H() {
		return 15;
	}

	default int I() {
		return 256;
	}

	default Stream<cfj> a(deg deg) {
		return fu.a(deg).map(this::d_);
	}

	default deh a(bpj bpj) {
		return a(bpj, (bpjx, fu) -> {
			cfj cfj4 = this.d_(fu);
			cxa cxa5 = this.b(fu);
			dem dem6 = bpjx.b();
			dem dem7 = bpjx.a();
			dfg dfg8 = bpjx.a(cfj4, this, fu);
			deh deh9 = this.a(dem6, dem7, fu, dfg8, cfj4);
			dfg dfg10 = bpjx.a(cxa5, this, fu);
			deh deh11 = dfg10.a(dem6, dem7, fu);
			double double12 = deh9 == null ? Double.MAX_VALUE : bpjx.b().g(deh9.e());
			double double14 = deh11 == null ? Double.MAX_VALUE : bpjx.b().g(deh11.e());
			return double12 <= double14 ? deh9 : deh11;
		}, bpjx -> {
			dem dem2 = bpjx.b().d(bpjx.a());
			return deh.a(bpjx.a(), fz.a(dem2.b, dem2.c, dem2.d), new fu(bpjx.a()));
		});
	}

	@Nullable
	default deh a(dem dem1, dem dem2, fu fu, dfg dfg, cfj cfj) {
		deh deh7 = dfg.a(dem1, dem2, fu);
		if (deh7 != null) {
			deh deh8 = cfj.m(this, fu).a(dem1, dem2, fu);
			if (deh8 != null && deh8.e().d(dem1).g() < deh7.e().d(dem1).g()) {
				return deh7.a(deh8.b());
			}
		}

		return deh7;
	}

	static <T> T a(bpj bpj, BiFunction<bpj, fu, T> biFunction, Function<bpj, T> function) {
		dem dem4 = bpj.b();
		dem dem5 = bpj.a();
		if (dem4.equals(dem5)) {
			return (T)function.apply(bpj);
		} else {
			double double6 = aec.d(-1.0E-7, dem5.b, dem4.b);
			double double8 = aec.d(-1.0E-7, dem5.c, dem4.c);
			double double10 = aec.d(-1.0E-7, dem5.d, dem4.d);
			double double12 = aec.d(-1.0E-7, dem4.b, dem5.b);
			double double14 = aec.d(-1.0E-7, dem4.c, dem5.c);
			double double16 = aec.d(-1.0E-7, dem4.d, dem5.d);
			int integer18 = aec.c(double12);
			int integer19 = aec.c(double14);
			int integer20 = aec.c(double16);
			fu.a a21 = new fu.a(integer18, integer19, integer20);
			T object22 = (T)biFunction.apply(bpj, a21);
			if (object22 != null) {
				return object22;
			} else {
				double double23 = double6 - double12;
				double double25 = double8 - double14;
				double double27 = double10 - double16;
				int integer29 = aec.k(double23);
				int integer30 = aec.k(double25);
				int integer31 = aec.k(double27);
				double double32 = integer29 == 0 ? Double.MAX_VALUE : (double)integer29 / double23;
				double double34 = integer30 == 0 ? Double.MAX_VALUE : (double)integer30 / double25;
				double double36 = integer31 == 0 ? Double.MAX_VALUE : (double)integer31 / double27;
				double double38 = double32 * (integer29 > 0 ? 1.0 - aec.h(double12) : aec.h(double12));
				double double40 = double34 * (integer30 > 0 ? 1.0 - aec.h(double14) : aec.h(double14));
				double double42 = double36 * (integer31 > 0 ? 1.0 - aec.h(double16) : aec.h(double16));

				while (double38 <= 1.0 || double40 <= 1.0 || double42 <= 1.0) {
					if (double38 < double40) {
						if (double38 < double42) {
							integer18 += integer29;
							double38 += double32;
						} else {
							integer20 += integer31;
							double42 += double36;
						}
					} else if (double40 < double42) {
						integer19 += integer30;
						double40 += double34;
					} else {
						integer20 += integer31;
						double42 += double36;
					}

					T object44 = (T)biFunction.apply(bpj, a21.d(integer18, integer19, integer20));
					if (object44 != null) {
						return object44;
					}
				}

				return (T)function.apply(bpj);
			}
		}
	}
}
