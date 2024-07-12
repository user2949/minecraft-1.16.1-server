import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class bbr extends bcb implements ape {
	private static final UUID b = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
	private static final apv c = new apv(b, "Attacking speed boost", 0.15F, apv.a.ADDITION);
	private static final tq<Optional<cfj>> d = tt.a(bbr.class, ts.h);
	private static final tq<Boolean> bv = tt.a(bbr.class, ts.i);
	private static final tq<Boolean> bw = tt.a(bbr.class, ts.i);
	private static final Predicate<aoy> bx = aoy -> aoy instanceof bbs && ((bbs)aoy).eL();
	private int by = Integer.MIN_VALUE;
	private int bz;
	private static final adx bA = aej.a(20, 39);
	private int bB;
	private UUID bC;

	public bbr(aoq<? extends bbr> aoq, bqb bqb) {
		super(aoq, bqb);
		this.G = 1.0F;
		this.a(czb.WATER, -1.0F);
	}

	@Override
	protected void o() {
		this.br.a(0, new aua(this));
		this.br.a(1, new bbr.a(this));
		this.br.a(2, new auq(this, 1.0, false));
		this.br.a(7, new avw(this, 1.0, 0.0F));
		this.br.a(8, new auo(this, bec.class, 8.0F));
		this.br.a(8, new ave(this));
		this.br.a(10, new bbr.b(this));
		this.br.a(11, new bbr.d(this));
		this.bs.a(1, new awc(this, bec.class, 10, true, false, this::b));
		this.bs.a(2, new bbr.c(this));
		this.bs.a(3, new awb(this));
		this.bs.a(4, new awc(this, bbs.class, 10, true, false, bx));
		this.bs.a(5, new awi<>(this, false));
	}

	public static apw.a m() {
		return bcb.eS().a(apx.a, 40.0).a(apx.d, 0.3F).a(apx.f, 7.0).a(apx.b, 64.0);
	}

	@Override
	public void i(@Nullable aoy aoy) {
		super.i(aoy);
		apt apt3 = this.a(apx.d);
		if (aoy == null) {
			this.bz = 0;
			this.S.b(bv, false);
			this.S.b(bw, false);
			apt3.d(c);
		} else {
			this.bz = this.K;
			this.S.b(bv, true);
			if (!apt3.a(c)) {
				apt3.b(c);
			}
		}
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(d, Optional.empty());
		this.S.a(bv, false);
		this.S.a(bw, false);
	}

	@Override
	public void H_() {
		this.a_(bA.a(this.J));
	}

	@Override
	public void a_(int integer) {
		this.bB = integer;
	}

	@Override
	public int F_() {
		return this.bB;
	}

	@Override
	public void a(@Nullable UUID uUID) {
		this.bC = uUID;
	}

	@Override
	public UUID G_() {
		return this.bC;
	}

	public void eL() {
		if (this.K >= this.by + 400) {
			this.by = this.K;
			if (!this.av()) {
				this.l.a(this.cC(), this.cF(), this.cG(), acl.dC, this.ct(), 2.5F, 1.0F, false);
			}
		}
	}

	@Override
	public void a(tq<?> tq) {
		if (bv.equals(tq) && this.eP() && this.l.v) {
			this.eL();
		}

		super.a(tq);
	}

	@Override
	public void b(le le) {
		super.b(le);
		cfj cfj3 = this.eN();
		if (cfj3 != null) {
			le.a("carriedBlockState", lq.a(cfj3));
		}

		this.c(le);
	}

	@Override
	public void a(le le) {
		super.a(le);
		cfj cfj3 = null;
		if (le.c("carriedBlockState", 10)) {
			cfj3 = lq.c(le.p("carriedBlockState"));
			if (cfj3.g()) {
				cfj3 = null;
			}
		}

		this.c(cfj3);
		this.a((zd)this.l, le);
	}

	private boolean g(bec bec) {
		bki bki3 = bec.bt.b.get(3);
		if (bki3.b() == bvs.cU.h()) {
			return false;
		} else {
			dem dem4 = bec.f(1.0F).d();
			dem dem5 = new dem(this.cC() - bec.cC(), this.cF() - bec.cF(), this.cG() - bec.cG());
			double double6 = dem5.f();
			dem5 = dem5.d();
			double double8 = dem4.b(dem5);
			return double8 > 1.0 - 0.025 / double6 ? bec.D(this) : false;
		}
	}

	@Override
	protected float b(apj apj, aon aon) {
		return 2.55F;
	}

	@Override
	public void k() {
		if (this.l.v) {
			for (int integer2 = 0; integer2 < 2; integer2++) {
				this.l.a(hh.Q, this.d(0.5), this.cE() - 0.25, this.g(0.5), (this.J.nextDouble() - 0.5) * 2.0, -this.J.nextDouble(), (this.J.nextDouble() - 0.5) * 2.0);
			}
		}

		this.aX = false;
		if (!this.l.v) {
			this.a((zd)this.l, true);
		}

		super.k();
	}

	@Override
	public boolean dN() {
		return true;
	}

	@Override
	protected void N() {
		if (this.l.J() && this.K >= this.bz + 600) {
			float float2 = this.aO();
			if (float2 > 0.5F && this.l.f(this.cA()) && this.J.nextFloat() * 30.0F < (float2 - 0.4F) * 2.0F) {
				this.i(null);
				this.eM();
			}
		}

		super.N();
	}

	protected boolean eM() {
		if (!this.l.s_() && this.aU()) {
			double double2 = this.cC() + (this.J.nextDouble() - 0.5) * 64.0;
			double double4 = this.cD() + (double)(this.J.nextInt(64) - 32);
			double double6 = this.cG() + (this.J.nextDouble() - 0.5) * 64.0;
			return this.o(double2, double4, double6);
		} else {
			return false;
		}
	}

	private boolean a(aom aom) {
		dem dem3 = new dem(this.cC() - aom.cC(), this.e(0.5) - aom.cF(), this.cG() - aom.cG());
		dem3 = dem3.d();
		double double4 = 16.0;
		double double6 = this.cC() + (this.J.nextDouble() - 0.5) * 8.0 - dem3.b * 16.0;
		double double8 = this.cD() + (double)(this.J.nextInt(16) - 8) - dem3.c * 16.0;
		double double10 = this.cG() + (this.J.nextDouble() - 0.5) * 8.0 - dem3.d * 16.0;
		return this.o(double6, double8, double10);
	}

	private boolean o(double double1, double double2, double double3) {
		fu.a a8 = new fu.a(double1, double2, double3);

		while (a8.v() > 0 && !this.l.d_(a8).c().c()) {
			a8.c(fz.DOWN);
		}

		cfj cfj9 = this.l.d_(a8);
		boolean boolean10 = cfj9.c().c();
		boolean boolean11 = cfj9.m().a(acz.a);
		if (boolean10 && !boolean11) {
			boolean boolean12 = this.a(double1, double2, double3, true);
			if (boolean12 && !this.av()) {
				this.l.a(null, this.m, this.n, this.o, acl.dD, this.ct(), 1.0F, 1.0F);
				this.a(acl.dD, 1.0F, 1.0F);
			}

			return boolean12;
		} else {
			return false;
		}
	}

	@Override
	protected ack I() {
		return this.eO() ? acl.dB : acl.dy;
	}

	@Override
	protected ack e(anw anw) {
		return acl.dA;
	}

	@Override
	protected ack dp() {
		return acl.dz;
	}

	@Override
	protected void a(anw anw, int integer, boolean boolean3) {
		super.a(anw, integer, boolean3);
		cfj cfj5 = this.eN();
		if (cfj5 != null) {
			this.a(cfj5.b());
		}
	}

	public void c(@Nullable cfj cfj) {
		this.S.b(d, Optional.ofNullable(cfj));
	}

	@Nullable
	public cfj eN() {
		return (cfj)this.S.a(d).orElse(null);
	}

	@Override
	public boolean a(anw anw, float float2) {
		if (this.b(anw)) {
			return false;
		} else if (anw instanceof any) {
			for (int integer4 = 0; integer4 < 64; integer4++) {
				if (this.eM()) {
					return true;
				}
			}

			return false;
		} else {
			boolean boolean4 = super.a(anw, float2);
			if (!this.l.s_() && this.J.nextInt(10) != 0) {
				this.eM();
			}

			return boolean4;
		}
	}

	public boolean eO() {
		return this.S.a(bv);
	}

	public boolean eP() {
		return this.S.a(bw);
	}

	public void eQ() {
		this.S.b(bw, true);
	}

	@Override
	public boolean K() {
		return super.K() || this.eN() != null;
	}

	static class a extends aug {
		private final bbr a;
		private aoy b;

		public a(bbr bbr) {
			this.a = bbr;
			this.a(EnumSet.of(aug.a.JUMP, aug.a.MOVE));
		}

		@Override
		public boolean a() {
			this.b = this.a.A();
			if (!(this.b instanceof bec)) {
				return false;
			} else {
				double double2 = this.b.h(this.a);
				return double2 > 256.0 ? false : this.a.g((bec)this.b);
			}
		}

		@Override
		public void c() {
			this.a.x().o();
		}

		@Override
		public void e() {
			this.a.t().a(this.b.cC(), this.b.cF(), this.b.cG());
		}
	}

	static class b extends aug {
		private final bbr a;

		public b(bbr bbr) {
			this.a = bbr;
		}

		@Override
		public boolean a() {
			if (this.a.eN() == null) {
				return false;
			} else {
				return !this.a.l.S().b(bpx.b) ? false : this.a.cX().nextInt(2000) == 0;
			}
		}

		@Override
		public void e() {
			Random random2 = this.a.cX();
			bqc bqc3 = this.a.l;
			int integer4 = aec.c(this.a.cC() - 1.0 + random2.nextDouble() * 2.0);
			int integer5 = aec.c(this.a.cD() + random2.nextDouble() * 2.0);
			int integer6 = aec.c(this.a.cG() - 1.0 + random2.nextDouble() * 2.0);
			fu fu7 = new fu(integer4, integer5, integer6);
			cfj cfj8 = bqc3.d_(fu7);
			fu fu9 = fu7.c();
			cfj cfj10 = bqc3.d_(fu9);
			cfj cfj11 = this.a.eN();
			if (cfj11 != null && this.a(bqc3, fu7, cfj11, cfj8, cfj10, fu9)) {
				bqc3.a(fu7, cfj11, 3);
				this.a.c(null);
			}
		}

		private boolean a(bqd bqd, fu fu2, cfj cfj3, cfj cfj4, cfj cfj5, fu fu6) {
			return cfj4.g() && !cfj5.g() && cfj5.r(bqd, fu6) && cfj3.a(bqd, fu2);
		}
	}

	static class c extends awc<bec> {
		private final bbr i;
		private bec j;
		private int k;
		private int l;
		private final axs m;
		private final axs n = new axs().c();

		public c(bbr bbr) {
			super(bbr, bec.class, false);
			this.i = bbr;
			this.m = new axs().a(this.k()).a(aoy -> bbr.g((bec)aoy));
		}

		@Override
		public boolean a() {
			this.j = this.i.l.a(this.m, this.i);
			return this.j != null;
		}

		@Override
		public void c() {
			this.k = 5;
			this.l = 0;
			this.i.eQ();
		}

		@Override
		public void d() {
			this.j = null;
			super.d();
		}

		@Override
		public boolean b() {
			if (this.j != null) {
				if (!this.i.g(this.j)) {
					return false;
				} else {
					this.i.a(this.j, 10.0F, 10.0F);
					return true;
				}
			} else {
				return this.c != null && this.n.a(this.i, this.c) ? true : super.b();
			}
		}

		@Override
		public void e() {
			if (this.i.A() == null) {
				super.a(null);
			}

			if (this.j != null) {
				if (--this.k <= 0) {
					this.c = this.j;
					this.j = null;
					super.c();
				}
			} else {
				if (this.c != null && !this.i.bn()) {
					if (this.i.g((bec)this.c)) {
						if (this.c.h(this.i) < 16.0) {
							this.i.eM();
						}

						this.l = 0;
					} else if (this.c.h(this.i) > 256.0 && this.l++ >= 30 && this.i.a(this.c)) {
						this.l = 0;
					}
				}

				super.e();
			}
		}
	}

	static class d extends aug {
		private final bbr a;

		public d(bbr bbr) {
			this.a = bbr;
		}

		@Override
		public boolean a() {
			if (this.a.eN() != null) {
				return false;
			} else {
				return !this.a.l.S().b(bpx.b) ? false : this.a.cX().nextInt(20) == 0;
			}
		}

		@Override
		public void e() {
			Random random2 = this.a.cX();
			bqb bqb3 = this.a.l;
			int integer4 = aec.c(this.a.cC() - 2.0 + random2.nextDouble() * 4.0);
			int integer5 = aec.c(this.a.cD() + random2.nextDouble() * 3.0);
			int integer6 = aec.c(this.a.cG() - 2.0 + random2.nextDouble() * 4.0);
			fu fu7 = new fu(integer4, integer5, integer6);
			cfj cfj8 = bqb3.d_(fu7);
			bvr bvr9 = cfj8.b();
			dem dem10 = new dem((double)aec.c(this.a.cC()) + 0.5, (double)integer5 + 0.5, (double)aec.c(this.a.cG()) + 0.5);
			dem dem11 = new dem((double)integer4 + 0.5, (double)integer5 + 0.5, (double)integer6 + 0.5);
			deh deh12 = bqb3.a(new bpj(dem10, dem11, bpj.a.OUTLINE, bpj.b.NONE, this.a));
			boolean boolean13 = deh12.a().equals(fu7);
			if (bvr9.a(acx.S) && boolean13) {
				this.a.c(cfj8);
				bqb3.a(fu7, false);
			}
		}
	}
}
