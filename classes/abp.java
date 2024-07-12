import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Dynamic;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class abp {
	public static final File b = new File("banned-players.json");
	public static final File c = new File("banned-ips.json");
	public static final File d = new File("ops.json");
	public static final File e = new File("whitelist.json");
	private static final Logger a = LogManager.getLogger();
	private static final SimpleDateFormat g = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
	private final MinecraftServer h;
	private final List<ze> i = Lists.<ze>newArrayList();
	private final Map<UUID, ze> j = Maps.<UUID, ze>newHashMap();
	private final abu k = new abu(b);
	private final abm l = new abm(c);
	private final abq m = new abq(d);
	private final abw n = new abw(e);
	private final Map<UUID, acq> o = Maps.<UUID, acq>newHashMap();
	private final Map<UUID, uq> p = Maps.<UUID, uq>newHashMap();
	private final dai q;
	private boolean r;
	private final gm.a s;
	protected final int f;
	private int t;
	private bpy u;
	private boolean v;
	private int w;

	public abp(MinecraftServer minecraftServer, gm.a a, dai dai, int integer) {
		this.h = minecraftServer;
		this.s = a;
		this.f = integer;
		this.q = dai;
	}

	public void a(me me, ze ze) {
		GameProfile gameProfile4 = ze.ez();
		abl abl5 = this.h.ap();
		GameProfile gameProfile6 = abl5.a(gameProfile4.getId());
		String string7 = gameProfile6 == null ? gameProfile4.getName() : gameProfile6.getName();
		abl5.a(gameProfile4);
		le le8 = this.a(ze);
		ug<bqb> ug9 = le8 != null ? (ug)cif.a(new Dynamic<>(lp.a, le8.c("Dimension"))).resultOrPartial(a::error).orElse(bqb.g) : bqb.g;
		zd zd10 = this.h.a(ug9);
		zd zd11;
		if (zd10 == null) {
			a.warn("Unknown respawn dimension {}, defaulting to overworld", ug9);
			zd11 = this.h.D();
		} else {
			zd11 = zd10;
		}

		ze.a_(zd11);
		ze.d.a((zd)ze.l);
		String string12 = "local";
		if (me.b() != null) {
			string12 = me.b().toString();
		}

		a.info("{}[{}] logged in with entity id {} at ({}, {}, {})", ze.P().getString(), string12, ze.V(), ze.cC(), ze.cD(), ze.cG());
		dab dab13 = zd11.u_();
		this.a(ze, null, zd11);
		zv zv14 = new zv(this.h, me, ze);
		bpx bpx15 = zd11.S();
		boolean boolean16 = bpx15.b(bpx.z);
		boolean boolean17 = bpx15.b(bpx.o);
		zv14.a(
			new ox(
				ze.V(), ze.d.b(), ze.d.c(), brg.a(zd11.B()), dab13.m(), this.h.E(), this.s, zd11.V(), zd11.W(), this.n(), this.t, boolean17, !boolean16, zd11.Z(), zd11.A()
			)
		);
		zv14.a(new ok(ok.a, new mg(Unpooled.buffer()).a(this.c().getServerModName())));
		zv14.a(new nz(dab13.r(), dab13.s()));
		zv14.a(new pg(ze.bJ));
		zv14.a(new pu(ze.bt.d));
		zv14.a(new qv(this.h.aD().b()));
		zv14.a(new qw(this.h.aE()));
		this.d(ze);
		ze.A().c();
		ze.B().a(ze);
		this.a(zd11.o_(), ze);
		this.h.ar();
		mx mx18;
		if (ze.ez().getName().equalsIgnoreCase(string7)) {
			mx18 = new ne("multiplayer.player.joined", ze.d());
		} else {
			mx18 = new ne("multiplayer.player.joined.renamed", ze.d(), string7);
		}

		this.a(mx18.a(i.YELLOW), mo.SYSTEM, v.b);
		zv14.a(ze.cC(), ze.cD(), ze.cG(), ze.p, ze.q);
		this.i.add(ze);
		this.j.put(ze.bR(), ze);
		this.a(new pi(pi.a.ADD_PLAYER, ze));

		for (int integer19 = 0; integer19 < this.i.size(); integer19++) {
			ze.b.a(new pi(pi.a.ADD_PLAYER, (ze)this.i.get(integer19)));
		}

		zd11.c(ze);
		this.h.aK().a(ze);
		this.a(ze, zd11);
		if (!this.h.Q().isEmpty()) {
			ze.a(this.h.Q(), this.h.R());
		}

		for (aog aog20 : ze.dg()) {
			zv14.a(new qu(ze.V(), aog20));
		}

		if (le8 != null && le8.c("RootVehicle", 10)) {
			le le19 = le8.p("RootVehicle");
			aom aom20 = aoq.a(le19.p("Entity"), zd11, aom -> !zd11.d(aom) ? null : aom);
			if (aom20 != null) {
				UUID uUID21;
				if (le19.b("Attach")) {
					uUID21 = le19.a("Attach");
				} else {
					uUID21 = null;
				}

				if (aom20.bR().equals(uUID21)) {
					ze.a(aom20, true);
				} else {
					for (aom aom23 : aom20.cn()) {
						if (aom23.bR().equals(uUID21)) {
							ze.a(aom23, true);
							break;
						}
					}
				}

				if (!ze.bn()) {
					a.warn("Couldn't reattach entity to player");
					zd11.h(aom20);

					for (aom aom23x : aom20.cn()) {
						zd11.h(aom23x);
					}
				}
			}
		}

		ze.f();
	}

	protected void a(ux ux, ze ze) {
		Set<dfj> set4 = Sets.<dfj>newHashSet();

		for (dfk dfk6 : ux.g()) {
			ze.b.a(new qh(dfk6, 0));
		}

		for (int integer5 = 0; integer5 < 19; integer5++) {
			dfj dfj6 = ux.a(integer5);
			if (dfj6 != null && !set4.contains(dfj6)) {
				for (ni<?> ni9 : ux.d(dfj6)) {
					ze.b.a(ni9);
				}

				set4.add(dfj6);
			}
		}
	}

	public void a(zd zd) {
		zd.f().a(new cgu() {
			@Override
			public void a(cgw cgw, double double2) {
				abp.this.a(new ps(cgw, ps.a.SET_SIZE));
			}

			@Override
			public void a(cgw cgw, double double2, double double3, long long4) {
				abp.this.a(new ps(cgw, ps.a.LERP_SIZE));
			}

			@Override
			public void a(cgw cgw, double double2, double double3) {
				abp.this.a(new ps(cgw, ps.a.SET_CENTER));
			}

			@Override
			public void a(cgw cgw, int integer) {
				abp.this.a(new ps(cgw, ps.a.SET_WARNING_TIME));
			}

			@Override
			public void b(cgw cgw, int integer) {
				abp.this.a(new ps(cgw, ps.a.SET_WARNING_BLOCKS));
			}

			@Override
			public void b(cgw cgw, double double2) {
			}

			@Override
			public void c(cgw cgw, double double2) {
			}
		});
	}

	@Nullable
	public le a(ze ze) {
		le le3 = this.h.aV().x();
		le le4;
		if (ze.P().getString().equals(this.h.M()) && le3 != null) {
			le4 = le3;
			ze.f(le3);
			a.debug("loading single player");
		} else {
			le4 = this.q.b(ze);
		}

		return le4;
	}

	protected void b(ze ze) {
		this.q.a(ze);
		acq acq3 = (acq)this.o.get(ze.bR());
		if (acq3 != null) {
			acq3.a();
		}

		uq uq4 = (uq)this.p.get(ze.bR());
		if (uq4 != null) {
			uq4.b();
		}
	}

	public void c(ze ze) {
		zd zd3 = ze.u();
		ze.a(acu.j);
		this.b(ze);
		if (ze.bn()) {
			aom aom4 = ze.cq();
			if (aom4.cp()) {
				a.debug("Removing player mount");
				ze.l();
				zd3.h(aom4);
				aom4.y = true;

				for (aom aom6 : aom4.cn()) {
					zd3.h(aom6);
					aom6.y = true;
				}

				zd3.d(ze.W, ze.Y).s();
			}
		}

		ze.T();
		zd3.e(ze);
		ze.J().a();
		this.i.remove(ze);
		this.h.aK().b(ze);
		UUID uUID4 = ze.bR();
		ze ze5 = (ze)this.j.get(uUID4);
		if (ze5 == ze) {
			this.j.remove(uUID4);
			this.o.remove(uUID4);
			this.p.remove(uUID4);
		}

		this.a(new pi(pi.a.REMOVE_PLAYER, ze));
	}

	@Nullable
	public mr a(SocketAddress socketAddress, GameProfile gameProfile) {
		if (this.k.a(gameProfile)) {
			abv abv4 = this.k.b(gameProfile);
			mx mx5 = new ne("multiplayer.disconnect.banned.reason", abv4.d());
			if (abv4.c() != null) {
				mx5.a(new ne("multiplayer.disconnect.banned.expiration", g.format(abv4.c())));
			}

			return mx5;
		} else if (!this.e(gameProfile)) {
			return new ne("multiplayer.disconnect.not_whitelisted");
		} else if (this.l.a(socketAddress)) {
			abn abn4 = this.l.b(socketAddress);
			mx mx5 = new ne("multiplayer.disconnect.banned_ip.reason", abn4.d());
			if (abn4.c() != null) {
				mx5.a(new ne("multiplayer.disconnect.banned_ip.expiration", g.format(abn4.c())));
			}

			return mx5;
		} else {
			return this.i.size() >= this.f && !this.f(gameProfile) ? new ne("multiplayer.disconnect.server_full") : null;
		}
	}

	public ze g(GameProfile gameProfile) {
		UUID uUID3 = bec.a(gameProfile);
		List<ze> list4 = Lists.<ze>newArrayList();

		for (int integer5 = 0; integer5 < this.i.size(); integer5++) {
			ze ze6 = (ze)this.i.get(integer5);
			if (ze6.bR().equals(uUID3)) {
				list4.add(ze6);
			}
		}

		ze ze5 = (ze)this.j.get(gameProfile.getId());
		if (ze5 != null && !list4.contains(ze5)) {
			list4.add(ze5);
		}

		for (ze ze7 : list4) {
			ze7.b.b(new ne("multiplayer.disconnect.duplicate_login"));
		}

		zd zd7 = this.h.D();
		zf zf6;
		if (this.h.P()) {
			zf6 = new yu(zd7);
		} else {
			zf6 = new zf(zd7);
		}

		return new ze(this.h, zd7, gameProfile, zf6);
	}

	public ze a(ze ze, boolean boolean2) {
		this.i.remove(ze);
		ze.u().e(ze);
		fu fu4 = ze.K();
		boolean boolean5 = ze.M();
		zd zd6 = this.h.a(ze.L());
		Optional<dem> optional7;
		if (zd6 != null && fu4 != null) {
			optional7 = bec.a(zd6, fu4, boolean5, boolean2);
		} else {
			optional7 = Optional.empty();
		}

		zd zd9 = zd6 != null && optional7.isPresent() ? zd6 : this.h.D();
		zf zf8;
		if (this.h.P()) {
			zf8 = new yu(zd9);
		} else {
			zf8 = new zf(zd9);
		}

		ze ze10 = new ze(this.h, zd9, ze.ez(), zf8);
		ze10.b = ze.b;
		ze10.a(ze, boolean2);
		ze10.e(ze.V());
		ze10.a(ze.dU());

		for (String string12 : ze.W()) {
			ze10.a(string12);
		}

		this.a(ze10, ze, zd9);
		boolean boolean11 = false;
		if (optional7.isPresent()) {
			dem dem12 = (dem)optional7.get();
			ze10.b(dem12.b, dem12.c, dem12.d, 0.0F, 0.0F);
			ze10.a(zd9.W(), fu4, boolean5, false);
			boolean11 = !boolean2 && zd9.d_(fu4).b() instanceof cam;
		} else if (fu4 != null) {
			ze10.b.a(new oq(oq.a, 0.0F));
		}

		while (!zd9.j(ze10) && ze10.cD() < 256.0) {
			ze10.d(ze10.cC(), ze10.cD() + 1.0, ze10.cG());
		}

		dab dab12 = ze10.l.u_();
		ze10.b.a(new pp(ze10.l.V(), ze10.l.W(), brg.a(ze10.u().B()), ze10.d.b(), ze10.d.c(), ze10.u().Z(), ze10.u().A(), boolean2));
		ze10.b.a(ze10.cC(), ze10.cD(), ze10.cG(), ze10.p, ze10.q);
		ze10.b.a(new px(zd9.u()));
		ze10.b.a(new nz(dab12.r(), dab12.s()));
		ze10.b.a(new qd(ze10.bM, ze10.bL, ze10.bK));
		this.a(ze10, zd9);
		this.d(ze10);
		zd9.d(ze10);
		this.i.add(ze10);
		this.j.put(ze10.bR(), ze10);
		ze10.f();
		ze10.c(ze10.dj());
		if (boolean11) {
			ze10.b.a(new qm(acl.ms, acm.BLOCKS, (double)fu4.u(), (double)fu4.v(), (double)fu4.w(), 1.0F, 1.0F));
		}

		return ze10;
	}

	public void d(ze ze) {
		GameProfile gameProfile3 = ze.ez();
		int integer4 = this.h.b(gameProfile3);
		this.a(ze, integer4);
	}

	public void d() {
		if (++this.w > 600) {
			this.a(new pi(pi.a.UPDATE_LATENCY, this.i));
			this.w = 0;
		}
	}

	public void a(ni<?> ni) {
		for (int integer3 = 0; integer3 < this.i.size(); integer3++) {
			((ze)this.i.get(integer3)).b.a(ni);
		}
	}

	public void a(ni<?> ni, ug<bqb> ug) {
		for (int integer4 = 0; integer4 < this.i.size(); integer4++) {
			ze ze5 = (ze)this.i.get(integer4);
			if (ze5.l.W() == ug) {
				ze5.b.a(ni);
			}
		}
	}

	public void a(bec bec, mr mr) {
		dfo dfo4 = bec.bC();
		if (dfo4 != null) {
			for (String string7 : dfo4.g()) {
				ze ze8 = this.a(string7);
				if (ze8 != null && ze8 != bec) {
					ze8.a(mr, bec.bR());
				}
			}
		}
	}

	public void b(bec bec, mr mr) {
		dfo dfo4 = bec.bC();
		if (dfo4 == null) {
			this.a(mr, mo.SYSTEM, bec.bR());
		} else {
			for (int integer5 = 0; integer5 < this.i.size(); integer5++) {
				ze ze6 = (ze)this.i.get(integer5);
				if (ze6.bC() != dfo4) {
					ze6.a(mr, bec.bR());
				}
			}
		}
	}

	public String[] e() {
		String[] arr2 = new String[this.i.size()];

		for (int integer3 = 0; integer3 < this.i.size(); integer3++) {
			arr2[integer3] = ((ze)this.i.get(integer3)).ez().getName();
		}

		return arr2;
	}

	public abu f() {
		return this.k;
	}

	public abm g() {
		return this.l;
	}

	public void a(GameProfile gameProfile) {
		this.m.a(new abr(gameProfile, this.h.g(), this.m.b(gameProfile)));
		ze ze3 = this.a(gameProfile.getId());
		if (ze3 != null) {
			this.d(ze3);
		}
	}

	public void b(GameProfile gameProfile) {
		this.m.c(gameProfile);
		ze ze3 = this.a(gameProfile.getId());
		if (ze3 != null) {
			this.d(ze3);
		}
	}

	private void a(ze ze, int integer) {
		if (ze.b != null) {
			byte byte4;
			if (integer <= 0) {
				byte4 = 24;
			} else if (integer >= 4) {
				byte4 = 28;
			} else {
				byte4 = (byte)(24 + integer);
			}

			ze.b.a(new on(ze, byte4));
		}

		this.h.aB().a(ze);
	}

	public boolean e(GameProfile gameProfile) {
		return !this.r || this.m.d(gameProfile) || this.n.d(gameProfile);
	}

	public boolean h(GameProfile gameProfile) {
		return this.m.d(gameProfile) || this.h.a(gameProfile) && this.h.aV().n() || this.v;
	}

	@Nullable
	public ze a(String string) {
		for (ze ze4 : this.i) {
			if (ze4.ez().getName().equalsIgnoreCase(string)) {
				return ze4;
			}
		}

		return null;
	}

	public void a(@Nullable bec bec, double double2, double double3, double double4, double double5, ug<bqb> ug, ni<?> ni) {
		for (int integer13 = 0; integer13 < this.i.size(); integer13++) {
			ze ze14 = (ze)this.i.get(integer13);
			if (ze14 != bec && ze14.l.W() == ug) {
				double double15 = double2 - ze14.cC();
				double double17 = double3 - ze14.cD();
				double double19 = double4 - ze14.cG();
				if (double15 * double15 + double17 * double17 + double19 * double19 < double5 * double5) {
					ze14.b.a(ni);
				}
			}
		}
	}

	public void h() {
		for (int integer2 = 0; integer2 < this.i.size(); integer2++) {
			this.b((ze)this.i.get(integer2));
		}
	}

	public abw i() {
		return this.n;
	}

	public String[] j() {
		return this.n.a();
	}

	public abq k() {
		return this.m;
	}

	public String[] l() {
		return this.m.a();
	}

	public void a() {
	}

	public void a(ze ze, zd zd) {
		cgw cgw4 = this.h.D().f();
		ze.b.a(new ps(cgw4, ps.a.INITIALIZE));
		ze.b.a(new qj(zd.Q(), zd.R(), zd.S().b(bpx.j)));
		ze.b.a(new px(zd.u()));
		if (zd.U()) {
			ze.b.a(new oq(oq.b, 0.0F));
			ze.b.a(new oq(oq.h, zd.d(1.0F)));
			ze.b.a(new oq(oq.i, zd.b(1.0F)));
		}
	}

	public void e(ze ze) {
		ze.a(ze.bv);
		ze.r();
		ze.b.a(new pu(ze.bt.d));
	}

	public int m() {
		return this.i.size();
	}

	public int n() {
		return this.f;
	}

	public boolean o() {
		return this.r;
	}

	public void a(boolean boolean1) {
		this.r = boolean1;
	}

	public List<ze> b(String string) {
		List<ze> list3 = Lists.<ze>newArrayList();

		for (ze ze5 : this.i) {
			if (ze5.v().equals(string)) {
				list3.add(ze5);
			}
		}

		return list3;
	}

	public int p() {
		return this.t;
	}

	public MinecraftServer c() {
		return this.h;
	}

	public le q() {
		return null;
	}

	private void a(ze ze1, @Nullable ze ze2, zd zd) {
		if (ze2 != null) {
			ze1.d.a(ze2.d.b(), ze2.d.c());
		} else if (this.u != null) {
			ze1.d.a(this.u, bpy.NOT_SET);
		}

		ze1.d.b(zd.l().aV().l());
	}

	public void r() {
		for (int integer2 = 0; integer2 < this.i.size(); integer2++) {
			((ze)this.i.get(integer2)).b.b(new ne("multiplayer.disconnect.server_shutdown"));
		}
	}

	public void a(mr mr, mo mo, UUID uUID) {
		this.h.a(mr, uUID);
		this.a(new oa(mr, mo, uUID));
	}

	public acq a(bec bec) {
		UUID uUID3 = bec.bR();
		acq acq4 = uUID3 == null ? null : (acq)this.o.get(uUID3);
		if (acq4 == null) {
			File file5 = this.h.a(dac.b).toFile();
			File file6 = new File(file5, uUID3 + ".json");
			if (!file6.exists()) {
				File file7 = new File(file5, bec.P().getString() + ".json");
				if (file7.exists() && file7.isFile()) {
					file7.renameTo(file6);
				}
			}

			acq4 = new acq(this.h, file6);
			this.o.put(uUID3, acq4);
		}

		return acq4;
	}

	public uq f(ze ze) {
		UUID uUID3 = ze.bR();
		uq uq4 = (uq)this.p.get(uUID3);
		if (uq4 == null) {
			File file5 = this.h.a(dac.a).toFile();
			File file6 = new File(file5, uUID3 + ".json");
			uq4 = new uq(this.h.ax(), this, this.h.ay(), file6, ze);
			this.p.put(uUID3, uq4);
		}

		uq4.a(ze);
		return uq4;
	}

	public void a(int integer) {
		this.t = integer;
		this.a(new pw(integer));

		for (zd zd4 : this.h.F()) {
			if (zd4 != null) {
				zd4.i().a(integer);
			}
		}
	}

	public List<ze> s() {
		return this.i;
	}

	@Nullable
	public ze a(UUID uUID) {
		return (ze)this.j.get(uUID);
	}

	public boolean f(GameProfile gameProfile) {
		return false;
	}

	public void t() {
		for (uq uq3 : this.p.values()) {
			uq3.a(this.h.ay());
		}

		this.a(new qw(this.h.aE()));
		qv qv2 = new qv(this.h.aD().b());

		for (ze ze4 : this.i) {
			ze4.b.a(qv2);
			ze4.B().a(ze4);
		}
	}

	public boolean u() {
		return this.v;
	}
}
