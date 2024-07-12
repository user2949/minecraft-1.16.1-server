import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public abstract class aoy extends aom {
	private static final UUID b = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
	private static final UUID c = UUID.fromString("87f46a96-686f-4796-b035-22e16ee9e038");
	private static final apv d = new apv(b, "Sprinting speed boost", 0.3F, apv.a.MULTIPLY_TOTAL);
	protected static final tq<Byte> an = tt.a(aoy.class, ts.a);
	private static final tq<Float> e = tt.a(aoy.class, ts.c);
	private static final tq<Integer> f = tt.a(aoy.class, ts.b);
	private static final tq<Boolean> g = tt.a(aoy.class, ts.i);
	private static final tq<Integer> bo = tt.a(aoy.class, ts.b);
	private static final tq<Integer> bp = tt.a(aoy.class, ts.b);
	private static final tq<Optional<fu>> bq = tt.a(aoy.class, ts.m);
	protected static final aon ao = aon.c(0.2F, 0.2F);
	private final apu br;
	private final anv bs = new anv(this);
	private final Map<aoe, aog> bt = Maps.<aoe, aog>newHashMap();
	private final gi<bki> bu = gi.a(2, bki.b);
	private final gi<bki> bv = gi.a(4, bki.b);
	public boolean ap;
	public anf aq;
	public int ar;
	public int as;
	public int at;
	public int au;
	public int av;
	public float aw;
	public int ax;
	public float ay;
	public float az;
	protected int aA;
	public float aB;
	public float aC;
	public float aD;
	public final int aE = 20;
	public final float aF;
	public final float aG;
	public float aH;
	public float aI;
	public float aJ;
	public float aK;
	public float aL = 0.02F;
	@Nullable
	protected bec aM;
	protected int aN;
	protected boolean aO;
	protected int aP;
	protected float aQ;
	protected float aR;
	protected float aS;
	protected float aT;
	protected float aU;
	protected int aV;
	protected float aW;
	protected boolean aX;
	public float aY;
	public float aZ;
	public float ba;
	protected int bb;
	protected double bc;
	protected double bd;
	protected double be;
	protected double bf;
	protected double bg;
	protected double bh;
	protected int bi;
	private boolean bw = true;
	@Nullable
	private aoy bx;
	private int by;
	private aoy bz;
	private int bA;
	private float bB;
	private int bC;
	private float bD;
	protected bki bj = bki.b;
	protected int bk;
	protected int bl;
	private fu bE;
	private Optional<fu> bF = Optional.empty();
	private anw bG;
	private long bH;
	protected int bm;
	private float bI;
	private float bJ;
	protected apr<?> bn;

	protected aoy(aoq<? extends aoy> aoq, bqb bqb) {
		super(aoq, bqb);
		this.br = new apu(apy.a(aoq));
		this.c(this.dw());
		this.i = true;
		this.aG = (float)((Math.random() + 1.0) * 0.01F);
		this.ac();
		this.aF = (float)Math.random() * 12398.0F;
		this.p = (float)(Math.random() * (float) (Math.PI * 2));
		this.aJ = this.p;
		this.G = 0.6F;
		lp lp4 = lp.a;
		this.bn = this.a(new Dynamic<>(lp4, lp4.createMap(ImmutableMap.of(lp4.a("memories"), lp4.emptyMap()))));
	}

	public apr<?> cI() {
		return this.bn;
	}

	protected apr.b<?> cJ() {
		return apr.a(ImmutableList.of(), ImmutableList.of());
	}

	protected apr<?> a(Dynamic<?> dynamic) {
		return this.cJ().a(dynamic);
	}

	@Override
	public void X() {
		this.a(anw.m, Float.MAX_VALUE);
	}

	public boolean a(aoq<?> aoq) {
		return true;
	}

	@Override
	protected void e() {
		this.S.a(an, (byte)0);
		this.S.a(f, 0);
		this.S.a(g, false);
		this.S.a(bo, 0);
		this.S.a(bp, 0);
		this.S.a(e, 1.0F);
		this.S.a(bq, Optional.empty());
	}

	public static apw.a cK() {
		return apw.a().a(apx.a).a(apx.c).a(apx.d).a(apx.i).a(apx.j);
	}

	@Override
	protected void a(double double1, boolean boolean2, cfj cfj, fu fu) {
		if (!this.aA()) {
			this.aH();
		}

		if (!this.l.v && boolean2 && this.C > 0.0F) {
			this.cP();
			this.cQ();
		}

		if (!this.l.v && this.C > 3.0F && boolean2) {
			float float7 = (float)aec.f(this.C - 3.0F);
			if (!cfj.g()) {
				double double8 = Math.min((double)(0.2F + float7 / 15.0F), 2.5);
				int integer10 = (int)(150.0 * double8);
				((zd)this.l).a(new hc(hh.d, cfj), this.cC(), this.cD(), this.cG(), integer10, 0.0, 0.0, 0.0, 0.15F);
			}
		}

		super.a(double1, boolean2, cfj, fu);
	}

	public boolean cL() {
		return this.dB() == apc.b;
	}

	@Override
	public void ad() {
		this.ay = this.az;
		if (this.R) {
			this.ej().ifPresent(this::a);
		}

		if (this.cM()) {
			this.cN();
		}

		super.ad();
		this.l.X().a("livingEntityBaseTick");
		boolean boolean2 = this instanceof bec;
		if (this.aU()) {
			if (this.aV()) {
				this.a(anw.f, 1.0F);
			} else if (boolean2 && !this.l.f().a(this.cb())) {
				double double3 = this.l.f().a(this) + this.l.f().n();
				if (double3 < 0.0) {
					double double5 = this.l.f().o();
					if (double5 > 0.0) {
						this.a(anw.f, (float)Math.max(1, aec.c(-double3 * double5)));
					}
				}
			}
		}

		if (this.az() || this.l.v) {
			this.ah();
		}

		boolean boolean3 = boolean2 && ((bec)this).bJ.a;
		if (this.aU()) {
			if (this.a(acz.a) && !this.l.d_(new fu(this.cC(), this.cF(), this.cG())).a(bvs.lc)) {
				if (!this.cL() && !aoh.c(this) && !boolean3) {
					this.j(this.l(this.bE()));
					if (this.bE() == -20) {
						this.j(0);
						dem dem4 = this.cB();

						for (int integer5 = 0; integer5 < 8; integer5++) {
							double double6 = this.J.nextDouble() - this.J.nextDouble();
							double double8 = this.J.nextDouble() - this.J.nextDouble();
							double double10 = this.J.nextDouble() - this.J.nextDouble();
							this.l.a(hh.e, this.cC() + double6, this.cD() + double8, this.cG() + double10, dem4.b, dem4.c, dem4.d);
						}

						this.a(anw.h, 2.0F);
					}
				}

				if (!this.l.v && this.bn() && this.cs() != null && !this.cs().bp()) {
					this.l();
				}
			} else if (this.bE() < this.bD()) {
				this.j(this.m(this.bE()));
			}

			if (!this.l.v) {
				fu fu4 = this.cA();
				if (!Objects.equal(this.bE, fu4)) {
					this.bE = fu4;
					this.c(fu4);
				}
			}
		}

		if (this.aU() && this.aC()) {
			this.ah();
		}

		if (this.au > 0) {
			this.au--;
		}

		if (this.Q > 0 && !(this instanceof ze)) {
			this.Q--;
		}

		if (this.dk()) {
			this.cT();
		}

		if (this.aN > 0) {
			this.aN--;
		} else {
			this.aM = null;
		}

		if (this.bz != null && !this.bz.aU()) {
			this.bz = null;
		}

		if (this.bx != null) {
			if (!this.bx.aU()) {
				this.a(null);
			} else if (this.K - this.by > 100) {
				this.a(null);
			}
		}

		this.dd();
		this.aT = this.aS;
		this.aI = this.aH;
		this.aK = this.aJ;
		this.r = this.p;
		this.s = this.q;
		this.l.X().c();
	}

	public boolean cM() {
		return this.K % 5 == 0 && this.cB().b != 0.0 && this.cB().d != 0.0 && !this.a_() && bny.j(this) && this.cO();
	}

	protected void cN() {
		dem dem2 = this.cB();
		this.l
			.a(
				hh.C,
				this.cC() + (this.J.nextDouble() - 0.5) * (double)this.cx(),
				this.cD() + 0.1,
				this.cG() + (this.J.nextDouble() - 0.5) * (double)this.cx(),
				dem2.b * -0.2,
				0.1,
				dem2.d * -0.2
			);
		float float3 = this.J.nextFloat() * 0.4F + this.J.nextFloat() > 0.9F ? 0.6F : 0.0F;
		this.a(acl.nS, float3, 0.6F + this.J.nextFloat() * 0.4F);
	}

	protected boolean cO() {
		return this.aJ().a(acx.aq);
	}

	@Override
	protected float am() {
		return this.cO() && bny.a(boa.l, this) > 0 ? 1.0F : super.am();
	}

	protected boolean b(cfj cfj) {
		return !cfj.g() || this.ee();
	}

	protected void cP() {
		apt apt2 = this.a(apx.d);
		if (apt2 != null) {
			if (apt2.a(c) != null) {
				apt2.b(c);
			}
		}
	}

	protected void cQ() {
		if (!this.aJ().g()) {
			int integer2 = bny.a(boa.l, this);
			if (integer2 > 0 && this.cO()) {
				apt apt3 = this.a(apx.d);
				if (apt3 == null) {
					return;
				}

				apt3.b(new apv(c, "Soul speed boost", (double)(0.03F * (1.0F + (float)integer2 * 0.35F)), apv.a.ADDITION));
				if (this.cX().nextFloat() < 0.04F) {
					bki bki4 = this.b(aor.FEET);
					bki4.a(1, this, aoy -> aoy.c(aor.FEET));
				}
			}
		}
	}

	protected void c(fu fu) {
		int integer3 = bny.a(boa.j, this);
		if (integer3 > 0) {
			bod.a(this, this.l, fu, integer3);
		}

		if (this.b(this.aJ())) {
			this.cP();
		}

		this.cQ();
	}

	public boolean x_() {
		return false;
	}

	public float cR() {
		return this.x_() ? 0.5F : 1.0F;
	}

	protected boolean cS() {
		return true;
	}

	@Override
	public boolean bp() {
		return false;
	}

	protected void cT() {
		this.ax++;
		if (this.ax == 20) {
			this.aa();

			for (int integer2 = 0; integer2 < 20; integer2++) {
				double double3 = this.J.nextGaussian() * 0.02;
				double double5 = this.J.nextGaussian() * 0.02;
				double double7 = this.J.nextGaussian() * 0.02;
				this.l.a(hh.P, this.d(1.0), this.cE(), this.g(1.0), double3, double5, double7);
			}
		}
	}

	protected boolean cU() {
		return !this.x_();
	}

	protected boolean cV() {
		return !this.x_();
	}

	protected int l(int integer) {
		int integer3 = bny.d(this);
		return integer3 > 0 && this.J.nextInt(integer3 + 1) > 0 ? integer : integer - 1;
	}

	protected int m(int integer) {
		return Math.min(integer + 4, this.bD());
	}

	protected int d(bec bec) {
		return 0;
	}

	protected boolean cW() {
		return false;
	}

	public Random cX() {
		return this.J;
	}

	@Nullable
	public aoy cY() {
		return this.bx;
	}

	public int cZ() {
		return this.by;
	}

	public void e(@Nullable bec bec) {
		this.aM = bec;
		this.aN = this.K;
	}

	public void a(@Nullable aoy aoy) {
		this.bx = aoy;
		this.by = this.K;
	}

	@Nullable
	public aoy da() {
		return this.bz;
	}

	public int db() {
		return this.bA;
	}

	public void z(aom aom) {
		if (aom instanceof aoy) {
			this.bz = (aoy)aom;
		} else {
			this.bz = null;
		}

		this.bA = this.K;
	}

	public int dc() {
		return this.aP;
	}

	public void n(int integer) {
		this.aP = integer;
	}

	protected void b(bki bki) {
		if (!bki.a()) {
			ack ack3 = acl.M;
			bke bke4 = bki.b();
			if (bke4 instanceof bid) {
				ack3 = ((bid)bke4).ad_().b();
			} else if (bke4 == bkk.qn) {
				ack3 = acl.L;
			}

			this.a(ack3, 1.0F, 1.0F);
		}
	}

	@Override
	public void b(le le) {
		le.a("Health", this.dj());
		le.a("HurtTime", (short)this.au);
		le.b("HurtByTimestamp", this.by);
		le.a("DeathTime", (short)this.ax);
		le.a("AbsorptionAmount", this.dS());
		le.a("Attributes", this.dA().c());
		if (!this.bt.isEmpty()) {
			lk lk3 = new lk();

			for (aog aog5 : this.bt.values()) {
				lk3.add(aog5.a(new le()));
			}

			le.a("ActiveEffects", lk3);
		}

		le.a("FallFlying", this.ee());
		this.ej().ifPresent(fu -> {
			le.b("SleepingX", fu.u());
			le.b("SleepingY", fu.v());
			le.b("SleepingZ", fu.w());
		});
		DataResult<lu> dataResult3 = this.bn.a(lp.a);
		dataResult3.resultOrPartial(h::error).ifPresent(lu -> le.a("Brain", lu));
	}

	@Override
	public void a(le le) {
		this.p(le.j("AbsorptionAmount"));
		if (le.c("Attributes", 9) && this.l != null && !this.l.v) {
			this.dA().a(le.d("Attributes", 10));
		}

		if (le.c("ActiveEffects", 9)) {
			lk lk3 = le.d("ActiveEffects", 10);

			for (int integer4 = 0; integer4 < lk3.size(); integer4++) {
				le le5 = lk3.a(integer4);
				aog aog6 = aog.b(le5);
				if (aog6 != null) {
					this.bt.put(aog6.a(), aog6);
				}
			}
		}

		if (le.c("Health", 99)) {
			this.c(le.j("Health"));
		}

		this.au = le.g("HurtTime");
		this.ax = le.g("DeathTime");
		this.by = le.h("HurtByTimestamp");
		if (le.c("Team", 8)) {
			String string3 = le.l("Team");
			dfk dfk4 = this.l.D().f(string3);
			boolean boolean5 = dfk4 != null && this.l.D().a(this.bS(), dfk4);
			if (!boolean5) {
				h.warn("Unable to add mob to team \"{}\" (that team probably doesn't exist)", string3);
			}
		}

		if (le.q("FallFlying")) {
			this.b(7, true);
		}

		if (le.c("SleepingX", 99) && le.c("SleepingY", 99) && le.c("SleepingZ", 99)) {
			fu fu3 = new fu(le.h("SleepingX"), le.h("SleepingY"), le.h("SleepingZ"));
			this.e(fu3);
			this.S.b(U, apj.SLEEPING);
			if (!this.R) {
				this.a(fu3);
			}
		}

		if (le.c("Brain", 10)) {
			this.bn = this.a(new Dynamic<>(lp.a, le.c("Brain")));
		}
	}

	protected void dd() {
		Iterator<aoe> iterator2 = this.bt.keySet().iterator();

		try {
			while (iterator2.hasNext()) {
				aoe aoe3 = (aoe)iterator2.next();
				aog aog4 = (aog)this.bt.get(aoe3);
				if (!aog4.a(this, () -> this.a(aog4, true))) {
					if (!this.l.v) {
						iterator2.remove();
						this.b(aog4);
					}
				} else if (aog4.b() % 600 == 0) {
					this.a(aog4, false);
				}
			}
		} catch (ConcurrentModificationException var11) {
		}

		if (this.bw) {
			if (!this.l.v) {
				this.C();
			}

			this.bw = false;
		}

		int integer3 = this.S.a(f);
		boolean boolean4 = this.S.a(g);
		if (integer3 > 0) {
			boolean boolean5;
			if (this.bB()) {
				boolean5 = this.J.nextInt(15) == 0;
			} else {
				boolean5 = this.J.nextBoolean();
			}

			if (boolean4) {
				boolean5 &= this.J.nextInt(5) == 0;
			}

			if (boolean5 && integer3 > 0) {
				double double6 = (double)(integer3 >> 16 & 0xFF) / 255.0;
				double double8 = (double)(integer3 >> 8 & 0xFF) / 255.0;
				double double10 = (double)(integer3 >> 0 & 0xFF) / 255.0;
				this.l.a(boolean4 ? hh.a : hh.u, this.d(0.5), this.cE(), this.g(0.5), double6, double8, double10);
			}
		}
	}

	protected void C() {
		if (this.bt.isEmpty()) {
			this.de();
			this.j(false);
		} else {
			Collection<aog> collection2 = this.bt.values();
			this.S.b(g, c(collection2));
			this.S.b(f, bmd.a(collection2));
			this.j(this.a(aoi.n));
		}
	}

	public double A(@Nullable aom aom) {
		double double3 = 1.0;
		if (this.bt()) {
			double3 *= 0.8;
		}

		if (this.bB()) {
			float float5 = this.dE();
			if (float5 < 0.1F) {
				float5 = 0.1F;
			}

			double3 *= 0.7 * (double)float5;
		}

		if (aom != null) {
			bki bki5 = this.b(aor.HEAD);
			bke bke6 = bki5.b();
			aoq<?> aoq7 = aom.U();
			if (aoq7 == aoq.au && bke6 == bkk.pd || aoq7 == aoq.aX && bke6 == bkk.pg || aoq7 == aoq.m && bke6 == bkk.ph) {
				double3 *= 0.5;
			}
		}

		return double3;
	}

	public boolean d(aoy aoy) {
		return true;
	}

	public boolean a(aoy aoy, axs axs) {
		return axs.a(this, aoy);
	}

	public static boolean c(Collection<aog> collection) {
		for (aog aog3 : collection) {
			if (!aog3.d()) {
				return false;
			}
		}

		return true;
	}

	protected void de() {
		this.S.b(g, false);
		this.S.b(f, 0);
	}

	public boolean df() {
		if (this.l.v) {
			return false;
		} else {
			Iterator<aog> iterator2 = this.bt.values().iterator();

			boolean boolean3;
			for (boolean3 = false; iterator2.hasNext(); boolean3 = true) {
				this.b((aog)iterator2.next());
				iterator2.remove();
			}

			return boolean3;
		}
	}

	public Collection<aog> dg() {
		return this.bt.values();
	}

	public Map<aoe, aog> dh() {
		return this.bt;
	}

	public boolean a(aoe aoe) {
		return this.bt.containsKey(aoe);
	}

	@Nullable
	public aog b(aoe aoe) {
		return (aog)this.bt.get(aoe);
	}

	public boolean c(aog aog) {
		if (!this.d(aog)) {
			return false;
		} else {
			aog aog3 = (aog)this.bt.get(aog.a());
			if (aog3 == null) {
				this.bt.put(aog.a(), aog);
				this.a(aog);
				return true;
			} else if (aog3.b(aog)) {
				this.a(aog3, true);
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean d(aog aog) {
		if (this.dB() == apc.b) {
			aoe aoe3 = aog.a();
			if (aoe3 == aoi.j || aoe3 == aoi.s) {
				return false;
			}
		}

		return true;
	}

	public boolean di() {
		return this.dB() == apc.b;
	}

	@Nullable
	public aog c(@Nullable aoe aoe) {
		return (aog)this.bt.remove(aoe);
	}

	public boolean d(aoe aoe) {
		aog aog3 = this.c(aoe);
		if (aog3 != null) {
			this.b(aog3);
			return true;
		} else {
			return false;
		}
	}

	protected void a(aog aog) {
		this.bw = true;
		if (!this.l.v) {
			aog.a().b(this, this.dA(), aog.c());
		}
	}

	protected void a(aog aog, boolean boolean2) {
		this.bw = true;
		if (boolean2 && !this.l.v) {
			aoe aoe4 = aog.a();
			aoe4.a(this, this.dA(), aog.c());
			aoe4.b(this, this.dA(), aog.c());
		}
	}

	protected void b(aog aog) {
		this.bw = true;
		if (!this.l.v) {
			aog.a().a(this, this.dA(), aog.c());
		}
	}

	public void b(float float1) {
		float float3 = this.dj();
		if (float3 > 0.0F) {
			this.c(float3 + float1);
		}
	}

	public float dj() {
		return this.S.a(e);
	}

	public void c(float float1) {
		this.S.b(e, aec.a(float1, 0.0F, this.dw()));
	}

	public boolean dk() {
		return this.dj() <= 0.0F;
	}

	@Override
	public boolean a(anw anw, float float2) {
		if (this.b(anw)) {
			return false;
		} else if (this.l.v) {
			return false;
		} else if (this.dk()) {
			return false;
		} else if (anw.p() && this.a(aoi.l)) {
			return false;
		} else {
			if (this.el() && !this.l.v) {
				this.em();
			}

			this.aP = 0;
			float float4 = float2;
			if ((anw == anw.q || anw == anw.r) && !this.b(aor.HEAD).a()) {
				this.b(aor.HEAD).a((int)(float2 * 4.0F + this.J.nextFloat() * float2 * 2.0F), this, aoy -> aoy.c(aor.HEAD));
				float2 *= 0.75F;
			}

			boolean boolean5 = false;
			float float6 = 0.0F;
			if (float2 > 0.0F && this.g(anw)) {
				this.m(float2);
				float6 = float2;
				float2 = 0.0F;
				if (!anw.b()) {
					aom aom7 = anw.j();
					if (aom7 instanceof aoy) {
						this.e((aoy)aom7);
					}
				}

				boolean5 = true;
			}

			this.aC = 1.5F;
			boolean boolean7 = true;
			if ((float)this.Q > 10.0F) {
				if (float2 <= this.aW) {
					return false;
				}

				this.e(anw, float2 - this.aW);
				this.aW = float2;
				boolean7 = false;
			} else {
				this.aW = float2;
				this.Q = 20;
				this.e(anw, float2);
				this.av = 10;
				this.au = this.av;
			}

			this.aw = 0.0F;
			aom aom8 = anw.k();
			if (aom8 != null) {
				if (aom8 instanceof aoy) {
					this.a((aoy)aom8);
				}

				if (aom8 instanceof bec) {
					this.aN = 100;
					this.aM = (bec)aom8;
				} else if (aom8 instanceof azk) {
					azk azk9 = (azk)aom8;
					if (azk9.eL()) {
						this.aN = 100;
						aoy aoy10 = azk9.eO();
						if (aoy10 != null && aoy10.U() == aoq.bb) {
							this.aM = (bec)aoy10;
						} else {
							this.aM = null;
						}
					}
				}
			}

			if (boolean7) {
				if (boolean5) {
					this.l.a(this, (byte)29);
				} else if (anw instanceof anx && ((anx)anw).y()) {
					this.l.a(this, (byte)33);
				} else {
					byte byte9;
					if (anw == anw.h) {
						byte9 = 36;
					} else if (anw.p()) {
						byte9 = 37;
					} else if (anw == anw.u) {
						byte9 = 44;
					} else {
						byte9 = 2;
					}

					this.l.a(this, byte9);
				}

				if (anw != anw.h && (!boolean5 || float2 > 0.0F)) {
					this.aP();
				}

				if (aom8 != null) {
					double double9 = aom8.cC() - this.cC();

					double double11;
					for (double11 = aom8.cG() - this.cG(); double9 * double9 + double11 * double11 < 1.0E-4; double11 = (Math.random() - Math.random()) * 0.01) {
						double9 = (Math.random() - Math.random()) * 0.01;
					}

					this.aw = (float)(aec.d(double11, double9) * 180.0F / (float)Math.PI - (double)this.p);
					this.a(0.4F, double9, double11);
				} else {
					this.aw = (float)((int)(Math.random() * 2.0) * 180);
				}
			}

			if (this.dk()) {
				if (!this.f(anw)) {
					ack ack9 = this.dp();
					if (boolean7 && ack9 != null) {
						this.a(ack9, this.dF(), this.dG());
					}

					this.a(anw);
				}
			} else if (boolean7) {
				this.c(anw);
			}

			boolean boolean9 = !boolean5 || float2 > 0.0F;
			if (boolean9) {
				this.bG = anw;
				this.bH = this.l.Q();
			}

			if (this instanceof ze) {
				aa.h.a((ze)this, anw, float4, float2, boolean5);
				if (float6 > 0.0F && float6 < 3.4028235E37F) {
					((ze)this).a(acu.J, Math.round(float6 * 10.0F));
				}
			}

			if (aom8 instanceof ze) {
				aa.g.a((ze)aom8, this, anw, float4, float2, boolean5);
			}

			return boolean9;
		}
	}

	protected void e(aoy aoy) {
		aoy.f(this);
	}

	protected void f(aoy aoy) {
		aoy.a(0.5F, aoy.cC() - this.cC(), aoy.cG() - this.cG());
	}

	private boolean f(anw anw) {
		if (anw.h()) {
			return false;
		} else {
			bki bki3 = null;

			for (anf anf8 : anf.values()) {
				bki bki4 = this.b(anf8);
				if (bki4.b() == bkk.qt) {
					bki3 = bki4.i();
					bki4.g(1);
					break;
				}
			}

			if (bki3 != null) {
				if (this instanceof ze) {
					ze ze5 = (ze)this;
					ze5.b(acu.c.b(bkk.qt));
					aa.B.a(ze5, bki3);
				}

				this.c(1.0F);
				this.df();
				this.c(new aog(aoi.j, 900, 1));
				this.c(new aog(aoi.v, 100, 1));
				this.l.a(this, (byte)35);
			}

			return bki3 != null;
		}
	}

	@Nullable
	public anw dl() {
		if (this.l.Q() - this.bH > 40L) {
			this.bG = null;
		}

		return this.bG;
	}

	protected void c(anw anw) {
		ack ack3 = this.e(anw);
		if (ack3 != null) {
			this.a(ack3, this.dF(), this.dG());
		}
	}

	private boolean g(anw anw) {
		aom aom3 = anw.j();
		boolean boolean4 = false;
		if (aom3 instanceof beg) {
			beg beg5 = (beg)aom3;
			if (beg5.r() > 0) {
				boolean4 = true;
			}
		}

		if (!anw.f() && this.ec() && !boolean4) {
			dem dem5 = anw.w();
			if (dem5 != null) {
				dem dem6 = this.f(1.0F);
				dem dem7 = dem5.a(this.cz()).d();
				dem7 = new dem(dem7.b, 0.0, dem7.d);
				if (dem7.b(dem6) < 0.0) {
					return true;
				}
			}
		}

		return false;
	}

	public void a(anw anw) {
		if (!this.y && !this.aO) {
			aom aom3 = anw.k();
			aoy aoy4 = this.dv();
			if (this.aV >= 0 && aoy4 != null) {
				aoy4.a(this, this.aV, anw);
			}

			if (aom3 != null) {
				aom3.a_(this);
			}

			if (this.el()) {
				this.em();
			}

			this.aO = true;
			this.du().g();
			if (!this.l.v) {
				this.d(anw);
				this.g(aoy4);
			}

			this.l.a(this, (byte)3);
			this.b(apj.DYING);
		}
	}

	protected void g(@Nullable aoy aoy) {
		if (!this.l.v) {
			boolean boolean3 = false;
			if (aoy instanceof baw) {
				if (this.l.S().b(bpx.b)) {
					fu fu4 = this.cA();
					cfj cfj5 = bvs.bA.n();
					if (this.l.d_(fu4).g() && cfj5.a((bqd)this.l, fu4)) {
						this.l.a(fu4, cfj5, 3);
						boolean3 = true;
					}
				}

				if (!boolean3) {
					bbg bbg4 = new bbg(this.l, this.cC(), this.cD(), this.cG(), new bki(bkk.bt));
					this.l.c(bbg4);
				}
			}
		}
	}

	protected void d(anw anw) {
		aom aom3 = anw.k();
		int integer4;
		if (aom3 instanceof bec) {
			integer4 = bny.g((aoy)aom3);
		} else {
			integer4 = 0;
		}

		boolean boolean5 = this.aN > 0;
		if (this.cV() && this.l.S().b(bpx.e)) {
			this.a(anw, boolean5);
			this.a(anw, integer4, boolean5);
		}

		this.dm();
		this.dn();
	}

	protected void dm() {
	}

	protected void dn() {
		if (!this.l.v && (this.cW() || this.aN > 0 && this.cU() && this.l.S().b(bpx.e))) {
			int integer2 = this.d(this.aM);

			while (integer2 > 0) {
				int integer3 = aos.a(integer2);
				integer2 -= integer3;
				this.l.c(new aos(this.l, this.cC(), this.cD(), this.cG(), integer3));
			}
		}
	}

	protected void a(anw anw, int integer, boolean boolean3) {
	}

	public uh do() {
		return this.U().i();
	}

	protected void a(anw anw, boolean boolean2) {
		uh uh4 = this.do();
		daw daw5 = this.l.l().aH().a(uh4);
		dat.a a6 = this.a(boolean2, anw);
		daw5.b(a6.a(dcz.f), this::a);
	}

	protected dat.a a(boolean boolean1, anw anw) {
		dat.a a4 = new dat.a((zd)this.l).a(this.J).a(dda.a, this).a(dda.f, this.cA()).a(dda.c, anw).b(dda.d, anw.k()).b(dda.e, anw.j());
		if (boolean1 && this.aM != null) {
			a4 = a4.a(dda.b, this.aM).a(this.aM.eU());
		}

		return a4;
	}

	public void a(float float1, double double2, double double3) {
		float1 = (float)((double)float1 * (1.0 - this.b(apx.c)));
		if (!(float1 <= 0.0F)) {
			this.ad = true;
			dem dem7 = this.cB();
			dem dem8 = new dem(double2, 0.0, double3).d().a((double)float1);
			this.m(dem7.b / 2.0 - dem8.b, this.t ? Math.min(0.4, dem7.c / 2.0 + (double)float1) : dem7.c, dem7.d / 2.0 - dem8.d);
		}
	}

	@Nullable
	protected ack e(anw anw) {
		return acl.eN;
	}

	@Nullable
	protected ack dp() {
		return acl.eI;
	}

	protected ack o(int integer) {
		return integer > 4 ? acl.eG : acl.eO;
	}

	protected ack c(bki bki) {
		return bki.G();
	}

	public ack d(bki bki) {
		return bki.H();
	}

	@Override
	public void c(boolean boolean1) {
		super.c(boolean1);
		if (boolean1) {
			this.bF = Optional.empty();
		}
	}

	public Optional<fu> dq() {
		return this.bF;
	}

	public boolean c_() {
		if (this.a_()) {
			return false;
		} else {
			fu fu2 = this.cA();
			cfj cfj3 = this.dr();
			bvr bvr4 = cfj3.b();
			if (bvr4.a(acx.as)) {
				this.bF = Optional.of(fu2);
				return true;
			} else if (bvr4 instanceof ccd && this.b(fu2, cfj3)) {
				this.bF = Optional.of(fu2);
				return true;
			} else {
				return false;
			}
		}
	}

	public cfj dr() {
		return this.l.d_(this.cA());
	}

	private boolean b(fu fu, cfj cfj) {
		if ((Boolean)cfj.c(ccd.a)) {
			cfj cfj4 = this.l.d_(fu.c());
			if (cfj4.a(bvs.cg) && cfj4.c(byy.a) == cfj.c(ccd.aq)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean aU() {
		return !this.y && this.dj() > 0.0F;
	}

	@Override
	public boolean b(float float1, float float2) {
		boolean boolean4 = super.b(float1, float2);
		int integer5 = this.e(float1, float2);
		if (integer5 > 0) {
			this.a(this.o(integer5), 1.0F, 1.0F);
			this.ds();
			this.a(anw.k, (float)integer5);
			return true;
		} else {
			return boolean4;
		}
	}

	protected int e(float float1, float float2) {
		aog aog4 = this.b(aoi.h);
		float float5 = aog4 == null ? 0.0F : (float)(aog4.c() + 1);
		return aec.f((float1 - 3.0F - float5) * float2);
	}

	protected void ds() {
		if (!this.av()) {
			int integer2 = aec.c(this.cC());
			int integer3 = aec.c(this.cD() - 0.2F);
			int integer4 = aec.c(this.cG());
			cfj cfj5 = this.l.d_(new fu(integer2, integer3, integer4));
			if (!cfj5.g()) {
				cbh cbh6 = cfj5.o();
				this.a(cbh6.g(), cbh6.a() * 0.5F, cbh6.b() * 0.75F);
			}
		}
	}

	public int dt() {
		return aec.c(this.b(apx.i));
	}

	protected void b(anw anw, float float2) {
	}

	protected void m(float float1) {
	}

	protected float c(anw anw, float float2) {
		if (!anw.f()) {
			this.b(anw, float2);
			float2 = anu.a(float2, (float)this.dt(), (float)this.b(apx.j));
		}

		return float2;
	}

	protected float d(anw anw, float float2) {
		if (anw.i()) {
			return float2;
		} else {
			if (this.a(aoi.k) && anw != anw.m) {
				int integer4 = (this.b(aoi.k).c() + 1) * 5;
				int integer5 = 25 - integer4;
				float float6 = float2 * (float)integer5;
				float float7 = float2;
				float2 = Math.max(float6 / 25.0F, 0.0F);
				float float8 = float7 - float2;
				if (float8 > 0.0F && float8 < 3.4028235E37F) {
					if (this instanceof ze) {
						((ze)this).a(acu.L, Math.round(float8 * 10.0F));
					} else if (anw.k() instanceof ze) {
						((ze)anw.k()).a(acu.H, Math.round(float8 * 10.0F));
					}
				}
			}

			if (float2 <= 0.0F) {
				return 0.0F;
			} else {
				int integer4 = bny.a(this.bk(), anw);
				if (integer4 > 0) {
					float2 = anu.a(float2, (float)integer4);
				}

				return float2;
			}
		}
	}

	protected void e(anw anw, float float2) {
		if (!this.b(anw)) {
			float2 = this.c(anw, float2);
			float2 = this.d(anw, float2);
			float var8 = Math.max(float2 - this.dS(), 0.0F);
			this.p(this.dS() - (float2 - var8));
			float float5 = float2 - var8;
			if (float5 > 0.0F && float5 < 3.4028235E37F && anw.k() instanceof ze) {
				((ze)anw.k()).a(acu.G, Math.round(float5 * 10.0F));
			}

			if (var8 != 0.0F) {
				float float6 = this.dj();
				this.c(float6 - var8);
				this.du().a(anw, float6, var8);
				this.p(this.dS() - var8);
			}
		}
	}

	public anv du() {
		return this.bs;
	}

	@Nullable
	public aoy dv() {
		if (this.bs.c() != null) {
			return this.bs.c();
		} else if (this.aM != null) {
			return this.aM;
		} else {
			return this.bx != null ? this.bx : null;
		}
	}

	public final float dw() {
		return (float)this.b(apx.a);
	}

	public final int dx() {
		return this.S.a(bo);
	}

	public final void p(int integer) {
		this.S.b(bo, integer);
	}

	public final int dy() {
		return this.S.a(bp);
	}

	public final void q(int integer) {
		this.S.b(bp, integer);
	}

	private int o() {
		if (aoh.a(this)) {
			return 6 - (1 + aoh.b(this));
		} else {
			return this.a(aoi.d) ? 6 + (1 + this.b(aoi.d).c()) * 2 : 6;
		}
	}

	public void a(anf anf) {
		this.a(anf, false);
	}

	public void a(anf anf, boolean boolean2) {
		if (!this.ap || this.ar >= this.o() / 2 || this.ar < 0) {
			this.ar = -1;
			this.ap = true;
			this.aq = anf;
			if (this.l instanceof zd) {
				nr nr4 = new nr(this, anf == anf.MAIN_HAND ? 0 : 3);
				zb zb5 = ((zd)this.l).i();
				if (boolean2) {
					zb5.a(this, nr4);
				} else {
					zb5.b(this, nr4);
				}
			}
		}
	}

	@Override
	protected void ai() {
		this.a(anw.m, 4.0F);
	}

	protected void dz() {
		int integer2 = this.o();
		if (this.ap) {
			this.ar++;
			if (this.ar >= integer2) {
				this.ar = 0;
				this.ap = false;
			}
		} else {
			this.ar = 0;
		}

		this.az = (float)this.ar / (float)integer2;
	}

	@Nullable
	public apt a(aps aps) {
		return this.dA().a(aps);
	}

	public double b(aps aps) {
		return this.dA().c(aps);
	}

	public double c(aps aps) {
		return this.dA().d(aps);
	}

	public apu dA() {
		return this.br;
	}

	public apc dB() {
		return apc.a;
	}

	public bki dC() {
		return this.b(aor.MAINHAND);
	}

	public bki dD() {
		return this.b(aor.OFFHAND);
	}

	public boolean a(bke bke) {
		return this.a((Predicate<bke>)(bke2 -> bke2 == bke));
	}

	public boolean a(Predicate<bke> predicate) {
		return predicate.test(this.dC().b()) || predicate.test(this.dD().b());
	}

	public bki b(anf anf) {
		if (anf == anf.MAIN_HAND) {
			return this.b(aor.MAINHAND);
		} else if (anf == anf.OFF_HAND) {
			return this.b(aor.OFFHAND);
		} else {
			throw new IllegalArgumentException("Invalid hand " + anf);
		}
	}

	public void a(anf anf, bki bki) {
		if (anf == anf.MAIN_HAND) {
			this.a(aor.MAINHAND, bki);
		} else {
			if (anf != anf.OFF_HAND) {
				throw new IllegalArgumentException("Invalid hand " + anf);
			}

			this.a(aor.OFFHAND, bki);
		}
	}

	public boolean a(aor aor) {
		return !this.b(aor).a();
	}

	@Override
	public abstract Iterable<bki> bk();

	public abstract bki b(aor aor);

	@Override
	public abstract void a(aor aor, bki bki);

	public float dE() {
		Iterable<bki> iterable2 = this.bk();
		int integer3 = 0;
		int integer4 = 0;

		for (bki bki6 : iterable2) {
			if (!bki6.a()) {
				integer4++;
			}

			integer3++;
		}

		return integer3 > 0 ? (float)integer4 / (float)integer3 : 0.0F;
	}

	@Override
	public void g(boolean boolean1) {
		super.g(boolean1);
		apt apt3 = this.a(apx.d);
		if (apt3.a(b) != null) {
			apt3.d(d);
		}

		if (boolean1) {
			apt3.b(d);
		}
	}

	protected float dF() {
		return 1.0F;
	}

	protected float dG() {
		return this.x_() ? (this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.5F : (this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F;
	}

	protected boolean dH() {
		return this.dk();
	}

	@Override
	public void i(aom aom) {
		if (!this.el()) {
			super.i(aom);
		}
	}

	private void a(aom aom) {
		dem dem3;
		if (!aom.y && !this.l.d_(aom.cA()).b().a(acx.al)) {
			dem3 = aom.c(this);
		} else {
			dem3 = new dem(aom.cC(), aom.cD() + (double)aom.cy(), aom.cG());
		}

		this.a(dem3.b, dem3.c, dem3.d);
	}

	protected float dI() {
		return 0.42F * this.al();
	}

	protected void dJ() {
		float float2 = this.dI();
		if (this.a(aoi.h)) {
			float2 += 0.1F * (float)(this.b(aoi.h).c() + 1);
		}

		dem dem3 = this.cB();
		this.m(dem3.b, (double)float2, dem3.d);
		if (this.bw()) {
			float float4 = this.p * (float) (Math.PI / 180.0);
			this.e(this.cB().b((double)(-aec.a(float4) * 0.2F), 0.0, (double)(aec.b(float4) * 0.2F)));
		}

		this.ad = true;
	}

	protected void c(adf<cwz> adf) {
		this.e(this.cB().b(0.0, 0.04F, 0.0));
	}

	protected float dL() {
		return 0.8F;
	}

	public boolean a(cwz cwz) {
		return false;
	}

	public void f(dem dem) {
		if (this.dR() || this.cr()) {
			double double3 = 0.08;
			boolean boolean5 = this.cB().c <= 0.0;
			if (boolean5 && this.a(aoi.B)) {
				double3 = 0.01;
				this.C = 0.0F;
			}

			cxa cxa6 = this.l.b(this.cA());
			if (this.aA() && this.cS() && !this.a(cxa6.a())) {
				double double7 = this.cD();
				float float9 = this.bw() ? 0.9F : this.dL();
				float float10 = 0.02F;
				float float11 = (float)bny.e(this);
				if (float11 > 3.0F) {
					float11 = 3.0F;
				}

				if (!this.t) {
					float11 *= 0.5F;
				}

				if (float11 > 0.0F) {
					float9 += (0.54600006F - float9) * float11 / 3.0F;
					float10 += (this.dM() - float10) * float11 / 3.0F;
				}

				if (this.a(aoi.D)) {
					float9 = 0.96F;
				}

				this.a(float10, dem);
				this.a(apd.SELF, this.cB());
				dem dem12 = this.cB();
				if (this.u && this.c_()) {
					dem12 = new dem(dem12.b, 0.2, dem12.d);
				}

				this.e(dem12.d((double)float9, 0.8F, (double)float9));
				dem dem13 = this.a(double3, boolean5, this.cB());
				this.e(dem13);
				if (this.u && this.e(dem13.b, dem13.c + 0.6F - this.cD() + double7, dem13.d)) {
					this.m(dem13.b, 0.3F, dem13.d);
				}
			} else if (this.aN() && this.cS() && !this.a(cxa6.a())) {
				double double7x = this.cD();
				this.a(0.02F, dem);
				this.a(apd.SELF, this.cB());
				if (this.b(acz.b) <= this.cw()) {
					this.e(this.cB().d(0.5, 0.8F, 0.5));
					dem dem9 = this.a(double3, boolean5, this.cB());
					this.e(dem9);
				} else {
					this.e(this.cB().a(0.5));
				}

				if (!this.aw()) {
					this.e(this.cB().b(0.0, -double3 / 4.0, 0.0));
				}

				dem dem9 = this.cB();
				if (this.u && this.e(dem9.b, dem9.c + 0.6F - this.cD() + double7x, dem9.d)) {
					this.m(dem9.b, 0.3F, dem9.d);
				}
			} else if (this.ee()) {
				dem dem7 = this.cB();
				if (dem7.c > -0.5) {
					this.C = 1.0F;
				}

				dem dem8 = this.bd();
				float float9x = this.q * (float) (Math.PI / 180.0);
				double double10 = Math.sqrt(dem8.b * dem8.b + dem8.d * dem8.d);
				double double12 = Math.sqrt(b(dem7));
				double double14 = dem8.f();
				float float16 = aec.b(float9x);
				float16 = (float)((double)float16 * (double)float16 * Math.min(1.0, double14 / 0.4));
				dem7 = this.cB().b(0.0, double3 * (-1.0 + (double)float16 * 0.75), 0.0);
				if (dem7.c < 0.0 && double10 > 0.0) {
					double double17 = dem7.c * -0.1 * (double)float16;
					dem7 = dem7.b(dem8.b * double17 / double10, double17, dem8.d * double17 / double10);
				}

				if (float9x < 0.0F && double10 > 0.0) {
					double double17 = double12 * (double)(-aec.a(float9x)) * 0.04;
					dem7 = dem7.b(-dem8.b * double17 / double10, double17 * 3.2, -dem8.d * double17 / double10);
				}

				if (double10 > 0.0) {
					dem7 = dem7.b((dem8.b / double10 * double12 - dem7.b) * 0.1, 0.0, (dem8.d / double10 * double12 - dem7.d) * 0.1);
				}

				this.e(dem7.d(0.99F, 0.98F, 0.99F));
				this.a(apd.SELF, this.cB());
				if (this.u && !this.l.v) {
					double double17 = Math.sqrt(b(this.cB()));
					double double19 = double12 - double17;
					float float21 = (float)(double19 * 10.0 - 3.0);
					if (float21 > 0.0F) {
						this.a(this.o((int)float21), 1.0F, 1.0F);
						this.a(anw.l, float21);
					}
				}

				if (this.t && !this.l.v) {
					this.b(7, false);
				}
			} else {
				fu fu7 = this.an();
				float float8 = this.l.d_(fu7).b().j();
				float float9xx = this.t ? float8 * 0.91F : 0.91F;
				dem dem10 = this.a(dem, float8);
				double double11 = dem10.c;
				if (this.a(aoi.y)) {
					double11 += (0.05 * (double)(this.b(aoi.y).c() + 1) - dem10.c) * 0.2;
					this.C = 0.0F;
				} else if (this.l.v && !this.l.C(fu7)) {
					if (this.cD() > 0.0) {
						double11 = -0.1;
					} else {
						double11 = 0.0;
					}
				} else if (!this.aw()) {
					double11 -= double3;
				}

				this.m(dem10.b * (double)float9xx, double11 * 0.98F, dem10.d * (double)float9xx);
			}
		}

		this.a(this, this instanceof ayr);
	}

	public void a(aoy aoy, boolean boolean2) {
		aoy.aB = aoy.aC;
		double double4 = aoy.cC() - aoy.m;
		double double6 = boolean2 ? aoy.cD() - aoy.n : 0.0;
		double double8 = aoy.cG() - aoy.o;
		float float10 = aec.a(double4 * double4 + double6 * double6 + double8 * double8) * 4.0F;
		if (float10 > 1.0F) {
			float10 = 1.0F;
		}

		aoy.aC = aoy.aC + (float10 - aoy.aC) * 0.4F;
		aoy.aD = aoy.aD + aoy.aC;
	}

	public dem a(dem dem, float float2) {
		this.a(this.q(float2), dem);
		this.e(this.g(this.cB()));
		this.a(apd.SELF, this.cB());
		dem dem4 = this.cB();
		if ((this.u || this.aX) && this.c_()) {
			dem4 = new dem(dem4.b, 0.2, dem4.d);
		}

		return dem4;
	}

	public dem a(double double1, boolean boolean2, dem dem) {
		if (!this.aw() && !this.bw()) {
			double double6;
			if (boolean2 && Math.abs(dem.c - 0.005) >= 0.003 && Math.abs(dem.c - double1 / 16.0) < 0.003) {
				double6 = -0.003;
			} else {
				double6 = dem.c - double1 / 16.0;
			}

			return new dem(dem.b, double6, dem.d);
		} else {
			return dem;
		}
	}

	private dem g(dem dem) {
		if (this.c_()) {
			this.C = 0.0F;
			float float3 = 0.15F;
			double double4 = aec.a(dem.b, -0.15F, 0.15F);
			double double6 = aec.a(dem.d, -0.15F, 0.15F);
			double double8 = Math.max(dem.c, -0.15F);
			if (double8 < 0.0 && !this.dr().a(bvs.lQ) && this.ed() && this instanceof bec) {
				double8 = 0.0;
			}

			dem = new dem(double4, double8, double6);
		}

		return dem;
	}

	private float q(float float1) {
		return this.t ? this.dM() * (0.21600002F / (float1 * float1 * float1)) : this.aL;
	}

	public float dM() {
		return this.bB;
	}

	public void n(float float1) {
		this.bB = float1;
	}

	public boolean B(aom aom) {
		this.z(aom);
		return false;
	}

	@Override
	public void j() {
		super.j();
		this.u();
		this.x();
		if (!this.l.v) {
			int integer2 = this.dx();
			if (integer2 > 0) {
				if (this.as <= 0) {
					this.as = 20 * (30 - integer2);
				}

				this.as--;
				if (this.as <= 0) {
					this.p(integer2 - 1);
				}
			}

			int integer3 = this.dy();
			if (integer3 > 0) {
				if (this.at <= 0) {
					this.at = 20 * (30 - integer3);
				}

				this.at--;
				if (this.at <= 0) {
					this.q(integer3 - 1);
				}
			}

			this.q();
			if (this.K % 20 == 0) {
				this.du().g();
			}

			if (!this.am) {
				boolean boolean4 = this.a(aoi.x);
				if (this.i(6) != boolean4) {
					this.b(6, boolean4);
				}
			}

			if (this.el() && !this.z()) {
				this.em();
			}
		}

		this.k();
		double double2 = this.cC() - this.m;
		double double4 = this.cG() - this.o;
		float float6 = (float)(double2 * double2 + double4 * double4);
		float float7 = this.aH;
		float float8 = 0.0F;
		this.aQ = this.aR;
		float float9 = 0.0F;
		if (float6 > 0.0025000002F) {
			float9 = 1.0F;
			float8 = (float)Math.sqrt((double)float6) * 3.0F;
			float float10 = (float)aec.d(double4, double2) * (180.0F / (float)Math.PI) - 90.0F;
			float float11 = aec.e(aec.g(this.p) - float10);
			if (95.0F < float11 && float11 < 265.0F) {
				float7 = float10 - 180.0F;
			} else {
				float7 = float10;
			}
		}

		if (this.az > 0.0F) {
			float7 = this.p;
		}

		if (!this.t) {
			float9 = 0.0F;
		}

		this.aR = this.aR + (float9 - this.aR) * 0.3F;
		this.l.X().a("headTurn");
		float8 = this.f(float7, float8);
		this.l.X().c();
		this.l.X().a("rangeChecks");

		while (this.p - this.r < -180.0F) {
			this.r -= 360.0F;
		}

		while (this.p - this.r >= 180.0F) {
			this.r += 360.0F;
		}

		while (this.aH - this.aI < -180.0F) {
			this.aI -= 360.0F;
		}

		while (this.aH - this.aI >= 180.0F) {
			this.aI += 360.0F;
		}

		while (this.q - this.s < -180.0F) {
			this.s -= 360.0F;
		}

		while (this.q - this.s >= 180.0F) {
			this.s += 360.0F;
		}

		while (this.aJ - this.aK < -180.0F) {
			this.aK -= 360.0F;
		}

		while (this.aJ - this.aK >= 180.0F) {
			this.aK += 360.0F;
		}

		this.l.X().c();
		this.aS += float8;
		if (this.ee()) {
			this.bl++;
		} else {
			this.bl = 0;
		}

		if (this.el()) {
			this.q = 0.0F;
		}
	}

	private void q() {
		Map<aor, bki> map2 = this.r();
		if (map2 != null) {
			this.a(map2);
			if (!map2.isEmpty()) {
				this.b(map2);
			}
		}
	}

	@Nullable
	private Map<aor, bki> r() {
		Map<aor, bki> map2 = null;

		for (aor aor6 : aor.values()) {
			bki bki7;
			switch (aor6.a()) {
				case HAND:
					bki7 = this.e(aor6);
					break;
				case ARMOR:
					bki7 = this.d(aor6);
					break;
				default:
					continue;
			}

			bki bki8 = this.b(aor6);
			if (!bki.b(bki8, bki7)) {
				if (map2 == null) {
					map2 = Maps.newEnumMap(aor.class);
				}

				map2.put(aor6, bki8);
				if (!bki7.a()) {
					this.dA().a(bki7.a(aor6));
				}

				if (!bki8.a()) {
					this.dA().b(bki8.a(aor6));
				}
			}
		}

		return map2;
	}

	private void a(Map<aor, bki> map) {
		bki bki3 = (bki)map.get(aor.MAINHAND);
		bki bki4 = (bki)map.get(aor.OFFHAND);
		if (bki3 != null && bki4 != null && bki.b(bki3, this.e(aor.OFFHAND)) && bki.b(bki4, this.e(aor.MAINHAND))) {
			((zd)this.l).i().b(this, new on(this, (byte)55));
			map.remove(aor.MAINHAND);
			map.remove(aor.OFFHAND);
			this.c(aor.MAINHAND, bki3.i());
			this.c(aor.OFFHAND, bki4.i());
		}
	}

	private void b(Map<aor, bki> map) {
		List<Pair<aor, bki>> list3 = Lists.<Pair<aor, bki>>newArrayListWithCapacity(map.size());
		map.forEach((aor, bki) -> {
			bki bki5 = bki.i();
			list3.add(Pair.of(aor, bki5));
			switch (aor.a()) {
				case HAND:
					this.c(aor, bki5);
					break;
				case ARMOR:
					this.b(aor, bki5);
			}
		});
		((zd)this.l).i().b(this, new qc(this.V(), list3));
	}

	private bki d(aor aor) {
		return this.bv.get(aor.b());
	}

	private void b(aor aor, bki bki) {
		this.bv.set(aor.b(), bki);
	}

	private bki e(aor aor) {
		return this.bu.get(aor.b());
	}

	private void c(aor aor, bki bki) {
		this.bu.set(aor.b(), bki);
	}

	protected float f(float float1, float float2) {
		float float4 = aec.g(float1 - this.aH);
		this.aH += float4 * 0.3F;
		float float5 = aec.g(this.p - this.aH);
		boolean boolean6 = float5 < -90.0F || float5 >= 90.0F;
		if (float5 < -75.0F) {
			float5 = -75.0F;
		}

		if (float5 >= 75.0F) {
			float5 = 75.0F;
		}

		this.aH = this.p - float5;
		if (float5 * float5 > 2500.0F) {
			this.aH += float5 * 0.2F;
		}

		if (boolean6) {
			float2 *= -1.0F;
		}

		return float2;
	}

	public void k() {
		if (this.bC > 0) {
			this.bC--;
		}

		if (this.cr()) {
			this.bb = 0;
			this.c(this.cC(), this.cD(), this.cG());
		}

		if (this.bb > 0) {
			double double2 = this.cC() + (this.bc - this.cC()) / (double)this.bb;
			double double4 = this.cD() + (this.bd - this.cD()) / (double)this.bb;
			double double6 = this.cG() + (this.be - this.cG()) / (double)this.bb;
			double double8 = aec.g(this.bf - (double)this.p);
			this.p = (float)((double)this.p + double8 / (double)this.bb);
			this.q = (float)((double)this.q + (this.bg - (double)this.q) / (double)this.bb);
			this.bb--;
			this.d(double2, double4, double6);
			this.a(this.p, this.q);
		} else if (!this.dR()) {
			this.e(this.cB().a(0.98));
		}

		if (this.bi > 0) {
			this.aJ = (float)((double)this.aJ + aec.g(this.bh - (double)this.aJ) / (double)this.bi);
			this.bi--;
		}

		dem dem2 = this.cB();
		double double3 = dem2.b;
		double double5 = dem2.c;
		double double7 = dem2.d;
		if (Math.abs(dem2.b) < 0.003) {
			double3 = 0.0;
		}

		if (Math.abs(dem2.c) < 0.003) {
			double5 = 0.0;
		}

		if (Math.abs(dem2.d) < 0.003) {
			double7 = 0.0;
		}

		this.m(double3, double5, double7);
		this.l.X().a("ai");
		if (this.dH()) {
			this.aX = false;
			this.aY = 0.0F;
			this.ba = 0.0F;
		} else if (this.dR()) {
			this.l.X().a("newAi");
			this.dO();
			this.l.X().c();
		}

		this.l.X().c();
		this.l.X().a("jump");
		if (this.aX && this.cS()) {
			double double9;
			if (this.aN()) {
				double9 = this.b(acz.b);
			} else {
				double9 = this.b(acz.a);
			}

			boolean boolean11 = this.aA() && double9 > 0.0;
			double double12 = this.cw();
			if (!boolean11 || this.t && !(double9 > double12)) {
				if (!this.aN() || this.t && !(double9 > double12)) {
					if ((this.t || boolean11 && double9 <= double12) && this.bC == 0) {
						this.dJ();
						this.bC = 10;
					}
				} else {
					this.c(acz.b);
				}
			} else {
				this.c(acz.a);
			}
		} else {
			this.bC = 0;
		}

		this.l.X().c();
		this.l.X().a("travel");
		this.aY *= 0.98F;
		this.ba *= 0.98F;
		this.t();
		deg deg9 = this.cb();
		this.f(new dem((double)this.aY, (double)this.aZ, (double)this.ba));
		this.l.X().c();
		this.l.X().a("push");
		if (this.bm > 0) {
			this.bm--;
			this.a(deg9, this.cb());
		}

		this.dP();
		this.l.X().c();
		if (!this.l.v && this.dN() && this.aC()) {
			this.a(anw.h, 1.0F);
		}
	}

	public boolean dN() {
		return false;
	}

	private void t() {
		boolean boolean2 = this.i(7);
		if (boolean2 && !this.t && !this.bn() && !this.a(aoi.y)) {
			bki bki3 = this.b(aor.CHEST);
			if (bki3.b() == bkk.qn && bjk.d(bki3)) {
				boolean2 = true;
				if (!this.l.v && (this.bl + 1) % 20 == 0) {
					bki3.a(1, this, aoy -> aoy.c(aor.CHEST));
				}
			} else {
				boolean2 = false;
			}
		} else {
			boolean2 = false;
		}

		if (!this.l.v) {
			this.b(7, boolean2);
		}
	}

	protected void dO() {
	}

	protected void dP() {
		List<aom> list2 = this.l.a(this, this.cb(), aop.a(this));
		if (!list2.isEmpty()) {
			int integer3 = this.l.S().c(bpx.s);
			if (integer3 > 0 && list2.size() > integer3 - 1 && this.J.nextInt(4) == 0) {
				int integer4 = 0;

				for (int integer5 = 0; integer5 < list2.size(); integer5++) {
					if (!((aom)list2.get(integer5)).bn()) {
						integer4++;
					}
				}

				if (integer4 > integer3 - 1) {
					this.a(anw.g, 6.0F);
				}
			}

			for (int integer4 = 0; integer4 < list2.size(); integer4++) {
				aom aom5 = (aom)list2.get(integer4);
				this.C(aom5);
			}
		}
	}

	protected void a(deg deg1, deg deg2) {
		deg deg4 = deg1.b(deg2);
		List<aom> list5 = this.l.a(this, deg4);
		if (!list5.isEmpty()) {
			for (int integer6 = 0; integer6 < list5.size(); integer6++) {
				aom aom7 = (aom)list5.get(integer6);
				if (aom7 instanceof aoy) {
					this.h((aoy)aom7);
					this.bm = 0;
					this.e(this.cB().a(-0.2));
					break;
				}
			}
		} else if (this.u) {
			this.bm = 0;
		}

		if (!this.l.v && this.bm <= 0) {
			this.c(4, false);
		}
	}

	protected void C(aom aom) {
		aom.i(this);
	}

	protected void h(aoy aoy) {
	}

	public void r(int integer) {
		this.bm = integer;
		if (!this.l.v) {
			this.c(4, true);
		}
	}

	public boolean dQ() {
		return (this.S.a(an) & 4) != 0;
	}

	@Override
	public void l() {
		aom aom2 = this.cs();
		super.l();
		if (aom2 != null && aom2 != this.cs() && !this.l.v) {
			this.a(aom2);
		}
	}

	@Override
	public void aW() {
		super.aW();
		this.aQ = this.aR;
		this.aR = 0.0F;
		this.C = 0.0F;
	}

	public void o(boolean boolean1) {
		this.aX = boolean1;
	}

	public void a(bbg bbg) {
		bec bec3 = bbg.i() != null ? this.l.b(bbg.i()) : null;
		if (bec3 instanceof ze) {
			aa.O.a((ze)bec3, bbg.g(), this);
		}
	}

	public void a(aom aom, int integer) {
		if (!aom.y && !this.l.v && (aom instanceof bbg || aom instanceof beg || aom instanceof aos)) {
			((zd)this.l).i().b(aom, new qq(aom.V(), this.V(), integer));
		}
	}

	public boolean D(aom aom) {
		dem dem3 = new dem(this.cC(), this.cF(), this.cG());
		dem dem4 = new dem(aom.cC(), aom.cF(), aom.cG());
		return this.l.a(new bpj(dem3, dem4, bpj.a.COLLIDER, bpj.b.NONE, this)).c() == dej.a.MISS;
	}

	@Override
	public float h(float float1) {
		return float1 == 1.0F ? this.aJ : aec.g(float1, this.aK, this.aJ);
	}

	public boolean dR() {
		return !this.l.v;
	}

	@Override
	public boolean aQ() {
		return !this.y;
	}

	@Override
	public boolean aR() {
		return this.aU() && !this.c_();
	}

	@Override
	protected void aP() {
		this.w = this.J.nextDouble() >= this.b(apx.c);
	}

	@Override
	public float bG() {
		return this.aJ;
	}

	@Override
	public void k(float float1) {
		this.aJ = float1;
	}

	@Override
	public void l(float float1) {
		this.aH = float1;
	}

	public float dS() {
		return this.bD;
	}

	public void p(float float1) {
		if (float1 < 0.0F) {
			float1 = 0.0F;
		}

		this.bD = float1;
	}

	public void g() {
	}

	public void h() {
	}

	protected void dT() {
		this.bw = true;
	}

	public abstract aou dU();

	public boolean dV() {
		return (this.S.a(an) & 1) > 0;
	}

	public anf dW() {
		return (this.S.a(an) & 2) > 0 ? anf.OFF_HAND : anf.MAIN_HAND;
	}

	private void u() {
		if (this.dV()) {
			if (bki.d(this.b(this.dW()), this.bj)) {
				this.bj = this.b(this.dW());
				this.bj.b(this.l, this, this.dY());
				if (this.v()) {
					this.b(this.bj, 5);
				}

				if (--this.bk == 0 && !this.l.v && !this.bj.m()) {
					this.s();
				}
			} else {
				this.eb();
			}
		}
	}

	private boolean v() {
		int integer2 = this.dY();
		bgf bgf3 = this.bj.b().t();
		boolean boolean4 = bgf3 != null && bgf3.e();
		boolean4 |= integer2 <= this.bj.k() - 7;
		return boolean4 && integer2 % 4 == 0;
	}

	private void x() {
		this.bJ = this.bI;
		if (this.by()) {
			this.bI = Math.min(1.0F, this.bI + 0.09F);
		} else {
			this.bI = Math.max(0.0F, this.bI - 0.09F);
		}
	}

	protected void c(int integer, boolean boolean2) {
		int integer4 = this.S.a(an);
		if (boolean2) {
			integer4 |= integer;
		} else {
			integer4 &= ~integer;
		}

		this.S.b(an, (byte)integer4);
	}

	public void c(anf anf) {
		bki bki3 = this.b(anf);
		if (!bki3.a() && !this.dV()) {
			this.bj = bki3;
			this.bk = bki3.k();
			if (!this.l.v) {
				this.c(1, true);
				this.c(2, anf == anf.OFF_HAND);
			}
		}
	}

	@Override
	public void a(tq<?> tq) {
		super.a(tq);
		if (bq.equals(tq)) {
			if (this.l.v) {
				this.ej().ifPresent(this::a);
			}
		} else if (an.equals(tq) && this.l.v) {
			if (this.dV() && this.bj.a()) {
				this.bj = this.b(this.dW());
				if (!this.bj.a()) {
					this.bk = this.bj.k();
				}
			} else if (!this.dV() && !this.bj.a()) {
				this.bj = bki.b;
				this.bk = 0;
			}
		}
	}

	@Override
	public void a(dg.a a, dem dem) {
		super.a(a, dem);
		this.aK = this.aJ;
		this.aH = this.aJ;
		this.aI = this.aH;
	}

	protected void b(bki bki, int integer) {
		if (!bki.a() && this.dV()) {
			if (bki.l() == blu.DRINK) {
				this.a(this.c(bki), 0.5F, this.l.t.nextFloat() * 0.1F + 0.9F);
			}

			if (bki.l() == blu.EAT) {
				this.a(bki, integer);
				this.a(this.d(bki), 0.5F + 0.5F * (float)this.J.nextInt(2), (this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F);
			}
		}
	}

	private void a(bki bki, int integer) {
		for (int integer4 = 0; integer4 < integer; integer4++) {
			dem dem5 = new dem(((double)this.J.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
			dem5 = dem5.a(-this.q * (float) (Math.PI / 180.0));
			dem5 = dem5.b(-this.p * (float) (Math.PI / 180.0));
			double double6 = (double)(-this.J.nextFloat()) * 0.6 - 0.3;
			dem dem8 = new dem(((double)this.J.nextFloat() - 0.5) * 0.3, double6, 0.6);
			dem8 = dem8.a(-this.q * (float) (Math.PI / 180.0));
			dem8 = dem8.b(-this.p * (float) (Math.PI / 180.0));
			dem8 = dem8.b(this.cC(), this.cF(), this.cG());
			this.l.a(new he(hh.I, bki), dem8.b, dem8.c, dem8.d, dem5.b, dem5.c + 0.05, dem5.d);
		}
	}

	protected void s() {
		if (!this.bj.equals(this.b(this.dW()))) {
			this.ea();
		} else {
			if (!this.bj.a() && this.dV()) {
				this.b(this.bj, 16);
				this.a(this.dW(), this.bj.a(this.l, this));
				this.eb();
			}
		}
	}

	public bki dX() {
		return this.bj;
	}

	public int dY() {
		return this.bk;
	}

	public int dZ() {
		return this.dV() ? this.bj.k() - this.dY() : 0;
	}

	public void ea() {
		if (!this.bj.a()) {
			this.bj.a(this.l, this, this.dY());
			if (this.bj.m()) {
				this.u();
			}
		}

		this.eb();
	}

	public void eb() {
		if (!this.l.v) {
			this.c(1, false);
		}

		this.bj = bki.b;
		this.bk = 0;
	}

	public boolean ec() {
		if (this.dV() && !this.bj.a()) {
			bke bke2 = this.bj.b();
			return bke2.d_(this.bj) != blu.BLOCK ? false : bke2.e_(this.bj) - this.bk >= 5;
		} else {
			return false;
		}
	}

	public boolean ed() {
		return this.bq();
	}

	public boolean ee() {
		return this.i(7);
	}

	@Override
	public boolean by() {
		return super.by() || !this.ee() && this.ab() == apj.FALL_FLYING;
	}

	public boolean a(double double1, double double2, double double3, boolean boolean4) {
		double double9 = this.cC();
		double double11 = this.cD();
		double double13 = this.cG();
		double double15 = double2;
		boolean boolean17 = false;
		fu fu18 = new fu(double1, double2, double3);
		bqb bqb19 = this.l;
		if (bqb19.C(fu18)) {
			boolean boolean20 = false;

			while (!boolean20 && fu18.v() > 0) {
				fu fu21 = fu18.c();
				cfj cfj22 = bqb19.d_(fu21);
				if (cfj22.c().c()) {
					boolean20 = true;
				} else {
					double15--;
					fu18 = fu21;
				}
			}

			if (boolean20) {
				this.a(double1, double15, double3);
				if (bqb19.j(this) && !bqb19.d(this.cb())) {
					boolean17 = true;
				}
			}
		}

		if (!boolean17) {
			this.a(double9, double11, double13);
			return false;
		} else {
			if (boolean4) {
				bqb19.a(this, (byte)46);
			}

			if (this instanceof apg) {
				((apg)this).x().o();
			}

			return true;
		}
	}

	public boolean eg() {
		return true;
	}

	public boolean eh() {
		return true;
	}

	public boolean e(bki bki) {
		return false;
	}

	@Override
	public ni<?> O() {
		return new no(this);
	}

	@Override
	public aon a(apj apj) {
		return apj == apj.SLEEPING ? ao : super.a(apj).a(this.cR());
	}

	public ImmutableList<apj> ei() {
		return ImmutableList.of(apj.STANDING);
	}

	public deg f(apj apj) {
		aon aon3 = this.a(apj);
		return new deg((double)(-aon3.a / 2.0F), 0.0, (double)(-aon3.a / 2.0F), (double)(aon3.a / 2.0F), (double)aon3.b, (double)(aon3.a / 2.0F));
	}

	public Optional<fu> ej() {
		return this.S.a(bq);
	}

	public void e(fu fu) {
		this.S.b(bq, Optional.of(fu));
	}

	public void ek() {
		this.S.b(bq, Optional.empty());
	}

	public boolean el() {
		return this.ej().isPresent();
	}

	public void b(fu fu) {
		if (this.bn()) {
			this.l();
		}

		cfj cfj3 = this.l.d_(fu);
		if (cfj3.b() instanceof bvm) {
			this.l.a(fu, cfj3.a(bvm.b, Boolean.valueOf(true)), 3);
		}

		this.b(apj.SLEEPING);
		this.a(fu);
		this.e(fu);
		this.e(dem.a);
		this.ad = true;
	}

	private void a(fu fu) {
		this.d((double)fu.u() + 0.5, (double)fu.v() + 0.6875, (double)fu.w() + 0.5);
	}

	private boolean z() {
		return (Boolean)this.ej().map(fu -> this.l.d_(fu).b() instanceof bvm).orElse(false);
	}

	public void em() {
		this.ej().filter(this.l::C).ifPresent(fu -> {
			cfj cfj3 = this.l.d_(fu);
			if (cfj3.b() instanceof bvm) {
				this.l.a(fu, cfj3.a(bvm.b, Boolean.valueOf(false)), 3);
				dem dem4 = (dem)bvm.a(this.U(), this.l, fu, 0).orElseGet(() -> {
					fu fu2 = fu.b();
					return new dem((double)fu2.u() + 0.5, (double)fu2.v() + 0.1, (double)fu2.w() + 0.5);
				});
				this.d(dem4.b, dem4.c, dem4.d);
			}
		});
		dem dem2 = this.cz();
		this.b(apj.STANDING);
		this.d(dem2.b, dem2.c, dem2.d);
		this.ek();
	}

	@Override
	public boolean aV() {
		return !this.el() && super.aV();
	}

	@Override
	protected final float a(apj apj, aon aon) {
		return apj == apj.SLEEPING ? 0.2F : this.b(apj, aon);
	}

	protected float b(apj apj, aon aon) {
		return super.a(apj, aon);
	}

	public bki f(bki bki) {
		return bki.b;
	}

	public bki a(bqb bqb, bki bki) {
		if (bki.F()) {
			bqb.a(null, this.cC(), this.cD(), this.cG(), this.d(bki), acm.NEUTRAL, 1.0F, 1.0F + (bqb.t.nextFloat() - bqb.t.nextFloat()) * 0.4F);
			this.a(bki, bqb, this);
			if (!(this instanceof bec) || !((bec)this).bJ.d) {
				bki.g(1);
			}
		}

		return bki;
	}

	private void a(bki bki, bqb bqb, aoy aoy) {
		bke bke5 = bki.b();
		if (bke5.s()) {
			for (Pair<aog, Float> pair8 : bke5.t().f()) {
				if (!bqb.v && pair8.getFirst() != null && bqb.t.nextFloat() < pair8.getSecond()) {
					aoy.c(new aog(pair8.getFirst()));
				}
			}
		}
	}

	private static byte f(aor aor) {
		switch (aor) {
			case MAINHAND:
				return 47;
			case OFFHAND:
				return 48;
			case HEAD:
				return 49;
			case CHEST:
				return 50;
			case FEET:
				return 52;
			case LEGS:
				return 51;
			default:
				return 47;
		}
	}

	public void c(aor aor) {
		this.l.a(this, f(aor));
	}

	public void d(anf anf) {
		this.c(anf == anf.MAIN_HAND ? aor.MAINHAND : aor.OFFHAND);
	}
}
