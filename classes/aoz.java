import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;

public abstract class aoz extends aoy {
	private static final tq<Byte> b = tt.a(aoz.class, ts.a);
	public int e;
	protected int f;
	protected atl g;
	protected atm bo;
	protected atk bp;
	private final atg c;
	protected awv bq;
	protected final auh br;
	protected final auh bs;
	private aoy d;
	private final axm bv;
	private final gi<bki> bw = gi.a(2, bki.b);
	protected final float[] bt = new float[2];
	private final gi<bki> bx = gi.a(4, bki.b);
	protected final float[] bu = new float[4];
	private boolean by;
	private boolean bz;
	private final Map<czb, Float> bA = Maps.newEnumMap(czb.class);
	private uh bB;
	private long bC;
	@Nullable
	private aom bD;
	private int bE;
	@Nullable
	private le bF;
	private fu bG = fu.b;
	private float bH = -1.0F;

	protected aoz(aoq<? extends aoz> aoq, bqb bqb) {
		super(aoq, bqb);
		this.br = new auh(bqb.Y());
		this.bs = new auh(bqb.Y());
		this.g = new atl(this);
		this.bo = new atm(this);
		this.bp = new atk(this);
		this.c = this.r();
		this.bq = this.b(bqb);
		this.bv = new axm(this);
		Arrays.fill(this.bu, 0.085F);
		Arrays.fill(this.bt, 0.085F);
		if (bqb != null && !bqb.v) {
			this.o();
		}
	}

	protected void o() {
	}

	public static apw.a p() {
		return aoy.cK().a(apx.b, 16.0).a(apx.g);
	}

	protected awv b(bqb bqb) {
		return new awu(this, bqb);
	}

	protected boolean q() {
		return false;
	}

	public float a(czb czb) {
		aoz aoz3;
		if (this.cs() instanceof aoz && ((aoz)this.cs()).q()) {
			aoz3 = (aoz)this.cs();
		} else {
			aoz3 = this;
		}

		Float float4 = (Float)aoz3.bA.get(czb);
		return float4 == null ? czb.a() : float4;
	}

	public void a(czb czb, float float2) {
		this.bA.put(czb, float2);
	}

	public boolean b(czb czb) {
		return czb != czb.DANGER_FIRE && czb != czb.DANGER_CACTUS && czb != czb.DANGER_OTHER;
	}

	protected atg r() {
		return new atg(this);
	}

	public atl t() {
		return this.g;
	}

	public atm u() {
		if (this.bn() && this.cs() instanceof aoz) {
			aoz aoz2 = (aoz)this.cs();
			return aoz2.u();
		} else {
			return this.bo;
		}
	}

	public atk v() {
		return this.bp;
	}

	public awv x() {
		if (this.bn() && this.cs() instanceof aoz) {
			aoz aoz2 = (aoz)this.cs();
			return aoz2.x();
		} else {
			return this.bq;
		}
	}

	public axm z() {
		return this.bv;
	}

	@Nullable
	public aoy A() {
		return this.d;
	}

	public void i(@Nullable aoy aoy) {
		this.d = aoy;
	}

	@Override
	public boolean a(aoq<?> aoq) {
		return aoq != aoq.D;
	}

	public boolean a(bkv bkv) {
		return false;
	}

	public void B() {
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(b, (byte)0);
	}

	public int D() {
		return 80;
	}

	public void F() {
		ack ack2 = this.I();
		if (ack2 != null) {
			this.a(ack2, this.dF(), this.dG());
		}
	}

	@Override
	public void ad() {
		super.ad();
		this.l.X().a("mobBaseTick");
		if (this.aU() && this.J.nextInt(1000) < this.e++) {
			this.eJ();
			this.F();
		}

		this.l.X().c();
	}

	@Override
	protected void c(anw anw) {
		this.eJ();
		super.c(anw);
	}

	private void eJ() {
		this.e = -this.D();
	}

