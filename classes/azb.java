import java.util.Random;
import javax.annotation.Nullable;

public class azb extends ayk {
	private static final tq<Integer> bv = tt.a(azb.class, ts.b);
	private static final uh bw = new uh("killer_bunny");
	private int bx;
	private int by;
	private boolean bz;
	private int bA;
	private int bB;

	public azb(aoq<? extends azb> aoq, bqb bqb) {
		super(aoq, bqb);
		this.bp = new azb.d(this);
		this.bo = new azb.e(this);
		this.i(0.0);
	}

	@Override
	protected void o() {
		this.br.a(1, new aua(this));
		this.br.a(1, new azb.f(this, 2.2));
		this.br.a(2, new att(this, 0.8));
		this.br.a(3, new avr(this, 1.0, bmr.a(bkk.oX, bkk.pc, bvs.bp), false));
		this.br.a(4, new azb.b(this, bec.class, 8.0F, 2.2, 2.2));
		this.br.a(4, new azb.b(this, azk.class, 10.0F, 2.2, 2.2));
		this.br.a(4, new azb.b(this, bcb.class, 4.0F, 2.2, 2.2));
		this.br.a(5, new azb.g(this));
		this.br.a(6, new avw(this, 0.6));
		this.br.a(11, new auo(this, bec.class, 10.0F));
	}

	@Override
	protected float dI() {
		if (!this.u && (!this.bo.b() || !(this.bo.e() > this.cD() + 0.5))) {
			czf czf2 = this.bq.k();
			if (czf2 != null && czf2.f() < czf2.e()) {
				dem dem3 = czf2.a(this);
				if (dem3.c > this.cD() + 0.5) {
					return 0.5F;
				}
			}

			return this.bo.c() <= 0.6 ? 0.2F : 0.3F;
		} else {
			return 0.5F;
		}
	}

	@Override
	protected void dJ() {
		super.dJ();
		double double2 = this.bo.c();
		if (double2 > 0.0) {
			double double4 = b(this.cB());
			if (double4 < 0.01) {
				this.a(0.1F, new dem(0.0, 0.0, 1.0));
			}
		}

		if (!this.l.v) {
			this.l.a(this, (byte)1);
		}
	}

	public void i(double double1) {
		this.x().a(double1);
		this.bo.a(this.bo.d(), this.bo.e(), this.bo.f(), double1);
	}

