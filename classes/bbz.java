import javax.annotation.Nullable;

public class bbz extends bcl implements bcf {
	private int bv;
	private final dem[][] bw;

	public bbz(aoq<? extends bbz> aoq, bqb bqb) {
		super(aoq, bqb);
		this.f = 5;
		this.bw = new dem[2][4];

		for (int integer4 = 0; integer4 < 4; integer4++) {
			this.bw[0][integer4] = dem.a;
			this.bw[1][integer4] = dem.a;
		}
	}

	@Override
	protected void o() {
		super.o();
		this.br.a(0, new aua(this));
		this.br.a(1, new bcl.b(this));
		this.br.a(4, new bbz.b());
		this.br.a(5, new bbz.a());
		this.br.a(6, new avi<>(this, 0.5, 20, 15.0F));
		this.br.a(8, new avf(this, 0.6));
		this.br.a(9, new auo(this, bec.class, 3.0F, 1.0F));
		this.br.a(10, new auo(this, aoz.class, 8.0F));
		this.bs.a(1, new awb(this, bfi.class).a());
		this.bs.a(2, new awc(this, bec.class, true).a(300));
		this.bs.a(3, new awc(this, bdk.class, false).a(300));
		this.bs.a(3, new awc(this, ayt.class, false).a(300));
	}

	public static apw.a eL() {
		return bcb.eS().a(apx.d, 0.5).a(apx.b, 18.0).a(apx.a, 32.0);
	}

	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		this.a(aor.MAINHAND, new bki(bkk.kf));
		return super.a(bqc, ane, apb, apo, le);
	}

	@Override
	protected void e() {
		super.e();
	}

	@Override
	public void k() {
		super.k();
		if (this.l.v && this.bB()) {
			this.bv--;
			if (this.bv < 0) {
				this.bv = 0;
			}

			if (this.au == 1 || this.K % 1200 == 0) {
				this.bv = 3;
				float float2 = -6.0F;
				int integer3 = 13;

				for (int integer4 = 0; integer4 < 4; integer4++) {
					this.bw[0][integer4] = this.bw[1][integer4];
					this.bw[1][integer4] = new dem(
						(double)(-6.0F + (float)this.J.nextInt(13)) * 0.5, (double)Math.max(0, this.J.nextInt(6) - 4), (double)(-6.0F + (float)this.J.nextInt(13)) * 0.5
					);
				}

				for (int integer4 = 0; integer4 < 16; integer4++) {
					this.l.a(hh.f, this.d(0.5), this.cE(), this.f(0.5), 0.0, 0.0, 0.0);
				}

				this.l.a(this.cC(), this.cD(), this.cG(), acl.gs, this.ct(), 1.0F, 1.0F, false);
			} else if (this.au == this.av - 1) {
				this.bv = 3;

				for (int integer2 = 0; integer2 < 4; integer2++) {
					this.bw[0][integer2] = this.bw[1][integer2];
					this.bw[1][integer2] = new dem(0.0, 0.0, 0.0);
				}
			}
		}
	}

	@Override
	public ack eM() {
		return acl.go;
	}

	@Override
	public boolean r(aom aom) {
		if (super.r(aom)) {
			return true;
		} else {
			return aom instanceof aoy && ((aoy)aom).dB() == apc.d ? this.bC() == null && aom.bC() == null : false;
		}
	}

	@Override
	protected ack I() {
		return acl.go;
	}

	@Override
	protected ack dp() {
		return acl.gq;
	}

	@Override
	protected ack e(anw anw) {
		return acl.gr;
	}

	@Override
	protected ack eN() {
		return acl.gp;
	}

	@Override
	public void a(int integer, boolean boolean2) {
	}

	@Override
	public void a(aoy aoy, float float2) {
		bki bki4 = this.f(this.b(bet.a(this, bkk.kf)));
		beg beg5 = bet.a(this, bki4, float2);
		double double6 = aoy.cC() - this.cC();
		double double8 = aoy.e(0.3333333333333333) - beg5.cD();
		double double10 = aoy.cG() - this.cG();
		double double12 = (double)aec.a(double6 * double6 + double10 * double10);
		beg5.c(double6, double8 + double12 * 0.2F, double10, 1.6F, (float)(14 - this.l.ac().a() * 4));
		this.a(acl.nw, 1.0F, 1.0F / (this.cX().nextFloat() * 0.4F + 0.8F));
		this.l.c(beg5);
	}

	class a extends bcl.c {
		private int e;

		private a() {
			super(bbz.this);
		}

		@Override
		public boolean a() {
			if (!super.a()) {
				return false;
			} else if (bbz.this.A() == null) {
				return false;
			} else {
				return bbz.this.A().V() == this.e ? false : bbz.this.l.d(bbz.this.cA()).a((float)and.NORMAL.ordinal());
			}
		}

		@Override
		public void c() {
			super.c();
			this.e = bbz.this.A().V();
		}

		@Override
		protected int g() {
			return 20;
		}

		@Override
		protected int h() {
			return 180;
		}

		@Override
		protected void j() {
			bbz.this.A().c(new aog(aoi.o, 400));
		}

		@Override
		protected ack k() {
			return acl.gt;
		}

		@Override
		protected bcl.a l() {
			return bcl.a.BLINDNESS;
		}
	}

	class b extends bcl.c {
		private b() {
			super(bbz.this);
		}

		@Override
		public boolean a() {
			return !super.a() ? false : !bbz.this.a(aoi.n);
		}

		@Override
		protected int g() {
			return 20;
		}

		@Override
		protected int h() {
			return 340;
		}

		@Override
		protected void j() {
			bbz.this.c(new aog(aoi.n, 1200));
		}

		@Nullable
		@Override
		protected ack k() {
			return acl.gu;
		}

		@Override
		protected bcl.a l() {
			return bcl.a.DISAPPEAR;
		}
	}
}
