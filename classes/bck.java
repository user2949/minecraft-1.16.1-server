import java.util.EnumSet;
import java.util.Random;
import javax.annotation.Nullable;

public class bck extends aoz implements bbt {
	private static final tq<Integer> bv = tt.a(bck.class, ts.b);
	public float b;
	public float c;
	public float d;
	private boolean bw;

	public bck(aoq<? extends bck> aoq, bqb bqb) {
		super(aoq, bqb);
		this.bo = new bck.d(this);
	}

	@Override
	protected void o() {
		this.br.a(1, new bck.b(this));
		this.br.a(2, new bck.a(this));
		this.br.a(3, new bck.e(this));
		this.br.a(5, new bck.c(this));
		this.bs.a(1, new awc(this, bec.class, 10, true, false, aoy -> Math.abs(aoy.cD() - this.cD()) <= 4.0));
		this.bs.a(3, new awc(this, ayt.class, true));
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(bv, 1);
	}

	protected void a(int integer, boolean boolean2) {
		this.S.b(bv, integer);
		this.ac();
		this.y_();
		this.a(apx.a).a((double)(integer * integer));
		this.a(apx.d).a((double)(0.2F + 0.1F * (float)integer));
		this.a(apx.f).a((double)integer);
		if (boolean2) {
			this.c(this.dw());
		}

		this.f = integer;
	}

	public int eQ() {
		return this.S.a(bv);
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.b("Size", this.eQ() - 1);
		le.a("wasOnGround", this.bw);
	}

	@Override
	public void a(le le) {
		int integer3 = le.h("Size");
		if (integer3 < 0) {
			integer3 = 0;
		}

		this.a(integer3 + 1, false);
		super.a(le);
		this.bw = le.q("wasOnGround");
	}

	public boolean eR() {
		return this.eQ() <= 1;
	}

	protected hf eJ() {
		return hh.J;
	}

	@Override
	protected boolean L() {
		return this.eQ() > 0;
	}