	@Override
	protected int d(bec bec) {
		if (this.f > 0) {
			int integer3 = this.f;

			for (int integer4 = 0; integer4 < this.bx.size(); integer4++) {
				if (!this.bx.get(integer4).a() && this.bu[integer4] <= 1.0F) {
					integer3 += 1 + this.J.nextInt(3);
				}
			}

			for (int integer4x = 0; integer4x < this.bw.size(); integer4x++) {
				if (!this.bw.get(integer4x).a() && this.bt[integer4x] <= 1.0F) {
					integer3 += 1 + this.J.nextInt(3);
				}
			}

			return integer3;
		} else {
			return this.f;
		}
	}

	public void G() {
		if (this.l.v) {
			for (int integer2 = 0; integer2 < 20; integer2++) {
				double double3 = this.J.nextGaussian() * 0.02;
				double double5 = this.J.nextGaussian() * 0.02;
				double double7 = this.J.nextGaussian() * 0.02;
				double double9 = 10.0;
				this.l.a(hh.P, this.c(1.0) - double3 * 10.0, this.cE() - double5 * 10.0, this.g(1.0) - double7 * 10.0, double3, double5, double7);
			}
		} else {
			this.l.a(this, (byte)20);
		}
	}

	@Override
	public void j() {
		super.j();
		if (!this.l.v) {
			this.eB();
			if (this.K % 5 == 0) {
				this.H();
			}
		}
	}

	protected void H() {
		boolean boolean2 = !(this.cl() instanceof aoz);
		boolean boolean3 = !(this.cs() instanceof bft);
		this.br.a(aug.a.MOVE, boolean2);
		this.br.a(aug.a.JUMP, boolean2 && boolean3);
		this.br.a(aug.a.LOOK, boolean2);
	}

	@Override
	protected float f(float float1, float float2) {
		this.c.a();
		return float2;
	}

	@Nullable
	protected ack I() {
		return null;
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.a("CanPickUpLoot", this.eu());
		le.a("PersistenceRequired", this.bz);
		lk lk3 = new lk();

		for (bki bki5 : this.bx) {
			le le6 = new le();
			if (!bki5.a()) {
				bki5.b(le6);
			}

			lk3.add(le6);
		}

		le.a("ArmorItems", lk3);
		lk lk4 = new lk();

		for (bki bki6 : this.bw) {
			le le7 = new le();
			if (!bki6.a()) {
				bki6.b(le7);
			}

			lk4.add(le7);
		}

		le.a("HandItems", lk4);
		lk lk5 = new lk();

		for (float float9 : this.bu) {
			lk5.add(lh.a(float9));
		}

		le.a("ArmorDropChances", lk5);
		lk lk6 = new lk();

		for (float float10 : this.bt) {
			lk6.add(lh.a(float10));
		}

		le.a("HandDropChances", lk6);
		if (this.bD != null) {
			le le7 = new le();
			if (this.bD instanceof aoy) {
				UUID uUID8 = this.bD.bR();
				le7.a("UUID", uUID8);
			} else if (this.bD instanceof baz) {
				fu fu8 = ((baz)this.bD).n();
				le7.b("X", fu8.u());
				le7.b("Y", fu8.v());
				le7.b("Z", fu8.w());
			}

			le.a("Leash", le7);
		} else if (this.bF != null) {
			le.a("Leash", this.bF.g());
		}

		le.a("LeftHanded", this.eF());
		if (this.bB != null) {
			le.a("DeathLootTable", this.bB.toString());
			if (this.bC != 0L) {
				le.a("DeathLootTableSeed", this.bC);
			}
		}

		if (this.eE()) {
			le.a("NoAI", this.eE());
		}
	}

