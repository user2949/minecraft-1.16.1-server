import java.util.function.Predicate;
import javax.annotation.Nullable;

public class bcg extends bfi {
	private static final Predicate<aom> b = aom -> aom.aU() && !(aom instanceof bcg);
	private int bv;
	private int bw;
	private int bx;

	public bcg(aoq<? extends bcg> aoq, bqb bqb) {
		super(aoq, bqb);
		this.G = 1.0F;
		this.f = 20;
	}

	@Override
	protected void o() {
		super.o();
		this.br.a(0, new aua(this));
		this.br.a(4, new bcg.a());
		this.br.a(5, new avw(this, 0.4));
		this.br.a(6, new auo(this, bec.class, 6.0F));
		this.br.a(10, new auo(this, aoz.class, 8.0F));
		this.bs.a(2, new awb(this, bfi.class).a());
		this.bs.a(3, new awc(this, bec.class, true));
		this.bs.a(4, new awc(this, bdk.class, true));
		this.bs.a(4, new awc(this, ayt.class, true));
	}

	@Override
	protected void H() {
		boolean boolean2 = !(this.cl() instanceof aoz) || this.cl().U().a(acy.b);
		boolean boolean3 = !(this.cs() instanceof bft);
		this.br.a(aug.a.MOVE, boolean2);
		this.br.a(aug.a.JUMP, boolean2 && boolean3);
		this.br.a(aug.a.LOOK, boolean2);
		this.br.a(aug.a.TARGET, boolean2);
	}

	public static apw.a m() {
		return bcb.eS().a(apx.a, 100.0).a(apx.d, 0.3).a(apx.c, 0.75).a(apx.f, 12.0).a(apx.g, 1.5).a(apx.b, 32.0);
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.b("AttackTick", this.bv);
		le.b("StunTick", this.bw);
		le.b("RoarTick", this.bx);
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.bv = le.h("AttackTick");
		this.bw = le.h("StunTick");
		this.bx = le.h("RoarTick");
	}

	@Override
	public ack eM() {
		return acl.lZ;
	}

	@Override
	protected awv b(bqb bqb) {
		return new bcg.b(this, bqb);
	}

	@Override
	public int ep() {
		return 45;
	}

	@Override
	public double aY() {
		return 2.1;
	}

	@Override
	public boolean es() {
		return !this.eE() && this.cl() instanceof aoy;
	}

	@Nullable
	@Override
	public aom cl() {
		return this.cm().isEmpty() ? null : (aom)this.cm().get(0);
	}

	@Override
	public void k() {
		super.k();
		if (this.aU()) {
			if (this.dH()) {
				this.a(apx.d).a(0.0);
			} else {
				double double2 = this.A() != null ? 0.35 : 0.3;
				double double4 = this.a(apx.d).b();
				this.a(apx.d).a(aec.d(0.1, double4, double2));
			}

			if (this.u && this.l.S().b(bpx.b)) {
				boolean boolean2 = false;
				deg deg3 = this.cb().g(0.2);

				for (fu fu5 : fu.b(aec.c(deg3.a), aec.c(deg3.b), aec.c(deg3.c), aec.c(deg3.d), aec.c(deg3.e), aec.c(deg3.f))) {
					cfj cfj6 = this.l.d_(fu5);
					bvr bvr7 = cfj6.b();
					if (bvr7 instanceof bza) {
						boolean2 = this.l.a(fu5, true, this) || boolean2;
					}
				}

				if (!boolean2 && this.t) {
					this.dJ();
				}
			}

			if (this.bx > 0) {
				this.bx--;
				if (this.bx == 10) {
					this.eZ();
				}
			}

			if (this.bv > 0) {
				this.bv--;
			}

			if (this.bw > 0) {
				this.bw--;
				this.eY();
				if (this.bw == 0) {
					this.a(acl.me, 1.0F, 1.0F);
					this.bx = 20;
				}
			}
		}
	}

