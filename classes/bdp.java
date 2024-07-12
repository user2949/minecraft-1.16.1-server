import bdt.f;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;

public class bdp extends bdk implements apl, bdr {
	private static final tq<bdq> by = tt.a(bdp.class, ts.q);
	public static final Map<bke, Integer> bw = ImmutableMap.of(bkk.kX, 4, bkk.oY, 1, bkk.oX, 1, bkk.qe, 1);
	private static final Set<bke> bz = ImmutableSet.of(bkk.kX, bkk.oY, bkk.oX, bkk.kW, bkk.kV, bkk.qe, bkk.qf);
	private int bA;
	private boolean bB;
	@Nullable
	private bec bC;
	private byte bE;
	private final awl bF = new awl();
	private long bG;
	private long bH;
	private int bI;
	private long bJ;
	private int bK;
	private long bL;
	private boolean bM;
	private static final ImmutableList<awp<?>> bN = ImmutableList.of(
		awp.b,
		awp.c,
		awp.d,
		awp.e,
		awp.g,
		awp.h,
		awp.i,
		awp.j,
		awp.k,
		awp.l,
		awp.J,
		awp.m,
		awp.n,
		awp.q,
		awp.r,
		awp.t,
		awp.u,
		awp.v,
		awp.w,
		awp.x,
		awp.y,
		awp.A,
		awp.f,
		awp.B,
		awp.C,
		awp.D,
		awp.F,
		awp.G,
		awp.H,
		awp.E
	);
	private static final ImmutableList<axo<? extends axn<? super bdp>>> bO = ImmutableList.of(axo.c, axo.d, axo.b, axo.e, axo.f, axo.g, axo.h, axo.i, axo.j, axo.k);
	public static final Map<awp<gc>, BiPredicate<bdp, ayc>> bx = ImmutableMap.of(
		awp.b, (bdp, ayc) -> ayc == ayc.r, awp.c, (bdp, ayc) -> bdp.eY().b().b() == ayc, awp.d, (bdp, ayc) -> ayc.a.test(ayc), awp.e, (bdp, ayc) -> ayc == ayc.s
	);

	public bdp(aoq<? extends bdp> aoq, bqb bqb) {
		this(aoq, bqb, bdu.c);
	}

	public bdp(aoq<? extends bdp> aoq, bqb bqb, bdu bdu) {
		super(aoq, bqb);
		((awu)this.x()).a(true);
		this.x().d(true);
		this.p(true);
		this.a(this.eY().a(bdu).a(bds.a));
	}

	@Override
	public apr<bdp> cI() {
		return (apr<bdp>)super.cI();
	}

	@Override
	protected apr.b<bdp> cJ() {
		return apr.a(bN, bO);
	}

	@Override
	protected apr<?> a(Dynamic<?> dynamic) {
		apr<bdp> apr3 = this.cJ().a(dynamic);
		this.a(apr3);
		return apr3;
	}

	public void b(zd zd) {
		apr<bdp> apr3 = this.cI();
		apr3.b(zd, this);
		this.bn = apr3.h();
		this.a(this.cI());
	}

	private void a(apr<bdp> apr) {
		bds bds3 = this.eY().b();
		if (this.x_()) {
			apr.a(bfn.c);
			apr.a(bfl.d, asx.a(0.5F));
		} else {
			apr.a(bfn.d);
			apr.a(bfl.c, asx.b(bds3, 0.5F), ImmutableSet.of(Pair.of(awp.c, awq.VALUE_PRESENT)));
		}

		apr.a(bfl.a, asx.a(bds3, 0.5F));
		apr.a(bfl.f, asx.d(bds3, 0.5F), ImmutableSet.of(Pair.of(awp.e, awq.VALUE_PRESENT)));
		apr.a(bfl.e, asx.c(bds3, 0.5F));
		apr.a(bfl.b, asx.e(bds3, 0.5F));
		apr.a(bfl.g, asx.f(bds3, 0.5F));
		apr.a(bfl.i, asx.g(bds3, 0.5F));
		apr.a(bfl.h, asx.h(bds3, 0.5F));
		apr.a(bfl.j, asx.i(bds3, 0.5F));
		apr.a(ImmutableSet.of(bfl.a));
		apr.b(bfl.b);
		apr.a(bfl.b);
		apr.a(this.l.R(), this.l.Q());
	}

