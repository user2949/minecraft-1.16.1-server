import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class azk extends apq implements ape {
	private static final tq<Boolean> by = tt.a(azk.class, ts.i);
	private static final tq<Integer> bz = tt.a(azk.class, ts.b);
	private static final tq<Integer> bA = tt.a(azk.class, ts.b);
	public static final Predicate<aoy> bx = aoy -> {
		aoq<?> aoq2 = aoy.U();
		return aoq2 == aoq.aq || aoq2 == aoq.an || aoq2 == aoq.C;
	};
	private float bB;
	private float bC;
	private boolean bD;
	private boolean bE;
	private float bF;
	private float bG;
	private static final adx bH = aej.a(20, 39);
	private UUID bI;

	public azk(aoq<? extends azk> aoq, bqb bqb) {
		super(aoq, bqb);
		this.u(false);
	}

	@Override
	protected void o() {
		this.br.a(1, new aua(this));
		this.br.a(2, new avn(this));
		this.br.a(3, new azk.a(this, azp.class, 24.0F, 1.5, 1.5));
		this.br.a(4, new aum(this, 0.4F));
		this.br.a(5, new auq(this, 1.0, true));
		this.br.a(6, new aue(this, 1.0, 10.0F, 2.0F, false));
		this.br.a(7, new att(this, 1.0));
		this.br.a(8, new avw(this, 1.0));
		this.br.a(9, new atp(this, 8.0F));
		this.br.a(10, new auo(this, bec.class, 8.0F));
		this.br.a(10, new ave(this));
		this.bs.a(1, new awg(this));
		this.bs.a(2, new awh(this));
		this.bs.a(3, new awb(this).a());
		this.bs.a(4, new awc(this, bec.class, 10, true, false, this::b));
		this.bs.a(5, new awf(this, ayk.class, false, bx));
		this.bs.a(6, new awf(this, azi.class, false, azi.bv));
		this.bs.a(7, new awc(this, bbk.class, false));
		this.bs.a(8, new awi<>(this, true));
	}

	public static apw.a eV() {
		return aoz.p().a(apx.d, 0.3F).a(apx.a, 8.0).a(apx.f, 2.0);
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(by, false);
		this.S.a(bz, bje.RED.b());
		this.S.a(bA, 0);
	}

	@Override
	protected void a(fu fu, cfj cfj) {
		this.a(acl.ra, 0.15F, 1.0F);
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.a("CollarColor", (byte)this.eY().b());
		this.c(le);
	}

	@Override
	public void a(le le) {
		super.a(le);
		if (le.c("CollarColor", 99)) {
			this.a(bje.a(le.h("CollarColor")));
		}

		this.a((zd)this.l, le);
	}

	@Override
	protected ack I() {
		if (this.I_()) {
			return acl.qV;
		} else if (this.J.nextInt(3) == 0) {
			return this.eL() && this.dj() < 10.0F ? acl.rb : acl.qY;
		} else {
			return acl.qT;
		}
	}

	@Override
	protected ack e(anw anw) {
		return acl.qX;
	}

	@Override
	protected ack dp() {
		return acl.qU;
	}

	@Override
	protected float dF() {
		return 0.4F;
	}

	@Override
	public void k() {
		super.k();
		if (!this.l.v && this.bD && !this.bE && !this.eJ() && this.t) {
			this.bE = true;
			this.bF = 0.0F;
			this.bG = 0.0F;
			this.l.a(this, (byte)8);
		}

		if (!this.l.v) {
			this.a((zd)this.l, true);
		}
	}

	@Override
	public void j() {
		super.j();
		if (this.aU()) {
			this.bC = this.bB;
			if (this.eZ()) {
				this.bB = this.bB + (1.0F - this.bB) * 0.4F;
			} else {
				this.bB = this.bB + (0.0F - this.bB) * 0.4F;
			}

			if (this.aC()) {
				this.bD = true;
				this.bE = false;
				this.bF = 0.0F;
				this.bG = 0.0F;
			} else if ((this.bD || this.bE) && this.bE) {
				if (this.bF == 0.0F) {
					this.a(acl.qZ, this.dF(), (this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F);
				}

				this.bG = this.bF;
				this.bF += 0.05F;
				if (this.bG >= 2.0F) {
					this.bD = false;
					this.bE = false;
					this.bG = 0.0F;
					this.bF = 0.0F;
				}

				if (this.bF > 0.4F) {
					float float2 = (float)this.cD();
					int integer3 = (int)(aec.a((this.bF - 0.4F) * (float) Math.PI) * 7.0F);
					dem dem4 = this.cB();

					for (int integer5 = 0; integer5 < integer3; integer5++) {
						float float6 = (this.J.nextFloat() * 2.0F - 1.0F) * this.cx() * 0.5F;
						float float7 = (this.J.nextFloat() * 2.0F - 1.0F) * this.cx() * 0.5F;
						this.l.a(hh.Z, this.cC() + (double)float6, (double)(float2 + 0.8F), this.cG() + (double)float7, dem4.b, dem4.c, dem4.d);
					}
				}
			}
		}
	}

	@Override
	public void a(anw anw) {
		this.bD = false;
		this.bE = false;
		this.bG = 0.0F;
		this.bF = 0.0F;
		super.a(anw);
	}

	@Override
	protected float b(apj apj, aon aon) {
		return aon.b * 0.8F;
	}

	@Override
	public int eo() {
		return this.eN() ? 20 : super.eo();
	}

	@Override
	public boolean a(anw anw, float float2) {
		if (this.b(anw)) {
			return false;
		} else {
			aom aom4 = anw.k();
			this.w(false);
			if (aom4 != null && !(aom4 instanceof bec) && !(aom4 instanceof beg)) {
				float2 = (float2 + 1.0F) / 2.0F;
			}

			return super.a(anw, float2);
		}
	}

	@Override
	public boolean B(aom aom) {
		boolean boolean3 = aom.a(anw.c(this), (float)((int)this.b(apx.f)));
		if (boolean3) {
			this.a(this, aom);
		}

		return boolean3;
	}

	@Override
	public void u(boolean boolean1) {
		super.u(boolean1);
		if (boolean1) {
			this.a(apx.a).a(20.0);
			this.c(20.0F);
		} else {
			this.a(apx.a).a(8.0);
		}

		this.a(apx.f).a(4.0);
	}

	@Override
	public ang b(bec bec, anf anf) {
		bki bki4 = bec.b(anf);
		bke bke5 = bki4.b();
		if (this.l.v) {
			boolean boolean6 = this.j(bec) || this.eL() || bke5 == bkk.mL && !this.eL() && !this.I_();
			return boolean6 ? ang.CONSUME : ang.PASS;
		} else {
			if (this.eL()) {
				if (this.k(bki4) && this.dj() < this.dw()) {
					if (!bec.bJ.d) {
						bki4.g(1);
					}

					this.b((float)bke5.t().a());
					return ang.SUCCESS;
				}

				if (!(bke5 instanceof bjf)) {
					ang ang6 = super.b(bec, anf);
					if ((!ang6.a() || this.x_()) && this.j(bec)) {
						this.w(!this.eP());
						this.aX = false;
						this.bq.o();
						this.i(null);
						return ang.SUCCESS;
					}

					return ang6;
				}

				bje bje6 = ((bjf)bke5).d();
				if (bje6 != this.eY()) {
					this.a(bje6);
					if (!bec.bJ.d) {
						bki4.g(1);
					}

					return ang.SUCCESS;
				}
			} else if (bke5 == bkk.mL && !this.I_()) {
				if (!bec.bJ.d) {
					bki4.g(1);
				}

				if (this.J.nextInt(3) == 0) {
					this.f(bec);
					this.bq.o();
					this.i(null);
					this.w(true);
					this.l.a(this, (byte)7);
				} else {
					this.l.a(this, (byte)6);
				}

				return ang.SUCCESS;
			}

			return super.b(bec, anf);
		}
	}

	@Override
	public boolean k(bki bki) {
		bke bke3 = bki.b();
		return bke3.s() && bke3.t().c();
	}

	@Override
	public int er() {
		return 8;
	}

	@Override
	public int F_() {
		return this.S.a(bA);
	}

	@Override
	public void a_(int integer) {
		this.S.b(bA, integer);
	}

	@Override
	public void H_() {
		this.a_(bH.a(this.J));
	}

	@Nullable
	@Override
	public UUID G_() {
		return this.bI;
	}

	@Override
	public void a(@Nullable UUID uUID) {
		this.bI = uUID;
	}

	public bje eY() {
		return bje.a(this.S.a(bz));
	}

	public void a(bje bje) {
		this.S.b(bz, bje.b());
	}

	public azk a(aok aok) {
		azk azk3 = aoq.aV.a(this.l);
		UUID uUID4 = this.B_();
		if (uUID4 != null) {
			azk3.b(uUID4);
			azk3.u(true);
		}

		return azk3;
	}

	public void x(boolean boolean1) {
		this.S.b(by, boolean1);
	}

	@Override
	public boolean a(ayk ayk) {
		if (ayk == this) {
			return false;
		} else if (!this.eL()) {
			return false;
		} else if (!(ayk instanceof azk)) {
			return false;
		} else {
			azk azk3 = (azk)ayk;
			if (!azk3.eL()) {
				return false;
			} else {
				return azk3.eN() ? false : this.eT() && azk3.eT();
			}
		}
	}

	public boolean eZ() {
		return this.S.a(by);
	}

	@Override
	public boolean a(aoy aoy1, aoy aoy2) {
		if (aoy1 instanceof bbn || aoy1 instanceof bbv) {
			return false;
		} else if (aoy1 instanceof azk) {
			azk azk4 = (azk)aoy1;
			return !azk4.eL() || azk4.eO() != aoy2;
		} else if (aoy1 instanceof bec && aoy2 instanceof bec && !((bec)aoy2).a((bec)aoy1)) {
			return false;
		} else {
			return aoy1 instanceof azm && ((azm)aoy1).eX() ? false : !(aoy1 instanceof apq) || !((apq)aoy1).eL();
		}
	}

	@Override
	public boolean a(bec bec) {
		return !this.I_() && super.a(bec);
	}

	class a<T extends aoy> extends ato<T> {
		private final azk j;

		public a(azk azk2, Class<T> class3, float float4, double double5, double double6) {
			super(azk2, class3, float4, double5, double6);
			this.j = azk2;
		}

		@Override
		public boolean a() {
			return super.a() && this.b instanceof azp ? !this.j.eL() && this.a((azp)this.b) : false;
		}

		private boolean a(azp azp) {
			return azp.fw() >= azk.this.J.nextInt(5);
		}

		@Override
		public void c() {
			azk.this.i(null);
			super.c();
		}

		@Override
		public void e() {
			azk.this.i(null);
			super.e();
		}
	}
}
