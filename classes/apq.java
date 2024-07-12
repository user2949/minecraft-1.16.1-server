import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;

public abstract class apq extends ayk {
	protected static final tq<Byte> bv = tt.a(apq.class, ts.a);
	protected static final tq<Optional<UUID>> bw = tt.a(apq.class, ts.o);
	private boolean bx;

	protected apq(aoq<? extends apq> aoq, bqb bqb) {
		super(aoq, bqb);
		this.eM();
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(bv, (byte)0);
		this.S.a(bw, Optional.empty());
	}

	@Override
	public void b(le le) {
		super.b(le);
		if (this.B_() != null) {
			le.a("Owner", this.B_());
		}

		le.a("Sitting", this.bx);
	}

	@Override
	public void a(le le) {
		super.a(le);
		UUID uUID3;
		if (le.b("Owner")) {
			uUID3 = le.a("Owner");
		} else {
			String string4 = le.l("Owner");
			uUID3 = abo.a(this.cg(), string4);
		}

		if (uUID3 != null) {
			try {
				this.b(uUID3);
				this.u(true);
			} catch (Throwable var4) {
				this.u(false);
			}
		}

		this.bx = le.q("Sitting");
		this.v(this.bx);
	}

	@Override
	public boolean a(bec bec) {
		return !this.eC();
	}

	public boolean eL() {
		return (this.S.a(bv) & 4) != 0;
	}

	public void u(boolean boolean1) {
		byte byte3 = this.S.a(bv);
		if (boolean1) {
			this.S.b(bv, (byte)(byte3 | 4));
		} else {
			this.S.b(bv, (byte)(byte3 & -5));
		}

		this.eM();
	}

	protected void eM() {
	}

	public boolean eN() {
		return (this.S.a(bv) & 1) != 0;
	}

	public void v(boolean boolean1) {
		byte byte3 = this.S.a(bv);
		if (boolean1) {
			this.S.b(bv, (byte)(byte3 | 1));
		} else {
			this.S.b(bv, (byte)(byte3 & -2));
		}
	}

	@Nullable
	public UUID B_() {
		return (UUID)this.S.a(bw).orElse(null);
	}

	public void b(@Nullable UUID uUID) {
		this.S.b(bw, Optional.ofNullable(uUID));
	}

	public void f(bec bec) {
		this.u(true);
		this.b(bec.bR());
		if (bec instanceof ze) {
			aa.x.a((ze)bec, this);
		}
	}

	@Nullable
	public aoy eO() {
		try {
			UUID uUID2 = this.B_();
			return uUID2 == null ? null : this.l.b(uUID2);
		} catch (IllegalArgumentException var2) {
			return null;
		}
	}

	@Override
	public boolean d(aoy aoy) {
		return this.j(aoy) ? false : super.d(aoy);
	}

	public boolean j(aoy aoy) {
		return aoy == this.eO();
	}

	public boolean a(aoy aoy1, aoy aoy2) {
		return true;
	}

	@Override
	public dfo bC() {
		if (this.eL()) {
			aoy aoy2 = this.eO();
			if (aoy2 != null) {
				return aoy2.bC();
			}
		}

		return super.bC();
	}

	@Override
	public boolean r(aom aom) {
		if (this.eL()) {
			aoy aoy3 = this.eO();
			if (aom == aoy3) {
				return true;
			}

			if (aoy3 != null) {
				return aoy3.r(aom);
			}
		}

		return super.r(aom);
	}

	@Override
	public void a(anw anw) {
		if (!this.l.v && this.l.S().b(bpx.l) && this.eO() instanceof ze) {
			this.eO().a(this.du().b(), v.b);
		}

		super.a(anw);
	}

	public boolean eP() {
		return this.bx;
	}

	public void w(boolean boolean1) {
		this.bx = boolean1;
	}
}
