import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ze extends bec implements bgt {
	private static final Logger bQ = LogManager.getLogger();
	public zv b;
	public final MinecraftServer c;
	public final zf d;
	private final List<Integer> bR = Lists.<Integer>newLinkedList();
	private final uq bS;
	private final acq bT;
	private float bU = Float.MIN_VALUE;
	private int bV = Integer.MIN_VALUE;
	private int bW = Integer.MIN_VALUE;
	private int bX = Integer.MIN_VALUE;
	private int bY = Integer.MIN_VALUE;
	private int bZ = Integer.MIN_VALUE;
	private float ca = -1.0E8F;
	private int cb = -99999999;
	private boolean cc = true;
	private int cd = -99999999;
	private int ce = 60;
	private bea cf;
	private boolean cg = true;
	private long ch = v.b();
	private aom ci;
	private boolean cj;
	private boolean ck;
	private final acp cl = new acp();
	private dem cm;
	private int cn;
	private boolean co;
	@Nullable
	private dem cp;
	private go cq = go.a(0, 0, 0);
	private ug<bqb> cr = bqb.g;
	@Nullable
	private fu cs;
	private boolean ct;
	private int cu;
	public boolean e;
	public int f;
	public boolean g;

	public ze(MinecraftServer minecraftServer, zd zd, GameProfile gameProfile, zf zf) {
		super(zd, zd.u(), gameProfile);
		zf.b = this;
		this.d = zf;
		this.c = minecraftServer;
		this.bT = minecraftServer.ac().a((bec)this);
		this.bS = minecraftServer.ac().f(this);
		this.G = 1.0F;
		this.b(zd);
	}

	private void b(zd zd) {
		fu fu3 = zd.u();
		if (zd.m().d() && zd.l().aV().l() != bpy.ADVENTURE) {
			int integer4 = Math.max(0, this.c.a(zd));
			int integer5 = aec.c(zd.f().b((double)fu3.u(), (double)fu3.w()));
			if (integer5 < integer4) {
				integer4 = integer5;
			}

			if (integer5 <= 1) {
				integer4 = 1;
			}

			long long6 = (long)(integer4 * 2 + 1);
			long long8 = long6 * long6;
			int integer10 = long8 > 2147483647L ? Integer.MAX_VALUE : (int)long8;
			int integer11 = this.u(integer10);
			int integer12 = new Random().nextInt(integer10);

			for (int integer13 = 0; integer13 < integer10; integer13++) {
				int integer14 = (integer12 + integer11 * integer13) % integer10;
				int integer15 = integer14 % (integer4 * 2 + 1);
				int integer16 = integer14 / (integer4 * 2 + 1);
				fu fu17 = yy.a(zd, fu3.u() + integer15 - integer4, fu3.w() + integer16 - integer4, false);
				if (fu17 != null) {
					this.a(fu17, 0.0F, 0.0F);
					if (zd.j(this)) {
						break;
					}
				}
			}
		} else {
			this.a(fu3, 0.0F, 0.0F);

			while (!zd.j(this) && this.cD() < 255.0) {
				this.d(this.cC(), this.cD() + 1.0, this.cG());
			}
		}
	}

	private int u(int integer) {
		return integer <= 16 ? integer - 1 : 17;
	}

	@Override
	public void a(le le) {
		super.a(le);
		if (le.c("playerGameType", 99)) {
			if (this.cg().aj()) {
				this.d.a(this.cg().r(), bpy.NOT_SET);
			} else {
				this.d.a(bpy.a(le.h("playerGameType")), le.c("previousPlayerGameType", 3) ? bpy.a(le.h("previousPlayerGameType")) : bpy.NOT_SET);
			}
		}

		if (le.c("enteredNetherPosition", 10)) {
			le le3 = le.p("enteredNetherPosition");
			this.cp = new dem(le3.k("x"), le3.k("y"), le3.k("z"));
		}

		this.ck = le.q("seenCredits");
		if (le.c("recipeBook", 10)) {
			this.cl.a(le.p("recipeBook"), this.c.aD());
		}

		if (this.el()) {
			this.em();
		}

		if (le.c("SpawnX", 99) && le.c("SpawnY", 99) && le.c("SpawnZ", 99)) {
			this.cs = new fu(le.h("SpawnX"), le.h("SpawnY"), le.h("SpawnZ"));
			this.ct = le.q("SpawnForced");
			if (le.e("SpawnDimension")) {
				this.cr = (ug<bqb>)bqb.f.parse(lp.a, le.c("SpawnDimension")).resultOrPartial(bQ::error).orElse(bqb.g);
			}
		}
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.b("playerGameType", this.d.b().a());
		le.b("previousPlayerGameType", this.d.c().a());
		le.a("seenCredits", this.ck);
		if (this.cp != null) {
			le le3 = new le();
			le3.a("x", this.cp.b);
			le3.a("y", this.cp.c);
			le3.a("z", this.cp.d);
			le.a("enteredNetherPosition", le3);
		}

		aom aom3 = this.cq();
		aom aom4 = this.cs();
		if (aom4 != null && aom3 != this && aom3.cp()) {
			le le5 = new le();
			le le6 = new le();
			aom3.d(le6);
			le5.a("Attach", aom4.bR());
			le5.a("Entity", le6);
			le.a("RootVehicle", le5);
		}

		le.a("recipeBook", this.cl.i());
		le.a("Dimension", this.l.W().a().toString());
		if (this.cs != null) {
			le.b("SpawnX", this.cs.u());
			le.b("SpawnY", this.cs.v());
			le.b("SpawnZ", this.cs.w());
			le.a("SpawnForced", this.ct);
			uh.a.encodeStart(lp.a, this.cr.a()).resultOrPartial(bQ::error).ifPresent(lu -> le.a("SpawnDimension", lu));
		}
	}

	public void a(int integer) {
		float float3 = (float)this.eG();
		float float4 = (float3 - 1.0F) / float3;
		this.bM = aec.a((float)integer / float3, 0.0F, float4);
		this.cd = -1;
	}

	public void b(int integer) {
		this.bK = integer;
		this.cd = -1;
	}

	@Override
	public void c(int integer) {
		super.c(integer);
		this.cd = -1;
	}

	@Override
	public void a(bki bki, int integer) {
		super.a(bki, integer);
		this.cd = -1;
	}

	public void f() {
		this.bw.a((bgt)this);
	}

	@Override
	public void g() {
		super.g();
		this.b.a(new ph(this.du(), ph.a.ENTER_COMBAT));
	}

	@Override
	public void h() {
		super.h();
		this.b.a(new ph(this.du(), ph.a.END_COMBAT));
	}

	@Override
	protected void a(cfj cfj) {
		aa.d.a(this, cfj);
	}

	@Override
	protected bkf i() {
		return new bla(this);
	}

	@Override
	public void j() {
		this.d.a();
		this.ce--;
		if (this.Q > 0) {
			this.Q--;
		}

		this.bw.c();
		if (!this.l.v && !this.bw.a((bec)this)) {
			this.m();
			this.bw = this.bv;
		}

		while (!this.bR.isEmpty()) {
			int integer2 = Math.min(this.bR.size(), Integer.MAX_VALUE);
			int[] arr3 = new int[integer2];
			Iterator<Integer> iterator4 = this.bR.iterator();
			int integer5 = 0;

			while (iterator4.hasNext() && integer5 < integer2) {
				arr3[integer5++] = (Integer)iterator4.next();
				iterator4.remove();
			}

			this.b.a(new pm(arr3));
		}

		aom aom2 = this.D();
		if (aom2 != this) {
			if (aom2.aU()) {
				this.a(aom2.cC(), aom2.cD(), aom2.cG(), aom2.p, aom2.q);
				this.u().i().a(this);
				if (this.eq()) {
					this.e(this);
				}
			} else {
				this.e(this);
			}
		}

		aa.w.a(this);
		if (this.cm != null) {
			aa.u.a(this, this.cm, this.K - this.cn);
		}

		this.bS.b(this);
	}

	public void w_() {
		try {
			if (!this.a_() || this.l.C(this.cA())) {
				super.j();
			}

			for (int integer2 = 0; integer2 < this.bt.ab_(); integer2++) {
				bki bki3 = this.bt.a(integer2);
				if (bki3.b().ae_()) {
					ni<?> ni4 = ((bix)bki3.b()).a(bki3, this.l, this);
					if (ni4 != null) {
						this.b.a(ni4);
					}
				}
			}

			if (this.dj() != this.ca || this.cb != this.bx.a() || this.bx.e() == 0.0F != this.cc) {
				this.b.a(new qe(this.dj(), this.bx.a(), this.bx.e()));
				this.ca = this.dj();
				this.cb = this.bx.a();
				this.cc = this.bx.e() == 0.0F;
			}

			if (this.dj() + this.dS() != this.bU) {
				this.bU = this.dj() + this.dS();
				this.a(dfp.g, aec.f(this.bU));
			}

			if (this.bx.a() != this.bV) {
				this.bV = this.bx.a();
				this.a(dfp.h, aec.f((float)this.bV));
			}

			if (this.bE() != this.bW) {
				this.bW = this.bE();
				this.a(dfp.i, aec.f((float)this.bW));
			}

			if (this.dt() != this.bX) {
				this.bX = this.dt();
				this.a(dfp.j, aec.f((float)this.bX));
			}

			if (this.bL != this.bZ) {
				this.bZ = this.bL;
				this.a(dfp.k, aec.f((float)this.bZ));
			}

			if (this.bK != this.bY) {
				this.bY = this.bK;
				this.a(dfp.l, aec.f((float)this.bY));
			}

			if (this.bL != this.cd) {
				this.cd = this.bL;
				this.b.a(new qd(this.bM, this.bL, this.bK));
			}

			if (this.K % 20 == 0) {
				aa.p.a(this);
			}
		} catch (Throwable var4) {
			j j3 = j.a(var4, "Ticking player");
			k k4 = j3.a("Player being ticked");
			this.a(k4);
			throw new s(j3);
		}
	}

	private void a(dfp dfp, int integer) {
		this.eM().a(dfp, this.bT(), dfl -> dfl.c(integer));
	}

	@Override
	public void a(anw anw) {
		boolean boolean3 = this.l.S().b(bpx.l);
		if (boolean3) {
			mr mr4 = this.du().b();
			this.b.a(new ph(this.du(), ph.a.ENTITY_DIED, mr4), future -> {
				if (!future.isSuccess()) {
					int integer4 = 256;
					String string5 = mr4.a(256);
					mr mr6 = new ne("death.attack.message_too_long", new nd(string5).a(i.YELLOW));
					mr mr7 = new ne("death.attack.even_more_magic", this.d()).a(nb -> nb.a(new mv(mv.a.a, mr6)));
					this.b.a(new ph(this.du(), ph.a.ENTITY_DIED, mr7));
				}
			});
			dfo dfo5 = this.bC();
			if (dfo5 == null || dfo5.k() == dfo.b.ALWAYS) {
				this.c.ac().a(mr4, mo.SYSTEM, v.b);
			} else if (dfo5.k() == dfo.b.HIDE_FOR_OTHER_TEAMS) {
				this.c.ac().a(this, mr4);
			} else if (dfo5.k() == dfo.b.HIDE_FOR_OWN_TEAM) {
				this.c.ac().b(this, mr4);
			}
		} else {
			this.b.a(new ph(this.du(), ph.a.ENTITY_DIED));
		}

		this.eL();
		if (this.l.S().b(bpx.F)) {
			this.eW();
		}

		if (!this.a_()) {
			this.d(anw);
		}

		this.eM().a(dfp.d, this.bT(), dfl::a);
		aoy aoy4 = this.dv();
		if (aoy4 != null) {
			this.b(acu.h.b(aoy4.U()));
			aoy4.a(this, this.aV, anw);
			this.g(aoy4);
		}

		this.l.a(this, (byte)3);
		this.a(acu.M);
		this.a(acu.i.b(acu.l));
		this.a(acu.i.b(acu.m));
		this.ah();
		this.b(0, false);
		this.du().g();
	}

	private void eW() {
		deg deg2 = new deg(this.cA()).c(32.0, 10.0, 32.0);
		this.l.b(aoz.class, deg2).stream().filter(aoz -> aoz instanceof ape).forEach(aoz -> ((ape)aoz).b((bec)this));
	}

	@Override
	public void a(aom aom, int integer, anw anw) {
		if (aom != this) {
			super.a(aom, integer, anw);
			this.t(integer);
			String string5 = this.bT();
			String string6 = aom.bT();
			this.eM().a(dfp.f, string5, dfl::a);
			if (aom instanceof bec) {
				this.a(acu.P);
				this.eM().a(dfp.e, string5, dfl::a);
			} else {
				this.a(acu.N);
			}

			this.a(string5, string6, dfp.m);
			this.a(string6, string5, dfp.n);
			aa.b.a(this, aom, anw);
		}
	}

	private void a(String string1, String string2, dfp[] arr) {
		dfk dfk5 = this.eM().i(string2);
		if (dfk5 != null) {
			int integer6 = dfk5.n().b();
			if (integer6 >= 0 && integer6 < arr.length) {
				this.eM().a(arr[integer6], string1, dfl::a);
			}
		}
	}

	@Override
	public boolean a(anw anw, float float2) {
		if (this.b(anw)) {
			return false;
		} else {
			boolean boolean4 = this.c.j() && this.eX() && "fall".equals(anw.v);
			if (!boolean4 && this.ce > 0 && anw != anw.m) {
				return false;
			} else {
				if (anw instanceof anx) {
					aom aom5 = anw.k();
					if (aom5 instanceof bec && !this.a((bec)aom5)) {
						return false;
					}

					if (aom5 instanceof beg) {
						beg beg6 = (beg)aom5;
						aom aom7 = beg6.v();
						if (aom7 instanceof bec && !this.a((bec)aom7)) {
							return false;
						}
					}
				}

				return super.a(anw, float2);
			}
		}
	}

	@Override
	public boolean a(bec bec) {
		return !this.eX() ? false : super.a(bec);
	}

	private boolean eX() {
		return this.c.X();
	}

	@Nullable
	@Override
	public aom a(zd zd) {
		this.cj = true;
		zd zd3 = this.u();
		ug<bqb> ug4 = zd3.W();
		if (ug4 == bqb.i && zd.W() == bqb.g) {
			this.T();
			this.u().e(this);
			if (!this.g) {
				this.g = true;
				this.b.a(new oq(oq.e, this.ck ? 0.0F : 1.0F));
				this.ck = true;
			}

			return this;
		} else {
			dab dab5 = zd.u_();
			this.b.a(new pp(zd.V(), zd.W(), brg.a(zd.B()), this.d.b(), this.d.c(), zd.Z(), zd.A(), true));
			this.b.a(new nz(dab5.r(), dab5.s()));
			abp abp6 = this.c.ac();
			abp6.d(this);
			zd3.e(this);
			this.y = false;
			double double7 = this.cC();
			double double9 = this.cD();
			double double11 = this.cG();
			float float13 = this.q;
			float float14 = this.p;
			float float15 = float14;
			zd3.X().a("moving");
			if (zd.W() == bqb.i) {
				fu fu16 = zd.a;
				double7 = (double)fu16.u();
				double9 = (double)fu16.v();
				double11 = (double)fu16.w();
				float14 = 90.0F;
				float13 = 0.0F;
			} else {
				if (ug4 == bqb.g && zd.W() == bqb.h) {
					this.cp = this.cz();
				}

				cif cif16 = zd3.m();
				cif cif17 = zd.m();
				double double18 = 8.0;
				if (!cif16.h() && cif17.h()) {
					double7 /= 8.0;
					double11 /= 8.0;
				} else if (cif16.h() && !cif17.h()) {
					double7 *= 8.0;
					double11 *= 8.0;
				}
			}

			this.b(double7, double9, double11, float14, float13);
			zd3.X().c();
			zd3.X().a("placing");
			double double16 = Math.min(-2.9999872E7, zd.f().e() + 16.0);
			double double18 = Math.min(-2.9999872E7, zd.f().f() + 16.0);
			double double20 = Math.min(2.9999872E7, zd.f().g() - 16.0);
			double double22 = Math.min(2.9999872E7, zd.f().h() - 16.0);
			double7 = aec.a(double7, double16, double20);
			double11 = aec.a(double11, double18, double22);
			this.b(double7, double9, double11, float14, float13);
			if (zd.W() == bqb.i) {
				int integer24 = aec.c(this.cC());
				int integer25 = aec.c(this.cD()) - 1;
				int integer26 = aec.c(this.cG());
				zd.a(zd);
				this.b((double)integer24, (double)integer25, (double)integer26, float14, 0.0F);
				this.e(dem.a);
			} else if (!zd.q_().a(this, float15)) {
				zd.q_().a(this);
				zd.q_().a(this, float15);
			}

			zd3.X().c();
			this.a_(zd);
			zd.b(this);
			this.c(zd3);
			this.b.a(this.cC(), this.cD(), this.cG(), float14, float13);
			this.d.a(zd);
			this.b.a(new pg(this.bJ));
			abp6.a(this, zd);
			abp6.e(this);

			for (aog aog25 : this.dg()) {
				this.b.a(new qu(this.V(), aog25));
			}

			this.b.a(new ou(1032, fu.b, 0, false));
			this.cd = -1;
			this.ca = -1.0F;
			this.cb = -1;
			return this;
		}
	}

	private void c(zd zd) {
		ug<bqb> ug3 = zd.W();
		ug<bqb> ug4 = this.l.W();
		aa.v.a(this, ug3, ug4);
		if (ug3 == bqb.h && ug4 == bqb.g && this.cp != null) {
			aa.C.a(this, this.cp);
		}

		if (ug4 != bqb.h) {
			this.cp = null;
		}
	}

	@Override
	public boolean a(ze ze) {
		if (ze.a_()) {
			return this.D() == this;
		} else {
			return this.a_() ? false : super.a(ze);
		}
	}

	private void a(cdl cdl) {
		if (cdl != null) {
			nv nv3 = cdl.a();
			if (nv3 != null) {
				this.b.a(nv3);
			}
		}
	}

	@Override
	public void a(aom aom, int integer) {
		super.a(aom, integer);
		this.bw.c();
	}

	@Override
	public Either<bec.a, ael> a(fu fu) {
		fz fz3 = this.l.d_(fu).c(byp.aq);
		if (this.el() || !this.aU()) {
			return Either.left(bec.a.OTHER_PROBLEM);
		} else if (!this.l.m().g()) {
			return Either.left(bec.a.NOT_POSSIBLE_HERE);
		} else if (!this.a(fu, fz3)) {
			return Either.left(bec.a.TOO_FAR_AWAY);
		} else if (this.b(fu, fz3)) {
			return Either.left(bec.a.OBSTRUCTED);
		} else {
			this.a(this.l.W(), fu, false, true);
			if (this.l.J()) {
				return Either.left(bec.a.NOT_POSSIBLE_NOW);
			} else {
				if (!this.b_()) {
					double double4 = 8.0;
					double double6 = 5.0;
					dem dem8 = dem.c(fu);
					List<bcb> list9 = this.l
						.a(bcb.class, new deg(dem8.a() - 8.0, dem8.b() - 5.0, dem8.c() - 8.0, dem8.a() + 8.0, dem8.b() + 5.0, dem8.c() + 8.0), bcb -> bcb.f(this));
					if (!list9.isEmpty()) {
						return Either.left(bec.a.NOT_SAFE);
					}
				}

				Either<bec.a, ael> either4 = super.a(fu).ifRight(ael -> {
					this.a(acu.ao);
					aa.q.a(this);
				});
				((zd)this.l).n_();
				return either4;
			}
		}
	}

	@Override
	public void b(fu fu) {
		this.a(acu.i.b(acu.m));
		super.b(fu);
	}

	private boolean a(fu fu, fz fz) {
		return this.g(fu) || this.g(fu.a(fz.f()));
	}

	private boolean g(fu fu) {
		dem dem3 = dem.c(fu);
		return Math.abs(this.cC() - dem3.a()) <= 3.0 && Math.abs(this.cD() - dem3.b()) <= 2.0 && Math.abs(this.cG() - dem3.c()) <= 3.0;
	}

	private boolean b(fu fu, fz fz) {
		fu fu4 = fu.b();
		return !this.f(fu4) || !this.f(fu4.a(fz.f()));
	}

	@Override
	public void a(boolean boolean1, boolean boolean2) {
		if (this.el()) {
			this.u().i().a(this, new nr(this, 2));
		}

		super.a(boolean1, boolean2);
		if (this.b != null) {
			this.b.a(this.cC(), this.cD(), this.cG(), this.p, this.q);
		}
	}

	@Override
	public boolean a(aom aom, boolean boolean2) {
		aom aom4 = this.cs();
		if (!super.a(aom, boolean2)) {
			return false;
		} else {
			aom aom5 = this.cs();
			if (aom5 != aom4 && this.b != null) {
				this.b.a(this.cC(), this.cD(), this.cG(), this.p, this.q);
			}

			return true;
		}
	}

	@Override
	public void l() {
		aom aom2 = this.cs();
		super.l();
		aom aom3 = this.cs();
		if (aom3 != aom2 && this.b != null) {
			this.b.a(this.cC(), this.cD(), this.cG(), this.p, this.q);
		}
	}

	@Override
	public boolean b(anw anw) {
		return super.b(anw) || this.H() || this.bJ.a && anw == anw.p;
	}

	@Override
	protected void a(double double1, boolean boolean2, cfj cfj, fu fu) {
	}

	@Override
	protected void c(fu fu) {
		if (!this.a_()) {
			super.c(fu);
		}
	}

	public void a(double double1, boolean boolean2) {
		fu fu5 = this.ak();
		if (this.l.C(fu5)) {
			super.a(double1, boolean2, this.l.d_(fu5), fu5);
		}
	}

	@Override
	public void a(ceh ceh) {
		ceh.a((bec)this);
		this.b.a(new pe(ceh.o()));
	}

	private void eY() {
		this.cu = this.cu % 100 + 1;
	}

	@Override
	public OptionalInt a(@Nullable anj anj) {
		if (anj == null) {
			return OptionalInt.empty();
		} else {
			if (this.bw != this.bv) {
				this.m();
			}

			this.eY();
			bgi bgi3 = anj.createMenu(this.cu, this.bt, this);
			if (bgi3 == null) {
				if (this.a_()) {
					this.a(new ne("container.spectatorCantOpen").a(i.RED), true);
				}

				return OptionalInt.empty();
			} else {
				this.b.a(new pd(bgi3.b, bgi3.a(), anj.d()));
				bgi3.a((bgt)this);
				this.bw = bgi3;
				return OptionalInt.of(this.cu);
			}
		}
	}

	@Override
	public void a(int integer1, bpa bpa, int integer3, int integer4, boolean boolean5, boolean boolean6) {
		this.b.a(new oz(integer1, bpa, integer3, integer4, boolean5, boolean6));
	}

	@Override
	public void a(azm azm, amz amz) {
		if (this.bw != this.bv) {
			this.m();
		}

		this.eY();
		this.b.a(new or(this.cu, amz.ab_(), azm.V()));
		this.bw = new bhe(this.cu, this.bt, amz, azm);
		this.bw.a((bgt)this);
	}

	@Override
	public void a(bki bki, anf anf) {
		bke bke4 = bki.b();
		if (bke4 == bkk.oT) {
			if (bma.a(bki, this.cv(), this)) {
				this.bw.c();
			}

			this.b.a(new pc(anf));
		}
	}

	@Override
	public void a(cdq cdq) {
		cdq.c(true);
		this.a((cdl)cdq);
	}

	@Override
	public void a(bgi bgi, int integer, bki bki) {
		if (!(bgi.a(integer) instanceof bhs)) {
			if (bgi == this.bv) {
				aa.e.a(this, this.bt, bki);
			}

			if (!this.e) {
				this.b.a(new oi(bgi.b, integer, bki));
			}
		}
	}

	public void a(bgi bgi) {
		this.a(bgi, bgi.b());
	}

	@Override
	public void a(bgi bgi, gi<bki> gi) {
		this.b.a(new og(bgi.b, gi));
		this.b.a(new oi(-1, -1, this.bt.m()));
	}

	@Override
	public void a(bgi bgi, int integer2, int integer3) {
		this.b.a(new oh(bgi.b, integer2, integer3));
	}

	@Override
	public void m() {
		this.b.a(new of(this.bw.b));
		this.o();
	}

	@Override
	public void n() {
		if (!this.e) {
			this.b.a(new oi(-1, -1, this.bt.m()));
		}
	}

	@Override
	public void o() {
		this.bw.b(this);
		this.bw = this.bv;
	}

	public void a(float float1, float float2, boolean boolean3, boolean boolean4) {
		if (this.bn()) {
			if (float1 >= -1.0F && float1 <= 1.0F) {
				this.aY = float1;
			}

			if (float2 >= -1.0F && float2 <= 1.0F) {
				this.ba = float2;
			}

			this.aX = boolean3;
			this.f(boolean4);
		}
	}

	@Override
	public void a(acr<?> acr, int integer) {
		this.bT.b(this, acr, integer);
		this.eM().a(acr, this.bT(), dfl -> dfl.a(integer));
	}

	@Override
	public void a(acr<?> acr) {
		this.bT.a(this, acr, 0);
		this.eM().a(acr, this.bT(), dfl::c);
	}

	@Override
	public int a(Collection<bmu<?>> collection) {
		return this.cl.a(collection, this);
	}

	@Override
	public void a(uh[] arr) {
		List<bmu<?>> list3 = Lists.<bmu<?>>newArrayList();

		for (uh uh7 : arr) {
			this.c.aD().a(uh7).ifPresent(list3::add);
		}

		this.a(list3);
	}

	@Override
	public int b(Collection<bmu<?>> collection) {
		return this.cl.b(collection, this);
	}

	@Override
	public void d(int integer) {
		super.d(integer);
		this.cd = -1;
	}

	@Override
	public void p() {
		this.co = true;
		this.ba();
		if (this.el()) {
			this.a(true, false);
		}
	}

	public boolean q() {
		return this.co;
	}

	public void r() {
		this.ca = -1.0E8F;
	}

	@Override
	public void a(mr mr, boolean boolean2) {
		this.b.a(new oa(mr, boolean2 ? mo.GAME_INFO : mo.CHAT, v.b));
	}

	@Override
	protected void s() {
		if (!this.bj.a() && this.dV()) {
			this.b.a(new on(this, (byte)9));
			super.s();
		}
	}

	@Override
	public void a(dg.a a, dem dem) {
		super.a(a, dem);
		this.b.a(new pj(a, dem.b, dem.c, dem.d));
	}

	public void a(dg.a a1, aom aom, dg.a a3) {
		dem dem5 = a3.a(aom);
		super.a(a1, dem5);
		this.b.a(new pj(a1, aom, a3));
	}

	public void a(ze ze, boolean boolean2) {
		if (boolean2) {
			this.bt.a(ze.bt);
			this.c(ze.dj());
			this.bx = ze.bx;
			this.bK = ze.bK;
			this.bL = ze.bL;
			this.bM = ze.bM;
			this.s(ze.eu());
			this.ah = ze.ah;
			this.ai = ze.ai;
			this.aj = ze.aj;
		} else if (this.l.S().b(bpx.c) || ze.a_()) {
			this.bt.a(ze.bt);
			this.bK = ze.bK;
			this.bL = ze.bL;
			this.bM = ze.bM;
			this.s(ze.eu());
		}

		this.bN = ze.bN;
		this.bu = ze.bu;
		this.Y().b(bp, ze.Y().a(bp));
		this.cd = -1;
		this.ca = -1.0F;
		this.cb = -1;
		this.cl.a(ze.cl);
		this.bR.addAll(ze.bR);
		this.ck = ze.ck;
		this.cp = ze.cp;
		this.h(ze.eP());
		this.i(ze.eQ());
	}

	@Override
	protected void a(aog aog) {
		super.a(aog);
		this.b.a(new qu(this.V(), aog));
		if (aog.a() == aoi.y) {
			this.cn = this.K;
			this.cm = this.cz();
		}

		aa.A.a(this);
	}

	@Override
	protected void a(aog aog, boolean boolean2) {
		super.a(aog, boolean2);
		this.b.a(new qu(this.V(), aog));
		aa.A.a(this);
	}

	@Override
	protected void b(aog aog) {
		super.b(aog);
		this.b.a(new pn(this.V(), aog.a()));
		if (aog.a() == aoi.y) {
			this.cm = null;
		}

		aa.A.a(this);
	}

	@Override
	public void a(double double1, double double2, double double3) {
		this.b.a(double1, double2, double3, this.p, this.q);
	}

	@Override
	public void b(double double1, double double2, double double3) {
		this.b.a(double1, double2, double3, this.p, this.q);
		this.b.c();
	}

	@Override
	public void a(aom aom) {
		this.u().i().a(this, new nr(aom, 4));
	}

	@Override
	public void b(aom aom) {
		this.u().i().a(this, new nr(aom, 5));
	}

	@Override
	public void t() {
		if (this.b != null) {
			this.b.a(new pg(this.bJ));
			this.C();
		}
	}

	public zd u() {
		return (zd)this.l;
	}

	@Override
	public void a(bpy bpy) {
		this.d.a(bpy);
		this.b.a(new oq(oq.d, (float)bpy.a()));
		if (bpy == bpy.SPECTATOR) {
			this.eL();
			this.l();
		} else {
			this.e(this);
		}

		this.t();
		this.dT();
	}

	@Override
	public boolean a_() {
		return this.d.b() == bpy.SPECTATOR;
	}

	@Override
	public boolean b_() {
		return this.d.b() == bpy.CREATIVE;
	}

	@Override
	public void a(mr mr, UUID uUID) {
		this.a(mr, mo.SYSTEM, uUID);
	}

	public void a(mr mr, mo mo, UUID uUID) {
		this.b.a(new oa(mr, mo, uUID), future -> {
			if (!future.isSuccess() && (mo == mo.GAME_INFO || mo == mo.SYSTEM)) {
				int integer6 = 256;
				String string7 = mr.a(256);
				mr mr8 = new nd(string7).a(i.YELLOW);
				this.b.a(new oa(new ne("multiplayer.message_not_delivered", mr8).a(i.RED), mo.SYSTEM, uUID));
			}
		});
	}

	public String v() {
		String string2 = this.b.a.b().toString();
		string2 = string2.substring(string2.indexOf("/") + 1);
		return string2.substring(0, string2.indexOf(":"));
	}

	public void a(rf rf) {
		this.cf = rf.d();
		this.cg = rf.e();
		this.Y().b(bp, (byte)rf.f());
		this.Y().b(bq, (byte)(rf.g() == aou.LEFT ? 0 : 1));
	}

	public bea x() {
		return this.cf;
	}

	public void a(String string1, String string2) {
		this.b.a(new po(string1, string2));
	}

	@Override
	protected int y() {
		return this.c.b(this.ez());
	}

	public void z() {
		this.ch = v.b();
	}

	public acq A() {
		return this.bT;
	}

	public acp B() {
		return this.cl;
	}

	@Override
	public void c(aom aom) {
		if (aom instanceof bec) {
			this.b.a(new pm(aom.V()));
		} else {
			this.bR.add(aom.V());
		}
	}

	@Override
	public void d(aom aom) {
		this.bR.remove(aom.V());
	}

	@Override
	protected void C() {
		if (this.a_()) {
			this.de();
			this.j(true);
		} else {
			super.C();
		}
	}

	public aom D() {
		return (aom)(this.ci == null ? this : this.ci);
	}

	public void e(aom aom) {
		aom aom3 = this.D();
		this.ci = (aom)(aom == null ? this : aom);
		if (aom3 != this.ci) {
			this.b.a(new pt(this.ci));
			this.a(this.ci.cC(), this.ci.cD(), this.ci.cG());
		}
	}

	@Override
	protected void E() {
		if (this.ae > 0 && !this.cj) {
			this.ae--;
		}
	}

	@Override
	public void f(aom aom) {
		if (this.d.b() == bpy.SPECTATOR) {
			this.e(aom);
		} else {
			super.f(aom);
		}
	}

	public long F() {
		return this.ch;
	}

	@Nullable
	public mr G() {
		return null;
	}

	@Override
	public void a(anf anf) {
		super.a(anf);
		this.eS();
	}

	public boolean H() {
		return this.cj;
	}

	public void I() {
		this.cj = false;
	}

	public uq J() {
		return this.bS;
	}

	public void a(zd zd, double double2, double double3, double double4, float float5, float float6) {
		this.e(this);
		this.l();
		if (zd == this.l) {
			this.b.a(double2, double3, double4, float5, float6);
		} else {
			zd zd11 = this.u();
			dab dab12 = zd.u_();
			this.b.a(new pp(zd.V(), zd.W(), brg.a(zd.B()), this.d.b(), this.d.c(), zd.Z(), zd.A(), true));
			this.b.a(new nz(dab12.r(), dab12.s()));
			this.c.ac().d(this);
			zd11.e(this);
			this.y = false;
			this.b(double2, double3, double4, float5, float6);
			this.a_(zd);
			zd.a(this);
			this.c(zd11);
			this.b.a(double2, double3, double4, float5, float6);
			this.d.a(zd);
			this.c.ac().a(this, zd);
			this.c.ac().e(this);
		}
	}

	@Nullable
	public fu K() {
		return this.cs;
	}

	public ug<bqb> L() {
		return this.cr;
	}

	public boolean M() {
		return this.ct;
	}

	public void a(ug<bqb> ug, @Nullable fu fu, boolean boolean3, boolean boolean4) {
		if (fu != null) {
			boolean boolean6 = fu.equals(this.cs) && ug.equals(this.cr);
			if (boolean4 && !boolean6) {
				this.a(new ne("block.minecraft.set_spawn"), v.b);
			}

			this.cs = fu;
			this.cr = ug;
			this.ct = boolean3;
		} else {
			this.cs = null;
			this.cr = bqb.g;
			this.ct = false;
		}
	}

	public void a(bph bph, ni<?> ni2, ni<?> ni3) {
		this.b.a(ni3);
		this.b.a(ni2);
	}

	public void a(bph bph) {
		if (this.aU()) {
			this.b.a(new op(bph.b, bph.c));
		}
	}

	public go N() {
		return this.cq;
	}

	public void a(go go) {
		this.cq = go;
	}

	@Override
	public void a(ack ack, acm acm, float float3, float float4) {
		this.b.a(new qm(ack, acm, this.cC(), this.cD(), this.cG(), float3, float4));
	}

	@Override
	public ni<?> O() {
		return new nq(this);
	}

	@Override
	public bbg a(bki bki, boolean boolean2, boolean boolean3) {
		bbg bbg5 = super.a(bki, boolean2, boolean3);
		if (bbg5 == null) {
			return null;
		} else {
			this.l.c(bbg5);
			bki bki6 = bbg5.g();
			if (boolean3) {
				if (!bki6.a()) {
					this.a(acu.f.b(bki6.b()), bki.E());
				}

				this.a(acu.E);
			}

			return bbg5;
		}
	}
}
