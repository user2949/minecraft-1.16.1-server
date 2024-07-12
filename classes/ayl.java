import com.google.common.collect.Lists;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class ayl extends ayk implements ape, ayr {
	private static final tq<Byte> bv = tt.a(ayl.class, ts.a);
	private static final tq<Integer> bw = tt.a(ayl.class, ts.b);
	private static final adx bx = aej.a(20, 39);
	private UUID by;
	private float bz;
	private float bA;
	private int bB;
	private int bC;
	private int bD;
	private int bE;
	private int bF = 0;
	private int bG = 0;
	@Nullable
	private fu bH = null;
	@Nullable
	private fu bI = null;
	private ayl.k bJ;
	private ayl.e bK;
	private ayl.f bL;
	private int bM;

	public ayl(aoq<? extends ayl> aoq, bqb bqb) {
		super(aoq, bqb);
		this.bo = new atj(this, 20, true);
		this.g = new ayl.j(this);
		this.a(czb.DANGER_FIRE, -1.0F);
		this.a(czb.WATER, -1.0F);
		this.a(czb.WATER_BORDER, 16.0F);
		this.a(czb.COCOA, -1.0F);
		this.a(czb.FENCE, -1.0F);
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(bv, (byte)0);
		this.S.a(bw, 0);
	}

	@Override
	public float a(fu fu, bqd bqd) {
		return bqd.d_(fu).g() ? 10.0F : 0.0F;
	}

	@Override
	protected void o() {
		this.br.a(0, new ayl.b(this, 1.4F, true));
		this.br.a(1, new ayl.d());
		this.br.a(2, new att(this, 1.0));
		this.br.a(3, new avr(this, 1.25, bmr.a(ada.L), false));
		this.bJ = new ayl.k();
		this.br.a(4, this.bJ);
		this.br.a(5, new auf(this, 1.25));
		this.br.a(5, new ayl.i());
		this.bK = new ayl.e();
		this.br.a(5, this.bK);
		this.bL = new ayl.f();
		this.br.a(6, this.bL);
		this.br.a(7, new ayl.g());
		this.br.a(8, new ayl.l());
		this.br.a(9, new aua(this));
		this.bs.a(1, new ayl.h(this).a(new Class[0]));
		this.bs.a(2, new ayl.c(this));
		this.bs.a(3, new awi<>(this, true));
	}

	@Override
	public void b(le le) {
		super.b(le);
		if (this.eV()) {
			le.a("HivePos", lq.a(this.eW()));
		}

		if (this.eM()) {
			le.a("FlowerPos", lq.a(this.eL()));
		}

		le.a("HasNectar", this.eY());
		le.a("HasStung", this.eZ());
		le.b("TicksSincePollination", this.bC);
		le.b("CannotEnterHiveTicks", this.bD);
		le.b("CropsGrownSincePollination", this.bE);
		this.c(le);
	}

	@Override
	public void a(le le) {
		this.bI = null;
		if (le.e("HivePos")) {
			this.bI = lq.b(le.p("HivePos"));
		}

		this.bH = null;
		if (le.e("FlowerPos")) {
			this.bH = lq.b(le.p("FlowerPos"));
		}

		super.a(le);
		this.t(le.q("HasNectar"));
		this.u(le.q("HasStung"));
		this.bC = le.h("TicksSincePollination");
		this.bD = le.h("CannotEnterHiveTicks");
		this.bE = le.h("CropsGrownSincePollination");
		this.a((zd)this.l, le);
	}

	@Override
	public boolean B(aom aom) {
		boolean boolean3 = aom.a(anw.b(this), (float)((int)this.b(apx.f)));
		if (boolean3) {
			this.a(this, aom);
			if (aom instanceof aoy) {
				((aoy)aom).q(((aoy)aom).dy() + 1);
				int integer4 = 0;
				if (this.l.ac() == and.NORMAL) {
					integer4 = 10;
				} else if (this.l.ac() == and.HARD) {
					integer4 = 18;
				}

				if (integer4 > 0) {
					((aoy)aom).c(new aog(aoi.s, integer4 * 20, 0));
				}
			}

			this.u(true);
			this.K_();
			this.a(acl.aC, 1.0F, 1.0F);
		}

		return boolean3;
	}

	@Override
	public void j() {
		super.j();
		if (this.eY() && this.fh() < 10 && this.J.nextFloat() < 0.05F) {
			for (int integer2 = 0; integer2 < this.J.nextInt(2) + 1; integer2++) {
				this.a(this.l, this.cC() - 0.3F, this.cC() + 0.3F, this.cG() - 0.3F, this.cG() + 0.3F, this.e(0.5), hh.al);
			}
		}

		this.ff();
	}

	private void a(bqb bqb, double double2, double double3, double double4, double double5, double double6, hf hf) {
		bqb.a(hf, aec.d(bqb.t.nextDouble(), double2, double3), double6, aec.d(bqb.t.nextDouble(), double4, double5), 0.0, 0.0, 0.0);
	}

	private void h(fu fu) {
		dem dem3 = dem.c(fu);
		int integer4 = 0;
		fu fu5 = this.cA();
		int integer6 = (int)dem3.c - fu5.v();
		if (integer6 > 2) {
			integer4 = 4;
		} else if (integer6 < -2) {
			integer4 = -4;
		}

		int integer7 = 6;
		int integer8 = 8;
		int integer9 = fu5.k(fu);
		if (integer9 < 15) {
			integer7 = integer9 / 2;
			integer8 = integer9 / 2;
		}

		dem dem10 = axu.b(this, integer7, integer8, integer4, dem3, (float) (Math.PI / 10));
		if (dem10 != null) {
			this.bq.a(0.5F);
			this.bq.a(dem10.b, dem10.c, dem10.d, 1.0);
		}
	}

	@Nullable
	public fu eL() {
		return this.bH;
	}

	public boolean eM() {
		return this.bH != null;
	}

	public void g(fu fu) {
		this.bH = fu;
	}

	private boolean fd() {
		return this.bC > 3600;
	}

	private boolean fe() {
		if (this.bD <= 0 && !this.bJ.k() && !this.eZ() && this.A() == null) {
			boolean boolean2 = this.fd() || this.l.U() || this.l.K() || this.eY();
			return boolean2 && !this.fg();
		} else {
			return false;
		}
	}

	public void t(int integer) {
		this.bD = integer;
	}

	private void ff() {
		this.bA = this.bz;
		if (this.fl()) {
			this.bz = Math.min(1.0F, this.bz + 0.2F);
		} else {
			this.bz = Math.max(0.0F, this.bz - 0.24F);
		}
	}

	@Override
	protected void N() {
		boolean boolean2 = this.eZ();
		if (this.aD()) {
			this.bM++;
		} else {
			this.bM = 0;
		}

		if (this.bM > 20) {
			this.a(anw.h, 1.0F);
		}

		if (boolean2) {
			this.bB++;
			if (this.bB % 5 == 0 && this.J.nextInt(aec.a(1200 - this.bB, 1, 1200)) == 0) {
				this.a(anw.n, this.dj());
			}
		}

		if (!this.eY()) {
			this.bC++;
		}

		if (!this.l.v) {
			this.a((zd)this.l, false);
		}
	}

	public void eP() {
		this.bC = 0;
	}

	private boolean fg() {
		if (this.bI == null) {
			return false;
		} else {
			cdl cdl2 = this.l.c(this.bI);
			return cdl2 instanceof cdi && ((cdi)cdl2).d();
		}
	}

	@Override
	public int F_() {
		return this.S.a(bw);
	}

	@Override
	public void a_(int integer) {
		this.S.b(bw, integer);
	}

	@Override
	public UUID G_() {
		return this.by;
	}

	@Override
	public void a(@Nullable UUID uUID) {
		this.by = uUID;
	}

	@Override
	public void H_() {
		this.a_(bx.a(this.J));
	}

	private boolean i(fu fu) {
		cdl cdl3 = this.l.c(fu);
		return cdl3 instanceof cdi ? !((cdi)cdl3).h() : false;
	}

	public boolean eV() {
		return this.bI != null;
	}

	@Nullable
	public fu eW() {
		return this.bI;
	}

	@Override
	protected void M() {
		super.M();
		qy.a(this);
	}

	private int fh() {
		return this.bE;
	}

	private void fi() {
		this.bE = 0;
	}

	private void fj() {
		this.bE++;
	}

	@Override
	public void k() {
		super.k();
		if (!this.l.v) {
			if (this.bD > 0) {
				this.bD--;
			}

			if (this.bF > 0) {
				this.bF--;
			}

			if (this.bG > 0) {
				this.bG--;
			}

			boolean boolean2 = this.I_() && !this.eZ() && this.A() != null && this.A().h(this) < 4.0;
			this.v(boolean2);
			if (this.K % 20 == 0 && !this.fk()) {
				this.bI = null;
			}
		}
	}

	private boolean fk() {
		if (!this.eV()) {
			return false;
		} else {
			cdl cdl2 = this.l.c(this.bI);
			return cdl2 != null && cdl2.u() == cdm.G;
		}
	}

	public boolean eY() {
		return this.u(8);
	}

	private void t(boolean boolean1) {
		if (boolean1) {
			this.eP();
		}

		this.d(8, boolean1);
	}

	public boolean eZ() {
		return this.u(4);
	}

	private void u(boolean boolean1) {
		this.d(4, boolean1);
	}

	private boolean fl() {
		return this.u(2);
	}

	private void v(boolean boolean1) {
		this.d(2, boolean1);
	}

	private boolean j(fu fu) {
		return !this.b(fu, 32);
	}

	private void d(int integer, boolean boolean2) {
		if (boolean2) {
			this.S.b(bv, (byte)(this.S.a(bv) | integer));
		} else {
			this.S.b(bv, (byte)(this.S.a(bv) & ~integer));
		}
	}

	private boolean u(int integer) {
		return (this.S.a(bv) & integer) != 0;
	}

	public static apw.a fa() {
		return aoz.p().a(apx.a, 10.0).a(apx.e, 0.6F).a(apx.d, 0.3F).a(apx.f, 2.0).a(apx.b, 48.0);
	}

	@Override
	protected awv b(bqb bqb) {
		awt awt3 = new awt(this, bqb) {
			@Override
			public boolean a(fu fu) {
				return !this.b.d_(fu.c()).g();
			}

			@Override
			public void c() {
				if (!ayl.this.bJ.k()) {
					super.c();
				}
			}
		};
		awt3.a(false);
		awt3.d(false);
		awt3.b(true);
		return awt3;
	}

	@Override
	public boolean k(bki bki) {
		return bki.b().a(ada.L);
	}

	private boolean k(fu fu) {
		return this.l.p(fu) && this.l.d_(fu).b().a(acx.N);
	}

	@Override
	protected void a(fu fu, cfj cfj) {
	}

	@Override
	protected ack I() {
		return null;
	}

	@Override
	protected ack e(anw anw) {
		return acl.az;
	}

	@Override
	protected ack dp() {
		return acl.ay;
	}

	@Override
	protected float dF() {
		return 0.4F;
	}

	public ayl a(aok aok) {
		return aoq.e.a(this.l);
	}

	@Override
	protected float b(apj apj, aon aon) {
		return this.x_() ? aon.b * 0.5F : aon.b * 0.5F;
	}

	@Override
	public boolean b(float float1, float float2) {
		return false;
	}

	@Override
	protected void a(double double1, boolean boolean2, cfj cfj, fu fu) {
	}

	@Override
	protected boolean au() {
		return true;
	}

	public void fc() {
		this.t(false);
		this.fi();
	}

	@Override
	public boolean a(anw anw, float float2) {
		if (this.b(anw)) {
			return false;
		} else {
			aom aom4 = anw.k();
			if (!this.l.v) {
				this.bJ.l();
			}

			return super.a(anw, float2);
		}
	}

	@Override
	public apc dB() {
		return apc.c;
	}

	@Override
	protected void c(adf<cwz> adf) {
		this.e(this.cB().b(0.0, 0.01, 0.0));
	}

	private boolean b(fu fu, int integer) {
		return fu.a(this.cA(), (double)integer);
	}

	abstract class a extends aug {
		private a() {
		}

		public abstract boolean g();

		public abstract boolean h();

		@Override
		public boolean a() {
			return this.g() && !ayl.this.I_();
		}

		@Override
		public boolean b() {
			return this.h() && !ayl.this.I_();
		}
	}

	class b extends auq {
		b(apg apg, double double3, boolean boolean4) {
			super(apg, double3, boolean4);
		}

		@Override
		public boolean a() {
			return super.a() && ayl.this.I_() && !ayl.this.eZ();
		}

		@Override
		public boolean b() {
			return super.b() && ayl.this.I_() && !ayl.this.eZ();
		}
	}

	static class c extends awc<bec> {
		c(ayl ayl) {
			super(ayl, bec.class, 10, true, false, ayl::b);
		}

		@Override
		public boolean a() {
			return this.h() && super.a();
		}

		@Override
		public boolean b() {
			boolean boolean2 = this.h();
			if (boolean2 && this.e.A() != null) {
				return super.b();
			} else {
				this.g = null;
				return false;
			}
		}

		private boolean h() {
			ayl ayl2 = (ayl)this.e;
			return ayl2.I_() && !ayl2.eZ();
		}
	}

	class d extends ayl.a {
		private d() {
		}

		@Override
		public boolean g() {
			if (ayl.this.eV() && ayl.this.fe() && ayl.this.bI.a(ayl.this.cz(), 2.0)) {
				cdl cdl2 = ayl.this.l.c(ayl.this.bI);
				if (cdl2 instanceof cdi) {
					cdi cdi3 = (cdi)cdl2;
					if (!cdi3.h()) {
						return true;
					}

					ayl.this.bI = null;
				}
			}

			return false;
		}

		@Override
		public boolean h() {
			return false;
		}

		@Override
		public void c() {
			cdl cdl2 = ayl.this.l.c(ayl.this.bI);
			if (cdl2 instanceof cdi) {
				cdi cdi3 = (cdi)cdl2;
				cdi3.a(ayl.this, ayl.this.eY());
			}
		}
	}

	public class e extends ayl.a {
		private int c = ayl.this.l.t.nextInt(10);
		private List<fu> d = Lists.<fu>newArrayList();
		@Nullable
		private czf e = null;
		private int f;

		e() {
			this.a(EnumSet.of(aug.a.MOVE));
		}

		@Override
		public boolean g() {
			return ayl.this.bI != null && !ayl.this.eA() && ayl.this.fe() && !this.d(ayl.this.bI) && ayl.this.l.d_(ayl.this.bI).a(acx.ai);
		}

		@Override
		public boolean h() {
			return this.g();
		}

		@Override
		public void c() {
			this.c = 0;
			this.f = 0;
			super.c();
		}

		@Override
		public void d() {
			this.c = 0;
			this.f = 0;
			ayl.this.bq.o();
			ayl.this.bq.g();
		}

		@Override
		public void e() {
			if (ayl.this.bI != null) {
				this.c++;
				if (this.c > 600) {
					this.k();
				} else if (!ayl.this.bq.n()) {
					if (!ayl.this.b(ayl.this.bI, 16)) {
						if (ayl.this.j(ayl.this.bI)) {
							this.l();
						} else {
							ayl.this.h(ayl.this.bI);
						}
					} else {
						boolean boolean2 = this.a(ayl.this.bI);
						if (!boolean2) {
							this.k();
						} else if (this.e != null && ayl.this.bq.k().a(this.e)) {
							this.f++;
							if (this.f > 60) {
								this.l();
								this.f = 0;
							}
						} else {
							this.e = ayl.this.bq.k();
						}
					}
				}
			}
		}

		private boolean a(fu fu) {
			ayl.this.bq.a(10.0F);
			ayl.this.bq.a((double)fu.u(), (double)fu.v(), (double)fu.w(), 1.0);
			return ayl.this.bq.k() != null && ayl.this.bq.k().i();
		}

		private boolean b(fu fu) {
			return this.d.contains(fu);
		}

		private void c(fu fu) {
			this.d.add(fu);

			while (this.d.size() > 3) {
				this.d.remove(0);
			}
		}

		private void j() {
			this.d.clear();
		}

		private void k() {
			if (ayl.this.bI != null) {
				this.c(ayl.this.bI);
			}

			this.l();
		}

		private void l() {
			ayl.this.bI = null;
			ayl.this.bF = 200;
		}

		private boolean d(fu fu) {
			if (ayl.this.b(fu, 2)) {
				return true;
			} else {
				czf czf3 = ayl.this.bq.k();
				return czf3 != null && czf3.m().equals(fu) && czf3.i() && czf3.b();
			}
		}
	}

	public class f extends ayl.a {
		private int c = ayl.this.l.t.nextInt(10);

		f() {
			this.a(EnumSet.of(aug.a.MOVE));
		}

		@Override
		public boolean g() {
			return ayl.this.bH != null && !ayl.this.eA() && this.j() && ayl.this.k(ayl.this.bH) && !ayl.this.b(ayl.this.bH, 2);
		}

		@Override
		public boolean h() {
			return this.g();
		}

		@Override
		public void c() {
			this.c = 0;
			super.c();
		}

		@Override
		public void d() {
			this.c = 0;
			ayl.this.bq.o();
			ayl.this.bq.g();
		}

		@Override
		public void e() {
			if (ayl.this.bH != null) {
				this.c++;
				if (this.c > 600) {
					ayl.this.bH = null;
				} else if (!ayl.this.bq.n()) {
					if (ayl.this.j(ayl.this.bH)) {
						ayl.this.bH = null;
					} else {
						ayl.this.h(ayl.this.bH);
					}
				}
			}
		}

		private boolean j() {
			return ayl.this.bC > 2400;
		}
	}

	class g extends ayl.a {
		private g() {
		}

		@Override
		public boolean g() {
			if (ayl.this.fh() >= 10) {
				return false;
			} else {
				return ayl.this.J.nextFloat() < 0.3F ? false : ayl.this.eY() && ayl.this.fk();
			}
		}

		@Override
		public boolean h() {
			return this.g();
		}

		@Override
		public void e() {
			if (ayl.this.J.nextInt(30) == 0) {
				for (int integer2 = 1; integer2 <= 2; integer2++) {
					fu fu3 = ayl.this.cA().c(integer2);
					cfj cfj4 = ayl.this.l.d_(fu3);
					bvr bvr5 = cfj4.b();
					boolean boolean6 = false;
					cgi cgi7 = null;
					if (bvr5.a(acx.ak)) {
						if (bvr5 instanceof bwv) {
							bwv bwv8 = (bwv)bvr5;
							if (!bwv8.h(cfj4)) {
								boolean6 = true;
								cgi7 = bwv8.c();
							}
						} else if (bvr5 instanceof cbp) {
							int integer8 = (Integer)cfj4.c(cbp.a);
							if (integer8 < 7) {
								boolean6 = true;
								cgi7 = cbp.a;
							}
						} else if (bvr5 == bvs.mg) {
							int integer8 = (Integer)cfj4.c(cbw.a);
							if (integer8 < 3) {
								boolean6 = true;
								cgi7 = cbw.a;
							}
						}

						if (boolean6) {
							ayl.this.l.c(2005, fu3, 0);
							ayl.this.l.a(fu3, cfj4.a(cgi7, Integer.valueOf((Integer)cfj4.c(cgi7) + 1)));
							ayl.this.fj();
						}
					}
				}
			}
		}
	}

	class h extends awb {
		h(ayl ayl2) {
			super(ayl2);
		}

		@Override
		public boolean b() {
			return ayl.this.I_() && super.b();
		}

		@Override
		protected void a(aoz aoz, aoy aoy) {
			if (aoz instanceof ayl && this.e.D(aoy)) {
				aoz.i(aoy);
			}
		}
	}

	class i extends ayl.a {
		private i() {
		}

		@Override
		public boolean g() {
			return ayl.this.bF == 0 && !ayl.this.eV() && ayl.this.fe();
		}

		@Override
		public boolean h() {
			return false;
		}

		@Override
		public void c() {
			ayl.this.bF = 200;
			List<fu> list2 = this.j();
			if (!list2.isEmpty()) {
				for (fu fu4 : list2) {
					if (!ayl.this.bK.b(fu4)) {
						ayl.this.bI = fu4;
						return;
					}
				}

				ayl.this.bK.j();
				ayl.this.bI = (fu)list2.get(0);
			}
		}

		private List<fu> j() {
			fu fu2 = ayl.this.cA();
			axz axz3 = ((zd)ayl.this.l).x();
			Stream<aya> stream4 = axz3.c(ayc -> ayc == ayc.t || ayc == ayc.u, fu2, 20, axz.b.ANY);
			return (List<fu>)stream4.map(aya::f).filter(fu -> ayl.this.i(fu)).sorted(Comparator.comparingDouble(fu2x -> fu2x.j(fu2))).collect(Collectors.toList());
		}
	}

	class j extends atl {
		j(aoz aoz) {
			super(aoz);
		}

		@Override
		public void a() {
			if (!ayl.this.I_()) {
				super.a();
			}
		}

		@Override
		protected boolean b() {
			return !ayl.this.bJ.k();
		}
	}

	class k extends ayl.a {
		private final Predicate<cfj> c = cfj -> {
			if (cfj.a(acx.M)) {
				return cfj.a(bvs.gU) ? cfj.c(bxg.a) == cgf.UPPER : true;
			} else {
				return cfj.a(acx.J);
			}
		};
		private int d = 0;
		private int e = 0;
		private boolean f;
		private dem g;
		private int h = 0;

		k() {
			this.a(EnumSet.of(aug.a.MOVE));
		}

		@Override
		public boolean g() {
			if (ayl.this.bG > 0) {
				return false;
			} else if (ayl.this.eY()) {
				return false;
			} else if (ayl.this.l.U()) {
				return false;
			} else if (ayl.this.J.nextFloat() < 0.7F) {
				return false;
			} else {
				Optional<fu> optional2 = this.o();
				if (optional2.isPresent()) {
					ayl.this.bH = (fu)optional2.get();
					ayl.this.bq.a((double)ayl.this.bH.u() + 0.5, (double)ayl.this.bH.v() + 0.5, (double)ayl.this.bH.w() + 0.5, 1.2F);
					return true;
				} else {
					return false;
				}
			}
		}

		@Override
		public boolean h() {
			if (!this.f) {
				return false;
			} else if (!ayl.this.eM()) {
				return false;
			} else if (ayl.this.l.U()) {
				return false;
			} else if (this.j()) {
				return ayl.this.J.nextFloat() < 0.2F;
			} else if (ayl.this.K % 20 == 0 && !ayl.this.k(ayl.this.bH)) {
				ayl.this.bH = null;
				return false;
			} else {
				return true;
			}
		}

		private boolean j() {
			return this.d > 400;
		}

		private boolean k() {
			return this.f;
		}

		private void l() {
			this.f = false;
		}

		@Override
		public void c() {
			this.d = 0;
			this.h = 0;
			this.e = 0;
			this.f = true;
			ayl.this.eP();
		}

		@Override
		public void d() {
			if (this.j()) {
				ayl.this.t(true);
			}

			this.f = false;
			ayl.this.bq.o();
			ayl.this.bG = 200;
		}

		@Override
		public void e() {
			this.h++;
			if (this.h > 600) {
				ayl.this.bH = null;
			} else {
				dem dem2 = dem.c(ayl.this.bH).b(0.0, 0.6F, 0.0);
				if (dem2.f(ayl.this.cz()) > 1.0) {
					this.g = dem2;
					this.m();
				} else {
					if (this.g == null) {
						this.g = dem2;
					}

					boolean boolean3 = ayl.this.cz().f(this.g) <= 0.1;
					boolean boolean4 = true;
					if (!boolean3 && this.h > 600) {
						ayl.this.bH = null;
					} else {
						if (boolean3) {
							boolean boolean5 = ayl.this.J.nextInt(25) == 0;
							if (boolean5) {
								this.g = new dem(dem2.a() + (double)this.n(), dem2.b(), dem2.c() + (double)this.n());
								ayl.this.bq.o();
							} else {
								boolean4 = false;
							}

							ayl.this.t().a(dem2.a(), dem2.b(), dem2.c());
						}

						if (boolean4) {
							this.m();
						}

						this.d++;
						if (ayl.this.J.nextFloat() < 0.05F && this.d > this.e + 60) {
							this.e = this.d;
							ayl.this.a(acl.aD, 1.0F, 1.0F);
						}
					}
				}
			}
		}

		private void m() {
			ayl.this.u().a(this.g.a(), this.g.b(), this.g.c(), 0.35F);
		}

		private float n() {
			return (ayl.this.J.nextFloat() * 2.0F - 1.0F) * 0.33333334F;
		}

		private Optional<fu> o() {
			return this.a(this.c, 5.0);
		}

		private Optional<fu> a(Predicate<cfj> predicate, double double2) {
			fu fu5 = ayl.this.cA();
			fu.a a6 = new fu.a();

			for (int integer7 = 0; (double)integer7 <= double2; integer7 = integer7 > 0 ? -integer7 : 1 - integer7) {
				for (int integer8 = 0; (double)integer8 < double2; integer8++) {
					for (int integer9 = 0; integer9 <= integer8; integer9 = integer9 > 0 ? -integer9 : 1 - integer9) {
						for (int integer10 = integer9 < integer8 && integer9 > -integer8 ? integer8 : 0;
							integer10 <= integer8;
							integer10 = integer10 > 0 ? -integer10 : 1 - integer10
						) {
							a6.a(fu5, integer9, integer7 - 1, integer10);
							if (fu5.a(a6, double2) && predicate.test(ayl.this.l.d_(a6))) {
								return Optional.of(a6);
							}
						}
					}
				}
			}

			return Optional.empty();
		}
	}

	class l extends aug {
		l() {
			this.a(EnumSet.of(aug.a.MOVE));
		}

		@Override
		public boolean a() {
			return ayl.this.bq.m() && ayl.this.J.nextInt(10) == 0;
		}

		@Override
		public boolean b() {
			return ayl.this.bq.n();
		}

		@Override
		public void c() {
			dem dem2 = this.g();
			if (dem2 != null) {
				ayl.this.bq.a(ayl.this.bq.a(new fu(dem2), 1), 1.0);
			}
		}

		@Nullable
		private dem g() {
			dem dem2;
			if (ayl.this.fk() && !ayl.this.b(ayl.this.bI, 22)) {
				dem dem3 = dem.a(ayl.this.bI);
				dem2 = dem3.d(ayl.this.cz()).d();
			} else {
				dem2 = ayl.this.f(0.0F);
			}

			int integer3 = 8;
			dem dem4 = axu.a(ayl.this, 8, 7, dem2, (float) (Math.PI / 2), 2, 1);
			return dem4 != null ? dem4 : axu.a(ayl.this, 8, 4, -2, dem2, (float) (Math.PI / 2));
		}
	}
}