	@Override
	public void o(boolean boolean1) {
		super.o(boolean1);
		if (boolean1) {
			this.a(this.eN(), this.dF(), ((this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F) * 0.8F);
		}
	}

	public void eL() {
		this.o(true);
		this.by = 10;
		this.bx = 0;
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(bv, 0);
	}

	@Override
	public void N() {
		if (this.bA > 0) {
			this.bA--;
		}

		if (this.bB > 0) {
			this.bB = this.bB - this.J.nextInt(3);
			if (this.bB < 0) {
				this.bB = 0;
			}
		}

		if (this.t) {
			if (!this.bz) {
				this.o(false);
				this.eX();
			}

			if (this.eO() == 99 && this.bA == 0) {
				aoy aoy2 = this.A();
				if (aoy2 != null && this.h((aom)aoy2) < 16.0) {
					this.b(aoy2.cC(), aoy2.cG());
					this.bo.a(aoy2.cC(), aoy2.cD(), aoy2.cG(), this.bo.c());
					this.eL();
					this.bz = true;
				}
			}

			azb.d d2 = (azb.d)this.bp;
			if (!d2.c()) {
				if (this.bo.b() && this.bA == 0) {
					czf czf3 = this.bq.k();
					dem dem4 = new dem(this.bo.d(), this.bo.e(), this.bo.f());
					if (czf3 != null && czf3.f() < czf3.e()) {
						dem4 = czf3.a(this);
					}

					this.b(dem4.b, dem4.d);
					this.eL();
				}
			} else if (!d2.d()) {
				this.eP();
			}
		}

		this.bz = this.t;
	}

	@Override
	public boolean aK() {
		return false;
	}

	private void b(double double1, double double2) {
		this.p = (float)(aec.d(double2 - this.cG(), double1 - this.cC()) * 180.0F / (float)Math.PI) - 90.0F;
	}

	private void eP() {
		((azb.d)this.bp).a(true);
	}

	private void eV() {
		((azb.d)this.bp).a(false);
	}

	private void eW() {
		if (this.bo.c() < 2.2) {
			this.bA = 10;
		} else {
			this.bA = 1;
		}
	}

	private void eX() {
		this.eW();
		this.eV();
	}

	@Override
	public void k() {
		super.k();
		if (this.bx != this.by) {
			this.bx++;
		} else if (this.by != 0) {
			this.bx = 0;
			this.by = 0;
			this.o(false);
		}
	}

	public static apw.a eM() {
		return aoz.p().a(apx.a, 3.0).a(apx.d, 0.3F);
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.b("RabbitType", this.eO());
		le.b("MoreCarrotTicks", this.bB);
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.t(le.h("RabbitType"));
		this.bB = le.h("MoreCarrotTicks");
	}

	protected ack eN() {
		return acl.lV;
	}

	@Override
	protected ack I() {
		return acl.lR;
	}

	@Override
	protected ack e(anw anw) {
		return acl.lU;
	}

	@Override
	protected ack dp() {
		return acl.lT;
	}

	@Override
	public boolean B(aom aom) {
		if (this.eO() == 99) {
			this.a(acl.lS, 1.0F, (this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F);
			return aom.a(anw.c(this), 8.0F);
		} else {
			return aom.a(anw.c(this), 3.0F);
		}
	}

	@Override
	public acm ct() {
		return this.eO() == 99 ? acm.HOSTILE : acm.NEUTRAL;
	}

	@Override
	public boolean a(anw anw, float float2) {
		return this.b(anw) ? false : super.a(anw, float2);
	}

	private boolean b(bke bke) {
		return bke == bkk.oX || bke == bkk.pc || bke == bvs.bp.h();
	}

	public azb a(aok aok) {
		azb azb3 = aoq.an.a(this.l);
		int integer4 = this.a(this.l);
		if (this.J.nextInt(20) != 0) {
			if (aok instanceof azb && this.J.nextBoolean()) {
				integer4 = ((azb)aok).eO();
			} else {
				integer4 = this.eO();
			}
		}

		azb3.t(integer4);
		return azb3;
	}

	@Override
	public boolean k(bki bki) {
		return this.b(bki.b());
	}

	public int eO() {
		return this.S.a(bv);
	}

	public void t(int integer) {
		if (integer == 99) {
			this.a(apx.i).a(8.0);
			this.br.a(4, new azb.a(this));
			this.bs.a(1, new awb(this).a());
			this.bs.a(2, new awc(this, bec.class, true));
			this.bs.a(2, new awc(this, azk.class, true));
			if (!this.Q()) {
				this.a(new ne(v.a("entity", bw)));
			}
		}

		this.S.b(bv, integer);
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		int integer7 = this.a(bqc);
		if (apo instanceof azb.c) {
			integer7 = ((azb.c)apo).a;
		} else {
			apo = new azb.c(integer7);
		}

		this.t(integer7);
		return super.a(bqc, ane, apb, apo, le);
	}

	private int a(bqc bqc) {
		bre bre3 = bqc.v(this.cA());
		int integer4 = this.J.nextInt(100);
		if (bre3.d() == bre.f.SNOW) {
			return integer4 < 80 ? 1 : 3;
		} else if (bre3.y() == bre.b.DESERT) {
			return 4;
		} else {
			return integer4 < 50 ? 0 : (integer4 < 90 ? 5 : 2);
		}
	}

	public static boolean c(aoq<azb> aoq, bqc bqc, apb apb, fu fu, Random random) {
		cfj cfj6 = bqc.d_(fu.c());
		return (cfj6.a(bvs.i) || cfj6.a(bvs.cC) || cfj6.a(bvs.C)) && bqc.b(fu, 0) > 8;
	}

	private boolean eY() {
		return this.bB == 0;
	}

	static class a extends auq {
		public a(azb azb) {
			super(azb, 1.4, true);
		}

		@Override
		protected double a(aoy aoy) {
			return (double)(4.0F + aoy.cx());
		}
	}

	static class b<T extends aoy> extends ato<T> {
		private final azb i;

		public b(azb azb, Class<T> class2, float float3, double double4, double double5) {
			super(azb, class2, float3, double4, double5);
			this.i = azb;
		}

		@Override
		public boolean a() {
			return this.i.eO() != 99 && super.a();
		}
	}

	public static class c extends aok.a {
		public final int a;

		public c(int integer) {
			this.a = integer;
			this.a(1.0F);
		}
	}

	public class d extends atk {
		private final azb c;
		private boolean d;

		public d(azb azb2) {
			super(azb2);
			this.c = azb2;
		}

		public boolean c() {
			return this.a;
		}

		public boolean d() {
			return this.d;
		}

		public void a(boolean boolean1) {
			this.d = boolean1;
		}

		@Override
		public void b() {
			if (this.a) {
				this.c.eL();
				this.a = false;
			}
		}
	}

	static class e extends atm {
		private final azb i;
		private double j;

		public e(azb azb) {
			super(azb);
			this.i = azb;
		}

		@Override
		public void a() {
			if (this.i.t && !this.i.aX && !((azb.d)this.i.bp).c()) {
				this.i.i(0.0);
			} else if (this.b()) {
				this.i.i(this.j);
			}

			super.a();
		}

		@Override
		public void a(double double1, double double2, double double3, double double4) {
			if (this.i.aA()) {
				double4 = 1.5;
			}

			super.a(double1, double2, double3, double4);
			if (double4 > 0.0) {
				this.j = double4;
			}
		}
	}

	static class f extends avb {
		private final azb g;

		public f(azb azb, double double2) {
			super(azb, double2);
			this.g = azb;
		}

		@Override
		public void e() {
			super.e();
			this.g.i(this.b);
		}
	}

	static class g extends auu {
		private final azb g;
		private boolean h;
		private boolean i;

		public g(azb azb) {
			super(azb, 0.7F, 16);
			this.g = azb;
		}

		@Override
		public boolean a() {
			if (this.c <= 0) {
				if (!this.g.l.S().b(bpx.b)) {
					return false;
				}

				this.i = false;
				this.h = this.g.eY();
				this.h = true;
			}

			return super.a();
		}

		@Override
		public boolean b() {
			return this.i && super.b();
		}

		@Override
		public void e() {
			super.e();
			this.g.t().a((double)this.e.u() + 0.5, (double)(this.e.v() + 1), (double)this.e.w() + 0.5, 10.0F, (float)this.g.eo());
			if (this.k()) {
				bqb bqb2 = this.g.l;
				fu fu3 = this.e.b();
				cfj cfj4 = bqb2.d_(fu3);
				bvr bvr5 = cfj4.b();
				if (this.i && bvr5 instanceof bwc) {
					Integer integer6 = cfj4.c(bwc.b);
					if (integer6 == 0) {
						bqb2.a(fu3, bvs.a.n(), 2);
						bqb2.a(fu3, true, this.g);
					} else {
						bqb2.a(fu3, cfj4.a(bwc.b, Integer.valueOf(integer6 - 1)), 2);
						bqb2.c(2001, fu3, bvr.i(cfj4));
					}

					this.g.bB = 40;
				}

				this.i = false;
				this.c = 10;
			}
		}

		@Override
		protected boolean a(bqd bqd, fu fu) {
			bvr bvr4 = bqd.d_(fu).b();
			if (bvr4 == bvs.bX && this.h && !this.i) {
				fu = fu.b();
				cfj cfj5 = bqd.d_(fu);
				bvr4 = cfj5.b();
				if (bvr4 instanceof bwc && ((bwc)bvr4).h(cfj5)) {
					this.i = true;
					return true;
				}
			}

			return false;
		}
	}
}
