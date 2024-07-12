import java.util.stream.Stream;
import javax.annotation.Nullable;

public interface bqd extends bpe, bpk, brg.a {
	@Nullable
	cgy a(int integer1, int integer2, chc chc, boolean boolean4);

	@Deprecated
	boolean b(int integer1, int integer2);

	int a(cio.a a, int integer2, int integer3);

	int c();

	brg d();

	default bre v(fu fu) {
		return this.d().a(fu);
	}

	default Stream<cfj> c(deg deg) {
		int integer3 = aec.c(deg.a);
		int integer4 = aec.c(deg.d);
		int integer5 = aec.c(deg.b);
		int integer6 = aec.c(deg.e);
		int integer7 = aec.c(deg.c);
		int integer8 = aec.c(deg.f);
		return this.a(integer3, integer5, integer7, integer4, integer6, integer8) ? this.a(deg) : Stream.empty();
	}

	@Override
	default bre b(int integer1, int integer2, int integer3) {
		cgy cgy5 = this.a(integer1 >> 2, integer3 >> 2, chc.d, false);
		return cgy5 != null && cgy5.i() != null ? cgy5.i().b(integer1, integer2, integer3) : this.a(integer1, integer2, integer3);
	}

	bre a(int integer1, int integer2, int integer3);

	boolean s_();

	@Deprecated
	int t_();

	cif m();

	default fu a(cio.a a, fu fu) {
		return new fu(fu.u(), this.a(a, fu.u(), fu.w()), fu.w());
	}

	default boolean w(fu fu) {
		return this.d_(fu).g();
	}

	default boolean x(fu fu) {
		if (fu.v() >= this.t_()) {
			return this.f(fu);
		} else {
			fu fu3 = new fu(fu.u(), this.t_(), fu.w());
			if (!this.f(fu3)) {
				return false;
			} else {
				for (fu var4 = fu3.c(); var4.v() > fu.v(); var4 = var4.c()) {
					cfj cfj4 = this.d_(var4);
					if (cfj4.b(this, var4) > 0 && !cfj4.c().a()) {
						return false;
					}
				}

				return true;
			}
		}
	}

	@Deprecated
	default float y(fu fu) {
		return this.m().a(this.B(fu));
	}

	default int c(fu fu, fz fz) {
		return this.d_(fu).c(this, fu, fz);
	}

	default cgy z(fu fu) {
		return this.a(fu.u() >> 4, fu.w() >> 4);
	}

	default cgy a(int integer1, int integer2) {
		return this.a(integer1, integer2, chc.m, true);
	}

	default cgy a(int integer1, int integer2, chc chc) {
		return this.a(integer1, integer2, chc, true);
	}

	@Nullable
	@Override
	default bpg c(int integer1, int integer2) {
		return this.a(integer1, integer2, chc.a, false);
	}

	default boolean A(fu fu) {
		return this.b(fu).a(acz.a);
	}

	default boolean d(deg deg) {
		int integer3 = aec.c(deg.a);
		int integer4 = aec.f(deg.d);
		int integer5 = aec.c(deg.b);
		int integer6 = aec.f(deg.e);
		int integer7 = aec.c(deg.c);
		int integer8 = aec.f(deg.f);
		fu.a a9 = new fu.a();

		for (int integer10 = integer3; integer10 < integer4; integer10++) {
			for (int integer11 = integer5; integer11 < integer6; integer11++) {
				for (int integer12 = integer7; integer12 < integer8; integer12++) {
					cfj cfj13 = this.d_(a9.d(integer10, integer11, integer12));
					if (!cfj13.m().c()) {
						return true;
					}
				}
			}
		}

		return false;
	}

	default int B(fu fu) {
		return this.c(fu, this.c());
	}

	default int c(fu fu, int integer) {
		return fu.u() >= -30000000 && fu.w() >= -30000000 && fu.u() < 30000000 && fu.w() < 30000000 ? this.b(fu, integer) : 15;
	}

	@Deprecated
	default boolean C(fu fu) {
		return this.b(fu.u() >> 4, fu.w() >> 4);
	}

	@Deprecated
	default boolean a(fu fu1, fu fu2) {
		return this.a(fu1.u(), fu1.v(), fu1.w(), fu2.u(), fu2.v(), fu2.w());
	}

	@Deprecated
	default boolean a(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
		if (integer5 >= 0 && integer2 < 256) {
			integer1 >>= 4;
			integer3 >>= 4;
			integer4 >>= 4;
			integer6 >>= 4;

			for (int integer8 = integer1; integer8 <= integer4; integer8++) {
				for (int integer9 = integer3; integer9 <= integer6; integer9++) {
					if (!this.b(integer8, integer9)) {
						return false;
					}
				}
			}

			return true;
		} else {
			return false;
		}
	}
}
