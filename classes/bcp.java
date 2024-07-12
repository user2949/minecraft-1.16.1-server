import java.util.EnumSet;
import javax.annotation.Nullable;

public class bcp extends bcb {
	protected static final tq<Byte> b = tt.a(bcp.class, ts.a);
	private aoz c;
	@Nullable
	private fu d;
	private boolean bv;
	private int bw;

	public bcp(aoq<? extends bcp> aoq, bqb bqb) {
		super(aoq, bqb);
		this.bo = new bcp.c(this);
		this.f = 3;
	}

	@Override
	public void a(apd apd, dem dem) {
		super.a(apd, dem);
		this.at();
	}

	@Override
	public void j() {
		this.H = true;
		super.j();
		this.H = false;
		this.e(true);
		if (this.bv && --this.bw <= 0) {
			this.bw = 20;
			this.a(anw.i, 1.0F);
		}
	}

	@Override
	protected void o() {
		super.o();
		this.br.a(0, new aua(this));
		this.br.a(4, new bcp.a());
		this.br.a(8, new bcp.d());
		this.br.a(9, new auo(this, bec.class, 3.0F, 1.0F));
		this.br.a(10, new auo(this, aoz.class, 8.0F));
		this.bs.a(1, new awb(this, bfi.class).a());
		this.bs.a(2, new bcp.b(this));
		this.bs.a(3, new awc(this, bec.class, true));
	}

	public static apw.a m() {
		return bcb.eS().a(apx.a, 14.0).a(apx.f, 4.0);
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(b, (byte)0);
	}

	@Override
	public void a(le le) {
		super.a(le);
		if (le.e("BoundX")) {
			this.d = new fu(le.h("BoundX"), le.h("BoundY"), le.h("BoundZ"));
		}

		if (le.e("LifeTicks")) {
			this.a(le.h("LifeTicks"));
		}
	}

	@Override
	public void b(le le) {
		super.b(le);
		if (this.d != null) {
			le.b("BoundX", this.d.u());
			le.b("BoundY", this.d.v());
			le.b("BoundZ", this.d.w());
		}

		if (this.bv) {
			le.b("LifeTicks", this.bw);
		}
	}

	public aoz eL() {
		return this.c;
	}

	@Nullable
	public fu eM() {
		return this.d;
	}

	public void g(@Nullable fu fu) {
		this.d = fu;
	}

	private boolean b(int integer) {
		int integer3 = this.S.a(b);
		return (integer3 & integer) != 0;
	}

	private void a(int integer, boolean boolean2) {
		int integer4 = this.S.a(b);
		if (boolean2) {
			integer4 |= integer;
		} else {
			integer4 &= ~integer;
		}

		this.S.b(b, (byte)(integer4 & 0xFF));
	}

	public boolean eN() {
		return this.b(1);
	}

	public void t(boolean boolean1) {
		this.a(1, boolean1);
	}

	public void a(aoz aoz) {
		this.c = aoz;
	}

	public void a(int integer) {
		this.bv = true;
		this.bw = integer;
	}

	@Override
	protected ack I() {
		return acl.pH;
	}

	@Override
	protected ack dp() {
		return acl.pJ;
	}

	@Override
	protected ack e(anw anw) {
		return acl.pK;
	}

	@Override
	public float aO() {
		return 1.0F;
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		this.a(ane);
		this.b(ane);
		return super.a(bqc, ane, apb, apo, le);
	}

	@Override
	protected void a(ane ane) {
		this.a(aor.MAINHAND, new bki(bkk.ko));
		this.a(aor.MAINHAND, 0.0F);
	}

	class a extends aug {
		public a() {
			this.a(EnumSet.of(aug.a.MOVE));
		}

		@Override
		public boolean a() {
			return bcp.this.A() != null && !bcp.this.u().b() && bcp.this.J.nextInt(7) == 0 ? bcp.this.h(bcp.this.A()) > 4.0 : false;
		}

		@Override
		public boolean b() {
			return bcp.this.u().b() && bcp.this.eN() && bcp.this.A() != null && bcp.this.A().aU();
		}

		@Override
		public void c() {
			aoy aoy2 = bcp.this.A();
			dem dem3 = aoy2.j(1.0F);
			bcp.this.bo.a(dem3.b, dem3.c, dem3.d, 1.0);
			bcp.this.t(true);
			bcp.this.a(acl.pI, 1.0F, 1.0F);
		}

		@Override
		public void d() {
			bcp.this.t(false);
		}

		@Override
		public void e() {
			aoy aoy2 = bcp.this.A();
			if (bcp.this.cb().c(aoy2.cb())) {
				bcp.this.B(aoy2);
				bcp.this.t(false);
			} else {
				double double3 = bcp.this.h(aoy2);
				if (double3 < 9.0) {
					dem dem5 = aoy2.j(1.0F);
					bcp.this.bo.a(dem5.b, dem5.c, dem5.d, 1.0);
				}
			}
		}
	}

	class b extends awj {
		private final axs b = new axs().c().e();

		public b(apg apg) {
			super(apg, false);
		}

		@Override
		public boolean a() {
			return bcp.this.c != null && bcp.this.c.A() != null && this.a(bcp.this.c.A(), this.b);
		}

		@Override
		public void c() {
			bcp.this.i(bcp.this.c.A());
			super.c();
		}
	}

	class c extends atm {
		public c(bcp bcp2) {
			super(bcp2);
		}

		@Override
		public void a() {
			if (this.h == atm.a.MOVE_TO) {
				dem dem2 = new dem(this.b - bcp.this.cC(), this.c - bcp.this.cD(), this.d - bcp.this.cG());
				double double3 = dem2.f();
				if (double3 < bcp.this.cb().a()) {
					this.h = atm.a.WAIT;
					bcp.this.e(bcp.this.cB().a(0.5));
				} else {
					bcp.this.e(bcp.this.cB().e(dem2.a(this.e * 0.05 / double3)));
					if (bcp.this.A() == null) {
						dem dem5 = bcp.this.cB();
						bcp.this.p = -((float)aec.d(dem5.b, dem5.d)) * (180.0F / (float)Math.PI);
						bcp.this.aH = bcp.this.p;
					} else {
						double double5 = bcp.this.A().cC() - bcp.this.cC();
						double double7 = bcp.this.A().cG() - bcp.this.cG();
						bcp.this.p = -((float)aec.d(double5, double7)) * (180.0F / (float)Math.PI);
						bcp.this.aH = bcp.this.p;
					}
				}
			}
		}
	}

	class d extends aug {
		public d() {
			this.a(EnumSet.of(aug.a.MOVE));
		}

		@Override
		public boolean a() {
			return !bcp.this.u().b() && bcp.this.J.nextInt(7) == 0;
		}

		@Override
		public boolean b() {
			return false;
		}

		@Override
		public void e() {
			fu fu2 = bcp.this.eM();
			if (fu2 == null) {
				fu2 = bcp.this.cA();
			}

			for (int integer3 = 0; integer3 < 3; integer3++) {
				fu fu4 = fu2.b(bcp.this.J.nextInt(15) - 7, bcp.this.J.nextInt(11) - 5, bcp.this.J.nextInt(15) - 7);
				if (bcp.this.l.w(fu4)) {
					bcp.this.bo.a((double)fu4.u() + 0.5, (double)fu4.v() + 0.5, (double)fu4.w() + 0.5, 0.25);
					if (bcp.this.A() == null) {
						bcp.this.t().a((double)fu4.u() + 0.5, (double)fu4.v() + 0.5, (double)fu4.w() + 0.5, 180.0F, 20.0F);
					}
					break;
				}
			}
		}
	}
}
