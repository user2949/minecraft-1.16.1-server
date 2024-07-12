import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class ayw extends ayk {
	private static final tq<Integer> bw = tt.a(ayw.class, ts.b);
	private static final tq<Integer> bx = tt.a(ayw.class, ts.b);
	private static final tq<Integer> by = tt.a(ayw.class, ts.b);
	private static final tq<Byte> bz = tt.a(ayw.class, ts.a);
	private static final tq<Byte> bA = tt.a(ayw.class, ts.a);
	private static final tq<Byte> bB = tt.a(ayw.class, ts.a);
	private static final axs bC = new axs().a(8.0).b().a();
	private boolean bD;
	private boolean bE;
	public int bv;
	private dem bF;
	private float bG;
	private float bH;
	private float bI;
	private float bJ;
	private float bK;
	private float bL;
	private ayw.g bM;
	private static final Predicate<bbg> bN = bbg -> {
		bke bke2 = bbg.g().b();
		return (bke2 == bvs.kY.h() || bke2 == bvs.cW.h()) && bbg.aU() && !bbg.p();
	};

	public ayw(aoq<? extends ayw> aoq, bqb bqb) {
		super(aoq, bqb);
		this.bo = new ayw.h(this);
		if (!this.x_()) {
			this.p(true);
		}
	}

	@Override
	public boolean e(bki bki) {
		aor aor3 = aoz.j(bki);
		return !this.b(aor3).a() ? false : aor3 == aor.MAINHAND && super.e(bki);
	}

	public int eL() {
		return this.S.a(bw);
	}

	public void t(int integer) {
		this.S.b(bw, integer);
	}

	public boolean eM() {
		return this.w(2);
	}

	public boolean eN() {
		return this.w(8);
	}

	public void t(boolean boolean1) {
		this.d(8, boolean1);
	}

	public boolean eO() {
		return this.w(16);
	}

	public void u(boolean boolean1) {
		this.d(16, boolean1);
	}

	public boolean eP() {
		return this.S.a(by) > 0;
	}

	public void v(boolean boolean1) {
		this.S.b(by, boolean1 ? 1 : 0);
	}

	private int fl() {
		return this.S.a(by);
	}

	private void v(int integer) {
		this.S.b(by, integer);
	}

	public void w(boolean boolean1) {
		this.d(2, boolean1);
		if (!boolean1) {
			this.u(0);
		}
	}

	public int eV() {
		return this.S.a(bx);
	}

	public void u(int integer) {
		this.S.b(bx, integer);
	}

	public ayw.a eW() {
		return ayw.a.a(this.S.a(bz));
	}

	public void a(ayw.a a) {
		if (a.a() > 6) {
			a = ayw.a.a(this.J);
		}

		this.S.b(bz, (byte)a.a());
	}

	public ayw.a eX() {
		return ayw.a.a(this.S.a(bA));
	}

	public void b(ayw.a a) {
		if (a.a() > 6) {
			a = ayw.a.a(this.J);
		}

		this.S.b(bA, (byte)a.a());
	}

	public boolean eY() {
		return this.w(4);
	}

	public void x(boolean boolean1) {
		this.d(4, boolean1);
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(bw, 0);
		this.S.a(bx, 0);
		this.S.a(bz, (byte)0);
		this.S.a(bA, (byte)0);
		this.S.a(bB, (byte)0);
		this.S.a(by, 0);
	}

	private boolean w(int integer) {
		return (this.S.a(bB) & integer) != 0;
	}

	private void d(int integer, boolean boolean2) {
		byte byte4 = this.S.a(bB);
		if (boolean2) {
			this.S.b(bB, (byte)(byte4 | integer));
		} else {
			this.S.b(bB, (byte)(byte4 & ~integer));
		}
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.a("MainGene", this.eW().b());
		le.a("HiddenGene", this.eX().b());
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.a(ayw.a.a(le.l("MainGene")));
		this.b(ayw.a.a(le.l("HiddenGene")));
	}

	@Nullable
	@Override
	public aok a(aok aok) {
		ayw ayw3 = aoq.ae.a(this.l);
		if (aok instanceof ayw) {
			ayw3.a(this, (ayw)aok);
		}

		ayw3.fh();
		return ayw3;
	}

	@Override
	protected void o() {
		this.br.a(0, new aua(this));
		this.br.a(2, new ayw.i(this, 2.0));
		this.br.a(2, new ayw.d(this, 1.0));
		this.br.a(3, new ayw.b(this, 1.2F, true));
		this.br.a(4, new avr(this, 1.0, bmr.a(bvs.kY.h()), false));
		this.br.a(6, new ayw.c(this, bec.class, 8.0F, 2.0, 2.0));
		this.br.a(6, new ayw.c(this, bcb.class, 4.0F, 2.0, 2.0));
		this.br.a(7, new ayw.k());
		this.br.a(8, new ayw.f(this));
		this.br.a(8, new ayw.l(this));
		this.bM = new ayw.g(this, bec.class, 6.0F);
		this.br.a(9, this.bM);
		this.br.a(10, new ave(this));
		this.br.a(12, new ayw.j(this));
		this.br.a(13, new auf(this, 1.25));
		this.br.a(14, new avw(this, 1.0));
		this.bs.a(1, new ayw.e(this).a(new Class[0]));
	}

	public static apw.a eZ() {
		return aoz.p().a(apx.d, 0.15F).a(apx.f, 6.0);
	}

	public ayw.a fa() {
		return ayw.a.b(this.eW(), this.eX());
	}

	public boolean fb() {
		return this.fa() == ayw.a.LAZY;
	}

	public boolean fc() {
		return this.fa() == ayw.a.WORRIED;
	}

	public boolean fd() {
		return this.fa() == ayw.a.PLAYFUL;
	}

	public boolean ff() {
		return this.fa() == ayw.a.WEAK;
	}

	@Override
	public boolean eG() {
		return this.fa() == ayw.a.AGGRESSIVE;
	}

	@Override
	public boolean a(bec bec) {
		return false;
	}

	@Override
	public boolean B(aom aom) {
		this.a(acl.jU, 1.0F, 1.0F);
		if (!this.eG()) {
			this.bE = true;
		}

		return super.B(aom);
	}

	@Override
	public void j() {
		super.j();
		if (this.fc()) {
			if (this.l.T() && !this.aA()) {
				this.t(true);
				this.v(false);
			} else if (!this.eP()) {
				this.t(false);
			}
		}

		if (this.A() == null) {
			this.bD = false;
			this.bE = false;
		}

		if (this.eL() > 0) {
			if (this.A() != null) {
				this.a(this.A(), 90.0F, 90.0F);
			}

			if (this.eL() == 29 || this.eL() == 14) {
				this.a(acl.jQ, 1.0F, 1.0F);
			}

			this.t(this.eL() - 1);
		}

		if (this.eM()) {
			this.u(this.eV() + 1);
			if (this.eV() > 20) {
				this.w(false);
				this.fs();
			} else if (this.eV() == 1) {
				this.a(acl.jK, 1.0F, 1.0F);
			}
		}

		if (this.eY()) {
			this.fr();
		} else {
			this.bv = 0;
		}

		if (this.eN()) {
			this.q = 0.0F;
		}

		this.fo();
		this.fm();
		this.fp();
		this.fq();
	}

	public boolean fg() {
		return this.fc() && this.l.T();
	}

	private void fm() {
		if (!this.eP() && this.eN() && !this.fg() && !this.b(aor.MAINHAND).a() && this.J.nextInt(80) == 1) {
			this.v(true);
		} else if (this.b(aor.MAINHAND).a() || !this.eN()) {
			this.v(false);
		}

		if (this.eP()) {
			this.fn();
			if (!this.l.v && this.fl() > 80 && this.J.nextInt(20) == 1) {
				if (this.fl() > 100 && this.l(this.b(aor.MAINHAND))) {
					if (!this.l.v) {
						this.a(aor.MAINHAND, bki.b);
					}

					this.t(false);
				}

				this.v(false);
				return;
			}

			this.v(this.fl() + 1);
		}
	}

	private void fn() {
		if (this.fl() % 5 == 0) {
			this.a(acl.jO, 0.5F + 0.5F * (float)this.J.nextInt(2), (this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F);

			for (int integer2 = 0; integer2 < 6; integer2++) {
				dem dem3 = new dem(((double)this.J.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, ((double)this.J.nextFloat() - 0.5) * 0.1);
				dem3 = dem3.a(-this.q * (float) (Math.PI / 180.0));
				dem3 = dem3.b(-this.p * (float) (Math.PI / 180.0));
				double double4 = (double)(-this.J.nextFloat()) * 0.6 - 0.3;
				dem dem6 = new dem(((double)this.J.nextFloat() - 0.5) * 0.8, double4, 1.0 + ((double)this.J.nextFloat() - 0.5) * 0.4);
				dem6 = dem6.b(-this.aH * (float) (Math.PI / 180.0));
				dem6 = dem6.b(this.cC(), this.cF() + 1.0, this.cG());
				this.l.a(new he(hh.I, this.b(aor.MAINHAND)), dem6.b, dem6.c, dem6.d, dem3.b, dem3.c + 0.05, dem3.d);
			}
		}
	}

	private void fo() {
		this.bH = this.bG;
		if (this.eN()) {
			this.bG = Math.min(1.0F, this.bG + 0.15F);
		} else {
			this.bG = Math.max(0.0F, this.bG - 0.19F);
		}
	}

	private void fp() {
		this.bJ = this.bI;
		if (this.eO()) {
			this.bI = Math.min(1.0F, this.bI + 0.15F);
		} else {
			this.bI = Math.max(0.0F, this.bI - 0.19F);
		}
	}

	private void fq() {
		this.bL = this.bK;
		if (this.eY()) {
			this.bK = Math.min(1.0F, this.bK + 0.15F);
		} else {
			this.bK = Math.max(0.0F, this.bK - 0.19F);
		}
	}

	private void fr() {
		this.bv++;
		if (this.bv > 32) {
			this.x(false);
		} else {
			if (!this.l.v) {
				dem dem2 = this.cB();
				if (this.bv == 1) {
					float float3 = this.p * (float) (Math.PI / 180.0);
					float float4 = this.x_() ? 0.1F : 0.2F;
					this.bF = new dem(dem2.b + (double)(-aec.a(float3) * float4), 0.0, dem2.d + (double)(aec.b(float3) * float4));
					this.e(this.bF.b(0.0, 0.27, 0.0));
				} else if ((float)this.bv != 7.0F && (float)this.bv != 15.0F && (float)this.bv != 23.0F) {
					this.m(this.bF.b, dem2.c, this.bF.d);
				} else {
					this.m(0.0, this.t ? 0.27 : dem2.c, 0.0);
				}
			}
		}
	}

	private void fs() {
		dem dem2 = this.cB();
		this.l
			.a(
				hh.T,
				this.cC() - (double)(this.cx() + 1.0F) * 0.5 * (double)aec.a(this.aH * (float) (Math.PI / 180.0)),
				this.cF() - 0.1F,
				this.cG() + (double)(this.cx() + 1.0F) * 0.5 * (double)aec.b(this.aH * (float) (Math.PI / 180.0)),
				dem2.b,
				0.0,
				dem2.d
			);
		this.a(acl.jL, 1.0F, 1.0F);

		for (ayw ayw5 : this.l.a(ayw.class, this.cb().g(10.0))) {
			if (!ayw5.x_() && ayw5.t && !ayw5.aA() && ayw5.fi()) {
				ayw5.dJ();
			}
		}

		if (!this.l.s_() && this.J.nextInt(700) == 0 && this.l.S().b(bpx.e)) {
			this.a(bkk.md);
		}
	}

	@Override
	protected void b(bbg bbg) {
		if (this.b(aor.MAINHAND).a() && bN.test(bbg)) {
			this.a(bbg);
			bki bki3 = bbg.g();
			this.a(aor.MAINHAND, bki3);
			this.bt[aor.MAINHAND.b()] = 2.0F;
			this.a(bbg, bki3.E());
			bbg.aa();
		}
	}

	@Override
	public boolean a(anw anw, float float2) {
		this.t(false);
		return super.a(anw, float2);
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		this.a(ayw.a.a(this.J));
		this.b(ayw.a.a(this.J));
		this.fh();
		if (apo == null) {
			apo = new aok.a();
			((aok.a)apo).a(0.2F);
		}

		return super.a(bqc, ane, apb, apo, le);
	}

	public void a(ayw ayw1, @Nullable ayw ayw2) {
		if (ayw2 == null) {
			if (this.J.nextBoolean()) {
				this.a(ayw1.ft());
				this.b(ayw.a.a(this.J));
			} else {
				this.a(ayw.a.a(this.J));
				this.b(ayw1.ft());
			}
		} else if (this.J.nextBoolean()) {
			this.a(ayw1.ft());
			this.b(ayw2.ft());
		} else {
			this.a(ayw2.ft());
			this.b(ayw1.ft());
		}

		if (this.J.nextInt(32) == 0) {
			this.a(ayw.a.a(this.J));
		}

		if (this.J.nextInt(32) == 0) {
			this.b(ayw.a.a(this.J));
		}
	}

	private ayw.a ft() {
		return this.J.nextBoolean() ? this.eW() : this.eX();
	}

	public void fh() {
		if (this.ff()) {
			this.a(apx.a).a(10.0);
		}

		if (this.fb()) {
			this.a(apx.d).a(0.07F);
		}
	}

	private void fu() {
		if (!this.aA()) {
			this.q(0.0F);
			this.x().o();
			this.t(true);
		}
	}

	@Override
	public ang b(bec bec, anf anf) {
		bki bki4 = bec.b(anf);
		if (this.fg()) {
			return ang.PASS;
		} else if (this.eO()) {
			this.u(false);
			return ang.a(this.l.v);
		} else if (this.k(bki4)) {
			if (this.A() != null) {
				this.bD = true;
			}

			if (this.x_()) {
				this.a(bec, bki4);
				this.a((int)((float)(-this.i() / 20) * 0.1F), true);
			} else if (!this.l.v && this.i() == 0 && this.eQ()) {
				this.a(bec, bki4);
				this.g(bec);
			} else {
				if (this.l.v || this.eN() || this.aA()) {
					return ang.PASS;
				}

				this.fu();
				this.v(true);
				bki bki5 = this.b(aor.MAINHAND);
				if (!bki5.a() && !bec.bJ.d) {
					this.a(bki5);
				}

				this.a(aor.MAINHAND, new bki(bki4.b(), 1));
				this.a(bec, bki4);
			}

			return ang.SUCCESS;
		} else {
			return ang.PASS;
		}
	}

	@Nullable
	@Override
	protected ack I() {
		if (this.eG()) {
			return acl.jR;
		} else {
			return this.fc() ? acl.jS : acl.jM;
		}
	}

	@Override
	protected void a(fu fu, cfj cfj) {
		this.a(acl.jP, 0.15F, 1.0F);
	}

	@Override
	public boolean k(bki bki) {
		return bki.b() == bvs.kY.h();
	}

	private boolean l(bki bki) {
		return this.k(bki) || bki.b() == bvs.cW.h();
	}

	@Nullable
	@Override
	protected ack dp() {
		return acl.jN;
	}

	@Nullable
	@Override
	protected ack e(anw anw) {
		return acl.jT;
	}

	public boolean fi() {
		return !this.eO() && !this.fg() && !this.eP() && !this.eY() && !this.eN();
	}

	public static enum a {
		NORMAL(0, "normal", false),
		LAZY(1, "lazy", false),
		WORRIED(2, "worried", false),
		PLAYFUL(3, "playful", false),
		BROWN(4, "brown", true),
		WEAK(5, "weak", true),
		AGGRESSIVE(6, "aggressive", false);

		private static final ayw.a[] h = (ayw.a[])Arrays.stream(values()).sorted(Comparator.comparingInt(ayw.a::a)).toArray(ayw.a[]::new);
		private final int i;
		private final String j;
		private final boolean k;

		private a(int integer3, String string4, boolean boolean5) {
			this.i = integer3;
			this.j = string4;
			this.k = boolean5;
		}

		public int a() {
			return this.i;
		}

		public String b() {
			return this.j;
		}

		public boolean c() {
			return this.k;
		}

		private static ayw.a b(ayw.a a1, ayw.a a2) {
			if (a1.c()) {
				return a1 == a2 ? a1 : NORMAL;
			} else {
				return a1;
			}
		}

		public static ayw.a a(int integer) {
			if (integer < 0 || integer >= h.length) {
				integer = 0;
			}

			return h[integer];
		}

		public static ayw.a a(String string) {
			for (ayw.a a5 : values()) {
				if (a5.j.equals(string)) {
					return a5;
				}
			}

			return NORMAL;
		}

		public static ayw.a a(Random random) {
			int integer2 = random.nextInt(16);
			if (integer2 == 0) {
				return LAZY;
			} else if (integer2 == 1) {
				return WORRIED;
			} else if (integer2 == 2) {
				return PLAYFUL;
			} else if (integer2 == 4) {
				return AGGRESSIVE;
			} else if (integer2 < 9) {
				return WEAK;
			} else {
				return integer2 < 11 ? BROWN : NORMAL;
			}
		}
	}

	static class b extends auq {
		private final ayw b;

		public b(ayw ayw, double double2, boolean boolean3) {
			super(ayw, double2, boolean3);
			this.b = ayw;
		}

		@Override
		public boolean a() {
			return this.b.fi() && super.a();
		}
	}

	static class c<T extends aoy> extends ato<T> {
		private final ayw i;

		public c(ayw ayw, Class<T> class2, float float3, double double4, double double5) {
			super(ayw, class2, float3, double4, double5, aop.g::test);
			this.i = ayw;
		}

		@Override
		public boolean a() {
			return this.i.fc() && this.i.fi() && super.a();
		}
	}

	class d extends att {
		private final ayw e;
		private int f;

		public d(ayw ayw2, double double3) {
			super(ayw2, double3);
			this.e = ayw2;
		}

		@Override
		public boolean a() {
			if (!super.a() || this.e.eL() != 0) {
				return false;
			} else if (!this.h()) {
				if (this.f <= this.e.K) {
					this.e.t(32);
					this.f = this.e.K + 600;
					if (this.e.dR()) {
						bec bec2 = this.b.a(ayw.bC, this.e);
						this.e.bM.a(bec2);
					}
				}

				return false;
			} else {
				return true;
			}
		}

		private boolean h() {
			fu fu2 = this.e.cA();
			fu.a a3 = new fu.a();

			for (int integer4 = 0; integer4 < 3; integer4++) {
				for (int integer5 = 0; integer5 < 8; integer5++) {
					for (int integer6 = 0; integer6 <= integer5; integer6 = integer6 > 0 ? -integer6 : 1 - integer6) {
						for (int integer7 = integer6 < integer5 && integer6 > -integer5 ? integer5 : 0; integer7 <= integer5; integer7 = integer7 > 0 ? -integer7 : 1 - integer7) {
							a3.a(fu2, integer6, integer4, integer7);
							if (this.b.d_(a3).a(bvs.kY)) {
								return true;
							}
						}
					}
				}
			}

			return false;
		}
	}

	static class e extends awb {
		private final ayw a;

		public e(ayw ayw, Class<?>... arr) {
			super(ayw, arr);
			this.a = ayw;
		}

		@Override
		public boolean b() {
			if (!this.a.bD && !this.a.bE) {
				return super.b();
			} else {
				this.a.i(null);
				return false;
			}
		}

		@Override
		protected void a(aoz aoz, aoy aoy) {
			if (aoz instanceof ayw && ((ayw)aoz).eG()) {
				aoz.i(aoy);
			}
		}
	}

	static class f extends aug {
		private final ayw a;
		private int b;

		public f(ayw ayw) {
			this.a = ayw;
		}

		@Override
		public boolean a() {
			return this.b < this.a.K && this.a.fb() && this.a.fi() && this.a.J.nextInt(400) == 1;
		}

		@Override
		public boolean b() {
			return !this.a.aA() && (this.a.fb() || this.a.J.nextInt(600) != 1) ? this.a.J.nextInt(2000) != 1 : false;
		}

		@Override
		public void c() {
			this.a.u(true);
			this.b = 0;
		}

		@Override
		public void d() {
			this.a.u(false);
			this.b = this.a.K + 200;
		}
	}

	static class g extends auo {
		private final ayw g;

		public g(ayw ayw, Class<? extends aoy> class2, float float3) {
			super(ayw, class2, float3);
			this.g = ayw;
		}

		public void a(aoy aoy) {
			this.b = aoy;
		}

		@Override
		public boolean b() {
			return this.b != null && super.b();
		}

		@Override
		public boolean a() {
			if (this.a.cX().nextFloat() >= this.d) {
				return false;
			} else {
				if (this.b == null) {
					if (this.e == bec.class) {
						this.b = this.a.l.a(this.f, this.a, this.a.cC(), this.a.cF(), this.a.cG());
					} else {
						this.b = this.a.l.b(this.e, this.f, this.a, this.a.cC(), this.a.cF(), this.a.cG(), this.a.cb().c((double)this.c, 3.0, (double)this.c));
					}
				}

				return this.g.fi() && this.b != null;
			}
		}

		@Override
		public void e() {
			if (this.b != null) {
				super.e();
			}
		}
	}

	static class h extends atm {
		private final ayw i;

		public h(ayw ayw) {
			super(ayw);
			this.i = ayw;
		}

		@Override
		public void a() {
			if (this.i.fi()) {
				super.a();
			}
		}
	}

	static class i extends avb {
		private final ayw g;

		public i(ayw ayw, double double2) {
			super(ayw, double2);
			this.g = ayw;
		}

		@Override
		public boolean a() {
			if (!this.g.bm()) {
				return false;
			} else {
				fu fu2 = this.a(this.a.l, this.a, 5, 4);
				if (fu2 != null) {
					this.c = (double)fu2.u();
					this.d = (double)fu2.v();
					this.e = (double)fu2.w();
					return true;
				} else {
					return this.g();
				}
			}
		}

		@Override
		public boolean b() {
			if (this.g.eN()) {
				this.g.x().o();
				return false;
			} else {
				return super.b();
			}
		}
	}

	static class j extends aug {
		private final ayw a;

		public j(ayw ayw) {
			this.a = ayw;
			this.a(EnumSet.of(aug.a.MOVE, aug.a.LOOK, aug.a.JUMP));
		}

		@Override
		public boolean a() {
			if ((this.a.x_() || this.a.fd()) && this.a.t) {
				if (!this.a.fi()) {
					return false;
				} else {
					float float2 = this.a.p * (float) (Math.PI / 180.0);
					int integer3 = 0;
					int integer4 = 0;
					float float5 = -aec.a(float2);
					float float6 = aec.b(float2);
					if ((double)Math.abs(float5) > 0.5) {
						integer3 = (int)((float)integer3 + float5 / Math.abs(float5));
					}

					if ((double)Math.abs(float6) > 0.5) {
						integer4 = (int)((float)integer4 + float6 / Math.abs(float6));
					}

					if (this.a.l.d_(this.a.cA().b(integer3, -1, integer4)).g()) {
						return true;
					} else {
						return this.a.fd() && this.a.J.nextInt(60) == 1 ? true : this.a.J.nextInt(500) == 1;
					}
				}
			} else {
				return false;
			}
		}

		@Override
		public boolean b() {
			return false;
		}

		@Override
		public void c() {
			this.a.x(true);
		}

		@Override
		public boolean D_() {
			return false;
		}
	}

	class k extends aug {
		private int b;

		public k() {
			this.a(EnumSet.of(aug.a.MOVE));
		}

		@Override
		public boolean a() {
			if (this.b <= ayw.this.K && !ayw.this.x_() && !ayw.this.aA() && ayw.this.fi() && ayw.this.eL() <= 0) {
				List<bbg> list2 = ayw.this.l.a(bbg.class, ayw.this.cb().c(6.0, 6.0, 6.0), ayw.bN);
				return !list2.isEmpty() || !ayw.this.b(aor.MAINHAND).a();
			} else {
				return false;
			}
		}

		@Override
		public boolean b() {
			return !ayw.this.aA() && (ayw.this.fb() || ayw.this.J.nextInt(600) != 1) ? ayw.this.J.nextInt(2000) != 1 : false;
		}

		@Override
		public void e() {
			if (!ayw.this.eN() && !ayw.this.b(aor.MAINHAND).a()) {
				ayw.this.fu();
			}
		}

		@Override
		public void c() {
			List<bbg> list2 = ayw.this.l.a(bbg.class, ayw.this.cb().c(8.0, 8.0, 8.0), ayw.bN);
			if (!list2.isEmpty() && ayw.this.b(aor.MAINHAND).a()) {
				ayw.this.x().a((aom)list2.get(0), 1.2F);
			} else if (!ayw.this.b(aor.MAINHAND).a()) {
				ayw.this.fu();
			}

			this.b = 0;
		}

		@Override
		public void d() {
			bki bki2 = ayw.this.b(aor.MAINHAND);
			if (!bki2.a()) {
				ayw.this.a(bki2);
				ayw.this.a(aor.MAINHAND, bki.b);
				int integer3 = ayw.this.fb() ? ayw.this.J.nextInt(50) + 10 : ayw.this.J.nextInt(150) + 10;
				this.b = ayw.this.K + integer3 * 20;
			}

			ayw.this.t(false);
		}
	}

	static class l extends aug {
		private final ayw a;

		public l(ayw ayw) {
			this.a = ayw;
		}

		@Override
		public boolean a() {
			if (this.a.x_() && this.a.fi()) {
				return this.a.ff() && this.a.J.nextInt(500) == 1 ? true : this.a.J.nextInt(6000) == 1;
			} else {
				return false;
			}
		}

		@Override
		public boolean b() {
			return false;
		}

		@Override
		public void c() {
			this.a.w(true);
		}
	}
}
