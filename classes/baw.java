import com.google.common.collect.ImmutableList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class baw extends bcb implements bcf {
	private static final tq<Integer> b = tt.a(baw.class, ts.b);
	private static final tq<Integer> c = tt.a(baw.class, ts.b);
	private static final tq<Integer> d = tt.a(baw.class, ts.b);
	private static final List<tq<Integer>> bv = ImmutableList.of(b, c, d);
	private static final tq<Integer> bw = tt.a(baw.class, ts.b);
	private final float[] bx = new float[2];
	private final float[] by = new float[2];
	private final float[] bz = new float[2];
	private final float[] bA = new float[2];
	private final int[] bB = new int[2];
	private final int[] bC = new int[2];
	private int bD;
	private final za bE = (za)new za(this.d(), amw.a.PURPLE, amw.b.PROGRESS).a(true);
	private static final Predicate<aoy> bF = aoy -> aoy.dB() != apc.b && aoy.eh();
	private static final axs bG = new axs().a(20.0).a(bF);

	public baw(aoq<? extends baw> aoq, bqb bqb) {
		super(aoq, bqb);
		this.c(this.dw());
		this.x().d(true);
		this.f = 50;
	}

	@Override
	protected void o() {
		this.br.a(0, new baw.a());
		this.br.a(2, new avh(this, 1.0, 40, 20.0F));
		this.br.a(5, new avw(this, 1.0));
		this.br.a(6, new auo(this, bec.class, 8.0F));
		this.br.a(7, new ave(this));
		this.bs.a(1, new awb(this));
		this.bs.a(2, new awc(this, aoz.class, 0, false, false, bF));
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(b, 0);
		this.S.a(c, 0);
		this.S.a(d, 0);
		this.S.a(bw, 0);
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.b("Invul", this.eM());
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.s(le.h("Invul"));
		if (this.Q()) {
			this.bE.a(this.d());
		}
	}

	@Override
	public void a(@Nullable mr mr) {
		super.a(mr);
		this.bE.a(this.d());
	}

	@Override
	protected ack I() {
		return acl.qJ;
	}

	@Override
	protected ack e(anw anw) {
		return acl.qM;
	}

	@Override
	protected ack dp() {
		return acl.qL;
	}

	@Override
	public void k() {
		dem dem2 = this.cB().d(1.0, 0.6, 1.0);
		if (!this.l.v && this.t(0) > 0) {
			aom aom3 = this.l.a(this.t(0));
			if (aom3 != null) {
				double double4 = dem2.c;
				if (this.cD() < aom3.cD() || !this.T_() && this.cD() < aom3.cD() + 5.0) {
					double4 = Math.max(0.0, double4);
					double4 += 0.3 - double4 * 0.6F;
				}

				dem2 = new dem(dem2.b, double4, dem2.d);
				dem dem6 = new dem(aom3.cC() - this.cC(), 0.0, aom3.cG() - this.cG());
				if (b(dem6) > 9.0) {
					dem dem7 = dem6.d();
					dem2 = dem2.b(dem7.b * 0.3 - dem2.b * 0.6, 0.0, dem7.d * 0.3 - dem2.d * 0.6);
				}
			}
		}

		this.e(dem2);
		if (b(dem2) > 0.05) {
			this.p = (float)aec.d(dem2.d, dem2.b) * (180.0F / (float)Math.PI) - 90.0F;
		}

		super.k();

		for (int integer3 = 0; integer3 < 2; integer3++) {
			this.bA[integer3] = this.by[integer3];
			this.bz[integer3] = this.bx[integer3];
		}

		for (int integer3 = 0; integer3 < 2; integer3++) {
			int integer4 = this.t(integer3 + 1);
			aom aom5 = null;
			if (integer4 > 0) {
				aom5 = this.l.a(integer4);
			}

			if (aom5 != null) {
				double double6 = this.u(integer3 + 1);
				double double8 = this.v(integer3 + 1);
				double double10 = this.w(integer3 + 1);
				double double12 = aom5.cC() - double6;
				double double14 = aom5.cF() - double8;
				double double16 = aom5.cG() - double10;
				double double18 = (double)aec.a(double12 * double12 + double16 * double16);
				float float20 = (float)(aec.d(double16, double12) * 180.0F / (float)Math.PI) - 90.0F;
				float float21 = (float)(-(aec.d(double14, double18) * 180.0F / (float)Math.PI));
				this.bx[integer3] = this.a(this.bx[integer3], float21, 40.0F);
				this.by[integer3] = this.a(this.by[integer3], float20, 10.0F);
			} else {
				this.by[integer3] = this.a(this.by[integer3], this.aH, 10.0F);
			}
		}

		boolean boolean3 = this.T_();

		for (int integer4x = 0; integer4x < 3; integer4x++) {
			double double5 = this.u(integer4x);
			double double7 = this.v(integer4x);
			double double9 = this.w(integer4x);
			this.l.a(hh.S, double5 + this.J.nextGaussian() * 0.3F, double7 + this.J.nextGaussian() * 0.3F, double9 + this.J.nextGaussian() * 0.3F, 0.0, 0.0, 0.0);
			if (boolean3 && this.l.t.nextInt(4) == 0) {
				this.l.a(hh.u, double5 + this.J.nextGaussian() * 0.3F, double7 + this.J.nextGaussian() * 0.3F, double9 + this.J.nextGaussian() * 0.3F, 0.7F, 0.7F, 0.5);
			}
		}

		if (this.eM() > 0) {
			for (int integer4xx = 0; integer4xx < 3; integer4xx++) {
				this.l.a(hh.u, this.cC() + this.J.nextGaussian(), this.cD() + (double)(this.J.nextFloat() * 3.3F), this.cG() + this.J.nextGaussian(), 0.7F, 0.7F, 0.9F);
			}
		}
	}

	@Override
	protected void N() {
		if (this.eM() > 0) {
			int integer2 = this.eM() - 1;
			if (integer2 <= 0) {
				bpt.a a3 = this.l.S().b(bpx.b) ? bpt.a.DESTROY : bpt.a.NONE;
				this.l.a(this, this.cC(), this.cF(), this.cG(), 7.0F, false, a3);
				if (!this.av()) {
					this.l.b(1023, this.cA(), 0);
				}
			}

			this.s(integer2);
			if (this.K % 10 == 0) {
				this.b(10.0F);
			}
		} else {
			super.N();

			for (int integer2x = 1; integer2x < 3; integer2x++) {
				if (this.K >= this.bB[integer2x - 1]) {
					this.bB[integer2x - 1] = this.K + 10 + this.J.nextInt(10);
					if ((this.l.ac() == and.NORMAL || this.l.ac() == and.HARD) && this.bC[integer2x - 1]++ > 15) {
						float float3 = 10.0F;
						float float4 = 5.0F;
						double double5 = aec.a(this.J, this.cC() - 10.0, this.cC() + 10.0);
						double double7 = aec.a(this.J, this.cD() - 5.0, this.cD() + 5.0);
						double double9 = aec.a(this.J, this.cG() - 10.0, this.cG() + 10.0);
						this.a(integer2x + 1, double5, double7, double9, true);
						this.bC[integer2x - 1] = 0;
					}

					int integer3 = this.t(integer2x);
					if (integer3 > 0) {
						aom aom4 = this.l.a(integer3);
						if (aom4 == null || !aom4.aU() || this.h(aom4) > 900.0 || !this.D(aom4)) {
							this.a(integer2x, 0);
						} else if (aom4 instanceof bec && ((bec)aom4).bJ.a) {
							this.a(integer2x, 0);
						} else {
							this.a(integer2x + 1, (aoy)aom4);
							this.bB[integer2x - 1] = this.K + 40 + this.J.nextInt(20);
							this.bC[integer2x - 1] = 0;
						}
					} else {
						List<aoy> list4 = this.l.a(aoy.class, bG, this, this.cb().c(20.0, 8.0, 20.0));

						for (int integer5 = 0; integer5 < 10 && !list4.isEmpty(); integer5++) {
							aoy aoy6 = (aoy)list4.get(this.J.nextInt(list4.size()));
							if (aoy6 != this && aoy6.aU() && this.D(aoy6)) {
								if (aoy6 instanceof bec) {
									if (!((bec)aoy6).bJ.a) {
										this.a(integer2x, aoy6.V());
									}
								} else {
									this.a(integer2x, aoy6.V());
								}
								break;
							}

							list4.remove(aoy6);
						}
					}
				}
			}

			if (this.A() != null) {
				this.a(0, this.A().V());
			} else {
				this.a(0, 0);
			}

			if (this.bD > 0) {
				this.bD--;
				if (this.bD == 0 && this.l.S().b(bpx.b)) {
					int integer2xx = aec.c(this.cD());
					int integer3 = aec.c(this.cC());
					int integer4 = aec.c(this.cG());
					boolean boolean5 = false;

					for (int integer6 = -1; integer6 <= 1; integer6++) {
						for (int integer7 = -1; integer7 <= 1; integer7++) {
							for (int integer8 = 0; integer8 <= 3; integer8++) {
								int integer9 = integer3 + integer6;
								int integer10 = integer2xx + integer8;
								int integer11 = integer4 + integer7;
								fu fu12 = new fu(integer9, integer10, integer11);
								cfj cfj13 = this.l.d_(fu12);
								if (c(cfj13)) {
									boolean5 = this.l.a(fu12, true, this) || boolean5;
								}
							}
						}
					}

					if (boolean5) {
						this.l.a(null, 1022, this.cA(), 0);
					}
				}
			}

			if (this.K % 20 == 0) {
				this.b(1.0F);
			}

			this.bE.a(this.dj() / this.dw());
		}
	}

	public static boolean c(cfj cfj) {
		return !cfj.g() && !acx.ag.a(cfj.b());
	}

	public void m() {
		this.s(220);
		this.c(this.dw() / 3.0F);
	}

	@Override
	public void a(cfj cfj, dem dem) {
	}

	@Override
	public void b(ze ze) {
		super.b(ze);
		this.bE.a(ze);
	}

	@Override
	public void c(ze ze) {
		super.c(ze);
		this.bE.b(ze);
	}

	private double u(int integer) {
		if (integer <= 0) {
			return this.cC();
		} else {
			float float3 = (this.aH + (float)(180 * (integer - 1))) * (float) (Math.PI / 180.0);
			float float4 = aec.b(float3);
			return this.cC() + (double)float4 * 1.3;
		}
	}

	private double v(int integer) {
		return integer <= 0 ? this.cD() + 3.0 : this.cD() + 2.2;
	}

	private double w(int integer) {
		if (integer <= 0) {
			return this.cG();
		} else {
			float float3 = (this.aH + (float)(180 * (integer - 1))) * (float) (Math.PI / 180.0);
			float float4 = aec.a(float3);
			return this.cG() + (double)float4 * 1.3;
		}
	}

	private float a(float float1, float float2, float float3) {
		float float5 = aec.g(float2 - float1);
		if (float5 > float3) {
			float5 = float3;
		}

		if (float5 < -float3) {
			float5 = -float3;
		}

		return float1 + float5;
	}

	private void a(int integer, aoy aoy) {
		this.a(integer, aoy.cC(), aoy.cD() + (double)aoy.cd() * 0.5, aoy.cG(), integer == 0 && this.J.nextFloat() < 0.001F);
	}

	private void a(int integer, double double2, double double3, double double4, boolean boolean5) {
		if (!this.av()) {
			this.l.a(null, 1024, this.cA(), 0);
		}

		double double10 = this.u(integer);
		double double12 = this.v(integer);
		double double14 = this.w(integer);
		double double16 = double2 - double10;
		double double18 = double3 - double12;
		double double20 = double4 - double14;
		bff bff22 = new bff(this.l, this, double16, double18, double20);
		bff22.b(this);
		if (boolean5) {
			bff22.a(true);
		}

		bff22.n(double10, double12, double14);
		this.l.c(bff22);
	}

	@Override
	public void a(aoy aoy, float float2) {
		this.a(0, aoy);
	}

	@Override
	public boolean a(anw anw, float float2) {
		if (this.b(anw)) {
			return false;
		} else if (anw == anw.h || anw.k() instanceof baw) {
			return false;
		} else if (this.eM() > 0 && anw != anw.m) {
			return false;
		} else {
			if (this.T_()) {
				aom aom4 = anw.j();
				if (aom4 instanceof beg) {
					return false;
				}
			}

			aom aom4 = anw.k();
			if (aom4 != null && !(aom4 instanceof bec) && aom4 instanceof aoy && ((aoy)aom4).dB() == this.dB()) {
				return false;
			} else {
				if (this.bD <= 0) {
					this.bD = 20;
				}

				for (int integer5 = 0; integer5 < this.bC.length; integer5++) {
					this.bC[integer5] = this.bC[integer5] + 3;
				}

				return super.a(anw, float2);
			}
		}
	}

	@Override
	protected void a(anw anw, int integer, boolean boolean3) {
		super.a(anw, integer, boolean3);
		bbg bbg5 = this.a(bkk.pl);
		if (bbg5 != null) {
			bbg5.r();
		}
	}

	@Override
	public void cH() {
		if (this.l.ac() == and.PEACEFUL && this.L()) {
			this.aa();
		} else {
			this.aP = 0;
		}
	}

	@Override
	public boolean b(float float1, float float2) {
		return false;
	}

	@Override
	public boolean c(aog aog) {
		return false;
	}

	public static apw.a eL() {
		return bcb.eS().a(apx.a, 300.0).a(apx.d, 0.6F).a(apx.b, 40.0).a(apx.i, 4.0);
	}

	public int eM() {
		return this.S.a(bw);
	}

	public void s(int integer) {
		this.S.b(bw, integer);
	}

	public int t(int integer) {
		return this.S.<Integer>a((tq<Integer>)bv.get(integer));
	}

	public void a(int integer1, int integer2) {
		this.S.b((tq<Integer>)bv.get(integer1), integer2);
	}

	public boolean T_() {
		return this.dj() <= this.dw() / 2.0F;
	}

	@Override
	public apc dB() {
		return apc.b;
	}

	@Override
	protected boolean n(aom aom) {
		return false;
	}

	@Override
	public boolean bK() {
		return false;
	}

	@Override
	public boolean d(aog aog) {
		return aog.a() == aoi.t ? false : super.d(aog);
	}

	class a extends aug {
		public a() {
			this.a(EnumSet.of(aug.a.MOVE, aug.a.JUMP, aug.a.LOOK));
		}

		@Override
		public boolean a() {
			return baw.this.eM() > 0;
		}
	}
}
