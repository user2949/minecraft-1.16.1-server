public abstract class azl extends azm {
	private static final tq<Boolean> bD = tt.a(azl.class, ts.i);

	protected azl(aoq<? extends azl> aoq, bqb bqb) {
		super(aoq, bqb);
		this.bB = false;
	}

	@Override
	protected void eL() {
		this.a(apx.a).a((double)this.fq());
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(bD, false);
	}

	public static apw.a eM() {
		return fj().a(apx.d, 0.175F).a(apx.m, 0.5);
	}

	public boolean eN() {
		return this.S.a(bD);
	}

	public void t(boolean boolean1) {
		this.S.b(bD, boolean1);
	}

	@Override
	protected int eO() {
		return this.eN() ? 17 : super.eO();
	}

	@Override
	public double aY() {
		return super.aY() - 0.25;
	}

	@Override
	protected void dm() {
		super.dm();
		if (this.eN()) {
			if (!this.l.v) {
				this.a(bvs.bR);
			}

			this.t(false);
		}
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.a("ChestedHorse", this.eN());
		if (this.eN()) {
			lk lk3 = new lk();

			for (int integer4 = 2; integer4 < this.by.ab_(); integer4++) {
				bki bki5 = this.by.a(integer4);
				if (!bki5.a()) {
					le le6 = new le();
					le6.a("Slot", (byte)integer4);
					bki5.b(le6);
					lk3.add(le6);
				}
			}

			le.a("Items", lk3);
		}
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.t(le.q("ChestedHorse"));
		if (this.eN()) {
			lk lk3 = le.d("Items", 10);
			this.fe();

			for (int integer4 = 0; integer4 < lk3.size(); integer4++) {
				le le5 = lk3.a(integer4);
				int integer6 = le5.f("Slot") & 255;
				if (integer6 >= 2 && integer6 < this.by.ab_()) {
					this.by.a(integer6, bki.a(le5));
				}
			}
		}

		this.ff();
	}

	@Override
	public boolean a_(int integer, bki bki) {
		if (integer == 499) {
			if (this.eN() && bki.a()) {
				this.t(false);
				this.fe();
				return true;
			}

			if (!this.eN() && bki.b() == bvs.bR.h()) {
				this.t(true);
				this.fe();
				return true;
			}
		}

		return super.a_(integer, bki);
	}

	@Override
	public ang b(bec bec, anf anf) {
		bki bki4 = bec.b(anf);
		if (!this.x_()) {
			if (this.eX() && bec.ep()) {
				this.f(bec);
				return ang.a(this.l.v);
			}

			if (this.bo()) {
				return super.b(bec, anf);
			}
		}

		if (!bki4.a()) {
			if (this.k(bki4)) {
				return this.b(bec, bki4);
			}

			if (!this.eX()) {
				this.fn();
				return ang.a(this.l.v);
			}

			if (!this.eN() && bki4.b() == bvs.bR.h()) {
				this.t(true);
				this.eP();
				if (!bec.bJ.d) {
					bki4.g(1);
				}

				this.fe();
				return ang.a(this.l.v);
			}

			if (!this.x_() && !this.N_() && bki4.b() == bkk.lO) {
				this.f(bec);
				return ang.a(this.l.v);
			}
		}

		if (this.x_()) {
			return super.b(bec, anf);
		} else {
			this.h(bec);
			return ang.a(this.l.v);
		}
	}

	@Override
	protected void eP() {
		this.a(acl.cP, 1.0F, (this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F);
	}

	public int eV() {
		return 5;
	}
}
