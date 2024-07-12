public class ber extends bes {
	public ber(aoq<? extends ber> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public ber(bqb bqb, azp azp) {
		this(aoq.R, bqb);
		super.b(azp);
		this.d(
			azp.cC() - (double)(azp.cx() + 1.0F) * 0.5 * (double)aec.a(azp.aH * (float) (Math.PI / 180.0)),
			azp.cF() - 0.1F,
			azp.cG() + (double)(azp.cx() + 1.0F) * 0.5 * (double)aec.b(azp.aH * (float) (Math.PI / 180.0))
		);
	}

	@Override
	public void j() {
		super.j();
		dem dem2 = this.cB();
		dej dej3 = bet.a(this, this::a, bpj.a.OUTLINE);
		if (dej3 != null) {
			this.a(dej3);
		}

		double double4 = this.cC() + dem2.b;
		double double6 = this.cD() + dem2.c;
		double double8 = this.cG() + dem2.d;
		this.x();
		float float10 = 0.99F;
		float float11 = 0.06F;
		if (this.l.a(this.cb()).noneMatch(cfi.a::g)) {
			this.aa();
		} else if (this.aD()) {
			this.aa();
		} else {
			this.e(dem2.a(0.99F));
			if (!this.aw()) {
				this.e(this.cB().b(0.0, -0.06F, 0.0));
			}

			this.d(double4, double6, double8);
		}
	}

	@Override
	protected void a(dei dei) {
		super.a(dei);
		aom aom3 = this.v();
		if (aom3 instanceof aoy) {
			dei.a().a(anw.a(this, (aoy)aom3).c(), 1.0F);
		}
	}

	@Override
	protected void a(deh deh) {
		super.a(deh);
		if (!this.l.v) {
			this.aa();
		}
	}

	@Override
	protected void e() {
	}

	@Override
	public ni<?> O() {
		return new nm(this);
	}
}
