import com.google.common.collect.Lists;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;

public class beu extends bes {
	private aom b;
	@Nullable
	private fz c;
	private int d;
	private double e;
	private double f;
	private double g;
	@Nullable
	private UUID an;

	public beu(aoq<? extends beu> aoq, bqb bqb) {
		super(aoq, bqb);
		this.H = true;
	}

	public beu(bqb bqb, aoy aoy, aom aom, fz.a a) {
		this(aoq.as, bqb);
		this.b(aoy);
		fu fu6 = aoy.cA();
		double double7 = (double)fu6.u() + 0.5;
		double double9 = (double)fu6.v() + 0.5;
		double double11 = (double)fu6.w() + 0.5;
		this.b(double7, double9, double11, this.p, this.q);
		this.b = aom;
		this.c = fz.UP;
		this.a(a);
	}

	@Override
	public acm ct() {
		return acm.HOSTILE;
	}

	@Override
	protected void b(le le) {
		super.b(le);
		if (this.b != null) {
			le.a("Target", this.b.bR());
		}

		if (this.c != null) {
			le.b("Dir", this.c.c());
		}

		le.b("Steps", this.d);
		le.a("TXD", this.e);
		le.a("TYD", this.f);
		le.a("TZD", this.g);
	}

	@Override
	protected void a(le le) {
		super.a(le);
		this.d = le.h("Steps");
		this.e = le.k("TXD");
		this.f = le.k("TYD");
		this.g = le.k("TZD");
		if (le.c("Dir", 99)) {
			this.c = fz.a(le.h("Dir"));
		}

		if (le.b("Target")) {
			this.an = le.a("Target");
		}
	}

	@Override
	protected void e() {
	}

	private void a(@Nullable fz fz) {
		this.c = fz;
	}

	private void a(@Nullable fz.a a) {
		double double4 = 0.5;
		fu fu3;
		if (this.b == null) {
			fu3 = this.cA().c();
		} else {
			double4 = (double)this.b.cy() * 0.5;
			fu3 = new fu(this.b.cC(), this.b.cD() + double4, this.b.cG());
		}

		double double6 = (double)fu3.u() + 0.5;
		double double8 = (double)fu3.v() + double4;
		double double10 = (double)fu3.w() + 0.5;
		fz fz12 = null;
		if (!fu3.a(this.cz(), 2.0)) {
			fu fu13 = this.cA();
			List<fz> list14 = Lists.<fz>newArrayList();
			if (a != fz.a.X) {
				if (fu13.u() < fu3.u() && this.l.w(fu13.g())) {
					list14.add(fz.EAST);
				} else if (fu13.u() > fu3.u() && this.l.w(fu13.f())) {
					list14.add(fz.WEST);
				}
			}

			if (a != fz.a.Y) {
				if (fu13.v() < fu3.v() && this.l.w(fu13.b())) {
					list14.add(fz.UP);
				} else if (fu13.v() > fu3.v() && this.l.w(fu13.c())) {
					list14.add(fz.DOWN);
				}
			}

			if (a != fz.a.Z) {
				if (fu13.w() < fu3.w() && this.l.w(fu13.e())) {
					list14.add(fz.SOUTH);
				} else if (fu13.w() > fu3.w() && this.l.w(fu13.d())) {
					list14.add(fz.NORTH);
				}
			}

			fz12 = fz.a(this.J);
			if (list14.isEmpty()) {
				for (int integer15 = 5; !this.l.w(fu13.a(fz12)) && integer15 > 0; integer15--) {
					fz12 = fz.a(this.J);
				}
			} else {
				fz12 = (fz)list14.get(this.J.nextInt(list14.size()));
			}

			double6 = this.cC() + (double)fz12.i();
			double8 = this.cD() + (double)fz12.j();
			double10 = this.cG() + (double)fz12.k();
		}

		this.a(fz12);
		double double13 = double6 - this.cC();
		double double15 = double8 - this.cD();
		double double17 = double10 - this.cG();
		double double19 = (double)aec.a(double13 * double13 + double15 * double15 + double17 * double17);
		if (double19 == 0.0) {
			this.e = 0.0;
			this.f = 0.0;
			this.g = 0.0;
		} else {
			this.e = double13 / double19 * 0.15;
			this.f = double15 / double19 * 0.15;
			this.g = double17 / double19 * 0.15;
		}

		this.ad = true;
		this.d = 10 + this.J.nextInt(5) * 10;
	}

