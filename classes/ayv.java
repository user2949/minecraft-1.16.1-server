import java.util.Random;
import javax.annotation.Nullable;

public class ayv extends ayk {
	private static final bmr bv = bmr.a(bkk.ml, bkk.mm);
	private static final tq<Boolean> bw = tt.a(ayv.class, ts.i);
	private ayv.a<bec> bx;
	private ayv.b by;

	public ayv(aoq<? extends ayv> aoq, bqb bqb) {
		super(aoq, bqb);
		this.eM();
	}

	private boolean eN() {
		return this.S.a(bw);
	}

	private void t(boolean boolean1) {
		this.S.b(bw, boolean1);
		this.eM();
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.a("Trusting", this.eN());
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.t(le.q("Trusting"));
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(bw, false);
	}

	@Override
	protected void o() {
		this.by = new ayv.b(this, 0.6, bv, true);
		this.br.a(1, new aua(this));
		this.br.a(3, this.by);
		this.br.a(7, new aum(this, 0.3F));
		this.br.a(8, new auy(this));
		this.br.a(9, new att(this, 0.8));
		this.br.a(10, new avw(this, 0.8, 1.0000001E-5F));
		this.br.a(11, new auo(this, bec.class, 10.0F));
		this.bs.a(1, new awc(this, ayn.class, false));
		this.bs.a(1, new awc(this, azi.class, 10, false, false, azi.bv));
	}

	@Override
	public void N() {
		if (this.u().b()) {
			double double2 = this.u().c();
			if (double2 == 0.6) {
				this.b(apj.CROUCHING);
				this.g(false);
			} else if (double2 == 1.33) {
				this.b(apj.STANDING);
				this.g(true);
			} else {
				this.b(apj.STANDING);
				this.g(false);
			}
		} else {
			this.b(apj.STANDING);
			this.g(false);
		}
	}

	@Override
	public boolean h(double double1) {
		return !this.eN() && this.K > 2400;
	}

	public static apw.a eL() {
		return aoz.p().a(apx.a, 10.0).a(apx.d, 0.3F).a(apx.f, 3.0);
	}

	@Override
	public boolean b(float float1, float float2) {
		return false;
	}

	@Nullable
	@Override
	protected ack I() {
		return acl.jG;
	}

	@Override
	public int D() {
		return 900;
	}

	@Override
	protected ack e(anw anw) {
		return acl.jF;
	}

	@Override
	protected ack dp() {
		return acl.jH;
	}

	private float eO() {
		return (float)this.b(apx.f);
	}

	@Override
	public boolean B(aom aom) {
		return aom.a(anw.c(this), this.eO());
	}

	@Override
	public boolean a(anw anw, float float2) {
		return this.b(anw) ? false : super.a(anw, float2);
	}

	@Override
	public ang b(bec bec, anf anf) {
		bki bki4 = bec.b(anf);
		if ((this.by == null || this.by.h()) && !this.eN() && this.k(bki4) && bec.h(this) < 9.0) {
			this.a(bec, bki4);
			if (!this.l.v) {
				if (this.J.nextInt(3) == 0) {
					this.t(true);
					this.u(true);
					this.l.a(this, (byte)41);
				} else {
					this.u(false);
					this.l.a(this, (byte)40);
				}
			}

			return ang.a(this.l.v);
		} else {
			return super.b(bec, anf);
		}
	}

	private void u(boolean boolean1) {
		hf hf3 = hh.G;
		if (!boolean1) {
			hf3 = hh.S;
		}

		for (int integer4 = 0; integer4 < 7; integer4++) {
			double double5 = this.J.nextGaussian() * 0.02;
			double double7 = this.J.nextGaussian() * 0.02;
			double double9 = this.J.nextGaussian() * 0.02;
			this.l.a(hf3, this.d(1.0), this.cE() + 0.5, this.g(1.0), double5, double7, double9);
		}
	}

	protected void eM() {
		if (this.bx == null) {
			this.bx = new ayv.a<>(this, bec.class, 16.0F, 0.8, 1.33);
		}

		this.br.a(this.bx);
		if (!this.eN()) {
			this.br.a(4, this.bx);
		}
	}

	public ayv a(aok aok) {
		return aoq.ac.a(this.l);
	}

	@Override
	public boolean k(bki bki) {
		return bv.a(bki);
	}

	public static boolean c(aoq<ayv> aoq, bqc bqc, apb apb, fu fu, Random random) {
		return random.nextInt(3) != 0;
	}

	@Override
	public boolean a(bqd bqd) {
		if (bqd.i(this) && !bqd.d(this.cb())) {
			fu fu3 = this.cA();
			if (fu3.v() < bqd.t_()) {
				return false;
			}

			cfj cfj4 = bqd.d_(fu3.c());
			if (cfj4.a(bvs.i) || cfj4.a(acx.H)) {
				return true;
			}
		}

		return false;
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		if (apo == null) {
			apo = new aok.a();
			((aok.a)apo).a(1.0F);
		}

		return super.a(bqc, ane, apb, apo, le);
	}

	static class a<T extends aoy> extends ato<T> {
		private final ayv i;

		public a(ayv ayv, Class<T> class2, float float3, double double4, double double5) {
			super(ayv, class2, float3, double4, double5, aop.e::test);
			this.i = ayv;
		}

		@Override
		public boolean a() {
			return !this.i.eN() && super.a();
		}

		@Override
		public boolean b() {
			return !this.i.eN() && super.b();
		}
	}

	static class b extends avr {
		private final ayv c;

		public b(ayv ayv, double double2, bmr bmr, boolean boolean4) {
			super(ayv, double2, bmr, boolean4);
			this.c = ayv;
		}

		@Override
		protected boolean g() {
			return super.g() && !this.c.eN();
		}
	}
}
