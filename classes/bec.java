import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public abstract class bec extends aoy {
	public static final aon bo = aon.b(0.6F, 1.8F);
	private static final Map<apj, aon> b = ImmutableMap.<apj, aon>builder()
		.put(apj.STANDING, bo)
		.put(apj.SLEEPING, ao)
		.put(apj.FALL_FLYING, aon.b(0.6F, 0.6F))
		.put(apj.SWIMMING, aon.b(0.6F, 0.6F))
		.put(apj.SPIN_ATTACK, aon.b(0.6F, 0.6F))
		.put(apj.CROUCHING, aon.b(0.6F, 1.5F))
		.put(apj.DYING, aon.c(0.2F, 0.2F))
		.build();
	private static final tq<Float> c = tt.a(bec.class, ts.c);
	private static final tq<Integer> d = tt.a(bec.class, ts.b);
	protected static final tq<Byte> bp = tt.a(bec.class, ts.a);
	protected static final tq<Byte> bq = tt.a(bec.class, ts.a);
	protected static final tq<le> br = tt.a(bec.class, ts.p);
	protected static final tq<le> bs = tt.a(bec.class, ts.p);
	private long e;
	public final beb bt = new beb(this);
	protected bho bu = new bho();
	public final bhf bv;
	public bgi bw;
	protected bge bx = new bge();
	protected int by;
	public float bz;
	public float bA;
	public int bB;
	public double bC;
	public double bD;
	public double bE;
	public double bF;
	public double bG;
	public double bH;
	private int f;
	protected boolean bI;
	public final bdz bJ = new bdz();
	public int bK;
	public int bL;
	public float bM;
	protected int bN;
	protected final float bO = 0.02F;
	private int g;
	private final GameProfile bQ;
	private bki bS = bki.b;
	private final bkf bT = this.i();
	@Nullable
	public beo bP;

	public bec(bqb bqb, fu fu, GameProfile gameProfile) {
		super(aoq.bb, bqb);
		this.a_(a(gameProfile));
		this.bQ = gameProfile;
		this.bv = new bhf(this.bt, !bqb.v, this);
		this.bw = this.bv;
		this.b((double)fu.u() + 0.5, (double)(fu.v() + 1), (double)fu.w() + 0.5, 0.0F, 0.0F);
		this.aU = 180.0F;
	}

	public boolean a(bqb bqb, fu fu, bpy bpy) {
		if (!bpy.d()) {
			return false;
		} else if (bpy == bpy.SPECTATOR) {
			return true;
		} else if (this.eJ()) {
			return false;
		} else {
			bki bki5 = this.dC();
			return bki5.a() || !bki5.a(bqb.p(), new cfn(bqb, fu, false));
		}
	}

	public static apw.a eo() {
		return aoy.cK().a(apx.f, 1.0).a(apx.d, 0.1F).a(apx.h).a(apx.k);
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(c, 0.0F);
		this.S.a(d, 0);
		this.S.a(bp, (byte)0);
		this.S.a(bq, (byte)1);
		this.S.a(br, new le());
		this.S.a(bs, new le());
	}

	@Override
	public void j() {
		this.H = this.a_();
		if (this.a_()) {
			this.t = false;
		}

		if (this.bB > 0) {
			this.bB--;
		}

		if (this.el()) {
			this.f++;
			if (this.f > 100) {
				this.f = 100;
			}

			if (!this.l.v && this.l.J()) {
				this.a(false, true);
			}
		} else if (this.f > 0) {
			this.f++;
			if (this.f >= 110) {
				this.f = 0;
			}
		}

		this.es();
		super.j();
		if (!this.l.v && this.bw != null && !this.bw.a(this)) {
			this.m();
			this.bw = this.bv;
		}

		this.p();
		if (!this.l.v) {
			this.bx.a(this);
			this.a(acu.k);
			if (this.aU()) {
				this.a(acu.l);
			}

			if (this.bt()) {
				this.a(acu.n);
			}

			if (!this.el()) {
				this.a(acu.m);
			}
		}

		int integer2 = 29999999;
		double double3 = aec.a(this.cC(), -2.9999999E7, 2.9999999E7);
		double double5 = aec.a(this.cG(), -2.9999999E7, 2.9999999E7);
		if (double3 != this.cC() || double5 != this.cG()) {
			this.d(double3, this.cD(), double5);
		}

		this.aA++;
		bki bki7 = this.dC();
		if (!bki.b(this.bS, bki7)) {
			if (!bki.d(this.bS, bki7)) {
				this.eS();
			}

			this.bS = bki7.i();
		}

		this.o();
		this.bT.a();
		this.et();
	}

	public boolean ep() {
		return this.bq();
	}

	protected boolean eq() {
		return this.bq();
	}

	protected boolean er() {
		return this.bq();
	}

	protected boolean es() {
		this.bI = this.a(acz.a);
		return this.bI;
	}

	private void o() {
		bki bki2 = this.b(aor.HEAD);
		if (bki2.b() == bkk.jY && !this.a(acz.a)) {
			this.c(new aog(aoi.m, 200, 0, false, false, true));
		}
	}

	protected bkf i() {
		return new bkf();
	}

	private void p() {
		this.bC = this.bF;
		this.bD = this.bG;
		this.bE = this.bH;
		double double2 = this.cC() - this.bF;
		double double4 = this.cD() - this.bG;
		double double6 = this.cG() - this.bH;
		double double8 = 10.0;
		if (double2 > 10.0) {
			this.bF = this.cC();
			this.bC = this.bF;
		}

		if (double6 > 10.0) {
			this.bH = this.cG();
			this.bE = this.bH;
		}

		if (double4 > 10.0) {
			this.bG = this.cD();
			this.bD = this.bG;
		}

		if (double2 < -10.0) {
			this.bF = this.cC();
			this.bC = this.bF;
		}

		if (double6 < -10.0) {
			this.bH = this.cG();
			this.bE = this.bH;
		}

		if (double4 < -10.0) {
			this.bG = this.cD();
			this.bD = this.bG;
		}

		this.bF += double2 * 0.25;
		this.bH += double6 * 0.25;
		this.bG += double4 * 0.25;
	}

	protected void et() {
		if (this.c(apj.SWIMMING)) {
			apj apj2;
			if (this.ee()) {
				apj2 = apj.FALL_FLYING;
			} else if (this.el()) {
				apj2 = apj.SLEEPING;
			} else if (this.bx()) {
				apj2 = apj.SWIMMING;
			} else if (this.dQ()) {
				apj2 = apj.SPIN_ATTACK;
			} else if (this.bq() && !this.bJ.b) {
				apj2 = apj.CROUCHING;
			} else {
				apj2 = apj.STANDING;
			}

			apj apj3;
			if (this.a_() || this.bn() || this.c(apj2)) {
				apj3 = apj2;
			} else if (this.c(apj.CROUCHING)) {
				apj3 = apj.CROUCHING;
			} else {
				apj3 = apj.SWIMMING;
			}

			this.b(apj3);
		}
	}

	@Override
	public int ae() {
		return this.bJ.a ? 1 : 80;
	}

	@Override
	protected ack aq() {
		return acl.lz;
	}

	@Override
	protected ack ar() {
		return acl.lx;
	}

	@Override
	protected ack as() {
		return acl.ly;
	}

	@Override
	public int bh() {
		return 10;
	}

	@Override
	public void a(ack ack, float float2, float float3) {
		this.l.a(this, this.cC(), this.cD(), this.cG(), ack, this.ct(), float2, float3);
	}

	public void a(ack ack, acm acm, float float3, float float4) {
	}

	@Override
	public acm ct() {
		return acm.PLAYERS;
	}

	@Override
	protected int cu() {
		return 20;
	}

	protected void m() {
		this.bw = this.bv;
	}

	@Override
	public void aW() {
		if (this.eq() && this.bn()) {
			this.l();
			this.f(false);
		} else {
			double double2 = this.cC();
			double double4 = this.cD();
			double double6 = this.cG();
			super.aW();
			this.bz = this.bA;
			this.bA = 0.0F;
			this.p(this.cC() - double2, this.cD() - double4, this.cG() - double6);
		}
	}

	@Override
	protected void dO() {
		super.dO();
		this.dz();
		this.aJ = this.p;
	}

	@Override
	public void k() {
		if (this.by > 0) {
			this.by--;
		}

		if (this.l.ac() == and.PEACEFUL && this.l.S().b(bpx.i)) {
			if (this.dj() < this.dw() && this.K % 20 == 0) {
				this.b(1.0F);
			}

			if (this.bx.c() && this.K % 10 == 0) {
				this.bx.a(this.bx.a() + 1);
			}
		}

		this.bt.j();
		this.bz = this.bA;
		super.k();
		this.aL = 0.02F;
		if (this.bw()) {
			this.aL = (float)((double)this.aL + 0.005999999865889549);
		}

		this.n((float)this.b(apx.d));
		float float2;
		if (this.t && !this.dk() && !this.bx()) {
			float2 = Math.min(0.1F, aec.a(b(this.cB())));
		} else {
			float2 = 0.0F;
		}

		this.bA = this.bA + (float2 - this.bA) * 0.4F;
		if (this.dj() > 0.0F && !this.a_()) {
			deg deg3;
			if (this.bn() && !this.cs().y) {
				deg3 = this.cb().b(this.cs().cb()).c(1.0, 0.0, 1.0);
			} else {
				deg3 = this.cb().c(1.0, 0.5, 1.0);
			}

			List<aom> list4 = this.l.a(this, deg3);

			for (int integer5 = 0; integer5 < list4.size(); integer5++) {
				aom aom6 = (aom)list4.get(integer5);
				if (!aom6.y) {
					this.c(aom6);
				}
			}
		}

		this.j(this.eP());
		this.j(this.eQ());
		if (!this.l.v && (this.C > 0.5F || this.aA()) || this.bJ.b || this.el()) {
			this.eL();
		}
	}

	private void j(@Nullable le le) {
		if (le != null && (!le.e("Silent") || !le.q("Silent")) && this.l.t.nextInt(200) == 0) {
			String string3 = le.l("id");
			aoq.a(string3).filter(aoq -> aoq == aoq.af).ifPresent(aoq -> {
				if (!ayx.a(this.l, this)) {
					this.l.a(null, this.cC(), this.cD(), this.cG(), ayx.a(this.l, this.l.t), this.ct(), 1.0F, ayx.a(this.l.t));
				}
			});
		}
	}

	private void c(aom aom) {
		aom.a_(this);
	}

	public int eu() {
		return this.S.a(d);
	}

	public void s(int integer) {
		this.S.b(d, integer);
	}

	public void t(int integer) {
		int integer3 = this.eu();
		this.S.b(d, integer3 + integer);
	}

	@Override
	public void a(anw anw) {
		super.a(anw);
		this.ac();
		if (!this.a_()) {
			this.d(anw);
		}

		if (anw != null) {
			this.m(
				(double)(-aec.b((this.aw + this.p) * (float) (Math.PI / 180.0)) * 0.1F), 0.1F, (double)(-aec.a((this.aw + this.p) * (float) (Math.PI / 180.0)) * 0.1F)
			);
		} else {
			this.m(0.0, 0.1, 0.0);
		}

		this.a(acu.M);
		this.a(acu.i.b(acu.l));
		this.a(acu.i.b(acu.m));
		this.ah();
		this.b(0, false);
	}

	@Override
	protected void dm() {
		super.dm();
		if (!this.l.S().b(bpx.c)) {
			this.ev();
			this.bt.k();
		}
	}

	protected void ev() {
		for (int integer2 = 0; integer2 < this.bt.ab_(); integer2++) {
			bki bki3 = this.bt.a(integer2);
			if (!bki3.a() && bny.e(bki3)) {
				this.bt.b(integer2);
			}
		}
	}

	@Override
	protected ack e(anw anw) {
		if (anw == anw.c) {
			return acl.lt;
		} else if (anw == anw.h) {
			return acl.ls;
		} else {
			return anw == anw.u ? acl.lu : acl.lr;
		}
	}

	@Override
	protected ack dp() {
		return acl.lq;
	}

	public boolean a(boolean boolean1) {
		return this.a(this.bt.a(this.bt.d, boolean1 && !this.bt.f().a() ? this.bt.f().E() : 1), false, true) != null;
	}

	@Nullable
	public bbg a(bki bki, boolean boolean2) {
		return this.a(bki, false, boolean2);
	}

	@Nullable
	public bbg a(bki bki, boolean boolean2, boolean boolean3) {
		if (bki.a()) {
			return null;
		} else {
			if (this.l.v) {
				this.a(anf.MAIN_HAND);
			}

			double double5 = this.cF() - 0.3F;
			bbg bbg7 = new bbg(this.l, this.cC(), double5, this.cG(), bki);
			bbg7.a(40);
			if (boolean3) {
				bbg7.c(this.bR());
			}

			if (boolean2) {
				float float8 = this.J.nextFloat() * 0.5F;
				float float9 = this.J.nextFloat() * (float) (Math.PI * 2);
				bbg7.m((double)(-aec.a(float9) * float8), 0.2F, (double)(aec.b(float9) * float8));
			} else {
				float float8 = 0.3F;
				float float9 = aec.a(this.q * (float) (Math.PI / 180.0));
				float float10 = aec.b(this.q * (float) (Math.PI / 180.0));
				float float11 = aec.a(this.p * (float) (Math.PI / 180.0));
				float float12 = aec.b(this.p * (float) (Math.PI / 180.0));
				float float13 = this.J.nextFloat() * (float) (Math.PI * 2);
				float float14 = 0.02F * this.J.nextFloat();
				bbg7.m(
					(double)(-float11 * float10 * 0.3F) + Math.cos((double)float13) * (double)float14,
					(double)(-float9 * 0.3F + 0.1F + (this.J.nextFloat() - this.J.nextFloat()) * 0.1F),
					(double)(float12 * float10 * 0.3F) + Math.sin((double)float13) * (double)float14
				);
			}

			return bbg7;
		}
	}

	public float c(cfj cfj) {
		float float3 = this.bt.a(cfj);
		if (float3 > 1.0F) {
			int integer4 = bny.f(this);
			bki bki5 = this.dC();
			if (integer4 > 0 && !bki5.a()) {
				float3 += (float)(integer4 * integer4 + 1);
			}
		}

		if (aoh.a(this)) {
			float3 *= 1.0F + (float)(aoh.b(this) + 1) * 0.2F;
		}

		if (this.a(aoi.d)) {
			float float4;
			switch (this.b(aoi.d).c()) {
				case 0:
					float4 = 0.3F;
					break;
				case 1:
					float4 = 0.09F;
					break;
				case 2:
					float4 = 0.0027F;
					break;
				case 3:
				default:
					float4 = 8.1E-4F;
			}

			float3 *= float4;
		}

		if (this.a(acz.a) && !bny.h(this)) {
			float3 /= 5.0F;
		}

		if (!this.t) {
			float3 /= 5.0F;
		}

		return float3;
	}

	public boolean d(cfj cfj) {
		return !cfj.q() || this.bt.f().b(cfj);
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.a_(a(this.bQ));
		lk lk3 = le.d("Inventory", 10);
		this.bt.b(lk3);
		this.bt.d = le.h("SelectedItemSlot");
		this.f = le.g("SleepTimer");
		this.bM = le.j("XpP");
		this.bK = le.h("XpLevel");
		this.bL = le.h("XpTotal");
		this.bN = le.h("XpSeed");
		if (this.bN == 0) {
			this.bN = this.J.nextInt();
		}

		this.s(le.h("Score"));
		this.bx.a(le);
		this.bJ.b(le);
		this.a(apx.d).a((double)this.bJ.b());
		if (le.c("EnderItems", 9)) {
			this.bu.a(le.d("EnderItems", 10));
		}

		if (le.c("ShoulderEntityLeft", 10)) {
			this.h(le.p("ShoulderEntityLeft"));
		}

		if (le.c("ShoulderEntityRight", 10)) {
			this.i(le.p("ShoulderEntityRight"));
		}
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.b("DataVersion", u.a().getWorldVersion());
		le.a("Inventory", this.bt.a(new lk()));
		le.b("SelectedItemSlot", this.bt.d);
		le.a("SleepTimer", (short)this.f);
		le.a("XpP", this.bM);
		le.b("XpLevel", this.bK);
		le.b("XpTotal", this.bL);
		le.b("XpSeed", this.bN);
		le.b("Score", this.eu());
		this.bx.b(le);
		this.bJ.a(le);
		le.a("EnderItems", this.bu.g());
		if (!this.eP().isEmpty()) {
			le.a("ShoulderEntityLeft", this.eP());
		}

		if (!this.eQ().isEmpty()) {
			le.a("ShoulderEntityRight", this.eQ());
		}
	}

	@Override
	public boolean b(anw anw) {
		if (super.b(anw)) {
			return true;
		} else if (anw == anw.h) {
			return !this.l.S().b(bpx.A);
		} else if (anw == anw.k) {
			return !this.l.S().b(bpx.B);
		} else {
			return anw.p() ? !this.l.S().b(bpx.C) : false;
		}
	}

	@Override
	public boolean a(anw anw, float float2) {
		if (this.b(anw)) {
			return false;
		} else if (this.bJ.a && !anw.h()) {
			return false;
		} else {
			this.aP = 0;
			if (this.dk()) {
				return false;
			} else {
				this.eL();
				if (anw.s()) {
					if (this.l.ac() == and.PEACEFUL) {
						float2 = 0.0F;
					}

					if (this.l.ac() == and.EASY) {
						float2 = Math.min(float2 / 2.0F + 1.0F, float2);
					}

					if (this.l.ac() == and.HARD) {
						float2 = float2 * 3.0F / 2.0F;
					}
				}

				return float2 == 0.0F ? false : super.a(anw, float2);
			}
		}
	}

	@Override
	protected void e(aoy aoy) {
		super.e(aoy);
		if (aoy.dC().b() instanceof bii) {
			this.p(true);
		}
	}

	public boolean a(bec bec) {
		dfo dfo3 = this.bC();
		dfo dfo4 = bec.bC();
		if (dfo3 == null) {
			return true;
		} else {
			return !dfo3.a(dfo4) ? true : dfo3.h();
		}
	}

	@Override
	protected void b(anw anw, float float2) {
		this.bt.a(anw, float2);
	}

	@Override
	protected void m(float float1) {
		if (this.bj.b() == bkk.qm) {
			if (!this.l.v) {
				this.b(acu.c.b(this.bj.b()));
			}

			if (float1 >= 3.0F) {
				int integer3 = 1 + aec.d(float1);
				anf anf4 = this.dW();
				this.bj.a(integer3, this, bec -> bec.d(anf4));
				if (this.bj.a()) {
					if (anf4 == anf.MAIN_HAND) {
						this.a(aor.MAINHAND, bki.b);
					} else {
						this.a(aor.OFFHAND, bki.b);
					}

					this.bj = bki.b;
					this.a(acl.mO, 0.8F, 0.8F + this.l.t.nextFloat() * 0.4F);
				}
			}
		}
	}

	@Override
	protected void e(anw anw, float float2) {
		if (!this.b(anw)) {
			float2 = this.c(anw, float2);
			float2 = this.d(anw, float2);
			float var8 = Math.max(float2 - this.dS(), 0.0F);
			this.p(this.dS() - (float2 - var8));
			float float5 = float2 - var8;
			if (float5 > 0.0F && float5 < 3.4028235E37F) {
				this.a(acu.K, Math.round(float5 * 10.0F));
			}

			if (var8 != 0.0F) {
				this.q(anw.g());
				float float6 = this.dj();
				this.c(this.dj() - var8);
				this.du().a(anw, float6, var8);
				if (var8 < 3.4028235E37F) {
					this.a(acu.I, Math.round(var8 * 10.0F));
				}
			}
		}
	}

	@Override
	protected boolean cO() {
		return !this.bJ.b && super.cO();
	}

	public void a(ceh ceh) {
	}

	public void a(bpc bpc) {
	}

	public void a(cdq cdq) {
	}

	public void a(cel cel) {
	}

	public void a(ceb ceb) {
	}

	public void a(azm azm, amz amz) {
	}

	public OptionalInt a(@Nullable anj anj) {
		return OptionalInt.empty();
	}

	public void a(int integer1, bpa bpa, int integer3, int integer4, boolean boolean5, boolean boolean6) {
	}

	public void a(bki bki, anf anf) {
	}

	public ang a(aom aom, anf anf) {
		if (this.a_()) {
			if (aom instanceof anj) {
				this.a((anj)aom);
			}

			return ang.PASS;
		} else {
			bki bki4 = this.b(anf);
			bki bki5 = bki4.i();
			ang ang6 = aom.a(this, anf);
			if (ang6.a()) {
				if (this.bJ.d && bki4 == this.b(anf) && bki4.E() < bki5.E()) {
					bki4.e(bki5.E());
				}

				return ang6;
			} else {
				if (!bki4.a() && aom instanceof aoy) {
					if (this.bJ.d) {
						bki4 = bki5;
					}

					ang ang7 = bki4.a(this, (aoy)aom, anf);
					if (ang7.a()) {
						if (bki4.a() && !this.bJ.d) {
							this.a(anf, bki.b);
						}

						return ang7;
					}
				}

				return ang.PASS;
			}
		}
	}

	@Override
	public double aX() {
		return -0.35;
	}

	@Override
	public void bb() {
		super.bb();
		this.j = 0;
	}

	@Override
	protected boolean dH() {
		return super.dH() || this.el();
	}

	@Override
	public boolean cS() {
		return !this.bJ.b;
	}

	@Override
	protected dem a(dem dem, apd apd) {
		if ((apd == apd.SELF || apd == apd.PLAYER) && this.t && this.er()) {
			double double4 = dem.b;
			double double6 = dem.d;
			double double8 = 0.05;

			while (double4 != 0.0 && this.l.a_(this, this.cb().d(double4, (double)(-this.G), 0.0))) {
				if (double4 < 0.05 && double4 >= -0.05) {
					double4 = 0.0;
				} else if (double4 > 0.0) {
					double4 -= 0.05;
				} else {
					double4 += 0.05;
				}
			}

			while (double6 != 0.0 && this.l.a_(this, this.cb().d(0.0, (double)(-this.G), double6))) {
				if (double6 < 0.05 && double6 >= -0.05) {
					double6 = 0.0;
				} else if (double6 > 0.0) {
					double6 -= 0.05;
				} else {
					double6 += 0.05;
				}
			}

			while (double4 != 0.0 && double6 != 0.0 && this.l.a_(this, this.cb().d(double4, (double)(-this.G), double6))) {
				if (double4 < 0.05 && double4 >= -0.05) {
					double4 = 0.0;
				} else if (double4 > 0.0) {
					double4 -= 0.05;
				} else {
					double4 += 0.05;
				}

				if (double6 < 0.05 && double6 >= -0.05) {
					double6 = 0.0;
				} else if (double6 > 0.0) {
					double6 -= 0.05;
				} else {
					double6 += 0.05;
				}
			}

			dem = new dem(double4, dem.c, double6);
		}

		return dem;
	}

	public void f(aom aom) {
		if (aom.bH()) {
			if (!aom.t(this)) {
				float float3 = (float)this.b(apx.f);
				float float4;
				if (aom instanceof aoy) {
					float4 = bny.a(this.dC(), ((aoy)aom).dB());
				} else {
					float4 = bny.a(this.dC(), apc.a);
				}

				float float5 = this.r(0.5F);
				float3 *= 0.2F + float5 * float5 * 0.8F;
				float4 *= float5;
				this.eS();
				if (float3 > 0.0F || float4 > 0.0F) {
					boolean boolean6 = float5 > 0.9F;
					boolean boolean7 = false;
					int integer8 = 0;
					integer8 += bny.b(this);
					if (this.bw() && boolean6) {
						this.l.a(null, this.cC(), this.cD(), this.cG(), acl.li, this.ct(), 1.0F, 1.0F);
						integer8++;
						boolean7 = true;
					}

					boolean boolean9 = boolean6 && this.C > 0.0F && !this.t && !this.c_() && !this.aA() && !this.a(aoi.o) && !this.bn() && aom instanceof aoy;
					boolean9 = boolean9 && !this.bw();
					if (boolean9) {
						float3 *= 1.5F;
					}

					float3 += float4;
					boolean boolean10 = false;
					double double11 = (double)(this.A - this.z);
					if (boolean6 && !boolean9 && !boolean7 && this.t && double11 < (double)this.dM()) {
						bki bki13 = this.b(anf.MAIN_HAND);
						if (bki13.b() instanceof blm) {
							boolean10 = true;
						}
					}

					float float13 = 0.0F;
					boolean boolean14 = false;
					int integer15 = bny.c(this);
					if (aom instanceof aoy) {
						float13 = ((aoy)aom).dj();
						if (integer15 > 0 && !aom.bm()) {
							boolean14 = true;
							aom.f(1);
						}
					}

					dem dem16 = aom.cB();
					boolean boolean17 = aom.a(anw.a(this), float3);
					if (boolean17) {
						if (integer8 > 0) {
							if (aom instanceof aoy) {
								((aoy)aom).a((float)integer8 * 0.5F, (double)aec.a(this.p * (float) (Math.PI / 180.0)), (double)(-aec.b(this.p * (float) (Math.PI / 180.0))));
							} else {
								aom.h(
									(double)(-aec.a(this.p * (float) (Math.PI / 180.0)) * (float)integer8 * 0.5F),
									0.1,
									(double)(aec.b(this.p * (float) (Math.PI / 180.0)) * (float)integer8 * 0.5F)
								);
							}

							this.e(this.cB().d(0.6, 1.0, 0.6));
							this.g(false);
						}

						if (boolean10) {
							float float18 = 1.0F + bny.a(this) * float3;

							for (aoy aoy21 : this.l.a(aoy.class, aom.cb().c(1.0, 0.25, 1.0))) {
								if (aoy21 != this && aoy21 != aom && !this.r(aoy21) && (!(aoy21 instanceof bay) || !((bay)aoy21).q()) && this.h((aom)aoy21) < 9.0) {
									aoy21.a(0.4F, (double)aec.a(this.p * (float) (Math.PI / 180.0)), (double)(-aec.b(this.p * (float) (Math.PI / 180.0))));
									aoy21.a(anw.a(this), float18);
								}
							}

							this.l.a(null, this.cC(), this.cD(), this.cG(), acl.ll, this.ct(), 1.0F, 1.0F);
							this.ew();
						}

						if (aom instanceof ze && aom.w) {
							((ze)aom).b.a(new qb(aom));
							aom.w = false;
							aom.e(dem16);
						}

						if (boolean9) {
							this.l.a(null, this.cC(), this.cD(), this.cG(), acl.lh, this.ct(), 1.0F, 1.0F);
							this.a(aom);
						}

						if (!boolean9 && !boolean10) {
							if (boolean6) {
								this.l.a(null, this.cC(), this.cD(), this.cG(), acl.lk, this.ct(), 1.0F, 1.0F);
							} else {
								this.l.a(null, this.cC(), this.cD(), this.cG(), acl.lm, this.ct(), 1.0F, 1.0F);
							}
						}

						if (float4 > 0.0F) {
							this.b(aom);
						}

						this.z(aom);
						if (aom instanceof aoy) {
							bny.a((aoy)aom, this);
						}

						bny.b(this, aom);
						bki bki18 = this.dC();
						aom aom19 = aom;
						if (aom instanceof baa) {
							aom19 = ((baa)aom).b;
						}

						if (!this.l.v && !bki18.a() && aom19 instanceof aoy) {
							bki18.a((aoy)aom19, this);
							if (bki18.a()) {
								this.a(anf.MAIN_HAND, bki.b);
							}
						}

						if (aom instanceof aoy) {
							float float20 = float13 - ((aoy)aom).dj();
							this.a(acu.F, Math.round(float20 * 10.0F));
							if (integer15 > 0) {
								aom.f(integer15 * 4);
							}

							if (this.l instanceof zd && float20 > 2.0F) {
								int integer21 = (int)((double)float20 * 0.5);
								((zd)this.l).a(hh.h, aom.cC(), aom.e(0.5), aom.cG(), integer21, 0.1, 0.0, 0.1, 0.2);
							}
						}

						this.q(0.1F);
					} else {
						this.l.a(null, this.cC(), this.cD(), this.cG(), acl.lj, this.ct(), 1.0F, 1.0F);
						if (boolean14) {
							aom.ah();
						}
					}
				}
			}
		}
	}

	@Override
	protected void h(aoy aoy) {
		this.f(aoy);
	}

	public void p(boolean boolean1) {
		float float3 = 0.25F + (float)bny.f(this) * 0.05F;
		if (boolean1) {
			float3 += 0.75F;
		}

		if (this.J.nextFloat() < float3) {
			this.eT().a(bkk.qm, 100);
			this.eb();
			this.l.a(this, (byte)30);
		}
	}

	@Override
	public void a(aom aom) {
	}

	public void b(aom aom) {
	}

	public void ew() {
		double double2 = (double)(-aec.a(this.p * (float) (Math.PI / 180.0)));
		double double4 = (double)aec.b(this.p * (float) (Math.PI / 180.0));
		if (this.l instanceof zd) {
			((zd)this.l).a(hh.W, this.cC() + double2, this.e(0.5), this.cG() + double4, 0, double2, 0.0, double4, 0.0);
		}
	}

	@Override
	public void aa() {
		super.aa();
		this.bv.b(this);
		if (this.bw != null) {
			this.bw.b(this);
		}
	}

	public boolean ey() {
		return false;
	}

	public GameProfile ez() {
		return this.bQ;
	}

	public Either<bec.a, ael> a(fu fu) {
		this.b(fu);
		this.f = 0;
		return Either.right(ael.INSTANCE);
	}

	public void a(boolean boolean1, boolean boolean2) {
		super.em();
		if (this.l instanceof zd && boolean2) {
			((zd)this.l).n_();
		}

		this.f = boolean1 ? 0 : 100;
	}

	@Override
	public void em() {
		this.a(true, true);
	}

	public static Optional<dem> a(zd zd, fu fu, boolean boolean3, boolean boolean4) {
		cfj cfj5 = zd.d_(fu);
		bvr bvr6 = cfj5.b();
		if (bvr6 instanceof cam && (Integer)cfj5.c(cam.a) > 0 && cam.a(zd)) {
			Optional<dem> optional7 = cam.a(aoq.bb, zd, fu);
			if (!boolean4 && optional7.isPresent()) {
				zd.a(fu, cfj5.a(cam.a, Integer.valueOf((Integer)cfj5.c(cam.a) - 1)), 3);
			}

			return optional7;
		} else if (bvr6 instanceof bvm && bvm.a((bqb)zd)) {
			return bvm.a(aoq.bb, zd, fu, 0);
		} else if (!boolean3) {
			return Optional.empty();
		} else {
			boolean boolean7 = bvr6.ak_();
			boolean boolean8 = zd.d_(fu.b()).b().ak_();
			return boolean7 && boolean8 ? Optional.of(new dem((double)fu.u() + 0.5, (double)fu.v() + 0.1, (double)fu.w() + 0.5)) : Optional.empty();
		}
	}

	public boolean eA() {
		return this.el() && this.f >= 100;
	}

	public int eB() {
		return this.f;
	}

	public void a(mr mr, boolean boolean2) {
	}

	public void a(uh uh) {
		this.b(acu.i.b(uh));
	}

	public void a(uh uh, int integer) {
		this.a(acu.i.b(uh), integer);
	}

	public void b(acr<?> acr) {
		this.a(acr, 1);
	}

	public void a(acr<?> acr, int integer) {
	}

	public void a(acr<?> acr) {
	}

	public int a(Collection<bmu<?>> collection) {
		return 0;
	}

	public void a(uh[] arr) {
	}

	public int b(Collection<bmu<?>> collection) {
		return 0;
	}

	@Override
	public void dJ() {
		super.dJ();
		this.a(acu.D);
		if (this.bw()) {
			this.q(0.2F);
		} else {
			this.q(0.05F);
		}
	}

	@Override
	public void f(dem dem) {
		double double3 = this.cC();
		double double5 = this.cD();
		double double7 = this.cG();
		if (this.bx() && !this.bn()) {
			double double9 = this.bd().c;
			double double11 = double9 < -0.2 ? 0.085 : 0.06;
			if (double9 <= 0.0 || this.aX || !this.l.d_(new fu(this.cC(), this.cD() + 1.0 - 0.1, this.cG())).m().c()) {
				dem dem13 = this.cB();
				this.e(dem13.b(0.0, (double9 - dem13.c) * double11, 0.0));
			}
		}

		if (this.bJ.b && !this.bn()) {
			double double9 = this.cB().c;
			float float11 = this.aL;
			this.aL = this.bJ.a() * (float)(this.bw() ? 2 : 1);
			super.f(dem);
			dem dem12 = this.cB();
			this.m(dem12.b, double9 * 0.6, dem12.d);
			this.aL = float11;
			this.C = 0.0F;
			this.b(7, false);
		} else {
			super.f(dem);
		}

		this.o(this.cC() - double3, this.cD() - double5, this.cG() - double7);
	}

	@Override
	public void aF() {
		if (this.bJ.b) {
			this.h(false);
		} else {
			super.aF();
		}
	}

	protected boolean f(fu fu) {
		return !this.l.d_(fu).o(this.l, fu);
	}

	@Override
	public float dM() {
		return (float)this.b(apx.d);
	}

	public void o(double double1, double double2, double double3) {
		if (!this.bn()) {
			if (this.bx()) {
				int integer8 = Math.round(aec.a(double1 * double1 + double2 * double2 + double3 * double3) * 100.0F);
				if (integer8 > 0) {
					this.a(acu.B, integer8);
					this.q(0.01F * (float)integer8 * 0.01F);
				}
			} else if (this.a(acz.a)) {
				int integer8 = Math.round(aec.a(double1 * double1 + double2 * double2 + double3 * double3) * 100.0F);
				if (integer8 > 0) {
					this.a(acu.v, integer8);
					this.q(0.01F * (float)integer8 * 0.01F);
				}
			} else if (this.aA()) {
				int integer8 = Math.round(aec.a(double1 * double1 + double3 * double3) * 100.0F);
				if (integer8 > 0) {
					this.a(acu.r, integer8);
					this.q(0.01F * (float)integer8 * 0.01F);
				}
			} else if (this.c_()) {
				if (double2 > 0.0) {
					this.a(acu.t, (int)Math.round(double2 * 100.0));
				}
			} else if (this.t) {
				int integer8 = Math.round(aec.a(double1 * double1 + double3 * double3) * 100.0F);
				if (integer8 > 0) {
					if (this.bw()) {
						this.a(acu.q, integer8);
						this.q(0.1F * (float)integer8 * 0.01F);
					} else if (this.bv()) {
						this.a(acu.p, integer8);
						this.q(0.0F * (float)integer8 * 0.01F);
					} else {
						this.a(acu.o, integer8);
						this.q(0.0F * (float)integer8 * 0.01F);
					}
				}
			} else if (this.ee()) {
				int integer8 = Math.round(aec.a(double1 * double1 + double2 * double2 + double3 * double3) * 100.0F);
				this.a(acu.A, integer8);
			} else {
				int integer8 = Math.round(aec.a(double1 * double1 + double3 * double3) * 100.0F);
				if (integer8 > 25) {
					this.a(acu.u, integer8);
				}
			}
		}
	}

	private void p(double double1, double double2, double double3) {
		if (this.bn()) {
			int integer8 = Math.round(aec.a(double1 * double1 + double2 * double2 + double3 * double3) * 100.0F);
			if (integer8 > 0) {
				aom aom9 = this.cs();
				if (aom9 instanceof bfr) {
					this.a(acu.w, integer8);
				} else if (aom9 instanceof bft) {
					this.a(acu.x, integer8);
				} else if (aom9 instanceof ayy) {
					this.a(acu.y, integer8);
				} else if (aom9 instanceof azm) {
					this.a(acu.z, integer8);
				} else if (aom9 instanceof bco) {
					this.a(acu.C, integer8);
				}
			}
		}
	}

	@Override
	public boolean b(float float1, float float2) {
		if (this.bJ.c) {
			return false;
		} else {
			if (float1 >= 2.0F) {
				this.a(acu.s, (int)Math.round((double)float1 * 100.0));
			}

			return super.b(float1, float2);
		}
	}

	public boolean eC() {
		if (!this.t && !this.ee() && !this.aA() && !this.a(aoi.y)) {
			bki bki2 = this.b(aor.CHEST);
			if (bki2.b() == bkk.qn && bjk.d(bki2)) {
				this.eD();
				return true;
			}
		}

		return false;
	}

	public void eD() {
		this.b(7, true);
	}

	public void eE() {
		this.b(7, true);
		this.b(7, false);
	}

	@Override
	protected void aI() {
		if (!this.a_()) {
			super.aI();
		}
	}

	@Override
	protected ack o(int integer) {
		return integer > 4 ? acl.ln : acl.lw;
	}

	@Override
	public void a_(aoy aoy) {
		this.b(acu.g.b(aoy.U()));
	}

	@Override
	public void a(cfj cfj, dem dem) {
		if (!this.bJ.b) {
			super.a(cfj, dem);
		}
	}

	public void d(int integer) {
		this.t(integer);
		this.bM = this.bM + (float)integer / (float)this.eG();
		this.bL = aec.a(this.bL + integer, 0, Integer.MAX_VALUE);

		while (this.bM < 0.0F) {
			float float3 = this.bM * (float)this.eG();
			if (this.bK > 0) {
				this.c(-1);
				this.bM = 1.0F + float3 / (float)this.eG();
			} else {
				this.c(-1);
				this.bM = 0.0F;
			}
		}

		while (this.bM >= 1.0F) {
			this.bM = (this.bM - 1.0F) * (float)this.eG();
			this.c(1);
			this.bM = this.bM / (float)this.eG();
		}
	}

	public int eF() {
		return this.bN;
	}

	@Override
	public void a(bki bki, int integer) {
		this.bK -= integer;
		if (this.bK < 0) {
			this.bK = 0;
			this.bM = 0.0F;
			this.bL = 0;
		}

		this.bN = this.J.nextInt();
	}

	public void c(int integer) {
		this.bK += integer;
		if (this.bK < 0) {
			this.bK = 0;
			this.bM = 0.0F;
			this.bL = 0;
		}

		if (integer > 0 && this.bK % 5 == 0 && (float)this.g < (float)this.K - 100.0F) {
			float float3 = this.bK > 30 ? 1.0F : (float)this.bK / 30.0F;
			this.l.a(null, this.cC(), this.cD(), this.cG(), acl.lv, this.ct(), float3 * 0.75F, 1.0F);
			this.g = this.K;
		}
	}

	public int eG() {
		if (this.bK >= 30) {
			return 112 + (this.bK - 30) * 9;
		} else {
			return this.bK >= 15 ? 37 + (this.bK - 15) * 5 : 7 + this.bK * 2;
		}
	}

	public void q(float float1) {
		if (!this.bJ.a) {
			if (!this.l.v) {
				this.bx.a(float1);
			}
		}
	}

	public bge eH() {
		return this.bx;
	}

	public boolean q(boolean boolean1) {
		return this.bJ.a || boolean1 || this.bx.c();
	}

	public boolean eI() {
		return this.dj() > 0.0F && this.dj() < this.dw();
	}

	public boolean eJ() {
		return this.bJ.e;
	}

	public boolean a(fu fu, fz fz, bki bki) {
		if (this.bJ.e) {
			return true;
		} else {
			fu fu5 = fu.a(fz.f());
			cfn cfn6 = new cfn(this.l, fu5, false);
			return bki.b(this.l.p(), cfn6);
		}
	}

	@Override
	protected int d(bec bec) {
		if (!this.l.S().b(bpx.c) && !this.a_()) {
			int integer3 = this.bK * 7;
			return integer3 > 100 ? 100 : integer3;
		} else {
			return 0;
		}
	}

	@Override
	protected boolean cW() {
		return true;
	}

	@Override
	protected boolean ax() {
		return !this.bJ.b && (!this.t || !this.bt());
	}

	@Override
	public void t() {
	}

	public void a(bpy bpy) {
	}

	@Override
	public mr P() {
		return new nd(this.bQ.getName());
	}

	public bho eK() {
		return this.bu;
	}

	@Override
	public bki b(aor aor) {
		if (aor == aor.MAINHAND) {
			return this.bt.f();
		} else if (aor == aor.OFFHAND) {
			return this.bt.c.get(0);
		} else {
			return aor.a() == aor.a.ARMOR ? this.bt.b.get(aor.b()) : bki.b;
		}
	}

	@Override
	public void a(aor aor, bki bki) {
		if (aor == aor.MAINHAND) {
			this.b(bki);
			this.bt.a.set(this.bt.d, bki);
		} else if (aor == aor.OFFHAND) {
			this.b(bki);
			this.bt.c.set(0, bki);
		} else if (aor.a() == aor.a.ARMOR) {
			this.b(bki);
			this.bt.b.set(aor.b(), bki);
		}
	}

	public boolean g(bki bki) {
		this.b(bki);
		return this.bt.e(bki);
	}

	@Override
	public Iterable<bki> bj() {
		return Lists.<bki>newArrayList(this.dC(), this.dD());
	}

	@Override
	public Iterable<bki> bk() {
		return this.bt.b;
	}

	public boolean g(le le) {
		if (this.bn() || !this.t || this.aA()) {
			return false;
		} else if (this.eP().isEmpty()) {
			this.h(le);
			this.e = this.l.Q();
			return true;
		} else if (this.eQ().isEmpty()) {
			this.i(le);
			this.e = this.l.Q();
			return true;
		} else {
			return false;
		}
	}

	protected void eL() {
		if (this.e + 20L < this.l.Q()) {
			this.k(this.eP());
			this.h(new le());
			this.k(this.eQ());
			this.i(new le());
		}
	}

	private void k(le le) {
		if (!this.l.v && !le.isEmpty()) {
			aoq.a(le, this.l).ifPresent(aom -> {
				if (aom instanceof apq) {
					((apq)aom).b(this.ak);
				}

				aom.d(this.cC(), this.cD() + 0.7F, this.cG());
				((zd)this.l).d(aom);
			});
		}
	}

	@Override
	public abstract boolean a_();

	@Override
	public boolean bx() {
		return !this.bJ.b && !this.a_() && super.bx();
	}

	public abstract boolean b_();

	@Override
	public boolean bU() {
		return !this.bJ.b;
	}

	public dfm eM() {
		return this.l.D();
	}

	@Override
	public mr d() {
		mx mx2 = dfk.a(this.bC(), this.P());
		return this.a(mx2);
	}

	public mr eN() {
		return new nd("").a(this.P()).c(" (").c(this.bQ.getId().toString()).c(")");
	}

	private mx a(mx mx) {
		String string3 = this.ez().getName();
		return mx.a(nb -> nb.a(new mp(mp.a.SUGGEST_COMMAND, "/tell " + string3 + " ")).a(this.ca()).a(string3));
	}

	@Override
	public String bT() {
		return this.ez().getName();
	}

	@Override
	public float b(apj apj, aon aon) {
		switch (apj) {
			case SWIMMING:
			case FALL_FLYING:
			case SPIN_ATTACK:
				return 0.4F;
			case CROUCHING:
				return 1.27F;
			default:
				return 1.62F;
		}
	}

	@Override
	public void p(float float1) {
		if (float1 < 0.0F) {
			float1 = 0.0F;
		}

		this.Y().b(c, float1);
	}

	@Override
	public float dS() {
		return this.Y().a(c);
	}

	public static UUID a(GameProfile gameProfile) {
		UUID uUID2 = gameProfile.getId();
		if (uUID2 == null) {
			uUID2 = c(gameProfile.getName());
		}

		return uUID2;
	}

	public static UUID c(String string) {
		return UUID.nameUUIDFromBytes(("OfflinePlayer:" + string).getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public boolean a_(int integer, bki bki) {
		if (integer >= 0 && integer < this.bt.a.size()) {
			this.bt.a(integer, bki);
			return true;
		} else {
			aor aor4;
			if (integer == 100 + aor.HEAD.b()) {
				aor4 = aor.HEAD;
			} else if (integer == 100 + aor.CHEST.b()) {
				aor4 = aor.CHEST;
			} else if (integer == 100 + aor.LEGS.b()) {
				aor4 = aor.LEGS;
			} else if (integer == 100 + aor.FEET.b()) {
				aor4 = aor.FEET;
			} else {
				aor4 = null;
			}

			if (integer == 98) {
				this.a(aor.MAINHAND, bki);
				return true;
			} else if (integer == 99) {
				this.a(aor.OFFHAND, bki);
				return true;
			} else if (aor4 == null) {
				int integer5 = integer - 200;
				if (integer5 >= 0 && integer5 < this.bu.ab_()) {
					this.bu.a(integer5, bki);
					return true;
				} else {
					return false;
				}
			} else {
				if (!bki.a()) {
					if (!(bki.b() instanceof bid) && !(bki.b() instanceof bjk)) {
						if (aor4 != aor.HEAD) {
							return false;
						}
					} else if (aoz.j(bki) != aor4) {
						return false;
					}
				}

				this.bt.a(aor4.b() + this.bt.a.size(), bki);
				return true;
			}
		}
	}

	@Override
	public void g(int integer) {
		super.g(this.bJ.a ? Math.min(integer, 1) : integer);
	}

	@Override
	public aou dU() {
		return this.S.a(bq) == 0 ? aou.LEFT : aou.RIGHT;
	}

	public void a(aou aou) {
		this.S.b(bq, (byte)(aou == aou.LEFT ? 0 : 1));
	}

	public le eP() {
		return this.S.a(br);
	}

	protected void h(le le) {
		this.S.b(br, le);
	}

	public le eQ() {
		return this.S.a(bs);
	}

	protected void i(le le) {
		this.S.b(bs, le);
	}

	public float eR() {
		return (float)(1.0 / this.b(apx.h) * 20.0);
	}

	public float r(float float1) {
		return aec.a(((float)this.aA + float1) / this.eR(), 0.0F, 1.0F);
	}

	public void eS() {
		this.aA = 0;
	}

	public bkf eT() {
		return this.bT;
	}

	@Override
	protected float am() {
		return !this.bJ.b && !this.ee() ? super.am() : 1.0F;
	}

	public float eU() {
		return (float)this.b(apx.k);
	}

	public boolean eV() {
		return this.bJ.d && this.y() >= 2;
	}

	@Override
	public boolean e(bki bki) {
		aor aor3 = aoz.j(bki);
		return this.b(aor3).a();
	}

	@Override
	public aon a(apj apj) {
		return (aon)b.getOrDefault(apj, bo);
	}

	@Override
	public ImmutableList<apj> ei() {
		return ImmutableList.of(apj.STANDING, apj.CROUCHING, apj.SWIMMING);
	}

	@Override
	public bki f(bki bki) {
		if (!(bki.b() instanceof bkv)) {
			return bki.b;
		} else {
			Predicate<bki> predicate3 = ((bkv)bki.b()).e();
			bki bki4 = bkv.a(this, predicate3);
			if (!bki4.a()) {
				return bki4;
			} else {
				predicate3 = ((bkv)bki.b()).b();

				for (int integer5 = 0; integer5 < this.bt.ab_(); integer5++) {
					bki bki6 = this.bt.a(integer5);
					if (predicate3.test(bki6)) {
						return bki6;
					}
				}

				return this.bJ.d ? new bki(bkk.kg) : bki.b;
			}
		}
	}

	@Override
	public bki a(bqb bqb, bki bki) {
		this.eH().a(bki.b(), bki);
		this.b(acu.c.b(bki.b()));
		bqb.a(null, this.cC(), this.cD(), this.cG(), acl.lp, acm.PLAYERS, 0.5F, bqb.t.nextFloat() * 0.1F + 0.9F);
		if (this instanceof ze) {
			aa.z.a((ze)this, bki);
		}

		return super.a(bqb, bki);
	}

	@Override
	protected boolean b(cfj cfj) {
		return this.bJ.b || super.b(cfj);
	}

	public static enum a {
		NOT_POSSIBLE_HERE,
		NOT_POSSIBLE_NOW(new ne("block.minecraft.bed.no_sleep")),
		TOO_FAR_AWAY(new ne("block.minecraft.bed.too_far_away")),
		OBSTRUCTED(new ne("block.minecraft.bed.obstructed")),
		OTHER_PROBLEM,
		NOT_SAFE(new ne("block.minecraft.bed.not_safe"));

		@Nullable
		private final mr g;

		private a() {
			this.g = null;
		}

		private a(mr mr) {
			this.g = mr;
		}

		@Nullable
		public mr a() {
			return this.g;
		}
	}
}