	@Override
	public void a(le le) {
		super.a(le);
		if (le.c("CanPickUpLoot", 1)) {
			this.p(le.q("CanPickUpLoot"));
		}

		this.bz = le.q("PersistenceRequired");
		if (le.c("ArmorItems", 9)) {
			lk lk3 = le.d("ArmorItems", 10);

			for (int integer4 = 0; integer4 < this.bx.size(); integer4++) {
				this.bx.set(integer4, bki.a(lk3.a(integer4)));
			}
		}

		if (le.c("HandItems", 9)) {
			lk lk3 = le.d("HandItems", 10);

			for (int integer4 = 0; integer4 < this.bw.size(); integer4++) {
				this.bw.set(integer4, bki.a(lk3.a(integer4)));
			}
		}

		if (le.c("ArmorDropChances", 9)) {
			lk lk3 = le.d("ArmorDropChances", 5);

			for (int integer4 = 0; integer4 < lk3.size(); integer4++) {
				this.bu[integer4] = lk3.i(integer4);
			}
		}

		if (le.c("HandDropChances", 9)) {
			lk lk3 = le.d("HandDropChances", 5);

			for (int integer4 = 0; integer4 < lk3.size(); integer4++) {
				this.bt[integer4] = lk3.i(integer4);
			}
		}

		if (le.c("Leash", 10)) {
			this.bF = le.p("Leash");
		}

		this.r(le.q("LeftHanded"));
		if (le.c("DeathLootTable", 8)) {
			this.bB = new uh(le.l("DeathLootTable"));
			this.bC = le.i("DeathLootTableSeed");
		}

		this.q(le.q("NoAI"));
	}

	@Override
	protected void a(anw anw, boolean boolean2) {
		super.a(anw, boolean2);
		this.bB = null;
	}

	@Override
	protected dat.a a(boolean boolean1, anw anw) {
		return super.a(boolean1, anw).a(this.bC, this.J);
	}

	@Override
	public final uh do() {
		return this.bB == null ? this.J() : this.bB;
	}

	protected uh J() {
		return super.do();
	}

	public void q(float float1) {
		this.ba = float1;
	}

	public void r(float float1) {
		this.aZ = float1;
	}

	public void s(float float1) {
		this.aY = float1;
	}

	@Override
	public void n(float float1) {
		super.n(float1);
		this.q(float1);
	}

	@Override
	public void k() {
		super.k();
		this.l.X().a("looting");
		if (!this.l.v && this.eu() && this.aU() && !this.aO && this.l.S().b(bpx.b)) {
			for (bbg bbg4 : this.l.a(bbg.class, this.cb().c(1.0, 0.0, 1.0))) {
				if (!bbg4.y && !bbg4.g().a() && !bbg4.p() && this.i(bbg4.g())) {
					this.b(bbg4);
				}
			}
		}

		this.l.X().c();
	}

	protected void b(bbg bbg) {
		bki bki3 = bbg.g();
		if (this.g(bki3)) {
			this.a(bbg);
			this.a(bbg, bki3.E());
			bbg.aa();
		}
	}

