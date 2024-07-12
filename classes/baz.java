import java.util.function.Predicate;
import javax.annotation.Nullable;
import org.apache.commons.lang3.Validate;

public abstract class baz extends aom {
	protected static final Predicate<aom> b = aom -> aom instanceof baz;
	private int e;
	protected fu c;
	protected fz d = fz.SOUTH;

	protected baz(aoq<? extends baz> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	protected baz(aoq<? extends baz> aoq, bqb bqb, fu fu) {
		this(aoq, bqb);
		this.c = fu;
	}

	@Override
	protected void e() {
	}

	protected void a(fz fz) {
		Validate.notNull(fz);
		Validate.isTrue(fz.n().d());
		this.d = fz;
		this.p = (float)(this.d.d() * 90);
		this.r = this.p;
		this.g();
	}

	protected void g() {
		if (this.d != null) {
			double double2 = (double)this.c.u() + 0.5;
			double double4 = (double)this.c.v() + 0.5;
			double double6 = (double)this.c.w() + 0.5;
			double double8 = 0.46875;
			double double10 = this.a(this.i());
			double double12 = this.a(this.k());
			double2 -= (double)this.d.i() * 0.46875;
			double6 -= (double)this.d.k() * 0.46875;
			double4 += double12;
			fz fz14 = this.d.h();
			double2 += double10 * (double)fz14.i();
			double6 += double10 * (double)fz14.k();
			this.n(double2, double4, double6);
			double double15 = (double)this.i();
			double double17 = (double)this.k();
			double double19 = (double)this.i();
			if (this.d.n() == fz.a.Z) {
				double19 = 1.0;
			} else {
				double15 = 1.0;
			}

			double15 /= 32.0;
			double17 /= 32.0;
			double19 /= 32.0;
			this.a(new deg(double2 - double15, double4 - double17, double6 - double19, double2 + double15, double4 + double17, double6 + double19));
		}
	}

	private double a(int integer) {
		return integer % 32 == 0 ? 0.5 : 0.0;
	}

	@Override
	public void j() {
		if (!this.l.v) {
			if (this.cD() < -64.0) {
				this.ai();
			}

			if (this.e++ == 100) {
				this.e = 0;
				if (!this.y && !this.h()) {
					this.aa();
					this.a(null);
				}
			}
		}
	}

	public boolean h() {
		if (!this.l.j(this)) {
			return false;
		} else {
			int integer2 = Math.max(1, this.i() / 16);
			int integer3 = Math.max(1, this.k() / 16);
			fu fu4 = this.c.a(this.d.f());
			fz fz5 = this.d.h();
			fu.a a6 = new fu.a();

			for (int integer7 = 0; integer7 < integer2; integer7++) {
				for (int integer8 = 0; integer8 < integer3; integer8++) {
					int integer9 = (integer2 - 1) / -2;
					int integer10 = (integer3 - 1) / -2;
					a6.g(fu4).c(fz5, integer7 + integer9).c(fz.UP, integer8 + integer10);
					cfj cfj11 = this.l.d_(a6);
					if (!cfj11.c().b() && !bxb.l(cfj11)) {
						return false;
					}
				}
			}

			return this.l.a(this, this.cb(), b).isEmpty();
		}
	}

	@Override
	public boolean aQ() {
		return true;
	}

	@Override
	public boolean t(aom aom) {
		if (aom instanceof bec) {
			bec bec3 = (bec)aom;
			return !this.l.a(bec3, this.c) ? true : this.a(anw.a(bec3), 0.0F);
		} else {
			return false;
		}
	}

	@Override
	public fz bY() {
		return this.d;
	}

	@Override
	public boolean a(anw anw, float float2) {
		if (this.b(anw)) {
			return false;
		} else {
			if (!this.y && !this.l.v) {
				this.aa();
				this.aP();
				this.a(anw.k());
			}

			return true;
		}
	}

	@Override
	public void a(apd apd, dem dem) {
		if (!this.l.v && !this.y && dem.g() > 0.0) {
			this.aa();
			this.a(null);
		}
	}

	@Override
	public void h(double double1, double double2, double double3) {
		if (!this.l.v && !this.y && double1 * double1 + double2 * double2 + double3 * double3 > 0.0) {
			this.aa();
			this.a(null);
		}
	}

	@Override
	public void b(le le) {
		le.a("Facing", (byte)this.d.d());
		fu fu3 = this.n();
		le.b("TileX", fu3.u());
		le.b("TileY", fu3.v());
		le.b("TileZ", fu3.w());
	}

	@Override
	public void a(le le) {
		this.c = new fu(le.h("TileX"), le.h("TileY"), le.h("TileZ"));
		this.d = fz.b(le.f("Facing"));
	}

	public abstract int i();

	public abstract int k();

	public abstract void a(@Nullable aom aom);

	public abstract void m();

	@Override
	public bbg a(bki bki, float float2) {
		bbg bbg4 = new bbg(this.l, this.cC() + (double)((float)this.d.i() * 0.15F), this.cD() + (double)float2, this.cG() + (double)((float)this.d.k() * 0.15F), bki);
		bbg4.m();
		this.l.c(bbg4);
		return bbg4;
	}

	@Override
	protected boolean aS() {
		return false;
	}

	@Override
	public void d(double double1, double double2, double double3) {
		this.c = new fu(double1, double2, double3);
		this.g();
		this.ad = true;
	}

	public fu n() {
		return this.c;
	}

	@Override
	public float a(cap cap) {
		if (this.d.n() != fz.a.Y) {
			switch (cap) {
				case CLOCKWISE_180:
					this.d = this.d.f();
					break;
				case COUNTERCLOCKWISE_90:
					this.d = this.d.h();
					break;
				case CLOCKWISE_90:
					this.d = this.d.g();
			}
		}

		float float3 = aec.g(this.p);
		switch (cap) {
			case CLOCKWISE_180:
				return float3 + 180.0F;
			case COUNTERCLOCKWISE_90:
				return float3 + 90.0F;
			case CLOCKWISE_90:
				return float3 + 270.0F;
			default:
				return float3;
		}
	}

	@Override
	public float a(bzj bzj) {
		return this.a(bzj.a(this.d));
	}

	@Override
	public void a(aox aox) {
	}

	@Override
	public void y_() {
	}
}
