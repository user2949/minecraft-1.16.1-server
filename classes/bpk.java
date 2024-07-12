import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;

public interface bpk extends bpg {
	cgw f();

	@Nullable
	bpg c(int integer1, int integer2);

	default boolean a(@Nullable aom aom, dfg dfg) {
		return true;
	}

	default boolean a(cfj cfj, fu fu, der der) {
		dfg dfg5 = cfj.b(this, fu, der);
		return dfg5.b() || this.a(null, dfg5.a((double)fu.u(), (double)fu.v(), (double)fu.w()));
	}

	default boolean i(aom aom) {
		return this.a(aom, dfd.a(aom.cb()));
	}

	default boolean b(deg deg) {
		return this.b(null, deg, aom -> true);
	}

	default boolean j(aom aom) {
		return this.b(aom, aom.cb(), aomx -> true);
	}

	default boolean a_(aom aom, deg deg) {
		return this.b(aom, deg, aomx -> true);
	}

	default boolean b(@Nullable aom aom, deg deg, Predicate<aom> predicate) {
		return this.d(aom, deg, predicate).allMatch(dfg::b);
	}

	Stream<dfg> c(@Nullable aom aom, deg deg, Predicate<aom> predicate);

	default Stream<dfg> d(@Nullable aom aom, deg deg, Predicate<aom> predicate) {
		return Stream.concat(this.b(aom, deg), this.c(aom, deg, predicate));
	}

	default Stream<dfg> b(@Nullable aom aom, deg deg) {
		return StreamSupport.stream(new bpl(this, aom, deg), false);
	}

	default Stream<dfg> a(@Nullable aom aom, deg deg, BiPredicate<cfj, fu> biPredicate) {
		return StreamSupport.stream(new bpl(this, aom, deg, biPredicate), false);
	}
}