	public boolean g(bki bki) {
		aor aor3 = j(bki);
		bki bki4 = this.b(aor3);
		boolean boolean5 = this.a(bki, bki4);
		if (boolean5 && this.h(bki)) {
			double double6 = (double)this.e(aor3);
			if (!bki4.a() && (double)Math.max(this.J.nextFloat() - 0.1F, 0.0F) < double6) {
				this.a(bki4);
			}

			this.b(aor3, bki);
			this.b(bki);
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void b(aor aor, bki bki) {
		this.a(aor, bki);
		this.d(aor);
		this.bz = true;
	}

	public void d(aor aor) {
		switch (aor.a()) {
			case HAND:
				this.bt[aor.b()] = 2.0F;
				break;
			case ARMOR:
				this.bu[aor.b()] = 2.0F;
		}
	}

	protected boolean a(bki bki1, bki bki2) {
		if (bki2.a()) {
			return true;
		} else if (bki1.b() instanceof blm) {
			if (!(bki2.b() instanceof blm)) {
				return true;
			} else {
				blm blm4 = (blm)bki1.b();
				blm blm5 = (blm)bki2.b();
				return blm4.f() != blm5.f() ? blm4.f() > blm5.f() : this.b(bki1, bki2);
			}
		} else if (bki1.b() instanceof bis && bki2.b() instanceof bis) {
			return this.b(bki1, bki2);
		} else if (bki1.b() instanceof biz && bki2.b() instanceof biz) {
			return this.b(bki1, bki2);
		} else if (bki1.b() instanceof bid) {
			if (bny.d(bki2)) {
				return false;
			} else if (!(bki2.b() instanceof bid)) {
				return true;
			} else {
				bid bid4 = (bid)bki1.b();
				bid bid5 = (bid)bki2.b();
				if (bid4.e() != bid5.e()) {
					return bid4.e() > bid5.e();
				} else {
					return bid4.f() != bid5.f() ? bid4.f() > bid5.f() : this.b(bki1, bki2);
				}
			}
		} else {
			if (bki1.b() instanceof bjb) {
				if (bki2.b() instanceof bim) {
					return true;
				}

				if (bki2.b() instanceof bjb) {
					bjb bjb4 = (bjb)bki1.b();
					bjb bjb5 = (bjb)bki2.b();
					if (bjb4.d() != bjb5.d()) {
						return bjb4.d() > bjb5.d();
					}

					return this.b(bki1, bki2);
				}
			}

			return false;
		}
	}

	public boolean b(bki bki1, bki bki2) {
		if (bki1.g() >= bki2.g() && (!bki1.n() || bki2.n())) {
			return bki1.n() && bki2.n()
				? bki1.o().d().stream().anyMatch(string -> !string.equals("Damage")) && !bki2.o().d().stream().anyMatch(string -> !string.equals("Damage"))
				: false;
		} else {
			return true;
		}
	}

	public boolean h(bki bki) {
		return true;
	}

	public boolean i(bki bki) {
		return this.h(bki);
	}

	public boolean h(double double1) {
		return true;
	}

	public boolean K() {
		return this.bn();
	}

	protected boolean L() {
		return false;
	}

	@Override
	public void cH() {
		if (this.l.ac() == and.PEACEFUL && this.L()) {
			this.aa();
		} else if (!this.ev() && !this.K()) {
			aom aom2 = this.l.a(this, -1.0);
			if (aom2 != null) {
				double double3 = aom2.h(this);
				int integer5 = this.U().e().f();
				int integer6 = integer5 * integer5;
				if (double3 > (double)integer6 && this.h(double3)) {
					this.aa();
				}

				int integer7 = this.U().e().g();
				int integer8 = integer7 * integer7;
				if (this.aP > 600 && this.J.nextInt(800) == 0 && double3 > (double)integer8 && this.h(double3)) {
					this.aa();
				} else if (double3 < (double)integer8) {
					this.aP = 0;
				}
			}
		} else {
			this.aP = 0;
		}
	}

	@Override
	protected final void dO() {
		this.aP++;
		this.l.X().a("sensing");
		this.bv.a();
		this.l.X().c();
		this.l.X().a("targetSelector");
		this.bs.b();
		this.l.X().c();
		this.l.X().a("goalSelector");
		this.br.b();
		this.l.X().c();
		this.l.X().a("navigation");
		this.bq.c();
		this.l.X().c();
		this.l.X().a("mob tick");
		this.N();
		this.l.X().c();
		this.l.X().a("controls");
		this.l.X().a("move");
		this.bo.a();
		this.l.X().b("look");
		this.g.a();
		this.l.X().b("jump");
		this.bp.b();
		this.l.X().c();
		this.l.X().c();
		this.M();
	}

	protected void M() {
		qy.a(this.l, this, this.br);
	}

	protected void N() {
	}

	public int eo() {
		return 40;
	}

	public int ep() {
		return 75;
	}

	public int eq() {
		return 10;
	}

	public void a(aom aom, float float2, float float3) {
		double double5 = aom.cC() - this.cC();
		double double9 = aom.cG() - this.cG();
		double double7;
		if (aom instanceof aoy) {
			aoy aoy11 = (aoy)aom;
			double7 = aoy11.cF() - this.cF();
		} else {
			double7 = (aom.cb().b + aom.cb().e) / 2.0 - this.cF();
		}

		double double11 = (double)aec.a(double5 * double5 + double9 * double9);
		float float13 = (float)(aec.d(double9, double5) * 180.0F / (float)Math.PI) - 90.0F;
		float float14 = (float)(-(aec.d(double7, double11) * 180.0F / (float)Math.PI));
		this.q = this.a(this.q, float14, float3);
		this.p = this.a(this.p, float13, float2);
	}

	private float a(float float1, float float2, float float3) {
		float float5 = aec.g(float2 - float1);
		if (float5 > float3) {
			float5 = float3;
		}

		if (float5 < -float3) {
			float5 = -float3;
		}

		return float1 + float5;
	}

	public static boolean a(aoq<? extends aoz> aoq, bqc bqc, apb apb, fu fu, Random random) {
		fu fu6 = fu.c();
		return apb == apb.SPAWNER || bqc.d_(fu6).a(bqc, fu6, aoq);
	}

	public boolean a(bqc bqc, apb apb) {
		return true;
	}

	public boolean a(bqd bqd) {
		return !bqd.d(this.cb()) && bqd.i(this);
	}

	public int er() {
		return 4;
	}

	public boolean c(int integer) {
		return false;
	}

	@Override
	public int bL() {
		if (this.A() == null) {
			return 3;
		} else {
			int integer2 = (int)(this.dj() - this.dw() * 0.33F);
			integer2 -= (3 - this.l.ac().a()) * 4;
			if (integer2 < 0) {
				integer2 = 0;
			}

			return integer2 + 3;
		}
	}

	@Override
	public Iterable<bki> bj() {
		return this.bw;
	}

	@Override
	public Iterable<bki> bk() {
		return this.bx;
	}

	@Override
	public bki b(aor aor) {
		switch (aor.a()) {
			case HAND:
				return this.bw.get(aor.b());
			case ARMOR:
				return this.bx.get(aor.b());
			default:
				return bki.b;
		}
	}

	@Override
	public void a(aor aor, bki bki) {
		switch (aor.a()) {
			case HAND:
				this.bw.set(aor.b(), bki);
				break;
			case ARMOR:
				this.bx.set(aor.b(), bki);
		}
	}

	@Override
	protected void a(anw anw, int integer, boolean boolean3) {
		super.a(anw, integer, boolean3);

		for (aor aor8 : aor.values()) {
			bki bki9 = this.b(aor8);
			float float10 = this.e(aor8);
			boolean boolean11 = float10 > 1.0F;
			if (!bki9.a() && !bny.e(bki9) && (boolean3 || boolean11) && Math.max(this.J.nextFloat() - (float)integer * 0.01F, 0.0F) < float10) {
				if (!boolean11 && bki9.e()) {
					bki9.b(bki9.h() - this.J.nextInt(1 + this.J.nextInt(Math.max(bki9.h() - 3, 1))));
				}

				this.a(bki9);
			}
		}
	}

	protected float e(aor aor) {
		float float3;
		switch (aor.a()) {
			case HAND:
				float3 = this.bt[aor.b()];
				break;
			case ARMOR:
				float3 = this.bu[aor.b()];
				break;
			default:
				float3 = 0.0F;
		}

		return float3;
	}

	protected void a(ane ane) {
		if (this.J.nextFloat() < 0.15F * ane.d()) {
			int integer3 = this.J.nextInt(2);
			float float4 = this.l.ac() == and.HARD ? 0.1F : 0.25F;
			if (this.J.nextFloat() < 0.095F) {
				integer3++;
			}

			if (this.J.nextFloat() < 0.095F) {
				integer3++;
			}

			if (this.J.nextFloat() < 0.095F) {
				integer3++;
			}

			boolean boolean5 = true;

			for (aor aor9 : aor.values()) {
				if (aor9.a() == aor.a.ARMOR) {
					bki bki10 = this.b(aor9);
					if (!boolean5 && this.J.nextFloat() < float4) {
						break;
					}

					boolean5 = false;
					if (bki10.a()) {
						bke bke11 = a(aor9, integer3);
						if (bke11 != null) {
							this.a(aor9, new bki(bke11));
						}
					}
				}
			}
		}
	}

	public static aor j(bki bki) {
		bke bke2 = bki.b();
		if (bke2 != bvs.cU.h() && (!(bke2 instanceof bim) || !(((bim)bke2).e() instanceof but))) {
			if (bke2 instanceof bid) {
				return ((bid)bke2).b();
			} else if (bke2 == bkk.qn) {
				return aor.CHEST;
			} else {
				return bke2 == bkk.qm ? aor.OFFHAND : aor.MAINHAND;
			}
		} else {
			return aor.HEAD;
		}
	}

	@Nullable
	public static bke a(aor aor, int integer) {
		switch (aor) {
			case HEAD:
				if (integer == 0) {
					return bkk.kY;
				} else if (integer == 1) {
					return bkk.lo;
				} else if (integer == 2) {
					return bkk.lc;
				} else if (integer == 3) {
					return bkk.lg;
				} else if (integer == 4) {
					return bkk.lk;
				}
			case CHEST:
				if (integer == 0) {
					return bkk.kZ;
				} else if (integer == 1) {
					return bkk.lp;
				} else if (integer == 2) {
					return bkk.ld;
				} else if (integer == 3) {
					return bkk.lh;
				} else if (integer == 4) {
					return bkk.ll;
				}
			case LEGS:
				if (integer == 0) {
					return bkk.la;
				} else if (integer == 1) {
					return bkk.lq;
				} else if (integer == 2) {
					return bkk.le;
				} else if (integer == 3) {
					return bkk.li;
				} else if (integer == 4) {
					return bkk.lm;
				}
			case FEET:
				if (integer == 0) {
					return bkk.lb;
				} else if (integer == 1) {
					return bkk.lr;
				} else if (integer == 2) {
					return bkk.lf;
				} else if (integer == 3) {
					return bkk.lj;
				} else if (integer == 4) {
					return bkk.ln;
				}
			default:
				return null;
		}
	}

	protected void b(ane ane) {
		float float3 = ane.d();
		if (!this.dC().a() && this.J.nextFloat() < 0.25F * float3) {
			this.a(aor.MAINHAND, bny.a(this.J, this.dC(), (int)(5.0F + float3 * (float)this.J.nextInt(18)), false));
		}

		for (aor aor7 : aor.values()) {
			if (aor7.a() == aor.a.ARMOR) {
				bki bki8 = this.b(aor7);
				if (!bki8.a() && this.J.nextFloat() < 0.5F * float3) {
					this.a(aor7, bny.a(this.J, bki8, (int)(5.0F + float3 * (float)this.J.nextInt(18)), false));
				}
			}
		}
	}

	@Nullable
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		this.a(apx.b).c(new apv("Random spawn bonus", this.J.nextGaussian() * 0.05, apv.a.MULTIPLY_BASE));
		if (this.J.nextFloat() < 0.05F) {
			this.r(true);
		} else {
			this.r(false);
		}

		return apo;
	}

	public boolean es() {
		return false;
	}

	public void et() {
		this.bz = true;
	}

	public void a(aor aor, float float2) {
		switch (aor.a()) {
			case HAND:
				this.bt[aor.b()] = float2;
				break;
			case ARMOR:
				this.bu[aor.b()] = float2;
		}
	}

	public boolean eu() {
		return this.by;
	}

	public void p(boolean boolean1) {
		this.by = boolean1;
	}

	@Override
	public boolean e(bki bki) {
		aor aor3 = j(bki);
		return this.b(aor3).a() && this.eu();
	}

	public boolean ev() {
		return this.bz;
	}

	@Override
	public final ang a(bec bec, anf anf) {
		if (!this.aU()) {
			return ang.PASS;
		} else if (this.eD() == bec) {
			this.a(true, !bec.bJ.d);
			return ang.a(this.l.v);
		} else {
			ang ang4 = this.c(bec, anf);
			if (ang4.a()) {
				return ang4;
			} else {
				ang4 = this.b(bec, anf);
				return ang4.a() ? ang4 : super.a(bec, anf);
			}
		}
	}

	private ang c(bec bec, anf anf) {
		bki bki4 = bec.b(anf);
		if (bki4.b() == bkk.pG && this.a(bec)) {
			this.b(bec, true);
			bki4.g(1);
			return ang.a(this.l.v);
		} else {
			if (bki4.b() == bkk.pH) {
				ang ang5 = bki4.a(bec, this, anf);
				if (ang5.a()) {
					return ang5;
				}
			}

			if (bki4.b() instanceof blh) {
				if (!this.l.v) {
					blh blh5 = (blh)bki4.b();
					Optional<aoz> optional6 = blh5.a(bec, this, (aoq<? extends aoz>)this.U(), this.l, this.cz(), bki4);
					optional6.ifPresent(aoz -> this.a(bec, aoz));
					return optional6.isPresent() ? ang.SUCCESS : ang.PASS;
				} else {
					return ang.CONSUME;
				}
			} else {
				return ang.PASS;
			}
		}
	}

	protected void a(bec bec, aoz aoz) {
	}

	protected ang b(bec bec, anf anf) {
		return ang.PASS;
	}

	public boolean ew() {
		return this.a(this.cA());
	}

	public boolean a(fu fu) {
		return this.bH == -1.0F ? true : this.bG.j(fu) < (double)(this.bH * this.bH);
	}

	public void a(fu fu, int integer) {
		this.bG = fu;
		this.bH = (float)integer;
	}

	public fu ex() {
		return this.bG;
	}

	public float ey() {
		return this.bH;
	}

	public boolean eA() {
		return this.bH != -1.0F;
	}

	@Nullable
	protected <T extends aoz> T b(aoq<T> aoq) {
		if (this.y) {
			return null;
		} else {
			T aoz3 = (T)aoq.a(this.l);
			aoz3.u(this);
			aoz3.p(this.eu());
			aoz3.a(this.x_());
			aoz3.q(this.eE());
			if (this.Q()) {
				aoz3.a(this.R());
				aoz3.n(this.bW());
			}

			if (this.ev()) {
				aoz3.et();
			}

			aoz3.m(this.bI());

			for (aor aor7 : aor.values()) {
				bki bki8 = this.b(aor7);
				if (!bki8.a()) {
					aoz3.a(aor7, bki8.i());
					aoz3.a(aor7, this.e(aor7));
					bki8.e(0);
				}
			}

			this.l.c(aoz3);
			this.aa();
			return aoz3;
		}
	}

	protected void eB() {
		if (this.bF != null) {
			this.eK();
		}

		if (this.bD != null) {
			if (!this.aU() || !this.bD.aU()) {
				this.a(true, true);
			}
		}
	}

	public void a(boolean boolean1, boolean boolean2) {
		if (this.bD != null) {
			this.k = false;
			if (!(this.bD instanceof bec)) {
				this.bD.k = false;
			}

			this.bD = null;
			this.bF = null;
			if (!this.l.v && boolean2) {
				this.a(bkk.pG);
			}

			if (!this.l.v && boolean1 && this.l instanceof zd) {
				((zd)this.l).i().b(this, new qa(this, null));
			}
		}
	}

	public boolean a(bec bec) {
		return !this.eC() && !(this instanceof bbt);
	}

	public boolean eC() {
		return this.bD != null;
	}

	@Nullable
	public aom eD() {
		if (this.bD == null && this.bE != 0 && this.l.v) {
			this.bD = this.l.a(this.bE);
		}

		return this.bD;
	}

	public void b(aom aom, boolean boolean2) {
		this.bD = aom;
		this.bF = null;
		this.k = true;
		if (!(this.bD instanceof bec)) {
			this.bD.k = true;
		}

		if (!this.l.v && boolean2 && this.l instanceof zd) {
			((zd)this.l).i().b(this, new qa(this, this.bD));
		}

		if (this.bn()) {
			this.l();
		}
	}

	@Override
	public boolean a(aom aom, boolean boolean2) {
		boolean boolean4 = super.a(aom, boolean2);
		if (boolean4 && this.eC()) {
			this.a(true, true);
		}

		return boolean4;
	}

	private void eK() {
		if (this.bF != null && this.l instanceof zd) {
			if (this.bF.b("UUID")) {
				UUID uUID2 = this.bF.a("UUID");
				aom aom3 = ((zd)this.l).a(uUID2);
				if (aom3 != null) {
					this.b(aom3, true);
					return;
				}
			} else if (this.bF.c("X", 99) && this.bF.c("Y", 99) && this.bF.c("Z", 99)) {
				fu fu2 = new fu(this.bF.h("X"), this.bF.h("Y"), this.bF.h("Z"));
				this.b(bbb.a(this.l, fu2), true);
				return;
			}

			if (this.K > 100) {
				this.a(bkk.pG);
				this.bF = null;
			}
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

		if (!bki.a() && !c(aor4, bki) && aor4 != aor.HEAD) {
			return false;
		} else {
			this.a(aor4, bki);
			return true;
		}
	}

	@Override
	public boolean cr() {
		return this.es() && super.cr();
	}

	public static boolean c(aor aor, bki bki) {
		aor aor3 = j(bki);
		return aor3 == aor || aor3 == aor.MAINHAND && aor == aor.OFFHAND || aor3 == aor.OFFHAND && aor == aor.MAINHAND;
	}

	@Override
	public boolean dR() {
		return super.dR() && !this.eE();
	}

	public void q(boolean boolean1) {
		byte byte3 = this.S.a(b);
		this.S.b(b, boolean1 ? (byte)(byte3 | 1) : (byte)(byte3 & -2));
	}

	public void r(boolean boolean1) {
		byte byte3 = this.S.a(b);
		this.S.b(b, boolean1 ? (byte)(byte3 | 2) : (byte)(byte3 & -3));
	}

	public void s(boolean boolean1) {
		byte byte3 = this.S.a(b);
		this.S.b(b, boolean1 ? (byte)(byte3 | 4) : (byte)(byte3 & -5));
	}

	public boolean eE() {
		return (this.S.a(b) & 1) != 0;
	}

	public boolean eF() {
		return (this.S.a(b) & 2) != 0;
	}

	public boolean eG() {
		return (this.S.a(b) & 4) != 0;
	}

	public void a(boolean boolean1) {
	}

	@Override
	public aou dU() {
		return this.eF() ? aou.LEFT : aou.RIGHT;
	}

	@Override
	public boolean d(aoy aoy) {
		return aoy.U() == aoq.bb && ((bec)aoy).bJ.a ? false : super.d(aoy);
	}

	@Override
	public boolean B(aom aom) {
		float float3 = (float)this.b(apx.f);
		float float4 = (float)this.b(apx.g);
		if (aom instanceof aoy) {
			float3 += bny.a(this.dC(), ((aoy)aom).dB());
			float4 += (float)bny.b(this);
		}

		int integer5 = bny.c(this);
		if (integer5 > 0) {
			aom.f(integer5 * 4);
		}

		boolean boolean6 = aom.a(anw.c(this), float3);
		if (boolean6) {
			if (float4 > 0.0F && aom instanceof aoy) {
				((aoy)aom).a(float4 * 0.5F, (double)aec.a(this.p * (float) (Math.PI / 180.0)), (double)(-aec.b(this.p * (float) (Math.PI / 180.0))));
				this.e(this.cB().d(0.6, 1.0, 0.6));
			}

			if (aom instanceof bec) {
				bec bec7 = (bec)aom;
				this.a(bec7, this.dC(), bec7.dV() ? bec7.dX() : bki.b);
			}

			this.a(this, aom);
			this.z(aom);
		}

		return boolean6;
	}

	private void a(bec bec, bki bki2, bki bki3) {
		if (!bki2.a() && !bki3.a() && bki2.b() instanceof bii && bki3.b() == bkk.qm) {
			float float5 = 0.25F + (float)bny.f(this) * 0.05F;
			if (this.J.nextFloat() < float5) {
				bec.eT().a(bkk.qm, 100);
				this.l.a(bec, (byte)30);
			}
		}
	}

	protected boolean eH() {
		if (this.l.J() && !this.l.v) {
			float float2 = this.aO();
			fu fu3 = this.cs() instanceof bft
				? new fu(this.cC(), (double)Math.round(this.cD()), this.cG()).b()
				: new fu(this.cC(), (double)Math.round(this.cD()), this.cG());
			if (float2 > 0.5F && this.J.nextFloat() * 30.0F < (float2 - 0.4F) * 2.0F && this.l.f(fu3)) {
				return true;
			}
		}

		return false;
	}

	@Override
	protected void c(adf<cwz> adf) {
		if (this.x().r()) {
			super.c(adf);
		} else {
			this.e(this.cB().b(0.0, 0.3, 0.0));
		}
	}

	@Override
	protected void bJ() {
		super.bJ();
		this.a(true, false);
	}
}
