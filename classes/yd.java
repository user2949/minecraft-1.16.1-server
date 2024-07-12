import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class yd extends MinecraftServer implements uv {
	private static final Logger j = LogManager.getLogger();
	private static final Pattern k = Pattern.compile("^[a-fA-F0-9]{40}$");
	private final List<ul> l = Collections.synchronizedList(Lists.newArrayList());
	private ace m;
	private final acb n;
	private acg o;
	private final yf p;
	@Nullable
	private yj q;

	public yd(
		Thread thread,
		gm.a a,
		dae.a a,
		aar<aap> aar,
		uw uw,
		dal dal,
		yf yf,
		DataFixer dataFixer,
		MinecraftSessionService minecraftSessionService,
		GameProfileRepository gameProfileRepository,
		abl abl,
		zn zn
	) {
		super(thread, a, a, dal, aar, Proxy.NO_PROXY, dataFixer, uw, minecraftSessionService, gameProfileRepository, abl, zn);
		this.p = yf;
		this.n = new acb(this);
	}

	@Override
	public boolean d() throws IOException {
		Thread thread2 = new Thread("Server console handler") {
			public void run() {
				BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

				String string3;
				try {
					while (!yd.this.ab() && yd.this.u() && (string3 = bufferedReader2.readLine()) != null) {
						yd.this.a(string3, yd.this.aC());
					}
				} catch (IOException var4) {
					yd.j.error("Exception handling console input", (Throwable)var4);
				}
			}
		};
		thread2.setDaemon(true);
		thread2.setUncaughtExceptionHandler(new m(j));
		thread2.start();
		j.info("Starting minecraft server version " + u.a().getName());
		if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
			j.warn("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
		}

		j.info("Loading properties");
		ye ye3 = this.p.a();
		if (this.N()) {
			this.a_("127.0.0.1");
		} else {
			this.d(ye3.a);
			this.e(ye3.b);
			this.a_(ye3.c);
		}

		this.f(ye3.f);
		this.g(ye3.g);
		this.a(ye3.h, this.aY());
		this.e(ye3.i);
		this.h(ye3.j);
		super.d(ye3.S.get());
		this.i(ye3.k);
		this.i.a(ye3.m);
		j.info("Default game type: {}", ye3.m);
		InetAddress inetAddress4 = null;
		if (!this.t().isEmpty()) {
			inetAddress4 = InetAddress.getByName(this.t());
		}

		if (this.L() < 0) {
			this.a(ye3.o);
		}

		j.info("Generating keypair");
		this.a(adn.b());
		j.info("Starting Minecraft server on {}:{}", this.t().isEmpty() ? "*" : this.t(), this.L());

		try {
			this.ad().a(inetAddress4, this.L());
		} catch (IOException var10) {
			j.warn("**** FAILED TO BIND TO PORT!");
			j.warn("The exception was: {}", var10.toString());
			j.warn("Perhaps a server is already running on that port?");
			return false;
		}

		if (!this.T()) {
			j.warn("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
			j.warn("The server will make no attempt to authenticate usernames. Beware.");
			j.warn(
				"While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose."
			);
			j.warn("To change this, set \"online-mode\" to \"true\" in the server.properties file.");
		}

		if (this.bc()) {
			this.ap().c();
		}

		if (!abo.e(this)) {
			return false;
		} else {
			this.a(new yc(this, this.f, this.e));
			long long5 = v.c();
			this.c(ye3.p);
			cei.a(this.ap());
			cei.a(this.an());
			abl.a(this.T());
			j.info("Preparing level \"{}\"", this.k_());
			this.l_();
			long long7 = v.c() - long5;
			String string9 = String.format(Locale.ROOT, "%.3fs", (double)long7 / 1.0E9);
			j.info("Done ({})! For help, type \"help\"", string9);
			if (ye3.q != null) {
				this.aJ().a(bpx.w).a(ye3.q, this);
			}

			if (ye3.r) {
				j.info("Starting GS4 status listener");
				this.m = new ace(this);
				this.m.a();
			}

			if (ye3.t) {
				j.info("Starting remote control listener");
				this.o = new acg(this);
				this.o.a();
			}

			if (this.bd() > 0L) {
				Thread thread10 = new Thread(new yg(this));
				thread10.setUncaughtExceptionHandler(new n(j));
				thread10.setName("Server Watchdog");
				thread10.setDaemon(true);
				thread10.start();
			}

			bkk.a.a(biy.g, gi.a());
			if (ye3.P) {
				alz.a(this);
			}

			return true;
		}
	}

	@Override
	public boolean V() {
		return this.g_().d && super.V();
	}

	@Override
	public boolean O() {
		return this.p.a().A && super.O();
	}

	@Override
	public boolean W() {
		return this.p.a().e && super.W();
	}

	public String aY() {
		ye ye2 = this.p.a();
		String string3;
		if (!ye2.x.isEmpty()) {
			string3 = ye2.x;
			if (!Strings.isNullOrEmpty(ye2.w)) {
				j.warn("resource-pack-hash is deprecated and found along side resource-pack-sha1. resource-pack-hash will be ignored.");
			}
		} else if (!Strings.isNullOrEmpty(ye2.w)) {
			j.warn("resource-pack-hash is deprecated. Please use resource-pack-sha1 instead.");
			string3 = ye2.w;
		} else {
			string3 = "";
		}

		if (!string3.isEmpty() && !k.matcher(string3).matches()) {
			j.warn("Invalid sha1 for ressource-pack-sha1");
		}

		if (!ye2.h.isEmpty() && string3.isEmpty()) {
			j.warn("You specified a resource pack without providing a sha1 hash. Pack will be updated on the client only if you change the name of the pack.");
		}

		return string3;
	}

	@Override
	public ye g_() {
		return this.p.a();
	}

	@Override
	public void p() {
		this.a(this.g_().l, true);
	}

	@Override
	public boolean f() {
		return this.g_().y;
	}

	@Override
	public j b(j j) {
		j = super.b(j);
		j.g().a("Is Modded", (l<String>)(() -> (String)this.n().orElse("Unknown (can't tell)")));
		j.g().a("Type", (l<String>)(() -> "Dedicated Server (map_server.txt)"));
		return j;
	}

	@Override
	public Optional<String> n() {
		String string2 = this.getServerModName();
		return !"vanilla".equals(string2) ? Optional.of("Definitely; Server brand changed to '" + string2 + "'") : Optional.empty();
	}

	@Override
	public void e() {
		if (this.q != null) {
			this.q.b();
		}

		if (this.o != null) {
			this.o.b();
		}

		if (this.m != null) {
			this.m.b();
		}
	}

	@Override
	public void b(BooleanSupplier booleanSupplier) {
		super.b(booleanSupplier);
		this.aZ();
	}

	@Override
	public boolean B() {
		return this.g_().z;
	}

	@Override
	public void a(ano ano) {
		ano.a("whitelist_enabled", this.ba().o());
		ano.a("whitelist_count", this.ba().j().length);
		super.a(ano);
	}

	public void a(String string, cz cz) {
		this.l.add(new ul(string, cz));
	}

	public void aZ() {
		while (!this.l.isEmpty()) {
			ul ul2 = (ul)this.l.remove(0);
			this.aB().a(ul2.b, ul2.a);
		}
	}

	@Override
	public boolean j() {
		return true;
	}

	@Override
	public boolean k() {
		return this.g_().C;
	}

	public yc ac() {
		return (yc)super.ac();
	}

	@Override
	public boolean m() {
		return true;
	}

	@Override
	public String h_() {
		return this.t();
	}

	@Override
	public int o() {
		return this.L();
	}

	@Override
	public String i_() {
		return this.Z();
	}

	public void bb() {
		if (this.q == null) {
			this.q = yj.a(this);
		}
	}

	@Override
	public boolean af() {
		return this.q != null;
	}

	@Override
	public boolean a(bpy bpy, boolean boolean2, int integer) {
		return false;
	}

	@Override
	public boolean l() {
		return this.g_().D;
	}

	@Override
	public int ai() {
		return this.g_().E;
	}

	@Override
	public boolean a(zd zd, fu fu, bec bec) {
		if (zd.W() != bqb.g) {
			return false;
		} else if (this.ba().k().c()) {
			return false;
		} else if (this.ba().h(bec.ez())) {
			return false;
		} else if (this.ai() <= 0) {
			return false;
		} else {
			fu fu5 = zd.u();
			int integer6 = aec.a(fu.u() - fu5.u());
			int integer7 = aec.a(fu.w() - fu5.w());
			int integer8 = Math.max(integer6, integer7);
			return integer8 <= this.ai();
		}
	}

	@Override
	public boolean ak() {
		return this.g_().Q;
	}

	@Override
	public int g() {
		return this.g_().F;
	}

	@Override
	public int h() {
		return this.g_().G;
	}

	@Override
	public void d(int integer) {
		super.d(integer);
		this.p.a(ye -> (ye)ye.S.a(integer));
	}

	@Override
	public boolean i() {
		return this.g_().L;
	}

	@Override
	public boolean S_() {
		return this.g_().M;
	}

	@Override
	public int as() {
		return this.g_().N;
	}

	@Override
	public int av() {
		return this.g_().K;
	}

	protected boolean bc() {
		boolean boolean3 = false;

		for (int integer2 = 0; !boolean3 && integer2 <= 2; integer2++) {
			if (integer2 > 0) {
				j.warn("Encountered a problem while converting the user banlist, retrying in a few seconds");
				this.bm();
			}

			boolean3 = abo.a((MinecraftServer)this);
		}

		boolean boolean4 = false;

		for (int var7 = 0; !boolean4 && var7 <= 2; var7++) {
			if (var7 > 0) {
				j.warn("Encountered a problem while converting the ip banlist, retrying in a few seconds");
				this.bm();
			}

			boolean4 = abo.b(this);
		}

		boolean boolean5 = false;

		for (int var8 = 0; !boolean5 && var8 <= 2; var8++) {
			if (var8 > 0) {
				j.warn("Encountered a problem while converting the op list, retrying in a few seconds");
				this.bm();
			}

			boolean5 = abo.c(this);
		}

		boolean boolean6 = false;

		for (int var9 = 0; !boolean6 && var9 <= 2; var9++) {
			if (var9 > 0) {
				j.warn("Encountered a problem while converting the whitelist, retrying in a few seconds");
				this.bm();
			}

			boolean6 = abo.d(this);
		}

		boolean boolean7 = false;

		for (int var10 = 0; !boolean7 && var10 <= 2; var10++) {
			if (var10 > 0) {
				j.warn("Encountered a problem while converting the player save files, retrying in a few seconds");
				this.bm();
			}

			boolean7 = abo.a(this);
		}

		return boolean3 || boolean4 || boolean5 || boolean6 || boolean7;
	}

	private void bm() {
		try {
			Thread.sleep(5000L);
		} catch (InterruptedException var2) {
		}
	}

	public long bd() {
		return this.g_().H;
	}

	@Override
	public String j_() {
		return "";
	}

	@Override
	public String a(String string) {
		this.n.d();
		this.g(() -> this.aB().a(this.n.f(), string));
		return this.n.e();
	}

	public void j(boolean boolean1) {
		this.p.a(ye -> (ye)ye.T.a(boolean1));
	}

	@Override
	public void s() {
		super.s();
		v.h();
	}

	@Override
	public boolean a(GameProfile gameProfile) {
		return false;
	}

	@Override
	public int b(int integer) {
		return this.g_().R * integer / 100;
	}

	@Override
	public String k_() {
		return this.d.a();
	}

	@Override
	public boolean aT() {
		return this.p.a().O;
	}
}
