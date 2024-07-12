public abstract class aot extends aoz {
	protected aot(aoq<? extends aot> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	@Override
	public boolean b(float float1, float float2) {
		return false;
	}

	@Override
	protected void a(double double1, boolean boolean2, cfj cfj, fu fu) {
	}

	@Override
	public void f(dem dem) {
		if (this.aA()) {
			this.a(0.02F, dem);
			this.a(apd.SELF, this.cB());
			this.e(this.cB().a(0.8F));
		} else if (this.aN()) {
			this.a(0.02F, dem);
			this.a(apd.SELF, this.cB());
			this.e(this.cB().a(0.5));
		} else {
			float float3 = 0.91F;
			if (this.t) {
				float3 = this.l.d_(new fu(this.cC(), this.cD() - 1.0, this.cG())).b().j() * 0.91F;
			}

			float float4 = 0.16277137F / (float3 * float3 * float3);
			float3 = 0.91F;
			if (this.t) {
				float3 = this.l.d_(new fu(this.cC(), this.cD() - 1.0, this.cG())).b().j() * 0.91F;
			}

			this.a(this.t ? 0.1F * float4 : 0.02F, dem);
			this.a(apd.SELF, this.cB());
			this.e(this.cB().a((double)float3));
		}

		this.a(this, false);
	}

	@Override
	public boolean c_() {
		return false;
	}
}
