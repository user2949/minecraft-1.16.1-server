import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public interface bqc extends bps, bqd, bqf {
	default float aa() {
		return cif.b[this.m().c(this.u_().e())];
	}

	default float f(float float1) {
		return this.m().b(this.u_().e());
	}

	bqr<bvr> G();

	bqr<cwz> F();

	bqb n();

	dab u_();

	ane d(fu fu);

	default and ac() {
		return this.u_().r();
	}

	chb E();

	@Override
	default boolean b(int integer1, int integer2) {
		return this.E().b(integer1, integer2);
	}

	Random v_();

	default void a(fu fu, bvr bvr) {
	}

	void a(@Nullable bec bec, fu fu, ack ack, acm acm, float float5, float float6);

	void a(hf hf, double double2, double double3, double double4, double double5, double double6, double double7);

	void a(@Nullable bec bec, int integer2, fu fu, int integer4);

	default int ad() {
		return this.m().m();
	}

	default void c(int integer1, fu fu, int integer3) {
		this.a(null, integer1, fu, integer3);
	}

	@Override
	default Stream<dfg> c(@Nullable aom aom, deg deg, Predicate<aom> predicate) {
		return bps.super.c(aom, deg, predicate);
	}

	@Override
	default boolean a(@Nullable aom aom, dfg dfg) {
		return bps.super.a(aom, dfg);
	}

	@Override
	default fu a(cio.a a, fu fu) {
		return bqd.super.a(a, fu);
	}
}
