import javax.annotation.Nullable;

public class bfe extends beg {
	private static final tq<Byte> g = tt.a(bfe.class, ts.a);
	private static final tq<Boolean> an = tt.a(bfe.class, ts.i);
	private bki ao = new bki(bkk.qL);
	private boolean ap;
	public int f;

	public bfe(aoq<? extends bfe> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public bfe(bqb bqb, aoy aoy, bki bki) {
		super(aoq.aJ, aoy, bqb);
		this.ao = bki.i();
		this.S.b(g, (byte)bny.f(bki));
		this.S.b(an, bki.u());
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(g, (byte)0);
		this.S.a(an, false);
	}

	@Override
	public void j() {
		if (this.c > 4) {
			this.ap = true;
		}

		aom aom2 = this.v();
		if ((this.ap || this.t()) && aom2 != null) {
			int integer3 = this.S.a(g);
			if (integer3 > 0 && !this.z()) {
				if (!this.l.v && this.d == beg.a.ALLOWED) {
					this.a(this.m(), 0.1F);
				}

				this.aa();
			} else if (integer3 > 0) {
				this.o(true);
				dem dem4 = new dem(aom2.cC() - this.cC(), aom2.cF() - this.cD(), aom2.cG() - this.cG());
				this.n(this.cC(), this.cD() + dem4.c * 0.015 * (double)integer3, this.cG());
				if (this.l.v) {
					this.E = this.cD();
				}

				double double5 = 0.05 * (double)integer3;
				this.e(this.cB().a(0.95).e(dem4.d().a(double5)));
				if (this.f == 0) {
					this.a(acl.oY, 10.0F, 1.0F);
				}

				this.f++;
			}
		}

		super.j();
	}

	private boolean z() {
		aom aom2 = this.v();
		return aom2 == null || !aom2.aU() ? false : !(aom2 instanceof ze) || !aom2.a_();
	}

	@Override
	protected bki m() {
		return this.ao.i();
	}

	@Nullable
	@Override
	protected dei a(dem dem1, dem dem2) {
		return this.ap ? null : super.a(dem1, dem2);
	}

	@Override
	protected void a(dei dei) {
		aom aom3 = dei.a();
		float float4 = 8.0F;
		if (aom3 instanceof aoy) {
			aoy aoy5 = (aoy)aom3;
			float4 += bny.a(this.ao, aoy5.dB());
		}

		aom aom5 = this.v();
		anw anw6 = anw.a(this, (aom)(aom5 == null ? this : aom5));
		this.ap = true;
		ack ack7 = acl.oW;
		if (aom3.a(anw6, float4)) {
			if (aom3.U() == aoq.u) {
				return;
			}

			if (aom3 instanceof aoy) {
				aoy aoy8 = (aoy)aom3;
				if (aom5 instanceof aoy) {
					bny.a(aoy8, aom5);
					bny.b((aoy)aom5, aoy8);
				}

				this.a(aoy8);
			}
		}

		this.e(this.cB().d(-0.01, -0.1, -0.01));
		float float8 = 1.0F;
		if (this.l instanceof zd && this.l.T() && bny.h(this.ao)) {
			fu fu9 = aom3.cA();
			if (this.l.f(fu9)) {
				aox aox10 = aoq.P.a(this.l);
				aox10.c(dem.c(fu9));
				aox10.d(aom5 instanceof ze ? (ze)aom5 : null);
				this.l.c(aox10);
				ack7 = acl.pd;
				float8 = 5.0F;
			}
		}

		this.a(ack7, float8, 1.0F);
	}

	@Override
	protected ack i() {
		return acl.oX;
	}

	@Override
	public void a_(bec bec) {
		aom aom3 = this.v();
		if (aom3 == null || aom3.bR() == bec.bR()) {
			super.a_(bec);
		}
	}

	@Override
	public void a(le le) {
		super.a(le);
		if (le.c("Trident", 10)) {
			this.ao = bki.a(le.p("Trident"));
		}

		this.ap = le.q("DealtDamage");
		this.S.b(g, (byte)bny.f(this.ao));
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.a("Trident", this.ao.b(new le()));
		le.a("DealtDamage", this.ap);
	}

	@Override
	public void h() {
		int integer2 = this.S.a(g);
		if (this.d != beg.a.ALLOWED || integer2 <= 0) {
			super.h();
		}
	}

	@Override
	protected float s() {
		return 0.99F;
	}
}