	@Override
	protected void m() {
		super.m();
		if (this.l instanceof zd) {
			this.b((zd)this.l);
		}
	}

	public static apw.a eX() {
		return aoz.p().a(apx.d, 0.5).a(apx.b, 48.0);
	}

	public boolean eZ() {
		return this.bM;
	}

	@Override
	protected void N() {
		this.l.X().a("villagerBrain");
		this.cI().a((zd)this.l, this);
		this.l.X().c();
		if (this.bM) {
			this.bM = false;
		}

		if (!this.eO() && this.bA > 0) {
			this.bA--;
			if (this.bA <= 0) {
				if (this.bB) {
					this.ft();
					this.bB = false;
				}

				this.c(new aog(aoi.j, 200, 0));
			}
		}

		if (this.bC != null && this.l instanceof zd) {
			((zd)this.l).a(axw.e, this.bC, this);
			this.l.a(this, (byte)14);
			this.bC = null;
		}

		if (!this.eE() && this.J.nextInt(100) == 0) {
			bfh bfh2 = ((zd)this.l).c_(this.cA());
			if (bfh2 != null && bfh2.v() && !bfh2.a()) {
				this.l.a(this, (byte)42);
			}
		}

		if (this.eY().b() == bds.a && this.eO()) {
			this.eT();
		}

		super.N();
	}

	@Override
	public void j() {
		super.j();
		if (this.eL() > 0) {
			this.s(this.eL() - 1);
		}

		this.fv();
	}

	@Override
	public ang b(bec bec, anf anf) {
		bki bki4 = bec.b(anf);
		if (bki4.b() == bkk.oF || !this.aU() || this.eO() || this.el()) {
			return super.b(bec, anf);
		} else if (this.x_()) {
			this.fk();
			return ang.a(this.l.v);
		} else {
			boolean boolean5 = this.eP().isEmpty();
			if (anf == anf.MAIN_HAND) {
				if (boolean5 && !this.l.v) {
					this.fk();
				}

				bec.a(acu.R);
			}

			if (boolean5) {
				return ang.a(this.l.v);
			} else {
				if (!this.l.v && !this.bv.isEmpty()) {
					this.h(bec);
				}

				return ang.a(this.l.v);
			}
		}
	}

	private void fk() {
		this.s(40);
		if (!this.l.s_()) {
			this.a(acl.pP, this.dF(), this.dG());
		}
	}

	private void h(bec bec) {
		this.i(bec);
		this.f(bec);
		this.a(bec, this.d(), this.eY().c());
	}

	@Override
	public void f(@Nullable bec bec) {
		boolean boolean3 = this.eN() != null && bec == null;
		super.f(bec);
		if (boolean3) {
			this.eT();
		}
	}

	@Override
	protected void eT() {
		super.eT();
		this.fl();
	}

	private void fl() {
		for (boz boz3 : this.eP()) {
			boz3.l();
		}
	}

	@Override
	public boolean fa() {
		return true;
	}

	public void fb() {
		this.fp();

		for (boz boz3 : this.eP()) {
			boz3.h();
		}

		this.bJ = this.l.Q();
		this.bK++;
	}

	private boolean fm() {
		for (boz boz3 : this.eP()) {
			if (boz3.r()) {
				return true;
			}
		}

		return false;
	}

	private boolean fn() {
		return this.bK == 0 || this.bK < 2 && this.l.Q() > this.bJ + 2400L;
	}

