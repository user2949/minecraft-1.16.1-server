import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;

public class bbg extends aom {
	private static final tq<bki> c = tt.a(bbg.class, ts.g);
	private int d;
	private int e;
	private int f = 5;
	private UUID g;
	private UUID an;
	public final float b = (float)(Math.random() * Math.PI * 2.0);

	public bbg(aoq<? extends bbg> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public bbg(bqb bqb, double double2, double double3, double double4) {
		this(aoq.L, bqb);
		this.d(double2, double3, double4);
		this.p = this.J.nextFloat() * 360.0F;
		this.m(this.J.nextDouble() * 0.2 - 0.1, 0.2, this.J.nextDouble() * 0.2 - 0.1);
	}

	public bbg(bqb bqb, double double2, double double3, double double4, bki bki) {
		this(bqb, double2, double3, double4);
		this.b(bki);
	}

	@Override
	protected boolean ax() {
		return false;
	}

	@Override
	protected void e() {
		this.Y().a(c, bki.b);
	}

	@Override
	public void j() {
		if (this.g().a()) {
			this.aa();
		} else {
			super.j();
			if (this.e > 0 && this.e != 32767) {
				this.e--;
			}

			this.m = this.cC();
			this.n = this.cD();
			this.o = this.cG();
			dem dem2 = this.cB();
			if (this.a(acz.a)) {
				this.u();
			} else if (this.a(acz.b)) {
				this.v();
			} else if (!this.aw()) {
				this.e(this.cB().b(0.0, -0.04, 0.0));
			}

			if (this.l.v) {
				this.H = false;
			} else {
				this.H = !this.l.j(this);
				if (this.H) {
					this.k(this.cC(), (this.cb().b + this.cb().e) / 2.0, this.cG());
				}
			}

			if (!this.t || b(this.cB()) > 1.0E-5F || (this.K + this.V()) % 4 == 0) {
				this.a(apd.SELF, this.cB());
				float float3 = 0.98F;
				if (this.t) {
					float3 = this.l.d_(new fu(this.cC(), this.cD() - 1.0, this.cG())).b().j() * 0.98F;
				}

				this.e(this.cB().d((double)float3, 0.98, (double)float3));
				if (this.t) {
					this.e(this.cB().d(1.0, -0.5, 1.0));
				}
			}

			boolean boolean3 = aec.c(this.m) != aec.c(this.cC()) || aec.c(this.n) != aec.c(this.cD()) || aec.c(this.o) != aec.c(this.cG());
			int integer4 = boolean3 ? 2 : 40;
			if (this.K % integer4 == 0) {
				if (this.l.b(this.cA()).a(acz.b) && !this.az()) {
					this.a(acl.eH, 0.4F, 2.0F + this.J.nextFloat() * 0.4F);
				}

				if (!this.l.v && this.z()) {
					this.x();
				}
			}

			if (this.d != -32768) {
				this.d++;
			}

			this.ad = this.ad | this.aG();
			if (!this.l.v) {
				double double5 = this.cB().d(dem2).g();
				if (double5 > 0.01) {
					this.ad = true;
				}
			}

			if (!this.l.v && this.d >= 6000) {
				this.aa();
			}
		}
	}

	private void u() {
		dem dem2 = this.cB();
		this.m(dem2.b * 0.99F, dem2.c + (double)(dem2.c < 0.06F ? 5.0E-4F : 0.0F), dem2.d * 0.99F);
	}

	private void v() {
		dem dem2 = this.cB();
		this.m(dem2.b * 0.95F, dem2.c + (double)(dem2.c < 0.06F ? 5.0E-4F : 0.0F), dem2.d * 0.95F);
	}

	private void x() {
		if (this.z()) {
			for (bbg bbg4 : this.l.a(bbg.class, this.cb().c(0.5, 0.0, 0.5), bbg -> bbg != this && bbg.z())) {
				if (bbg4.z()) {
					this.a(bbg4);
					if (this.y) {
						break;
					}
				}
			}
		}
	}

	private boolean z() {
		bki bki2 = this.g();
		return this.aU() && this.e != 32767 && this.d != -32768 && this.d < 6000 && bki2.E() < bki2.c();
	}

	private void a(bbg bbg) {
		bki bki3 = this.g();
		bki bki4 = bbg.g();
		if (Objects.equals(this.h(), bbg.h()) && a(bki3, bki4)) {
			if (bki4.E() < bki3.E()) {
				a(this, bki3, bbg, bki4);
			} else {
				a(bbg, bki4, this, bki3);
			}
		}
	}

	public static boolean a(bki bki1, bki bki2) {
		if (bki2.b() != bki1.b()) {
			return false;
		} else if (bki2.E() + bki1.E() > bki2.c()) {
			return false;
		} else {
			return bki2.n() ^ bki1.n() ? false : !bki2.n() || bki2.o().equals(bki1.o());
		}
	}

	public static bki a(bki bki1, bki bki2, int integer) {
		int integer4 = Math.min(Math.min(bki1.c(), integer) - bki1.E(), bki2.E());
		bki bki5 = bki1.i();
		bki5.f(integer4);
		bki2.g(integer4);
		return bki5;
	}

	private static void a(bbg bbg, bki bki2, bki bki3) {
		bki bki4 = a(bki2, bki3, 64);
		bbg.b(bki4);
	}

	private static void a(bbg bbg1, bki bki2, bbg bbg3, bki bki4) {
		a(bbg1, bki2, bki4);
		bbg1.e = Math.max(bbg1.e, bbg3.e);
		bbg1.d = Math.min(bbg1.d, bbg3.d);
		if (bki4.a()) {
			bbg3.aa();
		}
	}

	@Override
	public boolean az() {
		return this.g().b().u() || super.az();
	}

	@Override
	public boolean a(anw anw, float float2) {
		if (this.b(anw)) {
			return false;
		} else if (!this.g().a() && this.g().b() == bkk.pl && anw.d()) {
			return false;
		} else if (!this.g().b().a(anw)) {
			return false;
		} else {
			this.aP();
			this.f = (int)((float)this.f - float2);
			if (this.f <= 0) {
				this.aa();
			}

			return false;
		}
	}

	@Override
	public void b(le le) {
		le.a("Health", (short)this.f);
		le.a("Age", (short)this.d);
		le.a("PickupDelay", (short)this.e);
		if (this.i() != null) {
			le.a("Thrower", this.i());
		}

		if (this.h() != null) {
			le.a("Owner", this.h());
		}

		if (!this.g().a()) {
			le.a("Item", this.g().b(new le()));
		}
	}

	@Override
	public void a(le le) {
		this.f = le.g("Health");
		this.d = le.g("Age");
		if (le.e("PickupDelay")) {
			this.e = le.g("PickupDelay");
		}

		if (le.b("Owner")) {
			this.an = le.a("Owner");
		}

		if (le.b("Thrower")) {
			this.g = le.a("Thrower");
		}

		le le3 = le.p("Item");
		this.b(bki.a(le3));
		if (this.g().a()) {
			this.aa();
		}
	}

	@Override
	public void a_(bec bec) {
		if (!this.l.v) {
			bki bki3 = this.g();
			bke bke4 = bki3.b();
			int integer5 = bki3.E();
			if (this.e == 0 && (this.an == null || this.an.equals(bec.bR())) && bec.bt.e(bki3)) {
				bec.a(this, integer5);
				if (bki3.a()) {
					this.aa();
					bki3.e(integer5);
				}

				bec.a(acu.e.b(bke4), integer5);
				bec.a(this);
			}
		}
	}

	@Override
	public mr P() {
		mr mr2 = this.R();
		return (mr)(mr2 != null ? mr2 : new ne(this.g().j()));
	}

	@Override
	public boolean bH() {
		return false;
	}

	@Nullable
	@Override
	public aom a(zd zd) {
		aom aom3 = super.a(zd);
		if (!this.l.v && aom3 instanceof bbg) {
			((bbg)aom3).x();
		}

		return aom3;
	}

	public bki g() {
		return this.Y().a(c);
	}

	public void b(bki bki) {
		this.Y().b(c, bki);
	}

	@Override
	public void a(tq<?> tq) {
		super.a(tq);
		if (c.equals(tq)) {
			this.g().a(this);
		}
	}

	@Nullable
	public UUID h() {
		return this.an;
	}

	public void b(@Nullable UUID uUID) {
		this.an = uUID;
	}

	@Nullable
	public UUID i() {
		return this.g;
	}

	public void c(@Nullable UUID uUID) {
		this.g = uUID;
	}

	public void m() {
		this.e = 10;
	}

	@Override
	public void n() {
		this.e = 0;
	}

	public void o() {
		this.e = 32767;
	}

	public void a(int integer) {
		this.e = integer;
	}

	public boolean p() {
		return this.e > 0;
	}

	public void r() {
		this.d = -6000;
	}

	public void s() {
		this.o();
		this.d = 5999;
	}

	@Override
	public ni<?> O() {
		return new nm(this);
	}
}
