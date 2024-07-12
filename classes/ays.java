import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class ays extends ayk {
	private static final tq<Integer> bv = tt.a(ays.class, ts.b);
	private static final tq<Byte> bw = tt.a(ays.class, ts.a);
	private static final tq<Optional<UUID>> bx = tt.a(ays.class, ts.o);
	private static final tq<Optional<UUID>> by = tt.a(ays.class, ts.o);
	private static final Predicate<bbg> bz = bbg -> !bbg.p() && bbg.aU();
	private static final Predicate<aom> bA = aom -> {
		if (!(aom instanceof aoy)) {
			return false;
		} else {
			aoy aoy2 = (aoy)aom;
			return aoy2.da() != null && aoy2.db() < aoy2.K + 600;
		}
	};
	private static final Predicate<aom> bB = aom -> aom instanceof ayn || aom instanceof azb;
	private static final Predicate<aom> bC = aom -> !aom.bt() && aop.e.test(aom);
	private aug bD;
	private aug bE;
	private aug bF;
	private float bG;
	private float bH;
	private float bI;
	private float bJ;
	private int bK;

	public ays(aoq<? extends ays> aoq, bqb bqb) {
		super(aoq, bqb);
		this.g = new ays.k();
		this.bo = new ays.m();
		this.a(czb.DANGER_OTHER, 0.0F);
		this.a(czb.DAMAGE_OTHER, 0.0F);
		this.p(true);
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(bx, Optional.empty());
		this.S.a(by, Optional.empty());
		this.S.a(bv, 0);
		this.S.a(bw, (byte)0);
	}

	@Override
	protected void o() {
		this.bD = new awc(this, ayk.class, 10, false, false, aoy -> aoy instanceof ayn || aoy instanceof azb);
		this.bE = new awc(this, azi.class, 10, false, false, azi.bv);
		this.bF = new awc(this, ayh.class, 20, false, false, aoy -> aoy instanceof ayj);
		this.br.a(0, new ays.g());
		this.br.a(1, new ays.b());
		this.br.a(2, new ays.n(2.2));
		this.br.a(3, new ays.e(1.0));
		this.br.a(4, new ato(this, bec.class, 16.0F, 1.6, 1.4, aoy -> bC.test(aoy) && !this.c(aoy.bR()) && !this.fc()));
		this.br.a(4, new ato(this, azk.class, 8.0F, 1.6, 1.4, aoy -> !((azk)aoy).eL() && !this.fc()));
		this.br.a(4, new ato(this, ayz.class, 8.0F, 1.6, 1.4, aoy -> !this.fc()));
		this.br.a(5, new ays.u());
		this.br.a(6, new ays.o());
		this.br.a(6, new ays.s(1.25));
		this.br.a(7, new ays.l(1.2F, true));
		this.br.a(7, new ays.t());
		this.br.a(8, new ays.h(this, 1.25));
		this.br.a(9, new ays.q(32, 200));
		this.br.a(10, new ays.f(1.2F, 12, 2));
		this.br.a(10, new aum(this, 0.4F));
		this.br.a(11, new avw(this, 1.0));
		this.br.a(11, new ays.p());
		this.br.a(12, new ays.j(this, bec.class, 24.0F));
		this.br.a(13, new ays.r());
		this.bs.a(3, new ays.a(aoy.class, false, false, aoy -> bA.test(aoy) && !this.c(aoy.bR())));
	}

	@Override
	public ack d(bki bki) {
		return acl.et;
	}

	@Override
	public void k() {
		if (!this.l.v && this.aU() && this.dR()) {
			this.bK++;
			bki bki2 = this.b(aor.MAINHAND);
			if (this.l(bki2)) {
				if (this.bK > 600) {
					bki bki3 = bki2.a(this.l, this);
					if (!bki3.a()) {
						this.a(aor.MAINHAND, bki3);
					}

					this.bK = 0;
				} else if (this.bK > 560 && this.J.nextFloat() < 0.1F) {
					this.a(this.d(bki2), 1.0F, 1.0F);
					this.l.a(this, (byte)45);
				}
			}

			aoy aoy3 = this.A();
			if (aoy3 == null || !aoy3.aU()) {
				this.v(false);
				this.w(false);
			}
		}

		if (this.el() || this.dH()) {
			this.aX = false;
			this.aY = 0.0F;
			this.ba = 0.0F;
		}

		super.k();
		if (this.fc() && this.J.nextFloat() < 0.05F) {
			this.a(acl.ep, 1.0F, 1.0F);
		}
	}

	@Override
	protected boolean dH() {
		return this.dk();
	}

	private boolean l(bki bki) {
		return bki.b().s() && this.A() == null && this.t && !this.el();
	}

	@Override
	protected void a(ane ane) {
		if (this.J.nextFloat() < 0.2F) {
			float float3 = this.J.nextFloat();
			bki bki4;
			if (float3 < 0.05F) {
				bki4 = new bki(bkk.oU);
			} else if (float3 < 0.2F) {
				bki4 = new bki(bkk.mg);
			} else if (float3 < 0.4F) {
				bki4 = this.J.nextBoolean() ? new bki(bkk.pz) : new bki(bkk.pA);
			} else if (float3 < 0.6F) {
				bki4 = new bki(bkk.kW);
			} else if (float3 < 0.8F) {
				bki4 = new bki(bkk.lS);
			} else {
				bki4 = new bki(bkk.kN);
			}

			this.a(aor.MAINHAND, bki4);
		}
	}

	public static apw.a eL() {
		return aoz.p().a(apx.d, 0.3F).a(apx.a, 10.0).a(apx.b, 32.0).a(apx.f, 2.0);
	}

	public ays a(aok aok) {
		ays ays3 = aoq.C.a(this.l);
		ays3.a(this.J.nextBoolean() ? this.eM() : ((ays)aok).eM());
		return ays3;
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		bre bre7 = bqc.v(this.cA());
		ays.v v8 = ays.v.a(bre7);
		boolean boolean9 = false;
		if (apo instanceof ays.i) {
			v8 = ((ays.i)apo).a;
			if (((ays.i)apo).a() >= 2) {
				boolean9 = true;
			}
		} else {
			apo = new ays.i(v8);
		}

		this.a(v8);
		if (boolean9) {
			this.c_(-24000);
		}

		if (bqc instanceof zd) {
			this.fa();
		}

		this.a(ane);
		return super.a(bqc, ane, apb, apo, le);
	}

	private void fa() {
		if (this.eM() == ays.v.RED) {
			this.bs.a(4, this.bD);
			this.bs.a(4, this.bE);
			this.bs.a(6, this.bF);
		} else {
			this.bs.a(4, this.bF);
			this.bs.a(6, this.bD);
			this.bs.a(6, this.bE);
		}
	}

	@Override
	protected void a(bec bec, bki bki) {
		if (this.k(bki)) {
			this.a(this.d(bki), 1.0F, 1.0F);
		}

		super.a(bec, bki);
	}

	@Override
	protected float b(apj apj, aon aon) {
		return this.x_() ? aon.b * 0.85F : 0.4F;
	}

	public ays.v eM() {
		return ays.v.a(this.S.a(bv));
	}

	private void a(ays.v v) {
		this.S.b(bv, v.c());
	}

	private List<UUID> fb() {
		List<UUID> list2 = Lists.<UUID>newArrayList();
		list2.add(this.S.a(bx).orElse(null));
		list2.add(this.S.a(by).orElse(null));
		return list2;
	}

	private void b(@Nullable UUID uUID) {
		if (this.S.a(bx).isPresent()) {
			this.S.b(by, Optional.ofNullable(uUID));
		} else {
			this.S.b(bx, Optional.ofNullable(uUID));
		}
	}

	@Override
	public void b(le le) {
		super.b(le);
		List<UUID> list3 = this.fb();
		lk lk4 = new lk();

		for (UUID uUID6 : list3) {
			if (uUID6 != null) {
				lk4.add(lq.a(uUID6));
			}
		}

		le.a("Trusted", lk4);
		le.a("Sleeping", this.el());
		le.a("Type", this.eM().a());
		le.a("Sitting", this.eN());
		le.a("Crouching", this.bv());
	}

	@Override
	public void a(le le) {
		super.a(le);
		lk lk3 = le.d("Trusted", 11);

		for (int integer4 = 0; integer4 < lk3.size(); integer4++) {
			this.b(lq.a(lk3.k(integer4)));
		}

		this.z(le.q("Sleeping"));
		this.a(ays.v.a(le.l("Type")));
		this.t(le.q("Sitting"));
		this.v(le.q("Crouching"));
		if (this.l instanceof zd) {
			this.fa();
		}
	}

	public boolean eN() {
		return this.t(1);
	}

	public void t(boolean boolean1) {
		this.d(1, boolean1);
	}

	public boolean eO() {
		return this.t(64);
	}

	private void x(boolean boolean1) {
		this.d(64, boolean1);
	}

	private boolean fc() {
		return this.t(128);
	}

	private void y(boolean boolean1) {
		this.d(128, boolean1);
	}

	@Override
	public boolean el() {
		return this.t(32);
	}

	private void z(boolean boolean1) {
		this.d(32, boolean1);
	}

	private void d(int integer, boolean boolean2) {
		if (boolean2) {
			this.S.b(bw, (byte)(this.S.a(bw) | integer));
		} else {
			this.S.b(bw, (byte)(this.S.a(bw) & ~integer));
		}
	}

	private boolean t(int integer) {
		return (this.S.a(bw) & integer) != 0;
	}

	@Override
	public boolean e(bki bki) {
		aor aor3 = aoz.j(bki);
		return !this.b(aor3).a() ? false : aor3 == aor.MAINHAND && super.e(bki);
	}

	@Override
	public boolean h(bki bki) {
		bke bke3 = bki.b();
		bki bki4 = this.b(aor.MAINHAND);
		return bki4.a() || this.bK > 0 && bke3.s() && !bki4.b().s();
	}

	private void m(bki bki) {
		if (!bki.a() && !this.l.v) {
			bbg bbg3 = new bbg(this.l, this.cC() + this.bd().b, this.cD() + 1.0, this.cG() + this.bd().d, bki);
			bbg3.a(40);
			bbg3.c(this.bR());
			this.a(acl.ey, 1.0F, 1.0F);
			this.l.c(bbg3);
		}
	}

	private void n(bki bki) {
		bbg bbg3 = new bbg(this.l, this.cC(), this.cD(), this.cG(), bki);
		this.l.c(bbg3);
	}

	@Override
	protected void b(bbg bbg) {
		bki bki3 = bbg.g();
		if (this.h(bki3)) {
			int integer4 = bki3.E();
			if (integer4 > 1) {
				this.n(bki3.a(integer4 - 1));
			}

			this.m(this.b(aor.MAINHAND));
			this.a(bbg);
			this.a(aor.MAINHAND, bki3.a(1));
			this.bt[aor.MAINHAND.b()] = 2.0F;
			this.a(bbg, bki3.E());
			bbg.aa();
			this.bK = 0;
		}
	}

	@Override
	public void j() {
		super.j();
		if (this.dR()) {
			boolean boolean2 = this.aA();
			if (boolean2 || this.A() != null || this.l.T()) {
				this.fd();
			}

			if (boolean2 || this.el()) {
				this.t(false);
			}

			if (this.eO() && this.l.t.nextFloat() < 0.2F) {
				fu fu3 = this.cA();
				cfj cfj4 = this.l.d_(fu3);
				this.l.c(2001, fu3, bvr.i(cfj4));
			}
		}

		this.bH = this.bG;
		if (this.eX()) {
			this.bG = this.bG + (1.0F - this.bG) * 0.4F;
		} else {
			this.bG = this.bG + (0.0F - this.bG) * 0.4F;
		}

		this.bJ = this.bI;
		if (this.bv()) {
			this.bI += 0.2F;
			if (this.bI > 3.0F) {
				this.bI = 3.0F;
			}
		} else {
			this.bI = 0.0F;
		}
	}

	@Override
	public boolean k(bki bki) {
		return bki.b() == bkk.rl;
	}

	@Override
	protected void a(bec bec, aoz aoz) {
		((ays)aoz).b(bec.bR());
	}

	public boolean eP() {
		return this.t(16);
	}

	public void u(boolean boolean1) {
		this.d(16, boolean1);
	}

	public boolean eW() {
		return this.bI == 3.0F;
	}

	public void v(boolean boolean1) {
		this.d(4, boolean1);
	}

	@Override
	public boolean bv() {
		return this.t(4);
	}

	public void w(boolean boolean1) {
		this.d(8, boolean1);
	}

	public boolean eX() {
		return this.t(8);
	}

	@Override
	public void i(@Nullable aoy aoy) {
		if (this.fc() && aoy == null) {
			this.y(false);
		}

		super.i(aoy);
	}

	@Override
	protected int e(float float1, float float2) {
		return aec.f((float1 - 5.0F) * float2);
	}

	private void fd() {
		this.z(false);
	}

	private void fe() {
		this.w(false);
		this.v(false);
		this.t(false);
		this.z(false);
		this.y(false);
		this.x(false);
	}

	private boolean ff() {
		return !this.el() && !this.eN() && !this.eO();
	}

	@Override
	public void F() {
		ack ack2 = this.I();
		if (ack2 == acl.ev) {
			this.a(ack2, 2.0F, this.dG());
		} else {
			super.F();
		}
	}

	@Nullable
	@Override
	protected ack I() {
		if (this.el()) {
			return acl.ew;
		} else {
			if (!this.l.J() && this.J.nextFloat() < 0.1F) {
				List<bec> list2 = this.l.a(bec.class, this.cb().c(16.0, 16.0, 16.0), aop.g);
				if (list2.isEmpty()) {
					return acl.ev;
				}
			}

			return acl.eq;
		}
	}

	@Nullable
	@Override
	protected ack e(anw anw) {
		return acl.eu;
	}

	@Nullable
	@Override
	protected ack dp() {
		return acl.es;
	}

	private boolean c(UUID uUID) {
		return this.fb().contains(uUID);
	}

	@Override
	protected void d(anw anw) {
		bki bki3 = this.b(aor.MAINHAND);
		if (!bki3.a()) {
			this.a(bki3);
			this.a(aor.MAINHAND, bki.b);
		}

		super.d(anw);
	}

	public static boolean a(ays ays, aoy aoy) {
		double double3 = aoy.cG() - ays.cG();
		double double5 = aoy.cC() - ays.cC();
		double double7 = double3 / double5;
		int integer9 = 6;

		for (int integer10 = 0; integer10 < 6; integer10++) {
			double double11 = double7 == 0.0 ? 0.0 : double3 * (double)((float)integer10 / 6.0F);
			double double13 = double7 == 0.0 ? double5 * (double)((float)integer10 / 6.0F) : double11 / double7;

			for (int integer15 = 1; integer15 < 4; integer15++) {
				if (!ays.l.d_(new fu(ays.cC() + double13, ays.cD() + (double)integer15, ays.cG() + double11)).c().e()) {
					return false;
				}
			}
		}

		return true;
	}

	class a extends awc<aoy> {
		@Nullable
		private aoy j;
		private aoy k;
		private int l;

		public a(Class<aoy> class2, boolean boolean3, boolean boolean4, Predicate<aoy> predicate) {
			super(ays.this, class2, 10, boolean3, boolean4, predicate);
		}

		@Override
		public boolean a() {
			if (this.b > 0 && this.e.cX().nextInt(this.b) != 0) {
				return false;
			} else {
				for (UUID uUID3 : ays.this.fb()) {
					if (uUID3 != null && ays.this.l instanceof zd) {
						aom aom4 = ((zd)ays.this.l).a(uUID3);
						if (aom4 instanceof aoy) {
							aoy aoy5 = (aoy)aom4;
							this.k = aoy5;
							this.j = aoy5.cY();
							int integer6 = aoy5.cZ();
							return integer6 != this.l && this.a(this.j, this.d);
						}
					}
				}

				return false;
			}
		}

		@Override
		public void c() {
			this.a(this.j);
			this.c = this.j;
			if (this.k != null) {
				this.l = this.k.cZ();
			}

			ays.this.a(acl.ep, 1.0F, 1.0F);
			ays.this.y(true);
			ays.this.fd();
			super.c();
		}
	}

	class b extends aug {
		int a;

		public b() {
			this.a(EnumSet.of(aug.a.LOOK, aug.a.JUMP, aug.a.MOVE));
		}

		@Override
		public boolean a() {
			return ays.this.eO();
		}

		@Override
		public boolean b() {
			return this.a() && this.a > 0;
		}

		@Override
		public void c() {
			this.a = 40;
		}

		@Override
		public void d() {
			ays.this.x(false);
		}

		@Override
		public void e() {
			this.a--;
		}
	}

	public class c implements Predicate<aoy> {
		public boolean test(aoy aoy) {
			if (aoy instanceof ays) {
				return false;
			} else if (aoy instanceof ayn || aoy instanceof azb || aoy instanceof bcb) {
				return true;
			} else if (aoy instanceof apq) {
				return !((apq)aoy).eL();
			} else if (!(aoy instanceof bec) || !aoy.a_() && !((bec)aoy).b_()) {
				return ays.this.c(aoy.bR()) ? false : !aoy.el() && !aoy.bt();
			} else {
				return false;
			}
		}
	}

	abstract class d extends aug {
		private final axs b = new axs().a(12.0).c().a(ays.this.new c());

		private d() {
		}

		protected boolean g() {
			fu fu2 = new fu(ays.this.cC(), ays.this.cb().e, ays.this.cG());
			return !ays.this.l.f(fu2) && ays.this.f(fu2) >= 0.0F;
		}

		protected boolean h() {
			return !ays.this.l.a(aoy.class, this.b, ays.this, ays.this.cb().c(12.0, 6.0, 12.0)).isEmpty();
		}
	}

	class e extends att {
		public e(double double2) {
			super(ays.this, double2);
		}

		@Override
		public void c() {
			((ays)this.a).fe();
			((ays)this.c).fe();
			super.c();
		}

		@Override
		protected void g() {
			ays ays2 = (ays)this.a.a((aok)this.c);
			if (ays2 != null) {
				ze ze3 = this.a.eS();
				ze ze4 = this.c.eS();
				ze ze5 = ze3;
				if (ze3 != null) {
					ays2.b(ze3.bR());
				} else {
					ze5 = ze4;
				}

				if (ze4 != null && ze3 != ze4) {
					ays2.b(ze4.bR());
				}

				if (ze5 != null) {
					ze5.a(acu.O);
					aa.o.a(ze5, this.a, this.c, ays2);
				}

				this.a.c_(6000);
				this.c.c_(6000);
				this.a.eU();
				this.c.eU();
				ays2.c_(-24000);
				ays2.b(this.a.cC(), this.a.cD(), this.a.cG(), 0.0F, 0.0F);
				this.b.c(ays2);
				this.b.a(this.a, (byte)18);
				if (this.b.S().b(bpx.e)) {
					this.b.c(new aos(this.b, this.a.cC(), this.a.cD(), this.a.cG(), this.a.cX().nextInt(7) + 1));
				}
			}
		}
	}

	public class f extends auu {
		protected int g;

		public f(double double2, int integer3, int integer4) {
			super(ays.this, double2, integer3, integer4);
		}

		@Override
		public double h() {
			return 2.0;
		}

		@Override
		public boolean j() {
			return this.d % 100 == 0;
		}

		@Override
		protected boolean a(bqd bqd, fu fu) {
			cfj cfj4 = bqd.d_(fu);
			return cfj4.a(bvs.mg) && (Integer)cfj4.c(cbw.a) >= 2;
		}

		@Override
		public void e() {
			if (this.k()) {
				if (this.g >= 40) {
					this.m();
				} else {
					this.g++;
				}
			} else if (!this.k() && ays.this.J.nextFloat() < 0.05F) {
				ays.this.a(acl.ex, 1.0F, 1.0F);
			}

			super.e();
		}

		protected void m() {
			if (ays.this.l.S().b(bpx.b)) {
				cfj cfj2 = ays.this.l.d_(this.e);
				if (cfj2.a(bvs.mg)) {
					int integer3 = (Integer)cfj2.c(cbw.a);
					cfj2.a(cbw.a, Integer.valueOf(1));
					int integer4 = 1 + ays.this.l.t.nextInt(2) + (integer3 == 3 ? 1 : 0);
					bki bki5 = ays.this.b(aor.MAINHAND);
					if (bki5.a()) {
						ays.this.a(aor.MAINHAND, new bki(bkk.rl));
						integer4--;
					}

					if (integer4 > 0) {
						bvr.a(ays.this.l, this.e, new bki(bkk.rl, integer4));
					}

					ays.this.a(acl.oS, 1.0F, 1.0F);
					ays.this.l.a(this.e, cfj2.a(cbw.a, Integer.valueOf(1)), 2);
				}
			}
		}

		@Override
		public boolean a() {
			return !ays.this.el() && super.a();
		}

		@Override
		public void c() {
			this.g = 0;
			ays.this.t(false);
			super.c();
		}
	}

	class g extends aua {
		public g() {
			super(ays.this);
		}

		@Override
		public void c() {
			super.c();
			ays.this.fe();
		}

		@Override
		public boolean a() {
			return ays.this.aA() && ays.this.b(acz.a) > 0.25 || ays.this.aN();
		}
	}

	class h extends auf {
		private final ays b;

		public h(ays ays2, double double3) {
			super(ays2, double3);
			this.b = ays2;
		}

		@Override
		public boolean a() {
			return !this.b.fc() && super.a();
		}

		@Override
		public boolean b() {
			return !this.b.fc() && super.b();
		}

		@Override
		public void c() {
			this.b.fe();
			super.c();
		}
	}

	public static class i extends aok.a {
		public final ays.v a;

		public i(ays.v v) {
			this.a(false);
			this.a = v;
		}
	}

	class j extends auo {
		public j(aoz aoz, Class<? extends aoy> class3, float float4) {
			super(aoz, class3, float4);
		}

		@Override
		public boolean a() {
			return super.a() && !ays.this.eO() && !ays.this.eX();
		}

		@Override
		public boolean b() {
			return super.b() && !ays.this.eO() && !ays.this.eX();
		}
	}

	public class k extends atl {
		public k() {
			super(ays.this);
		}

		@Override
		public void a() {
			if (!ays.this.el()) {
				super.a();
			}
		}

		@Override
		protected boolean b() {
			return !ays.this.eP() && !ays.this.bv() && !ays.this.eX() & !ays.this.eO();
		}
	}

	class l extends auq {
		public l(double double2, boolean boolean3) {
			super(ays.this, double2, boolean3);
		}

		@Override
		protected void a(aoy aoy, double double2) {
			double double5 = this.a(aoy);
			if (double2 <= double5 && this.h()) {
				this.g();
				this.a.B(aoy);
				ays.this.a(acl.er, 1.0F, 1.0F);
			}
		}

		@Override
		public void c() {
			ays.this.w(false);
			super.c();
		}

		@Override
		public boolean a() {
			return !ays.this.eN() && !ays.this.el() && !ays.this.bv() && !ays.this.eO() && super.a();
		}
	}

	class m extends atm {
		public m() {
			super(ays.this);
		}

		@Override
		public void a() {
			if (ays.this.ff()) {
				super.a();
			}
		}
	}

	class n extends avb {
		public n(double double2) {
			super(ays.this, double2);
		}

		@Override
		public boolean a() {
			return !ays.this.fc() && super.a();
		}
	}

	public class o extends auk {
		@Override
		public boolean a() {
			if (!ays.this.eW()) {
				return false;
			} else {
				aoy aoy2 = ays.this.A();
				if (aoy2 != null && aoy2.aU()) {
					if (aoy2.bZ() != aoy2.bY()) {
						return false;
					} else {
						boolean boolean3 = ays.a(ays.this, aoy2);
						if (!boolean3) {
							ays.this.x().a(aoy2, 0);
							ays.this.v(false);
							ays.this.w(false);
						}

						return boolean3;
					}
				} else {
					return false;
				}
			}
		}

		@Override
		public boolean b() {
			aoy aoy2 = ays.this.A();
			if (aoy2 != null && aoy2.aU()) {
				double double3 = ays.this.cB().c;
				return (!(double3 * double3 < 0.05F) || !(Math.abs(ays.this.q) < 15.0F) || !ays.this.t) && !ays.this.eO();
			} else {
				return false;
			}
		}

		@Override
		public boolean D_() {
			return false;
		}

		@Override
		public void c() {
			ays.this.o(true);
			ays.this.u(true);
			ays.this.w(false);
			aoy aoy2 = ays.this.A();
			ays.this.t().a(aoy2, 60.0F, 30.0F);
			dem dem3 = new dem(aoy2.cC() - ays.this.cC(), aoy2.cD() - ays.this.cD(), aoy2.cG() - ays.this.cG()).d();
			ays.this.e(ays.this.cB().b(dem3.b * 0.8, 0.9, dem3.d * 0.8));
			ays.this.x().o();
		}

		@Override
		public void d() {
			ays.this.v(false);
			ays.this.bI = 0.0F;
			ays.this.bJ = 0.0F;
			ays.this.w(false);
			ays.this.u(false);
		}

		@Override
		public void e() {
			aoy aoy2 = ays.this.A();
			if (aoy2 != null) {
				ays.this.t().a(aoy2, 60.0F, 30.0F);
			}

			if (!ays.this.eO()) {
				dem dem3 = ays.this.cB();
				if (dem3.c * dem3.c < 0.03F && ays.this.q != 0.0F) {
					ays.this.q = aec.j(ays.this.q, 0.0F, 0.2F);
				} else {
					double double4 = Math.sqrt(aom.b(dem3));
					double double6 = Math.signum(-dem3.c) * Math.acos(double4 / dem3.f()) * 180.0F / (float)Math.PI;
					ays.this.q = (float)double6;
				}
			}

			if (aoy2 != null && ays.this.g(aoy2) <= 2.0F) {
				ays.this.B(aoy2);
			} else if (ays.this.q > 0.0F && ays.this.t && (float)ays.this.cB().c != 0.0F && ays.this.l.d_(ays.this.cA()).a(bvs.cC)) {
				ays.this.q = 60.0F;
				ays.this.i(null);
				ays.this.x(true);
			}
		}
	}

	class p extends aug {
		public p() {
			this.a(EnumSet.of(aug.a.MOVE));
		}

		@Override
		public boolean a() {
			if (!ays.this.b(aor.MAINHAND).a()) {
				return false;
			} else if (ays.this.A() != null || ays.this.cY() != null) {
				return false;
			} else if (!ays.this.ff()) {
				return false;
			} else if (ays.this.cX().nextInt(10) != 0) {
				return false;
			} else {
				List<bbg> list2 = ays.this.l.a(bbg.class, ays.this.cb().c(8.0, 8.0, 8.0), ays.bz);
				return !list2.isEmpty() && ays.this.b(aor.MAINHAND).a();
			}
		}

		@Override
		public void e() {
			List<bbg> list2 = ays.this.l.a(bbg.class, ays.this.cb().c(8.0, 8.0, 8.0), ays.bz);
			bki bki3 = ays.this.b(aor.MAINHAND);
			if (bki3.a() && !list2.isEmpty()) {
				ays.this.x().a((aom)list2.get(0), 1.2F);
			}
		}

		@Override
		public void c() {
			List<bbg> list2 = ays.this.l.a(bbg.class, ays.this.cb().c(8.0, 8.0, 8.0), ays.bz);
			if (!list2.isEmpty()) {
				ays.this.x().a((aom)list2.get(0), 1.2F);
			}
		}
	}

	class q extends avo {
		public q(int integer2, int integer3) {
			super(ays.this, integer3);
		}

		@Override
		public void c() {
			ays.this.fe();
			super.c();
		}

		@Override
		public boolean a() {
			return super.a() && this.g();
		}

		@Override
		public boolean b() {
			return super.b() && this.g();
		}

		private boolean g() {
			return !ays.this.el() && !ays.this.eN() && !ays.this.fc() && ays.this.A() == null;
		}
	}

	class r extends ays.d {
		private double c;
		private double d;
		private int e;
		private int f;

		public r() {
			this.a(EnumSet.of(aug.a.MOVE, aug.a.LOOK));
		}

		@Override
		public boolean a() {
			return ays.this.cY() == null
				&& ays.this.cX().nextFloat() < 0.02F
				&& !ays.this.el()
				&& ays.this.A() == null
				&& ays.this.x().m()
				&& !this.h()
				&& !ays.this.eP()
				&& !ays.this.bv();
		}

		@Override
		public boolean b() {
			return this.f > 0;
		}

		@Override
		public void c() {
			this.j();
			this.f = 2 + ays.this.cX().nextInt(3);
			ays.this.t(true);
			ays.this.x().o();
		}

		@Override
		public void d() {
			ays.this.t(false);
		}

		@Override
		public void e() {
			this.e--;
			if (this.e <= 0) {
				this.f--;
				this.j();
			}

			ays.this.t().a(ays.this.cC() + this.c, ays.this.cF(), ays.this.cG() + this.d, (float)ays.this.ep(), (float)ays.this.eo());
		}

		private void j() {
			double double2 = (Math.PI * 2) * ays.this.cX().nextDouble();
			this.c = Math.cos(double2);
			this.d = Math.sin(double2);
			this.e = 80 + ays.this.cX().nextInt(20);
		}
	}

	class s extends atz {
		private int c = 100;

		public s(double double2) {
			super(ays.this, double2);
		}

		@Override
		public boolean a() {
			if (ays.this.el() || this.a.A() != null) {
				return false;
			} else if (ays.this.l.T()) {
				return true;
			} else if (this.c > 0) {
				this.c--;
				return false;
			} else {
				this.c = 100;
				fu fu2 = this.a.cA();
				return ays.this.l.J() && ays.this.l.f(fu2) && !((zd)ays.this.l).b_(fu2) && this.g();
			}
		}

		@Override
		public void c() {
			ays.this.fe();
			super.c();
		}
	}

	class t extends ays.d {
		private int c = ays.this.J.nextInt(140);

		public t() {
			this.a(EnumSet.of(aug.a.MOVE, aug.a.LOOK, aug.a.JUMP));
		}

		@Override
		public boolean a() {
			return ays.this.aY == 0.0F && ays.this.aZ == 0.0F && ays.this.ba == 0.0F ? this.j() || ays.this.el() : false;
		}

		@Override
		public boolean b() {
			return this.j();
		}

		private boolean j() {
			if (this.c > 0) {
				this.c--;
				return false;
			} else {
				return ays.this.l.J() && this.g() && !this.h();
			}
		}

		@Override
		public void d() {
			this.c = ays.this.J.nextInt(140);
			ays.this.fe();
		}

		@Override
		public void c() {
			ays.this.t(false);
			ays.this.v(false);
			ays.this.w(false);
			ays.this.o(false);
			ays.this.z(true);
			ays.this.x().o();
			ays.this.u().a(ays.this.cC(), ays.this.cD(), ays.this.cG(), 0.0);
		}
	}

	class u extends aug {
		public u() {
			this.a(EnumSet.of(aug.a.MOVE, aug.a.LOOK));
		}

		@Override
		public boolean a() {
			if (ays.this.el()) {
				return false;
			} else {
				aoy aoy2 = ays.this.A();
				return aoy2 != null && aoy2.aU() && ays.bB.test(aoy2) && ays.this.h(aoy2) > 36.0 && !ays.this.bv() && !ays.this.eX() && !ays.this.aX;
			}
		}

		@Override
		public void c() {
			ays.this.t(false);
			ays.this.x(false);
		}

		@Override
		public void d() {
			aoy aoy2 = ays.this.A();
			if (aoy2 != null && ays.a(ays.this, aoy2)) {
				ays.this.w(true);
				ays.this.v(true);
				ays.this.x().o();
				ays.this.t().a(aoy2, (float)ays.this.ep(), (float)ays.this.eo());
			} else {
				ays.this.w(false);
				ays.this.v(false);
			}
		}

		@Override
		public void e() {
			aoy aoy2 = ays.this.A();
			ays.this.t().a(aoy2, (float)ays.this.ep(), (float)ays.this.eo());
			if (ays.this.h(aoy2) <= 36.0) {
				ays.this.w(true);
				ays.this.v(true);
				ays.this.x().o();
			} else {
				ays.this.x().a(aoy2, 1.5);
			}
		}
	}

	public static enum v {
		RED(0, "red", brk.g, brk.u, brk.af, brk.H, brk.ao, brk.I, brk.ap),
		SNOW(1, "snow", brk.F, brk.G, brk.an);

		private static final ays.v[] c = (ays.v[])Arrays.stream(values()).sorted(Comparator.comparingInt(ays.v::c)).toArray(ays.v[]::new);
		private static final Map<String, ays.v> d = (Map<String, ays.v>)Arrays.stream(values()).collect(Collectors.toMap(ays.v::a, v -> v));
		private final int e;
		private final String f;
		private final List<bre> g;

		private v(int integer3, String string4, bre... arr) {
			this.e = integer3;
			this.f = string4;
			this.g = Arrays.asList(arr);
		}

		public String a() {
			return this.f;
		}

		public List<bre> b() {
			return this.g;
		}

		public int c() {
			return this.e;
		}

		public static ays.v a(String string) {
			return (ays.v)d.getOrDefault(string, RED);
		}

		public static ays.v a(int integer) {
			if (integer < 0 || integer > c.length) {
				integer = 0;
			}

			return c[integer];
		}

		public static ays.v a(bre bre) {
			return SNOW.b().contains(bre) ? SNOW : RED;
		}
	}
}