	public boolean fc() {
		long long2 = this.bJ + 12000L;
		long long4 = this.l.Q();
		boolean boolean6 = long4 > long2;
		long long7 = this.l.R();
		if (this.bL > 0L) {
			long long9 = this.bL / 24000L;
			long long11 = long7 / 24000L;
			boolean6 |= long11 > long9;
		}

		this.bL = long7;
		if (boolean6) {
			this.bJ = long4;
			this.fx();
		}

		return this.fn() && this.fm();
	}

	private void fo() {
		int integer2 = 2 - this.bK;
		if (integer2 > 0) {
			for (boz boz4 : this.eP()) {
				boz4.h();
			}
		}

		for (int integer3 = 0; integer3 < integer2; integer3++) {
			this.fp();
		}
	}

	private void fp() {
		for (boz boz3 : this.eP()) {
			boz3.e();
		}
	}

	private void i(bec bec) {
		int integer3 = this.g(bec);
		if (integer3 != 0) {
			for (boz boz5 : this.eP()) {
				boz5.a(-aec.d((float)integer3 * boz5.n()));
			}
		}

		if (bec.a(aoi.F)) {
			aog aog4 = bec.b(aoi.F);
			int integer5 = aog4.c();

			for (boz boz7 : this.eP()) {
				double double8 = 0.3 + 0.0625 * (double)integer5;
				int integer10 = (int)Math.floor(double8 * (double)boz7.a().E());
				boz7.a(-Math.max(integer10, 1));
			}
		}
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(by, new bdq(bdu.c, bds.a, 1));
	}

	@Override
	public void b(le le) {
		super.b(le);
		bdq.a.encodeStart(lp.a, this.eY()).resultOrPartial(h::error).ifPresent(lu -> le.a("VillagerData", lu));
		le.a("FoodLevel", this.bE);
		le.a("Gossips", this.bF.a(lp.a).getValue());
		le.b("Xp", this.bI);
		le.a("LastRestock", this.bJ);
		le.a("LastGossipDecay", this.bH);
		le.b("RestocksToday", this.bK);
		if (this.bM) {
			le.a("AssignProfessionWhenSpawned", true);
		}
	}

	@Override
	public void a(le le) {
		super.a(le);
		if (le.c("VillagerData", 10)) {
			DataResult<bdq> dataResult3 = bdq.a.parse(new Dynamic<>(lp.a, le.c("VillagerData")));
			dataResult3.resultOrPartial(h::error).ifPresent(this::a);
		}

		if (le.c("Offers", 10)) {
			this.bv = new bpa(le.p("Offers"));
		}

		if (le.c("FoodLevel", 1)) {
			this.bE = le.f("FoodLevel");
		}

		lk lk3 = le.d("Gossips", 10);
		this.bF.a(new Dynamic<>(lp.a, lk3));
		if (le.c("Xp", 3)) {
			this.bI = le.h("Xp");
		}

		this.bJ = le.i("LastRestock");
		this.bH = le.i("LastGossipDecay");
		this.p(true);
		if (this.l instanceof zd) {
			this.b((zd)this.l);
		}

		this.bK = le.h("RestocksToday");
		if (le.e("AssignProfessionWhenSpawned")) {
			this.bM = le.q("AssignProfessionWhenSpawned");
		}
	}

	@Override
	public boolean h(double double1) {
		return false;
	}

	@Nullable
	@Override
	protected ack I() {
		if (this.el()) {
			return null;
		} else {
			return this.eO() ? acl.pQ : acl.pL;
		}
	}

	@Override
	protected ack e(anw anw) {
		return acl.pO;
	}

	@Override
	protected ack dp() {
		return acl.pN;
	}

	public void fd() {
		ack ack2 = this.eY().b().e();
		if (ack2 != null) {
			this.a(ack2, this.dF(), this.dG());
		}
	}

	public void a(bdq bdq) {
		bdq bdq3 = this.eY();
		if (bdq3.b() != bdq.b()) {
			this.bv = null;
		}

		this.S.b(by, bdq);
	}

