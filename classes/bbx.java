import java.util.EnumSet;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class bbx extends bcb {
	private static final tq<Boolean> b = tt.a(bbx.class, ts.i);
	private static final tq<Integer> d = tt.a(bbx.class, ts.b);
	private float bv;
	private float bw;
	private float bx;
	private float by;
	private float bz;
	private aoy bA;
	private int bB;
	private boolean bC;
	protected avf c;

	public bbx(aoq<? extends bbx> aoq, bqb bqb) {
		super(aoq, bqb);
		this.f = 10;
		this.a(czb.WATER, 0.0F);
		this.bo = new bbx.c(this);
		this.bv = this.J.nextFloat();
		this.bw = this.bv;
	}

	@Override
	protected void o() {
		auv auv2 = new auv(this, 1.0);
		this.c = new avf(this, 1.0, 80);
		this.br.a(4, new bbx.a(this));
		this.br.a(5, auv2);
		this.br.a(7, this.c);
		this.br.a(8, new auo(this, bec.class, 8.0F));
		this.br.a(8, new auo(this, bbx.class, 12.0F, 0.01F));
		this.br.a(9, new ave(this));
		this.c.a(EnumSet.of(aug.a.MOVE, aug.a.LOOK));
		auv2.a(EnumSet.of(aug.a.MOVE, aug.a.LOOK));
		this.bs.a(1, new awc(this, aoy.class, 10, true, false, new bbx.b(this)));
	}

	public static apw.a eN() {
		return bcb.eS().a(apx.f, 6.0).a(apx.d, 0.5).a(apx.b, 16.0).a(apx.a, 30.0);
	}

	@Override
	protected awv b(bqb bqb) {
		return new awx(this, bqb);
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(b, false);
		this.S.a(d, 0);
	}

	@Override
	public boolean cL() {
		return true;
	}

	@Override
	public apc dB() {
		return apc.e;
	}

	public boolean eO() {
		return this.S.a(b);
	}

	private void t(boolean boolean1) {
		this.S.b(b, boolean1);
	}

	public int eL() {
		return 80;
	}

	private void a(int integer) {
		this.S.b(d, integer);
	}

	public boolean eP() {
		return this.S.a(d) != 0;
	}

	@Nullable
	public aoy eQ() {
		if (!this.eP()) {
			return null;
		} else if (this.l.v) {
			if (this.bA != null) {
				return this.bA;
			} else {
				aom aom2 = this.l.a(this.S.a(d));
				if (aom2 instanceof aoy) {
					this.bA = (aoy)aom2;
					return this.bA;
				} else {
					return null;
				}
			}
		} else {
			return this.A();
		}
	}

	@Override
	public void a(tq<?> tq) {
		super.a(tq);
		if (d.equals(tq)) {
			this.bB = 0;
			this.bA = null;
		}
	}

	@Override
	public int D() {
		return 160;
	}

	@Override
	protected ack I() {
		return this.aD() ? acl.fs : acl.ft;
	}

	@Override
	protected ack e(anw anw) {
		return this.aD() ? acl.fy : acl.fz;
	}

	@Override
	protected ack dp() {
		return this.aD() ? acl.fv : acl.fw;
	}

	@Override
	protected boolean ax() {
		return false;
	}

	@Override
	protected float b(apj apj, aon aon) {
		return aon.b * 0.5F;
	}

	@Override
	public float a(fu fu, bqd bqd) {
		return bqd.b(fu).a(acz.a) ? 10.0F + bqd.y(fu) - 0.5F : super.a(fu, bqd);
	}

	@Override
	public void k() {
		if (this.aU()) {
			if (this.l.v) {
				this.bw = this.bv;
				if (!this.aA()) {
					this.bx = 2.0F;
					dem dem2 = this.cB();
					if (dem2.c > 0.0 && this.bC && !this.av()) {
						this.l.a(this.cC(), this.cD(), this.cG(), this.eM(), this.ct(), 1.0F, 1.0F, false);
					}

					this.bC = dem2.c < 0.0 && this.l.a(this.cA().c(), this);
				} else if (this.eO()) {
					if (this.bx < 0.5F) {
						this.bx = 4.0F;
					} else {
						this.bx = this.bx + (0.5F - this.bx) * 0.1F;
					}
				} else {
					this.bx = this.bx + (0.125F - this.bx) * 0.2F;
				}

				this.bv = this.bv + this.bx;
				this.bz = this.by;
				if (!this.aD()) {
					this.by = this.J.nextFloat();
				} else if (this.eO()) {
					this.by = this.by + (0.0F - this.by) * 0.25F;
				} else {
					this.by = this.by + (1.0F - this.by) * 0.06F;
				}

				if (this.eO() && this.aA()) {
					dem dem2 = this.f(0.0F);

					for (int integer3 = 0; integer3 < 2; integer3++) {
						this.l.a(hh.e, this.d(0.5) - dem2.b * 1.5, this.cE() - dem2.c * 1.5, this.g(0.5) - dem2.d * 1.5, 0.0, 0.0, 0.0);
					}
				}

				if (this.eP()) {
					if (this.bB < this.eL()) {
						this.bB++;
					}

					aoy aoy2 = this.eQ();
					if (aoy2 != null) {
						this.t().a(aoy2, 90.0F, 90.0F);
						this.t().a();
						double double3 = (double)this.w(0.0F);
						double double5 = aoy2.cC() - this.cC();
						double double7 = aoy2.e(0.5) - this.cF();
						double double9 = aoy2.cG() - this.cG();
						double double11 = Math.sqrt(double5 * double5 + double7 * double7 + double9 * double9);
						double5 /= double11;
						double7 /= double11;
						double9 /= double11;
						double double13 = this.J.nextDouble();

						while (double13 < double11) {
							double13 += 1.8 - double3 + this.J.nextDouble() * (1.7 - double3);
							this.l.a(hh.e, this.cC() + double5 * double13, this.cF() + double7 * double13, this.cG() + double9 * double13, 0.0, 0.0, 0.0);
						}
					}
				}
			}

			if (this.aD()) {
				this.j(300);
			} else if (this.t) {
				this.e(this.cB().b((double)((this.J.nextFloat() * 2.0F - 1.0F) * 0.4F), 0.5, (double)((this.J.nextFloat() * 2.0F - 1.0F) * 0.4F)));
				this.p = this.J.nextFloat() * 360.0F;
				this.t = false;
				this.ad = true;
			}

			if (this.eP()) {
				this.p = this.aJ;
			}
		}

		super.k();
	}

	protected ack eM() {
		return acl.fx;
	}

	public float w(float float1) {
		return ((float)this.bB + float1) / (float)this.eL();
	}

	@Override
	public boolean a(bqd bqd) {
		return bqd.i(this);
	}

	public static boolean b(aoq<? extends bbx> aoq, bqc bqc, apb apb, fu fu, Random random) {
		return (random.nextInt(20) == 0 || !bqc.x(fu)) && bqc.ac() != and.PEACEFUL && (apb == apb.SPAWNER || bqc.b(fu).a(acz.a));
	}

	@Override
	public boolean a(anw anw, float float2) {
		if (!this.eO() && !anw.t() && anw.j() instanceof aoy) {
			aoy aoy4 = (aoy)anw.j();
			if (!anw.d()) {
				aoy4.a(anw.a((aom)this), 2.0F);
			}
		}

		if (this.c != null) {
			this.c.h();
		}

		return super.a(anw, float2);
	}

	@Override
	public int eo() {
		return 180;
	}

	@Override
	public void f(dem dem) {
		if (this.dR() && this.aA()) {
			this.a(0.1F, dem);
			this.a(apd.SELF, this.cB());
			this.e(this.cB().a(0.9));
			if (!this.eO() && this.A() == null) {
				this.e(this.cB().b(0.0, -0.005, 0.0));
			}
		} else {
			super.f(dem);
		}
	}

	static class a extends aug {
		private final bbx a;
		private int b;
		private final boolean c;

		public a(bbx bbx) {
			this.a = bbx;
			this.c = bbx instanceof bbq;
			this.a(EnumSet.of(aug.a.MOVE, aug.a.LOOK));
		}

		@Override
		public boolean a() {
			aoy aoy2 = this.a.A();
			return aoy2 != null && aoy2.aU();
		}

		@Override
		public boolean b() {
			return super.b() && (this.c || this.a.h((aom)this.a.A()) > 9.0);
		}

		@Override
		public void c() {
			this.b = -10;
			this.a.x().o();
			this.a.t().a(this.a.A(), 90.0F, 90.0F);
			this.a.ad = true;
		}

		@Override
		public void d() {
			this.a.a(0);
			this.a.i(null);
			this.a.c.h();
		}

		@Override
		public void e() {
			aoy aoy2 = this.a.A();
			this.a.x().o();
			this.a.t().a(aoy2, 90.0F, 90.0F);
			if (!this.a.D(aoy2)) {
				this.a.i(null);
			} else {
				this.b++;
				if (this.b == 0) {
					this.a.a(this.a.A().V());
					if (!this.a.av()) {
						this.a.l.a(this.a, (byte)21);
					}
				} else if (this.b >= this.a.eL()) {
					float float3 = 1.0F;
					if (this.a.l.ac() == and.HARD) {
						float3 += 2.0F;
					}

					if (this.c) {
						float3 += 2.0F;
					}

					aoy2.a(anw.c(this.a, this.a), float3);
					aoy2.a(anw.c(this.a), (float)this.a.b(apx.f));
					this.a.i(null);
				}

				super.e();
			}
		}
	}

	static class b implements Predicate<aoy> {
		private final bbx a;

		public b(bbx bbx) {
			this.a = bbx;
		}

		public boolean test(@Nullable aoy aoy) {
			return (aoy instanceof bec || aoy instanceof azg) && aoy.h(this.a) > 9.0;
		}
	}

	static class c extends atm {
		private final bbx i;

		public c(bbx bbx) {
			super(bbx);
			this.i = bbx;
		}

		@Override
		public void a() {
			if (this.h == atm.a.MOVE_TO && !this.i.x().m()) {
				dem dem2 = new dem(this.b - this.i.cC(), this.c - this.i.cD(), this.d - this.i.cG());
				double double3 = dem2.f();
				double double5 = dem2.b / double3;
				double double7 = dem2.c / double3;
				double double9 = dem2.d / double3;
				float float11 = (float)(aec.d(dem2.d, dem2.b) * 180.0F / (float)Math.PI) - 90.0F;
				this.i.p = this.a(this.i.p, float11, 90.0F);
				this.i.aH = this.i.p;
				float float12 = (float)(this.e * this.i.b(apx.d));
				float float13 = aec.g(0.125F, this.i.dM(), float12);
				this.i.n(float13);
				double double14 = Math.sin((double)(this.i.K + this.i.V()) * 0.5) * 0.05;
				double double16 = Math.cos((double)(this.i.p * (float) (Math.PI / 180.0)));
				double double18 = Math.sin((double)(this.i.p * (float) (Math.PI / 180.0)));
				double double20 = Math.sin((double)(this.i.K + this.i.V()) * 0.75) * 0.05;
				this.i.e(this.i.cB().b(double14 * double16, double20 * (double18 + double16) * 0.25 + (double)float13 * double7 * 0.1, double14 * double18));
				atl atl22 = this.i.t();
				double double23 = this.i.cC() + double5 * 2.0;
				double double25 = this.i.cF() + double7 / double3;
				double double27 = this.i.cG() + double9 * 2.0;
				double double29 = atl22.d();
				double double31 = atl22.e();
				double double33 = atl22.f();
				if (!atl22.c()) {
					double29 = double23;
					double31 = double25;
					double33 = double27;
				}

				this.i.t().a(aec.d(0.125, double29, double23), aec.d(0.125, double31, double25), aec.d(0.125, double33, double27), 10.0F, 40.0F);
				this.i.t(true);
			} else {
				this.i.n(0.0F);
				this.i.t(false);
			}
		}
	}
}