	@Override
	public void cH() {
		if (this.l.ac() == and.PEACEFUL) {
			this.aa();
		}
	}

	@Override
	public void j() {
		super.j();
		if (!this.l.v) {
			if (this.b == null && this.an != null) {
				this.b = ((zd)this.l).a(this.an);
				if (this.b == null) {
					this.an = null;
				}
			}

			if (this.b == null || !this.b.aU() || this.b instanceof bec && ((bec)this.b).a_()) {
				if (!this.aw()) {
					this.e(this.cB().b(0.0, -0.04, 0.0));
				}
			} else {
				this.e = aec.a(this.e * 1.025, -1.0, 1.0);
				this.f = aec.a(this.f * 1.025, -1.0, 1.0);
				this.g = aec.a(this.g * 1.025, -1.0, 1.0);
				dem dem2 = this.cB();
				this.e(dem2.b((this.e - dem2.b) * 0.2, (this.f - dem2.c) * 0.2, (this.g - dem2.d) * 0.2));
			}

			dej dej2 = bet.a(this, this::a, bpj.a.COLLIDER);
			if (dej2.c() != dej.a.MISS) {
				this.a(dej2);
			}
		}

		dem dem2 = this.cB();
		this.d(this.cC() + dem2.b, this.cD() + dem2.c, this.cG() + dem2.d);
		bet.a(this, 0.5F);
		if (this.l.v) {
			this.l.a(hh.t, this.cC() - dem2.b, this.cD() - dem2.c + 0.15, this.cG() - dem2.d, 0.0, 0.0, 0.0);
		} else if (this.b != null && !this.b.y) {
			if (this.d > 0) {
				this.d--;
				if (this.d == 0) {
					this.a(this.c == null ? null : this.c.n());
				}
			}

			if (this.c != null) {
				fu fu3 = this.cA();
				fz.a a4 = this.c.n();
				if (this.l.a(fu3.a(this.c), this)) {
					this.a(a4);
				} else {
					fu fu5 = this.b.cA();
					if (a4 == fz.a.X && fu3.u() == fu5.u() || a4 == fz.a.Z && fu3.w() == fu5.w() || a4 == fz.a.Y && fu3.v() == fu5.v()) {
						this.a(a4);
					}
				}
			}
		}
	}

	@Override
	protected boolean a(aom aom) {
		return super.a(aom) && !aom.H;
	}

	@Override
	public boolean bm() {
		return false;
	}

	@Override
	public float aO() {
		return 1.0F;
	}

	@Override
	protected void a(dei dei) {
		super.a(dei);
		aom aom3 = dei.a();
		aom aom4 = this.v();
		aoy aoy5 = aom4 instanceof aoy ? (aoy)aom4 : null;
		boolean boolean6 = aom3.a(anw.a(this, aoy5).c(), 4.0F);
		if (boolean6) {
			this.a(aoy5, aom3);
			if (aom3 instanceof aoy) {
				((aoy)aom3).c(new aog(aoi.y, 200));
			}
		}
	}

	@Override
	protected void a(deh deh) {
		super.a(deh);
		((zd)this.l).a(hh.w, this.cC(), this.cD(), this.cG(), 2, 0.2, 0.2, 0.2, 0.0);
		this.a(acl.mY, 1.0F, 1.0F);
	}

	@Override
	protected void a(dej dej) {
		super.a(dej);
		this.aa();
	}

	@Override
	public boolean aQ() {
		return true;
	}

	@Override
	public boolean a(anw anw, float float2) {
		if (!this.l.v) {
			this.a(acl.mZ, 1.0F, 1.0F);
			((zd)this.l).a(hh.g, this.cC(), this.cD(), this.cG(), 15, 0.2, 0.2, 0.2, 0.0);
			this.aa();
		}

		return true;
	}

	@Override
	public ni<?> O() {
		return new nm(this);
	}
}