	@Override
	public void j() {
		this.c = this.c + (this.b - this.c) * 0.5F;
		this.d = this.c;
		super.j();
		if (this.t && !this.bw) {
			int integer2 = this.eQ();

			for (int integer3 = 0; integer3 < integer2 * 8; integer3++) {
				float float4 = this.J.nextFloat() * (float) (Math.PI * 2);
				float float5 = this.J.nextFloat() * 0.5F + 0.5F;
				float float6 = aec.a(float4) * (float)integer2 * 0.5F * float5;
				float float7 = aec.b(float4) * (float)integer2 * 0.5F * float5;
				this.l.a(this.eJ(), this.cC() + (double)float6, this.cD(), this.cG() + (double)float7, 0.0, 0.0, 0.0);
			}

			this.a(this.eO(), this.dF(), ((this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F) / 0.8F);
			this.b = -0.5F;
		} else if (!this.t && this.bw) {
			this.b = 1.0F;
		}

		this.bw = this.t;
		this.eL();
	}

	protected void eL() {
		this.b *= 0.6F;
	}

	protected int eK() {
		return this.J.nextInt(20) + 10;
	}

	@Override
	public void y_() {
		double double2 = this.cC();
		double double4 = this.cD();
		double double6 = this.cG();
		super.y_();
		this.d(double2, double4, double6);
	}

	@Override
	public void a(tq<?> tq) {
		if (bv.equals(tq)) {
			this.y_();
			this.p = this.aJ;
			this.aH = this.aJ;
			if (this.aA() && this.J.nextInt(20) == 0) {
				this.aI();
			}
		}

		super.a(tq);
	}

	@Override
	public aoq<? extends bck> U() {
		return (aoq<? extends bck>)super.U();
	}

	@Override
	public void aa() {
		int integer2 = this.eQ();
		if (!this.l.v && integer2 > 1 && this.dk()) {
			mr mr3 = this.R();
			boolean boolean4 = this.eE();
			float float5 = (float)integer2 / 4.0F;
			int integer6 = integer2 / 2;
			int integer7 = 2 + this.J.nextInt(3);

			for (int integer8 = 0; integer8 < integer7; integer8++) {
				float float9 = ((float)(integer8 % 2) - 0.5F) * float5;
				float float10 = ((float)(integer8 / 2) - 0.5F) * float5;
				bck bck11 = this.U().a(this.l);
				if (this.ev()) {
					bck11.et();
				}

				bck11.a(mr3);
				bck11.q(boolean4);
				bck11.m(this.bI());
				bck11.a(integer6, true);
				bck11.b(this.cC() + (double)float9, this.cD() + 0.5, this.cG() + (double)float10, this.J.nextFloat() * 360.0F, 0.0F);
				this.l.c(bck11);
			}
		}

		super.aa();
	}

	@Override
	public void i(aom aom) {
		super.i(aom);
		if (aom instanceof ayt && this.eM()) {
			this.j((aoy)aom);
		}
	}

	@Override
	public void a_(bec bec) {
		if (this.eM()) {
			this.j(bec);
		}
	}

	protected void j(aoy aoy) {
		if (this.aU()) {
			int integer3 = this.eQ();
			if (this.h((aom)aoy) < 0.6 * (double)integer3 * 0.6 * (double)integer3 && this.D(aoy) && aoy.a(anw.c(this), this.eN())) {
				this.a(acl.ny, 1.0F, (this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F);
				this.a(this, aoy);
			}
		}
	}

	@Override
	protected float b(apj apj, aon aon) {
		return 0.625F * aon.b;
	}

	protected boolean eM() {
		return !this.eR() && this.dR();
	}

	protected float eN() {
		return (float)this.b(apx.f);
	}

	@Override
	protected ack e(anw anw) {
		return this.eR() ? acl.od : acl.nA;
	}

	@Override
	protected ack dp() {
		return this.eR() ? acl.oc : acl.nz;
	}

	protected ack eO() {
		return this.eR() ? acl.of : acl.nC;
	}

	@Override
	protected uh J() {
		return this.eQ() == 1 ? this.U().i() : dao.a;
	}

	public static boolean c(aoq<bck> aoq, bqc bqc, apb apb, fu fu, Random random) {
		if (bqc.ac() != and.PEACEFUL) {
			bre bre6 = bqc.v(fu);
			if (bre6 == brk.h && fu.v() > 50 && fu.v() < 70 && random.nextFloat() < 0.5F && random.nextFloat() < bqc.aa() && bqc.B(fu) <= random.nextInt(8)) {
				return a(aoq, bqc, apb, fu, random);
			}

			if (!(bqc instanceof bqu)) {
				return false;
			}

			bph bph7 = new bph(fu);
			boolean boolean8 = ciy.a(bph7.b, bph7.c, ((bqu)bqc).B(), 987234911L).nextInt(10) == 0;
			if (random.nextInt(10) == 0 && boolean8 && fu.v() < 40) {
				return a(aoq, bqc, apb, fu, random);
			}
		}

		return false;
	}

	@Override
	protected float dF() {
		return 0.4F * (float)this.eQ();
	}

	@Override
	public int eo() {
		return 0;
	}

	protected boolean eS() {
		return this.eQ() > 0;
	}

	@Override
	protected void dJ() {
		dem dem2 = this.cB();
		this.m(dem2.b, (double)this.dI(), dem2.d);
		this.ad = true;
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		int integer7 = this.J.nextInt(3);
		if (integer7 < 2 && this.J.nextFloat() < 0.5F * ane.d()) {
			integer7++;
		}

		int integer8 = 1 << integer7;
		this.a(integer8, true);
		return super.a(bqc, ane, apb, apo, le);
	}

	private float m() {
		float float2 = this.eR() ? 1.4F : 0.8F;
		return ((this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F) * float2;
	}

	protected ack eP() {
		return this.eR() ? acl.oe : acl.nB;
	}

	@Override
	public aon a(apj apj) {
		return super.a(apj).a(0.255F * (float)this.eQ());
	}

	static class a extends aug {
		private final bck a;
		private int b;

		public a(bck bck) {
			this.a = bck;
			this.a(EnumSet.of(aug.a.LOOK));
		}

		@Override
		public boolean a() {
			aoy aoy2 = this.a.A();
			if (aoy2 == null) {
				return false;
			} else if (!aoy2.aU()) {
				return false;
			} else {
				return aoy2 instanceof bec && ((bec)aoy2).bJ.a ? false : this.a.u() instanceof bck.d;
			}
		}

		@Override
		public void c() {
			this.b = 300;
			super.c();
		}

		@Override
		public boolean b() {
			aoy aoy2 = this.a.A();
			if (aoy2 == null) {
				return false;
			} else if (!aoy2.aU()) {
				return false;
			} else {
				return aoy2 instanceof bec && ((bec)aoy2).bJ.a ? false : --this.b > 0;
			}
		}

		@Override
		public void e() {
			this.a.a(this.a.A(), 10.0F, 10.0F);
			((bck.d)this.a.u()).a(this.a.p, this.a.eM());
		}
	}

	static class b extends aug {
		private final bck a;

		public b(bck bck) {
			this.a = bck;
			this.a(EnumSet.of(aug.a.JUMP, aug.a.MOVE));
			bck.x().d(true);
		}

		@Override
		public boolean a() {
			return (this.a.aA() || this.a.aN()) && this.a.u() instanceof bck.d;
		}

		@Override
		public void e() {
			if (this.a.cX().nextFloat() < 0.8F) {
				this.a.v().a();
			}

			((bck.d)this.a.u()).a(1.2);
		}
	}

	static class c extends aug {
		private final bck a;

		public c(bck bck) {
			this.a = bck;
			this.a(EnumSet.of(aug.a.JUMP, aug.a.MOVE));
		}

		@Override
		public boolean a() {
			return !this.a.bn();
		}

		@Override
		public void e() {
			((bck.d)this.a.u()).a(1.0);
		}
	}

	static class d extends atm {
		private float i;
		private int j;
		private final bck k;
		private boolean l;

		public d(bck bck) {
			super(bck);
			this.k = bck;
			this.i = 180.0F * bck.p / (float) Math.PI;
		}

		public void a(float float1, boolean boolean2) {
			this.i = float1;
			this.l = boolean2;
		}

		public void a(double double1) {
			this.e = double1;
			this.h = atm.a.MOVE_TO;
		}

		@Override
		public void a() {
			this.a.p = this.a(this.a.p, this.i, 90.0F);
			this.a.aJ = this.a.p;
			this.a.aH = this.a.p;
			if (this.h != atm.a.MOVE_TO) {
				this.a.q(0.0F);
			} else {
				this.h = atm.a.WAIT;
				if (this.a.aj()) {
					this.a.n((float)(this.e * this.a.b(apx.d)));
					if (this.j-- <= 0) {
						this.j = this.k.eK();
						if (this.l) {
							this.j /= 3;
						}

						this.k.v().a();
						if (this.k.eS()) {
							this.k.a(this.k.eP(), this.k.dF(), this.k.m());
						}
					} else {
						this.k.aY = 0.0F;
						this.k.ba = 0.0F;
						this.a.n(0.0F);
					}
				} else {
					this.a.n((float)(this.e * this.a.b(apx.d)));
				}
			}
		}
	}

	static class e extends aug {
		private final bck a;
		private float b;
		private int c;

		public e(bck bck) {
			this.a = bck;
			this.a(EnumSet.of(aug.a.LOOK));
		}

		@Override
		public boolean a() {
			return this.a.A() == null && (this.a.t || this.a.aA() || this.a.aN() || this.a.a(aoi.y)) && this.a.u() instanceof bck.d;
		}

		@Override
		public void e() {
			if (--this.c <= 0) {
				this.c = 40 + this.a.cX().nextInt(60);
				this.b = (float)this.a.cX().nextInt(360);
			}

			((bck.d)this.a.u()).a(this.b, false);
		}
	}
}
