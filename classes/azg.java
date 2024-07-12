import java.util.Random;

public class azg extends azj {
	public float b;
	public float c;
	public float d;
	public float bv;
	public float bw;
	public float bx;
	public float by;
	public float bz;
	private float bA;
	private float bB;
	private float bC;
	private float bD;
	private float bE;
	private float bF;

	public azg(aoq<? extends azg> aoq, bqb bqb) {
		super(aoq, bqb);
		this.J.setSeed((long)this.V());
		this.bB = 1.0F / (this.J.nextFloat() + 1.0F) * 0.2F;
	}

	@Override
	protected void o() {
		this.br.a(0, new azg.b(this));
		this.br.a(1, new azg.a());
	}

	public static apw.a m() {
		return aoz.p().a(apx.a, 10.0);
	}

	@Override
	protected float b(apj apj, aon aon) {
		return aon.b * 0.5F;
	}

	@Override
	protected ack I() {
		return acl.oz;
	}

	@Override
	protected ack e(anw anw) {
		return acl.oB;
	}

	@Override
	protected ack dp() {
		return acl.oA;
	}

	@Override
	protected float dF() {
		return 0.4F;
	}

	@Override
	protected boolean ax() {
		return false;
	}

	@Override
	public void k() {
		super.k();
		this.c = this.b;
		this.bv = this.d;
		this.bx = this.bw;
		this.bz = this.by;
		this.bw = this.bw + this.bB;
		if ((double)this.bw > Math.PI * 2) {
			if (this.l.v) {
				this.bw = (float) (Math.PI * 2);
			} else {
				this.bw = (float)((double)this.bw - (Math.PI * 2));
				if (this.J.nextInt(10) == 0) {
					this.bB = 1.0F / (this.J.nextFloat() + 1.0F) * 0.2F;
				}

				this.l.a(this, (byte)19);
			}
		}

		if (this.aD()) {
			if (this.bw < (float) Math.PI) {
				float float2 = this.bw / (float) Math.PI;
				this.by = aec.a(float2 * float2 * (float) Math.PI) * (float) Math.PI * 0.25F;
				if ((double)float2 > 0.75) {
					this.bA = 1.0F;
					this.bC = 1.0F;
				} else {
					this.bC *= 0.8F;
				}
			} else {
				this.by = 0.0F;
				this.bA *= 0.9F;
				this.bC *= 0.99F;
			}

			if (!this.l.v) {
				this.m((double)(this.bD * this.bA), (double)(this.bE * this.bA), (double)(this.bF * this.bA));
			}

			dem dem2 = this.cB();
			float float3 = aec.a(b(dem2));
			this.aH = this.aH + (-((float)aec.d(dem2.b, dem2.d)) * (180.0F / (float)Math.PI) - this.aH) * 0.1F;
			this.p = this.aH;
			this.d = (float)((double)this.d + Math.PI * (double)this.bC * 1.5);
			this.b = this.b + (-((float)aec.d((double)float3, dem2.c)) * (180.0F / (float)Math.PI) - this.b) * 0.1F;
		} else {
			this.by = aec.e(aec.a(this.bw)) * (float) Math.PI * 0.25F;
			if (!this.l.v) {
				double double2 = this.cB().c;
				if (this.a(aoi.y)) {
					double2 = 0.05 * (double)(this.b(aoi.y).c() + 1);
				} else if (!this.aw()) {
					double2 -= 0.08;
				}

				this.m(0.0, double2 * 0.98F, 0.0);
			}

			this.b = (float)((double)this.b + (double)(-90.0F - this.b) * 0.02);
		}
	}

	@Override
	public boolean a(anw anw, float float2) {
		if (super.a(anw, float2) && this.cY() != null) {
			this.eM();
			return true;
		} else {
			return false;
		}
	}

	private dem g(dem dem) {
		dem dem3 = dem.a(this.c * (float) (Math.PI / 180.0));
		return dem3.b(-this.aI * (float) (Math.PI / 180.0));
	}