	@Override
	public bdq eY() {
		return this.S.a(by);
	}

	@Override
	protected void b(boz boz) {
		int integer3 = 3 + this.J.nextInt(4);
		this.bI = this.bI + boz.o();
		this.bC = this.eN();
		if (this.fs()) {
			this.bA = 40;
			this.bB = true;
			integer3 += 5;
		}

		if (boz.s()) {
			this.l.c(new aos(this.l, this.cC(), this.cD() + 0.5, this.cG(), integer3));
		}
	}

	@Override
	public void a(@Nullable aoy aoy) {
		if (aoy != null && this.l instanceof zd) {
			((zd)this.l).a(axw.c, aoy, this);
			if (this.aU() && aoy instanceof bec) {
				this.l.a(this, (byte)13);
			}
		}

		super.a(aoy);
	}

	@Override
	public void a(anw anw) {
		h.info("Villager {} died, message: '{}'", this, anw.a((aoy)this).getString());
		aom aom3 = anw.k();
		if (aom3 != null) {
			this.a(aom3);
		}

		this.a(awp.b);
		this.a(awp.c);
		this.a(awp.e);
		super.a(anw);
	}

	private void a(aom aom) {
		if (this.l instanceof zd) {
			Optional<List<aoy>> optional3 = this.bn.c(awp.h);
			if (optional3.isPresent()) {
				zd zd4 = (zd)this.l;
				((List)optional3.get()).stream().filter(aoy -> aoy instanceof apl).forEach(aoy -> zd4.a(axw.d, aom, (apl)aoy));
			}
		}
	}

	public void a(awp<gc> awp) {
		if (this.l instanceof zd) {
			MinecraftServer minecraftServer3 = ((zd)this.l).l();
			this.bn.c(awp).ifPresent(gc -> {
				zd zd5 = minecraftServer3.a(gc.a());
				if (zd5 != null) {
					axz axz6 = zd5.x();
					Optional<ayc> optional7 = axz6.c(gc.b());
					BiPredicate<bdp, ayc> biPredicate8 = (BiPredicate<bdp, ayc>)bx.get(awp);
					if (optional7.isPresent() && biPredicate8.test(this, optional7.get())) {
						axz6.b(gc.b());
						qy.c(zd5, gc.b());
					}
				}
			});
		}
	}

	@Override
	public boolean f() {
		return this.bE + this.fu() >= 12 && this.i() == 0;
	}

	private boolean fq() {
		return this.bE < 12;
	}

	private void fr() {
		if (this.fq() && this.fu() != 0) {
			for (int integer2 = 0; integer2 < this.eU().ab_(); integer2++) {
				bki bki3 = this.eU().a(integer2);
				if (!bki3.a()) {
					Integer integer4 = (Integer)bw.get(bki3.b());
					if (integer4 != null) {
						int integer5 = bki3.E();

						for (int integer6 = integer5; integer6 > 0; integer6--) {
							this.bE = (byte)(this.bE + integer4);
							this.eU().a(integer2, 1);
							if (!this.fq()) {
								return;
							}
						}
					}
				}
			}
		}
	}

	public int g(bec bec) {
		return this.bF.a(bec.bR(), awm -> true);
	}

	private void v(int integer) {
		this.bE = (byte)(this.bE - integer);
	}

	public void ff() {
		this.fr();
		this.v(12);
	}

	public void b(bpa bpa) {
		this.bv = bpa;
	}

	private boolean fs() {
		int integer2 = this.eY().c();
		return bdq.d(integer2) && this.bI >= bdq.c(integer2);
	}

	private void ft() {
		this.a(this.eY().a(this.eY().c() + 1));
		this.eW();
	}

