import com.google.common.collect.ImmutableSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public abstract class awv {
	protected final aoz a;
	protected final bqb b;
	@Nullable
	protected czf c;
	protected double d;
	protected int e;
	protected int f;
	protected dem g = dem.a;
	protected gr h = gr.d;
	protected long i;
	protected long j;
	protected double k;
	protected float l = 0.5F;
	protected boolean m;
	protected long n;
	protected cze o;
	private fu p;
	private int q;
	private float r = 1.0F;
	private final czh s;

	public awv(aoz aoz, bqb bqb) {
		this.a = aoz;
		this.b = bqb;
		int integer4 = aec.c(aoz.b(apx.b) * 16.0);
		this.s = this.a(integer4);
	}

	public void g() {
		this.r = 1.0F;
	}

	public void a(float float1) {
		this.r = float1;
	}

	public fu h() {
		return this.p;
	}

	protected abstract czh a(int integer);

	public void a(double double1) {
		this.d = double1;
	}

	public boolean i() {
		return this.m;
	}

	public void j() {
		if (this.b.Q() - this.n > 20L) {
			if (this.p != null) {
				this.c = null;
				this.c = this.a(this.p, this.q);
				this.n = this.b.Q();
				this.m = false;
			}
		} else {
			this.m = true;
		}
	}

	@Nullable
	public final czf a(double double1, double double2, double double3, int integer) {
		return this.a(new fu(double1, double2, double3), integer);
	}

	@Nullable
	public czf a(Stream<fu> stream, int integer) {
		return this.a((Set<fu>)stream.collect(Collectors.toSet()), 8, false, integer);
	}

	@Nullable
	public czf a(Set<fu> set, int integer) {
		return this.a(set, 8, false, integer);
	}

	@Nullable
	public czf a(fu fu, int integer) {
		return this.a(ImmutableSet.of(fu), 8, false, integer);
	}

	@Nullable
	public czf a(aom aom, int integer) {
		return this.a(ImmutableSet.of(aom.cA()), 16, true, integer);
	}

	@Nullable
	protected czf a(Set<fu> set, int integer2, boolean boolean3, int integer4) {
		if (set.isEmpty()) {
			return null;
		} else if (this.a.cD() < 0.0) {
			return null;
		} else if (!this.a()) {
			return null;
		} else if (this.c != null && !this.c.b() && set.contains(this.p)) {
			return this.c;
		} else {
			this.b.X().a("pathfind");
			float float6 = (float)this.a.b(apx.b);
			fu fu7 = boolean3 ? this.a.cA().b() : this.a.cA();
			int integer8 = (int)(float6 + (float)integer2);
			bql bql9 = new bql(this.b, fu7.b(-integer8, -integer8, -integer8), fu7.b(integer8, integer8, integer8));
			czf czf10 = this.s.a(bql9, this.a, set, float6, integer4, this.r);
			this.b.X().c();
			if (czf10 != null && czf10.m() != null) {
				this.p = czf10.m();
				this.q = integer4;
				this.e();
			}

			return czf10;
		}
	}

	public boolean a(double double1, double double2, double double3, double double4) {
		return this.a(this.a(double1, double2, double3, 1), double4);
	}

	public boolean a(aom aom, double double2) {
		czf czf5 = this.a(aom, 1);
		return czf5 != null && this.a(czf5, double2);
	}

	public boolean a(@Nullable czf czf, double double2) {
		if (czf == null) {
			this.c = null;
			return false;
		} else {
			if (!czf.a(this.c)) {
				this.c = czf;
			}

			if (this.m()) {
				return false;
			} else {
				this.E_();
				if (this.c.e() <= 0) {
					return false;
				} else {
					this.d = double2;
					dem dem5 = this.b();
					this.f = this.e;
					this.g = dem5;
					return true;
				}
			}
		}
	}

	@Nullable
	public czf k() {
		return this.c;
	}

	public void c() {
		this.e++;
		if (this.m) {
			this.j();
		}

		if (!this.m()) {
			if (this.a()) {
				this.l();
			} else if (this.c != null && this.c.f() < this.c.e()) {
				dem dem2 = this.b();
				dem dem3 = this.c.a(this.a, this.c.f());
				if (dem2.c > dem3.c && !this.a.aj() && aec.c(dem2.b) == aec.c(dem3.b) && aec.c(dem2.d) == aec.c(dem3.d)) {
					this.c.c(this.c.f() + 1);
				}
			}

			qy.a(this.b, this.a, this.c, this.l);
			if (!this.m()) {
				dem dem2 = this.c.a(this.a);
				fu fu3 = new fu(dem2);
				this.a.u().a(dem2.b, this.b.d_(fu3.c()).g() ? dem2.c : czl.a(this.b, fu3), dem2.d, this.d);
			}
		}
	}

	protected void l() {
		dem dem2 = this.b();
		this.l = this.a.cx() > 0.75F ? this.a.cx() / 2.0F : 0.75F - this.a.cx() / 2.0F;
		gr gr3 = this.c.g();
		double double4 = Math.abs(this.a.cC() - ((double)gr3.u() + 0.5));
		double double6 = Math.abs(this.a.cD() - (double)gr3.v());
		double double8 = Math.abs(this.a.cG() - ((double)gr3.w() + 0.5));
		boolean boolean10 = double4 < (double)this.l && double8 < (double)this.l && double6 < 1.0;
		if (boolean10 || this.a.b(this.c.h().l) && this.b(dem2)) {
			this.c.c(this.c.f() + 1);
		}

		this.a(dem2);
	}

	private boolean b(dem dem) {
		if (this.c.e() <= this.c.f() + 1) {
			return false;
		} else {
			dem dem3 = dem.c(this.c.a(this.c.f()).a());
			if (!dem.a(dem3, 2.0)) {
				return false;
			} else {
				dem dem4 = dem.c(this.c.a(this.c.f() + 1).a());
				dem dem5 = dem4.d(dem3);
				dem dem6 = dem.d(dem3);
				return dem5.b(dem6) > 0.0;
			}
		}
	}

	protected void a(dem dem) {
		if (this.e - this.f > 100) {
			if (dem.g(this.g) < 2.25) {
				this.o();
			}

			this.f = this.e;
			this.g = dem;
		}

		if (this.c != null && !this.c.b()) {
			gr gr3 = this.c.g();
			if (gr3.equals(this.h)) {
				this.i = this.i + (v.b() - this.j);
			} else {
				this.h = gr3;
				double double4 = dem.f(dem.c(this.h));
				this.k = this.a.dM() > 0.0F ? double4 / (double)this.a.dM() * 1000.0 : 0.0;
			}

			if (this.k > 0.0 && (double)this.i > this.k * 3.0) {
				this.e();
				this.o();
			}

			this.j = v.b();
		}
	}

	private void e() {
		this.h = gr.d;
		this.i = 0L;
		this.k = 0.0;
	}

	public boolean m() {
		return this.c == null || this.c.b();
	}

	public boolean n() {
		return !this.m();
	}

	public void o() {
		this.c = null;
	}

	protected abstract dem b();

	protected abstract boolean a();

	protected boolean p() {
		return this.a.aD() || this.a.aN();
	}

	protected void E_() {
		if (this.c != null) {
			for (int integer2 = 0; integer2 < this.c.e(); integer2++) {
				czd czd3 = this.c.a(integer2);
				czd czd4 = integer2 + 1 < this.c.e() ? this.c.a(integer2 + 1) : null;
				cfj cfj5 = this.b.d_(new fu(czd3.a, czd3.b, czd3.c));
				if (cfj5.a(bvs.eb)) {
					this.c.a(integer2, czd3.a(czd3.a, czd3.b + 1, czd3.c));
					if (czd4 != null && czd3.b >= czd4.b) {
						this.c.a(integer2 + 1, czd4.a(czd4.a, czd3.b + 1, czd4.c));
					}
				}
			}
		}
	}

	protected abstract boolean a(dem dem1, dem dem2, int integer3, int integer4, int integer5);

	public boolean a(fu fu) {
		fu fu3 = fu.c();
		return this.b.d_(fu3).i(this.b, fu3);
	}

	public cze q() {
		return this.o;
	}

	public void d(boolean boolean1) {
		this.o.c(boolean1);
	}

	public boolean r() {
		return this.o.e();
	}

	public void b(fu fu) {
		if (this.c != null && !this.c.b() && this.c.e() != 0) {
			czd czd3 = this.c.c();
			dem dem4 = new dem(((double)czd3.a + this.a.cC()) / 2.0, ((double)czd3.b + this.a.cD()) / 2.0, ((double)czd3.c + this.a.cG()) / 2.0);
			if (fu.a(dem4, (double)(this.c.e() - this.c.f()))) {
				this.j();
			}
		}
	}
}
