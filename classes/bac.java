import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class bac extends aoz implements bbt {
	private static final Logger bC = LogManager.getLogger();
	public static final tq<Integer> b = tt.a(bac.class, ts.b);
	private static final axs bD = new axs().a(64.0);
	public final double[][] c = new double[64][3];
	public int d = -1;
	private final baa[] bE;
	public final baa bv;
	private final baa bF;
	private final baa bG;
	private final baa bH;
	private final baa bI;
	private final baa bJ;
	private final baa bK;
	private final baa bL;
	public float bw;
	public float bx;
	public boolean by;
	public int bz;
	public float bA;
	@Nullable
	public bab bB;
	@Nullable
	private final cii bM;
	private final bat bN;
	private int bO = 100;
	private int bP;
	private final czd[] bQ = new czd[24];
	private final int[] bR = new int[24];
	private final cza bS = new cza();

	public bac(aoq<? extends bac> aoq, bqb bqb) {
		super(aoq.t, bqb);
		this.bv = new baa(this, "head", 1.0F, 1.0F);
		this.bF = new baa(this, "neck", 3.0F, 3.0F);
		this.bG = new baa(this, "body", 5.0F, 3.0F);
		this.bH = new baa(this, "tail", 2.0F, 2.0F);
		this.bI = new baa(this, "tail", 2.0F, 2.0F);
		this.bJ = new baa(this, "tail", 2.0F, 2.0F);
		this.bK = new baa(this, "wing", 4.0F, 2.0F);
		this.bL = new baa(this, "wing", 4.0F, 2.0F);
		this.bE = new baa[]{this.bv, this.bF, this.bG, this.bH, this.bI, this.bJ, this.bK, this.bL};
		this.c(this.dw());
		this.H = true;
		this.ac = true;
		if (bqb instanceof zd) {
			this.bM = ((zd)bqb).C();
		} else {
			this.bM = null;
		}

		this.bN = new bat(this);
	}

	public static apw.a m() {
		return aoz.p().a(apx.a, 200.0);
	}

	@Override
	protected void e() {
		super.e();
		this.Y().a(b, bas.k.b());
	}

	public double[] a(int integer, float float2) {
		if (this.dk()) {
			float2 = 0.0F;
		}

		float2 = 1.0F - float2;
		int integer4 = this.d - integer & 63;
		int integer5 = this.d - integer - 1 & 63;
		double[] arr6 = new double[3];
		double double7 = this.c[integer4][0];
		double double9 = aec.g(this.c[integer5][0] - double7);
		arr6[0] = double7 + double9 * (double)float2;
		double7 = this.c[integer4][1];
		double9 = this.c[integer5][1] - double7;
		arr6[1] = double7 + double9 * (double)float2;
		arr6[2] = aec.d((double)float2, this.c[integer4][2], this.c[integer5][2]);
		return arr6;
	}

	@Override
	public void k() {
		if (this.l.v) {
			this.c(this.dj());
			if (!this.av()) {
				float float2 = aec.b(this.bx * (float) (Math.PI * 2));
				float float3 = aec.b(this.bw * (float) (Math.PI * 2));
				if (float3 <= -0.3F && float2 >= -0.3F) {
					this.l.a(this.cC(), this.cD(), this.cG(), acl.ds, this.ct(), 5.0F, 0.8F + this.J.nextFloat() * 0.3F, false);
				}

				if (!this.bN.a().a() && --this.bO < 0) {
					this.l.a(this.cC(), this.cD(), this.cG(), acl.dt, this.ct(), 2.5F, 0.8F + this.J.nextFloat() * 0.3F, false);
					this.bO = 200 + this.J.nextInt(200);
				}
			}
		}

		this.bw = this.bx;
		if (this.dk()) {
			float float2x = (this.J.nextFloat() - 0.5F) * 8.0F;
			float float3x = (this.J.nextFloat() - 0.5F) * 4.0F;
			float float4 = (this.J.nextFloat() - 0.5F) * 8.0F;
			this.l.a(hh.w, this.cC() + (double)float2x, this.cD() + 2.0 + (double)float3x, this.cG() + (double)float4, 0.0, 0.0, 0.0);
		} else {
			this.eO();
			dem dem2 = this.cB();
			float float3x = 0.2F / (aec.a(b(dem2)) * 10.0F + 1.0F);
			float3x *= (float)Math.pow(2.0, dem2.c);
			if (this.bN.a().a()) {
				this.bx += 0.1F;
			} else if (this.by) {
				this.bx += float3x * 0.5F;
			} else {
				this.bx += float3x;
			}

			this.p = aec.g(this.p);
			if (this.eE()) {
				this.bx = 0.5F;
			} else {
				if (this.d < 0) {
					for (int integer4 = 0; integer4 < this.c.length; integer4++) {
						this.c[integer4][0] = (double)this.p;
						this.c[integer4][1] = this.cD();
					}
				}

				if (++this.d == this.c.length) {
					this.d = 0;
				}

				this.c[this.d][0] = (double)this.p;
				this.c[this.d][1] = this.cD();
				if (this.l.v) {
					if (this.bb > 0) {
						double double4 = this.cC() + (this.bc - this.cC()) / (double)this.bb;
						double double6 = this.cD() + (this.bd - this.cD()) / (double)this.bb;
						double double8 = this.cG() + (this.be - this.cG()) / (double)this.bb;
						double double10 = aec.g(this.bf - (double)this.p);
						this.p = (float)((double)this.p + double10 / (double)this.bb);
						this.q = (float)((double)this.q + (this.bg - (double)this.q) / (double)this.bb);
						this.bb--;
						this.d(double4, double6, double8);
						this.a(this.p, this.q);
					}

					this.bN.a().b();
				} else {
					bam bam4 = this.bN.a();
					bam4.c();
					if (this.bN.a() != bam4) {
						bam4 = this.bN.a();
						bam4.c();
					}

					dem dem5 = bam4.g();
					if (dem5 != null) {
						double double6 = dem5.b - this.cC();
						double double8 = dem5.c - this.cD();
						double double10 = dem5.d - this.cG();
						double double12 = double6 * double6 + double8 * double8 + double10 * double10;
						float float14 = bam4.f();
						double double15 = (double)aec.a(double6 * double6 + double10 * double10);
						if (double15 > 0.0) {
							double8 = aec.a(double8 / double15, (double)(-float14), (double)float14);
						}

						this.e(this.cB().b(0.0, double8 * 0.01, 0.0));
						this.p = aec.g(this.p);
						double double17 = aec.a(aec.g(180.0 - aec.d(double6, double10) * 180.0F / (float)Math.PI - (double)this.p), -50.0, 50.0);
						dem dem19 = dem5.a(this.cC(), this.cD(), this.cG()).d();
						dem dem20 = new dem((double)aec.a(this.p * (float) (Math.PI / 180.0)), this.cB().c, (double)(-aec.b(this.p * (float) (Math.PI / 180.0)))).d();
						float float21 = Math.max(((float)dem20.b(dem19) + 0.5F) / 1.5F, 0.0F);
						this.bA *= 0.8F;
						this.bA = (float)((double)this.bA + double17 * (double)bam4.h());
						this.p = this.p + this.bA * 0.1F;
						float float22 = (float)(2.0 / (double12 + 1.0));
						float float23 = 0.06F;
						this.a(0.06F * (float21 * float22 + (1.0F - float22)), new dem(0.0, 0.0, -1.0));
						if (this.by) {
							this.a(apd.SELF, this.cB().a(0.8F));
						} else {
							this.a(apd.SELF, this.cB());
						}

						dem dem24 = this.cB().d();
						double double25 = 0.8 + 0.15 * (dem24.b(dem20) + 1.0) / 2.0;
						this.e(this.cB().d(double25, 0.91F, double25));
					}
				}

				this.aH = this.p;
				dem[] arr4 = new dem[this.bE.length];

				for (int integer5 = 0; integer5 < this.bE.length; integer5++) {
					arr4[integer5] = new dem(this.bE[integer5].cC(), this.bE[integer5].cD(), this.bE[integer5].cG());
				}

				float float5 = (float)(this.a(5, 1.0F)[1] - this.a(10, 1.0F)[1]) * 10.0F * (float) (Math.PI / 180.0);
				float float6 = aec.b(float5);
				float float7 = aec.a(float5);
				float float8 = this.p * (float) (Math.PI / 180.0);
				float float9 = aec.a(float8);
				float float10 = aec.b(float8);
				this.a(this.bG, (double)(float9 * 0.5F), 0.0, (double)(-float10 * 0.5F));
				this.a(this.bK, (double)(float10 * 4.5F), 2.0, (double)(float9 * 4.5F));
				this.a(this.bL, (double)(float10 * -4.5F), 2.0, (double)(float9 * -4.5F));
				if (!this.l.v && this.au == 0) {
					this.a(this.l.a(this, this.bK.cb().c(4.0, 2.0, 4.0).d(0.0, -2.0, 0.0), aop.e));
					this.a(this.l.a(this, this.bL.cb().c(4.0, 2.0, 4.0).d(0.0, -2.0, 0.0), aop.e));
					this.b(this.l.a(this, this.bv.cb().g(1.0), aop.e));
					this.b(this.l.a(this, this.bF.cb().g(1.0), aop.e));
				}

				float float11 = aec.a(this.p * (float) (Math.PI / 180.0) - this.bA * 0.01F);
				float float12 = aec.b(this.p * (float) (Math.PI / 180.0) - this.bA * 0.01F);
				float float13 = this.eN();
				this.a(this.bv, (double)(float11 * 6.5F * float6), (double)(float13 + float7 * 6.5F), (double)(-float12 * 6.5F * float6));
				this.a(this.bF, (double)(float11 * 5.5F * float6), (double)(float13 + float7 * 5.5F), (double)(-float12 * 5.5F * float6));
				double[] arr14 = this.a(5, 1.0F);

				for (int integer15 = 0; integer15 < 3; integer15++) {
					baa baa16 = null;
					if (integer15 == 0) {
						baa16 = this.bH;
					}

					if (integer15 == 1) {
						baa16 = this.bI;
					}

					if (integer15 == 2) {
						baa16 = this.bJ;
					}

					double[] arr17 = this.a(12 + integer15 * 2, 1.0F);
					float float18 = this.p * (float) (Math.PI / 180.0) + this.i(arr17[0] - arr14[0]) * (float) (Math.PI / 180.0);
					float float19 = aec.a(float18);
					float float20 = aec.b(float18);
					float float21 = 1.5F;
					float float22 = (float)(integer15 + 1) * 2.0F;
					this.a(
						baa16,
						(double)(-(float9 * 1.5F + float19 * float22) * float6),
						arr17[1] - arr14[1] - (double)((float22 + 1.5F) * float7) + 1.5,
						(double)((float10 * 1.5F + float20 * float22) * float6)
					);
				}

				if (!this.l.v) {
					this.by = this.b(this.bv.cb()) | this.b(this.bF.cb()) | this.b(this.bG.cb());
					if (this.bM != null) {
						this.bM.b(this);
					}
				}

				for (int integer15 = 0; integer15 < this.bE.length; integer15++) {
					this.bE[integer15].m = arr4[integer15].b;
					this.bE[integer15].n = arr4[integer15].c;
					this.bE[integer15].o = arr4[integer15].d;
					this.bE[integer15].D = arr4[integer15].b;
					this.bE[integer15].E = arr4[integer15].c;
					this.bE[integer15].F = arr4[integer15].d;
				}
			}
		}
	}

	private void a(baa baa, double double2, double double3, double double4) {
		baa.d(this.cC() + double2, this.cD() + double3, this.cG() + double4);
	}

	private float eN() {
		if (this.bN.a().a()) {
			return -1.0F;
		} else {
			double[] arr2 = this.a(5, 1.0F);
			double[] arr3 = this.a(0, 1.0F);
			return (float)(arr2[1] - arr3[1]);
		}
	}

	private void eO() {
		if (this.bB != null) {
			if (this.bB.y) {
				this.bB = null;
			} else if (this.K % 10 == 0 && this.dj() < this.dw()) {
				this.c(this.dj() + 1.0F);
			}
		}

		if (this.J.nextInt(10) == 0) {
			List<bab> list2 = this.l.a(bab.class, this.cb().g(32.0));
			bab bab3 = null;
			double double4 = Double.MAX_VALUE;

			for (bab bab7 : list2) {
				double double8 = bab7.h(this);
				if (double8 < double4) {
					double4 = double8;
					bab3 = bab7;
				}
			}

			this.bB = bab3;
		}
	}

	private void a(List<aom> list) {
		double double3 = (this.bG.cb().a + this.bG.cb().d) / 2.0;
		double double5 = (this.bG.cb().c + this.bG.cb().f) / 2.0;

		for (aom aom8 : list) {
			if (aom8 instanceof aoy) {
				double double9 = aom8.cC() - double3;
				double double11 = aom8.cG() - double5;
				double double13 = double9 * double9 + double11 * double11;
				aom8.h(double9 / double13 * 4.0, 0.2F, double11 / double13 * 4.0);
				if (!this.bN.a().a() && ((aoy)aom8).cZ() < aom8.K - 2) {
					aom8.a(anw.c(this), 5.0F);
					this.a(this, aom8);
				}
			}
		}
	}

	private void b(List<aom> list) {
		for (aom aom4 : list) {
			if (aom4 instanceof aoy) {
				aom4.a(anw.c(this), 10.0F);
				this.a(this, aom4);
			}
		}
	}

	private float i(double double1) {
		return (float)aec.g(double1);
	}

	private boolean b(deg deg) {
		int integer3 = aec.c(deg.a);
		int integer4 = aec.c(deg.b);
		int integer5 = aec.c(deg.c);
		int integer6 = aec.c(deg.d);
		int integer7 = aec.c(deg.e);
		int integer8 = aec.c(deg.f);
		boolean boolean9 = false;
		boolean boolean10 = false;

		for (int integer11 = integer3; integer11 <= integer6; integer11++) {
			for (int integer12 = integer4; integer12 <= integer7; integer12++) {
				for (int integer13 = integer5; integer13 <= integer8; integer13++) {
					fu fu14 = new fu(integer11, integer12, integer13);
					cfj cfj15 = this.l.d_(fu14);
					bvr bvr16 = cfj15.b();
					if (!cfj15.g() && cfj15.c() != cxd.m) {
						if (this.l.S().b(bpx.b) && !acx.af.a(bvr16)) {
							boolean10 = this.l.a(fu14, false) || boolean10;
						} else {
							boolean9 = true;
						}
					}
				}
			}
		}

		if (boolean10) {
			fu fu11 = new fu(
				integer3 + this.J.nextInt(integer6 - integer3 + 1), integer4 + this.J.nextInt(integer7 - integer4 + 1), integer5 + this.J.nextInt(integer8 - integer5 + 1)
			);
			this.l.c(2008, fu11, 0);
		}

		return boolean9;
	}

	public boolean a(baa baa, anw anw, float float3) {
		if (this.bN.a().i() == bas.j) {
			return false;
		} else {
			float3 = this.bN.a().a(anw, float3);
			if (baa != this.bv) {
				float3 = float3 / 4.0F + Math.min(float3, 1.0F);
			}

			if (float3 < 0.01F) {
				return false;
			} else {
				if (anw.k() instanceof bec || anw.d()) {
					float float5 = this.dj();
					this.f(anw, float3);
					if (this.dk() && !this.bN.a().a()) {
						this.c(1.0F);
						this.bN.a(bas.j);
					}

					if (this.bN.a().a()) {
						this.bP = (int)((float)this.bP + (float5 - this.dj()));
						if ((float)this.bP > 0.25F * this.dw()) {
							this.bP = 0;
							this.bN.a(bas.e);
						}
					}
				}

				return true;
			}
		}
	}

	@Override
	public boolean a(anw anw, float float2) {
		if (anw instanceof anx && ((anx)anw).y()) {
			this.a(this.bG, anw, float2);
		}

		return false;
	}

	protected boolean f(anw anw, float float2) {
		return super.a(anw, float2);
	}

	@Override
	public void X() {
		this.aa();
		if (this.bM != null) {
			this.bM.b(this);
			this.bM.a(this);
		}
	}

	@Override
	protected void cT() {
		if (this.bM != null) {
			this.bM.b(this);
		}

		this.bz++;
		if (this.bz >= 180 && this.bz <= 200) {
			float float2 = (this.J.nextFloat() - 0.5F) * 8.0F;
			float float3 = (this.J.nextFloat() - 0.5F) * 4.0F;
			float float4 = (this.J.nextFloat() - 0.5F) * 8.0F;
			this.l.a(hh.v, this.cC() + (double)float2, this.cD() + 2.0 + (double)float3, this.cG() + (double)float4, 0.0, 0.0, 0.0);
		}

		boolean boolean2 = this.l.S().b(bpx.e);
		int integer3 = 500;
		if (this.bM != null && !this.bM.d()) {
			integer3 = 12000;
		}

		if (!this.l.v) {
			if (this.bz > 150 && this.bz % 5 == 0 && boolean2) {
				this.a(aec.d((float)integer3 * 0.08F));
			}

			if (this.bz == 1 && !this.av()) {
				this.l.b(1028, this.cA(), 0);
			}
		}

		this.a(apd.SELF, new dem(0.0, 0.1F, 0.0));
		this.p += 20.0F;
		this.aH = this.p;
		if (this.bz == 200 && !this.l.v) {
			if (boolean2) {
				this.a(aec.d((float)integer3 * 0.2F));
			}

			if (this.bM != null) {
				this.bM.a(this);
			}

			this.aa();
		}
	}

	private void a(int integer) {
		while (integer > 0) {
			int integer3 = aos.a(integer);
			integer -= integer3;
			this.l.c(new aos(this.l, this.cC(), this.cD(), this.cG(), integer3));
		}
	}

	public int eJ() {
		if (this.bQ[0] == null) {
			for (int integer2 = 0; integer2 < 24; integer2++) {
				int integer3 = 5;
				int integer5;
				int integer6;
				if (integer2 < 12) {
					integer5 = aec.d(60.0F * aec.b(2.0F * ((float) -Math.PI + (float) (Math.PI / 12) * (float)integer2)));
					integer6 = aec.d(60.0F * aec.a(2.0F * ((float) -Math.PI + (float) (Math.PI / 12) * (float)integer2)));
				} else if (integer2 < 20) {
					int integer4 = integer2 - 12;
					integer5 = aec.d(40.0F * aec.b(2.0F * ((float) -Math.PI + (float) (Math.PI / 8) * (float)integer4)));
					integer6 = aec.d(40.0F * aec.a(2.0F * ((float) -Math.PI + (float) (Math.PI / 8) * (float)integer4)));
					integer3 += 10;
				} else {
					int var7 = integer2 - 20;
					integer5 = aec.d(20.0F * aec.b(2.0F * ((float) -Math.PI + (float) (Math.PI / 4) * (float)var7)));
					integer6 = aec.d(20.0F * aec.a(2.0F * ((float) -Math.PI + (float) (Math.PI / 4) * (float)var7)));
				}

				int integer7 = Math.max(this.l.t_() + 10, this.l.a(cio.a.MOTION_BLOCKING_NO_LEAVES, new fu(integer5, 0, integer6)).v() + integer3);
				this.bQ[integer2] = new czd(integer5, integer7, integer6);
			}

			this.bR[0] = 6146;
			this.bR[1] = 8197;
			this.bR[2] = 8202;
			this.bR[3] = 16404;
			this.bR[4] = 32808;
			this.bR[5] = 32848;
			this.bR[6] = 65696;
			this.bR[7] = 131392;
			this.bR[8] = 131712;
			this.bR[9] = 263424;
			this.bR[10] = 526848;
			this.bR[11] = 525313;
			this.bR[12] = 1581057;
			this.bR[13] = 3166214;
			this.bR[14] = 2138120;
			this.bR[15] = 6373424;
			this.bR[16] = 4358208;
			this.bR[17] = 12910976;
			this.bR[18] = 9044480;
			this.bR[19] = 9706496;
			this.bR[20] = 15216640;
			this.bR[21] = 13688832;
			this.bR[22] = 11763712;
			this.bR[23] = 8257536;
		}

		return this.o(this.cC(), this.cD(), this.cG());
	}

	public int o(double double1, double double2, double double3) {
		float float8 = 10000.0F;
		int integer9 = 0;
		czd czd10 = new czd(aec.c(double1), aec.c(double2), aec.c(double3));
		int integer11 = 0;
		if (this.bM == null || this.bM.c() == 0) {
			integer11 = 12;
		}

		for (int integer12 = integer11; integer12 < 24; integer12++) {
			if (this.bQ[integer12] != null) {
				float float13 = this.bQ[integer12].b(czd10);
				if (float13 < float8) {
					float8 = float13;
					integer9 = integer12;
				}
			}
		}

		return integer9;
	}

	@Nullable
	public czf a(int integer1, int integer2, @Nullable czd czd) {
		for (int integer5 = 0; integer5 < 24; integer5++) {
			czd czd6 = this.bQ[integer5];
			czd6.i = false;
			czd6.g = 0.0F;
			czd6.e = 0.0F;
			czd6.f = 0.0F;
			czd6.h = null;
			czd6.d = -1;
		}

		czd czd5 = this.bQ[integer1];
		czd czd6 = this.bQ[integer2];
		czd5.e = 0.0F;
		czd5.f = czd5.a(czd6);
		czd5.g = czd5.f;
		this.bS.a();
		this.bS.a(czd5);
		czd czd7 = czd5;
		int integer8 = 0;
		if (this.bM == null || this.bM.c() == 0) {
			integer8 = 12;
		}

		while (!this.bS.e()) {
			czd czd9 = this.bS.c();
			if (czd9.equals(czd6)) {
				if (czd != null) {
					czd.h = czd6;
					czd6 = czd;
				}

				return this.a(czd5, czd6);
			}

			if (czd9.a(czd6) < czd7.a(czd6)) {
				czd7 = czd9;
			}

			czd9.i = true;
			int integer10 = 0;

			for (int integer11 = 0; integer11 < 24; integer11++) {
				if (this.bQ[integer11] == czd9) {
					integer10 = integer11;
					break;
				}
			}

			for (int integer11x = integer8; integer11x < 24; integer11x++) {
				if ((this.bR[integer10] & 1 << integer11x) > 0) {
					czd czd12 = this.bQ[integer11x];
					if (!czd12.i) {
						float float13 = czd9.e + czd9.a(czd12);
						if (!czd12.c() || float13 < czd12.e) {
							czd12.h = czd9;
							czd12.e = float13;
							czd12.f = czd12.a(czd6);
							if (czd12.c()) {
								this.bS.a(czd12, czd12.e + czd12.f);
							} else {
								czd12.g = czd12.e + czd12.f;
								this.bS.a(czd12);
							}
						}
					}
				}
			}
		}

		if (czd7 == czd5) {
			return null;
		} else {
			bC.debug("Failed to find path from {} to {}", integer1, integer2);
			if (czd != null) {
				czd.h = czd7;
				czd7 = czd;
			}

			return this.a(czd5, czd7);
		}
	}

	private czf a(czd czd1, czd czd2) {
		List<czd> list4 = Lists.<czd>newArrayList();
		czd czd5 = czd2;
		list4.add(0, czd2);

		while (czd5.h != null) {
			czd5 = czd5.h;
			list4.add(0, czd5);
		}

		return new czf(list4, new fu(czd2.a, czd2.b, czd2.c), true);
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.b("DragonPhase", this.bN.a().i().b());
	}

	@Override
	public void a(le le) {
		super.a(le);
		if (le.e("DragonPhase")) {
			this.bN.a(bas.a(le.h("DragonPhase")));
		}
	}

	@Override
	public void cH() {
	}

	public baa[] eK() {
		return this.bE;
	}

	@Override
	public boolean aQ() {
		return false;
	}

	@Override
	public acm ct() {
		return acm.HOSTILE;
	}

	@Override
	protected ack I() {
		return acl.dp;
	}

	@Override
	protected ack e(anw anw) {
		return acl.du;
	}

	@Override
	protected float dF() {
		return 5.0F;
	}

	public dem t(float float1) {
		bam bam3 = this.bN.a();
		bas<? extends bam> bas4 = bam3.i();
		dem dem5;
		if (bas4 == bas.d || bas4 == bas.e) {
			fu fu6 = this.l.a(cio.a.MOTION_BLOCKING_NO_LEAVES, cks.a);
			float float7 = Math.max(aec.a(fu6.a(this.cz(), true)) / 4.0F, 1.0F);
			float float8 = 6.0F / float7;
			float float9 = this.q;
			float float10 = 1.5F;
			this.q = -float8 * 1.5F * 5.0F;
			dem5 = this.f(float1);
			this.q = float9;
		} else if (bam3.a()) {
			float float6 = this.q;
			float float7 = 1.5F;
			this.q = -45.0F;
			dem5 = this.f(float1);
			this.q = float6;
		} else {
			dem5 = this.f(float1);
		}

		return dem5;
	}

	public void a(bab bab, fu fu, anw anw) {
		bec bec5;
		if (anw.k() instanceof bec) {
			bec5 = (bec)anw.k();
		} else {
			bec5 = this.l.a(bD, (double)fu.u(), (double)fu.v(), (double)fu.w());
		}

		if (bab == this.bB) {
			this.a(this.bv, anw.d(bec5), 10.0F);
		}

		this.bN.a().a(bab, fu, anw, bec5);
	}

	@Override
	public void a(tq<?> tq) {
		if (b.equals(tq) && this.l.v) {
			this.bN.a(bas.a(this.Y().a(b)));
		}

		super.a(tq);
	}

	public bat eL() {
		return this.bN;
	}

	@Nullable
	public cii eM() {
		return this.bM;
	}

	@Override
	public boolean c(aog aog) {
		return false;
	}

	@Override
	protected boolean n(aom aom) {
		return false;
	}

	@Override
	public boolean bK() {
		return false;
	}
}