	@Override
	protected mr bF() {
		return new ne(this.U().f() + '.' + gl.aS.b(this.eY().b()).a());
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		if (apb == apb.BREEDING) {
			this.a(this.eY().a(bds.a));
		}

		if (apb == apb.COMMAND || apb == apb.SPAWN_EGG || apb == apb.SPAWNER || apb == apb.DISPENSER) {
			this.a(this.eY().a(bdu.a(bqc.v(this.cA()))));
		}

		if (apb == apb.STRUCTURE) {
			this.bM = true;
		}

		return super.a(bqc, ane, apb, apo, le);
	}

	public bdp a(aok aok) {
		double double4 = this.J.nextDouble();
		bdu bdu3;
		if (double4 < 0.5) {
			bdu3 = bdu.a(this.l.v(this.cA()));
		} else if (double4 < 0.75) {
			bdu3 = this.eY().a();
		} else {
			bdu3 = ((bdp)aok).eY().a();
		}

		bdp bdp6 = new bdp(aoq.aO, this.l, bdu3);
		bdp6.a(this.l, this.l.d(bdp6.cA()), apb.BREEDING, null, null);
		return bdp6;
	}

	@Override
	public void a(aox aox) {
		if (this.l.ac() != and.PEACEFUL) {
			h.info("Villager {} was struck by lightning {}.", this, aox);
			bcr bcr3 = aoq.aR.a(this.l);
			bcr3.b(this.cC(), this.cD(), this.cG(), this.p, this.q);
			bcr3.a(this.l, this.l.d(bcr3.cA()), apb.CONVERSION, null, null);
			bcr3.q(this.eE());
			if (this.Q()) {
				bcr3.a(this.R());
				bcr3.n(this.bW());
			}

			bcr3.et();
			this.l.c(bcr3);
			this.aa();
		} else {
			super.a(aox);
		}
	}

	@Override
	protected void b(bbg bbg) {
		bki bki3 = bbg.g();
		if (this.i(bki3)) {
			anm anm4 = this.eU();
			boolean boolean5 = anm4.b(bki3);
			if (!boolean5) {
				return;
			}

			this.a(bbg);
			this.a(bbg, bki3.E());
			bki bki6 = anm4.a(bki3);
			if (bki6.a()) {
				bbg.aa();
			} else {
				bki3.e(bki6.E());
			}
		}
	}

	@Override
	public boolean i(bki bki) {
		bke bke3 = bki.b();
		return (bz.contains(bke3) || this.eY().b().c().contains(bke3)) && this.eU().b(bki);
	}

	public boolean fg() {
		return this.fu() >= 24;
	}

	public boolean fh() {
		return this.fu() < 12;
	}

	private int fu() {
		anm anm2 = this.eU();
		return bw.entrySet().stream().mapToInt(entry -> anm2.a((bke)entry.getKey()) * (Integer)entry.getValue()).sum();
	}

	public boolean fi() {
		return this.eU().a(ImmutableSet.of(bkk.kV, bkk.oY, bkk.oX, bkk.qf));
	}

	@Override
	protected void eW() {
		bdq bdq2 = this.eY();
		Int2ObjectMap<f[]> int2ObjectMap3 = (Int2ObjectMap<f[]>)bdt.a.get(bdq2.b());
		if (int2ObjectMap3 != null && !int2ObjectMap3.isEmpty()) {
			f[] arr4 = int2ObjectMap3.get(bdq2.c());
			if (arr4 != null) {
				bpa bpa5 = this.eP();
				this.a(bpa5, arr4, 2);
			}
		}
	}

	public void a(bdp bdp, long long2) {
		if ((long2 < this.bG || long2 >= this.bG + 1200L) && (long2 < bdp.bG || long2 >= bdp.bG + 1200L)) {
			this.bF.a(bdp.bF, this.J, 10);
			this.bG = long2;
			bdp.bG = long2;
			this.a(long2, 5);
		}
	}

	private void fv() {
		long long2 = this.l.Q();
		if (this.bH == 0L) {
			this.bH = long2;
		} else if (long2 >= this.bH + 24000L) {
			this.bF.b();
			this.bH = long2;
		}
	}

