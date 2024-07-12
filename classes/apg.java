public abstract class apg extends aoz {
	protected apg(aoq<? extends apg> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public float f(fu fu) {
		return this.a(fu, this.l);
	}

	public float a(fu fu, bqd bqd) {
		return 0.0F;
	}

	@Override
	public boolean a(bqc bqc, apb apb) {
		return this.a(this.cA(), bqc) >= 0.0F;
	}

	public boolean eJ() {
		return !this.x().m();
	}

	@Override
	protected void eB() {
		super.eB();
		aom aom2 = this.eD();
		if (aom2 != null && aom2.l == this.l) {
			this.a(aom2.cA(), 5);
			float float3 = this.g(aom2);
			if (this instanceof apq && ((apq)this).eN()) {
				if (float3 > 10.0F) {
					this.a(true, true);
				}

				return;
			}

			this.t(float3);
			if (float3 > 10.0F) {
				this.a(true, true);
				this.br.a(aug.a.MOVE);
			} else if (float3 > 6.0F) {
				double double4 = (aom2.cC() - this.cC()) / (double)float3;
				double double6 = (aom2.cD() - this.cD()) / (double)float3;
				double double8 = (aom2.cG() - this.cG()) / (double)float3;
				this.e(
					this.cB()
						.b(Math.copySign(double4 * double4 * 0.4, double4), Math.copySign(double6 * double6 * 0.4, double6), Math.copySign(double8 * double8 * 0.4, double8))
				);
			} else {
				this.br.b(aug.a.MOVE);
				float float4 = 2.0F;
				dem dem5 = new dem(aom2.cC() - this.cC(), aom2.cD() - this.cD(), aom2.cG() - this.cG()).d().a((double)Math.max(float3 - 2.0F, 0.0F));
				this.x().a(this.cC() + dem5.b, this.cD() + dem5.c, this.cG() + dem5.d, this.eK());
			}
		}
	}

	protected double eK() {
		return 1.0;
	}

	protected void t(float float1) {
	}
}
