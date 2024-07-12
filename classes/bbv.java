import java.util.EnumSet;
import java.util.Random;

public class bbv extends aot implements bbt {
	private static final tq<Boolean> b = tt.a(bbv.class, ts.i);
	private int c = 1;

	public bbv(aoq<? extends bbv> aoq, bqb bqb) {
		super(aoq, bqb);
		this.f = 5;
		this.bo = new bbv.b(this);
	}

	@Override
	protected void o() {
		this.br.a(5, new bbv.d(this));
		this.br.a(7, new bbv.a(this));
		this.br.a(7, new bbv.c(this));
		this.bs.a(1, new awc(this, bec.class, 10, true, false, aoy -> Math.abs(aoy.cD() - this.cD()) <= 4.0));
	}

	public void t(boolean boolean1) {
		this.S.b(b, boolean1);
	}

	public int eJ() {
		return this.c;
	}

	@Override
	protected boolean L() {
		return true;
	}

	@Override
	public boolean a(anw anw, float float2) {
		if (this.b(anw)) {
			return false;
		} else if (anw.j() instanceof beq && anw.k() instanceof bec) {
			super.a(anw, 1000.0F);
			return true;
		} else {
			return super.a(anw, float2);
		}
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(b, false);
	}

	public static apw.a eK() {
		return aoz.p().a(apx.a, 10.0).a(apx.b, 100.0);
	}

	@Override
	public acm ct() {
		return acm.HOSTILE;
	}

	@Override
	protected ack I() {
		return acl.eR;
	}

	@Override
	protected ack e(anw anw) {
		return acl.eT;
	}

	@Override
	protected ack dp() {
		return acl.eS;
	}

	@Override
	protected float dF() {
		return 5.0F;
	}

	public static boolean b(aoq<bbv> aoq, bqc bqc, apb apb, fu fu, Random random) {
		return bqc.ac() != and.PEACEFUL && random.nextInt(20) == 0 && a(aoq, bqc, apb, fu, random);
	}

	@Override
	public int er() {
		return 1;
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.b("ExplosionPower", this.c);
	}

	@Override
	public void a(le le) {
		super.a(le);
		if (le.c("ExplosionPower", 99)) {
			this.c = le.h("ExplosionPower");
		}
	}

	@Override
	protected float b(apj apj, aon aon) {
		return 2.6F;
	}

	static class a extends aug {
		private final bbv a;

		public a(bbv bbv) {
			this.a = bbv;
			this.a(EnumSet.of(aug.a.LOOK));
		}

		@Override
		public boolean a() {
			return true;
		}

		@Override
		public void e() {
			if (this.a.A() == null) {
				dem dem2 = this.a.cB();
				this.a.p = -((float)aec.d(dem2.b, dem2.d)) * (180.0F / (float)Math.PI);
				this.a.aH = this.a.p;
			} else {
				aoy aoy2 = this.a.A();
				double double3 = 64.0;
				if (aoy2.h(this.a) < 4096.0) {
					double double5 = aoy2.cC() - this.a.cC();
					double double7 = aoy2.cG() - this.a.cG();
					this.a.p = -((float)aec.d(double5, double7)) * (180.0F / (float)Math.PI);
					this.a.aH = this.a.p;
				}
			}
		}
	}

	static class b extends atm {
		private final bbv i;
		private int j;

		public b(bbv bbv) {
			super(bbv);
			this.i = bbv;
		}

		@Override
		public void a() {
			if (this.h == atm.a.MOVE_TO) {
				if (this.j-- <= 0) {
					this.j = this.j + this.i.cX().nextInt(5) + 2;
					dem dem2 = new dem(this.b - this.i.cC(), this.c - this.i.cD(), this.d - this.i.cG());
					double double3 = dem2.f();
					dem2 = dem2.d();
					if (this.a(dem2, aec.f(double3))) {
						this.i.e(this.i.cB().e(dem2.a(0.1)));
					} else {
						this.h = atm.a.WAIT;
					}
				}
			}
		}

		private boolean a(dem dem, int integer) {
			deg deg4 = this.i.cb();

			for (int integer5 = 1; integer5 < integer; integer5++) {
				deg4 = deg4.c(dem);
				if (!this.i.l.a_(this.i, deg4)) {
					return false;
				}
			}

			return true;
		}
	}

	static class c extends aug {
		private final bbv b;
		public int a;

		public c(bbv bbv) {
			this.b = bbv;
		}

		@Override
		public boolean a() {
			return this.b.A() != null;
		}

		@Override
		public void c() {
			this.a = 0;
		}

		@Override
		public void d() {
			this.b.t(false);
		}

		@Override
		public void e() {
			aoy aoy2 = this.b.A();
			double double3 = 64.0;
			if (aoy2.h(this.b) < 4096.0 && this.b.D(aoy2)) {
				bqb bqb5 = this.b.l;
				this.a++;
				if (this.a == 10 && !this.b.av()) {
					bqb5.a(null, 1015, this.b.cA(), 0);
				}

				if (this.a == 20) {
					double double6 = 4.0;
					dem dem8 = this.b.f(1.0F);
					double double9 = aoy2.cC() - (this.b.cC() + dem8.b * 4.0);
					double double11 = aoy2.e(0.5) - (0.5 + this.b.e(0.5));
					double double13 = aoy2.cG() - (this.b.cG() + dem8.d * 4.0);
					if (!this.b.av()) {
						bqb5.a(null, 1016, this.b.cA(), 0);
					}

					beq beq15 = new beq(bqb5, this.b, double9, double11, double13);
					beq15.e = this.b.eJ();
					beq15.d(this.b.cC() + dem8.b * 4.0, this.b.e(0.5) + 0.5, beq15.cG() + dem8.d * 4.0);
					bqb5.c(beq15);
					this.a = -40;
				}
			} else if (this.a > 0) {
				this.a--;
			}

			this.b.t(this.a > 10);
		}
	}

	static class d extends aug {
		private final bbv a;

		public d(bbv bbv) {
			this.a = bbv;
			this.a(EnumSet.of(aug.a.MOVE));
		}

		@Override
		public boolean a() {
			atm atm2 = this.a.u();
			if (!atm2.b()) {
				return true;
			} else {
				double double3 = atm2.d() - this.a.cC();
				double double5 = atm2.e() - this.a.cD();
				double double7 = atm2.f() - this.a.cG();
				double double9 = double3 * double3 + double5 * double5 + double7 * double7;
				return double9 < 1.0 || double9 > 3600.0;
			}
		}

		@Override
		public boolean b() {
			return false;
		}

		@Override
		public void c() {
			Random random2 = this.a.cX();
			double double3 = this.a.cC() + (double)((random2.nextFloat() * 2.0F - 1.0F) * 16.0F);
			double double5 = this.a.cD() + (double)((random2.nextFloat() * 2.0F - 1.0F) * 16.0F);
			double double7 = this.a.cG() + (double)((random2.nextFloat() * 2.0F - 1.0F) * 16.0F);
			this.a.u().a(double3, double5, double7, 1.0);
		}
	}
}
