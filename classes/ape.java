import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;

public interface ape {
	int F_();

	void a_(int integer);

	@Nullable
	UUID G_();

	void a(@Nullable UUID uUID);

	void H_();

	default void c(le le) {
		le.b("AngerTime", this.F_());
		if (this.G_() != null) {
			le.a("AngryAt", this.G_());
		}
	}

	default void a(zd zd, le le) {
		this.a_(le.h("AngerTime"));
		if (!le.b("AngryAt")) {
			this.a(null);
		} else {
			UUID uUID4 = le.a("AngryAt");
			this.a(uUID4);
			aom aom5 = zd.a(uUID4);
			if (aom5 != null) {
				if (aom5 instanceof aoz) {
					this.a((aoz)aom5);
				}

				if (aom5.U() == aoq.bb) {
					this.e((bec)aom5);
				}
			}
		}
	}

	default void a(zd zd, boolean boolean2) {
		aoy aoy4 = this.A();
		UUID uUID5 = this.G_();
		if ((aoy4 == null || aoy4.dk()) && uUID5 != null && zd.a(uUID5) instanceof aoz) {
			this.K_();
		} else {
			if (aoy4 != null && !Objects.equals(uUID5, aoy4.bR())) {
				this.a(aoy4.bR());
				this.H_();
			}

			if (this.F_() > 0 && (aoy4 == null || aoy4.U() != aoq.bb || !boolean2)) {
				this.a_(this.F_() - 1);
				if (this.F_() == 0) {
					this.K_();
				}
			}
		}
	}

	default boolean b(aoy aoy) {
		if (!aop.f.test(aoy)) {
			return false;
		} else {
			return aoy.U() == aoq.bb && this.a(aoy.l) ? true : aoy.bR().equals(this.G_());
		}
	}

	default boolean a(bqb bqb) {
		return bqb.S().b(bpx.G) && this.I_() && this.G_() == null;
	}

	default boolean I_() {
		return this.F_() > 0;
	}

	default void b(bec bec) {
		if (bec.l.S().b(bpx.F)) {
			if (bec.bR().equals(this.G_())) {
				this.K_();
			}
		}
	}

	default void J_() {
		this.K_();
		this.H_();
	}

	default void K_() {
		this.a(null);
		this.a(null);
		this.i(null);
		this.a_(0);
	}

	void a(@Nullable aoy aoy);

	void e(@Nullable bec bec);

	void i(@Nullable aoy aoy);

	@Nullable
	aoy A();
}
