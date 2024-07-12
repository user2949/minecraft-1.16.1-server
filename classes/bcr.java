import java.util.List;
import java.util.UUID;

public class bcr extends bfi implements bcf {
	private static final UUID b = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
	private static final apv bv = new apv(b, "Drinking speed penalty", -0.25, apv.a.ADDITION);
	private static final tq<Boolean> bw = tt.a(bcr.class, ts.i);
	private int bx;
	private awe<bfi> by;
	private awd<bec> bz;

	public bcr(aoq<? extends bcr> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	@Override
	protected void o() {
		super.o();
		this.by = new awe<>(this, bfi.class, true, aoy -> aoy != null && this.fc() && aoy.U() != aoq.aR);
		this.bz = new awd<>(this, bec.class, 10, true, false, null);
		this.br.a(1, new aua(this));
		this.br.a(2, new avh(this, 1.0, 60, 10.0F));
		this.br.a(2, new avw(this, 1.0));
		this.br.a(3, new auo(this, bec.class, 8.0F));
		this.br.a(3, new ave(this));
		this.bs.a(1, new awb(this, bfi.class));
		this.bs.a(2, this.by);
		this.bs.a(3, this.bz);
	}

	@Override
	protected void e() {
		super.e();
		this.Y().a(bw, false);
	}

	@Override
	protected ack I() {
		return acl.qD;
	}

	@Override
	protected ack e(anw anw) {
		return acl.qH;
	}

	@Override
	protected ack dp() {
		return acl.qF;
	}

	public void v(boolean boolean1) {
		this.Y().b(bw, boolean1);
	}

	public boolean m() {
		return this.Y().a(bw);
	}

	public static apw.a eL() {
		return bcb.eS().a(apx.a, 26.0).a(apx.d, 0.25);
	}

	@Override
	public void k() {
		if (!this.l.v && this.aU()) {
			this.by.j();
			if (this.by.h() <= 0) {
				this.bz.a(true);
			} else {
				this.bz.a(false);
			}

			if (this.m()) {
				if (this.bx-- <= 0) {
					this.v(false);
					bki bki2 = this.dC();
					this.a(aor.MAINHAND, bki.b);
					if (bki2.b() == bkk.nv) {
						List<aog> list3 = bmd.a(bki2);
						if (list3 != null) {
							for (aog aog5 : list3) {
								this.c(new aog(aog5));
							}
						}
					}

					this.a(apx.d).d(bv);
				}
			} else {
				bmb bmb2 = null;
				if (this.J.nextFloat() < 0.15F && this.a(acz.a) && !this.a(aoi.m)) {
					bmb2 = bme.x;
				} else if (this.J.nextFloat() < 0.15F && (this.bm() || this.dl() != null && this.dl().p()) && !this.a(aoi.l)) {
					bmb2 = bme.m;
				} else if (this.J.nextFloat() < 0.05F && this.dj() < this.dw()) {
					bmb2 = bme.z;
				} else if (this.J.nextFloat() < 0.5F && this.A() != null && !this.a(aoi.a) && this.A().h(this) > 121.0) {
					bmb2 = bme.o;
				}

				if (bmb2 != null) {
					this.a(aor.MAINHAND, bmd.a(new bki(bkk.nv), bmb2));
					this.bx = this.dC().k();
					this.v(true);
					if (!this.av()) {
						this.l.a(null, this.cC(), this.cD(), this.cG(), acl.qG, this.ct(), 1.0F, 0.8F + this.J.nextFloat() * 0.4F);
					}

					apt apt3 = this.a(apx.d);
					apt3.d(bv);
					apt3.b(bv);
				}
			}

			if (this.J.nextFloat() < 7.5E-4F) {
				this.l.a(this, (byte)15);
			}
		}

		super.k();
	}

	@Override
	public ack eM() {
		return acl.qE;
	}

	@Override
	protected float d(anw anw, float float2) {
		float2 = super.d(anw, float2);
		if (anw.k() == this) {
			float2 = 0.0F;
		}

		if (anw.t()) {
			float2 = (float)((double)float2 * 0.15);
		}

		return float2;
	}

	@Override
	public void a(aoy aoy, float float2) {
		if (!this.m()) {
			dem dem4 = aoy.cB();
			double double5 = aoy.cC() + dem4.b - this.cC();
			double double7 = aoy.cF() - 1.1F - this.cD();
			double double9 = aoy.cG() + dem4.d - this.cG();
			float float11 = aec.a(double5 * double5 + double9 * double9);
			bmb bmb12 = bme.B;
			if (aoy instanceof bfi) {
				if (aoy.dj() <= 4.0F) {
					bmb12 = bme.z;
				} else {
					bmb12 = bme.G;
				}

				this.i(null);
			} else if (float11 >= 8.0F && !aoy.a(aoi.b)) {
				bmb12 = bme.r;
			} else if (aoy.dj() >= 8.0F && !aoy.a(aoi.s)) {
				bmb12 = bme.D;
			} else if (float11 <= 3.0F && !aoy.a(aoi.r) && this.J.nextFloat() < 0.25F) {
				bmb12 = bme.M;
			}

			bfd bfd13 = new bfd(this.l, this);
			bfd13.b(bmd.a(new bki(bkk.qi), bmb12));
			bfd13.q -= -20.0F;
			bfd13.c(double5, double7 + (double)(float11 * 0.2F), double9, 0.75F, 8.0F);
			if (!this.av()) {
				this.l.a(null, this.cC(), this.cD(), this.cG(), acl.qI, this.ct(), 1.0F, 0.8F + this.J.nextFloat() * 0.4F);
			}

			this.l.c(bfd13);
		}
	}

	@Override
	protected float b(apj apj, aon aon) {
		return 1.62F;
	}

	@Override
	public void a(int integer, boolean boolean2) {
	}

	@Override
	public boolean eO() {
		return false;
	}
}
