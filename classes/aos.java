import java.util.Map.Entry;

public class aos extends aom {
	public int b;
	public int c;
	public int d;
	private int e = 5;
	private int f;
	private bec g;
	private int an;

	public aos(bqb bqb, double double2, double double3, double double4, int integer) {
		this(aoq.y, bqb);
		this.d(double2, double3, double4);
		this.p = (float)(this.J.nextDouble() * 360.0);
		this.m((this.J.nextDouble() * 0.2F - 0.1F) * 2.0, this.J.nextDouble() * 0.2 * 2.0, (this.J.nextDouble() * 0.2F - 0.1F) * 2.0);
		this.f = integer;
	}

	public aos(aoq<? extends aos> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	@Override
	protected boolean ax() {
		return false;
	}

	@Override
	protected void e() {
	}

	@Override
	public void j() {
		super.j();
		if (this.d > 0) {
			this.d--;
		}

		this.m = this.cC();
		this.n = this.cD();
		this.o = this.cG();
		if (this.a(acz.a)) {
			this.i();
		} else if (!this.aw()) {
			this.e(this.cB().b(0.0, -0.03, 0.0));
		}

		if (this.l.b(this.cA()).a(acz.b)) {
			this.m((double)((this.J.nextFloat() - this.J.nextFloat()) * 0.2F), 0.2F, (double)((this.J.nextFloat() - this.J.nextFloat()) * 0.2F));
			this.a(acl.eH, 0.4F, 2.0F + this.J.nextFloat() * 0.4F);
		}

		if (!this.l.b(this.cb())) {
			this.k(this.cC(), (this.cb().b + this.cb().e) / 2.0, this.cG());
		}

		double double2 = 8.0;
		if (this.an < this.b - 20 + this.V() % 100) {
			if (this.g == null || this.g.h(this) > 64.0) {
				this.g = this.l.a(this, 8.0);
			}

			this.an = this.b;
		}

		if (this.g != null && this.g.a_()) {
			this.g = null;
		}

		if (this.g != null) {
			dem dem4 = new dem(this.g.cC() - this.cC(), this.g.cD() + (double)this.g.cd() / 2.0 - this.cD(), this.g.cG() - this.cG());
			double double5 = dem4.g();
			if (double5 < 64.0) {
				double double7 = 1.0 - Math.sqrt(double5) / 8.0;
				this.e(this.cB().e(dem4.d().a(double7 * double7 * 0.1)));
			}
		}

		this.a(apd.SELF, this.cB());
		float float4 = 0.98F;
		if (this.t) {
			float4 = this.l.d_(new fu(this.cC(), this.cD() - 1.0, this.cG())).b().j() * 0.98F;
		}

		this.e(this.cB().d((double)float4, 0.98, (double)float4));
		if (this.t) {
			this.e(this.cB().d(1.0, -0.9, 1.0));
		}

		this.b++;
		this.c++;
		if (this.c >= 6000) {
			this.aa();
		}
	}

	private void i() {
		dem dem2 = this.cB();
		this.m(dem2.b * 0.99F, Math.min(dem2.c + 5.0E-4F, 0.06F), dem2.d * 0.99F);
	}

	@Override
	protected void aI() {
	}

	@Override
	public boolean a(anw anw, float float2) {
		if (this.b(anw)) {
			return false;
		} else {
			this.aP();
			this.e = (int)((float)this.e - float2);
			if (this.e <= 0) {
				this.aa();
			}

			return false;
		}
	}

	@Override
	public void b(le le) {
		le.a("Health", (short)this.e);
		le.a("Age", (short)this.c);
		le.a("Value", (short)this.f);
	}

	@Override
	public void a(le le) {
		this.e = le.g("Health");
		this.c = le.g("Age");
		this.f = le.g("Value");
	}

	@Override
	public void a_(bec bec) {
		if (!this.l.v) {
			if (this.d == 0 && bec.bB == 0) {
				bec.bB = 2;
				bec.a(this, 1);
				Entry<aor, bki> entry3 = bny.a(boa.K, bec, bki::f);
				if (entry3 != null) {
					bki bki4 = (bki)entry3.getValue();
					if (!bki4.a() && bki4.f()) {
						int integer5 = Math.min(this.c(this.f), bki4.g());
						this.f = this.f - this.b(integer5);
						bki4.b(bki4.g() - integer5);
					}
				}

				if (this.f > 0) {
					bec.d(this.f);
				}

				this.aa();
			}
		}
	}

	private int b(int integer) {
		return integer / 2;
	}

	private int c(int integer) {
		return integer * 2;
	}

	public int g() {
		return this.f;
	}

	public static int a(int integer) {
		if (integer >= 2477) {
			return 2477;
		} else if (integer >= 1237) {
			return 1237;
		} else if (integer >= 617) {
			return 617;
		} else if (integer >= 307) {
			return 307;
		} else if (integer >= 149) {
			return 149;
		} else if (integer >= 73) {
			return 73;
		} else if (integer >= 37) {
			return 37;
		} else if (integer >= 17) {
			return 17;
		} else if (integer >= 7) {
			return 7;
		} else {
			return integer >= 3 ? 3 : 1;
		}
	}

	@Override
	public boolean bH() {
		return false;
	}

	@Override
	public ni<?> O() {
		return new nn(this);
	}
}
