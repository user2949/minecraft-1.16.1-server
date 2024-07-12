import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class bay extends aoy {
	private static final gn bq = new gn(0.0F, 0.0F, 0.0F);
	private static final gn br = new gn(0.0F, 0.0F, 0.0F);
	private static final gn bs = new gn(-10.0F, 0.0F, -10.0F);
	private static final gn bt = new gn(-15.0F, 0.0F, 10.0F);
	private static final gn bu = new gn(-1.0F, 0.0F, -1.0F);
	private static final gn bv = new gn(1.0F, 0.0F, 1.0F);
	public static final tq<Byte> b = tt.a(bay.class, ts.a);
	public static final tq<gn> c = tt.a(bay.class, ts.k);
	public static final tq<gn> d = tt.a(bay.class, ts.k);
	public static final tq<gn> e = tt.a(bay.class, ts.k);
	public static final tq<gn> f = tt.a(bay.class, ts.k);
	public static final tq<gn> g = tt.a(bay.class, ts.k);
	public static final tq<gn> bo = tt.a(bay.class, ts.k);
	private static final Predicate<aom> bw = aom -> aom instanceof bfr && ((bfr)aom).o() == bfr.a.RIDEABLE;
	private final gi<bki> bx = gi.a(2, bki.b);
	private final gi<bki> by = gi.a(4, bki.b);
	private boolean bz;
	public long bp;
	private int bA;
	private gn bB = bq;
	private gn bC = br;
	private gn bD = bs;
	private gn bE = bt;
	private gn bF = bu;
	private gn bG = bv;

	public bay(aoq<? extends bay> aoq, bqb bqb) {
		super(aoq, bqb);
		this.G = 0.0F;
	}

	public bay(bqb bqb, double double2, double double3, double double4) {
		this(aoq.b, bqb);
		this.d(double2, double3, double4);
	}

	@Override
	public void y_() {
		double double2 = this.cC();
		double double4 = this.cD();
		double double6 = this.cG();
		super.y_();
		this.d(double2, double4, double6);
	}

	private boolean A() {
		return !this.q() && !this.aw();
	}

	@Override
	public boolean dR() {
		return super.dR() && this.A();
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(b, (byte)0);
		this.S.a(c, bq);
		this.S.a(d, br);
		this.S.a(e, bs);
		this.S.a(f, bt);
		this.S.a(g, bu);
		this.S.a(bo, bv);
	}

	@Override
	public Iterable<bki> bj() {
		return this.bx;
	}

	@Override
	public Iterable<bki> bk() {
		return this.by;
	}

	@Override
	public bki b(aor aor) {
		switch (aor.a()) {
			case HAND:
				return this.bx.get(aor.b());
			case ARMOR:
				return this.by.get(aor.b());
			default:
				return bki.b;
		}
	}

	@Override
	public void a(aor aor, bki bki) {
		switch (aor.a()) {
			case HAND:
				this.b(bki);
				this.bx.set(aor.b(), bki);
				break;
			case ARMOR:
				this.b(bki);
				this.by.set(aor.b(), bki);
		}
	}

	@Override
	public boolean a_(int integer, bki bki) {
		aor aor4;
		if (integer == 98) {
			aor4 = aor.MAINHAND;
		} else if (integer == 99) {
			aor4 = aor.OFFHAND;
		} else if (integer == 100 + aor.HEAD.b()) {
			aor4 = aor.HEAD;
		} else if (integer == 100 + aor.CHEST.b()) {
			aor4 = aor.CHEST;
		} else if (integer == 100 + aor.LEGS.b()) {
			aor4 = aor.LEGS;
		} else {
			if (integer != 100 + aor.FEET.b()) {
				return false;
			}

			aor4 = aor.FEET;
		}

		if (!bki.a() && !aoz.c(aor4, bki) && aor4 != aor.HEAD) {
			return false;
		} else {
			this.a(aor4, bki);
			return true;
		}
	}

	@Override
	public boolean e(bki bki) {
		aor aor3 = aoz.j(bki);
		return this.b(aor3).a() && !this.d(aor3);
	}

	@Override
	public void b(le le) {
		super.b(le);
		lk lk3 = new lk();

		for (bki bki5 : this.by) {
			le le6 = new le();
			if (!bki5.a()) {
				bki5.b(le6);
			}

			lk3.add(le6);
		}

		le.a("ArmorItems", lk3);
		lk lk4 = new lk();

		for (bki bki6 : this.bx) {
			le le7 = new le();
			if (!bki6.a()) {
				bki6.b(le7);
			}

			lk4.add(le7);
		}

		le.a("HandItems", lk4);
		le.a("Invisible", this.bB());
		le.a("Small", this.m());
		le.a("ShowArms", this.o());
		le.b("DisabledSlots", this.bA);
		le.a("NoBasePlate", this.p());
		if (this.q()) {
			le.a("Marker", this.q());
		}

		le.a("Pose", this.B());
	}

	@Override
	public void a(le le) {
		super.a(le);
		if (le.c("ArmorItems", 9)) {
			lk lk3 = le.d("ArmorItems", 10);

			for (int integer4 = 0; integer4 < this.by.size(); integer4++) {
				this.by.set(integer4, bki.a(lk3.a(integer4)));
			}
		}

		if (le.c("HandItems", 9)) {
			lk lk3 = le.d("HandItems", 10);

			for (int integer4 = 0; integer4 < this.bx.size(); integer4++) {
				this.bx.set(integer4, bki.a(lk3.a(integer4)));
			}
		}

		this.j(le.q("Invisible"));
		this.a(le.q("Small"));
		this.p(le.q("ShowArms"));
		this.bA = le.h("DisabledSlots");
		this.q(le.q("NoBasePlate"));
		this.r(le.q("Marker"));
		this.H = !this.A();
		le le3 = le.p("Pose");
		this.g(le3);
	}

	private void g(le le) {
		lk lk3 = le.d("Head", 5);
		this.a(lk3.isEmpty() ? bq : new gn(lk3));
		lk lk4 = le.d("Body", 5);
		this.b(lk4.isEmpty() ? br : new gn(lk4));
		lk lk5 = le.d("LeftArm", 5);
		this.c(lk5.isEmpty() ? bs : new gn(lk5));
		lk lk6 = le.d("RightArm", 5);
		this.d(lk6.isEmpty() ? bt : new gn(lk6));
		lk lk7 = le.d("LeftLeg", 5);
		this.e(lk7.isEmpty() ? bu : new gn(lk7));
		lk lk8 = le.d("RightLeg", 5);
		this.f(lk8.isEmpty() ? bv : new gn(lk8));
	}

	private le B() {
		le le2 = new le();
		if (!bq.equals(this.bB)) {
			le2.a("Head", this.bB.a());
		}

		if (!br.equals(this.bC)) {
			le2.a("Body", this.bC.a());
		}

		if (!bs.equals(this.bD)) {
			le2.a("LeftArm", this.bD.a());
		}

		if (!bt.equals(this.bE)) {
			le2.a("RightArm", this.bE.a());
		}

		if (!bu.equals(this.bF)) {
			le2.a("LeftLeg", this.bF.a());
		}

		if (!bv.equals(this.bG)) {
			le2.a("RightLeg", this.bG.a());
		}

		return le2;
	}

	@Override
	public boolean aR() {
		return false;
	}

	@Override
	protected void C(aom aom) {
	}

	@Override
	protected void dP() {
		List<aom> list2 = this.l.a(this, this.cb(), bw);

		for (int integer3 = 0; integer3 < list2.size(); integer3++) {
			aom aom4 = (aom)list2.get(integer3);
			if (this.h(aom4) <= 0.2) {
				aom4.i(this);
			}
		}
	}

	@Override
	public ang a(bec bec, dem dem, anf anf) {
		bki bki5 = bec.b(anf);
		if (this.q() || bki5.b() == bkk.pH) {
			return ang.PASS;
		} else if (bec.a_()) {
			return ang.SUCCESS;
		} else if (bec.l.v) {
			return ang.CONSUME;
		} else {
			aor aor6 = aoz.j(bki5);
			if (bki5.a()) {
				aor aor7 = this.g(dem);
				aor aor8 = this.d(aor7) ? aor6 : aor7;
				if (this.a(aor8) && this.a(bec, aor8, bki5, anf)) {
					return ang.SUCCESS;
				}
			} else {
				if (this.d(aor6)) {
					return ang.FAIL;
				}

				if (aor6.a() == aor.a.HAND && !this.o()) {
					return ang.FAIL;
				}

				if (this.a(bec, aor6, bki5, anf)) {
					return ang.SUCCESS;
				}
			}

			return ang.PASS;
		}
	}

	private aor g(dem dem) {
		aor aor3 = aor.MAINHAND;
		boolean boolean4 = this.m();
		double double5 = boolean4 ? dem.c * 2.0 : dem.c;
		aor aor7 = aor.FEET;
		if (double5 >= 0.1 && double5 < 0.1 + (boolean4 ? 0.8 : 0.45) && this.a(aor7)) {
			aor3 = aor.FEET;
		} else if (double5 >= 0.9 + (boolean4 ? 0.3 : 0.0) && double5 < 0.9 + (boolean4 ? 1.0 : 0.7) && this.a(aor.CHEST)) {
			aor3 = aor.CHEST;
		} else if (double5 >= 0.4 && double5 < 0.4 + (boolean4 ? 1.0 : 0.8) && this.a(aor.LEGS)) {
			aor3 = aor.LEGS;
		} else if (double5 >= 1.6 && this.a(aor.HEAD)) {
			aor3 = aor.HEAD;
		} else if (!this.a(aor.MAINHAND) && this.a(aor.OFFHAND)) {
			aor3 = aor.OFFHAND;
		}

		return aor3;
	}

	private boolean d(aor aor) {
		return (this.bA & 1 << aor.c()) != 0 || aor.a() == aor.a.HAND && !this.o();
	}

	private boolean a(bec bec, aor aor, bki bki, anf anf) {
		bki bki6 = this.b(aor);
		if (!bki6.a() && (this.bA & 1 << aor.c() + 8) != 0) {
			return false;
		} else if (bki6.a() && (this.bA & 1 << aor.c() + 16) != 0) {
			return false;
		} else if (bec.bJ.d && bki6.a() && !bki.a()) {
			bki bki7 = bki.i();
			bki7.e(1);
			this.a(aor, bki7);
			return true;
		} else if (bki.a() || bki.E() <= 1) {
			this.a(aor, bki);
			bec.a(anf, bki6);
			return true;
		} else if (!bki6.a()) {
			return false;
		} else {
			bki bki7 = bki.i();
			bki7.e(1);
			this.a(aor, bki7);
			bki.g(1);
			return true;
		}
	}

	@Override
	public boolean a(anw anw, float float2) {
		if (this.l.v || this.y) {
			return false;
		} else if (anw.m.equals(anw)) {
			this.aa();
			return false;
		} else if (this.b(anw) || this.bz || this.q()) {
			return false;
		} else if (anw.d()) {
			this.g(anw);
			this.aa();
			return false;
		} else if (anw.a.equals(anw)) {
			if (this.bm()) {
				this.f(anw, 0.15F);
			} else {
				this.f(5);
			}

			return false;
		} else if (anw.c.equals(anw) && this.dj() > 0.5F) {
			this.f(anw, 4.0F);
			return false;
		} else {
			boolean boolean4 = anw.j() instanceof beg;
			boolean boolean5 = boolean4 && ((beg)anw.j()).r() > 0;
			boolean boolean6 = "player".equals(anw.q());
			if (!boolean6 && !boolean4) {
				return false;
			} else if (anw.k() instanceof bec && !((bec)anw.k()).bJ.e) {
				return false;
			} else if (anw.v()) {
				this.F();
				this.D();
				this.aa();
				return boolean5;
			} else {
				long long7 = this.l.Q();
				if (long7 - this.bp > 5L && !boolean4) {
					this.l.a(this, (byte)32);
					this.bp = long7;
				} else {
					this.f(anw);
					this.D();
					this.aa();
				}

				return true;
			}
		}
	}

	private void D() {
		if (this.l instanceof zd) {
			((zd)this.l)
				.a(
					new hc(hh.d, bvs.n.n()),
					this.cC(),
					this.e(0.6666666666666666),
					this.cG(),
					10,
					(double)(this.cx() / 4.0F),
					(double)(this.cy() / 4.0F),
					(double)(this.cx() / 4.0F),
					0.05
				);
		}
	}

	private void f(anw anw, float float2) {
		float float4 = this.dj();
		float4 -= float2;
		if (float4 <= 0.5F) {
			this.g(anw);
			this.aa();
		} else {
			this.c(float4);
		}
	}

	private void f(anw anw) {
		bvr.a(this.l, this.cA(), new bki(bkk.pB));
		this.g(anw);
	}

	private void g(anw anw) {
		this.F();
		this.d(anw);

		for (int integer3 = 0; integer3 < this.bx.size(); integer3++) {
			bki bki4 = this.bx.get(integer3);
			if (!bki4.a()) {
				bvr.a(this.l, this.cA().b(), bki4);
				this.bx.set(integer3, bki.b);
			}
		}

		for (int integer3x = 0; integer3x < this.by.size(); integer3x++) {
			bki bki4 = this.by.get(integer3x);
			if (!bki4.a()) {
				bvr.a(this.l, this.cA().b(), bki4);
				this.by.set(integer3x, bki.b);
			}
		}
	}

	private void F() {
		this.l.a(null, this.cC(), this.cD(), this.cG(), acl.S, this.ct(), 1.0F, 1.0F);
	}

	@Override
	protected float f(float float1, float float2) {
		this.aI = this.r;
		this.aH = this.p;
		return 0.0F;
	}

	@Override
	protected float b(apj apj, aon aon) {
		return aon.b * (this.x_() ? 0.5F : 0.9F);
	}

	@Override
	public double aX() {
		return this.q() ? 0.0 : 0.1F;
	}

	@Override
	public void f(dem dem) {
		if (this.A()) {
			super.f(dem);
		}
	}

	@Override
	public void l(float float1) {
		this.aI = this.r = float1;
		this.aK = this.aJ = float1;
	}

	@Override
	public void k(float float1) {
		this.aI = this.r = float1;
		this.aK = this.aJ = float1;
	}

	@Override
	public void j() {
		super.j();
		gn gn2 = this.S.a(c);
		if (!this.bB.equals(gn2)) {
			this.a(gn2);
		}

		gn gn3 = this.S.a(d);
		if (!this.bC.equals(gn3)) {
			this.b(gn3);
		}

		gn gn4 = this.S.a(e);
		if (!this.bD.equals(gn4)) {
			this.c(gn4);
		}

		gn gn5 = this.S.a(f);
		if (!this.bE.equals(gn5)) {
			this.d(gn5);
		}

		gn gn6 = this.S.a(g);
		if (!this.bF.equals(gn6)) {
			this.e(gn6);
		}

		gn gn7 = this.S.a(bo);
		if (!this.bG.equals(gn7)) {
			this.f(gn7);
		}
	}

	@Override
	protected void C() {
		this.j(this.bz);
	}

	@Override
	public void j(boolean boolean1) {
		this.bz = boolean1;
		super.j(boolean1);
	}

	@Override
	public boolean x_() {
		return this.m();
	}

	@Override
	public void X() {
		this.aa();
	}

	@Override
	public boolean ch() {
		return this.bB();
	}

	@Override
	public cxf z_() {
		return this.q() ? cxf.IGNORE : super.z_();
	}

	private void a(boolean boolean1) {
		this.S.b(b, this.a(this.S.a(b), 1, boolean1));
	}

	public boolean m() {
		return (this.S.a(b) & 1) != 0;
	}

	private void p(boolean boolean1) {
		this.S.b(b, this.a(this.S.a(b), 4, boolean1));
	}

	public boolean o() {
		return (this.S.a(b) & 4) != 0;
	}

	private void q(boolean boolean1) {
		this.S.b(b, this.a(this.S.a(b), 8, boolean1));
	}

	public boolean p() {
		return (this.S.a(b) & 8) != 0;
	}

	private void r(boolean boolean1) {
		this.S.b(b, this.a(this.S.a(b), 16, boolean1));
	}

	public boolean q() {
		return (this.S.a(b) & 16) != 0;
	}

	private byte a(byte byte1, int integer, boolean boolean3) {
		if (boolean3) {
			byte1 = (byte)(byte1 | integer);
		} else {
			byte1 = (byte)(byte1 & ~integer);
		}

		return byte1;
	}

	public void a(gn gn) {
		this.bB = gn;
		this.S.b(c, gn);
	}

	public void b(gn gn) {
		this.bC = gn;
		this.S.b(d, gn);
	}

	public void c(gn gn) {
		this.bD = gn;
		this.S.b(e, gn);
	}

	public void d(gn gn) {
		this.bE = gn;
		this.S.b(f, gn);
	}

	public void e(gn gn) {
		this.bF = gn;
		this.S.b(g, gn);
	}

	public void f(gn gn) {
		this.bG = gn;
		this.S.b(bo, gn);
	}

	public gn r() {
		return this.bB;
	}

	public gn t() {
		return this.bC;
	}

	@Override
	public boolean aQ() {
		return super.aQ() && !this.q();
	}

	@Override
	public boolean t(aom aom) {
		return aom instanceof bec && !this.l.a((bec)aom, this.cA());
	}

	@Override
	public aou dU() {
		return aou.RIGHT;
	}

	@Override
	protected ack o(int integer) {
		return acl.T;
	}

	@Nullable
	@Override
	protected ack e(anw anw) {
		return acl.U;
	}

	@Nullable
	@Override
	protected ack dp() {
		return acl.S;
	}

	@Override
	public void a(aox aox) {
	}

	@Override
	public boolean eg() {
		return false;
	}

	@Override
	public void a(tq<?> tq) {
		if (b.equals(tq)) {
			this.y_();
			this.i = !this.q();
		}

		super.a(tq);
	}

	@Override
	public boolean eh() {
		return false;
	}

	@Override
	public aon a(apj apj) {
		float float3 = this.q() ? 0.0F : (this.x_() ? 0.5F : 1.0F);
		return this.U().l().a(float3);
	}
}
