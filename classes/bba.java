import javax.annotation.Nullable;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class bba extends baz {
	private static final Logger e = LogManager.getLogger();
	private static final tq<bki> f = tt.a(bba.class, ts.g);
	private static final tq<Integer> g = tt.a(bba.class, ts.b);
	private float an = 1.0F;
	private boolean ao;

	public bba(aoq<? extends bba> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public bba(bqb bqb, fu fu, fz fz) {
		super(aoq.M, bqb, fu);
		this.a(fz);
	}

	@Override
	protected float a(apj apj, aon aon) {
		return 0.0F;
	}

	@Override
	protected void e() {
		this.Y().a(f, bki.b);
		this.Y().a(g, 0);
	}

	@Override
	protected void a(fz fz) {
		Validate.notNull(fz);
		this.d = fz;
		if (fz.n().d()) {
			this.q = 0.0F;
			this.p = (float)(this.d.d() * 90);
		} else {
			this.q = (float)(-90 * fz.e().a());
			this.p = 0.0F;
		}

		this.s = this.q;
		this.r = this.p;
		this.g();
	}

	@Override
	protected void g() {
		if (this.d != null) {
			double double2 = 0.46875;
			double double4 = (double)this.c.u() + 0.5 - (double)this.d.i() * 0.46875;
			double double6 = (double)this.c.v() + 0.5 - (double)this.d.j() * 0.46875;
			double double8 = (double)this.c.w() + 0.5 - (double)this.d.k() * 0.46875;
			this.n(double4, double6, double8);
			double double10 = (double)this.i();
			double double12 = (double)this.k();
			double double14 = (double)this.i();
			fz.a a16 = this.d.n();
			switch (a16) {
				case X:
					double10 = 1.0;
					break;
				case Y:
					double12 = 1.0;
					break;
				case Z:
					double14 = 1.0;
			}

			double10 /= 32.0;
			double12 /= 32.0;
			double14 /= 32.0;
			this.a(new deg(double4 - double10, double6 - double12, double8 - double14, double4 + double10, double6 + double12, double8 + double14));
		}
	}

	@Override
	public boolean h() {
		if (this.ao) {
			return true;
		} else if (!this.l.j(this)) {
			return false;
		} else {
			cfj cfj2 = this.l.d_(this.c.a(this.d.f()));
			return cfj2.c().b() || this.d.n().d() && bxb.l(cfj2) ? this.l.a(this, this.cb(), b).isEmpty() : false;
		}
	}

	@Override
	public void a(apd apd, dem dem) {
		if (!this.ao) {
			super.a(apd, dem);
		}
	}

	@Override
	public void h(double double1, double double2, double double3) {
		if (!this.ao) {
			super.h(double1, double2, double3);
		}
	}

	@Override
	public float bc() {
		return 0.0F;
	}

	@Override
	public void X() {
		this.c(this.o());
		super.X();
	}

	@Override
	public boolean a(anw anw, float float2) {
		if (this.ao) {
			return anw != anw.m && !anw.v() ? false : super.a(anw, float2);
		} else if (this.b(anw)) {
			return false;
		} else if (!anw.d() && !this.o().a()) {
			if (!this.l.v) {
				this.b(anw.k(), false);
				this.a(acl.gI, 1.0F, 1.0F);
			}

			return true;
		} else {
			return super.a(anw, float2);
		}
	}

	@Override
	public int i() {
		return 12;
	}

	@Override
	public int k() {
		return 12;
	}

	@Override
	public void a(@Nullable aom aom) {
		this.a(acl.gG, 1.0F, 1.0F);
		this.b(aom, true);
	}

	@Override
	public void m() {
		this.a(acl.gH, 1.0F, 1.0F);
	}

	private void b(@Nullable aom aom, boolean boolean2) {
		if (!this.ao) {
			if (!this.l.S().b(bpx.g)) {
				if (aom == null) {
					this.c(this.o());
				}
			} else {
				bki bki4 = this.o();
				this.b(bki.b);
				if (aom instanceof bec) {
					bec bec5 = (bec)aom;
					if (bec5.bJ.d) {
						this.c(bki4);
						return;
					}
				}

				if (boolean2) {
					this.a(bkk.oV);
				}

				if (!bki4.a()) {
					bki4 = bki4.i();
					this.c(bki4);
					if (this.J.nextFloat() < this.an) {
						this.a(bki4);
					}
				}
			}
		}
	}

	private void c(bki bki) {
		if (bki.b() == bkk.nf) {
			czv czv3 = bko.b(bki, this.l);
			czv3.a(this.c, this.V());
			czv3.a(true);
		}

		bki.a(null);
	}

	public bki o() {
		return this.Y().a(f);
	}

	public void b(bki bki) {
		this.a(bki, true);
	}

	public void a(bki bki, boolean boolean2) {
		if (!bki.a()) {
			bki = bki.i();
			bki.e(1);
			bki.a(this);
		}

		this.Y().b(f, bki);
		if (!bki.a()) {
			this.a(acl.gF, 1.0F, 1.0F);
		}

		if (boolean2 && this.c != null) {
			this.l.c(this.c, bvs.a);
		}
	}

	@Override
	public boolean a_(int integer, bki bki) {
		if (integer == 0) {
			this.b(bki);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void a(tq<?> tq) {
		if (tq.equals(f)) {
			bki bki3 = this.o();
			if (!bki3.a() && bki3.z() != this) {
				bki3.a(this);
			}
		}
	}

	public int p() {
		return this.Y().a(g);
	}

	public void a(int integer) {
		this.a(integer, true);
	}

	private void a(int integer, boolean boolean2) {
		this.Y().b(g, integer % 8);
		if (boolean2 && this.c != null) {
			this.l.c(this.c, bvs.a);
		}
	}

	@Override
	public void b(le le) {
		super.b(le);
		if (!this.o().a()) {
			le.a("Item", this.o().b(new le()));
			le.a("ItemRotation", (byte)this.p());
			le.a("ItemDropChance", this.an);
		}

		le.a("Facing", (byte)this.d.c());
		le.a("Invisible", this.bB());
		le.a("Fixed", this.ao);
	}

	@Override
	public void a(le le) {
		super.a(le);
		le le3 = le.p("Item");
		if (le3 != null && !le3.isEmpty()) {
			bki bki4 = bki.a(le3);
			if (bki4.a()) {
				e.warn("Unable to load item from: {}", le3);
			}

			bki bki5 = this.o();
			if (!bki5.a() && !bki.b(bki4, bki5)) {
				this.c(bki5);
			}

			this.a(bki4, false);
			this.a(le.f("ItemRotation"), false);
			if (le.c("ItemDropChance", 99)) {
				this.an = le.j("ItemDropChance");
			}
		}

		this.a(fz.a(le.f("Facing")));
		this.j(le.q("Invisible"));
		this.ao = le.q("Fixed");
	}

	@Override
	public ang a(bec bec, anf anf) {
		bki bki4 = bec.b(anf);
		boolean boolean5 = !this.o().a();
		boolean boolean6 = !bki4.a();
		if (this.ao) {
			return ang.PASS;
		} else if (!this.l.v) {
			if (!boolean5) {
				if (boolean6 && !this.y) {
					this.b(bki4);
					if (!bec.bJ.d) {
						bki4.g(1);
					}
				}
			} else {
				this.a(acl.gJ, 1.0F, 1.0F);
				this.a(this.p() + 1);
			}

			return ang.CONSUME;
		} else {
			return !boolean5 && !boolean6 ? ang.PASS : ang.SUCCESS;
		}
	}

	public int q() {
		return this.o().a() ? 0 : this.p() % 8 + 1;
	}

	@Override
	public ni<?> O() {
		return new nm(this, this.U(), this.d.c(), this.n());
	}
}
