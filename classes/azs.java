import javax.annotation.Nullable;

public class azs extends azm {
	private final azt bD = new azt(this);
	private boolean bE;
	private int bF;

	public azs(aoq<? extends azs> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public static apw.a eM() {
		return fj().a(apx.a, 15.0).a(apx.d, 0.2F);
	}

	@Override
	protected void eL() {
		this.a(apx.m).a(this.fr());
	}

	@Override
	protected void eW() {
	}

	@Override
	protected ack I() {
		super.I();
		return this.a(acz.a) ? acl.nr : acl.nn;
	}

	@Override
	protected ack dp() {
		super.dp();
		return acl.no;
	}

	@Override
	protected ack e(anw anw) {
		super.e(anw);
		return acl.np;
	}

	@Override
	protected ack aq() {
		if (this.t) {
			if (!this.bo()) {
				return acl.nu;
			}

			this.bC++;
			if (this.bC > 5 && this.bC % 3 == 0) {
				return acl.ns;
			}

			if (this.bC <= 5) {
				return acl.nu;
			}
		}

		return acl.nq;
	}

	@Override
	protected void d(float float1) {
		if (this.t) {
			super.d(0.3F);
		} else {
			super.d(Math.min(0.1F, float1 * 25.0F));
		}
	}

	@Override
	protected void fo() {
		if (this.aA()) {
			this.a(acl.nt, 0.4F, 1.0F);
		} else {
			super.fo();
		}
	}

	@Override
	public apc dB() {
		return apc.b;
	}

	@Override
	public double aY() {
		return super.aY() - 0.1875;
	}

	@Override
	public void k() {
		super.k();
		if (this.eN() && this.bF++ >= 18000) {
			this.aa();
		}
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.a("SkeletonTrap", this.eN());
		le.b("SkeletonTrapTime", this.bF);
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.t(le.q("SkeletonTrap"));
		this.bF = le.h("SkeletonTrapTime");
	}

	@Override
	public boolean bp() {
		return true;
	}

	@Override
	protected float dL() {
		return 0.96F;
	}

	public boolean eN() {
		return this.bE;
	}

	public void t(boolean boolean1) {
		if (boolean1 != this.bE) {
			this.bE = boolean1;
			if (boolean1) {
				this.br.a(1, this.bD);
			} else {
				this.br.a(this.bD);
			}
		}
	}

	@Nullable
	@Override
	public aok a(aok aok) {
		return aoq.av.a(this.l);
	}

	@Override
	public ang b(bec bec, anf anf) {
		bki bki4 = bec.b(anf);
		if (!this.eX()) {
			return ang.PASS;
		} else if (this.x_()) {
			return super.b(bec, anf);
		} else if (bec.ep()) {
			this.f(bec);
			return ang.a(this.l.v);
		} else if (this.bo()) {
			return super.b(bec, anf);
		} else {
			if (!bki4.a()) {
				if (bki4.b() == bkk.lO && !this.N_()) {
					this.f(bec);
					return ang.a(this.l.v);
				}

				ang ang5 = bki4.a(bec, this, anf);
				if (ang5.a()) {
					return ang5;
				}
			}

			this.h(bec);
			return ang.a(this.l.v);
		}
	}
}