	private void eY() {
		if (this.J.nextInt(6) == 0) {
			double double2 = this.cC() - (double)this.cx() * Math.sin((double)(this.aH * (float) (Math.PI / 180.0))) + (this.J.nextDouble() * 0.6 - 0.3);
			double double4 = this.cD() + (double)this.cy() - 0.3;
			double double6 = this.cG() + (double)this.cx() * Math.cos((double)(this.aH * (float) (Math.PI / 180.0))) + (this.J.nextDouble() * 0.6 - 0.3);
			this.l.a(hh.u, double2, double4, double6, 0.4980392156862745, 0.5137254901960784, 0.5725490196078431);
		}
	}

	@Override
	protected boolean dH() {
		return super.dH() || this.bv > 0 || this.bw > 0 || this.bx > 0;
	}

	@Override
	public boolean D(aom aom) {
		return this.bw <= 0 && this.bx <= 0 ? super.D(aom) : false;
	}

	@Override
	protected void f(aoy aoy) {
		if (this.bx == 0) {
			if (this.J.nextDouble() < 0.5) {
				this.bw = 40;
				this.a(acl.md, 1.0F, 1.0F);
				this.l.a(this, (byte)39);
				aoy.i(this);
			} else {
				this.a(aoy);
			}

			aoy.w = true;
		}
	}

	private void eZ() {
		if (this.aU()) {
			for (aom aom4 : this.l.a(aoy.class, this.cb().g(4.0), b)) {
				if (!(aom4 instanceof bbj)) {
					aom4.a(anw.c(this), 6.0F);
				}

				this.a(aom4);
			}

			dem dem3 = this.cb().f();

			for (int integer4 = 0; integer4 < 40; integer4++) {
				double double5 = this.J.nextGaussian() * 0.2;
				double double7 = this.J.nextGaussian() * 0.2;
				double double9 = this.J.nextGaussian() * 0.2;
				this.l.a(hh.P, dem3.b, dem3.c, dem3.d, double5, double7, double9);
			}
		}
	}

	private void a(aom aom) {
		double double3 = aom.cC() - this.cC();
		double double5 = aom.cG() - this.cG();
		double double7 = Math.max(double3 * double3 + double5 * double5, 0.001);
		aom.h(double3 / double7 * 4.0, 0.2, double5 / double7 * 4.0);
	}

	@Override
	public boolean B(aom aom) {
		this.bv = 10;
		this.l.a(this, (byte)4);
		this.a(acl.lY, 1.0F, 1.0F);
		return super.B(aom);
	}

	@Nullable
	@Override
	protected ack I() {
		return acl.lX;
	}

	@Override
	protected ack e(anw anw) {
		return acl.mb;
	}

	@Override
	protected ack dp() {
		return acl.ma;
	}

	@Override
	protected void a(fu fu, cfj cfj) {
		this.a(acl.mc, 0.15F, 1.0F);
	}

	@Override
	public boolean a(bqd bqd) {
		return !bqd.d(this.cb());
	}

	@Override
	public void a(int integer, boolean boolean2) {
	}

	@Override
	public boolean eO() {
		return false;
	}

	class a extends auq {
		public a() {
			super(bcg.this, 1.0, true);
		}

		@Override
		protected double a(aoy aoy) {
			float float3 = bcg.this.cx() - 0.1F;
			return (double)(float3 * 2.0F * float3 * 2.0F + aoy.cx());
		}
	}

	static class b extends awu {
		public b(aoz aoz, bqb bqb) {
			super(aoz, bqb);
		}

		@Override
		protected czh a(int integer) {
			this.o = new bcg.c();
			return new czh(this.o, integer);
		}
	}

	static class c extends czl {
		private c() {
		}

		@Override
		protected czb a(bpg bpg, boolean boolean2, boolean boolean3, fu fu, czb czb) {
			return czb == czb.LEAVES ? czb.OPEN : super.a(bpg, boolean2, boolean3, fu, czb);
		}
	}
}
