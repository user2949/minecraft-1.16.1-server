import javax.annotation.Nullable;

public class ayy extends ayk implements aow, apm {
	private static final tq<Boolean> bv = tt.a(ayy.class, ts.i);
	private static final tq<Integer> bw = tt.a(ayy.class, ts.b);
	private static final bmr bx = bmr.a(bkk.oX, bkk.oY, bkk.qe);
	private final aov by = new aov(this.S, bw, bv);

	public ayy(aoq<? extends ayy> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	@Override
	protected void o() {
		this.br.a(0, new aua(this));
		this.br.a(1, new avb(this, 1.25));
		this.br.a(3, new att(this, 1.0));
		this.br.a(4, new avr(this, 1.2, bmr.a(bkk.pj), false));
		this.br.a(4, new avr(this, 1.2, false, bx));
		this.br.a(5, new auf(this, 1.1));
		this.br.a(6, new avw(this, 1.0));
		this.br.a(7, new auo(this, bec.class, 6.0F));
		this.br.a(8, new ave(this));
	}

	public static apw.a eL() {
		return aoz.p().a(apx.a, 10.0).a(apx.d, 0.25);
	}

	@Nullable
	@Override
	public aom cl() {
		return this.cm().isEmpty() ? null : (aom)this.cm().get(0);
	}

	@Override
	public boolean es() {
		aom aom2 = this.cl();
		if (!(aom2 instanceof bec)) {
			return false;
		} else {
			bec bec3 = (bec)aom2;
			return bec3.dC().b() == bkk.pj || bec3.dD().b() == bkk.pj;
		}
	}

	@Override
	public void a(tq<?> tq) {
		if (bw.equals(tq) && this.l.v) {
			this.by.a();
		}

		super.a(tq);
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(bv, false);
		this.S.a(bw, 0);
	}

	@Override
	public void b(le le) {
		super.b(le);
		this.by.a(le);
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.by.b(le);
	}

	@Override
	protected ack I() {
		return acl.kM;
	}

	@Override
	protected ack e(anw anw) {
		return acl.kO;
	}

	@Override
	protected ack dp() {
		return acl.kN;
	}

	@Override
	protected void a(fu fu, cfj cfj) {
		this.a(acl.kQ, 0.15F, 1.0F);
	}

	@Override
	public ang b(bec bec, anf anf) {
		boolean boolean4 = this.k(bec.b(anf));
		if (!boolean4 && this.N_() && !this.bo()) {
			if (!this.l.v) {
				bec.m(this);
			}

			return ang.a(this.l.v);
		} else {
			ang ang5 = super.b(bec, anf);
			if (!ang5.a()) {
				bki bki6 = bec.b(anf);
				return bki6.b() == bkk.lO ? bki6.a(bec, this, anf) : ang.PASS;
			} else {
				return ang5;
			}
		}
	}

	@Override
	public boolean M_() {
		return this.aU() && !this.x_();
	}

	@Override
	protected void dm() {
		super.dm();
		if (this.N_()) {
			this.a(bkk.lO);
		}
	}

	@Override
	public boolean N_() {
		return this.by.b();
	}

	@Override
	public void a(@Nullable acm acm) {
		this.by.a(true);
		if (acm != null) {
			this.l.a(null, this, acl.kP, acm, 0.5F, 1.0F);
		}
	}

	@Override
	public dem c(aoy aoy) {
		fz fz3 = this.bZ();
		if (fz3.n() == fz.a.Y) {
			return super.c(aoy);
		} else {
			int[][] arr4 = bfu.a(fz3);
			fu fu5 = this.cA();
			fu.a a6 = new fu.a();

			for (apj apj8 : aoy.ei()) {
				deg deg9 = aoy.f(apj8);

				for (int[] arr13 : arr4) {
					a6.d(fu5.u() + arr13[0], fu5.v(), fu5.w() + arr13[1]);
					double double14 = this.l.m(a6);
					if (bfu.a(double14)) {
						dem dem16 = dem.a(a6, double14);
						if (bfu.a(this.l, aoy, deg9.c(dem16))) {
							aoy.b(apj8);
							return dem16;
						}
					}
				}
			}

			return super.c(aoy);
		}
	}

	@Override
	public void a(aox aox) {
		if (this.l.ac() != and.PEACEFUL) {
			bcw bcw3 = aoq.ba.a(this.l);
			bcw3.a(aor.MAINHAND, new bki(bkk.kE));
			bcw3.b(this.cC(), this.cD(), this.cG(), this.p, this.q);
			bcw3.q(this.eE());
			bcw3.a(this.x_());
			if (this.Q()) {
				bcw3.a(this.R());
				bcw3.n(this.bW());
			}

			bcw3.et();
			this.l.c(bcw3);
			this.aa();
		} else {
			super.a(aox);
		}
	}

	@Override
	public void f(dem dem) {
		this.a(this, this.by, dem);
	}

	@Override
	public float O_() {
		return (float)this.b(apx.d) * 0.225F;
	}

	@Override
	public void a_(dem dem) {
		super.f(dem);
	}

	@Override
	public boolean P_() {
		return this.by.a(this.cX());
	}

	public ayy a(aok aok) {
		return aoq.ah.a(this.l);
	}

	@Override
	public boolean k(bki bki) {
		return bx.a(bki);
	}
}
