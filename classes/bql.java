import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class bql implements bpg, bpk {
	protected final int a;
	protected final int b;
	protected final cgy[][] c;
	protected boolean d;
	protected final bqb e;

	public bql(bqb bqb, fu fu2, fu fu3) {
		this.e = bqb;
		this.a = fu2.u() >> 4;
		this.b = fu2.w() >> 4;
		int integer5 = fu3.u() >> 4;
		int integer6 = fu3.w() >> 4;
		this.c = new cgy[integer5 - this.a + 1][integer6 - this.b + 1];
		chb chb7 = bqb.E();
		this.d = true;

		for (int integer8 = this.a; integer8 <= integer5; integer8++) {
			for (int integer9 = this.b; integer9 <= integer6; integer9++) {
				this.c[integer8 - this.a][integer9 - this.b] = chb7.a(integer8, integer9);
			}
		}

		for (int integer8 = fu2.u() >> 4; integer8 <= fu3.u() >> 4; integer8++) {
			for (int integer9 = fu2.w() >> 4; integer9 <= fu3.w() >> 4; integer9++) {
				cgy cgy10 = this.c[integer8 - this.a][integer9 - this.b];
				if (cgy10 != null && !cgy10.a(fu2.v(), fu3.v())) {
					this.d = false;
					return;
				}
			}
		}
	}

	private cgy d(fu fu) {
		return this.a(fu.u() >> 4, fu.w() >> 4);
	}

	private cgy a(int integer1, int integer2) {
		int integer4 = integer1 - this.a;
		int integer5 = integer2 - this.b;
		if (integer4 >= 0 && integer4 < this.c.length && integer5 >= 0 && integer5 < this.c[integer4].length) {
			cgy cgy6 = this.c[integer4][integer5];
			return (cgy)(cgy6 != null ? cgy6 : new che(this.e, new bph(integer1, integer2)));
		} else {
			return new che(this.e, new bph(integer1, integer2));
		}
	}

	@Override
	public cgw f() {
		return this.e.f();
	}

	@Override
	public bpg c(int integer1, int integer2) {
		return this.a(integer1, integer2);
	}

	@Nullable
	@Override
	public cdl c(fu fu) {
		cgy cgy3 = this.d(fu);
		return cgy3.c(fu);
	}

	@Override
	public cfj d_(fu fu) {
		if (bqb.l(fu)) {
			return bvs.a.n();
		} else {
			cgy cgy3 = this.d(fu);
			return cgy3.d_(fu);
		}
	}

	@Override
	public Stream<dfg> c(@Nullable aom aom, deg deg, Predicate<aom> predicate) {
		return Stream.empty();
	}

	@Override
	public Stream<dfg> d(@Nullable aom aom, deg deg, Predicate<aom> predicate) {
		return this.b(aom, deg);
	}

	@Override
	public cxa b(fu fu) {
		if (bqb.l(fu)) {
			return cxb.a.h();
		} else {
			cgy cgy3 = this.d(fu);
			return cgy3.b(fu);
		}
	}
}