	public void a(long long1, int integer) {
		if (this.a(long1)) {
			deg deg5 = this.cb().c(10.0, 10.0, 10.0);
			List<bdp> list6 = this.l.a(bdp.class, deg5);
			List<bdp> list7 = (List<bdp>)list6.stream().filter(bdp -> bdp.a(long1)).limit(5L).collect(Collectors.toList());
			if (list7.size() >= integer) {
				ayt ayt8 = this.fw();
				if (ayt8 != null) {
					list6.forEach(bdp -> bdp.b(long1));
				}
			}
		}
	}

	private void b(long long1) {
		this.bn.a(awp.E, long1);
	}

	private boolean c(long long1) {
		Optional<Long> optional4 = this.bn.c(awp.E);
		if (!optional4.isPresent()) {
			return false;
		} else {
			Long long5 = (Long)optional4.get();
			return long1 - long5 <= 600L;
		}
	}

	public boolean a(long long1) {
		return !this.d(this.l.Q()) ? false : !this.c(long1);
	}

	@Nullable
	private ayt fw() {
		fu fu2 = this.cA();

		for (int integer3 = 0; integer3 < 10; integer3++) {
			double double4 = (double)(this.l.t.nextInt(16) - 8);
			double double6 = (double)(this.l.t.nextInt(16) - 8);
			fu fu8 = this.a(fu2, double4, double6);
			if (fu8 != null) {
				ayt ayt9 = aoq.K.b(this.l, null, null, null, fu8, apb.MOB_SUMMONED, false, false);
				if (ayt9 != null) {
					if (ayt9.a(this.l, apb.MOB_SUMMONED) && ayt9.a(this.l)) {
						this.l.c(ayt9);
						return ayt9;
					}

					ayt9.aa();
				}
			}
		}

		return null;
	}

	@Nullable
	private fu a(fu fu, double double2, double double3) {
		int integer7 = 6;
		fu fu8 = fu.a(double2, 6.0, double3);
		cfj cfj9 = this.l.d_(fu8);

		for (int integer10 = 6; integer10 >= -6; integer10--) {
			fu fu11 = fu8;
			cfj cfj12 = cfj9;
			fu8 = fu8.c();
			cfj9 = this.l.d_(fu8);
			if ((cfj12.g() || cfj12.c().a()) && cfj9.c().f()) {
				return fu11;
			}
		}

		return null;
	}

	@Override
	public void a(axw axw, aom aom) {
		if (axw == axw.a) {
			this.bF.a(aom.bR(), awm.MAJOR_POSITIVE, 20);
			this.bF.a(aom.bR(), awm.MINOR_POSITIVE, 25);
		} else if (axw == axw.e) {
			this.bF.a(aom.bR(), awm.TRADING, 2);
		} else if (axw == axw.c) {
			this.bF.a(aom.bR(), awm.MINOR_NEGATIVE, 25);
		} else if (axw == axw.d) {
			this.bF.a(aom.bR(), awm.MAJOR_NEGATIVE, 25);
		}
	}

	@Override
	public int eM() {
		return this.bI;
	}

	public void u(int integer) {
		this.bI = integer;
	}

	private void fx() {
		this.fo();
		this.bK = 0;
	}

	public awl fj() {
		return this.bF;
	}

	public void a(lu lu) {
		this.bF.a(new Dynamic<>(lp.a, lu));
	}

	@Override
	protected void M() {
		super.M();
		qy.a(this);
	}

	@Override
	public void b(fu fu) {
		super.b(fu);
		this.bn.a(awp.F, this.l.Q());
		this.bn.b(awp.m);
		this.bn.b(awp.D);
	}

	@Override
	public void em() {
		super.em();
		this.bn.a(awp.G, this.l.Q());
	}

	private boolean d(long long1) {
		Optional<Long> optional4 = this.bn.c(awp.F);
		return optional4.isPresent() ? long1 - (Long)optional4.get() < 24000L : false;
	}
}