	private void eM() {
		this.a(acl.oC, this.dF(), this.dG());
		dem dem2 = this.g(new dem(0.0, -1.0, 0.0)).b(this.cC(), this.cD(), this.cG());

		for (int integer3 = 0; integer3 < 30; integer3++) {
			dem dem4 = this.g(new dem((double)this.J.nextFloat() * 0.6 - 0.3, -1.0, (double)this.J.nextFloat() * 0.6 - 0.3));
			dem dem5 = dem4.a(0.3 + (double)(this.J.nextFloat() * 2.0F));
			((zd)this.l).a(hh.V, dem2.b, dem2.c + 0.5, dem2.d, 0, dem5.b, dem5.c, dem5.d, 0.1F);
		}
	}

	@Override
	public void f(dem dem) {
		this.a(apd.SELF, this.cB());
	}

	public static boolean b(aoq<azg> aoq, bqc bqc, apb apb, fu fu, Random random) {
		return fu.v() > 45 && fu.v() < bqc.t_();
	}

	public void a(float float1, float float2, float float3) {
		this.bD = float1;
		this.bE = float2;
		this.bF = float3;
	}

	public boolean eL() {
		return this.bD != 0.0F || this.bE != 0.0F || this.bF != 0.0F;
	}

	class a extends aug {
		private int b;

		private a() {
		}

		@Override
		public boolean a() {
			aoy aoy2 = azg.this.cY();
			return azg.this.aA() && aoy2 != null ? azg.this.h((aom)aoy2) < 100.0 : false;
		}

		@Override
		public void c() {
			this.b = 0;
		}

		@Override
		public void e() {
			this.b++;
			aoy aoy2 = azg.this.cY();
			if (aoy2 != null) {
				dem dem3 = new dem(azg.this.cC() - aoy2.cC(), azg.this.cD() - aoy2.cD(), azg.this.cG() - aoy2.cG());
				cfj cfj4 = azg.this.l.d_(new fu(azg.this.cC() + dem3.b, azg.this.cD() + dem3.c, azg.this.cG() + dem3.d));
				cxa cxa5 = azg.this.l.b(new fu(azg.this.cC() + dem3.b, azg.this.cD() + dem3.c, azg.this.cG() + dem3.d));
				if (cxa5.a(acz.a) || cfj4.g()) {
					double double6 = dem3.f();
					if (double6 > 0.0) {
						dem3.d();
						float float8 = 3.0F;
						if (double6 > 5.0) {
							float8 = (float)((double)float8 - (double6 - 5.0) / 5.0);
						}

						if (float8 > 0.0F) {
							dem3 = dem3.a((double)float8);
						}
					}

					if (cfj4.g()) {
						dem3 = dem3.a(0.0, dem3.c, 0.0);
					}

					azg.this.a((float)dem3.b / 20.0F, (float)dem3.c / 20.0F, (float)dem3.d / 20.0F);
				}

				if (this.b % 10 == 5) {
					azg.this.l.a(hh.e, azg.this.cC(), azg.this.cD(), azg.this.cG(), 0.0, 0.0, 0.0);
				}
			}
		}
	}

	class b extends aug {
		private final azg b;

		public b(azg azg2) {
			this.b = azg2;
		}

		@Override
		public boolean a() {
			return true;
		}

		@Override
		public void e() {
			int integer2 = this.b.dc();
			if (integer2 > 100) {
				this.b.a(0.0F, 0.0F, 0.0F);
			} else if (this.b.cX().nextInt(50) == 0 || !this.b.L || !this.b.eL()) {
				float float3 = this.b.cX().nextFloat() * (float) (Math.PI * 2);
				float float4 = aec.b(float3) * 0.2F;
				float float5 = -0.1F + this.b.cX().nextFloat() * 0.2F;
				float float6 = aec.a(float3) * 0.2F;
				this.b.a(float4, float5, float6);
			}
		}
	}
}
